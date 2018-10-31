// $Id: SoftZoneParameter.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.Parameter;

/**<jp>
 * ゾーンパラメータを保持するクラスです。
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
public class SoftZoneParameter
        extends Parameter
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------
    /**<jp>
     * ゾーン番号
     </jp>*/
    /**<en>
     * Zone no.
     </en>*/
    protected int wZoneID;

    /**<jp>
     * ゾーン名称
     </jp>*/
    /**<en>
     * Zone name
     </en>*/
    protected String wZoneName;

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
    public SoftZoneParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ゾーン番号を設定します。
     * @param zid   設定するゾーン番号
     </jp>*/
    /**<en>
     * Set the zone no.
     * @param zid   :zone no. to set
     </en>*/
    public void setZoneID(int zid)
    {
        wZoneID = zid;
    }

    /**<jp>
     * ゾーン番号を取得します。
     * @return    ゾーン番号
     </jp>*/
    /**<en>
     * Retrieve the zone no.
     * @return    zone no.
     </en>*/
    public int getZoneID()
    {
        return wZoneID;
    }

    /**<jp>
     * ゾーン名称を設定します。
     * @param nm 設定するゾーン名称
     </jp>*/
    /**<en>
     * Set the zone name.
     * @param nm :zone name to set
     </en>*/
    public void setZoneName(String nm)
    {
        wZoneName = nm;
    }

    /**<jp>
     * ゾーン名称を取得します。
     * @return    ゾーン名称
     </jp>*/
    /**<en>
     * Retrieve the zone name.
     * @return    zone name
     </en>*/
    public String getZoneName()
    {
        return (wZoneName);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

