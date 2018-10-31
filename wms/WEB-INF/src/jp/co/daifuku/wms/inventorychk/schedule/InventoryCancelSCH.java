// $Id: InventoryCancelSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.schedule.InventoryCancelSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InventSettingFinder;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.inventorychk.operator.InventoryOperator;

/**
 * 棚卸キャンセルのスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class InventoryCancelSCH
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
    public InventoryCancelSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new InventSettingFinder(getConnection());
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

        //棚卸キャンセル処理
        InventoryOperator ope = new InventoryOperator(this.getConnection(), this.getClass());
        try
        {

            InventoryInParameter param = null;

            List<InventoryInParameter> listParam = new ArrayList<InventoryInParameter>();

            for (ScheduleParams startParam : ps)
            {
                param = new InventoryInParameter(getWmsUserInfo());
                param.setScheduleNo(startParam.getString(SCHEDULE));
                param.setLastUpdateDate(startParam.getDate(LASTUPDATE));
                param.setRowNo(startParam.getRowIndex());
                listParam.add(param);
            }

            InventoryInParameter[] inParams = new InventoryInParameter[listParam.size()];
            listParam.toArray(inParams);

            //キャンセル処理
            ope.deleteInventoryWorkInfo(inParams);

            //設定しました。
            setMessage("6001006");

            return true;
        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (NotFoundException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023015");
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                setErrorRowIndex(e.getErrorLineNo());
                // 6023015=No.{0}他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, e.getErrorLineNo()));
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
        InventSettingSearchKey searchKey = new InventSettingSearchKey();

        //検索条件
        searchKey.setStatusFlag(InventSetting.STATUS_FLAG_DELETE, "!=");
        searchKey.setStatusFlag(InventSetting.STATUS_FLAG_COMPLETION, "!=");
        searchKey.setJoin(InventSetting.AREA_NO, Area.AREA_NO);

        //取得項目
        searchKey.setScheduleNoCollect();
        searchKey.setRegistDateCollect();
        searchKey.setLastUpdateDateCollect();
        searchKey.setAreaNoCollect();
        searchKey.setCollect(Area.AREA_NAME);
        searchKey.setFromLocationNoCollect();
        searchKey.setToLocationNoCollect();

        //表示順セット
        searchKey.setRegistDateOrder(true);

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
        InventSetting[] entities = (InventSetting[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (InventSetting ent : entities)
        {
            Params param = new Params();

            //スケジュールNo.
            param.set(SCHEDULE, ent.getScheduleNo());
            //棚卸開始日時
            param.set(REGIST_DATE, ent.getRegistDate());
            //エリア
            param.set(AREA_NO, ent.getAreaNo());
            //エリア名称
            param.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME)));
            //開始棚
            param.set(FROM_LOCATION_NO, ent.getFromLocationNo());
            //終了棚
            param.set(TO_LOCATION_NO, ent.getToLocationNo());
            //最終更新日時
            param.set(LASTUPDATE, ent.getLastUpdateDate());

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
