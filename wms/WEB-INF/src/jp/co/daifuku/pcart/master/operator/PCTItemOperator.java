// $Id: PCTItemOperator.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.PCTItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.Entity;


/**
 * PCT出庫予定操作用のオペレータクラスです<br>
 *
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  020246
 * @author  Last commit: $Author: arai $
 */


public class PCTItemOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * PCT商品マスタハンドラ
     */
    private PCTItemHandler _piHndl;

    /**
     * PCT商品マスタ検索キー
     */
    private PCTItemSearchKey _piSKey;

    /**
     * PCT商品マスタ更新キー
     */
    private PCTItemAlterKey _piAKey;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して、インスタンスを生成します。<BR>
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public PCTItemOperator(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);

        // PCT商品マスタ
        _piHndl = new PCTItemHandler(conn);
        _piSKey = new PCTItemSearchKey();
        _piAKey = new PCTItemAlterKey();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * PCT商品マスタ検索<BR>
     * PctSystemInParameterの検索条件に一致するPCT商品マスタ情報を検索し、戻り値で返します。<BR>
     * 一致したデータが無かった場合はnullを返します。<BR>
     * 
     * @param param 検索用パラメータ
     * @return Entity[] PCT商品マスタの検索結果
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPCTItem(Parameter param)
            throws ReadWriteException
    {
        // キャスト
        PCTMasterInParameter pctParam = (PCTMasterInParameter)param;

        // 検索条件セット
        _piSKey.clear();
        _piSKey.setConsignorCode(pctParam.getConsignorCode());
        _piSKey.setItemCode(pctParam.getItemCode());
        // TODO テスト納入製版用 START
        _piSKey.setLotEnteringQty(pctParam.getLotEnteringQty());
        // TODO テスト納入製版用 END

        // PCT商品マスタ情報の取得
        Entity[] ent = _piHndl.find(_piSKey);
        if (ent == null || ent.length == 0)
        {
            return null;
        }
        else
        {
            return ent;
        }
    }

    /**
     * PCT商品マスタ削除<BR>
     * startParamで指定されたパラメータの内容をもとに、PCT商品マスタデータ削除処理を行います。<BR>
     * 削除失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public void deletePCTItem(String registKind, Parameter startParam)
            throws NotFoundException,
                ReadWriteException,
                ScheduleException
    {
        // キャスト
        PCTMasterInParameter pInParam = (PCTMasterInParameter)startParam;

        // PCT商品マスタ削除条件セット
        _piSKey.clear();
        if (!(StringUtil.isBlank(pInParam.getConsignorCode()) && StringUtil.isBlank(pInParam.getItemCode())))
        {
            // 荷主コード、商品コードをセット
            _piSKey.setConsignorCode(pInParam.getConsignorCode());
            _piSKey.setItemCode(pInParam.getItemCode());
            // TODO テスト納入製版用 START
            // ロット入数をセット
            _piSKey.setLotEnteringQty(pInParam.getLotEnteringQty());
            // TODO テスト納入製版用 END
        }
        else
        {
            // 検索条件が不足している場合、ScheduleExceptionをスローします。
            throw new ScheduleException();
        }

        // PCT出庫予定削除
        _piHndl.drop(_piSKey);
    }

    /**
     * PCT商品マスタ修正<BR>
     * startParamで指定されたパラメータの内容をもとに、PCT商品マスタデータ削除処理を行います。<BR>
     * 削除失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public void modifyPCTItem(String registKind, Parameter startParam)
            throws NotFoundException,
                ReadWriteException,
                ScheduleException
    {
        // キャスト
        PCTMasterInParameter pInParam = (PCTMasterInParameter)startParam;

        // PCT商品マスタ修正条件セット
        _piAKey.clear();
        if (!(StringUtil.isBlank(pInParam.getConsignorCode()) && StringUtil.isBlank(pInParam.getItemCode())))
        {
            // 更新条件をセット
            // 荷主コード
            _piAKey.setConsignorCode(pInParam.getConsignorCode());
            // 商品コード
            _piAKey.setItemCode(pInParam.getItemCode());
            // ロット入数
            _piAKey.setLotEnteringQty(pInParam.getLotEnteringQty());

            // 更新内容をセット
            // 荷主名称
            if (!StringUtil.isBlank(pInParam.getConsignorName()))
            {
                _piAKey.updateConsignorName(pInParam.getConsignorName());
            }
            // 商品名称
            if (!StringUtil.isBlank(pInParam.getItemName()))
            {
                _piAKey.updateItemName(pInParam.getItemName());
            }
            // JANコード
            if (!StringUtil.isBlank(pInParam.getJan()))
            {
                _piAKey.updateJan(pInParam.getJan());
            }
            // ケースITF
            if (!StringUtil.isBlank(pInParam.getItf()))
            {
                _piAKey.updateItf(pInParam.getItf());
            }
            // ボールITF
            if (!StringUtil.isBlank(pInParam.getBundleItf()))
            {
                _piAKey.updateBundleItf(pInParam.getBundleItf());
            }
            // ケース入数 
            if (0 < pInParam.getEnteringQty())
            {
                _piAKey.updateEnteringQty(pInParam.getEnteringQty());
            }
            // ボール入数 
            if (0 < pInParam.getBundleEnteringQty())
            {
                _piAKey.updateBundleEnteringQty(pInParam.getBundleEnteringQty());
            }
            // 単位
            if (0 < pInParam.getUnit())
            {
                _piAKey.updateUnit(pInParam.getUnit());
            }
            // 単位重量
            if (0.0 < pInParam.getSingleWeight())
            {
                _piAKey.updateSingleWeight(pInParam.getSingleWeight());
            }
            // 誤差率
            if (0.0 < pInParam.getWeightDistinctRate())
            {
                // 誤差率
                _piAKey.updateWeightDistinctRate(pInParam.getWeightDistinctRate());
                // 最大検品単位数
                _piAKey.updateMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(pInParam.getWeightDistinctRate()));
            }
            // ロケーションNo.1
            if (!StringUtil.isBlank(pInParam.getLocationNo1()))
            {
                _piAKey.updateLocationNo1(pInParam.getLocationNo1());
            }
            // ロケーション入数1
            if (0 < pInParam.getEnteringQty1())
            {
                _piAKey.updateEnteringQty1(pInParam.getEnteringQty1());
            }
            // ロケーションNo.2
            if (!StringUtil.isBlank(pInParam.getLocationNo2()))
            {
                _piAKey.updateLocationNo2(pInParam.getLocationNo2());
            }
            // ロケーション入数2
            if (0 < pInParam.getEnteringQty2())
            {
                _piAKey.updateEnteringQty2(pInParam.getEnteringQty2());
            }
            // ロケーションNo.3
            if (!StringUtil.isBlank(pInParam.getLocationNo3()))
            {
                _piAKey.updateLocationNo3(pInParam.getLocationNo3());
            }
            // ロケーション入数3
            if (0 < pInParam.getEnteringQty3())
            {
                _piAKey.updateEnteringQty3(pInParam.getEnteringQty3());
            }
            // ロケーションNo.4
            if (!StringUtil.isBlank(pInParam.getLocationNo4()))
            {
                _piAKey.updateLocationNo4(pInParam.getLocationNo4());
            }
            // ロケーション入数4
            if (0 < pInParam.getEnteringQty4())
            {
                _piAKey.updateEnteringQty4(pInParam.getEnteringQty4());
            }
            // メッセージ 
            if (!StringUtil.isBlank(pInParam.getInformation()))
            {
                _piAKey.updateInformation(pInParam.getInformation());
            }
            // 賞味期限
            if (!StringUtil.isBlank(pInParam.getUseByPeriod()))
            {
                _piAKey.updateUseByPeriod(pInParam.getUseByPeriod());
            }
            // 入荷限度日
            if (!StringUtil.isBlank(pInParam.getInstockLimitDate()))
            {
                _piAKey.updateInstockLimitDate(pInParam.getInstockLimitDate());
            }
            // 出荷限度日
            if (!StringUtil.isBlank(pInParam.getShippingLimitDate()))
            {
                _piAKey.updateShippingLimitDate(pInParam.getShippingLimitDate());
            }
            // 最新賞味期限
            if (!StringUtil.isBlank(pInParam.getLatestUseByDate()))
            {
                _piAKey.updateLatestUseByDate(pInParam.getLatestUseByDate());
            }
            // 最新製造日
            if (!StringUtil.isBlank(pInParam.getLatestManufacutureDate()))
            {
                _piAKey.updateLatestManufacutureDate(pInParam.getLatestManufacutureDate());
            }
            // 最新出庫日
            if (!StringUtil.isBlank(pInParam.getLatestRetrievalDate()))
            {
                _piAKey.updateLatestRetrievalDate(pInParam.getLatestRetrievalDate());
            }
            // 最新在庫
            if (!StringUtil.isBlank(pInParam.getLatestStock()))
            {
                _piAKey.updateLatestStock(pInParam.getLatestStock());
            }
            // 最古在庫
            if (!StringUtil.isBlank(pInParam.getOldestStock()))
            {
                _piAKey.updateOldestStock(pInParam.getOldestStock());
            }
            // 管理フラグ
            if (!StringUtil.isBlank(pInParam.getManagementFlag()))
            {
                _piAKey.updateManagementFlag(pInParam.getManagementFlag());
            }
            // 上限在庫数
            if (0 < pInParam.getUpperQty())
            {
                _piAKey.updateUpperQty(pInParam.getUpperQty());
            }
            // 下限在庫数
            if (0 < pInParam.getLowerQty())
            {
                _piAKey.updateLowerQty(pInParam.getLowerQty());
            }
            // 最終更新処理名
            _piAKey.updateLastUpdatePname(getCallerName());
        }
        else
        {
            // 検索条件が不足している場合、OperatorExceptionをスローします。
            throw new ScheduleException();
        }

        // PCT出庫予定修正
        _piHndl.modify(_piAKey);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * PCT商品マスタデータを作成します
     * 
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public void createPCTItem(String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        // 商品マスタ情報
        PCTItem piEnt = new PCTItem();
        PCTMasterInParameter pInParam = (PCTMasterInParameter)startParam;

        // 作成内容をセット(PCT商品情報)
        piEnt.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        piEnt.setConsignorCode(pInParam.getConsignorCode());
        piEnt.setConsignorName(pInParam.getConsignorName());
        piEnt.setItemCode(pInParam.getItemCode());
        piEnt.setItemName(pInParam.getItemName());
        piEnt.setJan(pInParam.getJan());
        piEnt.setItf(pInParam.getItf());
        piEnt.setBundleItf(pInParam.getBundleItf());
        piEnt.setLotEnteringQty(pInParam.getLotEnteringQty());
        piEnt.setEnteringQty(pInParam.getEnteringQty());
        piEnt.setBundleEnteringQty(pInParam.getBundleEnteringQty());
        piEnt.setUnit(pInParam.getUnit());
        piEnt.setSingleWeight(pInParam.getSingleWeight());
        piEnt.setWeightDistinctRate(pInParam.getWeightDistinctRate());
        piEnt.setMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(pInParam.getWeightDistinctRate()));
        piEnt.setLocationNo1(pInParam.getLocationNo1());
        piEnt.setEnteringQty1(pInParam.getEnteringQty1());
        piEnt.setLocationNo2(pInParam.getLocationNo2());
        piEnt.setEnteringQty2(pInParam.getEnteringQty2());
        piEnt.setLocationNo3(pInParam.getLocationNo3());
        piEnt.setEnteringQty3(pInParam.getEnteringQty3());
        piEnt.setLocationNo4(pInParam.getLocationNo4());
        piEnt.setEnteringQty4(pInParam.getEnteringQty4());
        piEnt.setInformation(pInParam.getInformation());
        piEnt.setUseByPeriod(pInParam.getUseByPeriod());
        piEnt.setInstockLimitDate(pInParam.getInstockLimitDate());
        piEnt.setShippingLimitDate(pInParam.getShippingLimitDate());
        piEnt.setLatestUseByDate(pInParam.getLatestUseByDate());
        piEnt.setLatestManufacutureDate(pInParam.getLatestManufacutureDate());
        piEnt.setLatestRetrievalDate(pInParam.getLatestRetrievalDate());
        piEnt.setLatestStock(pInParam.getLatestStock());
        piEnt.setOldestStock(pInParam.getOldestStock());
        piEnt.setManagementFlag(pInParam.getManagementFlag());
        piEnt.setUpperQty(pInParam.getUpperQty());
        piEnt.setLowerQty(pInParam.getLowerQty());
        piEnt.setRegistPname(getCallerName());
        piEnt.setLastUpdatePname(getCallerName());

        // PCT商品マスタデータ作成
        _piHndl.create(piEnt);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTItemOperator.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
