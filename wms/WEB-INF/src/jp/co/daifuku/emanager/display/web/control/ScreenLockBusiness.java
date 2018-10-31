package jp.co.daifuku.emanager.display.web.control;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;

/**
 * <jp>プログレスウィンドウ画面クラスです。<br></jp>
 * <en>Progress screen.<br></en> 
 * 
 * @author K.Fukumori
 */
public class ScreenLockBusiness
        extends ScreenLock
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
        // httpRequest.setAttribute(jp.co.daifuku.bluedog.webapp.Constants.KEY_FOCUS_TAG_SUPPORT, null);
        String arg = request.getParameter(EmConstants.RKEY_WAITINTERVAL);
        msg.setResourceKey("MSG-T0026");
        int interval = 5000;
        if (arg != null && !arg.equals(""))
        {
            try
            {
                interval = Integer.parseInt(arg) * 1000;
            }
            catch (NumberFormatException ex)
            {
            }
        }
        String messageText = DispResources.getText("MSG-T0025");
        addOnloadScript("setTimeout('timerScript(\"" + messageText + "\")','" + interval + "');");
        btn_ToLoginScreen.setVisible(false);
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

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_ToLoginScreen_Server(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_ToLoginScreen_Click(ActionEvent e)
            throws Exception
    {
        redirect("/", null);
    }


}
