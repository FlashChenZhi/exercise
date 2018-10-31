// $Id: RFTWorkingModeSettingSCH.java 4282 2009-05-14 00:34:47Z okayama $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.RFTWorkingModeSettingSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftSettingHandler;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.RftSetting;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * RFTモード切替設定のスケジュール処理を行います。
 *
 * @version $Revision: 4282 $, $Date: 2009-05-14 09:34:47 +0900 (木, 14 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RFTWorkingModeSettingSCH
        extends AbstractSCH
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public RFTWorkingModeSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        RFTWorkingModeSettingSCHParams param = (RFTWorkingModeSettingSCHParams)ps[0];

        try
        {
            // 処理前のチェックを行う。
            // 日次更新チェック
            if (!canStart())
            {
                return false;
            }

            //検索を行う
            Rft[] rft = find(param.getString(RFT_NO));

            if (rft.length <= 0)
            {
                //6006038=対象データはありませんでした。
                setMessage("6003011");

                return false;
            }

            // ロックを行う
            if (!lock(param.getString(RFT_NO)))
            {
                //6023115=他端末で処理されたため、処理を中断しました。
                setMessage("6023115");

                return false;
            }

            for (Rft entity : rft)
            {
                modifyData(entity.getRftNo(), param);
            }
            // 6001006=設定しました。
            setMessage("6001006");
            return true;
        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
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
     * 
     */
    private Rft[] find(String rftNo)
            throws CommonException
    {
        Rft[] rft = null;

        RftHandler rftHand = new RftHandler(getConnection());
        RftSearchKey rftKey = new RftSearchKey();

        if (!WmsParam.ALL_RFT_NO.equals(rftNo))
        {
            rftKey.setRftNo(rftNo);
        }
        rftKey.setJoin(Rft.RFT_NO, RftSetting.RFT_NO);

        rftKey.setTerminalType(SystemDefine.TERMINAL_TYPE_HT);

        rftKey.setRftNoCollect();
        rftKey.setRftNoGroup();

        rft = (Rft[])rftHand.find(rftKey);

        return rft;
    }

    /**
     * パラメータでわたされた、端末No.をキーに、DMRft,DMRftSettingをロックします。<BR>
     * また、他端末から更新・ロックされていないかをチェックします。<BR>
     * 
     * @param rftNo 号機No.
     * @return true:ロック成功、false:ロック失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean lock(String rftNo)
            throws CommonException
    {
        // DMRFT、DMRFTSETTINGをロック
        RftHandler rftHand = new RftHandler(getConnection());
        RftSearchKey rftKey = new RftSearchKey();

        if (!WmsParam.ALL_RFT_NO.equals(rftNo))
        {
            rftKey.setRftNo(rftNo);
            rftKey.setJoin(Rft.RFT_NO, RftSetting.RFT_NO);
        }
        rftKey.setTerminalType(SystemDefine.TERMINAL_TYPE_HT);

        rftKey.setRftNoCollect();
        rftKey.setRftNoGroup();

        return rftHand.lock(rftKey, RftHandler.WAIT_SEC_NOWAIT);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * 画面からのパラメータを元にRFTSettingを更新します。
     * @param rftNo 更新対象号機No.
     * @param param 画面からのパラメータ
     * @throws CommonException 全ての例外を報告します。
     */
    protected void modifyData(String rftNo, RFTWorkingModeSettingSCHParams param)
            throws CommonException
    {
        // 荷主スキップ
        String defualtCon = param.getString(CONSIGNOR_CODE);

        // ITFtoJAN
        boolean itfToJAN;
        if (param.getInt(ITF_TO_JAN) == 1)
        {
            itfToJAN = true;
        }
        else
        {
            itfToJAN = false;
        }

        // C/P初期モード
        int casePieseMode = param.getInt(CASE_PIESE_MODE);

        // 検品モード
        boolean inspectionMode;
        if (param.getInt(INSPECTION_MODE) == 1)
        {
            inspectionMode = true;
        }
        else
        {
            inspectionMode = false;
        }

        // 入荷
        if (param.getBoolean(WORK_KIND_RECEIVING))
        {
            modifyReceivingSetting(rftNo, defualtCon, itfToJAN, casePieseMode);
        }

        // 入庫
        if (param.getBoolean(WORK_KIND_STORAGE))
        {
            modifyStorageSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 出庫
        if (param.getBoolean(WORK_KIND_RETRIEVAL))
        {
            modifyRetrievalSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 仕分
        if (param.getBoolean(WORK_KIND_SORT))
        {
            modifySortSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 出荷
        if (param.getBoolean(WORK_KIND_SHIPPING))
        {
            modifyShippingSetting(rftNo, defualtCon, itfToJAN, casePieseMode);
        }

        // 移動出庫
        if (param.getBoolean(WORK_KIND_RELOCATINO_RETRIEV))
        {
            modifyReRetrievalSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 移動入庫
        if (param.getBoolean(WORK_KIND_RELOCATINO_STORAGE))
        {
            modifyReStorageSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 棚卸
        if (param.getBoolean(WORK_KIND_INVENTRY))
        {
            modifyInventorySetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 予定外入庫
        if (param.getBoolean(WORK_KIND_NO_PLAN_STORAGE))
        {
            modifyNoPlanStorageSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

        // 予定外出庫
        if (param.getBoolean(WORK_KIND_NO_PLAN_RETRIEVAL))
        {
            modifyNoPlanRetrievalSetting(rftNo, defualtCon, itfToJAN, casePieseMode, inspectionMode);
        }

    }

    /**
     * 入荷の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyReceivingSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RECEIVING_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RECEIVING_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RECEIVING_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 入庫の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyStorageSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_STORAGE_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_STORAGE_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_STORAGE_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_STORAGE_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 出庫の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyRetrievalSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RETRIEVAL_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RETRIEVAL_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RETRIEVAL_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_RETRIEVAL_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 仕分の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifySortSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SORTING_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SORTING_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SORTING_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SORTING_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 出荷の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyShippingSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SHIPPING_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SHIPPING_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_SHIPPING_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 移動出庫の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyReRetrievalSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVERETRIEVAL_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVERETRIEVAL_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVERETRIEVAL_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVERETRIEVAL_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 移動入庫の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyReStorageSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVESTORAGE_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVESTORAGE_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVESTORAGE_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_MOVESTORAGE_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 棚卸の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyInventorySetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_INVENTORY_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_INVENTORY_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_INVENTORY_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_INVENTORY_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);
    }

    /**
     * 予定外入庫の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyNoPlanStorageSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANSTORAGE_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANSTORAGE_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANSTORAGE_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANSTORAGE_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    /**
     * 予定外出庫の作業モード設定を変更します。
     * 
     * @param rftNo RFT号機No.
     * @param consignorCode 荷主コード
     * @param itfToJAN ITFtoJANモード
     * @param casePieseMode C/Pモード
     * @param inspectionMode 検品モード
     * 
     * @throws NotFoundException This exception occurs when searching information and no corresponding data was found.
     * @throws ReadWriteException This exception is used for errors occurred during the access to the external storage device.vp8 
     */
    private void modifyNoPlanRetrievalSetting(String rftNo, String consignorCode, boolean itfToJAN, int casePieseMode,
            boolean inspectionMode)
            throws NotFoundException,
                ReadWriteException
    {
        RftSettingHandler handler = new RftSettingHandler(getConnection());
        RftSettingAlterKey aKey = new RftSettingAlterKey();

        // 荷主スキップモード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANRETRIEVAL_CONSIGNOR);
        aKey.updateValue(consignorCode);
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // ITFtoJAN設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANRETRIEVAL_ITFTOJAN);
        aKey.updateValue(String.valueOf(itfToJAN));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // C/P初期モード設定
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANRETRIEVAL_CP_MODE);
        aKey.updateValue(String.valueOf(casePieseMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

        // 検品モード
        aKey.clear();
        aKey.setRftNo(rftNo);
        aKey.setKey(RftSetting.KEY_NOPLANRETRIEVAL_INSPECTION_MODE);
        aKey.updateValue(String.valueOf(inspectionMode));
        aKey.updateLastUpdatePname(this.getClass().getSimpleName());
        handler.modify(aKey);

    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
