// $Id: WmsAllocPriorityPullDownModel.java 87 2008-10-04 03:07:38Z admin $
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
 * 引当パターンプルダウンのサポートを行うプルダウンモデルです。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class WmsAllocPriorityPullDownModel
        extends PullDownModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 引き当てタイプ
     */
    public enum AllocateType
    {
        /** 通常（出庫）用 */
        NORMAL,
        /** 補充用 */
        REPLENISHMENT
    }

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
     * 引き当てパターンプルダウンモデルを生成します。<br>
     * このコンストラクタではプルダウンの値を取得しません。<br>
     * init()を呼び出したときにプルダウンの値を取得してセットします。
     * 
     * @param pdown プルダウンコントロール
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WmsAllocPriorityPullDownModel(PullDownBehavior pdown, Locale locale, DfkUserInfo ui)
    {
        super(pdown, locale,ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 引当パターンプルダウンを表示するためのデータをプルダウンにセットします。<br>
     * プルダウンの種別（以下に示す種類のみ可能）、初期表示の選択データを与えます。
     * 
     * <ul>
     * <li>通常(出庫)用 (AllocateType.NORMALを指定)
     * <li>補充用 (AllocateType.REPLENISHMENTを指定)
     * </ul>
     * 
     * @param conn データベースコネクション
     * @param params 以下のパラメータを指定します。<br>
     * <ol>
     * <li>AllocateType
     * </ol>
     * @throws ReadWriteException データベースの読み込みに失敗したときスローされます。
     */
    @Override
    public void init(Connection conn, Object... params)
            throws ReadWriteException
    {
        Class[] needed = {
            AllocateType.class,
        };
        validateParameter(params, needed);

        PulldownController pc = new PulldownController(conn, getTerminalNo(), getLocale(), getClass());

        jp.co.daifuku.wms.base.controller.PulldownController.AllocateType type;

        AllocateType aType = (AllocateType)params[0];
        switch (aType)
        {
            // replenish
            case REPLENISHMENT:
                type = jp.co.daifuku.wms.base.controller.PulldownController.AllocateType.REPLENISHMENT;
                break;
            // normal is default
            case NORMAL:
            default:
                type = jp.co.daifuku.wms.base.controller.PulldownController.AllocateType.NORMAL;
                break;
        }
        String[] allocItems = pc.getAllocatePriorityPulldown(type, "");
        setupPullDown(getPullDown(), allocItems);
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
        return "$Id: WmsAllocPriorityPullDownModel.java 87 2008-10-04 03:07:38Z admin $";
    }
}
