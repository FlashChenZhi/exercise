// $Id: LabelParam.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.util.labeltool ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Locale;
import java.util.ResourceBundle;

import jp.co.daifuku.common.ResourceUtil;

/**
 * ラベル印刷実行時のパラメータのキーを定義するクラスです。
 * LabeltoolParam class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/12/11</td><td nowrap>070456</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  070456
 * @author  Last commit: $Author: kitamaki $
 */
public class LabelParam
{
	/**
	 * デフォルトのリソース
	 */
	public static final String LABEL_DEFAULT_RESOURCE = "LabelParam" ;

	/**
	 * プリンタIPアドレス
	 */
	public static final String PRINTER_IP_ADRESS = getParam("PRINTER_IP_ADRESS") ;

	/**
	 * Entity作成時のパッケージ宣言のパス
	 */
	public static final String ENEITY_PACKAGE_PASS = getParam("ENEITY_PACKAGE_PASS") ;

	/**
	 * 印刷実行時に読込むxmlファイルのパス
	 */
	public static final String XML_FILE_PASS = getParam("XML_FILE_PASS") ;

    /**
     * ビットマップファイルのパス
     */
    public static final String BIT_MAP_FILE_PATH = getParam("BIT_MAP_FILE_PATH") ;

	/**
	 * DB関連情報XMLファイルパス
	 */
	public static final String DB_RELATION_FILE_PASS = getParam("DB_RELATION_FILE_PASS") ;

	/**
	 * ラベル情報管理XMLファイル名
	 */
	public static final String DB_MANAGE_FILE_PASS = getParam("DB_MANAGE_FILE_PASS") ;

    /**
     * Labelトレースファイル名
     */
    public static final String LABEL_TRACE_NAME = getParam("LABEL_TRACE_NAME");
    
    /**
     * LabelトレースファイルのMaxサイズ(Byte)
     */
    public static final int LABEL_TRACE_MAX_SIZE = getIntParam("LABEL_TRACE_MAX_SIZE");

    /**
     * Labelトレース Write on/off
     */
    public static final boolean LABEL_TRACE_ON = getBoolParam("LABEL_TRACE_ON");

	/**
	 * キーから、パラメータの内容を取得します。
	 * 
	 * 
	 * @param key 取得するパラメータのキー
	 * @return  パラメータの文字列表現
	 */
	private static String getParam(String key)
	{
		try
		{
			ResourceBundle rb = getBundle(LABEL_DEFAULT_RESOURCE, Locale.getDefault()) ;
			if (rb != null)
			{
				return (rb.getString(key)) ;
			}
		}
		catch (Exception e)
		{
			// do nothing.
			// リソースファイルNG
			e.printStackTrace() ;
		}
		return "" ;

	}

	/**
	 * リソースバンドルを取得します。
	 * @param res  リソース
	 * @param locale Localeオブジェクト
	 * @return リソースバンドル
	 */
	private static ResourceBundle getBundle(String res, Locale locale)
	{
		return (ResourceBundle.getBundle(res, locale)) ;
	}
	
    /**
     * キーから、パラメータの内容を数値表現で取得します。<br>
     * 
     * @param key 取得するパラメータのキー
     * @return int - パラメータの数値表現
     */
    private static int getIntParam(String key)
    {
        try
        {
            return Integer.parseInt(getParam(key));
        }
        catch (Exception e)
        {
            // do nothing.
        }
        return -1;
    }
    
    /**
     * キーから、パラメータの内容を真偽値表現で取得します。<br>
     * 
     * @param key 取得するパラメータのキー
     * @return boolean - パラメータの真偽値表現
     */
    private static boolean getBoolParam(String key)
    {
        return ResourceUtil.getBoolParam(LABEL_DEFAULT_RESOURCE, key);
	}
}
