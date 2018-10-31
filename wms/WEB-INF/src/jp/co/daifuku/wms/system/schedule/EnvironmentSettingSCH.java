// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCHParams.DATA_NAME;
import static jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCHParams.DATA_TYPE;
import static jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCHParams.DESTINATION_FOLDER;
import static jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCHParams.HDN_TYPE;
import static jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCHParams.PREFIX_NAME;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.util.DisplayResource;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class EnvironmentSettingSCH
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

    /** データ受信ファイル名のセクション名 **/
    private static final String RECEIVED_FILENAME = "DATALOAD_FILENAME";

    /** データ報告フォルダのセクション名 **/
    private static final String REPORTDATA_FOLDER = "REPORTDATA_FOLDER";

    /** データ報告ファイル名のセクション名 **/
    private static final String REPORTDATA_FILENAME = "REPORTDATA_FILENAME";

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

    private static final String REPORT_DATA = "送信";

    private static final String RECEIVE_DATA = "受信";

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
    public EnvironmentSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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

        // 返却データ
        List<Params> result = new ArrayList<Params>();

        Params param = new EnvironmentSettingSCHParams();

        if (sysController == null)
        {
            return null;
        }

        // 環境設定ファイルから格納フォルダ、ファイル名を取得
        IniFileOperator iniFile = new IniFileOperator(WmsParam.ENVIRONMENT);

        // 入荷
        if (sysController.hasReceivingPack())
        {
            //入荷実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_RECEIVE));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_RECEIVE);
            result.add(param);
            //入荷予定
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_RECEIVE));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[REPORTTYPE_INSTOCKRECEIVE]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_RECEIVE);
            result.add(param);
        }

        // 入庫
        if (sysController.hasStoragePack())
        {
            // 入庫実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_STORAGE));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_STORAGE]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_STORAGE]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_STORAGE);
            result.add(param);
            // 入庫予定
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_STORAGE));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_STORAGE]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[REPORTTYPE_STORAGE]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_STORAGE);
            result.add(param);
        }

        // 出庫
        if (sysController.hasRetrievalPack())
        {

            //出庫実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_RETRIEVAL));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_RETRIEVAL]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_RETRIEVAL]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_RETRIEVAL);
            result.add(param);
            // 出庫予定
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_RETRIEVAL));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_RETRIEVAL]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[REPORTTYPE_RETRIEVAL]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_RETRIEVAL);
            result.add(param);
        }

        // 仕分
        if (sysController.hasSortingPack())
        {
            // 仕分実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_SORTING));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_PICKING]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_PICKING]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_SORTING);
            result.add(param);
            // 仕分予定
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_SORTING));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_PICKING]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[REPORTTYPE_PICKING]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_SORTING);
            result.add(param);
        }

        // 出荷
        if (sysController.hasShippingPack())
        {
            // 出荷実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_SHIPPING));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_SHIPPING]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_SHIPPING]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_SHIPPING);
            result.add(param);
            // 出荷予定
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_SHIPPING));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_SHIPPING]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[REPORTTYPE_SHIPPING]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_SHIPPING);
            result.add(param);
        }

        // 在庫
        if (sysController.hasStockPack())
        {
            // 在庫移動実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_MOVEMENT));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_STOCKMOVING]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_STOCKMOVING]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_MOVEMENT);
            result.add(param);

            // 在庫情報
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_STOCK));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_DATA_TYPE_STOCK]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_DATA_TYPE_STOCK]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_STOCK);
            result.add(param);
        }

        // 棚卸実績
        if (sysController.hasStoragePack())
        {
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_INVENTORY));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_INVENTORY]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_INVENTORY]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_INVENTORY);
            result.add(param);
        }

        // 予定外入庫実績
        if (sysController.hasStockPack())
        {
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_NOPLAN_STORAGE));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_NOPLANSTORAGE]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_NOPLANSTORAGE]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_NOPLAN_STORAGE);
            result.add(param);
        }

        // 予定外出庫実績
        if (sysController.hasStockPack())
        {
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_NOPLAN_RETRIEVAL));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_NOPLANRETRIEVAL]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_NOPLANRETRIEVAL]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_NOPLAN_RETRIEVAL);
            result.add(param);
        }

        // TC
        if (sysController.hasCrossdockPack())
        {
            // TC実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_CROSSDOCK));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_CROSS_DOCK]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_CROSS_DOCK]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_CROSSDOCK);
            result.add(param);
            // TC予定
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_CROSSDOCK));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[REPORTTYPE_CROSS_DOCK]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[REPORTTYPE_CROSS_DOCK]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_CROSSDOCK);
            result.add(param);
        }

        // マスタ
        if (sysController.hasMasterPack())
        {
            // 商品マスタ
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_ITEM_MASTER));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_ITEM_MASTER]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[RECEIVETYPE_ITEM_MASTER]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_ITEM_MASTER);
            result.add(param);
            // 仕入先マスタ
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_SUPPLIER_MASTER));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_SUPPLIER_MASTER]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[RECEIVETYPE_SUPPLIER_MASTER]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_SUPPLIER_MASTER);
            result.add(param);
            // 出荷先マスタ
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_CUSTOMER_MASTER));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_CUSTOMER_MASTER]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[RECEIVETYPE_CUSTOMER_MASTER]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_CUSTOMER_MASTER);
            result.add(param);
            // 商品固定棚マスタ
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_FIXED_LOCATION_MASTER));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[RECEIVETYPE_FIXED_LOCATION_MASTER]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[RECEIVETYPE_FIXED_LOCATION_MASTER]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_FIXED_LOCATION_MASTER);
            result.add(param);
        }

        // PCT出庫実績
        if (sysController.hasPCTRetrievalPack())
        {
            // PCT出庫実績
            param = new Params();
            param.set(DATA_TYPE, REPORT_DATA);
            param.set(DATA_NAME, DisplayResource.getReportData(SystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT));
            param.set(DESTINATION_FOLDER, iniFile.get(REPORTDATA_FOLDER, TYPE_KEY[REPORTTYPE_PCTRETRIEVAL]));
            param.set(PREFIX_NAME, iniFile.get(REPORTDATA_FILENAME, TYPE_KEY[REPORTTYPE_PCTRETRIEVAL]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT);
            result.add(param);
            // PCT商品マスタ
            param = new Params();
            param.set(DATA_TYPE, RECEIVE_DATA);
            param.set(DATA_NAME, DisplayResource.getLoadDataType(SystemInParameter.DATA_TYPE_PCTITEM_MASTER));
            param.set(DESTINATION_FOLDER, iniFile.get(RECEIVED_FOLDER, TYPE_KEY[RCEIVETYPE_PCTITEM_MASTER]));
            param.set(PREFIX_NAME, iniFile.get(RECEIVED_FILENAME, TYPE_KEY[RCEIVETYPE_PCTITEM_MASTER]));
            param.set(HDN_TYPE, SystemInParameter.DATA_TYPE_PCTITEM_MASTER);
            result.add(param);
        }
        return result;
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
                    setMessage(WmsMessageFormatter.format(6023142,
                            DisplayResource.getReportData(ps[i].getString(HDN_TYPE)),
                            SystemInParameter.FILE_PATH_MAXLENGTH));
                }
                else
                {
                    setMessage(WmsMessageFormatter.format(6023142,
                            DisplayResource.getLoadDataType(ps[i].getString(HDN_TYPE)),
                            SystemInParameter.FILE_PATH_MAXLENGTH));
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
        //定義ファイルに書き込みする
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
