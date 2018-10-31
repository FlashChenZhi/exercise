// $Id: RftId0022.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;


import jp.co.daifuku.wms.base.communication.rft.RecvIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 荷主一覧問合せ ID=0022 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id0012の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>            <TH>長さ</TH>    <TH>内容</TH></TR>
 * <tr><td>STX</td>               <td>  1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>           <td>  4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                <td>  4 byte</td> <td>0022</td></tr>
 * <tr><td>端末送信時間</td>      <td>  6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>    <td>  6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>       <td>  3 byte</td> <td></td></tr>
 * <tr><td>ユーザID</td>          <td>  8 byte</td> <td></td></tr>
 * <tr><td>予定日</td>            <td>  8 byte</td> <td></td></tr>
 * <tr><td>作業区分</td>          <td>  2 byte</td> <td></td></tr>
 * <tr><td>作業区分詳細</td>      <td>  1 byte</td> <td></td></tr>
 * <tr><td>ETX</td>               <td>  1 byte</td> <td>0x03</td></tr>
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
public class RftId0022
        extends RecvIdMessage
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
     * ETXのオフセットの定義
     */
    //private static final int OFF_ETX = OFF_JOB_DETAILS + LEN_JOB_DETAILS;
    /**
     * ID番号
     */
    public static final String ID = "0022";

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
     * インスタンスを生成します。
     */
    public RftId0022()
    {
        super();
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * ユーザIDを取得します。
     * @return   ユーザID
     */
    public String getUserId()
    {
        String userId = getFromBuffer(OFF_USER_ID, LEN_USER_ID);
        return userId.trim();
    }

    /**
     * 予定日を取得します。
     * @return   予定日
     */
    public String getPlanDate()
    {
        String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
        return planDate.trim();
    }

    /**
     * 作業区分を取得します。
     * @return     作業区分
     */
    public String getJobType()
    {
        String jobType = getFromBuffer(OFF_JOB_TYPE, LEN_JOB_TYPE);
        return jobType.trim();
    }

    /**
     * 作業区分詳細を取得します。
     * @return    作業区分詳細
     */
    public String getJobDetails()
    {
        String jobDetails = getFromBuffer(OFF_JOB_DETAILS, LEN_JOB_DETAILS);
        return jobDetails.trim();
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
