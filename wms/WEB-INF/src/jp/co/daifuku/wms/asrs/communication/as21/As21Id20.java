// $Id: As21Id20.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「伝送TEST応答(TransmissionTestResponse) ID=20」電文を組み立てます。
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
 * Composes communication message "Response to transmission test ID=20" according to AS21 communication protocol.
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
public class As21Id20
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * テストデータの長さ
     </jp>*/
    /**<en>
     * Length of test data
     </en>*/
    private static final int TEST_DATA = 488;

    /**<jp>
     * 個々の伝送Test応答電文長
     </jp>*/
    /**<en>
     * Length of respective communication message responding to transmission test
     </en>*/
    private static final int LEN_TOTAL = TEST_DATA;

    /**<jp>
     * テストデータを保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves test data
     </en>*/
    private String _testdata;

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
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id20()
    {
        super();

    }

    /**<jp>
     * 伝送TEST応答電文を作成します。
     * @param td <code>as21Id20</code>  AGCからの伝送テスト要求応答(ID=40)から抽出したDataを渡す。
     </jp>*/
    /**<en>
     * Creates communication message responding to transmission test.
     * @param td <code>as21Id20</code>  Passes data abstracted from the responce to transmission test
     * from AGC, ID=40.
     </en>*/
    public As21Id20(String td)
    {
        super();
        _testdata = td;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 伝送TEST応答電文を作成します。
     * <p>伝送TEST応答を出すのに必要な</p>
     * <ul>
     * <li>Test Data
     * </ul>
     * <p>は、コンストラクタで渡されたテストデータから情報を入手します。
     * </p>
     * @return  伝送TEST応答電文
     * @throws  InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates communication message responding to transmission test.
     * <p><p/> required to release the responce of transmission test
     * <ul>
     * <li>Test Data
     * </ul>
     * <p> acquires data from test data provided in constructor.
     * </p>
     * @return  communication message responding to transmission test
     * @throws  InvalidProtocolException : Notifies if communication message includes improper contents in protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> tesxt buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        mbuf.append(getTestData());

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("20");
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
        //<en> content of text</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    /**<jp>
     * テストデータをセットします。
     * @param tt テストデータ
     </jp>*/
    /**<en>
     * Sets test data.
     * @param tt test data
     </en>*/
    public void setTestData(String tt)
    {
        _testdata = tt;
    }

    /**<jp>
     * テストデータを取得します。
     * @return テストデータ
     * @throws InvalidProtocolException  テストデータが指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Gets the test data.
     * @return test data 
     * @throws InvalidProtocolException : Reports if test data is not the allowable length.
     </en>*/
    private String getTestData()
            throws InvalidProtocolException
    {
        if (_testdata.length() != TEST_DATA)
        {
            throw new InvalidProtocolException("testdata = " + TEST_DATA + "--->" + _testdata);
        }
        return (_testdata);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
