// $Id: StoragePlanRegistSCH.java 8096 2015-02-27 02:33:04Z okamura $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.storage.schedule.StoragePlanRegistSCHParams.*;

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
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.storage.operator.StoragePlanOperator;

/**
 * 入庫予定データ登録のスケジュール処理を行います。
 *
 * @version $Revision: 8096 $, $Date: 2015-02-27 11:33:04 +0900 (金, 27 2 2015) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class StoragePlanRegistSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    // DAC added from here
    /**
     * Prefix for loading unit key : 取込単位キーのプリフィックス
     */
    private static final String PREFIX_LOADUNITKEY = "Web-St-";

    // DAC added to here


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    // DAC added from here
    /**
     * WarenaviSystemコントローラ
     */
    private WarenaviSystemController _WNSysCtrl;

    /**
     * 入庫予定オペレータ
     */
    private StoragePlanOperator _SPlanOpe;

    /**
     * 商品マスタハンドラ
     */
    private ItemHandler _ItemHndlr;

    /**
     * 商品マスタ検索キー
     */
    private ItemSearchKey _ItemSKey;

    /**
     * エリアマスタハンドラ
     */
    private AreaHandler _AreaHndr;

    /**
     * エリアマスタ検索キー
     */
    private AreaSearchKey _AreaSKey;

    // DAC added to here


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Constructor to create SCH object
     * @param conn Database Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     * @param ui DfkUserInfo
     * @throws CommonException
     */
    public StoragePlanRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        // DAC added from here
        _WNSysCtrl = new WarenaviSystemController(conn, this.getClass());
        _SPlanOpe = new StoragePlanOperator(conn, this.getClass());
        _ItemHndlr = new ItemHandler(conn);
        _ItemSKey = new ItemSearchKey();
        _AreaHndr = new AreaHandler(conn);
        _AreaSKey = new AreaSearchKey();
        // DAC added to here

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
        ///////////////////////////////////////////////////////////////////////////
        // DAC added this portion from here 
        ///////////////////////////////////////////////////////////////////////////
        // a flag intended to prevent confusion when this class initiates an update
        boolean isUpdateLoadDataFlag = false;
        // ListCell Row No.
        int rowNum = 0;
        // Casts startParams
        List<StorageInParameter> rpl = createStartSCHParams(ps);
        StorageInParameter[] inParams = rpl.toArray(new StorageInParameter[rpl.size()]);
        // 取込単位キー
        String loadUnitKey = PREFIX_LOADUNITKEY.concat(DbDateUtil.getSystemDateTime());
        ///////////////////////////////////////////////////////////////////////////
        // DAC added portion to here 
        ///////////////////////////////////////////////////////////////////////////


        // Checks before proceeding.
        // Checks Daily Cleanup.

        // Creates parameter to operator
        //xxxInParameter[] inParams = new xxxInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new StorageInParameter(this.getWmsUserInfo());
            inParams[i].setConsignorCode(ps[i].getString(StoragePlanRegistSCHParams.CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(StoragePlanRegistSCHParams.ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(StoragePlanRegistSCHParams.CASE_PACK));
        }

        try
        {

            ///////////////////////////////////////////////////////////////////////////
            // DAC modified this portion from here 
            ///////////////////////////////////////////////////////////////////////////                
            if (!canStart() || isLoadData())
            {
                return false;
            }
            // 搬送データクリアチェック
            if (isAllocationClear())
            {
                return false;
            }
            ///////////////////////////////////////////////////////////////////////////
            // DAC modified this portion to here 
            ///////////////////////////////////////////////////////////////////////////                


            ///////////////////////////////////////////////////////////////////////////
            // DAC added this portion from here 
            ///////////////////////////////////////////////////////////////////////////        
            if (!doLoadStart())
            {
                return false;
            }
            doCommit(this.getClass());
            isUpdateLoadDataFlag = true;

            // Check  each of category
            for (StorageInParameter param : rpl) //inParams --> rpl
            {

                // Check Area Master
                if (!existAreaNo(param))
                {
                    // 6023043 = No.{0} 指定された{1}は、他の端末で更新されたため登録できません。 
                    // {1}=エリアマスタデータ
                    setMessage(WmsMessageFormatter.format(6023043, param.getRowNo(), DisplayText.getText("LBL-W0348")));
                    setErrorRowIndex(param.getRowNo());
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
                Entity[] ent = _SPlanOpe.findPlan(param);
                if (ent != null && ent.length != 0)
                {
                    // 6023029 = "Cannot add No. {0}. The same data already exists."
                    setMessage(WmsMessageFormatter.format(6023029, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }

            // Creates receiving plan information
            for (rowNum = 0; rowNum < rpl.size(); rowNum++) // inParams.length; rowNum++)
            {
                StorageInParameter param = rpl.get(rowNum); // inParams[rowNum];
                param.setLoadUnitKey(loadUnitKey);

                _SPlanOpe.createPlan(SystemDefine.REGIST_KIND_TERMINAL_REGIST, param);
            }
            ///////////////////////////////////////////////////////////////////////////
            // DAC added portion to here 
            ///////////////////////////////////////////////////////////////////////////


            // (6001003)登録しました。
            setMessage("6001003");
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
        ///////////////////////////////////////////////////////////////////////////
        // DAC added this portion from here 
        ///////////////////////////////////////////////////////////////////////////        
        finally
        {
            // Sets loading flag OFF
            if (isUpdateLoadDataFlag)
            {
                doLoadEnd();
                doCommit(this.getClass());
            }
        }
        ///////////////////////////////////////////////////////////////////////////
        // DAC added this portion to here 
        ///////////////////////////////////////////////////////////////////////////        
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
    // DAC changed : ScheduleParams ---> StoragePlanRegistSCHParams ofr argument
    public boolean check(StoragePlanRegistSCHParams p, ScheduleParams... ps)
            throws CommonException
    {

        ///////////////////////////////////////////////////////////////////////////
        // DAC added this portion from here 
        ///////////////////////////////////////////////////////////////////////////        

        StorageInParameter inputparam = createCheckParams(p);

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


        // Storage Location Input value check : 入庫棚入力チェック
        if (StringUtil.isBlank(p.getString(STORAGE_AREA)) && !StringUtil.isBlank(p.getString(STORAGE_LOCATION)))
        {
            // 6023018 = 入庫エリアの指定がない場合、入庫棚の入力はできません。
            setMessage(WmsMessageFormatter.format(6023018));

            return false;
        }

        // The given storage Location exist check : 棚の存在チェック
        if (!existLocation(inputparam))
        {
            // 6023067=実在する棚No.を入力してください。
            setMessage("6023067");
            return false;
        }

        // Area Master exist check : 入庫エリアのマスタ存在チェック
        if (!existAreaNo(inputparam))
        {
            // 6023042 = 指定された{0}は、他の端末で更新されたため入力できません。 {0}=エリアマスタデータ
            setMessage(WmsMessageFormatter.format(6023042, DisplayText.getText("LBL-W0348")));

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
        if (!duplicateCheck(inputparam))
        {
            // (6023020)既に同一データが存在するため、入力できません。
            setMessage("6023020");

            // 異常の場合はfalseを返却
            return false;
        }

        setMessage("6001019");
        ///////////////////////////////////////////////////////////////////////////
        // DAC added this portion to here 
        ///////////////////////////////////////////////////////////////////////////        


        // Checks if it exceeds the maximum number of display.
        /* sample -----------------------------------------
         // Checks the number of display.
         if (ps != null && ps.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
         {
         // 6023019 = Cannot enter because the number exceeds {0}.
         setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));
         return false;
         }
         ---------------------------------------------------*/

        // Checks if the input items and customer were registered on the master,
        /* sample -----------------------------------------
         // Gets the existence of the master package from a system controler.
         WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());
         if (systemController.hasMasterPack())
         {
         // Checks if an item code exists.
         ItemHandler itemHandler = new ItemHandler(getConnection());
         ItemSearchKey itemKey = new ItemSearchKey();

         itemKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
         itemKey.setItemCode(p.getString(ITEM_CODE));

         if (itemHandler.count(itemKey) <= 0)
         {
         // 6023021=The item code is not registered on the master.
         setMessage("6023021");
         return false;
         }
         }
         ---------------------------------------------------*/

        // Checks the quantity.
        /* sample -----------------------------------------
         // If the case pack is 0 and the storage case quantity is 1 or larger,
         if (p.getInt(ENTERING_QTY) == 0 && p.getInt(RESULT_CASE_QTY) >= 1)
         {
         // 6023036=Item code does not exist in the item master.
         setMessage("6023036");
         return false;
         }

         // Cheks the entered quantity.
         if (p.getInt(RESULT_CASE_QTY) == 0 && p.getInt(RESULT_PIECE_QTY) == 0)
         {
         // 6023035=Enter more than 1 for Case Qty or Piece Qty.
         setMessage("6023035");
         return false;
         }

         // Checks overflow.
         if ((long)((long)p.getInt(ENTERING_QTY) * (long)p.getInt(RESULT_CASE_QTY) + (long)p.getInt(RESULT_PIECE_QTY)) > (long)WmsParam.MAX_xxx_QTY)
         {
         // 6023217=Enter the value smaller than the  maximum stock quantity {0} for the storage quantity.
         setMessage(WmsMessageFormatter.format(6023217, MAX_xxx_QTY_DISP));
         return false;
         }
         ---------------------------------------------------*/

        // Searches DB about data in the input fields and checks items to be checked.
        // Checks with the List Area.
        /* sample -----------------------------------------

         //  Checks duplication with the area.
         if (ps != null)
         {
         for (ScheduleParams schParam : ps)
         {
         if (p.getString(ITEM_CODE).equals(schParam.getString(ITEM_CODE))
         && p.getString(AREA_NO).equals(schParam.getString(AREA_NO))
         && p.getString(LOCATION_NO).equals(schParam.getString(LOCATION_NO))
         && p.getString(LOT_NO).equals(schParam.getString(LOT_NO)))
         {
         // 6023020 = It is not possible to input it because the same data already exists.
         setMessage("6023020");
         return false;
         }
         }
         }
         ---------------------------------------------------*/
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // DAC added from here
    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets data to display in a screen.<BR>
     * Called when a display button is pushed.<BR>
     * If necessary, please implement each inherit class.<BR>
     * 
     * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
     * @return An instance that inherits <CODE>Parameter</CODE>  with search results.
     * @throws CommonException Thrown when an exception happens
     */
    public List<Params> query(StoragePlanRegistSCHParams searchParam)
            throws CommonException
    {
        Params param = new Params();
        List<Params> outParam = new ArrayList<Params>();


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

        return outParam;
    }


    /**
     * マスタパッケージが導入フラグを取得します。<BR>
     * <BR>
     * @param searchParam 検索条件をもつ<CODE>RetrievalInParameter</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>RetrievalOutParameter</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Parameter initFind(Parameter searchParam)
            throws CommonException
    {
        // 出庫出力パラメータ
        StorageOutParameter outParam = new StorageOutParameter();
        // システムコントローラ
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // 取得データをセット
        // マスタパッケージ導入有無フラグ
        outParam.setMasterFlag(systemController.hasMasterPack());

        // 設定したパラメータを返却
        return outParam;
    }


    /**
     * check()にて使用するパラメータを生成します。<BR>
     * <BR>
     * @param p 出庫予定データ登録(詳細入力)画面情報
     * @return inputParam 生成したパラメータ
     */
    protected StorageInParameter createCheckParams(StoragePlanRegistSCHParams p)
    {
        // 出庫パラメータの生成
        StorageInParameter inputParam = new StorageInParameter(getWmsUserInfo());

        // 取得データのセット
        // 荷主コード
        inputParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出庫予定日
        inputParam.setStoragePlanDay(p.getString(STORAGE_PLAN_DATE));
        // 伝票No.
        inputParam.setTicketNo(p.getString(SLIP_NUMBER));
        // 行No.
        inputParam.setLineNo(p.getInt(LINE_NO));
        // 作業枝番
        inputParam.setBranchNo(p.getInt(BRANCH_NO));
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

        // 生成した出庫パラメータを返却
        return inputParam;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // DAC added to here
    ////////////////////////////////////////////////////////////////////////////////////////////


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
    protected List<StorageInParameter> createStartSCHParams(ScheduleParams... ps)
    {

        // 出庫パラメータの生成
        StorageInParameter rp = null;
        // 出庫パラメータ配列の生成
        List<StorageInParameter> rpl = new ArrayList<StorageInParameter>();

        // 取得件数分、繰り返す
        for (int i = 0; i < ps.length; i++)
        {
            // 出庫予定データ登録パラメータ型に変換
            StoragePlanRegistSCHParams pp = (StoragePlanRegistSCHParams)ps[i];
            // 出庫パラメータの初期化
            rp = new StorageInParameter(getWmsUserInfo());

            // 取得データのセット
            // リストセル行No.
            rp.setRowNo(i + 1);
            // 荷主コード
            rp.setConsignorCode(pp.getString(CONSIGNOR_CODE));
            // 出庫予定日
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            rp.setStoragePlanDay(df.format(pp.getDate(STORAGE_PLAN_DATE)));
            // 伝票No.
            rp.setTicketNo(pp.getString(SLIP_NUMBER));
            // 行No.
            rp.setLineNo(pp.getInt(LINE_NO));
            // 作業枝番
            rp.setBranchNo(pp.getInt(BRANCH_NO));
            // 商品コード
            rp.setItemCode(pp.getString(ITEM_CODE));
            // 商品名称
            rp.setItemName(pp.getString(ITEM_NAME));
            // ケース入数
            int enteringQty = pp.getInt(ENTERING_QTY);
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
            // Storage Area : 入庫エリアNo.
            rp.setStorageAreaNo(pp.getString(STORAGE_AREA));
            // Storage Location : 入庫棚
            rp.setStorageLocation(pp.getString(STORAGE_LOCATION));

            // 設定した値を配列に格納
            rpl.add(rp);
        }
        // 生成した配列を返却
        return rpl;


    }


    /**
     * 入庫エリアがマスタに存在するかをチェックします。<BR>
     * @param param 入庫エリアを含む入力パラメータ
     * @return エリアがマスタに存在する、または引数にエリアがセットされていない場合、trueを返します
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    protected boolean existAreaNo(Parameter param)
            throws CommonException
    {
        StorageInParameter sInParam = (StorageInParameter)param;

        if (!StringUtil.isBlank(sInParam.getStorageAreaNo()))
        {
            _AreaSKey.clear();
            _AreaSKey.setAreaNo(sInParam.getStorageAreaNo());
            _AreaSKey.setAreaNoCollect();
            Area[] ent = (Area[])_AreaHndr.find(_AreaSKey);

            if (ent == null || ent.length == 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 実在する棚が存在するかチェックします。
     * @param param エリア、棚Noを含むパラメータ
     * @return 実在しない場合false、それ以外の場合trueを返します。
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    // 
    protected boolean existLocation(StorageInParameter param)
            throws CommonException
    {
        // 全エリアの場合チェックを行わない
        if (StringUtil.isBlank(param.getStorageAreaNo()))
        {
            return true;
        }

        if (isASRSArea(param))
        {
            // AS/RSエリアの場合はDMShelfから検索する
            return existShelf(param);
        }
        else
        {
            // AS/RS以外の場合は棚マスタ情報から検索する
            return existLocate(param);
        }
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
            StorageInParameter sInParam = (StorageInParameter)param;

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


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 実在する棚がDMShelfに存在するかチェックします。
     * @param param エリア、棚Noを含むパラメータ
     * @return 正常：true 異常：false
     * @throws CommonException 全ての例外をスローします。
     */
    private boolean existShelf(StorageInParameter param)
            throws CommonException
    {
        AreaController areaCon = new AreaController(getConnection(), getClass());
        ShelfSearchKey skey = new ShelfSearchKey();
        ShelfHandler handler = new ShelfHandler(getConnection());

        skey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);
        skey.setKey(Area.AREA_NO, param.getStorageAreaNo());

        if (!StringUtil.isBlank(param.getStorageLocation()))
        {
            String location = areaCon.toAsrsLocation(param.getStorageAreaNo(), param.getStorageLocation());
            skey.setStationNo(location);

            Shelf[] locate = (Shelf[])handler.find(skey);

            // 該当データがなければ、falseを返す。
            if (locate == null || locate.length == 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 実在する棚がDMLocateに存在するかチェックします。
     * @param param エリア、棚Noを含むパラメータ
     * @return 正常：true 異常：false
     * @throws CommonException 全ての例外をスローします。
     */
    private boolean existLocate(StorageInParameter param)
            throws CommonException
    {
        AreaSearchKey akey = new AreaSearchKey();
        AreaHandler ahandler = new AreaHandler(getConnection());

        if (!StringUtil.isBlank(param.getStorageAreaNo()))
        {
            akey.setAreaNo(param.getStorageAreaNo());
            akey.setLocationTypeCollect();

            Area[] area = (Area[])ahandler.find(akey);

            // 棚がフリー管理方式であればtrueを返して終了する。
            if (SystemDefine.LOCATION_TYPE_FREE.equals(area[0].getLocationType()))
            {
                return true;
            }

            LocateSearchKey skey = new LocateSearchKey();
            LocateHandler handler = new LocateHandler(getConnection());

            skey.setAreaNo(param.getStorageAreaNo());

            if (StringUtil.isBlank(param.getStorageLocation()))
            {
                return true;
            }
            else
            {
                skey.setLocationNo(param.getStorageLocation());
            }

            Locate[] locate = (Locate[])handler.find(skey);

            // 該当データがなければ、falseを返す。
            if (locate == null || locate.length == 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 検索対象エリアがAS/RSエリアかどうかチェックします。
     * <BR>
     * @param param 入力パラメータ
     * @return AS/RSエリアの場合はtrue、そうでない場合はfalseを返します。
     * @throws CommonException 全ての例外をスローします。
     */
    private boolean isASRSArea(StorageInParameter param)
            throws CommonException
    {
        AreaHandler areaHndl = new AreaHandler(getConnection());
        AreaSearchKey key = new AreaSearchKey();

        // エリアNo.をキーにエリア情報を取得する
        key.setAreaNo(param.getStorageAreaNo());
        Area[] area = (Area[])areaHndl.find(key);

        // エリアタイプを判別
        if (SystemDefine.AREA_TYPE_ASRS.equals(area[0].getAreaType()))
        {
            // AS/RSエリアの場合はtrue
            return true;
        }
        else
        {
            // AS/RS以外の場合はfalse
            return false;
        }
    }


    /**
     * <CODE>checkParam</CODE>の内容を元に、出庫予定情報との重複チェックを行います。<BR>
     * <BR>
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true : 正常 false : 異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private boolean duplicateCheck(Parameter checkParam)
            throws CommonException
    {
        // 出庫パラメータ
        StorageInParameter param = (StorageInParameter)checkParam;
        // 出庫予定オペレーター
        StoragePlanOperator rtOp = new StoragePlanOperator(getConnection(), this.getClass());

        // 予定データが存在する場合
        Entity[] ent = rtOp.findPlan(checkParam);
        if (ent != null && rtOp.findPlan(checkParam).length == 0)
        {
            ent = null;
        }
        if (ent != null)
        {
            // (6023029)No.{0} すでに同一データが存在するため、登録できません。
            setMessage(WmsMessageFormatter.format(6023029, param.getRowNo()));

            // エラー行の設定
            setErrorRowIndex(param.getRowNo());

            // 異常の場合はfalseを返却
            return false;
        }
        // 正常の場合はtrueを返却
        return true;
    }


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
