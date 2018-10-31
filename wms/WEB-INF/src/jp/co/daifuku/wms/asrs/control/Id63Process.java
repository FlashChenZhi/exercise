// $Id: Id63Process.java 4725 2009-07-22 04:05:38Z shibamoto $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id63;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 作業Mode切替完了報告を処理するクラスです。
 * 指定された完了Modeで該当するStationの現在モードを変更します。
 * 変更の際、Stationの要求中フラグの内容は考慮しません。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4725 $, $Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This class process the completion report of work mode switch. It modifies the current mode
 * of corresponding Station according to the specified completion mode.
 * When modifying, it will not considers the contents of requesting flag of the Station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4725 $, $Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $
 * @author  $Author: shibamoto $
 </en>*/
public class Id63Process
        extends IdProcess
{
    // Class fields --------------------------------------------------

    /**<jp>
     * 完了Mode(入庫)
     </jp>*/
    /**<en>
     * Completion mode (storage)
     </en>*/
    private static final int FAINL_MODE_IN = 1;

    /**<jp>
     * 完了Mode(出庫)
     </jp>*/
    /**<en>
     * Completion mode (retrieval)
     </en>*/
    private static final int FAINL_MODE_OUT = 2;

    // Class variables -----------------------------------------------

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
        return ("$Revision: 4725 $,$Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     * AGCNoにGroupController.DEFAULT_AGC_NUMBERをセット
     </jp>*/
    /**<en>
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     </en>*/
    public Id63Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNumber AGCNo
     </en>*/
    public Id63Process(String agcNumber)
    {
        super(agcNumber);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * 作業Mode切替完了報告受信の処理を行います。
     * 受信した電文のStationNoから、<code>Station</code>を検索し、
     * 作業モードの更新を行います。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the receiving of work mode switch completion report.
     * Based on the Station no. in the communication message received, it searches <code>Station</code>
     * and updates the work mode.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id63 id63dt = new As21Id63(rdt);
        StationAlterKey stakey = new StationAlterKey();
        StationHandler sth = new StationHandler(getConnection());

        Station st = StationFactory.makeStation(getConnection(), (id63dt.getStationNo()));
        int completionMode = id63dt.getCompletionMode();

        switch (completionMode)
        {
            //<jp> 入庫モード切替</jp>
            //<en> Switching to storage mode</en>
            case FAINL_MODE_IN:
                stakey.setStationNo(st.getStationNo());
                stakey.updateCurrentMode(Station.CURRENT_MODE_STORAGE);
                stakey.updateModeRequest(Station.MODE_REQUEST_NONE);
                stakey.updateModeRequestDate(null);
                sth.modify(stakey);
                break;

            //<jp> 出庫モード切替</jp>
            //<en> Switching to retrieval mode</en>
            case FAINL_MODE_OUT:
                stakey.setStationNo(st.getStationNo());
                stakey.updateCurrentMode(Station.CURRENT_MODE_RETRIEVAL);
                stakey.updateModeRequest(Station.MODE_REQUEST_NONE);
                stakey.updateModeRequestDate(null);
                sth.modify(stakey);
                break;

            default:
                // invalid mode
        }

        //<jp> 自動モード切替ステーションの場合、</jp>
        //<jp> 自動モード切替搬送指示をwait()から抜けさせるため、</jp>
        //<jp> メッセ－ジの送信を行なう。</jp>
        //<en> If it was with automatic mode switch station, it sends the message</en>
        //<en> so that the carry instruction of automatic mode switch shall gets out</en>
        //<en> of the loop.</en>
        if (Station.MODE_TYPE_AUTO_CHANGE.equals(st.getModeType()))
        {
        	if (WmsParam.MULTI_ASRSSERVER)
        	{
	            RmiSendClient rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + st.getControllerNo());
	            Object[] param = new Object[2];
	            param[0] = null;
	            //<jp> AutomaticModeChangeSenderをキックします</jp>
	            //<en> Kicks the AutomaticModeChangeSender.</en>
	            rmiSndC.write("AutomaticModeChangeSender" + st.getControllerNo(), param);
	        }
        	else
        	{
                RmiSendClient rmiSndC = new RmiSendClient();
                Object[] param = new Object[2];
                param[0] = null;
                //<jp> AutomaticModeChangeSenderをキックします</jp>
                //<en> Kicks the AutomaticModeChangeSender.</en>
                rmiSndC.write("AutomaticModeChangeSender", param);
        	}
        }
        // オペレーションログ出力
        log_write(id63dt);
    }

    // Private methods -----------------------------------------------
    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   id63dt    As21Id63
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(As21Id63 id63dt)
            throws Exception
    {
        // オペレーションログ出力
        DfkUserInfo userInfo = new DfkUserInfo();
        // DS番号
        userInfo.setDsNumber(DsNumberDefine.DS_AGC_ID63);
        // ユーザID
        userInfo.setUserId(WmsParam.SYS_USER_ID);
        // ユーザ名称
        userInfo.setUserName(WmsParam.SYS_USER_NAME);
        // 端末No
        userInfo.setTerminalNumber(WmsParam.SYS_TERMINAL_NO);
        // 端末名称
        userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        // 端末IPアドレス
        userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        // リソース番号
        userInfo.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AGC_ID63);

        List<String> itemLog = new ArrayList<String>();

        // AGC-No.
        itemLog.add(getAgcNo());
        // ステーションNo
        itemLog.add(id63dt.getStationNo());
        // 完了Mode
        itemLog.add(String.valueOf(id63dt.getCompletionMode()));

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(getConnection());
        opeLogWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_CONTROL_MODULE, itemLog);

    }
}
//end of class
