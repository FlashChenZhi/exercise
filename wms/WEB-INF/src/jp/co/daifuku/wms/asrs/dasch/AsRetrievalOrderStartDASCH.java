// $Id: AsRetrievalOrderStartDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import static jp.co.daifuku.wms.asrs.dasch.AsRetrievalOrderStartDASCHParams.*;

/**
 * AS/RS 出庫開始に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsRetrievalOrderStartDASCH
        extends AbstractWmsDASCH
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

    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */

    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public AsRetrievalOrderStartDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // TODO : Implement for export
        // Create Finder Object
        _finder = new WorkInfoFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        WorkInfo[] ents = (WorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (WorkInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            // エリア
            p.set(AREA_NO, ent.getPlanAreaNo());
            // エリア名称
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            // ステーション
            p.set(STATION_NO, ent.getValue(CarryInfo.DEST_STATION_NO, ""));
            // ステーション名称
            p.set(STATION_NAME, ent.getValue(Station.STATION_NAME, ""));
            // ﾊﾞｯﾁNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // ｵｰﾀﾞｰNo.
            p.set(ORDER_NO, ent.getOrderNo());
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME, ""));
            // 作業No.
            p.set(JOB_NO, ent.getValue(CarryInfo.WORK_NO, ""));
            // 出庫棚
            p.set(RETRIEVAL_LOCATION_NO, ent.getPlanLocationNo());
            // 商品ｺｰﾄﾞ
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ﾛｯﾄNo.
            p.set(LOT_NO, ent.getPlanLotNo());
            // 出庫予定日
            p.set(RETRIEVAL_PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // ｹｰｽ入数 取得
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY));
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            // 出庫ｹｰｽ数
            long planQty = ent.getBigDecimal(WorkInfo.PLAN_QTY).longValue();
            p.set(RETRIEVAL_CASE_QTY, DisplayUtil.getCaseQty(planQty, enteringQty));
            // 出庫ﾋﾟｰｽ数
            p.set(RETRIEVAL_PIECE_QTY, DisplayUtil.getPieceQty(planQty, enteringQty));
            // JANｺｰﾄﾞ
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            // ｹｰｽITF
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));

            break;
        }
        // return Pram objstc
        return p;
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        // TODO : Implement for export or listcell
        //throw new ScheduleException("This method is not implemented.");
        WorkInfoHandler handler = new WorkInfoHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        throw new ScheduleException("This method is not implemented.");
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return 検索条件
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        // 検索条件
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UKEYS)))
        {
            key.setSettingUnitKey(param.getString(SETTING_UKEYS));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_DATE)))
        {
            key.setKey(CarryInfo.REGIST_DATE, param.getString(SEARCH_DATE), ">=", "", "", true);
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_TO_DATE)))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(param.getDate(SEARCH_TO_DATE));
            curDate.add(Calendar.SECOND, +1);

            key.setKey(CarryInfo.REGIST_DATE, curDate.getTime(), "<", "", "", true);
        }
        // 作業区分
        key.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
        // 状態フラグ
        key.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
        // ハードウェア区分
        key.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);
        // オーダーNo.
        key.setOrderNo("", "!=");
        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            key.setPlanAreaNo(param.getString(AREA_NO));
        }
        // ステーションNo.
        if (!StringUtil.isBlank(param.getString(STATION_NO)))
        {
            // 搬送先ステーション
            key.setKey(CarryInfo.DEST_STATION_NO, param.getString(STATION_NO));
        }

        // 結合条件
        key.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
        key.setJoin(CarryInfo.DEST_STATION_NO, Station.STATION_NO);
        key.setJoin(WorkInfo.PLAN_AREA_NO, Area.AREA_NO);
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        key.setJoin(Customer.CUSTOMER_CODE, "(+)", WorkInfo.CUSTOMER_CODE, "");
        key.setJoin(Customer.CONSIGNOR_CODE, "(+)", WorkInfo.CONSIGNOR_CODE, "");

        // 集約条件
        key.setCollectJobNoGroup();
        key.setGroup(CarryInfo.WORK_NO);
        key.setPlanAreaNoGroup();
        key.setGroup(Area.AREA_NAME);
        key.setGroup(CarryInfo.DEST_STATION_NO);
        key.setGroup(Station.STATION_NAME);
        key.setPlanLocationNoGroup();
        key.setItemCodeGroup();
        key.setGroup(Item.ITEM_NAME);
        key.setPlanDayGroup();
        key.setGroup(Item.ENTERING_QTY);
        key.setGroup(Item.JAN);
        key.setGroup(Item.ITF);
        key.setGroup(CarryInfo.WORK_NO);
        key.setCustomerCodeGroup();
        key.setGroup(Customer.CUSTOMER_NAME);
        key.setBatchNoGroup();
        key.setOrderNoGroup();
        key.setPlanLotNoGroup();
        key.setStockIdGroup();
        if (isSet)
        {
            // ステーションNo.
            key.setOrder(CarryInfo.DEST_STATION_NO, true);
            // ｵｰﾀﾞｰNo.
            key.setOrderNoOrder(true);
            // 作業No.
            key.setOrder(CarryInfo.WORK_NO, true);
            // 商品ｺｰﾄﾞ
            key.setItemCodeOrder(true);
            // ロットNo.
            key.setPlanLotNoOrder(true);

            // 取得項目
            // エリア
            key.setPlanAreaNoCollect();
            // エリア名称
            key.setCollect(Area.AREA_NAME);
            // ステーションNo.
            key.setCollect(CarryInfo.DEST_STATION_NO);
            // ステーション名称
            key.setCollect(Station.STATION_NAME);
            // 出庫棚
            key.setPlanLocationNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setCollect(Item.ITEM_NAME);
            // ロットNo.
            key.setPlanLotNoCollect();
            // 出庫予定日
            key.setPlanDayCollect();
            // ケース入数
            key.setCollect(Item.ENTERING_QTY);
            // 予定数
            key.setPlanQtyCollect("SUM");
            // JANコード
            key.setCollect(Item.JAN);
            // ケースITF
            key.setCollect(Item.ITF);
            // 作業No.
            key.setCollect(CarryInfo.WORK_NO);
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCollect(Customer.CUSTOMER_NAME);
            // バッチNo.
            key.setBatchNoCollect();
            // オーダーNo.
            key.setOrderNoCollect();
        }

        return key;
    }

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
