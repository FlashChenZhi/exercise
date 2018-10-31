// $Id: XDPlanRegistSCH.java 8096 2015-02-27 02:33:04Z okamura $
package jp.co.daifuku.wms.crossdock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.crossdock.schedule.XDPlanRegistSCHParams.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.crossdock.operator.XDPlanOperator;

/**
 * TC予定データ登録のスケジュール処理を行います。
 *
 * @version $Revision: 8096 $, $Date: 2015-02-27 11:33:04 +0900 (金, 27 2 2015) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class XDPlanRegistSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * TC予定情報用取込単位キーのプリフィックス
     */
    protected static final String PREFIX_LOADUNITKEY = "Web-Sort-";

    /**
     * 出荷予定情報用取込単位キーのプリフィックス
     */
    protected static final String PREFIX_LOADSHIPKEY = "Web-Ship-";


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * WarenaviSystemコントローラ
     */
    protected WarenaviSystemController _WNSysCtrl;

    /**
     * クロスドック予定オペレータ
     */
    protected XDPlanOperator _XDPlanOpe;

    /**
     * Item search Key
     */
    protected ItemSearchKey _ItemKey;

    /**
     * Item Hander
     */
    protected ItemHandler _ItemHander;

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
    public XDPlanRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        // DBハンドラ、コントローラ生成
        _WNSysCtrl = new WarenaviSystemController(conn, this.getClass());
        _XDPlanOpe = new XDPlanOperator(conn, this.getClass());
        _ItemKey = new ItemSearchKey();
        _ItemHander = new ItemHandler(conn);
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
        boolean isUpdate = false;
        int rowNum = 0;
        XDInParameter[] listParam = null;
        List<XDInParameter> rpl = createStartSCHParams(ps);
        listParam = rpl.toArray(new XDInParameter[rpl.size()]);
        String loadUnitKey = PREFIX_LOADUNITKEY.concat(DbDateUtil.getSystemDateTime());
        String shipLoadUnitKey = PREFIX_LOADSHIPKEY.concat(DbDateUtil.getSystemDateTime());

        if (!canStart())
        {
            return false;
        }

        // 取込み中フラグをONにする
        _WNSysCtrl.updateDataLoading(true);
        isUpdate = true;

        try
        {
            // マスタ管理フラグがtrueの場合
            if (_WNSysCtrl.hasMasterPack())
            {
                XDInParameter param = listParam[0];

                // 仕入先コード、商品コードの存在チェックを行う

                // 仕入先コードのマスタ存在チェック
                if (!existSupplierCode(param))
                {
                    // 6023042=指定された{0}は、他の端末で更新されたため入力できません。 {0}=仕入先マスタ
                    setMessage(WmsMessageFormatter.format(6023042, DisplayText.getText("LBL-W0315")));

                    return false;
                }

                // 商品コードのマスタ存在チェック
                if (!existItemCode(param.getItemCode()))
                {
                    // 6023042=指定された{0}は、他の端末で更新されたため入力できません。 {0}=商品マスタ
                    setMessage(WmsMessageFormatter.format(6023042, DisplayText.getText("LBL-W0317")));

                    return false;
                }
            }
            for (XDInParameter param : listParam)
            {
                // マスタ管理フラグがtrueの場合、
                // 出荷先コードのマスタ存在チェック
                if (_WNSysCtrl.hasMasterPack() && !existCustomerCode(param))
                {
                    // 6023043 = No.{0} 指定された{1}は、他の端末で更新されたため登録できません。 {1}=出荷先マスタデータ
                    setMessage(WmsMessageFormatter.format(6023043, param.getRowNo(), DisplayText.getText("LBL-W0316")));
                    //setNgCellRow(param.getRowNo());
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }

                // 重複チェック
                CrossDockPlan[] plans = _XDPlanOpe.findPlan(param);
                // 取得配列が1件以上ある場合
                if (!ArrayUtil.isEmpty(plans))
                {
                    // 6023029 = No.{0} すでに同一データが存在するため、登録できません。
                    setMessage(WmsMessageFormatter.format(6023029, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }

                // 同一予定日、出荷先コード、出荷伝票No.、出荷伝票行No.で異なる商品、ロットNoのチェック
                if (!existPlan(param))
                {
                    // 6023255=すでに同一の出荷伝票No.、行No.で異なる商品コード/ロットNo.が登録されているため、登録できません。
                    setMessage(WmsMessageFormatter.format(6023255));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }

            try
            {
                for (rowNum = 0; rowNum < listParam.length; rowNum++)
                {
                    XDInParameter param = listParam[rowNum];
                    param.setLoadUnitKey(loadUnitKey);
                    param.setShipLUnitKey(shipLoadUnitKey);
                    _XDPlanOpe.createPlan(loadUnitKey, shipLoadUnitKey, SystemDefine.REGIST_KIND_TERMINAL_REGIST, param);
                }

                // Start processing.  6021021 --> 6001003
                setMessage(WmsMessageFormatter.format(6001003));
                return true;
            }
            catch (OperatorException e)
            {
                // Catches exceptions from Operator and displays the matched error message.

                // Updated on other terminal, Used in other temrinal, or Work Process Completed
                if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                        || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                        || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
                {
                    // 6023015=No.{0} Wrok Process was canceled because it was done on other terminal.
                    setMessage(WmsMessageFormatter.format(6023015, listParam[e.getErrorLineNo() - 1].getRowNo()));
                    setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                    return false;
                }
                else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
                {
                    // 6023189=No.{0} Please enter the value smaller than the allocatable number in Picking Qty.
                    setMessage(WmsMessageFormatter.format(6023189, listParam[e.getErrorLineNo() - 1].getRowNo()));
                    setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                    return false;
                }
                // Throws exceptions other than described above.
                throw e;
            }
        }
        catch (CommonException e)
        {
            doRollBack(getClass());
            throw e;
        }
        finally
        {
            if (isUpdate)
            {
                // 取込み中フラグをOFFにする
                _WNSysCtrl.updateDataLoading(false);
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
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
        // 入力パラメータクラスのキャスト
        XDInParameter cParam = createCheckParams((XDPlanRegistSCHParams)p);

        // ためうちパラメータがWmsParamの最大件数を超えている場合
        if (ps.length > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // 6023019 = 件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, WmsParam.MAX_NUMBER_OF_DISP));
            return false;
        }

        // 入力パラメータの予定ケース数、予定ピース数が未入力（ゼロ以下）の場合
        if (cParam.getPlanCaseQty() <= 0 && cParam.getPlanPieceQty() <= 0)
        {
            // 6023016=予定ケース数または予定ピース数を入力してください。
            setMessage(WmsMessageFormatter.format(6023016));
            return false;
        }

        // 入力パラメータのケース入数が0の場合、予定ケース数に入力があった場合
        if (cParam.getEnteringQty() == 0 && cParam.getPlanCaseQty() > 0)
        {
            // 6023017 = ケース入数が0の場合、予定ケース数は入力できません
            setMessage(WmsMessageFormatter.format(6023017));
            return false;
        }

        // オーバーフローの場合
        // (パラメタのケース入数 * パラメタのケース数) + パラメタのピース数 > WmsParamのMAX_TOTAL_QTY の場合
        long overflowcheckcount =
                ((long)cParam.getEnteringQty() * (long)cParam.getPlanCaseQty()) + (long)cParam.getPlanPieceQty();
        if (overflowcheckcount > (long)WmsParam.MAX_TOTAL_QTY)
        {
            // 6023184=予定数には作業上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023184, WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
            return false;
        }

        // 重複チェックを実施する
        // 出荷先一意チェックフラグ
        boolean isNonCheckCustomer = !WmsParam.IS_UNIQUE_CHECK_CUSTOMER;
        // 仕入先一意チェックフラグ
        boolean isNonCheckSupplier = !WmsParam.IS_UNIQUE_CHECK_SUPPLIER;

        //キー項目：予定日、出荷先コード、出荷伝票No.、出荷伝票行No.、入荷伝票No.、入荷伝票行No.、仕入先コード、荷主コード

        // ためうちエリアとの重複チェック
        for (ScheduleParams parameter : ps)
        {
            // 予定日
            if (cParam.getPlanDay().equals(parameter.getString(PLAN_DATE))
            // 出荷伝票No.
                    && cParam.getShipTicketNo().equals(parameter.getString(SHIPPING_TICKET))
                    // 出荷伝票行No.
                    && cParam.getShipLineNo() == parameter.getInt(SHIPPING_TICKET_LINE)
                    // 入荷伝票No.
                    && cParam.getReceiveTicketNo().equals(parameter.getString(RECEIVING_SLIP_NUMBER))
                    // 入荷伝票行No.
                    && cParam.getReceiveLineNo() == parameter.getInt(LINE_NO)
                    // 荷主コード
                    && cParam.getConsignorCode().equals(parameter.getString(CONSIGNOR_CODE))
                    // 出荷先コード
                    && (isNonCheckCustomer ? true
                                          : cParam.getCustomerCode().equals(parameter.getString(CUSTOMER_CODE)))
                    // 仕入先コード
                    && (isNonCheckSupplier ? true
                                          : cParam.getSupplierCode().equals(parameter.getString(SUPPLIER_CODE))))
            {
                // 6023020 = 既に同一データが存在するため、入力できません。
                setMessage(WmsMessageFormatter.format(6023020));
                return false;
            }
        }

        // DBとの重複チェックを行う
        CrossDockPlan[] plans = _XDPlanOpe.findPlan(cParam);
        if (!ArrayUtil.isEmpty(plans))
        {
            // 6023020 = 既に同一データが存在するため、入力できません。
            setMessage(WmsMessageFormatter.format(6023020));

            return false;
        }

        // 同一予定日、出荷先コード、出荷伝票No.、出荷伝票行No.で異なる商品、ロットNoのチェック
        if (!existPlan(cParam))
        {
            // 6023255=すでに同一の出荷伝票No.、行No.で異なる商品コード/ロットNo.が登録されているため、登録できません。
            setMessage(WmsMessageFormatter.format(6023255));
            return false;
        }

        // 6001019=入力を受け付けました。
        setMessage(WmsMessageFormatter.format(6001019));

        return true;
    }

    public List<Params> query(XDPlanRegistSCHParams searchParam)
            throws CommonException
    {
        Customer[] customer = new Customer[0];
        CustomerHandler customerHandler = new CustomerHandler(getConnection());
        CustomerSearchKey customerKey = new CustomerSearchKey();
        Params param = new Params();
        List<Params> outParam = new ArrayList<Params>();

        // 出荷先検索取得項目セット
        // 出荷先名称
        customerKey.setCustomerNameCollect();

        // 出荷先検索条件セット
        // 出荷先コード
        customerKey.setCustomerCode(searchParam.getString(XDPlanRegistSCHParams.CUSTOMER_CODE));
        // 荷主コード
        customerKey.setConsignorCode(searchParam.getString(XDPlanRegistSCHParams.CONSIGNOR_CODE));

        // 検索する
        customer = (Customer[])customerHandler.find(customerKey);

        if (ArrayUtil.isEmpty(customer))
        {
            // 検索結果が存在しない場合
            // 6023138=出荷先コードがマスタに登録されていません。
            setMessage(WmsMessageFormatter.format(6023138));

            if (customer == null || customer.length <= 0)
            {
                return new ArrayList<Params>();
            }
        }
        else
        {
            param.set(XDPlanRegistSCHParams.CUSTOMER_NAME, customer[0].getCustomerName());
        }
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
        XDOutParameter outParam = new XDOutParameter();
        // システムコントローラ
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // 取得データをセット
        // マスタパッケージ導入有無フラグ
        outParam.setMasterFlag(systemController.hasMasterPack());

        // 設定したパラメータを返却
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
     * 商品コードがマスタに存在するかをチェックします。<BR>
     * マスタパッケージが導入されていない場合は無条件でtrueを返します。
     * 
     * @param param 商品コードを含む入力パラメータ
     * @return 商品コードが商品マスタに存在する場合true、存在しない場合false
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    protected boolean existItemCode(Parameter param)
            throws CommonException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            XDInParameter sInParam = (XDInParameter)param;

            if (!StringUtil.isBlank(sInParam.getItemCode()))
            {
                ItemFinder finder = null;
                try
                {
                    finder = new ItemFinder(this.getConnection());
                    finder.open(true);

                    // 検索キーの設定
                    ItemSearchKey skey = new ItemSearchKey();
                    skey.setConsignorCode(sInParam.getConsignorCode());
                    skey.setItemCode(sInParam.getItemCode());

                    int searchCount = finder.search(skey);

                    if (searchCount < 1)
                    {
                        return false;
                    }
                }
                finally
                {
                    closeFinder(finder);
                }
            }
        }
        return true;
    }

    /**
     * クロスドック予定検索<BR>
     * 予定日、出荷先コード、出荷伝票No.、出荷伝票行No.、商品コード、ロットNoの検索条件に一致する<BR>
     * クロスドック予定情報の有無を返します。<BR>
     *
     * @param param 入力パラメータ
     * @return 一致する予定情報が有った時はtrue、無かった時はfalseを返します。
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public boolean existPlan(Parameter param)
            throws CommonException
    {
        // キャスト
        XDInParameter xdParam = (XDInParameter)param;

        // TC予定情報検索キーのインスタンス生成
        CrossDockPlanSearchKey xdkey = new CrossDockPlanSearchKey();
        CrossDockPlanFinder xdf = new CrossDockPlanFinder(getConnection());

        // 検索条件セット
        // TC予定情報．予定日
        xdkey.setPlanDay(xdParam.getPlanDay());
        // TC予定情報．出荷先コード
        if (WmsParam.IS_UNIQUE_CHECK_CUSTOMER)
        {
            xdkey.setCustomerCode(xdParam.getCustomerCode());
        }
        // TC予定情報．出荷伝票No.
        xdkey.setShipTicketNo(xdParam.getShipTicketNo());
        // TC予定情報．出荷伝票行No.
        xdkey.setShipLineNo(xdParam.getShipLineNo());
        // TC予定情報．荷主コード
        xdkey.setConsignorCode(xdParam.getConsignorCode());
        // TC予定情報．状態フラグ
        xdkey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        try
        {
            xdf.open(true);

            //該当データ件数取得
            int cnt = xdf.search(xdkey);

            // データが見つかったとき
            if (cnt > 0)
            {
                // データを取得(紐付くデータは1件のみとという前提)
                CrossDockPlan plan = (CrossDockPlan)xdf.getEntities(1)[0];

                // DBデータと入力データの商品(商品コード・ロットNo.で一意)が異なるとき
                if (!plan.getItemCode().equals(xdParam.getItemCode())
                        || !plan.getPlanLotNo().equals(xdParam.getPlanLotNo()))
                {
                    return false;
                }
            }
            return true;
        }
        finally
        {
            closeFinder(xdf);
        }
    }

    /**
     * 出荷先コードが出荷先マスタに存在するかをチェックします。<BR>
     * マスタパッケージが導入されていない場合は無条件でtrueを返します。
     * 
     * @param param 出荷先コードを含む入力パラメータ
     * @return 出荷先コードが出荷先マスタに存在する場合true、存在しない場合false
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    protected boolean existCustomerCode(Parameter param)
            throws CommonException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            XDInParameter sInParam = (XDInParameter)param;

            if (!StringUtil.isBlank(sInParam.getCustomerCode()))
            {
                CustomerFinder finder = null;
                try
                {
                    finder = new CustomerFinder(this.getConnection());
                    finder.open(true);

                    // 検索キーの設定
                    CustomerSearchKey skey = new CustomerSearchKey();
                    skey.setConsignorCode(sInParam.getConsignorCode());
                    skey.setCustomerCode(sInParam.getCustomerCode());

                    // 検索
                    int searchCount = finder.search(skey);

                    // 検索結果なし
                    if (searchCount < 1)
                    {
                        return false;
                    }
                }
                finally
                {
                    closeFinder(finder);
                }
            }
        }
        return true;
    }

    /**
     * 仕入先コードがマスタに存在するかをチェックします。<BR>
     * マスタパッケージが導入されていない場合は無条件でtrueを返します。
     * 
     * @param param 仕入先コードを含む入力パラメータ
     * @return 仕入先コードが商仕入先マスタに存在する場合true、存在しない場合false
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public boolean existSupplierCode(Parameter param)
            throws CommonException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            XDInParameter sInParam = (XDInParameter)param;

            if (!StringUtil.isBlank(sInParam.getSupplierCode()))
            {
                SupplierFinder finder = null;
                try
                {
                    finder = new SupplierFinder(this.getConnection());
                    finder.open(true);

                    // 検索キーの設定
                    SupplierSearchKey skey = new SupplierSearchKey();
                    skey.setConsignorCode(sInParam.getConsignorCode());
                    skey.setSupplierCode(sInParam.getSupplierCode());

                    int searchCount = finder.search(skey);

                    if (searchCount < 1)
                    {
                        return false;
                    }
                }
                finally
                {
                    closeFinder(finder);
                }
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
    protected XDInParameter createCheckParams(XDPlanRegistSCHParams p)
    {
        // 出庫パラメータの生成
        XDInParameter inputParam = new XDInParameter(getWmsUserInfo());

        // 取得データのセット
        // Consignor code : 荷主コード
        inputParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // XD plan date : 予定日
        inputParam.setPlanDay(p.getString(PLAN_DATE));
        // Receving Ticket No. : 伝票No.
        inputParam.setReceiveTicketNo(p.getString(RECEIVING_SLIP_NUMBER));
        // Receving Ticket Line No. : 行No.
        inputParam.setReceiveLineNo(p.getInt(LINE_NO));
        // Supplier Code :
        inputParam.setSupplierCode(p.getString(SUPPLIER_CODE));
        // Supplier Name :
        inputParam.setSupplierName(p.getString(SUPPLIER_NAME));
        // Item Code : 商品コード
        inputParam.setItemCode(p.getString(ITEM_CODE));
        // Item Name : 商品名称
        inputParam.setItemName(p.getString(ITEM_NAME));
        // Case Pack : ケース入数
        inputParam.setEnteringQty(p.getInt(CASE_PACK));
        // UPC code : JANコード
        inputParam.setJan(p.getString(JAN_CODE));
        // Case ITF : ケースITF
        inputParam.setItf(p.getString(CASE_ITF));
        // Lot No. : ロットNo.
        inputParam.setPlanLotNo(p.getString(LOT_NO));
        // Plan case qty : 予定ケース数
        inputParam.setPlanCaseQty(p.getInt(PLAN_CASE_QTY));
        // plan peice qty : 予定ピース数
        inputParam.setPlanPieceQty(p.getInt(PLAN_PIECE_QTY));
        // Shipping Ticket No. :
        inputParam.setShipTicketNo(p.getString(SHIPPING_TICKET));
        // Shipping Ticket Line No. :
        inputParam.setShipLineNo(p.getInt(SHIPPING_TICKET_LINE));
        // Customer code ; 出荷先コード
        inputParam.setCustomerCode(p.getString(CUSTOMER_CODE));
        // Customer Name : 出荷先名称
        inputParam.setCustomerName(p.getString(CUSTOMER_NAME));
        // Consolidation Area :
        inputParam.setSortingPlace(p.getString(SORT_PLACE));

        // 生成した出庫パラメータを返却
        return inputParam;
    }


    /**
     * startSCH()にて使用するパラメータ配列を生成します。<BR>
     * <BR>
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。
     * @return rpl 生成したパラメータ配列
     */
    protected List<XDInParameter> createStartSCHParams(ScheduleParams... ps)
    {
        // 出庫パラメータの生成
        XDInParameter rp = null;
        // 出庫パラメータ配列の生成
        List<XDInParameter> rpl = new ArrayList<XDInParameter>();

        // 取得件数分、繰り返す
        for (int i = 0; i < ps.length; i++)
        {
            // 出庫予定データ登録パラメータ型に変換
            XDPlanRegistSCHParams pp = (XDPlanRegistSCHParams)ps[i];
            // 出庫パラメータの初期化
            rp = new XDInParameter(getWmsUserInfo());

            // 取得データのセット
            // リストセル行No.
            rp.setRowNo(i + 1);
            rp.setConsignorCode(pp.getString(CONSIGNOR_CODE));
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            rp.setPlanDay(df.format(pp.getDate(PLAN_DATE)));
            rp.setBatchNo(pp.getString(BATCH_NO));
            rp.setSupplierCode(pp.getString(SUPPLIER_CODE));
            rp.setSupplierName(pp.getString(SUPPLIER_NAME));
            rp.setReceiveTicketNo(pp.getString(RECEIVING_SLIP_NUMBER));
            rp.setReceiveLineNo(pp.getInt(LINE_NO));
            rp.setItemCode(pp.getString(ITEM_CODE));
            rp.setItemName(pp.getString(ITEM_NAME));
            int enteringQty = pp.getInt(CASE_PACK);
            rp.setEnteringQty(enteringQty);
            int caseQty = pp.getInt(PLAN_CASE_QTY);
            rp.setPlanCaseQty(caseQty);
            int pieceQty = pp.getInt(PLAN_PIECE_QTY);
            rp.setPlanPieceQty(pieceQty);
            rp.setPlanLotNo(pp.getString(LOT_NO));
            rp.setJan(pp.getString(JAN_CODE));
            rp.setItf(pp.getString(CASE_ITF));
            rp.setPlanQty(enteringQty * caseQty + pieceQty);
            rp.setShipTicketNo(pp.getString(SHIPPING_TICKET));
            rp.setShipLineNo(pp.getInt(SHIPPING_TICKET_LINE));
            rp.setCustomerCode(pp.getString(CUSTOMER_CODE));
            rp.setCustomerName(pp.getString(CUSTOMER_NAME));
            // DFKLOOK: DAC modified from here
            rp.setSortingPlace(pp.getString(CONSOLIDATION_AREA));
            // DFKLOOK: DAC modified to here
            rpl.add(rp);
        }
        // 生成した配列を返却
        return rpl;
    }

    /**
     * Check the given item code exists or not in Master.
     */
    public boolean existItemCode(String code)
            throws CommonException
    {
        _ItemKey.clear();
        _ItemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _ItemKey.setItemCode(code);
        _ItemKey.setItemCodeCollect();
        Item[] ent = (Item[])_ItemHander.find(_ItemKey);
        if (ent == null || ent.length == 0)
        {
            return false;
        }
        return true;
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
