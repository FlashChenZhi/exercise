package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.stock.dasch.LstEmpLocationDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.dbhandler.LocateFinder;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.stock.display.noplanstorage.NoPlanStorage;

/**
 * 空棚候補検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:43:43 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstEmpLocationDASCH
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
    public LstEmpLocationDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new LocateFinder(getConnection());
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
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        Locate[] ents = (Locate[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (Locate ent : ents)
        {
            // エリア
            p.set(AREA_NO, ent.getAreaNo());
            // 棚
            p.set(LOCATION_NO, ent.getLocationNo());
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
        LocateHandler handler = new LocateHandler(getConnection());

        // find count

        SearchKey key = createSearchKey(p, false);

        if (key == null)
        {
            return 0;
        }
        _total = handler.count(key);

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
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        Locate[] ents = (Locate[])_finder.getEntities(start, start + cnt);

        for (Locate ent : ents)
        {
            Params p = new Params();
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // エリア
            p.set(AREA_NO, ent.getAreaNo());
            // 棚
            p.set(LOCATION_NO, ent.getLocationNo());

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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        LocateSearchKey key = new LocateSearchKey();

        Locate locate = new Locate();
        // 検索条件、集約条件をセットする
        // where, group by
        // エリア
        locate.setAreaNo(param.getString(AREA_NO));
        key.setAreaNo(param.getString(AREA_NO));

        // 棚
        if (!StringUtil.isBlank(param.getString(LOCATION_NO)))
        {
            locate.setLocationNo(param.getString(LOCATION_NO));
            key.setLocationNo(param.getString(LOCATION_NO), ">=");
        }

        LocateController locateCnt = new LocateController(getConnection(), NoPlanStorage.class);
        key = locateCnt.getEmpLocationKey(locate, ">=");

        if (key == null)
        {
            // MSG = フリー管理エリアのため、空棚検索を行えません。
            setMessage("6023126");
            return key;
        }

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setAreaNoOrder(true);
            key.setLocationNoOrder(true);

            // 取得項目
            // エリアNo.
            key.setAreaNoCollect();
            // 棚No.
            key.setLocationNoCollect();
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
