// $Id: HardZone.java 4122 2009-04-10 10:58:38Z ota $
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
 * 棚のハードゾーンを管理するためのクラスです。
 * 入力された荷高等の情報より格納ゾーンを決定する運用です。
 * １つの棚に１つだけゾーンを定義出来ます。また、荷姿小→中→大のようにゾーン優先順位を持つことが出来ます。
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
 * This class is used to control the hard zones of locations.
 * Storage zone will be determined based on the entered data such as load height, etc.
 * It is possible to define just one zone per location. 
 * Also it is possible to keep the prioritized location order, e.g., small load -> medium load -> large.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class HardZone
        extends ToolEntity
{
    // Class fields --------------------------------------------------

    /**<jp>
     * 未設定のハードゾーンを表すフィールド
     </jp>*/
    /**<en>
     * Field of non-set up hard zone
     </en>*/
    public static final int UN_SETTING = 0;

    // Class variables -----------------------------------------------
    /**<jp>
     * ハードゾーン番号
     </jp>*/
    /**<en>
     * Hard zone no.
     </en>*/
    protected int wHardZoneID;

    /**<jp>
     * ハードゾーン名称
     </jp>*/
    /**<en>
     * Name of the hard zone
     </en>*/
    protected String wHardZoneName;

    /**<jp>
     * ステーションの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no. that the station belongs to
     </en>*/
    protected String wWhStationNo;

    /**<jp>
     * 荷高情報
     </jp>*/
    /**<en>
     * Load height
     </en>*/
    protected int wHeight = 0;

    /**<jp>
     * ハードゾーン運用の優先順位
     </jp>*/
    /**<en>
     * Priority in hard zone operation
     </en>*/
    protected String wPriority;

    /**<jp>
     * 開始バンク
     </jp>*/
    /**<en>
     * starting bank 
     </en>*/
    protected int wStartBankNo = 0;

    /**<jp>
     * 開始ベイ
     </jp>*/
    /**<en>
     * starting bay 
     </en>*/
    protected int wStartBayNo = 0;

    /**<jp>
     * 開始レベル
     </jp>*/
    /**<en>
     * starting level 
     </en>*/
    protected int wStartLevelNo = 0;

    /**<jp>
     * 終了バンク
     </jp>*/
    /**<en>
     * ending bank 
     </en>*/
    protected int wEndBankNo = 0;

    /**<jp>
     * 終了ベイ
     </jp>*/
    /**<en>
     * ending bay 
     </en>*/
    protected int wEndBayNo = 0;

    /**<jp>
     * 終了レベル
     </jp>*/
    /**<en>
     * ending level 
     </en>*/
    protected int wEndLevelNo = 0;

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
     * 新しい<CODE>HardZone</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>HardZone</CODE>.
     </en>*/
    public HardZone()
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * ハードゾーンIDを設定します。
     * @param hz ハードゾーンID
     </jp>*/
    /**<en>
     * Set the hard zone ID.
     * @param hz :hard zone ID
     </en>*/
    public void setHardZoneID(int hz)
    {
        wHardZoneID = hz;
    }

    /**<jp>
     * ハードゾーンIDを取得します。
     * @return ハードゾーンID
     </jp>*/
    /**<en>
     * Retrieve the hard zone ID.
     * @return :hard zone ID
     </en>*/
    public int getHardZoneID()
    {
        return wHardZoneID;
    }

    /**<jp>
     * ハードゾーンの名称を設定します。
     * @param nam ハードゾーンの名称
     </jp>*/
    /**<en>
     * Set the name of the hard zone.
     * @param nam :name of the hard zone
     </en>*/
    public void setHardZoneName(String nam)
    {
        wHardZoneName = nam;
    }

    /**<jp>
     * ハードゾーンの名称を取得します。
     * @return ハードゾーンの名称
     </jp>*/
    /**<en>
     * Retrieve name of the hard zone.
     * @return :name of the hard zone
     </en>*/
    public String getHardZoneName()
    {
        return wHardZoneName;
    }

    /**<jp>
     * このゾーンが属している倉庫Noを設定します。
     * @param whnum 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no. this zone belongs to.
     * @param whnum :warehouse no.
     </en>*/
    public void setWhStationNo(String whnum)
    {
        wWhStationNo = whnum;
    }

    /**<jp>
     * このゾーンが属している倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no. this zone belongs to.
     * @return :warehouse no.
     </en>*/
    public String getWhStationNo()
    {
        return wWhStationNo;
    }

    /**<jp>
     * ハードゾーンの荷高をセットします。
     * @param hgt セットする荷高
     </jp>*/
    /**<en>
     * Set the load height of the hard zone.
     * @param hgt :the load height to set
     </en>*/
    public void setHeight(int hgt)
    {
        wHeight = hgt;
    }

    /**<jp>
     * ハードゾーンの荷高を取得します。
     * @return 荷高
     </jp>*/
    /**<en>
     * Retrieve the load height of hard zone.
     * @return :the load height
     </en>*/
    public int getHeight()
    {
        return wHeight;
    }

    /**<jp>
     * ハードゾーン検索優先順序を設定します。
     * @param pri ハードゾーン検索優先順序
     </jp>*/
    /**<en>
     * Set the priority of hard zone search.
     * @param pri :the priority of hard zone search
     </en>*/
    public void setPriority(String pri)
    {
        wPriority = pri;
    }

    /**<jp>
     * ハードゾーン検索優先順序を取得します。
     * @return ハードゾーン検索優先順序
     </jp>*/
    /**<en>
     * Retrieve the priority of hard zone search.
     * @return :the priority of hard zone search
     </en>*/
    public String getPriority()
    {
        return wPriority;
    }

    /**<jp>
     * 開始バンクを設定します。
     * @param sbnk 開始バンク
     </jp>*/
    /**<en>
     * Set the startinging bank.
     * @param sbnk :starting bank.
     </en>*/
    public void setStartBankNo(int sbnk)
    {
        wStartBankNo = sbnk;
    }

    /**<jp>
     * 開始バンクを取得します。
     * @return 開始バンク
     </jp>*/
    /**<en>
     * Retrieve the starting bank.
     * @return :starting bank
     </en>*/
    public int getStartBankNo()
    {
        return wStartBankNo;
    }

    /**<jp>
     * 開始ベイを設定します。
     * @param sbay 開始ベイ
     </jp>*/
    /**<en>
     * Sest teh starting bay.
     * @param sbay :starting bay
     </en>*/
    public void setStartBayNo(int sbay)
    {
        wStartBayNo = sbay;
    }

    /**<jp>
     * 開始ベイを取得します。
     * @return 開始ベイ
     </jp>*/
    /**<en>
     * Retrieve the starting bay.
     * @return :starting bay
     </en>*/
    public int getStartBayNo()
    {
        return wStartBayNo;
    }

    /**<jp>
     * 開始レベルを設定します。
     * @param slvl 開始レベル
     </jp>*/
    /**<en>
     * Set the starting level.
     * @param slvl :starting level
     </en>*/
    public void setStartLevelNo(int slvl)
    {
        wStartLevelNo = slvl;
    }

    /**<jp>
     * 開始レベルを取得します。
     * @return 開始レベル
     </jp>*/
    /**<en>
     * Retrieve the starting level.
     * @return :starting level
     </en>*/
    public int getStartLevelNo()
    {
        return wStartLevelNo;
    }

    /**<jp>
     * 終了バンクを設定します。
     * @param ebnk 終了バンク
     </jp>*/
    /**<en>
     * Set the ending bank.
     * @param ebnk :the ending bank
     </en>*/
    public void setEndBankNo(int ebnk)
    {
        wEndBankNo = ebnk;
    }

    /**<jp>
     * 終了バンクを取得します。
     * @return 終了バンク
     </jp>*/
    /**<en>
     * Retrieve the ending bank.
     * @return :the ending bank
     </en>*/
    public int getEndBankNo()
    {
        return wEndBankNo;
    }

    /**<jp>
     * 終了ベイを設定します。
     * @param ebay 終了ベイ
     </jp>*/
    /**<en>
     * Set the ending bay.
     * @param ebay :the ending bay
     </en>*/
    public void setEndBayNo(int ebay)
    {
        wEndBayNo = ebay;
    }

    /**<jp>
     * 終了ベイを取得します。
     * @return 終了ベイ
     </jp>*/
    /**<en>
     * Retrieve the ending bay.
     * @return :the ending bay
     </en>*/
    public int getEndBayNo()
    {
        return wEndBayNo;
    }

    /**<jp>
     * 終了レベルを設定します。
     * @param elvl 終了レベル
     </jp>*/
    /**<en>
     * Set the ending level.
     * @param elvl :the ending level
     </en>*/
    public void setEndLevelNo(int elvl)
    {
        wEndLevelNo = elvl;
    }

    /**<jp>
     * 終了レベルを取得します。
     * @return 終了レベル
     </jp>*/
    /**<en>
     * Retrieve the ending level.
     * @return :the ending level
     </en>*/
    public int getEndLevelNo()
    {
        return wEndLevelNo;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

