// $Id: RouteOperatorException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.exception;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 搬送ルートチェックで搬送不可の際にスローされる例外です。<br>
 * 
 * <!--
 * ルートステータス(int)以外の情報を保持する必要があれば、
 * RouteStatus クラスを使用するように拡張してください。
 * この例外そのものにアクセッサをむやみに追加することは避けてください。
 * -->
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class RouteOperatorException
        extends OperatorException
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ルートステータス (オフライン) */
    public static final int OFFLINE = 1; // FIXME use RouteController.OFFLINE;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private int _routeStatus;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ルートステータスを初期化して、例外を生成します。
     * @param status ルートステータス
     */
    public RouteOperatorException(int status)
    {
        super();
        setRouteStatus(status);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * ルートステータスを返します。
     * @return ルートステータス
     */
    public int getRouteStatus()
    {
        return _routeStatus;
    }

    /**
     * ルートステータスを設定します。
     * @param status ルートステータス
     */
    public void setRouteStatus(int status)
    {
        _routeStatus = status;
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
        return "$Id: RouteOperatorException.java 87 2008-10-04 03:07:38Z admin $";
    }

    //    /**
    //     * ルートステータスを保持します。
    //     * 
    //     * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
    //     * @author  ss
    //     * @author  Last commit: $Author: admin $
    //     */
    //    public class RouteStatus
    //    {
    //        private int _status;
    //
    //        /**
    //         * インスタンスを生成します。
    //         */
    //        public RouteStatus()
    //        {
    //        }
    //
    //        /**
    //         * ルートチェックステータスを返します。
    //         * @return ルートチェックステータス
    //         */
    //        public int getStatus()
    //        {
    //            return _status;
    //        }
    //
    //        /**
    //         * ルートチェックステータスを設定します。
    //         * @param status ルートチェックステータス
    //         */
    //        public void setStatus(int status)
    //        {
    //            _status = status;
    //        }
    //    }
}
