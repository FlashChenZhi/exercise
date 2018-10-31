// $Id: OperationMsg.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.toolmenu ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.StringTokenizer;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;

/**<jp>
 * オペレーションメッセージを保持します。
 * 画面から入庫、出庫設定を行った時などにユーザにメッセージを通知するため、
 * 各画面はOperationMsgインスタンスにメッセージ内容、フォントカラーをセットし
 * "opmsg"という名前でセッションにインスタンスを保持して下さい。
 * 画面の設定項目の入力チェックにはInputDataCheckerを使用し、<BR>
 * 同じくInputDataCheckerインスタンスを"opmsg"という名前で保持することで
 * MessageCtrl.jspにメッセージを表示することが可能です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>OperationMsg(String msg, Locale locale)コンストラクタ追加</TD></TR>
 * <TR><TD>2002/06/03</TD><TD>kawashima</TD><TD>OperationMsg(String msg, Locale locale, String default_Msg)コンストラクタ追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class preserves the operation message.
 * This is used to notifiy the users with message when storage/retrieval was set up via screen.
 * Each display should set the OperationMsg isntance with contents and font color of the message 
 * and preserve the instance by the name 'opmsg" in the session.
 * It is possible to display the message on MessageCtrl.jsp by using InputDataChecker to check the
 * input data of set items and by preserving the InputDataChecker instance by the name "opmsg".
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>OperationMsg(String msg, Locale locale)constructor added</TD></TR>
 * <TR><TD>2002/06/03</TD><TD>kawashima</TD><TD>OperationMsg(String msg, Locale locale, String default_Msg)constructor added</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $
 * @author  $Author: admin $
 </en>*/
public class OperationMsg
{
    // Class fields --------------------------------------------------
    /**<jp>
     * メッセージ・カラー・コード(案内) 青
     </jp>*/
    /**<en>
     * Message color code (information) Blue
     </en>*/
    public static final int INFO_MSG = 0;
    /**<jp>
     * メッセージ・カラー・コード(注意) オレンジ
     </jp>*/
    /**<en>
     * Message color cod (caution) Orange
     </en>*/
    public static final int ATNT_MSG = 1;
    /**<jp>
     * メッセージ・カラー・コード(警告) 緑
     </jp>*/
    /**<en>
     * Message color cod (warning)  Green
     </en>*/
    public static final int WARN_MSG = 2;
    /**<jp>
     * メッセージ・カラー・コード(異常) 赤
     </jp>*/
    /**<en>
     * Message color cod (error) Red
     </en>*/
    public static final int ERR_MSG  = 3;

    // Class variables -----------------------------------------------
    /**<jp>
     * オペレーションメッセージを保持します
     </jp>*/
    /**<en>
     * Preserve the operation message.
     </en>*/
    protected String     operationmsg     = "";

    /**<jp>
     * メッセージの色を保持します
     </jp>*/
    /**<en>
     * Preserve the color of the message.
     </en>*/
    protected int        msgcolor         = 0;

    /**<jp>
     * JavaScriptのconfirm(MessageBox)を表示して確認をユーザに求める場合はこのFlagをtrueにして下さい。
     </jp>*/
    /**<en>
     * If displaying the confirm(MessageBox) of JavaScript in order to request the
     * confirmation for users, please set this flag 'true'.
     </en>*/
    protected boolean    showmsgbox         = false;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public OperationMsg()
    {
    }

    /**<jp>
     * メッセージ、メッセージカラーを引数にしてインスタンスを作成して下さい
     * @param msg         メッセージ
     * @param msgcolorNo  メッセージカラー
     </jp>*/
    /**<en>
     * Please create the instance by setting the messag and message color as parameters.
     * @param msg         :message 
     * @param msgcolorNo  :message color
     </en>*/
    public OperationMsg(String msg, int msgcolorNo)
    {
        this(msg, msgcolorNo, false);
    }

    /**<jp>
     * メッセージ、メッセージカラーを引数にしてインスタンスを作成して下さい
     * @param msg         メッセージ
     * @param msgcolorNo  メッセージカラー
     * @param flag  JavaScriptのconfirm(MessageBox)を表示して確認をユーザに求める場合はこのFlagをtrueにして下さい。
     </jp>*/
    /**<en>
     * Please create the instance by setting the messag and message color as parameters.
     * @param msg         :message 
     * @param msgcolorNo  :message color
     * @param flag  :if requesting users to confirm by displaying confirm(MessageBox) of JavaScript,
     * please set the Flag 'true'.
     </en>*/
    public OperationMsg(String msg, int msgcolorNo, boolean flag)
    {
        this.operationmsg   = msg;
        this.msgcolor         = msgcolorNo;
        this.showmsgbox     = flag;
    }

