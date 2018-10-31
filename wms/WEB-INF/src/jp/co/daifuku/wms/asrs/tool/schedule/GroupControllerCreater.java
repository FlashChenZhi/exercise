// $Id: GroupControllerCreater.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.GroupControllerInformation;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;

/**<jp>
 * グループコントローラーを行なうクラスです。
 * AbstractCreaterを継承し、グループコントローラーに必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class operates the group controller.
 * It inherits the AbstractCreater, and implements the processes requried for group controller.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class GroupControllerCreater extends AbstractCreater
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------
    
    /**<jp> 状態（オフライン） </jp>*/
    /**<en> Status (off-line) </en>*/
    private int DEFAULTSTATUS = 2;
    
    /**<jp> ポート番号 </jp>*/
    /**<en> Port no.  </en>*/
    private int DEFAULTPORT = 2000;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Retunrs the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete parameter of the specified position.<BR>
     * @param conn :Connection to connect with database
     * @param index :position of the deleting parameter
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
     * Delete all parameters.<BR>
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
     * このクラスの初期化を行ないます。初期化時に<CODE>ReStoringHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public GroupControllerCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param locale オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Implement the process to run when the print-issue button was pressed on the display.<BR>
     * @param conn :connection object with database
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
     * @param conn データベースとのコネクションオブジェクト
     * @param locale オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn :connection object with database
     * @param locale object
     * @param searchParam :search conditions
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
    {
        GroupControllerInformation[] array = getGroupControllerArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>    
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        GroupControllerParameter dispData = null;
            if (array.length > 0)
            {
                for (int i = 0; i < array.length; i++)
                {
                    dispData = new GroupControllerParameter();
                    dispData.setControllerNumber(array[i].getControllerNo());
                    dispData.setIPAddress(array[i].getIPAddress());
                    vec.addElement(dispData);
                }
                GroupControllerParameter[] fmt = new GroupControllerParameter[vec.size()];
                vec.toArray(fmt);
                return fmt;
            }
            return null;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * ＜修正数＞<BR>
     * ・再入庫数以下でなくてはなりません
     * ・基準積み付け数以下でなくてはなりません
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータ
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the paramter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * <number for the modification><BR>
     *  -the number should be less than restorage quantity.
     *  -the number should be less than standard load quantity.
     * @param conn :connection object with database
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        GroupControllerParameter mParameter 
            = (GroupControllerParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        if (processingKind == M_CREATE)
        {
            //<jp>AGCNoのチェック</jp>
            //<en>Check the AGCNo.</en>
            if (!check.checkAgcNo(mParameter.getControllerNumber()))
            {
                //<jp>異常内容をセットする</jp>
                //<en>Set the contents of the error.</en>
                setMessage(check.getMessage());
                return false;
            }
            //<jp>ホスト名称のチェック</jp>
            //<en>Check teh host name.</en>
            if (!check.checkHostName(mParameter.getIPAddress()))
            {
                //<jp>異常内容をセットする</jp>
                //<en>Set the contents of the error.</en>
                setMessage(check.getMessage());
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
     * @param conn データベースとのコネクションオブジェクト
     * @param logpath 異常が発生したときのログを書き込むファイルを置くためのパス
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file.
     * @param conn :connection object with database
     * @param logpath :path to place the file in which the log will be written when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException
    {
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/" + ToolParam.getParam("CONSTRAINT_CHECK_FILE");
        
        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            ToolGroupControllerSearchKey gcKey = new ToolGroupControllerSearchKey();
            GroupControllerInformation[] gcArray = (GroupControllerInformation[])getToolGroupControllerHandler(conn).find(gcKey);

            //<jp>GroupControllerが設定されていない場合</jp>
            //<en>If GroupController has not been set,</en>
            if (gcArray.length == 0)
            {
                //<jp>6123180 = グループコントローラが設定されていません</jp>
                //<en>6123180 = The group controller has not been set.</en>
                loghandle.write("GroupController", "GroupController Table", "6123180");
                return false;
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
      * 同一品名コード、ロットナンバーのデータはため打ちデータに登録できません。
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータ
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.
      * Data of identical item code and lot no. cannot be registered in entered data summary.
     * It checks the duplication of parameter, then returns true if there was no duplicated data 
     * or returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :connection object with database
     * @param param :parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        Parameter[] mArray = (Parameter[])getParameters();
        GroupControllerParameter mParam = (GroupControllerParameter)param;
        //<jp>同一データチェック</jp>
        //<en>Check the identical data.</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }
        
        //<jp>修正の時のみチェックする。</jp>
        //<en>Check only when modifing data.</en>
        if (getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
        {
            //<jp>*** 修正の時、キー項目は変更不可とする ***</jp>
            //<en>*** Key items are not to be modifiable when modifing data. ***</en>
            int gcNo = mParam.getControllerNumber();
            
            //<jp>ため打ちの中のキー項目</jp>
            //<en>Key items for enterd data summary</en>
            int orgGcNo = 0;
            
            Parameter[] mAllArray = (Parameter[])getAllParameters();
            for (int i = 0 ; i < mAllArray.length ; i++)
            {
                GroupControllerParameter castparam = (GroupControllerParameter)mAllArray[i];
                //<jp>キー項目</jp>
                //<en>Key items</en>
                orgGcNo = castparam.getControllerNumber(); 
                //<jp>変更されていないのでOK</jp>
                //<en>Acceptable as the data is not modified.</en>
                if (gcNo == orgGcNo)
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
     * @param conn データベースとのコネクションオブジェクト
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
     * @param conn :connection object with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException :Notifies if unexpected error occurred during the maintenance process.
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
     * このクラスの初期化時に生成した<CODE>ReStoringHandler</CODE>インスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <CODE>ReStoringHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
     * @param conn :connection object with database
     * @return <CODE>ReStoringHandler</CODE>
     </en>*/
    protected ToolGroupControllerHandler getToolGroupControllerHandler(Connection conn)
    {
        return new ToolGroupControllerHandler(conn);
    }
    
    /**<jp>
     * このクラスの初期化時に生成した<CODE>FindUtil</CODE>インスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <CODE>FindUtil</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
     * @param conn :connection object with database
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
     *  - Append ReStoring instance to the parameter in ordder to check whether/not the data 
     *    has been modified by other terminals.
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
     * @param conn データベースとのコネクションオブジェクト
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations. The scheduled restorage data is not registered in this process.
     * Returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :connection object with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn) throws ReadWriteException, ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            GroupControllerParameter castparam = null;
            if (mArray.length > 0)
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                getToolGroupControllerHandler(conn).truncate();
                GroupControllerInformation gcinfo = new GroupControllerInformation();
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (GroupControllerParameter)mArray[i];
                    gcinfo.setControllerNo(castparam.getControllerNumber());
                    gcinfo.setStatusFlag(DEFAULTSTATUS);
                    gcinfo.setIPAddress(castparam.getIPAddress());
                    gcinfo.setPort(DEFAULTPORT);
                    getToolGroupControllerHandler(conn).create(gcinfo);
                }
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                getToolGroupControllerHandler(conn).truncate();
                return true;
            }
        }
        catch (DataExistsException e)
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
     * Set the process type of selected item to delete to 'processed'. The acrual deletion will 
     * be done in daily transactions.
     * Returns true if the maintenance process succeeded, or false if it failed.
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
     * 確認するためのチェックを実装します。グループコントローラーでは、
     * 追加するパラメータの格納区分がため打ちデータ内に存在するかを確認します。
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing data
     * from the list box to edit.
     * In the group contrller, it checks whether/not the storage type of the appending parameter
     * exists in the entered data summary.
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :return true if identical data exists.
     </en>*/
    private boolean isSameData(GroupControllerParameter param, 
                                Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int newAgcNo = 99999; 
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int orgAgcNo = 99999; 
        
        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加する格納区分で比較する</jp>
            //<en>Compare by the storage type appended in this process.</en>
            newAgcNo = param.getControllerNumber();
            
            for (int i = 0 ; i < array.length ; i++)
            {
                GroupControllerParameter castparam = (GroupControllerParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgAgcNo = castparam.getControllerNumber();
                //<jp>同一AGCNo.の確認</jp>
                //<en>Check the identical AGCNo.</en>
                if (newAgcNo == orgAgcNo)
                {
                    //<jp>6123046 = 既に入力されています。同一AGCNo.を入力することはできません</jp>
                    //<en>6123046 = The data is already entered. Cannot input the identical AGC no.</en>
                    setMessage("6123046");
                    return true;
                }
            }
        }
        
        return false;
    }

    /**<jp>
     * GroupControllerインスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <code>GroupController</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the GroupController instance.
     * @param conn :connection object with database
     * @return :the array of <code>GroupController</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private GroupControllerInformation[] getGroupControllerArray(Connection conn) throws ReadWriteException
    {
        ToolGroupControllerSearchKey groupControllerKey  = new ToolGroupControllerSearchKey();
        groupControllerKey.setControllerNoOrder(1, true);
        
        //<jp>*** GroupControllerインスタンスを取得 ***</jp>
        //<en>*** Retrieve the GroupController instance ***</en>
        GroupControllerInformation[] array = (GroupControllerInformation[])getToolGroupControllerHandler(conn).find(groupControllerKey);
        return array;
    }
}
//end of class

