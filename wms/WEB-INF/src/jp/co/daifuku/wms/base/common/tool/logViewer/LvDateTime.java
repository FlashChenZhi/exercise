package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 抽出開始日時・抽出終了日時のコンポーネント作成
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

class LvDateTime extends JPanel
{
    /**
     * ラベル
     */
    JLabel lblDateTime;

    /**
     * 日付入力項目
     */
    JTextField txtDate;
    
    /**
     * 時刻入力項目
     */
    JTextField txtTime;
    
    /**
     * 背景色のRGB値
     */
    int[] rgbColor = LogViewerParam.BackColor;
    
    /**
     * ラベルサイズ
     */
    final Dimension LabelSize = new Dimension(100, 26);
    
	/**
	 * ラベルのフォント
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	/**
	 * 日付の最大桁数
	 */
	static final int MaxLengthDate = 8;
    
	/**
	 * 時刻の最大桁数
	 */
    static final int MaxLengthTime = 6;
    
    /**
     * コンストラクタ
     * 
     * @param labelString	表示するラベル文字列
     */
    LvDateTime(String labelString)
    {
        // ラベルの作成
        lblDateTime = new JLabel(labelString);
        lblDateTime.setPreferredSize(LabelSize);
        lblDateTime.setHorizontalAlignment(JLabel.RIGHT);
        lblDateTime.setFont(LabelFont);

        // 検索日付入力欄の定義
        txtDate = new LvTextField(MaxLengthDate);
        
        // 検索時間入力欄の定義
        txtTime = new LvTextField(MaxLengthTime);
        
        // 背景色の設定
        Color backColor = new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
        lblDateTime.setBackground(backColor);
        this.setBackground(backColor);
        
        // コンポーネントの追加
        this.setLayout(new FlowLayout());
        this.add(lblDateTime);
        this.add(txtDate);
        this.add(txtTime);
        
    }
    
    /**
     * 入力値をクリアします。
     */
    public void clear()
    {
        txtDate.setText("");
        txtTime.setText("");
    }
    
    /**
     * 入力された日付を取得します。
     * @return	入力された日付
     */
    public String getDate()
    {
    	return txtDate.getText();
    }

    /**
     * 入力された時刻を取得します。
     * @return	入力された時刻
     */
    public String getTime()
    {
    	return txtTime.getText();
    }
}