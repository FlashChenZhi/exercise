//$Id: LvDateTimePanel.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
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

/**
 * ログビューワで使用する処理日時表示ラベル、テキストフィールドのパネルコンポーネントです。
 */
public class LvDateTimePanel extends JPanel
{
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $";
	}

	/**
	 * 設定ファイルから背景色を取得
	 */
    protected final int[] formBackColor = LogViewerParam.BackColor;

    /**
     * テキストフィールド背景色のRGB値
     */
	protected final Color textColor = new Color(255, 255, 255);

    /**
     * ラベルサイズ
     */
    final Dimension LabelSize = new Dimension(100, 26);

	/**
	 * ボタンラベルのフォント
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	/**
	 * デフォルトコンストラクタ
	 */
	public LvDateTimePanel()
	{
	    super(new FlowLayout());
	}

	/**
	 * 表示するラベル文字列の番号を指定するコンストラクタ
	 * 
	 * @param msgNo		DispResourceの番号
	 * @param date 		処理日時
	 */
	public LvDateTimePanel(String msgNo, String date)
	{
		super();
		initialize(msgNo, date);
	}

	/**
	 * 表示するラベル文字列の番号を指定します。
	 * 
	 * @param msgNo			DispResourceの番号
	 * @param processDate 	処理日時
	 */
	public void initialize (String msgNo, String processDate) 
    {
	    // 処理日時ラベル
	    JLabel lblDate;

	    // 処理日時表示テキストフィールド
	    JTextField txtDate;

	    // パネル構成
	    String title = DispResourceFile.getText(msgNo);
        lblDate = new JLabel(title);				// 処理日時ラベル
        txtDate = new JTextField(processDate);		// 処理日時表示テキストフィールド

        lblDate.setPreferredSize(LabelSize);
        lblDate.setHorizontalAlignment(JLabel.RIGHT);
        lblDate.setFont(LabelFont);

        // パネル内設定
        txtDate.setEditable(false);					// 入力不可設定
        txtDate.setBackground(textColor);			// 日時処理テキストフィールド背景色
        Color backColor = new Color(formBackColor[0], formBackColor[1], formBackColor[2]);
        lblDate.setBackground(backColor);
        this.setBackground(backColor);				// パネル背景色を指定
        this.setSize(100,100);        				// パネルサイズを指定

        // RFT入力ラベル・RFT入力欄を追加
        this.add(lblDate);
        this.add(txtDate);
    }
}

