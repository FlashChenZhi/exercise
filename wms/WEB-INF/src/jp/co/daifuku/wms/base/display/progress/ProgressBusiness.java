// $Id: ProgressBusiness.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.progress;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.wms.base.common.WMSConstants;

/**
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class ProgressBusiness extends Progress implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void msg_Server(ActionEvent e) throws Exception
	{
	}


}
//end of class
