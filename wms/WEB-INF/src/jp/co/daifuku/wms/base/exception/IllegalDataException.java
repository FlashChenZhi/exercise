//$Id: IllegalDataException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.exception;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 取込ファイル上の指定された項目の値が不正だった場合の例外です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/24</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class IllegalDataException
        extends Exception
{
    // Class fields --------------------------------------------------
    /**<jp>
     * エラーレベル(警告)
     </jp>*/
    /**<en>
     * Error level (warning)
     </en>*/
    public static final int ERROR_LEVEL_WARN = 3;

    /**<jp>
     * エラーレベル(異常)
     </jp>*/
    /**<en>
     * Error level (error)
     </en>*/
    public static final int ERROR_LEVEL__ERROR = 4;


    // Class variables -----------------------------------------------	
    /**
     * エラーレベル保持用
     */
    private int _errorLevel = -1;

    /**
     * エラーメッセージ保持用
     */
    private String _message = null;

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * 取込ファイルの項目で、不正なデータがあった場合に、
     * エラー内容をセットして、例外を通知します。
     * 
     * @param value エラー内容
     */
    public IllegalDataException(String value)
    {
        super(value);

        this.setMessage(value);
    }

    /**
     * 取込ファイルの項目で、不正なデータがあった場合に、
     * エラー内容をセットして、例外を通知します。
     * 
     * @param value エラー内容
     */
    public IllegalDataException(int value)
    {
        super();

        this.setErrorLevel(value);
    }

    /**
     * このクラスで行ったチェックでNGだった際の
     * エラーメッセージを設定します。
     * 
     * @param value エラー内容
     */
    public void setMessage(String value)
    {
        _message = value;
    }

    /**
     * このクラスで行ったチェックでNGだった際の
     * エラーメッセージを取得します。
     * 
     * @return エラーメッセージ
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * このクラスで行ったチェックでNGだった際の
     * エラーレベルを設定します。
     * 
     * @param value エラーレベル
     */
    public void setErrorLevel(int value)
    {
        _errorLevel = value;
    }

    /**
     * このクラスで行ったチェックでNGだった際の
     * エラーレベルを取得します。
     * 
     * @return エラーレベル
     */
    public int getErrorLevel()
    {
        return _errorLevel;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
