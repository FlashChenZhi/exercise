// $Id: ReplenishListCompleteSCH.java 7585 2010-03-15 11:21:44Z ota $
package jp.co.daifuku.wms.replenish.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.replenish.operator.ReplenishOperator;
import static jp.co.daifuku.wms.replenish.schedule.ReplenishListCompleteSCHParams.*;

/**
 * 補充リスト作業完了設定のスケジュール処理を行います。
 *
 * @version $Revision: 7585 $, $Date: 2010-03-15 20:21:44 +0900 (月, 15 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class ReplenishListCompleteSCH
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
    public ReplenishListCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
            finder = new MoveWorkInfoFinder(getConnection());
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
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // オペレータパラメータ生成
        ReplenishInParameter[] inParams = new ReplenishInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new ReplenishInParameter(getWmsUserInfo());

            inParams[i].setSettingUnitKey(ps[i].getString(L_SETTING_UKEY));
            inParams[i].setJobNo(ps[i].getString(JOB_NO));
            inParams[i].setReplenishQty(((Integer)ps[i].get(REPLENISH_QTY)));
            inParams[i].setJobType(ps[i].getString(SELECT_JOB_TYPE));
            inParams[i].setRowNo(ps[i].getRowIndex());

        }

        // 補充処理
        try
        {
            ReplenishOperator operator = new ReplenishOperator(getConnection(), this.getClass());

            operator.webComplete(inParams);

            setMessage(WmsMessageFormatter.format(6001014));

            return true;
        }
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                if (e.getErrorLineNo() > 0)
                {
                    // No. {0} 他端末で処理されたため、処理を中断しました。
                    setMessage(WmsMessageFormatter.format(6023015, e.getErrorLineNo()));
                    setErrorRowIndex(e.getErrorLineNo());
                }
                else
                {
                    // 他端末で処理されたため、処理を中断しました。
                    setMessage(WmsMessageFormatter.format(6023115, e.getErrorLineNo()));
                }
                return false;
            }
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023248=No.{0} 補充数が補充元在庫の補充可能数を超えるため、補充できません。
                setMessage(WmsMessageFormatter.format(6023248, e.getErrorLineNo()));
                setErrorRowIndex(e.getErrorLineNo());
                return false;
            }
            else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                List list = e.getDetailList();

                // 6023249=No.{0} 補充先の在庫数が在庫上限数を超えるため、補充できません。在庫数={1} 補充数={2}
                setMessage(WmsMessageFormatter.format(6023249, e.getErrorLineNo(),
                        WmsFormatter.getNumFormat(Long.valueOf((Integer)list.get(0))),
                        WmsFormatter.getNumFormat(Long.valueOf((Integer)list.get(1)))));
                setErrorRowIndex(e.getErrorLineNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }

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
        MoveWorkInfoSearchKey searchKey = new MoveWorkInfoSearchKey();

        // select
        // 移動作業情報.作業No.
        searchKey.setCollect(MoveWorkInfo.JOB_NO);
        // 移動作業情報.補充元エリア
        searchKey.setCollect(MoveWorkInfo.RETRIEVAL_AREA_NO);
        // 移動作業情報.補充棚
        searchKey.setCollect(MoveWorkInfo.RETRIEVAL_LOCATION_NO);
        // 移動作業情報.補充先エリア
        searchKey.setCollect(MoveWorkInfo.STORAGE_AREA_NO);
        // 移動作業情報.補充棚
        searchKey.setCollect(MoveWorkInfo.STORAGE_LOCATION_NO);
        // 移動作業情報.予定数
        searchKey.setCollect(MoveWorkInfo.PLAN_QTY);
        // 移動作業情報.ロットNo.
        searchKey.setCollect(MoveWorkInfo.LOT_NO);
        // 移動作業情報.商品コード
        searchKey.setCollect(MoveWorkInfo.ITEM_CODE);
        // 商品マスタ情報.商品名
        searchKey.setCollect(Item.ITEM_NAME);
        // 商品マスタ情報.ケース入数
        searchKey.setCollect(Item.ENTERING_QTY);

        // where
        // 作業区分
        searchKey.setJobType(p.getString(SELECT_JOB_TYPE));
        // 作業単位キー
        searchKey.setSettingUnitKey(p.getString(SETTING_UKEY));
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

        // join
        searchKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING, "=", "((", "", true);
        searchKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_LIST, "=", "", ")", false);
        searchKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING, "=", "(", "", true);
        searchKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_ASRS, "=", "", "))", true);

        // order by
        // 移動元エリア
        searchKey.setRetrievalAreaNoOrder(true);
        // 移動元棚
        searchKey.setRetrievalLocationNoOrder(true);
        // 商品コード
        searchKey.setItemCodeOrder(true);
        // ロットNo.
        searchKey.setLotNoOrder(true);
        // 補充先エリア
        searchKey.setStorageAreaNoOrder(true);
        // 補充先棚
        searchKey.setStorageLocationNoOrder(true);

        //商品マスタ情報取得
        searchKey.setJoin(MoveWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        searchKey.setJoin(MoveWorkInfo.ITEM_CODE, Item.ITEM_CODE);

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
        MoveWorkInfo[] entities = (MoveWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        AreaController areCont = new AreaController(getConnection(), this.getClass());
        
        for (MoveWorkInfo ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            //作業No.
            param.set(JOB_NO, ent.getJobNo());
            // 補充元エリアNo.
            param.set(FROM_AREA, ent.getRetrievalAreaNo());
            // 補充元棚No.
            param.set(FROM_LOCATION, ent.getRetrievalLocationNo());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, (String)ent.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            param.set(LOT_NO, ent.getLotNo());
            // ケース入数
            param.set(CASE_PACK, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 予定ケース数
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 予定ピース数
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 補充先エリアNo.
            param.set(TO_AREA, ent.getStorageAreaNo());
            String loc = null;
            try
            {
                loc = WmsFormatter.toDispLocation(ent.getStorageLocationNo(), areCont.getLocationStyle(ent.getStorageAreaNo()));
            }
            catch (ScheduleException e)
            {
                // 棚フォーマットでエラーが発生した場合
                throw new ReadWriteException();
            }
            // 補充先棚No.
            param.set(TO_LOCATION, loc);

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
