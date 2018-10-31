// $Id: MessageResourceFile.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 * コンポーネントに表示するラベル文字列をMessageResourceから取得するためのクラスです。。
 * <BR>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class MessageResourceFile
{
	/**
	 * デフォルトのリソース
	 */
	public static final String MESSAGE_RESOURCE = "MessageResource" ;

	/**
	 * キーから、パラメータの内容を文字列表現で取得します。
	 * @param key  取得するパラメータのキー
	 * @return   パラメータの文字列表現
	 */
	public static String getText(String key)
	{
		try
		{
			ResourceBundle rb = ResourceBundle.getBundle(MESSAGE_RESOURCE, Locale.getDefault());
			if (rb != null)
			{
				return rb.getString(key);
			}
		}
		catch(Exception e)
		{
			return key;
		}
		return key;
	}
}
