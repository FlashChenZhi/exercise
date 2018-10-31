// $Id: WmsWorkFlagPullDownModel.java 7968 2010-05-31 09:50:37Z ota $
package jp.co.daifuku.wms.base.util.uimodel;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.PullDownBehavior;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;


public class WmsWorkFlagPullDownModel
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
     * TODO default constructor.
     */
    public WmsWorkFlagPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業区分プルダウンのデータを読み込んでプルダウンコントロールにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * 
     * </ol>
     * @throws CommonException データベースアクセスに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException,
                CommonException
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

        String[] workcontitems = pc.getWorkFlagPulldown("", workconttype.booleanValue());

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
        return "$Id: WmsWorkFlagPullDownModel.java 7968 2010-05-31 09:50:37Z ota $";
    }
}
