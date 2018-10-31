// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.display.printermodify;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.PrinterModifySCH;
import jp.co.daifuku.wms.system.schedule.PrinterModifySCHParams;

/**
 *
 * 端末ごとのプリンタ登録を行う画面処理を行うクラスです。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class PrinterModifyBusiness
        extends PrinterModify
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    // DFKLOOK start
    // Defines the number of scrollListCell column.
    private static final int PUL_COLUM_NO = 3;

    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // DFKLOOK　end
    
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PrinterModify(LST_HDN_UPDATA_DATE) */
    private static final ListCellKey KEY_LST_HDN_UPDATA_DATE = new ListCellKey("LST_HDN_UPDATA_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false, false);

    /** lst_PrinterModify(LST_TERMINAL) */
    private static final ListCellKey KEY_LST_TERMINAL = new ListCellKey("LST_TERMINAL", new StringCellColumn(), true, false);

    /** lst_PrinterModify(LST_TERMINAL_NAME) */
    private static final ListCellKey KEY_LST_TERMINAL_NAME = new ListCellKey("LST_TERMINAL_NAME", new StringCellColumn(), true, false);

    /** lst_PrinterModify(LST_PRINTER_NAME) */
    private static final ListCellKey KEY_LST_PRINTER_NAME = new ListCellKey("LST_PRINTER_NAME", new StringCellColumn(), true, true);

    /** lst_PrinterModify keys */
    private static final ListCellKey[] LST_PRINTERMODIFY_KEYS = {
        KEY_LST_HDN_UPDATA_DATE,
        KEY_LST_TERMINAL,
        KEY_LST_TERMINAL_NAME,
        KEY_LST_PRINTER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_PrinterModify */
    private ListCellModel _lcm_lst_PrinterModify;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PrinterModifyBusiness()
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

    // DFKLOOK start
	/**
	 * @param e
	 *        ActionEvent
	 * @throws Exception
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		// get event source.
		String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
		if(eventSource == null)
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
		if(eventSource.startsWith("btn_Set2_Click"))
		{
			// process call.
			btn_Set2_Click_Process(eventSource);
		}
	}
	// DFKLOOK end

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set2_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set2_Click_Process(null);
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

    //DFKLOOK start

    /**
     * 端末に対応したプリンタが登録されているプルダウンインデックスの
     * 値を返します。
     * 対応する値がない場合0を返します。
     * 
     * @param　 
     * @return 端末に対応したプリンタプルダウンのインデックスを返します。
     * 
     */
    protected int retPrintIndex(Params ps, ArrayList<String> pr)
            throws CommonException
    {
        int index = 0;

        String checkPrint = ps.getString(PrinterModifySCHParams.PRINTER_NAME);

        for (String p : pr)
        {
            if (checkPrint.equals(p) || checkPrint.equals(""))
            {
                break;
            }
            index++;
        }
        return index;
    }

    /**
     * 
     * 同じプリンタ名が入っていない配列をソートして返します。
     * 
     * @param　ファイルとDBに登録されているプリンタのリスト 
     * @return 同一名を省きソートしたリストを返します。
     */
    protected ArrayList<String> checkSortPrinter(ArrayList<String> allprinter)
            throws CommonException
    {
        ArrayList<String> retPrinter = new ArrayList<String>();

        for (int c = 0; c < allprinter.size(); c++)
        {
            String checkPrint = allprinter.get(c);

            if (0 == retPrinter.size())
            {
                retPrinter.add(checkPrint);
            }
            else
            {
                int c2 = retPrinter.size();
                int existFlag = 0;
                //返却リストに登録されている数分ループ
                for (int counter = 0; counter < c2; counter++)
                {
                    // 同じプリンタ名が存在する場合フラグ更新
                    if (checkPrint.equals(retPrinter.get(counter)))
                    {
                        existFlag = 1;
                    }
                }
                //同じプリンタ名が存在しない場合リストにセットする。
                if (existFlag == 0)
                {
                    retPrinter.add(checkPrint);
                }
            }
        }

        //ソートを行います。
        Collections.sort(retPrinter);

        return retPrinter;
    }


    /**
     * ArratListをPulldownItemの配列型にして返します。<BR>
     * 
     * @param aryParam ArrayList
     * @return String[]
     */

    protected PullDownItem[] convertToArrayItem(ArrayList<String> aryParam)
            throws CommonException
    {
        if (aryParam.size() > 0)
        {
            ArrayList<PullDownItem> pulldownData = new ArrayList<PullDownItem>();
            for (int c = 0; aryParam.size() > c; c++)
            {
                PullDownItem itm = new PullDownItem();
                itm.setValue(aryParam.get(c));
                pulldownData.add(itm);
            }
            return pulldownData.toArray(new PullDownItem[pulldownData.size()]);
        }
        else
        {
            // Set null if there is no data;
            return new PullDownItem[0];
        }
    }

    // DFKLOOK End

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

        // initialize lst_PrinterModify.
        _lcm_lst_PrinterModify = new ListCellModel(lst_PrinterModify, LST_PRINTERMODIFY_KEYS, locale);
        _lcm_lst_PrinterModify.setToolTipVisible(KEY_LST_TERMINAL, true);
        _lcm_lst_PrinterModify.setToolTipVisible(KEY_LST_TERMINAL_NAME, true);
        _lcm_lst_PrinterModify.setToolTipVisible(KEY_LST_PRINTER_NAME, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PrinterModify_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK start
        //line.addToolTip("LBL-W0149", KEY_LST_TERMINAL_NAME);
        line.addToolTip("LBL-9007", KEY_LST_TERMINAL_NAME);
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(lst_PrinterModify);

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
        PrinterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PrinterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            //DFKLOOK:ここから修正
            PrinterModifySCHParams inparam = new PrinterModifySCHParams(ui);
            //DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PrinterModify.clear();

            // DFKLOOK start
            
            ArrayList<String> printParams = WmsParam.PRINTER_NAME;
            ArrayList<String> pullParam = new ArrayList<String>();
            
            //　ファイルに登録されているプリンタ名をセットします。
            for(String printParam : printParams)
            {
                pullParam.add(printParam);
            }
            
            // DBに登録されているプリンタ名をセットします。
            for (Params outparam : outparams)
            {
                if(!outparam.get(PrinterModifySCHParams.PRINTER_NAME).equals(""))
                {
                    pullParam.add(outparam.getString(PrinterModifySCHParams.PRINTER_NAME));
                }
            }
            
            // 登録されているプリンタがない場合、空文字をセットします。
            if (0 == pullParam.size())
            {    
                    pullParam.add("");
            }
             
            // リストに二重に登録されているプリンタ名がないかチェックし、ソートします。
            ArrayList<String> setParam = checkSortPrinter(pullParam);
            
            // アイテム配列に変換します。
            PullDownItem[] pul = convertToArrayItem(setParam);
            // DFKLOOK end

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PrinterModify.getNewLine();
                line.setValue(KEY_LST_TERMINAL, outparam.get(PrinterModifySCHParams.TERMINAL));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(PrinterModifySCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_HDN_UPDATA_DATE, outparam.get(PrinterModifySCHParams.HDN_UPDATA_DATE));
                line.setValue(KEY_LST_PRINTER_NAME, outparam.get(PrinterModifySCHParams.PRINTER_NAME));
                lst_PrinterModify_SetLineToolTip(line);
                _lcm_lst_PrinterModify.add(line);
                //DFKLOOK start
                lst_PrinterModify.setPullDownItems(3, Arrays.asList(pul));
                int printno = retPrintIndex(outparam, setParam);
                lst_PrinterModify.setValue(PUL_COLUM_NO, setParam.get(printno));
                //DFKLOOK end
            }

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
    // DFKLOOK 引数追加
    private void btn_Set2_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set2_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrinterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PrinterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_PrinterModify.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_PrinterModify.get(i);
                
                // DFKLOOK
                if(line.getValue(KEY_LST_PRINTER_NAME).equals(""))
                {
                    message.setMsgResourceKey("6003001");
                    return;
                }
                
                
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                PrinterModifySCHParams lineparam = new PrinterModifySCHParams(ui);
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(PrinterModifySCHParams.TERMINAL, line.getValue(KEY_LST_TERMINAL));
                lineparam.set(PrinterModifySCHParams.TERMINAL_NAME, line.getValue(KEY_LST_TERMINAL_NAME));
                lineparam.set(PrinterModifySCHParams.PRINTER_NAME, line.getValue(KEY_LST_PRINTER_NAME));
                lineparam.set(PrinterModifySCHParams.HDN_UPDATA_DATE, line.getValue(KEY_LST_HDN_UPDATA_DATE));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_PrinterModify.resetEditRow();
                _lcm_lst_PrinterModify.resetHighlight();
                _lcm_lst_PrinterModify.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_PrinterModify.size(); i++)
            {
                ListCellLine line = _lcm_lst_PrinterModify.get(i);
                lst_PrinterModify.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_TERMINAL), "");
                part11List.add(line.getViewString(KEY_LST_PRINTER_NAME), "");
                // DFKLOOK start
                // 変更前:プリンタ名
                part11List.add(line.getValue(_lcm_lst_PrinterModify.getColumnKey(_lcm_lst_PrinterModify.getColumnIndex(KEY_LST_PRINTER_NAME)).getShadowKey()), "");
                // DFKLOOK end
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_PrinterModify.resetEditRow();
            _lcm_lst_PrinterModify.resetHighlight();

            // set input parameters.
            PrinterModifySCHParams inparam = new PrinterModifySCHParams(ui);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_PrinterModify.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PrinterModify.getNewLine();
                line.setValue(KEY_LST_TERMINAL, outparam.get(PrinterModifySCHParams.TERMINAL));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(PrinterModifySCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_PRINTER_NAME, outparam.get(PrinterModifySCHParams.PRINTER_NAME));
                line.setValue(KEY_LST_HDN_UPDATA_DATE, outparam.get(PrinterModifySCHParams.HDN_UPDATA_DATE));
                lst_PrinterModify_SetLineToolTip(line);
                _lcm_lst_PrinterModify.add(line);
            }

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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrinterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PrinterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PrinterModifySCHParams inparam = new PrinterModifySCHParams(ui);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PrinterModify.clear();
            
            // DFKLOOK start
            
            ArrayList<String> printParams = WmsParam.PRINTER_NAME;
            ArrayList<String> pullParam = new ArrayList<String>();
            
            //　ファイルに登録されているプリンタ名をセットします。
            for(String printParam : printParams)
            {
                pullParam.add(printParam);
            }
            
            // DBに登録されているプリンタ名をセットします。
            for (Params outparam : outparams)
            {
                if(!outparam.get(PrinterModifySCHParams.PRINTER_NAME).equals(""))
                {
                    pullParam.add(outparam.getString(PrinterModifySCHParams.PRINTER_NAME));
                }
            }
            
            // 登録されているプリンタがない場合、空文字をセットします。
            if (0 == pullParam.size())
            {    
//                    message.setMsgResourceKey("602305");
//                    btn_Set2.setEnabled(false);
//                    btn_Clear.setEnabled(false);
                    pullParam.add("");
            }
             
            // リストに二重に登録されているプリンタ名がないかチェックし、ソートします。
            ArrayList<String> setParam = checkSortPrinter(pullParam);
            
            // アイテム配列に変換します。
            PullDownItem[] pul = convertToArrayItem(setParam);
            // DFKLOOK end
         
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PrinterModify.getNewLine();
                line.setValue(KEY_LST_TERMINAL, outparam.get(PrinterModifySCHParams.TERMINAL));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(PrinterModifySCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_HDN_UPDATA_DATE, outparam.get(PrinterModifySCHParams.HDN_UPDATA_DATE));
                line.setValue(KEY_LST_PRINTER_NAME, outparam.get(PrinterModifySCHParams.PRINTER_NAME));
                lst_PrinterModify_SetLineToolTip(line);
                _lcm_lst_PrinterModify.add(line);
                //DFKLOOK start
                lst_PrinterModify.setPullDownItems(3, Arrays.asList(pul));
                int printno = retPrintIndex(outparam, setParam);
                lst_PrinterModify.setValue(PUL_COLUM_NO, setParam.get(printno));
                //DFKLOOK end

            }

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
