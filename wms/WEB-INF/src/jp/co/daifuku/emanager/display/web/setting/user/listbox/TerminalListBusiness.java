// $Id: TerminalListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user.listbox;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.ui.web.ToolTipHelper;

/** <jp>
 * 端末一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class TerminalListBusiness
        extends TerminalList
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /** 
     * 端末№の受け渡しに使用するキーです
     */
    public static final String TERMINALNUMBER_KEY = "TERMINALNUMBER_KEY";

    /** 
     * 端末名の受け渡しに使用するキーです
     */
    public static final String TERMINALNAME_KEY = "TERMINALNAME_KEY";

    /** 
     * 端末IPアドレスの受け渡しに使用するキーです
     */
    public static final String IPADDRESS_KEY = "IPADDRESS_KEY";

    /** 
     * ロールIDの受け渡しに使用するキーです
     */
    public static final String TERMINAL_ROLEID_KEY = "TERMINAL_ROLEID_KEY";

    /** 
     * プリンタ名の受け渡しに使用するキーです
     */
    public static final String PRINTERNAME_KEY = "PRINTERNAME_KEY";

    /** 
     * 親画面のメニュータイプの受け渡しに使用するキーです
     */
    public static final String MENUTYPE_KEY = "MENUTYPE_KEY";


    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        //画面名をセットする
        lbl_ListName.setText(DispResources.getText("TLE-T0017"));

        Connection conn = null;

        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            //呼び出し元画面でセットされたパラメータの取得
            String[] terminalnumber = new String[1];
            terminalnumber[0] = this.request.getParameter(TERMINALNUMBER_KEY);
            //ViewStateに保存
            this.getViewState().setString("TerminalNumber", terminalnumber[0]);

            // 呼び出し元画面メニュータイプの取得
            String menutype = this.request.getParameter(MENUTYPE_KEY);
            //ViewStateに保存
            this.getViewState().setString("MenuType", menutype);

            int total = 0;
            //Terminal表の検索条件
            String[] terminal = new String[2];
            //システム定義の端末
            terminal[0] = EmConstants.UNDEFINED_TERMINAL;

            //端末№テキストボックスの値によって場合分け
            if (terminalnumber[0].equals(""))
            {
                //データ数合計
                total = terminalHandler.findCount();
            }
            else if (terminalnumber[0].indexOf("*") > -1)
            {
                //「*」を「%」に置換
                terminal[1] = terminalnumber[0].replace('*', '%');
                //データ数合計
                total = terminalHandler.finCountLike(terminalnumber[0].replace('*', '%'));
            }
            else
            {
                //端末№をセット
                terminal[1] = terminalnumber[0];
                //データ数合計
                total = terminalHandler.findCountGreaterThan(terminal[1]);
            }

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
                lst_TerminalList.setVisible(false);

                //対象データはありませんでした
                message.setMsgResourceKey("6403077");

            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

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
        //呼び出し元画面でセットされたパラメータの取得
        String[] terminalnumber = new String[1];
        //ViewStateから端末№を取得
        terminalnumber[0] = this.getViewState().getString("TerminalNumber");

        List list = null;
        //Terminal表の検索条件
        String[] terminal = new String[2];
        //システム定義の端末
        terminal[0] = EmConstants.UNDEFINED_TERMINAL;

        jp.co.daifuku.emanager.database.entity.Terminal[] terminalArr = null;

        //端末№テキストボックスの値によって場合分け
        if (terminalnumber[0].equals(""))
        {
            //データベースからリストデータを取得
            terminalArr = terminalHandler.findTerminalLike("%", next_index + 1, next_end);
        }
        else if (terminalnumber[0].indexOf("*") > -1)
        {
            //「*」を「%」に置換
            terminal[1] = terminalnumber[0].replace('*', '%');
            //データベースからリストデータを取得
            terminalArr = terminalHandler.findTerminalLike(terminal[1], next_index + 1, next_end);
        }
        else
        {
            //端末№をセット
            terminal[1] = terminalnumber[0];
            //データベースからリストデータを取得
            terminalArr = terminalHandler.findTerminalGreatherThan(terminal[1], next_index + 1, next_end);
        }

        if (terminalArr == null)
        {
            //Pagerへの値セット
            pager.setMax(0); //最大件数
            pager.setPage(0); //1Page表示件数
            pager.setIndex(0); //開始位置              
            //ヘッダーを隠します
            lst_TerminalList.setVisible(false);

            //対象データはありませんでした
            message.setMsgResourceKey("6403077");
            return;
        }

        list = new ArrayList();

        for (int i = 0; i < terminalArr.length; i++)
        {
            list.add(terminalArr[i]);
        }

        //表を削除
        lst_TerminalList.clearRow();

        //Tipで使用する銘板
        String title_TerminalName = DispResources.getText("LBL-T0041");
        String title_IpAddress = DispResources.getText("LBL-T0042");
        String title_PrinterName = DispResources.getText("LBL-T0043");

        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            jp.co.daifuku.emanager.database.entity.Terminal terminalEntity =
                    (jp.co.daifuku.emanager.database.entity.Terminal)itr.next();

            //最終行を取得
            int count = lst_TerminalList.getMaxRows();
            //行追加
            lst_TerminalList.setCurrentRow(count);
            lst_TerminalList.addRow();

            //表にデータをセット
            lst_TerminalList.setValue(1, Integer.toString(count + next_index));
            lst_TerminalList.setValue(2, terminalEntity.getTerminalNumber());
            lst_TerminalList.setValue(3, terminalEntity.getTerminalName());
            lst_TerminalList.setValue(4, terminalEntity.getTerminalAddress());
            lst_TerminalList.setValue(5, terminalEntity.getRoleId());
            lst_TerminalList.setValue(6, terminalEntity.getPrinterName());

            //端末名のNull処理
            String terminalname = terminalEntity.getTerminalName();
            if (terminalname == null)
            {
                terminalname = "";
            }
            //端末IPのNull処理
            String ipaddress = terminalEntity.getTerminalAddress();
            if (ipaddress == null)
            {
                ipaddress = "";
            }
            //プリンタ名のNull処理
            String printername = terminalEntity.getPrinterName();
            if (printername == null)
            {
                printername = "";
            }

            //ToolTipをセット
            ToolTipHelper toolTip = new ToolTipHelper();
            toolTip.add(title_TerminalName, terminalname);
            toolTip.add(title_IpAddress, ipaddress);
            toolTip.add(title_PrinterName, printername);
            lst_TerminalList.setToolTip(count, toolTip.getText());
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

    // Event handler methods -----------------------------------------
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


    /** 
     * リストが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_TerminalList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_TerminalList.setCurrentRow(lst_TerminalList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(TERMINALNUMBER_KEY, lst_TerminalList.getValue(2));
        param.setParameter(TERMINALNAME_KEY, lst_TerminalList.getValue(3));
        param.setParameter(IPADDRESS_KEY, lst_TerminalList.getValue(4));
        param.setParameter(TERMINAL_ROLEID_KEY, lst_TerminalList.getValue(5));
        param.setParameter(PRINTERNAME_KEY, lst_TerminalList.getValue(6));

        //終了処理
        btn_Close_Click(null);

        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

}
//end of class
