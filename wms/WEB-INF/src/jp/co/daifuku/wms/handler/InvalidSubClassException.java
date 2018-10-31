//$Id: InvalidSubClassException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 実装サブクラスが正しくない場合にスローされる例外です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class InvalidSubClassException
        extends RuntimeException
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 1314968632695523651L;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * クラス情報を指定して例外を生成します。
     * @param correct 正しいクラス
     * @param recvd 使用されたクラス
     */
    public InvalidSubClassException(Class correct, Class recvd)
    {
        super("Correct class:" + correct.toString() + ", Received class:" + recvd.toString());
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
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
        return "$Id: InvalidSubClassException.java 87 2008-10-04 03:07:38Z admin $";
    }
}
