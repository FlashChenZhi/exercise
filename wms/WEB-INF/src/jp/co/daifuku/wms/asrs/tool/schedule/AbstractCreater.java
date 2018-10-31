// $Id: AbstractCreater.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;

/**<jp>
 * 環境設定処理を行なう抽象クラスです。
 * Createrインターフェースを実装し、このインターフェースの実装に必要な処理を実装します。
 * 共通メソッドはこのクラスに実装され、実際のメンテナンス処理など個別の振る舞いについては、
 * このクラスを継承したクラスによって実装されます。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the abstruct class which operates the environment setting process.
 * It implements the Creater interface and the processes requried to implement this interface itself.
 * The common method is implemented in this class, and individual behaviours such as actual maintenance
 * and other are implemented by the classes derived from this class.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public abstract class AbstractCreater implements Creater
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiters
     * his is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM ;
    
    
    /**<jp>
     * wParamVec内に保持可能なパラメータの最大件数を保持する。
     </jp>*/
    /**<en>
     * Preserve the max number of parameters which can be preserved in wParamVec.
     </en>*/
    private final int wInputMaxCount = 50;
    
    /**<jp>
     * メンテナンス処理実行時に発生した問題の詳細メッセージを格納する。
     </jp>*/
    /**<en>
     * Store the detail message for what occurred during the execution of maintenance process.
     </en>*/
    protected String wMessage = "";
    
    /**<jp>
     * パラメータ配列を保持する。
     </jp>*/
    /**<en>
     * Preserve the parameter array.
     </en>*/
    protected Vector wParamVec ;

    /**<jp>
     * 製番
     </jp>*/
    /**<en>
     * Product number
     </en>*/
    private String wProductionNumber = "";

    /**<jp>
     * 処理区分
     </jp>*/
    /**<en>
     * Process type
     </en>*/
    private int wProcessingKind ;

    /**<jp>
     * 修正中フラグ
     </jp>*/
    /**<en>
     * Update flag
     </en>*/
    protected int wUpdating = ScheduleInterface.NO_UPDATING;
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
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     * @param pno 製番
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :connection object with database
     * @param kind :process type
     * @param pno  :product number
     </en>*/
    public AbstractCreater(Connection conn, int kind, String pno)
    {
        wProcessingKind = kind;
        wProductionNumber = pno;
        wParamVec = new Vector(wInputMaxCount);
    }
    /**<jp>
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :Connection object with database
     * @param kind :process type
     </en>*/
    public AbstractCreater(Connection conn, int kind)
    {
        this(conn, kind, "");
    }


    // Public methods ------------------------------------------------
    /**<jp>
     * このインターフェースを実装したクラスが保持するパラメータの配列の個数を返します。
     * 修正中であればパラメータの個数-1を返します。
     * @return スケジュールパラメータの個数
     </jp>*/
    /**<en>
     * Return the number of parameter arrays that the class implemented with the interface preserves.
     * Or return -1 if data is being updated. 
     * @return :number of schedule parameters
     </en>*/
    public int getParameterCount()
    {
        return getParameters().length;
    }
    /**<jp>
     * メンテナンス処理を開始します。
     * 実際のメンテナンス処理は<code>doStart()</code>で実装します。
     * メンテナンス処理の前にパラメータのチェックを行うことを確実とするためにこのメソッドを実装しています。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Start the maintenance process. 
     * Actual processing of maintenance will be implemented by <code>doStart()</code>.
     * This method is implemented in order to ensure htat parameters should be checked prior to
     * the maintenance process.
     * Return true if maintenance process succeeded, or false if failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public boolean startMaintenance(Connection conn) throws ReadWriteException, ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        try
        {
            //<jp>パラメータチェック</jp>
            //<en>Check the prarmaters.</en>
            Parameter[] paramarray = (Parameter[])getParameters(); 
            for (int i = 0 ; i < paramarray.length ; i++)
            {
                if (!check(conn, (Parameter)paramarray[i]))
                {
                    return false;
                }
            }
            //<jp>メンテナンス処理実行</jp>
            //<en>Execute the maintenance process.</en>
            if (doStart(conn))
            {
                return true;
            }
            return false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
    }
    
    /**<jp>
     * 指定されたパラメータを追加します。このメソッドは、ため打ちに初期表示を行うときに使用します。<BR>
     * 内部でのチェック処理は行わずに、そのままパラメータの追加を行います。<BR>
     * @param param 追加するパラメータの内容
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Append the specified parameter. This method will be used for initial display in input data sumary.<BR>
     * It will not process internal checks, only appends the parameters.<BR>
     * @param param :contents of parameter to append
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the check process.
     * @return :returns true if Append the specified parameter of the parameter succeeded or returns false if it failed.
     </en>*/
    public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        //<jp>パラメータの補完処理を行います</jp>
        //<en>Complement the parameters.</en>
        Parameter cParam = complementParameter(param);
        if (cParam == null)
        {
            return false;
        }
        wParamVec.add(cParam);
        return true;
    }

    
    /**<jp>
     * 指定されたパラメータを追加します。<BR>
     * パラメータの追加に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
     * パラメータの追加に失敗した場合、その理由を<code>getMessage</code>で取得できます。<BR>
     * @param conn データベース接続用 Connection
     * @param param 追加するパラメータの内容
     * @return パラメータの追加に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Append the specified parameters.<BR>
     * It returns true if the parameters are appended successfully, or returns false if it failed.<BR>
     * If this process failed, its reason can be obtained by <code>getMessage</code>.<BR>
     * @param conn Databse Connection Object
     * @param param :contents of parameters to add
     * @return :returns true if the process succeeded or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the check process.
     </en>*/
    public boolean addParameter(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        //<jp>追加時のチェック</jp>
        //<en>Check when appending the parameter.</en>
        if (addCheck(conn, param))
        {
            //<jp>パラメータの補完処理を行います</jp>
            //<en>Complement the parameters.</en>
            Parameter cParam = complementParameter(param);
            if (cParam == null)
            {
                return false;
            }
            wParamVec.add(cParam);
            return true;
        }
        
        return false;
    }

    /**<jp>
     * 指定された位置のパラメータを置き換えます。<BR>
     * パラメータの修正に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
     * パラメータの修正に失敗した場合、その理由を<code>getMessage</code>で取得できます。<BR>
     * @param conn データベース接続用 Connection
     * @param index 修正するパラメータ位置
     * @param param 修正するパラメータの内容
     * @return パラメータの修正に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Replace the parameter of the specified position.<BR>
     * It returns true if modification of the parameter succeeded or returns false if it failed.<BR>
     * If the modification of the parameter failed, its reason can be obtained by <code>getMessage</code>.<BR>
     * @param conn Databse Connection Object
     * @param index :position of the parameter to modify
     * @param param :contents of the parameter to modify
     * @return :returns true if modification of the parameter succeeded or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check process.
     * @throws ScheduleException  :Notifies if there is no parameter in specified position.
     </en>*/
    public boolean changeParameter(Connection conn, int index, Parameter param) throws ReadWriteException, ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        Parameter cParam = complementParameter(param);

        if (addCheck(conn, cParam))
        {
            try
            {
                wParamVec.set(index, cParam);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                throw new ScheduleException(e.getMessage());
            }
            return true;
        }
        return false;
    }

    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param conn データベース接続用 Connection
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of specified position.<BR>
     * @param conn Databse Connection Object
     * @param index :position of the parameter to delete
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
     * @param conn データベース接続用 Connection
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all parameters.<BR>
     * @param conn Databse Connection Object
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public void removeAllParameters(Connection conn) throws ScheduleException
    {
        wParamVec.removeAllElements();
        setMessage("6121003");
    }

    /**<jp>
     * このインターフェースを実装したクラスが保持するパラメータの配列を返します。
     * メンテナンス処理で返すべきパラメータは処理により異なるので、このメソッドの
     * 実装は、各処理クラスで行います。
     * @return パラメータの配列
     </jp>*/
    /**<en>
     * Returns the parameter arrays that the class, inplmented with this interface, preserves.
     * As parameters to return during the maintenance process differ depending on the process,
     * this method will be implemented by each processing class.
     * @return :parameter array
     </en>*/
    public  Parameter[] getParameters()
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        if  (getUpdatingFlag() == ScheduleInterface.NO_UPDATING)
        {

            Parameter[] params = new Parameter[wParamVec.size()];
            wParamVec.copyInto(params);
            return params;
        }
        else
        {
            Parameter[] params = new Parameter[wParamVec.size()];
            wParamVec.copyInto(params);

            Parameter[] rparams = new Parameter[params.length - 1];
            for (int i = 0, ins = 0 ; ins < rparams.length ; i++)
            {
                if (i != getUpdatingFlag())
                {
                    rparams[ins] = params[i];
                    ins++;
                }
            }
            return rparams;
        }

    }

