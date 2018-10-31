// $Id: Id68Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id68;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.db.SysDate;

/**<jp>
 * 作業表示トリガ報告を処理するクラスです。
 * OPERATIONDISPLAYに受信内容を記録し、作業指示画面の表示対象とします。
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
 * This class processes the on-line indication trigger report. It records the contents received in 
 * OPERATIONDISPLAY, then keeps them subject to indication for on-line operation.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id68Process
        extends IdProcess
{
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
     * GroupController.DEFAULT_AGC_NUMBER is set to AGCNo
     </en>*/
    public Id68Process()
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
    public Id68Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 作業表示トリガ報告電文を処理します。
     * OPERATIONDISPLAYに受信内容をセットします。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the communication message of on-line indication trigger report.
     * Sets the contents received to OPERATIONDISPLAY.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        //<jp> As21Id68のインスタンス生成</jp>
        //<en> Generates the instance of As21Id68.</en>
        As21Id68 id68dt = new As21Id68(rdt);

        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();

        //<jp> MC keyを検索条件として設定</jp>
        //<en> Set the MC key as a search condition.</en>
        cskey.setCarryKey(id68dt.getMcKey());

        //<jp> 該当のCarryInfoを取得</jp>
        //<en> Retrieve the corresponding CarryInfo.</en>
        CarryInfo[] carryInfo = (CarryInfo[])cih.find(cskey);

        //<jp> 該当データなし</jp>
        //<en> There is no corresponding data.</en>
        if (carryInfo.length == 0)
        {
            //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
            //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
            Object[] tObj = {
                id68dt.getMcKey(),
            };
            RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
            return;
        }

        //<jp> 作業指示、作業表示ステーションのみ登録する。</jp>
        //<en> Register only the on-line indication and the station that has on-line indication function.</en>
        Station st = null;
        try
        {
            st = StationFactory.makeStation(getConnection(), id68dt.getStationNo());
        }
        catch (NotFoundException e)
        {
            //<jp> 指定されたステーションが存在しない場合は</jp>
            //<jp> そのまま本ID処理を終了する。例外扱いにはしない</jp>
            //<en> If there is no corresponding data, </en>
            //<en> no handling of exception is done but terminates this ID processing jsut as it is. </en>
            return;
        }

        String operationDisplay = st.getOperationDisplay();
        if ((Station.OPERATION_DISPLAY_DISP_ONLY.equals(operationDisplay))
                || (Station.OPERATION_DISPLAY_INSTRUCTION.equals(operationDisplay)))
        {
            OperationDisplay od = new OperationDisplay();
            OperationDisplayHandler odh = new OperationDisplayHandler(getConnection());
            //<jp> OPERATIONDISPLAYの情報に記録する。</jp>
            //<en> Records in OPERATIONDISPLAY data.</en>
            od.setCarryKey(id68dt.getMcKey());
            od.setStationNo(id68dt.getStationNo());
            od.setArrivalDate(new SysDate());
            od.setRegistPname(getClass().getSimpleName());
            od.setLastUpdatePname(getClass().getSimpleName());

            odh.create(od);
        }
    }
}
//end of class

