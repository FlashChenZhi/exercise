// $Id: TerminalChangeListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user.listbox;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.display.web.setting.user.TerminalBusiness;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.ui.web.ToolTipHelper;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
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
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class TerminalChangeListBusiness
        extends TerminalChangeList
{
    // Class fields --------------------------------------------------
    /** 切替元の端末Noを保持するキー */
    public static final String PARENT_TERMINAL_NO = "PARENT_TERMINAL_NO";

    /** 選択された端末No.のリストを保持するキー */
    public static final String SELECTED_TERMINAL_NO = "SELECTED_TERMINAL_NO";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        //画面名称をセットする
        this.lbl_ListName.setResourceKey("TLE-T0016");

        Connection conn = null;

        // 親画面で選択されている端末No.の配列をCSVに変換して取得
        String selectedTerminalNoList =
                EManagerUtil.getCsvFromStringArray(this.request.getParameterValues(SELECTED_TERMINAL_NO));
        // ViewState に保存
        this.viewState.setString(SELECTED_TERMINAL_NO, selectedTerminalNoList);

        // 親画面の端末No.を取得
        String parentTerminalNo = this.request.getParameter(PARENT_TERMINAL_NO);
        // ViewState に保存
        this.viewState.setString(PARENT_TERMINAL_NO, parentTerminalNo);


        try
        {
            // コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            // TerminalHandler取得
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            int total = terminalHandler.finCountNotTerminal(parentTerminalNo);
            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));
            //最初のページの表示終了位置
            int end = 0;
            //データがある場合
            if (total > 0)
            {
                if (total <= page)
                {
                    end = total;
                }
                else
                {
                    end = page;
                }

                //リストデータをセット
                setList(terminalHandler, 0, end);
                //pagerに値をセット
                setPagerValue(1, total, page);
            }
            else
            {
                //Pagerへの値セット
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置               
                //ヘッダーを隠します
                lst_TerminalChange.setVisible(false);

                //対象データはありませんでした
                message.setMsgResourceKey("6403077");

            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * リストセルに値をセットします
     * @param terminalHandler TerminalHandler
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(TerminalHandler terminalHandler, int next_index, int next_end)
            throws Exception
    {

        // 親画面で選択されている端末No.の配列を取得
        List selectedTerminalNoList =
                Arrays.asList(EManagerUtil.getStringArrayFromCsv(this.viewState.getString(SELECTED_TERMINAL_NO)));

        // 親画面の端末No.を取得
        String parentTerminalNo = this.viewState.getString(PARENT_TERMINAL_NO);

        // 端末の一覧取得
        Terminal[] terminalArr = terminalHandler.findAllNotTerminal(parentTerminalNo, next_index + 1, next_end);

        // 親画面以外の端末を格納するリスト
        //      ArrayList terminalList = new ArrayList();

        // 取得した端末がなければメッセージを表示して終了
        if (terminalArr == null || terminalArr.length == 0)
        {
            this.lst_TerminalChange.setVisible(false);
            this.lbl_ListName.setResourceKey("MSG-T0010");
            return;
        }

        // 親画面のターミナルナンバー以外の端末をリストに登録
        //      for(int i = 0; terminalArr != null && i < terminalArr.length; i++)
        //      {
        //          // 親画面の端末はリストに登録しない
        //          if(!terminalArr[i].getTerminalNumber().equals(parentTerminalNo))
        //          {
        //              terminalList.add(terminalArr[i]);
        //          }
        //      }

        // 端末一覧から親画面の端末を除外した結果、端末が全てなくなればメッセージを表示して終了
        if (terminalArr == null || terminalArr.length == 0)
        {
            this.lst_TerminalChange.setVisible(false);
            this.lbl_ListName.setResourceKey("MSG-T0010");
            return;
        }

        //Tipで使用する銘板
        String title_TerminalName = DispResources.getText("LBL-T0041");
        String title_IpAddress = DispResources.getText("LBL-T0042");
        String title_PrinterName = DispResources.getText("LBL-T0043");

        // リストをクリア
        this.lst_TerminalChange.clearRow();

        //      Iterator itr = terminalList.iterator();
        for (int i = 0; i < terminalArr.length; i++)
        //      while(itr.hasNext())
        {
            //          jp.co.daifuku.emanager.database.entity.Terminal terminal = (jp.co.daifuku.emanager.database.entity.Terminal)itr.next();

            int count = this.lst_TerminalChange.getMaxRows();

            this.lst_TerminalChange.setCurrentRow(count);
            this.lst_TerminalChange.addRow();

            // 各項目をセット
            this.lst_TerminalChange.setChecked(1, selectedTerminalNoList.contains(terminalArr[i].getTerminalNumber()));
            this.lst_TerminalChange.setValue(2, terminalArr[i].getTerminalNumber());
            this.lst_TerminalChange.setValue(3, terminalArr[i].getTerminalName());
            this.lst_TerminalChange.setValue(4, terminalArr[i].getTerminalAddress());
            this.lst_TerminalChange.setValue(5, terminalArr[i].getRoleId());
            this.lst_TerminalChange.setValue(6, terminalArr[i].getPrinterName());

            //ToolTipをセット
            ToolTipHelper toolTip = new ToolTipHelper();
            toolTip.add(title_TerminalName, terminalArr[i].getTerminalName());
            toolTip.add(title_IpAddress, terminalArr[i].getTerminalAddress());
            toolTip.add(title_PrinterName, terminalArr[i].getPrinterName());
            lst_TerminalChange.setToolTip(count, toolTip.getText());
        }
    }

    /** 
     * ページャーに値をセットします
     * @param index int
     * @param total int
     * @param page int
     */
    private void setPagerValue(int index, int total, int page)
    {
        pager.setIndex(index);
        pager.setMax(total);
        pager.setPage(page);
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

    /** 
     * 設定ボタン押下時
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            // リストセルの行数取得
            int numOfRow = this.lst_TerminalChange.getMaxRows();

            // チェックが入っている端末の端末No.を格納する
            ArrayList list = new ArrayList();

            ForwardParameters param = new ForwardParameters();

            if (1 < numOfRow)
            {
                for (int i = 1; i <= numOfRow - 1; i++)
                {
                    this.lst_TerminalChange.setCurrentRow(i);
                    if (this.lst_TerminalChange.getChecked(1))
                    {
                        // リストセルでチェックが入っている行の端末No.をリストに格納
                        list.add(this.lst_TerminalChange.getValue(2));
                    }
                }
                // リストをString[]に変換
                String[] selectedTerminalNum = (String[])list.toArray(new String[0]);
                // String[]を登録
                param.setParameter(SELECTED_TERMINAL_NO, selectedTerminalNum);
            }
            param.setParameter(TerminalBusiness.CHILD_SCREEN, TerminalBusiness.TERMINAL_CHANGE_MAP);
            parentRedirect(param);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

    }

    /** 
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        //呼び出し元の画面へ遷移します
        parentRedirect(null);
    }


    /** 
     * Pagerの次へボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        //Handlerにセッションの値をセット
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();
            int index = pager.getIndex();
            int next_index = 0;
            int next_end = 0;

            if (index + page * 2 <= total)
            {
                next_index = index + page - 1;
                next_end = index + page * 2 - 1;
            }
            else
            {
                next_index = index + page - 1;
                next_end = total;
            }
            //Pagerに値をセット
            setPagerValue(next_index + 1, total, page);

            //リストデータをセット
            setList(terminalHandler, next_index, next_end);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }


    }

    /** 
     * Pagerの前へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();
            int index = pager.getIndex();
            int next_index = 0;
            int next_end = 0;

            if (index - page <= 0)
            {
                next_index = 0;
                next_end = page;
            }
            else
            {
                next_index = index - page - 1;
                next_end = index - 1;
            }

            //Pagerに値をセット
            setPagerValue(next_index + 1, total, page);

            //リストデータをセット
            setList(terminalHandler, next_index, next_end);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * Pagerの最後ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();

            int next_index = 0;
            int next_end = 0;
            if (total % page == 0)
            {
                next_index = total - page;
                next_end = total;
            }
            else
            {
                next_index = total - (total % page);
                next_end = total;
            }

            //Pagerに値をセット
            setPagerValue(next_index + 1, total, page);

            //リストデータをセット
            setList(terminalHandler, next_index, next_end);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * Pagerの最初ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_First(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();

            //Pagerに値をセット
            setPagerValue(1, total, page);

            //リストデータをセット
            setList(terminalHandler, 0, page);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }
}
//end of class
