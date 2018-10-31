package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;

/**
 * <CODE>ReceivingInParameter</CODE>Class used for passing parameters within the screen scheduler of the receiving package.<BR>
 * This class is used for preserving an item within every screen of the receiving package, although the contencts vary by screen.<BR>
 * <BR>
 * <DIR>
 * <CODE>ReceivingInParameter</CODE>for preserving the class<BR>
 * <BR>
 *     予定一意キー plan unique key<BR>
 *     取込単位キー Load Unit Key<BR>
 *     設定単位キー Settign Unit Key<BR>
 *     ホスト取消区分 host cancellation division<BR>
 *     作業No. work no.<BR>
 *     集約作業No. collected work no.<BR>
 *     状態フラグ status flag<BR>
 *     完了フラグ completion flag<BR>
 *     
 *     入庫予定日 receiving plan day<BR>
 *     荷主コード consignor code<BR>
 *     荷主名称 consignor name<BR>
 *     supplier code<BR>
 *     supplier name<BR>
 *     Item Code<BR>
 *     Item Code(to)<BR>
 *     Item Name<BR>
 *     Slip No.<BR>
 *     Row No.<BR>
 *     Work Branch<BR>
 *     Receving Area No.<BR>
 *     Receving Area Location<BR>
 *     Lot No.<BR>
 *     Plan Lot No.<BR>
 *     Quantity of pieces in one case<BR>
 *     Quantity of planned cases<BR>
 *     Quantity of planned pieces<BR>
 *     JAN Code<BR>
 *     Case ITF<BR>
 *     Planned quantity<BR>
 *     result quantity<BR>
 *     Receiving date<BR>
 *     Receiving date(to)<BR>
 *     Work Time (Seconds)<BR>
 *     Number of mistakes<BR>
 *     Compulsive Receiving Type<BR>
 *     ListCell Row No.<BR>
 *     Slip Issue Flag<BR>
 *     Initial Input flag for quantity of receiving<BR>
 *     Designate Range flag<BR>
 *     Start/End Flag<BR>
 *     Process  Flag<BR>
 *     Collective Conditions<BR>
 *     Search Table<BR>
 *     Last Update Date<BR>
 *     Hardware Type<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 4971 $, $Date: 2009-09-03 09:54:36 +0900 (木, 03 9 2009) $
 * @author  $Author: kishimoto $
 */
