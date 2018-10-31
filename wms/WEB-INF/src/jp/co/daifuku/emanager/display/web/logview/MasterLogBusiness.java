// $Id: MasterLogBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.LogExpImpSet;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler;
import jp.co.daifuku.emanager.database.handler.Part11LogHandler;
import jp.co.daifuku.emanager.display.web.logview.listbox.DsNoListBusiness;
import jp.co.daifuku.emanager.display.web.logview.listbox.MasterLogListBusiness;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en> */
public class MasterLogBusiness
        extends MasterLog
        implements EmConstants
{
    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(EmConstants.MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            //<jp>ViewStateへ保持する</jp><en> It holds to ViewState. </en>
            this.getViewState().setString(EmConstants.M_MENUID_KEY, menuID);
            this.getViewState().setString(EmConstants.M_TITLE_KEY, title);

            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);

            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }
        else if (!Validator.isEmptyCheck(this.getViewState().getString(EmConstants.M_TITLE_KEY)))
        {
            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(this.getViewState().getString(EmConstants.M_TITLE_KEY));
        }

        // DB検索かCSV検索かのフラグ
        String part11Log =
                (String)httpRequest.getAttribute(PART11LOG_VIEW_KEY) != null ? (String)httpRequest.getAttribute(PART11LOG_VIEW_KEY)
                                                                            : PART11LOG_VIEW_DB;
        this.getViewState().setString(EmConstants.PART11LOG_VIEW_KEY, part11Log);

        // 日付形式の設定
        lbl_Day.setText(DisplayText.getText("LBL-W1365"));

        // 入力項目のクリア
        this.clearInputArea();

        // 初期データ設定
        this.setInitValues(PART11LOG_VIEW_DB.equals(part11Log));

        this.setFocus(txt_RetrievalBeginning);
        
    }

    /**
     * 入力項目のクリア処理を行います。
     */
    private void clearInputArea()
    {
        // 開始検索日時クリア
        txt_RetrievalBeginning.setText("");
        // 開始検索時刻クリア
        txt_TimeRetrievalBeginning.setText("");
        // 終了検索日時クリア
        txt_RetrievalEnd.setText("");
        // 終了検索時刻クリア
        txt_TimeRetrievalEnd.setText("");
        // 終了検索日時クリア
        txt_RetrievalEnd.setReadOnly(false);
        //txt_RetrievalEnd.
        // 終了検索時刻txt_RetrievalEnd
        txt_TimeRetrievalEnd.setReadOnly(false);

        txt_UserId.setText("");
        txt_DsNo.setText("");
        txt_R_PageName.setText("");
        txt_R_PageName.setReadOnly(true);

        pul_Master.setSelectedIndex(0);
    }


    /** 
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        this.setFocus(txt_RetrievalBeginning);
        this.disableTabs();
        
// 2008/12/25 K.Matsuda Start eAWCがPart11未対応のため、v2.1では在庫履歴は表示しない
        if(!EmConstants.PRODUCT_NAME_WMS.equals(EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME)))
        {
            // プロダクトがeWareNavi以外の場合は、在庫履歴のタブは非表示
            addOnloadScript("document.all('" + tab_InventoryLog.getId() + "').style.display=\"none\";");
        }
// 2008/12/25 K.Matsuda End

    }

    /**
     * Set properties required to dispay initial screen 
     * 
     * @param isDBSearch DB検索かどうか
     * @throws Exception
     */
    private void setInitValues(boolean isDBSearch)
            throws Exception
    {
        // マスタプルダウンの初期化
        this.setMasterPullDownData();

        // DB検索の場合
        if (isDBSearch)
        {
            Calendar cal = Calendar.getInstance();

            // 開始検索日時
            txt_RetrievalBeginning.setDate(cal.getTime());
            // 終了検索日時
            txt_RetrievalEnd.setDate(cal.getTime());
        }
        // CSV検索の場合
        else
        {
            Connection conn = null;
            try
            {
                conn = EmConnectionHandler.getPageDbConnection(this);
                Part11LogHandler p11Hdlr = EmHandlerFactory.getPart11LogHandler(conn);

                PullDownItem firstItem = pul_Master.getItem(0);
                // 一番初めに表示されるマスタの取込テーブルを取得
                String impTable = firstItem.getValue().split(",")[1];

                // 取込テーブル内の最小の日付を取得する
                Date minDate = p11Hdlr.findMinLogDate(impTable);
                if (minDate != null)
                {
                    // 開始検索日時
                    txt_RetrievalBeginning.setDate(minDate);
                    // 終了検索日時
                    txt_RetrievalEnd.setDate(minDate);
                }
            }
            finally
            {
                EmConnectionHandler.closeConnection(conn);
            }
        }
    }

    private void disableTabs()
    {
        // disable all tabs except AccessLog tab 
        tab_AccessLog.setSelectedIndex(0);
        tab_InventoryLog.setSelectedIndex(0);
        tab_OperationLog.setSelectedIndex(0);
    }

    /**
     * マスタプルダウンを初期化します。
     * 
     * @throws Exception
     */
    private void setMasterPullDownData()
            throws Exception
    {
        this.pul_Master.clearItem();
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogExpImpSetHandler handler = EmHandlerFactory.getLogExpImpSetHandler(conn);

            LogExpImpSet[] entity = handler.findMasterData();
            if (entity != null)
            {
                for (int i = 0; i < entity.length; i++)
                {
                    String exportTableName = entity[i].getExportTable();
                    String importTableName = entity[i].getImportTable();
                    String masterUri = entity[i].getMasterUri();
                    int masterFlag = entity[i].getMasterFlag();
                    String value = exportTableName + "," + importTableName + "," + masterUri + "," + masterFlag;
                    this.pul_Master.addItem(value, entity[i].getTableResourceKey(), null, false);
                }
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_SearchPopup_Click(ActionEvent e)
            throws Exception
    {
        //DS number search parameter
        ForwardParameters param = new ForwardParameters();
        param.setParameter(DsNoListBusiness.DS_NO, txt_DsNo.getText());
        param.setParameter(DsNoListBusiness.USER_ID, txt_UserId.getText());

        // if date is not null, do cast
        Date startDate = txt_RetrievalBeginning.getDate();
        Date startTime = txt_TimeRetrievalBeginning.getTime();
        Date endDate = txt_RetrievalEnd.getDate();
        Date endTime = txt_TimeRetrievalEnd.getTime();

        // if the view is not from Import data , set db data
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);

        // modify user entered dates to search dates based on conditions
        List searchDates = EManagerUtil.getSearchDates(startDate, startTime, endDate, endTime);

        String tableNames = this.pul_Master.getSelectedValue();

        String[] tables = tableNames.split(",", -1);
        //set Screen title and Table to access the data based on input condition
        if (part11LogKey != null && EmConstants.PART11LOG_VIEW_IMP.equals(part11LogKey))
        {
            param.setParameter(DsNoListBusiness.TABLE_NAME, tables[1]);
        }
        else if (part11LogKey != null && EmConstants.PART11LOG_VIEW_DB.equals(part11LogKey))
        {
            param.setParameter(DsNoListBusiness.TABLE_NAME, tables[0]);
        }

        this.session.setAttribute(DsNoListBusiness.START_DATE, searchDates.get(0));
        this.session.setAttribute(DsNoListBusiness.END_DATE, searchDates.get(1));

        redirect("/emanager/logview/listbox/DsNoList.do", param, "/Progress.do");
    }


    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        ForwardParameters param = new ForwardParameters();

        String tableNames = this.pul_Master.getSelectedValue();
        // if the view is not from Import data , set db data
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        String[] tables = tableNames.split(",", -1);

        int masterFlag = Integer.parseInt(tables[3]);
        // ユーザ管理用マスタの場合
        if (EmConstants.LOG_SETTING_TOOLS_MASTER == masterFlag)
        {
            // put values in session to pass them to AccessLogList box
            param.setParameter(MasterLogListBusiness.USER_ID, txt_UserId.getText());
            param.setParameter(MasterLogListBusiness.DS_NO, txt_DsNo.getText());

            // if date is not null, do cast
            Date startDate = txt_RetrievalBeginning.getDate();
            Date startTime = txt_TimeRetrievalBeginning.getTime();
            Date endDate = txt_RetrievalEnd.getDate();
            Date endTime = txt_TimeRetrievalEnd.getTime();

            //modify user entered dates to search dates based on conditions
            List searchDates =
                    EManagerUtil.getSearchDates(startDate, startTime, endDate, endTime);

            //set Screen title and Table to access the data based on input condition
            if (part11LogKey != null && EmConstants.PART11LOG_VIEW_IMP.equals(part11LogKey))
            {
                param.setParameter(MasterLogListBusiness.TITLE, "TLE-T0140");
                param.setParameter(MasterLogListBusiness.TABLE_NAME, tables[1]);
            }
            else if (part11LogKey != null && EmConstants.PART11LOG_VIEW_DB.equals(part11LogKey))
            {
                param.setParameter(MasterLogListBusiness.TITLE, "TLE-T0139");
                param.setParameter(MasterLogListBusiness.TABLE_NAME, tables[0]);
            }

            this.session.setAttribute(MasterLogListBusiness.START_DATE, searchDates.get(0));
            this.session.setAttribute(MasterLogListBusiness.END_DATE, searchDates.get(1));
        }
        // プロダクト用のマスタの場合
        else if (EmConstants.LOG_SETTING_PRODUCT_MASTER == masterFlag)
        {
            String pageNameResourceKey = "";
            // DS番号からページ名リソースキーを取得する
            if (!Validator.isEmptyCheck(txt_DsNo.getText()))
            {
                Connection conn = null;
                try
                {
                    conn = EmConnectionHandler.getPageDbConnection(this);
                    FunctionHandler handler = EmHandlerFactory.getFunctionHandler(conn);
                    Function[] funcs = handler.findByDsNo(txt_DsNo.getText());
                    if (funcs != null && funcs.length > 0)
                    {
                        pageNameResourceKey = funcs[0].getPageResourceKey();
                    }
                }
                finally
                {
                    EmConnectionHandler.closeConnection(conn);
                }
            }
            String dbTypeKey = getViewState().getString(PART11LOG_VIEW_KEY);

// 2008/12/25 K.Matsuda Start eAWCに組み込んだ場合はeWareNaviのクラスはないため、リフレクションに変更
            if(EmConstants.PRODUCT_NAME_WMS.equals(EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME)))
            {
                Class part11ListBoxDefine = Class.forName("jp.co.daifuku.wms.part11.listbox.Part11ListBoxDefine");
                
                // 現在保持しているログを検索するかインポートしたログを検索するかどうかのフラグ
                if (PART11LOG_VIEW_IMP.equals(dbTypeKey))
                {
                    dbTypeKey = (String)part11ListBoxDefine.getField("DBTYPE_IMP").get(null);
                }
                else
                {
                    dbTypeKey = (String)part11ListBoxDefine.getField("DBTYPE_DB").get(null);
                }
                
                // 補完済の日付、時刻を求める
                Class wmsFormatter = Class.forName("jp.co.daifuku.wms.base.util.WmsFormatter");
                String fromDay = (String)wmsFormatter.getMethod("toParamDate", Date.class).invoke(null, txt_RetrievalBeginning.getDate());
                String fromTime = (String)wmsFormatter.getMethod("toParamTime", Date.class).invoke(null, txt_TimeRetrievalBeginning.getTime());
                String toDay = (String)wmsFormatter.getMethod("toParamDate", Date.class).invoke(null, txt_RetrievalEnd.getDate());
                String toTime = (String)wmsFormatter.getMethod("toParamTime", Date.class).invoke(null, txt_TimeRetrievalEnd.getTime());

                Class[] list = {String.class, String.class, String.class, String.class, boolean.class};
                String[] times = (String[])wmsFormatter.getMethod("getSearchDayTime", list).invoke(null, fromDay, fromTime, toDay, toTime, true);

                // DBフラグ
                param.setParameter((String)part11ListBoxDefine.getField("TABLE_NAME").get(null), dbTypeKey);
                // 開始日付
                param.setParameter((String)part11ListBoxDefine.getField("DISPFROMDAY_KEY").get(null), times[0]);
                // 開始時刻
                param.setParameter((String)part11ListBoxDefine.getField("DISPFROMTIME_KEY").get(null), times[1]);
                // 終了日付
                param.setParameter((String)part11ListBoxDefine.getField("DISPTODAY_KEY").get(null), times[2]);
                // 終了日付
                param.setParameter((String)part11ListBoxDefine.getField("DISPTOTIME_KEY").get(null), times[3]);
                // ユーザID
                param.setParameter((String)part11ListBoxDefine.getField("USERID_KEY").get(null), txt_UserId.getText());
                // DS番号
                param.setParameter((String)part11ListBoxDefine.getField("DSNUMBER_KEY").get(null), txt_DsNo.getText());
                // ページ名リソースキー
                param.setParameter((String)part11ListBoxDefine.getField("PAGENAMERESOURCEKEY").get(null), pageNameResourceKey);
            }
            else if(EmConstants.PRODUCT_NAME_AWC.equals(EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME))) 
            {
            }
// 2008/12/25 K.Matsuda End
        }

        String masterUri = tables[2];
        redirect(masterUri, param, "/Progress.do");
    }

    /**
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();
        // get DS number
        String dsNo = param.getParameter(DsNoListBusiness.DS_NO);
        if (!Validator.isEmptyCheck(dsNo))
        {
            txt_DsNo.setText(dsNo);
            setFocus(txt_DsNo);
        }
        //get Page resource Key
        String pageReousrceKey = param.getParameter(DsNoListBusiness.PAGE_NAME_KEY);

        if (!Validator.isEmptyCheck(pageReousrceKey))
        {
            // set page name
            txt_R_PageName.setText(pageReousrceKey);
        }
        if (Validator.isEmptyCheck(txt_DsNo.getText()))
        {
            // set page name
            txt_R_PageName.setText("");
        }
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        page_Load(e);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_MasterLog_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        page_Load(e);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_AccessLog_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        forward("/emanager/logview/AccessLog.do");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_InventoryLog_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        forward("/emanager/logview/InventoryLog.do");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_OperationLog_Click(ActionEvent e)
            throws Exception
    {
        //if the view is not from Import data , set db data
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        forward("/emanager/logview/OperationLog.do");
    }
}
//end of class
