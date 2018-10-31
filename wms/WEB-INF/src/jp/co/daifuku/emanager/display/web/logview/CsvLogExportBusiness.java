// $Id: CsvLogExportBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.util.CsvLogUtil;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.P11LogWriter;
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
public class CsvLogExportBusiness
        extends CsvLogExport
        implements EmConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            //<jp>ViewStateへ保持する</jp><en> It holds to ViewState. </en>
            this.getViewState().setString(M_MENUID_KEY, menuID);

            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);

            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }

    }


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
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

    /** 
     * 設定ボタンが押下されたときに呼ばれます。 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        // どちらのチェックボックスにもチェックがついていない場合
        if (chk_AuthLog.getChecked() == false && chk_MainteLog.getChecked() == false)
        {
            // チェックボックスにチェックをつけてください。
            message.setMsgResourceKey("6403047");
            return;
        }

        // 出力したデータがあるかどうか
        boolean hasExportData = false;

        // 認証ログ出力フラグ
        boolean authLogFlag = false;

        try
        {
            // 認証ログエクスポート
            if (chk_AuthLog.getChecked())
            {
                // CSV出力
                String dateFormat = EManagerUtil.getDateFormat_NoSlah(this.httpRequest.getLocale());
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                String value = formatter.format(date);
                String fileName = CsvLogUtil.AUTH_LOG_TABLE + "_" + value + ".csv";

                CsvLogUtil.exportCsv(CsvLogUtil.AUTH_LOG_TABLE, fileName);

                // 出力しました。
                message.setMsgResourceKey("6401013");

                // 出力フラグを更新
                authLogFlag = true;
                // 出力データ有り
                hasExportData = true;
            }
        }
        catch (Exception ex)
        {
            if (ex.getMessage() != null)
            {
                // 出力するデータが存在しませんでした。
                message.setMsgResourceKey(ex.getMessage());
            }
        }

        try
        {
            // メンテナンスログエクスポート
            if (chk_MainteLog.getChecked())
            {
                // CSV出力
                String dateFormat = EManagerUtil.getDateFormat_NoSlah(this.httpRequest.getLocale());
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                String value = formatter.format(date);
                String fileName = CsvLogUtil.MAINTENANCE_LOG_TABLE + "_" + value + ".csv";


                CsvLogUtil.exportCsv(CsvLogUtil.MAINTENANCE_LOG_TABLE, fileName);

                // 出力しました。
                message.setMsgResourceKey("6401013");
                // 出力データ有り
                hasExportData = true;
            }
        }
        catch (Exception ex)
        {
            if (ex.getMessage() != null && authLogFlag == false)
            {
                // 出力するデータが存在しませんでした。
                message.setMsgResourceKey(ex.getMessage());
            }
        }

        // 出力データがあった場合のみログ出力する
        if (hasExportData)
        {
            Connection conn = null;
            try
            {
                conn = EmConnectionHandler.getPageDbConnection(this);

                P11LogWriter writer = new P11LogWriter(conn);

                List itemList = new ArrayList();

                // 認証ログ
                itemList.add(chk_AuthLog.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                     : String.valueOf(EmConstants.DB_FLAG_FALSE));
                // メンテナンスログ
                itemList.add(chk_MainteLog.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                       : String.valueOf(EmConstants.DB_FLAG_FALSE));

                writer.createOperationLog((DfkUserInfo)getUserInfo(), EmConstants.OPELOG_CLASS_SETTING, itemList);

                EmConnectionHandler.commit(conn);
            }
            catch (Exception ex)
            {
                EmConnectionHandler.rollback(conn);
                throw ex;
            }
            finally
            {
                EmConnectionHandler.closeConnection(conn);
            }
        }
    }

    /** 
     * 「クリア」をクリックしたときに呼び出されます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        chk_AuthLog.setChecked(false);
        chk_MainteLog.setChecked(false);
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
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
    }
}
//end of class
