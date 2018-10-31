// $Id: WmsSoftZonePullDownModel.java 7503 2010-03-12 05:48:08Z ota $
package jp.co.daifuku.wms.base.util.uimodel;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.ui.control.PullDownBehavior;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.controller.PulldownController.SoftZoneType;

/**
 * ハードゾーン選択用のプルダウンをサポートするモデルクラスです。<br>
 *
 *
 * @version $Revision: 7503 $, $Date: 2010-03-12 14:48:08 +0900 (金, 12 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class WmsSoftZonePullDownModel
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
     * 対象プルダウンを指定してインスタンスを生成します。
     * 
     * @param pdown 対象プルダウン
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsSoftZonePullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * ハードゾーンの選択肢をプルダウンにセットします。
     * 
     * @param conn データベースコネクション
     * @param params 使用しません。
     * @throws ReadWriteException データの読み込みに失敗したときスローされます。
     * @throws NotFoundException 
     * @throws InvalidDefineException 
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        Class[] needed = {
                SoftZoneType.class,
            };
        validateParameter(params, needed);
        
        SoftZoneType sztype = (SoftZoneType)params[0];
        DfkUserInfo ui = getUserInfo();
        String term = (null == ui) ? ""
                                  : ui.getTerminalNumber();

        List<String> item = null;
        boolean freeZone = true;
        if (params.length > 1)
        {
            if (!StringUtil.isBlank(params[1]))
            {
                item = (List<String>) params[1];
            }
        }
        if (params.length > 2)
        {
            freeZone =  (Boolean)params[2];
        }

        PulldownController pc = new PulldownController(conn, term, getLocale(), getClass());

        String[] items = pc.getSoftZonePulldown("", sztype, item, freeZone);

        setupPullDown(getPullDown(), items);
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
        return "$Id: WmsSoftZonePullDownModel.java 7503 2010-03-12 05:48:08Z ota $";
    }

}
