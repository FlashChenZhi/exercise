package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 表示区分ボタンコンポーネントの作成
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
class LvDisplayConditionButton extends JPanel
{
    /**
     * 表示区分ラベル
     */
    JLabel lblDisplayConditionButton;
    
    /**
     * 表示区分ボタン（全て）
     */
    JRadioButton btnAll;
    
    /**
     * 表示区分ボタン（送信）
     */
    JRadioButton btnSend;
    
    /**
     * 表示区分ボタン（受信）
     */
    JRadioButton btnReceive;

    /**
     * ラジオボタングループ
     */
    ButtonGroup buttonGroup;
    
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

	protected final String commandAll = DispResourceFile.getText("RDB-W0051");
    protected final String commandSend = DispResourceFile.getText("RDB-W0052");
    protected final String commandRecv = DispResourceFile.getText("RDB-W0053");

    /**
     * インスタンスを生成します。
     */
    LvDisplayConditionButton()
    {
        // タイトルの設定
        lblDisplayConditionButton = new JLabel(DispResourceFile.getText("LBL-W0044"));
        lblDisplayConditionButton.setPreferredSize(LabelSize);
        lblDisplayConditionButton.setHorizontalAlignment(JLabel.RIGHT);
        lblDisplayConditionButton.setFont(LabelFont);
        
        // ラジオボタンのタイトルの設定する
        btnAll = new JRadioButton(commandAll);
        btnSend = new JRadioButton(commandSend);
        btnReceive = new JRadioButton(commandRecv);
        
        // 背景色の設定
        Color backColor = new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
        btnAll.setBackground(backColor);
        btnAll.setSelected(true);
        btnSend.setBackground(backColor);
        btnReceive.setBackground(backColor);
        btnAll.setFont(LabelFont);
        btnSend.setFont(LabelFont);
        btnReceive.setFont(LabelFont);
        lblDisplayConditionButton.setBackground(backColor);
        this.setBackground(backColor);
        
        // 各ボタンのグループ化
        buttonGroup = new ButtonGroup();
        buttonGroup.add(btnAll);
        buttonGroup.add(btnSend);
        buttonGroup.add(btnReceive);

        btnAll.setActionCommand("1");
        btnSend.setActionCommand("2");
        btnReceive.setActionCommand("3");
        
        this.setLayout(new FlowLayout());
        this.add(lblDisplayConditionButton);
        this.add(btnAll);
        this.add(btnSend);
        this.add(btnReceive);
    }
    
    /**
     * コンポーネントを初期状態にします。
     */
    public void clear()
    {
    	btnAll.setSelected(true);
    }
    
    /**
     * 選択された表示条件を取得します。
     * 
     * @return		送受信区分
     */
    public int getSendRecvDivision()
    {
    	ButtonModel bm = buttonGroup.getSelection();
    	String command = bm.getActionCommand();
    	return Integer.parseInt(command);
    }
}