/* 2003.05.29 tahara change start */
    /**<jp>
     * このインターフェースを実装したクラスが保持するパラメータの配列を返します。
     * メンテナンス処理で返すべきパラメータは処理により異なるので、このメソッドの
     * 実装は、各処理クラスで行います。
     * 修正中データも返します。
     * @return パラメータの配列
     </jp>*/
    /**<en>
     * Returns the parameter arrays that the class, inplmented with this interface, preserves.
     * As parameters to return during the maintenance process differ depending on the process,
     * this method will be implemented by each processing class.
     * Also return the updating data.
     * @return :parameter array
     </en>*/
    public  Parameter[] getAllParameters()
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");

        Parameter[] params = new Parameter[wParamVec.size()];
        wParamVec.copyInto(params);
        return params;
    }
/* 2003.05.29 tahara change end */
    
    /**<jp>
     * 現在修正対象となっている入力済みパラメータを返します。
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return teh entered parameter currently is the target of modification.
     * @return :arry of the schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check process.
     </en>*/
    public Parameter getUpdatingParameter() throws ReadWriteException, ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        if (getUpdatingFlag() == ScheduleInterface.NO_UPDATING)
        {
            //<jp> 修正中ではありません</jp>
            //<en> The data is not in update process.</en>
            wMessage = "9999999";
            return null;
        }
        Parameter[] params = new Parameter[wParamVec.size()];
        wParamVec.copyInto(params);
        return params[getUpdatingFlag()];
    }
    /**<jp>
     * パラメータチェック処理を行ないます。メンテナンス処理を実行する前に呼ばれます。
     * パラメータチェックはメンテナンスの種類によって異なるため、このメソッドは<code>AbstractCreater</code>
     * クラスを継承したクラスにより実装されます。パラメータチェックに成功した場合はtrue、
     * 失敗した場合はfalseを返します。
     * パラメータチェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param チェックするパラメータの内容
     * @return パラメータチェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the paramter check. It will be called before the execution of maintenance process.
     * As parameter check differs depending on the type of maintenance, this method will be implemented 
     * by the class derived from <code>AbstractCreater</code> class.
     * It returns true if the parameter check succeeded or returns false if it failed.
     * If parameter check failed, its reason can be obtained by <code>getMessage</code>.
     * @param param :content of parameter to check
     * @return :returns true if the parameter check succeeded or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check process.
     </en>*/
    public abstract boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;

    /**<jp>
     * パラメータの重複チェック処理を行ないます。
     * パラメータ重複チェックはメンテナンスの種類によって異なるため、このメソッドは<code>AbstractCreater</code>
     * クラスを継承したクラスにより実装されます。
     * 通常、２種類のチェック処理が必要になると考えられます。<BR>
     * １．画面的に同一なデータのチェック（リストボックスから同一データが選択されたことのチェック）<BR>
     * ２．ため打ちデータとして同一なデータのチェック(DBのチェック含む)<BR>
     * 画面的に同一なデータのチェックはisSameData()というメソッド名で定義します。
     * ため打ちデータ、すでにDBへ登録されているデータとの重複チェックは各処理の内容に合わせてメソッドを作成します。
     * 通常、登録の場合は、２のチェックのみ行います。修正の場合は１，２のチェックを行い、
     * 削除の場合は１のチェックのみを行います。
     * 実装方法については、以下の例を参考にしてください。
     *    <pre>
     *    public boolean duplicationCheck(Parameter param) throws ReadWriteException, ScheduleException
     *     {
     *        ZoneMaintenanceParameter[] mArray = (ZoneMaintenanceParameter[])getParameters();
     *        ZoneMaintenanceParameter mParam = (ZoneMaintenanceParameter)param;
     *
     *        //登録処理の場合
     *        if(getProcessingKind() == M_CREATE)
     *        {
     *            //ゾーン範囲チェック
     *            if(isSameZoneRange(mParam, mArray))
     *            {
     *                return false;
     *            }
     *            return true;
     *        }
     *        //修正処理の場合
     *        else if(getProcessingKind() == M_MODIFY)
     *        {
     *            //同一データチェック
     *            if(isSameData(mParam, mArray))
     *            {
     *                return false;
     *            }
     *            //ゾーン範囲チェック
     *            if(isSameZoneRange(mParam, mArray))
     *            {
     *                return false;
     *            }
     *             return true;
     *        }
     *        //削除処理の場合
     *        else
     *        {
     *            //同一データチェック
     *            if(isSameData(mParam, mArray))
     *            {
     *                return false;
     *            }
     *            return true;
     *        }
     *    }
     *    </pre>
     * パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param チェックするパラメータの内容
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the parameter duplicate check.
     * As parameter duplicate check differs depending on the maintenance type, this method will be 
     * implemented by the class which inherited <code>AbstractCreater</code> class.
     * In ordinaly process, there are 2 different checks are considered to be necessary.<BR>
     * 1: Check of identical data on the display aspect.(check whether/not the identical data were selected
     *    from the listbox.<BR>
     * 2: Check the identical data in entered data summary (including the DB checks)<BR>
     * Check of identical data on the display aspect will be defined by a method called isSameData().
     * For the duplicate data check of entered data summary ane the DB data which have been already entered,
     * mew methods will be created in accordance with the contents of each process.
     * Normally the only #2 will be checked for data registrations. If data is modified, #1 and #2 will 
     * be checked. In case of data deletion, only #1 will be checked.
     * Please refer to the example below for the implementation method.
     *    <pre>
     *    public boolean duplicationCheck(Parameter param) throws ReadWriteException, ScheduleException
     *     {
     *        ZoneMaintenanceParameter[] mArray = (ZoneMaintenanceParameter[])getParameters();
     *        ZoneMaintenanceParameter mParam = (ZoneMaintenanceParameter)param;
     *
     *        //In case of data registration,
     *        if(getProcessingKind() == M_CREATE)
     *        {
     *            //Check the zone range:
     *            if(isSameZoneRange(mParam, mArray))
     *            {
     *                return false;
     *            }
     *            return true;
     *        }
     *        //In case of data modification,
     *        else if(getProcessingKind() == M_MODIFY)
     *        {
     *            //Check the identical data:
     *            if(isSameData(mParam, mArray))
     *            {
     *                return false;
     *            }
     *            //Check the range of the zones:
     *            if(isSameZoneRange(mParam, mArray))
     *            {
     *                return false;
     *            }
     *             return true;
     *        }
     *        //In case of data deletion:
     *        else
     *        {
     *            //Check the identical data:
     *            if(isSameData(mParam, mArray))
     *            {
     *                return false;
     *            }
     *            return true;
     *        }
     *    }
     *    </pre>
     * It returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param param :contens of parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public abstract boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;


    /**<jp>
     * メンテナンス処理中に発生した内容についてのメッセージを取得します。
     * @return メッセージ
     </jp>*/
    /**<en>
     * Retrieve the message for what occurred during the maintenance processing.
     * @return :message
     </en>*/
    public String getMessage()
    {
        return wMessage;
    }

    /**<jp>
     * 修正中フラグをセットします。
     * @param flag 修正中フラグ
     </jp>*/
    /**<en>
     * Set the update flag.
     * @param flag :update flag
     </en>*/
    public void setUpdatingFlag(int flag)
    {
        wUpdating = flag;
    }
    /**<jp>
     * 修正中フラグを取得します
     * @return 修正中フラグ
     </jp>*/
    /**<en>
     * Retrieve the update flag.
     * @return :update flag
     </en>*/
    public int getUpdatingFlag()
    {
        return wUpdating;
    }
    /**<jp>
     * メンテナンス処理を実装します。
     * メンテナンス処理はメンテナンスの種類によって異なるため、このメソッドは<code>AbstractCreater</code>クラスを継承した
     * クラスにより実装されます。
     * メンテナンス処理の種類は処理区分（getProcessingKind()メソッドより取得）により内部で判断する必要があります。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Implement the maintenance process.
     * As maintenance process differs depending on the maintenance type, this method will be 
     * implemented by the class which inherited <code>AbstractMaintenance</code> class.
     * It is necessary that type of the maintentace should be determined internally according to
     * the process type (obtained by getProcessingKind() method.)
     * Return true if maintenance process succeeded, or false if failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :true if process succeeded, or false if failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public abstract boolean doStart(Connection conn) throws ReadWriteException, ScheduleException;
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * ため打ちデータの入力済み件数のチェックを行ないます。
     * このメソッドはAWCリソースパラメータファイルに定義されている(DISPLAY_INPUT_MAX_COUNT）の値と
     * 現在このインスタンスが<code>wParamVec</code>に保持している入力済みデータの件数を比較します。
     * 入力済みデータがDISPLAY_INPUT_MAX_COUNTで規定されている値より小さい値であればtrueを返します。
     * 入力済みデータがDISPLAY_INPUT_MAX_COUNTで規定されている値以上であればfalseを返します。
     * @return 入力済みデータがDISPLAY_INPUT_MAX_COUNTで規定されている値より小さい値であればtrueを返します。
     *         DISPLAY_INPUT_MAX_COUNT以上であればfalseを返します。
     </jp>*/
    /**<en>
     * Check the number of entered data in entered data summary.
     * This method compares the value of (DISPLAY_INPUT_MAX_COUNT) defined in the AWC resource
     * parameter file with the number of entered data that this instance currently preserves
     * in the <code>wParamVec</code>.
     * If number of entered data is lower than the value set by DISPLAY_INPUT_MAX_COUNT, it will 
     * return true.
     * And if number of entered data is higher than the valud set by DISPLAY_INPUT_MAX_COUNT, 
     * it will return false.
     * @return :true if the number of entered data is lower than the value set by 
     * DISPLAY_INPUT_MAX_COUNT, or return false if higher than DISPLAY_INPUT_MAX_COUNT.
     </en>*/
    protected boolean checkInputMaxCount()
    {
        if (getParameterCount() >= wInputMaxCount)
        {
            //<jp> 入力済みデータの件数が最大入力可能件数を超えている場合はfalseを返す。</jp>
            //<en> Return false if the number of entered data exceeded the max number of enterd data acceptable.</en>
            setMessage("6123276");
            return false;
        }
        return true;
    }
    
    
    /**<jp>
     * 製番を取得します。
     * @return 製番
     </jp>*/
    /**<en>
     * Retrieve the product no.
     * @return :product no.
     </en>*/
    protected String getProductionNumber()
    {
        return wProductionNumber;
    }
    /**<jp>
     * 製番をセットします。
     * @param pno 製番
     </jp>*/
    /**<en>
     * Set the product no.
     * @param pno product no.
     </en>*/
    protected void setProductionNumber(String pno)
    {
        wProductionNumber = pno;
    }
    
    /**<jp>
     * 処理区分を取得します。
     * @return 処理区分
     </jp>*/
    /**<en>
     * Retrieve the process type.
     * @return :process type
     </en>*/
    protected int getProcessingKind()
    {
        return wProcessingKind;
    }
    /**<jp>
     * 処理区分をセットします。
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Set the process type.
     * @param kind process type
     </en>*/
    protected void setProcessingKind(int kind)
    {
        wProcessingKind = kind;
    }
    /**<jp>
     * パラメータの補完処理を行います。
     * 他端末で変更されたかチェックするためのインスタンスをパラメータにセットしたり
     * 品名コードを元に品名をセットする等の処理を実装します。
     * この処理はメンテナンスの種類によって異なるため、<code>AbstractCreater</code>
     * クラスを継承したクラスにより実装されます。処理に成功した場合は補完したパラメータ。
     * 失敗した場合はnullを返すように実装してください。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param インスタンスをセットするパラメータ
     * @return 補完したパラメータを返します。失敗した場合はnullを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Complement the parameters.
     * It implements the process such as setting the instance as a aprameter in order to check 
     * if data has been modified by other terminal, or setting the article name based on the item code.
     * As this process differs depending on the maintenance type, this method will be implemented by
     * the class which inherited <code>AbstractCreater</code> class.
     * Please implement accordingly so that the complemented parameter to be returned if the process
     * succeeded, and null to be retruned if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param :parameter to set the instance
     * @return :return the complemented parameters. Or return null if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
     </en>*/
    protected abstract Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException;

    /**<jp>
     * パラメータチェック処理を行ないます。このメソッドはパラメータを追加する処理で呼ばれます。
     * パラメータチェックはメンテナンスの種類によって異なるため、このメソッドは<code>AbstractCreater</code>
     * クラスを継承したクラスにより実装されます。パラメータチェックに成功した場合はtrue、
     * 失敗した場合はfalseを返します。
     * パラメータチェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @param param チェックするパラメータの内容
     * @return パラメータチェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter check. This method will be called when appending the parameters.
     * As parameter check differs depending on the type of maintenance, this method will be 
     * implemented  by the class derived from <code>AbstractCreater</code> class.
     * It returns true if the parameter check succeeded or returns false if it failed.
     * If parameter check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @param param :parameter contents to check
     * @return :returns true if the parameter check succeeded or returns false if it failed.e.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check. 
     </en>*/
    protected boolean addCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
    {
        //<jp>重複チェックを行う</jp>
        //<en>Check the duplicate data.</en>
        if (duplicationCheck(conn, param))
        {
            //<jp>チェックを行う</jp>
            //<en>Carry out the checks.</en>
            if (check(conn, param))
            {
                return true;
            }
        }
        return false;
    }

    /**<jp>
     * 指定されたメッセージをメッセージ格納エリアにセットします。
     * @param msg メッセージ
     </jp>*/
    /**<en>
     * Set the specified message in the message storage area.
     * @param msg :message
     </en>*/
    protected void setMessage(String msg)
    {
        wMessage = msg;
    }

    // Private methods -----------------------------------------------
}
//end of class

