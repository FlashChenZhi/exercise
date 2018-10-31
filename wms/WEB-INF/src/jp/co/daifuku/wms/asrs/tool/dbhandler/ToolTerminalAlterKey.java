// $Id: ToolTerminalAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * TERMINALテーブルの更新を行うための情報を定義したクラスです。
 * ItemHandlerでテーブル更新を行う場合に使用されます。
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
 * Defined in this class is the information to update the TERMINAL table.
 * It will be used when updating table by ItemHandler.
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
public class ToolTerminalAlterKey extends ToolSQLAlterKey
{
    // Class fields --------------------------------------------------
    //<jp> 検索条件および更新対象となる可能性のあるカラムを定義します。</jp>
    //<en> Define here the columns which could be search conditions or the target data of update.</en>
    private static final String TERMINALNUMBER  = "TERMINAL.TERMINALNUMBER";
    private static final String PRINTERNAME        = "TERMINAL.PRINTERNAME";
    
    // Class variables -----------------------------------------------
    //<jp> 宣言されたカラム名を定義した変数を配列にセットします。</jp>
    //<en> Set the variable, defined with the declared colunm, in the array. </en>
    private static final String[] Columns =
    {
        TERMINALNUMBER,
        PRINTERNAME
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
    public ToolTerminalAlterKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    //<jp>======================<更新条件設定メソッド>====================</jp>
    //<en>=============<Method of update condition settings>==============</en>
    /**<jp>
     * TERMINALNUMBERの検索値をセットします。
     * @param TERMINALNUMBERの検索値
     </jp>*/
    /**<en>
     * Set the search value of TERMINALNUMBER.
     * @param :search value of TERMINALNUMBER
     </en>*/
    public void setTerminalNumber(String no)
    {
        setValue(TERMINALNUMBER, no);
    }

    /**<jp>
     * TERMINALNUMBERを取得します。
     * @return TERMINALNUMBER
     </jp>*/
    /**<en>
     * Retrieve the TERMINALNUMBER.
     * @return TERMINALNUMBER
     </en>*/
    public String getTerminalNumber()
    {
        return (String)getValue(TERMINALNUMBER);
    }

    //<jp>======================<更新値設定メソッド>======================</jp>
    //<en>==============<Method of update value settings>==================</en>

    /**<jp>
     * PRINTERNAMEの更新値をセットします。
     * @param PRINTERNAMEの更新値
     </jp>*/
    /**<en>
     * Set the update value of PRINTERNAME.
     * @param :update value of PRINTERNAME
     </en>*/
    public void updatePrinterName(String printer)
    {
        setUpdValue(PRINTERNAME, printer);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

