// $Id: InventoryStartSCH.java 7856 2010-04-22 05:58:41Z shibamoto $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.schedule.InventoryStartSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.inventorychk.operator.InventoryOperator;

/**
 * 棚卸開始のスケジュール処理を行います。
 * 
 * @version $Revision: 7856 $, $Date: 2010-04-22 14:58:41 +0900 (木, 22 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class InventoryStartSCH
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
    public InventoryStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期データ検索を行います。
     * 
     * @param p 検索条件を指定したパラメータ
     * @return 初期表示用データ
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        Params outParam = new Params();
       
        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingSearchKey key = new WebSettingSearchKey();

            // 端末No.
            key.setTerminalNo(getWmsUserInfo().getTerminalNo());
            // 画面ID
            key.setFunctionid(p.getString(FUNCTION_ID));
            // キーデータ
            key.setKeydata(WebSetting.KEY_LIST_CHECK);

            WebSetting[] webset = (WebSetting[])webhandler.find(key);

            if (webset != null && webset.length > 0)
            {
                outParam.set(INVENTORY_ISSUE, webset[0].getValue());
            }
        }
        catch (Exception e)
        {
            // 6006042=画面定義情報の参照に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006042, e), getClass().getName());
        }

        return outParam;
    }
    
    
    /**
     * 画面から入力された条件をパラメータとして受け取り、棚卸開始の設定<BR>
     * 発行処理クラスにパラメータを渡します。<BR>
     * @return 正常完了はtrue、条件エラーなどの場合はfalseを返します。
     * @param  ps 検索条件パラメータ
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowされます。<BR>
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {

        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        InventoryInParameter param = new InventoryInParameter(getWmsUserInfo());
        Connection conn = this.getConnection();

        InventoryStartSCHParams inParam = (InventoryStartSCHParams)ps[0];

        // 荷主コード
        param.setConsignorCode(inParam.getString(CONSIGNOR_CODE));

        // エリア
        param.setAreaNo(inParam.getString(AREA));

        String[] loc = WmsFormatter.getFromTo(inParam.getString(LOCATION_FROM), inParam.getString(LOCATION_TO));

        // 開始棚
        if (StringUtil.isBlank(loc[0]))
        {
            param.setLocationNo(chgLocation(inParam.getString(AREA), true, conn));
        }
        else
        {
            param.setLocationNo(loc[0]);
        }

        // 終了棚
        if (StringUtil.isBlank(loc[1]))
        {
            param.setLocationNoTo(chgLocation(inParam.getString(AREA), false, conn));
        }
        else
        {
            param.setLocationNoTo(loc[1]);
        }

        param.setReportFlag(inParam.getBoolean(INVENTORY_ISSUE));
        param.setStockprintFlag(inParam.getBoolean(INVENTORY_STOCK_REPORT));

        //オペレータを呼び出し、棚卸作業情報の作成を行います。
        InventoryOperator ope = new InventoryOperator(conn, this.getClass());
        try
        {
            ope.createInventoryWorkInfo(param);

            //MSG="設定しました。"
            setMessage("6001006");
            
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(INVENTORY_ISSUE) ? WebSetting.KEYDATA_ON : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value); 
            
            return true;

        }
        catch (NotFoundException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (DataExistsException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 他端末で処理中のため、処理を中断しました。
                setMessage("6023114");
                return false;
            }
            else if (OperatorException.ERR_INVENT_LOCATION_DUPLICATED.equals(e.getErrorCode()))
            {
                //6023152=入力した棚は既に設定した棚卸範囲と重複しています。
                setMessage("6023152");
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
     * 棚が未入力の場合に使用します。<BR>
     * @return 棚の最小値又は最大値を返します。
     * @param areaNo エリアNo
     * @param flag 最小棚時はtrue、最大棚時はfalseをセットします。
     * @param conn コネクション
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected String chgLocation(String areaNo, boolean flag, Connection conn)
            throws CommonException
    {
        //エリアコントローラを取得します。
        AreaController areacon = new AreaController(conn, this.getClass());

        // 棚フォーマットの取得
        String style = areacon.getLocationStyle(areaNo);

        if (flag)
        {
            // 棚フォーマットに"-"が含まれていない場合
            if (style.indexOf('-') == -1)
            {
            	// 棚フォーマットに"9"が含まれていない場合
                if (style.indexOf('9') == -1)
                {
        	            style = "0";
                }
            }
            else
            {
            	style = style.replace("X", "0");
            }
            style = style.replace("9", "0");
        }
        else
        {
        	style = style.replace("X", "z");
        }
        
        //ハイフンを消す
        style = style.replace("-", "");

        return style;
    }
    
    /**
     * 画面定義情報を更新します。<br>
     * 更新処理で異常が発生した場合は、Exceptionをスローせず、
     * ロギングのみ行います。
     * 
     * @param term 端末No.
     * @param funcid 画面ID
     * @param value 更新値
     */
    protected void updateWebSetting(String term, String funcid, String value)
    {
        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingAlterKey akey = new WebSettingAlterKey();

            akey.setTerminalNo(term);
            akey.setFunctionid(funcid);
            akey.setKeydata(WebSetting.KEY_LIST_CHECK);

            akey.updateValue(value);
            akey.updateLastUpdatePname(getClass().getSimpleName());

            try
            {
                webhandler.modify(akey);
            }
            catch (NotFoundException e)
            {
                // 更新対象がない場合は新規作成
                WebSetting newdata = new WebSetting();

                newdata.setTerminalNo(term);
                newdata.setFunctionid(funcid);
                newdata.setKeydata(WebSetting.KEY_LIST_CHECK);
                newdata.setValue(value);
                newdata.setRegistPname(getClass().getSimpleName());
                newdata.setLastUpdatePname(getClass().getSimpleName());

                webhandler.create(newdata);
            }
        }
        catch (Exception e)
        {
            // 6006043=画面定義情報の更新に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006043, e), getClass().getName());
        }
    }
    
    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
