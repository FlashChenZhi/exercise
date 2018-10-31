// $Id: ReceivingPlanRegistSCH.java 8096 2015-02-27 02:33:04Z okamura $
package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCHParams.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.receiving.operator.ReceivingPlanOperator;

/**
 * 入荷予定データ登録のスケジュール処理を行います。
 *
 * @version $Revision: 8096 $, $Date: 2015-02-27 11:33:04 +0900 (金, 27 2 2015) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class ReceivingPlanRegistSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /**
     * Prefix for loading unit key : 取込単位キーのプリフィックス
     */
    private static final String PREFIX_LOADUNITKEY = "Web-Recv-";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * WarenaviSystem Controller
     */
    private WarenaviSystemController _WNSysCtrl;

    /**
     * Receiving Plan Operator
     */
    private ReceivingPlanOperator _RecvPlanOper;

    /**
     * Item Master Handler
     */
    private ItemHandler _ItemHndlr;

    /**
     * Item Master Search Key
     */
    private ItemSearchKey _ItemSKey;

    /**
     * Supplier Handler
     */
    private SupplierHandler _SupplierHndlr;

    /**
     * Supplier Search Key
     */
    private SupplierSearchKey _SupplierKey;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Constructor to create SCH object
     * 
     * Sets a DB connetion.<BR> 
     * Committing/Rollbacking a transaction should be controlled by a caller of this class.<BR>
     * Initializes process result messages.<BR>
     * 
     * @param conn Database Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     * @param ui DfkUserInfo
     * @throws CommonException
     */
    public ReceivingPlanRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        _WNSysCtrl = new WarenaviSystemController(conn, this.getClass());
        _RecvPlanOper = new ReceivingPlanOperator(conn, this.getClass());
        _ItemHndlr = new ItemHandler(conn);
        _ItemSKey = new ItemSearchKey();
        _SupplierHndlr = new SupplierHandler(conn);
        _SupplierKey = new SupplierSearchKey();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Receieves the items that was input on the screen as a parameter and start schedule.<BR>
     *
     * @param startParams Setting items saved in <CODE>ScheduleParams</CODE> array.<BR>
     * @throws CommonException Reports all the exceptions.
     * @return Return true if the schedule ends normally and return false if it failed so.
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {

        // a flag intended to prevent confusion when this class initiates an update
        boolean isUpdateLoadDataFlag = false;
        // ListCell Row No.
        int rowNum = 0;
        // Casts startParams
        List<ReceivingInParameter> rpl = createStartSCHParams(ps);
        ReceivingInParameter[] inParams = rpl.toArray(new ReceivingInParameter[rpl.size()]);
        // 取込単位キー
        String loadUnitKey = PREFIX_LOADUNITKEY.concat(DbDateUtil.getSystemDateTime());

        // Checks before proceeding.
        // Checks Daily Cleanup.
        // Creates parameter to operator
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new ReceivingInParameter(this.getWmsUserInfo());

            inParams[i].setConsignorCode(ps[i].getString(ReceivingPlanRegistSCHParams.CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ReceivingPlanRegistSCHParams.ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(ReceivingPlanRegistSCHParams.CASE_PACK));
        }

        try
        {
            if (!canStart() || isLoadData() || !doLoadStart())
            {
                return false;
            }

            doCommit(this.getClass());
            isUpdateLoadDataFlag = true;

            // Check  each of category
            for (ReceivingInParameter param : rpl) // inParams --> rpl
            {
                // Check the Supplier Code
                if (!existSupplierCode(param))
                {
                    // 6023177=指定された{0}は、他の端末で更新されたため登録できません。
                    setMessage(WmsMessageFormatter.format(6023177, DisplayText.getText("LBL-T0213")));

                    return false;
                }

                // Item-Code-Master-Exist check
                if (!existItemCode(param))
                {
                    // 6023043 = "No {0} Specified value {1} cannot be set because it was updated in another terminal." (1)=Item Master data
                    setMessage(WmsMessageFormatter.format(6023043, param.getRowNo(), DisplayText.getText("LBL-W0317")));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }

                // Duplicate check
                Entity[] ent = _RecvPlanOper.findPlan(param);
                if (ent != null && ent.length != 0)
                {
                    // 6023029 = "Cannot add No. {0}. The same data already exists."
                    setMessage(WmsMessageFormatter.format(6023029, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());

                    return false;
                }
            }

            // Creates receiving plan information
            for (rowNum = 0; rowNum < rpl.size(); rowNum++) // inParams --> rpl
            {
                ReceivingInParameter param = rpl.get(rowNum); // inParams --. rpl
                param.setLoadUnitKey(loadUnitKey);

                _RecvPlanOper.createPlan(SystemDefine.REGIST_KIND_TERMINAL_REGIST, param);
            }

            // Start processing.
            setMessage(WmsMessageFormatter.format(6001003));
            return true;

        }
        catch (LockTimeOutException e)
        {
            doRollBack(getClass());
            // 6023114=
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (NotFoundException e)
        {
            doRollBack(getClass());
            // 6023115=Work process was cancelled because it was done on other terminal.
            setMessage(WmsMessageFormatter.format(6023015));
            return false;
        }
        catch (OperatorException e)
        {
            doRollBack(getClass());
            // Catches exceptions from Operator and displays the matched error message.

            // Updated on other terminal, Used in other temrinal, or Work Process Completed
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023015=No.{0} Wrok Process was canceled because it was done on other terminal.
                setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} Please enter the value smaller than the allocatable number in Picking Qty.
                setMessage(WmsMessageFormatter.format(6023189, inParams[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            // Throws exceptions other than described above.
            throw e;
        }
        catch (CommonException e)
        {
            doRollBack(getClass());
            throw e;
        }
        finally
        {
            // Sets loading flag OFF
            if (isUpdateLoadDataFlag)
            {
                doLoadEnd();
                doCommit(this.getClass());
            }
        }
    }

    /**
     * Receives the items that was entered on the screen and data in the List Area as parameter,
     * and check them.<BR>
     *
     * @param p A input parameter.
     * @param ps A parameter in the List Area.
     * @return Returns True if it is not input check, overflow, dupliation, or Item Master or Storage Location errors.
     * @throws CommonException Reports if an unexpected exception occurs when checking.
     */
    // DAC changed : ScheduleParams ---> ReceivingPlanRegistSCHParams ofr argument
    public boolean check(ReceivingPlanRegistSCHParams p, ScheduleParams... ps)
            throws CommonException
    {
        // Item-Code-on-irregular-location-input check
        if (WmsParam.IRREGULAR_ITEMCODE.equals(p.getString(ITEM_CODE)))
        {
            // 6023078 = "Cannot enter the item code for error location."
            setMessage(WmsMessageFormatter.format(6023078, WmsParam.IRREGULAR_ITEMCODE));

            return false;
        }

        if (WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(p.getString(ITEM_CODE)))
        {
            // 6023259 = {0}は簡易直行用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023259, WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE));

            return false;
        }

        // Quantity input check
        if (p.getInt(PLAN_CASE_QTY) <= 0 && p.getInt(PLAN_PIECE_QTY) <= 0)
        {
            // 6023016 = "Enter either Plan Case Qty or Plan Piece Qty."
            setMessage(WmsMessageFormatter.format(6023016));

            return false;
        }

        // Quantity-of-pieces-in-one-case input check
        if (p.getInt(PLAN_CASE_QTY) > 0 && p.getInt(CASE_PACK) <= 0)
        {
            // 6023017 = "When the Case Pack is 0, Plan Case Qty cannot be entered."
            setMessage(WmsMessageFormatter.format(6023017));

            return false;
        }

        // Overflow check
        long overflow = (long)p.getInt(CASE_PACK) * (long)p.getInt(PLAN_CASE_QTY) + (long)p.getInt(PLAN_PIECE_QTY);
        if (overflow > (long)WmsParam.MAX_TOTAL_QTY)
        {
            // 6023184 = "Enter the value lesser than {0} work upper qty for plan qty."
            setMessage(WmsMessageFormatter.format(6023184, MAX_TOTAL_QTY_DISP));

            return false;
        }

        // Display-quantity check 
        if (ps != null && ps.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // 6023019 = "More than {0} data exist. Data cannot be entered."
            setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));

            return false;
        }

        // Duplicate check in preset areas.
        if (ps != null)
        {
            for (ScheduleParams lParam : ps)
            {
                if (p.getInt(LINE_NO) == lParam.getInt(LINE_NO))
                {
                    // 6023020 = "It is not possible to input it because the same data already exists."
                    setMessage(WmsMessageFormatter.format(6023020));

                    return false;
                }
            }
        }

        // Duplication at DB
        Entity[] ent = _RecvPlanOper.findPlan(p);
        if (ent != null && ent.length != 0)
        {
            // 6023020 = "It is not possible to input it because the same data already exists."
            setMessage(WmsMessageFormatter.format(6023020));

            return false;
        }
        setMessage("6001019");

        return true;
    }


    /**
     * Gets data to display in a screen.<BR>
     * Called when a display button is pushed.<BR>
     * If necessary, please implement each inherit class.<BR>
     * 
     * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
     * @return An instance that inherits <CODE>Parameter</CODE>  with search results.
     * @throws CommonException Thrown when an exception happens
     */
    // Parameter searchParam ==> ReceivingPlanRegisterSCHParams
    public List<Params> query(ReceivingPlanRegistSCHParams searchParam)
            throws CommonException
    {
        Params param = new Params();
        List<Params> outParam = new ArrayList<Params>();

        if (!StringUtil.isBlank(searchParam.getString(SUPPLIER_CODE)))
        {

            // Validate the supplier
            SupplierSearchKey vpSupplierKey = new SupplierSearchKey();
            vpSupplierKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
            vpSupplierKey.setSupplierCode(searchParam.getString(SUPPLIER_CODE));
            Supplier[] vapSuppliers = (Supplier[])new SupplierHandler(getConnection()).find(vpSupplierKey);
            if (vapSuppliers.length == 0)
            {
                // 6023137 = Supplier code does not exist in the supplier master.
                setMessage("6023137");
                return new ArrayList<Params>();
            }

            param.set(SUPPLIER_NAME, vapSuppliers[0].getSupplierName());
        }

        if (!StringUtil.isBlank(searchParam.getString(ITEM_CODE)))
        {
            // Item Master search
            ItemHandler itemHandler = new ItemHandler(getConnection());
            ItemSearchKey itemKey = new ItemSearchKey();

            // Sets search conditions
            // Consignor Code
            itemKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
            // Item Code
            itemKey.setItemCode(searchParam.getString(ITEM_CODE));

            // Searches
            Item[] item = (Item[])itemHandler.find(itemKey);

            // Acquired-data check
            if (item == null || item.length <= 0)
            {
                // 6023021 = "Item code does not exist in the item master."
                setMessage("6023021");
                return new ArrayList<Params>();
            }

            // Returns index 0 element, since a corresponding datum should be one
            // Item Name
            param.set(ITEM_NAME, item[0].getItemName());
            // Quantity of pieces in one case
            param.set(CASE_PACK, item[0].getEnteringQty());
            // JAN Code
            param.set(JAN_CODE, item[0].getJan());
            // Case ITF
            param.set(CASE_ITF, item[0].getItf());
            outParam.add(param);
        }
        return outParam;
    }


    /**
     * 
     */
    public ReceivingOutParameter initFind(Parameter searchParam)
            throws CommonException
    {
        ReceivingOutParameter outParam = new ReceivingOutParameter();
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());
        // 取得データをセット
        // マスタパッケージ導入有無フラグ
        outParam.setMasterFlag(systemController.hasMasterPack());
        return outParam;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * startSCH()にて使用するパラメータ配列を生成します。<BR>
     * <BR>
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。
     * @return rpl 生成したパラメータ配列
     */
    protected List<ReceivingInParameter> createStartSCHParams(ScheduleParams... ps)
    {
        // 出庫パラメータの生成
        ReceivingInParameter rp = null;
        // 出庫パラメータ配列の生成
        List<ReceivingInParameter> rpl = new ArrayList<ReceivingInParameter>();

        // 取得件数分、繰り返す
        for (int i = 0; i < ps.length; i++)
        {
            // 出庫予定データ登録パラメータ型に変換
            ReceivingPlanRegistSCHParams pp = (ReceivingPlanRegistSCHParams)ps[i];
            // 出庫パラメータの初期化
            rp = new ReceivingInParameter(getWmsUserInfo());

            // 取得データのセット
            // リストセル行No.
            rp.setRowNo(i + 1);
            // 荷主コード
            rp.setConsignorCode(pp.getString(CONSIGNOR_CODE));
            // Receving plan date : 入庫予定日
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            rp.setReceivingPlanDay(df.format(pp.getDate(RECEIVING_PLAN_DATE)));
            // 伝票No.
            rp.setTicketNo(pp.getString(SLIP_NUMBER));
            // 行No.
            rp.setTicketLineNo(pp.getInt(LINE_NO));
            // 商品コード
            rp.setItemCode(pp.getString(ITEM_CODE));
            // 商品名称
            rp.setItemName(pp.getString(ITEM_NAME));
            // ケース入数
            int enteringQty = pp.getInt(CASE_PACK);
            rp.setEnteringQty(enteringQty);
            // 予定ケース入数
            int caseQty = pp.getInt(PLAN_CASE_QTY);
            // 予定ピース数
            int pieceQty = pp.getInt(PLAN_PIECE_QTY);
            // 予定数(ケース入数 * 予定ケース数 + 予定ピース数)
            rp.setPlanQty(enteringQty * caseQty + pieceQty);
            // ロットNo.
            rp.setLotNo(pp.getString(LOT_NO));
            // JANコード
            rp.setJanCode(pp.getString(JAN_CODE));
            // ケースITF
            rp.setItf(pp.getString(CASE_ITF));
            // Supplier Code
            rp.setSupplierCode(pp.getString(SUPPLIER_CODE));
            // Supplier Name
            rp.setSupplierName(pp.getString(SUPPLIER_NAME));

            // 設定した値を配列に格納
            rpl.add(rp);
        }
        // 生成した配列を返却
        return rpl;
    }


    /**
     * Checks if Item Code exists in Master or not.<BR>
     * @param param Input Parameter including Item Code.<BR>
     * @return  True if Item Code exists in Master and  Item Code is not set in param<BR> 
     *          and Master package is not introduced.  Otherwise false.<BR>
     * @throws CommonException Thrown when a DB error happens<BR>
     */
    protected boolean existItemCode(Parameter param)
            throws CommonException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            ReceivingInParameter sInParam = (ReceivingInParameter)param;

            if (!StringUtil.isBlank(sInParam.getItemCode()))
            {
                _ItemSKey.clear();
                _ItemSKey.setConsignorCode(sInParam.getConsignorCode());
                _ItemSKey.setItemCode(sInParam.getItemCode());
                _ItemSKey.setConsignorCodeCollect();
                _ItemSKey.setItemCodeCollect();
                Item[] ent = (Item[])_ItemHndlr.find(_ItemSKey);

                if (ent == null || ent.length == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if Supplier Code exists in Master or not.<BR>
     * @param param Input Parameter including Supplier Code.<BR>
     * @return  True if Supplier Code exists in Master and  Supplier Code is not set in param<BR> 
     *          and Master package is not introduced.  Otherwise false.<BR>
     * @throws CommonException Thrown when a DB error happens
     */
    public boolean existSupplierCode(ReceivingInParameter param)
            throws CommonException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            _SupplierKey.clear();
            _SupplierKey.setConsignorCode(param.getConsignorCode());
            _SupplierKey.setSupplierCode(param.getSupplierCode());
            _SupplierKey.setSupplierCodeCollect();
            Supplier[] ent = (Supplier[])_SupplierHndlr.find(_SupplierKey);

            if (ent == null || ent.length == 0)
            {
                return false;
            }
        }
        return true;
    }


    /**
     * check()にて使用するパラメータを生成します。<BR>
     * <BR>
     * @param p 出庫予定データ登録(詳細入力)画面情報
     * @return inputParam 生成したパラメータ
     */
    protected ReceivingInParameter createCheckParams(ReceivingPlanRegistSCHParams p)
    {
        ReceivingInParameter inputParam = new ReceivingInParameter(getWmsUserInfo());

        // 取得データのセット
        // 荷主コード
        inputParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出庫予定日
        inputParam.setReceivingPlanDay(p.getString(RECEIVING_PLAN_DATE));
        // 伝票No.
        inputParam.setTicketNo(p.getString(SLIP_NUMBER));
        // 行No.
        inputParam.setTicketLineNo(p.getInt(LINE_NO));
        // 商品コード
        inputParam.setItemCode(p.getString(ITEM_CODE));
        // 商品名称
        inputParam.setItemName(p.getString(ITEM_NAME));
        // ロットNo.
        inputParam.setLotNo(p.getString(LOT_NO));
        // ケース入数
        inputParam.setEnteringQty(p.getInt(CASE_PACK));
        // 予定ケース数
        inputParam.setPlanCaseQty(p.getInt(PLAN_CASE_QTY));
        // 予定ピース数
        inputParam.setPlanPieceQty(p.getInt(PLAN_PIECE_QTY));
        // JANコード
        inputParam.setJanCode(p.getString(JAN_CODE));
        // ケースITF
        inputParam.setItf(p.getString(CASE_ITF));
        // Supplier Code
        inputParam.setSupplierCode(p.getString(SUPPLIER_CODE));
        // Supplier Name
        inputParam.setSupplierName(p.getString(SUPPLIER_NAME));

        // 生成した出庫パラメータを返却
        return inputParam;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class
