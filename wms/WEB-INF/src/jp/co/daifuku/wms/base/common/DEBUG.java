// $Id: DEBUG.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.MissingResourceException ;

/**
 * WareNaviシステムデバッグメッセージ出力行うためのクラスです。
 * リソース名称のデフォルトは、<code>DEBUG</code>となっています。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class DEBUG
		extends Object
{
	/** デフォルトのリソース */
	public static final String DEFAULT_RESOURCE = "DEBUG" ;

	//--------------------------------
	// キー定義
	//--------------------------------
	/** カテゴリキー (Handler) */
	public static final String HANDLER = "HANDLER" ;

	/** カテゴリキー (Method) */
	public static final String METHOD = "METHOD" ;

	/** カテゴリキー (SCH) */
	public static final String SCH = "SCHEDULE" ;

	/** カテゴリキー (CONTROL) */
	public static final String CONTROL = "CONTROL" ;

	/** カテゴリキー (NOPARAM) */
	public static final String NOPARAM = "NOPARAM" ;


	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	// Constructors --------------------------------------------------
	// No Constructors! all of method is static.

	// Public methods ------------------------------------------------
	/**
	 * キーから、パラメータの内容を取得します。
	 * @param key  メッセージ出力条件キー
	 * @param pmsg  出力メッセージ内容
	 */
	public static void MSG(String key, String pmsg)
	{
		try
		{
			String pout = DebugParam.getParam(key) ;

			switch (Integer.parseInt(pout))
			{
				case 0:
					break ;

				case 1:
					System.out.println(pmsg) ;
					break ;

				case 2:
					System.out.println("No Support Parameter" + pout) ;
					break ;

				case 3:
					System.out.println("No Support Parameter" + pout) ;
					break ;

				default:
					System.out.println("No Support Parameter" + pout) ;
					break ;
			}
		}
		catch (MissingResourceException e)
		{
            // MissingResourceExceptionは無視する。
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			System.out.println("Please check the following file by connection at the time of error generating") ;
			System.out.println("File Name[C:/daifuku/wms/data/ini/DebugParam.properties]") ;
		}
	}

	/**
	 * mainメソッド<BR>
	 * WareNaviシステムデバッグメッセージ出力を行います。
	 * @param argv NOT USED
	 */
	public static void main(String[] argv)
	{
		String[] keys = {
				"HANDLER",
				"METHOD",
				"SCHEDULE",
				"CONTROL",
				"NOPARAM",
				"XXXXXXX"
		} ;
		for (int i = 0; i < keys.length; i++)
		{
			MSG(keys[i], ("SAMPLE TEST MESSAGE Key:" + keys[i] + " point:" + i)) ;
		}
	}
}
