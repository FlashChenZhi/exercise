// $Id: StationParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;
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
 * This is an entity class which will be used in station.
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
public class StationParameter extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
    * 製番フォルダのパス
    </jp>*/
    /**<en>
    * path of the product number folder
    </en>*/
    String wFilePath = "";
    
    /**<jp> ステーションNo </jp>*/
    /**<en> station no. </en>*/
    protected String wNumber = "" ;

    /**<jp> ステーション名称 </jp>*/
    /**<en> station name </en>*/
    protected String wName = ""  ;
    
    /**<jp> ステーション種別 </jp>*/
    /**<en> station type </en>*/
    protected int wType = 0 ;

    /**<jp> アイルステーションNo. </jp>*/
    /**<en> aisle station no. </en>*/
    protected String wAisleStationNo = "";

    /**<jp> コントローラーNo. </jp>*/
    /**<en> controller no.</en>*/
    protected int wControllerNumber = 0;

    /**<jp> 設定区分 </jp>*/
    /**<en> set type </en>*/
    protected int wSettingType = 0 ;

    /**<jp> 到着報告チェック </jp>*/
    /**<en> arrival report check </en>*/
    protected int wArrivalCheck = 0;

    /**<jp> 荷姿チェック </jp>*/
    /**<en> load size check </en>*/
    protected int wLoadSizeCheck = 0;
    
    /**<jp> 作業場種別 </jp>*/
    /**<en> workshop type </en>*/
    protected int wWorkplaceType = 0 ;
    
    /**<jp> 作業表示運用 </jp>*/
    /**<en> on-line indication </en>*/
    protected int wOperataionDisplay = 0;

    /**<jp> 再入庫作業有無 </jp>*/
    /**<en> availability of restorage works </en>*/
    protected int wReStoringOperation = 0;
    
    /**<jp> 再入庫搬送指示送信有無 </jp>*/
    /**<en> sendability of restorage instructions </en>*/
    protected int wReStoringInstruction = 0;
    
    /**<jp> 払出し区分 </jp>*/
    /**<en> removal </en>*/
    protected int wRemove = 0 ;

    /**<jp> モード切替種別 </jp>*/
    /**<en> mode switch</en>*/
    protected int wModeType = 0;

    /**<jp> 最大搬送指示可能数 </jp>*/
    /**<en> max. number of carry instruction sendable </en>*/
    protected int wMaxInstruction = 0 ;

    /**<jp> 最大出庫指示可能数 </jp>*/
    /**<en> max. number of retrieval instruction sendable </en>*/
    protected int wMaxPalletQuantity = 0 ;
    
    /**<jp> ステーションの所属する倉庫No </jp>*/
    /**<en> warehouse no. that the station belong to </en>*/
    protected String wWareHouseStationNo ;

    /**<jp> ステーションの所属する倉庫名称 </jp>*/
    /**<en> name of the warehouse that the station belongs to </en>*/
    protected String wWareHouseName ;
    
    /**<jp> 親ステーションNo. </jp>*/
    /**<en> parent station no.. </en>*/
    protected String wParentNumber = null ;
    
    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
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
     * Create the new <code>Station</code> instance. if the instance which has stations 
     * already defined, please use <code>StationFactory</code> class.
     * As transaction controls are not internally conducted, it is necessary to commit externally.
     </en>*/
    public StationParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
    * 製番フォルダのパスをセットします。
    * @param filepath
    </jp>*/
    /**<en>
    * Set the path of the product number folder.
    * @param filepath
    </en>*/
    public void setFilePath(String filepath)
    {
        wFilePath = filepath;
    }
    
    /**<jp>
    * 製番フォルダのパスを返します。
    * @return 製番フォルダのパス
    </jp>*/
    /**<en>
    * Return the path of the product number folder.
    * @return path of the product number folder
    </en>*/
    public String getFilePath()
    {
        return wFilePath;
    }
    
    /**<jp>
     * ステーションNo.をセットします。
     * @param StationNo
     </jp>*/
    /**<en>
     * Set the station no.
     * @param StationNo
     </en>*/
    public void setNumber(String stnm)
    {
        wNumber = stnm ;
    }
    
    /**<jp>
     * ステーションNo.を取得します。
     * @return wNumber
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return wNumber
     </en>*/
    public String getNumber()
    {
        return wNumber ;
    }
    
    /**<jp>
     * ステーション名称をセットします。
     * @param Name
     </jp>*/
    /**<en>
     * Set the station name.
     * @param Name
     </en>*/
    public void setName(String nm)
    {
        wName = nm ;
    }

    /**<jp>
     * ステーション名称を取得します。
     * @return wName
     </jp>*/
    /**<en>
     * Retrieve the station name.
     * @return wName
     </en>*/
    public String getName()
    {
        return wName ;
    }
    
    /**<jp>
     * ステーション種別をセットします。
     * @param Type
     </jp>*/
    /**<en>
     * Set the station type.
     * @param Type
     </en>*/
    public void setType(int type)
    {
        wType = type ;
    }

    /**<jp>
     * ステーション種別を取得します。
     * @return wType
     </jp>*/
    /**<en>
     * Retrieve the station type.
     * @return wType
     </en>*/
    public int getType()
    {
        return wType;
    }
    
    /**<jp>
     * アイルステーションNoをセットします。
     * @param AisleStationNo
     </jp>*/
    /**<en>
     * Set the aisle station no.
     * @param AisleStationNo
     </en>*/
    public void setAisleStationNo(String arg)
    {
        wAisleStationNo = arg;
    }

    /**<jp>
     * アイルステーションNoを取得します。
     * @return wAisleStationNo
     </jp>*/
    /**<en>
     * Retrieve the aisle station no.
     * @return wAisleStationNo
     </en>*/
    public String getAisleStationNo()
    {
        return wAisleStationNo;
    }
    
    /**<jp>
     * コントローラーNo.をセットします。
     * @param ControllerNumber
     </jp>*/
    /**<en>
     * Set the controller no.
     * @param ControllerNumber
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
     * Retrieve the controller no.
     * @return wControllerNumber
     </en>*/
    public int getControllerNumber()
    {
        return wControllerNumber;
    }
    
    /**<jp>
     * 設定区分をセットします。
     * @param SettingType
     </jp>*/
    /**<en>
     * Set the set type.
     * @param SettingType
     </en>*/
    public void setSettingType(int stype)
    {
        wSettingType = stype ;
    }

    /**<jp>
     * 設定区分を取得します。
     * @return wSettingType
     </jp>*/
    /**<en>
     * Retrieve the set type.
     * @return wSettingType
     </en>*/
    public int getSettingType()
    {
        return wSettingType;
    }
    
    /**<jp>
     * 到着報告チェックをセットします。
     * @param ArrivalCheck
     </jp>*/
    /**<en>
     * Set the arrival report check.
     * @param ArrivalCheck
     </en>*/
    public void setArrivalCheck(int ari)
    {
        wArrivalCheck = ari ;
    }

    /**<jp>
     * 到着報告チェックを返します。
     * @return wArrivalCheck
     </jp>*/
    /**<en>
     * Return the arrival report check.
     * @return wArrivalCheck
     </en>*/
    public int getArrivalCheck()
    {
        return wArrivalCheck ;
    }
    
    /**<jp>
     * 荷姿チェックをセットします。
     * @param LoadSizeCheck
     </jp>*/
    /**<en>
     * Set the load size check.
     * @param LoadSizeCheck
     </en>*/
    public void setLoadSizeCheck(int load)
    {
        wLoadSizeCheck = load ;
    }

    /**<jp>
     * 荷姿チェックを返します。
     * @return wLoadSizeCheck
     </jp>*/
    /**<en>
     * Return the load size check.
     * @return wLoadSizeCheck
     </en>*/
    public int getLoadSizeCheck()
    {
        return wLoadSizeCheck ;
    }
    
    /**<jp>
     * 作業場種別をセットします。
     * @param wptype 作業場種別
     </jp>*/
    /**<en>
     * Set the workshop type.
     * @param wptype workshop type
     </en>*/
    public void setWorkplaceType(int wptype)
    {
        wWorkplaceType = wptype ;
    }
    
    /**<jp>
     * 作業場種別を取得します。
     * @return 作業場種別
     </jp>*/
    /**<en>
     * Retrieve the workshop type.
     * @return workshop type
     </en>*/
    public int getWorkplaceType()
    {
        return wWorkplaceType ;
    }

    /**<jp>
     * 作業表示運用をセットします。
     * @param OperationDisplay
     </jp>*/
    /**<en>
     * Set the on-line indication.
     * @param OperationDisplay
     </en>*/
    public void setOperationDisplay(int dis)
    {
        wOperataionDisplay = dis ;
    }
    
    /**<jp>
     * 作業表示運用を取得します。
     * @return wOperataionDisplay
     </jp>*/
    /**<en>
     * Retrieve the on-line indication.
     * @return wOperataionDisplay
     </en>*/
    public int getOperationDisplay()
    {
        return wOperataionDisplay ;
    }
    
    /**<jp>
     * 再入庫作業有無をセットします。
     * @param ReStoringOperation
     </jp>*/
    /**<en>
     * Set the availability of restorage works.
     * @param ReStoringOperation
     </en>*/
    public void setReStoringOperation(int type)
    {
        wReStoringOperation = type ;
    }

    /**<jp>
     * 再入庫作業有無を取得します。
     * @return wReStoringOperation
     </jp>*/
    /**<en>
     * Retrieve the availability of restorage works.
     * @return wReStoringOperation
     </en>*/
    public int getReStoringOperation()
    {
        return wReStoringOperation ;
    }

    /**<jp>
     * 再入庫搬送指示送信有無をセットします。
     * @param ReStoringInstruction
     </jp>*/
    /**<en>
     * Set the sendability of restorage instructions.
     * @param ReStoringInstruction
     </en>*/
    public void setReStoringInstruction(int retype)
    {
        wReStoringInstruction = retype ;
    }

    /**<jp>
     * 再入庫搬送指示送信有無を取得します。
     * @return wReStoringInstruction
     </jp>*/
    /**<en>
     * Retrieve the sendability of restorage instructions.
     * @return wReStoringInstruction
     </en>*/
    public int getReStoringInstruction()
    {
        return wReStoringInstruction ;
    }

    /**<jp>
     * 払出し区分をセットします。
     * @param mt 払出し区分
     </jp>*/
    /**<en>
     * Set the removal type.
     * @param mt removal
     </en>*/
    public void setRemove(int rmv)
    {
        wRemove = rmv;
    }

    /**<jp>
     * 払出し区分を取得します。
     * @return wRemove
     </jp>*/
    /**<en>
     * Retrieve the removal type.
     * @return wRemove
     </en>*/
    public int getRemove()
    {
        return wRemove;
    }

    /**<jp>
     * モード切替種別をセットします。
     * @param ModeType
     </jp>*/
    /**<en>
     * Set the type of mode switch.
     * @param ModeType
     </en>*/
    public void setModeType(int mt)
    {
        wModeType = mt;
    }

    /**<jp>
     * モード切替種別を取得します。
     * @return wModeType
     </jp>*/
    /**<en>
     * Retrieve the type of mode switch.
     * @return wModeType
     </en>*/
    public int getModeType()
    {
        return wModeType;
    }

    /**<jp>
     * 最大搬送指示可能数をセットします。
     * @param MaxInstruction
     </jp>*/
    /**<en>
     * Set the max. number of carry instruction sendable.
     * @param nm
     </en>*/
    public void setMaxInstruction(int nm)
    {
        wMaxInstruction = nm ;
    }

    /**<jp>
     * 最大搬送指示可能数を取得します。
     * @return wMaxInstruction
     </jp>*/
    /**<en>
     * Retrieve the max. number of carry instruction sendable.
     * @return wMaxInstruction
     </en>*/
    public int getMaxInstruction()
    {
        return wMaxInstruction ;
    }
    
    /**<jp>
     * 最大出庫指示可能数をセットします。
     * @param pnum
     </jp>*/
    /**<en>
     * Set the max. number of retrieval instruction sendable.
     * @param pnum
     </en>*/
    public void setMaxPalletQuantity(int pnum)
    {
        wMaxPalletQuantity = pnum ;
    }

    /**<jp>
     * 最大出庫指示可能数を取得します。
     * @return wMaxPalletQuantity
     </jp>*/
    /**<en>
     * Retrieve the max. number of retrieval instruction sendable.
     * @return wMaxPalletQuantity
     </en>*/
    public int getMaxPalletQuantity()
    {
        return wMaxPalletQuantity ;
    }
    
    /**<jp>
     * 倉庫Noを設定します。
     * @param WareHouseStationNo
     </jp>*/
    /**<en>
     * Set the warehouse no.
     * @param WareHouseStationNo
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
     * Retrieve the warehouse no.
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
     * Retrieve the warehouse name.
     * @return wWareHouseName
     </en>*/
    public String getWareHouseName()
    {
        return wWareHouseName;
    }

    /**<jp>
     * 親ステーションNoを設定します。
     * @param pnum ParentNumber
     </jp>*/
    /**<en>
     * Set the parent station no.
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
     * Retrieve the parent station no.
     * @return wParentNumber
     </en>*/
    public String getParentNumber()
    {
        return wParentNumber;
    }

}
//end of class

