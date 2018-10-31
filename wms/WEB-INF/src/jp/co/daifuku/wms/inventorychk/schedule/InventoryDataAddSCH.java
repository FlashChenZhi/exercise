// $Id: InventoryDataAddSCH.java 4812 2009-08-10 11:05:22Z kumano $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.schedule.InventoryDataAddSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.InventSettingController;
import jp.co.daifuku.wms.base.dbhandler.InventSettingFinder;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 新規棚卸データ追加のスケジュール処理を行います。
 * 
 * @version $Revision: 4812 $, $Date: 2009-08-10 20:05:22 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class InventoryDataAddSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public InventoryDataAddSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new ItemFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * リスト作業No.パラメータを元に、入力値の存在チェックします。<BR>
     * 
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力したリスト作業No.が作業情報に存在する場合
     * @throws CommonException スケジュールエラーを通知します。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        InventoryInParameter param = new InventoryInParameter(getWmsUserInfo());

        param.setSettingUnitKey(p.getString(LIST_WORK_NO));
        param.setConsignorCode(p.getString(CONSIGNOR_CODE));
        param.setAreaNo(p.getString(AREA_NO));
        param.setLocationNo(p.getString(LOCATION_NO));
        param.setWorkInfoCheckFlag(p.getBoolean(WORKINFO_CHECK));
        try
        {

            // 棚卸作業情報を検索
            InventWorkInfoSearchKey serchkey = new InventWorkInfoSearchKey();
            InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());
            // 棚卸設定情報取得
            InventSettingFinder settingFinder = new InventSettingFinder(getConnection());
            InventSettingSearchKey settingSerchkey = new InventSettingSearchKey();

            //棚卸範囲に入っているか確認
            InventSettingController inventScon = null;

            inventScon = new InventSettingController(getConnection(), getClass());
            String schuleNo = inventScon.rangeCheck(param.getConsignorCode(), param.getAreaNo(), param.getLocationNo());

            if (!StringUtil.isBlank(param.getSettingUnitKey()))
            {

                serchkey.clear();
                serchkey.setSettingUnitKey(param.getSettingUnitKey());

                // 検索処理実行
                if (handler.count(serchkey) == 0)
                {
                    // 入力値、リスト作業No.、エリアNo.、棚No.の作業情報整合性チェック
                    if (param.isWorkInfoCheckFlag() == false)
                    {
                        // 入力したリスト作業No.は作業情報に存在しません。
                        setMessage("6023162");
                        return false;
                    }
                    else if (param.isWorkInfoCheckFlag() == true)
                    {
                        InventSetting[] inventSetting = null;
                        // 取得したスケジュールNo.より、棚卸設定情報の開始棚、終了棚を取得
                        if (!StringUtil.isBlank(schuleNo))
                        {
                            settingSerchkey.setScheduleNo(schuleNo);
                            settingFinder.open(true);

                            settingFinder.search(settingSerchkey);
                            inventSetting = (InventSetting[])settingFinder.getEntities(1);
                        }
                        if (inventSetting != null)
                        {
                            serchkey.clear();
                            serchkey.setAreaNo(inventSetting[0].getAreaNo());
                            serchkey.setLocationNo(inventSetting[0].getFromLocationNo(), ">=");
                            serchkey.setLocationNo(inventSetting[0].getToLocationNo(), "<=");
                        }
                        if (handler.count(serchkey) > 0)
                        {
                            // 6023221=指定したエリアNo.と棚No.はすでに違うリスト作業No.で開始されています。
                            setMessage("6023221");
                            return false;
                        }

                    }
                }
            }


        }
        catch (OperatorException ex)
        {
            if (OperatorException.ERR_INVENT_LOCATION_OUTSIDE_RANGE.equals(ex.getErrorCode()))
            {
                //指定された棚は棚卸中でありません。
                setMessage("6023132");
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw ex;
        }
        return true;
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        ItemSearchKey searchKey = new ItemSearchKey();

        searchKey.setItemCode(p.getString(ITEM_CODE));
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Item[] entities = (Item[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Item ent : entities)
        {
            Params param = new Params();


            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());

            // 商品名称
            param.set(ITEM_NAME, ent.getItemName());

            param.set(ENTERING_QTY, ent.getEnteringQty());

            result.add(param);
        }

        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
