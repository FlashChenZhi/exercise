package jp.co.daifuku.wms.base.common.tool.logViewer;
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


/**
 * 通信トレースログ一情報を取得し、
 * 通信トレースログ一覧画面にて指定された検索条件より
 * 表示対象となる情報を検索します。<BR>
 * 通信トレースログデータクラスへセットし、
 * 通信トレースログデータクラスより、通信トレースログ一覧クラスへ
 * 情報の引渡しを行います。
 * 
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

public class TraceTable extends JPanel
{
    // Class fields --------------------------------------------------
    /**
     * RFT号機NO
     */
    public String rftNo;
    
    /**
     * 通信トレースログ一覧テーブル
     */
    private JTable tblTraceList;

    /**
     * スクロールパネル
     */
    JScrollPane scrollPane;

    /**
     * 通信トレースログ一覧テーブルモデル
     */
    private DefaultTableModel model;

    /**
     * 通信トレースログ一覧テーブル行番号セル幅
     */
    public static final int LineNoWidth = 40;

    /**
     * 通信トレースログ一覧テーブル処理日時セル幅
     */
    public static final int ProcessDateWidth = 80;
    
    /**
     * 通信トレースログ一覧テーブル処理時間セル幅
     */
    public static final int ProcessTimeWidth = 80;

    /**
     * 通信トレースログ一覧テーブル送信／受信区分セル幅
     */
    public static final int SendReceiveDivisionWidth = 65;

    /**
     * 通信トレースログ一覧テーブルＩＤＮｏセル幅
     */
    public static final int IdNoWidth = 45;

    /**
     * 通信トレースログ一覧テーブル電文セル幅
     */
    public static final int TraceMessageWidth = LogViewerParam.TraceMessageWidth;

    /**
     * フォント
     */
   protected final Font TableFont = new Font("Monospaced", Font.PLAIN, 12);    
    
    /**
     * テーブル用のパネルサイズ
     */
    public static final Dimension PanelSize
    	= new Dimension(LogViewerParam.WindowWidth - 40,
    					LogViewerParam.WindowHeight - 285);
    
    /**
     * 背景色のRGB値
     */
    final int[] backColor = LogViewerParam.BackColor;

    /**
     * 通信トレースログ一覧ヘッダー
     */
    private static String[] ColumnNames =
    	{" ",
    	 DispResourceFile.getText("LBL-W0560"),
    	 DispResourceFile.getText("LBL-W0561"),
    	 DispResourceFile.getText("LBL-W0188"),
    	 DispResourceFile.getText("LBL-W0001"),
    	 DispResourceFile.getText("LBL-W0288")
    	 };

    /**
	 * 通信トレースログデータ
	 */
    protected TraceList traceList;
    protected Selection mouseAdapter = new Selection();
    
    /**
     * コンストラクタ
     */
    TraceTable()
    {
    	super();

    	model = new DefaultTableModel(ColumnNames, 0);
		tblTraceList = new JTable(model);

		// マウスリスナーの設定
		tblTraceList.addMouseListener(mouseAdapter);

		setColumnInfo();

		// 表示内容を編集できないようにする
		tblTraceList.setEnabled(false);

        // マウスでのセル幅変更は無効
        tblTraceList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        // テーブルヘッダの設定をする
        JTableHeader tableHeader = tblTraceList.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        
        // スクロールパネルの設定
        scrollPane = new JScrollPane(tblTraceList);
        scrollPane.setPreferredSize(PanelSize);

        this.setFocusable(false);
        tblTraceList.setFocusable(false);
        scrollPane.setFocusable(false);
		tableHeader.setFocusable(false);
        
        // フォントの設定
        tblTraceList.setFont(TableFont);
        
		// 背景色の設定
        this.setBackground(new Color(backColor[0], backColor[1], backColor[2]));

        // スクロールパネルの表示
        this.add(scrollPane);
    }
    
    /**
	 * 通信トレースログ一覧画面で【表示】ボタン押下時の処理
	 * 
	 * @param list 通信トレースログデータ
	 */
    public void setData(TraceList list)
    {
    	traceList = list;
    	mouseAdapter.setTraceList(list);
    	
        int dataCount = list.getSize();
        String tableData[][] = new String[dataCount][ColumnNames.length];

        for (int idx = 0; idx < dataCount; idx ++)
        {
        	TraceData data = list.getTraceData(idx);
            tableData[idx][0] = String.valueOf(idx + 1);
            tableData[idx][1] = data.getProcessDate();
            tableData[idx][2] = data.getProcessTime();
            if (data.getSendRecvDivision().equals("S"))
            {
                tableData[idx][3] = DispResourceFile.getText("RDB-W0052");
            }
            else
            {
                tableData[idx][3] = DispResourceFile.getText("RDB-W0053");
            }
            tableData[idx][4] = data.getIdNo();
            tableData[idx][5] = data.getStringMessage().trim();
        }
    	
        // データを一覧にセット
        model.setDataVector(tableData, ColumnNames);

        setColumnInfo();
    }

    /**
     * テーブルの各カラムの属性をセットします。
     * 
     */
    protected void setColumnInfo()
    {
        // セル幅の設定
        tblTraceList.getColumnModel().getColumn(0).setPreferredWidth(LineNoWidth);
        tblTraceList.getColumnModel().getColumn(1).setPreferredWidth(ProcessDateWidth);
        tblTraceList.getColumnModel().getColumn(2).setPreferredWidth(ProcessTimeWidth);
        tblTraceList.getColumnModel().getColumn(3).setPreferredWidth(SendReceiveDivisionWidth);
        tblTraceList.getColumnModel().getColumn(4).setPreferredWidth(IdNoWidth);
        tblTraceList.getColumnModel().getColumn(5).setMinWidth(TraceMessageWidth);

        // 行番号欄の設定
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tblTraceList.getColumnModel();
		for (int i = 0; i < tblTraceList.getColumnCount(); i ++)
		{
			TableColumn tableColumn = columnModel.getColumn(i);
			if (i == 0)
			{
				tableColumn.setCellRenderer(new Column0Recorder());
				tableColumn.setMaxWidth(LineNoWidth);
				tableColumn.setResizable(false);
			}
			if (i == 5)
			{
				tableColumn.setResizable(true);
			}
			else
			{
				tableColumn.setResizable(false);
			}
		}
    }

    /**
     * RFT号機NOを設定します。
     * @param value RFT号機NO
     */
    public void setRftNo(String value)
    {
        rftNo = value;
    }
    
    /**
     * RFT号機NOを取得します。
     * @return RFT号機NO
     */
    public String getRftNo()
    {
      return rftNo;  
    }
    
    /**
     * 表示内容のクリアします。
     */
    public void clear()
    {
        // データを一覧にセット
        model.setDataVector(null, ColumnNames);
        
        setColumnInfo();
    }
}

