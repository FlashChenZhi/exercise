// $Id: CommonException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**<jp>
 * 全てのユーザ定義例外のスーパークラスです。<br>
 * 新しくユーザ定義例外を作成する場合は当クラスを継承する必要があります。
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * 
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public abstract class CommonException
        extends Exception
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
    // Constructors --------------------------------------------------
    /**<jp>
     * 詳細メッセージを指定しないで Exception を構築します
     </jp>*/
    /**<en>
     * Constructs the Exception without specifying detail message. 
     </en>*/
    protected CommonException()
    {
        super();
    }

    /**<jp>
     * メッセージ付きの例外を作成します。
     * @param msg  詳細メッセージ
     </jp>*/
    /**<en>
     * Creates the exception with message attached.
     * @param msg  detail message
     </en>*/
    protected CommonException(String msg)
    {
        super(msg);
    }


    /**
     * 発生元の例外を保存する例外を作成します。
     * 
     * @param cause 例外の発生元
     */
    protected CommonException(Throwable cause)
    {
        super(cause);
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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }
}
