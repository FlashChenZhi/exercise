// $Id: ToolSessionRet.java 7308 2010-03-01 02:49:02Z okayama $
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolDatabaseFinder;

/**<jp>
 * 検索を行い結果を保持するクラスです。
 * リストボックスの表示を手助けするクラスで、ToolSessionStationRetクラスなどはこのクラスを継承して作る必要があります。<BR>
 * ToolDatabaseFinderインスタンス生成はこのクラス内で行いません。<BR>
 * 画面（JSP）は、セッションに生成したToolSession***クラスのインスタンスを保持し、使用後そのセッションをRemoveして下さい。<BR>
 * 検索メソッドはHandlerクラスのfindを使用しないで下さい。<BR>
 * findメソッドは検索結果全てのイスタンスを生成するため、パフォーマンスが落ちます。<BR>
 * 必ずFinderクラスを使用してインスタンスは必要な分のみ生成するようにして下さい。<BR>
 * 新規でリストボックスを作成する場合、ToolEntity、ToolFinder、ToolSession***クラス、***Info.jsp、***Ret.jspを作成する必要があります。<BR>
 * 作成に関する詳細情報はJSPリファレンスを参照してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/03/01</TD><TD>sawa</TD><TD><code>ToolDatabaseFinder</code>変数追加。しかし、
 *                                         <code>ToolSessionRet</code>を継承しているサブクラスは未対応</TD></TR>
 * <TR><TD>2002/08/13</TD><TD>sawa</TD><TD>LISTBOX検索結果上限数 追加</TD></TR>
 * <TR><TD>2003/01/29</TD><TD>miyashita</TD><TD>getFinder()メソッド追加。
 *                                         これによりToolSessionRetを継承しているクラスからgetFinder()メソッドを削除。</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7308 $, $Date: 2010-03-01 11:49:02 +0900 (月, 01 3 2010) $
 * @author  $Author: okayama $
 </jp>*/
/**<en>
 * This class conducts search and preserves results.
 * This class supports the display of list box; class such as ToolSessionStationRet needs to
 * be created by inheriting this class.<BR>
 * Instance of ToolDatabaseFinder will not be generated in this class.<BR>
 * Please let the screen (JSP) preserve the instance of ToolSession*** class generated in session,
 * then please remove the session after use of this class.<BR>
 * Please do not use 'find' in Handler class as a search method.<BR>
 * It leads to inferior performance since 'find' method will generates instances of all the data
 * from search result.<BR>
 * Please always use Finder class so that only as much instances as requried will be generated.<BR>
 * When creating aaa new list box, it is necessary to create the ToolEntity, ToolFinder, ToolSession***
 * class, ***Info.jsp and ***Ret.jsp.<BR>
 * Please see JSP reference for detailed information about the creation.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/03/01</TD><TD>sawa</TD><TD><code>ToolDatabaseFinder</code> variable added.
 *                                         but not for sub classes derived from <code>ToolSessionRet</code>.</TD></TR>
 * <TR><TD>2002/08/13</TD><TD>sawa</TD><TD>upper limit of LISTBOX search results added.</TD></TR>
 * <TR><TD>2003/01/29</TD><TD>miyashita</TD><TD>getFinder() method is added.
 *                                         Therefore getFinder() was deleted from the class derived from ToolSessionRet.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7308 $, $Date: 2010-03-01 11:49:02 +0900 (月, 01 3 2010) $
 * @author  $Author: okayama $
 </en>*/
public class ToolSessionRet
{
    // Class fields --------------------------------------------------
    /**<jp>
     * LISTBOX 検索結果上限数
     </jp>*/
    /**<en>
     * LISTBOX uppoer limit of search results
     </en>*/
    public static final int MAXDISP = 10000;
    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public static String wDelim = MessageResource.DELIM ;


    // Class variables -----------------------------------------------
    /**<jp>
     * LogWriteするための準備
     * StackTraceの内容をLogにWriteする時使用します。
     </jp>*/
    /**<en>
     * Preparation for LogWrite
     * This is used when writing the log for the detail of StackTrace.
     </en>*/
    protected StringWriter      sw           = new StringWriter();

