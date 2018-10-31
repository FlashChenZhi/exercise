// $Id: AccessNgShelf.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * アクセス不可棚を管理するためのクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class controls the width of locations.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class AccessNgShelf
        extends ToolEntity
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 倉庫STNo
     </jp>*/
    /**<en>
     * Warehouse station no.
     </en>*/
    protected String wWareHouseStationNo;

    /**<jp>
     * バンク
     </jp>*/
    /**<en>
     * Bank no. 
     </en>*/
    protected int wBankNo;

    /**<jp>
     * ベイ
     </jp>*/
    /**<en>
     * Bay no. 
     </en>*/
    protected int wBayNo;

    /**<jp>
     * レベル
     </jp>*/
    /**<en>
     * Level no. 
     </en>*/
    protected int wLevelNo;

    /**<jp>
     * 荷幅
     </jp>*/
    /**<en>
     * Load width
     </en>*/
    protected int wWidth;

    /**<jp>
     * 範囲指定の為の開始アドレス。
     </jp>*/
    /**<en>
     * Starting address which specifies the range. 
     </en>*/
    protected int wStartAddressNo;

    /**<jp>
     * 範囲指定の為の終了アドレス。
     </jp>*/
    /**<en>
     * Ending address which specifies the range. 
     </en>*/
    protected int wEndAddressNo;

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM;

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
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>Width</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>Width</CODE>.
     </en>*/
    public AccessNgShelf()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 倉庫STNoを設定します(例：9000)。
     * @param wh 倉庫STNo
     </jp>*/
    /**<en>
     * Set the warehouse station no.
     * @param whnum warehouse station no.
     </en>*/
    public void setWareHouseStationNo(String whnum)
    {
        wWareHouseStationNo = whnum;
    }

    /**<jp>
     * 倉庫STNoを取得します(例：9000)。
     * @return 倉庫STNo
     </jp>*/
    /**<en>
     * Retrieve the warehouse station no.
     * @return warehouse station no.
     </en>*/
    public String getWareHouseStationNo()
    {
        return wWareHouseStationNo;
    }

    /**<jp>
     * バンクを設定します。
     * @param bank バンク
     </jp>*/
    /**<en>
     * Set the bank.
     * @param bank :bank
     </en>*/
    public void setBankNo(int bank)
    {
        wBankNo = bank;
    }

    /**<jp>
     * バンクを取得します。
     * @return バンク
     </jp>*/
    /**<en>
     * Retrieve the bank.
     * @return :bank
     </en>*/
    public int getBankNo()
    {
        return wBankNo;
    }

    /**<jp>
     * ベイを設定します。
     * @param bay ベイ
     </jp>*/
    /**<en>
     * Set the bay.
     * @param bay :the bay
     </en>*/
    public void setBayNo(int bay)
    {
        wBayNo = bay;
    }

    /**<jp>
     * ベイを取得します。
     * @return ベイ
     </jp>*/
    /**<en>
     * Retrieve the bay.
     * @return :the bay 
     </en>*/
    public int getBayNo()
    {
        return wBayNo;
    }

    /**<jp>
     * レベルを設定します。
     * @param level レベル
     </jp>*/
    /**<en>
     * Set the level.
     * @param level :level
     </en>*/
    public void setLevelNo(int level)
    {
        wLevelNo = level;
    }

    /**<jp>
     * レベルを取得します。
     * @return レベル
     </jp>*/
    /**<en>
     * Retrieve the level.
     * @return :level
     </en>*/
    public int getLevelNo()
    {
        return wLevelNo;
    }

    /**<jp>
     * 荷幅を設定します。
     * @param width   設定する荷幅
     </jp>*/
    /**<en>
     * Set hte width.
     * @param width   :load width to set
     </en>*/
    public void setWidth(int width)
    {
        wWidth = width;
    }

    /**<jp>
     * 荷幅を取得します。
     * @return    荷幅
     </jp>*/
    /**<en>
     * Retrieve the load width.
     * @return    :load width.
     </en>*/
    public int getWidth()
    {
        return wWidth;
    }

    /**<jp>
     * 開始アドレスを設定します。
     * @param saddress 開始アドレス
     </jp>*/
    /**<en>
     * Set the starting address.
     * @param saddress :starting address
     </en>*/
    public void setStartAddressNo(int saddress)
    {
        wStartAddressNo = saddress;
    }

    /**<jp>
     * 開始アドレスを取得します。
     * @return 開始アドレス
     </jp>*/
    /**<en>
     * Retrieve the starting address.
     * @return :starting address
     </en>*/
    public int getStartAddressNo()
    {
        return wStartAddressNo;
    }

    /**<jp>
     * 終了アドレスを設定します。
     * @param eaddress 終了アドレス
     </jp>*/
    /**<en>
     * Set teh ending address.
     * @param eaddress :ending address
     </en>*/
    public void setEndAddressNo(int eaddress)
    {
        wEndAddressNo = eaddress;
    }

    /**<jp>
     * 終了アドレスを取得します。
     * @return 終了アドレス
     </jp>*/
    /**<en>
     * Retrieve the ending address.
     * @return :ending address
     </en>*/
    public int getEndAddressNo()
    {
        return wEndAddressNo;
    }

    /**<jp>
     * Widthの文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation of Width.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100);
        buf.append("\nWarehouseStationNo:" + wWareHouseStationNo);
        buf.append("\nWidth:" + Integer.toString(wWidth));
        buf.append("\nBank:" + Integer.toString(wBankNo));
        buf.append("\nBay:" + Integer.toString(wBayNo));
        buf.append("\nLevel:" + Integer.toString(wLevelNo));
        buf.append("\nStartBank:" + Integer.toString(wStartAddressNo));
        buf.append("\nEndBank:" + Integer.toString(wEndAddressNo));
        return buf.toString();
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

