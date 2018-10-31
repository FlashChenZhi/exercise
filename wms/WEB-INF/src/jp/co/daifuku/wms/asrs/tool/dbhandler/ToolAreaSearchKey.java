// $Id: ToolAreaSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * DMArea表を検索するためのキーをセットするクラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
public class ToolAreaSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    private static final String AREANO = "AREA_NO";
    private static final String AREATYPE = "AREA_TYPE";
    private static final String WHSTATIONNO = "WHSTATION_NO";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        AREANO,
        AREATYPE,
        WHSTATIONNO
    };

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    public ToolAreaSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * AREANOの検索値をセット
     * @param no AREANO
     </jp>*/
    public void setAreaNo(String no)
    {
        setValue(AREANO, no);
    }

    /**<jp>
     * AREANOを取得
     * @return AREANO
     </jp>*/
    public String getAreaNo()
    {
        return (String)getValue(AREANO);
    }

    /**<jp>
     * AREANOのソート順セット
     * @param num ソート順
     * @param bool 昇順／降順の指定
     </jp>*/
    public void setAreaNoOrder(int num, boolean bool)
    {
        setOrder(AREANO, num, bool);
    }

    /**<jp>
     * AREANOのソート順を取得
     * @return ソート順
     </jp>*/
    public int getAreaNoOrder()
    {
        return (getOrder(AREANO));
    }

    /**<jp>
     * AREATYPEの検索値をセット
     * @param type AREATYPE
     </jp>*/
    public void setAreaType(String type)
    {
        setValue(AREATYPE, type);
    }

    /**<jp>
     * AREATYPEを取得
     * @return AREATYPE
     </jp>*/
    public String getAreaType()
    {
        return (String)getValue(AREATYPE);
    }

    /**<jp>
     * AREATYPEのソート順セット
     * @param num ソート順
     * @param bool 昇順／降順の指定
     </jp>*/
    public void setAreaTypeOrder(int num, boolean bool)
    {
        setOrder(AREATYPE, num, bool);
    }

    /**<jp>
     * AREATYPEのソート順を取得
     * @return ソート順
     </jp>*/
    public int getAreaTypeOrder()
    {
        return (getOrder(AREATYPE));
    }

    /**<jp>
     * WHSTATIONNOの検索値をセット
     * @param st WHSTATIONNO
     </jp>*/
    public void setWhStationNo(String st)
    {
        setValue(WHSTATIONNO, st);
    }

    /**<jp>
     * WHSTATIONNOを取得
     * @return WHSTATIONNO
     </jp>*/
    public String getWhStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     * @param num ソート順
     * @param bool 昇順／降順の指定
     </jp>*/
    public void setWhStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     * @return ソート順
     </jp>*/
    public int getWhStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