/**
 * 通信トレースログ一覧状でのマウスのクリックイベント処理<BR>
 * 行番号欄がクリックされた場合、選択行の電文情報を詳細照会画面 へ引き渡し、<BR>
 * 詳細電文の項目ごとの詳細情報の表示を行います。
 */
class Selection extends MouseAdapter
{
	private TraceList traceList;

	Selection()
	{
		super();
	}

	public void setTraceList(TraceList list)
	{
		traceList = list;
	}
	
	public void mousePressed(MouseEvent e)
	{
		// テーブル情報取得
		JTable cell = (JTable) e.getSource();

		// 選択列取得
		int col = cell.columnAtPoint(e.getPoint());

		// 選択行取得
		int row = cell.rowAtPoint(e.getPoint());

		// 行番号押下時、詳細照会画面へ遷移
		if (col == 0)
		{
			// ID項目設定情報の取得
			DetailsWindow disp = new DetailsWindow();

			// 詳細照会画面へ遷移
			disp.startPopup(traceList.getRftNo(), traceList.getTraceData(row));
		}
	}
}

/**
 * 通信トレースログ一覧の行番号列の設定を行う<BR>
 * 行番号のセット、背景色をオレンジ色に設定する。
 */
class Column0Recorder extends DefaultTableCellRenderer 
{
    /**
	 * 行番号背景色
	 */
    final Color LineAreaBackColor = new Color(255, 165, 0);
    
    public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		// 背景色をオレンジ色
		setBackground(LineAreaBackColor);
		// 行番号セット
		setValue(new Integer(row + 1));
		// 罫線の設定
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setHorizontalAlignment(RIGHT);

		return this;
	}
}


