package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * ホスト名入力用コンポーネント
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/09</TD><TD>tsukoi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

public class LvHostname extends LvRftNo
{
	/**
     * ホスト名の最大桁数
     */
    static final int MaxLength = 16;

    /**
     * インスタンスを生成します。
     */
    LvHostname() 
    {
    	this(true);
    }

    /**
     * インスタンスを生成します。
     */
    LvHostname(boolean enabled, String text) 
    {
    	this(enabled);
    	txtRftNo.setText(text);
    }

    /**
     * インスタンスを生成します。
     * @param enabled 入力可／入力不可
     */
    LvHostname(boolean enabled) 
    {
    	super(enabled);
    	
        lblRftNo.setText(DispResourceFile.getText("LBL-W0187"));
        txtRftNo.setColumns(MaxLength);
    }
    
    /**
     * 入力値をクリアします。
     */
    public void clear()
    {
    	txtRftNo.setText("");
    }

    /**
     * 入力されたRFT号機Noを取得します。
     * 3桁未満の場合は前に0を必要な数だけ埋めてから返します。
     * @return	RFT号機No
     */
    public String getRftNo()
    {
		return txtRftNo.getText();
    }
}
