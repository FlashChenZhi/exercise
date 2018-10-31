// $Id: LvButton.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 * ログビューワで使用するボタンコンポーネントです。
 * <BR>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

public class LvButton extends JButton
{
	/**
	 * ボタンの背景色
	 */
	protected final Color bgColor = new Color(255, 165, 0);

	/**
	 * ボタンラベルのフォント
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	/**
	 * ボタンサイズ
	 */
	protected final Dimension size = new Dimension(72, 26);

	/**
	 * デフォルトコンストラクタ
	 */
	public LvButton()
	{
		super();
	}

	/**
	 * 表示するラベル文字列の番号を指定するコンストラクタ
	 * 
	 * @param msgNo		DispResourceの番号
	 */
	public LvButton(String msgNo)
	{
		super();
		initialize(msgNo);
	}

	/**
	 * ボタンコンポーネントを初期化します。
	 * 
	 * @param msgNo		DispResourceの番号
	 */
	public void initialize(String msgNo)
	{
		setBackground(bgColor);
		setFont(LabelFont);
		setPreferredSize(size);
        
		// DispResourceからラベル文字列を取得する。
		String title = DispResourceFile.getText(msgNo);
		setText(title);
	}
}
