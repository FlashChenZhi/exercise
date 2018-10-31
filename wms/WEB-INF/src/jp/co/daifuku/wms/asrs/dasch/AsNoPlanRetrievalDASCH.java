// $Id: AsNoPlanRetrievalDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import static jp.co.daifuku.wms.asrs.dasch.AsNoPlanRetrievalDASCHParams.*;

/**
 * AS/RS 予定外出庫設定に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsNoPlanRetrievalDASCH
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
    public AsNoPlanRetrievalDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            p.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME, "")));
            // ステーション
            p.set(STATION_NO, String.valueOf(ent.getValue(CarryInfo.DEST_STATION_NO, "")));
            // ステーション名称
            p.set(STATION_NAME, String.valueOf(ent.getValue(Station.STATION_NAME, "")));
            // 作業No.
            p.set(WORK_NO, String.valueOf(ent.getValue(CarryInfo.WORK_NO, "")));
            // 出庫棚
            p.set(LOCATION_NO, ent.getPlanLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(LOT_NO, ent.getPlanLotNo());
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 出庫ケース数
            p.set(CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 出庫ピース数
            p.set(PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            // JANコード
            p.set(JAN, ent.getValue(Item.JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(Item.ITF, ""));

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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        throw new RuntimeException("This method is not implemented.");
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by

        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UKEY)))
        {
            key.setSettingUnitKey(param.getString(SETTING_UKEY));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getDate(FROM_DATE)))
        {
            key.setKey(CarryInfo.REGIST_DATE, param.getDate(FROM_DATE), ">=", "", "", true);
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getDate(TO_DATE)))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(param.getDate(TO_DATE));
            curDate.add(Calendar.SECOND, +1);

            key.setKey(CarryInfo.REGIST_DATE, curDate.getTime(), "<", "", "", true);

        }
        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            key.setPlanAreaNo(param.getString(AREA_NO));
        }
        // ステーション
        if (!StringUtil.isBlank(param.getString(STATION_NO)))
        {
            // 搬送先ステーション
            key.setKey(CarryInfo.DEST_STATION_NO, param.getString(STATION_NO));
        }
        // 作業区分(予定外出庫)
        key.setJobType(WorkInfo.JOB_TYPE_NOPLAN_RETRIEVAL);
        // ハードウェア区分(ASRS)
        key.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);
        // 状態フラグ
        key.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
        // 荷主コード
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        // エリア
        key.setJoin(WorkInfo.PLAN_AREA_NO, Area.AREA_NO);
        // システム接続キー
        key.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
        // ステーションNo.
        key.setJoin(CarryInfo.DEST_STATION_NO, Station.STATION_NO);

        if (isSet)
        {
            // 集約条件
            // 予定エリアNo.
            key.setPlanAreaNoGroup();
            // 予定棚No.
            key.setPlanLocationNoGroup();
            // 商品コード
            key.setItemCodeGroup();
            // 予定ロットNo.
            key.setPlanLotNoGroup();
            // オーダーNo.
            key.setOrderNoGroup();
            // 商品名称
            key.setGroup(Item.ITEM_NAME);
            // ケース入数
            key.setGroup(Item.ENTERING_QTY);
            // JANコード
            key.setGroup(Item.JAN);
            // ケースITF
            key.setGroup(Item.ITF);
            // エリア名称
            key.setGroup(Area.AREA_NAME);
            // ステーションNo.
            key.setGroup(CarryInfo.DEST_STATION_NO);
            // ステーション名称
            key.setGroup(Station.STATION_NAME);
            // 作業No.
            key.setGroup(CarryInfo.WORK_NO);

            // 取得項目
            // 予定エリアNo.
            key.setPlanAreaNoCollect();
            // 予定棚No.
            key.setPlanLocationNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 予定ロットNo.
            key.setPlanLotNoCollect();
            // 予定数
            key.setPlanQtyCollect("SUM");
            // オーダーNo.
            key.setOrderNoCollect();
            // 商品名称
            key.setCollect(Item.ITEM_NAME);
            // ケース入数
            key.setCollect(Item.ENTERING_QTY);
            // JANコード
            key.setCollect(Item.JAN);
            // ケースITF
            key.setCollect(Item.ITF);
            // エリア名称
            key.setCollect(Area.AREA_NAME);
            // ステーションNo.
            key.setCollect(CarryInfo.DEST_STATION_NO);
            // ステーション名称
            key.setCollect(Station.STATION_NAME);
            // 作業No.
            key.setCollect(CarryInfo.WORK_NO);
            
            
            // パラメータで受け取った入出庫実績情報検索キーにソート順を以下にセットする
            key.setOrder(CarryInfo.DEST_STATION_NO, true); // ステーション
            key.setOrder(CarryInfo.WORK_NO, true); // 作業No.
            key.setItemCodeOrder(true); // 商品コード
            key.setPlanLotNoOrder(true); // 予定ロットNo.


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
