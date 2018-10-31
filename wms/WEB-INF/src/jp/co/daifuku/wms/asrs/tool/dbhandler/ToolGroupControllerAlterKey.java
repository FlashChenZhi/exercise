// $Id: ToolGroupControllerAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * GROUPCONTROLLERテーブルの更新を行うための情報を定義したクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/04/10</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class defines the information which will be used to update the GROUPCONTROLLER table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/04/10</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolGroupControllerAlterKey extends ToolSQLAlterKey
{
    // Class fields --------------------------------------------------
    //<jp> 検索条件および更新対象となる可能性のあるカラムを定義します。</jp>
    //<en> Define here the columns which could be search conditions or the target data of update. </en>
    private static final String CONTROLLERNO  = "CONTROLLER_NO";
    private static final String STATUSFLAG  = "STATUS_FLAG";
    private static final String IPADDRESS  = "IPADDRESS";
    private static final String PORT  = "PORT";
    
    // Class variables -----------------------------------------------
    //<jp> 宣言されたカラム名を定義した変数を配列にセットします。</jp>
    //<en> Set the variable defined with declared column to the array.</en>
    private static final String[] Columns =
    {
        CONTROLLERNO,
        STATUSFLAG,
        IPADDRESS,
        PORT
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
     * 宣言されたテーブルカラムの初期設定を行います。
     </jp>*/
    /**<en>
     * Initialize the declared table column.
     </en>*/
    public ToolGroupControllerAlterKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    //<jp>======================<更新条件設定メソッド>====================</jp>
    //<en>============<Method of update condition settings>===============</en>
    /**<jp>
     * CONTROLLERNOの検索値をセットします。
     * @param CONTROLLERNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of CONTROLLERNO.
     * @param no :the search value of CONTROLLERNO
     </en>*/
    public void setControllerNo(int no)
    {
        setValue(CONTROLLERNO, no);
    }

    //<jp>======================<更新値設定メソッド>======================</jp>
    //<en>========<Method of update value settings>================</en>
    /**<jp>
     * STATUSの更新値をセットします。
     * @param STATUSの更新値
     </jp>*/
    /**<en>
     * Set the update value of STATUSFLAG.
     * @param status :update value of STATUSFLAG
     </en>*/
    public void updateStatusFlag(String status)
    {
        setUpdValue(STATUSFLAG, status);
    }
    
    /**<jp>
     * IPADDRESSの更新値をセットします。
     * @param IPADDRESSの更新値
     </jp>*/
    /**<en>
     * Set the update value of IPADDRESS.
     * @param ipaddress :update value of IPADDRESS
     </en>*/
    public void updateIPAddress(String ipaddress)
    {
        setUpdValue(IPADDRESS, ipaddress);
    }
    
    /**<jp>
     * PORTの更新値をセットします。
     * @param PORTの更新値
     </jp>*/
    /**<en>
     * Set the update value of PORT.
     * @param port :update value of PORT
     </en>*/
    public void updatePort(String port)
    {
        setUpdValue(PORT, port);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

