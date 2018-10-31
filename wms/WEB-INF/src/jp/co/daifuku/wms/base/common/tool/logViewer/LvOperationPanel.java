package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * 表示ボタン・クリアボタンを持つコンポーネントです。
 * 
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

class LvOperationPanel extends JPanel 
{
    /**
     * 表示ボタン
     */
    LvButton btnDisplay;
    
    /**
     * クリアボタン
     */
    LvButton btnClear;
    
    /**
     * 背景色のRGB値
     */
    final int[] rgbColor = LogViewerParam.BackColor;

	/**
	 * ボタンサイズ
	 */
	protected final Dimension size = new Dimension(72, 26);
   
    /**
     * コンストラクタ
     * 
     * @param parent	親コンポーネント
     */
    LvOperationPanel(LogViewer parent) 
    {
        // ボタン作成
        btnDisplay = new LvButton("BTN-9022");
        btnClear = new LvButton("BTN-9005");
        
        btnDisplay.addActionListener(parent);
        btnClear.addActionListener(parent);
     
        btnDisplay.setPreferredSize(size);
        btnClear.setPreferredSize(size);

        // 背景色セット
        Color backColor = new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
        this.setBackground(backColor);
        
        // ボタン貼り付け
        this.add(btnDisplay);
        this.add(btnClear);
    }

	/**
	 * クリアボタンを取得します。
	 * 
	 * @return	クリアボタン
	 */
	public LvButton getClearButton()
	{
		return btnClear;
	}

	/**
	 * 表示ボタンを取得します。
	 * 
	 * @return	表示アボタン
	 */
	public LvButton getDisplayButton()
	{
		return btnDisplay;
	}
}

    