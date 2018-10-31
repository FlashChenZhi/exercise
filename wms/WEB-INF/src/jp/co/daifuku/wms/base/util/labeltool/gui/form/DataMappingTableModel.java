// $Id: DataMappingTableModel.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import javax.swing.table.DefaultTableModel ;


/**
 * ラベル印字項目とDB項目関連用のテーブルモデルです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/14</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class DataMappingTableModel
		extends DefaultTableModel
{

	/** <code>印字項目の列番号</code> */
	public static final int PRINT_FIELD_COL = 0 ;

	/** <code>DB項目の列番号</code> */
	public static final int DB_FIELD_COL = 1 ;

	/** <code>テーブル列のヘッダ名称</code> */
	private static String[] $columnNames = {
			"\u5370\u5B57\u9805\u76EE",
			"\u30C6\u30FC\u30D6\u30EB\u9805\u76EE"
	} ;

	/**
	 * このクラスのコンストラクタです。<br>
	 * 
	 * @param data データ配列
	 */
	public DataMappingTableModel(Object[][] data)
	{
		super(data, $columnNames) ;
	}

	/**
	 * セールの編集可否状態を設定します。
	 * 
	 * @param row 行番号
	 * @param col 列番号
	 * @return 編集可否状態
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col)
	{
		if (col == DB_FIELD_COL)
		{
			return true ;
		}
		return false ;
	}

	/**
	 * 値を設定します。<br>
	 * 
	 * @param obj 対象オブジェクト
	 * @param row 行番号
	 * @param col 列番号
	 * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object obj, int row, int col)
	{
		super.setValueAt(obj, row, col) ;
	}

}
