//$Id: DetailsWindow.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

/**
 * Designer :  <BR>
 * Maker :   <BR>
 * <BR>
 * 詳細照会画面
 * </pre>
 * @author 1.00 hota
 * @version 1.00 2006/02/02 (MTB) 新規作成
 */
public class DetailsWindow extends JFrame implements ActionListener
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
	 * インスタンスを生成します。
	 */
	public DetailsWindow()
	{
		super();
		this.setResizable(false);
	}
	
	/**
	 * ウィンドウサイズ（幅）
	 */
	final int widthSize = 600;

	/**
	 * ウィンドウサイズ（高）
	 */
	final int heightSize = 600;

	/**
	 * ヘッダータイトル
	 */
	final String WindowTitle = DispResourceFile.getText("TLE-9001");

	/**
	 * 背景色設定
	 */
    final int[] formBackColor = LogViewerParam.BackColor;		

	/**
	 * 詳細照会画面を表示する。<BR>
	 * 通信トレースログ一覧表示画面から<BR>
	 * 電文、号機No、処理日時、IDを受け取り各処理を行います。<BR>
	 * 
	 * エラーの場合このウィンドウを閉じる。<BR>
	 * 
	 * @param 	rftNo	RFT号機No
	 * @param	traceData	トレースログ情報
	 */
	public void startPopup (String rftNo, TraceData traceData)
	{
		try 
		{
			// 電文情報保持用
			IdData telegramData[] = null;
			
			// ID項目設定ファイル
			IdLayout idColumns = new IdLayout();
			
			// 電文、IDを元に表示用データを取得
			telegramData = idColumns.getIdColumns(traceData.getCommunicateMessage(), traceData.getIdNo());

			if (telegramData == null)
			{
				// データが取得できなかった場合はウィンドウを閉じる
				closeWindow();
			}
			else
			{
				// 画面に表示する内容を配置
				detailsDisp(telegramData, rftNo, traceData.getProcessDate() + " " + traceData.getProcessTime());

				show();
			}
		} 
		catch (Exception e) 
		{
            
		    JOptionPane.showMessageDialog(null, e.getMessage());

    		// エラーの場合詳細ウィンドウを閉じる
			closeWindow();
		}	
	}

	/**
	 * 詳細照会画面を表示します。<BR>
	 * フレーム上に各コンポーネントで作成したパネル、リスト、ボタンの
	 * レイアウトを指定し、貼り付けます。
	 * 
	 * @param telegramData 電文情報
	 * @param rftNo RFT号機No
	 * @param dateTime 処理日時
	 */
	public void detailsDisp (IdData telegramData[], String rftNo, String dateTime)
	{
		JScrollPane scrollPane;	// 一覧表示パネル

		Container contentPane = this.getContentPane();

		// 画面設定
		// ヘッダータイトル、画面サイズ、フレーム改設定、背景色
		setTitle(WindowTitle);										// タイトル
		this.setSize(widthSize, heightSize);						// サイズ
		contentPane.setVisible(true);								// 可視設定
		
		// フレームの背景色
		this.getContentPane().setBackground(new Color(formBackColor[0], formBackColor[1], formBackColor[2]));

		// レイアウトマネージャー
		SpringLayout layout = new SpringLayout();
		// コンポーネントコンテナ
		this.getContentPane().setLayout(layout);
		
		// RFT号機Noコンポーネントのインスタンス生成
		LvRftNo comRftNo = OperationMode.getRftNoInstance(false, rftNo); // RFT号機Noパネル

		// 処理日時コンポーネントのインスタンス生成
		LvDateTimePanel comDate = new LvDateTimePanel("LBL-W0039", dateTime);

		// テーブルコンポーネントのインスタンス生成
		ColumnTable comTable = new ColumnTable();
		
		// 詳細照会テーブル
		scrollPane = comTable.initialize(telegramData);

		// ボタンコンポーネント
		LvButton btnClose = new LvButton("BTN-9003");

		// RFT号機Noの配置
		contentPane.add(comRftNo);
		layout.putConstraint(SpringLayout.NORTH, comRftNo, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, comRftNo, 20, SpringLayout.WEST, this);

        // 処理日時の配置
    	contentPane.add(comDate);
		layout.putConstraint(SpringLayout.NORTH, comDate, 40, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, comDate, 20, SpringLayout.WEST, this);

        // 項目・内容の配置
		contentPane.add(scrollPane);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 85, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, this);
		
        // ボタンの配置
		contentPane.add(btnClose);
		layout.putConstraint(SpringLayout.NORTH, btnClose, 515, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, btnClose, 500, SpringLayout.WEST, this);
		btnClose.addActionListener(this);
	
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * 詳細照会画面を閉じる
	 */
	public void closeWindow()
	{
    	this.dispose();
	}

	/**
	 * ボタンアクションを実行します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 */
	public void actionPerformed(ActionEvent e)
	{
	    // 終了ボタン押下時は詳細一覧を閉じる。
		closeWindow();
	}
}
