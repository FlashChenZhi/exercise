// $Id: As21MachineState.java 7255 2010-02-26 05:58:43Z kanda $
package jp.co.daifuku.wms.asrs.tool.communication.as21 ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineAlterKey;
import jp.co.daifuku.wms.asrs.tool.equipment.MachineState;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

/**<jp>
 * AS21プロトコルにおける、機器状態の管理を行うためのクラスです。
 * 機器そのものの状態と、エラーコード、切り離しなどの概念上の利用可能状態を管理します。
 * 最新の状態を取得するためには、<code>ToolAs21MachineStateHandler</code>を利用して、最新のインスタンスを取得してください。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7255 $, $Date: 2010-02-26 14:58:43 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </jp>*/
/**<en>
 * This class controls the machine status according to the AS21 protocol.
 * It controls the status of machines themselves, and the conceptional states of 
 * availability such as error code, off-line, etc.
 * In order to retrieve the latest status, please utilize the <code>ToolAs21MachineStateHandler</code>
 * and retrieve hte latest instance.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7255 $, $Date: 2010-02-26 14:58:43 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </en>*/
public class As21MachineState extends MachineState
{
    // Class fields -----------------------------------------------
    /**<jp>
     * プロトコルタイプを表すフィールド(AS21プロトコル)
     </jp>*/
    /**<en>
     * Field of protocol type (A21 protocol)
     </en>*/
    public static final String PROTOCOL_AS21 = "AS21" ;

    /**<jp>
     * 機器状態を表すフィールド (未接続)
     </jp>*/
    /**<en>
     * Field of machine status (disconnected)
     </en>*/    
    public static final int STATE_DISCONNECT = -1 ;
    
    /**<jp>
     * 機器状態を表すフィールド (運転中)
     </jp>*/
    /**<en>
     * Field of machine status  (active)
     </en>*/
    public static final int STATE_ACTIVE = 0 ;
    
    /**<jp>
     * 機器状態を表すフィールド (停止中)
     </jp>*/
    /**<en>
     * Field of machine status (stopped)
     </en>*/
    public static final int STATE_STOP = 1 ;
    
    /**<jp>
     * 機器状態を表すフィールド (異常中)
     </jp>*/
    /**<en>
     * Field of machine status (failure)
     </en>*/
    public static final int STATE_FAIL = 2 ;
    
    /**<jp>
     * 機器状態を表すフィールド (切離し)
     </jp>*/
    /**<en>
     * Field of machine status (off-line)
     </en>*/
    public static final int STATE_OFFLINE = 3 ;

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーションNo
     </jp>*/
    /**<en>
     * station no.
     </en>*/
    protected String wStationNo = null;
    
    /**<jp>
     * 機器タイプ
     </jp>*/
    /**<en>
     * machine type
     </en>*/
    protected int wType = 0;
    
    /**<jp>
     * 機器番号(号機番号)
     </jp>*/
    /**<en>
     * machine no.
     </en>*/
    protected int wNumber = 0;
    
    /**<jp>
     * 機器状態
     </jp>*/
    /**<en>
     * machine status
     </en>*/
    protected int wState = 0;
    
    /**<jp>
     * エラーコード
     </jp>*/
    /**<en>
     * error code
     </en>*/
    protected String wErrCode = null;
    
    // DFKLOOK 20100222　ここから追加
    /**<jp>
     * 機器名称
     </jp>*/
    /**<en>
     * device name.
     </en>*/
    protected String wDeviceName = null;
    // DFKLOOK ここまで
    
    /**<jp>
     * コントローラーNo
     </jp>*/
    /**<en>
     * controller no.
     </en>*/
    protected int wControllerNumber = 0;
        
