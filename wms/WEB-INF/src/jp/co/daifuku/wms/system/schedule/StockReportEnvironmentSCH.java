// $Id: StockReportEnvironmentSCH.java 7277 2010-02-26 07:43:50Z okayama $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.StockReportEnvironmentSCHParams.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;

/**
 * 在庫情報報告環境設定のスケジュール処理を行います。<BR>
 * <BR>
 * @version $Revision: 7277 $, $Date: 2010-02-26 16:43:50 +0900 (金, 26 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StockReportEnvironmentSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Back Slash\Yen mark **/
    private static final String BACK_SLASH = "\\";

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
    public StockReportEnvironmentSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、<BR>
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     * <BR>
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードがみつからない場合は要素数0のリストを返します。<BR>
     *          最大表示件数を超えた場合は最大表示件数まで表示します。<BR>
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

        // 取得項目
        skey.setFolderNameCollect();
        skey.setDataIdCollect();

        // 検索条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
        skey.setDataType(ExchangeEnvironment.DATA_STOCK);

        // 検索
        ExchangeEnvironment exEnvs = (ExchangeEnvironment)exHdl.findPrimary(skey);

        // 値の設定
        Params param = new Params();
        param.set(STOCK_DATA_FOLDER, exEnvs.getFolderName());
        param.set(STOCK_DATA_PREFIX, exEnvs.getDataId());

        // 返却パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();
        result.add(param);
        return result;
    }

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
        // フォルダパスのバイト数
        String filepath = ps[0].getString(STOCK_DATA_FOLDER);
        // ファイル名のバイト数
        String fileName = ps[0].getString(STOCK_DATA_PREFIX);

        // ファイルチェック
        File objFile = new File(filepath);
        if (!objFile.isDirectory())
        {
            // 6003019=指定されたフォルダは無効です。
            setMessage("6003019");
            return false;
        }

        // 格納フォルダの最終に"\"が付いているかを確認し、ない場合は付加する。
        if (!BACK_SLASH.equals(filepath.substring(filepath.length() - 1)))
        {
            filepath += BACK_SLASH;
        }

        // ファイルパスのバイト数チェック(YYYYMMDDHHMMSS.txt = 18文字)
        if (filepath.getBytes().length + fileName.getBytes().length + 18 > SystemInParameter.FILE_PATH_MAXLENGTH)
        {
            // 6023509={0}の格納フォルダとファイル名には合わせて{1}文字以内で入力してください。
            setMessage(WmsMessageFormatter.format(6023142, DisplayText.getText("LBL-W0489"),
                    SystemInParameter.FILE_PATH_MAXLENGTH));
            return false;
        }

        // バックアップデータ作成先と同じパスが無いかチェックする
        File backupFilePath = new File(WmsParam.HISTORY_HOSTDATA_PATH);
        if (backupFilePath.equals(objFile))
        {
            // 6023180={0}の出力先がバックアップファイルの作成先と同一です。
            setMessage(WmsMessageFormatter.format(6023180, DisplayText.getText("LBL-W0489")));
            return false;
        }

        // 登録処理を実行する
        registData(ps);
        //6001006=設定しました
        setMessage("6001006");

        return true;
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
     * 環境設定ファイルにデータ格納フォルダの登録および更新をします。<BR>
     *
     * @param ps 入力パラメータ配列
     * @throws CommonException 発生した全ての例外を通知します。
     */
    protected void registData(ScheduleParams... ps)
            throws CommonException
    {
        // 交換データ環境定義情報ハンドラの生成
        ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
        // 交換データ環境定義情報更新キーの生成
        ExchangeEnvironmentAlterKey exEnvAKey = new ExchangeEnvironmentAlterKey();

        // 件数分繰り返し
        for (Params p : ps)
        {
            // 値のセット
            exEnvAKey.clear();
            exEnvAKey.setJobType(Parameter.DATA_TYPE_STOCK);
            exEnvAKey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
            exEnvAKey.setDataType(ExchangeEnvironment.DATA_STOCK);
            exEnvAKey.updateFolderName(p.getString(STOCK_DATA_FOLDER));

            // 交換データ環境定義情報更新
            exHdl.modify(exEnvAKey);
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
