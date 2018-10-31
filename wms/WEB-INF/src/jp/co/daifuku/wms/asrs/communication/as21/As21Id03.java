// $Id: As21Id03.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「作業終了要求(WorkCompletionRequest) ID=03」電文を組み立てます。
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
 * Composes communication message "Request for cork completion ID=03" according to AS21 communication protocol.
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
public class As21Id03
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 要求区分を表すフィールド (通常終了)
     </jp>*/
    /**<en>
     * Field of request segment (normal termination)
     </en>*/
    public static final String GENERAL_END = "0";

    /**<jp>
     * 要求区分を表すフィールド (強制終了１)
     </jp>*/
    /**<en>
     * Field of request segment (forced termination 1)
     </en>*/
    public static final String EXTRAORDINARY_END_ONE = "1";

    /**<jp>
     * 要求区分を表すフィールド (強制終了２)
     </jp>*/
    /**<en>
     * Field of request segment (forced termination 2)
     </en>*/
    public static final String EXTRAORDINARY_END_TWO = "2";

    /**<jp>
     * 要求区分の長さ
     </jp>*/
    /**<en>
     * Length of request classification
     </en>*/
    public static final int REQUEST_CLASSIFICATION = 1;

    /**<jp>
     * 電文のデータの長さ
     </jp>*/
    /**<en>
     * Length of communication message data
     </en>*/
    protected static final int LEN_TOTAL = REQUEST_CLASSIFICATION;

    // Class variables -----------------------------------------------
    /**<jp>
     * 要求区分を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves the request classification
     </en>*/
    public String _reqinfo;

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
     * コンストラクタ
     </jp>*/
    /**<en>
     * Constructor
     </en>*/
    public As21Id03()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業終了要求電文を作成します。
     * <p><code>
     * 電文の内容は、以下のようになります。<br>
     * <table>
     * <tr><td>要求区分</td><td>1 byte</td></tr>
     * </table></code></p>
     * @return    作業終了電文
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message requesting the work to terminate.
     * <p><code>
     * Communication message should contain the following;<br>
     * <table>
     * <tr><td>request classification</td><td>1 byte</td></tr>
     * </table></code></p>
     * @return    communication Message of work completion
     * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> Text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //-------------------------------------------------
        //<jp> ここから先、順序が重要なので注意!</jp>
        //<en> Attention! The order of contents must be observed!</en>
        //-------------------------------------------------
        //<jp> 要求区分</jp>
        //<en> Request classfication</en>
        mbuf.append(_reqinfo);

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Sets as sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("03");
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

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 作業終了をセットします。
     * @param reqinfo   要求区分<BR>
     *      0:通常終了(残作業なしで終了)<BR>
     *      1:強制終了１(残作業があっても終了)<BR>
     *      2:強制終了２(残作業があったら削除して終了)<BR>
     * @throws InvalidProtocolException 不正な要求区分が指定された場合に報告されます。
     </jp>*/
    /**<en>
     * Sets the termination of work.
     * @param reqinfo :request classification<BR>
     *      0:forced termination (terminated with no remaining job)<BR>
     *      1:forced termination 1 (terminated regardless of remaining jobs)<BR>
     *      2:forced termination 2 (terminated ; having deleted any remaining jobs)<BR>
     * @throws InvalidProtocolException : Reports if entered request classification is invalid.
     </en>*/
    public void setRequestClass(String reqinfo)
            throws InvalidProtocolException
    {
        _reqinfo = reqinfo;
        if (_reqinfo == GENERAL_END)
        {
            //<jp> 通常終了</jp>
            //<en> Normal termination</en>
        }
        else if (_reqinfo == EXTRAORDINARY_END_ONE)
        {
            //<jp> 強制終了１</jp>
            //<en> Forced termination 1</en>
        }
        else if (_reqinfo == EXTRAORDINARY_END_TWO)
        {
            //<jp> 強制終了２</jp>
            //<en> Forced termination 2</en>
        }
        else
        {
            //<jp> 不正な要求区分が指定された</jp>
            //<en> Entered request classification is invalid.</en>
            throw new InvalidProtocolException("\n" + "Exeption. Request classification = " + _reqinfo);
        }
    }
}
// end of class
