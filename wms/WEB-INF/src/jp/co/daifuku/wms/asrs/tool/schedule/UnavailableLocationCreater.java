// $Id: UnavailableLocationCreater.java 87 2008-10-04 03:07:38Z admin $
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

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;

/**<jp>
 * 禁止棚設定を行なうクラスです。
 * AbstractCreaterを継承し、禁止棚設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>テキストへの書き込みをこの画面では行わないように変更。<BR>
 * この画面では、禁止棚に設定された棚のSHELF表のACCESSNGFLAGを更新する。<BR>
 * テキストへの書き込みはDataOperator.javaで行う。<BR>
 * また、本画面への遷移時にテキストを読み込み、SHELF表より削除されている棚をcreateする。
 * </TD></TR>
 * <TR><TD>2003/12/19</TD><TD>okamura</TD><TD>Modified method of setUnavailableLocation<BR>
 * The conditions for displaying at the time of screen changes are added.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class sets up the restricted locations.
 * IT inherits the AbstractCreater, and implements the processes requried to set the restricted 
 * locations. 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>disabled the writing with texts via this display.<BR>
 * Through this screen, ACCESSNGFLAG of Shelf table for the restricted locations can be updated.<BR>
 * The writing of the texts will be done at DataOperator.java.<BR>
 * Also it reads text when transfering to this screen and create locations which have been deleted
 * from SHELF table.
 * </TD></TR>
 * <TR><TD>2003/12/19</TD><TD>okamura</TD><TD>Modified method of setUnavailableLocation<BR>
 * The conditions for displaying at the time of screen changes are added.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class UnavailableLocationCreater extends AbstractCreater
{
    // Class fields --------------------------------------------------
    /**<jp> 棚情報を保存するテキスト名称  </jp>*/
    /**<en> Name of the text which saves the shelf information. </en>*/
    public static final String SHELFTEXT_NAME = "UnUnavailableLoc.txt";

    /**<jp>
     * デリミタ
     </jp>*/
    /**<en>
     * Delimiter
     </en>*/
    public static final String DELIM = ",";

    // Class variables -----------------------------------------------

/* 2003/12/12 INSERT okamura START */
    public static boolean TRUE = true;

    public static boolean FALSE = false;
