// $Id: ControlInfo.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.communication.as21;


/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの制御情報を管理します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
public class ControlInfo
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 制御情報の長さ
     </jp>*/
    public static final int LEN_CONTROL_INFO = 30;

    /**<jp>
     *制御情報の荷幅区分のオフセット定義
     </jp>*/
    private static final int OFF_CONTROL_INFO_WIDTH = 0;

    /**<jp>
     * 荷幅区分の長さ
     </jp>*/
    private static final int LEN_CONTROL_INFO_WIDTH = 1;

    /**<jp>
     *制御情報の荷長区分のオフセット定義
     </jp>*/
    private static final int OFF_CONTROL_INFO_LENGTH = OFF_CONTROL_INFO_WIDTH + LEN_CONTROL_INFO_WIDTH;

    /**<jp>
     * 荷長区分の長さ
     </jp>*/
    private static final int LEN_CONTROL_INFO_LENGTH = 1;

    /**<jp>
     *制御情報の荷高区分のオフセット定義
     </jp>*/
    private static final int OFF_CONTROL_INFO_HEIGHT = OFF_CONTROL_INFO_LENGTH + LEN_CONTROL_INFO_LENGTH;

    /**<jp>
     * 荷高区分の長さ
     </jp>*/
    private static final int LEN_CONTROL_INFO_HEIGHT = 1;

    // Class variables -----------------------------------------------
    /**
     * 荷幅区分
     */
    private int _width = 0;

    /**
     * 荷長区分
     */
    private int _length = 0;

    /**
     * 荷高区分
     */
    private int _height = 0;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version adn the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * コンストラクタ
     </jp>*/
    /**<en>
     * Constructor
     </en>*/
    public ControlInfo()
    {
    }

    // Public methods ------------------------------------------------
    /**
     * 荷幅を返します。
     * @return 荷幅を返します。
     */
    public int getWidth()
    {
        return _width;
    }

    /**
     * 荷幅を設定します。
     * @param width 荷幅
     */
    public void setWidth(int width)
    {
        _width = width;
    }

    /**
     * 荷長を返します。
     * @return 荷長を返します。
     */
    public int getLength()
    {
        return _length;
    }

    /**
     * 荷長を設定します。
     * @param length 荷長
     */
    public void setLength(int length)
    {
        _length = length;
    }

    /**
     * 荷高を返します。
     * @return 荷高を返します。
     */
    public int getHeight()
    {
        return _height;
    }

    /**
     * 荷高を設定します。
     * @param length 荷高
     */
    public void setHeight(int height)
    {
        _height = height;
    }

    /**
     * String型の制御情報を、項目毎に分けた制御情報クラスとして返します。
     * @param controlInfo 制御情報
     * @return 制御情報を返します。
     */
    public ControlInfo convertControlInfo(String controlInfo)
    {
        ControlInfo cinfo = new ControlInfo();

        if (controlInfo.trim().length() < (LEN_CONTROL_INFO_WIDTH + LEN_CONTROL_INFO_LENGTH + LEN_CONTROL_INFO_HEIGHT))
        {
            return null;
        }

        int width =
                Integer.parseInt(controlInfo.substring(OFF_CONTROL_INFO_WIDTH, OFF_CONTROL_INFO_WIDTH
                        + LEN_CONTROL_INFO_WIDTH));
        int length =
                Integer.parseInt(controlInfo.substring(OFF_CONTROL_INFO_LENGTH, OFF_CONTROL_INFO_LENGTH
                        + LEN_CONTROL_INFO_LENGTH));
        int height =
                Integer.parseInt(controlInfo.substring(OFF_CONTROL_INFO_HEIGHT, OFF_CONTROL_INFO_HEIGHT
                        + LEN_CONTROL_INFO_HEIGHT));

        cinfo.setWidth(width);
        cinfo.setLength(length);
        cinfo.setHeight(height);

        return cinfo;
    }

    /**
     * 制御情報クラスの制御情報を、String型として返します。
     * @param controlInfo 制御情報
     * @return 制御情報を返します。
     */
    public String convertControlInfo(ControlInfo controlInfo)
    {
        String cinfo =
                Integer.toString(controlInfo.getWidth()) + Integer.toString(controlInfo.getLength())
                        + Integer.toString(controlInfo.getHeight());

        return cinfo;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
// end of class
