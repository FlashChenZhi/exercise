// $Id: ToolGroupControllerSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してCONTROLLERNUMBERテーブルを検索し、CONTROLLERNUMBERクラスのインスタンスを生成するために使用するキークラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is a key class which is used to search CONTROLLERNUMBER table using the handler class
 * and to generate the instance of CONTROLLERNUMBER class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolGroupControllerSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or which may be sorted. </en>
    private static final String CONTROLLERNO = "CONTROLLER_NO";
    private static final String IPADDRESS    = "IPADDRESS";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        CONTROLLERNO,
        IPADDRESS
    };

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the veriosn of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * カラム定義を設定します。
     </jp>*/
    /**<en>
     * Set teh column definition.
     </en>*/
    public ToolGroupControllerSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * CONTROLLERNOの検索値をセットします。
     * @param 検索対象となるAGCNo.
     </jp>*/
    /**<en>
     * Set the search value of CONTROLLERNO.
     * @param agcnum :AGCNo. which will be the target data of search
     </en>*/
    public void setControllerNo(int agcnum)
    {
        setValue(CONTROLLERNO, agcnum);
    }

    /**<jp>
     * CONTROLLERNOの検索値を取得します。
     * @return AGCNo.
     </jp>*/
    /**<en>
     * Retrieve the search value of CONTROLLERNO.
     * @return AGCNo.
     </en>*/
    public int getControllerNo()
    {
        Integer intobj = (Integer)getValue(CONTROLLERNO);
        return (intobj.intValue());
    }

    /**<jp>
     * CONTROLLERNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of CONTROLLERNO.
     </en>*/
    public void setControllerNoOrder(int num, boolean bool)
    {
        setOrder(CONTROLLERNO, num, bool);
    }

    /**<jp>
     * CONTROLLERNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of CONTROLLERNO.
     </en>*/
    public int getControllerNoOrder()
    {
        return (getOrder(CONTROLLERNO));
    }

    /**<jp>
     * IPADDRESSの検索値をセットします。
     * @param ipaddress 検索するIPアドレス
     </jp>*/
    /**<en>
     * Set the search value of IPADDRESS.
     * @param ipaddress :IP address to be searched
     </en>*/
    public void setIPAddress(String ipaddress)
    {
        setValue(IPADDRESS, ipaddress);
    }

    /**<jp>
     * IPADDRESSを検索値を取得します。
     * @return IPアドレス
     </jp>*/
    /**<en>
     * Retrieve the search value of IPADDRESS.
     * @return :IP address
     </en>*/
    public String getIPAddress()
    {
        return (String)getValue(IPADDRESS);
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

