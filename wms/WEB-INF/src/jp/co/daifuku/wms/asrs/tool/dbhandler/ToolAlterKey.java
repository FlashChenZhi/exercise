// $Id: ToolAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;

/**<jp>
 * 保管情報を変更する場合に指定するキー情報の為のインターフェースです。
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
 * This is an interface for the key information which will be specified when modifing the 
 * stored information.
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
public interface ToolAlterKey
{
    // Public methods ------------------------------------------------
    /**<jp>
     * 指定されたカラムに文字型の検索値をセットします。
     </jp>*/
    /**<en>
     * Set the string type search value to the specified column.
     </en>*/
    public void setValue(String column, String value);

    /**<jp>
     * 指定されたカラムに数値型の検索値をセットします。
     </jp>*/
    /**<en>
     * Set the numeric type search value to the specified column.
     </en>*/
    public void setValue(String column, int intval);

    /**<jp>
     * 指定されたカラムに日付型の検索値をセットします。
     </jp>*/
    /**<en>
     * Set the date format search value to the specified column.
     </en>*/
    public void setValue(String column, Date dtval);

    /**<jp>
     * 指定されたカラムの検索値を取得します。
     </jp>*/
    /**<en>
     * Retrieve the search value of the specified column.
     </en>*/
    public Object getValue(String column);

    /**<jp>
     * 指定されたカラムに文字型の更新値をセットします。
     </jp>*/
    /**<en>
     * Set the string type update value to the specified column.
     </en>*/
    public void setUpdValue(String column, String value);

    /**<jp>
     * 指定されたカラムに数値型の更新値をセットします。
     </jp>*/
    /**<en>
     * Set the numeric update value to the specified column.
     </en>*/
    public void setUpdValue(String column, int intval);

    /**<jp>
     * 設定されたキーより条件文の生成を行います。ハンドラ側でこの内容を元に検索を行います。
     * @param tablename 変更対象となるテーブル名
     </jp>*/
    /**<en>
     * Generate the condition sentence based on the set key. 
     * For handler to conduct search based on the contents.
     * @param tablename :name of target table to modify
     </en>*/
    public String ReferenceCondition(String tablename);

    /**<jp>
     * 設定されたキーより更新文の生成を行います。ハンドラ側でこの内容を元に更新を行います。
     * @param tablename 変更対象となるテーブル名
     </jp>*/
    /**<en>
     * Generate the update sentence based on the set key.
     * For handler to conduct update based on the contents.
     * @param tablename :name of target table to update
     </en>*/
    public String ModifyContents(String tablename);


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

