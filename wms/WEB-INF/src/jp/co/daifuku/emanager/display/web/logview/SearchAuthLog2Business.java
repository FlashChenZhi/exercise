// $Id: SearchAuthLog2Business.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;

import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.display.web.logview.listbox.AuthLogListBusiness;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.UserListBusiness;
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
public class SearchAuthLog2Business
        extends SearchAuthLog2
        implements EmConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 各コントロールイベント呼び出し前に呼び出されます。
     * 
     * @param e  ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // ViewStateから取得
        String menuparam = this.viewState.getString(MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);

            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);

            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }


        // フォーカス設定
        setFocus(txt_LogDateRetrievalBeginning);
    }

    /**
     * ポップアップウインドから、戻ってくるときにこのメソッドが 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * 
     * @param e  ActionEvent
     * @exception Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();

        //ユーザIDを取得
        String userkey = param.getParameter(UserListBusiness.USERID_KEY);

        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(userkey))
        {
            txt_UserId.setText(userkey);
            setFocus(txt_UserId);
        }
    }

    /**
     * 画面の初期化を行います。
     * 
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // タブを選択状態に
        tab.setSelectedIndex(2);

        //Pager初期化

        // プルダウンの初期化
        pul_LogClass.clearItem();

        // プルダウンデータの作成
        PullDownItem item1 = new PullDownItem();
        PullDownItem item2 = new PullDownItem();
        PullDownItem item3 = new PullDownItem();
        PullDownItem item4 = new PullDownItem();
        PullDownItem item5 = new PullDownItem();
        PullDownItem item6 = new PullDownItem();

        // ログイン
        item1.setValue("1");
        item1.setResourceKey("PUL-T0002");
        item1.setSelected(true);

        // ログアウト
        item2.setValue("2");
        item2.setResourceKey("PUL-T0003");
        item2.setSelected(false);

        // タイムアウト
        item3.setValue("3");
        item3.setResourceKey("PUL-T0004");
        item3.setSelected(false);

        // 認証エラー
        item4.setValue("4");
        item4.setResourceKey("PUL-T0005");
        item4.setSelected(false);

        // ユーザロック
        item5.setValue("5");
        item5.setResourceKey("PUL-T0006");
        item5.setSelected(false);

        // ユーザロック
        // 全て
        item6.setValue("6");
        item6.setResourceKey("PUL-T0012");
        item6.setSelected(false);

        // PullDownItemの作成
        pul_LogClass.addItem(item1);
        pul_LogClass.addItem(item2);
        pul_LogClass.addItem(item3);
        pul_LogClass.addItem(item4);
        pul_LogClass.addItem(item5);
        pul_LogClass.addItem(item6);

        // テキストボックスの初期化
        txt_LogDateRetrievalBeginning.setText("");
        txt_LogTimeRetrievalBeginn.setText("");
        txt_LogDateRetrievalEnd.setText("");
        txt_LogTimeRetrievalEnd.setText("");
        txt_UserId.setText("");

        // コントロールの非活性化
        txt_LogDateRetrievalBeginning.setReadOnly(false);
        txt_LogTimeRetrievalBeginn.setReadOnly(false);
        txt_LogDateRetrievalEnd.setReadOnly(false);
        txt_LogTimeRetrievalEnd.setReadOnly(false);
        pul_LogClass.setEnabled(true);
        txt_UserId.setReadOnly(false);
        btn_SearchUserId.setEnabled(true);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------


    /** 
     * 「戻る」ボタンをクリックしたときに呼び出されます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        //1画面目に遷移
        forward("/emanager/logview/SearchAuthLog1.do");
    }

    /** 
     * 「メニューへ」をクリックしたときに呼び出されます。
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // ViewStateから取得
        String menuparam = this.viewState.getString(MENUPARAM);

        //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
        String menuID = CollectionUtils.getMenuParam(2, menuparam);

        // メニューのIDを設定
        forward(BusinessClassHelper.getSubMenuPath(menuID));
    }

    /** 
     * ユーザIDの「検索」ボタンがクリックされたときに呼び出されます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_SearchUserId_Click(ActionEvent e)
            throws Exception
    {
        //ユーザ一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(UserListBusiness.USERID_KEY, txt_UserId.getText());

        //ViewStateよりメニュータイプを取得
        String menutype = this.getViewState().getString("MENUTYPE");
        param.setParameter(UserListBusiness.MENUTYPE_KEY, menutype);

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/UserList.do", param, "/Progress.do");
    }

    /** 
     *  「検索」ボタンがクリックされたときに呼び出されます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_SearchLog_Click(ActionEvent e)
            throws Exception
    {
        //ユーザ一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        //param.setParameter(AuthLogListBusiness.LOGDATESTART,txt_LogDateRetrievalBeginning.getText());
        param.setParameter(AuthLogListBusiness.LOGDATESTARTTIME, txt_LogTimeRetrievalBeginn.getText());
        //param.setParameter(AuthLogListBusiness.LOGDATEEND,txt_LogDateRetrievalEnd.getText());
        param.setParameter(AuthLogListBusiness.LOGDATEENDTIME, txt_LogTimeRetrievalEnd.getText());
        param.setParameter(AuthLogListBusiness.LOGCLASS, pul_LogClass.getSelectedValue());
        param.setParameter(AuthLogListBusiness.USERID, txt_UserId.getText());


        this.getSession().setAttribute(AuthLogListBusiness.LOGDATESTART, txt_LogDateRetrievalBeginning.getDate());
        this.getSession().setAttribute(AuthLogListBusiness.LOGDATEEND, txt_LogDateRetrievalEnd.getDate());

        //処理中画面->結果画面
        redirect("/emanager/logview/listbox/AuthLogList.do", param, "/Progress.do");
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        this.page_Load(e);
    }

}
//end of class
