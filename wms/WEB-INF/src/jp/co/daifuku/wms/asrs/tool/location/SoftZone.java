// $Id: SoftZone.java 4122 2009-04-10 10:58:38Z ota $
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
 * 棚のソフトゾーンを管理するためのクラスです。
 * 入力された分類、特性等の情報より格納ゾーンを決定する運用です。
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
 * This class is used to control the soft zones of locations.
 * Storage zone will be determined based on the entered data such as load height, etc.
 * It is possible to define just one zone per location. 
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
public class SoftZone
        extends ToolEntity
{
    // Class fields --------------------------------------------------

    /**<jp>
     * 未設定のソフトゾーンを表すフィールド
     </jp>*/
    /**<en>
     * Field of non-set up soft zone
     </en>*/
    public static final int UN_SETTING = 0;

    // Class variables -----------------------------------------------
    /**<jp>
     * ソフトゾーン番号
     </jp>*/
    /**<en>
     * Soft zone no.
     </en>*/
    protected int wSoftZoneID;

    /**<jp>
     * ソフトゾーン名称
     </jp>*/
    /**<en>
     * Name of the soft zone
     </en>*/
    protected String wSoftZoneName;

    /**<jp>
     * 倉庫No
     </jp>*/
    /**<en>
     * Warehouse no.
     </en>*/
    protected String wWhStationNo;

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
     * 新しい<CODE>SoftZone</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>SoftZone</CODE>.
     </en>*/
    public SoftZone()
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * ソフトゾーンIDを設定します。
     * @param sz ソフトゾーンID
     </jp>*/
    /**<en>
     * Set the soft zone ID.
     * @param sz :soft zone ID
     </en>*/
    public void setSoftZoneID(int sz)
    {
        wSoftZoneID = sz;
    }

    /**<jp>
     * ソフトゾーンIDを取得します。
     * @return ソフトゾーンID
     </jp>*/
    /**<en>
     * Retrieve the soft zone ID.
     * @return :soft zone ID
     </en>*/
    public int getSoftZoneID()
    {
        return wSoftZoneID;
    }

    /**<jp>
     * ソフトゾーンの名称を設定します。
     * @param nam ソフトゾーンの名称
     </jp>*/
    /**<en>
     * Set the name of the soft zone.
     * @param nam :name of the soft zone
     </en>*/
    public void setSoftZoneName(String nam)
    {
        wSoftZoneName = nam;
    }

    /**<jp>
     * ソフトゾーンの名称を取得します。
     * @return ソフトゾーンの名称
     </jp>*/
    /**<en>
     * Retrieve name of the soft zone.
     * @return :name of the soft zone
     </en>*/
    public String getSoftZoneName()
    {
        return wSoftZoneName;
    }

    /**<jp>
     * 倉庫Noを設定します。
     * @param whnum 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no.
     * @param whnum :warehouse no.
     </en>*/
    public void setWhStationNo(String whnum)
    {
        wWhStationNo = whnum;
    }

    /**<jp>
     * 倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no.
     * @return :warehouse no.
     </en>*/
    public String getWhStationNo()
    {
        return wWhStationNo;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