/* 2003/12/12 INSERT okamura END   */

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
     * このクラスの初期化を行ないます。初期化時に<CODE>ReStoringHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public UnavailableLocationCreater(Connection conn, int kind)
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
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector<UnavailableLocationParameter> vec = new Vector(100);

        //<jp>表示用のエンティティクラス作成</jp>
        //<en>Create the entity class for display.</en>
        UnavailableLocationParameter dispData = null;
        Shelf[] array = getShelfArray(conn);
        if (array.length > 0)
        {
            for (int disp = 0; disp < array.length; disp++)
            {
                dispData = new UnavailableLocationParameter();
                int whNumber = getFindUtil(conn).getWarehouseNumber(array[disp].getWarehouseStationNo());
                dispData.setWarehouseNumber(whNumber);
                dispData.setBank(array[disp].getBankNo());
                dispData.setBay(array[disp].getBayNo());
                dispData.setLevel(array[disp].getLevelNo());
                vec.addElement(dispData);
            }
            UnavailableLocationParameter[] fmt = new UnavailableLocationParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null ;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * 指定された棚が倉庫に存在する棚かどうかをチェックします。
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
     * Check to see of the specified location exists in the warehouse.
     * @param conn Databse Connection Object
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        UnavailableLocationParameter mParam 
            = (UnavailableLocationParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        if (processingKind == M_CREATE)
        {
            //<jp> 倉庫表に登録されているかチェック</jp>
            //<en> Check to see if the specified data is registered in warehouse table.</en>
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();
            if (warehousehandle.count(warehosuekey) <= 0)
            {
                //<jp> 6123117 = 倉庫管理情報がありません。倉庫設定で登録してください</jp>
                //<en> 6123117 = The information of the warehouse cannot be found. </en>
                //<en> Please register in warehouse setting screen.</en>
                setMessage("6123117");
                return false;
            }
            //<jp>指定された棚が倉庫に存在するかを確認</jp>
            //<en>Check if the specified data is registered in warehouse table.</en>
            if (!getFindUtil(conn).isExistShelf(mParam.getWarehouseNumber(),
                                            mParam.getBank(), 
                                            mParam.getBay(), 
                                            mParam.getLevel()))
            {
                //<jp>6123037 指定された棚No.は倉庫内に存在しません</jp>
                //<en>6123037 The location no. specified does not exist in the warehouse.</en>
                setMessage("6123037");
                return false;
            }
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

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * 異常があった場合、その詳細をファイルへ書き込みます。
     * @param conn データベース接続用 Connection
     * @param logpath 異常が発生したときのログを書き込むファイルを置くためのパス
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file.
     * @param conn Databse Connection Object
     * @param logpath :path to place the file in which the log will be written when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
    {
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + ToolParam.getParam("CONSTRAINT_CHECK_FILE");
        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            Shelf[] shelfArray = getShelfArray(conn);
            if (shelfArray.length == 0)
            {
                //<jp>使用不可棚設定がされていない場合は以下のチェックを行わずにぬけます</jp>
                //<en>If the unavailable setting has not been done withe locations, discontinue</en>
                //<en>the checks and exit.</en>
                return true ;
            }
            else
            {
                for (int i = 0; i < shelfArray.length; i++)
                {
                    String whStationNo = shelfArray[i].getWarehouseStationNo() ;
                    int bank  = shelfArray[i].getBankNo();
                    int bay   = shelfArray[i].getBayNo();
                    int level = shelfArray[i].getLevelNo();
                     
                    String location = Integer.toString(bank) + Integer.toString(bay) + Integer.toString(level) ;
                    String errorMsg = "";
                
                    int[] bayRange = getToolShelfHandler(conn).getBayRange(whStationNo, bank);
/* 2003/12/19 MODIFY okamura START */
                    //<jp>ベイ範囲の確認</jp>
                    //<en>Check the bay range.</en>
                    if (bayRange[0] > bay || bay > bayRange[1])
                    {
                        //<jp>6123142=現在の棚範囲に存在しません。LocationNo={0}</jp>
                        //<en>6123142=It does not exist in current location range. LocationNo={0}</en>
                        errorMsg = "6123142" + wDelim + location;
                        loghandle.write("UnavailableLocation", "UnavailableLocationText", errorMsg);
                        errorFlag = false;
                    }
                    int[] levelRange = getToolShelfHandler(conn).getLevelRange(whStationNo, bank);
                    //<jp>レベル範囲の確認</jp>
                    //<en>Check the level range.</en>
                    if (levelRange[0] > level || level > levelRange[1])
/* 2003/12/19 MODIFY okamura END   */
                    {
                        //<jp>6123142=現在の棚範囲に存在しません。LocationNo={0}</jp>
                        //<en>6123142=It does not exist in current location range. LocationNo={0}</en>
                        errorMsg = "6123142" + wDelim + location;
                        loghandle.write("UnavailableLocation", "UnavailableLocationText", errorMsg);
                        errorFlag = false;
                    }
                    
                }
            }
        }
        catch (IOException e)
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
        UnavailableLocationParameter mParam = (UnavailableLocationParameter)param;
        //<jp>*** 本来checkで実装する処理ですが、入力ボタン押下時にのみ</jp>
        //<jp>*** このチェックを行いたいのでここに実装しています。</jp>
        //<en>*** Though this process should be basically implemented by 'check', </en>
        //<en>*** it is implemented here in this method as this checking is needed </en>
        //<en>*** only when the entere button was pressed.</en>

        //<jp>同一データチェック</jp>
        //<en>Check the identical data.</en>
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
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");    
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolShelfHandler</CODE>インスタンスを取得します。
     * @return <CODE>ToolShelfHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolShelfHandler</CODE> isntance which was generated at the
     * generation of this class.
     * @param conn Databse Connection Object
     * @return <CODE>ToolShelfHandler</CODE>
     </en>*/
    protected ToolShelfHandler getToolShelfHandler(Connection conn)
    {
        return new ToolShelfHandler(conn);
    }
    
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolFindUtil</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>ToolFindUtil</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolFindUtil</CODE>isntance which was generated at the
     * generation of this class.
     * @param conn Databse Connection Object
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
     * - Append ReStoring instance to the parameter in order to check whether/not the data 
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
     * AlterKeyに検索条件として作業No、品名コード、ロットNoをセットして入庫数を更新します。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Set the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
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
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ScheduleException :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn) throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getParameters();
            UnavailableLocationParameter castparam = null;

            ToolShelfAlterKey shelfAlt = new ToolShelfAlterKey();

            if (mArray.length > 0)
            {
                setAccessNgFlag(conn);
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (UnavailableLocationParameter)mArray[i];

                    //<jp>倉庫ステーションNo取得</jp>
                    //<en>Retrieve the warehouse station no.</en>
                    String whstno = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());
                    shelfAlt.setWHStationNo(whstno);
                    shelfAlt.setBankNo(castparam.getBank());
                    shelfAlt.setBayNo(castparam.getBay());
                    shelfAlt.setLevelNo(castparam.getLevel());
                    //<jp> アクセス不可（使用禁止棚にする）</jp>
                    //<en> inaccessible (set restriction)</en>
                    shelfAlt.updateAccessNgFlag(TRUE);    

                    getToolShelfHandler(conn).modify(shelfAlt);
                }
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                setAccessNgFlag(conn);
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
     * Process the deletion of maintenance.
     * Deletion will be done based on the key items of parameter array. 
     * Set the process type of selected item to delete to 'processed'. The actual deletion will 
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
     * 確認するためのチェックを実装します。禁止棚設定では、
     * 追加するパラメータと同一のデータ（格納区分、バンク、ベイ、レベル）がため打ちデータ内に存在するかを確認します。
     * @param param 今回追加するパラメータ
     * @param array ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing data
     * from the list box to edit. In the setting of restricted locations, it checks whether/not the 
     * identical data as apprending parameter (storage type, bank, level) exists in the entered data summary.
     * @param param  :the parameter which will be appended in this process
     * @param array  :entered data summary (pool)
     * @return       :return true if identical data exists.
     </en>*/
    private boolean isSameData(UnavailableLocationParameter param, 
                                Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        int warehousenumber = 99999;
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int bank = 0; 
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int bay = 0; 
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int level = 0; 

        int orgwarehousenumber = 99999;
        int orgbank = 0;
        int orgbay = 0; 
        int orglevel = 0;
        
        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加するロケーションで比較する</jp>
            //<en>Compare by the location appended in this process.</en>
            warehousenumber = param.getWarehouseNumber();
            bank = param.getBank();
            bay = param.getBay();
            level = param.getLevel();
            
            for (int i = 0 ; i < array.length ; i++)
            {
                UnavailableLocationParameter castparam = (UnavailableLocationParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgwarehousenumber = castparam.getWarehouseNumber();
                orgbank = castparam.getBank();
                orgbay = castparam.getBay();
                orglevel = castparam.getLevel();

                //<jp>同一棚Noの確認</jp>
                //<en>Check the identical location no.</en>
                if (warehousenumber == orgwarehousenumber && bank == orgbank && bay == orgbay && level == orglevel)
                {
                    //<jp>6123033 = 既に入力されています。同一棚No.を入力することはできません</jp>
                    //<en>6123033 = The data has already been entered. Cannot enter the identical location no.</en>
                    setMessage("6123033");
                    return true;
                }
            }
        }
        return false;
    }

    /**<jp>
     * 倉庫順、ロケーション順に並べ替えした使用不可棚に設定されているSHELFインスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <code>Shelf</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the SHELF instance which are set as unavailable locations sorted in order of 
     * warehouse and locations.
     * @param conn Databse Connection Object
     * @return :the array of <code>Shelf</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Shelf[] getShelfArray(Connection conn) throws ReadWriteException
    {
        ToolShelfSearchKey shelfKey  = new ToolShelfSearchKey();
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
        shelfKey.setWHStationNoOrder(1, TRUE);
        shelfKey.setBankNoOrder(2, TRUE);
        shelfKey.setBayNoOrder(3, TRUE);
        shelfKey.setLevelNoOrder(4, true);
        //<jp>*** Shelfインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Shelf instance. ***</en>
        Shelf[] array = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
        return array;
    }

    /**<jp>
     * 使用不可棚に設定されている棚を、使用可の状態に更新します。
     * @param conn データベース接続用のConnection
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Renew the status of the location, which is set unavailable, to 'available'.
     * @param conn :Connection to connect with database
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if the target data for update cannot be found in database.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    private void setAccessNgFlag(Connection conn) throws ReadWriteException, NotFoundException, InvalidDefineException
    {
        ToolShelfSearchKey shelfKey = new ToolShelfSearchKey() ;
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
        if (getToolShelfHandler(conn).count(shelfKey) > 0)
        {
            ToolShelfAlterKey shelfAlt = new ToolShelfAlterKey();
            shelfAlt.setAccessNgFlag(Shelf.ACCESS_NG);
            //<jp> アクセス可（使用可棚とする）</jp>
            //<en> accessible (set as available)</en>
            shelfAlt.updateAccessNgFlag(FALSE);    
            getToolShelfHandler(conn).modify(shelfAlt);
        }
    }
}
//end of class

