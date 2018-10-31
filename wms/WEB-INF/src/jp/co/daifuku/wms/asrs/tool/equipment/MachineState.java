// $Id: MachineState.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.equipment ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

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
 * This is the virtual class which controls the machine status. As the implementations
 * of machine status differ depending on the controllers of the lower classes, 
 * It is necessary to usitlzie the implementation of respective protocols.
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
public abstract class MachineState extends ToolEntity
{
    // Class variables -----------------------------------------------
    /**<jp>
     * 機器のタイプ
     </jp>*/
    /**<en>
     * type of machine
     </en>*/
    protected int wType = 0 ;
    
    /**<jp>
     * 機器番号
     </jp>*/
    /**<en>
     * machine no.
     </en>*/
    protected int wNumber = 0 ;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }
    
    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * 機器の状態を設定します。詳細の処理についてはサブクラスで実装する必要があります。
     * @param stat  機器の状態を設定します。
     </jp>*/
    /**<en>
     * Set the status of machines. Detailed processings must be implemented in subclasses.
     * @param stat  :set the status of machines.
     </en>*/
    public abstract void setState(int stat) ;
    
    /**<jp>
     * 機器の状態を取得します。詳細の処理についてはサブクラスで実装する必要があります。
     * @return    機器の状態
     </jp>*/
    /**<en>
     * Retrieve the status of machines. Detailed processings must be implemented in subclasses.
     * @return    :the status of machines
     </en>*/
    public abstract int getState() ;

    /**<jp>
     * 機器のエラーコードを設定します。詳細の処理についてはサブクラスで実装する必要があります。
     * @param errcode  機器のエラーコード
     </jp>*/
    /**<en>
     * Set the error codes of mahines. Detailed processings must be implemented in subclasses.
      * @param errcode  :machine error code
     </en>*/
    public abstract void setErrorCode(String errcode) ;
    
    /**<jp>
     * 機器のエラーコードを取得します。
     * 詳細の処理についてはサブクラスで実装する必要があります。
     * @return    機器のエラーコード
     </jp>*/
    /**<en>
     * Retrieve the error codes of mahines.
     * Detailed processings must be implemented in subclasses.
     * @return    :error codes of machines
     </en>*/
    public abstract String getErrorCode() ;

    /**<jp>
     * 機器が起動中かどうか調査します。詳細の処理についてはサブクラスで実装する必要があります。
     * @return    起動中なら true
     </jp>*/
    /**<en>
     * Examine whether/not the machine has been started up. Detailed processings must be implemented 
     * in subclasses.
     * @return   :true if started up
     </en>*/
    public abstract boolean isRunning() ;
    
    /**<jp>
     * 機器が未接続かどうか調査します。詳細の処理についてはサブクラスで実装する必要があります。
     * @return    未接続なら true
     </jp>*/
    /**<en>
     * Checks if the machine is disconnected or not.
     * It is necessary that subclasses should implement the detailed processes.
     * @return    'true' if it is disconnected.
     </en>*/
    public abstract boolean isDisconnected() ;

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