    /**<jp>
     * LogWriteするための準備
     * StackTraceの内容をLogにWriteする時使用します。
     </jp>*/
    /**<en>
     * Preparation for LogWrite
     * This is used when writing the log for the detail of StackTrace.
     </en>*/
    protected PrintWriter       pw           = new PrintWriter(sw);

    /**<jp>
     * LogWriteするための準備
     * StackTraceの内容をLogにWriteする時使用します。
     </jp>*/
    /**<en>
     * Preparation for LogWrite
     * This is used when writing the log for the detail of StackTrace.
     </en>*/
    protected String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");

    /**<jp>
     * <code>Connection</code>
     </jp>*/
    /**<en>
     * <code>Connection</code>
     </en>*/
    protected Connection wConn;

    /**<jp>
     * <code>ToolDatabaseFinder</code>参照を保持します。<BR>
     * この<code>ToolSessionRet</code>を継承している全クラスは現在各クラスごとにFinderの参照を持っていますが、
     * いずれはこの変数をキャストして各クラスが保持するように仕組みを修正します。
     </jp>*/
    /**<en>
     * Preserve the <code>ToolDatabaseFinder</code> reference. <BR>
     * All the classes which inherit this <code>ToolSessionRet</code> currently own reference of Finder
     * of each class. In future the system will be corrected so that the variables will be casted and 
     * respective classes will preserve sthem.
     </en>*/
    protected ToolDatabaseFinder wFinder;

    /**<jp>
     * 表示条件
     * リストボックス表示件数をシステム定義から取得
     </jp>*/
    /**<en>
     * Conditions for the display
     * Retrieve teh number of list boxes to display from system deficnition.
     </en>*/
    protected int wCondition  = 100;

    /**<jp>
     * 検索結果から選択されると、この指定されたページに飛びます。
     </jp>*/
    /**<en>
     * If a data is selected in search result, screen will jump to this specified page.
     </en>*/
    protected String wNextPage;

    /**<jp>
     * 検索結果から選択されると、この指定されたフレームに飛びます。
     </jp>*/
    /**<en>
     *If a data is selected in search result, screen will jump to this specified frame.
     </en>*/
    protected String wFrameName;

    /**<jp>
     * 検索リストを呼びに行った基のページのアドレスを表します。
     </jp>*/
    /**<en>
     * Displays the address for the original page where search list was called.
     </en>*/
    protected String wBasePage;

    /**<jp>
     * 検索結果件数
     </jp>*/
    /**<en>
     * number of search results
     </en>*/
    protected int wLength;

    /**<jp>
     * 現在の表示済件数
     </jp>*/
    /**<en>
     * Current number of displayed data
     </en>*/
    protected int wCurrent;

    /**<jp>
     * 表示開始位置
     </jp>*/
    /**<en>
     * Start index of data to dispaly
     </en>*/
    protected int wStartpoint ;

    /**<jp>
     * 表示終了位置
     </jp>*/
    /**<en>
     * End index of data to dispaly
     </en>*/
    protected int wEndpoint ;

    /**<jp>
     * 最終ページでの件数の端数
     </jp>*/
    /**<en>
     * Fractional data in final page.
     </en>*/
    protected int wFraction ;

