// $Id: ToolMenuText.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.common ;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 品名コードなど画面に表示するテキストボックス、ラベルにおけるINPUTタグのSIZE、MAXLENGTHを
 * リソースファイルに定義し、このクラスのメソッドから取得します。<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/15</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Defines the size and max. length of input tags of list boxes/labels, which will be displayed
 * on the screen for item code, etc., in the resource file and retrives via methods of this class.<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/15</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolMenuText
{
    // Class fields --------------------------------------------------
    /**<jp>
     * デフォルトのリソースファイル名
     </jp>*/
    /**<en>
     * Name of default resource file
     </en>*/
    public static final String DEFAULT_RESOURCE = "MenuText" ;
    /**<jp>
     * 画面表示項目とデータ部分の区切り文字
     </jp>*/
    /**<en>
     * Delimiter between the displaying item for screen and the data.
     </en>*/
    private static final String SEPARATE = "@";
    /**<jp>
     * KEYの区切り文字。デフォルトは"="です。
     </jp>*/
    /**<en>
     * Delimiter of KEY. Default delimiter is "=".
     </en>*/
    public static final String wSeparate = "=";
    // Class variables -----------------------------------------------
    /**<jp>
     * キーを保持します。
     </jp>*/
    /**<en>
     * Preserve keys.
     </en>*/
    protected String wKey = "" ;
    /**<jp>
     * ヴァリューを保持します。
     </jp>*/
    /**<en>
     * Preserve the values.
     </en>*/
    protected String wValue = "" ;
    /**<jp>
     * 表示順を保持します。
     </jp>*/
    /**<en>
     * Preserve the order of display.
     </en>*/
    protected String wDispNo = "" ;
    /**<jp>
     * 権限（管理者）を保持します。
     </jp>*/
    /**<en>
     * Preserve the authority (administrator).
     </en>*/
    protected String wAdmin = "" ;
    /**<jp>
     * 権限（メンテナンス者）を保持します。
     </jp>*/
    /**<en>
     * Preserve the autority (maintenance conductor)
     </en>*/
    protected String wMaintenance = "" ;
    /**<jp>
     * 権限（作業者）を保持します。
     </jp>*/
    /**<en>
     * Preserve the autority (work operator).
     </en>*/
    protected String wWork = "" ;
    /**<jp>
     * 権限（観覧者）を保持します。
     </jp>*/
    /**<en>
     * Preserve the autority (visitor).
     </en>*/
    protected String wShow = "" ;
    /**<jp>
     * フレーム名を保持します。
     </jp>*/
    /**<en>
     * Preserve the name of the frame.
     </en>*/
    protected String wFrame = "" ;
    /**<jp>
     * ファイルパスを保持します。
     </jp>*/
    /**<en>
     * Preserve the file path.
     </en>*/
    protected String wFilePath = "" ;

    // Public Class method -------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * DispMessageリソースファイルを読み込み、文字列の
     * 置き換えを行わない場合はこのメソッドを呼び出して下さい。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Retrieve the contents of paramter based on the key.
     * Read the DispMessage resource file, and if the strings are not switched,
     * please call this method.
     * @param key  :key of the retrieving parameter.
     * @return     :string representation of parameter
     </en>*/
    public static String getText(String key)
    {
        return (getText(DEFAULT_RESOURCE, key, null)) ;
    }

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param key  取得するパラメータのキー
     * @param request <code>HttpServletRequest</code>
     *                DispMessageリソースファイルの読み込みに必要な変数
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Retrieve the contents of paramter based on the key.
     * @param key  :key of the retrieving parameter.
     * @param request <code>HttpServletRequest</code>
     *                the variable required to read the DispMessage resource file
     * @return     :string representation of parameter
     </en>*/
    public static String getText(String key, HttpServletRequest request)
    {
        return (getText(DEFAULT_RESOURCE, key, request)) ;
    }

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param resource  取得するパラメータのリソースファイル名
     * @param key  取得するパラメータのキー
     * @param request <code>HttpServletRequest</code>
     *                DispMessageリソースファイルの読み込みに必要な変数
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Retrieve the contents of paramter based on the key.
     * @param resource  :name of the resource file of retrieving parameter
     * @param key       :key of the retrieving parameter.
     * @param request <code>HttpServletRequest</code>
     *                 the variable required to read the DispMessage resource file
     * @return   :string representation of parameter
     </en>*/
    public static String getText(String resource, String key, HttpServletRequest request)
    {
        ResourceBundle rb = null;
        if (request == null)
        {
            rb = ResourceBundle.getBundle(resource, Locale.getDefault()) ;
        }
        else
        {
            rb = ResourceBundle.getBundle(resource, request.getLocale()) ;
        }
        String str = rb.getString(key) ;
        int start = str.indexOf(SEPARATE);
        if (start < 0)
        {
            return str;
        }
        else
        {
            return str.substring(0, str.indexOf(SEPARATE));
        }
    }
    // Constructors --------------------------------------------------
    /**<jp>
     * コンストラクタ
     </jp>*/
    /**<en>
     * Constructor
     </en>*/
    public ToolMenuText()
    {
    }
    /**<jp>
     * コンストラクタ
     </jp>*/
    /**<en>
     * Constructor
     </en>*/
    public ToolMenuText(String key, String value, String dispNo, String admin, String main, String work, String show, String frame, String filepath)
    {
        wKey = key;
        wValue = value;
        wDispNo = dispNo ;
        wAdmin = admin ;
        wMaintenance = main;
        wWork = work;
        wShow = show;
        wFrame = frame;
        wFilePath = filepath;
    }
    /**<jp>
     * コンストラクタ
     * @param param 
     </jp>*/
    /**<en>
     * Constructor
     * @param param 
     </en>*/
    public ToolMenuText(String param)
    {
        String w_chk0  = wSeparate;
        int    start0 = param.indexOf(w_chk0) ;
        if (start0 >= 0)
        {
            wKey =  (param.substring(0, start0)) ;
        }

        String w_chk_value  = "@";
        int    start_value = param.indexOf(w_chk_value) ;
        if (start_value >= 0)
        {
            wValue =  (param.substring(start0 + 1, start_value)) ;
        }
        else
        {
            wValue = param.substring(start0 + 1, param.length());
        }
        
        String w_chk2  = "DISPNO=\"" ;
        int    start2 = param.indexOf(w_chk2) ;
        if (start2 >= 0)
        {
            int    end2   = param.indexOf("\"", start2 + w_chk2.length()) ;
            wDispNo =  (param.substring(start2 + w_chk2.length(), end2)) ;
        }

        String w_chk3  = "ADMIN=\"" ;
        int    start3 = param.indexOf(w_chk3) ;
        if (start3 >= 0)
        {
            int    end3   = param.indexOf("\"", start3 + w_chk3.length()) ;
            wAdmin =  (param.substring(start3 + w_chk3.length(), end3)) ;
        }

        String w_chk4  = "MAINTENANCE=\"" ;
        int    start4 = param.indexOf(w_chk4) ;
        if (start4 >= 0)
        {
            int    end4   = param.indexOf("\"", start4 + w_chk4.length()) ;
            wMaintenance =  (param.substring(start4 + w_chk4.length(), end4)) ;
        }

        String w_chk5  = "WORK=\"" ;
        int    start5 = param.indexOf(w_chk5) ;
        if (start5 >= 0)
        {
            int    end5   = param.indexOf("\"", start5 + w_chk5.length()) ;
            wWork =  (param.substring(start5 + w_chk5.length(), end5)) ;
        }

        String w_chk6  = "SHOW=\"" ;
        int    start6 = param.indexOf(w_chk6) ;
        if (start6 >= 0)
        {
            int    end6   = param.indexOf("\"", start6 + w_chk6.length()) ;
            wShow =  (param.substring(start6 + w_chk6.length(), end6)) ;
        }

        String w_chk7  = "FRAME=\"" ;
        int    start7 = param.indexOf(w_chk7) ;
        if (start7 >= 0)
        {
            int    end7   = param.indexOf("\"", start7 + w_chk7.length()) ;
            wFrame =  (param.substring(start7 + w_chk7.length(), end7)) ;
        }
        String w_chk8  = "FILEPATH=\"" ;
        int    start8 = param.indexOf(w_chk8) ;
        if (start8 >= 0)
        {
            int    end8   = param.indexOf("\"", start8 + w_chk8.length()) ;
            wFilePath =  (param.substring(start8 + w_chk8.length(), end8)) ;
        }
        
    }
    // Public methods ------------------------------------------------
    /**<jp>
     * キーを設定します。
     * @param key キー
     </jp>*/
    /**<en>
     * Set the key.
     * @param key :key
     </en>*/
    public void setKey(String key)
    {
        wKey = key;
    }

    /**<jp>
     * キーを取得します。
     * @return キー
     </jp>*/
    /**<en>
     * Retrieve the key.
     * @return :key
     </en>*/
    public String getKey()
    {
        return wKey;
    }
    /**<jp>
     * ヴァリューを設定します。
     * @param value ヴァリュー
     </jp>*/
    /**<en>
     * Set the value.
     * @param value :value
     </en>*/
    public void setValue(String value)
    {
        wValue = value;
    }

    /**<jp>
     * ヴァリューを取得します。
     * @return ヴァリュー
     </jp>*/
    /**<en>
     * Retrieve the value.
     * @return :value
     </en>*/
    public String getValue()
    {
        return wValue;
    }

    /**<jp>
     * 表示順を設定します。
     * @param dispNo 表示順
     </jp>*/
    /**<en>
     * Set the order of display.
     * @param dispNo :the order of display
     </en>*/
    public void setDispNo(String dispNo)
    {
        wDispNo = dispNo;
    }

    /**<jp>
     * 表示順を取得します。
     * @return 表示順
     </jp>*/
    /**<en>
     * Retrieve the order of display.
     * @return :the order of display
     </en>*/
    public String getDispNo()
    {
        return wDispNo;
    }

    /**<jp>
     * 権限（管理者）を設定します。
     * @param admin 権限（管理者）
     </jp>*/
    /**<en>
     * Set the authority (administrator).
     * @param admin :authority (administrator)
     </en>*/
    public void setAdmin(String admin)
    {
        wAdmin = admin;
    }

    /**<jp>
     * 権限（管理者）を取得します。
     * @return 権限（管理者）
     </jp>*/
    /**<en>
     * Retrieve the authority (administrator).
     * @return :authority (administrator)
     </en>*/
    public String getAdmin()
    {
        return wAdmin;
    }

    /**<jp>
     * 権限（メンテナンス者）を設定します。
     * @param main 権限（メンテナンス者）
     </jp>*/
    /**<en>
     * Set the authority (maintenance conductor).
     * @param main :authority (maintenance conductor)
     </en>*/
    public void setMaintenance(String main)
    {
        wMaintenance = main;
    }

    /**<jp>
     * 権限（メンテナンス者）を取得します。
     * @return 権限（メンテナンス者）
     </jp>*/
    /**<en>
     * Retrieve the authority (maintenance conductor).
     * @return :authority (maintenance conductor)
     </en>*/
    public String getMaintenance()
    {
        return wMaintenance;
    }

    /**<jp>
     * 権限（作業者）を設定します。
     * @param work 権限（作業者）
     </jp>*/
    /**<en>
     * Set the authority (operator).
     * @param work :authority (operator)
     </en>*/
    public void setWork(String work)
    {
        wWork = work;
    }

    /**<jp>
     * 権限（作業者）を取得します。
     * @return 権限（作業者）
     </jp>*/
    /**<en>
     * Retrieve the authority (operator).
     * @return :authority (operator)
     </en>*/
    public String getWork()
    {
        return wWork;
    }

    /**<jp>
     * 権限（観覧者）を設定します。
     * @param show 権限（観覧者）
     </jp>*/
    /**<en>
     * Set the authority (visitor).
     * @param show :authority (visitor)
     </en>*/
    public void setShow(String show)
    {
        wShow = show;
    }

    /**<jp>
     * 権限（観覧者）を取得します。
     * @return 権限（観覧者）
     </jp>*/
    /**<en>
     * Retrieve the authority (visitor).
     * @return :authority (visitor)
     </en>*/
    public String getShow()
    {
        return wShow;
    }
    /**<jp>
     * フレーム名を設定します。
     * @param fp フレーム名
     </jp>*/
    /**<en>
     * Set the name of the frame.
     * @param fp :name of the frame
     </en>*/
    public void setFilePath(String fp)
    {
        wFilePath = fp;
    }

    /**<jp>
     * フレーム名を取得します。
     * @return フレーム名
     </jp>*/
    /**<en>
     * Retrieve the name of the frame.
     * @return :the name of the frame.
     </en>*/
    public String getFrame()
    {
        return wFrame;
    }
    /**<jp>
     * フレーム名を設定します。
     * @param fn フレーム名
     </jp>*/
    /**<en>
     * Set the name of the frame.
     * @param fn :the name of the frame.
     </en>*/
    public void setFrame(String fn)
    {
        wFrame = fn;
    }

    /**<jp>
     * ファイルパスを取得します。
     * @return ファイルパス
     </jp>*/
    /**<en>
     * Retrieve the file path.
     * @return :file path
     </en>*/
    public String getFilePath()
    {
        return wFilePath;
    }

    /**<jp>
     * MenuText型をリソースファイルに書き込むとき用に一行であらわす。
     </jp>*/
    /**<en>
     * Represent within 1 line for the case writing MenuText type in the resouce file.
     </en>*/
    public String toString()
    {
        if (wKey.length() != 0)
        {
            if (wKey.substring(1,5).equals("0000"))
            {
                return wValue;
            }
            else
            {
                return wValue+"@DISPNO=\""+getDispNo()+"\"ADMIN=\""+getAdmin()+"\"MAINTENANCE=\""+
                    getMaintenance()+"\"WORK=\""+getWork()+"\"SHOW=\""+getShow()+"\"FRAME=\""+getFrame()+"\"FILEPATH=\""+getFilePath()+"\"";
            }
        }
        return "";
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Debug methods -------------------------------------------------

}
//end of class
