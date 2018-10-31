// $Id: WMSConstants.java 2512 2009-01-06 02:18:46Z kishimoto $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.common;

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.util.ControlColor;

/**
 * WMS用の定数定義インターフェイスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 2512 $, $Date: 2009-01-06 11:18:46 +0900 (火, 06 1 2009) $
 * @author  $Author: kishimoto $
 */
public interface WMSConstants
        extends Constants
{

    // Class fields --------------------------------------------------

    /**
     * WMSで使用するデータソース名
     */
    public static final String DATASOURCE_NAME = "wms";

    /**
     * RFTで使用するデータソース名
     */
    public static final String DATASOURCE_RFT_NAME = "rft";

    /**
     * WMSで使用するスケジュールパラメータに渡す日付フォーマット
     */
    public static final String PARAM_DATE_FORMAT = "yyyyMMdd";

    /**
     * 印刷画面でCSV出力を指定するためのキーワード
     */
    public static final int CSV = 1;

    /**
     * 印刷画面でXLS出力を指定するためのキーワード
     */
    public static final int XLS = 2;
    
    /**
     * リストセルの警告行の色（標準は黄色）
     */
    public static final jp.co.daifuku.bluedog.util.ControlColorSupport LISTCELL_WARN_COLOR = ControlColor.Yellow;

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
