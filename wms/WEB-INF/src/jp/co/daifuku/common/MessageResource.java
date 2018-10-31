// $Id: MessageResource.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.util.MessageResources;

/**<jp>
 * メッセージのフォーマット用文字列を取得するためのクラスです。
 * フォーマット文字はリソースを利用していますので、多国語対応が可能になります。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/02/19</TD><TD>sawa</TD><TD>concatenate(String[] param) メソッド追加</TD></TR>
 * <TR><TD>2002/02/27</TD><TD>miyashita</TD><TD>DEFAULT_RESOURCE private→public変更</TD></TR>
 * <TR><TD>2003/04/02</TD><TD>inoue</TD><TD>staticなgetMessageメソッドを追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class acquires the message-formatting strings.
 * Formatting characters are formed of resource; therefore, it is possible to provide
 * the multilingual service.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/02/19</TD><TD>sawa</TD><TD>concatenate(String[] param) Method added</TD></TR>
 * <TR><TD>2002/02/27</TD><TD>miyashita</TD><TD>DEFAULT_RESOURCE private->public modified</TD></TR>
 * <TR><TD>2003/04/02</TD><TD>inoue</TD><TD>static getMessage Method added</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class MessageResource
        extends Object
{
	// Class fields --------------------------------------------------
	/**<jp>
	 * デリミタ
	 </jp>*/
	/**<en>
	 * Delimiter
	 </en>*/
	public static final String DELIM = "\t" ;
	/**<jp>
	 * デフォルトのリソース名
	 </jp>*/
	/**<en>
	 * Default resource name
	 </en>*/
	public static final String DEFAULT_RESOURCE = "MessageDef" ;

