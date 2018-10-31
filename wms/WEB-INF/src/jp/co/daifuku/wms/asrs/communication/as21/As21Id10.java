// $Id: As21Id10.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

import jp.co.daifuku.wms.base.entity.CarryInfo;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「機器状態要求(MachineStatusRequest) ID=10」電文を組み立てます。
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
 * Composes "Request for machine status, ID=10" according to AS21 communication protocol.
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
public class As21Id10
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 個々の機器状態要求電文長
     </jp>*/
    /**<en>
     * Length of each communication message requesting for respective machine status
     </en>*/
    protected static final int LEN_TOTAL = 0;

    // Class variables -----------------------------------------------
    /**<jp>
     * 搬送情報を持つ、<code>CarryInformation</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>CarryInformation</code> with carry information.
     </en>*/
    protected CarryInfo _carryInfo;

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
    public As21Id10()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 機器状態要求を作成します。<BR>
     * @return    機器状態要求
     </jp>*/
    /**<en>
     * Creates the request for the status of machine.<BR>
     * @return    request for the status of machine
     </en>*/
    public String getSendMessage()
    {
        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Sets as sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("10");
        //<jp> id 区分</jp>
        //<en> id segmentaion</en>
        setIDClass("00");
        //<jp> 送信時刻</jp>
        //<en> time sent</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> time sent to AGC</en>
        setAGCSendDate("000000");
        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
