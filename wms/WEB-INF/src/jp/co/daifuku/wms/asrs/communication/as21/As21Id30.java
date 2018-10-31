// $Id: As21Id30.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

import jp.co.daifuku.common.InvalidProtocolException;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「機器状態報告(MachineStatusReport) ID=30」電文を処理します。
 * 機器状態報告(MachineStatusReport)電文から報告機器状態を取得します。<BR>
 * 内部的にはMachineStateクラスからインスタンスを生成し報告件数分の機器状態をセットします。<BR>
 * 詳細は機種Code、号機No.毎の状態(運転中 停止中 故障中 切離し中)と異常Codeがセットされます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Processes comunication message "Machine Status Report, ID=30" according to AS21 communication protocol.
 * Acquires the status of machines from the communication message "Machine State Report".<BR>
 * Internally, it generates instance of MachineState class and sets as many machine states as numbers of reports.<BR>
 * In detail, following will be set: machine code, state of each machine per code (operating/stopped/down/cutting off)
 * and the error codes.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class As21Id30
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 電文の長さ(byte)の初期設定
     </jp>*/
    /**<en>
     * Initial setting for the length of communication message (in byte)
     </en>*/
    public int LEN_ID30 = 0;

    /**<jp>
     * 継続区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of continuation classification
     </en>*/
    private static final int OFF_ID30_CONTINU_CLASS = 0;

    /**<jp>
     * 継続区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of continuation classsification (in byte)
     </en>*/
    private static final int LEN_ID30_CONTINU_CLASS = 1;

    /**<jp>
     * 報告件数のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of number of reports
     </en>*/
    private static final int OFF_ID30_REPORTNUM_CLASS = OFF_ID30_CONTINU_CLASS + LEN_ID30_CONTINU_CLASS;

    /**<jp>
     * 報告件数の長さ(byte)
     </jp>*/
    /**<en>
     * Length of number of reports (in byte)
     </en>*/
    private static final int LEN_ID30_REPORTNUM_CLASS = 2;

    /**<jp>
     * 機種Codeのオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of machine code
     </en>*/
    private static final int OFF_ID30_MODEL_CODE = OFF_ID30_REPORTNUM_CLASS + LEN_ID30_REPORTNUM_CLASS;

    /**<jp>
     * 機種Codeの長さ(byte)
     </jp>*/
    /**<en>
     * Length of machine code (in byte)
     </en>*/
    private static final int LEN_ID30_MODEL_CODE = 2;

    /**<jp>
     * 号機No.のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of machine code number
     </en>*/
    private static final int OFF_ID30_MACHINE_NO = OFF_ID30_MODEL_CODE + LEN_ID30_MODEL_CODE;

    /**<jp>
     * 号機No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of machine code number(in byte)
     </en>*/
    private static final int LEN_ID30_MACHINE_NO = 4;

    /**<jp>
     * 状態のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of state
     </en>*/
    private static final int OFF_ID30_CONDITION = OFF_ID30_MACHINE_NO + LEN_ID30_MACHINE_NO;

    /**<jp>
     * 状態の長さ(byte)
     </jp>*/
    /**<en>
     * Length of state (in byte)
     </en>*/
    private static final int LEN_ID30_CONDITION = 1;

    /**<jp>
     * 異常Codeのオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of error code
     </en>*/
    private static final int OFF_ID30_ABNORMAL_CODE = OFF_ID30_CONDITION + LEN_ID30_CONDITION;

    /**<jp>
     * 異常Codeの長さ(byte)
     </jp>*/
    /**<en>
     * Length of error code (in byte)
     </en>*/
    private static final int LEN_ID30_ABNORMAL_CODE = 7;

    /**<jp>
     * １件分の報告機器状態の長さ(byte)
     </jp>*/
    /**<en>
     * Length of machine status of individual report (in byte)
     </en>*/
    private static final int FT = 14;

    /**<jp>
     * 継続区分：報告TEXTの終結および最終を表す
     </jp>*/
    /**<en>
     * Continuation classification : indicates the end of report text and the final
     </en>*/
    public static final String NO_CONTINU = "2";

    /**<jp>
     * 状態：運転中
     </jp>*/
    /**<en>
     * State : operating
     </en>*/
    public static final int STATE_ACTIVE = 0;

    /**<jp>
     * 状態：停止中
     </jp>*/
    /**<en>
     * State : stopped
     </en>*/
    public static final int STATE_STOP = 1;

    /**<jp>
     * 状態：故障中
     </jp>*/
    /**<en>
     * State : down
     </en>*/
    public static final int STATE_FAIL = 2;

    /**<jp>
     * 状態：切り離し中
     </jp>*/
    /**<en>
     * State : separated
     </en>*/
    public static final int STATE_OFFLINE = 3;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer;

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
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id30()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id30 <code>as21Id30</code>  機器状態報告(MachineStatusReport) ID=30 電文
     * 機器状態を更新します。
     </jp>*/
    /**<en>APasses communication message received from AGC to the constructor.
     * @param as21Id30 :<code>as21Id30</code>  the communication message "Machine Status Report, ID=30"
     * Updates the states of machines.
     </en>*/
    public As21Id30(byte[] as21Id30)
    {
        super();
        setReceiveMessage(as21Id30);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 機器状態報告(MachineStatusReport)電文から継続区分(電文が複数に別れるか否か)を取得します。
     * @return     True:継続<BR>
     *              False:１電文にて終結
     </jp>*/
    /**<en>
     * Acquires continuation classification (whether/not the communication message consists of 2 sentences or more)
     * from the message Machine Status Report.
     * @return     True: continues<BR>
     *              False: complete in just one sentence.
     </en>*/
    public boolean getContinuationClassification()
    {
        String continuationClassification = getContent().substring(OFF_ID30_CONTINU_CLASS, LEN_ID30_CONTINU_CLASS);
        return (continuationClassification.equals(NO_CONTINU));
    }

    /**<jp>
     * 機器状態報告(MachineStatusReport)電文から報告件数を取得します。
     * @return    報告件数
     * @throws InvalidProtocolException 報告件数が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires the number of reports from the communication message Machine Status Report.
     * @return    number of reports
     * @throws InvalidProtocolException : Reports if numeric value was not provided for number of reports.
     </en>*/
    public int getNumberOfReports()
            throws InvalidProtocolException
    {
        int numberOfReports = 0;
        String snumberOfReports =
                getContent().substring(OFF_ID30_REPORTNUM_CLASS, OFF_ID30_REPORTNUM_CLASS + LEN_ID30_REPORTNUM_CLASS);
        try
        {
            numberOfReports = Integer.parseInt(snumberOfReports);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + snumberOfReports));
        }
        return (numberOfReports);
    }

    /**<jp>
     * 機器状態報告(MachineStatusReport)電文から機種Codeを取得します。
     * @param  num     報告機器状態のインデックス
     * @return         機種Code
     </jp>*/
    /**<en>
     * Acquires machine code from the communication message Machine Status Report.
     * @param  num     index of status of machines
     * @return         machine code
     </en>*/
    public String getModelCode(int num)
    {
        String sModelCode =
                getContent().substring(OFF_ID30_MODEL_CODE + (FT * num),
                        OFF_ID30_MODEL_CODE + (FT * num) + LEN_ID30_MODEL_CODE);
        return (sModelCode);
    }

    /**<jp>
     * 機器状態報告(MachineStatusReport)電文から号機No.を取得します。
     * @param  num     報告機器状態のインデックス
     * @return         号機No.
     </jp>*/
    /**<en>
     * Acquires machine code number from the communication message Machine Status Report.
     * @param  num     index of status of machines
     * @return         machine code number
     </en>*/
    public String getMachineNo(int num)
    {
        String sMachineNo =
                getContent().substring(OFF_ID30_MACHINE_NO + (FT * num),
                        OFF_ID30_MACHINE_NO + (FT * num) + LEN_ID30_MACHINE_NO);
        return (sMachineNo);
    }

    /**<jp>
     * 機器状態報告(MachineStatusReport)電文から状態を取得します。
     * @param  num     報告機器状態のインデックス
     * @return         状態
     * @throws InvalidProtocolException 状態が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires the state from the communication message Machine Status Report.
     * @param  num     index of status of machines
     * @return         state
     * @throws InvalidProtocolException :Reports if numeric value was not provided for state.
     </en>*/
    public int getCondition(int num)
            throws InvalidProtocolException
    {
        int iCondition = 0;
        String condition =
                getContent().substring(OFF_ID30_CONDITION + (FT * num),
                        OFF_ID30_CONDITION + (FT * num) + LEN_ID30_CONDITION);
        try
        {
            iCondition = Integer.parseInt(condition);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + condition));
        }
        return (iCondition);
    }

    /**<jp>
     * 機器状態報告(MachineStatusReport)電文から異常Codeを取得します。
     * @param  num     報告機器状態のインデックス
     * @return         異常Code
     </jp>*/
    /**<en>
     * Acquires error code from the communication message Machine Status Report.
     * @param  num     index of status of machines
     * @return         error Code
     </en>*/
    public String getAbnormalCode(int num)
    {
        String sAbnormalCode =
                getContent().substring(OFF_ID30_ABNORMAL_CODE + (FT * num),
                        OFF_ID30_ABNORMAL_CODE + (FT * num) + LEN_ID30_ABNORMAL_CODE);
        return (sAbnormalCode);
    }

    /**<jp>
     * 機器状態報告電文の内容を取得します。
     * @return  機器状態報告TEXTの内容
     </jp>*/
    /**<en>
     * Acquires the content of communication message Mahicne Status Report.
     * @return  text content of machine status report
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param  rmsg    AGCから受け取った電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param  rmsg    communication message received from AGC
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 受け取った電文の長さ</jp>
        //<en> length of received message</en>
        LEN_ID30 = rmsg.length;

        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        _localBuffer = new byte[LEN_ID30];
        for (int i = 0; i < LEN_ID30; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

