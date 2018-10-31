//$Id: ColumnTable.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * <pre>
 * 詳細照会画面表示情報コンポーネント<br>
 * 詳細照会表示情報を<br>
 * JTableに表示情報を設定し<br>
 * JScrollPaneに貼り付けパネルを返します。<br>
 * </pre>
 * @author 1.00 hota
 * @version 1.00 2006/02/02 
 */
public class ColumnTable extends JPanel
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
	 * ヘッダータイトル
	 */
	private static String[] header = {DispResourceFile.getText("LBL-W0570")
                                       ,DispResourceFile.getText("LBL-W0571")};

	/**
	 * カラム（項目）幅
	 */
	private final int NameWidth = 150;

	/**
	 * カラム（内容）幅
	 */
	private final int ValueWidth = 400;

	/**
	 * 項目内容の1行の最大表示byte数
	 */
	protected final int maxLength = 64;

    /**
     * テーブル用のパネルサイズ
     */
    public static final Dimension PanelSize = new Dimension(553, 420);

    /**
	 * ツールチップの背景色
	 */
	protected final Color toolTipColor = new Color(255, 255, 229);

	/**
	 * デフォルトコンストラクタ
	 */
	public ColumnTable()
	{
		super();
	}

	/**
	 * 表示するラベル文字列の番号を指定するコンストラクタ
	 * 
	 * @param idInfo DispResourceの番号
	 */
	public ColumnTable(IdData[] idInfo)
	{
		super();
		initialize(idInfo);
	}


	/**
	 * 電文情報とID項目名、内容を取得後、
	 * 電文をID項目ごとに分解、
	 * テーブルにセットし
	 * JScrollPaneコンポーネントを返します。
	 * 
	 * @param idInfo 電文情報の配列
	 * @return JScrollPaneコンポーネント
	 */
	public JScrollPane initialize(IdData[] idInfo)
	{
		// 電文情報をセット用
	    final String data[][] = new String[idInfo.length][3];

	    // 表示内容データを取得する。
		for (int i = 0; i < idInfo.length; i ++)
		{
			data[i][0] = idInfo[i].getTelegramData().getName();
			data[i][1] = idInfo[i].getTelegramData().getValue();
			data[i][2] = idInfo[i].getTelegramData().getComment();
		}
	
	    JTable table;

	    // テーブルに情報をセット
	    table = new JTable(data, header)
		{
			// ツールチップ用マウスイベント
			public String getToolTipText(MouseEvent me)
			{
				// マウスの位置を取得
				Point pt = me.getPoint();
				int row = rowAtPoint(pt);
				
				// ツールチップ表示データ取得
				String toolTipText = data[row][2];
				if (row < 0) 
				{
					return null;
				}
				else
				{
					// コメントがない場合、ツールチップを表示しない
					if(toolTipText.equals(null) || toolTipText.equals(""))
					{
						return null;
					}
					return toolTipText;
				}
			}
			// 独自ツールチップを作成
			public JToolTip createToolTip() 
			{
				return new JToolTipEx();
			}
			// 背景色をセットする
			class JToolTipEx extends JToolTip 
			{
				public void paint(Graphics g) 
				{
					setBackground(toolTipColor);
					super.paint(g);
				}
			}
		}
        ;

        // テーブルカラムのサイズ設定
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        nameColumn.setPreferredWidth(NameWidth);

        TableColumn valueColumn = table.getColumnModel().getColumn(1);
        valueColumn.setPreferredWidth(ValueWidth);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 行の高さを取得する。
        int rowHeight = table.getRowHeight();
        for (int i = 0; i < data.length; i ++)
        {
			// 電文分割内容のbyte数が表示最大byte数より大きい場合
			if (data[i][1].length() > maxLength)
			{
				// 電文分割内容の長さを表示最大byte数で割った数を行数とする
				int rowCnt = data[i][1].length() / maxLength;
				if (data[i][1].length() % maxLength > 0)
				{
					rowCnt ++;
				}

				// 指定した行数の分の高さを設定する
				table.setRowHeight(i, rowHeight * rowCnt);
			}
        }

        // テーブルヘッダの設定をする
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        
        // 表示テーブル設定
        table.setDefaultRenderer(Object.class, new MyCellRenderer());

        // テーブルのアクションを使用不可にする
        table.setEnabled(false);

        // JScrollPaneにテーブル内容をセット
        JScrollPane scrollPane = new JScrollPane(table);

        // スクロールパネルのアクションを使用不可にする
		scrollPane.setEnabled(false);
        scrollPane.setPreferredSize(PanelSize);
		this.add(scrollPane);

		return scrollPane;
	}

	/**
	 * テーブル情報が最大表示幅を超える場合のため独自のコンポーネント設定<BR>
	 * 電文内容が最大表示列幅を超えた場合、改行するためのクラス
	 */
	class MyCellRenderer extends JTextArea implements TableCellRenderer
	{
		/**
		 * コンストラクタ
		 */
		MyCellRenderer()
		{
			super();
			setLineWrap(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
													   boolean isSelected, boolean hasFocus,
													   int row, int column)
		{
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}