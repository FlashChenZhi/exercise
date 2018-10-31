// $Id: ToolEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.MessageResource;

/**<jp>
 * エンティティ系オブジェクトの親となるクラスです。インスタンスを保管・取得するためのハンドラを保持します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is a parent class of entity system object. It preserves handlers 
 * for the storage and retrieval of instances.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolEntity extends Object
{

    // Class fields --------------------------------------------------
    /**<jp> メッセージの作成に用いるデリミタ </jp>*/
    /**<en> Delimiter used in messages creation </en>*/
    protected final String wDelim = MessageResource.DELIM;

    // Class variables -----------------------------------------------
    /**<jp>
     * インスタンス・ハンドラ
     </jp>*/
    /**<en>
     * Instance handler
     </en>*/
    protected ToolEntityHandler wHandler = null ;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * インスタンスを保管・取得するためのハンドラを設定します。
     * @param hndler エンティティ・ハンドラ
     </jp>*/
    /**<en>
     * Set the handler in order to store and retrieve the instance.
     * @param hndler :entity handler
     </en>*/
    public void setHandler(ToolEntityHandler hndler)
    {
        wHandler = hndler ;
    }

    /**<jp>
     * インスタンスを保管・取得するためのハンドラを取得します。
     * @return エンティティ・ハンドラ
     </jp>*/
    /**<en>
     * Retrieves the handler in order to store and retrieve the instance.
     * @return :entity handler
     </en>*/
    public ToolEntityHandler getHandler()
    {
        return (wHandler) ;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class


