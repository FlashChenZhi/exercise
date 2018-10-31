// $Id: IndividuallyHardZoneCreater.java 87 2008-10-04 03:07:38Z admin $
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

import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
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
 * ハードゾーン設定（個別）を行なうクラスです。
 * AbstractCreaterを継承し、ハードゾーン設定（個別）に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>不要なメソッド削除<BR>
 * write、read、getSeparatedItem、getArray、isExistSameData、unsetZoneidLocation、getText
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>不要になったimportの削除</TD></TR>    
 * <TR><TD></TD><TD></TD><TD>setZoneidLocation コメント修正</TD></TR>
 * <TR><TD></TD><TD></TD><TD>createのいらないチェック、catch削除</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Hashtableに関する記述全部削除</TD></TR>
 * <TR><TD></TD><TD></TD><TD>checkに禁止棚のチェック追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class opreates the individual hard zone settings.
 * IT inherits the AbstractCreater, and implements the processes requried for 
 * the individual hard zone setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>Deleted unnecessary method.<BR>
 * write,read,getSeparatedItem,getArray,isExistSameData,unsetZoneidLocation,getText
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted unnecessary import.</TD></TR>    
 * <TR><TD></TD><TD></TD><TD>Corected the comment for setZoneidLocation.</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted unnecessary check in 'create' and catch.</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted all descrption concerning Hashtable.</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Added restricted location checking in 'check'.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class IndividuallyHardZoneCreater extends AbstractCreater
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
    public IndividuallyHardZoneCreater(Connection conn, int kind)
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
        Shelf[] array = getShelfArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);    
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        IndividuallyHardZoneParameter dispData = null;
        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new IndividuallyHardZoneParameter();
                dispData.setZoneID(array[i].getHardZoneId());
                dispData.setWarehouseNumber(getFindUtil(conn).getWarehouseNumber(array[i].getWarehouseStationNo()));
                dispData.setBank(array[i].getBankNo());
                dispData.setBay(array[i].getBayNo());
                dispData.setLevel(array[i].getLevelNo());

                vec.addElement(dispData);
            }
            IndividuallyHardZoneParameter[] fmt = new IndividuallyHardZoneParameter[vec.size()];
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
     * ＜修正数＞<BR>
     * ・再入庫数以下でなくてはなりません
     * ・基準積み付け数以下でなくてはなりません
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
     * < number used in modification ><BR>
     *  -the number should be less than the restoring quantity.
     *  -the number should be less than standard load quantity.
     * @param conn Databse Connection Object
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        IndividuallyHardZoneParameter mParam 
            = (IndividuallyHardZoneParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        if (processingKind == M_CREATE)
        {
            //<jp> 倉庫表に登録されているかチェック</jp>
            //<en> Check if the data is registered in warehouse table.</en>
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();
            if (warehousehandle.count(warehosuekey) <= 0)
            {
                //<jp> 6123117 = 倉庫管理情報がありません。倉庫設定で登録してください</jp>
                //<en> 6123117 = There is no information of the warehouse. Please register in warehouse setting screen.</en>
                setMessage("6123117");
                return false;
            }
            //<jp> ハードゾーン表に登録されているかチェック</jp>
            //<en> Check if the data is registered in hard zone table.</en>
            ToolHardZoneHandler hardzonehandle = new ToolHardZoneHandler(conn) ;
            ToolHardZoneSearchKey hardzonekey = new ToolHardZoneSearchKey() ;
            if (hardzonehandle.count(hardzonekey) <= 0)
            {
                //<jp> 6123267 = ゾーン情報がありません。ゾーン設定（範囲）で登録してください</jp>
                //<en> 6123267 = There is no zone information. Please register in zone (range) setting.</en>
                setMessage("6123267");
                return false ;
            }
            //<jp>指定された棚が倉庫に存在するかを確認</jp>
            //<en>Check to see if specified shelf exists in the warehouse.</en>
            if (!getFindUtil(conn).isExistShelf(mParam.getWarehouseNumber(),
                                            mParam.getBank(), 
                                            mParam.getBay(), 
                                            mParam.getLevel()))
            {
                //<jp>6123037 指定された棚No.は倉庫内に存在しません</jp>
                //<en>6123037 Specified location no. does not exist in the warehouse.</en>
                setMessage("6123037");
                return false;
            }
/* 2003/12/11  INSERT  okamura  START */
            //<jp>指定された棚が使用不可棚に設定されているかを確認</jp>
            //<en>Check to see if the specified location is unusable.</en>
            ToolShelfSearchKey shelfkey = new ToolShelfSearchKey();
            shelfkey.setWarehouseStationNo(getFindUtil(conn).getWarehouseStationNumber(mParam.getWarehouseNumber()) );
            shelfkey.setBankNo(mParam.getBank());
            shelfkey.setBayNo(mParam.getBay());
            shelfkey.setLevelNo(mParam.getLevel());
            Shelf[] shelf = (Shelf[])getToolShelfHandler(conn).find(shelfkey);

            if (shelf[0].isAccessNgFlag())
            {
                //<jp>6123274 = 指定された棚No.は使用不可棚に設定されているため、設定できません</jp>
                //<en>6123274 = Since it is set as the use improper shelf, specified shelf No. cannot be set up.</en>
                setMessage("6123274");
                return false;
            }
/* 2003/12/11  INSERT  okamura  END */            
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
     * It checks the duplication of parameter, then returns true if there was no duplicated data or 
     * returns false if there were any duplication.
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
        IndividuallyHardZoneParameter mParam = (IndividuallyHardZoneParameter)param;
        //<jp>*** 本来checkで実装する処理ですが、入力ボタン押下時にのみ</jp>
        //<jp>*** このチェックを行いたいのでここに実装しています。</jp>
        //<en>*** This process is originally to be implemented by 'check', </en>
        //<en>*** however implemented here since checking is needed only when </en>
        //<en>*** the entere buttone was pressed.</en>
        
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
     * @param conn データベース接続用 Connection
     * @return <CODE>ToolShelfHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolShelfHandler</CODE> instance generated at the initialization of this class.
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
     * Retrieve the <CODE>ToolFindUtil</CODE> instance generated at the initialization of this class.
     * @param conn Databse Connection Object
     * @return <CODE>ToolFindUtil</CODE>
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
     * It returns true if the maintenance process succeeded, or false if it failed.
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
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn) throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            IndividuallyHardZoneParameter castparam = null;
            
            if (mArray.length > 0)
            {
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (IndividuallyHardZoneParameter)mArray[i];
                    //<jp> 新規入力されたデータ</jp>
                    //<en> data newly entered:</en>
                    int warehouseNumber = castparam.getWarehouseNumber();
                    int zoneid = castparam.getZoneID();
                    int bank = castparam.getBank();
                    int bay = castparam.getBay();
                    int level = castparam.getLevel();

/* 2003/12/11 DELETE okamura START */
//                    ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
//<jp>                    //倉庫ステーションNo</jp>
//<en>                    //warehouse station no.</en>
//                    String whstno = getFindUtil(conn).getWarehouseStationNo(warehouseNumber);
//                    shelfKey.setWarehouseStationNo(whstno);
//                    shelfKey.setBank(bank);
//                    shelfKey.setBay(bay);
//                    shelfKey.setLevel(level);
//                    Shelf[] shelf = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
//                    
//<jp>                    //該当する棚が無い場合</jp>
//<en>                    //If there is no corresponding location,</en>
//                    if(shelf==null || shelf.length == 0)
//                    {
//<jp>                        //6123037 = 指定された棚No.は倉庫内に存在しません</jp>
//<en>                        //6123037 = The location specified does not exist in warehouse.</en>
//                        setMessage("6123037");
//                        return false;
//                    }

/* 2003/12/11 DELETE okamura END */

                    //<jp>使用不可棚にする</jp>
                    //<en>Set the location unavailable.</en>
                    setZoneidLocation(conn, warehouseNumber,    bank, bay, level, zoneid);
                }
            }
            return true;
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
     * Implement the check in order to see that the identical data has been selected when 
     * chosing data from the list box to edit.
     * In warehouse setting, it checks whether/not the storage type of appending parameter exists in 
     * the entered data summary.
     * @param param  :the parameter which will be appended in this process
     * @param array  :entered data summary (pool)
     * @return       :return true if identical data exists.
     </en>*/
    private boolean isSameData(IndividuallyHardZoneParameter param, 
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
                IndividuallyHardZoneParameter castparam = (IndividuallyHardZoneParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgwarehousenumber = castparam.getWarehouseNumber();
                orgbank = castparam.getBank();
                orgbay = castparam.getBay();
                orglevel = castparam.getLevel();

                //<jp>同一棚Noの確認</jp>
                //<en>Check the identical lcoations</en>
                if (warehousenumber == orgwarehousenumber && bank == orgbank && bay == orgbay && level == orglevel)
                {
                    //<jp>6123033 = 既に入力されています。同一棚No.を入力することはできません</jp>
                    //<en>6123033 = The data is already entered. Cannot input the identical location no.</en>
                    setMessage("6123033");
                    return true;
                }
            }
        }
        return false;
    }
    
    /**<jp>
     * 指定されたロケーションのハードゾーンIDを更新します。
     * @param conn データベース接続用 Connection
     * @param warehousenumber  格納区分
     * @param bank   バンク
     * @param bay    ベイ
     * @param level  レベル
     * @param zoneid ゾーンID
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Set the shelf of the specified location 'unavailable'.
     * The location which is defined in StationType table will also be deleted at the same time.
     * @param conn Databse Connection Object
     * @param warehousenumber :storage type
     * @param bank  :bank
     * @param bay   :bay
     * @param level :level
     * @param zoneid :zone ID
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    private void setZoneidLocation(Connection conn, int warehousenumber, int bank, int bay, int level, int zoneid) throws ScheduleException
    {
        try
        {
            ToolShelfAlterKey alterkey = new ToolShelfAlterKey();
            //<jp>倉庫ステーションNo</jp>
            //<en>warehouse station no.</en>
            String whstno = getFindUtil(conn).getWarehouseStationNumber(warehousenumber);
            alterkey.setWHStationNo(whstno);
            alterkey.setBankNo(bank);
            alterkey.setBayNo(bay);
            alterkey.setLevelNo(level);
            alterkey.updateHardZone(zoneid);
            getToolShelfHandler(conn).modify(alterkey);
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (InvalidDefineException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * Shelfインスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <code>Shelf</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the Shelf instance.
     * @param conn Databse Connection Object
     * @return :the array of <code>Shelf</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Shelf[] getShelfArray(Connection conn) throws ReadWriteException
    {
        ToolShelfSearchKey shelfKey  = new ToolShelfSearchKey();
        shelfKey.setStationNoOrder(1, true);
        //<jp>*** shelfインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Shelf isntance ***</en>
        Shelf[] array = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
        return array;
    }
}
//end of class

