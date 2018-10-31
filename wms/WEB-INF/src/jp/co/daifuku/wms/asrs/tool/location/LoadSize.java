// $Id: LoadSize.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * 荷姿を管理するためのクラスです。
 * 入力された荷長、荷高にて荷姿を決定します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to control the load size.
 * Storage load size will be determined based on the entered data such as load height, etc.
 * It is possible to define just one load size per location. 
 * Also it is possible to keep the prioritized location order, e.g., small load -> medium load -> large.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class LoadSize
        extends ToolEntity
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 荷姿
     </jp>*/
    /**<en>
     * Load size
     </en>*/
    protected int wLoadSize = 0;

    /**<jp>
     * 荷姿名称
     </jp>*/
    /**<en>
     * Name of the load size
     </en>*/
    protected String wLoadSizeName;

    /**<jp>
     * 荷長
     </jp>*/
    /**<en>
     * Load length
     </en>*/
    protected int wLength = 0;

    /**<jp>
     * 荷高
     </jp>*/
    /**<en>
     * Load height
     </en>*/
    protected int wHeight = 0;

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>LoadSize</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>LoadSize</CODE>.
     </en>*/
    public LoadSize()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 荷姿をセットします。
     * @param loadsize セットする荷姿
     </jp>*/
    /**<en>
     * Set the load size.
     * @param loadsize :the load size to set
     </en>*/
    public void setLoadSize(int loadsize)
    {
        wLoadSize = loadsize;
    }

    /**<jp>
     * 荷姿を取得します。
     * @return 荷姿
     </jp>*/
    /**<en>
     * Retrieve the load size.
     * @return :the load size
     </en>*/
    public int getLoadSize()
    {
        return wLoadSize;
    }

    /**<jp>
     * 荷姿の名称を設定します。
     * @param nam 荷姿の名称
     </jp>*/
    /**<en>
     * Set the name of the load size.
     * @param nam :name of the load size
     </en>*/
    public void setLoadSizeName(String nam)
    {
        wLoadSizeName = nam;
    }

    /**<jp>
     * 荷姿の名称を取得します。
     * @return 荷姿の名称
     </jp>*/
    /**<en>
     * Retrieve name of the load size.
     * @return :name of the load size
     </en>*/
    public String getLoadSizeName()
    {
        return wLoadSizeName;
    }

    /**<jp>
     * 荷長をセットします。
     * @param length セットする荷長
     </jp>*/
    /**<en>
     * Set the load length.
     * @param length :the load length to set
     </en>*/
    public void setLength(int length)
    {
        wLength = length;
    }

    /**<jp>
     * 荷長を取得します。
     * @return 荷長
     </jp>*/
    /**<en>
     * Retrieve the load length.
     * @return :the load length
     </en>*/
    public int getLength()
    {
        return wLength;
    }

    /**<jp>
     * 荷高をセットします。
     * @param height セットする荷高
     </jp>*/
    /**<en>
     * Set the load height.
     * @param height :the load height to set
     </en>*/
    public void setHeight(int height)
    {
        wHeight = height;
    }

    /**<jp>
     * 荷高を取得します。
     * @return 荷高
     </jp>*/
    /**<en>
     * Retrieve the load height.
     * @return :the load height
     </en>*/
    public int getHeight()
    {
        return wHeight;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

