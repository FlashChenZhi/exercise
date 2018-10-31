package jp.co.daifuku.emanager.display.web.control;

import jp.co.daifuku.bluedog.webapp.ActionEvent;

/**
 * <jp>プログレスウィンドウ画面クラスです。<br></jp>
 * <en>Progress screen.<br></en> 
 * 
 * @author K.Fukumori
 */
public class ProgressBusiness
        extends Progress
{
    /** 
     * <jp> 発生条件：各コントロールイベント呼び出し前に呼び出されます。<br></jp>
     * <en>Generating conditions: It is called before each control event call. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // When the page_Initialize method at the parent screen set forcus, it should remove this reference.  
        httpRequest.setAttribute(jp.co.daifuku.bluedog.webapp.Constants.KEY_FOCUS_TAG_SUPPORT, null);
    }

    /** 
     * <jp>画面の初期化を行います。<br></jp>
     * <en>Initialize a screen. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void msg_Server(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * <jp>ログインチェックをオーバライドします。<br></jp>
     * <en>Carry out the override of the login check. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_LoginCheck(ActionEvent e)
            throws Exception
    {
    }
}
