// $Id: ToolSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 保管情報を取得する場合に指定するキー情報の為のインターフェースです。
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
 * This is an interface for the key information which will be specified
 * when retrieving the stored information.
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
public interface ToolSearchKey
{
    // Public methods ------------------------------------------------

    //<jp> 指定されたカラムに文字型の検索値をセットします。</jp>
    //<en> Set the string type search value in the specified column.</en>
    public void setValue(String column, String value);

    //<jp> 指定されたカラムに数値型の検索値をセットします。</jp>
    //<en> Set the numeric type search value in the specified column.</en>
    public void setValue(String column, int intval);

    //<jp> 指定されたカラムの検索値を取得します。</jp>
    //<en> Retrieve the search value of the specified column.</en>
    public Object getValue(String column);

    //<jp> 指定されたカラムにソート順をセットします。</jp>
    //<en> Set the sort order to the specified column.</en>
    public void setOrder(String column, int num, boolean bool);

    //<jp> 指定されたカラムのソート順を取得します。</jp>
    //<en> Retrieve the sort order of the specified column.</en>
    public int getOrder(String column);

    //<jp> 設定されたキーより条件文の生成を行います。ハンドラ側でこの内容を元に検索を行います。</jp>
    //<en> Generate the conditional statement according to the set key.</en>
    //<en> The handler will conduct search based on its contents.</en>
    public String ReferenceCondition();

    //<jp> 設定されたキーよりソート順の生成を行います。ハンドラ側でこの内容を元にソートを行います。</jp>
    //<en> Generate the sort order according to the specified key.</en>
    //<en> The handler will conduct search based on its contents.</en>
    public String SortCondition();

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

