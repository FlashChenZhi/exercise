// $Id: WmsWorkspacePullDownModel.java 5389 2009-11-05 01:49:40Z shibamoto $
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
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;

/**
 * 作業場選択のプルダウンをサポートするモデルクラスです。
 *
 *
 * @version $Revision: 5389 $, $Date: 2009-11-05 10:49:40 +0900 (木, 05 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */

public class WmsWorkspacePullDownModel
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     *  対象プルダウンを指定してインスタンスを生成します。
     * 
     * @param pdown 対象プルダウン
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsWorkspacePullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 作業場プルダウンのデータを読み込んでプルダウンコントロールにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * <li>StationType
     * <li>初期選択するデータ(使用しない場合は、この引数は不要。
     * ただし、Distributionを指定する場合は、この引数を必ずセットしてください。
     * 特に指定しない場合は""を指定してください。)
     * <li>Distribution(自動または全体を指定したい場合に使用。使用しない場合は、この引数は不要)
     * <li>Boolean 親のエリアプルダウンに「全て」がある場合trueを指定。
     * </ol>
     * @throws CommonException データベースアクセスに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws CommonException
    {
        Class[] needed = {
            StationType.class,
        };
        validateParameter(params, needed);

        StationType stype = (StationType)params[0];

        int argidx = 1;
        String selected = "";
        Distribution disp = null;
        Boolean parentAreaHasAll = false;

        final int numArgs = params.length;
        if (argidx < numArgs)
        {
            selected = (String)params[argidx];
        }
        argidx++;
        if (argidx < numArgs)
        {
            disp = (Distribution)params[argidx];
        }
        else
        {
            disp = Distribution.UNUSE;
        }
        argidx++;
        if (argidx < numArgs)
        {
        	
        	parentAreaHasAll = Boolean.valueOf(params[argidx].toString());
        }

        DfkUserInfo ui = getUserInfo();
        String term = (null == ui) ? ""
                                  : ui.getTerminalNumber();
        PulldownController pc = new PulldownController(conn, term, getLocale(), getClass());

        String[] stations = pc.getWorkplacePulldown(stype, selected, disp, parentAreaHasAll);
        setupPullDown(getPullDown(), stations);
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
        return "$Id: WmsWorkspacePullDownModel.java 5389 2009-11-05 01:49:40Z shibamoto $";
    }
}
