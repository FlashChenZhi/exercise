// $Id: WidthParameter.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.wms.asrs.tool.location.Width;

/**<jp>
 * 荷幅パラメータを保持するクラスです。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class preserves the width parameters.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class WidthParameter
        extends Parameter
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------

    /**<jp>
     * Widthインスタンス
     </jp>*/
    /**<en>
     * Width instance
     </en>*/
    private Width wInstance;

    /**<jp>
     * 荷幅ID
     </jp>*/
    /**<en>
     * Load width id.
     </en>*/
    protected int wWidthId;

    /**<jp>
     * 荷幅
     </jp>*/
    /**<en>
     * Load width.
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
    protected int wMaxStorage;

    /**<jp>
     * ステーションの所属する倉庫STNo
     </jp>*/
    /**<en>
     * Warehouse station no. that the station belongs to
     </en>*/
    protected String wWareHouseStationNo;

    /**<jp>
     * ステーションの所属する倉庫No（格納区分）
     </jp>*/
    /**<en>
     * Warehouse no. that the station belongs to
     </en>*/
    protected String wWareHouseNo;

    /**<jp>
     * ステーションの所属する倉庫名称
     </jp>*/
    /**<en>
     * Name of the warehouse no. that the station belongs to
     </en>*/
    protected String wWareHouseName;

    /**<jp>
     * 範囲指定の為の開始バンク。
     </jp>*/
    /**<en>
     * Starting bank which specifies the range
     </en>*/
    protected int wStartBank;

    /**<jp>
     * 範囲指定の為の開始ベイ。
     </jp>*/
    /**<en>
     * Starting bay which specifies the range
     </en>*/
    protected int wStartBay;

    /**<jp>
     * 範囲指定の為の開始レベル。
     </jp>*/
    /**<en>
     * Starting level which specifies the range
     </en>*/
    protected int wStartLevel;

    /**<jp>
     * 範囲指定の為の終了バンク。
     </jp>*/
    /**<en>
     * Ending bank which specifies the range
     </en>*/
    protected int wEndBank;

    /**<jp>
     * 範囲指定の為の終了ベイ。
     </jp>*/
    /**<en>
     * Ending bay which specifies the range
     </en>*/
    protected int wEndBay;

    /**<jp>
     * 範囲指定の為の終了レベル。
     </jp>*/
    /**<en>
     * Ending level which specifies the range
     </en>*/
    private int wEndLevel;

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
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public WidthParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * Widthインスタンスをセットします。
     * @param objセットするインスタンス
     </jp>*/
    /**<en>
     * Set the Width instance.
     * @param obj :instance to set
     </en>*/
    public void setInstance(Width obj)
    {
        wInstance = obj;
    }

    /**<jp>
     * Widthインスタンスを取得します。
     * @return Widthインスタンス
     </jp>*/
    /**<en>
     * Retrieve the Width instance.
     * @return Width instance
     </en>*/
    public Width getInstance()
    {
        return wInstance;
    }

    /**<jp>
     * 荷幅IDを設定します。
     * @param width   設定する荷幅ID
     </jp>*/
    /**<en>
     * Set the load width id.
     * @param id   :load width id. to set
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
     * @return    load width id.
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
     * Set the load width.
     * @param width   :load width. to set
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
     * @return    load width.
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
     * Set the load width name.
     * @param nm :load width name to set
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
     * Retrieve the load width name.
     * @return    load width name
     </en>*/
    public String getWidthName()
    {
        return (wWidthName);
    }

    /**<jp>
     * 最大格納数を設定します。
     * @param maxstorage 設定する最大格納数
     </jp>*/
    /**<en>
     * Set the max storage.
     * @param ht :max storage to set
     </en>*/
    public void setMaxStorage(int maxstorage)
    {
        wMaxStorage = maxstorage;
    }

    /**<jp>
     * 最大格納数を取得します。
     * @return    最大格納数
     </jp>*/
    /**<en>
     * Retrieve the max storage.
     * @return    max storage
     </en>*/
    public int getMaxStorage()
    {
        return (wMaxStorage);
    }

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
     * 倉庫No(格納区分)を設定します(例：1)。
     * @param wh 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no.
     * @param whnum warehouse no.
     </en>*/
    public void setWareHouseNo(String whnum)
    {
        wWareHouseNo = whnum;
    }

    /**<jp>
     * 倉庫No(格納区分)を取得します(例：1)。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no.
     * @return warehouse no.
     </en>*/
    public String getWareHouseNo()
    {
        return wWareHouseNo;
    }

    /**<jp>
     * 倉庫名称を設定します。
     * @param  倉庫名称
     </jp>*/
    /**<en>
     * Set the name of the warehouse.
     * @param  the name of the warehouse
     </en>*/
    public void setWareHouseName(String wh)
    {
        wWareHouseName = wh;
    }

    /**<jp>
     * 倉庫名称を取得します。
     * @return 倉庫名称
     </jp>*/
    /**<en>
     * Retrieve the name of the warehouse.
     * @return the name of the warehouse
     </en>*/
    public String getWareHouseName()
    {
        return wWareHouseName;
    }

    /**<jp>
     * 開始バンクを設定します。
     * @param sbank 開始バンク
     </jp>*/
    /**<en>
     * Set the starting bank.
     * @param sbank :starting bank
     </en>*/
    public void setStartBank(int sbank)
    {
        wStartBank = sbank;
    }

    /**<jp>
     * 開始バンクを取得します。
     * @return 開始バンク
     </jp>*/
    /**<en>
     * Retrieve the starting bank.
     * @return :starting bank
     </en>*/
    public int getStartBank()
    {
        return wStartBank;
    }

    /**<jp>
     * 開始ベイを設定します。
     * @param sbay 開始ベイ
     </jp>*/
    /**<en>
     * Set the starting bay.
     * @param sbay :starting bay
     </en>*/
    public void setStartBay(int sbay)
    {
        wStartBay = sbay;
    }

    /**<jp>
     * 開始ベイを取得します。
     * @return 開始ベイ
     </jp>*/
    /**<en>
     * Retrieve the starting bay.
     * @return :starting bay
     </en>*/
    public int getStartBay()
    {
        return wStartBay;
    }

    /**<jp>
     * 開始レベルを設定します。
     * @param slevel 開始レベル
     </jp>*/
    /**<en>
     * Set the starting level.
     * @param slevel :satrting level
     </en>*/
    public void setStartLevel(int slevel)
    {
        wStartLevel = slevel;
    }

    /**<jp>
     * 開始レベルを取得します。
     * @return 開始レベル
     </jp>*/
    /**<en>
     * Retrieve the starting level.
     * @return :satrting level
     </en>*/
    public int getStartLevel()
    {
        return wStartLevel;
    }

    /**<jp>
     * 終了バンクを設定します。
     * @param ebank 終了バンク
     </jp>*/
    /**<en>
     * Set the ending bank.
     * @param ebank :ending bank
     </en>*/
    public void setEndBank(int ebank)
    {
        wEndBank = ebank;
    }

    /**<jp>
     * 終了バンクを取得します。
     * @return 終了バンク
     </jp>*/
    /**<en>
     * Retrieve the ending bank.
     * @return :ending bank
     </en>*/
    public int getEndBank()
    {
        return wEndBank;
    }

    /**<jp>
     * 終了ベイを設定します。
     * @param ebay 終了ベイ
     </jp>*/
    /**<en>
     * Set the ending bay.
     * @param ebay :ending bay
     </en>*/
    public void setEndBay(int ebay)
    {
        wEndBay = ebay;
    }

    /**<jp>
     * 終了ベイを取得します。
     * @return 終了ベイ
     </jp>*/
    /**<en>
     * Retrieve the ending bay.
     * @return :ending bay
     </en>*/
    public int getEndBay()
    {
        return wEndBay;
    }

    /**<jp>
     * 終了レベルを設定します。
     * @param elevel 終了レベル
     </jp>*/
    /**<en>
     * Set the ending level.
     * @param elevel :ending level
     </en>*/
    public void setEndLevel(int elevel)
    {
        wEndLevel = elevel;
    }

    /**<jp>
     * 終了レベルを取得します。
     * @return 終了レベル
     </jp>*/
    /**<en>
     * Retrieve the ending bank.
     * @return :ending level
     </en>*/
    public int getEndLevel()
    {
        return wEndLevel;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

