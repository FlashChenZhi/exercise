// $Id: RouteCreater.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperator;


/**<jp>
 * 搬送ルート設定を行なうクラスです。
 * AbstractCreaterを継承し、搬送ルート設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class operates the carry route setting.
 * It inherits the AbstractCreater, and implements the processes required for the setting 
 * of carry route.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class RouteCreater extends AbstractCreater
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 搬送元ステーションNoの区切り文字
     </jp>*/
    /**<en>
     * Delimiter for sending station no.
     </en>*/
    public static final String wSeparate = ":";

    // Class variables -----------------------------------------------
    /**<jp>
     * Route.txtのパス
     </jp>*/
    /**<en>
     * Path of Route.txt
     </en>*/
    public String wFileName = "";

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このクラスの初期化を行ないます。初期化時に<CODE>ToolAs21MachineStateHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ToolAs21MachineStateHandler</CODE> at 
     * the initialization.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public RouteCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param conn データベース接続用 Connection
     * @param <code>Locale</code> オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Implement the process to run when the print-issue button was pressed on the display.<BR>
     * @param conn Databse Connection Object
     * @param locale object
     * @param listParam :schedule parameter
     * @return :result of print job
     </en>*/
    public boolean print(Connection conn, Locale locale, Parameter listParam)
    {
        return true;
    }
    /**<jp>
     * 画面へ表示するデータを取得します。<BR>
     * @param conn データベース接続用 Connection
     * @param <code>Locale</code> オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn Databse Connection Object
     * @param locale object
     * @param searchParam :search conditions
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
    {
        RouteParameter param = (RouteParameter)searchParam;
        wFileName = param.getRouteTextPath();

        IniFileOperator ifo = null;
        try
        {
            ifo = new IniFileOperator(wFileName, wSeparate);
        }
        catch (ReadWriteException e)
        {
            return null;
        }

        String[] fromStations = ifo.getKeys();
        String[] toStations = ifo.getValues();
        
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);    
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        RouteParameter dispData = null;

        if (fromStations == null)
        {
            return null;
        }

        if (fromStations.length > 0)
        {
            for (int i = 0; i < fromStations.length; i++)
            {
                dispData = new RouteParameter();
                dispData.setStationNo(fromStations[i]);
                dispData.setConnectStNumber(toStations[i]);
                vec.addElement(dispData);
            }
            RouteParameter[] fmt = new RouteParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null;
    }
    /**<jp>
     * パラメータの配列を返します。
     * @return パラメータの配列
     </jp>*/
    /**<en>
     * Return the parameter array.
     * @return  :parameter array
     </en>*/
