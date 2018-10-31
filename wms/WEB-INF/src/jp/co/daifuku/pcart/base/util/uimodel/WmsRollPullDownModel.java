// $Id: WmsRollPullDownModel.java 6307 2009-12-02 07:33:38Z okamura $
package jp.co.daifuku.pcart.base.util.uimodel;

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
import jp.co.daifuku.bluedog.model.StringSupport;
import jp.co.daifuku.bluedog.ui.control.PullDownBehavior;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.pcart.base.controller.PCTPulldownController;

/**
 * ロール用のプルダウンをサポートするデータモデルです。
 *
 * @version $Revision: 6307 $, $Date: 2009-12-02 16:33:38 +0900 (水, 02 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class WmsRollPullDownModel
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
     * ロールプルダウンとデータベースコネクションおよびロケールを
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
    public WmsRollPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ロール情報を読み込んでプルダウンにセットします。
     * 
     * @param conn データベースコネクション
     * @param args 以下のパラメータが必要です
     * <ol>
     * <li>デフォルト選択エリアNo (String, オプション)
     * <li>全エリア表示有無 (Boolean, オプション, デフォルト=表示あり)
     * </ol>
     * @throws CommonException エリア情報の読込に失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... args)
            throws CommonException
    {
        // エリアの内容を読み込んでプルダウンデータを生成
        PCTPulldownController pull = new PCTPulldownController(conn, getTerminalNo(), getLocale(), this.getClass());

        int argidx = 0;
        String defaultArea = "";
        Boolean isShowAll = Boolean.TRUE;

        final int numArgs = args.length;
        // 第1パラメータ (デフォルト選択エリア)
        if (argidx < numArgs)
        {
            defaultArea = StringSupport.valueOf(args[argidx]);
        }
        // 第2パラメータ (すべての表示有無)
        argidx++;
        if (argidx < numArgs)
        {
            isShowAll = (Boolean)args[argidx];
        }
        String[] roll = pull.getRollPulldown(defaultArea, isShowAll.booleanValue());

        // プルダウンデータをプルダウンへセット
        setupPullDown(getPullDown(), roll);
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
        return "$Id: WmsRollPullDownModel.java 6307 2009-12-02 07:33:38Z okamura $";
    }
}
