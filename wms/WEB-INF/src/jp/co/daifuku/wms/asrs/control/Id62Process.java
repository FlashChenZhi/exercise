// $Id: Id62Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id62;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 作業Mode切り替え指示応答を処理するクラスです。
 * 応答区分が正常または作業Mode切替中であれば何も行いません。
 * それ以外のError応答であれば、該当ステーションに対するモード切替要求のキャンセルを行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class processes the response to the work mode switch instruction.
 * If the response classification is either normal or switching the work mode, there will be no
 * processing. For error response with other cases, it cancels the mode switch request at the corresponding station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id62Process
        extends IdProcess
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 応答区分(正常受付)
     </jp>*/
    /**<en>
     * Reponse classification (normal acceptance)
     </en>*/
    private static final String NORMAL = "00";

    /**<jp>
     * 応答区分(Error Mode切替中)
     </jp>*/
    /**<en>
     * Reponse classification (switching to Error Mode)
     </en>*/
    private static final String MODE_CHANGE = "01";

    /**<jp>
     * 応答区分(Error Station No .Error)
     </jp>*/
    /**<en>
     * Reponse classification (Error Station No .Error)
     </en>*/
    private static final String STATION_ERROR = "02";

    /**<jp>
     * 応答区分(Error 指示 Mode)
     </jp>*/
    /**<en>
     *Reponse classification (Error instruction Mode)
     </en>*/
    private static final String MISION_MODE = "03";

    /**<jp>
     * 応答区分(Error 搬送Dataあり)
     </jp>*/
    /**<en>
     * Reponse classification (Error, with Carry data)
     </en>*/
    private static final String H_MODE = "04";

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
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
    public Id62Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNo AGCNo
     </en>*/
    public Id62Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 作業Mode切り替え指示応答の処理を行います。
     * 受信テキストのStationNoから、<code>Station</code> を検索し、
     * 応答区分を元に作業モード切替要求の更新を行います。
     * 正常、作業Mode切替中であれば何も行いません。
     * それ以外のError応答であれば、該当ステーションに対するモード切替要求のキャンセルを行います。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the response to the work mode switch instruction.
     * Based on the StationNO. of the text received, search <code>Station</code>.
     * Then based on the reponse classification, update the work mode switch request.
     * There will be no processing if it is normal or if switching the work mode.
     * If the response was error for other cases, it cncels the mode switch request at corresponding stations.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id62 id62dt = new As21Id62(rdt);
        StationAlterKey sakey = new StationAlterKey();
        StationHandler sh = new StationHandler(getConnection());
        Station st = StationFactory.makeStation(getConnection(), id62dt.getStationNo());

        String responseClassification = id62dt.getResponseClassification();

        if (NORMAL.equals(responseClassification))
        {
            //<jp> 正常時は処理は行わない</jp>
            //<en> If normally processed, there will be no processing.</en>
        }
        else if (MODE_CHANGE.equals(responseClassification))
        {
            //<jp> モード切替中 問題なしと判断し</jp>
            //<jp> 完了報告を待つ(何もしない)</jp>
            //<en> If mode is being switched, it determines there should be no problem and waits for </en>
            //<en> completion report. (there will be no processing)</en>
        }
        else if (STATION_ERROR.equals(responseClassification))
        {
            //<jp> 6022023=指示されたステーションNo.が不正なため、モード切替要求は受付けられませんでした。StationNo={0}</jp>
            //<en> 6022023=Unable to accept the mode change request due to invalid station No. ST No={0}</en>
            Object[] tObj = new Object[1];
            tObj[0] = st.getStationNo();
            RmiMsgLogClient.write(6022023, LogMessage.F_ERROR, getClass().getName(), tObj);

            //<jp> モード切替要求のキャンセルを行う。</jp>
            //<en> Cancels the mode switch request.</en>
            sakey.setStationNo(st.getStationNo());
            sakey.updateModeRequest(Station.MODE_REQUEST_NONE);
            sakey.updateModeRequestDate(null);
            sh.modify(sakey);
        }
        else if (MISION_MODE.equals(responseClassification))
        {
            //<jp> 6022024=指示されたモードが不正なため、モード切替要求は受付けられませんでした。StationNo={0}</jp>
            //<en> 6022024=Unable to accept the mode change request due to invalid mode. ST No={0}</en>
            Object[] tObj = new Object[1];
            tObj[0] = st.getStationNo();
            RmiMsgLogClient.write(6022024, LogMessage.F_ERROR, getClass().getName(), tObj);

            //<jp> モード切替要求のキャンセルを行う。</jp>
            //<en> Cancels the mode switch request.</en>
            sakey.setStationNo(st.getStationNo());
            sakey.updateModeRequest(Station.MODE_REQUEST_NONE);
            sakey.updateModeRequestDate(null);
            sh.modify(sakey);
        }
        else if (H_MODE.equals(responseClassification))
        {
            //<jp> 6022025=搬送データが存在するため、モード切替要求は受付けられませんでした。StationNo={0}</jp>
            //<en> 6022025=Unable to accept the mode change request. Transfer data exists. ST No={0}</en>
            Object[] tObj = new Object[1];
            tObj[0] = st.getStationNo();
            RmiMsgLogClient.write(6022025, LogMessage.F_ERROR, getClass().getName(), tObj);

            //<jp> モード切替要求のキャンセルを行う。</jp>
            //<en> Cancels the mode switch request.</en>
            sakey.setStationNo(st.getStationNo());
            sakey.updateModeRequest(Station.MODE_REQUEST_NONE);
            sakey.updateModeRequestDate(null);
            sh.modify(sakey);
        }
        else
        {
            throw (new InvalidProtocolException("Invalid Completion Class for Incoming"));
        }
    }
}
