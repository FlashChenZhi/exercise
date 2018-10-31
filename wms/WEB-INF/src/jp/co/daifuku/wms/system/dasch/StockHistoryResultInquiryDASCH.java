// $Id: StockHistoryResultInquiryDASCH.java 4690 2009-07-16 00:24:27Z okayama $
package jp.co.daifuku.wms.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.dasch.StockHistoryResultInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;

/**
 * 入出庫実績照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4690 $, $Date: 2009-07-16 09:24:27 +0900 (木, 16 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StockHistoryResultInquiryDASCH
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
    public StockHistoryResultInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // Implement for export
        // Create Finder Object
        _finder = new StockHistoryFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
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
        StockHistory[] ents = (StockHistory[])_finder.getEntities(1);
        Params param = new Params();
        // conver Entity to Param object
        for (StockHistory ent : ents)
        {
            param.set(DFK_DS_NO, getDsNumber());
            param.set(DFK_USER_ID, getUserId());
            param.set(DFK_USER_NAME, getUserName());
            param.set(SYS_DAY, getPrintDate());
            param.set(SYS_TIME, getPrintDate());

            // 発生日
            param.set(REGIST_DATE, ent.getRegistDate());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(StockHistory.ITEM_NAME, "")));
            // ロットNo.
            param.set(LOT_NO, String.valueOf(ent.getValue(StockHistory.LOT_NO, "")));
            // 増減区分
            param.set(INC_DEC_TYPE, DisplayResource.getIncDecType(ent.getIncDecType()));
            // 作業区分
            param.set(JOB_TYPE, DisplayResource.getJobType(ent.getJobType()));
            // エリア
            param.set(AREA_NO, ent.getAreaNo());
            // 棚
            param.set(LOCATION_NO, ent.getLocationNo());
            // 作業数
            param.set(INC_DEC_QTY, ent.getIncDecQty());
            // 入庫日
            param.set(STORAGE_DATE, ent.getStorageDate());
            // ユーザ名称
            param.set(USER_NAME, ent.getUserName());


            break;
        }
        // return Pram objcts
        return param;
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
        StockHistoryHandler handler = new StockHistoryHandler(getConnection());

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
        List<Params> params = new ArrayList<Params>();
        StockHistory[] ents = (StockHistory[])_finder.getEntities(start, start + cnt);

        for (StockHistory ent : ents)
        {
            Params param = new Params();
            // 発生日
            param.set(REGIST_DATE, ent.getRegistDate());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(StockHistory.ITEM_NAME, "")));
            // ロットNo.
            param.set(LOT_NO, String.valueOf(ent.getValue(StockHistory.LOT_NO, "")));
            // 増減区分
            param.set(INC_DEC_TYPE, DisplayResource.getIncDecType(ent.getIncDecType()));
            // 作業区分
            param.set(JOB_TYPE, DisplayResource.getJobType(ent.getJobType()));
            // エリア
            param.set(AREA_NO, ent.getAreaNo());
            // 棚
            param.set(LOCATION_NO, ent.getLocationNo());
            // 作業数
            param.set(INC_DEC_QTY, ent.getIncDecQty());
            // 入庫日
            param.set(STORAGE_DATE, ent.getStorageDate());
            // ユーザ名称
            param.set(USER_NAME, ent.getUserName());

            param.set(DFK_DS_NO, getDsNumber());
            param.set(DFK_USER_ID, getUserId());
            param.set(DFK_USER_NAME, getUserName());
            param.set(SYS_DAY, getPrintDate());
            param.set(SYS_TIME, getPrintDate());

            params.add(param);
        }
        return params;
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
            throws ScheduleException
    {
        StockHistorySearchKey skey = new StockHistorySearchKey();

        // 検索条件、集約条件をセットする
        // 開始検索日時、終了検索日時
        if (!(StringUtil.isBlank(param.getDate(SEARCH_DAY_FROM)) && StringUtil.isBlank(param.getDate(SEARCH_DAY_TO))))
        {
            Date[] tmp =
                    WmsFormatter.getFromTo(param.getDate(SEARCH_DAY_FROM), param.getDate(SEARCH_TIME_FROM),
                            param.getDate(SEARCH_DAY_TO), param.getDate(SEARCH_TIME_TO));
            skey.setRegistDate(tmp[0], ">=");
            skey.setRegistDate(tmp[1], "<");
        }

        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            skey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            skey.setItemCode(param.getString(ITEM_CODE));
        }
        // エリア
        if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
        {
            skey.setAreaNo(param.getString(AREA_NO));
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getString(LOT_NO)))
        {
            skey.setLotNo(param.getString(LOT_NO));
        }

        // 作業区分
        if (!SystemInParameter.SEARCH_ALL.equals(param.getString(JOB_TYPE)))
        {
            skey.setJobType(param.getString(JOB_TYPE));
        }

        // ユーザ名称
        if (StringUtil.isBlank(param.getString(USER_NAME)))
        {
            skey.setUserId(EmConstants.DAIFUKU_SV_USER, "!=");
            skey.setUserId(EmConstants.USERMAINTENANCE_USER, "!=");
        }
        else
        {
            skey.setUserName(param.getString(USER_NAME));
        }


        //ソート順
        // 登録日時
        skey.setRegistDateOrder(true);
        // 商品コード
        skey.setItemCodeOrder(true);
        // ロットNo.
        skey.setLotNoOrder(true);
        // 増減区分
        skey.setIncDecTypeOrder(true);
        // 作業区分
        skey.setJobTypeOrder(true);
        // エリア
        skey.setAreaNoOrder(true);
        // 棚
        skey.setLocationNoOrder(true);


        return skey;
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
