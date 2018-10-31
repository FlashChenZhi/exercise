package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>ReceivingOutParameter</CODE> Class used for passing parameters within the screen scheduler of the receiving package.<BR>
 * This class is used for preserving an item within every screen of the receiving package, although the contencts vary by screen.<BR>
 * 
 * 
 * <BR>
 * <DIR>
 * Headings that <CODE>ReceivingOutParameter</CODE> class stores<BR>
 * <BR>
 *     Plan Unique Key<BR>
 *     Load Unit Key<BR>
 *     Setting Unit Key<BR>
 *     Work No.<BR>
 *     Collective Work No.<BR>
 *     Status Flag<BR>
 *     Planned Receiving Date<BR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     supplier code<BR>
 *     supplier name<BR>
 *     Item Code<BR>
 *     Item Name<BR>
 *     Slip No.<BR>
 *     Row No.<BR>
 *     Work Branch<BR>
 *     Receiving Area No.<BR>
 *     Result Area No. <BR>
 *     Receiving Area Name<BR>
 *     Receiving Location<BR>
 *     Result Location<BR>
 *     Planned Lot No.<BR>
 *     Result Lot No.<BR>
 *     Detail Counts<BR>
 *     Planned Item Quantity<BR>
 *     Result Item Quantity<BR>
 *     Quantity of pieces in one case<BR>
 *     Quantity of bundles in one case<BR>
 *     Planned Quantity<BR>
 *     Result Quantity<BR>
 *     Planned Case Quantity<BR>
 *     Planned Piece Quantity<BR>
 *     Result Case Quantity<BR>
 *     Result Piece Quantity<BR>
 *     Shortage Quantity<BR>
 *     Shortage Case Quantity<BR>
 *     Shortage Piece Quantity<BR>
 *     JAN Code<BR>
 *     Case ITF<BR>
 *     Bundle ITF<BR>
 *     Receiving Date<BR>
 *     Progress Rate<BR>
 *     Work Date<BR>
 *     Work Time<BR>
 *     Result Report Type<BR>
 *     Location Indicate Type<BR>
 *     Button Control Flag<BR>
 *     Stock Control Introduce Falg<BR>
 *     Master Control Introduce Falg<BR>
 *     Quantity-of-pieces-in-one-case Update Flag<BR>
 *     Collective Conditions<BR>
 *     Register Date<BR>
 *     Last Update Date<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class ReceivingOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------
    /**
     * Plan Unique Key
     */
    private String _planUKey;

    /**
     * Load Unit Key
     */
    private String _loadUnitKey;

    /**
     * Setting Unit Key
     */
    private String _settingUnitKey;

    /**
     * Work No.
     */
    private String _jobNo;

    /**
     * Collective Work No.
     */
    private String _collectJobNo;

    /**
     * Status Flag
     */
    private String _statusFlag;

    /**
     * Planned Receing Date
     */
    private String _receivingPlanDay;

    /**
     * Consignor Code
     */
    private String _consignorCode;

    /**
     * Consignor Name
     */
    private String _consignorName;

    /**
     * Supplier Code
     */
    private String _supplierCode;

    /**
     * Supplier Name
     */
    private String _supplierName;

    /**
     * Item Code
     */
    private String _itemCode;

    /**
     * Item Name
     */
    private String _itemName;

    /**
     * Slip No.
     */
    private String _ticketNo;

    /**
     * Row No.
     */
    private int _ticketLineNo;

    /**
     * Receiving Area No.
     */
    private String _recvAreaNo;

    /**
     * Result Area No. 
     */
    private String _resultAreaNo;

    /**
     * Receiving Area Name
     */
    private String _recvAreaName;

    /**
     * Receiving Location
     */
    private String _receivingLocation;

    /**
     * Result Location
     */
    private String _resultLocation;

    /**
     * Planned Lot No.
     */
    private String _planLotNo;

    /**
     * Result Lot No.
     */
    private String _resultLotNo;

    /**
     * Detail Counts
     */
    private long _detailCnt;

    /**
     * Planned Item Quantity
     */
    private long _planItemCnt;

    /**
     * Result Item Quantity
     */
    private long _resultItemCnt;

    /**
     * Quantity of pieces in one case
     */
    private int _enteringQty;

    /**
     * Quantity of bundles in one case
     */
    private int _bundleEnteringQty;

    /**
     * Planned Quantity
     */
    private int _planQty;

    /**
     * Result Quantity
     */
    private int _resultQty;

    /**
     * Planned Case Quantity
     */
    private int _planCaseQty;

    /**
     * Planned Piece Quantity
     */
    private int _planPieceQty;

    /**
     * Result Case Quantity
     */
    private int _resultCaseQty;

    /**
     * Result Piece Quantity
     */
    private int _resultPieceQty;

    /**
     * Shortage Quantity
     */
    private int _shortageQty;

    /**
     * Shortage Case Quantity
     */
    private int _shortageCaseQty;

    /**
     * Shortage Piece Quantity
     */
    private int _shortagePieceQty;

    /**
     * JAN Code
     */
    private String _janCode;

    /**
     * Case ITF
     */
    private String _itf;

    /**
     * Bundle ITF
     */
    private String _bundleItf;

    /**
     * Receiving Date
     */
    private String _receivingDay;

    /**
     * Progress Rate
     */
    private double _progressRate;

    /**
     * Work Date
     */
    private String _workDay;

    /**
     * Work Time
     */
    private String _workTime;

    /**
     * Result Report Type
     */
    private String _reportFlag;

    /**
     * Location Indicate Type
     */
    private String _locationStyle;

    /**
     * Button Control Flag
     */
    private String _buttonControlFlag;

    /**
     * Stock Control Introduce Falg
     */
    private boolean _stockFlag;

    /**
     * Master Control Introduce Falg
     */
    private boolean _masterFlag;

    /**
     * Collective Conditions
     */
    private String _collectCondition;

    /**
     * Quantity-of-pieces-in-one-case Update Flag
     */
    private boolean _chgEnteringQty;

    private String _areaNo;
    
    private String _areaName;
    
    /**
     * Register Date
     */
    private java.util.Date _registDate;

    /**
     * Last Update Date
     */
    private java.util.Date _lastUpdateDate;

    // Public methods ------------------------------------------------
    /**
     * Returns a version of this class.
     * @return Version and Date
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**
     * Gets Plan Unique Key
     * @return Plan Unique Key
     */
    public String getPlanUKey()
    {
        return _planUKey;
    }

    /**
     * Sets Plan Unique Key
     * @param planUKey Plan Unique Key
     */
    public void setPlanUKey(String planUKey)
    {
        _planUKey = planUKey;
    }

    /**
     * Gets Load Unit Key
     * @return Load Unit Key
     */
    public String getLoadUnitKey()
    {
        return _loadUnitKey;
    }

    /**
     * Sets Load Unit Key
     * @param loadUnitKey Load Unit Key
     */
    public void setLoadUnitKey(String loadUnitKey)
    {
        _loadUnitKey = loadUnitKey;
    }

    /**
     * gets Setting Unit Key
     * @return Setting Unit Key
     */
    public String getSettingUnitKey()
    {
        return _settingUnitKey;
    }

    /**
     * Sets Setting Unit Key
     * @param settingUnitKey Setting Unit Key
     */
    public void setSettingUnitKey(String settingUnitKey)
    {
        _settingUnitKey = settingUnitKey;
    }

    /**
     * Gets Work No.
     * @return Work No.
     */
    public String getJobNo()
    {
        return _jobNo;
    }

    /**
     * Sets Work No.
     * @param jobNo Work No.
     */
    public void setJobNo(String jobNo)
    {
        _jobNo = jobNo;
    }

    /**
     * Gets Collective Work No.
     * @return Collective Work No.
     */
    public String getCollectJobNo()
    {
        return _collectJobNo;
    }

    /**
     * Sets Collective Work No.
     * @param collectJobNo Collective Work No.
     */
    public void setCollectJobNo(String collectJobNo)
    {
        _collectJobNo = collectJobNo;
    }

    /**
     * Gets Status Flag
     * @return Status Flag
     */
    public String getStatusFlag()
    {
        return _statusFlag;
    }

    /**
     * Sets Status Flag
     * @param statusFlag Status Flag
     */
    public void setStatusFlag(String statusFlag)
    {
        _statusFlag = statusFlag;
    }

    /**
     * Gets Planned Receing Date
     * @return Planned Receing Date
     */
    public String getReceivingPlanDay()
    {
        return _receivingPlanDay;
    }

    /**
     * Sets Planned Receing Date
     * @param receivingPlanDay Planned Receing Date
     */
    public void setReceivingPlanDay(String receivingPlanDay)
    {
        _receivingPlanDay = receivingPlanDay;
    }

    /**
     * Gets Consignor Code
     * @return Consignor Code
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * Sets Consignor Code
     * @param consignorCode Consignor Code
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
    }

    /**
     * Gets Consignor Name
     * @return Consignor Name
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * Sets Consignor Name
     * @param consignorName Consignor Name
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
    }

    /**
     * get Supplier Code
     * @return supplierCode
     */
    public String getSupplierCode()
    {
        return _supplierCode;
    }

    /**
     * set Supplier Code
     * @param supplierCode
     */
    public void setSupplierCode(String supplierCode)
    {
        _supplierCode = supplierCode;
    }

    /**
     * get Supplier Name
     * @return Supplier Name
     */
    public String getSupplierName()
    {
        return _supplierName;
    }

    /**
     * set Supplier Name
     * @param supplierName
     */
    public void setSupplierName(String supplierName)
    {
        _supplierName = supplierName;
    }

    /**
     * Gets Item Code
     * @return Item Code
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * Sets Item Code
     * @param itemCode Item Code
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * Gets Item Name
     * @return Item Name
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * Sets Item Name
     * @param itemName Item Name
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * Gets Slip No.
     * @return Slip No.
     */
    public String getTicketNo()
    {
        return _ticketNo;
    }

    /**
     * Sets Slip No.
     * @param ticketNo Slip No.
     */
    public void setTicketNo(String ticketNo)
    {
        _ticketNo = ticketNo;
    }

    /**
     * Gets Row No.
     * @return Row No.
     */
    public int getTicketLineNo()
    {
        return _ticketLineNo;
    }

    /**
     * Sets ticketLineNo
     * @param ticketLineNo ticketLineNo
     */
    public void setTicketLineNo(int ticketLineNo)
    {
        _ticketLineNo = ticketLineNo;
    }

    /**
     * Gets Receiving Area No.
     * @return Receiving Area No.
     */
    public String getReceivingAreaNo()
    {
        return _recvAreaNo;
    }

    /**
     * Sets Receiving Area No.
     * @param recvAreaNo Receiving Area No.
     */
    public void setReceivingAreaNo(String recvAreaNo)
    {
        _recvAreaNo = recvAreaNo;
    }

    /**
     * Gets Result Area No.
     * @return Result Area
     */
    public String getResultAreaNo()
    {
        return _resultAreaNo;
    }

    /**
     * Sets Result Area No.
     * @param resultAreaNo Result Area No.
     */
    public void setResultAreaNo(String resultAreaNo)
    {
        _resultAreaNo = resultAreaNo;
    }

    /**
     * Gets Receiving Area Name
     * @return Receiving Area Name
     */
    public String getReceivingAreaName()
    {
        return _recvAreaName;
    }

    /**
     * Sets Receiving Area Name
     * @param recvAreaName Receiving Area Name
     */
    public void setReceivingAreaName(String recvAreaName)
    {
        _recvAreaName = recvAreaName;
    }

    /**
     * Gets Receiving Location
     * @return Receiving Location
     */
    public String getReceivingLocation()
    {
        return _receivingLocation;
    }

    /**
     * Sets Receiving Location
     * @param isRecvLoc Receiving Location
     */
    public void setReceivingLocation(String isRecvLoc)
    {
        _receivingLocation = isRecvLoc;
    }

    /**
     * Gets Result Location
     * @return Result Location
     */
    public String getResultLocation()
    {
        return _resultLocation;
    }

    /**
     * Sets Result Location
     * @param resultLocation Result Location
     */
    public void setResultLocation(String resultLocation)
    {
        _resultLocation = resultLocation;
    }

    /**
     * Gets Planned Lot No.
     * @return Planned Lot No.
     */
    public String getPlanLotNo()
    {
        return _planLotNo;
    }

    /**
     * Sets Planned Lot No.
     * @param planLotNo Planned Lot No.
     */
    public void setPlanLotNo(String planLotNo)
    {
        _planLotNo = planLotNo;
    }

    /**
     * Gets Result Lot No.
     * @return Result Lot No.
     */
    public String getResultLotNo()
    {
        return _resultLotNo;
    }

    /**
     * Gets Result Lot No.
     * @param resultLotNo Result Lot No.
     */
    public void setResultLotNo(String resultLotNo)
    {
        _resultLotNo = resultLotNo;
    }

    /**
     * Gets Detail Counts
     * @return Detail Counts
     */
    public long getDetailCnt()
    {
        return _detailCnt;
    }

    /**
     * Sets Detail Counts
     * @param detailCnt Detail Counts
     */
    public void setDetailCnt(long detailCnt)
    {
        _detailCnt = detailCnt;
    }

    /**
     * Gets Planned Item Quantity
     * @return Planned Item Quantity
     */
    public long getPlanItemCnt()
    {
        return _planItemCnt;
    }

    /**
     * Sets Planned Item Quantity
     * @param planItemCnt Planned Item Quantity
     */
    public void setPlanItemCnt(long planItemCnt)
    {
        _planItemCnt = planItemCnt;
    }

    /**
     * Gets Result Item Quantity
     * @return Result Item Quantity
     */
    public long getResultItemCnt()
    {
        return _resultItemCnt;
    }

    /**
     * Sets Result Item Quantity
     * @param resultItemCnt Result Item Quantity
     */
    public void setResultItemCnt(long resultItemCnt)
    {
        _resultItemCnt = resultItemCnt;
    }

    /**
     * Gets Quantity of pieces in one case
     * @return Quantity of pieces in one case
     */
    public int getEnteringQty()
    {
        return _enteringQty;
    }

    /**
     * Sets Quantity of pieces in one case
     * @param enteringQty Quantity of pieces in one case
     */
    public void setEnteringQty(int enteringQty)
    {
        _enteringQty = enteringQty;
    }

    /**
     * Gets Quantity of bundles in one case
     * @return Quantity of bundles in one case
     */
    public int getBundleEnteringQty()
    {
        return _bundleEnteringQty;
    }

    /**
     * Sets Quantity of bundles in one case
     * @param bundleEnteringQty Quantity of bundles in one case
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        _bundleEnteringQty = bundleEnteringQty;
    }

    /**
     * Gets Planned Quantity
     * @return Planned Quantity
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * Sets Planned Quantity
     * @param planQty Planned Quantity
     */
    public void setPlanQty(int planQty)
    {
        _planQty = planQty;
    }

    /**
     * Gets Result Quantity
     * @return Result Quantity
     */
    public int getResultQty()
    {
        return _resultQty;
    }

    /**
     * Sets Result Quantity
     * @param resultQty Result Quantity
     */
    public void setResultQty(int resultQty)
    {
        _resultQty = resultQty;
    }

    /**
     * Sets Planned Case Quantity
     * @return Planned Case Quantity
     */
    public int getPlanCaseQty()
    {
        return _planCaseQty;
    }

    /**
     * Sets Planned Case Quantity
     * @param planCaseQty Planned Case Quantity
     */
    public void setPlanCaseQty(int planCaseQty)
    {
        _planCaseQty = planCaseQty;
    }

    /**
     * Gets Planned Piece Quantity
     * @return Planned Piece Quantity
     */
    public int getPlanPieceQty()
    {
        return _planPieceQty;
    }

    /**
     * Sets Planned Piece Quantity
     * @param planPieceQty Planned Piece Quantity
     */
    public void setPlanPieceQty(int planPieceQty)
    {
        _planPieceQty = planPieceQty;
    }

    /**
     * Gets Result Case Quantity
     * @return Result Case Quantity
     */
    public int getResultCaseQty()
    {
        return _resultCaseQty;
    }

    /**
     * Sets Result Case Quantity
     * @param resultCaseQty Result Case Quantity
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }

    /**
     * Gets Result Piece Quantity
     * @return Result Piece Quantity
     */
    public int getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * Sets Result Piece Quantity
     * @param resultPieceQty Result Piece Quantity
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
    }

    /**
     * Gets Shortage Quantity
     * @return Shortage Quantity
     */
    public int getShortageQty()
    {
        return _shortageQty;
    }

    /**
     * Sets Shortage Quantity
     * @param shortageQty Shortage Quantity
     */
    public void setShortageQty(int shortageQty)
    {
        _shortageQty = shortageQty;
    }

    /**
     * Gets Shortage Case Quantity
     * @return Shortage Case Quantity
     */
    public int getShortageCaseQty()
    {
        return _shortageCaseQty;
    }

    /**
     * Sets Shortage Case Quantity
     * @param shortageCaseQty Shortage Case Quantity
     */
    public void setShortageCaseQty(int shortageCaseQty)
    {
        _shortageCaseQty = shortageCaseQty;
    }

    /**
     * Gets Shortage Piece Quantity
     * @return Shortage Piece Quantity
     */
    public int getShortagePieceQty()
    {
        return _shortagePieceQty;
    }

    /**
     * Sets Shortage Piece Quantity
     * @param shortagePieceQty Shortage Piece Quantity
     */
    public void setShortagePieceQty(int shortagePieceQty)
    {
        _shortagePieceQty = shortagePieceQty;
    }

    /**
     * Gets JAN Code
     * @return JAN Code
     */
    public String getJanCode()
    {
        return _janCode;
    }

    /**
     * Sets JAN Code
     * @param janCode JAN Code
     */
    public void setJanCode(String janCode)
    {
        _janCode = janCode;
    }

    /**
     * Gets Case ITF
     * @return Case ITF
     */
    public String getItf()
    {
        return _itf;
    }

    /**
     * Sets Case ITF
     * @param itf Case ITF
     */
    public void setItf(String itf)
    {
        _itf = itf;
    }

    /**
     * Gets Bundle ITF
     * @return Bundle ITF
     */
    public String getBundleItf()
    {
        return _bundleItf;
    }

    /**
     * Sets Bundle ITF
     * @param bundleItf Bundle ITF
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
    }

    /**
     * Gets Receiving Date
     * @return Receiving Date
     */
    public String getReceivingDay()
    {
        return _receivingDay;
    }

    /**
     * Sets Receiving Date
     * @param receivingDay Receiving Date
     */
    public void setReceivingDay(String receivingDay)
    {
        _receivingDay = receivingDay;
    }

    /**
     * Gets Progress Rate
     * @return Progress Rate
     */
    public double getProgressRate()
    {
        return _progressRate;
    }

    /**
     * Sets Progress Rate
     * @param progressRate Progress Rate
     */
    public void setProgressRate(double progressRate)
    {
        _progressRate = progressRate;
    }

    /**
     * Gets Work Date
     * @return Work Date
     */
    public String getWorkDay()
    {
        return _workDay;
    }

    /**
     * Sets Work Date
     * @param workDay Work Date
     */
    public void setWorkDay(String workDay)
    {
        _workDay = workDay;
    }

    /**
     * Gets Work Time
     * @return Work Time
     */
    public String getWorkTime()
    {
        return _workTime;
    }

    /**
     * Sets Work Time
     * @param workTime Work Time
     */
    public void setWorkTime(String workTime)
    {
        _workTime = workTime;
    }

    /**
     * Gets Result Report Type
     * @return Result Report Type
     */
    public String getReportFlag()
    {
        return _reportFlag;
    }

    /**
     * Sets Result Report Type
     * @param reportFlag Result Report Type
     */
    public void setReportFlag(String reportFlag)
    {
        _reportFlag = reportFlag;
    }

    /**
     * Gets Location Indicate Type
     * @return Location Indicate Type
     */
    public String getLocationStyle()
    {
        return _locationStyle;
    }

    /**
     * Sets Location Indicate Type
     * @param locationStyle Location Indicate Type
     */
    public void setLocationStyle(String locationStyle)
    {
        _locationStyle = locationStyle;
    }

    /**
     * Gets Button Control Flag
     * @return Button Control Flag
     */
    public String getButtonControlFlag()
    {
        return _buttonControlFlag;
    }

    /**
     * Sets Button Control Flag
     * @param buttonControlFlag Button Control Flag
     */
    public void setButtonControlFlag(String buttonControlFlag)
    {
        _buttonControlFlag = buttonControlFlag;
    }

    /**
     * Gets Stock Control Introduce Falg
     * @return Stock Control Introduce Falg
     */
    public boolean isStockFlag()
    {
        return _stockFlag;
    }

    /**
     * Sets Stock Control Introduce Falg
     * @param stockFlag Stock Control Introduce Falg
     */
    public void setStockFlag(boolean stockFlag)
    {
        _stockFlag = stockFlag;
    }

    /**
     * Gets Master Control Introduce Falg
     * @return Master Control Introduce Falg
     */
    public boolean isMasterFlag()
    {
        return _masterFlag;
    }

    /**
     * Sets Master Control Introduce Falg
     * @param masterFlag Master Control Introduce Falg
     */
    public void setMasterFlag(boolean masterFlag)
    {
        _masterFlag = masterFlag;
    }

    /**
     * Gets Collective Conditions
     * @return Collective Conditions
     */
    public String getCollectCondition()
    {
        return _collectCondition;
    }

    /**
     * Sets Collective Conditions
     * @param collectCondition Collective Conditions
     */
    public void setCollectCondition(String collectCondition)
    {
        _collectCondition = collectCondition;
    }

    /**
     * Gets Quantity-of-pieces-in-one-case Update Flag
     * @return Quantity-of-pieces-in-one-case Update Flag
     */
    public boolean isChgEnteringQty()
    {
        return _chgEnteringQty;
    }

    /**
     * Sets Quantity-of-pieces-in-one-case Update Flag
     * @param chgEnteringQty Quantity-of-pieces-in-one-case Update Flag
     */
    public void setChgEnteringQty(boolean chgEnteringQty)
    {
        _chgEnteringQty = chgEnteringQty;
    }

    /**
     * Gets Register Date
     * @return Register Date
     */
    public java.util.Date getRegistDate()
    {
        return _registDate;
    }

    /**
     * Sets Register Date
     * @param registDate Register Date
     */
    public void setRegistDate(java.util.Date registDate)
    {
        _registDate = registDate;
    }

    /**
     * Gets Last Update Date
     * @return Last Update Date
     */
    public java.util.Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * Sets Last Update Date
     * @param lastUpdateDate Last Update Date
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * Get Area No
     * 
     * @return AreaNo
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * Set Area No
     * 
     * @param no
     */
    public void setAreaNo(String no)
    {
        _areaNo = no;
    }
    
    /**
     * Get Area Name
     * 
     * @return areaName
     */
    public String getAreaName()
    {
        return _areaName;
    }

    /**
     * Set Area Name
     * 
     * @param name
     */
    public void setAreaName(String name)
    {
        _areaName = name;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
