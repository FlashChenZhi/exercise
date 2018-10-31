// $Id: ReasonController.java,v 1.1.1.1 2009/02/10 08:55:27 arai Exp $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.ReasonHandler;
import jp.co.daifuku.wms.base.dbhandler.ReasonSearchKey;
import jp.co.daifuku.wms.base.entity.Reason;

/**
 * 作業理由マスタを操作するためのコントローラクラスです。
 *
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */
public class ReasonController
        extends AbstractController
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
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public ReasonController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 理由区分に対する名称を取得します。<br>
     * 
     * @param reasonType 対象の理由区分
     * @return 理由区分に対する名称
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String getReasonName(int reasonType)
            throws ReadWriteException
    {
        // 理由名称取得
        ReasonHandler reasonHandler = new ReasonHandler(getConnection());
        ReasonSearchKey rKey = new ReasonSearchKey();

        // 取得条件
        rKey.setReasonType(reasonType);
        rKey.setReasonNameCollect();

        try
        {
            Reason reason = (Reason)reasonHandler.findPrimary(rKey);
            if (reason == null)
            {
                return "";
            }
            return reason.getReasonName();
        }
        catch (NoPrimaryException e)
        {
            return "";
        }
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
        return "$Id: ReasonController.java,v 1.1.1.1 2009/02/10 08:55:27 arai Exp $";
    }
}
