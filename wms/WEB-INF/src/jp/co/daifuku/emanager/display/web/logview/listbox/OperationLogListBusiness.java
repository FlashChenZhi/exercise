// $Id: OperationLogListBusiness.java 7466 2010-03-08 07:58:31Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview.listbox;

import java.util.List;

import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.display.web.logview.OperationLogBusiness;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.ui.web.ToolTipHelper;
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
 * @version $Revision: 7466 $, $Date: 2010-03-08 16:58:31 +0900 (月, 08 3 2010) $
 * @author  $Author: shibamoto $
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
 * @version $Revision: 7466 $, $Date: 2010-03-08 16:58:31 +0900 (月, 08 3 2010) $
 * @author  $Author: shibamoto $
 </en> */
public class OperationLogListBusiness
        extends OperationLogList
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
        lbl_ListName.setResourceKey("TLE-T0134");
        lst_SearchConditionTwoColumn.clearRow();
        lst_SearchConditionTwoColumn.setVisible(true);
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(1);
        // 開始入庫日
        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0275"));
        lst_SearchConditionTwoColumn.setValue(2, request.getParameter(OperationLogBusiness.START_DATE));

        lst_SearchConditionTwoColumn.setValue(3, DispResourceMap.getText("LBL-T0139"));
        lst_SearchConditionTwoColumn.setValue(4, request.getParameter(OperationLogBusiness.OPERATION_TYPE));

        // set user information
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(2);

        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0093"));
        lst_SearchConditionTwoColumn.setValue(2, request.getParameter(OperationLogBusiness.USER_ID));
        lst_SearchConditionTwoColumn.setValue(3, DispResourceMap.getText("LBL-T0140"));
        lst_SearchConditionTwoColumn.setValue(4, request.getParameter(OperationLogBusiness.USER_NAME));

        //DS Number and Screen Name
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(3);

        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0131"));
        lst_SearchConditionTwoColumn.setValue(2, request.getParameter(OperationLogBusiness.DS_NO));

        lst_SearchConditionTwoColumn.setValue(3, DispResourceMap.getText("LBL-T0142"));
        lst_SearchConditionTwoColumn.setValue(4, request.getParameter(OperationLogBusiness.TITLE));

        //Terminal Information
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(4);

        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0065"));
        lst_SearchConditionTwoColumn.setValue(2, request.getParameter(OperationLogBusiness.IPADDRESS));

        lst_SearchConditionTwoColumn.setValue(3, DispResourceMap.getText("LBL-T0141"));
        lst_SearchConditionTwoColumn.setValue(4, request.getParameter(OperationLogBusiness.TERMINAL_NAME));

        String itemData = request.getParameter(OperationLogBusiness.ITEM_DATA);
        List itemList = CollectionUtils.getList(itemData);

        if (itemList == null || itemList.size() <= 0)
        {
            message.setMsgResourceKey("6403077");
            return;
        }
        int no = 1;
        lst_ScreenControlLOGDetails.clearRow();
        for (int i = 0; i < itemList.size(); i++)
        {
            lst_ScreenControlLOGDetails.addRow();
            lst_ScreenControlLOGDetails.setCurrentRow(lst_ScreenControlLOGDetails.getMaxRows() - 1);

            lst_ScreenControlLOGDetails.setValue(1, Integer.toString(no++));
            lst_ScreenControlLOGDetails.setValue(2, (String)itemList.get(i));

            // ToolTip設定
            ToolTipHelper toolTip = new ToolTipHelper();
            // LBL-T0162=内容
            toolTip.add(DispResourceMap.getText("LBL-T0162"), (String)itemList.get(i));
            lst_ScreenControlLOGDetails.setToolTip(i + 1, toolTip.getText());
        }
        message.setMsgResourceKey("6401017");

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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        this.parentRedirect(null);
    }


}
//end of class
