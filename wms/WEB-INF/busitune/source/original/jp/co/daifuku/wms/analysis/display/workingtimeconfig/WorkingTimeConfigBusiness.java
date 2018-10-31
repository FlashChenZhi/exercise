// $Id: WorkingTimeConfigBusiness.java 7451 2010-03-08 04:26:04Z okayama $
package jp.co.daifuku.wms.analysis.display.workingtimeconfig;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.display.workingtimeconfig.ViewStateKeys;
import jp.co.daifuku.wms.analysis.display.workingtimeconfig.WorkingTimeConfig;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeConfigSCH;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeConfigSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7451 $, $Date:: 2010-03-08 13:26:04 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class WorkingTimeConfigBusiness
        extends WorkingTimeConfig
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

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
    public WorkingTimeConfigBusiness()
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
    public void btn_StorageWkTimeResult_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_StorageWkTimeResult_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RetrievalWkTimeResult_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RetrievalWkTimeResult_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Setting_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Setting_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Restore_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Restore_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Reflesh_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Reflesh_Click_Process();
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
    private void dispose()
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
        setFocus(txt_StorageSecPerItem);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeConfigSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeConfigSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeConfigSCHParams inparam = new WorkingTimeConfigSCHParams();
            inparam.set(WorkingTimeConfigSCHParams.KIND_WORK, "");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StorageSecPerItem.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM));
                txt_StorageSecPerPiece.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE));
                txt_StorageAveTimeItem.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_AVE_TIME_ITEM));
                txt_StorageAvePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_AVE_PIECE));
                txt_StorageAveTimePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_AVE_TIME_PIECE));
                txt_RetrievalSecPerItem.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM));
                txt_RetrievalSecPerPiece.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE));
                txt_RetrievalAveTimeItem.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_AVE_TIME_ITEM));
                txt_RetrievalAvePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_AVE_PIECE));
                txt_RetrievalAveTimePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_AVE_TIME_PIECE));
            }

            // clear.
            txt_StorageAveTimeItem.setReadOnly(true);
            txt_StorageAvePiece.setReadOnly(true);
            txt_StorageAveTimePiece.setReadOnly(true);
            txt_RetrievalAveTimeItem.setReadOnly(true);
            txt_RetrievalAvePiece.setReadOnly(true);
            txt_RetrievalAveTimePiece.setReadOnly(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_StorageWkTimeResult_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.KIND_OF_WORK, "LBL-W1222");
            viewState.setObject(ViewStateKeys.STORAGE_SEC_PER_ITEM, txt_StorageSecPerItem.getValue());
            viewState.setObject(ViewStateKeys.STORAGE_SEC_PER_PIECE, txt_StorageSecPerPiece.getValue());

            // forward.
            forward("/analysis/workingtimeconfig/WorkingTimeConfig2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_RetrievalWkTimeResult_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.KIND_OF_WORK, "LBL-W1223");
            viewState.setObject(ViewStateKeys.RETRIEVAL_SEC_PER_ITEM, txt_RetrievalSecPerItem.getValue());
            viewState.setObject(ViewStateKeys.RETRIEVAL_SEC_PER_PIECE, txt_RetrievalSecPerPiece.getValue());

            // forward.
            forward("/analysis/workingtimeconfig/WorkingTimeConfig2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Setting_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StorageSecPerItem.validate(this, true);
        txt_StorageSecPerPiece.validate(this, true);
        txt_RetrievalSecPerItem.validate(this, true);
        txt_RetrievalSecPerPiece.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeConfigSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeConfigSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeConfigSCHParams inparam = new WorkingTimeConfigSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM, txt_StorageSecPerItem.getValue());
            inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE, txt_StorageSecPerPiece.getValue());
            inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM, txt_RetrievalSecPerItem.getValue());
            inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE, txt_RetrievalSecPerPiece.getValue());

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
            part11List.add(txt_StorageSecPerItem.getStringValue(), "0");
            part11List.add(txt_StorageSecPerPiece.getStringValue(), "0");
            part11List.add(txt_StorageAveTimeItem.getStringValue(), "0");
            part11List.add(txt_StorageAvePiece.getStringValue(), "0");
            part11List.add(txt_StorageAveTimePiece.getStringValue(), "0");
            part11List.add(txt_RetrievalSecPerItem.getStringValue(), "0");
            part11List.add(txt_RetrievalSecPerPiece.getStringValue(), "0");
            part11List.add(txt_RetrievalAveTimeItem.getStringValue(), "0");
            part11List.add(txt_RetrievalAvePiece.getStringValue(), "0");
            part11List.add(txt_RetrievalAveTimePiece.getStringValue(), "0");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

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
    private void btn_Restore_Click_Process()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Reflesh_Click_Process()
            throws Exception
    {
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
