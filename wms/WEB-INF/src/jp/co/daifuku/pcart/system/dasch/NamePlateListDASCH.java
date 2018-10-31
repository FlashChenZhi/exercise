// $Id: NamePlateListDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.pcart.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.dasch.NamePlateListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserFinder;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * ユーザーID（名札用）リストに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class NamePlateListDASCH
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
    public NamePlateListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new Com_loginuserFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;

        setMessage("6001013");
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
        Com_loginuser[] ents = (Com_loginuser[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (Com_loginuser ent : ents)
        {
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(USER_ID, ent.getUserid());
            p.set(USER_NAME, ent.getUsername());

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
        //throw new ScheduleException("This method is not implemented.");
        Com_loginuserHandler handler = new Com_loginuserHandler(getConnection());

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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        Com_loginuser[] ents = (Com_loginuser[])_finder.getEntities(start, start + cnt);

        for (Com_loginuser ent : ents)
        {
            Params p = new Params();
            p.set(USER_ID, ent.getUserid());
            p.set(USER_NAME, ent.getUsername());

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
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        Com_loginuserSearchKey key = new Com_loginuserSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // パラメータで受け取ったPCTSystemInParameterに以下をセットする
        // ユーザーID(to)
        if (!StringUtil.isBlank(param.getString(FROM_USER_ID)) && !StringUtil.isBlank(param.getString(TO_USER_ID)))
        {
            String[] userList = WmsFormatter.getFromTo(param.getString(FROM_USER_ID), param.getString(TO_USER_ID));

            key.setUserid(userList[0], ">=", "(", "", true);
            key.setUserid(userList[1], "<=", "", ")", true);
        }
        else
        {
            // ユーザーID(from) fromのみ
            if (!StringUtil.isBlank(param.getString(FROM_USER_ID)))
            {
                key.setUserid(param.getString(FROM_USER_ID), ">=", "", "", true);

            }
            else
            {
                if (!StringUtil.isBlank(param.getString(TO_USER_ID)))
                {
                    key.setUserid(param.getString(TO_USER_ID), "<=", "", "", true);
                }
            }
        }

        // DAIFUKUユーザ以外
        // ユーザIDが指定されていたら、それ以降全てを検索
        key.setUserid("DAIFUKU", "!=", "", "", true);

        // ロールID
        if (!StringUtil.isBlank(param.getString(ROLE)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(ROLE)))
            {
                key.setRoleid(param.getString(ROLE));
            }
        }

        if (isSet)
        {
            key.setUseridOrder(true);
            // 取得項目
            // ユーザーID
            key.setUseridCollect();
            // ユーザー名称
            key.setUsernameCollect();

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
