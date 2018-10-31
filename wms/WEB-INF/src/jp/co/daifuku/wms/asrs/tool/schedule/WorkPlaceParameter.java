// $Id: WorkPlaceParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * ステーションで使用されるエンティティクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is an entity class used when setting up the stations.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class WorkPlaceParameter
        extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**<jp>
     * 製番フォルダのパス
     </jp>*/
    /**<en>
     * path of product number folder
     </en>*/
    String wFilePath = "";

    /**<jp> 親ステーションNo </jp>*/
    /**<en> parent station no. </en>*/
    protected String wParentNumber = "";

    /**<jp> 親ステーション名称 </jp>*/
    /**<en> name of the parent station </en>*/
    protected String wParentName = "";

    /**<jp> 作業場種別 </jp>*/
    /**<en> workshop type</en>*/
    protected int wWorkPlaceType = 0;

    /**<jp> ステーションNo </jp>*/
    /**<en> station no. </en>*/
    protected String wNumber = "";

    /**<jp> ステーション名称 </jp>*/
    /**<en> station name </en>*/
    protected String wName = "";

    /**<jp> ステーション種別 </jp>*/
    /**<en> station type </en>*/
    protected int wType = 0;

    /**<jp> ステーションの所属する倉庫No </jp>*/
    /**<en> warehouse no. the station belongs to </en>*/
    protected String wWareHouseStationNo;

    /**<jp> ステーションの所属する倉庫名称 </jp>*/
    /**<en> warehouse name the station belongs to </en>*/
    protected String wWareHouseName;

    /**<jp> クラス名称 </jp>*/
    /**<en> class name </en>*/
    protected String wClassName = "";

    /**<jp> アイルステーションNo. </jp>*/
    /**<en> aisle station no.. </en>*/
    protected String wAisleNumber = "";

    /**<jp> 代表ステーション </jp>*/
    /**<en> main station </en>*/
    protected int wMain = 0;

    /**<jp> 搬送指示可能数 </jp>*/
    /**<en> number of carry instruction sendable </en>*/
    protected int wMaxInstruction = 0;

    /**<jp> 出庫指示可能数 </jp>*/
    /**<en> number of retrieval instruction sendable </en>*/
    protected int wMaxPalletQuantity = 0;

    /**<jp> コントローラーNo. </jp>*/
    /**<en> controller no. </en>*/
    protected int wControllerNumber = 0;

    /**<jp> 送信可能区分 </jp>*/
    /**<en> division of sendability </en>*/
    protected int wSendable = 0;

    /**<jp> 再入庫搬送指示有無 </jp>*/
    /**<en> Restorage Transfer Command Send Existence </en>*/
    protected int wReStoringInstruction = 0;

    /**<jp> 払出し区分 </jp>*/
    /**<en> remove type </en>*/
    protected boolean wRemove = true;

    /**<jp> モード切替種別 </jp>*/
    /**<en> Mode Change Type </en>*/
    protected int wModeType = 0;

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
     * 新しい<code>Station</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @param snum  保持するステーション番号
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create the new instance of <code>Station</code>. If the instance which owns the stations
     * already defined is wanted, please utilize <code>StationFactory</code> class.
     * is not conducted internally, it is necessary to commit the transaction externally.
     </en>*/
    public WorkPlaceParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 製番フォルダのパスをセットします。
     </jp>*/
    /**<en>
     * Set the path of product number folder.
     </en>*/
    public void setFilePath(String filepath)
    {
        wFilePath = filepath;
    }

    /**<jp>
     * 製番フォルダのパスを返します。
     </jp>*/
    /**<en>
     * Return the path of product number folder.
     </en>*/
    public String getFilePath()
    {
        return wFilePath;
    }

    /**<jp>
     * 親ステーションNoを設定します。
     * @param pnum ParentNumber
     </jp>*/
    /**<en>
     * Set the parent station no.
     * @param ParentNumber
     </en>*/
    public void setParentNumber(String pnum)
    {
        wParentNumber = pnum;
    }

    /**<jp>
     * 親ステーションNoを取得します。
     * @return wParentNumber
     </jp>*/
    /**<en>
     * Retrieves the parent station no.
     * @return wParentNumber
     </en>*/
    public String getParentNumber()
    {
        return wParentNumber;
    }

    /**<jp>
     * 親ステーション名称を設定します。
     * @param ParentName
     </jp>*/
    /**<en>
     * Set the name of the parent station.
     * @param pnum ParentName
     </en>*/
    public void setParentName(String pnum)
    {
        wParentName = pnum;
    }

    /**<jp>
     * 親ステーション名称を取得します。
     * @return wParentName
     </jp>*/
    /**<en>
     * Retrieves the name of the parent station.
     * @return wParentName
     </en>*/
    public String getParentName()
    {
        return wParentName;
    }

    /**<jp>
     * 作業場種別を設定します。
     * @param WorkPlaceType
     </jp>*/
    /**<en>
     * Set the workshop type.
     * @param type
     </en>*/
    public void setWorkPlaceType(int type)
    {
        wWorkPlaceType = type;
    }

    /**<jp>
     * 作業場種別を取得します。
     * @return wWorkPlaceType
     </jp>*/
    /**<en>
     * Retrieves the workshop type.
     * @return wWorkPlaceType
     </en>*/
    public int getWorkPlaceType()
    {
        return wWorkPlaceType;
    }

    /**<jp>
     * ステーションNo.をセットします。
     * @param StationNo
     </jp>*/
    /**<en>
     * Set the station no.
     * @param stnm StationNo
     </en>*/
    public void setNumber(String stnm)
    {
        wNumber = stnm;
    }

    /**<jp>
     * ステーションNo.を取得します。
     * @return wNumber
     </jp>*/
    /**<en>
     * Retrieves the station no.
     * @return wNumber
     </en>*/
    public String getNumber()
    {
        return wNumber;
    }

    /**<jp>
     * ステーション名称をセットします。
     * @param Name
     </jp>*/
    /**<en>
     * Set the station name.
     * @param nm Name
     </en>*/
    public void setName(String nm)
    {
        wName = nm;
    }

    /**<jp>
     * ステーション名称を取得します。
     * @return wName
     </jp>*/
    /**<en>
     * Retrieves the station name.
     * @return wName
     </en>*/
    public String getName()
    {
        return wName;
    }

    /**<jp>
     * ステーション種別をセットします。
     * @param Type
     </jp>*/
    /**<en>
     * Set the station type.
     * @param type Type
     </en>*/
    public void setType(int type)
    {
        wType = type;
    }

    /**<jp>
     * ステーション種別を取得します。
     * @return wType
     </jp>*/
    /**<en>
     * Retrieves the station type.
     * @return wType
     </en>*/
    public int getType()
    {
        return wType;
    }

    /**<jp>
     * 倉庫Noを設定します。
     * @param WareHouseStationNo
     </jp>*/
    /**<en>
     * Set the warehouse no.
     * @param whnum WareHouseStationNo
     </en>*/
    public void setWareHouseStationNo(String whnum)
    {
        wWareHouseStationNo = whnum;
    }

    /**<jp>
     * 倉庫Noを取得します。
     * @return wWareHouseStationNo
     </jp>*/
    /**<en>
     * Retrieves the warehouse no.
     * @return wWareHouseStationNo
     </en>*/
    public String getWareHouseStationNo()
    {
        return wWareHouseStationNo;
    }

    /**<jp>
     * 倉庫名称を設定します。
     * @param WareHouseName
     </jp>*/
    /**<en>
     * Set the warehouse name.
     * @param wh WareHouseName
     </en>*/
    public void setWareHouseName(String wh)
    {
        wWareHouseName = wh;
    }

    /**<jp>
     * 倉庫名称を取得します。
     * @return wWareHouseName
     </jp>*/
    /**<en>
     * Retrieves the warehouse name.
     * @return wWareHouseName
     </en>*/
    public String getWareHouseName()
    {
        return wWareHouseName;
    }

    /**<jp>
     * クラス名称を設定します。
     * @param ClassName
     </jp>*/
    /**<en>
     * Set the class name.
     * @param cn ClassName
     </en>*/
    public void setClassName(String cn)
    {
        wClassName = cn;
    }

    /**<jp>
     * クラス名称を取得します。
     * @return wClassName
     </jp>*/
    /**<en>
     * Retrieves the class name.
     * @return wClassName
     </en>*/
    public String getClassName()
    {
        return wClassName;
    }

    /**<jp>
     * アイルステーションNo.を設定します。
     * @param AisleNumber
     </jp>*/
    /**<en>
     * Set the aisle station no.
     * @param as AisleNumber
     </en>*/
    public void setAisleNumber(String as)
    {
        wAisleNumber = as;
    }

    /**<jp>
     * アイルステーションNo.を取得します。
     * @return wAisleNumber
     </jp>*/
    /**<en>
     * Retrieves the aisle station no.
     * @return wAisleNumber
     </en>*/
    public String getAisleNumber()
    {
        return wAisleNumber;
    }

    /**<jp>
     * 代表ステーションをセットします。
     * @param Main
     </jp>*/
    /**<en>
     * Set the main station.
     * @param ms Main
     </en>*/
    public void setMainStation(int ms)
    {
        wMain = ms;
    }

    /**<jp>
     * 代表ステーションを取得します。
     * @return wMain
     </jp>*/
    /**<en>
     * Retrieves the main station.
     * @return wMain
     </en>*/
    public int getMainStation()
    {
        return wMain;
    }

    /**<jp>
     * 搬送指示可能数を設定します。
     * @param nm 搬送指示可能数
     </jp>*/
    /**<en>
     * Set the number of carry instruction sendable.
     * @param nm number of carry instruction sendable
     </en>*/
    public void setMaxInstruction(int nm)
    {
        wMaxInstruction = nm;
    }

    /**<jp>
     * 搬送指示可能数を取得します。
     * @return   搬送指示可能数
     </jp>*/
    /**<en>
     * Retrieves the number of carry instruction sendable.
     * @return   number of carry instruction sendable
     </en>*/
    public int getMaxInstruction()
    {
        return wMaxInstruction;
    }

    /**<jp>
     * 出庫指示可能数を設定します。
     * @param pnum 出庫指示可能数
     </jp>*/
    /**<en>
     * Set the number of retrieval instruction sendable.
     * @param pnum number of retrieval instruction sendable
     </en>*/
    public void setMaxPalletQuantity(int pnum)
    {
        wMaxPalletQuantity = pnum;
    }

    /**<jp>
     * 出庫指示可能数を取得します。
     * @return    出庫指示可能数
     </jp>*/
    /**<en>
     * Retrieves the number of retrieval instruction sendable.
     * @return    number of retrieval instruction sendable
     </en>*/
    public int getMaxPalletQuantity()
    {
        return wMaxPalletQuantity;
    }

    /**<jp>
     * コントローラーNo.をセットします。
     * @param ControllerNumber
     </jp>*/
    /**<en>
     * Set the controller no.
     * @param arg ControllerNumber
     </en>*/
    public void setControllerNumber(int arg)
    {
        wControllerNumber = arg;
    }

    /**<jp>
     * コントローラーNo.を取得します。
     * @return wControllerNumber
     </jp>*/
    /**<en>
     * Retrieves the controller no.
     * @return wControllerNumber
     </en>*/
    public int getControllerNumber()
    {
        return wControllerNumber;
    }

    /**<jp>
     * 送信可能区分をセットします。
     * @param Sendable
     </jp>*/
    /**<en>
     * Set the division of sendability.
     * @param snd Sendable
     </en>*/
    public void setSendable(int snd)
    {
        wSendable = snd;
    }

    /**<jp>
     * 送信可能区分を取得します。
     * @param wSendable
     </jp>*/
    /**<en>
     * Retrieves the division of sendability.
     * @return wSendable
     </en>*/
    public int getSendable()
    {
        return wSendable;
    }

    /**<jp>
     * 再入庫搬送指示有無をセットします。
     * @param restoreInstruction
     </jp>*/
    /**<en>
     * Set the restorage transfer command send existence.
     * @param restoreInstruction Restorage Transfer Command Send Existence
     </en>*/
    public void setReStoringInstruction(int restoreInstruction)
    {
        wReStoringInstruction = restoreInstruction;
    }

    /**<jp>
     * 再入庫搬送指示有無を取得します。
     * @return wReStoringInstruction
     </jp>*/
    /**<en>
     * Retrieves the restorage transfer command send existence.
     * @return wReStoringInstruction
     </en>*/
    public int getReStoringInstruction()
    {
        return wReStoringInstruction;
    }

    /**<jp>
     * 払出し区分をセットします。
     * @param remove
     </jp>*/
    /**<en>
     * Set the remove type.
     * @param remove remove type
     </en>*/
    public void setRemove(boolean remove)
    {
        wRemove = remove;
    }

    /**<jp>
     * 払出し区分を取得します。
     * @return    true  払出し可能
     * @return    false 払出し不可
     </jp>*/
    /**<en>
     * Retrieves the remove type.
     * @return    true  :available of removal
     *             false unavailable of removal
     </en>*/
    public boolean isRemove()
    {
        return wRemove;
    }

    /**<jp>
     * モード切替種別をセットします。
     * @param modeType
     </jp>*/
    /**<en>
     * Set the mode change type.
     * @param modeType Mode Change Type
     </en>*/
    public void setModeType(int modeType)
    {
        wModeType = modeType;
    }

    /**<jp>
     * モード切替種別を取得します。
     * @return wModeType
     </jp>*/
    /**<en>
     * Retrieves the mode change type.
     * @return wModeType
     </en>*/
    public int getModeType()
    {
        return wModeType;
    }
}
//end of class

