// $Id: WmsAislePullDownModel.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.controller.PulldownController;

/**
 * アイルプルダウンのサポートを行うためのプルダウンモデルです。
 * 複数倉庫の場合の選択データ、「全RM」の表示有無フラグを与えます。<BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class WmsAislePullDownModel
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
    public WmsAislePullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * アイルプルダウンのデータを読み込んでプルダウンコントロールにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * <li>倉庫ID (String)
     * <li>"全RM"の表示有無 (Boolean)
     * <ul><li>Boolean.TRUE: "全RM" を表示します
     * <li>Boolean.FALSE: "全RM" を表示しません</ul>
     * </ol>
     * @throws ReadWriteException データベースアクセスに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException
    {
        Class[] needed = {
            String.class,
            Boolean.class,
        };
        validateParameter(params, needed);

        PulldownController pc = new PulldownController(conn, getTerminalNo(), getLocale(), getClass());

        // check parameter #1
        Object param1 = params[0];
        String whNo = (String)param1;

        // check parameter #2
        Object param2 = params[1];
        Boolean showAllRM = (Boolean)param2;

        String[] aisleItems = pc.getAislePulldown("", whNo, showAllRM.booleanValue());

        super.setupPullDown(getPullDown(), aisleItems);
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
        return "$Id: WmsAislePullDownModel.java 87 2008-10-04 03:07:38Z admin $";
    }
}
