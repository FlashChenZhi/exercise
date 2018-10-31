// $Id: LstInventoryJobNoDASCH.java 4807 2009-08-10 06:30:29Z shibamoto $
package jp.co.daifuku.wms.inventorychk.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.dasch.LstInventoryJobNoDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * リスト作業No.検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4807 $, $Date: 2009-08-10 15:30:29 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstInventoryJobNoDASCH
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
    public LstInventoryJobNoDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new InventWorkInfoFinder(getConnection());
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
        Params p = new Params();

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
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

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
        List<Params> params = new ArrayList<Params>();
        InventWorkInfo[] ents = (InventWorkInfo[])_finder.getEntities(start, start + cnt);

        AreaController areacon = null;
        try
        {
            areacon = new AreaController(getConnection(), this.getClass());
        }
        catch (CommonException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        for (InventWorkInfo ent : ents)
        {
            Params p = new Params();
            p.set(COLUMN_1, String.valueOf(params.size() + 1));
            p.set(LIST_WORK_NO, ent.getSettingUnitKey());
            p.set(AREA_NO, ent.getAreaNo());

            try
            {
                // 棚No(from)
                String location = String.valueOf(ent.getValue(InventSetting.FROM_LOCATION_NO));
                location = areacon.toDispLocation(p.getString(AREA_NO), location);
                p.set(START_LOCATION, location);
                // 棚No(to)
                location = String.valueOf(ent.getValue(InventSetting.TO_LOCATION_NO));
                location = areacon.toDispLocation(p.getString(AREA_NO), location);
                p.set(END_LOCATION, location);
            }
            catch (CommonException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }


            params.add(p);
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
     * @return SearchKey 検索条件キー
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
    {
        InventWorkInfoSearchKey skey = new InventWorkInfoSearchKey();

        //統合条件の指定
        skey.setJoin(InventWorkInfo.CONSIGNOR_CODE, InventSetting.CONSIGNOR_CODE);
        skey.setJoin(InventWorkInfo.SCHEDULE_NO, InventSetting.SCHEDULE_NO);

        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            // 荷主コード
            skey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        if (!StringUtil.isBlank(param.getString(LIST_WORK_NO)))
        {
            // 設定単位キー
            if (DBFormat.isPatternMatching(param.getString(LIST_WORK_NO)))
            {
                // ワイルドカード検索
                skey.setSettingUnitKey(param.getString(LIST_WORK_NO), "like");
            }
            else
            {
                skey.setSettingUnitKey(param.getString(LIST_WORK_NO), ">=");
            }
        }
        if (!StringUtil.isBlank(param.getString(STATUSFLAG)))
        {
            skey.setStatusFlag(param.getString(STATUSFLAG));
        }
        else
        {
            skey.setStatusFlag(InventWorkInfo.STATUS_FLAG_DELETE, "!=");
        }


        // 昇順・集約設定
        skey.setSettingUnitKeyOrder(true);

        // 設定単位キーISNOTNULL
        skey.setSettingUnitKey("", "!=");

        // 取得項目
        skey.setSettingUnitKeyGroup();
        skey.setAreaNoGroup();
        skey.setGroup(InventSetting.FROM_LOCATION_NO);
        skey.setGroup(InventSetting.TO_LOCATION_NO);

        skey.setSettingUnitKeyCollect();
        skey.setAreaNoCollect();
        skey.setCollect(InventSetting.FROM_LOCATION_NO);
        skey.setCollect(InventSetting.TO_LOCATION_NO);
        if (isSet)
        {
            // OrderByや、collect項目を記載する
        }

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
