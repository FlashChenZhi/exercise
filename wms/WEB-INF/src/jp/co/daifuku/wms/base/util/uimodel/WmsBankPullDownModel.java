// $Id: WmsBankPullDownModel.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.controller.PulldownController.BankType;

/**
 * Bank選択のプルダウンをサポートするモデルクラスです。<br>
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class WmsBankPullDownModel
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
    public WmsBankPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * バンクプルダウンの内容をセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * <li>バンクタイプ (BankType)
     * </ol>
     * @see BankType
     * @throws ReadWriteException データの読み込みに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException
    {
        Class[] needed = {
            BankType.class,
        };
        validateParameter(params, needed);

        DfkUserInfo ui = getUserInfo();
        String term = (null == ui) ? ""
                                  : ui.getTerminalNumber();

        BankType banktype = (BankType)params[0];
        PulldownController pc = new PulldownController(conn, term, getLocale(), getClass());

        String[] bankitems = pc.getBankPulldown(banktype, "");

        setupPullDown(getPullDown(), bankitems);
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
        return "$Id: WmsBankPullDownModel.java 87 2008-10-04 03:07:38Z admin $";
    }
}
