// $Id: DuplicateOperatorException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.exception;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * データ重複時に詳細情報とともにスローされる例外です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class DuplicateOperatorException
        extends OperatorException
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * オペレータ例外を生成します。
     */
    public DuplicateOperatorException()
    {
        super();
    }

    /**
     * エラーコード情報を指定してオペレータ例外を生成します。
     * @param errorCode エラーコード
     */
    public DuplicateOperatorException(String errorCode)
    {
        super(errorCode);
    }

    /**
     * エラーコード情報および詳細を指定してオペレータ例外を生成します。
     * @param errorCode エラーコード
     * @param detail 詳細情報
     */
    public DuplicateOperatorException(String errorCode, Object detail)
    {
        super(errorCode, detail);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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
        return "$Id: DuplicateOperatorException.java 87 2008-10-04 03:07:38Z admin $";
    }
}
