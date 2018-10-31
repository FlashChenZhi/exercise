// $Id: WmsTerminalTypePullDownModel.java 5022 2009-09-18 00:56:29Z shibamoto $
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
import jp.co.daifuku.wms.base.controller.PulldownController;

/**
 * RFTNoプルダウンのサポートを行うためのプルダウンモデルです。
 * 端末区分により、「全HT」、「全Pカート」の表示有無フラグを与えます。<BR>
 *
 * @version $Revision: 5022 $, $Date: 2009-09-18 09:56:29 +0900 (金, 18 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */

public class WmsTerminalTypePullDownModel
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
     * プルダウンモデルを初期化します。
     * 
     * @param pdown プルダウンコントロール
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsTerminalTypePullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 号機No.プルダウンのデータを読み込んでプルダウンコントロールにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * <li>デフォルト選択端末区分 (String)
     * </ol>
     * @throws CommonException データベースアクセスに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws CommonException
    {
        Class[] needed = {
                String.class,
        };
        validateParameter(params, needed);

        PulldownController pc = new PulldownController(conn, getTerminalNo(), getLocale(), getClass());

        // check parameter #1
        Object param1 = params[0];
        String selected = (String)param1;

        String[] terminalTypeItems = pc.getTerminalType(selected);

        super.setupPullDown(getPullDown(), terminalTypeItems);
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
        return "$Id: WmsTerminalTypePullDownModel.java 5022 2009-09-18 00:56:29Z shibamoto $";
    }
}
