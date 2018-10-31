// $Id: MachineState.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.equipment;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 機器状態の管理を行うための仮想クラスです。機器状態に関する実装は
 * 下位層のコントローラによって異なるので、各プロトコルごとの実装を利用するようにします。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/03/19</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the virtual class which will be used to control the machine status. The implementations
 * of machine status differ depending on the controllers of lower class, therefore, please utilize the implementations of each protocol.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/03/19</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public abstract class MachineState
        extends Object
{
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

    // Public methods ------------------------------------------------
    /**<jp>
     * 機器の状態を設定します。詳細の処理についてはサブクラスで実装する必要があります。
     * @param stat  機器の状態
     </jp>*/
    /**<en>
     * Set the machine status. It is necessary that subclasses should implement the detailed processes.
     * @param stat  machine status
     </en>*/
    public abstract void setState(String stat);

    /**<jp>
     * 機器の状態を取得します。詳細の処理についてはサブクラスで実装する必要があります。
     * @return    機器の状態
     </jp>*/
    /**<en>
     * Retrieves the machine status. It is necessary that subclasses should implement the detailed processes.
     * @return    machine status
     </en>*/
    public abstract String getState();

    /**<jp>
     * 機器のエラーコードを設定します。詳細の処理についてはサブクラスで実装する必要があります。
     * @param errcode  機器のエラーコード
     </jp>*/
    /**<en>
     * Error code of the machine is set. Detaile processing must be implemented by sub class.
     * @param errcode  :machine error code
     </en>*/
    public abstract void setErrorCode(String errcode);

    /**<jp>
     * 機器のエラーコードを取得します。
     * 詳細の処理についてはサブクラスで実装する必要があります。
     * @return    機器のエラーコード
     </jp>*/
    /**<en>
     * Retrieves the error codes of machine.
     * It is necessary that subclasses should implement the detailed processes.
     * @return    errror codes of machine
     </en>*/
    public abstract String getErrorCode();

    /**<jp>
     * 機器が起動中かどうか調査します。詳細の処理についてはサブクラスで実装する必要があります。
     * @return    起動中なら true
     </jp>*/
    /**<en>
     * Investigate whether/not the machine is starting. 
     * It is necessary that subclasses should implement the detailed processes.
     * @return    true if starting
     </en>*/
    public abstract boolean isRunning();

    /**<jp>
     * 機器が未接続かどうか調査します。詳細の処理についてはサブクラスで実装する必要があります。
     * @return    未接続なら true
     </jp>*/
    /**<en>
     * Checks if the machine is disconnected or not.
     * It is necessary that subclasses should implement the detailed processes.
     * @return    'true' if it is disconnected.
     </en>*/
    public abstract boolean isDisconnected();

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

