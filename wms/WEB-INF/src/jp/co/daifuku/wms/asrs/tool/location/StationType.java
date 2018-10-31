// $Id: StationType.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.common.MessageResource;

/**<jp>
 * ステーションNo.に対するステーション種別の情報を管理するための
 * クラスです。<BR>
 * ステーションは、固有の番号を持ちます。これは任意の英数字からなり
 * 文字列として扱われます。
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
 * This class is used to control the informatin of station types according to  
 * the station numbers.<BR>
 * Stations possess the unique numbers. These numbers consist of any alphanumerics and 
 * are handled as strings.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class StationType extends ToolEntity
{
    // Class fields --------------------------------------------------

    /**<jp>
     * ステーション番号の長さ
     </jp>*/
    /**<en>
     * StationNo Length
     </en>*/
    public static final int STATIONNO_LEN = 4 ;

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM ;

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーション番号を保持する
     </jp>*/
    /**<en>
     * Preserve the station no.
     </en>*/
    protected String wStationNo = "" ;

    /**<jp>
     * 対応するHandlerClassを保持する
     </jp>*/
    /**<en>
     * Preserve the corresponding HandlerClass.
     </en>*/
    protected String wClassName = "" ;

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
    /**<jp>
     * 新しい<CODE>StationType</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>StationType</CODE>.
     </en>*/
    public StationType()
    {
    }
    
    // Public methods ------------------------------------------------
    /**<jp>
     * ステーション番号を取得します。
     * @return    ステーション番号
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return    :station no.
     </en>*/
    public String getStationNo()
    {
        return (wStationNo) ;
    }

    /**<jp>
     * ステーション番号をセットします。
     * @param  arg  ステーション番号
     </jp>*/
    /**<en>
     * Set the station no.
     * @param  arg  : station no.
     </en>*/
    public void setStationNo(String arg)
    {
        wStationNo = arg;
    }

    /**<jp>
     * ハンドラクラスを取得します。
     * @return    ハンドラクラス
     </jp>*/
    /**<en>
     * Retrieve the handler class.
     * @return    handler class
     </en>*/
    public String getClassName()
    {
        return (wClassName) ;
    }
    
    /**<jp>
     * ハンドラクラスをセットします。
     * @param  arg  ハンドラクラス
     </jp>*/
    /**<en>
     * Set the handler class.
     * @param  arg  : handler class
     </en>*/
    public void setClassName(String arg)
    {
        wClassName = arg;
    }

    /**<jp>
     * 文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        try
        {
            buf.append("\nStation Number:" + wStationNo) ;
            buf.append("\nHandler Class:" + wClassName) ;
        }
        catch (Exception e)
        {
        }
        
        return (buf.toString()) ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

