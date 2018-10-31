// $Id: PDFOutBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.pdfout;

import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.wms.base.common.ListBoxDefine;

/** 
 * ツールが生成した画面クラスです。 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class PDFOutBusiness
        extends PDFOut
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 画面の初期化を行います。
     * @param e ActionEvent イベントの情報 
     * @throws Exception 全ての例外を報告します。
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // ForwardParameter から PDFファイルのパスと、ウィドウタイトルの DispResourceKey を取得する
        String path = this.request.getParameter(ListBoxDefine.PREVIEW_FILEPATH_KEY);
        String windowTitleKey = this.request.getParameter(ListBoxDefine.PREVIEW_TITLE_KEY);

        // ウィンドウのタイトルと、PDFファイルのパスをセット
        this.getHttpRequest().setAttribute("PreviewPath", path);
        this.getHttpRequest().setAttribute("WindowTitleKey", windowTitleKey);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

}
//end of class
