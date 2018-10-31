// $Id: WmsChecker.java 87 2008-10-04 03:07:38Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jp.co.daifuku.bluedog.ui.control.FreeTextBox;
import jp.co.daifuku.bluedog.ui.control.TextArea;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;

/**
 * eWareNavi 画面入力値について入力チェックを行うクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/27</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class WmsChecker
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**
     * エラーメッセージ保持用
     */
    private String _message = null;

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**
     * パラメータで渡されたテキストコントロールの入力チェックを行います。<BR>
     * 
     * @param ctrl テキストコントロール
     * @return 入力が正しかった場合true、誤っていた場合false
     */
    public boolean checkContainNgText(FreeTextBox ctrl)
    {
        return checkContainNgText(ctrl, 0);
    }

    /**
     * パラメータで渡されたテキストコントロールの入力チェックを行います。<BR>
     * テキストに入力がなかった場合は、trueを返します。
     * <BR>
     * ＜チェック内容＞<BR>
     * ・先頭文字が半角スペースでないこと<BR>
     * 
     * @param ctrl テキストコントロール
     * @param rowNo 行No
     * @return 入力が正しかった場合true、誤っていた場合false
     */
    public boolean checkContainNgText(FreeTextBox ctrl, int rowNo)
    {
        String str = ctrl.getText();

        if (str.length() == 0)
        {
            return true;
        }

        if (str.charAt(0) == ' ')
        {
            String ctrlName = DisplayText.getText(ctrl.getErrMsgParamKey());
            if (rowNo == 0)
            {
                // 6023048={0}の先頭文字がスペースです。先頭にスペースは入力できません。
                _message = WmsMessageFormatter.format(6023048, ctrlName);
            }
            else
            {
                // 6023049=No.{0} {1}の先頭文字がスペースです。先頭にスペースは入力できません。
                _message = WmsMessageFormatter.format(6023049, rowNo, ctrlName);
            }

            return false;
        }

        return true;
    }

    /**
     * パラメータで渡されたテキストコントロールの入力チェックを行います。<BR>
     * テキストに入力がなかった場合は、trueを返します。
     * <BR>
     * ＜チェック内容＞<BR>
     * ・先頭文字が半角スペースでないこと<BR>
     * 
     * @param ctrl テキストコントロール
     * @return 入力が正しかった場合true、誤っていた場合false
     */
    public boolean checkContainNgText(TextArea ctrl)
    {
        String str = ctrl.getText();

        if (str.length() == 0)
        {
            return true;
        }

        if (str.charAt(0) == ' ')
        {
            String ctrlName = DisplayText.getText(ctrl.getErrMsgParamKey());
            // 6023048={0}の先頭文字がスペースです。先頭にスペースは入力できません。
            _message = WmsMessageFormatter.format(6023048, ctrlName);

            return false;
        }

        return true;
    }

    /**
     * パラメータで渡されたテキストコントロールの入力チェックを行います。<BR>
     * テキストに入力がなかった場合は、trueを返します。
     * <BR>
     * ＜チェック内容＞<BR>
     * ・先頭文字が半角スペースでないこと<BR>
     * 
     * @param str 入力値
     * @param rowNo 行No
     * @param msg 項目名称のリソースキー
     * @return 入力が正しかった場合true、誤っていた場合false
     */
    public boolean checkContainNgText(String str, int rowNo, String msg)
    {
        if (str.length() == 0)
        {
            return true;
        }

        if (str.charAt(0) == ' ')
        {
            if (!StringUtil.isBlank(msg))
            {
                if (rowNo == 0)
                {
                    // 6023048={0}の先頭文字がスペースです。先頭にスペースは入力できません。
                    _message = WmsMessageFormatter.format(6023048, DisplayText.getText(msg));
                }
                else
                {
                    // 6023049=No.{0} {1}の先頭文字がスペースです。先頭にスペースは入力できません。
                    _message = WmsMessageFormatter.format(6023049, rowNo, DisplayText.getText(msg));
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 日付チェック
     * 文字列(YYYYMMDD)が日付として有効かチェックします。<BR>
     * @param  day  入力文字列 YYYYMMDD
     * @return 入力が正しかった場合true、誤っていた場合false
     */
    public boolean checkDay(String day)
    {
        // 入力文字列が８桁以外の場合エラー
        if (StringUtil.isBlank(day) || day.length() != 8)
        {
            return false;
        }

        for (int i = 0; i < day.length(); i++)
        {
            char charData = day.charAt(i);
            if ((charData < '0') || (charData > '9'))
            {
                return false;
            }
        }

        int intYear = java.lang.Integer.parseInt(day.substring(0, 4));
        int intMonth = java.lang.Integer.parseInt(day.substring(4, 6));
        int intDay = java.lang.Integer.parseInt(day.substring(6, 8));

        Calendar cal = new GregorianCalendar();
        cal.setLenient(false);
        cal.set(intYear, intMonth - 1, intDay);

        try
        {
            cal.getTime();
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }

        return true;
    }
    
    /**
     * 日付、時間が並んでいるテキストボックスの入力チェックを行います。
     * 
     * @param day 日付テキストボックス
     * @param time 時間テキストボックス
     * @return true:入力が正しい false:入力が誤り
     */
    public boolean checkDate(Date day, Date time)
    {
        if (StringUtil.isBlank(day) && !StringUtil.isBlank(time))
        {
            //6023009=時刻入力時は、日付入力も行ってください。
            _message = "6023009";
            return false;
        }
        else
        {
            return true;
        }

    }

    /**
     * このクラスで行ったチェックでNGだった際の
     * エラーメッセージを取得します。
     * 
     * @return エラーメッセージ
     */
    public String getMessage()
    {
        return _message;
    }

}
