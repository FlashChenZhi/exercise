// $Id: Id61Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id61;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.db.SysDate;

/**<jp>
 * 作業Mode切り替え要求を処理するクラスです。
 * 指定された要求区分で該当するStationのモード切替要求中フラグを変更します。
 * AGCに対しては、該当するStationが見つからない場合や処理に失敗した場合を除き、常に正常受付を返します。
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
 * This class proceses the work mode switch request. It switches the mode switch requesting flag of the 
 * corresponding Station according to the specified request classification.
 * Concerning AGC, it always returns the normal acceptance excepting the cases where corresponding station cannot be found
 * or the case where the process failed.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id61Process
        extends IdProcess
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 要求区分 (入庫)
     </jp>*/
    /**<en>
     * Request classification (storage)
     </en>*/
    private static final int REQUEST_CLASS_IN = 1;

    /**<jp>
     * 要求区分 (出庫)
     </jp>*/
    /**<en>
     * Request classification (retrieval)
     </en>*/
    private static final int REQUEST_CLASS_OUT = 2;

    /**<jp>
     * 要求区分 (入庫要求Cancel)
     </jp>*/
    /**<en>
     * Request classification (storage request cancel)
     </en>*/
    private static final int REQUEST_CLASS_IN_CANCEL = 3;

    /**<jp>
     * 要求区分 (出庫要求Cancel)
     </jp>*/
    /**<en>
     * Request classification (retrieval request cancel)
     </en>*/
    private static final int REQUEST_CLASS_OUT_CANCEL = 4;

    /**<jp>
     * 応答区分 (正常受付)
     </jp>*/
    /**<en>
     * Response classification (normaly received)
     </en>*/
    private static final String RESPONCE_OK = "00";

    /**<jp>
     * 応答区分 (Error (作業中))
     </jp>*/
    /**<en>
     * Response classification (Error (working))
     </en>*/
    @SuppressWarnings("unused")
    private static final String RESPONCE_NG_WORKING = "01";

    /**<jp>
     * 応答区分 (Error (Station No.))
     </jp>*/
    /**<en>
     * Response classification (Error (Station No.))
     </en>*/
    private static final String RESPONCE_NG_STATION = "02";

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
    public Id61Process()
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
    public Id61Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 作業Mode切り替え要求の処理を行います。
     * 受信テキストのStationNoから、<code>Station</code> を検索し、要求区分を元に作業モード切替要求を行います。
     * 正常に処理が行われた場合は、正常応答をAGCに送信します。
     * Stationが見つからなかった場合や、
     * 処理に失敗した場合はStationNo ErrorをAGCに送信します。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the work mode switch reequest.
     * Search <code>Station</code> based on the StationNo. of text received, then request 
     * for the work mode switch based on the request classification.
     * If normally processed, it sends the normal response to AGC.
     * In case the Station was not found, or in case the process failed, it sends StationNo Error to AGC.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        StationAlterKey stakey = new StationAlterKey();
        StationHandler sth = new StationHandler(getConnection());

        String wReqinfo = null;
        As21Id61 id61dt = new As21Id61(rdt);
        Station st = null;

        try
        {
            st = StationFactory.makeStation(getConnection(), id61dt.getStationNo());
            try
            {
                int req = id61dt.getRequestClassification();
                switch (req)
                {
                    //<jp> 入庫モード切替要求</jp>
                    //<en> Requests to switch to the storage mode</en>
                    case REQUEST_CLASS_IN:
                        stakey.setStationNo(st.getStationNo());
                        stakey.updateModeRequest(Station.MODE_REQUEST_STORAGE);
                        stakey.updateModeRequestDate(new SysDate());
                        sth.modify(stakey);

                        wReqinfo = RESPONCE_OK;
                        break;

                    //<jp> 出庫モード切替要求</jp>
                    //<en> Requests to switch to the retrieval mode</en>
                    case REQUEST_CLASS_OUT:
                        stakey.setStationNo(st.getStationNo());
                        stakey.updateModeRequest(Station.MODE_REQUEST_RETRIEVAL);
                        stakey.updateModeRequestDate(new SysDate());
                        sth.modify(stakey);

                        wReqinfo = RESPONCE_OK;
                        break;

                    //<jp> 入庫モード切替要求Cancel</jp>
                    //<en> Cancels the request to switch to the retrieval mode</en>
                    case REQUEST_CLASS_IN_CANCEL:
                        stakey.setStationNo(st.getStationNo());
                        stakey.updateModeRequest(Station.MODE_REQUEST_NONE);
                        stakey.updateModeRequestDate(null);
                        sth.modify(stakey);

                        wReqinfo = RESPONCE_OK;
                        break;

                    //<jp> 出庫モード切替要求Cancel</jp>
                    //<en> Cancels the request to switch to the storage mode</en>
                    case REQUEST_CLASS_OUT_CANCEL:
                        stakey.setStationNo(st.getStationNo());
                        stakey.updateModeRequest(Station.MODE_REQUEST_NONE);
                        stakey.updateModeRequestDate(null);
                        sth.modify(stakey);

                        wReqinfo = RESPONCE_OK;
                        break;

                    default:
                        wReqinfo = RESPONCE_NG_STATION;
                }
                //<jp>作業モード切替要求応答送信処理</jp>
                //<en>Submitting the reply to work mode switch request.</en>
                SystemTextTransmission.id41send(st.getStationNo(), wReqinfo, getAgcNo());
            }
            catch (Exception e)
            {
                //<jp> 受信テキストに対する更新処理に失敗した場合、StationNo Errorを送信する。</jp>
                //<en> In case the update of received text failed, it sends StationNo Error.</en>
                wReqinfo = RESPONCE_NG_STATION;
                SystemTextTransmission.id41send(id61dt.getStationNo(), wReqinfo, getAgcNo());
                throw e;
            }
        }
        catch (Exception e)
        {
            //<jp> 受信テキストのステーションインスタンスの生成に失敗した場合、StationNo Errorを送信する。</jp>
            //<en> If the generation of station instance of the received text failed, it sends StationNo Error.</en>
            wReqinfo = RESPONCE_NG_STATION;
            SystemTextTransmission.id41send(id61dt.getStationNo(), wReqinfo, getAgcNo());
            throw e;
        }
    }
}
//end of class
