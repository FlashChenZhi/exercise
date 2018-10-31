// $Id: AsInventoryCheckListDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.AsInventoryCheckListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 在庫確認作業リスト発行に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsInventoryCheckListDASCH
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
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public AsInventoryCheckListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        //エリアマスタ情報コントローラの作成
        AreaController area = new AreaController(getConnection(), getClass());

        // get Next entity from finder class
        WorkInfo[] workinfos = (WorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (WorkInfo workinfo : workinfos)
        {
            // 取得データのセット
        	// エリア
            p.set(AREA_NO, workinfo.getPlanAreaNo());
        	// エリア名称
            p.set(AREA_NAME, String.valueOf(workinfo.getValue(Area.AREA_NAME)));
            // 発行日
            p.set(SYS_DAY, getPrintDate());            
            // 発行時刻
            p.set(SYS_TIME, getPrintDate());
        	// ステーション
            p.set(STATION_NO, String.valueOf(workinfo.getValue(CarryInfo.DEST_STATION_NO, "")));
        	// ステーション名称
            p.set(STATION_NAME, String.valueOf(workinfo.getValue(Station.STATION_NAME, "")));
            // 作業No
            p.set(JOB_NO, String.valueOf(workinfo.getValue(CarryInfo.WORK_NO)));
            // 棚
            p.set(LOCATION_NO, area.toDispLocation(workinfo.getPlanAreaNo(), workinfo.getPlanLocationNo()));
        	// 商品コード
            p.set(ITEM_CODE, workinfo.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(workinfo.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(LOT_NO, workinfo.getPlanLotNo());

            // ケース入数
            p.set(ENTERING_QTY, workinfo.getBigDecimal(Item.ENTERING_QTY));

            int entering_qty = Integer.parseInt(String.valueOf(workinfo.getValue(Item.ENTERING_QTY)));
            //在庫ケース数(在庫数/ケース入数)
            p.set(STOCK_CASE_QTY,
                    DisplayUtil.getCaseQty(workinfo.getBigDecimal(Stock.STOCK_QTY).longValue(), entering_qty));
            //在庫ピース数(在庫数%ケース入数)
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(workinfo.getBigDecimal(Stock.STOCK_QTY).longValue(),
                    entering_qty));

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());

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
        //エリアマスタ情報コントローラの作成
        AreaController area = new AreaController(getConnection(), getClass());

        List<Params> params = new ArrayList<Params>();
        WorkInfo[] workinfos = (WorkInfo[])_finder.getEntities(start, start + cnt);

        for (WorkInfo workinfo : workinfos)
        {
            Params p = new Params();

            // 取得データのセット
        	// エリア
            p.set(AREA_NO, workinfo.getPlanAreaNo());
        	// エリア名称
            p.set(AREA_NAME, String.valueOf(workinfo.getValue(Area.AREA_NAME)));
            // 発行日
            p.set(SYS_DAY, getPrintDate());            
            // 発行時刻
            p.set(SYS_TIME, getPrintDate());
        	// ステーション
            p.set(STATION_NO, String.valueOf(workinfo.getValue(CarryInfo.DEST_STATION_NO, "")));
        	// ステーション名称
            p.set(STATION_NAME, String.valueOf(workinfo.getValue(Station.STATION_NAME, "")));
            // 作業No
            p.set(WORK_NO, String.valueOf(workinfo.getValue(CarryInfo.WORK_NO)));
            // 棚
            p.set(PLAN_LOCATION_NO, area.toDispLocation(workinfo.getPlanAreaNo(), workinfo.getPlanLocationNo()));
        	// 商品コード
            p.set(ITEM_CODE, workinfo.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(workinfo.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(PLAN_LOT_NO, workinfo.getPlanLotNo());

            // ケース入数
            p.set(ENTERING_QTY, workinfo.getBigDecimal(Item.ENTERING_QTY));

            int entering_qty = Integer.parseInt(String.valueOf(workinfo.getValue(Item.ENTERING_QTY)));
            //在庫ケース数(在庫数/ケース入数)
            p.set(STOCK_CASE_QTY,
                    DisplayUtil.getCaseQty(workinfo.getBigDecimal(Stock.STOCK_QTY).longValue(), entering_qty));
            //在庫ピース数(在庫数%ケース入数)
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(workinfo.getBigDecimal(Stock.STOCK_QTY).longValue(),
                    entering_qty));

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());

            params.add(p);
        }
        return params;
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param key 入出庫作業情報キー
     * @throws ReadWriteException データベース処理でエラーが発生した場合に通知します。
     */
    protected void setSearchKey(WorkInfoSearchKey key, Params param)
            throws ScheduleException
    {
        // 検索条件、集約条件をセットする
        // エリアNo
        String areano = param.getString(AREA_NO);
        if (!StringUtil.isBlank(areano))
        {
            key.setPlanAreaNo(areano);
        }
        // ステーション
        String stationno = param.getString(STATION_NO);
        if (!StringUtil.isBlank(stationno))
        {
            // 搬送先ステーション
            key.setKey(CarryInfo.DEST_STATION_NO, stationno);
        }

        // 検索日時
        Date[] tmp = WmsFormatter.getFromTo(param.getDate(FROM_SEARCH_DATE), param.getDate(FROM_SEARCH_TIME), param.getDate(TO_SEARCH_DATE), param.getDate(TO_SEARCH_TIME));
        Date from = tmp[0];
        Date to = tmp[1];
        
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            key.setKey(CarryInfo.REGIST_DATE, from, ">=", "", "", true);
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(to);
            curDate.add(Calendar.SECOND, +1);

            key.setKey(CarryInfo.REGIST_DATE, curDate.getTime(), "<", "", "", true);
        }
        
        // 作業区分(在庫確認)
        key.setJobType(WorkInfo.JOB_TYPE_ASRS_INVENTORYCHECK);
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
        // 在庫ID
        key.setJoin(WorkInfo.STOCK_ID, Stock.STOCK_ID);
    }

    /**
     * ソート順のセットを行います。<BR>
     * @param key 入出庫作業情報キー
     */
    protected void setOrderKey(WorkInfoSearchKey key)
    {
        // 搬送先ステーションNo.
        key.setOrder(CarryInfo.DEST_STATION_NO, true);
        // 作業No.
        key.setOrder(CarryInfo.WORK_NO, true);
        // 予定棚
        key.setPlanLocationNoOrder(true);
        // 商品コード
        key.setItemCodeOrder(true);
        // 予定ロットNo.
        key.setPlanLotNoOrder(true);
    }

    /**
     * 取得項目のセットを行います。<BR>
     * @param key 入出庫作業情報キー
     * @throws ReadWriteException データベース処理でエラーが発生した場合にthrowします。
     */
    protected void setCollectKey(WorkInfoSearchKey key)
            throws ReadWriteException
    {
        // WorkInfoの全てのカラム
        key.setCollect(new FieldName(WorkInfo.STORE_NAME, FieldName.ALL_FIELDS));
        // 商品名称
        key.setCollect(Item.ITEM_NAME);
        // ケース入数
        key.setCollect(Item.ENTERING_QTY);
        // エリア名称
        key.setCollect(Area.AREA_NAME);
        // ステーションNo.
        key.setCollect(CarryInfo.DEST_STATION_NO);
        // ステーション名称
        key.setCollect(Station.STATION_NAME);
        // 作業No.
        key.setCollect(CarryInfo.WORK_NO);
        // 在庫数
        key.setCollect(Stock.STOCK_QTY);
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
        // 入出庫作業情報検索キーに検索キーをセットする
        setSearchKey(key, param);

        // 出力用データの場合のみ検索
        if (isSet)
        {
	        // 入出庫作業情報ソート順をセット
	        setOrderKey(key);
	
	        // 情報取得項目をセットする
	        setCollectKey(key);
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
