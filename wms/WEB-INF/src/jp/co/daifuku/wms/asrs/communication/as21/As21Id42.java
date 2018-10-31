// $Id: As21Id42.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * AS21通信プロトコルでの「作業Mode切替え指示(WorkModeChangeCommand) ID=42」電文を組み立てます。
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
 * Composes communication message "WorkModeChangeCommand" ID=42, the command to change the work mode according to 
 * AS21 communication protocol.
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
public class As21Id42
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 指示区分を表すフィールド（入庫Mode(通常)）
     </jp>*/
    /**<en>
     * Field of instruction classification (storage mode-normal)
     </en>*/
    public static final int CLASS_STORAGE = 1;

    /**<jp>
     * 指示区分を表すフィールド（入庫Mode(緊急)）
     </jp>*/
    /**<en>
     * Field of instruction classification (storage mode-urgent)
     </en>*/
    public static final int CLASS_STORAGE_EMG = 2;

    /**<jp>
     * 指示区分を表すフィールド（出庫Mode(通常)）
     </jp>*/
    /**<en>
     * Field of instruction classification (retrieval mode-normal)
     </en>*/
    public static final int CLASS_RETRIEVAL = 3;

    /**<jp>
     * 指示区分を表すフィールド（出庫Mode(緊急)）
     </jp>*/
    /**<en>
     * Field of instruction classification (retrieval mode-urgent)
     </en>*/
    public static final int CLASS_RETRIEVAL_EMG = 4;

    /**<jp>
     * Station No.の長さ
     </jp>*/
    /**<en>
     * Length of station No.
     </en>*/
    protected static final int LEN_STATIONNUMBER = 4;

    /**<jp>
     * 指示区分の長さ
     </jp>*/
    /**<en>
     * Length of instruction classification
     </en>*/
    protected static final int LEN_INSTRUCTIONCLASS = 1;

    /**<jp>
     * 作業Mode切替え指示の電文長
     </jp>*/
    /**<en>
     * Length of communication message "WorkModeChangeCommand" 
     </en>*/
    protected static final int LEN_TOTAL = OFF_CONTENT + LEN_STATIONNUMBER + LEN_INSTRUCTIONCLASS;

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーション情報を持つ、<code>Station</code>インスタンスを保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves instance of <code>Station</code>, containing station data
     </en>*/
    private Station _station;

    /**<jp>
     * 指示区分を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves instruction classification
     </en>*/
    private String _workModeChangeCommand;

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
    public As21Id42()
    {
        super();
    }

    /**<jp>
     * 作業Mode切り替えを指示するステーション情報を持った<code>Station</code>インスタンスと
     * Mode切り替えの指示区分を指定して、このクラスのインスタンスを生成します。
     * @param  s    ステーション情報を持つ<code>Station</code>
     * @param  mode 指示区分
     </jp>*/
    /**<en>
     * Generates the instance of this class by specifying the instance of <code>Station</code>
     * containing the station data which directs work mode to change and by specifying the instruction
     * classification for changing modes.
     * @param  s    :<code>Station</code> , containing the station data
     * @param  mode :instruction classification
     </en>*/
    public As21Id42(Station s, String mode)
    {
        super();
        setStation(s);
        setModeChangeCommand(mode);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業Mode切替え指示電文を作成します。
     * @return    作業Mode切替え指示電文
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message "WorkModeChangeCommand". 
     * @return    communication message "WorkModeChangeCommand"
     * @throws  InvalidProtocolException Notifies if provided value is not following the communication message protocol.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        // Station No.
        mbuf.append(_station.getStationNo());

        //<jp> 指示区分</jp>
        //<en> instruction classification</en>
        mbuf.append(_workModeChangeCommand);

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for sending message buffer</en>
        //-------------------------------------------------
        // ID
        setID("42");
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

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * ステーション情報<code>Station</code>をセットします
     * @param st 入力されたStationインスタンス
     </jp>*/
    /**<en>
     * Sets the station data <code>Station</code>
     * @param st Station instance one has entered
     </en>*/
    private void setStation(Station st)
    {
        _station = st;
    }

    /**<jp>
     * 指示区分を内部バッファにセットします。
     * @param modecommand 入力された指示区分
     </jp>*/
    /**<en>
     * Sets the instruction classification in the internal buffer.
     * @param modecommand instruction classification one has entered
     </en>*/
    private void setModeChangeCommand(String modecommand)
    {
        _workModeChangeCommand = modecommand;
    }
}
//end of class

