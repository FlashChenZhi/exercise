// $Id: As21Id16.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「一斉起動/停止 指示(SystemStartupOverallStopCommand) ID=16」電文を組み立てます。
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
 *Composes communication message "command for overall start/stop, ID=16" according to AS21 communication protocol.
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
public class As21Id16
        extends SendIdMessage
{

    //Class fields --------------------------------------------------
    /**<jp>
     * 起動/停止区分を表すフィールド (一斉起動)
     </jp>*/
    /**<en>
     * Field of all start/all stop classification (all start)
     </en>*/
    public static final String ALLMOVE = "1";

    /**<jp>
     * 起動/停止区分を表すフィールド (一斉停止)
     </jp>*/
    /**<en>
     * Field of all start/all stop classification (all stop)
     </en>*/
    public static final String ALLSTOP = "2";

    /**<jp>
     * 起動/停止区分の長さ
     </jp>*/
    /**<en>
     * Length of start/stop classification
     </en>*/
    public static final int LEN_MOVE_STOP_CLASS = 1;

    /**<jp>
     * 電文長
     </jp>*/
    /**<en>
     * Length of communication message
     </en>*/
    protected static final int LEN_TOTAL = OFF_CONTENT + LEN_MOVE_STOP_CLASS;

    // Class variables -----------------------------------------------
    /**<jp>
     * boolean型のwboo変数を宣言
     </jp>*/
    /**<en>
     * Declares the boolean oriented wboo variables.
     </en>*/
    private boolean _booleanType;

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
    public As21Id16()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 一斉起動/停止区分クラスのインスタンスを生成します。
     * @param  b  起動/停止区分に関する情報を持つ
     </jp>*/
    /**<en>
     * Generates instance of all start/all stop class.
     * @param  b  contains information related to start/stop segments
     </en>*/
    public As21Id16(boolean b)
    {
        super();
        setBoo(b);
    }

    /**<jp>
     * 一斉起動/停止 指示電文を作成します。
     * @return    一斉起動/停止 指示電文
     </jp>*/
    /**<en>
     * Creates communication message directing to all start/ all stop.
     * @return    Direction message to all start/ all stop
     </en>*/
    public String getSendMessage()
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //<jp> 起動/停止区分</jp>
        //<en> Start/stop classification</en>
        mbuf.append(getMovestopclass(_booleanType));

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("16");
        //<jp> id 区分</jp>
        //<en> id segment</en>
        setIDClass("00");
        //<jp> 送信時刻</jp>
        //<en> time sent</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> time sent to AGC</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> contents of text</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL));
    }

    /**<jp>
     * 起動/停止区分をセットします。
     * @param st  一斉起動時にはTRUE、一斉停止時にはFALSEを指定
     * @return 起動/停止区分
     </jp>*/
    /**<en>
     * Sets start/stop classification.
     * @param st  Returns 'TRUE' for all start, 'FALSE' for all stop.
     * @return start/stop classification
     </en>*/
    public String getMovestopclass(boolean st)
    {

        String movestopclass = "";

        if (st == true)
        {
            movestopclass = ALLMOVE;
        }
        else
        {
            movestopclass = ALLSTOP;
        }
        return (movestopclass);

    }

    /**<jp>
     * Booの値をセット	
     * @param b  起動/停止区分に関する情報
     </jp>*/
    /**<en>
     * Sets value of Boo
     * @param b  information related with start/stop classification
     </en>*/
    private void setBoo(boolean b)
    {
        _booleanType = b;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
