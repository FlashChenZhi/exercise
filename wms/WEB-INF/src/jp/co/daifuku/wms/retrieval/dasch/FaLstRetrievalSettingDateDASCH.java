package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.foundation.da.AbstractDASCH;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import static jp.co.daifuku.wms.retrieval.dasch.FaLstRetrievalSettingDateDASCHParams.*;

/**
 * 出庫開始日時検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 6185 $, $Date: 2009-11-24 17:49:15 +0900 (火, 24 11 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaLstRetrievalSettingDateDASCH
        extends AbstractDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * DB Finder定義
     */
    private AbstractDBFinder _finder = null;

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
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public FaLstRetrievalSettingDateDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // create Finder object
        _finder = new ShortageInfoFinder(getConnection());

        // データ件数初期化
        _finder.open(isForwardOnly());

        // 検索条件作成及び対象データ取得
        _finder.search(createSearchKey(p));

        return;
    }

    /**
     * 次のデータが存在するかどうかを判定します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        // TODO : Implement for export
        throw new ScheduleException("This method is not implemented.");
    }

    /**
     * データを1件返します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        // TODO : Implement for export
        throw new ScheduleException("This method is not implemented.");
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
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    @Override
    protected int actualCount(Params p)
            throws CommonException
    {
        ShortageInfoHandler handler = new ShortageInfoHandler(getConnection());
        int total = handler.count(createSearchKey(p));

        return total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     */
    @Override
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 復帰パラメータ領域取得
        List<Params> params = new ArrayList<Params>();

        // 対象情報取得
        ShortageInfo[] restData = (ShortageInfo[])_finder.getEntities(start, start + cnt);

        int _point = 1;
        // 取得情報をパラメータに編集
        for (ShortageInfo rData : restData)
        {
            Params p = new Params();

            // No.
            p.set(COLUMN_1, start + _point);
            // 出庫開始日時
            p.set(START_DATETIME, rData.getValue(ShortageInfo.RETRIEVAL_START_DATE));
            // 欠品区分
            p.set(TYPE, DisplayResource.getShortageFlag(rData.getValue(ShortageInfo.SHORTAGE_FLAG).toString()));

            params.add(p);
            _point++;
        }

        return params;
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

    /**
     * 検索条件の編集を行います。
     * @param p 検索条件パラメータ
     * @return SearchKey
     */
    private SearchKey createSearchKey(Params p)
    {
        ShortageInfoSearchKey key = new ShortageInfoSearchKey();

        /* 取得項目と集約条件の指定 */
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }

        // 出庫開始日時
        if (!StringUtil.isBlank(p.getDate(START_DATE)))
        {
            Date _startdatetime = null;
            try
            {
                _startdatetime = WmsFormatter.getFromSearchDate(p.getDate(START_DATE), p.getDate(START_TIME));
            }
            catch (Exception ex)
            {
            }
            key.setRetrievalStartDate(_startdatetime, ">=");
        }

        // 作業区分 (出庫 or 予定外出庫)
        key.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL, "=", "(", "", false);
        key.setJobType(SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL, "=", "", ")", true);

        /* 集約条件 */
        // 出庫開始日時
        key.setRetrievalStartDateGroup();
        key.setShortageFlagGroup();

        /* ソート順の指定 */
        // 出庫開始日時の昇順
        //key.setRetrievalStartDateOrder(true);
        // 20091218 EG要望により降順に変更
        key.setRetrievalStartDateOrder(false);

        /* 取得項目 */
        // 出庫開始日時
        key.setRetrievalStartDateCollect();
        key.setShortageFlagCollect();

        return key;
    }

}
//end of class
