package jp.co.daifuku.wms.exercise.util.bluedog;

import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.common.ExceptionHandler;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-27
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public class WmsExceptionHandler
{
    public static void handleException(Exception ex, Page page, Message message) throws ValidateException
    {
        ex.printStackTrace();
        message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, page));

    }
}
