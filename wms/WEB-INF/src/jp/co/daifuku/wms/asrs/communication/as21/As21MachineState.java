// $Id: As21MachineState.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.equipment.MachineState;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.EntityHandler;

/**<jp>
 * AS21プロトコルにおける、機器状態の管理を行うためのクラスです。
 * 機器そのものの状態と、エラーコード、切り離しなどの概念上の利用可能状態を管理します。
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
 * This class controls the machine states according to AS21 protocol.
 * It controls the status of machines themselves, errorcodes and conceptual availability such as off-line.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class As21MachineState
        extends MachineState
{
    // Class fields -----------------------------------------------
    /**<jp>
     * プロトコルタイプを表すフィールド(AS21プロトコル)
     </jp>*/
    /**<en>
     * Filed for protocol type (AS21 protocol)
     </en>*/
    public static final String PROTOCOL_AS21 = "AS21";

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーションNo
     </jp>*/
    /**<en>
     * station No.
     </en>*/
    private String _stationNo = null;

    /**<jp>
     * 機器タイプ
     </jp>*/
    /**<en>
     * Machine type
     </en>*/
    private int _type = 0;

    /**<jp>
     * 機器番号(号機番号)
     </jp>*/
    /**<en>
     * Number of machine (number)
     </en>*/
    private int _no = 0;

    /**<jp>
     * 機器状態
     </jp>*/
    /**<en>
     * State of machine
     </en>*/
    private String _state = SystemDefine.MACHINE_STATE_ACTIVE;

    /**<jp>
     * エラーコード
     </jp>*/
    /**<en>
     * Error code
     </en>*/
    private String _errCode = null;

    /**<jp>
     * コントローラーNo
     </jp>*/
    /**<en>
     * Controller No.
     </en>*/
    private int _controllerNo = 0;

    /**<jp>
     * インスタンス・ハンドラ
     </jp>*/
    /**<en>
     * Instance handler
     </en>*/
    private EntityHandler _handler = null;

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
     * 機器のタイプ、コントローラナンバーと号機番号から、<code>As21MachineState</code>のインスタンスを作成します。<BR>
     * @param  type 作成する<code>Machine</code>のタイプ
     * @param  num 作成する<code>Machine</code>の号機番号
     * @param  cnt 作成する<code>Machine</code>のコントローラナンバー
     </jp>*/
    /**<en>
     * Generates the instance of <code>As21MachineState</code> according to machine type, controller No. and 
     * the machine number.<BR>
     * @param  type type of <code>Machine</code> to create
     * @param  num machine number of <code>Machine</code> to create
     * @param  cnt controller number of <code>Machine</code> to create
     </en>*/
    public As21MachineState(int type, int num, int cnt)
    {
        setType(type);
        setNo(num);
        setControllerNumber(cnt);
    }

    /**<jp>
     * 機器のタイプと号機番号から、<code>As21MachineState</code>のインスタンスを作成します。<BR>
     * @param  type 作成する<code>MachineState</code>のタイプ
     * @param  num 作成する<code>MachineState</code>の号機番号
     </jp>*/
    /**<en>
     * Generates the instance of <code>As21MachineState</code> according to machine type and machine number.<BR>
     * @param  type type of <code>MachineState</code> to create
     * @param  num machine number of <code>MachineState</code> to create
     </en>*/
    public As21MachineState(int type, int num)
    {
        setType(type);
        setNo(num);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステーションNoに値をセットします。
     * @param stno セットするステーションNo
     </jp>*/
    /**<en>
     * Sets value of station No.
     * @param stno station No. to set
     </en>*/
    public void setStationNumber(String stno)
    {
        _stationNo = stno;
    }

    /**<jp>
     * ステーションNoを取得します。
     * @return  ステーションNo
     </jp>*/
    /**<en>
     * Gets station No.
     * @return  station No.
     </en>*/
    public String getStationNumber()
    {
        return _stationNo;
    }

    /**<jp>
     * 機器のタイプを設定します。
     * @param type  機器のタイプ
     </jp>*/
    /**<en>
     * Sets the type of machine.
     * @param type  type of machine
     </en>*/
    public void setType(int type)
    {
        _type = type;
    }

    /**<jp>
     * 機器のタイプを取得します。
     * @return    機器のタイプ
     </jp>*/
    /**<en>
     * Gets type of machine.
     * @return    type of machine
     </en>*/
    public int getType()
    {
        return _type;
    }

    /**<jp>
     * 機器番号(号機)を設定します。
     * @param num  機器番号(号機)
     </jp>*/
    /**<en>
     * Sets the machine number.
     * @param num  machine number
     </en>*/
    public void setNo(int num)
    {
        _no = num;
    }

    /**<jp>
     * 機器番号(号機)を取得します。
     * @return    機器番号(号機)
     </jp>*/
    /**<en>
     * Gets the machine number.
     * @return    machine number
     </en>*/
    public int getNo()
    {
        return _no;
    }

    /**<jp>
     * 機器の状態を設定します。状態の一覧は、このクラスのフィールドとして定義されています。
     * @param stat  機器の状態
     </jp>*/
    /**<en>
     * Sets the state of machine. List of states is defined as fields of this class.
     * @param stat  machine state
     </en>*/
    public void setState(String stat)
    {
        _state = stat;
    }

    /**<jp>
     * 機器の状態を取得します。状態の一覧は、このクラスのフィールドとして定義されています。
     * @return    機器の状態
     </jp>*/
    /**<en>
     * Gets the state of machine. List of states is defined as fields of this class.
     * @return    state of machine
     </en>*/
    public String getState()
    {
        return _state;
    }

    /**<jp>
     * 機器のエラーコードを設定します。
     * @param errcode  機器のエラーコード
     </jp>*/
    /**<en>
     * Sets the machine error codes.
     * @param errcode  the machine error codes
     </en>*/
    public void setErrorCode(String errcode)
    {
        _errCode = errcode;
    }

    /**<jp>
     * 機器のエラーコードを取得します。
     * @return    機器のエラーコード
     </jp>*/
    /**<en>
     * Gets the machine error codes.
     * @return    the machine error codes
     </en>*/
    public String getErrorCode()
    {
        return _errCode;
    }

    /**<jp>
     * コントローラーNoを設定します。
     * @param gcid  コントローラーNo
     </jp>*/
    /**<en>
     * Sets the controller number.
     * @param gcid  controller number
     </en>*/
    public void setControllerNumber(int gcid)
    {
        _controllerNo = gcid;
    }

    /**<jp>
     * コントローラーNoを取得します。
     * @return    コントローラーNo
     </jp>*/
    /**<en>
     * Gets the controller number.
     * @return    controller number
     </en>*/
    public int getControllerNumber()
    {
        return _controllerNo;
    }

    /**<jp>
     * 機器が起動中かどうか調査します。
     * @return    起動中なら true
     </jp>*/
    /**<en>
     * Check if the machine is active or not.
     * @return    'true' if it is active
     </en>*/
    public boolean isRunning()
    {
        String[] runTypes = {
            SystemDefine.MACHINE_STATE_ACTIVE,
            SystemDefine.MACHINE_STATE_STOP,
            SystemDefine.MACHINE_STATE_FAIL,
        };

        for (String runType : runTypes)
        {
            if (runType.equals(getState()))
            {
                return true;
            }
        }
        return false;
        // 旧ソース
        //        switch (_state)
        //        {
        //            case STATE_ACTIVE:
        //            case STATE_STOP:
        //            case STATE_FAIL:
        //                return (true);
        //
        //            case STATE_DISCONNECT:
        //            case STATE_OFFLINE:
        //                break;
        //        }
        //
        //        return (false);
    }

    /**<jp>
     * 機器が未接続かどうか調査します。
     * @return    未接続なら true
     </jp>*/
    /**<en>
     * Checks if the machine is disconnected or not.
     * @return    'true' if it is disconnected.
     </en>*/
    public boolean isDisconnected()
    {
        return SystemDefine.MACHINE_STATE_DISCONNECT.equals(_state);
    }

    /**<jp>
     * インスタンスを保管・取得するためのハンドラを設定します。
     * @param hndler エンティティ・ハンドラ
     </jp>*/
    /**<en>
     * Sets the handler in order to store and get instances.
     * @param hndler entity handler
     </en>*/
    public void setHandler(EntityHandler hndler)
    {
        _handler = hndler;
    }

    /**<jp>
     * インスタンスを保管・取得するためのハンドラを取得します。
     * @return エンティティ・ハンドラ
     </jp>*/
    /**<en>
     * Getse the handler in order to store and get instances.
     * @return entity handler
     </en>*/
    public EntityHandler getHandler()
    {
        return _handler;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

