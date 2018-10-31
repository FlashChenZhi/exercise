// $Id: FaItemInquiryBusiness.java 7401 2010-03-05 12:08:12Z shibamoto $
package jp.co.daifuku.wms.master.display.faiteminquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.master.dasch.FaItemInquiryDASCH;
import jp.co.daifuku.wms.master.dasch.FaItemInquiryDASCHParams;
import jp.co.daifuku.wms.master.display.faiteminquiry.FaItemInquiry;
import jp.co.daifuku.wms.master.exporter.ItemMasterListParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7401 $, $Date:: 2010-03-05 21:08:12 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaItemInquiryBusiness
        extends FaItemInquiry
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaItemInquiry(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaItemInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaItemInquiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaItemInquiry(LST_SOFTZONE) */
    private static final ListCellKey KEY_LST_SOFTZONE = new ListCellKey("LST_SOFTZONE", new StringCellColumn(), true, false);

    /** lst_FaItemInquiry(LST_TEMPORARY_TYPE_NAME) */
    private static final ListCellKey KEY_LST_TEMPORARY_TYPE_NAME = new ListCellKey("LST_TEMPORARY_TYPE_NAME", new StringCellColumn(), true, false);

    /** lst_FaItemInquiry keys */
    private static final ListCellKey[] LST_FAITEMINQUIRY_KEYS = {
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_SOFTZONE,
        KEY_LST_TEMPORARY_TYPE_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel StockExistence */
    private RadioButtonGroup _grp_StockExistence;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaItemInquiry */
    private ScrollListCellModel _lcm_lst_FaItemInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaItemInquiryBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        setTitle();

        // save a popup event source.
        viewState.setString(_KEY_POPUPSOURCE, request.getParameter(_KEY_POPUPSOURCE));

        // initialize pulldown models.
        initializePulldownModels();

        // process call.
        page_Load_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize components.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_P_FromSearchItemCode_Click"))
        {
            // process call.
            btn_P_FromSearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_FromSearchItemName_Click"))
        {
            // process call.
            btn_P_FromSearchItemName_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_ToSearchItemCode_Click"))
        {
            // process call.
            btn_P_ToSearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_ToSearchItemName_Click"))
        {
            // process call.
            btn_P_ToSearchItemName_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_FromSearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_FromSearchItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_FromSearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_FromSearchItemName_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_ToSearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_ToSearchItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_ToSearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_ToSearchItemName_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_Pager_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_Pager_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_Pager_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_Pager_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // セッションからコネクションを削除する
        SessionUtil.deleteSession(getSession());
        // メニューへ遷移します
        forward(BusinessClassHelper.getSubMenuPath(viewState.getString(Constants.M_MENUID_KEY)));
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     *
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize StockExistence.
        _grp_StockExistence = new RadioButtonGroup(new RadioButton[]{rdo_StockExistence_existence, rdo_StockExistence_Noexistence, rdo_StockExistence_All}, locale);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_Pager}, locale);

        // initialize lst_FaItemInquiry.
        _lcm_lst_FaItemInquiry = new ScrollListCellModel(lst_FaItemInquiry, LST_FAITEMINQUIRY_KEYS, locale);
        _lcm_lst_FaItemInquiry.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_FaItemInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_FaItemInquiry.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_FaItemInquiry.setToolTipVisible(KEY_LST_SOFTZONE, false);
        _lcm_lst_FaItemInquiry.setToolTipVisible(KEY_LST_TEMPORARY_TYPE_NAME, false);
    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void dispose()
            throws Exception
    {
        // dispose DASCH.
        disposeDasch();
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_FaItemInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_FromItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_StockExistence_All.setChecked(true);
        btn_Print.setEnabled(false);
        btn_Preview.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_FromSearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_FromItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_FromSearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_FromSearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_FromItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_FromItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_FromItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_FromSearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_FromItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_FromSearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_FromSearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_FromItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));
        txt_FromItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));

        // set focus.
        setFocus(txt_FromItemName);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_ToSearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ToItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_ToSearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_ToSearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ToItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ToItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ToItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_ToSearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ToItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_ToSearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_ToSearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ToItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));
        txt_ToItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ToItemName);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaItemInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaItemInquiryDASCHParams inparam = new FaItemInquiryDASCHParams();
            inparam.set(FaItemInquiryDASCHParams.FROM_ITEM_CODE, txt_FromItemCode.getValue());
            inparam.set(FaItemInquiryDASCHParams.TO_ITEM_CODE, txt_ToItemCode.getValue());
            inparam.set(FaItemInquiryDASCHParams.STOCK_EXISTENCE, _grp_StockExistence.getSelectedValue());
            inparam.set(FaItemInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaItemInquiry.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaItemInquiry.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
            }
            else
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        FaItemInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaItemInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaItemInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaItemInquiry.getNewLine();
                line.setValue(KEY_LST_NO, outparam.get(FaItemInquiryDASCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaItemInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaItemInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_SOFTZONE, outparam.get(FaItemInquiryDASCHParams.SOFT_ZONE_NAME));
                line.setValue(KEY_LST_TEMPORARY_TYPE_NAME, outparam.get(FaItemInquiryDASCHParams.TEMPORARY_TYPE_NAME));
                lst_FaItemInquiry_SetLineToolTip(line);
                _lcm_lst_FaItemInquiry.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaItemInquiry.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromItemCode.validate(this, false);
        txt_FromItemName.validate(this, false);
        txt_ToItemCode.validate(this, false);
        txt_ToItemName.validate(this, false);
        rdo_StockExistence_existence.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaItemInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaItemInquiryDASCHParams inparam = new FaItemInquiryDASCHParams();
            inparam.set(FaItemInquiryDASCHParams.FROM_ITEM_CODE, txt_FromItemCode.getValue());
            inparam.set(FaItemInquiryDASCHParams.TO_ITEM_CODE, txt_ToItemCode.getValue());
            inparam.set(FaItemInquiryDASCHParams.STOCK_EXISTENCE, _grp_StockExistence.getSelectedValue());
            inparam.set(FaItemInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("ItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ItemMasterListParams expparam = new ItemMasterListParams();
                expparam.set(ItemMasterListParams.ITEM_CODE, outparam.get(FaItemInquiryDASCHParams.ITEM_CODE));
                expparam.set(ItemMasterListParams.ITEM_NAME, outparam.get(FaItemInquiryDASCHParams.ITEM_NAME));
                expparam.set(ItemMasterListParams.SOFT_ZONE_NAME, outparam.get(FaItemInquiryDASCHParams.SOFT_ZONE_NAME));
                expparam.set(ItemMasterListParams.TEMPORARY_TYPE_NAME, outparam.get(FaItemInquiryDASCHParams.TEMPORARY_TYPE_NAME));
                expparam.set(ItemMasterListParams.LAST_UPDATE_DATE, outparam.get(FaItemInquiryDASCHParams.LAST_UPDATE_DATE));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_FromItemCode.getStringValue(), "");
            part11List.add(txt_ToItemCode.getStringValue(), "");
            part11List.add(InParameter.STOCK_EXISTENCE_EXISTENCE, "", rdo_StockExistence_existence.getChecked());
            part11List.add(InParameter.STOCK_EXISTENCE_NOEXISTENCE, "", rdo_StockExistence_Noexistence.getChecked());
            part11List.add(InParameter.STOCK_EXISTENCE_ALL, "", rdo_StockExistence_All.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);
            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_FromItemCode.setValue(null);
        txt_FromItemName.setValue(null);
        txt_ToItemCode.setValue(null);
        txt_ToItemName.setValue(null);
        rdo_StockExistence_All.setChecked(true);

        // set focus.
        setFocus(txt_FromItemCode);
    }

    /**
     * 画面タイトルを設定します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void setTitle()
            throws Exception
    {
        // httpRequestからメニュー用パラメータを取得する
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // ViewStateへ保存する
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     *
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class
