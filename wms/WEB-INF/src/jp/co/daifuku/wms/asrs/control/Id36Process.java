// $Id: Id36Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id36;

/**<jp>
 * 一斉起動不可報告を処理するクラスです。起動不可理由をメッセージログに出力します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class processes the system start-up unavailable report. It outputs the reasons why
 * it cannot start-up the system in message log.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id36Process
        extends IdProcess
{
    // Class fields --------------------------------------------------
    //    /**<jp>
    //     * 作業状態でない
    //     </jp>*/
    //    /**<en>
    //     * No in work state.
    //     </en>*/
    //    private static final String TRANS_CLASS_IN = "01";

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
     * AGCNoにGroupController.DEFAULT_AGC_NUMBERをセット
     </jp>*/
    /**<en>
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     </en>*/
    public Id36Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNo AGCNo
     </en>*/
    public Id36Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 一斉起動不可報告を処理します。
     * 受信テキストの起動不可理由をロギングする
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processest the system start-up unavailable report.
     * Logging of the reason why the system could not start up the received text.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        //<jp> As21Id30のインスタンス生成</jp>
        //<en> Generates the instance of As21Id30.</en>
        As21Id36 id36dt = new As21Id36(rdt);

        //<jp> 6022034=一斉起動不可報告を受信しました。 AGCNo.={0} 起動不可理由={1}</jp>
        //<en> 6022034=Start-up failure report is received. SRC No.={0} Reason={1}</en>
        Object[] tObj = {
            getAgcNo(),
            id36dt.getReason(),
        };
        RmiMsgLogClient.write(6022034, LogMessage.F_NOTICE, getClass().getName(), tObj);
    }
}
//end of class

