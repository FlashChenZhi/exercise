// $Id: StopTimeKeeper.java 6148 2009-11-20 08:21:35Z okamura $
package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to
 * license terms.
 */

/**
 * TimeKeeper終了処理を実行します。
 *
 * @version $Revision: 6148 $, $Date: 2009-11-20 17:21:35 +0900 (金, 20 11 2009) $
 * @author Y.Okamura
 * @author Last commit: $Author: okamura $
 */

public class StopTimeKeeper
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

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
     */
    private StopTimeKeeper()
    {
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 終了処理を行います。
     *
     * @param args 使用していません
     */
    public static void main(final String[] args)
    {
        StopTimeKeeper stk = new StopTimeKeeper();
        stk.start();
    }


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

    /**
     * 終了処理を行います
     */
    private void start()
    {
        InstructionFileHandler handler = new InstructionFileHandler();
        handler.putTerminate();
        
    }
    
    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<br>
     *
     * @return リビジョン文字列。<br>
     */
    public static String getVersion()
    {
        return "$Id: StopTimeKeeper.java 6148 2009-11-20 08:21:35Z okamura $";
    }
}
