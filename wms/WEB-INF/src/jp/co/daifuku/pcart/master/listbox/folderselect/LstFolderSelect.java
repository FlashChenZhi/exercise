// $Id: LstFolderSelect.java 3209 2009-03-02 06:34:19Z arai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.listbox.folderselect;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * ツールが生成した不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class LstFolderSelect extends Page
{

	// Class variables -----------------------------------------------
	/** <code>lbl_ListName</code> */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");
	/** <code>message</code> */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	/** <code>lbl_Folder</code> */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Folder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Folder" , "W_Folder");
	/** <code>pul_Folder</code> */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Folder = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Folder" , "W_Folder");
	/** <code>btn_Up</code> */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Up" , "W_Up");
	/** <code>btn_Select</code> */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Select = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Select" , "W_SelectSync");
	/** <code>btn_Close</code> */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "Close");

}
//end of class
