// $Id: LstStationStatusDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

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
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationFinder;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaHandler;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.TerminalArea;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import static jp.co.daifuku.wms.asrs.dasch.LstStationStatusDASCHParams.*;

/**
 * ステーション状態一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstStationStatusDASCH
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
    public LstStationStatusDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new StationFinder(getConnection());
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
        throw new ScheduleException("This method is not implemented.");
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
        //throw new ScheduleException("This method is not implemented.");
        TerminalAreaHandler handler = new TerminalAreaHandler(getConnection());

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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        Station[] ents = (Station[])_finder.getEntities(start, start + cnt);

        // DFKLOOK
        Params p = null;
        for (Station ent : ents)
        {
            // DFKLOOK ここから
            p = new Params();
            // 番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // ステーションNo.
            p.set(STATION_NO, ent.getStationNo());
            // ステーション名称
            p.set(STATION, ent.getStationName());
            // AWCモード切替
            p.set(MODE_TYPE, DisplayResource.getModeType(ent.getModeType()));
            p.set(HIDDEN_MODE_TYPE, ent.getModeType());
            /* 現在作業モード */
            p.set(CURRENT_MODE, DisplayResource.getMode(ent.getCurrentMode()));
            p.set(HIDDEN_CURRENT_MODE, ent.getCurrentMode());
            /* 機器状態 */
            p.set(STATUS, DisplayResource.getStationStatus(ent.getStatus()));
            p.set(HIDDEN_STATUS, ent.getStatus());
            /* 中断中フラグ */
            p.set(SUSPEND, DisplayResource.getSuspend(ent.getSuspend()));
            p.set(HIDDEN_SUSPEND, ent.getSuspend());
            /* モード切替要求区分 */
            p.set(MODE_REQUEST, DisplayResource.getModeRequest(ent.getModeRequest()));
            p.set(HIDDEN_MODE_REQUEST, ent.getModeRequest());
            // 作業件数
            p.set(WORK_COUNT, getWorkingCount(ent.getStationNo()));
            // ここまで

            params.add(p);
        }
        return params;
    }

    /**
     * DFKLOOK
     * ステーションNo.にて、作業件数を取得します。
     * @param pStation ステーションNo.
     * @return 作業件数
     */
    protected int getWorkingCount(String pStation)
    {
        try
        {
            CarryInfoHandler wCiHandler = new CarryInfoHandler(getConnection());
            CarryInfoSearchKey wCiSearchKey = new CarryInfoSearchKey();
            //検索実行

            // 取得条件をセットします
            // 搬送テーブルより、該当ステーションでの作業件数を取得します。
            wCiSearchKey.clearKeys();
            // 搬送元ステーションNo.
            wCiSearchKey.setSourceStationNo(pStation, "=", "(", "", false);
            // 搬送先ステーションNo.
            wCiSearchKey.setDestStationNo(pStation, "=", "", ")", false);

            // 集約条件
            // 搬送Key
            wCiSearchKey.setCarryKeyCollect("DISTINCT");

            int rCount = wCiHandler.count(wCiSearchKey);

            return rCount;
        }
        catch (ReadWriteException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6006020, ex), this.getClass().getName());
            return 0;
        }
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
        // DFKLOOK ここから
        Connection conn = getConnection();

        // 端末情報からステーションNo.を取得
        TerminalAreaHandler tahndl = new TerminalAreaHandler(conn);
        TerminalAreaSearchKey key = new TerminalAreaSearchKey();

        key.setTerminalNo(this.getTerminalNo());
        TerminalArea[] tmarea = (TerminalArea[])tahndl.find(key);

        StationSearchKey sKey = new StationSearchKey();
        // ここまで

        if (tmarea != null)
        {
            // DFKLOOK ここから

            /* 検索条件の指定 */
            // 操作端末から取得できる全てのステーション情報
            String[] st = new String[tmarea.length];
            for (int i = 0; i < tmarea.length; i++)
            {
                st[i] = tmarea[i].getStationNo();
            }
            sKey.setStationNo(st, true);
            // 動作モードが手動または自動
            String[] modeType = {
                    Station.MODE_TYPE_AWC_CHANGE,
                    Station.MODE_TYPE_AUTO_CHANGE
            };
            sKey.setModeType(modeType, false);
            // ここまで

            if (isSet)
            {
                // DFKLOOK ここから
                /* 集約条件の指定 */
                // 集約なし
                /* ソート順の指定 */
                // ステーションNo.順
                sKey.setStationNoOrder(true);

                /* 取得項目の指定 */
                sKey.setStationNoCollect(); // ステーションNo.
                sKey.setStationNameCollect(); // ステーション名称
                sKey.setModeTypeCollect(); // モード切替種別(動作モード)
                sKey.setCurrentModeCollect(); // 現在作業モード
                sKey.setStatusCollect(); // ステーション状態(機器状態)
                sKey.setSuspendCollect(); // 作業中フラグ(作業状態)
                sKey.setModeRequestCollect(); // モード切替要求区分

                // ここまで
            }
        }


        return sKey;
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