//	private static DecimalFormat $msg_num_format = new DecimalFormat("0000000") ;

	// Class variables -----------------------------------------------
	private Locale wLocale ;
	private String wResourceName ;
	private ResourceBundle wRB ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class,
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 8053 $,$Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $") ;
	}

	/**<jp>
	 * メッセージ番号とパラメータを区切り文字でつなぎます。
	 * 引数には始めの配列にメッセージ番号をセットしそれ以降の配列に必要なパラメータ分セットして下さい。<BR>
	 * 例外にセットするメッセージにはこのメソッドを使用することをお奨めします。<BR>
	 * @param param  メッセージ番号とメッセージにセットするパラメータの配列
	 * @return メッセージ番号とメッセージにセットするパラメータを区切り文字でつないだ文字列
	 </jp>*/
	/**<en>
	 * Using break characters, it connects the message number and the parameter.
	 * As for arguments, please set message number for the first array and to be followed by array with <BR>
     * required parameters. <BR>
	 * This method is recommended for exceptional messages setting.
     * @param param  message number then parameter array to be set in the message
	 * @return message number and the parameter array to be set in the message, connected by break character
	 </en>*/
	public static String concatenate(String[] param)
	{
		String msgstr = "" ;
		if (param == null || param.length == 0)
		{
			return null ;
		}

		msgstr = param[0] ;
		for (int i = 1; i < param.length; i++)
		{
			msgstr += DELIM + param[i] ;
		}
		return (msgstr) ;
	}

	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 * 注 入力されたキーが指定したロケールに存在しなかった場合は英語リソースを表示します。
	 * @param rname   取得するパラメータのキー
	 * @param locale 利用するロケールの指定
	 * @return       パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Retrieves the contents of parameter according to the key.
     * Attn: In case the entered key was not found in the specified locale, English resource will display.
	 * @param rname   Key to the parameter to be retrieved
	 * @param locale Specifying the locale to use
	 * @return       Parameter in string representaion
	 </en>*/
	public static String getMessage(String rname, Locale locale)
	{
//		ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE, locale) ;
//		try
//		{
			//return (rb.getString(rname));
			return MessageResources.getText(locale, rname);
//		}
//		catch (MissingResourceException e)
//		{
//			Locale   locale_en = new Locale( Locale.ENGLISH.getLanguage(), Locale.US.getCountry() ) ;
//			rb = ResourceBundle.getBundle(DEFAULT_RESOURCE, locale_en) ;
//			return (rb.getString(rname));
//		}
	}

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * 注 入力されたキーが指定したロケールに存在しなかった場合は英語リソースを表示します。
     * @param rname   取得するパラメータのキー
     * @param params  メッセージにセットするパラメータ
     * @param locale 利用するロケールの指定
     * @return       パラメータの文字列表現
     </jp>*/
    /**<en>
     * Retrieves the contents of parameter according to the key.
     * Attn: In case the entered key was not found in the specified locale, English resource will display.
     * @param rname   Key to the parameter to be retrieved
     * @param params  Parameter to set for the message
     * @param locale Specifying the locale to use
     * @return       Parameter in string representaion
     </en>*/
    public static String getMessage(String rname, Object[] params, Locale locale)
    {
//      ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE, locale) ;
//      try
//      {
            //return (rb.getString(rname));
            return MessageResources.getText(locale, rname, params);
//      }
//      catch (MissingResourceException e)
//      {
//          Locale   locale_en = new Locale( Locale.ENGLISH.getLanguage(), Locale.US.getCountry() ) ;
//          rb = ResourceBundle.getBundle(DEFAULT_RESOURCE, locale_en) ;
//          return (rb.getString(rname));
//      }
    }
	/**<jp>
	 * キーから、パラメータの内容を取得します。
	 *
	 * @param rname   取得するパラメータのキー
	 * @return       パラメータの文字列表現
	 </jp>*/
	/**<en>
	 * Retrieves the contents of parameter according to the key.
	 * @param rname   Key to the parameter to be retrieved
	 * @return       Parameter in string representaion
	 </en>*/
	public static String getMessage(String rname)
	{
//		ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE, locale) ;
//		try
//		{
			//return (rb.getString(rname));
			return MessageResources.getText(rname);
//		}
//		catch (MissingResourceException e)
//		{
//			Locale   locale_en = new Locale( Locale.ENGLISH.getLanguage(), Locale.US.getCountry() ) ;
//			rb = ResourceBundle.getBundle(DEFAULT_RESOURCE, locale_en) ;
//			return (rb.getString(rname));
//		}
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * デフォルト・コンストラクタ。
	 * メッセージリソースは、<code>MessageDef</code>と仮定されます。
	 * リソースの検索は、CLASSPATH に従います。クラスと同様に、階層構造がありますので、
	 * CLASSPATH以下のディレクトリにリソースをおく場合は、<code>foo.MessageDef</code>の様に
	 * 指定する必要があります。<br>
	 * ロケールは、デフォルト・ロケールが使われます。
	 </jp>*/
	/**<en>
	 * Default constructors.
	 * It assumes the message resource is <code>MessageDef</code>.
	 * Search of resource will be done according to CLASSPATH. Much the same as classes, they have the
	 * hierarchical structure. If the resource shall be placed in the directory under CLASSPATH, it is
	 * necessary to specify e.g.,<code>foo.MessageDef</code><br>
	 * Default locale will be used.
	 </en>*/
	public MessageResource()
	{
		this(DEFAULT_RESOURCE, Locale.getDefault()) ;
	}

	/**<jp>
	 * ロケール指定での、コンストラクタ。
	 * メッセージリソースは、<code>MessageDef</code>と仮定されます。
	 * リソースの検索は、CLASSPATH に従います。クラスと同様に、階層構造がありますので、
	 * CLASSPATH以下のディレクトリにリソースをおく場合は、<code>foo.MessageDef</code>の様に
	 * 指定する必要があります。
	 * @param locale 利用するロケールの指定
	 </jp>*/
	/**<en>
	 * Constructor with specified locale.
	 * Assumed message resource is <code>MessageDef</code>.
	 * Search of resource will be done according to CLASSPATH. Much the same as classes, they have the
	 * hierarchical structure. If the resource shall be placed in the directory under CLASSPATH, it is
	 * necessary to specify e.g.,<code>foo.MessageDef</code>.<br>
	 * @param locale Specify the locale to be used
	 </en>*/
	public MessageResource(Locale locale)
	{
		this(DEFAULT_RESOURCE, locale) ;
	}

	/**<jp>
	 * リソース指定での、コンストラクタ。
	 * リソースの検索は、CLASSPATH に従います。クラスと同様に、階層構造がありますので、
	 * CLASSPATH以下のディレクトリにリソースをおく場合は、<code>foo.MessageDef</code>の様に
	 * 指定する必要があります。<br>
	 * ロケールは、デフォルト・ロケールが使われます。
	 * @param res 利用するリソースの指定
	 </jp>*/
	/**<en>
	 * Constructor with specified locale.
	 * Search of resource will be done according to CLASSPATH. Much the same as classes, they have the
	 * hierarchical structure. If the resource shall be placed in the directory under CLASSPATH, it is
	 * necessary to specify e.g.,<code>foo.MessageDef</code><br>
	 * Default locale will be used.
	 * @param res Specify the locale to be used
	 </en>*/
	public MessageResource(String res)
	{
		this(res, Locale.getDefault()) ;
	}

	/**<jp>
	 * リソースおよび、ロケール指定での、コンストラクタ。
	 * リソースの検索は、CLASSPATH に従います。クラスと同様に、階層構造がありますので、
	 * CLASSPATH以下のディレクトリにリソースをおく場合は、<code>foo.MessageDef</code>の様に
	 * 指定する必要があります。<br>
	 * @param res 利用するリソースの指定
	 * @param locale 利用するロケールの指定
	 </jp>*/
	/**<en>
	 * Constructor with specified resource and the locale.
	 * Search of resource will be done according to CLASSPATH. Much the same as classes, they have the
	 * hierarchical structure. If the resource shall be placed in the directory under CLASSPATH, it is
	 * necessary to specify e.g.,<code>foo.MessageDef</code><br>
	 * @param res Specify the resource to be used
	 * @param locale Specify the locale to be used
	 </en>*/
	public MessageResource(String res, Locale locale)
	{
		setLocale(locale) ;
		setResourceName(res) ;
//		getBundle(getResourceName(), getLocale()) ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * <code>ResourceBundle</code>を取得します
	 * @return   <code>ResourceBundle</code>
	 </jp>*/
	/**<en>
	 * <code>ResourceBundle</code> will be retrieved.
	 * @return   <code>ResourceBundle</code>
	 </en>*/
	public ResourceBundle getResourceBundle()
	{
		return (wRB) ;
	}

	/**<jp>
	 * メッセージフォーマット用文字列を取得します。
	 * @param num  メッセージ番号
	 * @return フォーマット用文字列
	 * @throws MissingResourceException 定義されていないメッセージ番号がセットされた時に通知されます。
	 </jp>*/
	/**<en>
	 * Retrieves message formatting string.
	 * @param num  message number
	 * @return formatting string
	 * @throws MissingResourceException This throws the notification when undefined message number
	 * has been set.
	 </en>*/
	public String getFormatMessage(int num) throws MissingResourceException
	{
		String key = new DecimalFormat("0000000").format(num) ;
//		try
//		{
			return MessageResources.getText(key);
			//return (wRB.getString(key));
//		}
//		catch (MissingResourceException e)
//		{
//			Locale   locale = new Locale( Locale.ENGLISH.getLanguage(), Locale.US.getCountry() ) ;
//			getBundle(getResourceName(), locale) ;
//			return (wRB.getString(key));
//		}
	}

	/**<jp>
	 * フォーマット済みメッセージ文字列を取得します。
	 * @param num  メッセージ番号
	 * @param params  メッセージにセットするパラメータ
	 * @return フォーマット用文字列
	 </jp>*/
	/**<en>
	 * Retrieves the formatted message string.
	 * @param num  Pessage number
	 * @param params  Parameter to set for the message
	 * @return Formatting string
	 </en>*/
	public String getMessage(int num, Object[] params)
	{
		String key = new DecimalFormat("0000000").format(num) ;
		return MessageResources.getText(key, params);
		//String msgstr = SimpleFormat.format(getFormatMessage(num), params) ;
		//return (msgstr) ;
	}


	/**<jp>
	 * メッセージがリソースに存在するかを確認します。<BR>
	 *
	 * @param msg  メッセージ番号とメッセージにセットするパラメータを<CODE>DELIM</CODE>区切りでもらいます。
	 * @return true 存在する    false 存在しない
	 </jp>*/
	/**<en>
	 * Verifies if the message resides in resource.<BR>
	 *
	 * @param msg  Message number and the parameter to be set to the message will be provided, devided by
	 * <CODE>DELIM</CODE>.
	 * @return true It resides.    false It does not resides.
	 </en>*/
	public boolean isMessage(String msg)
	{
		boolean isMsg = false;
		try
		{
	     	StringTokenizer stk = new StringTokenizer(msg, DELIM, false);

	     	//<jp> メッセージ番号</jp>
	     	//<en> Message number</en>
	     	int num = Integer.parseInt(stk.nextToken());

			//<jp> メッセージ取得可能か試してみる</jp>
			//<en>Check if the message is retrievable.</en>
			getFormatMessage(num);

			isMsg = true;
			return isMsg;
		}
		//<jp> メッセージの取得が失敗した場合</jp>
		//<en>If the retrieval of the message failed,</en>
		catch (Exception e)
		{
			return isMsg;
		}
	}



	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**<jp>
	 * 利用するロケールを指定します。
	 * @param   locale  ロケール
	 * @return    Comment for return value
	 </jp>*/
	/**<en>
	 * It specifies the locale to use.
	 * @param   locale  Locale
	 </en>*/
	private void setLocale(Locale locale)
	{
		wLocale = locale ;
	}

	/**<jp>
	 * 現在のロケールを取得します。
	 * @return    現在のロケール
	 </jp>*/
	/**<en>
	 * It retrieves the current locale.
	 * @return    Current locale
	 </en>*/
	public Locale getLocale()
	{
		return (wLocale) ;
	}

	/**<jp>
	 * 利用するリソースを指定します。
	 * @param   res  リソース名
	 </jp>*/
	/**<en>
	 * It specifies the resource to use.
	 * @param   res  Reource name
	 </en>*/
	private void setResourceName(String res)
	{
		wResourceName = res ;
	}

	/**<jp>
	 * 現在のリソース名を取得します
	 * @return    リソース名
	 </jp>*/
	/**<en>
	 * It retrieves the current resource name.
	 * @return    Resource name
	 </en>*/
    public String getResourceName()
	{
		return (wResourceName) ;
	}

	// for debug method ----------------------------------------------



}
//end of class

