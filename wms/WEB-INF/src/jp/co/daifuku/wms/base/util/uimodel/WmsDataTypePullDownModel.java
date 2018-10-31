// $Id: codetemplates.xml 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.uimodel;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.wms.base.controller.PulldownController.DataType;


public class WmsDataTypePullDownModel
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
     * 送受信データタイプをプルダウンとデータベースコネクションおよびロケールを
     * 指定してインスタンスを生成します。<br>
     * このコンストラクタではプルダウンの内容を取得しません。<br>
     * 内容を取得するためには、init()を呼び出してください。<br>
     * @param pdown エリアプルダウンコントロール
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsDataTypePullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 送受信データタイプをプルダウンにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 使用しません。
     * @throws ReadWriteException データの読み込みに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws CommonException
    {
        Class[] needed = {
            DataType.class,
        };
        validateParameter(params, needed);

        DfkUserInfo ui = getUserInfo();
        String term = (null == ui) ? ""
                                  : ui.getTerminalNumber();

        DataType dataType = (DataType)params[0];
        PulldownController pc = new PulldownController(conn, term, getLocale(), getClass());

        String[] dateItems = pc.getDataTypePulldown(dataType, "");

        setupPullDown(getPullDown(), dateItems);
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
        return "$Id: codetemplates.xml 87 2008-10-04 03:07:38Z admin $";
    }
}
