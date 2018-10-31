//$Id: IdData.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * <pre>
 * 電文情報クラス<br>
 * 表示電文の内容を保持します。
 * </pre>
 * @author hota
 * @version 
 */
public class IdData 
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
	 * コンストラクタ
	 */
	public IdData()
	{
		super();
	}

	/**
	 * 電文項目情報クラスのインスタンスを生成します。
	 */
	protected ColumnInfo columnInfo;	// 電文内容

	/**
	 * 電文項目情報クラスから電文情報を取得します
	 * @return 電文情報
	 */
	public ColumnInfo getTelegramData()
	{
		return columnInfo;
	}

	/**
	 * 電文項目情報を保持します。
	 * @param columnInfo 電文項目情報
	 */
	public void setTelegramData(ColumnInfo columnInfo)
	{
		this.columnInfo = columnInfo;
	}
}
