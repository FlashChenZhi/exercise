// $Id: WmsRftNoPullDownModel.java 2733 2009-01-15 09:11:22Z arai $
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
import jp.co.daifuku.wms.base.controller.PulldownController.TerminalType;

/**
 * RFTNoプルダウンのサポートを行うためのプルダウンモデルです。
 * 端末区分により、「全HT」、「全Pカート」の表示有無フラグを与えます。<BR>
 *
 * @version $Revision: 2733 $, $Date: 2009-01-15 18:11:22 +0900 (木, 15 1 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class WmsRftNoPullDownModel
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
    public WmsRftNoPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
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
     * <li>デフォルト選択号機No. (String)
     * <li>端末区分 (String)
     * <li>"全HT"、"全Pカート"の表示有無 (Boolean)
     * <ul><li>Boolean.TRUE: "全HT" を表示します
     * <li>Boolean.FALSE: "全HT" を表示しません</ul>
     * </ol>
     * @throws ReadWriteException データベースアクセスに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException
    {
        Class[] needed = {
                String.class,
                TerminalType.class,
                Boolean.class,
        };
        validateParameter(params, needed);

        PulldownController pc = new PulldownController(conn, getTerminalNo(), getLocale(), getClass());

        // check parameter #1
        Object param1 = params[0];
        String selected = (String)param1;

        // check parameter #2
        Object param2 = params[1];
        TerminalType terminalType = (TerminalType)param2;

        // check parameter #2
        Object param3 = params[2];
        Boolean showAllRFT = (Boolean)param3;

        String[] rtfNoItems = pc.getRFTNo(selected, terminalType, showAllRFT.booleanValue());

        super.setupPullDown(getPullDown(), rtfNoItems);
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
        return "$Id: WmsRftNoPullDownModel.java 2733 2009-01-15 09:11:22Z arai $";
    }
}