    // Class method --------------------------------------------------
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
        return ("$Revision: 7308 $,$Date: 2010-03-01 11:49:02 +0900 (月, 01 3 2010) $") ;
    }

    /**
     * データベース接続をクローズします
     * @throws Exception　例外発生時に通知します。
     */
    public void closeConnection() throws Exception
    {
        if (wConn != null)
        {
            wConn.close();
        }
    }
    
    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>ToolSessionRet</CODE>を構築します。
     </jp>*/
    /**<en>
     * Constructs new <CODE>ToolSessionRet</CODE>.
     </en>*/
    public ToolSessionRet()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 表示条件を返します。
     * @return wCondition
     </jp>*/
    /**<en>
     * Return the condition for data display.
     * @return wCondition
     </en>*/
    public int getCondition()
    {
        return wCondition ;
    }
    /**<jp>
     * ToolDatabaseFinderインスタンスを返します。
     * @return wFinder
     </jp>*/
    /**<en>
     * Return the ToolDatabaseFinder instance.
     * @return wFinder
     </en>*/
    public ToolDatabaseFinder getFinder()
    {
        return wFinder;
    }
    /**<jp>
     * 指定されたページを得られます。
     * @return wNextPage
     </jp>*/
    /**<en>
     * Specified page will be retrieved.
     * @return wNextPage
     </en>*/
    public String getNextPage()
    {
        return wNextPage;
    }

    /**<jp>
     * 指定されたフレームを得られます。
     * @return wFrameName
     </jp>*/
    /**<en>
     * Specified frame will be retrieved.
     * @return wFrameName
     </en>*/
    public String getFrameName()
    {
        return wFrameName;
    }

    /**<jp>
     * 検索した基のページを表します。
     * @return wBasePage
     </jp>*/
    /**<en>
     * Display the original searched page.
     * @return wBasePage
     </en>*/
    public String getBasePage()
    {
        return wBasePage;
    }

    /**<jp>
     * 表示件数を得られます。
     * @return wLength
     </jp>*/
    /**<en>
     * Numebr of data display will be retrieved.
     * @return wLength
     </en>*/
    public int getLength()
    {
        return wLength;
    }

    /**<jp>
     * 終了位置を得られます。
     * @return wLength
     </jp>*/
    /**<en>
     * End index of data will be retrieved.
     * @return wLength
     </en>*/
    public int getEnd()
    {
        return wEndpoint;
    }

    /**<jp>
     * 検索結果の件数についてのチェックを行ないます。<BR>
     * チェックエラーの場合メッセージ番号を返します。正常の場合は
     * nullを返します。<BR>
     * <チェック内容><BR>
     * ０件ではないか<BR>
     * 検索結果件数が表示最大数を超えていないか<BR>
     * @return メッセージ番号
     </jp>*/
    /**<en>
     * Check the number of search results.<BR>
     * It returns the message no. in case the check error occurred. 
     * If checked normally, it will return null.<BR>
     * < detail of the check ><BR>
     * whether/not there was no data.<BR>
     * whether/not the search results exceeded the max number of data display.<BR>
     * @return :message no.
     </en>*/
    public String checkLength()
    {

        //<jp> データ件数0件のときは「対象データはありませんでした。」と表示</jp>
        //<en> If there was no data, it displays 'there was no target data.'</en>
        if (getLength() == 0)
        {
            return "6123001";
        }


        //<jp> 表示最大数を超えた場合</jp>
        //<en> If the data exceeded the max number of data display,</en>
        if (getLength() > MAXDISP)
        {
            String delim = MessageResource.DELIM ;
            //<jp> {0}件該当しました。件数が{1}件を超えるため、検索条件を絞り込んで下さい。</jp>
            //<en> Corresponding data {0}. Please narrow the search as number of data exceeds {1}.</en>
            return "6123114" + delim + getLength() + delim + MAXDISP;
        }

        return null;
    }

    /**<jp>
     * 現在の表示済件数を元にデータ表示Noが得られます。
     * @return データ表示No
     </jp>*/
    /**<en>
     * Number of data display will be retrieved based on current number of displayed data.
     * @return :number of data display
     </en>*/
    public int getCurrent()
    {
        return wStartpoint;
    }

    /**<jp>
     * 検索結果の最後のページはデータ件数が端数になる時があるので
     * 端数を保持しておかないとデータ表示Noがおかしくなる。
     * @return 端数
     </jp>*/
    /**<en>
     * The last page of search results may show fractional numbers of data; 
     * it is necessary that fraction should be preserved, or incorrect number of data display 
     * may be provided.
     * @return :fractional number 
     </en>*/
    public int getFraction()
    {
        return wFraction;
    }

    /**<jp>
     * 検索結果の表示位置（行）をセットします。<BR>
     * <PRE>
     * 例 ItemSearchRet.jsp
     * 検索結果の表示位置（行）を取得
     * String scrtrans     = request.getParameter("SCRTRANS");
     * 
     * SessionItemRet 取得
     * SessionItemRet itemret = (SessionItemRet)session.getAttribute( "ItemRet" );
     * itemret.setScreenTrans(scrtrans);
     * </PRE>
     * @param scrtrans ***Ret.jsp画面で取得した値（リクエスト・パラメータである"SCRTRANS"）
     </jp>*/
    /**<en>
     * Set the index(line)of search results to display. <BR>
     * <PRE>
     * ex. ItemSearchRet.jsp
     * Retrieve the index(line) of search results to display. 
     * String scrtrans     = request.getParameter("SCRTRANS");
     * 
     * Retrieve SessionItemRet.
     * SessionItemRet itemret = (SessionItemRet)session.getAttribute( "ItemRet" );
     * itemret.setScreenTrans(scrtrans);
     * </PRE>
     * @param scrtrans :the value retrieved via ***Ret.jsp screen ("SCRTRANS" which is a request parameter)
     </en>*/
    public void setScreenTrans(String scrtrans)
    {
        try
        {
            if (wCurrent == 0)
            {
                wStartpoint = 0;
            }
            else
            {
                wStartpoint = Integer.parseInt(scrtrans);
            }
            wEndpoint   = wStartpoint + wCondition;
        }
        catch (Exception e)
        {
        }
    }

    /**<jp>
     * ListBoxで押されたボタンの種類によって検索結果の表示位置（行）をセットします。<BR>
     * ＜ボタンアクションの種類＞<BR>
     * first   最初のページへ<BR>
     * prvious 前のページへ<BR>
     * next    次のページへ<BR>
     * last    最後のページへ<BR>
     * @param process ListBox画面で押されたボタンアクションの種類
     </jp>*/
    /**<en>
     * Set the index(line) of search results to display according to the button type
     * pressed in ListBox.<BR>
     * < type of button actions > <BR>
     * first   :to the first page<BR>
     * prvious :to the previous pate<BR>
     * next    :to the next page<BR>
     * last    :to the last page<BR>
     * @param process :type of button action pressed on ListBox screen.
     </en>*/
    public void setActionName(String process)
    {
        try
        {
            //<jp> 余りが0のときは検索結果からwConditionを引く</jp>
            //<en> Subtract 'wCondition' from the search result in case the remainder is 0.</en>
            int remainder    = getLength() %  wCondition;
            if (remainder == 0)
            {
                remainder = wCondition;
            }
            int end    = getLength() - remainder;
            //<jp> 「前へ」・「次へ」の表示で、最終ページを表示する時は"端数"を加算</jp>
            //<en> Add fractional number if displaying the last page by indicating 'to previous' </en>
            //<en> and 'to next'.</en>
            int back   = wEndpoint - (wCondition * 2);
            if (wCurrent == getLength())
            {
                back = wEndpoint - (wCondition + getFraction());
            }

            if (process.equals("first"))
            {
                wStartpoint = 0;
            }
            else if (process.equals("previous"))
            {
                //<jp> 「前へ」の表示で、0以下は表示しない</jp>
                //<en> Let no data less than 0 display by indicating 'to previous'.</en>
                if (back > 0)
                {
                    wStartpoint = back;
                }
                else
                {
                    wStartpoint = 0;
                }
            }
            else if (process.equals("next"))
            {
                //<jp> 「次へ」の表示で、MAX値以上は表示しない</jp>
                //<en> Let no data greater than max value display by indicating 'to next'.</en>
                if (wEndpoint < getLength())
                {
                    wStartpoint = wEndpoint;
                }
            }
            else if (process.equals("last"))
            {
                wStartpoint = end;
            }
            
            wEndpoint   = wStartpoint + wCondition;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
