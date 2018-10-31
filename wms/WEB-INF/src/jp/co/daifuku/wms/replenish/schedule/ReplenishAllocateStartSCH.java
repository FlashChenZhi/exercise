package jp.co.daifuku.wms.replenish.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;

import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.WarenaviSystem;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.retrieval.allocate.PlannedReplenishCaseOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCH;

import static jp.co.daifuku.wms.replenish.schedule.ReplenishAllocateStartSCHParams.*;

/**
 * 計画補充開始設定のスケジュール処理を行います。
 *
 * @version $Revision: 7400 $, $Date: 2010-03-05 20:45:20 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ReplenishAllocateStartSCH
        extends RetrievalOrderStartSCH
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
    public ReplenishAllocateStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
                outParam.set(ISSUE_REPORT, webset[0].getValue());
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
     * 完了ボタン押下時処理
     * 
     * @param startParams メンテナンス対象データ
     * @return boolean型
     * @throws CommonException スケジュールエラーが発生した場合に報告されます。
     */
    public boolean startSCH(ScheduleParams... startParams)
            throws CommonException
    {

        ScheduleParams param = startParams[0];
        // コミット済みフラグ
        boolean isCommit = false;

        DefaultDDBFinder finder = null;
        try
        {

            // 日次更新チェック
            if (!super.canStart())
            {
                return false;
            }

            // 日次更新チェックで問題がない場合はコネクションをコミットし、
            // コミットした事を一時的に覚えておく
            doCommit(getClass());
            isCommit = true;

            // 棚情報を取得
            finder = getDefaultDDBFinder(param);

            // 対象データが存在しない場合
            if (!finder.hasNext())
            {
                setMessage(WmsMessageFormatter.format(6003011));
                return false;
            }

            int datacnt = 0;
            Entity[] entities;
            PlannedReplenishCaseOperator operator =
                    new PlannedReplenishCaseOperator(getConnection(), param.getInt(RATE),
                            param.getString(ALLOC_PATTERN), this.getClass());

            // 検索結果フラグ
            // 全欠品
            boolean isShortageAll = false;
            // 全数引当
            boolean isAllocateAll = false;
            // 一部欠品
            boolean isShortage = false;

            // データを展開する
            while (finder.hasNext())
            {
                entities = (Entity[])finder.getEntities(datacnt, datacnt + 1);
                // 計画補充引当を行う
                int result = operator.allocate(entities[0]);

                // 全欠品だった場合（戻り値＝オペレータのgetPlanQty）
                if (result == operator.getPlanQty())
                {
                    isShortageAll = true;
                }
                // 全数引当だった場合（戻り値＝０）
                else if (result == 0)
                {
                    isAllocateAll = true;
                }
                // 上記以外いくつかは一部欠品
                else
                {
                    isShortage = true;
                }

                datacnt++;
            }

            // 今回作成した搬送情報の出庫指示詳細と再入庫区分の決定処理を行う
            operator.decideCarryInfo();

            // 全欠品 ON かつ 全数引当 off かつ 一部欠品 off
            if (isShortageAll && !isAllocateAll && !isShortage)
            {
                // 6021013=一部のデータをスキップした結果、対象データはありませんでした。
                setMessage(WmsMessageFormatter.format(6021015));
            }
            // 全欠品 off かつ 全数引当 ON かつ 一部欠品 off
            else if (!isShortageAll && isAllocateAll && !isShortage)
            {
                // 6001006=設定しました。
                setMessage(WmsMessageFormatter.format(6001006));
            }
            // 上記以外
            else
            {
                // 6021020=設定しました。（一部のデータがスキップされました。）
                setMessage(WmsMessageFormatter.format(6021020));
            }

            // Businessの印刷処理で使用するためパラメータに印刷KEYをセットする。
            param.set(SETTING_UKEYS, operator.getSettingUnitKeys());
            param.set(AS_SETTING_UKEYS, operator.getAsrsSettingUnitKeys());
            param.set(SHORTAGE_KEY, operator.getStartUnitKey());

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = startParams[0].getBoolean(ISSUE_REPORT) ? WebSetting.KEYDATA_ON
                                                                  : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), startParams[0].getString(FUNCTION_ID), value);

            return true;
        }
        finally
        {
            // コミット済みの場合
            if (isCommit)
            {
                // WareNaviSystemの引当中フラグをOFFにする。
                updateRetrievalAllocate(WarenaviSystem.PROCESS_COMPLETED);

                // コネクションをコミット
                doCommit(getClass());
            }

            closeFinder(finder);
        }
    }

    /**
     * パラメータの内容が正しいかチェックを行います。<BR>
     * <CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
     * パラメータ入力内容チェック処理を行います。<BR>
     * 必要に応じて、各継承クラスで実装してください。
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合  false：そうでない場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams checkParam)
            throws CommonException
    {
        return super.check(checkParam);
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
     * 対象となる固定棚情報を取得を検索します。<BR>
     * 
     * @param param 検索対象データ
     * @return 固定棚情報データがない場合はnullを返します。
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     */
    protected DefaultDDBFinder getDefaultDDBFinder(ScheduleParams param)
            throws ReadWriteException
    {
        DefaultDDBFinder finder = new DefaultDDBFinder(getConnection(), new FixedLocateInfo());

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append("    fixed.*, ");
        query.append("    item.* ");
        query.append("FROM ");
        query.append("    DmFixedLocateInfo fixed, ");
        query.append("    DmItem item ");
        query.append("WHERE ");
        // 荷主コード
        query.append("    fixed.consignor_code = '");
        query.append(param.getString(CONSIGNOR_CODE));
        query.append("' AND ");
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            query.append("    fixed.item_code = '");
            query.append(param.getString(ITEM_CODE));
            query.append("' AND ");
        }
        // エリアNo.
        if (!WmsParam.ALL_AREA_NO.equals(param.getString(TO_REPLENISHMENT_AREA)))
        {
            query.append("    fixed.area_no = '");
            query.append(param.getString(TO_REPLENISHMENT_AREA));
            query.append("' AND ");

            String inputFrom = param.getString(FROM_LOCATION);
            String inputTo = param.getString(TO_LOCATION);

            String[] tmp = WmsFormatter.getFromTo(inputFrom, inputTo);


            if (!StringUtil.isBlank(tmp[0]))
            {
                // 補充先棚(from)
                query.append("    fixed.location_no >= '");
                query.append(tmp[0]);
                query.append("' AND ");
            }
            if (!StringUtil.isBlank(tmp[1]))
            {
                // 補充先棚(to)
                query.append("    fixed.location_no <= '");
                query.append(tmp[1]);
                query.append("' AND ");
            }
        }


        query.append("    fixed.consignor_code = item.consignor_code AND ");
        query.append("    fixed.item_code = item.item_code AND ");
        query.append("    ((fixed.replenishment_qty * ");
        query.append(param.getString(ReplenishAllocateStartSCHParams.RATE));
        query.append(" ) / 100)  > ( ");
        query.append("        SELECT ");
        query.append("            nvl( sum(stock.stock_qty), 0 ) ");
        query.append("        FROM ");
        query.append("            dnstock stock ");
        query.append("        WHERE ");
        query.append("            stock.area_no = fixed.area_no AND ");
        query.append("            stock.location_no = fixed.location_no AND ");
        query.append("            stock.consignor_code = fixed.consignor_code AND ");
        query.append("            stock.item_code = fixed.item_code ) ");

        finder.open(true);
        finder.search(query.toString());

        return finder;
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
