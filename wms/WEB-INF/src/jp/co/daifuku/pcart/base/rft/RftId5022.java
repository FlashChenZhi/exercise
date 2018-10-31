// $Id: RftId5022.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

import jp.co.daifuku.wms.base.communication.rft.SendIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 荷主一覧応答 ID=5022 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id5022の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>                      <TH>長さ</TH>   <TH>内容</TH></TR>
 * <tr><td>STX</td>                         <td> 1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>                     <td> 4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                          <td> 4 byte</td> <td>5022</td></tr>
 * <tr><td>端末送信時間</td>                <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>              <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>                 <td> 3 byte</td> <td></td></tr>
 * <tr><td>ユーザID</td>                    <td> 8 byte</td> <td></td></tr>
 * <tr><td>予定日</td>                      <td> 8 byte</td> <td></td></tr>
 * <tr><td>作業区分</td>                    <td> 2 byte</td> <td></td></tr>
 * <tr><td>作業区分詳細</td>                <td> 1 byte</td> <td></td></tr>
 * <tr><td>一覧ファイル名</td>              <td>30 byte</td> <td></td></tr>
 * <tr><td>ファイルレコード数</td>          <td> 5 byte</td> <td></td></tr>
 * <tr><td>応答フラグ</td>                  <td> 1 byte</td> <td>0:正常 8:該当データなし 9:エラー</td></tr>
 * <tr><td>エラー詳細</td>                  <td> 2 byte</td> <td></td></tr> 
 * <tr><td>ETX</td>                         <td> 1 byte</td> <td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/30</TD><TD>T.kojima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  $Author: rnakai $
 */
public class RftId5022
        extends SendIdMessage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * ユーザIDのオフセットの定義
     */
    private static final int OFF_USER_ID = LEN_HEADER;

    /**
     * 予定日のオフセットの定義
     */
    private static final int OFF_PLAN_DATE = OFF_USER_ID + LEN_USER_ID;

    /**
     * 作業区分のオフセットの定義
     */
    private static final int OFF_JOB_TYPE = OFF_PLAN_DATE + LEN_PLAN_DATE;

    /**
     * 作業区分詳細のオフセットの定義
     */
    private static final int OFF_JOB_DETAILS = OFF_JOB_TYPE + LEN_JOB_TYPE;

    /**
     * 一覧ファイル名のオフセットの定義
     */
    private static final int OFF_FILE_NAME = OFF_JOB_DETAILS + LEN_JOB_DETAILS;

    /**
     * ファイルレコード数のオフセットの定義
     */
    private static final int OFF_FILE_RECORD_NUMBER = OFF_FILE_NAME + LEN_FILE_NAME;

    /**
     * 応答フラグのオフセットの定義
     */
    private static final int OFF_ANSWER_CODE = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;

    /**
     * エラー詳細のオフセットの定義
     */
    private static final int OFF_ERROR_DETAILS = OFF_ANSWER_CODE + LEN_ANSWER_CODE;

    /**
     * ETXのオフセットの定義
     */
    private static final int OFF_ETX = OFF_ERROR_DETAILS + LEN_ERROR_DETAILS;

    /**
     * ID番号
     */
    public static final String ID = "5022";


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 親クラスのコンストラクタを呼び出した後、
     * 電文の長さをセットします。また、内部バッファを空白で初期化します。
     */
    public RftId5022()
    {
        super();
        _length = OFF_ETX + LEN_ETX;
        initializeBuffer();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * ユーザIDを設定します。
     * @param  userId      ユーザID
     */
    public void setUserId(String userId)
    {
        setToBufferLeft(userId, OFF_USER_ID, LEN_USER_ID);
    }

    /**
     * 予定日を設定します。
     * @param  planDate      予定日
     */
    public void setPlanDate(String planDate)
    {
        setToBufferLeft(planDate, OFF_PLAN_DATE, LEN_PLAN_DATE);
    }

    /**
     * 作業区分を設定します。
     * @param jobType      作業区分
     */
    public void setJobType(String jobType)
    {
        setToBufferLeft(jobType, OFF_JOB_TYPE, LEN_JOB_TYPE);
    }

    /**
     * 作業区分詳細を設定します。
     * @param jobDetails      作業区分詳細
     */
    public void setJobDetails(String jobDetails)
    {
        setToBufferLeft(jobDetails, OFF_JOB_DETAILS, LEN_JOB_DETAILS);
    }

    /**
     * 一覧ファイル名を設定します。
     * @param fileName      一覧ファイル名
     */
    public void setFileName(String fileName)
    {
        setToBufferLeft(fileName, OFF_FILE_NAME, LEN_FILE_NAME);
    }

    /**
     * ファイルレコード数を設定します。
     * @param fileRecodeNumber      ファイルレコード数
     */
    public void setFileRecodeNumber(int fileRecodeNumber)
    {
        // データを右詰めで格納する
        setToBufferRight(fileRecodeNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
    }

    /**
     * 応答フラグを設定します。
     * @param ansCode      設定する応答フラグ
     */
    public void setAnsCode(String ansCode)
    {
        setToBufferLeft(ansCode, OFF_ANSWER_CODE, LEN_ANSWER_CODE);
    }

    /**
     * エラー詳細を設定します。
     * @param errDetails    設定するエラー詳細
     */
    public void setErrDetails(String errDetails)
    {
        setToBufferLeft(errDetails, OFF_ERROR_DETAILS, LEN_ERROR_DETAILS);
    }

    /**
     * ETXを設定します。
     */
    public void setETX()
    {
        setToByteBuffer(DEF_ETX, OFF_ETX);
    }

    /**
     * 応答フラグを取得します。
     * @return  応答フラグ
     */
    public String getAnsCode()
    {
        String ansCode = getFromBuffer(OFF_ANSWER_CODE, LEN_ANSWER_CODE);
        return ansCode.trim();
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 4181 $,$Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $");
    }
}
//end of class
