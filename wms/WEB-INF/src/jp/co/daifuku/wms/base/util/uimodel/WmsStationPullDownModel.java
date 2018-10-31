// $Id: WmsStationPullDownModel.java 5389 2009-11-05 01:49:40Z shibamoto $
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
 * ステーション選択のプルダウンをサポートするモデルクラスです。
 *
 *
 * @version $Revision: 5389 $, $Date: 2009-11-05 10:49:40 +0900 (木, 05 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */

public class WmsStationPullDownModel
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
     * 対象プルダウンを指定してインスタンスを生成します。<br>
     * 
     * @param pdown 対象プルダウン
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsStationPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * ステーションプルダウンの内容をセットします。
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。
     * <ol>
     * <li>StationType
     * <li>Distribution
     * <li>連動プルダウンの親に指定したDistribution(指定していない場合は、不要)
     * <li>作業場を元にステーションを取得するための作業場No.(直行設定時に使用)
     * <li>Boolean 親のエリアプルダウンに「全て」がある場合trueを指定。
     * </ol>
     * @see jp.co.daifuku.wms.base.controller.PulldownController.BankType
     * @throws CommonException データの読み込みに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws CommonException
    {
        Class[] needed = {
            StationType.class,
            Distribution.class,
        };
        validateParameter(params, needed);

        StationType sttype = (StationType)params[0];
        Distribution append = ((Distribution)params[1]);

        int argidx = 1;
        Distribution parent = null;
        String workplace = null;
        boolean parentAreaHasAll = false;

        final int numArgs = params.length;
        argidx++;
        if (argidx < numArgs)
        {
            parent = (Distribution)params[argidx];
        }
        else
        {
            parent = Distribution.UNUSE;
        }
        argidx++;
        if (argidx < numArgs)
        {
            workplace = (String)params[argidx];
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

        String[] stations = pc.getStationPulldown(sttype, "", append, parent, workplace, parentAreaHasAll);

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
        return "$Id: WmsStationPullDownModel.java 5389 2009-11-05 01:49:40Z shibamoto $";
    }
}
