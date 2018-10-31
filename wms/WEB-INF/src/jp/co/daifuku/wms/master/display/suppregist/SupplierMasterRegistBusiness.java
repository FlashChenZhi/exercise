// $Id: SupplierMasterRegistBusiness.java 7364 2010-03-05 00:42:34Z okayama $
package jp.co.daifuku.wms.master.display.suppregist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.listbox.supp.LstSupplierParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.master.schedule.SupplierMasterRegistSCH;
import jp.co.daifuku.wms.master.schedule.SupplierMasterRegistSCHParams;

/**
 * 仕入先マスタ登録の画面処理を行います。
 *
 * @version $Revision: 7364 $, $Date: 2010-03-05 09:42:34 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class SupplierMasterRegistBusiness
        extends SupplierMasterRegist
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public SupplierMasterRegistBusiness()
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
        // initialize componenets.
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
        if (eventSource.equals("btn_Search_Click"))
        {
            // process call.
            btn_Search_Click_DlgBack(dialogParams);
        }
    }

    // DFKLOOK start
    /**
    *
    * @param e ActionEvent
    * @throws Exception
    */
    public void page_ConfirmBack(ActionEvent e)
           throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
			return;
		}

		// remove event source.
		viewState.remove(_KEY_CONFIRMSOURCE);

		// check result.
		boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
		if(!isExecute)
		{
			return;
		}

		// choose process.
		if(eventSource.startsWith("btn_Submit_Click"))
		{
			// process call.
			btn_Submit_Click_Process(eventSource);
		}
	}
	// DFKLOOK end
   
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Search_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Search_Click_Process();
    }


    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
    	// DFKLOOK 引数追加
        // process call.
        btn_Submit_Click_Process(null);
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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_SupplierCode);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_SupplierCode.setValue(null);
        txt_SupplierName.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Search_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstSupplierParams inparam = new LstSupplierParams();
        inparam.set(LstSupplierParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
        inparam.set(LstSupplierParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Search_Click");
        redirect("/base/listbox/supp/LstSupplier.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Search_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstSupplierParams outparam = new LstSupplierParams(dialogParams);
        txt_SupplierCode.setValue(outparam.get(LstSupplierParams.SUPPLIER_CODE));
        txt_SupplierName.setValue(outparam.get(LstSupplierParams.SUPPLIER_NAME));

        // set focus.
        setFocus(txt_SupplierCode);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK 引数追加
    private void btn_Submit_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_SupplierCode.validate(this, true);
        txt_SupplierName.validate(this, false);

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 登録しますか?
            this.setConfirm("MSG-W0012", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
            return;
        }
    	// DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SupplierMasterRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SupplierMasterRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SupplierMasterRegistSCHParams inparam = new SupplierMasterRegistSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(SupplierMasterRegistSCHParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
            inparam.set(SupplierMasterRegistSCHParams.SUPPLIER_NAME, txt_SupplierName.getValue());
            inparam.set(SupplierMasterRegistSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_SupplierCode.getStringValue(), "");
            part11List.add(txt_SupplierName.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
        txt_SupplierCode.setValue(null);
        txt_SupplierName.setValue(null);

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
