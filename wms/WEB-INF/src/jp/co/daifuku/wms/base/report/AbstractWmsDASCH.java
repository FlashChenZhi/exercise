// $Id: AbstractWmsDASCH.java 849 2008-10-28 09:07:56Z kishimoto $
package jp.co.daifuku.wms.base.report;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.LocaleUtils;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.foundation.da.AbstractDASCH;
import jp.co.daifuku.wms.base.util.DbDateUtil;


/**
 * 帳票データ作成･印刷の共通処理を定義したクラスです。<BR>
 * 各パッケージのDASCHクラスはこのクラスを継承してください。<BR>
 *
 * @version $Revision: 849 $, $Date: 2008-10-28 18:07:56 +0900 (火, 28 10 2008) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: kishimoto $
 */


public abstract class AbstractWmsDASCH
        extends AbstractDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    private Date _printDate = null;

    private String _userId = "";

    private String _userName = "";

    private String _dsNo = "";

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 問い合わせ処理に必要な情報をセットしてインスタンスに保持します。
     * 
     * @param conn データベースコネクション
     * @param parent 呼び出し元クラス<br>
     *   null が指定された場合は、自身が設定されます。
     * @param locale 対象ロケール<br>
     *   null が指定された場合は、デフォルト・ロケールが設定されます。
     * @param ui ユーザ情報
     */
    protected AbstractWmsDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);

        setConnection(conn);
        setParent(parent);
        setLocale(locale);
        setUserInfo(ui);

        try
        {
            _printDate = DbDateUtil.getTimeStamp();
        }
        catch (Exception e)
        {

        }
        
        setPart11ListHeader(ui);
        
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 保持しているロケールを返します。
     * 
     * @return 保持しているロケールを返します。
     */
    public Locale getLocale()
    {
        Locale locale = super.getLocale();
        
        if (locale == null)
        {
            return Locale.getDefault();
        }
        else
        {
            try
            {
                return LocaleUtils.fullLocale(locale);
            }
            catch (Exception e)
            {
                return Locale.getDefault();
            }
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 帳票発行日時を取得します。<BR>
     * @return 帳票発行日時
     */
    protected Date getPrintDate()
    {
        return _printDate;
    }
    
    /**
     * ユーザIDを取得します
     * @return ユーザID
     */
    protected String getUserId()
    {
        return _userId;
    }
    
    /**
     * ユーザ名称を取得します
     * @return ユーザ名称
     */
    protected String getUserName()
    {
        return _userName;
    }
    
    /**
     * DS No.を取得します
     * @return DS No.
     */
    protected String getDsNumber()
    {
        return _dsNo;
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 帳票レイアウトにPART11用のヘッダー編集を行います。
     * PART11パッケージが導入されていない場合は、スペース編集されます。
     * 
     * @param   userInfo    ユーザ定義情報
     */
    private void setPart11ListHeader(DfkUserInfo userInfo)
    {
        if (P11Config.isPart11Log())
        {
            // ユーザ名称がnullまたは空の場合
            if (StringUtil.isBlank(userInfo.getUserName()))
            {
                _dsNo = userInfo.getDsNumber();
                _userId = userInfo.getUserId();
                _userName = "";
            }
            // ユーザ名称がセットされている場合
            else
            {
                _dsNo = userInfo.getDsNumber();
                _userId = userInfo.getUserId();
                _userName = userInfo.getUserName();
            }
        }
        else
        {
            _dsNo = "";
            _userId = "";
            _userName = "";
        }
    }


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractWmsDASCH.java 849 2008-10-28 09:07:56Z kishimoto $";
    }
}
