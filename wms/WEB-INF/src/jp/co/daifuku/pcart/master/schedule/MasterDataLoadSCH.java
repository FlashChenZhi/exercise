// $Id: MasterDataLoadSCH.java 7898 2010-04-29 04:15:28Z kumano $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.MasterDataLoadSCHParams.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;

/**
 * マスタデータ取込のスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 7898 $, $Date: 2010-04-29 13:15:28 +0900 (木, 29 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class MasterDataLoadSCH
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
    public MasterDataLoadSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // 交換データ環境定義情報ハンドラの生成
        ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
        // 交換データ環境定義情報検索キーの生成
        ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();

        // システム定義情報コントローラの生成
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());
        // 返却パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();

        // PCTマスタパッケージ導入時
        if (sysController.hasPCTMasterPack())
        {
            // 返却パラメータの生成
            Params param = null;

            // 取得項目
            skey.setJobTypeCollect();
            skey.setClassNameCollect();
            skey.setDataIdCollect();
            skey.setDataNameCollect();

            // 検索条件
            skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);
            skey.setDataType(ExchangeEnvironment.DATA_MASTER);

            // 並び順
            skey.setJobTypeOrder(true);

            // 検索を行い件数分繰り返す
            ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
            for (ExchangeEnvironment exEnv : exEnvs)
            {
                // 返却パラメータの生成
                param = new Params();

                // 取込区分
                param.set(DATA_TYPE, exEnv.getJobType());
                // 取込内容
                param.set(LOAD_DATA_TYPE, exEnv.getDataName());
                // 取込ファイル名
                param.set(LOAD_FILE_NAME, exEnv.getDataId());
                // ファイル数
                int count = getLoadFileCount(exEnv.getClassName());
                param.set(DATA_COUNT, count);
                // クラス名
                param.set(CLASS_NAME, exEnv.getClassName());
                // 自動印刷
                param.set(AUTO_PRINT, exEnv.getAutoPrintErrorList());

                // 取込ファイルが存在しなかった場合
                if (count == 0)
                {
                    // (6006038)対象データはありませんでした。
                    param.set(MESSAGE, MessageResource.getMessage("6006038"));
                }
                else if (count < 0)
                {
                    // (6007001)スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
                    param.set(MESSAGE, MessageResource.getMessage("6007001"));
                }
                else
                {
                    param.set(MESSAGE, "");
                }

                // 設定したパラメータを配列に格納
                result.add(param);
            }
        }
        // 設定したパラメータ配列を返却
        return result;
    }

    /**
     * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
     * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * スケジュール処理が成功した場合は結果をセットした<CODE>Parameter</CODE>を返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     *
     * @param ps 検索条件をもつ<CODE>ScheduleParams</CODE>クラスを継承したクラス
     * @return スケジュール処理に成功した場合は<CODE>Parameter</CODE>を返します。失敗した場合はnullを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // 返却パラメータ用
        List<Params> result = new ArrayList<Params>();
        Params outParam = null;

        // コンストラクタクラス
        Constructor constructor = null;
        // メソッドクラス
        Method method = null;
        // クラスオブジェクト
        Object obj = null;
        // メッセージNo.
        int msgNo = 0;

        // ホスト通信状態チェック
        if (!isHostCommEnabled())
        {
            //6023696=上位通信状態が無効のため、処理できません。
            setMessage("6023696");

            // 問題があった場合、falseを返却
            return null;
        }
        
        // 処理開始、取込中チェック
        if (!canStart() || isLoadData())
        {
            return null;
        }

        try
        {
            // 件数分繰り返す
            for (ScheduleParams p : ps)
            {
                // チェックが入っていない場合は処理しない
                if (p.getBoolean(SELECT))
                {
                    // 文字列からクラスを生成
                    Class<?> loaderClass = Class.forName(p.getString(CLASS_NAME));
                    // コンストラクタを取得
                    constructor = loaderClass.getConstructor(DfkUserInfo.class, Locale.class);
                    // インスタンス生成
                    obj = constructor.newInstance(getUserInfo(), getLocale());
                    // execute()呼び出し
                    method = obj.getClass().getMethod("execute", new Class<?>[]{String[].class});
                    method.invoke(obj, new Object[]{new String[]{}});
                }

                // 返却パラメータの生成
                outParam = new Params();
                // 選択
                outParam.set(SELECT, p.getBoolean(SELECT));
                // 取込内容
                outParam.set(LOAD_DATA_TYPE, p.getString(LOAD_DATA_TYPE));
                outParam.set(DATA_TYPE, p.getString(DATA_TYPE));
                // 取込ファイル名
                outParam.set(LOAD_FILE_NAME, p.getString(LOAD_FILE_NAME));
                // データ数
                outParam.set(DATA_COUNT, getLoadFileCount(p.getString(CLASS_NAME)));
                // クラス名
                outParam.set(CLASS_NAME, p.getString(CLASS_NAME));
                // 自動印刷
                outParam.set(AUTO_PRINT, p.getString(AUTO_PRINT));

                // チェックが入っていない場合は処理しない
                if (p.getBoolean(SELECT))
                {
                    // 個別メッセージ
                    method = obj.getClass().getMethod("getMessage", new Class<?>[]{});
                    msgNo = Integer.parseInt(method.invoke(obj, new Object[]{}).toString());
                    outParam.set(MESSAGE, MessageResource.getMessage(WmsMessageFormatter.format(msgNo)));
                }
                // 生成したパラメータを配列に格納
                result.add(outParam);
            }
            // 6001028=設定しました。詳細は下記メッセージを参照して下さい。
            setMessage("6001028");

            // 生成した返却パラメータ配列を返却
            return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
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
     * 取込データのファイル数を数えます。
     *
     * @param className クラス名
     * @return int 検出したファイル数
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected int getLoadFileCount(String className)
            throws CommonException
    {
        // コンストラクタクラス
        Constructor constructor = null;
        // メソッドクラス
        Method method = null;

        try
        {
            // 文字列からクラスを生成
            Class<?> loaderClass = Class.forName(className);
            // コンストラクタを取得
            constructor = loaderClass.getConstructor(DfkUserInfo.class, Locale.class);
            // インスタンス生成
            Object obj = constructor.newInstance(getUserInfo(), getLocale());
            // getFileCount()呼び出し
            method = obj.getClass().getMethod("getFileCount", new Class<?>[]{});

            // 結果を返却(件数取得のため返却値はIntegerとなる)
            return (Integer)method.invoke(obj, new Object[]{});
        }
        // 問題があれば-1件として通知
        catch (Exception e)
        {
            return -1;
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
