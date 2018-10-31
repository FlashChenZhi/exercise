// $Id: LvTextField.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 * ログビューワで使用するテキストフィールドコンポーネントです。
 * <BR>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

public class LvTextField extends JTextField
{
	int maxLength = -1;

	/**
	 * デフォルトコンストラクタ
	 */
	public LvTextField()
	{
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param maxLength		入力最大桁数
	 */
	public LvTextField(int maxLength)
	{
		super(maxLength);
		this.maxLength = maxLength;
		initialize();
	}

	/**
	 * テキストフィールドコンポーネントを初期化します。
	 */
	public void initialize()
	{
		this.setDocument(new LimitedDocument(maxLength));
	}

	/**
	 * RFT号機NO入力欄の定義します。
	 * @param columns 号機NO
	 */
	public void setColumns(int columns)
	{
		super.setColumns(columns);
		maxLength = columns;
		initialize();
	}

	/**
	 * インナークラスです。
	 */
	class LimitedDocument extends PlainDocument
	{
		int limit;

		/**
		 * コンストラクタ
		 */
		LimitedDocument(int limit)
		{
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet a)
		{
			if (limit >= 0 && getLength() >= limit)
			{
				return;
			}
			try
			{
				super.insertString(offset, str, a);
			}
			catch (BadLocationException e)
			{
				System.out.println(e);
			}
		}
	}
}