    /**<jp>
     * インスタンス・ハンドラ
     </jp>*/
    /**<en>
     * instance handler
     </en>*/
    protected ToolEntityHandler wHandler = null ;

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
        return ("$Revision: 7255 $,$Date: 2010-02-26 14:58:43 +0900 (金, 26 2 2010) $") ;
    }
    
    // Constructors --------------------------------------------------
    /**<jp>
     * As21MachineStateオブジェクトを構築します。
     </jp>*/
    /**<en>
     * Constructs the As21MachineState object.
     </en>*/
    public As21MachineState()
    {
    }

    /**<jp>
     * 機器のタイプ、コントローラナンバーと号機番号から、<code>As21MachineState</code>のインスタンスを作成します。<BR>
     * @param  type 作成する<code>Machine</code>のタイプ
     * @param  num 作成する<code>Machine</code>の号機番号
     * @param  cnt 作成する<code>Machine</code>のコントローラナンバー
     </jp>*/
    /**<en>
     * Create the <code>As21MachineState</code> instance according to the machine type, controller no.
     * and the machine no.<BR>
     * @param  type :type of <code>Machine</code> to create
     * @param  num  :machine no. of <code>Machine</code> to create
     * @param  cnt  :controller no. of <code>Machine</code> to create
     </en>*/
    public As21MachineState(int type, int num, int cnt)
    {
        setType(type) ;
        setNumber(num) ;
        setControllerNumber(cnt);
    }
    
    /**<jp>
     * 機器のタイプと号機番号から、<code>As21MachineState</code>のインスタンスを作成します。<BR>
     * @param  type 作成する<code>MachineState</code>のタイプ
     * @param  num 作成する<code>MachineState</code>の号機番号
     </jp>*/
    /**<en>
     * Based on the machine type and the machine no., generate the instance of 
     * <code>As21MachineState</code>.<BR>
     * @param  type :type of <code>MachineState</code> creating
     * @param  num  :machine no. of <code>MachineState</code> creating
     </en>*/
    public As21MachineState(int type, int num)
    {
        setType(type) ;
        setNumber(num) ;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステーションNoに値をセットします。
     * @param stno セットするステーションNo
     </jp>*/
    /**<en>
     * Set the value of station no.
     * @param stno :station no. to set
     </en>*/
    public void setStationNo(String stno)
    {
        wStationNo = stno ;
    }

    /**<jp>
     * ステーションNoを取得します。
     * @return ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return  station no.
     </en>*/
    public String getStationNo()
    {
        return wStationNo ;
    }

    /**<jp>
     * 機器のタイプを設定します。
     * @param type  機器のタイプ
     </jp>*/
    /**<en>
     * Set the machine type.
     * @param type  :machine type
     </en>*/
    public void setType(int type)
    {
        wType = type ;
    }

    /**<jp>
     * 機器のタイプを取得します。
     * @return    機器のタイプ
     </jp>*/
    /**<en>
     * Retrieve the machine type.
     * @return    machine type
     </en>*/
    public int getType()
    {
        return (wType) ;
    }

    /**<jp>
     * 機器番号(号機)を設定します。
     * @param num  機器番号(号機)
     </jp>*/
    /**<en>
     * Sets the machine number.
     * @param num  machine number
     </en>*/
    public void setNumber(int num)
    {
        wNumber = num ;
    }

    /**<jp>
     * 機器番号(号機)を取得します。
     * @return    機器番号(号機)
     </jp>*/
    /**<en>
     * Gets the machine number.
     * @return    machine number
     </en>*/
    public int getNumber()
    {
        return (wNumber) ;
    }

    /**<jp>
     * 機器の状態を設定します。状態の一覧は、このクラスのフィールドとして定義されています。
     * @param stat  機器の状態を設定します。
     </jp>*/
    /**<en>
     * Set the machine status. The list of status is defined as a field of this class.
     * @param stat  :set the status of the machine.
     </en>*/
    public void setState(int stat)
    {
        wState = stat ;
    }

    /**<jp>
     * 機器の状態を取得します。状態の一覧は、このクラスのフィールドとして定義されています。
     * @return    機器の状態
     </jp>*/
    /**<en>
     * Retrieve the machine status. The list of the status is defined as a field of this class.
     * @return    machine status
     </en>*/
    public int getState()
    {
        return (wState) ;
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
        wErrCode = errcode ;
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
        return (wErrCode) ;
    }

    // DFKLOOK 20100222追加 ここから
    /**<jp>
     * 機器名称を設定します。
     * @param   機器名称
     </jp>*/
    /**<en>
     * Sets the device name.
     * @param devname  the device name
     </en>*/
    public void setDeviceName(String devname)
    {
        wDeviceName = devname ;
    }

    /**<jp>
     * 機器名称を取得します。
     * @return    機器名称
     </jp>*/
    /**<en>
     * Gets the device name.
     * @return    the device name
     </en>*/
    public String getDeviceName()
    {
        return (wDeviceName) ;
    }
    // DFKLOOK ここまで
    
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
        wControllerNumber = gcid ;
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
        return (wControllerNumber) ;
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
        switch (wState)
        {
            case STATE_ACTIVE:
            case STATE_STOP :
            case STATE_FAIL :
                return (true) ;
                
            case STATE_DISCONNECT :
            case STATE_OFFLINE :
                break;
        }
        
        return (false);
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
        return (wState == STATE_DISCONNECT) ;
    }

    /**<jp>
     * インスタンスを保管・取得するためのハンドラを設定します。
     * @param hndler エンティティ・ハンドラ
     </jp>*/
    /**<en>
     * Sets the handler in order to store and get instances.
     * @param hndler entity handler
     </en>*/
    public void setHandler(ToolEntityHandler hndler)
    {
        wHandler = hndler ;
    }

    /**<jp>
     * インスタンスを保管・取得するためのハンドラを取得します。
     * @return エンティティ・ハンドラ
     </jp>*/
    /**<en>
     * Getse the handler in order to store and get instances.
     * @return entity handler
     </en>*/
    public ToolEntityHandler getHandler()
    {
        return (wHandler) ;
    }

    /**<jp>
     * 機器状態を未接続（不定）に変更します。ハンドラを使用してデータベースを更新します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the machine status to discnnected (indefinite) .
     * Database will be updated by using handler.
     * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
     * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
     * @throws NotFoundException      :Notifies if this station cannot be found in database.
     </en>*/
    public void unknown() throws InvalidDefineException, ReadWriteException, NotFoundException
    {
        ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
        ak.setMachineType(this.getType());
        ak.setMachineNo(this.getNumber());
        ak.setControllerNo(this.getControllerNumber());
        ak.updateStatusFlag(STATE_DISCONNECT);
        ak.updateErrorCode(null);
        getHandler().modify(ak);
        setState(STATE_DISCONNECT);
        setErrorCode(null);
    }

    /**<jp>
     * 機器状態を運転中に変更します。ハンドラを使用してデータベースを更新します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the machine status to active. 
     * Database will be updated by using handler.
     * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
     * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
     * @throws NotFoundException      :Notifies if this station cannot be found in database.
     </en>*/
    public void active() throws InvalidDefineException, ReadWriteException, NotFoundException
    {
        ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
        ak.setMachineType(this.getType());
        ak.setMachineNo(this.getNumber());
        ak.setControllerNo(this.getControllerNumber());
        ak.updateStatusFlag(STATE_ACTIVE);
        ak.updateErrorCode(null);
        getHandler().modify(ak);
        setState(STATE_ACTIVE);
        setErrorCode(null);
    }

    /**<jp>
     * 機器状態を停止中に変更します。ハンドラを使用してデータベースを更新します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the machine status to stopped.
     * Database will be updated by using handler.
     * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
     * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
     * @throws NotFoundException      :Notifies if this station cannot be found in database.
     </en>*/
    public void stop() throws InvalidDefineException, ReadWriteException, NotFoundException
    {
        ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
        ak.setMachineType(this.getType());
        ak.setMachineNo(this.getNumber());
        ak.setControllerNo(this.getControllerNumber());
        ak.updateStatusFlag(STATE_STOP);
        ak.updateErrorCode(null);
        getHandler().modify(ak);
        setState(STATE_STOP);
        setErrorCode(null);
    }

    /**<jp>
     * 機器状態を異常に変更します。ハンドラを使用してデータベースを更新します。
     * @param  errcd 異常コード
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the machine status to failure.
     * Database will be updated by using handler.
     * @param  errcd :error code
     * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
     * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
     * @throws NotFoundException      :Notifies if this station cannot be found in database.
     </en>*/
    public void failure(String errcd) throws InvalidDefineException, ReadWriteException, NotFoundException
    {
        ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
        ak.setMachineType(this.getType());
        ak.setMachineNo(this.getNumber());
        ak.setControllerNo(this.getControllerNumber());
        ak.updateStatusFlag(STATE_FAIL);
        ak.updateErrorCode(errcd);
        getHandler().modify(ak);
        setState(STATE_FAIL);
        setErrorCode(errcd);
    }

    /**<jp>
     * 機器状態を切離中に変更します。ハンドラを使用してデータベースを更新します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the machine status to off-line. 
     * Database will be updated by using handler.
     * @throws InvalidDefineException :Notifies if there are any inconsistent update conditions for the table.
     * @throws ReadWriteException     :Notifies if any trouble occurred in accessing data.
     * @throws NotFoundException      :Notifies if this station cannot be found in database.
     </en>*/
    public void offline() throws InvalidDefineException, ReadWriteException, NotFoundException
    {
        ToolAs21MachineAlterKey ak = new ToolAs21MachineAlterKey();
        ak.setMachineType(this.getType());
        ak.setMachineNo(this.getNumber());
        ak.setControllerNo(this.getControllerNumber());
        ak.updateStatusFlag(STATE_OFFLINE);
        ak.updateErrorCode(null);
        getHandler().modify(ak);
        setState(STATE_OFFLINE);
        setErrorCode(null);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

