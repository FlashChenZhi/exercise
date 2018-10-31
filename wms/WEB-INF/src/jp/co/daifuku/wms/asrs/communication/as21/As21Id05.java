// $Id: As21Id05.java 4843 2009-08-20 07:45:35Z ota $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * AS21通信プロトコルでの「搬送指示(TransportCommand) ID=05」電文を組み立てます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4843 $, $Date: 2009-08-20 16:45:35 +0900 (木, 20 8 2009) $
 * @author $Author: ota $
 </jp>*/
/**<en>
 * Composes communication message "Transport Command ID=05" according to the communication protocol AS21.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4843 $, $Date: 2009-08-20 16:45:35 +0900 (木, 20 8 2009) $
 * @author $Author: ota $
 </en>*/
public class As21Id05
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 搬送区分を表すフィールド (入庫)
     </jp>*/
    /**<en>
     * Field of storage (transport section)
     </en>*/
    public static final String C_STORAGE = "1";

    /**<jp>
     * 搬送区分を表すフィールド (直行)
     </jp>*/
    /**<en>
     * Field of direct traveling (transport section)
     </en>*/
    public static final String C_DIRECT_TRANSFER = "3";

    /**<jp>
     * 入庫設定タイプを表すフィールド (先行設定)
     </jp>*/
    /**<en>
     * Field of storage setting type (setting in advance)
     </en>*/
    public static final String C_SETTING_IN_ADVANCE = "1";

    /**<jp>
     * 入庫設定タイプを表すフィールド (在荷確認設定)
     </jp>*/
    /**<en>
     * Field of storage setting type (Setting to confirm load presence)
     </en>*/
    public static final String C_LOAD_CHECK_SETTING = "2";

    /**<jp>
     * Headerの長さ 各電文固定
     </jp>*/
    /**<en>
     * Length of Header; fixed for each communication message
     </en>*/
    protected static final int HEDERLENGTH = 16;

    /**<jp>
     * MC Keyの長さ
     </jp>*/
    /**<en>
     * Length of MC Key
     </en>*/
    protected static final int CARRYKEY = 8;

    /**<jp>
     * 搬送区分の長さ
     </jp>*/
    /**<en>
     * Length of transport classification
     </en>*/
    protected static final int TRANSPORT_CLASSIFICATION = 1;

    /**<jp>
     * 設定区分の長さ
     </jp>*/
    /**<en>
     *Length of setting classification
     </en>*/
    protected static final int SETTING_CLASSIFICATION = 1;

    /**<jp>
     * Group No.の長さ
     </jp>*/
    /**<en>
     * Length of Group number
     </en>*/
    protected static final int GROUP_NO = 3;

    /**<jp>
     * Station No.の長さ
     </jp>*/
    /**<en>
     * Length of Station number
     </en>*/
    protected static final int STATIONNO = 4;

    /**<jp>
     * Location No.の長さ
     </jp>*/
    /**<en>
     * Length of location number 
     </en>*/
    protected static final int LOCATION_NO = 12;

    /**<jp>
     * 荷姿情報の長さ
     </jp>*/
    /**<en>
     * Length of load size
     </en>*/
    protected static final int DIMENSION_INFORMATION = 2;

    /**<jp>
     * BC Dataの長さ
     </jp>*/
    /**<en>
     * Length of BC Data
     </en>*/
    protected static final int BC_DATA = 30;

    /**<jp>
     * 作業No.の長さ
     </jp>*/
    /**<en>
     * Length of of work number
     </en>*/
    protected static final int WORK_NO = 8;

    /**<jp>
     * 制御情報の長さ
     </jp>*/
    /**<en>
     * Length of control information
     </en>*/
    protected static final int CONTROL_INFORMATION = 30;

    /**<jp>
     * 電文長
     </jp>*/
    /**<en>
     * Length of communication message
     </en>*/
    protected static final int LEN_TOTAL =
            HEDERLENGTH + CARRYKEY + TRANSPORT_CLASSIFICATION + SETTING_CLASSIFICATION + GROUP_NO
            // From and To
                    + (STATIONNO * 2) + LOCATION_NO + DIMENSION_INFORMATION + BC_DATA + WORK_NO + CONTROL_INFORMATION;

    // Class variables -----------------------------------------------
    /**<jp>
     * データベースコネクションオブジェクト、<code>Connection</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>Connection</code> containing database connection
     </en>*/
    private Connection _conn = null;

    /**<jp>
     * 搬送情報を持つ、<code>CarryInformation</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>CarryInformation</code>, containing carry information.
     </en>*/
    private CarryInfo _carryInfo;

    /**<jp>
     * パレット情報を持つ、<code>Pallet</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>Pallet</code>, containing pallet information.
     </en>*/
    private Pallet _pallet;

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
        return ("$Revision: 4843 $,$Date: 2009-08-20 16:45:35 +0900 (木, 20 8 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id05()
    {
        super();
    }

    /**<jp>
     * 搬送すべき情報を持った<code>CarryInformation</code>インスタンスを指定して、この
     * クラスのインスタンスを生成します。
     * @param  ci   搬送情報を持つ<code>CarryInformation</code>
     * @param  conn データベースコネクション
     </jp>*/
    /**<en>
     * Generates instance of this class by specifying the instance <code>CarryInformation</code>,
     * which owns information to carry.
     * @param  ci   <code>CarryInformation</code> which owns carry information
     * @param  conn database connection object
     </en>*/
    public As21Id05(CarryInfo ci, Connection conn)
    {
        super();
        setCarryInfo(ci);
        setConnection(conn);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送指示電文を作成します。
     * <p>搬送指示を出すのに必要な</p>
     * <ul>
     * <li>MC Key
     * <li>搬送区分
     * <li>設定区分
     * <li>Group No.
     * <li>搬送元Station No.
     * <li>搬送先Station No.
     * <li>Location No.
     * <li>荷姿情報
     * <li>BC Data
     * <li>作業No.
     * <li>制御情報
     * </ul>
     * <p>は、コンストラクタで渡された<code>CarryInformation</code>のインスタンスから情報を入手します。
     * </p>
     * @return 搬送指示電文
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates communication message of carry instruction.
     * <p></p>required to releasae carry instruction
     * <ul>
     * <li>MC Key
     * <li>transport segment
     * <li>setting classification
     * <li>Group number
     * <li>Sending station number
     * <li>Receiving station number
     * <li>Location number
     * <li>load size
     * <li>BC Data
     * <li>work number
     * <li>control information
     * </ul>
     * <p> acquires information out of instance of <code>CarryInformation</code> given in constructor.
     * </p>
     * @return communication message for carrying instruction
     * @throws InvalidProtocolException  : Notifies if communication message includes improper contents from the protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        try
        {
            // パレット情報を検索する
            // 搬送指示テキスト用のパレット情報検索
            PalletHandler palletHandelr = new PalletHandler(getConnection());
            PalletSearchKey palletKey = new PalletSearchKey();
            Pallet pallet = null;
            palletKey.setPalletId(getCarryInfo().getPalletId());
            pallet = (Pallet)palletHandelr.findPrimary(palletKey);
            setPallet(pallet);

            //-------------------------------------------------
            //<jp> ここから先、順序が重要なので注意!</jp>
            //<en> Attention! Order of the contents must be observed!</en>
            //-------------------------------------------------
            // MC Key
            mbuf.append(getMCKey());
            //<jp> 搬送区分(これはCarryInformationにある情報)</jp>
            //<en> Transport classification (included in Carry Information)</en>
            mbuf.append(getTransClass());
            //<jp> 設定区分(これはStationにある情報)</jp>
            //<en> Setting classification (included in Station)</en>
            mbuf.append(getSettingClass());
            //<jp> Group No.(これはCarryInformationにある情報)</jp>
            //<en> Group number(included in Carry Information)</en>
            mbuf.append(getGroupNo());
            //<jp> 搬送元Station No.(これはCarryInformationにある情報)</jp>
            //<en> Sending station number (included in Carry Information)</en>
            mbuf.append(getFromStationNo());
            //<jp> 搬送先Station No.(これはCarryInformationにある情報)</jp>
            //<en> Receiving station number (included in Carry Information)</en>
            mbuf.append(getDestStationNo());
            //<jp> Location No.(これはCarryInformationにある情報)</jp>
            //<en> Location number (included in Carry Information)</en>
            mbuf.append(getDestLocationNo());
            //<jp> 荷姿情報  (これはPalletにある情報)</jp>
            //<en> Load size (included in Pallet)</en>
            mbuf.append(getDimensionInfo());
            //<jp> BC Data (これはPalletにある情報)</jp>
            //<en> BC Data (included in Pallet)</en>
            mbuf.append(getBcData());
            //<jp> 作業No.(これはCarryInformationにある情報)</jp>
            //<en> Work number (included in Carry Information)</en>
            mbuf.append(getWorkNo());
            //<jp> 制御情報(これはCarryInformationにある情報)</jp>
            //<en> Control information (included in Carry Information)</en>
            mbuf.append(getControlInfo());

            //-------------------------------------------------
            //<jp> 送信メッセージバッファに設定</jp>
            //<en> Sets as sending message buffer</en>
            //-------------------------------------------------
            // id
            setID("05");
            //<jp> id 区分</jp>
            //<en> id segment</en>
            setIDClass("00");
            //<jp> 送信時刻</jp>
            //<en> time sent</en>
            setSendDate();
            //<jp> AGC送信時刻</jp>
            //<en> time sent to AGC</en>
            setAGCSendDate();
            //<jp> テキスト内容</jp>
            //<en> text contents</en>
            setContent(String.valueOf(mbuf));

            return (getFromBuffer(0, LEN_TOTAL));
        }
        catch (Exception e)
        {
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Records errors in the log file.</en>
            // 6026052=送信電文組み立て処理でエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026052, e), getClass().getName());
            throw (new InvalidProtocolException(String.valueOf(e)));
        }
    }

    // Accessor methods -----------------------------------------------
    /**
     * データベースコネクションを返します。
     * @return データベースコネクション
     */
    public Connection getConnection()
    {
        return _conn;
    }

    /**
     * データベースコネクションを設定します。
     * @param connection データベースコネクション
     */
    public void setConnection(Connection connection)
    {
        _conn = connection;
    }

    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>を取得します。
     * @return 現在保持している<code>CarryInformation</code>
     </jp>*/
    /**<en>
     * Retrieve <code>CarryInformation</code> to contain carry information.
     * @return :<code>CarryInformation</code> currently preserved
     </en>*/
    protected CarryInfo getCarryInfo()
    {
        return _carryInfo;
    }

    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>を設定します。
     * @param carryInfo  現在保持している<code>CarryInformation</code>
     </jp>*/
    /**<en>
     * Set <code>CarryInformation</code> to contain carry information.
     * @param carryInfo :<code>CarryInformation</code> currently preserved
     </en>*/
    public void setCarryInfo(CarryInfo carryInfo)
    {
        _carryInfo = carryInfo;
    }

    /**<jp>
     * パレット情報を持つ<code>Pallet</code>を設定します。
     * @param pallet  現在保持している<code>Pallet</code>
     </jp>*/
    /**<en>
     * Set <code>Pallet</code> to contain pallet information.
     * @param pallet :<code>Pallet</code> currently preserved
     </en>*/
    public void setPallet(Pallet pallet)
    {
        _pallet = pallet;
    }

    /**<jp>
     * パレット情報を持つ<code>Pallet</code>を取得します。
     * @return 現在保持している<code>Pallet</code>
     </jp>*/
    /**<en>
     * Retrieve <code>Pallet</code> to contain pallet information.
     * @return :<code>Pallet</code> currently preserved
     </en>*/
    protected Pallet getPallet()
    {
        return _pallet;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>CarryInformation</code>からMC Keyを取り出します。
     * @return MC Key
     * @throws InvalidProtocolException MC Keyのデータ長が指定の長さを超えた場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves MC Key out of <code>CarryInformation</code>.
     * @return MC Key
     * @throws InvalidProtocolException : Reports if MC Key exceeds the allowable length.
     </en>*/
    private String getMCKey()
            throws InvalidProtocolException
    {
        StringBuffer stbuff = new StringBuffer(CARRYKEY);
        //<jp> MC Key(これはCarryInformationにある情報)</jp>
        //<en> MC Key(included in CarryInformation)</en>
        String carryKey = getCarryInfo().getCarryKey();
        if (carryKey.length() > CARRYKEY)
        {
            throw new InvalidProtocolException("carryKey = " + CARRYKEY + "--->" + carryKey);
        }
        else
        {
            stbuff.replace(0, CARRYKEY, "00000000");
            stbuff.replace(CARRYKEY - carryKey.length(), CARRYKEY, carryKey);
        }
        return (String.valueOf(stbuff));
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送区分を取り出します。
     * @return 搬送区分
     * @throws InvalidProtocolException  搬送区分が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves transport classification out of <code>CarryInformation</code>.
     * @return transport classification
     * @throws InvalidProtocolException : Reports if transport classification is not the allowable length.
     </en>*/
    private String getTransClass()
            throws InvalidProtocolException
    {
        //<jp> 搬送区分(これはCarryInformationにある情報)</jp>
        //<en> Transport classification (included in CarryInformation)</en>
        String transportClassification = "";
        String inoutKind = getCarryInfo().getCarryFlag();
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(inoutKind))
        {
            transportClassification = C_STORAGE;
        }
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(inoutKind))
        {
            transportClassification = C_DIRECT_TRANSFER;
        }
        else
        {
            System.err.println("transportClassification = " + transportClassification);
        }
        if (transportClassification.length() != TRANSPORT_CLASSIFICATION)
        {
            throw new InvalidProtocolException("transportClassification = " + TRANSPORT_CLASSIFICATION + "--->"
                    + transportClassification);
        }
        return (transportClassification);
    }

    /**<jp>
     * <code>CarryInformation</code>から設定区分を取り出します。
     * @return 設定区分
     * @throws InvalidProtocolException  ステーションが取得できないか設定区分が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves setting classification out of <code>CarryInformation</code>.
     * @return setting classification
     * @throws InvalidProtocolException : Reports if station can not be acquired or if setting classification is not the allowable length.
     </en>*/
    private String getSettingClass()
            throws InvalidProtocolException
    {
        //<jp> 設定区分(これはStationにある情報)</jp>
        //<en> Setting classification (included in Station)</en>
        String settingClassification = "";
        Station station = null;
        String stationNo = getCarryInfo().getSourceStationNo();

        try
        {
            station = StationFactory.makeStation(getConnection(), stationNo);
        }
        catch (Exception e)
        {
            throw new InvalidProtocolException("makeStation error StationNo = " + stationNo);
        }

        String settingType = station.getSettingType();

        if (Station.SETTING_TYPE_PRECEDE.equals(settingType))
        {
            settingClassification = C_SETTING_IN_ADVANCE;
        }
        else if (Station.SETTING_TYPE_CONFIRM.equals(settingType))
        {
            settingClassification = C_LOAD_CHECK_SETTING;
        }

        if (settingClassification.length() != SETTING_CLASSIFICATION)
        {
            throw new InvalidProtocolException("settingClassification = " + SETTING_CLASSIFICATION + "--->"
                    + settingClassification);
        }
        return (settingClassification);
    }

    /**<jp>
     * <code>CarryInformation</code>からグループ番号を取り出します。
     * @return グループ番号
     * @throws InvalidProtocolException グループ番号が指定の値を超えた場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves group number out of <code>CarryInformation</code>.
     * @return group number
     * @throws InvalidProtocolException : Reports if group number exceeds the allowable value.
     </en>*/
    private String getGroupNo()
            throws InvalidProtocolException
    {
        //<jp> Group No.(これはCarryInformationにある情報)</jp>
        //<en> Group No.(included in CarryInformation)</en>
        int groupNo = getCarryInfo().getGroupNo();
        if (groupNo > 999)
        {
            throw new InvalidProtocolException("group number too large");
        }
        DecimalFormat fmt = new DecimalFormat("000");
        String c_groupNo = fmt.format(groupNo);
        return (c_groupNo);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送元ステーション番号を取り出します。
     * @return 搬送元ステーション番号
     * @throws InvalidProtocolException  搬送元ステーション番号が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves sending station number out of <code>CarryInformation</code>.
     * @return :sending station number
     * @throws InvalidProtocolException : Reports if sending station number is not the allowable length.
     </en>*/
    private String getFromStationNo()
            throws InvalidProtocolException
    {
        //<jp> 搬送元Station No.(これはCarryInformationにある情報)</jp>
        //<en> Sending station number (included in CarryInformation)</en>
        String sourceSt = getCarryInfo().getSourceStationNo();
        if (sourceSt.length() != STATIONNO)
        {
            throw new InvalidProtocolException("Source Station = " + STATIONNO + "--->" + sourceSt);
        }
        return (sourceSt);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送先ステーション番号を取り出します。
     * @return 搬送先ステーション番号
     * @throws InvalidProtocolException  ステーションが取得できないか搬送先ステーション番号が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves receiving station number out of <code>CarryInformation</code>.
     * @return Receiving station number
     * @throws InvalidProtocolException : Reports if station can not be acquired or if receiving station number is not the allowable length.
     </en>*/
    private String getDestStationNo()
            throws InvalidProtocolException
    {
        if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(getCarryInfo().getCarryFlag()))
        {
            String toStno = getCarryInfo().getDestStationNo();
            //<jp> 搬送先ステーション番号がステーション(４桁)ならばそのままセット</jp>
            //<en> If the receiving station is to be the station (4 digits), set as it is.</en>
            if (toStno.length() != STATIONNO)
            {
                throw new InvalidProtocolException("Destination Station = " + STATIONNO + "--->" + toStno);
            }
            return (toStno);
        }
        else
        {
            // UPDATE 2009/07/28 K.Mori
            // return ("9000");
            return getPallet().getWhStationNo();
            // UPDATE 2009/07/28
        }
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送先棚番号を取り出します。
     * @return 搬送先棚番号
     * @throws InvalidProtocolException  ステーションかロケーションが取得できない場合に報告されます。
     </jp>*/
    /**<en>
     * Retrieves location number of the receiving stationout of <code>CarryInformation</code>.
     * @return location number of receiving station
     * @throws InvalidProtocolException : Reports if station or location can not be acquired.
     </en>*/
    private String getDestLocationNo()
            throws InvalidProtocolException
    {
        Station toSt = null;
        String locationNo;

        String toStno = getCarryInfo().getDestStationNo();
        //<jp> 搬送先ステーション番号がステーション(12桁)ならばそのままセット</jp>
        //<en> If the receiving station number is the station (12 digits), set as it is.</en>
        if (toStno.length() != LOCATION_NO)
        {
            try
            {
                toSt = StationFactory.makeStation(getConnection(), toStno);
            }
            catch (Exception e)
            {
                throw new InvalidProtocolException("makeStation error StationNo = " + toStno);
            }

            if (toSt instanceof Shelf)
            {
                Shelf shelf = (Shelf)toSt;
                locationNo = shelf.getStationNo();
                if (locationNo.length() != LOCATION_NO)
                {
                    throw new InvalidProtocolException("locationNo = " + LOCATION_NO + "--->" + locationNo);
                }
            }
            else
            {
                byte[] wk = new byte[LOCATION_NO];
                for (int i = 0; i < wk.length; i++)
                {
                    wk[i] = '0';
                }
                locationNo = new String(wk);
            }
        }
        else
        {
            locationNo = toStno;
        }

        return (locationNo);
    }

    /**<jp>
     * <code>Pallet</code>から荷姿情報を取り出します。
     * @return 荷姿情報
     </jp>*/
    /**<en>
     * Acquires load size information out of <code>Pallet</code>.
     * @return loar size
     </en>*/
    private String getDimensionInfo()
    {
        DecimalFormat fmt1 = new DecimalFormat("00");
        return (fmt1.format(getPallet().getHeight()));
    }

    /**<jp>
     * <code>CarryInformation</code>から作業番号を取り出します。
     * @return 作業番号
     </jp>*/
    /**<en>
     * Acquires work number out of <code>CarryInformation</code>.
     * @return work number
     </en>*/
    private String getWorkNo()
    {
        String tmpwnum = getCarryInfo().getWorkNo();
        if (StringUtil.isBlank(tmpwnum))
        {
            tmpwnum = "";
        }
        String wnum = operateMessage(tmpwnum, WORK_NO);

        return (wnum);
    }

    /**<jp>
     * <code>Pallet</code>からバーコード情報を取り出します。
     * @return バーコード情報
     </jp>*/
    /**<en>
     * Acquires bar code information out of <code>Pallet</code>
     * @return bar code information
     </en>*/
    private String getBcData()
    {
        String tmpBcd = getPallet().getBcrData();
        if (StringUtil.isBlank(tmpBcd))
        {
            tmpBcd = "";
        }
        String bcd = operateMessage(tmpBcd, BC_DATA);

        return (bcd);
    }

    /**<jp>
     * <code>CarryInformation</code>から制御情報を取り出します。
     * @return 制御情報
     </jp>*/
    /**<en>
     * Retrieves control information out of <code>CarryInformation</code>.
     * @return control information
     </en>*/
    private String getControlInfo()
    {
        String tmpController = getCarryInfo().getControlinfo();
        if (StringUtil.isBlank(tmpController))
        {
            tmpController = "";
        }
        String ci = operateMessage(tmpController, CONTROL_INFORMATION);

        return (ci);
    }
}
//end of class
