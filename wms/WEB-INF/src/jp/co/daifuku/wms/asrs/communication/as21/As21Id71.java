// $Id: As21Id71.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

import jp.co.daifuku.common.InvalidProtocolException;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「Access不可棚報告(AccessImpossibleLocationReport) ID=71」電文を処理します。
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
 * Processes the communication message "AccessImpossibleLocationReport" ID=71, report of locations
 * which have no availability of access, according to AS21 communication protcol.
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
public class As21Id71
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 電文の長さ(byte)の初期設定
     </jp>*/
    /**<en>
     * Length of communication message in initial setting (byte)
     </en>*/
    public int LEN_ID71 = 0;

    /**<jp>
     * 継続区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of classification for continuation
     </en>*/
    private static final int OFF_ID71_CONTINU_CLASS = 0;

    /**<jp>
     * 継続区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of classification for continuation (byte)
     </en>*/
    private static final int LEN_ID71_CONTINU_CLASS = 1;

    /**<jp>
     * 報告件数のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of number of reports
     </en>*/
    private static final int OFF_ID71_REPORTNUM_CLASS = OFF_ID71_CONTINU_CLASS + LEN_ID71_CONTINU_CLASS;

    /**<jp>
     * 報告件数の長さ(byte)
     </jp>*/
    /**<en>
     * Length of the number of reports (byte)
     </en>*/
    private static final int LEN_ID71_REPORTNUM_CLASS = 2;

    /**<jp>
     * 状態のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of status
     </en>*/
    private static final int OFF_ID71_CONDITION = OFF_ID71_REPORTNUM_CLASS + LEN_ID71_REPORTNUM_CLASS;

    /**<jp>
     * 状態の長さ(byte)
     </jp>*/
    /**<en>
     * Length of status (byte)
     </en>*/
    private static final int LEN_ID71_CONDITION = 1;

    /**<jp>
     * 格納区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of storage classification
     </en>*/
    private static final int OFF_ID71_STATUS_CLASS = OFF_ID71_CONDITION + LEN_ID71_CONDITION;

    /**<jp>
     * 格納区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of storage classification (byte)
     </en>*/
    private static final int LEN_ID71_STATUS_CLASS = 1;

    /**<jp>
     * Bank No.のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of Bank No.
     </en>*/
    private static final int OFF_ID71_BANK_NO = OFF_ID71_STATUS_CLASS + LEN_ID71_STATUS_CLASS;

    /**<jp>
     * Bank No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of Bank No.(byte)
     </en>*/
    private static final int LEN_ID71_BANK_NO = 2;

    /**<jp>
     * Start Bayのオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of Start Bay
     </en>*/
    private static final int OFF_ID71_START_BAY = OFF_ID71_BANK_NO + LEN_ID71_BANK_NO;

    /**<jp>
     * Start Bayの長さ(byte)
     </jp>*/
    /**<en>
     * Length of Start Bay (byte)
     </en>*/
    private static final int LEN_ID71_START_BAY = 3;

    /**<jp>
     * Start Level no.のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of Start Level no.
     </en>*/
    private static final int OFF_ID71_STLEVEL_NO = OFF_ID71_START_BAY + LEN_ID71_START_BAY;

    /**<jp>
     * Start Level no.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of Start Level no. (byte)
     </en>*/
    private static final int LEN_ID71_STLEVEL_NO = 3;

    /**<jp>
     * End Bayのオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of End Bay
     </en>*/
    private static final int OFF_ID71_END_BAY = OFF_ID71_STLEVEL_NO + LEN_ID71_STLEVEL_NO;

    /**<jp>
     * End Bayの長さ(byte)
     </jp>*/
    /**<en>
     * Length ofEnd Bay (byte)
     </en>*/
    private static final int LEN_ID71_END_BAY = 3;

    /**<jp>
     * End Level No.のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of End Level No.
     </en>*/
    private static final int OFF_ID71_ENLEVEL_NO = OFF_ID71_END_BAY + LEN_ID71_END_BAY;

    /**<jp>
     * End Level No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of End Level No. (byte)
     </en>*/
    private static final int LEN_ID71_ENLEVEL_NO = 3;

    /**<jp>
     * １件分のAccess可/不可棚状態の長さ(byte)
     </jp>*/
    /**<en>
     * Length of the state of availability of each location, per case (byte)
     </en>*/
    private static final int ST = 16;

    /**<jp>
     * 継続区分：報告TEXTの終結および最終を表す
     </jp>*/
    /**<en>
     * Classification of continuation : End and the final of the report text
     </en>*/
    public static final String NO_CONTINU = "2";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variables for the preservation of communication message
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
     * このクラスの初期化を行います。
     </jp>*/
    /**<en>
     * Operates the initialization of this class.
     </en>*/
    public As21Id71()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id71 <code>as21Id71</code>  Access不可棚報告電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then implements the initialization of this class.
     * @param as21Id71 :<code>as21Id71</code>  communication message "AccessImpossibleLocationReport"
     </en>*/
    public As21Id71(byte[] as21Id71)
    {
        super();
        setReceiveMessage(as21Id71);
    }

    // Public methods ------------------------------------------------9
    /**<jp>
     * Access不可棚報告電文から継続区分(電文が複数に別れるか否か)を取得します。
     * @return      継続区分<BR>
     *               1:複数に別れる<BR>
     *               2:１電文にて終結
     </jp>*/
    /**<en>
     * Acquires the continuation classification (whether/not the message consists of 2 messages or more)
     * based on the message "AccessImpossibleLocationReport"
     * @return      continuation classification<BR>
     *               1: the message consists of 2 or more sentences<BR>
     *               2: the message consists of 1 sentence
     </en>*/
    public boolean getContinuationClassification()
    {
        String continuationClassification = getContent().substring(OFF_ID71_CONTINU_CLASS, LEN_ID71_CONTINU_CLASS);
        return (continuationClassification.equals(NO_CONTINU));
    }

    /**<jp>
     * Access不可棚報告電文から報告件数を取得します。
     * @return    報告件数
     * @throws InvalidProtocolException 報告件数が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires the number of reports from the message "AccessImpossibleLocationReport".
     * @return    the number of reports
     * @throws InvalidProtocolException : Reports if numeric value was not provided for number of reports.
     </en>*/
    public int getNumberOfReports()
            throws InvalidProtocolException
    {
        int numberOfReports = 0;
        String snumberOfReports =
                getContent().substring(OFF_ID71_REPORTNUM_CLASS, OFF_ID71_REPORTNUM_CLASS + LEN_ID71_REPORTNUM_CLASS);
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
     * Access不可棚報告電文からAccess可/不可棚状態状態を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    Access可/不可棚状態
     </jp>*/
    /**<en>
     * Acquires the availability of the location the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    Access availability
     </en>*/
    public String getCondition(int num)
    {
        String sCondition =
                getContent().substring(OFF_ID71_CONDITION + (ST * num),
                        OFF_ID71_CONDITION + (ST * num) + LEN_ID71_CONDITION);
        return (sCondition);
    }

    /**<jp>
     * Access不可棚報告電文から格納区分を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    格納区分
     </jp>*/
    /**<en>
     * Acquires the classification of storage from the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    classification of storage
     </en>*/
    public String getStatusClass(int num)
    {
        String sStatusClass =
                getContent().substring(OFF_ID71_STATUS_CLASS + (ST * num),
                        OFF_ID71_STATUS_CLASS + (ST * num) + LEN_ID71_STATUS_CLASS);
        return (sStatusClass);
    }

    /**<jp>
     * Access不可棚報告電文からBank No.を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    Bank No.
     </jp>*/
    /**<en>
     * Acquires the Bank No. from the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    Bank No.
     </en>*/
    public String getBankNo(int num)
    {
        String sBankNo =
                getContent().substring(OFF_ID71_BANK_NO + (ST * num), OFF_ID71_BANK_NO + (ST * num) + LEN_ID71_BANK_NO);
        return (sBankNo);
    }

    /**<jp>
     * Access不可棚報告電文から開始BayNo.を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    開始BayNo.
     </jp>*/
    /**<en>
     * Acquires the Start Bay No. in the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    Start Bay No.
     </en>*/
    public String getStartBay(int num)
    {
        String sStartBay =
                getContent().substring(OFF_ID71_START_BAY + (ST * num),
                        OFF_ID71_START_BAY + (ST * num) + LEN_ID71_START_BAY);
        return (sStartBay);
    }

    /**<jp>
     * Access不可棚報告電文から開始LevelNo.を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    開始LevelNo.
     </jp>*/
    /**<en>
     * Acquires the Start Level No. the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    Start Level No.
     </en>*/
    public String getStLevelNo(int num)
    {
        String sStLevelNo =
                getContent().substring(OFF_ID71_STLEVEL_NO + (ST * num),
                        OFF_ID71_STLEVEL_NO + (ST * num) + LEN_ID71_STLEVEL_NO);
        return (sStLevelNo);
    }

    /**<jp>
     * Access不可棚報告電文から終了BayNo.を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    終了BayNo.
     </jp>*/
    /**<en>
     * Acquires the End Bay No. from the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    End Bay No.
     </en>*/
    public String getEndBay(int num)
    {
        String sEndBay =
                getContent().substring(OFF_ID71_END_BAY + (ST * num), OFF_ID71_END_BAY + (ST * num) + LEN_ID71_END_BAY);
        return (sEndBay);
    }

    /**<jp>
     * Access不可棚報告電文から終了LevelNo.を取得します。
     * @param     num    Access可/不可棚状態のインデックス
     * @return    終了LevelNo.
     </jp>*/
    /**<en>
     * Acquires the End Bay No. from the message "AccessImpossibleLocationReport".
     * @param     num    index of status of access availability
     * @return    End Level No.
     </en>*/
    public String getEnLevelNo(int num)
    {
        String sEnLevelNo =
                getContent().substring(OFF_ID71_ENLEVEL_NO + (ST * num),
                        OFF_ID71_ENLEVEL_NO + (ST * num) + LEN_ID71_ENLEVEL_NO);
        return (sEnLevelNo);
    }

    /**<jp>
     * Access不可棚報告電文の内容を取得します。
     * @return  Access不可棚報告電文の内容
     </jp>*/
    /**<en>
     * Acquires the content of message "AccessImpossibleLocationReport".
     * @return  content of message "AccessImpossibleLocationReport"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg Access不可棚報告電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message: AccessImpossibleLocationReport
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 受け取った電文の長さ</jp>
        //<en> length of received message</en>
        LEN_ID71 = rmsg.length;

        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        _localBuffer = new byte[LEN_ID71];
        for (int i = 0; i < LEN_ID71; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }
    // Private methods -----------------------------------------------

}
//end of class

