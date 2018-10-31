// $Id: ToolAs21MachineAlterKey.java 87 2008-10-04 03:07:38Z admin $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * Machineテーブルの更新を行うための情報を定義したクラスです。
 * AS21MachineStateHandlerでテーブル更新を行う場合に使用されます。
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
 * This class is the definition of information which is used to update the Machine table.
 * It will be used when updating the tables by AS21MachineStateHandler.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolAs21MachineAlterKey extends ToolSQLAlterKey
{
    // Class fields --------------------------------------------------

    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String MACHINETYPE    = "MACHINE_TYPE";
    private static final String MACHINENO      = "MACHINE_NO";
    private static final String STATUSFLAG     = "STATUS_FLAG";
    private static final String ERRORCODE      = "ERROR_CODE";
    private static final String CONTROLLERNO   = "CONTROLLER_NO";

    // Class variables -----------------------------------------------

    private static final String[] Columns =
    {
        MACHINETYPE,
        MACHINENO,
        STATUSFLAG,
        ERRORCODE,
        CONTROLLERNO
    };

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
        return ("$Revision: 87 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 宣言されたテーブルカラムの初期設定を行います。
     </jp>*/
    /**<en>
     * Initialize the declared table column.
     </en>*/
    public ToolAs21MachineAlterKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    //<jp>======================<更新条件設定メソッド>====================</jp>
    //<en>================<Method of update condition settings>=================</en>

    /**<jp>
     * MACHINETYPEの検索値をセットします。
     * @param MACHINETYPEの検索値
     </jp>*/
    /**<en>
     * Set the search value of MACHINETYPE.
     * @param type :the search value of MACHINETYPE
     </en>*/
    public void setMachineType(int type)
    {
        setValue(MACHINETYPE, type);
    }

    /**<jp>
     * MACHINETYPEの検索値を取得します。
     * @param MACHINETYPE
     </jp>*/
    /**<en>
     * Retrieve the search value of MACHINETYPE.
     * @return type
     </en>*/
    public int getMachineType()
    {
        Integer intobj = (Integer)getValue(MACHINETYPE);
        return (intobj.intValue());
    }

    /**<jp>
     * MACHINENOの検索値をセットします。
     * @param MACHINENOの検索値
     </jp>*/
    /**<en>
     * Set the search value of MACHINENO.
     * @param num :the search value of MACHINENO
     </en>*/
    public void setMachineNo(int num)
    {
        setValue(MACHINENO, num);
    }

    /**<jp>
     * MACHINENOの検索値を取得します。
     * @param MACHINENO
     </jp>*/
    /**<en>
     * Retrieve the search value of MACHINENO.
     * @return number
     </en>*/
    public int getMachineNo()
    {
        Integer intobj = (Integer)getValue(MACHINENO);
        return (intobj.intValue());
    }

    /**<jp>
     * CONTROLLERNOの検索値をセットします。
     * @param CONTROLLERNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of CONTROLLERNO.
     * @param num :the search value of CONTROLLERNO
     </en>*/
    public void setControllerNo(int num)
    {
        setValue(CONTROLLERNO, num);
    }

    /**<jp>
     * CONTROLLERNOの検索値を取得します。
     * @param CONTROLLERNO
     </jp>*/
    /**<en>
     * Retrieve the search value of CONTROLLERNO.
     * @return controller number
     </en>*/
    public int getControllerNo()
    {
        Integer intobj = (Integer)getValue(CONTROLLERNO);
        return (intobj.intValue());
    }

    //<jp>======================<更新値設定メソッド>======================</jp>
    //<en>============<Method of update condition settings>================</en>

    /**<jp>
     * STATUSFLAGの更新値をセットします。
     * @param STATUSFLAGの更新値
     </jp>*/
    /**<en>
     * Set the update value of STATUSFLAG.
     * @param num :the update value of STATUSFLAG
     </en>*/
    public void updateStatusFlag(int num)
    {
        setUpdValue(STATUSFLAG, num);
    }

    /**<jp>
     * ERRORCODEの更新値をセットします。
     * @param ERRORCODEの更新値
     </jp>*/
    /**<en>
     * Set the update value of ERRORCODE.
     * @param ercd :the update value of ERRORCODE
    </en>*/

    public void updateErrorCode(String ercd)
    {
        setUpdValue(ERRORCODE, ercd);
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

