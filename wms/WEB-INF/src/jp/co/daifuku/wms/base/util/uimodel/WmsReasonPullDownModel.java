// $Id: WmsReasonPullDownModel.java,v 1.1.1.1 2009/02/10 08:55:37 arai Exp $
package jp.co.daifuku.wms.base.util.uimodel;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.*;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.ui.control.PullDownBehavior;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.controller.PulldownController;


public class WmsReasonPullDownModel
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
     * 作業理由マスタプルダウンとデータベースコネクションおよびロケールを
     * 指定してインスタンスを生成します。<br>
     * このコンストラクタではプルダウンの内容を取得しません。<br>
     * 内容を取得するためには、init()を呼び出してください。<br>
     * 
     * @param pdown エリアプルダウンコントロール
     * @param locale ロケール
     * @param ui ユーザ情報
     * 
     * @see Locale
     */
    public WmsReasonPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業理由の選択肢をプルダウンにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 使用しません。
     * @throws ReadWriteException データの読み込みに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws CommonException
    {
        // TODO 自動生成されたメソッド・スタブ
        DfkUserInfo ui = getUserInfo();
        String term = (null == ui) ? ""
                                  : ui.getTerminalNumber();

        PulldownController pc = new PulldownController(conn, term, getLocale(), getClass());

        String[] reasonitems = pc.getReasonPulldown("");

        setupPullDown(getPullDown(), reasonitems);
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
        return "$Id: WmsReasonPullDownModel.java,v 1.1.1.1 2009/02/10 08:55:37 arai Exp $";
    }
}
