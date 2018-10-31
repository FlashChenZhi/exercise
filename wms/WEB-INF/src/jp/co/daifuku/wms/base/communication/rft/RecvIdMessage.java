// $Id: RecvIdMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.authentication.DfkUserInfo;

/**
 * RFT通信での電文共通部分を組み立て・分解するための、
 * ユーティリティ・メソッドを提供する、スーパークラスです。<BR>
 * 実際の電文組み立て・分解は、各IDごとのサブクラスを利用してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class RecvIdMessage
        extends IdMessage
{

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return "$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $";
    }


    // Constructors --------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public RecvIdMessage()
    {
        super();
    }

    /**
     * インスタンスを生成します。
     * @param  dt  RFTから受け取った電文
     */
    public RecvIdMessage(byte[] dt)
    {
        super();
        setReceiveMessage(dt);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * RFT送信時刻を取得します。
     * @return 送信時刻
     */
    public Date getRftSendDate()
    {
        String wdt = getFromBuffer(OFF_RFTSENDDATE, LEN_RFTSENDDATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(ID_DATE_FORMAT);
        return (dateFormat.parse(wdt, new ParsePosition(0)));
    }

    /**
     * SERV送信時刻を取得します。
     * @return SERV送信時刻
     */
    public Date getServSendDate()
    {
        String wdt = getFromBuffer(OFF_SERVSENDDATE, LEN_SERVSENDDATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(ID_DATE_FORMAT);
        return (dateFormat.parse(wdt, new ParsePosition(0)));
    }

    /**
     * RFT号機を取得します。
     * @return RFT号機
     */
    public String getRftNo()
    {
        return (getFromBuffer(OFF_RFTNO, LEN_RFTNO));
    }

    /**
     * RFTから受け取った電文を内部バッファにセットします。
     * @param  rmsg   セットする電文情報
     */
    public void setReceiveMessage(byte[] rmsg)
    {
        if (null == rmsg)
        {
            return;
        }
        int size = Math.min(_wDataBuffer.length, rmsg.length);
        System.arraycopy(rmsg, 0, _wDataBuffer, 0, size);
    }

    /**
     * ユーザIDを取得します。<br>
     * この実装ではユーザIDを持たない電文のために空文字を返します。<br>
     * ユーザIDのあるサブクラスではこのメソッドをオーバーライドしてください。
     * 
     * @return ユーザID
     * @since 2008/4/2 add by Softecs.
     */
    public String getUserId()
    {
        return "";
    }

    /**
     * @return DFKユーザ情報を保持している内容より生成して返します。
     * @since 2008/4/2 add by Softecs.
     */
    public DfkUserInfo getDfkUserInfo()
    {
        DfkUserInfo userInfo = new DfkUserInfo();
        userInfo.setUserId(getUserId());
        userInfo.setTerminalNumber(getRftNo());
        return userInfo;
    }
}
//end of class
