// $Id: As21Id19.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「伝送TEST要求(TransmissionTestRequest) ID=19」電文を組み立てます。
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
 * Composes communication message "transmission test request ID=19" according to AS21 communication protocol.
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
public class As21Id19
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * テストデータの長さ
     </jp>*/
    /**<en>
     * Length of test data
     </en>*/
    protected static final int TEST_DATA = 488;

    /**<jp>
     *電文長
     </jp>*/
    /**<en>
     * Length of communication message
     </en>*/
    protected final int LEN_TOTAL = TEST_DATA;

    // Class variables -----------------------------------------------
    /**<jp>
     * テストデータを保持する変数。
     </jp>*/
    /**<en>
     * Variable which perserves test data
     </en>*/
    private String _test;

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
    public As21Id19()
    {
        super();
    }

    /**<jp>
     * テストデータを指定して、このクラスのインスタンスを生成します。
     * @param  testData     Test data
     </jp>*/
    /**<en>
     * Generates the instance of this class by specifying the test data.
     * @param  testData     Test data
     </en>*/
    public As21Id19(String testData)
    {
        super();
        _test = testData;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 伝送TEST要求電文を作成します。
     * @return    伝送TEST要求電文
     * @throws  InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates communication message requesting the transmission test.
     * @return    communication message requesting the transmission test
     * @throws  InvalidProtocolException : Notifies if communication message includes improper contents in protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);
        // TestData
        mbuf.append(getTestData());
        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for the sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("19");
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

        return (getFromBuffer(0, OFF_CONTENT + LEN_TOTAL));
    }

    /**<jp>
     * テストデータをセットします。
     * @param st テストデータ
     </jp>*/
    /**<en>
     * Sets the test data
     * @param st test data
     </en>*/
    public void setTestData(String st)
    {
        _test = st;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

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
        if (_test.getBytes().length != TEST_DATA)
        {
            throw new InvalidProtocolException("test=" + TEST_DATA + "---->" + _test);
        }
        else
        {
            return _test;
        }
    }
}
//end of class
