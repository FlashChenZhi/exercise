//$Id: ColumnInfo.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * <pre>
 * 電文項目情報クラス<br>
 * 項目名、コメント、電文内容を取得し、<br>
 * 電文項目情報を保持します。<br>
 * </pre>
 * @author hota
 * @version 
 */
public class ColumnInfo 
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
	 * 項目名
	 */
	protected String name;

	/**
	 * コメント（ツールチップ）
	 */
	protected String comment;
	
	/**
	 * 内容（電文分割）
	 */
	protected String value;
    
    /**
     * [
     */
    private final String STX_CHAR= "[";
    
    /**
     * ]
     */
    private final String ETX_CHAR = "]";
	
	
	/**
	 * コンストラクタ
	 */
	public ColumnInfo()
	{
		super();
	}

	/**
	 * 項目名を取得します
	 * @return  項目名
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 項目名を保持します
	 * @param name 項目名
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * コメントを取得します
	 * @return  コメント
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * コメントを保持します
	 * @param comment コメント
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	/**
	 * 電文内容を取得します
	 * @return 電文内容
	 */
	public String getValue()
	{
        if (value.equals(STX_CHAR))
        {
            return LogViewerParam.DisplaySTX;
        }
        else if (value.equals(ETX_CHAR))
        {
            return LogViewerParam.DisplayETX;
        }
        else
        {
            return value;
        }
	}

	/**
	 * 電文内容を保持します
	 * @param 電文内容
	 */
	public void setValue(String value)
	{
        this.value = value;
	}
}
