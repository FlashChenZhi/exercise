package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jp.co.daifuku.common.text.StringUtil;


/**
 * RFT号機No入力用コンポーネント
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

public class LvRftNo extends JPanel
{
    /**
     * RFT号機NOラベル
     */
    JLabel lblRftNo;

    /**
     * RFT号機NO入力欄
     */
    JTextField txtRftNo;
    
	/**
	 * テキストフィールド内の文字文字色
	 */
	protected final Color fontColor = new Color (000, 000, 000);

    /**
     * 背景色のRGB値
     */
    int[] backColor = LogViewerParam.BackColor;
    
    /**
     * パネルサイズ
     */
    final Dimension PanelSize = new Dimension(100, 100);
    
    /**
     * ラベルサイズ
     */
    final Dimension LabelSize = new Dimension(100, 26);

	/**
	 * ラベルのフォント
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	/**
     * RFT号機番号の最大桁数
     */
    static final int MaxLength = 3;

    /**
     * インスタンスを生成します。
     */
    LvRftNo() 
    {
    	this(true);
    }

    /**
     * インスタンスを生成します。
     * @param enabled 入力可／入力不可
     * @param text RFT号機番号
     */
    LvRftNo(boolean enabled, String text) 
    {
    	this(enabled);
    	txtRftNo.setText(text);
    }

    /**
     * インスタンスを生成します。
     * @param enabled 入力可／入力不可
     */
    LvRftNo(boolean enabled) 
    {
        super(new FlowLayout());
        

        // RFT号機NOラベルの定義
        lblRftNo = new JLabel(DispResourceFile.getText("LBL-W0186"));
        lblRftNo.setPreferredSize(LabelSize);
        lblRftNo.setHorizontalAlignment(JLabel.RIGHT);
        lblRftNo.setFont(LabelFont);
        
        // RFT号機NO入力欄の定義
        txtRftNo = new LvTextField(MaxLength);
        txtRftNo.setColumns(MaxLength + 1);
        
        //背景色をセット
        this.setBackground(new Color(backColor[0], backColor[1], backColor[2]));
		lblRftNo.setBackground(new Color(backColor[0], backColor[1], backColor[2]));
        txtRftNo.setDisabledTextColor(fontColor);
        
        // パネルサイズを指定する
        this.setSize(PanelSize);
        txtRftNo.setEnabled(enabled);				// 入力可／不可設定

        // RFT入力ラベル・RFT入力欄を追加
        this.add(lblRftNo);
        this.add(txtRftNo);
    }
    
    /**
     * 入力値をクリアします。
     */
    public void clear()
    {
    	txtRftNo.setText("");
    }

    /**
     * 入力されたRFT号機Noを取得します。<BR>
     * 3桁未満の場合は前に0を必要な数だけ埋めてから返します。
     * @return	RFT号機No
     */
    public String getRftNo()
    {
		String rftNo = txtRftNo.getText();

		if (! StringUtil.isBlank(rftNo))
		{
			// RFTNo.を数値入力するために、"000"文字列の上に入力されたRFTNo.を上書きする
			StringBuffer rftBuf = new StringBuffer("000");
			rftBuf.replace(rftBuf.length() - rftNo.length(), rftBuf.length(), rftNo);
			rftNo = rftBuf.toString();
		}

		return rftNo;
    }
}
