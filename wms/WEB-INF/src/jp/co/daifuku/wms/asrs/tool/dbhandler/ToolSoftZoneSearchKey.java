// $Id: ToolSoftZoneSearchKey.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してTEMP_SOFTZONEテーブルを検索し、TEMP_SOFTZONEクラスのインスタンスを生成するために使用するキークラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is a key class which is used to search TEMP_SOFTZONE table by using handler class
 * and generate the instance of TEMP_SOFTZONE class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolSoftZoneSearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String SOFTZONEID = "SOFT_ZONE_ID";

    private static final String SOFTZONENAME = "SOFT_ZONE_NAME";

    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            SOFTZONEID,
            SOFTZONENAME,
    };

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
        return ("$Revision: 4122 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * カラム定義を設定します。
     </jp>*/
    /**<en>
     * Define the column definition.
     </en>*/
    public ToolSoftZoneSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ソフトゾーンIDの検索値をセットします。
     * @param szid ソフトゾーンIDの検索値
     </jp>*/
    /**<en>
     * Set the search value of soft zone ID.
     * @param szid :the search value of soft zone ID
     </en>*/
    public void setSoftZoneID(int szid)
    {
        setValue(SOFTZONEID, szid);
    }

    /**<jp>
     * ソフトゾーンIDを検索値を取得します。
     * @return ソフトゾーンID
     </jp>*/
    /**<en>
     * Retrieve the search value of soft zone ID.
     * @return :soft zone
     </en>*/
    public int getSoftZoneID()
    {
        Integer intobj = (Integer)getValue(SOFTZONEID);
        return (intobj.intValue());
    }

    /**<jp>
     * ソフトゾーンIDのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of soft zone ID.
     * @param num  :prioriry in sort order
     * @param bool true: in ascending order, false: in descending order
     </en>*/
    public void setSoftZoneIDOrder(int num, boolean bool)
    {
        setOrder(SOFTZONEID, num, bool);
    }

    /**<jp>
     * ソフトゾーンIDのソート順を取得
     * @return ソフトゾーンID順
     </jp>*/
    /**<en>
     * Retrieve the sort order of soft zone ID.
     * @return :the order of soft zone ID
     </en>*/
    public int getSoftZoneIDOrder()
    {
        return (getOrder(SOFTZONEID));
    }

    /**<jp>
     * ソフトゾーン名称の検索値をセットします。
     * @param nam ソフトゾーン名称の検索値
     </jp>*/
    /**<en>
     * Set the search value of soft zone name.
     * @param nam :the search value of soft zone name
     </en>*/
    public void setSoftZoneName(String nam)
    {
        setValue(SOFTZONENAME, nam);
    }

    /**<jp>
     * ソフトゾーン名称を検索値を取得します。
     * @return ソフトゾーン名称
     </jp>*/
    /**<en>
     * Retrieve the search value of soft zone name.
     * @return :name of soft zone
     </en>*/
    public String getSoftZoneName()
    {
        return (String)getValue(SOFTZONENAME);
    }

    /**<jp>
     * ソフトゾーン名称のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of soft zone name.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setSoftZoneNameOrder(int num, boolean bool)
    {
        setOrder(SOFTZONENAME, num, bool);
    }

    /**<jp>
     * ソフトゾーン名称のソート順を取得
     * @return ソフトゾーン名称順
     </jp>*/
    /**<en>
     * Retrieve the sort order of gsoft zone name.
     * @return :the order of soft zone name
     </en>*/
    public int getSoftZoneNameOrder()
    {
        return (getOrder(SOFTZONENAME));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

