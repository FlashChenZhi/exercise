package jp.co.daifuku.wms.system.dasch;

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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.foundation.da.AbstractDASCH;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserFinder;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;

import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import static jp.co.daifuku.wms.system.dasch.LstSystemUserDASCHParams.*;

/**
 * ユーザ名称検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4809 $, $Date: 2009-08-10 15:33:13 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstSystemUserDASCH
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
    public LstSystemUserDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new Com_loginuserFinder(getConnection());
        
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
        Com_loginuserHandler handler = new Com_loginuserHandler(getConnection());
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
        Com_loginuser[] restData = (Com_loginuser[])_finder.getEntities(start, start + cnt);
        
        int _point = 1;
        // 取得情報をパラメータに編集
        for (Com_loginuser rData: restData)
        {
            Params p = new Params();
            
            // No.
            p.set(COLUMN_1, start + _point);
            // ユーザ名称
            p.set(USER_NAME, rData.getValue(Com_loginuser.USERNAME));
            
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
        Com_loginuserSearchKey key = new Com_loginuserSearchKey();

        /* 取得項目と集約条件の指定 */

        // ユーザーID
        key.setUseridCollect();
        key.setUserid(EmConstants.ANONYMOUS_USER, "!=");
        key.setUserid(EmConstants.DAIFUKU_SV_USER, "!=");
        key.setUserid(EmConstants.USERMAINTENANCE_USER, "!=");

        // ユーザー名称
        key.setUsername("", "IS NOT NULL");

        /* ソート順の指定 */
        // ユーザーID
        key.setUseridOrder(true);

        // ユーザー名称
        key.setUsernameOrder(true);
        
        /* 取得項目 */
        // ユーザー名称
        key.setUsernameCollect();
        
        return key;
    }

}
//end of class