public class ReceivingInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * Plan Unit Key
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
     * glm
     */
    private String _TCDCFlag;

    /**
     * Host Cancellation Type
     */
    private String _cancelFlag;

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
     * Completion Flag
     */
    private String _completionFlag;

    /**
     * Planned Receiving Date
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
     * supplier code
     */
    private String _supplierCode;

    /**
     * supplier name
     */
    private String _supplierName;

    /**
     * Item Code
     */
    private String _itemCode;

    /**
     * Item Code(to)
     */
    private String _toItemCode;

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
     * Row No.（String）
     */
    private String _strLineNo;

    /**
     * Receiving Receiving Area No
     */
    private String _receivingAreaNo;

    /**
     * Receiving Receiving Location No
     * <BR>Should always be WmsParam.DEFAULT_LOCATION_NO
     */
    private String _receivingLocation;

    /**
     * New Receiving Receiving Area No
     */
    private String _newReceivingAreaNo;

    /**
     * Lot No.
     */
    private String _lotNo;

    /**
     * Plan Lot No.
     */
    private String _planlotNo;

    /**
     * Quantity of pieces in one case
     */
    private int _enteringQty;

    /**
     * Quantity of planned cases
     */
    private int _planCaseQty;

    /**
     * Quantity of planned pieces
     */
    private int _planPieceQty;

    /**
     * Quantity of received cases
     */
    private int _resultCaseQty;

    /**
     * Quantity of received pieces
     */
    private int _resultPieceQty;

    /**
     * Quantity of old received cases
     */
    private int _oldResultCaseQty;

    /**
     * Quantity of old received pieces
     */
    private int _oldResultPieceQty;

    /**
     * JAN Code
     */
    private String _janCode;

    /**
     * Case ITF
     */
    private String _itf;

    /**
     * Planned quantity
     */
    private int _planQty;

    /**
     * result quantity
     */
    private int _resultQty;

    /**
     * Receiving date
     */
    private String _receivingDay;

    /**
     * Receiving date(to)
     */
    private String _toReceivingDay;

    /**
     * Work Time (Seconds)
     */
    private int _workSeconds;

    /**
     * Number of mistakes
     */
    private int _missCnt;

    /**
     * Compulsive Receiving Type
     */
    private boolean _forceReceivingFlag;

    /**
     * ListCell Row No.
     */
    private int _rowNo;

    /**
     * Slip Issue Flag
     */
    private boolean _printFlag;

    /**
     * Initial Input flag for quantity of receiving
     */
    private boolean _receivingQtyInputFlag;

    /**
     * Designate Range flag
     */
    private boolean _rangeFlag;

    /**
     * Start/End Flag
     */
    private String _fromtoFrag;

    /**
     * Start/End Flag(Receiving date)
     */
    private String _fromtoDateFrag;

    /**
     * Process  Flag
     */
    private String _processFlag;

    /**
     * Collective Conditions
     */
    private String _collectCondition;

    /**
     * Search Table
     */
    private String _searchTable;

    /**
     * Last Update Date
     */
    private java.util.Date _lastUpdateDate;

    /**
     * Shortage Flag
     */
    private boolean _shortageFlag;

    /**
     * Planned Receiving Date（For Delete-All Slip）
     *
     */
    private String[] _receivingPlanDays;

    /**
     * Load Unit Key（For Delete-All Slip）
     */
    private String[] _loadUnitKeys;

    /**
     * Last Update Date（For Delete-All Slip）
     */
    private java.util.Date[] _lastUpdateDates;
    
    /**
     * Itf To Jan
     */
    private boolean _itfToJan;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public ReceivingInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }
    

    // Public methods ------------------------------------------------
    /**
     * Returns a version of this class
     * @return Version and Date
     */
    public static String getVersion()
    {
        return ("$Revision: 4971 $,$Date: 2009-09-03 09:54:36 +0900 (木, 03 9 2009) $");
    }

    /**
     * Compares the given keys in ReceivingWorkInfo and ones in this class.
     * If the Keys are different, then replaces them.<BR>
     *
     * @param newWorkInfo ReceivingWorkInfo class
     * @return True if all keys are correct. Otherwise false.
     * <ol>
     * <li>Area
     * <li>Location
     * <li>Lot No.
     * <li>Supplier
     * </ol>
     */
    public boolean setKeys(ReceivingWorkInfo newWorkInfo)
    {
        boolean isEqual = true;
        // LOT
        String wkstr = newWorkInfo.getPlanLotNo();
        if (!equals(getLotNo(), wkstr))
        {
            setLotNo(wkstr);
            isEqual &= false;
        }

        // Supplier
        wkstr = newWorkInfo.getSupplierCode();
        if (!equals(getSupplierCode(), wkstr))
        {
            setSupplierCode(wkstr);
            isEqual &= false;
        }

        return isEqual;
    }

    /**
     * Gets Plan Unit Key
     * @return Plan Unit Key
     */
    public String getPlanUKey()
    {
        return _planUKey;
    }

    /**
     * Sets Plan Unit Key
     * @param planUKey Plan Unit Key
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
     * Gets Seeting Unit Key
     * @return Seeting Unit Key
     */
    public String getSettingUnitKey()
    {
        return _settingUnitKey;
    }

    /**
     * Sets Seeting Unit Key
     * @param settingUnitKey Seeting Unit Key
     */
    public void setSettingUnitKey(String settingUnitKey)
    {
        _settingUnitKey = settingUnitKey;
    }

    /**
     * glm
     * @return TCDCFlag
     */
    public String getTCDCFlag()
    {
        return _TCDCFlag;
    }

    /**
     * glm
     * @param TCDCFlag
     */
    public void setTCDCFlag(String TCDCFlag)
    {
        _TCDCFlag = TCDCFlag;
    }

    /**
     * Gets Host Cancelation Type
     * @return Host Cancelation Type
     */
    public String getCancelFlag()
    {
        return _cancelFlag;
    }

    /**
     * Sets Host Cancelation Type
     * @param cancelFlag Host Cancelation Type
     */
    public void setCancelFlag(String cancelFlag)
    {
        _cancelFlag = cancelFlag;
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
     * gets Completion Flag
     * @return Completion Flag
     */
    public String getCompletionFlag()
    {
        return _completionFlag;
    }

    /**
     * Set Completion Flag
     * @param completionFlag Completion Flag
     */
    public void setCompletionFlag(String completionFlag)
    {
        _completionFlag = completionFlag;
    }

    /**
     * Gets Planned Receiving Date
     * @return Planned Receiving Date
     */
    public String getReceivingPlanDay()
    {
        return _receivingPlanDay;
    }

    /**
     * Sets Planned Receiving Date
     * @param receivingPlanDay Planned Receiving Date
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
     * get Supplier Code
     * @return Supplier Code
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
     * Gets Item Code(to)
     * @return Item Code(to)
     */
    public String getToItemCode()
    {
        return _toItemCode;
    }

    /**
     * Sets Item Code(to)
     * @param toItemCode Item Code(to)
     */
    public void setToItemCode(String toItemCode)
    {
        _toItemCode = toItemCode;
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
     * Gets TicketLineNo.
     * @return TicketLineNo.
     */
    public int getTicketLineNo()
    {
        return _ticketLineNo;
    }

    /**
     * Sets TicketLineNo.
     * @param ticketLineNo Row No.
     */
    public void setTicketLineNo(int ticketLineNo)
    {
        _ticketLineNo = ticketLineNo;
    }

    /**
     * Gets Row No.(String)
     * @return Row No.
     */
    public String getStrLineNo()
    {
        return _strLineNo;
    }

    /**
     * Sets Row No.(String)
     * @param strLineNo Row No.
     */
    public void setStrLineNo(String strLineNo)
    {
        _strLineNo = strLineNo;
    }

    /**
     * Get the receiving area
     * @return ReceivingAreaNo
     */
    public String getReceivingAreaNo()
    {
        return _receivingAreaNo;
    }

    /**
     * Set the receiving area
     * @param receivingAreaNo receivingAreaNo.
     */
    public void setReceivingAreaNo(String receivingAreaNo)
    {
        _receivingAreaNo = receivingAreaNo;
    }

    /**
     * Get the receiving location (this should always be 99999999)
     * @return Receving Area Location
     */
    public String getReceivingLocation()
    {
        _receivingLocation = WmsParam.DEFAULT_LOCATION_NO;
        return _receivingLocation;
    }

    /**
     * Set the receiving location (set to 99999999 regardless)
     * @param receivingLocation
     */
    public void setReceivingLocation(String receivingLocation)
    {
        _receivingLocation = WmsParam.DEFAULT_LOCATION_NO;
    }

    /**
     * Gets Lot No.
     * @return Lot No.
     */
    public String getLotNo()
    {
        return _lotNo;
    }

    /**
     * Sets Lot No.
     * @param lotNo Lot No.
     */
    public void setLotNo(String lotNo)
    {
        _lotNo = lotNo;
    }

    /**
     * Gets Plan_Lot No.
     * @return Plan_Lot No.
     */
    public String getPlanLotNo()
    {
        return _planlotNo;
    }

    /**
     * Sets Lot No.
     * @param planlotNo Plan Lot No.
     */
    public void setPlanLotNo(String planlotNo)
    {
        _planlotNo = planlotNo;
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
     * Gets Quantity of planned cases
     * @return Quantity of planned cases
     */
    public int getPlanCaseQty()
    {
        return _planCaseQty;
    }

    /**
     * Sets Quantity of planned cases
     * @param planCaseQty Quantity of planned cases
     */
    public void setPlanCaseQty(int planCaseQty)
    {
        _planCaseQty = planCaseQty;
    }

    /**
     * Gets Quantity of planned pieces
     * @return Quantity of planned pieces
     */
    public int getPlanPieceQty()
    {
        return _planPieceQty;
    }

    /**
     * Sets Quantity of planned pieces
     * @param planPieceQty Quantity of planned pieces
     */
    public void setPlanPieceQty(int planPieceQty)
    {
        _planPieceQty = planPieceQty;
    }

    /**
     * Gets Quantity of received cases
     * @return Quantity of received cases
     */
    public int getResultCaseQty()
    {
        return _resultCaseQty;
    }

    /**
     * Sets Quantity of received cases
     * @param resultCaseQty Quantity of received cases
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }


    /**
     * Gets Quantity of received pieces
     * @return Quantity of received pieces
     */
    public int getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * Sets Quantity of received pieces
     * @param resultPieceQty Quantity of received pieces
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
    }


    /**
     * Gets Quantity of old received cases
     * @return Quantity of old received cases
     */
    public int getOldResultCaseQty()
    {
        return _oldResultCaseQty;
    }

    /**
     * Sets Quantity of old received cases
     * @param oldResultCaseQty Quantity of old received cases
     */
    public void setOldResultCaseQty(int oldResultCaseQty)
    {
        _oldResultCaseQty = oldResultCaseQty;
    }


    /**
     * Gets Quantity of old received pieces
     * @return Quantity of old received pieces
     */
    public int getOldResultPieceQty()
    {
        return _oldResultPieceQty;
    }

    /**
     * Sets Quantity of old received pieces
     * @param oldResultPieceQty Quantity of old received pieces
     */
    public void setOldResultPieceQty(int oldResultPieceQty)
    {
        _oldResultPieceQty = oldResultPieceQty;
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
     * Gets Planned quantity
     * @return Planned quantity
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * Sets Planned quantity
     * @param planQty Planned quantity
     */
    public void setPlanQty(int planQty)
    {
        _planQty = planQty;
    }

    /**
     * Gets result quantity
     * @return result quantity
     */
    public int getResultQty()
    {
        return _resultQty;
    }

    /**
     * Sets result quantity
     * @param resultQty result quantity
     */
    public void setResultQty(int resultQty)
    {
        _resultQty = resultQty;
    }

    /**
     * Gets Receiving date
     * @return Receiving date
     */
    public String getReceivingDay()
    {
        return _receivingDay;
    }

    /**
     * Sets Receiving date
     * @param receivingDay Receiving date
     */
    public void setReceivingDay(String receivingDay)
    {
        _receivingDay = receivingDay;
    }

    /**
     * Gets Receiving date(to)
     * @return Receiving date(to)
     */
    public String getToReceivingDay()
    {
        return _toReceivingDay;
    }

    /**
     * Sets Receiving date(to)
     * @param toReceivingDay Receiving date(to)
     */
    public void setToReceivingDay(String toReceivingDay)
    {
        _toReceivingDay = toReceivingDay;
    }

    /**
     * Gets Work Time (Seconds)
     * @return 作業時間
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * Sets Work Time (Seconds)
     * @param secs 作業時間
     */
    public void setWorkSeconds(int secs)
    {
        _workSeconds = secs;
    }

    /**
     * Gets Number of mistakes
     * @return Number of mistakes
     */
    public int getMissCnt()
    {
        return _missCnt;
    }

    /**
     * Sets Number of mistakes
     * @param missCnt Number of mistakes
     */
    public void setMissCnt(int missCnt)
    {
        _missCnt = missCnt;
    }

    /**
     * Compulsive Receiving Type
     * @return Compulsive Receiving Type
     */
    public boolean isForceReceivingFlag()
    {
        return _forceReceivingFlag;
    }

    /**
     * Compulsive Receiving Type
     * @param forceReceivingFlag Compulsive Receiving Type
     */
    public void setForceReceivingFlag(boolean forceReceivingFlag)
    {
        _forceReceivingFlag = forceReceivingFlag;
    }

    /**
     * ListCell Row No.
     * @return ListCell Row No.
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * ListCell Row No.
     * @param rowNo ListCell Row No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
     * Slip Issue Flag
     * @return Slip Issue Flag
     */
    public boolean isPrintFlag()
    {
        return _printFlag;
    }

    /**
     * Slip Issue Flag
     * @param printFlag Slip Issue Flag
     */
    public void setPrintFlag(boolean printFlag)
    {
        _printFlag = printFlag;
    }

    /**
     * Initial Input flag for quantity of receiving
     * @return Initial Input flag for quantity of receiving
     */
    public boolean isReceivingQtyInputFlag()
    {
        return _receivingQtyInputFlag;
    }

    /**
     * Initial Input flag for quantity of receiving
     * @param receivingQtyInputFlag Initial Input flag for quantity of receiving
     */
    public void setReceivingQtyInputFlag(boolean receivingQtyInputFlag)
    {
        _receivingQtyInputFlag = receivingQtyInputFlag;
    }

    /**
     * Designate Range flag
     * @return Range Flag
     */
    public boolean getRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * Designate Range flag
     * @param rangeFlag Range Flag
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * Start/End Flag
     * @return Start/End Flag
     */
    public String getFromToFrag()
    {
        return _fromtoFrag;
    }

    /**
     * Start/End Flag
     * @param fromtoFlag Start/End Flag
     */
    public void setFromToFlag(String fromtoFlag)
    {
        _fromtoFrag = fromtoFlag;
    }

    /**
     * Start/End Flag(Receiving date)
     * @return Start/End Flag(Receiving date)
     */
    public String getFromToDateFrag()
    {
        return _fromtoDateFrag;
    }

    /**
     * Start/End Flag(Receiving date)
     * @param fromtoDateFlag Start/End Flag(Receiving date)
     */
    public void setFromToDateFlag(String fromtoDateFlag)
    {
        _fromtoDateFrag = fromtoDateFlag;
    }

    /**
     * Process  Flag
     * @return Process  Flag
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * Process  Flag
     * @param processFlag Process  Flag
     */
    public void setProcessFlag(String processFlag)
    {
        _processFlag = processFlag;
    }

    /**
     * Collective Conditions
     * @return Collective Conditions
     */
    public String getCollectCondition()
    {
        return _collectCondition;
    }

    /**
     * Collective Conditions
     * @param collectCondition Collective Conditions
     */
    public void setCollectCondition(String collectCondition)
    {
        _collectCondition = collectCondition;
    }

    /**
     * Search Table
     * @return Search Table
     */
    public String getSearchTable()
    {
        return _searchTable;
    }

    /**
     * Search Table
     * @param searchTable Search Table
     */
    public void setSearchTable(String searchTable)
    {
        _searchTable = searchTable;
    }

    /**
     * Last Update Date
     * @return Last Update Date
     */
    public java.util.Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * Last Update Date
     * @param lastUpdateDate Last Update Date
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * Shortage Flag
     * @return Shortage Flag
     */
    public boolean isShortageFlag()
    {
        return _shortageFlag;
    }

    /**
     * Shortage Flag
     * @param shortageFlag Shortage Flag
     */
    public void setShortageFlag(boolean shortageFlag)
    {
        _shortageFlag = shortageFlag;
    }

    /**
     * Gets Planned Receiving Date
     * @return Planned Receiving Date
     */
    public String[] getPlanDays()
    {
        return _receivingPlanDays;
    }

    /**
     * Sets plandate to Planned Receiving Date
     * @param plandate String[] Planned Receiving Date to set
     */
    public void setPlanDays(String[] plandate)
    {
        _receivingPlanDays = plandate;
    }


    /**
     * Load Unit Key
     * @return Load Unit Key
     */
    public String[] getLoadUnitKeys()
    {
        return _loadUnitKeys;
    }

    /**
     * Load Unit Key
     * @param loadUnitKey Load Unit Key
     */
    public void setLoadUnitKeys(String[] loadUnitKey)
    {
        _loadUnitKeys = loadUnitKey;
    }

    /**
     * Last Update Date
     * @return Last Update Date
     */
    public java.util.Date[] getLastUpdateDates()
    {
        return _lastUpdateDates;
    }

    /**
     * Last Update Date
     * @param lastUpdateDate Last Update Date
     */
    public void setLastUpdateDates(java.util.Date[] lastUpdateDate)
    {
        _lastUpdateDates = lastUpdateDate;
    }

    /**
     * NewReceivingAreaNo
     * @return newReceivingAreaNo
     */
    public String getNewReceivingAreaNo()
    {
        return _newReceivingAreaNo;
    }

    /**
     * NewReceivingAreaNo
     * @param isTempAreaNo isTempAreaNo
     */
    public void setNewReceivingAreaNo(String isTempAreaNo)
    {
        _newReceivingAreaNo = isTempAreaNo;
    }

    /**
     * ITF To JAN
     * @return ITF To JAN
     */
    public boolean getItfToJan()
    {
        return _itfToJan;
    }
    
    /**
     * ITF To JAN
     * @param itfToJan ITF To JAN
     */
    public void setItfToJan(boolean itfToJan)
    {
        _itfToJan = itfToJan;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**
     * Compares the given two objects.<BR>
     * If both of them are null, then considers they are same. 
     *
     * @param o1 Compare target1
     * @param o2 Compare target2
     * @return True if they are same. Otherwise fasle.
     */
    protected boolean equals(Object o1, Object o2)
    {
        if (o1 == null && o2 == null)
        {
            // Both of null:  same
            return true;
        }
        if (o1 == null)
        {
            // o1 is null: different
            return false;
        }
        if (o2 == null)
        {
            // o2 is null: different
            return false;
        }
        // compares both of them.
        return o1.equals(o2);
    }

    // Private methods -----------------------------------------------
}
//end of class
