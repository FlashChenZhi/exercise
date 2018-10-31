// $Id: Shelf.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * 棚の情報を持つためのクラスです。
 * 棚はコンベアなどのステーションと異なり、起動などの操作が無い為、いくつかの情報は
 * 固定的に保持されています。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>未設定のソフトゾーンを表すフィールドを追加<BR>
 * 新規に棚を作成するときに使用します。
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to preserve the shelf information.
 * Unlike the stations such as conveyers, there are no opearations with shelves e.g. start ups;
 * therefore some of the information are fixed and preserved.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>Added the field of unspecified soft zone.<BR>
 * This will be used when newly creating the locations.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class Shelf
        extends Station
{
    // Class fields --------------------------------------------------
    /**<jp>
     * デフォルト倉庫ステーションNo.
     </jp>*/
    /**<en>
     * Default station no. of the warehouse
     </en>*/
    public static final int DEFAULT_WH_NUM = 1;

    /**<jp>
     * アクセス可能・不可能を表すフィールド(アクセス可）
     </jp>*/
    /**<en>
     * Field of accessibility (accessible)
     </en>*/
    public static final int ACCESS_OK = 0;

    /**<jp>
     * アクセス可能・不可能を表すフィールド(アクセス不可）
     </jp>*/
    /**<en>
     * Field of accessibility (inaccessible)
     </en>*/
    public static final int ACCESS_NG = 1;

    /**<jp>
     * 棚の状態を表すフィールド(空棚）
     </jp>*/
    /**<en>
     * Field of location status (empty)
     </en>*/
    public static final int PRESENCE_EMPTY = 0;

    /**<jp>
     * 棚の状態を表すフィールド(実棚）
     </jp>*/
    /**<en>
     * Field of location status (loaded)
     </en>*/
    public static final int PRESENCE_STORAGED = 1;

    /**<jp>
     * 棚の状態を表すフィールド(予約棚）
     </jp>*/
    /**<en>
     * Field of location status (reserved)
     </en>*/
    public static final int PRESENCE_RESERVATION = 2;

    /**<jp>
     * ハードゾーンを表すフィールド
     </jp>*/
    /**<en>
     * Field of hard zone
     </en>*/
    public static final int HARD = 1;

    /**<jp>
     * ソフトゾーンを表すフィールド
     </jp>*/
    /**<en>
     * Field of soft zone
     </en>*/
    public static final int SOFT = 2;

    /**<jp>
     * 未設定のソフトゾーンを表すフィールド
     </jp>*/
    /**<en>
     * Field of non-set up soft zone
     </en>*/
    public static final int UN_SETTING = 0;

    /**
     * 棚使用不可フラグ : 使用可
     */
    public static final int PROHIBITION_FLAG_OK = 0;

    /**
     * 棚使用不可フラグ : 使用不可
     */
    public static final int PROHIBITION_FLAG_NG = 1;

    /**
     * 棚使用フラグ : 使用可
     */
    public static final int LOCATION_USE_FLAG_OK = 0;

    /**
     * 棚使用フラグ : 使用不可
     */
    public static final int LOCATION_USE_FLAG_NG = 1;

    // Class variables -----------------------------------------------

    /**<jp>
     * 棚のバンクを保持します。
     </jp>*/
    /**<en>
     * Preserve the bank of the location.
     </en>*/
    private int wBankNo;

    /**<jp>
     * 棚のベイを保持します。
     </jp>*/
    /**<en>
     * Preserve the bay of the location.
     </en>*/
    private int wBayNo;

    /**<jp>
     * 棚のレベルを保持します。
     </jp>*/
    /**<en>
     * Preserve the level of the location.
     </en>*/
    private int wLevelNo;

    /**<jp>
     * 棚のアドレスを保持します。
     </jp>*/
    /**<en>
     * Preserve the address of the location.
     </en>*/
    private int wAddressNo;

    /**
     * 状態
     */
    private int wProhibitionFlag;

    /**<jp>
     * 棚状態を保持します。
     </jp>*/
    /**<en>
     * Preserve the status of the location.
     </en>*/
    private int wStatusFlag;

    /**<jp>
     * ハードゾーンを保持します。
     </jp>*/
    /**<en>
     * Preserve the hard zone.
     </en>*/
    private int wHardZoneId;

    /**<jp>
     * ソフトゾーンを保持します。
     </jp>*/
    /**<en>
     * Preserve the soft zone.
     </en>*/
    private int wSoftZoneId;

    /**<jp>
     * アクセス可能、不可能を保持します。
     </jp>*/
    /**<en>
     * Preserve the accessibility.
     </en>*/
    private boolean wAccessNgFlag = false;

    /**<jp>
     * 棚位置（手前、奥）を保持します。
     </jp>*/
    /**<en>
     * Preserves the positions of the shelves. (front or rear)
     </en>*/
    private int wSide;

    /**<jp>
     * 荷幅を保持します。
     </jp>*/
    /**<en>
     * Preserves the load width. 
     </en>*/
    private int wWidth;

    /**<jp>
     * 棚使用フラグを保持します。
     </jp>*/
    private int wLocationUseFlag;

    /**<jp>
     * 優先順を保持します。
     </jp>*/
    /**<en>
     * Preserve the priority.
     </en>*/
    private int wPriority;

    /**<jp>
     * ペア棚のステーションNo（棚No）を保持します。
     </jp>*/
    /**<en>
     * Preserves station no. (location no.) of pair shelves.
     </en>*/
    private String wPairStationNumber;

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

    /**<jp>
     * Bank,Bay,Levelから、棚のステーション番号(棚番号)を返します。
     * @param bank バンク
     * @param bay ベイ
     * @param level レベル
     * @return 棚のステーション番号(棚番号)
     </jp>*/
    /**<en>
     * Return the station no. based on the Bank, Bay and Level.
     * @param bank   :bank
     * @param bay    :bay
     * @param level  :level
     * @return station no. of the location (location no.)
     </en>*/
    public static String getNumber(int bank, int bay, int level)
    {
        return (getNumber(DEFAULT_WH_NUM, bank, bay, level, 0));
    }

    /**<jp>
     * WareHouse, Bank,Bay,Levelから、棚のステーション番号(棚番号)を返します。
     * @param whnum  倉庫のステーション番号
     * @param bank バンク
     * @param bay ベイ
     * @param level レベル
     * @return 棚のステーション番号(棚番号)
     </jp>*/
    /**<en>
     * Return the station no. based on the WareHouse, Bank, Bay and Level.
     * @param whnum  :station no. of the warehouse
     * @param bank   :bank
     * @param bay    :bay
     * @param level  :level
     * @return station no. of the location (location no.)
     </en>*/
    public static String getNumber(int whnum, int bank, int bay, int level)
    {
        return (getNumber(whnum, bank, bay, level, 0));
    }

    /**<jp>
     * Bank,Bay,Level,Addressから、棚のステーション番号(棚番号)を返します。
     * @param whnum  倉庫のステーション番号
     * @param bank バンク
     * @param bay ベイ
     * @param level レベル
     * @param addr アドレス
     * @return 棚のステーション番号(棚番号)
     </jp>*/
    /**<en>
     * Return the station no. based on the Bank, Bay, Level and Address.
     * @param whnum  :station no. of the warehouse
     * @param bank   :bank
     * @param bay    :bay
     * @param level  :level
     * @param addr   :address
     * @return station no. of the location (location no.)
     </en>*/
    public static String getNumber(int whnum, int bank, int bay, int level, int addr)
    {
        int banksize = WmsParam.ASRS_BANK_LENGTH;
        int baysize = WmsParam.ASRS_BAY_LENGTH;
        int levelsize = WmsParam.ASRS_LEVEL_LENGTH;
        int whsize = WmsParam.WAREHOUSE_LENGTH;
        int areasize = WmsParam.ASRS_SUBLOC_LENGTH;

        String whdecimal = "";
        for (int i = 0; i < whsize; i++)
        {
            whdecimal = whdecimal + "0";
        }
        DecimalFormat whfmt = new DecimalFormat(whdecimal);

        String bankdecimal = "";
        for (int i = 0; i < banksize; i++)
        {
            bankdecimal = bankdecimal + "0";
        }
        DecimalFormat bankfmt = new DecimalFormat(bankdecimal);

        String baydecimal = "";
        for (int i = 0; i < baysize; i++)
        {
            baydecimal = baydecimal + "0";
        }
        DecimalFormat bayfmt = new DecimalFormat(baydecimal);

        String leveldecimal = "";
        for (int i = 0; i < levelsize; i++)
        {
            leveldecimal = leveldecimal + "0";
        }
        DecimalFormat levelfmt = new DecimalFormat(leveldecimal);

        String areadecimal = "";
        for (int i = 0; i < areasize; i++)
        {
            areadecimal = areadecimal + "0";
        }
        DecimalFormat areafmt = new DecimalFormat(areadecimal);

        return (whfmt.format(whnum) + bankfmt.format(bank) + bayfmt.format(bay) + levelfmt.format(level) + areafmt.format(addr));
    }

    /**<jp>
     * Bank,Bay,Levelから、棚番号("01001001"形式)を返します。
     * @param bank バンク
     * @param bay ベイ
     * @param level レベル
     * @return 棚番号("01001001"形式)
     </jp>*/
    public static String getShelfNumber(int bank, int bay, int level)
    {
        int banksize = WmsParam.ASRS_BANK_LENGTH;
        int baysize = WmsParam.ASRS_BAY_LENGTH;
        int levelsize = WmsParam.ASRS_LEVEL_LENGTH;

        String bankdecimal = "";
        for (int i = 0; i < banksize; i++)
        {
            bankdecimal = bankdecimal + "0";
        }
        DecimalFormat bankfmt = new DecimalFormat(bankdecimal);

        String baydecimal = "";
        for (int i = 0; i < baysize; i++)
        {
            baydecimal = baydecimal + "0";
        }
        DecimalFormat bayfmt = new DecimalFormat(baydecimal);

        String leveldecimal = "";
        for (int i = 0; i < levelsize; i++)
        {
            leveldecimal = leveldecimal + "0";
        }
        DecimalFormat levelfmt = new DecimalFormat(leveldecimal);

        return (bankfmt.format(bank) + bayfmt.format(bay) + levelfmt.format(level));
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しく棚を管理するための<code>Shelf</code>インスタンスを作成します。既に定義されている棚情報を
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param snum  棚のステーション番号(棚番号)
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create a new instance of <code>Shelf</code> in order to newly control the locations.
     * Please use <code>StationFactory</code> class if the instance which has the defined 
     * shelf information already is required.
     * @param snum  :station no. of the location (location no.)
     * @see StationFactory
     </en>*/
    public Shelf(String snum)
    {
        super(snum);

        //<jp> 最大パレット保持数は1固定</jp>
        //<en> max. number of preserved pallet is 1 and fixed.</en>
        wMaxPalletQty = 1;

        //<jp> 棚は常に送信可能</jp>
        //<en> the location is always sendable.</en>
        wSendable = true;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 最大パレット保持数を設定します。このメソッドは他のクラスとの
     * 互換性のためにありますが、棚にはパレットは1つしか保管できないため、
     * このメソッドでは何も変更されません。
     * @param pnum 設定する最大パレット保持数
     </jp>*/
    /**<en>
     * Set the max. number of preserved pallet. Though this method exists to provide the 
     * compatibility with other classes, only one pallet can be preserved in a shelf practically
     * and therefore there will be no modificaiton in this method.
     * @param pnum :the max. number of preserved pallet to set
     </en>*/
    public void setMaxPalletQty(int pnum)
    {
        super.setMaxPalletQty(1);
    }

    /**<jp>
     * 最大パレット保持数を取得します。
     * @return    最大パレット保持数
     </jp>*/
    /**<en>
     * Retrieve the the max. number of preserved pallet.
     * @return   :the max. number of preserved pallet
     </en>*/
    public int getMaxPalletQty()
    {
        return (1);
    }

    /**<jp>
     * バンクを設定します。
     * @param b バンク
     </jp>*/
    /**<en>
     * Set the bank.
     * @param b :bank
     </en>*/
    public void setBankNo(int b)
    {
        wBankNo = b;
    }

    /**<jp>
     * ベイを設定します。
     * @param b ベイ
     </jp>*/
    /**<en>
     * Set the bay.
     * @param b :bay
     </en>*/
    public void setBayNo(int b)
    {
        wBayNo = b;
    }

    /**<jp>
     * レベルを設定します。
     * @param lv レベル
     </jp>*/
    /**<en>
     * Set the level.
     * @param lv :level
     </en>*/
    public void setLevelNo(int lv)
    {
        wLevelNo = lv;
    }

    /**<jp>
     * アドレスを設定します。
     * @param addr アドレス
     </jp>*/
    /**<en>
     * Set the address.
     * @param addr :address
     </en>*/
    public void setAddressNo(int addr)
    {
        wAddressNo = addr;
    }

    /**<jp>
     * バンクを返します。
     * @return バンク
     </jp>*/
    /**<en>
     * Return the bank.
     * @return   :bank
     </en>*/
    public int getBankNo()
    {
        return (wBankNo);
    }

    /**<jp>
     * ベイを返します。
     * @return ベイ
     </jp>*/
    /**<en>
     * Return the bay.
     * @return   :bay
     </en>*/
    public int getBayNo()
    {
        return (wBayNo);
    }

    /**<jp>
     * レベルを返します。
     * @return レベル
     * @return   :the max. number of preserved pallet
     </jp>*/
    /**<en>
     * Return the level.
     * @return   :level
     </en>*/
    public int getLevelNo()
    {
        return (wLevelNo);
    }

    /**<jp>
     * アドレスを返します。
     * @return アドレス
     </jp>*/
    /**<en>
     * Return the address.
     * @return   :address
     </en>*/
    public int getAddressNo()
    {
        return (wAddressNo);
    }

    /**<jp>
     * 棚状態を設定します。
     * @param  pre 棚状態
     * @throws InvalidStatusException preの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the status of the location.
     * @param  pre :location status
     * @throws InvalidStatusException :Notifies if the contents of pre is invalid.
     </en>*/
    public void setStatusFlag(int pre)
            throws InvalidStatusException
    {
        //<jp> 状態のチェック</jp>
        //<en> Check the status.</en>
        switch (pre)
        {
            //<jp> 正しい状態の一覧</jp>
            //<en> List of correct status</en>
            case PRESENCE_EMPTY:
            case PRESENCE_STORAGED:
            case PRESENCE_RESERVATION:
                wStatusFlag = pre;
                break;

            //<jp> 正しくない状態を設定しようとした場合は例外を発生させ、状態の変更はしない</jp>
            //<en> IF incorrect status were to set, it lets exception occur but will not modify the status.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[3];
                tObj[0] = this.getClass().getName();
                tObj[1] = "wStatusFlag";
                tObj[2] = Integer.toString(pre);
                String classname = (String)tObj[0];
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, classname, tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
        }
    }

    /**<jp>
     * 棚状態を取得します。
     * @return   棚状態
     </jp>*/
    /**<en>
     * Retrieve the location status.
     * @return   :status of the location
     </en>*/
    public int getStatusFlag()
    {
        return wStatusFlag;
    }

    /**<jp>
     * 状態を設定します。
     * @param  pre状態
     * @throws InvalidStatusException preの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the status
     * @param  pre :status
     * @throws InvalidStatusException :Notifies if the contents of pre is invalid.
     </en>*/
    public void setProhibitionFlag(int pre)
            throws InvalidStatusException
    {
        //<jp> 状態のチェック</jp>
        //<en> Check the status.</en>
        switch (pre)
        {
            //<jp> 正しい状態の一覧</jp>
            //<en> List of correct status</en>
            case PROHIBITION_FLAG_NG:
            case PROHIBITION_FLAG_OK:
                wProhibitionFlag = pre;
                break;

            //<jp> 正しくない状態を設定しようとした場合は例外を発生させ、状態の変更はしない</jp>
            //<en> IF incorrect status were to set, it lets exception occur but will not modify the status.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[3];
                tObj[0] = this.getClass().getName();
                tObj[1] = "wProhibitionFlag";
                tObj[2] = Integer.toString(pre);
                String classname = (String)tObj[0];
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, classname, tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
        }
    }

    /**<jp>
     * 状態を取得します。
     * @return   状態
     </jp>*/
    /**<en>
     * Retrieve the status.
     * @return   :status
     </en>*/
    public int getProhibitionFlag()
    {
        return wProhibitionFlag;
    }

    /**<jp>
     * ハードゾーンを設定します。
     * @param zone ハードゾーン
     </jp>*/
    /**<en>
     * Set the hard zone.
     * @param zone :hard zone
     </en>*/
    public void setHardZoneId(int zone)
    {
        wHardZoneId = zone;
    }

    /**<jp>
     * ハードゾーンを取得します。
     * @return   ハードゾーン
     </jp>*/
    /**<en>
     * Retrieve the hard zone.
     * @return   :hard zone
     </en>*/
    public int getHardZoneId()
    {
        return wHardZoneId;
    }

    /**<jp>
     * ソフトゾーンを設定します。
     * @param zone ソフトゾーン
     </jp>*/
    /**<en>
     * Set the soft zone.
     * @param zone :soft zone
     </en>*/
    public void setSoftZoneId(int zone)
    {
        wSoftZoneId = zone;
    }

    /**<jp>
     * ソフトゾーンを取得します。
     * @return ソフトゾーン
     </jp>*/
    /**<en>
     * Retrieve the soft zone.
     * @return   :soft zone
     </en>*/
    public int getSoftZoneId()
    {
        return wSoftZoneId;
    }

    /**<jp>
     * アクセス不可棚フラグを設定します。
     * @param acs アクセス不可棚にする場合はtrue, アクセス可能棚にする場合は false。
     </jp>*/
    /**<en>
     * Set the inacceible flag.
     * @param acs :true if setting the location inaccessible, or false if setting accessible.
     </en>*/
    public void setAccessNgFlag(boolean acs)
    {
        wAccessNgFlag = acs;
    }

    /**<jp>
     * この棚がアクセス可能かどうかを返します。
     * @return    true  アクセス不可棚
     * @return    false アクセス可能棚
     </jp>*/
    /**<en>
     * Return whether/not this location is accessible.
     * @return    true  :inaccesible
     *             false :accessible
     </en>*/
    public boolean isAccessNgFlag()
    {
        return (wAccessNgFlag);
    }

    /**<jp>
     * 棚の位置（手前、奥）をセットします。
     * @param side 棚の位置（手前または奥）
     * @throws InvalidStatusException sideの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Sets the position of shelves.(front or rear)
     * @param side :position of the shelves (front or rear)
     * @throws InvalidStatusException :Notifies if contents of side was outside the range.
     </en>*/
    public void setSide(int side)
            throws InvalidStatusException
    {
        //<jp> 状態のチェック</jp>
        //<en> Checking status</en>
        switch (side)
        {
            //<jp> 正しい状態の一覧</jp>
            //<en> list of normal status</en>
            case Bank.NEAR:
            case Bank.FAR:
                wSide = side;
                break;

            //<jp> 正しくない状態を設定しようとした場合は例外を発生させ、状態の変更はしない</jp>
            //<en> If the status other than normal was attempted to set, it lets occur the exception;</en>
            //<en> it will not change the status.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[3];
                tObj[0] = this.getClass().getName();
                tObj[1] = "wSide";
                tObj[2] = Integer.toString(side);
                String classname = (String)tObj[0];
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, classname, tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
        }
    }

    /**<jp>
     * 棚の位置（手前、奥）を取得します。
     * @return   棚の位置（手前または奥）
     </jp>*/
    /**<en>
     * Retrieves the position of the shelves. (front or rear).
     * @return   position of the shelves (front or rear)
     </en>*/
    public int getSide()
    {
        return wSide;
    }

    /**<jp>
     * 荷幅を設定します。
     * @param width 荷幅
     </jp>*/
    /**<en>
     * Set the load width.
     * @param width :load width
     </en>*/
    public void setWidth(int width)
    {
        wWidth = width;
    }

    /**<jp>
     * 荷幅を取得します。
     * @return 荷幅
     </jp>*/
    /**<en>
     * Retrieve the load width.
     * @return   :load width
     </en>*/
    public int getWidth()
    {
        return wWidth;
    }

    /**<jp>
     * 棚使用フラグをセットします。
     * @param flag 棚使用フラグ
     * @throws InvalidStatusException flagの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Sets the location use flag
     * @param flag :location use flag
     * @throws InvalidStatusException :Notifies if contents of flag was outside the range.
     </en>*/
    public void setLocationUseFlag(int flag)
            throws InvalidStatusException
    {
        //<jp> 棚使用フラグのチェック</jp>
        //<en> Checking location use flag</en>
        switch (flag)
        {
            //<jp> 正しい棚使用フラグの一覧</jp>
            //<en> list of normal location use flag</en>
            case LOCATION_USE_FLAG_OK:
            case LOCATION_USE_FLAG_NG:
                wSide = flag;
                break;

            //<jp> 正しくない状態を設定しようとした場合は例外を発生させ、棚使用フラグの変更はしない</jp>
            //<en> If the location use flag other than normal was attempted to set, it lets occur the exception;</en>
            //<en> it will not change the status.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[3];
                tObj[0] = this.getClass().getName();
                tObj[1] = "wLocationUseFlag";
                tObj[2] = Integer.toString(flag);
                String classname = (String)tObj[0];
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, classname, tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
        }
    }

    /**<jp>
     * 棚使用フラグを取得します。
     * @return   棚使用フラグ
     </jp>*/
    /**<en>
     * Retrieves the location use flag.
     * @return   location use flag
     </en>*/
    public int getLocationUseFlag()
    {
        return wLocationUseFlag;
    }

    /**<jp>
     * 引当て優先順をセットします。
     * @param pty 優先順
     * @throws InvalidStatusException ptyの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Sets the priority.
     * @param pty :priority
     * @throws InvalidStatusException :Notifies if contents of pty was outside the range.
     </en>*/
    public void setPriority(int pty)
    {
        wPriority = pty;
    }

    /**<jp>
     * 引当て優先順を取得します。
     * @return   優先順
     </jp>*/
    /**<en>
     * Retrieves the priority.
     * @return   priority
     </en>*/
    public int getPriority()
    {
        return wPriority;
    }

    /**<jp>
     * ペア棚のステーションNo（棚No）をセットします。
     * @param pair ペア棚のステーションNo（棚No）
     </jp>*/
    /**<en>
     * Sets the station no.(location no.) of the paired shelves.
     * @param pair :station no.(location no.) of the paired shelves
     </en>*/
    public void setPairStationNumber(String pair)
    {
        wPairStationNumber = pair;
    }

    /**<jp>
     * ペア棚のステーションNo（棚No）を返します。
     * @return ペア棚のステーションNo（棚No）
     </jp>*/
    /**<en>
     * Returns the station no.(location no.) of the paired shelves.
     * @return station no.(location no.) of the paired shelves
     </en>*/
    public String getPairStationNumber()
    {
        return wPairStationNumber;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

