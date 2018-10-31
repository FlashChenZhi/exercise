// $Id: SystemTextTransmission.java 8057 2013-05-24 10:18:15Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Arrays;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

/**
 * <jp> AS21通信プロトコルに対応した、各種テキスト送信を行なうためのメソッドを持つクラスです。<BR>
 * AGCに対してテキストの送信を行なう場合、本クラスに定義されているメソッドを使用します。 このクラスでは、要求された内容をもとに、送信テキストの組み立てを行ないます。 <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2001/07/01</TD>
 * <TD>mori</TD>
 * <TD>created this class</TD>
 * </TR>
 * <TR>
 * <TD>2004/04/01</TD>
 * <TD>kubo</TD>
 * <TD>messageSendメソッドでIOExceptionをcatchする</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 8057 $, $Date: 2013-05-24 19:18:15 +0900 (金, 24 5 2013) $
 * @author $Author: kishimoto $ </jp>
 */
/**
 * <en> This class preserves the method for sending each type of texts according to AS21
 * communication protocol.<br>
 * The method defined in this class is used when text is being sent to AGC. In this class, text is
 * send is composed based on the request. <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2001/07/01</TD>
 * <TD>mori</TD>
 * <TD>created this class</TD>
 * </TR>
 * <TR>
 * <TD>2004/04/01</TD>
 * <TD>kubo</TD>
 * <TD>Catch the IO Exception by messageSend method.</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 8057 $, $Date: 2013-05-24 19:18:15 +0900 (金, 24 5 2013) $
 * @author $Author: kishimoto $ </en>
 */
public class SystemTextTransmission extends Object
{
	/** <code>AGC_NUMBER_LENGTH</code> */
	private static final int AGC_NUMBER_LENGTH = 2;

	/** <code>AGC_PREFIX</code> */
	private static final String AGC_PREFIX = "AGC";

	// Class fields --------------------------------------------------
	/**
	 * <jp> MCKEYの長さをあらわすフィールド </jp>
	 */
	/**
	 * <en> Length of MC Key </en>
	 */
	protected static final int LEN_CARRYKEY = 8;

	/**
	 * <jp> 完了区分を表すフィールド(二重格納) </jp>
	 */
	/**
	 * <en> Classification field for completion (location occupied) </en>
	 */
	public static final int CLASS_DOBULE_STRAGE = 0;

	/**
	 * <jp> 完了区分を表すフィールド(空出荷時) </jp>
	 */
	/**
	 * <en> Classification field for completion (empty retrieval) </en>
	 */
	public static final int CLASS_RETRIEVAL_ERROR = 1;

	/**
	 * <jp> 完了区分を表すフィールド(荷姿不一致) </jp>
	 */
	/**
	 * <en> Classification field for completion (load size mismatch) </en>
	 */
	public static final int CLASS_LOAD_MISALIGNMENT = 2;

