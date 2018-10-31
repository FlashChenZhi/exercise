// $Id: DummyStationCreater.java 5355 2009-11-02 00:44:35Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * ダミーステーション設定を行なうクラスです。
 * AbstractCreaterを継承し、ステーション設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class sets up the dummy station.
 * It inherits the AbstractCreater and implements the processes required in setting stations.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class DummyStationCreater extends AbstractCreater
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
    * <CODE>ToolStationSearchKey</CODE>インスタンス
     </jp>*/
    /**<en>
    * <CODE>ToolStationSearchKey</CODE> instance
     </en>*/
    protected ToolStationSearchKey wStationKey = null;
    /**<jp>
     * <CODE>ToolStationAlterKey</CODE>インスタンス
     </jp>*/
    /**<en>
     * <CODE>ToolStationAlterKey</CODE> instance
     </en>*/
    protected ToolStationAlterKey wStationAKey = null;
    
    private boolean TRUE = true;

    private boolean FALSE = false;

    /**<jp> クラス名（入庫専用） </jp>*/
    /**<en> Class name (dedicated for storage)</en>*/
    public static String CLASS_STORAGE  = "jp.co.daifuku.wms.asrs.location.StorageStationOperator" ;
    
    /**<jp> クラス名（出庫専用） </jp>*/
    /**<en> Class name (dedicated for retrieva)</en>*/
    public static String CLASS_RETRIEVAL  = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator" ;
    
    /**<jp> クラス名（固定荷受台・自走台車） </jp>*/
    /**<en> Class name (P&D stand, powered cart)</en>*/
    public static String CLASS_INOUTSTATION  = "jp.co.daifuku.wms.asrs.location.InOutStationOperator" ;
    
    /**<jp> クラス名（コの字入庫） </jp>*/
    /**<en> Class name  (U-shaped storage) </en>*/
    public static String CLASS_FREESTORAGE  = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator" ;
    
    /**<jp> クラス名（コの字出庫） </jp>*/
    /**<en> Class name  (U-shaped retrieval)</en>*/
    public static String CLASS_FREERETRIEVAL  = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator" ;
    
    /**<jp> 荷高 </jp>*/
    /**<en> load height </en>*/
    private int HEIGHT = 0;
    
    /**<jp> 製番フォルダ </jp>*/
    /**<en> product number folder </en>*/
    public String wFilePath = "";
    
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
        return ("$Revision: 5355 $,$Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position.<BR>
     * @param conn :Connection to connect with database
     * @param index :position of the parameter to be deleted
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(Connection conn, int index) throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        try
        {
            wParamVec.remove(index);
            setMessage("6121003");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
    }
    /**<jp>
     * 全てのパラメータを削除します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all the parameters.<BR>
     * @param conn :Connection to connect with database
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeAllParameters(Connection conn) throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");

        wParamVec.removeAllElements();
        //<jp>削除しました</jp>
        //<en>Deleted the data.</en>
        setMessage("6121003");
    }
    /**<jp>
     * このクラスの初期化を行ないます。初期化時に<CODE>ToolStationHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ToolStationHandler</CODE> at the initialization.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public DummyStationCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param <code>Locale</code> オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Implement the process to run when the print-issue button was pressed on the display.<BR>
     * @param locale object
     * @param listParam :schedule parameter
     * @return :result of print job
     </en>*/
    public boolean print(Locale locale, Parameter listParam)
    {
        return true;
    }

    /**<jp>
     * 画面へ表示するデータを取得します。<BR>
     * @param conn データベース接続用のConnection
     * @param locale <code>Locale</code>オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn :Connection to connet with database
     * @param locale <code>Locale</code> object
     * @param searchParam :search conditions
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
    {
        Station[] array = getDummyStationArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>Vector vec = new Vector(100);    
        Vector vec = new Vector(100);
        
        //<jp>他のメソッドではファイル名を外部から渡すことができないので</jp>
        //<jp>ここでセットする。</jp>
        //<en>The file name should be set here, as it cannot be provided from external </en>
        //<en>by other methods.</en>
        wFilePath =  ((StationParameter)searchParam).getFilePath();

        if (array.length > 0)
        {
            for (int i = 0 ; i < array.length ; i++)
            {
                //<jp>表示用のエンティティクラス</jp>
                //<en>Entity class for display</en>
                StationParameter dispData = new StationParameter() ;
                //<jp>倉庫ステーションNo.</jp>
                //<en>warehouse station no.</en>
                dispData.setWareHouseStationNo(array[i].getWarehouseStationNo());    
                //<jp>ステーションNo.</jp>
                //<en>station no.</en>
                dispData.setNumber(array[i].getStationNo());                                    
                //<jp>ステーション名称</jp>
                //<en>station name</en>
                dispData.setName(array[i].getStationName());                                        
                //AGC No.
                dispData.setControllerNumber(array[i].getGroupController().getControllerNumber()) ; 

                vec.addElement(dispData);
            }
            StationParameter[] fmt = new StationParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * ＜格納区分＞<BR>
     * WAREHOUSE表に存在するか<BR>
     * ＜AGC No.＞<BR>
     * GROUPCONTROLLER表に存在するか<BR>
     * ＜ステーションNo.＞<BR>
     * 禁止文字が使われていないか<BR>
     * ＜ステーション名称＞<BR>
     * 禁止文字が使われていないか<BR>
     * @param conn データベース接続用のConnection
     * @param param チェックするパラメータ
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the paramter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * <storage type><BR>
     *  whether/not the data exists in WAREHOUSE table.
     * <AGC No.><BR>
     *  whether/not the data exists in GROUPCONTROLLER table.<BR>
     * <station no.><BR>
     *  whether/not the unacceptable letters and symbols are used.<BR>
     * <station name><BR>
     *  whether/not the unacceptable letters and symbols are used.<BR>
     * @param conn  :connection to connect with database
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        StationParameter mParameter = (StationParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        switch(processingKind)
        {
            case M_CREATE:
                //<jp> 倉庫表に登録されているかチェック</jp>
                //<en> Check to see if the data is registered in the warehouse table.</en>
                ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
                ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();
    
                if (warehousehandle.count(warehosuekey) <= 0)
                {
                    //<jp> 6123117 = 倉庫管理情報がありません。倉庫設定で登録してください</jp>
                    //<en> 6123117 = There is no warehous control data. Please register in the setting </en>
                    //<en> display of warehouses.</en>
                    setMessage("6123117");
                    return false;
                }
                //<jp> GROUPCONTROLLER表に登録されているかチェック</jp>
                //<en> Check to see of the data is registered in GROUPCONTROLLER table.</en>
                ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
                ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();
    
                if (gchandle.count(gckey) <= 0)
                {
                    //<jp> グループコントローラ情報がありません。グループコントローラ設定画面で登録してください</jp>
                    //<en> There is no group controller data. Please register in the setting diplay of </en>
                    //<en> group controller.</en>
                    setMessage("6123078");
                    return false;
                }

                //<jp>ステーションNoのチェック(禁止文字チェック)</jp>
                //<en>Check the station no. (unacceptable letters and symbols)</en>
                if (!check.checkStationNo(mParameter.getNumber()))
                {
                    //<jp>異常内容をセットする</jp>
                    //<en>Set the contents of error.</en>
                    setMessage(check.getMessage());
                    return false;
                }

                //<jp>ステーション名称のチェック(禁止文字チェック)</jp>
                //<en>Check the station name. (unacceptable letters and symbols)</en>
                if (!check.checkStationName(mParameter.getName()))
                {
                    //<jp>異常内容をセットする</jp>
                    //<en>Set the contents of error.</en>
                    setMessage(check.getMessage());
                    return false;
                }

                // システム定義チェック
                if (WmsParam.ALL_STATION.equals(mParameter.getNumber()))
                {
                    // 6023222=入力された{0}はシステムで使用しているため登録できません。
                    setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W0303")));
                    return false;
                }
                break ;
                
            default :
                //<jp> 予期しない値がセットされました。{0} = {1}</jp>
                //<en> Unexpected value has been set.{0} = {1}</en>
                String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
                //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
                throw new ScheduleException("6126499") ;    
        }
        
        return true ;
    }

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * 異常があった場合、その詳細をファイルへ書き込みます。
     * @param conn データベース接続用のConnectionオブジェクト
     * @param logpath 異常が発生したときのログを書き込むファイルを置くためのパス
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file which is specified by filename.
     * @param conn    :Connection object to connect with database
     * @param logpath :Path to place teh file the log will be written in when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/" + ToolParam.getParam("CONSTRAINT_CHECK_FILE");
        
        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);
            
            ToolStationSearchKey wstationKey = new ToolStationSearchKey();
            Station[] wArray = (Station[])getToolStationHandler(conn).find(wstationKey);
            //<jp>*** Stationが設定されていない場合 ***</jp>
            //<en>*** If Station has not been set ***</en>
            if (wArray.length == 0)
            {
                //<jp>6123181 = ステーションが設定されていません</jp>
                //<en>6123181 = Station has not been set.</en>
                loghandle.write("DummyStation", "Station Table", "6123181");
                //<jp>ステーションが設定されていない場合は、後のチェックを行わずに抜ける</jp>
                //<en>If the station has not been set, discontinue the check and exit.</en>
                return false;
            }

            ToolStationSearchKey stationKey = new ToolStationSearchKey();
            stationKey.setStationType(Station.STATION_TYPE_OTHER);
            stationKey.setSendable(Station.NOT_SENDABLE);
            Station[] stArray = (Station[])getToolStationHandler(conn).find(stationKey);
            
            //<jp>*** 格納区分のチェック(Warehouse表) ***</jp>
            //<jp>STATION表の倉庫ステーションNo.がWAREHOUSE表に存在するか確認</jp>
            //<en>*** Check the storage type (Warehouse table) ***</en>
            //<en>Check if the warehouse station no. of the STATION table exists in WAREHOUSE table.</en>
            for (int i = 0 ; i < stArray.length ; i++)
            {
                String warehouseStationNo = stArray[i].getWarehouseStationNo();
                if (!check.isExistAutoWarehouseStationNo(warehouseStationNo))
                {
                    loghandle.write("DummyStation", "Station Table", check.getMessage());
                    errorFlag = false;
                }
            }
            //<jp>*** ステーションNoのチェック(Station表) ***</jp>
            //<jp>STATION表のステーションNo.がSTATIONTYPE表に存在するか確認</jp>
            //<en>*** Check the station no. (Station table) ***</en>
            //<en>Check if the station no. of the STATION table exists in STATIONTYPE table.</en>
            for (int i = 0 ; i < stArray.length ; i++)
            {
                String stationNo = stArray[i].getStationNo();
                if (!check.isExistStationType(stationNo))
                {
                    loghandle.write("DummyStation", "Station Table", check.getMessage());
                    errorFlag = false;
                }
            }
            //<jp>*** AGC No.のチェック(GroupController表) ***</jp>
            //<jp>STATION表のAGC No.がGROUPCONTROLLER表に存在するか確認</jp>
            //<en>*** Check the AGC No. (GroupController table) ***</en>
            //<en>Check if AGC no. of the STATION table exists in GROUPCONTROLLER table.</en>
            for (int i = 0 ; i < stArray.length ; i++)
            {
                int agcNo = stArray[i].getGroupController().getControllerNumber();
                if (!check.isExistControllerNo(agcNo))
                {
                    loghandle.write("DummyStation", "GroupController Table", check.getMessage());
                    errorFlag = false;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
    
        }
        return errorFlag;
    }

    /**<jp>
     * パラメータの重複チェック処理を行ないます。<BR>
     * 重複したステーションNo.を登録することはできません。
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用のConnectionオブジェクト
     * @param param チェックするパラメータ
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.<BR>
     * Identical station no. cannot be registered. 
     * It checks the duplication of parameter, then returns true if there was no duplicated data 
     * or returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :Connection to connect with database
     * @param param :parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        Parameter[] mArray = (Parameter[])getParameters();
        StationParameter mParam = (StationParameter)param;

        //<jp>STATIONTYPE表に同一のデータがないかチェック</jp>
        //<en>Chec if the identical data exists in STATIONTYPE table.</en>
        String stationNo = mParam.getNumber();
        if (getToolStationHandler(conn).isStationType(stationNo, ToolStationHandler.STATION_HANDLE))
        {
            //<jp>6123122 すでに登録されています。同一ステーションNoを登録することはできません。</jp>
            //<en>6123122 The data is already registered. Cannot register the identical station no.</en>
            setMessage("6123122");
            return false;
        }

        //<jp>STATION表に同一のデータがないかチェック</jp>
        //<en>Check if the identical data exists in STATION table.</en>
        if (isStationSameData(conn, mParam))
        {
            return false;
        }

        //<jp>同一データチェック(ためうちチェック)</jp>
        //<en>Check the identical data(entered data summary).</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }

        //<jp>修正の時のみチェックする。</jp>
        //<en>Check only when modifing data.</en>
        if (getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
        {
            //<jp>*** 修正の時、キー項目は変更不可とする ***</jp>
            //<en>*** Key items are regarder not modifiable when modifing data. ***</en>
            //<jp> 倉庫ステーションNo.</jp>
            //<en> warehouse station no.</en>
            String warehouseStNo = mParam.getWareHouseStationNo();        
            //<jp> ステーションNo.</jp>
            //<en> station no.</en>
            String stNo = mParam.getNumber();                                
            //<jp>ため打ちの中のキー項目</jp>
            //<en>Key items for enterd data summary</en>
            String orgwarehouseStNo = "";
            String orgstationNo = "";
            //<jp>修正中のパラメータ</jp>
            //<en>Parameter which is modified</en>
            
            
            Parameter[] mAllArray = (Parameter[])getAllParameters();
            for (int i = 0 ; i < mAllArray.length ; i++)
            {
                StationParameter castparam = (StationParameter)mAllArray[i];
                //<jp>キー項目</jp>
                //<en>Key items</en>
                orgwarehouseStNo = castparam.getWareHouseStationNo();
                orgstationNo = castparam.getNumber();
                
                if (warehouseStNo.equals(orgwarehouseStNo)
                    && stNo.equals(orgstationNo))
                {
                    return true;
                }
            }

            return true;
        }
        return true;
    }

    /**<jp>
     * メンテナンス処理を行います。
     * メンテナンス処理の種類は処理区分（getProcessingKind()メソッドより取得）により内部で判断する必要があります。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用のConnectionオブジェクト
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
     * @param conn :connection object to connect with database
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
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");    
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolStationHandler</CODE>インスタンスを取得します。
     * @param conn データベース接続用のConnectionオブジェクト
     * @return <CODE>ToolStationHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolStationHandler</CODE> instance generated at the initialization of this class.
     * @param conn :connection object to connect with database
     * @return <CODE>ToolStationHandler</CODE>
     </en>*/
    protected ToolStationHandler getToolStationHandler(Connection conn)
    {
        return new ToolStationHandler(conn);
    }
    
    /**<jp>
     * このクラスの初期化時に生成した<CODE>FindUtil</CODE>インスタンスを取得します。
     * @param conn データベース接続用のConnectionオブジェクト
     * @return <CODE>FindUtil</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
     * @param conn :connection object to connect with database
     * @return <CODE>FindUtil</CODE>
     </en>*/
    protected ToolFindUtil getFindUtil(Connection conn)
    {
        return new ToolFindUtil(conn);
    }
    
    /**<jp>
     * パラメータの補完処理を行います。<BR>
     * ・他の端末で更新されたかチェックするためStationParameterインスタンス
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
     * - Append StationParameter instance to the parameter in ordder to check whether/not the data 
     *   has been modified by other terminals.
     * It returns the complemented parameter if the process succeeded, or returns false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param :parameter which is used for the complementarity process
     * @return :returns the parameter if the process succeeded, ro returns null if it failed.
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
     * AlterKeyに検索条件として作業No、品名コード、ロットNoをセットして入庫数を更新します。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Sets the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean modify()
    {
        return true;
    }

    /**<jp>
     * メンテナンス登録処理を行います。再入庫予定データの登録は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用のConnectionオブジェクト
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations. The scheduled restoring data will not be registered.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :connection object to connect with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn) throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            StationParameter castparam = null;
            

            if (mArray.length > 0)
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropDummyStation(conn);

                //<jp>***STATION表の更新処理 *****/</jp>
                //<en>***** Update process of STATION table *****/</en>
                Station station = new Station();
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (StationParameter)mArray[i];

                    //<jp>画面入力より（ステーションNo.）</jp>
                    //<en>the entered data via screen (station no.)</en>
                    //<jp>ステーションNo.</jp>
                    //<en>station no.</en>
                    station.setStationNo(castparam.getNumber());                        
                    //<jp>搬送指示可能数(0)</jp>
                    //<en>number of carry instruction sendable(0)</en>
                    station.setMaxInstruction(0);                                            
                    //<jp>出庫指示可能数(0)</jp>
                    //<en>number of retrieva instruction sendable(0)</en>
                    station.setMaxPalletQty(0);                                        
                    //<jp>送信可能区分（0:送信不可能）</jp>
                    //<en>division of sendability(0:not sendable)</en>
                    station.setSendable(FALSE);                                                
                    //<jp>状態（0:切離中）</jp>
                    //<en>status (0:off-line)</en>
                    station.setStatus(Station.STATION_NG);                                    
                    //<jp>画面入力より（AGC No.）</jp>
                    //<en>the entered data via screen(AGC No.)</en>
                    //AGCNo(0)
                    GroupController gc = new GroupController(conn, castparam.getControllerNumber());                        
                    //<jp>画面入力のAGC No.</jp>
                    //<en>AGC No. entered via screen</en>
                    station.setGroupController(gc);                                            
                    //<jp>ステーション種別(0:その他)</jp>
                    //<en>station type (0:other)</en>
                    station.setStationType(Station.STATION_TYPE_OTHER);                            
                    //<jp>設定区分(0)</jp>
                    //<en>setting type(0)</en>
                    station.setSettingType(0);                                            
                    //<jp>作業場種別（0:未指定）</jp>
                    //<en>workshop type(0:unspecified)</en>
                    station.setWorkPlaceType(Station.NOT_WORKPLACE);                        
                    //<jp>作業表示運用(0:作業指示画面なし)</jp>
                    //<en>on-line indication(0:no displat)</en>
                    station.setOperationDisplay(Station.NOT_OPERATIONDISPLAY);                
                    //<jp>画面入力より（ステーション名称）</jp>
                    //<en>the entered data via screen (station name)</en>
                    //<jp>ステーション名称</jp>
                    //<en>station name</en>
                    station.setStationName(castparam.getName());                                    
                    //<jp>中断中フラグ（0:使用可能）</jp>/
                    //<en>suspention flag (0:available)</en>
                    station.setSuspend(FALSE);                                                
                    //<jp>到着報告（チェックなし）</jp>
                    //<en>arrival report(no check)</en>
                    station.setArrivalCheck(FALSE);                                            
                    //<jp>荷姿検知器（チェックなし）</jp>
                    //<en>load size checker (no check)</en>
                    station.setLoadSize(FALSE);                                        
                    //<jp>払出し区分（0:払出し可）</jp>
                    //<en>removal (0:available)</en>
                    station.setRemove(TRUE);                                                
                    //<jp>在庫確認チェック（0:在庫確認未作業）</jp>
                    //<en>inventory checking (0:unprocessed)</en>
                    station.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);                
                    //<jp>再入庫作業（なし）</jp>
                    //<en>restorage work (none)</en>
                    station.setReStoringOperation(FALSE);                                    
                    //<jp>再入庫搬送指示送信有無(0:搬送指示不要)</jp>
                    //<en>requriements for carry instruction</en>
                    //<en>for restorage (0:not necessary)</en>
                    station.setReStoringInstruction(0);
                    //<jp>画面入力より（格納区分から得た倉庫ステーションNo.）</jp>
                    //<en>the entered data via screen (warehouse station no. retrieved from the storage type)</en>
                    //<jp>倉庫ステーションNo.</jp>
                    //<en>warehouse station no.</en>
                    station.setWhStationNo(castparam.getWareHouseStationNo());
                    //<jp>親ステーションNo.(空白)</jp>
                    //<en>parent station no.(space)</en>
                    station.setParentStationNo("");                                        
                    //<jp>アイルステーションNo.(空白)</jp>
                    //<en>aisle station no. (space)</en>
                    station.setAisleStationNo("");                                        
                    //<jp>モード切替(モード切替無し)</jp>
                    //<en>mode switch (no seitch)</en>
                    station.setModeType(Station.NO_MODE_CHANGE);                            
                    //<jp>現在作業モード（0:ニュートラルモード）</jp>
                    //<en>current work mode(0:neutral)</en>
                    station.setCurrentMode(Station.NEUTRAL);                                
                    //<jp>モード切替要求区分（モード切替要求なし）</jp>
                    //<en>request of mode switch(no request)</en>
                    station.setModeRequest(Station.NO_REQUEST);                                                                    
                    //<jp>クラス名(空白)</jp>
                    //<en>class name (space)</en>
                    station.setClassName("");                                                

                    getToolStationHandler(conn).create(station);
                }
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropDummyStation(conn);
                return true;
            }
        }
        catch (DataExistsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (InvalidStatusException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (ReadWriteException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (NotFoundException e)
        {
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
     * Set the process type of selected item to delete to 'processed'. The acrual 
     * deletion will be done in daily transactions.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or returns false if it failed. 
     </en>*/
    protected boolean delete()
    {
        return true;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * リストボックスより編集するデータを選択するときに、同一のデータが選択されたことを
     * 確認するためのチェックを実装します。ステーション設定では、
     * 追加するパラメータの格納区分がため打ちデータ内に存在するかを確認します。
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing data
     * to edit from the listbox. 
     * In the station setting, it checks whether/not the storage type of appending parameter exists in 
     * the entered data summary.
     * @param param :parameter which wil be appended in this process
     * @param array :entered data summary
     * @return      :return true if identical data exists.
     </en>*/
    private boolean isSameData(StationParameter param, 
                                Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        //<jp>通常使用しない値</jp>
        //<en>the value which normally will not be used</en>
        String newStationNo = "99999"; 
        //<jp>通常使用しない値</jp>
        //<en>the value which normally will not be used</en>
        String orgStationNo = "99999"; 
        
        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加するステーションNo.で比較する</jp>
            //<en>Compare by the station no. appended in this process.</en>
            newStationNo = param.getNumber();
            
            for (int i = 0 ; i < array.length ; i++)
            {
                StationParameter castparam = (StationParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgStationNo = castparam.getNumber();
                //<jp>同一ステーションNo.のチェック</jp>
                //<en>Check the identical station no.</en>
                if (newStationNo.equals(orgStationNo))
                {
                    //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません</jp>
                    //<en>6123016 = The data is already enterd. Cannot input the identical station numbers.</en>
                    setMessage("6123016");
                    return true;
                }
            }
        }
        return false;
    }

    /**<jp>
     * STATION表に同一のステーションNo.が登録されているかチェックします。
     * 登録されている場合はtrue、登録されていない場合はfalseを返します。
     * ステーション種別が'0'以外のもので判断しているのは、修正の場合に自分のステーションNo.で
     * 入力不可の判断をしてしまわないためです。
     * @param param 今回追加するパラメータ
     * @return    true：同一データあり  false：同一データなし
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the identical station no. is registered in STATION table.
     * Return true if it is registered, or false if not.
     * Station type other than '0' should be the targeted, as station type 0 may cause incorrect process
     * e.g., it may determine that own station no. cannot be entered when modifing data, etc.
     * @param conn :connection object to connect with database
     * @param param :the parameter which will be appended in this process
     * @return      true:Identical data is found, false:There is no identical data.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private boolean isStationSameData(Connection conn, StationParameter param) throws ReadWriteException
    {
        wStationKey = new ToolStationSearchKey();
        wStationKey.setStationNo(param.getNumber());
        Station[] array = (Station[])getToolStationHandler(conn).find(wStationKey);
        for (int i = 0 ; i < array.length ; i++)
        {
            //<jp> ステーション種別が'0'以外で同一ステーション№のものがあるとNG</jp>
            //<en> Unacceptable if station type is other than 0 and idetntical station no. exists.</en>
            if (array[i].getStationType() != Station.STATION_TYPE_OTHER)
            {
                //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません</jp>
                //<en>6123016 = The data is already registered. Cannot entere the identical station no.</en>
                setMessage("6123016");
                return true;
            }
        }
        return false;
    }

    /**<jp>
     * Stationインスタンスを取得します。
     * @return <code>Station</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the Station instance.
     * @param conn :connection object to connect with database
     * @return :the array of <code>Station</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Station[] getDummyStationArray(Connection conn) throws ReadWriteException
    {
        ToolStationSearchKey stationKey  = new ToolStationSearchKey();
        //<jp> ステーション種別が0:その他のデータを検索</jp>
        //<en> Station type 0: search other data</en>
        stationKey.setStationType(Station.STATION_TYPE_OTHER);
        stationKey.setSendable(Station.NOT_SENDABLE);
        //<jp> 表示順、倉庫ステーションNo、ステーションNo.</jp>
        //<en> order of data display : station no.</en>
        stationKey.setWareHouseStationNoOrder(1, true);
        stationKey.setStationNoOrder(2, true);
        //<jp>*** Stationインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Station instance ***</en>
        Station[] array = (Station[])getToolStationHandler(conn).find(stationKey);
        return array;
    }
    
    /**<jp>
     * ステーションのTYPEが”その他”で送信不可のステーションを削除します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべきデータが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the stations of the type 'other' and not sendable.
     * @param conn :connection object to connect with database
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    private void isDropDummyStation(Connection conn) throws ReadWriteException, NotFoundException
    {
        wStationKey = new ToolStationSearchKey();
        wStationKey.setStationType(Station.STATION_TYPE_OTHER);
        wStationKey.setSendable(Station.NOT_SENDABLE);
        getToolStationHandler(conn).drop(wStationKey);
    }
    
}
//end of class

