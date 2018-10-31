//$Id: LogViewer.java 5319 2009-10-29 04:56:03Z ota $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.NoSuchElementException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

import jp.co.daifuku.common.text.StringUtil;

/**
 * 通信トレースログ一覧画面 <BR>
 * 通信トレースログ一覧表示画面を作成する
 * 
 * <BR>
 * 
 * @version $Revision: 5319 $, $Date: 2009-10-29 13:56:03 +0900 (木, 29 10 2009) $
 * @author $Author: ota $
 */

class LogViewer extends JFrame implements ActionListener
{
	/**
	 * ウィンドウタイトル
	 */
	final String WindowTitle = DispResourceFile.getText("TLE-9000");
    
    /**
     * 一覧表示件数
     */
    private static int TraceLogFileDispCnt;

    /**
     * 操作パネル
     */
    LvOperationPanel comOperationPanel;
    
    /**
     * RFT号機No入力パネル
     */
    LvRftNo comRftNo;

    /**
     * ID入力パネル
     */
    LvIdNo comIdNo;

    /**
     * 検索条件開始パネル
     */
    LvDateTime comStDateTime;
    
    /**
     * 検索条件終了パネル
     */
    LvDateTime comEdDateTime;

    /**
     * トレースデータ一覧テーブル
     */
    TraceTable comTraceLogTable = new TraceTable();

    /**
     * 終了ボタン
     */
    LvButton btnExit;

    /**
     * 一覧表示件数を設定します。
     * @param トレースログ一覧表示件数
     * @author tsukoi
     */
    public static void setTraceLogFileDispCnt(int Count)
    {
       TraceLogFileDispCnt = Count;
    }
    
    /**
     * 一覧表示件数の取得します。
     * @return 一覧表示件数
     * @author tsukoi
     */
    public static int getTraceLogFileDispCnt()
    {
        return TraceLogFileDispCnt;
    }

