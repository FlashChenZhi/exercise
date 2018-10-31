// $Id: DispatchBusiness.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.control;

import jp.co.daifuku.bluedog.webapp.ActionEvent;

/** 
 * <jp>Dispatchを行うビジネスクラスです。<br></jp>
 * <en>It is business class by doing Dispatch.<br></en>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class DispatchBusiness
        extends Dispatch
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * <jp>画面の初期化を行います。<br></jp>
     * <en>The screen is initialized.<br></en>
     * @param e ActionEvent
     * @throws Exception 
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        this.forward(request.getParameter("PATH"));
    }


    /** 
     * <jp>ログインチェックをオーバライドします。<br></jp>
     * <en>The login check is Orbaraided. <br></en>
     * @param e ActionEvent
     * @throws Exception 
     */
    public void page_LoginCheck(ActionEvent e)
            throws Exception
    {
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------
}
//end of class
