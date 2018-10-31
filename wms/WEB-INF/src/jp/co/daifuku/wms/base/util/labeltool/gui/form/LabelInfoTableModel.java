// $Id: LabelInfoTableModel.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import javax.swing.table.DefaultTableModel ;


/**
 * ラベル管理情報一覧表示用テーブルモデルです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/05/31</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class LabelInfoTableModel
		extends DefaultTableModel
{

	/** <code>管理Noの列番号</code> */
	public static final int ID = 0 ;

	/** <code>ラベル名称の列番号</code> */
	public static final int LABEL_NAME = 1 ;

	/** <code>レイアウトファイル名の列番号</code> */
	public static final int LAYOUT_NAME = 2 ;

	/** <code>ステータスの列番号</code> */
	public static final int STATUS = 3 ;

	/** <code>変更ボタンの列番号</code> */
	public static final int UPDATE = 4 ;

	/** <code>削除ボタンの列番号</code> */
	public static final int DELETE = 5 ;

	/** <code>自動発行設定ボタンの列番号</code> */
	public static final int AUTO_PRINT_SETTING = 6 ;

	/** <code>テーブル列のヘッダ名称</code> */
	private static Object[] $columnNames = {
			"\u7BA1\u7406No",
			"\u30E9\u30D9\u30EB\u540D\u79F0",
			"\u30EC\u30A4\u30A2\u30A6\u30C8\u30D5\u30A1\u30A4\u30EB\u540D",
			"",
			"",
			"",
			""
	} ;

	/**
	 * このクラスのコンストラクタです。<br>
	 * 
	 * @param data データ配列
	 */
	public LabelInfoTableModel(Object[][] data)
	{
		super(data, $columnNames) ;
	}

	/**
	 * データ配列をテーブルモデルに設定します。<br>
	 * 
	 * @param data データ配列
	 */
	public void setDataVector(Object[][] data)
	{
		super.setDataVector(data, $columnNames) ;
	}

	/**
	 * データ配列をテーブルモデルに設定します。<br>
	 * 
	 * @param row 行番号
	 * @param column 列番号
	 * @return ボタン以外のセールが編集不可
	 */
	public boolean isCellEditable(int row, int column)
	{
		if (column == UPDATE || column == DELETE || column == AUTO_PRINT_SETTING)
		{
			return true ;
		}
		return false ;
	}

}
