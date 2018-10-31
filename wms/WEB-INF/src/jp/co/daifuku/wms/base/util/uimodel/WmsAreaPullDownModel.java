// $Id: WmsAreaPullDownModel.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.bluedog.model.StringSupport;
import jp.co.daifuku.bluedog.ui.control.PullDownBehavior;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;

/**
 * エリア選択用のプルダウンをサポートするデータモデルです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class WmsAreaPullDownModel
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
     * エリアプルダウンとデータベースコネクションおよびロケールを
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
    public WmsAreaPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * エリア情報を読み込んでプルダウンにセットします。
     * 
     * @param conn データベースコネクション
     * @param args 以下のパラメータが必要です
     * <ol>
     * <li>AreaType
     * <li>StationType (オプション)
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
        PulldownController pull = new PulldownController(conn, getTerminalNo(), getLocale(), this.getClass());

        Class[] needed = {
            AreaType.class,
        };
        validateParameter(args, needed);

        int argidx = 0;
        AreaType areaType = (AreaType)args[argidx];
        StationType stationType = null;
        String defaultArea = "";
        Boolean isShowAll = Boolean.TRUE;

        final int numArgs = args.length;
        // 第2パラメータ (StationType)
        argidx++;
        if (argidx < numArgs)
        {
            stationType = (StationType)args[argidx];
        }
        // 第3パラメータ (デフォルト選択エリア)
        argidx++;
        if (argidx < numArgs)
        {
            defaultArea = StringSupport.valueOf(args[argidx]);
        }
        // 第4パラメータ (すべての表示有無)
        argidx++;
        if (argidx < numArgs)
        {
            isShowAll = (Boolean)args[argidx];
        }
        String[] sagyoba = pull.getAreaPulldown(areaType, stationType, defaultArea, isShowAll.booleanValue());

        // プルダウンデータをプルダウンへセット
        setupPullDown(getPullDown(), sagyoba);
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
        return "$Id: WmsAreaPullDownModel.java 87 2008-10-04 03:07:38Z admin $";
    }
}
