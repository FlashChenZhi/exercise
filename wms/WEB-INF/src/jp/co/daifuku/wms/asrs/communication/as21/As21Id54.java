// $Id: codetemplates.xml 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.wms.base.entity.Station;


public class As21Id54
        extends SendIdMessage
{
    // 指示区分
    //  0:消灯
    public static final int CLASS_OFF = 0;

    //  1:点灯
    public static final int CLASS_ON = 1;

    //  2:点滅
    public static final int CLASS_ONOFF = 2;

    // Text Format
    protected static final int LEN_CNT = 2;

    protected static final int LEN_DODATA = 7;

    protected static final int LEN_TOTAL = OFF_CONTENT + LEN_CNT + LEN_DODATA;

    private Station _station;

    private String _lampNo;

    private String _workModeCommand;

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: codetemplates.xml 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * default constructor.
     */
    public As21Id54()
    {
        super();
    }

    public As21Id54(Station s, String lampNo, String mode)
    {
        super();
        setStation(s);
        setLampNo(lampNo);
        setModeCommand(mode);
    }

    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        // 件数
        mbuf.append("01");

        // Station No.
        mbuf.append(_station.getStationNo());

        // ランプ番号
        mbuf.append(_lampNo);

        //<jp> 指示区分</jp>
        //<en> instruction classification</en>
        mbuf.append(_workModeCommand);

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for sending message buffer</en>
        //-------------------------------------------------
        // ID
        setID("54");
        //<jp> ID 区分</jp>
        //<en> ID segment</en>
        setIDClass("00");
        //<jp> MC送信時刻</jp>
        //<en> MC sending time</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> AGC sending time</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> text contents</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL));
    }

    private void setStation(Station st)
    {
        _station = st;
    }

    private void setLampNo(String lampNo)
    {
        _lampNo = lampNo;
    }

    private void setModeCommand(String modecommand)
    {
        _workModeCommand = modecommand;
    }

}
