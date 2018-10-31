// $Id: WmsWorkContentsPullDownModel.java 267 2008-10-16 06:35:07Z toda $
package jp.co.daifuku.wms.base.util.uimodel;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.ui.control.PullDownBehavior;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.controller.PulldownController;

/**
 * 作業内容選択のプルダウンをサポートするモデルクラスです。<br>
 *
 *
 * @version $Revision: 267 $, $Date: 2008-10-16 15:35:07 +0900 (木, 16 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: toda $
 */

public class WmsWorkContentsPullDownModel
        extends PullDownModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * 対象のプルダウンを指定してインスタンスを生成します。<br>
     * 
     * @param pdown 対象プルダウン
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsWorkContentsPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 作業内容プルダウンの内容をセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * <li>"全て"の表示有無 (Boolean)
     * <ul><li>Boolean.TRUE: "全て" を表示します
     * <li>Boolean.FALSE: "全て" を表示しません</ul>
     * </ol>
     * @throws ReadWriteException データの読み込みに失敗したときスローされます。
     * @throws CommonException データの読み込みに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException, CommonException
    {
        Class[] needed = {
            Boolean.class,
        };
        validateParameter(params, needed);

        DfkUserInfo ui = getUserInfo();
        String term = (null == ui) ? ""
                                  : ui.getTerminalNumber();

        Boolean workconttype = (Boolean)params[0];
        PulldownController pc = new PulldownController(conn, term, getLocale(), getClass());

        String[] workcontitems = pc.getWorkContentsPulldown("", workconttype.booleanValue());

        setupPullDown(getPullDown(), workcontitems);
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WmsWorkContentsPullDownModel.java 267 2008-10-16 06:35:07Z toda $";
    }
}
