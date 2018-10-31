package jp.co.daifuku.wms.base.common;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 *
 * <CODE>OutParameter</CODE>クラスは、BlueDog等の画面処理を行うクラスとスケジュール処理を行うクラス間での
 * パラメータの受渡しを行うために用意されたクラスです。<BR>
 * OutParamererクラスにはスケジュール→画面間で値の受渡しを行う項目をインスタンス変数に保持します。<BR>
 * 各パッケージ固有の項目を定義する場合はこのクラスを継承し、必要な項目を追加してください。<BR>
 * <BR>
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class OutParameter
        extends Parameter
{
    // Class variables -----------------------------------------------
    /**
     * ボタン制御フラグ : 前次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_ALL_OFF = "0";

    /**
     * ボタン制御フラグ : 前頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_PREVIOUS_OFF = "1";

    /**
     * ボタン制御フラグ : 次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_NEXT_OFF = "2";

    /**
     * ボタン制御フラグ : 前次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_ALL_ON = "3";

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
