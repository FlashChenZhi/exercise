// $Id: DsNumberDefine.java 7837 2010-04-21 02:13:53Z kishimoto $
package jp.co.daifuku.wms.base.common;

import jp.co.daifuku.authentication.DfkUserInfo;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * Part11で使用する固定値のDS番号を定義します。<br>
 * また、ページ名リソースキーも合わせて定義します。<br>
 *
 * @version $Revision: 7837 $, $Date: 2010-04-21 11:13:53 +0900 (水, 21 4 2010) $
 * @author  073064
 * @author  Last commit: $Author: kishimoto $
 */


public class DsNumberDefine
        extends Object
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * DS番号：システムバックアップ
     */
    public static final String DS_SYSTEMBACKUP = "000201";

    /**
     * DS番号：自動取込
     */
    public static final String DS_AUTOLOAD = "000202";

    /**
     * DS番号：自動報告
     */
    public static final String DS_AUTOREPORT = "000203";

    /**
     * DS番号：作業開始応答(id21)
     */
    public static final String DS_AGC_ID21 = "000321";

    /**
     * DS番号：作業終了応答(id23)
     */
    public static final String DS_AGC_ID23 = "000323";

    /**
     * DS番号：搬送DataCancel応答(id24)
     */
    public static final String DS_AGC_ID24 = "000324";

    /**
     * DS番号：搬送指示応答(id25)
     */
    public static final String DS_AGC_ID25 = "000325";

    /**
     * DS番号：到着報告(id26)
     */
    public static final String DS_AGC_ID26 = "000326";

    /**
     * DS番号：機器状態報告(id30)
     */
    public static final String DS_AGC_ID30 = "000330";

    /**
     * DS番号：作業完了報告(id33)
     */
    public static final String DS_AGC_ID33 = "000333";

    /**
     * DS番号：搬送Data削除報告(id35)
     */
    public static final String DS_AGC_ID35 = "000335";

    /**
     * DS番号：Mode切替完了報告(id63)
     */
    public static final String DS_AGC_ID63 = "000363";

    /**
     * DS番号：搬送指示送信(StorageSender)
     */
    public static final String DS_AGC_S_SENDER = "000401";

    /**
     * DS番号：ダブルディープ搬送指示送信(DoubleDeepStorageSender)
     */
    public static final String DS_AGC_DD_SENDER = "000402";

    /**
     * DS番号：自動モード切替搬送・出庫指示送信(AutomaticModeChangeSender)
     */
    public static final String DS_AGC_AMC_SENDER = "000403";

    /**
     * DS番号：在庫情報修正・削除
     */
    public static final String DS_STOCK_MNT = "060802";

    /**
     * DS番号：ASRS在庫情報変更
     */
    public static final String DS_SHELF_MNT = "061101";

    /**
     * DS番号：ASRSステーション運転/中断設定
     */
    public static final String DS_STN_CHANGE = "070202";


    /**
     * DS番号：作業表示（ASRS）
     */
    public static final String DS_WORK_DISP = "070501";

    /**
     * DS番号：作業メンテナンス（ASRS）
     */
    public static final String DS_WORK_MNT = "070601";

    /**
     * ページ名リソースキー：システムバックアップ
     */
    public static final String PAGERESOUCE_SYSTEMBACKUP = "MTLE-W0811";

    /**
     * ページ名リソースキー：自動取込
     */
    public static final String PAGERESOUCE_AUTOLOAD = "MTLE-W0812";

    /**
     * ページ名リソースキー：自動報告
     */
    public static final String PAGERESOUCE_AUTOREPORT = "MTLE-W0813";

    /**
     * ページ名リソースキー：作業開始応答(id21)
     */
    public static final String PAGERESOUCE_AGC_ID21 = "MTLE-W0716";

    /**
     * ページ名リソースキー：作業終了応答(id23)
     */
    public static final String PAGERESOUCE_AGC_ID23 = "MTLE-W0717";

    /**
     * ページ名リソースキー：搬送DataCancel応答(id24)
     */
    public static final String PAGERESOUCE_AGC_ID24 = "MTLE-W0719";

    /**
     * ページ名リソースキー：搬送指示応答(id25)
     */
    public static final String PAGERESOUCE_AGC_ID25 = "MTLE-W0720";

    /**
     * ページ名リソースキー：到着報告(id26)
     */
    public static final String PAGERESOUCE_AGC_ID26 = "MTLE-W0721";

    /**
     * ページ名リソースキー：機器状態報告(id30)
     */
    public static final String PAGERESOUCE_AGC_ID30 = "MTLE-W0718";

    /**
     * ページ名リソースキー：作業完了報告(id33)
     */
    public static final String PAGERESOUCE_AGC_ID33 = "MTLE-W0722";

    /**
     * ページ名リソースキー：搬送Data削除報告(id35)
     */
    public static final String PAGERESOUCE_AGC_ID35 = "MTLE-W0728";

    /**
     * ページ名リソースキー：Mode切替完了報告(id63)
     */
    public static final String PAGERESOUCE_AGC_ID63 = "MTLE-W0715";

    /**
     * ページ名リソースキー：搬送指示送信(StorageSender)
     */
    public static final String PAGERESOUCE_AGC_S_SENDER = "MTLE-W0725";

    /**
     * ページ名リソースキー：ダブルディープ搬送指示送信(DoubleDeepStorageSender)
     */
    public static final String PAGERESOUCE_AGC_DD_SENDER = "MTLE-W0726";

    /**
     * ページ名リソースキー：自動モード切替搬送・出庫指示送信(AutomaticModeChangeSender)
     */
    public static final String PAGERESOUCE_AGC_AMC_SENDER = "MTLE-W0727";

    /**
     * ページ名リソースキー：作業表示（ASRS）
     */
    public static final String PAGERESOUCE_WORK_DISP = "TLE-W0718";

    /**
     * ページ名リソースキー：作業メンテナンス（ASRS）
     */
    public static final String PAGERESOUCE_WORK_MNT = "TLE-W0719";

    /**
     * ページ名リソースキー：ASRSステーション運転/中断設定
     */
    public static final String PAGERESOUCE_STN_CHANGE = "TLE-W0714";

    /**
     * RFT通信のパッケージ名(inventorychk)
     */
    public static final String RFT_PACKAGE_INVENT = "jp.co.daifuku.wms.inventorychk.rft";

    /**
     * RFT通信のパッケージ名(stock)
     */
    public static final String RFT_PACKAGE_STOCK = "jp.co.daifuku.wms.stock.rft";

    /**
     * RFT通信のパッケージ名(retrieval)
     */
    public static final String RFT_PACKAGE_RETRIEVAL = "jp.co.daifuku.wms.retrieval.rft";

    /**
     * RFT通信のパッケージ名(storage)
     */
    public static final String RFT_PACKAGE_STORAGE = "jp.co.daifuku.wms.storage.rft";

    /**
     * RFT通信のパッケージ名(receiving)
     */
    public static final String RFT_PACKAGE_RECEIVING = "jp.co.daifuku.wms.receiving.rft";

    /**
     * AGC通信のパッケージ名
     */
    public static final String ASRS_PACKAGE = "jp.co.daifuku.wms.asrs.control.id";

    /**
     * AS/RS搬送指示、出庫指示のパッケージ名
     */
    public static final String ASRS_SENDER = "jp.co.daifuku.wms.asrs.communication.as21";

    /**
     * 作業メンテナンス画面クラス名
     */
    public static final String ASRS_WORKMNT = "jp.co.daifuku.wms.asrs.schedule.asworkmntsch";

    /**
     * 作業メンテナンスオペレータクラス名
     */
    public static final String ASRS_WORKMNTRET = "jp.co.daifuku.wms.asrs.location.retrievalstationoperator";

    /**
     * 作業表示メンテナンス画面クラス名
     */
    public static final String ASRS_WORKDISP = "jp.co.daifuku.wms.asrs.schedule.asworkdisplaysch";

    /**
     * MC-AGC ID番号 搬送DataCancel応答(id24)
     */
    public static final String AGCID24 = "Id24Process";

    /**
     * MC-AGC ID番号 搬送指示応答(id25)
     */
    public static final String AGCID25 = "Id25Process";

    /**
     * MC-AGC ID番号 到着報告(id26)
     */
    public static final String AGCID26 = "Id26Process";

    /**
     * MC-AGC ID番号 作業完了報告(id33)
     */
    public static final String AGCID33 = "Id33Process";

    /**
     * MC-AGC ID番号 搬送Data削除報告(id35)
     */
    public static final String AGCID35 = "Id35Process";

    /**
     * 搬送指示送信(StorageSender)
     */
    public static final String ASRS_S_SENDER = "StorageSender";

    /**
     * ダブルディープ搬送指示送信(DoubleDeepStorageSender)
     */
    public static final String ASRS_DD_SENDER = "DoubleDeepStorageSender";

    /**
     * 自動モード切替搬送・出庫指示送信(AutomaticModeChangeSender)
     */
    public static final String ASRS_AMC_SENDER = "AutomaticModeChangeSender";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    /**
     * DS番号
     */
    private String _dsNum;

    /**
     * ページ名リソースキー
     */
    private String _pageResouceKey;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    public DsNumberDefine()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 呼び出し元クラスがRFT通信か確認します。<br>
     * @param caller 呼び出し元クラス
     * @return RFTからの呼び出しの場合はtrue、それ以外の場合はfalseを返す
     */
    public boolean fromRft(Class caller)
    {
        String callerName = caller.getName().toLowerCase();
        // パッケージ名にRFT(inventory)が含まれている
        if (callerName.indexOf(RFT_PACKAGE_INVENT) >= 0)
        {
            return true;
        }

        // パッケージ名にRFT(stock)が含まれている
        if (callerName.indexOf(RFT_PACKAGE_STOCK) >= 0)
        {
            return true;
        }

        // パッケージ名にRFT(storage)が含まれている
        if (callerName.indexOf(RFT_PACKAGE_STORAGE) >= 0)
        {
            return true;
        }

        // パッケージ名にRFT(retrieval)が含まれている
        if (callerName.indexOf(RFT_PACKAGE_RETRIEVAL) >= 0)
        {
            return true;
        }

        // パッケージ名にRFT(receiving)が含まれている
        if (callerName.indexOf(RFT_PACKAGE_RECEIVING) >= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 呼び出し元クラスがAGC通信、搬送指示、出庫指示送信か確認します。<br>
     * @param caller 呼び出し元クラス
     * @return RFTからの呼び出しの場合はtrue、それ以外の場合はfalseを返す
     */
    public boolean fromAGC(Class caller)
    {
        String callerName = caller.getName().toLowerCase();

        // パッケージ名にASRS（AGC通信）が含まれている
        if (callerName.indexOf(ASRS_PACKAGE) >= 0)
        {
            return true;
        }

        // パッケージ名にASRS（搬送指示、出庫指示送信）が含まれている
        if (callerName.indexOf(ASRS_SENDER) >= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 呼び出し元クラスがASRS作業メンテナンス画面、作業表示画面か確認します。<br>
     * AGC経由でない場合のみ呼び出されます。<br>
     * @param caller 呼び出し元クラス
     * @return RFTからの呼び出しの場合はtrue、それ以外の場合はfalseを返す
     */
    public boolean fromAsWork(Class caller)
    {
        String callerName = caller.getName().toLowerCase();

        if (callerName.indexOf(ASRS_WORKMNT) >= 0)
        {
            return true;
        }
        if (callerName.indexOf(ASRS_WORKMNTRET) >= 0)
        {
            return true;
        }
        if (callerName.indexOf(ASRS_WORKDISP) >= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * Defaultのユーザ情報（WmsParam）をセットします。
     * @param user セットするユーザ
     * @return セットされたユーザ
     */
    public DfkUserInfo setDefaultUserInfo(DfkUserInfo user)
    {
        // ユーザ情報をセット
        user.setUserId(WmsParam.SYS_USER_ID);
        user.setUserName(WmsParam.SYS_USER_NAME);
        user.setTerminalNumber(WmsParam.SYS_TERMINAL_NO);
        user.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        user.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        user.setDsNumber(WmsParam.SYS_DS_NUMBER);
        user.setPageNameResourceKey(WmsParam.SYS_PAGE_RESOUCE_KEY);

        return user;
    }

    /**
     * 呼び出し元クラスがAGC通信の場合、DS番号、ページ名リソースキーをセットします。
     * @param caller 呼び出し元クラス
     */
    public void setInfo(Class caller)
    {
        String callerName = caller.getSimpleName();

        if (AGCID24.equals(callerName))
        {
            _dsNum = DS_AGC_ID24;
            _pageResouceKey = PAGERESOUCE_AGC_ID24;
        }
        else if (AGCID25.equals(callerName))
        {
            _dsNum = DS_AGC_ID25;
            _pageResouceKey = PAGERESOUCE_AGC_ID25;
        }
        else if (AGCID26.equals(callerName))
        {
            _dsNum = DS_AGC_ID26;
            _pageResouceKey = PAGERESOUCE_AGC_ID26;
        }
        else if (AGCID33.equals(callerName))
        {
            _dsNum = DS_AGC_ID33;
            _pageResouceKey = PAGERESOUCE_AGC_ID33;
        }
        else if (AGCID35.equals(callerName))
        {
            _dsNum = DS_AGC_ID35;
            _pageResouceKey = PAGERESOUCE_AGC_ID35;
        }
        else if (ASRS_S_SENDER.equals(callerName))
        {
            _dsNum = DS_AGC_S_SENDER;
            _pageResouceKey = PAGERESOUCE_AGC_S_SENDER;
        }
        else if (ASRS_DD_SENDER.equals(callerName))
        {
            _dsNum = DS_AGC_DD_SENDER;
            _pageResouceKey = PAGERESOUCE_AGC_DD_SENDER;
        }
        else if (ASRS_AMC_SENDER.equals(callerName))
        {
            _dsNum = DS_AGC_AMC_SENDER;
            _pageResouceKey = PAGERESOUCE_AGC_AMC_SENDER;
        }
        else if (ASRS_WORKMNT.equals(caller.getName().toLowerCase()))
        {
            _dsNum = DS_WORK_MNT;
            _pageResouceKey = PAGERESOUCE_WORK_MNT;
        }
        else if (ASRS_WORKMNTRET.equals(caller.getName().toLowerCase()))
        {
            _dsNum = DS_WORK_MNT;
            _pageResouceKey = PAGERESOUCE_WORK_MNT;
        }
        else if (ASRS_WORKDISP.equals(caller.getName().toLowerCase()))
        {
            _dsNum = DS_WORK_DISP;
            _pageResouceKey = PAGERESOUCE_WORK_DISP;
        }
        else
        {
            _dsNum = DS_AGC_ID26;
            _pageResouceKey = PAGERESOUCE_AGC_ID26;
        }
    }

    /**
     * DS番号を返します。
     * @return DS番号を返します。
     */
    public String getDsNum()
    {
        return _dsNum;
    }

    /**
     * ページ名リソースキーを返します。
     * @return ページ名リソースキーを返します。
     */
    public String getPageResouceKey()
    {
        return _pageResouceKey;
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


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: DsNumberDefine.java 7837 2010-04-21 02:13:53Z kishimoto $";
    }
}
