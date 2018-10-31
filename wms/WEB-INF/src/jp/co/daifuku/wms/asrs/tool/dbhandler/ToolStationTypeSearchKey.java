// $Id: ToolStationTypeSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**<jp>
 * ステーション番号をキーとして、ステーションタイプ検索を行う場合に使われる、キー指定のためのクラス。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to specify keys when searching the station type based on the station no.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolStationTypeSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String STATIONNO        = "STATION_NO";
    private static final String CLASSNAME        = "CLASS_NAME";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        STATIONNO,
        CLASSNAME
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
        return ("$Revision: 87 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public ToolStationTypeSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * STATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     </en>*/
    public void setStationNo(String stnum)
    {
        setValue(STATIONNO, stnum);
    }

    /**<jp>
     * STATIONNOを取得
     </jp>*/
    /**<en>
     * Retrieve the STATIONNO.
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * STATIONNOの検索値をセット
     * 配列で渡されたSTATIONNO同士はORで結合されます。
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * Each station no. passed as arrays will be connected by placing OR inbetween.
     </en>*/
    public void setStationNo(String[] stnums)
    {
        setValue(STATIONNO, stnums);
    }

    /**<jp>
     * STATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of STATIONNO.
     </en>*/
    public void setStationNoOrder(int num, boolean bool)
    {
        setOrder(STATIONNO, num, bool);
    }

    /**<jp>
     * STATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve hte sort ordr of STATIONNO.
     </en>*/
    public int getStationNorOrder()
    {
        return (getOrder(STATIONNO));
    }

    /**<jp>
     * HANDLERCLASSの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of HANDLERCLASS.
     </en>*/
    public void setClassName(String hdcl)
    {
        setValue(CLASSNAME, hdcl);
    }
    
    /**<jp>
     * HANDLERCLASSの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of HANDLERCLASS.
     </en>*/
    public void setClassName(String[] hdcl)
    {
        setValue(CLASSNAME, hdcl);
    }

    /**<jp>
     * HANDLERCLASSを取得
     </jp>*/
    /**<en>
     * Retrieve the HANDLERCLASS.
     </en>*/
    public String getClassName()
    {
        return (String)getValue(CLASSNAME);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