    /**
     * インスタンスを生成します。
     */
    LogViewer()
    {

        // 設定ファイルより画面サイズを取得
        final int windowWidth = LogViewerParam.WindowWidth;
        final int windowHeight = LogViewerParam.WindowHeight;

        // コントロール作成
        this.setTitle(WindowTitle);
        comRftNo = OperationMode.getRftNoInstance(); // RFT号機Noパネル
        comIdNo = new LvIdNo(); // IDパネル
        comStDateTime = new LvDateTime(DispResourceFile.getText("LBL-W0221")); // 検索条件開始パネル
        comEdDateTime = new LvDateTime(DispResourceFile.getText("LBL-W0058")); // 検索条件終了パネル
        comOperationPanel = new LvOperationPanel(this); // 表示、クリアボタンパネル
    	btnExit = new LvButton("MBTN-W0040");
    	btnExit.addActionListener(this);

    	// コンテナ生成
        Container contentPane = this.getContentPane();

        // Frameのセットアップ
        contentPane.setLayout(new FlowLayout());

        // コントロールの追加
        contentPane.add(comRftNo); // RFT号機No
        contentPane.add(comIdNo); // ID
        contentPane.add(comStDateTime); // 検索条件開始
        contentPane.add(comEdDateTime); // 検索条件終了
        contentPane.add(comOperationPanel); // 
        contentPane.add(comTraceLogTable); // テーブル
        contentPane.add(btnExit); // 終了ボタン

        // レイアウトの定義
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        // コントロール縦位置の設定
        layout.putConstraint(SpringLayout.NORTH, comRftNo, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, comIdNo, 0, SpringLayout.SOUTH, comRftNo);
        layout.putConstraint(SpringLayout.NORTH, comStDateTime, 0, SpringLayout.SOUTH, comIdNo);
        layout.putConstraint(SpringLayout.NORTH, comEdDateTime, 0, SpringLayout.SOUTH, comIdNo);
        layout.putConstraint(SpringLayout.NORTH, comOperationPanel, 0, SpringLayout.SOUTH, comEdDateTime);
        layout.putConstraint(SpringLayout.NORTH, comTraceLogTable, 5, SpringLayout.SOUTH, comOperationPanel);
        layout.putConstraint(SpringLayout.NORTH, btnExit, 10, SpringLayout.SOUTH, comTraceLogTable);

        // コントロール横位置の設定
        layout.putConstraint(SpringLayout.WEST, comRftNo, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comIdNo, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comStDateTime, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comEdDateTime, 260, SpringLayout.WEST, comStDateTime);
        layout.putConstraint(SpringLayout.WEST, comTraceLogTable, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, comOperationPanel, 10, SpringLayout.WEST, comTraceLogTable);
        layout.putConstraint(SpringLayout.WEST, btnExit, 10, SpringLayout.WEST, comTraceLogTable);

        // フォームの設定
        int[] formBackColor = LogViewerParam.BackColor;
        this.getContentPane().setBackground(new Color(formBackColor[0], formBackColor[1], formBackColor[2]));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setVisible(true);
        this.setResizable(false);

		// ENTERキーでフォーカス移動するようにする
		HashSet keySet = new HashSet();
		AWTKeyStroke s = AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0);
		keySet.add(s);
		s = AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0);
		keySet.add(s);
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keySet);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        
    	Object sender = e.getSource();
    	if (sender == btnExit)
    	{
            // 終了ボタン押下時はアプリケーションを終了する
            System.exit(0);
    	}
    	else
    	{
    		if (sender == comOperationPanel.getClearButton())
    		{
    			clearInputArea();
    		}
    		else if (sender == comOperationPanel.getDisplayButton())
    		{
    			displayList();
    		}
    	}
    }

    /**
     * 通信トレースログ一覧画面
     * 
     * @param args 動作モード
     */
    public static void main(String[] args)
    {
    	// 引数で指定された動作モードを設定する
    	OperationMode.setMode(args[0]);
    	
    	try
    	{
    		LogViewerParam.initialize();
    	}
    	catch (Exception e)
    	{
    		// 設定ファイルの読み込みに失敗した場合はダイアログで通知した上で、
    		// デフォルト設定で起動する。
    		String param[] = {LogViewerParam.resourcePath};
    		String msg = MessageResourceFile.getText(e.getMessage());
			msg = MessageFormat.format(msg, param);
    		JOptionPane.showMessageDialog(null, msg);
    	}

        //メイン処理
        new LogViewer();
    }
    
    /**
     * 通信とレースログの一覧を表示します。
     */
    protected void displayList()
    {
        // 通信トレースログ一覧クラス保持用
        TraceList dispData = null;

        if (! checkInputData())
        {
        	return;
        }
        
        TraceLogFile traceLogFile = OperationMode.getTraceLogFileInstance();
        traceLogFile.setRftNo(comRftNo.getRftNo());
        traceLogFile.setIdNo1(comIdNo.getIdNo1());
        traceLogFile.setIdNo2(comIdNo.getIdNo2());
        traceLogFile.setStartProcessDate(comStDateTime.getDate());
        traceLogFile.setStartProcessTime(comStDateTime.getTime());
        traceLogFile.setEndProcessDate(comEdDateTime.getDate());
        traceLogFile.setEndProcessTime(comEdDateTime.getTime());
//        traceLogFile.setDisplayCondition(comDisplayConditionButton.getSendRecvDivision());

        // 一覧データを取得
        try
        {
            dispData = traceLogFile.getTraceData();
            dispData.setRftNo(comRftNo.getRftNo());
            comTraceLogTable.setData(dispData);
        }
        catch (EOFException e)
        {
            comTraceLogTable.clear();
            String param[] = null;
            param = new String[1];
            param[0] = "RFT" + comRftNo.getRftNo() + TraceLogFile.TraceLogFileName;
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            JOptionPane.showMessageDialog(null, msg);
        }
        catch (NoSuchElementException e)
        {
            comTraceLogTable.clear();
            String param[] = null;
            param = new String[1];
            param[0] = "RFT" + comRftNo.getRftNo() + TraceLogFile.TraceLogFileName;
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            JOptionPane.showMessageDialog(null, msg);
        }
        catch (Exception e)
        {
            comTraceLogTable.clear();
        	String msgCode = e.getMessage();
    		JOptionPane.showMessageDialog(this, MessageResourceFile.getText(msgCode));
        }
    }
    
    /**
     * 入力エリアの検索条件をチェックします。
     * 
     * @return	エラーがない場合はtrue、エラーがある場合はfalseを返します。
     */
    protected boolean checkInputData()
    {
    	String param[] = null;

    	try
    	{
        	String rftNo = comRftNo.getRftNo();
        	if (StringUtil.isBlank(rftNo))
        	{
        		// 号機No未入力
        		throw new Exception("6023179");
        	}

        	String id1 = comIdNo.getIdNo1();
        	String id2 = comIdNo.getIdNo2();
        	if (! StringUtil.isBlank(id1))
        	{
        		try
        		{
        			Integer.parseInt(id1);
        		}
        		catch (NumberFormatException e)
        		{
        			param = new String[1];
        			param[0] = "ID1";
        			throw new Exception("6003104");
        		}
        	}
        	if (! StringUtil.isBlank(id2))
        	{
        		try
        		{
        			Integer.parseInt(id2);
        		}
        		catch (NumberFormatException e)
        		{
        			param = new String[1];
        			param[0] = "ID2";
        			throw new Exception("6003104");
        		}
        	}
        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        	dateFormatter.setLenient(false);
        	SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        	datetimeFormatter.setLenient(false);
        	String startDate = comStDateTime.getDate();
    		String startTime = comStDateTime.getTime();
    		java.util.Date startDateTime = null;
    		java.util.Date endDateTime = null;
        	if (! StringUtil.isBlank(startDate))
        	{
        		if (startDate.length() < 8)
        		{
    				throw new Exception("6023038");
        		}

        		try
    			{
        			if (StringUtil.isBlank(startTime))
        			{
        				startDateTime = dateFormatter.parse(startDate);
        			}
        			else
        			{
        				startDateTime = datetimeFormatter.parse(startDate + startTime);
        			}
    			}
    			catch (ParseException e)
    			{
    				throw new Exception("6023510");
    			}
        	}
        	else if (! StringUtil.isBlank(startTime))
        	{
        		throw new Exception("6023510");
        	}

        	String endDate = comEdDateTime.getDate();
    		String endTime = comEdDateTime.getTime();
        	if (! StringUtil.isBlank(endDate))
        	{
        		if (endDate.length() < 8)
        		{
    				throw new Exception("6023038");
        		}

        		try
    			{
        			if (StringUtil.isBlank(endTime))
        			{
        				endDateTime = dateFormatter.parse(endDate);
        			}
        			else
        			{
        				endDateTime = datetimeFormatter.parse(endDate + endTime);
        			}
    			}
    			catch (ParseException e)
    			{
    				throw new Exception("6023510");
    			}
        	}
        	else if (! StringUtil.isBlank(endTime))
        	{
        		throw new Exception("6023510");
        	}

        	if (startDateTime != null && endDateTime != null)
        	{
        		if (StringUtil.isBlank(endTime))
        		{
    				endDateTime = datetimeFormatter.parse(endDate + "235959");
        		}

        		if (startDateTime.compareTo(endDateTime) > 0)
        		{
        			// 開始日時が終了日時よりも後の場合はエラーとする。
        			throw new Exception("6023182");
        		}
        	}

        	return true;
    	}
    	catch (Exception e)
    	{
    		String msg = "";
			msg = MessageResourceFile.getText(e.getMessage());
    		if (param != null)
    		{
    			msg = MessageFormat.format(msg, param);
    		}
    		JOptionPane.showMessageDialog(this, msg);
    		return false;
    	}
    }

    /**
     * 入力エリアを初期化します。
     */
    protected void clearInputArea()
    {
    	comRftNo.clear();
    	comIdNo.clear();
    	comStDateTime.clear();
    	comEdDateTime.clear();
//    	comDisplayConditionButton.clear();
        comTraceLogTable.clear();
    }
}
