// $Id: RouteException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.exception;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.CommonException;


/**
 * AS/RSにおいて、搬送ルートが確保できない場合にスローされる例外です。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class RouteException
        extends CommonException
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
    private int _routeStatus;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 詳細情報なしで例外を生成します。
     */
    public RouteException()
    {
        super();
    }

    /**
     * メッセージを指定して例外を生成します。<br>
     * 通常はこのコンストラクタを使用しないでください。
     * @param msg 詳細メッセージ
     */
    protected RouteException(String msg)
    {
        super(msg);
    }

    /**
     * 元となった例外を指定して例外を生成します。<br>
     * 通常はこのコンストラクタを使用しないでください。
     * @param cause この例外の元となった例外
     */
    protected RouteException(Throwable cause)
    {
        super(cause);
    }


    /**
     * ルートステータスを指定して例外を生成します。
     * @param routeStat ルートステータス
     */
    public RouteException(int routeStat)
    {
        setRouteStatus(routeStat);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * routeStatusを返します。
     * @return routeStatusを返します。
     */
    public int getRouteStatus()
    {
        return _routeStatus;
    }

    /**
     * routeStatusを設定します。
     * @param routeStatus routeStatus
     */
    public void setRouteStatus(int routeStatus)
    {
        _routeStatus = routeStatus;
    }

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
        return "$Id: RouteException.java 87 2008-10-04 03:07:38Z admin $";
    }

}
