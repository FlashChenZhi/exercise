//$Id: ItemNotFoundException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.exception;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 報告ファイルまたは環境情報に指定された項目が存在しない場合の例外処理を行います。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/24</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class ItemNotFoundException
        extends Exception
{

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * 報告ファイルまたは環境情報に指定された項目が存在しない場合の例外処理を行います。
     * 
     * @param columName 項目名
     */
    public ItemNotFoundException(String columName)
    {
        super("環境情報、または 報告ファイルに 項目 " + columName + "の情報がありません。 ");
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
