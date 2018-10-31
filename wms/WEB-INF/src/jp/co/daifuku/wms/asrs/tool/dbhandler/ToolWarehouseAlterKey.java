// $Id: ToolWarehouseAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * WAREHOUSEテーブルの更新を行うための情報を定義したクラスです。
 * WareHouseHandlerクラスがWAREHOUSEテーブルの更新を行う場合に使用されます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Defined in this class is hte information to update the WAREHOUSE table.
 * This is used when WareHouseHandler class updates the WAREHOUSE table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolWarehouseAlterKey extends ToolSQLAlterKey
{
    // Class fields --------------------------------------------------

    //<jp> 検索条件および更新対象となる可能性のあるカラムを定義します。</jp>
    //<en> Define here the columns which could be search conditions or the target data of update</en>
    private static final String STATION_NO             = "STATION_NO";
    private static final String LAST_USED_STATION_NO    = "LAST_USED_STATION_NO";

    // Class variables -----------------------------------------------

    //<jp> 宣言されたカラム名を定義した変数を配列にセットします。</jp>
    //<en> Set the variable, defined with the declared colunm, in the array. </en>
    private static final String[] Columns =
    {
        STATION_NO,
        LAST_USED_STATION_NO
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
     * Conduct the initial setting of declared table column.
     </en>*/
    public ToolWarehouseAlterKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    //<jp>======================<更新条件設定メソッド>====================</jp>
    //<en>============<Method of update condition settings>===============</en>

    /**<jp>
     * STATIONNOの検索値をセットします。
     * @param 検索ステーションNo
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * @param :search station no.
     </en>*/
    public void setStationNo(String stnum)
    {
        setValue(STATION_NO, stnum);
    }

    /**<jp>
     * STATIONNOをの検索値を取得します。
     * @return 検索ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the search value of STATIONNO.
     * @return :search station no.
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATION_NO);
    }

    //<jp>======================<更新値設定メソッド>======================</jp>
    //<en>============<Method of update value settings>==================</en>

    /**<jp>
     * LASTUSEDSTATIONNOの更新値をセットします。
     * @param LASTUSEDSTATIONNOの更新値
     </jp>*/
    /**<en>
     * Set the update value of LASTUSEDSTATIONNO.
     * @param :update value of LASTUSEDSTATIONNO
     </en>*/
    public void updateLastUsedStationNo(String lst)
    {
        setUpdValue(LAST_USED_STATION_NO, lst);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