    /**<jp>
     * メッセージ番号を指定してメッセージ内容(operationmsg)と色(msgcolor)に値をセットします。
     * @param msg  メッセージ <I>メッセージ番号<I>と</I>パラメータ</I>を<CODE>MessageResource</CODE>の区切り文字で結合した文字列
     * @param locale ロケールの指定
     * @see jp.co.daifuku.common.LogMessage
     * @see jp.co.daifuku.common.MessageResource
     </jp>*/
    /**<en>
     * Set value of contents (operationmsg) and the color(msgcolor) of the message by
     * specifing the message no. 
     * @param msg  :the string which was made by concatenating the message<I>, message no.<I> and </I> parameter</I>,
     * </I> parameter</I>, using delimiters of <CODE>MessageResource</CODE>.
     * @param locale :specify the locale.
     * @see jp.co.daifuku.common.LogMessage
     * @see jp.co.daifuku.common.MessageResource
     </en>*/
    public OperationMsg(String msg, Locale locale)
    {
        setMessageInfo(msg, locale);
    }

    /**<jp>
     * メッセージ番号を指定してメッセージ内容(operationmsg)と色(msgcolor)に値をセットします。<BR>
     * Javaの例外をキャッチする可能性がある時にはこのコンストラクタを使用してください。<BR>
     * この場合、default_Msgで指定されたメッセージ番号（＋パラメータ）をメッセージの内容にセットします。
     * @param msg  メッセージ <I>メッセージ番号<I>と</I>パラメータ</I>を<CODE>MessageResource</CODE>の区切り文字で結合した文字列
     * @param locale ロケールの指定
     * @param default_Msg Javaの例外をキャッチした場合に表示するメッセージの<I>メッセージ番号<I>と
     *        </I>パラメータ</I>を<CODE>MessageResource</CODE>の区切り文字で結合した文字列
     * @see jp.co.daifuku.common.LogMessage
     * @see jp.co.daifuku.common.MessageResource
     </jp>*/
    /**<en>
     * Set the valud of message contents (operationmsg) and the color (msgcolor), by 
     * specifing the message no.<BR>
     * If there are possibility  that the exceptions of Java may be caught, plese use this constructor.<BR>
     * In that case, the message no. (+ parameter) specified by default_Msg should be set to
     * the contents of the message.
     * @param msg  :the string which was made by concatenating the message<I>, message no.<I> and </I> parameter</I>,
     * </I> parameter</I>, using delimiters of <CODE>MessageResource</CODE>.
     * @param locale :specify the locale.
     * @param default_Msg :The string made by connecting the <I>message no.<I> which will be displayed 
     * when Java exception is caught and the <I>parameter<I>, using delimiter of <CODE>MessageResource</CODE>.
     * @see jp.co.daifuku.common.LogMessage
     * @see jp.co.daifuku.common.MessageResource
     </en>*/
    public OperationMsg(String msg, Locale locale, String default_Msg)
    {
        MessageResource mr = new MessageResource(locale);

        //<jp> Javaの例外の場合</jp>
        //<en> In case of Java exception,</en>
        if (!mr.isMessage(msg))
        {
            msg = default_Msg;
        }
        setMessageInfo(msg, locale);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     *    表示したいMSGをゲット
     *  @return messeage
     </jp>*/
    /**<en>
     *    Get the message to be displayed.
     *  @return messeage
     </en>*/
    public String getValue()
    {
        return operationmsg;
    }

    /**<jp>
     *    表示したいMSGの Color名 をゲット
     *  @return messeage color
     </jp>*/
    /**<en>
     *    Get the color of the message to display.
     *  @return messeage color
     </en>*/
    public String getMsgColor()
    {
        /*<jp> 案内 </jp>*/
        /*<en> information </en>*/
        if (msgcolor == INFO_MSG)
        {
            return "INFORMATION";
        /*<jp> 注意 </jp>*/
        /*<en> caution </en>*/
        }
        else if (msgcolor == ATNT_MSG)
        {
            return "ATTENTION";
        /*<jp> 警告 </jp>*/
        /*<en> worning </en>*/
        }
        else if (msgcolor == WARN_MSG)
        {
            return "WARNING";
        /*<jp> 異常（障害） </jp>*/
        /*<en> error (trouble) </en>*/
        }
        else if (msgcolor == ERR_MSG)
        {
            return "ERROR";
        /* Program Error */
        }
        else
        {
            return "PURPLE";
        }
    }

    /**<jp>
     *    JavaScriptのconfirm(MessageBox)を表示して確認をユーザに求める場合のFlag
     *  @return JavaScriptのconfirm(MessageBox)を表示して確認をユーザに求める場合はtrue
     </jp>*/
    /**<en>
     *    Flag to be used when requesting user to confirm by displaying JavaScript confirm(MessageBox).
     *  @return :return true if requesting user to confirm by displaying JavaScript confirm(MessageBox).
     </en>*/
    public boolean getShowMsgbox()
    {
        return showmsgbox;
    }

    /**<jp>
     * 表示したいMSGをセット
     * @param msg         メッセージ
     * @param msgcolorNo  メッセージカラー
     </jp>*/
    /**<en>
     * Set the message to display.
     * @param msg         :message
     * @param msgcolorNo  :message color
     </en>*/
    public void setValue(String msg, int msgcolorNo)
    {
        /*<jp> nullだったら空白をセット </jp>*/
        /*<en> Set blank if null. </en>*/
        if (operationmsg == null)
        {
            operationmsg = "";
        }

        /*<jp> MSGをセット </jp>*/
        /*<en> Set the message. </en>*/
        operationmsg    = msg;
        /*<jp> MSG COLORをセット </jp>*/
        /*<en> Set the MSG COLOR. </en>*/
        msgcolor         = msgcolorNo;
    }

    /**<jp>
     * 表示したいMSGをセット
     * @param msg         メッセージ
     * @param msgcolorNo  メッセージカラー
     * @param flag  JavaScriptのconfirm(MessageBox)を表示して確認をユーザに求める場合はこのFlagをtrueにして下さい。
     </jp>*/
    /**<en>
     * Set the MSG to display.
     * @param msg         :message
     * @param msgcolorNo  :mesage color
     * @param flag  :Please set this Flag 'true' if requesting user to confirm by displaying JavaScript 
     * confirm(MessageBox).
     </en>*/
    public void setValue(String msg, int msgcolorNo, boolean flag)
    {
        /*<jp> nullだったら空白をセット </jp>*/
        /*<en> Set blank if null.</en>*/
        if (operationmsg == null)
        {
            operationmsg = "";
        }

        /*<jp> MSGをセット </jp>*/
        /*<en> Set the MSG. </en>*/
        operationmsg    = msg;
        /*<jp> MSG COLORをセット </jp>*/
        /*<en> Set the MSG COLOR. </en>*/
        msgcolor         = msgcolorNo;
        /*<jp> JavaScriptのconfirm(MessageBox)を表示して確認をユーザに求める場合のFlagをセット </jp>*/
        /*<en> Set the Flag true if requesting user to confirm by displaying </en>*/
        /*<en> JavaScript confirm(MessageBox). </en>*/
        showmsgbox      = flag;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * メッセージ番号を指定してメッセージ内容(operationmsg)と色(msgcolor)に値をセットします。
     * @param msg  メッセージ <I>メッセージ番号<I>と</I>パラメータ</I>を<CODE>MessageResource</CODE>の区切り文字で結合した文字列
     * @param locale ロケールの指定
     </jp>*/
    /**<en>
     * Set the valud of contents(operationmsg) and the color of the message(msgcolor) by specifing the 
     * message no.
     * @param msg  :the string which was made by concatenating the message<I>, message no.<I>
     * and </I> parameter</I>, </I> parameter</I>, using delimiters of <CODE>MessageResource</CODE>.
     * @param locale :locale to be specified
     </en>*/
    private void setMessageInfo(String msg, Locale locale)
    {
        int msgnum = 0;
        if (msg.indexOf(MessageResource.DELIM) > 0)
        {
            StringTokenizer stk = new StringTokenizer(msg, MessageResource.DELIM, false) ;
            //<jp> メッセージ番号</jp>
            //<en> message no.</en>
             msgnum = Integer.parseInt(stk.nextToken()) ;
        }
        else if (msg == null || msg.equals(""))
        {
            //<jp>6126021 = メッセージ番号にnullがセットされました。</jp>
            //<en>6126021 = Null was set for the message no.</en>
            msgnum = 6126021;
        } 
        else
        {
            //<jp> メッセージ番号</jp>
            //<en> message no.</en>
            msgnum = Integer.parseInt(msg) ;
        }

        String facility = LogMessage.getFacility(msgnum);
        if (facility != null)
        {
            if (facility == LogMessage.F_INFO)
            {
                this.msgcolor         = INFO_MSG;
            }
            else if (facility == LogMessage.F_NOTICE)
            {
                this.msgcolor         = ATNT_MSG;
            }
            else if (facility == LogMessage.F_WARN)
            {
                this.msgcolor         = WARN_MSG;
            }
            else if (facility == LogMessage.F_ERROR)
            {
                this.msgcolor         = ERR_MSG;
            }
        }
        this.operationmsg   = MessageResource.getMessage(msg);
    }


}
