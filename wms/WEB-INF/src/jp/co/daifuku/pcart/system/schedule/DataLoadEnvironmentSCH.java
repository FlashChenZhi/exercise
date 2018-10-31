// $Id: DataLoadEnvironmentSCH.java 7277 2010-02-26 07:43:50Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.system.schedule.DataLoadEnvironmentSCHParams.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;

/**
 * データ取込環境設定(ファイル)のスケジュール処理を行います。
 *
 * @version $Revision: 7277 $, $Date:: 2010-02-26 16:43:50 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class DataLoadEnvironmentSCH
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
    public DataLoadEnvironmentSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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

        // 返却データ
        List<Params> result = new ArrayList<Params>();
        Params param = null;

        // 取得項目
        skey.setJobTypeCollect();
        skey.setFolderNameCollect();
        skey.setDataIdCollect();
        skey.setDataNameCollect();

        // 条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);
        skey.setDataType(ExchangeEnvironment.DATA_PLAN);
        skey.setIsPrefix(ExchangeEnvironment.IS_PREFIX_FALSE);

        // 並び順
        skey.setJobTypeOrder(true);

        // 検索を行い件数分繰り返す
        ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
        for (ExchangeEnvironment exEnv : exEnvs)
        {
            // 返却パラメータの生成
            param = new Params();

            // 値を設定
            param.set(DATA_TYPE, exEnv.getJobType());
            param.set(PACKAGE_NAME, exEnv.getDataName());
            param.set(DESTINATION_FOLDER, exEnv.getFolderName());
            param.set(DATA_FILE_NAME, exEnv.getDataId());

            // 生成したパラメータを配列に格納
            result.add(param);
        }
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
        for (int i = 0; i < ps.length; i++)
        {
            // フォルダパスのバイト数
            String filepath = ps[i].getString(DESTINATION_FOLDER);
            // ファイル名のバイト数
            String fileName = ps[i].getString(DATA_FILE_NAME);
            // ファイルチェック
            File objFile = new File(filepath);

            // 存在チェック、ディレクトリチェック
            if (!objFile.isDirectory())
            {
                // 6003019=指定されたフォルダは無効です。
                setMessage("6003019");
                setErrorRowIndex(ps[i].getRowIndex());
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
                // 6023142={0}の格納フォルダとファイル名には合わせて{1}文字以内で入力してください。
                setMessage(WmsMessageFormatter.format(6023142,
                        DisplayResource.getFaLoadDataType(ps[i].getString(DATA_TYPE)),
                        SystemInParameter.FILE_PATH_MAXLENGTH));
                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }

            // 報告とフォルダパス + ファイル名が同一の場合はエラー
            if (!checkFilePathSynchronously(ps[i]))
            {
                return false;
            }

            // 外部データ履歴作成先と同じパスが無いかチェックする
            File backupFilePath = new File(WmsParam.HISTORY_HOSTDATA_PATH);
            if (backupFilePath.equals(objFile))
            {
                // 6023180={0}の出力先がバックアップファイルの作成先と同一です。
                setMessage(WmsMessageFormatter.format(6023180,
                        DisplayResource.getLoadDataType(ps[i].getString(DATA_TYPE))));
                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }
        }
        // 登録処理を実行する
        registData(ps);

        // 6001006=設定しました。
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
     * データ報告環境のデータ出力先と一致したものが無いかチェックします。<BR>
     * @param p システム入力パラメータ
     * @return データ報告の出力先と同じものがなければtrue、あればfalseを返します
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected boolean checkFilePathSynchronously(ScheduleParams p)
            throws CommonException
    {
        // 交換データ環境定義情報ハンドラの生成
        ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
        // 交換データ環境定義情報検索キーの生成
        ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();

        // 取得項目
        skey.setJobTypeCollect();
        skey.setFolderNameCollect();
        skey.setDataIdCollect();

        // 条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
        skey.setDataType(ExchangeEnvironment.DATA_PLAN);

        // 検索を行い件数分繰り返す
        ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
        for (ExchangeEnvironment exEnv : exEnvs)
        {
            // PCT出庫予定とPCT出庫実績の場合
            if (PCTSystemInParameter.DATA_TYPE_PICKINGRET.equals(p.getString(DATA_TYPE))
                    && PCTSystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT.equals(exEnv.getJobType()))
            {
                // 同一の場合
                if (p.getString(DESTINATION_FOLDER).toUpperCase().equals(exEnv.getFolderName().toUpperCase())
                        && p.getString(DATA_FILE_NAME).toUpperCase().equals(exEnv.getDataId().toUpperCase()))
                {
                    // {0}の取り込み先がデータ報告の出力先と同一です。
                    setMessage(WmsMessageFormatter.format(6023144, p.getString(PACKAGE_NAME)));
                    setErrorRowIndex(p.getRowIndex());
                    return false;
                }
            }
            // PCT入荷予定とPCT入荷実績の場合
            else if (PCTSystemInParameter.DATA_TYPE_PICKINGRECEIVE.equals(p.getString(DATA_TYPE))
                    && PCTSystemInParameter.DATA_TYPE_PCTINSTOCK_RESULT.equals(exEnv.getJobType()))
            {
                // 同一の場合
                if (p.getString(DESTINATION_FOLDER).toUpperCase().equals(exEnv.getFolderName().toUpperCase())
                        && p.getString(DATA_FILE_NAME).toUpperCase().equals(exEnv.getDataId().toUpperCase()))
                {
                    // {0}の取り込み先がデータ報告の出力先と同一です。
                    setMessage(WmsMessageFormatter.format(6023144, p.getString(PACKAGE_NAME)));
                    setErrorRowIndex(p.getRowIndex());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 環境設定ファイルにデータ格納フォルダの登録および更新をします。<BR>
     *
     * @param ps パラメータ
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
            exEnvAKey.setJobType(p.getString(DATA_TYPE));
            exEnvAKey.setIsPrefix(ExchangeEnvironment.IS_PREFIX_FALSE);
            exEnvAKey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);
            exEnvAKey.updateDataId(p.getString(DATA_FILE_NAME));
            exEnvAKey.updateFolderName(p.getString(DESTINATION_FOLDER));

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
