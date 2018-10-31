// $Id: Width.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * 荷幅を管理するためのクラスです。
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
public class Width
        extends ToolEntity
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Bank)
     </jp>*/
    /**<en>
     * Element numbers of bank, bay and level for start/end shelf (Bank)
     </en>*/
    public static final int BANK = 0;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Bay)
     </jp>*/
    /**<en>
     *  Element numbers of bank, bay and level for start/end shelf (Bay)
     </en>*/
    public static final int BAY = 1;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Level)
     </jp>*/
    /**<en>
     *  Element numbers of bank, bay and level for start/end shelf (Level)
     </en>*/
    public static final int LEVEL = 2;

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーションの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no. the station belongs to
     </en>*/
    protected String wWhStationNo;

    /**<jp>
     * 荷幅ID
     </jp>*/
    /**<en>
     * Load width id
     </en>*/
    protected int wWidthId;

    /**<jp>
     * 荷幅
     </jp>*/
    /**<en>
     * Load width
     </en>*/
    protected int wWidth;

    /**<jp>
     * 荷幅名称
     </jp>*/
    /**<en>
     * Load width name
     </en>*/
    protected String wWidthName;

    /**<jp>
     * 最大格納数
     </jp>*/
    /**<en>
     * Max storage
     </en>*/
    protected int wMaxStorage = 0;

    /**<jp>
     * 範囲指定の為の開始バンク。
     </jp>*/
    /**<en>
     * Starting bank which specifies the range. 
     </en>*/
    protected int wStartBankNo;

    /**<jp>
     * 範囲指定の為の開始ベイ。
     </jp>*/
    /**<en>
     * Starting bay which specifies the range. 
     </en>*/
    protected int wStartBayNo;

    /**<jp>
     * 範囲指定の為の開始レベル。
     </jp>*/
    /**<en>
     * Starting level which specifies the range. 
     </en>*/
    protected int wStartLevelNo;

    /**<jp>
     * 範囲指定の為の終了バンク。
     </jp>*/
    /**<en>
     * Ending bank which specifies the range. 
     </en>*/
    protected int wEndBankNo;

    /**<jp>
     * 範囲指定の為の終了ベイ。
     </jp>*/
    /**<en>
     * Ending bay which specifies the range.
     </en>*/
    protected int wEndBayNo;

    /**<jp>
     * 範囲指定の為の終了レベル。
     </jp>*/
    /**<en>
     * Ending level which specifies the range.
     </en>*/
    private int wEndLevelNo;

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
    public Width()
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * このWidthオブジェクトと引数で指定されたオブジェクトが等しいかどうかを比較します。
     * @param obj 比較するオブジェクト
     * @return 引数に指定されたオブジェクトとこのオブジェクトが等しい場合は true、そうでない場合は false
     </jp>*/
    /**<en>
     * Compare this Width object with the object which has been specified through parameter
     * to see if they are equal.
     * @param obj :Object to compare
     * @return true if both objects are the same, or false if not.
     </en>*/
    public boolean equals(Width obj)
    {
        if (wWidthId == obj.getWidthId() && wWidth == obj.getWidth()
                && StringUtil.isEqualsStr(wWhStationNo, obj.getWhStationNo()) && wWidthName == obj.getWidthName()
                && wMaxStorage == obj.getMaxStorage() && wStartBankNo == obj.getStartBankNo()
                && wStartBayNo == obj.getStartBayNo() && wStartLevelNo == obj.getStartLevelNo()
                && wEndBankNo == obj.getEndBankNo() && wEndBayNo == obj.getEndBayNo()
                && wEndLevelNo == obj.getEndLevelNo())
        {
            return true;
        }

        return false;
    }

    /**<jp>
     * 荷幅IDを設定します。
     * @param width id   設定する荷幅ID
     </jp>*/
    /**<en>
     * Set hte width id.
     * @param id   :load width id to set
     </en>*/
    public void setWidthId(int id)
    {
        wWidthId = id;
    }

    /**<jp>
     * 荷幅IDを取得します。
     * @return    荷幅ID
     </jp>*/
    /**<en>
     * Retrieve the load width id.
     * @return    :load width id.
     </en>*/
    public int getWidthId()
    {
        return wWidthId;
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
     * 荷幅名称を設定します。
     * @param nm 設定する荷幅名称
     </jp>*/
    /**<en>
     * Set the name of the load width.
     * @param nm :the name of the load width to set
     </en>*/
    public void setWidthName(String nm)
    {
        wWidthName = nm;
    }

    /**<jp>
     * 荷幅名称を取得します。
     * @return    荷幅名称
     </jp>*/
    /**<en>
     * Retrieve the name of the load width.
     * @return    :the name of the load width
     </en>*/
    public String getWidthName()
    {
        return (wWidthName);
    }

    /**<jp>
     * 倉庫Noを設定します。
     * @param whnum 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no.
     * @param whnum :warehouse no.
     </en>*/
    public void setWhStationNo(String whnum)
    {
        wWhStationNo = whnum;
    }

    /**<jp>
     * 倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no.
     * @return :warehouse no.
     </en>*/
    public String getWhStationNo()
    {
        return wWhStationNo;
    }

    /**<jp>
     * 最大格納数を取得します。
     * @return 最大格納数
     </jp>*/
    /**<en>
     * Retrievet the max storage
     * @return :max storage
     </en>*/
    public int getMaxStorage()
    {
        return wMaxStorage;
    }

    /**<jp>
     * 最大格納数をセットします。
     * @param hgt セットする最大格納数
     </jp>*/
    /**<en>
     * Set the max storage
     * @param maxstorage :max storage to set
     </en>*/
    public void setMaxStorage(int maxstorage)
    {
        wMaxStorage = maxstorage;
    }

    /**<jp>
     * 開始バンクを設定します。
     * @param sbank 開始バンク
     </jp>*/
    /**<en>
     * Set the starting bank.
     * @param sbank :starting bank
     </en>*/
    public void setStartBankNo(int sbank)
    {
        wStartBankNo = sbank;
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
     * Set the starting bay.
     * @param sbay :the starting bay
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
     * @return :the starting bay 
     </en>*/
    public int getStartBayNo()
    {
        return wStartBayNo;
    }

    /**<jp>
     * 開始レベルを設定します。
     * @param slevel 開始レベル
     </jp>*/
    /**<en>
     * Set the starting level.
     * @param slevel :strating level
     </en>*/
    public void setStartLevelNo(int slevel)
    {
        wStartLevelNo = slevel;
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
     * @param ebank 終了バンク
     </jp>*/
    /**<en>
     * Set teh ending bank.
     * @param ebank :ending bank
     </en>*/
    public void setEndBankNo(int ebank)
    {
        wEndBankNo = ebank;
    }

    /**<jp>
     * 終了バンクを取得します。
     * @return 終了バンク
     </jp>*/
    /**<en>
     * Retrieve the ending bank.
     * @return :ending bank
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
     * @param ebay :ending bay
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
     * @return :ending bay
     </en>*/
    public int getEndBayNo()
    {
        return wEndBayNo;
    }

    /**<jp>
     * 終了レベルを設定します。
     * @param elevel 終了レベル
     </jp>*/
    /**<en>
     * Set the ending level.
     * @param elevel :ending level
     </en>*/
    public void setEndLevelNo(int elevel)
    {
        wEndLevelNo = elevel;
    }

    /**<jp>
     * 終了レベルを取得します。
     * @return 終了レベル
     </jp>*/
    /**<en>
     * Retrieve the ending level.
     * @return :ending level
     </en>*/
    public int getEndLevelNo()
    {
        return wEndLevelNo;
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
        buf.append("\nWidth:" + Integer.toString(wWidth));
        buf.append("\nWareHouseStationNo:" + wWhStationNo);
        buf.append("\nStartBank:" + Integer.toString(wStartBankNo));
        buf.append("\nStartBay:" + Integer.toString(wStartBayNo));
        buf.append("\nStartLevel:" + Integer.toString(wStartLevelNo));
        buf.append("\nEndBank:" + Integer.toString(wEndBankNo));
        buf.append("\nEndBay:" + Integer.toString(wEndBayNo));
        buf.append("\nEndLevel:" + Integer.toString(wEndLevelNo));
        return buf.toString();
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

