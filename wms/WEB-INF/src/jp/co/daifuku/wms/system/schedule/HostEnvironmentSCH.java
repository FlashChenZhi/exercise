// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.HostEnvironmentSCHParams.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.util.DisplayResource;

/**
 * 環境設定のスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class HostEnvironmentSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** データタイプ 入荷 **/
    private static final int REPORTTYPE_INSTOCKRECEIVE = 0;

    /** データタイプ 入庫 **/
    private static final int REPORTTYPE_STORAGE = 1;

    /** データタイプ 出庫 **/
    private static final int REPORTTYPE_RETRIEVAL = 2;

    /** データタイプ 仕分 **/
    private static final int REPORTTYPE_PICKING = 3;

    /** データタイプ 出荷 **/
    private static final int REPORTTYPE_SHIPPING = 4;

    /** データタイプ 在庫移動 **/
    private static final int REPORTTYPE_STOCKMOVING = 5;

    /** データタイプ 棚卸 **/
    private static final int REPORTTYPE_INVENTORY = 6;

    /** データタイプ 予定外入庫 **/
    private static final int REPORTTYPE_NOPLANSTORAGE = 7;

    /** データタイプ 予定外出庫 **/
    private static final int REPORTTYPE_NOPLANRETRIEVAL = 8;

    /** データタイプ クロスドック **/
    private static final int REPORTTYPE_CROSS_DOCK = 9;

    /** データタイプ PCT出庫 **/
    private static final int REPORTTYPE_PCTRETRIEVAL = 10;

    /** データタイプ　在庫情報 **/
    private static final int REPORTTYPE_DATA_TYPE_STOCK = 11;

    /** データタイプ　仕入先マスタ **/
    private static final int RECEIVETYPE_SUPPLIER_MASTER = 12;

    /** データタイプ　出荷先マスタ **/
    private static final int RECEIVETYPE_CUSTOMER_MASTER = 13;

    /** データタイプ　商品マスタ **/
    private static final int RECEIVETYPE_ITEM_MASTER = 14;

    /** データタイプ　商品固定棚マスタ **/
    private static final int RECEIVETYPE_FIXED_LOCATION_MASTER = 15;

    /** データタイプ　PCT商品マスタ **/
    private static final int RCEIVETYPE_PCTITEM_MASTER = 16;

    /** データ受信フォルダのセクション名 **/
    private static final String RECEIVED_FOLDER = "DATALOAD_FOLDER";

    /** データ報告フォルダのセクション名 **/
    private static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

    //    /** データ報告報告単位のセクション名 **/
    //    private static final String REPORT_TYPE = "REPORT_TYPE";

    /** Back Slash\Yen mark **/
    private static final String BACK_SLASH = "\\";

    /** データ環境設定キーをString配列で定義 **/
    private static final String[] TYPE_KEY = {
        "RECEIVE_SUPPORT",
        "STORAGE_SUPPORT",
        "RETRIEVAL_SUPPORT",
        "PICKING_SUPPORT",
        "SHIP_INSPECTION",
        "MOVE_SUPPORT",
        "INVENT_SUPPORT",
        "NOPLANSTRAGE_SUPPORT",
        "NOPLANRETRIEVAL_SUPPORT",
        "CROSSDOCK_SUPPORT",
        "PCTRETRIEVAL_SUPPORT",
        "STOCK_SUPPORT",
        "MASTER_SUPPLIER",
        "MASTER_CUSTOMER",
        "MASTER_ITEM",
        "AREA_FIXEDLOCATE",
        "PCTITEM_SUPPORT"
    };

    /** データ区分を定義 **/

    private static final String REPORT_DATA = DisplayText.getText("RDB-W0065");

    private static final String RECEIVE_DATA = DisplayText.getText("RDB-W0064");

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
    public HostEnvironmentSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
        // システム定義情報のコンストラクタ
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // ソート前の返却データ

        Params param = new HostEnvironmentSCHParams();

        // 返却データ
        List<Params> retParam = new ArrayList<Params>();


        if (sysController == null)
        {
            return null;
        }

        // FA用処理　DBから値を取得する
        if (sysController.isFaDaEnabled())
        {
            // 交換データ環境定義情報ハンドラの生成
            ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
            // 交換データ環境定義情報検索キーの生成
            ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();

            // 取得項目
            // 取込データ区分
            skey.setJobTypeCollect();
            // 取込フォルダ
            skey.setFolderNameCollect();
            // 送受信区分
            skey.setExchangeTypeCollect();
            // データID
            skey.setDataIdCollect();

            //ソート条件のセット
            //ジョブタイプ
            skey.setJobTypeOrder(true);
            //送受信区分
            skey.setExchangeTypeOrder(false);

            // 検索を行い件数分繰り返す
            ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
            for (ExchangeEnvironment exEnv : exEnvs)
            {
                // 返却パラメータの生成
                param = new Params();

                // 送受信区分
                if (exEnv.getExchangeType().equals("1"))
                {
                    param.set(DATA_TYPE, RECEIVE_DATA);

                    // データ名称
                    param.set(DATA_NAME, DisplayResource.getFaLoadDataType(exEnv.getJobType()));

                }
                else if (exEnv.getExchangeType().equals("0"))
                {
                    param.set(DATA_TYPE, REPORT_DATA);

                    // データ名称
                    param.set(DATA_NAME, DisplayResource.getFaReportData(exEnv.getJobType()));
                }

                // プレフィックス名
                param.set(PREFIX_NAME, exEnv.getDataId());

                // 取込フォルダ
                param.set(DESTINATION_FOLDER, exEnv.getFolderName());

                // データタイプを隠し項目にセット
                param.set(HDN_TYPE, exEnv.getJobType());

                // 生成したパラメータを配列に格納

                retParam.add(param);
                //result.add(param);
            }
        }
        // DA用処理　ファイルから値を取得する
        else if (!sysController.isFaDaEnabled())
        {

            // 交換データ環境定義情報ハンドラの生成
            ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
            // 交換データ環境定義情報検索キーの生成
            ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();

            // 取得項目
            // 取込データ区分
            skey.setJobTypeCollect();
            // 取込フォルダ
            skey.setFolderNameCollect();
            // 送受信区分
            skey.setExchangeTypeCollect();
            // データID
            skey.setDataIdCollect();

            //ソート条件のセット
            //ジョブタイプ
            skey.setJobTypeOrder(true);
            //送受信区分
            skey.setExchangeTypeOrder(false);

            // 検索を行い件数分繰り返す
            ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
            for (ExchangeEnvironment exEnv : exEnvs)
            {
                // 返却パラメータの生成
                param = new Params();

                // 送受信区分
                if (exEnv.getExchangeType().equals("1"))
                {
                    param.set(DATA_TYPE, RECEIVE_DATA);

                    // データ名称
                    param.set(DATA_NAME, DisplayResource.getLoadDataType(exEnv.getJobType()));

                }
                else if (exEnv.getExchangeType().equals("0"))
                {
                    param.set(DATA_TYPE, REPORT_DATA);

                    // データ名称
                    param.set(DATA_NAME, DisplayResource.getReportData(exEnv.getJobType()));
                }

                // プレフィックス名
                param.set(PREFIX_NAME, exEnv.getDataId());

                // 取込フォルダ
                param.set(DESTINATION_FOLDER, exEnv.getFolderName());

                // データタイプを隠し項目にセット
                param.set(HDN_TYPE, exEnv.getJobType());

                // 生成したパラメータを配列に格納

                retParam.add(param);
                //result.add(param);
            }
        }
        return retParam;
        //return result;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // システム定義情報のコンストラクタ
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // 指定されたフォルダが正しいかチェック
        for (int i = 0; i < ps.length; i++)
        {
            // フォルダパスのバイト数
            String filepath = ps[i].getString(DESTINATION_FOLDER);
            // ファイル名のバイト数
            String fileName = ps[i].getString(PREFIX_NAME);
            // ファイルチェック
            File objFile = new File(filepath);

            if (!objFile.isDirectory())
            {
                // 6003019=指定されたフォルダは無効です
                setMessage("6003019");
                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }
            // 格納フォルダの最終に"\"が付いているかを確認し、ない場合は付加する
            if (!BACK_SLASH.equals(filepath.substring(filepath.length() - 1)))
            {
                filepath += BACK_SLASH;
            }
            // ファイルパスのバイト数チェック(YYYYMMDDHHMMSS.txt = 18文字)
            if (filepath.getBytes().length + fileName.getBytes().length + 18 > SystemInParameter.FILE_PATH_MAXLENGTH)
            {
                // 6023509={0}の格納フォルダとファイル名には合わせて{1}文字以内で入力してください
                if (REPORT_DATA.equals(ps[i].get(DATA_TYPE)))
                {
                    // FA用処理の追加
                    if (sysController.isFaDaEnabled())
                    {
                        setMessage(WmsMessageFormatter.format(6023142,
                                DisplayResource.getFaReportData(ps[i].getString(HDN_TYPE)),
                                SystemInParameter.FILE_PATH_MAXLENGTH));

                    }
                    // DA用処理
                    else
                    {
                        setMessage(WmsMessageFormatter.format(6023142,
                                DisplayResource.getReportData(ps[i].getString(HDN_TYPE)),
                                SystemInParameter.FILE_PATH_MAXLENGTH));

                    }
                }
                else
                {

                    // FA用処理の追加
                    if (sysController.isFaDaEnabled())
                    {
                        setMessage(WmsMessageFormatter.format(6023142,
                                DisplayResource.getFaLoadDataType(ps[i].getString(HDN_TYPE)),
                                SystemInParameter.FILE_PATH_MAXLENGTH));
                    }
                    else
                    {
                        setMessage(WmsMessageFormatter.format(6023142,
                                DisplayResource.getLoadDataType(ps[i].getString(HDN_TYPE)),
                                SystemInParameter.FILE_PATH_MAXLENGTH));
                    }
                }
                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }
            // バックアップデータ作成先と同じパスが無いかチェックする
            File backupFilePath = new File(WmsParam.HISTORY_HOSTDATA_PATH);
            if (backupFilePath.equals(objFile))
            {
                // 6023180={0}の出力先がバックアップファイルの作成先と同一です。
                if (REPORT_DATA.equals(ps[i].get(DATA_TYPE)))
                {
                    setMessage(WmsMessageFormatter.format(6023180,
                            DisplayResource.getReportData(ps[i].getString(HDN_TYPE))));
                }
                else
                {
                    setMessage(WmsMessageFormatter.format(6023180,
                            DisplayResource.getLoadDataType(ps[i].getString(HDN_TYPE))));
                }

                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }
        }
        //FA用処理　DBを更新する。
        if (sysController.isFaDaEnabled())
        {
            //ジョブタイプで検索し、セットする。
            for (Params p : ps)
            {
                // 交換データ環境定義情報ハンドラの生成
                ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
                // 交換データ環境定義情報更新キーの生成
                ExchangeEnvironmentAlterKey exEnvAKey = new ExchangeEnvironmentAlterKey();

                if (REPORT_DATA.equals(p.get(DATA_TYPE)))
                {
                    exEnvAKey.setExchangeType("0");
                }
                else if (RECEIVE_DATA.equals(p.get(DATA_TYPE)))
                {
                    exEnvAKey.setExchangeType("1");
                }

                //検索キーのセット ジョブタイプ
                exEnvAKey.setJobType(p.getString(HDN_TYPE));

                // 受信フォルダのセット
                exEnvAKey.updateFolderName(p.getString(DESTINATION_FOLDER));

                // 交換データ環境定義情報更新
                exHdl.modify(exEnvAKey);
            }
        }
        // DA用処理 ファイルを更新する
        else if (!sysController.isFaDaEnabled())
        {
            //定義ファイルに書き込みする
            registData(ps);
        }

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
        // 環境情報ディスクリプタ取得
        IniFileOperator io = new IniFileOperator(WmsParam.ENVIRONMENT);

        for (int i = 0; i < ps.length; i++)
        {
            // 入力ファイルパス
            String wSetFolder = ps[i].getString(DESTINATION_FOLDER);
            // パッケージフラグ
            String packageFlag = ps[i].getString(HDN_TYPE);
            // 受信、送信データフラグ
            String isReport = ps[i].getString(DATA_TYPE);

            // 格納フォルダの最終に"\"が付いているかを確認し、ない場合は付加する
            if (!BACK_SLASH.equals(wSetFolder.substring(wSetFolder.length() - 1)))
            {
                wSetFolder += BACK_SLASH;
            }

            //入荷

            if (SystemInParameter.DATA_TYPE_RECEIVE.equals(packageFlag))
            {
                if (isReport.equals(REPORT_DATA))
                {
                    io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE], wSetFolder);
                }
                else
                {
                    io.set(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE], wSetFolder);
                }
            }
            // 入庫
            else if (SystemInParameter.DATA_TYPE_STORAGE.equals(packageFlag))
            {
                if (isReport.equals(REPORT_DATA))
                {
                    io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_STORAGE], wSetFolder);
                }
                else
                {
                    io.set(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_STORAGE], wSetFolder);
                }
            }
            //出庫
            else if (SystemInParameter.DATA_TYPE_RETRIEVAL.equals(packageFlag))
            {
                if (isReport.equals(REPORT_DATA))
                {
                    io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_RETRIEVAL], wSetFolder);
                }
                else
                {
                    io.set(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_RETRIEVAL], wSetFolder);
                }
            }
            //仕分
            else if (SystemInParameter.DATA_TYPE_SORTING.equals(packageFlag))
            {
                if (isReport.equals(REPORT_DATA))
                {
                    io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_PICKING], wSetFolder);
                }
                else
                {
                    io.set(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_PICKING], wSetFolder);
                }
            }
            //出荷
            else if (SystemInParameter.DATA_TYPE_SHIPPING.equals(packageFlag))
            {
                if (isReport.equals(REPORT_DATA))
                {
                    io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_SHIPPING], wSetFolder);
                }
                else
                {
                    io.set(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_SHIPPING], wSetFolder);
                }
            }
            // 在庫移動
            else if (SystemInParameter.DATA_TYPE_MOVEMENT.equals(packageFlag))
            {
                io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_STOCKMOVING], wSetFolder);
            }
            // 棚卸
            else if (SystemInParameter.DATA_TYPE_INVENTORY.equals(packageFlag))
            {
                io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_INVENTORY], wSetFolder);
            }
            // 予定外入庫
            else if (SystemInParameter.DATA_TYPE_NOPLAN_STORAGE.equals(packageFlag))
            {
                io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_NOPLANSTORAGE], wSetFolder);
            }
            // 予定外出庫
            else if (SystemInParameter.DATA_TYPE_NOPLAN_RETRIEVAL.equals(packageFlag))
            {
                io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_NOPLANRETRIEVAL], wSetFolder);
            }
            // クロスドック
            else if (SystemInParameter.DATA_TYPE_CROSSDOCK.equals(packageFlag))
            {
                if (isReport.equals(REPORT_DATA))
                {
                    io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_CROSS_DOCK], wSetFolder);
                }
                else
                {
                    io.set(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_CROSS_DOCK], wSetFolder);
                }
            }
            // PCT出庫
            else if (SystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT.equals(packageFlag))
            {
                io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_PCTRETRIEVAL], wSetFolder);
            }
            //在庫情報
            else if (SystemInParameter.DATA_TYPE_STOCK.equals(packageFlag))
            {
                io.set(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_DATA_TYPE_STOCK], wSetFolder);
            }
            //商品マスタ
            else if (SystemInParameter.DATA_TYPE_ITEM_MASTER.equals(packageFlag))
            {
                io.set(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_ITEM_MASTER], wSetFolder);
            }
            //仕入先マスタ
            else if (SystemInParameter.DATA_TYPE_SUPPLIER_MASTER.equals(packageFlag))
            {
                io.set(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_SUPPLIER_MASTER], wSetFolder);
            }
            //出荷先マスタ
            else if (SystemInParameter.DATA_TYPE_CUSTOMER_MASTER.equals(packageFlag))
            {
                io.set(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_CUSTOMER_MASTER], wSetFolder);
            }
            //商品固定棚マスタ
            else if (SystemInParameter.DATA_TYPE_FIXED_LOCATION_MASTER.equals(packageFlag))
            {
                io.set(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_FIXED_LOCATION_MASTER], wSetFolder);
            }
            //PCT商品マスタ
            else if (SystemInParameter.DATA_TYPE_PCTITEM_MASTER.equals(packageFlag))
            {
                io.set(RECEIVED_FOLDER, TYPE_KEY[RCEIVETYPE_PCTITEM_MASTER], wSetFolder);
            }
        }

        // 環境情報データの書込み
        io.flush();
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
