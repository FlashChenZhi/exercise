// $Id: LoadSizeParameter.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.wms.asrs.tool.location.LoadSize;

/**<jp>
 * 荷姿パラメータを保持するクラスです。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class preserves the zone parameters.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class LoadSizeParameter
        extends Parameter
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------

    /**<jp>
     * LoadSizeインスタンス
     </jp>*/
    /**<en>
     * LoadSize instance
     </en>*/
    private LoadSize wInstance;

    /**<jp>
     * 荷姿
     </jp>*/
    /**<en>
     * Load size
     </en>*/
    protected int wLoadSize;

    /**<jp>
     * 荷姿名称
     </jp>*/
    /**<en>
     * Load size name
     </en>*/
    protected String wLoadSizeName;

    /**<jp>
     * 荷長
     </jp>*/
    /**<en>
     * Load length
     </en>*/
    protected int wLength;

    /**<jp>
     * 荷高
     </jp>*/
    /**<en>
     * Load height
     </en>*/
    protected int wHeight;

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
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public LoadSizeParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * LoadSizeインスタンスをセットします。
     * @param objセットするインスタンス
     </jp>*/
    /**<en>
     * Set the LoadSize instance.
     * @param obj :instance to set
     </en>*/
    public void setInstance(LoadSize obj)
    {
        wInstance = obj;
    }

    /**<jp>
     * LoadSizeインスタンスを取得します。
     * @return LoadSizeインスタンス
     </jp>*/
    /**<en>
     * Retrieve the LoadSize instance.
     * @return LoadSize instance
     </en>*/
    public LoadSize getInstance()
    {
        return wInstance;
    }

    /**<jp>
     * 荷姿を設定します。
     * @param loadsize 設定する荷姿
     </jp>*/
    /**<en>
     * Set the load size.
     * @param loadsize :load size to set
     </en>*/
    public void setLoadSize(int loadsize)
    {
        wLoadSize = loadsize;
    }

    /**<jp>
     * 荷姿を取得します。
     * @return    荷姿
     </jp>*/
    /**<en>
     * Retrieve the load size.
     * @return    load size
     </en>*/
    public int getLoadSize()
    {
        return (wLoadSize);
    }

    /**<jp>
     * 荷姿名称を設定します。
     * @param nm 設定する荷姿名称
     </jp>*/
    /**<en>
     * Set the load size name.
     * @param nm :load size name to set
     </en>*/
    public void setLoadSizeName(String nm)
    {
        wLoadSizeName = nm;
    }

    /**<jp>
     * 荷姿名称を取得します。
     * @return    荷姿名称
     </jp>*/
    /**<en>
     * Retrieve the load size name.
     * @return    load size name
     </en>*/
    public String getLoadSizeName()
    {
        return (wLoadSizeName);
    }

    /**<jp>
     * 荷長を設定します。
     * @param length 設定する荷長
     </jp>*/
    /**<en>
     * Set the load length.
     * @param ht :load length to set
     </en>*/
    public void setLength(int length)
    {
        wLength = length;
    }

    /**<jp>
     * 荷長を取得します。
     * @return    荷長
     </jp>*/
    /**<en>
     * Retrieve the load length.
     * @return    load length
     </en>*/
    public int getLength()
    {
        return (wLength);
    }

    /**<jp>
     * 荷高を設定します。
     * @param height 設定する荷高
     </jp>*/
    /**<en>
     * Set the load height.
     * @param ht :load height to set
     </en>*/
    public void setHeight(int height)
    {
        wHeight = height;
    }

    /**<jp>
     * 荷高を取得します。
     * @return    荷高
     </jp>*/
    /**<en>
     * Retrieve the load height.
     * @return    load height
     </en>*/
    public int getHeight()
    {
        return (wHeight);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