	private static final String LOG_NAME = SystemTextTransmission.class.getName();

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	/**
	 * <jp> このクラスのバージョンを返します。
	 * @return バージョンと日付 </jp>
	 */
	/**
	 * <en> Returns the version of this class.
	 * @return Version and the date </en>
	 */
	public static String getVersion()
	{
		return("$Revision: 8057 $,$Date: 2013-05-24 19:18:15 +0900 (金, 24 5 2013) $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * <jp> 作業開始要求を送信処理するメソッド
	 * @param gcNo
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Method of transmission of work start request
	 * @param gcNo
	 *        No. of GroupController
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id01send(String gcNo) throws Exception
	{
		As21Id01 as21id01 = new As21Id01();
		String sendMsg = as21id01.getSendMessage();
		messageSend(sendMsg, gcNo);
	}

	/**
	 * <jp> 日付・時刻Data送信処理
	 * @param agc
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Transmission of date and time Data
	 * @param agc
	 *        No. of agc
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id02send(String agc) throws Exception
	{
		As21Id02 as21id02 = new As21Id02();
		String sendMsg = as21id02.getSendMessage();
		messageSend(sendMsg, agc);
	}

	/**
	 * <jp> 作業終了要求の送信を行います。 このメソッド内ではGroupControllerのStatusを「終了予約」にしませんので、アプリ（画面）側で行う必要があります。
	 * @param gcNo
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @param reqInfoClass
	 *        要求区分 0:通常終了(残作業なしで終了) 1:強制終了１(残作業があっても終了) 2:強制終了２(残作業があったら削除して終了)
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Transmission of work completion request In this method, status of GroupController will
	 * not be shifted to 'reserved for termination'. Therefore, it must be done by application
	 * side(display).
	 * @param gcNo
	 *        No. of GroupController
	 * @param reqInfoClass
	 *        request classification 0:forced termination (terminated with no remaining job)<BR>
	 *        1:forced termination 1 (terminated regardless of remaining jobs)<BR>
	 *        2:forced termination 2 (terminated ; having deleted any remaining jobs)
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id03send(String gcNo, String reqInfoClass) throws Exception
	{
		As21Id03 as21id03 = new As21Id03();
		as21id03.setRequestClass(reqInfoClass);
		String sendMsg = as21id03.getSendMessage();
		messageSend(sendMsg, gcNo);
	}

	/**
	 * <jp> 指定されたCarryInformationの内容をもとに、搬送DataCancel要求の送信を行ないます。
	 * @param ci
	 *        送DataCancel対象となる<code>CarryInformation</code>インスタンス
	 * @param conn
	 *        データベースの接続コネクション
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Carry Data Cancel request
	 * @param ci
	 *        CarryInformation
	 * @param conn
	 *        Connection
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id04send(CarryInfo ci, Connection conn) throws Exception
	{
		PalletSearchKey pSkey = new PalletSearchKey();
		PalletHandler pHandler = new PalletHandler(conn);
		pSkey.setPalletId(ci.getPalletId());
		Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

		Station st = StationFactory.makeStation(conn, pallet.getCurrentStationNo());
		GroupController agc = null;

		if(st instanceof Shelf)
		{
			st = StationFactory.makeStation(conn, st.getParentStationNo());
			agc = GroupController.getInstance(conn, st.getControllerNo());
		}
		else
		{
			agc = GroupController.getInstance(conn, st.getControllerNo());
		}

		if(agc.getStatus() == GroupController.STATUS_ONLINE)
		{
			As21Id04 as21id04 = new As21Id04(ci, pallet);
			String sendMsg = as21id04.getSendMessage();
			messageSend(sendMsg, agc.getNo());
		}
		else
		{
			// <jp> 6024003=グループコントローラーがオフラインのため送信不可。</jp>
			// <en> 6024003=Cannot send data. Group controller is offline.</en>
			Object[] tObj = {"AGC_CONDITION_ERR",};
			RmiMsgLogClient.write(6024003, LogMessage.F_ERROR, LOG_NAME, tObj);
			throw new InvalidStatusException(String.valueOf(6024003));
		}
	}

	/**
	 * <jp> 搬送先変更指示の送信を行ないます。
	 * @param ci
	 *        搬送変更指示の対象となる<code>CarryInformation</code>インスタンス
	 * @param agcdata
	 *        搬送先変更指示テキストにセットするAGCデータの内容
     * @param isNoDest
     *        trueの場合、搬送先変更不可を送信
	 * @param conn
	 *        データベースとのコネクション
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Instruction of destination change
	 * @param ci
	 *        CarryInformation
	 * @param agcdata
	 *        AGC data
     * @param isNoDest
     *        true:Send void locaton.
	 * @param conn
	 *        Connection
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	@SuppressWarnings("cast")
	public static void id08send(CarryInfo ci, String agcdata, boolean isNoDest, Connection conn) throws Exception
	{
		PalletSearchKey pSkey = new PalletSearchKey();
		PalletHandler pHandler = new PalletHandler(conn);
		pSkey.setPalletId(ci.getPalletId());
		Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

		String mcKey = getMCKey(ci);
		String locationNo = "";
		String rejectStationNo = "";
		boolean modeCommand;
		String agc;

		// V3.5.3 isNoDestがtrueのとき、ロケーションなし
		if(isNoDest)
		{
			locationNo = getVoidLocationNo();
			rejectStationNo = getVoidStationNo();
			modeCommand = false;
			Station st = StationFactory.makeStation(conn, pallet.getCurrentStationNo());

			// <jp> 送信すべきAGCを決定</jp>
			// <en> Determine AGC to send to</en>
			if(st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNo());
				agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
			}
			else
			{
				agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
			}
		}
		else
		{
			Station st = StationFactory.makeStation(conn, ci.getDestStationNo());
			if(st instanceof Shelf)
			{
				locationNo = st.getStationNo();
				rejectStationNo = getVoidStationNo();
			}
			else if(st instanceof Station)
			{
				rejectStationNo = st.getStationNo();
				locationNo = getVoidLocationNo();
			}
			modeCommand = true;

			// <jp> 送信すべきAGCを決定</jp>
			// <en> Determine AGC to send to</en>
			if(st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNo());
				agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
			}
			else
			{
				agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
			}

		}
		As21Id08 as21id08 = new As21Id08(mcKey, modeCommand, locationNo, rejectStationNo, agcdata);
		String sendMsg = as21id08.getSendMessage();
		messageSend(sendMsg, agc);
	}

	/**
	 * <jp> 機器状態要求の送信をおこないます。
	 * @param gcNo
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Machine state request
	 * @param gcNo
	 *        no. of GroupController
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id10send(String gcNo) throws Exception
	{
		As21Id10 as21id10 = new As21Id10();
		String sendMsg = as21id10.getSendMessage();
		messageSend(sendMsg, gcNo);
	}

	/**
	 * <jp> 代替棚指示の送信をおこないます。
	 * @param ci
	 *        代替棚指示の対象となる<code>CarryInformation</code>インスタンス
	 * @param rclass
	 *        区分 0:二重格納 1:空出荷 2:荷姿不一致
	 * @param stat
	 *        代替棚の有無。代替棚またはステーションが存在する場合はtrue、ない場合はfalseをセットします。
	 * @param conn
	 *        データベースとのコネクション
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Sending the alternative location instruction.
	 * @param ci
	 *        CarryInformation
	 * @param rclass
	 *        classification 0:location occupied 1:empty retrieval 2:load size mismatch
	 * @param stat
	 *        Presence or absence of alternative location. If there are any alternative loaction or
	 *        station, it returns 'true'. If there is none. it returns 'false'.
	 * @param conn
	 *        Connection
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	@SuppressWarnings("cast")
	public static void id11send(CarryInfo ci, int rclass, boolean stat, Connection conn)
			throws Exception
	{
		PalletSearchKey pSkey = new PalletSearchKey();
		PalletHandler pHandler = new PalletHandler(conn);
		pSkey.setPalletId(ci.getPalletId());
		Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

		Station st;
		String locationNo = null;
		String rejectStationNo = null;
		String requestClass = null;

		As21Id11 as21id11 = new As21Id11();

		// <jp> 搬送先ステーションNo取得</jp>
		// <en> Getting the receiving station number</en>
		String deststno = ci.getDestStationNo();

		if(stat)
		{
			st = StationFactory.makeStation(conn, deststno);
			if(st instanceof Shelf)
			{
				locationNo = st.getStationNo();
				rejectStationNo = getVoidStationNo();
				switch(rclass)
				{
					// <jp> 二重格納</jp>
					// <en> location occupied</en>
					case CLASS_DOBULE_STRAGE:
						requestClass = As21Id11.DUP_NEW_LOCATION;
						break;

					// <jp> 空出荷</jp>
					// <en> empty retrieval</en>
					case CLASS_RETRIEVAL_ERROR:
						// <jp> 標準システムでは無条件でデータキャンセル指示を送信</jp>
						// <en> Cancel instruction is sent without conditions according to the
						// standard system.</en>
						requestClass = As21Id11.EMPTY_DATA_CANCEL;
						break;

					// <jp> 荷姿不一致</jp>
					// <en> load size mismatch</en>
					case CLASS_LOAD_MISALIGNMENT:
						requestClass = As21Id11.DIM_NEW_LOCATION;
						break;

					default:
						break;
				}
			}
			else if(st instanceof Station)
			{
				rejectStationNo = st.getStationNo();
				locationNo = getVoidLocationNo();
				switch(rclass)
				{
					// <jp> 二重格納</jp>
					// <en> location occupied</en>
					case CLASS_DOBULE_STRAGE:
						requestClass = As21Id11.DUP_PAID;
						break;

					// <jp> 空出荷</jp>
					// <en> empty retrieval</en>
					case CLASS_RETRIEVAL_ERROR:
						// <jp> 標準システムでは無条件でデータキャンセル指示を送信</jp>
						// <en> Cancel instruction is sent without conditions according to the
						// standard system.</en>
						requestClass = As21Id11.EMPTY_DATA_CANCEL;
						break;

					// <jp> 荷姿不一致</jp>
					// <en> load size mismatch</en>
					case CLASS_LOAD_MISALIGNMENT:
						requestClass = As21Id11.DIM_PAID;
						break;

					default:
						break;
				}
			}
		}
		else
		{
			locationNo = getVoidLocationNo();
			rejectStationNo = getVoidStationNo();
			st = StationFactory.makeStation(conn, pallet.getCurrentStationNo());
			switch(rclass)
			{
				// <jp> 二重格納</jp>
				// <en> location occupied</en>
				case CLASS_DOBULE_STRAGE:
					requestClass = As21Id11.DUP_NO_SUBSHELF;
					break;

				// <jp> 空出荷</jp>
				// <en> empty retrieval</en>
				case CLASS_RETRIEVAL_ERROR:
					// <jp> 標準システムでは無条件でデータキャンセル指示を送信</jp>
					// <en> Cancel instruction is sent without conditions according to the standard
					// system.</en>
					requestClass = As21Id11.EMPTY_DATA_CANCEL;
					break;

				// <jp> 荷姿不一致</jp>
				// <en> load size mismatch</en>
				case CLASS_LOAD_MISALIGNMENT:
					requestClass = As21Id11.DIM_NO_SUBSHELF;
					break;

				default:
					break;
			}
		}

		// <jp> 指示区分をセット</jp>
		// <en> Set the instruciton classification</en>
		as21id11.setRequestClass(requestClass);
		// <jp> MCKeyをセット</jp>
		// <en> Set the MCKey</en>
		String mcKey = getMCKey(ci);
		as21id11.setMcKey(mcKey);
		// <jp> LocationNoをセット</jp>
		// <en> Set the LocationNo</en>
		as21id11.setLocationNo(locationNo);
		// <jp> StationNoをセット</jp>
		// <en> Set the StationNo</en>
		as21id11.setStationNo(rejectStationNo);
		// <jp> 指示区分がNewLocation（荷姿不一致）のとき荷姿情報をセット</jp>
		// <en> Set the load size if the instruction classification states 'NewLocation, load size
		// mismatch'.</en>

		int height = (As21Id11.DIM_NEW_LOCATION.equals(requestClass)) ? pallet.getHeight() : 0;
		as21id11.setDimensionInfo(formatDimensionInfo(height));

		// <jp> BCDataをセット</jp>
		// <en> Set the BCData</en>
		String tmpBcd = pallet.getBcrData();
		if(StringUtil.isBlank(tmpBcd))
		{
			tmpBcd = "";
		}
		String bcd = operateMessage(tmpBcd, As21Id11.BC_DATA);
		as21id11.setBcData(bcd);
		// <jp> 作業Noをセット</jp>
		// <en> Set the work number.</en>
		String tmpnum = ci.getWorkNo();
		if(StringUtil.isBlank(tmpnum))
		{
			tmpnum = "";
		}
		String num = operateMessage(tmpnum, As21Id11.WORK_NO);
		as21id11.setWorkNo(num);
		// <jp> 制御情報をセット</jp>
		// <en> Set the control data.</en>
		String tmpinfo = ci.getControlinfo();
		if(StringUtil.isBlank(tmpinfo))
		{
			tmpinfo = "";
		}
		String info = operateMessage(tmpinfo, As21Id11.CONTROL_INFORMATION);
		as21id11.setControlInfo(info);

		String agc;
		if(st instanceof Shelf)
		{
			st = StationFactory.makeStation(conn, st.getParentStationNo());
			agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
		}
		else
		{
			agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
		}

		String sendMsg = as21id11.getSendMessage();
		messageSend(sendMsg, agc);
	}

	/**
	 * 荷姿情報のフォーマットを行います。
	 * @param height
	 *        荷姿情報
	 * @return 送信用に編集された荷姿情報
	 */
	static String formatDimensionInfo(int height)
	{
		char[] dformat = new char[As21Id11.DIMENSION_INFORMATION];
		Arrays.fill(dformat, '0');

		DecimalFormat fmt = new DecimalFormat(String.valueOf(dformat));
		return fmt.format(height);
	}

	/**
	 * <jp> 一斉起動/停止指示の送信をおこないます。
	 * @param gcNo
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @param b
	 *        起動/停止区分 1:起動 2:停止
	 * @param conn
	 *        データベースとのコネクション
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> All start/stop
	 * @param gcNo
	 *        no. of GroupController
	 * @param b
	 *        classificaiton of start/stop 1:start 2:stop
	 * @param conn
	 *        Connection
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id16send(String gcNo, boolean b, Connection conn) throws Exception
	{
		GroupController gc = GroupController.getInstance(conn, gcNo);
		// <jp> システムの状態を確認する。</jp>
		// <en> Check the status of system.</en>
		if(gc.getStatus() == GroupController.STATUS_ONLINE)
		{
			As21Id16 as21id16 = new As21Id16(b);
			String sendMsg = as21id16.getSendMessage();
			messageSend(sendMsg, gcNo);
		}
	}

	/**
	 * <jp> 伝送Test応答の送信をおこないます。
	 * @param gcNo
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @param msg
	 *        送信TestData
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Respond to the transmission Test
	 * @param gcNo
	 *        no. of GroupController
	 * @param msg
	 *        SendTestData
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id20send(String gcNo, String msg) throws Exception
	{
		As21Id20 as21id20 = new As21Id20(msg);
		String sendMsg = as21id20.getSendMessage();
		messageSend(sendMsg, gcNo);
	}

	/**
	 * <jp> 作業Mode切替要求応答の送信を行ないます。
	 * @param stnum
	 *        応答するStationNo
	 * @param kbn
	 *        応答区分 00:正常受付 01:Error (作業中) 02:Error (Station No.)
	 * @param agc
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Respond to the work mode switch request
	 * @param stnum
	 *        StationNo to respond to
	 * @param kbn
	 *        Classificaiton of response 00:normaly received 01:Error (working) 02:Error (Station
	 *        No.)
	 * @param agc
	 *        Controller no. to send
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id41send(String stnum, String kbn, String agc) throws Exception
	{
		As21Id41 as21id41 = new As21Id41();
		as21id41.setStationNo(stnum);
		as21id41.setRequestClass(kbn);
		String sendMsg = as21id41.getSendMessage();
		messageSend(sendMsg, agc);
	}

	/**
	 * <jp> 作業Mode切替指示の送信を行ないます。
	 * @param st
	 *        切替対象ステーション
	 * @param mode
	 *        切替作業モード 1:入庫Mode(通常) 2:入庫Mode(緊急) 3:出庫Mode(通常) 4:出庫Mode(緊急)
	 * @param conn
	 *        データベースとのコネクション
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Work mode switch instruction
	 * @param st
	 *        station
	 * @param mode
	 *        work mode 1:storage mode-normal 2:storage mode-urgent 3:retrieval mode-normal
	 *        4:retrieval mode-urgent
	 * @param conn
	 *        Connection
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id42send(Station st, String mode, Connection conn) throws Exception
	{
		As21Id42 as21id42 = new As21Id42(st, mode);
		String sendMsg = as21id42.getSendMessage();
		String agc;
		if(st instanceof Shelf)
		{
			Station st2 = StationFactory.makeStation(conn, st.getParentStationNo());
			agc = GroupController.getInstance(conn, st2.getControllerNo()).getNo();
		}
		else
		{
			agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
		}

		messageSend(sendMsg, agc);
	}

	/**
	 * <jp> MC作業完了報告を送信します。
	 * @param ci
	 *        報告対象となる<code>CarryInformation</code>インスタンス
	 * @param conn
	 *        データベースとのコネクション
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> MC worl completion report
	 * @param ci
	 *        carry information
	 * @param conn
	 *        Connection
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id45send(CarryInfo ci, Connection conn) throws Exception
	{
		PalletSearchKey pSkey = new PalletSearchKey();
		PalletHandler pHandler = new PalletHandler(conn);
		pSkey.setPalletId(ci.getPalletId());
		Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

		Station st = StationFactory.makeStation(conn, pallet.getCurrentStationNo());

		if(st instanceof Shelf)
		{
			st = StationFactory.makeStation(conn, ci.getDestStationNo());
		}

		GroupController groupControll = new GroupController(conn, st.getControllerNo());

		if(groupControll.getStatus() == GroupController.STATUS_ONLINE)
		{
			As21Id45 as21id45 = new As21Id45(ci);
			String sendMsg = as21id45.getSendMessage();
			String agc;
			if(st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNo());
				agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
			}
			else
			{
				agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
			}

			messageSend(sendMsg, agc);
		}
		else
		{
			// <jp> 6024003=グループコントローラーがオフラインのため送信不可。</jp>
			// <en> 6024003=Cannot send data. Group controller is offline.</en>
			Object[] tObj = {"AGC_CONDITION_ERR",};
			RmiMsgLogClient.write(6024003, LogMessage.F_ERROR, LOG_NAME, tObj);
			throw new InvalidStatusException(String.valueOf(6024003));
		}
	}

	/**
	 * <jp> アクセス不可棚要求を送信します。
	 * @param agc
	 *        送信対象となるグループコントローラ（AGC）番号
	 * @throws Exception
	 *         障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Inaccessible location request
	 * @param agc
	 *        AGC number
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	public static void id51send(String agc) throws Exception
	{
		As21Id51 as21id51 = new As21Id51();
		String sendMsg = as21id51.getSendMessage();
		messageSend(sendMsg, agc);
	}

    /**
     * DO出力指示
     * @param st
     * @param lampNo
     * @param mode
     * @param conn
     * @throws Exception
     */
    public static void id54send(Station st, String lampNo, String mode, Connection conn)
            throws Exception
    {
        As21Id54 as21id54 = new As21Id54(st, lampNo, mode);
        String sendMsg = as21id54.getSendMessage();
        String agc;
        if (st instanceof Shelf)
        {
            Station st2 = StationFactory.makeStation(conn, st.getParentStationNo());
            agc = GroupController.getInstance(conn, st2.getControllerNo()).getNo();
        }
        else
        {
            agc = GroupController.getInstance(conn, st.getControllerNo()).getNo();
        }

        messageSend(sendMsg, agc);
    }

	/**
	 * 搬送できない場合に使用するロケーションNo.
	 * @return すべて"0"で表現されるロケーションNo.
	 */
	public static String getVoidLocationNo()
	{
		char[] cbuff = new char[As21Id08.LEN_LOCATION];
		Arrays.fill(cbuff, '0');

		return String.valueOf(cbuff);
	}

	/**
	 * 搬送できない場合に使用するロケーションNo.
	 * @return すべて"0"で表現されるロケーションNo.
	 */
	public static String getVoidStationNo()
	{
		char[] cbuff = new char[As21Id08.LEN_STATION];
		Arrays.fill(cbuff, '0');

		return String.valueOf(cbuff);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * <jp> RMIサーバーを経由して、テキスト送信処理タスクに指定されたテキスト送信を指示します。
	 * @param sendMsg
	 *        送信テキストメッセージ
	 * @param agcNo
	 *        送信対象となるグループコントローラー(AGC)番号
	 * @throws Exception
	 *         メッセージが送信できなかった場合に通知されます。 </jp>
	 */
	/**
	 * <en> Send the specified instruction text via RMI server to the text send process task.
	 * @param sendMsg
	 *        Send text message
	 * @param agcNo
	 *        Group controller (AGC) number
	 * @throws Exception
	 *         Notifies if trouble occurs. </en>
	 */
	private static void messageSend(String sendMsg, String agcNo) throws Exception
	{
		Object[] param = new Object[2];
		RmiSendClient rmiSndC = null;
		if(WmsParam.MULTI_ASRSSERVER)
		{
			// <jp> RMIを利用してAs21Senderのwriteメソッドをコール</jp>
			// <en> Calls the write method of As21Sender using RMI.</en>
			rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + agcNo);
		}
		else
		{
	        rmiSndC = new RmiSendClient();
		}
		param[0] = sendMsg;
		try
		{
			rmiSndC.write(formatAGC(agcNo), param);
			rmiSndC = null;
		}
		catch(RuntimeException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			// <jp> 6024030=AGCとの接続が確立されていません。テキストを送信できませんでした。AGCNO={0}</jp>
			// <en> 6024030=Cannot send the text since SRC is not connected. SRC No.={0}</en>
			Object[] tObj = {agcNo,};
			RmiMsgLogClient.write(6024030, LogMessage.F_WARN, SystemTextTransmission.class
					.getName(), tObj);
			throw e;
		}

		param[0] = null;
	}

	/**
	 * AGC Noを送信用に編集します。
	 * @param agcNo
	 *        agcNo.
	 * @return 送信用AGC No
	 */
	static String formatAGC(String agcNo)
	{
		char[] agcFmt = new char[AGC_NUMBER_LENGTH];
		Arrays.fill(agcFmt, '0');

		DecimalFormat fmt = new DecimalFormat(String.valueOf(agcFmt));
		return AGC_PREFIX + fmt.format(Integer.valueOf(agcNo));
	}

	/**
	 * <jp> eWareNavi3.0では、CarryKeyがそのままMCKeyとして使えるように採番されるため、 CarryKeyを加工せずに返します。<br>
	 * 指定された<code>CarryInformation</code>からMCKeyを取り出し、返します。
	 * @param ci
	 *        取り出し対象<code>CarryInformation</code>
	 * @return MCKey
	 * @throws InvalidProtocolException
	 *         MC Keyが指定の長さでなかった場合に報告されます。 </jp>
	 */
	/**
	 * <en> Retrieve MC key from <code>CarryInformation</code>.
	 * @param ci
	 *        CarryInformation
	 * @return MC Key
	 * @throws InvalidProtocolException :
	 *         Reports if MC Key is not the allowable length. </en>
	 */
	public static String getMCKey(CarryInfo ci) throws InvalidProtocolException
	{
		return ci.getCarryKey();
	}

	/**
	 * <jp> メッセージを指定の長さに整形します。 オリジナルが指定より長い場合は切り捨てられ、短い場合は必要数分スペースが埋められます。
	 * @param src
	 *        オリジナル・メッセージ
	 * @param len
	 *        文字列長さ指定
	 * @return 整形後の文字列 </jp>
	 */
	/**
	 * <en> Adjust the message in the specified length. Cut down the length if the original message
	 * is longer than specifiation. If shorter, fill with space to the specified length.
	 * @param src
	 *        Original message
	 * @param len
	 *        Specification of the string length
	 * @return String after adjusted </en>
	 */
	private static String operateMessage(String src, int len)
	{
		int dif = len - src.length();
		if(dif <= 0)
		{
			// <jp> オリジナルメッセージが長い</jp>
			// <en> src too long</en>
			return(src.substring(0, len));
		}
		else
		{
			// <jp> オリジナルメッセージが短い</jp>
			// <en> src too short</en>
			byte[] wb = new byte[dif];
			for(int i = 0; i < dif; i++)
			{
				wb[i] = ' ';
			}
			return(src + new String(wb));
		}
	}
}
// end of class

