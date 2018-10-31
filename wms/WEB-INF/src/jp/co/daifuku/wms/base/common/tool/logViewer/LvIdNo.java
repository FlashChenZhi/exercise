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
 * IDNO入力用コンポーネント作成
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

public class LvIdNo extends JPanel
{

    /**
     * ラベル
     */
    JLabel lblIdNo;
    
    /**
     * IDNO入力エリア１
     */
    JTextField txtIdNo1;
    
    /**
     * IDNO入力エリア２
     */
    JTextField txtIdNo2;
    
    /**
     * パネルサイズ
     */
    final Dimension PanelSize = new Dimension(100, 26);
    
    /**
     * ラベルサイズ
     */
    final Dimension LabelSize = new Dimension(100, 26);

	/**
	 * ラベルのフォント
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	/**
     * IDの最大桁数
     */
    static final int MaxLength = LogViewerParam.IdFigure;
    /**
     * インスタンスを生成します。
     */
    LvIdNo() 
    {
        // パネルの定義
        super(new FlowLayout());
        
        // ラベル･入力領域コンポーネント作成
        lblIdNo = new JLabel(DispResourceFile.getText("LBL-W0001"));
        lblIdNo.setPreferredSize(LabelSize);
        lblIdNo.setHorizontalAlignment(JLabel.RIGHT);
        lblIdNo.setFont(LabelFont);
        
        // ID入力欄の定義
        txtIdNo1 = new LvTextField(MaxLength);
        txtIdNo2 = new LvTextField(MaxLength);
        
        //背景色の設定
        int[] rgbValue = LogViewerParam.BackColor;
        Color backColor = new Color(rgbValue[0], rgbValue[1], rgbValue[2]);
        lblIdNo.setBackground(backColor);
		this.setBackground(backColor);

		// パネルサイズの設定
		this.setSize(PanelSize);

        // パネルにラベル・入力エリアを追加
        this.add(lblIdNo);
        this.add(txtIdNo1);
        this.add(txtIdNo2);
    }
    
    /**
     * 入力値をクリアします。
     */
    public void clear()
    {
        txtIdNo1.setText("");
        txtIdNo2.setText("");
    }
    
    /**
     * ID番号1を取得します。
     * @return		ID番号1
     */
    public String getIdNo1()
    {
    	return txtIdNo1.getText();
    }

    /**
     * ID番号2を取得します。
     * @return		ID番号2
     */
    public String getIdNo2()
    {
    	return txtIdNo2.getText();
    }
}
