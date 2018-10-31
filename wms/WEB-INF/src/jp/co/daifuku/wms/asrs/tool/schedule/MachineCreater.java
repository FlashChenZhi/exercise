// $Id: MachineCreater.java 7258 2010-02-26 05:59:51Z kanda $
package jp.co.daifuku.wms.asrs.tool.schedule ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.As21MachineState;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineStateHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;


/**<jp>
 * 機種情報設定を行なうクラスです。
 * AbstractCreaterを継承し、機種情報設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7258 $, $Date: 2010-02-26 14:59:51 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </jp>*/
/**<en>
 * This class operates the setting of machine information.
 * It inherits the AbstractCreater, and implements the processes requried for the setting
 * of machine information.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7258 $, $Date: 2010-02-26 14:59:51 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </en>*/
public class MachineCreater extends AbstractCreater
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------

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
        return ("$Revision: 7258 $,$Date: 2010-02-26 14:59:51 +0900 (金, 26 2 2010) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このクラスの初期化を行ないます。初期化時に<CODE>ToolAs21MachineStateHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ToolAs21MachineStateHandler</CODE>
     * at the initialization.
     * @param conn データベース接続用 Connection
     * @param kind :process type
     </en>*/
    public MachineCreater(Connection conn, int kind)
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
     * Retrieve the data to display on the screen.<BR>
     * @param conn Databse Connection Object
     * @param locale object
     * @param searchParam :search conditions
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
    {
        As21MachineState[] array = getAs21MachineStateArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        MachineParameter dispData = null;

        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new MachineParameter();
                dispData.setControllerNumber(array[i].getControllerNumber());
                dispData.setMachineType(array[i].getType());
                dispData.setMachineNumber(array[i].getNumber());
                dispData.setStationNo(array[i].getStationNo());
                dispData.setStationName(getFindUtil(conn).getStationName(array[i].getStationNo()));
                // DFKLOOK 20100222 追加
                dispData.setDeviceName(array[i].getDeviceName());
                vec.addElement(dispData);
            }
            MachineParameter[] fmt = new MachineParameter[vec.size()];
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
     * @return :parameter array
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
        MachineParameter mParameter = (MachineParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        switch(processingKind)
        {
            case M_CREATE:
                //<jp> GROUPCONTROLLER表に登録されているかチェック</jp>
                //<en> Check to see if the data is registered in GROUPCONTROLLER table.</en>
                ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
                ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();

                if (gchandle.count(gckey) <= 0)
                {
                    //<jp> グループコントローラ情報がありません。グループコントローラ設定画面で登録してください</jp>
                    //<en> The information of the group controller cannot be found. </en>
                    //<en> Please register in group controller setting screen.</en>
                    setMessage("6123078");
                    return false;
                }

                //<jp> ステーション表に登録されているかチェック</jp>
                //<en> Check to see if the data is registered in station table.</en>
                ToolStationHandler sthandle = new ToolStationHandler(conn);
                ToolStationSearchKey stkey = new ToolStationSearchKey();

                if (sthandle.count(stkey) <= 0)
                {
                    //<jp> ステーション情報がありません。ステーション設定画面で登録してください</jp>
                    //<en> The information of the station cannot be found. </en>
                    //<en> Please register in station setting screen.</en>
                    setMessage("6123079");
                    return false;
                }

                //<jp> アイル表に登録されているかチェック</jp>
                //<en> Check to see if the data is registered in aisle table.</en>
                ToolAisleHandler ahandle = new ToolAisleHandler(conn);
                ToolAisleSearchKey akey = new ToolAisleSearchKey();

                if (ahandle.count(akey) <= 0)
                {
                    //<jp> アイル情報がありません。アイル設定画面で登録してください</jp>
                    //<en> The information of the aisler cannot be found. </en>
                    //<en> Please register in aisle setting screen.</en>
                    setMessage("6123098");
                    return false;
                }
                
                //<jp>ステーションNoのチェック</jp>
                //<en>Check the station no.</en>
                if (!check.checkStationNo(mParameter.getStationNo()))
                {
                    //<jp>異常内容をセットする</jp>
                    //<en>Set the contents of the error.</en>
                    setMessage(check.getMessage());
                    return false;
                }

                //<jp> 入力されたStaionが存在するかチェック</jp>
                //<en> Check to see if the Station which was entered exists.</en>
                stkey.setStationNo(mParameter.getStationNo());
                if (sthandle.count(stkey) <= 0)
                {
                    akey.setStationNo(mParameter.getStationNo());
                    if (ahandle.count(stkey) <= 0)
                    {
                        //<jp> 入力されたステーションNo.は存在しません</jp>
                        //<en> The station no. which was jsut entered does not exist.</en>
                        setMessage("6123080");
                        return false;
                    }
                }

                //<jp> 号機No.０以下は不正</jp>
                //<en> The machine no. of 0 or smaller value are invalid.</en>
                if (mParameter.getMachineNumber() <= 0)
                {
                    //<jp>号機No.は1以上の値を入力してください</jp>
                    //<en>Please enter 1 or greater value for the mahine no.</en>
                    setMessage("6123211");
                    return false;
                }
                
                //<jp>AGCNo.とステーションNo.がテーブル通りに対応しているか</jp>
                //<jp>STATION表をチェック</jp>
                //<en>Check to see if the AGCNo. and the station no. function as in tables</en>
                //<en>Check the STATION table.</en>
                ToolStationHandler stationhandle = new ToolStationHandler(conn) ;
                ToolStationSearchKey stationkey = new ToolStationSearchKey() ;
                stationkey.setStationNo(mParameter.getStationNo()) ;
                stationkey.setControllerNo(mParameter.getControllerNumber()) ;
                //<jp>AISLE表をチェック</jp>
                //<en>Check the AISLE table.</en>
                ToolAisleHandler ailehandle = new ToolAisleHandler(conn) ;
                ToolAisleSearchKey ailekey = new ToolAisleSearchKey() ;
                ailekey.setStationNo(mParameter.getStationNo()) ;
                ailekey.setControllerNo(mParameter.getControllerNumber()) ;
                if (stationhandle.count(stationkey) <= 0 && ailehandle.count(ailekey) <= 0)
                {
                    //<jp>AGC No.={0}とステーションNo.={1}が正しく対応していません</jp>
                    //<en>AGC No.={0} and the station no. ={1} do not corporate properly.</en>
                    setMessage("6123270" + wDelim + mParameter.getControllerNumber() + wDelim + mParameter.getStationNo());
                    return false ;
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
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String filename, Locale locale) throws ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        try
        {
            String checkfile = ToolParam.getParam("CONSTRAINT_CHECK_FILE");
            LogHandler loghandle = new LogHandler(filename + "/" + checkfile, locale);

            ToolAs21MachineSearchKey machineKey = new ToolAs21MachineSearchKey();

            int count = getToolAs21MachineStateHandler(conn).count(machineKey);
            //<jp> 機器情報設定されていない場合</jp>
            //<en> If the machine information has not been set,</en>
            if (count <= 0)
            {
                //<jp>機器情報が設定されていません</jp>
                //<en>The machine information has not been set.</en>
                loghandle.write("Machine", "Machine Table", "6123216");
                //<jp> 機器情報が設定されていない場合は、後のチェックを行わずに抜ける</jp>
                //<en> If the machine information has not been set, discontinue the checks and exit.</en>
                return false;
            }

            //<jp>*** グループコントローラNoのチェック(Machine表) ***</jp>
            //<jp>Machine表のグループコントローラNoがグループコントローラ表に存在するか確認</jp>
            //<en>*** Check the group controller no. (Machine table) ***</en>
            //<en>Check to see if the group controller no. in Machine table exists </en>
            //<en>in the group controller table.</en>
            As21MachineState[] machineArray = (As21MachineState[])getToolAs21MachineStateHandler(conn).find(machineKey);
            for (int i = 0; i < machineArray.length; i++)
            {
                if (!check.isExistControllerNo(machineArray[i].getControllerNumber()))
                {
                    loghandle.write("Machine", "Machine Table", check.getMessage());
                    errorFlag = false;
                }
            }
            
            //<jp>*** ステーションNoのチェック(Machine表) ***</jp>
            //<jp>Machine表のステーションNoがステーション表もしくはアイル表に存在するか確認</jp>
            //<en>*** Check the station no. (Machine table) ***</en>
            //<en>Check to see if the station no. in Machine table exists in station table or in aisle table.</en>
            for (int i = 0; i < machineArray.length; i++)
            {
                String stationNo = machineArray[i].getStationNo();
// 2004.12.20 T.Yamashita UPD Start
//                if(!check.isExistAllStationNo(stationNo))
                if (!check.isExistAllMachiniStationNo(stationNo))
// 2004.12.20 T.Yamashita UPD End
                {
                    loghandle.write("Machine", "Machine Table", check.getMessage());
                    errorFlag = false;
                }
                if (!check.isExistStationType(stationNo))
                {
                    loghandle.write("Machine", "Machine Table", check.getMessage());
                    errorFlag = false;
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
        MachineParameter mParam = (MachineParameter)param;

        //<jp>同一データチェック</jp>
        //<en>Check the duplciate data.</en>
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
            if (!create(conn))
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
            RmiMsgLogClient.write( msg, (String)this.getClass().getName());
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");    
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ReStoringHandler</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>ReStoringHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
     * @param conn Databse Connection Object
     * @return <CODE>ReStoringHandler</CODE>
     </en>*/
    protected ToolAs21MachineStateHandler getToolAs21MachineStateHandler(Connection conn)
    {
        return new ToolAs21MachineStateHandler(conn);
    }
    
    /**<jp>
     * このクラスの初期化時に生成した<CODE>FindUtil</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>FindUtil</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
     * @param conn Databse Connection Object
     * @return <CODE>FindUtil</CODE>
     </en>*/
    protected ToolFindUtil getFindUtil(Connection conn)
    {
        return new ToolFindUtil(conn);
    }
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
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
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
     * @param conn データベース接続用 Connection
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    protected boolean create(Connection conn) throws ReadWriteException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            MachineParameter castparam = null;
            if (mArray.length > 0)
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                getToolAs21MachineStateHandler(conn).truncate();
                As21MachineState machine = new As21MachineState();
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (MachineParameter)mArray[i];
                    machine.setControllerNumber(castparam.getControllerNumber());
                    machine.setType(castparam.getMachineType());
                    machine.setNumber(castparam.getMachineNumber());
                    machine.setStationNo(castparam.getStationNo());
                    // DFKLOOK 20100222 追加
                    machine.setDeviceName(castparam.getDeviceName());
                    // DFKLOOK
                    machine.setState(As21MachineState.STATE_ACTIVE);

                    getToolAs21MachineStateHandler(conn).create(machine);
                }
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                getToolAs21MachineStateHandler(conn).truncate();
                return true;
            }
        }
        catch (DataExistsException e)
        {
            //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません</jp>
            //<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
            setMessage("6123016");
            return false;
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
     * In the warehouse setting, it checks whether/not the storage type of appending parameter exists 
     * in the entered data summary.
     * @param param  :the parameter which will be appended in this process
     * @param array  :entered data summary (pool)
     * @return    :return true if identical data exists.
     </en>*/
    private boolean isSameData(MachineParameter param, Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        String newKey = "";
        String orgKey = "";

        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加する格納区分で比較する</jp>
            //<en>Compare by the storage type appended in this process.</en>
            newKey = Integer.toString(param.getMachineType())
                   + Integer.toString(param.getMachineNumber())
                   + Integer.toString(param.getControllerNumber());

            for (int i = 0 ; i < array.length ; i++)
            {
                MachineParameter castparam = (MachineParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgKey = Integer.toString(castparam.getMachineType())
                       + Integer.toString(castparam.getMachineNumber())
                       + Integer.toString(castparam.getControllerNumber());
                
                //<jp>同一ステーションNoのチェック</jp>
                //<en>Check the identical user name</en>
                if (newKey.equals(orgKey))
                {
                    //<jp>6123192 = すでに登録されています。同一AGCNo.・機種コード・号機No.を入力することはできません</jp>
                    //<en>6123192 = The data is already entered. Cannot input the identical AGCNo.</en>
                    //<en>machine code and machine no.</en>
                    setMessage("6123192");
                    return true;
                }
            }
        }

        return false;
    }

    /**<jp>
     * As21MachineStateインスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <code>As21MachineState</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the As21MachineState instance.
     * @param conn Databse Connection Object
     * @return :the array of <code>As21MachineState</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private As21MachineState[] getAs21MachineStateArray(Connection conn) throws ReadWriteException
    {
        ToolAs21MachineSearchKey machineKey  = new ToolAs21MachineSearchKey();
        machineKey.setControllerNoOrder(1, true);
        machineKey.setMachineTypeOrder(2, true);
        machineKey.setMachineNoOrder(3, true);
        

        //<jp>*** As21MachineStateインスタンスを取得 ***</jp>
        //<en>*** Retrieve the As21MachineState of instance  ***</en>
        As21MachineState[] array = (As21MachineState[])getToolAs21MachineStateHandler(conn).find(machineKey);
        return array;
    }
}
//end of class

