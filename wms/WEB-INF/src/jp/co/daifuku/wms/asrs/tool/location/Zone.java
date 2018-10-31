// $Id: Zone.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.StringUtil;

/**<jp>
 * 棚のゾーンを管理するためのクラスです。
 * ゾーンは、倉庫内の棚ごとに割り当てられますゾーンの管理方式には以下のものがあります。
 * ゾーン管理方式の識別はインスタンス内のゾーン種別に保持されます。
 * 1.ソフトゾーン
 *   商品マスタのゾーン情報をもとに格納ゾーンを決定する運用です。
 *   ゾーンの範囲（バンク、ベイ、レベル）を持ちます。１つの棚に複数のゾーン範囲が含める事も可能です。
 * 2.ハードゾーン
 *   入力された荷高等の情報より格納ゾーンを決定する運用です。
 *   １つの棚に１つだけゾーンを定義出来ます。また、荷姿小→中→大のようにゾーン優先順位を持つことが出来ます。
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
 * This class controls the zone of locations.
 * Zone will be assigned to each shelf of the warehouse. Zone information is managed as follows.
 * Distinctions of zone control method are preserved in zone type of the instance.
 * 1: Soft zone
 *   Storage zone will be determined based on the zone information in article name master.
 *   The range of the zone (bank, bay and level) is preserved. 
 *   Also it is possible to include more than one zone range in one location.
 * 2: Hard zone
 *   Storage zone will be determined based on teh entered data of load height, etc.
 *   Also it is possible to keep the prioritized zones d.g. small load size -> medium load size -> large.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Zone extends ZoneInformation
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 空棚検索時の棚検索方向を表すフィールド（ＨＰ手前から棚検索）
     </jp>*/
    /**<en>
     * Field of search direction at the empty location search  (from HP front)
     </en>*/
    public static final int HP_FRONT = 1;

    /**<jp>
     * 空棚検索時の棚検索方向を表すフィールド（ＨＰ下段から棚検索）
     </jp>*/
    /**<en>
     * Field of search direction at the empty location search (from HP lower level)
     </en>*/
    public static final int HP_LOWER = 2;

    /**<jp>
     * 空棚検索時の棚検索方向を表すフィールド（ＯＰの手前から棚検索）
     </jp>*/
    /**<en>
     * Field of search direction at the empty location search  (from OP front)
     </en>*/
    public static final int OP_FRONT = 3;

    /**<jp>
     * 空棚検索時の棚検索方向を表すフィールド（ＯＰの下段から棚検索）
     </jp>*/
    /**<en>
     * Field of search direction at the empty location search  (from OP loser level)
     </en>*/
    public static final int OP_LOWER = 4;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Bank)
     </jp>*/
    /**<en>
     * Element numbers of bank, bay and level for start/end shelf (Bank)
     </en>*/
    public static final int BANK = 0 ;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Bay)
     </jp>*/
    /**<en>
     *  Element numbers of bank, bay and level for start/end shelf (Bay)
     </en>*/
    public static final int BAY = 1 ;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Level)
     </jp>*/
    /**<en>
     *  Element numbers of bank, bay and level for start/end shelf (Level)
     </en>*/
    public static final int LEVEL = 2 ;

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーションの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no. the station belongs to
     </en>*/
    protected String wWhStationNo ;

    /**<jp>
     * ゾーン番号
     </jp>*/
    /**<en>
     * Zone no.
     </en>*/
    protected int wZoneID ;

    /**<jp>
     * ゾーン名称
     </jp>*/
    /**<en>
     * Zone name
     </en>*/
    protected String wZoneName ;

    /**<jp>
     * 荷高情報
     </jp>*/
    /**<en>
     * Load height
     </en>*/
    protected int wHeight = 0;

    /**<jp>
     * 空棚検索方向
     </jp>*/
    /**<en>
     * Direction of empty location search
     </en>*/
    protected int wDirection;

    /**<jp>
     * 範囲指定の為の開始バンク。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Starting bank which specifies the range. 
     * The variable is valid only with soft zone type.
     </en>*/
    protected int wStartBankNo ;

    /**<jp>
     * 範囲指定の為の開始ベイ。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Starting bay which specifies the range. 
     * The variable is valid only with soft zone type.
     </en>*/
    protected int wStartBayNo ;

    /**<jp>
     * 範囲指定の為の開始レベル。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Starting level which specifies the range. 
     * The variable is valid only with soft zone type.
     </en>*/
    protected int wStartLevelNo ;

    /**<jp>
     * 範囲指定の為の終了バンク。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Ending bank which specifies the range. 
     * The variable is valid only with soft zone type.
     </en>*/
    protected int wEndBankNo ;

    /**<jp>
     * 範囲指定の為の終了ベイ。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Ending bay which specifies the range.
     * The variable is valid only with soft zone type.
     </en>*/
    protected int wEndBayNo ;

    /**<jp>
     * 範囲指定の為の終了レベル。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Ending level which specifies the range.
     * The variable is valid only with soft zone type.
     </en>*/
    private int wEndLevelNo ;

    /**<jp>
     * ゾーン検索優先順序。同一ゾーンIDの情報が複数存在する場合の、ゾーン検索優先順位を保持します。
     * 種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Prioritized order of zone search. It is preserved for in case the identical 
     * zone ID existed.
     * The variable is valid only with soft zone type.
     </en>*/
    protected int wOrderNumber ;

    /**<jp>
     * シリアルNo。ゾーンを一意に識別するための番号です。
     </jp>*/
    /**<en>
     * This number is used to identify the serial no. and zone. 
     </en>*/
    protected int wSerialNumber ;

    /**<jp>
     * ハードゾーン運用の優先順位を保持します。種別がハードゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Preserve the priority of hard zone operation. THis variable is valid only with
     * hard zone type.
     </en>*/
    private String wPriority ;

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
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
    /**<jp>
     * 新しい<CODE>Zone</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>Zone</CODE>.
     </en>*/
    public Zone()
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * このZoneオブジェクトと引数で指定されたオブジェクトが等しいかどうかを比較します。
     * @param obj 比較するオブジェクト
     * @return 引数に指定されたオブジェクトとこのオブジェクトが等しい場合は true、そうでない場合は false
     </jp>*/
    /**<en>
     * Compare this Zone object with the object which has been specified through parameter
     * to see if they are equal.
     * @param obj :Object to compare
     * @return true if both objects are the same, or false if not.
     </en>*/
    public boolean equals(Zone obj)
    {
        if (wZoneID == obj.getZoneID()
            && StringUtil.isEqualsStr(wWhStationNo, obj.getWhStationNo())
            && wZoneName == obj.getZoneName()
            && wHeight == obj.getHeight()
            && wDirection == obj.getDirection()
            && wStartBankNo == obj.getStartBankNo()
            && wStartBayNo == obj.getStartBayNo()
            && wStartLevelNo == obj.getStartLevelNo()
            && wEndBankNo == obj.getEndBankNo()
            && wEndBayNo ==  obj.getEndBayNo()
            && wEndLevelNo == obj.getEndLevelNo()
            && wOrderNumber == obj.getOrderNumber()
            && wSerialNumber == obj.getSerialNumber()
            && wPriority == obj.getPriority())
        {
            return true;
        }
        
        return false;
    }

    /**<jp>
     * ゾーン番号を設定します。
     * @param zid   設定するゾーン番号
     </jp>*/
    /**<en>
     * Set hte zone no.
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
     * ソフトゾーン名称を設定します。
     * @param nm 設定するソフトゾーン名称
     </jp>*/
    /**<en>
     * Set the name of the soft zone.
     * @param nm :the name of the soft zone to set
     </en>*/
    public void setZoneName(String nm)
    {
        wZoneName = nm ;
    }

    /**<jp>
     * ソフトゾーン名称を取得します。
     * @return    ソフトゾーン名称
     </jp>*/
    /**<en>
     * Retrieve the name of the soft zone.
     * @return    :the name of the soft zone
     </en>*/
    public String getZoneName()
    {
        return (wZoneName) ;
    }

    /**<jp>
     * このゾーンが属している倉庫Noを設定します。
     * @param whnum 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no. that this zone belong to.
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
     * Retrieve the warehouse no. that this zone belong to.
     * @return :warehouse no.
     </en>*/
    public String getWhStationNo()
    {
        return wWhStationNo;
    }

    /**<jp>
     * ハードゾーンの荷高を取得します。
     * @return 荷高
     </jp>*/
    /**<en>
     * Retrievet the load height of the hard zone.
     * @return :load height
     </en>*/
    public int getHeight()
    {
        return wHeight ;
    }

    /**<jp>
     * ハードゾーンの荷高をセットします。
     * @param hgt セットする荷高
     </jp>*/
    /**<en>
     * Set the load height of the hard zone.
     * @param hgt :load height to set
     </en>*/
    public void setHeight(int hgt)
    {
        wHeight = hgt ;
    }

    /**<jp>
     * 空棚検索方向をセットします。
     * @param di セットする空棚検索方向
     </jp>*/
    /**<en>
     * Set the direction of empty location search.
     * @param di :the direction of empty location search to set
     </en>*/
    public void setDirection(int di)
    {
        wDirection = di ;
    }

    /**<jp>
     * 空棚検索方向を取得します。
     * @return 空棚検索方向
     </jp>*/
    /**<en>
     * Retrieve the direction of empty location search.
     * @return :the direction of empty location search
     </en>*/
    public int getDirection()
    {
        return wDirection ;
    }

    /**<jp>
     * ソフトゾーンの開始バンクを設定します。
     * @param sbank 開始バンク
     </jp>*/
    /**<en>
     * Set the starting bank of the soft zone.
     * @param sbank :starting bank
     </en>*/
    public void setStartBankNo(int sbank)
    {
        wStartBankNo = sbank ;
    }

    /**<jp>
     * ソフトゾーンの開始バンクを取得します。
     * @return 開始バンク
     </jp>*/
    /**<en>
     * Retrieve the starting bank of the soft zone.
     * @return :starting bank
     </en>*/
    public int getStartBankNo()
    {
        return wStartBankNo ;
    }

    /**<jp>
     * ソフトゾーンの開始ベイを設定します。
     * @param sbay 開始バンク
     </jp>*/
    /**<en>
     * Set the starting bay of the soft zone.
     * @param sbay :the starting bay
     </en>*/
    public void setStartBayNo(int sbay)
    {
        wStartBayNo = sbay ;
    }

    /**<jp>
     * ソフトゾーンの開始ベイを取得します。
     * @return 開始ベイ
     </jp>*/
    /**<en>
     * Retrieve the starting bay of the soft zone.
     * @return :the starting bay 
     </en>*/
    public int getStartBayNo()
    {
        return wStartBayNo ;
    }

    /**<jp>
     * ソフトゾーンの開始レベルを設定します。
     * @param slevel 開始レベル
     </jp>*/
    /**<en>
     * Set the starting level of the soft zone.
     * @param slevel :strating level
     </en>*/
    public void setStartLevelNo(int slevel)
    {
        wStartLevelNo = slevel ;
    }

    /**<jp>
     * ソフトゾーンの開始レベルを取得します。
     * @return 開始レベル
     </jp>*/
    /**<en>
     * Retrieve the starting level of the soft zone.
     * @return :starting level
     </en>*/
    public int getStartLevelNo()
    {
        return wStartLevelNo ;
    }

    /**<jp>
     * ソフトゾーンの終了バンクを設定します。
     * @param ebank 終了バンク
     </jp>*/
    /**<en>
     * Set teh ending bank of the soft zone.
     * @param ebank :ending bank
     </en>*/
    public void setEndBankNo(int ebank)
    {
        wEndBankNo = ebank ;
    }

    /**<jp>
     * ソフトゾーンの終了バンクを取得します。
     * @return 終了バンク
     </jp>*/
    /**<en>
     * Retrieve the ending bank of the soft zone.
     * @return :ending bank
     </en>*/
    public int getEndBankNo()
    {
        return wEndBankNo ;
    }

    /**<jp>
     * ソフトゾーンの終了ベイを設定します。
     * @param ebay 終了ベイ
     </jp>*/
    /**<en>
     * Set the ending bay of the soft zone.
     * @param ebay :ending bay
     </en>*/
    public void setEndBayNo(int ebay)
    {
        wEndBayNo = ebay ;
    }

    /**<jp>
     * ソフトゾーンの終了ベイを取得します。
     * @return 終了ベイ
     </jp>*/
    /**<en>
     * Retrieve the ending bay of the soft zone.
     * @return :ending bay
     </en>*/
    public int getEndBayNo()
    {
        return wEndBayNo ;
    }

    /**<jp>
     * ソフトゾーンの終了レベルを設定します。
     * @param elevel 終了レベル
     </jp>*/
    /**<en>
     * Set the ending level of the soft zone.
     * @param elevel :ending level
     </en>*/
    public void setEndLevelNo(int elevel)
    {
        wEndLevelNo = elevel ;
    }

    /**<jp>
     * ソフトゾーンの終了レベルを取得します。
     * @return 終了レベル
     </jp>*/
    /**<en>
     * Retrieve the ending level of the soft zone.
     * @return :ending level
     </en>*/
    public int getEndLevelNo()
    {
        return wEndLevelNo ;
    }

    /**<jp>
     * ゾーン検索優先順序を設定します。
     * @param order ゾーン検索優先順序
     </jp>*/
    /**<en>
     * Set the priority of zone search.
     * @param order :the priority of zone search
     </en>*/
    public void setOrderNumber(int order)
    {
        wOrderNumber = order ;
    }

    /**<jp>
     * ゾーン検索優先順序を取得します。
     * @return ゾーン検索優先順序
     </jp>*/
    /**<en>
     * Retrieve the priority of zone search.
     * @return :the priority of zone search
     </en>*/
    public int getOrderNumber()
    {
        return wOrderNumber ;
    }

    /**<jp>
     * シリアルNo.を設定します。
     * @param sno シリアルNo.
     </jp>*/
    /**<en>
     * Set the serial no..
     * @param sno :serial no.
     </en>*/
    public void setSerialNumber(int sno)
    {
        wSerialNumber = sno ;
    }

    /**<jp>
     * シリアルNo.を取得します。
     * @return シリアルNo.
     </jp>*/
    /**<en>
     * Retrieve the serial no.
     * @return :serial no.
     </en>*/
    public int getSerialNumber()
    {
        return wSerialNumber ;
    }

    /**<jp>
     * ハードゾーン運用の優先順位を設定します。
     * @param pri ハードゾーン運用の優先順位
     </jp>*/
    /**<en>
     * Set the priority of hard zone operation.
     * @param pri :the priority of hard zone operation
     </en>*/
    public void setPriority(String pri)
    {
        wPriority = pri ;
    }

    /**<jp>
     * ハードゾーン運用の優先順位を取得します。
     * @return ハードゾーン運用の優先順位
     </jp>*/
    /**<en>
     * Retrieve the priority of hard zone operation.
     * @return :the priority of hard zone operation
     </en>*/
    public String getPriority()
    {
        return wPriority ;
    }

    /**<jp>
     * Zoneの文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation of Zone.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        buf.append("\nZoneID:" + Integer.toString(wZoneID)) ;
        buf.append("\nDirection:" + Integer.toString(wDirection)) ;
        buf.append("\nWareHouseStationNo:" + wWhStationNo) ;
        buf.append("\nStartBank:" + Integer.toString(wStartBankNo)) ;
        buf.append("\nStartBay:" + Integer.toString(wStartBayNo)) ;
        buf.append("\nStartLevel:" + Integer.toString(wStartLevelNo)) ;
        buf.append("\nEndBank:" + Integer.toString(wEndBankNo)) ;
        buf.append("\nEndBay:" + Integer.toString(wEndBayNo)) ;
        buf.append("\nEndLevel:" + Integer.toString(wEndLevelNo)) ;
        buf.append("\nOrderNumber:" + Integer.toString(wOrderNumber)) ;
        buf.append("\nSerialNumber:" + Integer.toString(wSerialNumber)) ;
        buf.append("\nPriority:" + wPriority) ;
        return buf.toString() ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

