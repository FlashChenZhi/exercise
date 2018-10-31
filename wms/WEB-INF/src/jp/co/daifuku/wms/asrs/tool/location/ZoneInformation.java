// $Id: ZoneInformation.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;

/**<jp>
 * ゾーンマスター情報を管理します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class controls the zone master information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ZoneInformation extends ToolEntity
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ゾーンの種別を表すフィールド（ハードゾーン）
     </jp>*/
    /**<en>
     * Field of zone type (hard zone)
     </en>*/
    public static final int HARD = 1;

    /**<jp>
     * ゾーンの種別を表すフィールド（ソフトゾーン）
     </jp>*/
    /**<en>
     * Field of zone type (soft zone)
     </en>*/
    public static final int SOFT = 2;


    // Class variables -----------------------------------------------
    /**<jp>
     * ゾーン番号
     </jp>*/
    /**<en>
     * zone no.
     </en>*/
    protected int wZoneID ;

    /**<jp>
     * ゾーン名称
     </jp>*/
    /**<en>
     * name of zone
     </en>*/
    protected String wZoneName ;

    /**<jp>
     * 運用種別
     </jp>*/
    /**<en>
     * operation type
     </en>*/
    protected int wType ;

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiters
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM ;

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
     * ゾーン番号を設定します。
     * @param zid   設定するゾーン番号
     </jp>*/
    /**<en>
     * Set the zone no.
     * @param zid   :zone no. to set
     </en>*/
    public void setZoneID(int zid)
    {
        wZoneID = zid ;
    }

    /**<jp>
     * ゾーン番号を取得します。
     * @return    ゾーン番号
     </jp>*/
    /**<en>
     * Retrieve the zone no.
     * @return    :zone no.
     </en>*/
    public int getZoneID()
    {
        return wZoneID ;
    }

    /**<jp>
     * ゾーン名称を設定します。
     * @param nm 設定するゾーン名称
     </jp>*/
    /**<en>
     * Set the name of zone.
     * @param nm :name of zone to set
     </en>*/
    public void setZoneName(String nm)
    {
        wZoneName = nm ;
    }

    /**<jp>
     * ゾーン名称を取得します。
     * @return    ゾーン名称
     </jp>*/
    /**<en>
     * Retrieve the name of zone.
     * @return    :name of zone
     </en>*/
    public String getZoneName()
    {
        return (wZoneName) ;
    }

    /**<jp>
     * 運用種別を設定します。
     * @param type 運用種別
     * @throws InvalidStatusException typeの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the operation type.
     * @param type :operation type
     * @throws InvalidStatusException :Notifies if contents of type is invalid.
     </en>*/
    public void setType(int type) throws InvalidStatusException
    {
        //<jp> 運用種別のチェック</jp>
        //<en> Check the operation type.</en>
        switch (type)
        {
            //<jp> 正しい運用種別の一覧</jp>
            //<en> List of correct operation type</en>
            case HARD:
            case SOFT:
                break ;
                
            //<jp> 正しくない運用種別を設定しようとした場合は例外を発生させ、運用種別の変更はしない</jp>
            //<en> It lets occur the exception if incorrect operation type was to set. It will not alter the operation type.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1] ;
                tObj[0] = "TYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0])) ;
        }
        
        //<jp> 運用種別の変更</jp>
        //<en> Modify the operation type.</en>
        wType = type ;
    }

    /**<jp>
     * 運用種別を取得します。
     * @return 運用種別
     </jp>*/
    /**<en>
     * Retrieve the operation type.
     * @return :operation type
     </en>*/
    public int getType()
    {
        return (wType) ;
    }

    /**<jp>
     * ZoneInformationの文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation of ZoneInformation.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        buf.append("\nZoneID:" + Integer.toString(wZoneID)) ;
        buf.append("\nZoneName:" + wZoneName) ;
        buf.append("\nType:" + Integer.toString(wType)) ;
        return buf.toString() ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