/*    public Parameter[] getParameters()
    {
        ReStoringMaintenanceParameter[] mArray 
            = new ReStoringMaintenanceParameter[wParamVec.size()];
        wParamVec.copyInto(mArray);
        return mArray;
    }
*/    
    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @param param チェックするパラメータ
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the parameter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        RouteParameter mParameter = (RouteParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        switch(processingKind)
        {
            case M_CREATE:
                //<jp> ステーション表が登録されているかチェック</jp>
                //<en> Check to see if data is registered in station table.</en>
                ToolStationHandler sthandle = new ToolStationHandler(conn);
                ToolStationSearchKey stkey = new ToolStationSearchKey();

                if (sthandle.count(stkey) <= 0)
                {
                    //<jp> ステーション情報がありません。ステーション設定画面で登録してください</jp>
                    //<en> The information of the station cannot be found. Please register in station setting screen.</en>
                    setMessage("6123079");
                    return false;
                }

                //<jp> アイル表に登録されているかチェック</jp>
                //<en> Check to see data is registered in aisle table.</en>
                ToolAisleHandler ahandle = new ToolAisleHandler(conn);
                ToolAisleSearchKey akey = new ToolAisleSearchKey();

                if (ahandle.count(akey) <= 0)
                {
                    //<jp> アイル情報がありません。アイル設定画面で登録してください</jp>
                    //<en> The information of the aisle cannot be found. Please register in aisle setting screen.</en>
                    setMessage("6123098");
                    return false;
                }

                //<jp> 接続ステーションNoのチェック</jp>
                //<en> Check the connected station no.</en>
                if (isPatternMatching(mParameter.getConnectStNumber()))
                {
                    //<jp>6123009 = ステーションNo.にシステムで使用できない文字が含まれています</jp>
                    //<en>6123009 = The station no. contains the unacceptable letters in system.</en>
                    setMessage("6123009");
                    return false;
                }

                //<jp> 搬送元ステーションNo.</jp>
                //<en> Sending station no.</en>
                String SourceStation = mParameter.getStationNo();

                //<jp> 搬送元ステーションNoがSTATION、AISLE表に存在するかチェック</jp>
                //<en> Check to see if the sending station no. exists in STATION table and AISLE table.</en>
                if (!check.isExistAllStationNo(SourceStation)) 
                {
                    //<jp> 入力されたステーションNo.は存在しません</jp>
                    //<en> The station no. which was jsut entered does not exist.</en>
                    setMessage("6123080");
                    return false;
                }

                //<jp> 接続ステーションNoを取得</jp>
                //<en> Retrieve the connected station no.</en>
                StringTokenizer deftoken = new StringTokenizer(mParameter.getConnectStNumber(), ",", false) ;

                //<jp> 接続ステーションNoで入力されたステーションの数</jp>
                //<en> The station no. has not been entered.</en>
                int count = deftoken.countTokens();
                
                //<jp> ステーションNoが入力されていない</jp>
                //<en> The station no. has not been entered.</en>
                if (count <= 0)
                {
                    //<jp> 接続ステーションNo.を入力してください</jp>
                    //<en> Please enter the connected station no.</en>
                    setMessage("6123096");
                    return false;
                }

                //<jp> カンマも1つのトークンとするStringTokenizerのインスタンス生成</jp>
                //<en> Generate the instance which regards the comma as a token.</en>
                StringTokenizer delimstoken = new StringTokenizer(mParameter.getConnectStNumber(), ",", true) ;
                int delimscount = delimstoken.countTokens();
                
                //<jp> カンマの入力が不正ではないか確認</jp>
                //<en> Check to see if the entry of comma is invalid.</en>
                if ((delimscount - count) > (count - 1))
                {
                    //<jp> カンマの入力が不正です。</jp>
                    //<en> Entry of the comma is invalid.</en>
                    setMessage("6123188");
                    return false;
                }

                //<jp> 接続ステーションNo.を格納</jp>
                //<en> Store the connected station no.</en>
                String[] toStations = new String[count];
                for (int i = 0; i < count; i++)
                {
                    toStations[i] = deftoken.nextToken();
                }

                //<jp> 搬送元の倉庫ステーションNoを取得</jp>
                //<en> Retrieve the warehouse station no. of the sending station.</en>
                String whstno = getWHStationNo(conn, SourceStation);
                if (whstno == null)
                {
                    //<jp> 入力されたステーションNo.は存在しません</jp>
                    //<en> The station no. has not been entered.</en>
                    setMessage("6123080");
                    return false;
                }

                //<jp> 接続ステーションNoがSTATION、AISLE表に存在するかチェック</jp>
                //<en> Check to see of the connect station no. exists in STATION table </en>
                //<en> and in AISLE table.</en>
                for (int loopcnt = 0; loopcnt < count; loopcnt++)
                {
                    if (!check.isExistAllStationNo(toStations[loopcnt])) 
                    {
                        //<jp> 入力されたステーションNo.は存在しません</jp>
                        //<en> The station no. has not been entered.</en>
                        setMessage("6123080");
                        return false;
                    }

                    //<jp> 作業場</jp>
                    //<en> workshop</en>
//                    int types[] = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS};
//                    ToolStationSearchKey stwkey = new ToolStationSearchKey();
//                    stwkey.setWorkPlaceType(types);
//                    stwkey.setNumber(toStations[loopcnt]);
//                    //<jp> 作業場かどうか</jp>
//                    //<en> WHether/not it is a workshop:</en>
//                    if(sthandle.count(stwkey) > 0)
//                    {
//                        //<jp> 作業場を設定することはできません</jp>
//                        //<en> Cannot set the workshops.</en>
//                        setMessage("6123189");
//                        return false;
//                    }
                    int[] types = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS, 
                                    Station.WPTYPE_ALL};
                    ToolStationSearchKey stwkey = new ToolStationSearchKey();
                    stwkey.setWorkPlaceType(types);
                    stwkey.setStationNo(toStations[loopcnt]);
                    //<jp> 作業場かどうか</jp>
                    //<en> WHether/not it is a workshop:</en>
                    if (sthandle.count(stwkey) > 0)
                    {
                        //<jp> 作業場を設定することはできません</jp>
                        //<en> Cannot set the workshops.</en>
                        setMessage("6123189");
                        return false;
                    }

                    //<jp> 接続ステーション内に搬送元ステーションNo.を設定されてないか</jp>
                    //<en> Check to see if sending station no. is set in connect stations.</en>
                    if (SourceStation.equals(toStations[loopcnt]))
                    {
                        //<jp> 接続ステーションNo.に搬送元ステーションNo.を設定することはできません</jp>
                        //<en> Sending station no. cannot be set in connect station no.</en>
                        setMessage("6123208");
                        return false;
                    }

                    //<jp> 接続ステーション内に同一ステーションが設定されていないか</jp>
                    //<en> Check to see if same stations are set in connect stations.</en>
                    for (int stcnt = 0; stcnt < count; stcnt++)
                    {
                        if (loopcnt != stcnt)
                        {
                            if (toStations[loopcnt].equals(toStations[stcnt]))
                            {
                                //<jp> 接続ステーションNo.内に同一ステーションNo.を設定することはできません</jp>
                                //<en> Cannot set the identical stations in the connect station no.</en>
                                setMessage("6123191");
                                return false;
                            }
                        }
                    }

                    //<jp> 接続ステーションの倉庫ステーションNoを取得</jp>
                    //<en> Retrieve the warehouse station no. in the connect stations.</en>
                    String connectwhst = getWHStationNo(conn, toStations[loopcnt]);
                    if (connectwhst == null)
                    {
                        //<jp> 入力されたステーションNo.は存在しません</jp>
                        //<en> The station no. has not been entered.</en>
                        setMessage("6123080");
                        return false;
                    }

                    //<jp> 搬送元の格納区分と接続ステーションの格納区分が同じか</jp>
                    //<en> Check to see if the storage type of sending station and the </en>
                    //<en> connect stations are the same.</en>
                    if (!whstno.equals(connectwhst))
                    {
                        //<jp> 搬送元ステーションNo.と接続ステーションNo.の格納区分が違います。</jp>
                        //<en> The storage type of the sending station no. and the connect </en>
                        //<en> station no. are different.</en>
                        setMessage("6123215");
                        return false;
                    }
                }

                break;
            default:
                //<jp> 予期しない値がセットされました。{0} = {1}</jp>
                //<en> Unexpected value has been set.{0} = {1}</en>
                String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
                //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
                throw new ScheduleException("6126499");    
        }

        return true;
    }

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * 異常があった場合、その詳細をfilenameで指定したファイルへ書き込みます。
     * @param conn データベース接続用 Connection
     * @param filename 異常が発生したときのログを書き込むファイル名
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file which is specified by filename.
     * @param conn Databse Connection Object
     * @param filename :name of the file the log will be written in when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String filename, Locale locale)throws ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        try
        {
            String checkfile = ToolParam.getParam("CONSTRAINT_CHECK_FILE");
            LogHandler loghandle = new LogHandler(filename + "/" + checkfile, locale);

            //<jp> ToolParamからRoute.txtのデフォルトパス取得</jp>
            //<en> Retrieve the default path of Route.txt from ToolParam.</en>
            String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
            File routepath = new File(defaultRouteText);

            IniFileOperator ifo = null;
            try
            {
                ifo = new IniFileOperator(filename + "/" + routepath.getName(), wSeparate);
            }
            catch (ReadWriteException e)
            {
                //<jp> 6123146 = ファイルが存在しないので設定できません</jp>
                //<en> 6123146 = Cannot set; the file cannot be found.</en>
                loghandle.write("Route", "RouteText", "6123146");
                return false;
            }
            String[] fromStations = ifo.getKeys();
            String[] toStations = ifo.getValues();
            
            if (fromStations == null || fromStations.length <= 0)
            {
                //<jp> 搬送ルートが設定されていません</jp>
                //<en> The carry route has not been set.</en>
                loghandle.write("Route", "RouteText", "6123190");
                return false;
            }

            //<jp> route.txtに定義されているステーションがSTATION、AISLE表に存在するか</jp>
            //<en> CHeck to see if the stations defined in route.txt exist in STATION table and AISLE table.</en>
            for (int i = 0; i < fromStations.length; i++)
            {
// 2004.12.20 T.Yamashita UPD Start
//                if (!check.isExistAllStationNo(fromStations[i])) 
                if (!check.isExistAllRoutStationNo(fromStations[i])) 
// 2004.12.20 T.Yamashita UPD End
                {
                    loghandle.write("Route", "RouteText", check.getMessage());
                    errorFlag = false;
                }
                if (!check.isExistStationType(fromStations[i]))
                {
                    loghandle.write("Route", "RouteText", check.getMessage());
                    errorFlag = false;
                }

                //<jp> 接続ステーションNoを取得</jp>
                //<en> Retrieve the connect station no.</en>
                StringTokenizer deftoken = new StringTokenizer(toStations[i], ",", false) ;
                int count = deftoken.countTokens();

                //<jp> 接続ステーションNoがSTATION、AISLE表に存在するかチェック</jp>
                //<en> Check to see if the connect stations exist in STATION table and AISLE table.</en>
                for (int loopcnt = 0; loopcnt < count ; loopcnt++)
                {
                    String toStation = deftoken.nextToken();
// 2004.12.20 T.Yamashita UPD Start
//                    if (!check.isExistAllStationNo(toStation)) 
                    if (!check.isExistAllRoutStationNo(toStation)) 
// 2004.12.20 T.Yamashita UPD End
                    {
                        loghandle.write("Route", "RouteText", check.getMessage());
                        errorFlag = false;
                    }
                    if (!check.isExistStationType(toStation))
                    {
                        loghandle.write("Route", "RouteText", check.getMessage());
                        errorFlag = false;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
    
        }
        return errorFlag;
    }

    /**<jp>
     * パラメータの重複チェック処理を行ないます。
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @param param チェックするパラメータ
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.
     * It checks the duplication of parameter, then returns true if there was no duplicated data 
     * or returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @param param :parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        Parameter[] mArray = (Parameter[])getParameters();
        RouteParameter mParam = (RouteParameter)param;

        //<jp>同一データチェック</jp>
        //<en>Check the duplicate data.</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }
        return true;
    }

    /**<jp>
     * メンテナンス処理を行います。
     * メンテナンス処理の種類は処理区分（getProcessingKind()メソッドより取得）により内部で判断する必要があります。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conduct the maintenance process.
     * It is necessary that type of the maintentace should be determined internally according to 
     * the process type (obtained by getProcessingKind() method.)
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public boolean doStart(Connection conn) throws ReadWriteException, ScheduleException
    {
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        if (processingKind == M_CREATE)
        {
            if (!create())
            {
                return false;
            }
            //<jp>6121004 = 編集しました</jp>
            //<en>6121004 = Edited the data.</en>
            setMessage("6121004");
            return true;
        }
        //<jp>処理区分が異常</jp>
        //<en>Error with the process type.</en>
        else
        {
            //<jp> 予期しない値がセットされました。{0} = {1}</jp>
            //<en> Unexpected value has been set.{0} = {1}</en>
            String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");    
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * パラメータの補完処理を行います。<BR>
     * ・他の端末で更新されたかチェックするためReStoringインスタンス
     * をパラメータに追加する
     * 処理にに成功した場合は補完したパラメータ。失敗した場合はnullを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param 補完処理を行うためのパラメータ
     * @return 処理に成功した場合はパラメータ。失敗した場合はnullを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conduct the complementarity process of parameter.<BR>
     * - Append ReStoring instance to the parameter in ordder to check whether/not the data 
     *   has been modified by other terminals.
     * It returns the complemented parameter if the process succeeded, or returns false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param :parameter which is used for the complementarity process
     * @return :returns the parameter if the process succeeded, or returns null if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
     </en>*/
    protected  Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException
    {
        return param;
    }
    /**<jp>
     * メンテナンス修正処理を行います。
     * パラメータ配列のキーとなる項目を元に変更処理を行います。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean modify()
    {
        return true;
    }

    /**<jp>
     * メンテナンス登録処理を行います。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create() throws ScheduleException
    {
        //<jp> 新規に作成したファイルをOpenしたかどうか true:open</jp>
        //<en> Whether/not the newly created file was Opened - true:open</en>
        boolean openflag = false ;
        PrintWriter writer = null;
        //<jp> 元のroute.txt</jp>
        //<en> Original route.txt</en>
        File orgFile = null;
        File newFile = null;

        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            RouteParameter castparam = null;
            //<jp> 元のroute.txt</jp>
            //<en> Original route.txt</en>
            orgFile = new File(wFileName);

            if (mArray.length > 0)
            {
                //<jp> Route.txtを別名で新規作成</jp>
                //<en> Newly create the Route.txt by a differenet name.</en>
                String newfileName = wFileName + "new";
                writer = new PrintWriter(new FileWriter(newfileName, true));
                //<jp> 新規作成するファイル</jp>
                //<en> The file to newly create</en>
                newFile = new File(newfileName);
                openflag = true;

                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (RouteParameter)mArray[i];
                    writer.println(castparam.getStationNo() + wSeparate + castparam.getConnectStNumber());
                }
                writer.close();

                //<jp> 元々のRoute.txtを削除</jp>
                //<en> Delete the original Route.txt.</en>
                orgFile.delete();
                //<jp> 新規作成したファイルをrename</jp>
                //<en> Rename the newly created file.</en>
                newFile.renameTo(orgFile);
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                orgFile.delete();
                orgFile.createNewFile();
            }

            return true;
        }
        catch (Exception e)
        {
            if (openflag)
            {
                writer.close();
                newFile.delete();
            }
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * メンテナンス削除処理を行います。
     * パラメータ配列のキーとなる項目を元に削除処理を行います。
     * 削除が選択された項目の処理区分を「処理済み」にセットします。実際の削除は
     * 日締め処理にて行われます。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance deletions.
     * Deletion will be done based on the key items of parameter array. 
     * Set the process type of selected item to delete to 'processed'. The acrual deletion will
     * be done in daily transactions.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean delete()
    {
        return true;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * リストボックスより編集するデータを選択するときに、同一のデータが選択されたことを
     * 確認するためのチェックを実装します。倉庫設定では、
     * 追加するパラメータの格納区分がため打ちデータ内に存在するかを確認します。
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing 
     * data from the list box to edit.
     * In the warehouse setting, it checks whether/not the storage type of appending parameter
     * exists in the entered data summary.
     * @param param  :the parameter which will be appended in this process
     * @param array  :entered data summary (pool)
     * @return       :return true if identical data exists.
     </en>*/
    private boolean isSameData(RouteParameter param, Parameter[] array)
    {

        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        
        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {

            String station = param.getStationNo();
            String alreadyst = "";
            for (int i = 0; i < array.length; i++)
            {
                RouteParameter castparam = (RouteParameter)array[i];
                alreadyst = castparam.getStationNo();
                if (alreadyst.equals(station))
                {
                    //<jp>6123209 = 既に登録されています。同一搬送元ステーションNo.を入力することはできません</jp>
                    //<jp> 接続ステーションNo.に搬送元ステーションNo.を設定することはできません</jp>
                    //<en>6123209 = The data is already entered. Cannot input the identical sending station no.</en>
                    //<en>Cannot set the sending station no. as a connect station no.</en>
                    setMessage("6123209");
                    return true;
                }
            }

/*            
            
            //<jp>今回追加する格納区分で比較する</jp>
            //<en>Compare by the storage type appended in this process.</en>
            newKey = param.getStationNo() + param.getConnectStNumber();
            
            for (int i = 0 ; i < array.length ; i++)
            {
                RouteParameter castparam = (RouteParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgKey = castparam.getStationNo() + castparam.getConnectStNumber();
                
                //<jp>同一ステーションNoのチェック</jp>
                //<en>Check the identical user name</en>
                if(newKey.equals(orgKey))
                {
                    //<jp>6123209 = 既に登録されています。同一搬送元ステーションNo.を入力することはできません</jp>
                    //<en>6123209 = The data is already entered. Cannot input the identical</en>
                    //<en>sending station no.</en>
                    setMessage("6123209");
                    return true;
                }
            }
*/
        }

        return false;
    }

    
    /**<jp>
     * 指定されたステーションNo.の倉庫ステーションNo.を返します。
     * @param conn データベース接続用 Connection
     * @param stno  ステーションNo.
     * @return      倉庫ステーションNo.を返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the warehouse station no. of the specified station no.
     * @param conn Databse Connection Object
     * @param stno :station no.
     * @return     :return the warehouse station no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private String getWHStationNo(Connection conn, String stno) throws ReadWriteException
    {
        //<jp> ステーション表が登録されているかチェック</jp>
        //<en> Check to see if the station table is registered.</en>
        ToolStationHandler sthandle = new ToolStationHandler(conn);
        ToolStationSearchKey stkey = new ToolStationSearchKey();
        ToolAisleHandler ahandle = new ToolAisleHandler(conn);
        ToolAisleSearchKey akey = new ToolAisleSearchKey();
        Station[] stations = null;

        //<jp> Station表検索</jp>
        //<en> Serach in Station table.</en>
        stkey.setStationNo(stno);
        stations = (Station[])sthandle.find(stkey);

        if (stations != null && stations.length > 0)
        {
            return stations[0].getWarehouseStationNo();
        }

        //<jp> Stationにない場合Aisle表検索</jp>
        //<en> If the data is not found in Station, search in Aisle table.</en>
        akey.setStationNo(stno);
        stations = (Station[])ahandle.find(akey);

        if (stations != null && stations.length > 0)
        {
            return stations[0].getWarehouseStationNo();
        }

        return null;
    }
    
    /**<jp>
     * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
     * 禁止文字の定義は、CommonParamにて指定します。
     * この搬送ルートの接続ステーションNo.のチェックではカンマは禁止文字に含みません。
     * @param pattern 対象となる文字列を指定します。
     * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
     </jp>*/
    /**<en>
     * Examines data to see if the specified string contains the unacceptable letters and symbols in system.
     * The definition of unacceptable letters and symbols is specified in CommonParam.
     * In this checking of connect station no. of carry route, the cumma is not included in unacceptable letters and symbols.
     * @param pattern :specifies the target string.
     * @return :true if unacceptable letters and symbols are included in the string, or false if not.
     </en>*/
    private static boolean isPatternMatching(String pattern)
    {
        String ng_chars = "*%\"';+";
        
        if (pattern != null && !pattern.equals(""))
        {
            for (int i = 0; i < ng_chars.length() ; i++)
            {
                if (pattern.indexOf(ng_chars.charAt(i)) > -1)
                {
                    return true ;
                }
            }
        }
        return false ;
    }
    
}
//end of class

