// $Id: DataOperator.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.toolmenu.dataoperate;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.sql.DataSourceConfig;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.converter.Converter;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.TerminalArea;
import jp.co.daifuku.wms.asrs.tool.schedule.AccessNgShelfCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.AisleCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.DummyStationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.GroupControllerCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.HardZoneCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.LoadSizeCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.MachineCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.RouteCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.SoftZoneCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.SoftZonePriorityCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.SoftZoneRangeCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.StationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.UnavailableLocationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WarehouseCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WidthCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WorkPlaceCreater;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.util.BackupUtils;


/**<jp>
 * このクラスはログイン時にTEMP表の作成、TEMP表への既存データの書き込み<BR>
 * また、システム定義ファイル生成でTEMP表のデータを<BR>
 * テキストファイルへの書き込みを行うメソッドを集めたものです。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/12</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/10</TD><TD>hondou</TD><TD>checkメソッド修正。<BR>
 * 使用不可棚でAisle、作業場全体でDummyStationのconsystencycheckを呼んでいたため修正しました。
 * </TD></TR>
 * <TR><TD>2003/12/12</TD><TD>okamura</TD><TD>createTextFilesメソッドに使用不可棚に関する記述追加。<BR>
 * 使用不可棚の定義ファイルを作成、SHELF、STATIONTYPE表からの削除を行うよう追記。<BR>
 * deleteShelf、getText、writeメソッド追加。
 * </TD></TR>
 * <TR><TD>2003/12/19</TD><TD>okamura</TD><TD>Modified method of deleteShelf.<BR>
 * Modified to do nothing when the restricted locations aren't exist SHELF table.</TD></TR>
 * <TR><TD>2004/04/14</TD><TD>kubo</TD><TD>システム予約の分類名称をリソースから取得</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is a collection of methods, for example to be used when creating the TEMP table at log-in,
 * wrieting the existing data in TEMP table, or used when writing the data in TEMP table in the text file
 * when gfenerating the system definition file.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/12</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/10</TD><TD>hondou</TD><TD>Modified method of check.<BR>
 * In the place which should call consisytencyCheck of UnavailableLocationCreater and FloorAllCreater, 
 * since consisytencyCheck of AisleCreater and DummyStation was called, it corrected.</TD></TR>
 * <TR><TD>2003/12/12</TD><TD>okamura</TD><TD>Modified method of createTextFiles<BR>
 * Modified to create the textFile of UnavailableLocationCreater, and delete the data from SHELF table and STATIONTYPE table.<BR>
 * append the method of deleteShelf, getText and write.</TD></TR>
 * <TR><TD>2003/12/19</TD><TD>okamura</TD><TD>Modified method of deleteShelf.<BR>
 * Modified to do nothing when the restricted locations aren't exist SHELF table.</TD></TR>
 * <TR><TD>2004/04/14</TD><TD>kubo</TD><TD>The classification name of system reservation is acquired from a resource.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class DataOperator
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 分類名称
     </jp>*/
    /**<en>
     * Classification name
     </en>*/

    /**<jp> 使用不可棚情報を保存するテキスト名称  </jp>*/
    /**<en> The name of the text for saving the restricted locations  </en>*/
    public final String SHELFTEXT_NAME = "UnUnavailableLoc.txt";

    /**<jp>
     * デリミタ
     </jp>*/
    /**<en>
     * Delimita
     </en>*/
    public static final String DELIM = ",";

    // Class private fields ------------------------------------------
    /**<jp> <CODE>Connection</CODE> </jp>*/
    /**<en> <CODE>Connection</CODE> </en>*/
    private Connection wConn = null;

    /**
     * 処理実行時に発生した問題の詳細メッセージを格納する。
     */
    protected String wMessage = "";

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<code>DataOperator</code>のインスタンスを作成します。
     * @param conn <CODE>Connection</CODE>
     </jp>*/
    /**<en>
     * Newly create the <code>DataOperator</code> instance.
     * @param conn <CODE>Connection</CODE>
     </en>*/
    public DataOperator(Connection conn)
    {
        wConn = conn;
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * ログイン初期処理を行います。<BR>
     * 新規製番の場合は新規に製番フォルダを作成し、データベースにTEMP表を作成します。<BR>
     * 次にデフォルトのリソースファイルを製番フォルダにコピーをします。<BR>
     * 既存製番の場合は製番フォルダからテキストデータを読み込みデータベースに登録します。<BR>
     * また、製番フォルダのパスはユーザ情報とともにセッションで保持します。
     * 製番フォルダの取得方法<BR>
     * <code>DaifukuUserInformation</code>インスタンスををセッションから取得し<BR>
     * getUserProperty("WorkFolder")メソッドを実行する。←"WorkFolder"という名前で保持しているため。
     * @param  request HttpServletRequest
     * @param  folder 入力フォルダ
     * @return 成功／失敗
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Initial process for log-in.<BR>
     * In case of new job number, it newly creates the job number file and creates TEMP table 
     * in database.<BR>
     * Then get a copy of default resource file in the ob number folder.<BR>
     * In case of existing job number, it reads the text data in the job number folder, then register 
     * in database.<BR>
     * Also the path of the job number folder will be preserved along with user information by session.
     * How to retrieve the job number folder<BR>
     * Retrieve the <code>DaifukuUserInformation</code> instance from the session, then<BR>
     * execute the getUserProperty("WorkFolder") method. <-as it is preserved as "WorkFolder".
     * @param  request HttpServletRequest
     * @param  folder :input data folder
     * @return succeeded/failed
     </en>*/
    public boolean login(HttpServletRequest request, String folder)
    {
        //<jp> 製番ファイルフォルダのパスをリソースから取得します。</jp>
        //<en> Retrieve the path of job number file folder from the resource.</en>
        String currentFolder = ToolParam.getParam("EAWC_ITEM_ROUTE_PATH") + "/" + folder;

        System.out.println("current Folder. <" + currentFolder + ">");

        //<jp> セッションに保持に保持している作業フォルダを一度クリアします。</jp>
        request.getSession().setAttribute("WorkFolder", null);

        //<jp> デフォルトのroute.txt</jp>
        //<en> Default route.txt</en>
        String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
        //<jp> ファイルリスト取得</jp>
        //<en> Retrieve the file list.</en>
        File file = new File(currentFolder);
        //<jp> 指定されたパスが存在するかどうか</jp>
        //<en> Whether/not the specified path exists.</en>
        if (!file.exists())
        {
            try
            {
                if (file.mkdirs())
                {
                    System.out.println("The folder was created.");
                }
                else
                {
                    System.out.println("Creation of folder has failed.");
                    //<jp>フォルダの作成に失敗しました</jp>
                    //<en>Failed to create a folder.</en>
                    setMessage("6127003");
                    return false;
                }
                //<jp> 製番ファイルにリソースファイルをコピーする。AWCParam, Route</jp>
                //<en> Copy the resource file to the job number file. AWCParam, Route</en>
                ToolFindUtil.copyFile(defaultRouteText, currentFolder + "/" + "route.txt");

                // USER/MENU関係のテーブルについては
                // WMSユーザーのデータをそのまま使用し、テキストファイル化した上で
                // WMSTOOLユーザーのテーブルにデータをセットアップする。
                // 対象はDMAREAとTERMINAL表
                UserMenuTableToTextFromWMS(currentFolder);

                //<jp> デフォルトテーブル作成コマンドのパス</jp>
                //<en> Path of default table creation command.</en>
                DataSourceConfig dsc = DataSourceConfig.getInstance();
                Properties prop = dsc.findProperties(WMSToolConstants.DATASOURCE_NAME);

                String command = ToolParam.getParam("DEFAULT_TABLE_CREATOR_PATH");
                command = command + " " + prop.getProperty("username") + " " + prop.getProperty("password");
                Runtime runtime = Runtime.getRuntime();
                Process pr = runtime.exec(command);

                InputStream is = pr.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null)
                {
                    //<jp> 画面出力。</jp>
                    //<en> Output of the screen.</en>
                    System.out.println(line);
                }

                // ファイル化したDMAREA表をWMSTOOLにINSERTする。
                UserMenuTextToTable(currentFolder);

                // INSERT後、ファイルは不要なので削除する。
                deleteFile(currentFolder);

                //<jp> トランザクションを確定する。</jp>
                //<en> Fix the transaction.</en>
                wConn.commit();

                //<jp> 作成した作業フォルダをセッションに保持します。</jp>
                //<en> Preserving the work folder in session.</en>
                request.getSession().setAttribute("WorkFolder", currentFolder);

                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                try
                {
                    wConn.rollback();
                }
                catch (SQLException ee)
                {
                    //<jp> データベースエラーが発生しました。{0}</jp>
                    //<en> Database error occurred.{0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6126001, ee), this.getClass().getName());
                }
                //<jp> 表作成に失敗しました。</jp>
                //<en> Failed to create the table.</en>
                setMessage("6127002");
                return false;
            }
        }
        //<jp> すでに製番フォルダが存在していた場合</jp>
        //<en> If the job number folder already exists,</en>
        else
        {
            try
            {
                //<jp> デフォルトテーブル作成コマンドのパス</jp>
                //<en> Path of the default table creation command</en>
                DataSourceConfig dsc = DataSourceConfig.getInstance();
                Properties prop = dsc.findProperties(WMSToolConstants.DATASOURCE_NAME);

                String command = ToolParam.getParam("DEFAULT_TABLE_CREATOR_PATH");
                command = command + " " + prop.getProperty("username") + " " + prop.getProperty("password");

                Runtime runtime = Runtime.getRuntime();
                Process pr = runtime.exec(command);
                InputStream is = pr.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null)
                {
                    //<jp> 画面出力。</jp>
                    //<en> Output of the screen.</en>
                    System.out.println(line);
                }

                //<jp> テーブル一覧をリソースから取得する。</jp>
                //<en> Retrieve the table list from the resource.</en>
                String[] tablenames = ToolParam.getParamArray("TABLE_NAMES");
                // USER/MENU関係のテーブルについては
                // WMSユーザーのデータをそのまま使用し、テキストファイル化した上で
                // WMSTOOLユーザーのテーブルにデータをセットアップする。
                UserMenuTableToTextFromWMS(currentFolder);

                for (int i = 0; i < tablenames.length; i++)
                {
                    //<jp> 挿入前にテーブルをクリアしておく。</jp>
                    //<jp> 製番フォルダにあるテキストファイルを読み込んでテーブルに書き込む。</jp>
                    //<jp> 書き込むテーブル名はgetTableName()により決められる現在は"TEMP_"が頭につく。</jp>
                    //<en> Clear the table before insertion.</en>
                    //<en> It reads the text file in job number file, then write in the table.</en>
                    //<en> The table name which will be written will be derermined by getTableName().</en>
                    //<en> Currently it adds "TEMP_" to the head.</en>
                    String filename = currentFolder + "/" + tablenames[i] + ".txt";
                    File textFile = new File(filename);
                    if (textFile.exists())
                    {
                        Converter.textToTable(wConn, getTableName(tablenames[i]), filename);
                    }
                    else
                    {
                        System.out.println("File Not Found [" + filename + "]");
                    }
                }

                // 使用不可棚設定テキストから、使用不可棚をAS/RS棚情報へ復元します。
                createUnavailableLocation(wConn, currentFolder);

                // ファイル化したDMAREA表をWMSTOOLにINSERTする。
                UserMenuTextToTable(currentFolder);

                // INSERT後、ファイルは不要なので削除する。
                deleteFile(currentFolder);

                //<jp> トランザクションを確定する。</jp>
                //<en> Fix the transaction.</en>
                wConn.commit();

                //<jp> 作業フォルダをセッションに保持します。</jp>
                //<en> Preserving the work folder in session.</en>
                request.getSession().setAttribute("WorkFolder", currentFolder);

                return true;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                try
                {
                    wConn.rollback();
                }
                catch (SQLException e)
                {
                    //<jp> データベースエラーが発生しました。{0}</jp>
                    //<en> Database error occurred.{0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                }
                //<jp> データベース書き込みに失敗しました。</jp>
                //<en> Failed to write data in database.</en>
                setMessage("6127001");

                deleteFile(currentFolder);

                return false;
            }
        }
    }

    /**<jp>
     * システムデータ作成処理を行います。
     * TEMP表にあるデータをテキストデータに書き込みます。
     * テキストファイルに書き込む前にDBのデータが整合性のあっているデータかどうかのチェックを行います。
     * エラーがある場合はログに書き込みます。
     * @param  request HttpServletRequest
     * @return 成功／失敗
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the creation of system data.
     * It writes data from TEMP table into the text data.
     * The data will be checked for consistency before writing into text file.
     * If any error is  found, the log will be recorded.
     * @param  request HttpServletRequest
     * @return succeeded/failed
     </en>*/
    public boolean createTextFiles(HttpServletRequest request)
    {
        return createTextFiles(request, true);
    }

    /**<jp>
     * システムデータ作成処理を行います。
     * TEMP表にあるデータをテキストデータに書き込みます。
     * テキストファイルに書き込む前にDBのデータが整合性のあっているデータかどうかのチェックを行います。
     * エラーがある場合はログに書き込みます。
     * @param  request HttpServletRequest
     * @param  check 作成時に整合性チェックを行う場合:true それ以外:false
     * @return 成功／失敗
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the creation of system data.
     * It writes data from TEMP table into the text data.
     * The data will be checked for consistency before writing into text file.
     * If any error is  found, the log will be recorded.
     * @param  request HttpServletRequest
     * @param  check -consistency check ito be done at data creation:true, other case:false
     * @return succeeded/failed
     </en>*/
    public boolean createTextFiles(HttpServletRequest request, boolean check)
    {
        try
        {
            //<jp> 製番フォルダのパス。</jp>
            //<en> Path of job number folder</en>
            String crrentfolder = (String)request.getSession().getAttribute("WorkFolder");
            /*****************************************************************************************
             //<jp> ここで今までtemp表に登録してきた内容が整合性を保っているかどうかをチェックする。</jp>
             //<jp> また、チェックしてエラーが発生した場合にはログに落とす。</jp>
             //<jp> ロケーション設定[グループコントローラ]</jp>
             //<jp> ロケーション設定[倉庫]</jp>
             //<jp> ロケーション設定[アイル]</jp>
             //<jp> ロケーション設定[使用不可棚]</jp>
             //<jp> ハードゾーン設定[範囲]</jp>
             //<jp> ステーション設定[ステーション]</jp>
             //<jp> ステーション設定[ダミーステーション]</jp>
             //<jp> ステーション設定[作業場]</jp>
             //<jp> ステーション設定[搬送ルート]</jp>
             //<jp> 機器情報設定</jp>
             //<en> Data will be checked here whether/not the data which have been registered in TEMP table</en>
             //<en> sustains consistency.</en>
             //<en> If error is found as a result of check, log will be recorded.</en>
             //<en> location setting[group controller]</en>
             //<en> location setting[warehouse]</en>
             //<en> location setting[aisle]</en>
             //<en> location setting[unavailable location]</en>
             //<en> hard zone setting[range]</en>
             //<en> station setting[station]</en>
             //<en> station setting[dummy station]</en>
             //<en> station setting[workshop]</en>
             //<en> station setting[carry route]</en>
             //<en> machine information setting</en>
             ********************************************************************************************/
            if (check)
            {
                if (!check(crrentfolder, request.getLocale()))
                {
                    //<jp> 整合性チェックでエラーが発生しました。ログを参照してください。</jp>
                    //<en> Error occurred during the data consistency check. Please see log.</en>
                    setMessage("6127004");
                    return false;
                }
            }

            //<jp> 使用不可棚に設定されている棚をテキストに書き込み、その棚をSHELF表、STATIONTYPE表から削除する。</jp>
            //<en> Create the textFile of UnavailableLocationCreater, and delete the data from SHELF table and STATIONTYPE table.</en>
            deleteShelf(crrentfolder);

            updateLoadSizeCheck();

            // 端末エリア情報は、端末情報、ステーション情報から再セットする。
            createTerminalAreaTable();

            //<jp> テーブル一覧をリソースから取得する。</jp>
            //<en> Retrieve the list of tables from the resource.</en>
            String[] tablenames = ToolParam.getParamArray("TABLE_NAMES");
            for (int i = 0; i < tablenames.length; i++)
            {
                //<jp> DBの内容をテキストに落とす。</jp>
                //<en> Record the contents of database in text.</en>
                Converter.tableToText(wConn, getTableName(tablenames[i]), crrentfolder + "/" + tablenames[i] + ".txt");
            }

            // 一旦削除したSHELF表を使用不可棚設定テキストから、使用不可棚をAS/RS棚情報へ復元します。
            createUnavailableLocation(wConn, crrentfolder);

            wConn.commit();

            System.out.println("Writing of the text has completed.");
            setMessage("6121004");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //<jp> 致命的なエラー</jp>
            //<en> Fatal error</en>
            setMessage("6127006");
            return false;
        }
    }

    /**<jp>
     * 指定されたテーブル名称からTEMP表名を作成します。
     * @param  tbl 実表名
     * @return TEMP表名
     </jp>*/
    /**<en>
     * Create the name of TEMP table based on the specified table name.
     * @param  tbl :actual table name
     * @return :name of TEMP table
     </en>*/
    public String getTableName(String tbl)
    {
        return ("TEMP_" + tbl);
    }

    /**<jp>
     * メッセージ格納エリアにセットされたメッセージを返します。
     * @return メッセージ
     </jp>*/
    /**<en>
     * Return the message set in message storage area.
     * @return message
     </en>*/
    public String getMessage()
    {
        return wMessage;
    }

    /**<jp>
     * 指定されたメッセージをメッセージ格納エリアにセットします。
     * @param msg メッセージ
     </jp>*/
    /**<en>
     * Sets the specified message into the message storage area.
     * @param msg :message
     </en>*/
    protected void setMessage(String msg)
    {
        wMessage = msg;
    }

    /**<jp>
     * データベーステーブルを切り捨てます。
     * @param  tbl 切り捨てたい表名
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Truncate the database table.
     * @param  tbl :name of the table to be truncated
     * @throws ReadWriteException :Notifies if error occurred in connection with database.
     </en>*/
    protected void deleteTable(String tbl)
            throws ReadWriteException
    {
        Statement stmt = null;
        try
        {
            stmt = wConn.createStatement();
            String sql = "delete from " + tbl;
            stmt.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "createlogininfo");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<jp>6126020 = 削除できませんでした。</jp>
            //<en>ReadWriteException should be thrown here with error message.</en>
            //<en>6126020 = Could not delete the data.</en>
            throw (new ReadWriteException(e));
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occurred.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "createlogininfo");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<jp>6126020 = 削除できませんでした。</jp>
                //<en>ReadWriteException should be thrown here with error message.</en>
                //<en>6126020 = Could not delete the data.</en>
                throw (new ReadWriteException(e));
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 整合性チェックを行います。<BR>
     * 整合性エラーがあった場合はエラーメッセージをログに落とします。
     * @param  folder ログを落とすフォルダパス
     * @param  locale Locale
     * @return 成功／失敗
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the data consistency.<BR>
     * If any error is found with consistency, log for the error message will be recorded.
     * @param  folder :file path to record the log
     * @param  locale Locale
     * @return succeeded/failed
     * @throws ReadWriteException :Notifies if error occurred in connection with database.
     </en>*/
    protected boolean check(String folder, Locale locale)
            throws ReadWriteException
    {
        try
        {
            System.out.println("GroupControllerCreater");
            GroupControllerCreater gcreater = new GroupControllerCreater(wConn, Creater.M_CREATE);
            if (!gcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("WarehouseCreater");
            WarehouseCreater wcreater = new WarehouseCreater(wConn, Creater.M_CREATE);
            if (!wcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("AisleCreater");
            AisleCreater acreater = new AisleCreater(wConn, Creater.M_CREATE);
            if (!acreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("UnavailableLocationCreater");
            UnavailableLocationCreater ulcreater = new UnavailableLocationCreater(wConn, Creater.M_CREATE);
            if (!ulcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("LoadSizeCreater");
            LoadSizeCreater lscreater = new LoadSizeCreater(wConn, Creater.M_CREATE);
            if (!lscreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("WidthCreater");
            WidthCreater widthcreater = new WidthCreater(wConn, Creater.M_CREATE);
            if (!widthcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("AccessNgShelfCreater");
            AccessNgShelfCreater accessngshelfcreater = new AccessNgShelfCreater(wConn, Creater.M_CREATE);
            if (!accessngshelfcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("SoftZoneCreater");
            SoftZoneCreater szcreater = new SoftZoneCreater(wConn, Creater.M_CREATE);
            if (!szcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("SoftZonePriorityCreater");
            SoftZonePriorityCreater szpcreater = new SoftZonePriorityCreater(wConn, Creater.M_CREATE);
            if (!szpcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("SoftZoneRangeCreater");
            SoftZoneRangeCreater szrcreater = new SoftZoneRangeCreater(wConn, Creater.M_CREATE);
            if (!szrcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("HardZoneCreater");
            HardZoneCreater hzcreater = new HardZoneCreater(wConn, Creater.M_CREATE);
            if (!hzcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("StationCreater");
            StationCreater stcreater = new StationCreater(wConn, Creater.M_CREATE);
            if (!stcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("DummyStationCreater");
            DummyStationCreater dcreater = new DummyStationCreater(wConn, Creater.M_CREATE);
            if (!dcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("WorkPlaceCreater");
            WorkPlaceCreater wpcreater = new WorkPlaceCreater(wConn, Creater.M_CREATE);
            if (!wpcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
            System.out.println("RouteCreater");
            RouteCreater rcreater = new RouteCreater(wConn, Creater.M_CREATE);
            if (!rcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }

            System.out.println("MachineCreater");
            MachineCreater mcreater = new MachineCreater(wConn, Creater.M_CREATE);
            if (!mcreater.consistencyCheck(wConn, folder, locale))
            {
                return false;
            }
        }
        catch (ScheduleException e)
        {
            return false;
        }

        return true;
    }

    /**<jp>
     * 端末エリアテーブルにシステムで予約されているデータを登録する
     * 端末エリアテーブルは現在登録されている情報を全て削除し
     * 新たに端末情報を検索し、全ての端末情報にエリア、ステーションを再セットする。
     * @throws ReadWriteException データベースエラーが発生した場合に通知される。
     * @throws ScheduleException 登録処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    protected void createTerminalAreaTable()
            throws ReadWriteException,
                ScheduleException
    {
        try
        {
            // 全てのエリアの作業場、ステーション一覧の取得
            ToolStationHandler sthandl = new ToolStationHandler(wConn);
            Station[] starray = sthandl.getStationInArea();

            // 全ての端末一覧の取得
            String[] tnoarray = sthandl.getTerminalNumbers();


            // 端末エリア情報ハンドラを使用し、端末エリア情報の再登録を行う。
            ToolTerminalAreaHandler tahandl = new ToolTerminalAreaHandler(wConn);
            for (int i = 0; i < tnoarray.length; i++)
            {
                // 既存の端末エリア情報を削除した上で、端末情報を再登録
                ToolTerminalAreaSearchKey tskey = new ToolTerminalAreaSearchKey();
                tskey.setTerminalNo(tnoarray[i].trim());
                tahandl.drop(tskey);

                for (int j = 0; j < starray.length; j++)
                {
                    TerminalArea tentity = new TerminalArea();
                    tentity.setTerminalNo(tnoarray[i].trim());
                    // 端末エリア情報のエリアIDは数値型に変換必要
                    // 自動倉庫の倉庫ステーションNo.がエリアIDとなるので必ず数字となる。
                    tentity.setAreaId(Integer.parseInt(starray[j].getWarehouseStationNo()));
                    tentity.setStationNo(starray[j].getStationNo());
                    tahandl.create(tentity);
                }
            }
        }
        catch (DataExistsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * User/Menu設定データテキスト作成処理
     * User/Menu設定toolテーブルからデータを抜き出し、テキストデータに書き込みます。
     * @param filepath ファイルパス
     * @throws SQLException 
     * @throws IOException
     */
    protected void UserMenuTableToTextFromWMS(String filepath)
            throws SQLException,
                IOException
    {
        Connection conn = null;
        try
        {
            // WMSTOOLユーザーのデータではなく、WMSユーザーが持つユーザー定義情報を取得する。
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_NAME);
            String[] tables = {
                "DMAREA"
            };
            BackupUtils backup = new BackupUtils();
            for (int i = 0; i < tables.length; i++)
            {
                backup.mkFile(filepath + "/", tables[i], conn);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
        }
        finally
        {
            try
            {

                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 =データベースエラーが発生しました。{0}</jp>
                //<en>6126001 =Database error occured.  {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            }
        }
    }

    /**
     * User/Menu設定データテキスト復元処理
     * 指定されたフォルダのテキストからUser/Menu設定toolテーブルにデータを復元します。
     * @param filepath ファイルパス
     * @throws FileNotFoundException,SQLException,IOException
     */
    protected void UserMenuTextToTable(String filepath)
            throws FileNotFoundException,
                SQLException,
                IOException
    {
        String[] tables = {
            "DMAREA"
        };
        BackupUtils backup = new BackupUtils();
        for (int i = 0; i < tables.length; i++)
        {
            backup.ldFile(filepath + "/", tables[i], wConn);
        }
    }

    // Private methods -----------------------------------------------

    /**<jp>
     * 使用不可棚に設定されている棚をテキストに書き込み、SHELF表、STATIONTYPE表から削除します。<BR>
     * @param path ファイルパス
     * @param throws ReadWriteException データベースとの接続で、異常が発生した場合に通知されます
     * @param throws ScheduleException スケジュール処理で、予期しないエラーが発生した場合に通知されます
     </jp>*/
    /**<en>
     * Create the textFile of UnavailableLocationCreater, and delete the data from SHELF table and STATIONTYPE table.<BR>
     * @param filepath file path
     * @throws ReadWriteException 
     * @throws ScheduleException 
     * @throws NotFoundException
     </en>*/
    private void deleteShelf(String filepath)
            throws ReadWriteException,
                ScheduleException,
                NotFoundException
    {
        ToolShelfHandler shelfHandle = new ToolShelfHandler(wConn);
        ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
        Shelf[] ngShelf = (Shelf[])shelfHandle.find(shelfKey);

        //<jp>SHELF表にあって使用不可棚に設定されている棚            </jp>
        //<en>In case the shelf has been set the restricted locations in SHELF table.</en>
        if (ngShelf.length > 0)
        {
            String[] shelftext = new String[ngShelf.length];
            for (int i = 0; i < ngShelf.length; i++)
            {
                shelftext[i] = getText(ngShelf[i]);
                //<jp> SHELF表、STATIONTYPE表から削除</jp>
                //<en> Delete from SHELF table and STATIONTYPE table.</en>
                shelfHandle.drop(ngShelf[i]);
            }
            write(filepath + "/" + SHELFTEXT_NAME, shelftext);
        }
        else
        {
            // 使用不可棚設定ファイルを削除します。
            String filename = filepath + "/" + SHELFTEXT_NAME;
            File file = new File(filename);
            //<jp>ファイルが存在する場合</jp>
            //<en>If the file exists,</en>
            if (file.exists())
            {
                // 使用不可棚設定ファイル削除
                file.delete();
            }
        }
    }

    /**<jp>
     * Shefインスタンスを文字列に変換します。
     * @param shelf  文字列に変換するShelfインスタンス
     * @return 変換された文字列
     </jp>*/
    /**<en>
     * The instance of Shelf is changed into a character sequence.
     * @param shelf The instance of Shelf that is changed into a charcter sequence.
     * @return That has been changed into a charcter sequence.
     </en>*/
    private String getText(Shelf shelf)
    {
        String stationNo = shelf.getStationNo();
        String bank = String.valueOf(shelf.getBankNo());
        String bay = String.valueOf(shelf.getBayNo());
        String level = String.valueOf(shelf.getLevelNo());
        String address = String.valueOf(shelf.getAddressNo());
        String warehouseStationNo = shelf.getWarehouseStationNo();
        String prohibitionFlag = String.valueOf(shelf.getProhibitionFlag());
        String statusFlag = String.valueOf(shelf.getStatusFlag());
        String hardZone = String.valueOf(shelf.getHardZoneId());
        String softZone = String.valueOf(shelf.getSoftZoneId());
        String parentStationNo = shelf.getParentStationNo();
        String accessNgFlag = "";
        //<jp> accsess Status</jp>
        //<en> accsess Status</en>
        if (shelf.isAccessNgFlag())
        {
            accessNgFlag = String.valueOf(Shelf.ACCESS_NG);
        }
        else
        {
            accessNgFlag = String.valueOf(Shelf.ACCESS_OK);
        }
        String priority = String.valueOf(shelf.getPriority());
        String pairStationNumber = shelf.getPairStationNumber();
        String side = String.valueOf(shelf.getSide());
        String width = String.valueOf(shelf.getWidth());
        String locationuseflag = String.valueOf(shelf.getLocationUseFlag());
        String text =
                stationNo + DELIM + bank + DELIM + bay + DELIM + level + DELIM + address + DELIM + warehouseStationNo
                        + DELIM + prohibitionFlag + DELIM + statusFlag + DELIM + hardZone + DELIM + softZone + DELIM
                        + parentStationNo + DELIM + accessNgFlag + DELIM + priority + DELIM + pairStationNumber + DELIM
                        + side + DELIM + width + DELIM + locationuseflag;
        return text;
    }

    /**
     * 使用不可棚設定テキストを読み込み、使用不可棚をデータベースに復元します。
     * @param conn データベース接続用のConnection
     * @param filePath 製番のディレクトリ
     */
    private void createUnavailableLocation(Connection conn, String filePath)
            throws ScheduleException
    {
        String fileName = filePath + "/" + UnavailableLocationCreater.SHELFTEXT_NAME;

        //<jp>*** 使用不可棚設定テキストの読み込み ***</jp>
        //<en>*** Read the unavailable location settings ***</en>
        String[] textArray = read(fileName);
        if (textArray != null)
        {
            for (int i = 0; i < textArray.length; i++)
            {
                //<jp>テキストの内容のSHELF表を作成する</jp>
                //<en>Create the SHELF table according to the contents of the text.</en>
                setUnavailableLocation(conn, textArray[i]);
            }
        }
    }

    /**<jp>
     * テキストから取得した使用不可棚情報でSHELFテーブルを作成します。
     * もうすでに、その棚情報がSHELF表に存在する場合は、何も行いません。
     * @param conn データベース接続用のConnection
     * @param shelfText     テキストから取得した使用不可棚情報
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知されます
     </jp>*/
    /**<en>
     * Create the SHELF table based on the information unavailable location informaion which was 
     * retrieved from the text.
     * If the location data already exists in the SHELF table, no processing will be done.
     * @param conn :Connection to connect with database
     * @param shelfText     :anavailable location information retrieed from the text
     * @throws ScheduleException :Notifies if unexpected exception occurred during the execution of 
     * the schedule processing.
     </en>*/
    private void setUnavailableLocation(Connection conn, String shelfText)
            throws ScheduleException
    {
        try
        {
            String[] param = getSeparatedItem(shelfText);

            String locationNo = param[0];

            ToolShelfHandler shelfHandler = new ToolShelfHandler(conn);
            ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
            shelfKey.setStationNo(locationNo);
            if (shelfHandler.count(shelfKey) <= 0)
            {
                /* 2003/12/19 MODIFY okamura START */
                String whStationNo = param[5];
                int bank = Integer.parseInt(param[1]);
                int bay = Integer.parseInt(param[2]);
                int level = Integer.parseInt(param[3]);

                //String location = Integer.toString(bank) + Integer.toString(bay) + Integer.toString(level) ;
                int[] bayRange = shelfHandler.getBayRange(whStationNo, bank);
                //<jp>ベイ範囲の確認</jp>
                //<en>Check the bay range</en>
                if (bayRange[0] > bay || bay > bayRange[1])
                {
                    return;
                }
                int[] levelRange = shelfHandler.getLevelRange(whStationNo, bank);
                //<jp>レベル範囲の確認</jp>
                //<en>Check the level range</en>
                if (levelRange[0] > level || level > levelRange[1])
                {
                    return;
                }
                /* 2003/12/19 MODIFY okamura END   */
                //<jp>ステーションNo.（ロケーションNo.）</jp>
                //<en>station no. (location no.)</en>
                Shelf shelf = new Shelf(locationNo);
                //<jp>バンク</jp>
                //<en>bank</en>
                shelf.setBankNo(Integer.parseInt(param[1]));
                //<jp>ベイ</jp>
                //<en>bay</en>
                shelf.setBayNo(Integer.parseInt(param[2]));
                //<jp>レベル</jp>
                //<en>level</en>
                shelf.setLevelNo(Integer.parseInt(param[3]));
                //<jp>アドレス</jp>
                //<en>address</en>
                shelf.setAddressNo(Integer.parseInt(param[4]));
                //<jp>倉庫ステーションNo</jp>
                //<en>warehouse station no.</en>
                shelf.setWhStationNo(param[4]);
                //<jp>状態(使用可)</jp>
                //<en>status (available)</en>
                shelf.setProhibitionFlag(Integer.parseInt(param[6]));
                //<jp>在荷</jp>
                //<en>load presence</en>
                shelf.setStatusFlag(Integer.parseInt(param[7]));
                //Hard Zone
                shelf.setHardZoneId(Integer.parseInt(param[8]));
                //Soft Zone
                shelf.setSoftZoneId(Integer.parseInt(param[9]));
                //<jp>親ステーション</jp>
                //<en>parent station</en>
                shelf.setParentStationNo(param[10]);
                //<jp>使用不可棚</jp>
                //<en>unavailable location</en>
                if (param[11].equals(Integer.toString(Shelf.ACCESS_NG)))
                {
                    shelf.setAccessNgFlag(UnavailableLocationCreater.TRUE);
                }
                else
                {
                    shelf.setAccessNgFlag(UnavailableLocationCreater.FALSE);
                }
                // 空棚検索順
                shelf.setPriority(Integer.valueOf(param[12]));
                // ペア ステーションNo
                shelf.setPairStationNumber(param[13]);
                // 手前、奥棚区分
                shelf.setSide(Integer.valueOf(param[14]));
                // 荷幅
                shelf.setWidth(Integer.valueOf(param[15]));
                // 棚使用フラグ
                shelf.setLocationUseFlag(Integer.valueOf(param[16]));

                //<jp>棚情報作成</jp>
                //<en>Create the location data.</en>
                if (StringUtil.isBlank(shelf.getParentStationNo()))
                {
                    // Single Deep Shelf
                    shelfHandler.create(shelf);
                }
                else
                {
                    // Double Deep Shelf
                    shelfHandler.createDoubleDeep(shelf);
                }
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (InvalidStatusException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (DataExistsException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * 引数で指定されたString文字列を、所定の区切り文字で分離し、その結果をStringの配列で返します。
     * さらに、DisplayText.trimメソッドにより、文字列の後ろに空白が有る場合は自動的に削除されます。
     * 例<BR>
     * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
     * @param buf 分離する文字列
     * @return 作成されたStringの配列
     </jp>*/
    /**<en>
     * Split the String array, which was specified through parameter, with specified delimiter then return them
     * in form on the String array.
     * Also it automartically delete the blanks that follow the string, if there are any, by DisplayText.trim method.
     * Example<BR>
     * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
     * @param buf :string which will be split up
     * @return    :the array of the created String
     </en>*/
    private String[] getSeparatedItem(String buf)
    {
        Vector bufVec = new Vector();

        //<jp>区切り文字が連続している場合には、空白１バイトを挿入する。</jp>
        //<en>If there are cosecutive delimiters in the string, insert spaces of 1 byte.</en>
        buf = DisplayText.delimiterCheck(buf, DELIM);

        StringTokenizer stk = new StringTokenizer(buf, DELIM, false);
        while (stk.hasMoreTokens())
        {
            bufVec.addElement(DisplayText.trim((String)stk.nextToken()));
        }
        String[] array = new String[bufVec.size()];
        bufVec.copyInto(array);
        return array;
    }

    /**<jp>
     * 引数で指定したテキストにarrayで渡した文字列を書き込みます。
     * @param filename 書き込みを行うファイル
     * @param shelf    書き込みを行う文字列配列
     </jp>*/
    /**<en>
     * It writes in a text file.
     * @param filename It's a text file that is write.
     * @param array The charcter sequence that is writes in a text file.
     </en>*/
    private void write(String filename, String[] array)
            throws ScheduleException
    {
        try
        {
            FileWriter dos = new FileWriter(filename);

            for (int i = 0; i < array.length; i++)
            {
                dos.write(array[i]);
                dos.write("\n");
            }
            dos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * 指定されたファイルの読み込みを行い、その結果をStringの配列で返します。
     * @param filename 読み込みを行う外部データの<CODE>File</CODE>インスタンス
     * @return 作成されたStringの配列
     * @throws FileNotFoundException 指定されたファイルが見つからない場合
     * @throws ScheduleException 処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Read the specified file, and return the result in form of String array.
     * @param filename :<CODE>File</CODE> instance of external data to read
     * @return :the array of String created
     * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
     * has been passed to the method.
     </en>*/
    private String[] read(String filename)
            throws ScheduleException
    {
        try
        {
            File file = new File(filename);
            //<jp>ファイルが存在する場合</jp>
            //<en>If the file exists,</en>
            if (file.exists())
            {
                //<jp>ファイル読み込み</jp>
                //<en>Read the file.</en>
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                Vector vec = new Vector(10);
                String buf = "";
                while ((buf = br.readLine()) != null)
                {
                    vec.add(buf);
                }
                fr.close();
                br.close();
                String[] array = new String[vec.size()];
                vec.copyInto(array);

                return array;
            }
            //<jp>ファイルが存在しない場合はnullを返す</jp>
            //<en>If the file cannot be found, return null.</en>
            else
            {
                return null;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * ダブルディープ運用の場合、そのアイルに属するSTATIONの荷姿チェックを荷姿チェックありに更新します。<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知します。
     */
    private void updateLoadSizeCheck()
            throws ReadWriteException
    {
        ToolStationHandler staionHandle = new ToolStationHandler(wConn);
        staionHandle.modifyLoadSizeCheck();
    }

    /**<jp>
     * 不要になったファイルを削除します。
     * @param filepath ファイルパス
     </jp>*/
    /**<en>
     * The file that became unnecessary is deleted. 
     * @param filepath filepath.
     </en>*/
    private void deleteFile(String filepath)
    {
        String[] tablename = {
                "COM_TERMINAL",
                "DMAREA"
        };
        File delfile;

        for (int i = 0; i < tablename.length; i++)
        {
            delfile = new File(filepath + "/" + tablename[i] + ".sql");
            delfile.delete();
        }
    }


    private void getDBProperties()
    {
        DataSourceConfig dsc = DataSourceConfig.getInstance();
        Properties prop = dsc.findProperties("wmstool");
        System.out.println(prop.getProperty("username"));
        System.out.println(prop.getProperty("password"));
    }
}
//end of class

