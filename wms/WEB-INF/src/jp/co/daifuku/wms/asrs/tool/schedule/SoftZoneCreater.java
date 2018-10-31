// $Id: SoftZoneCreater.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

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
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.SoftZone;

/**<jp>
 * ゾーンのメンテナンス処理を行なうクラスです。
 * AbstractCreaterを継承し、ゾーンの設定処理に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class processes the zone maintenance.
 * It inherits the AbstractCreater, and implements the processes required for zone setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class SoftZoneCreater
        extends AbstractCreater
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * <CODE>ToolSoftZoneSearchKey</CODE>インスタンス
     </jp>*/
    /**<en>
     * <CODE>ToolSoftZoneSearchKey</CODE> instance
     </en>*/
    protected ToolSoftZoneSearchKey wZoneKey = null;

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
     * 指定された位置のパラメータを削除します。<BR>
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position. <BR>
     * @param index :position of the deleting parameter
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(int index)
            throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the messages</en>
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
     * @param conn :Connection to connect with database
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
     * @param <code>Locale</code> オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn :Connection to connect with database
     * @param locale object
     * @param searchParam :search conditions
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam)
            throws ReadWriteException,
                ScheduleException
    {
        SoftZone[] array = getSoftZoneArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        SoftZoneParameter dispData = null;
        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new SoftZoneParameter();
                dispData.setZoneID(array[i].getSoftZoneID());
                dispData.setZoneName(array[i].getSoftZoneName());
                vec.addElement(dispData);
            }
            SoftZoneParameter[] fmt = new SoftZoneParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null;
    }

    /**<jp>
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :connetion object with database
     * @param kind :process type
     </en>*/
    public SoftZoneCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * パラメータの補完処理を行います。<BR>
     * ・倉庫名称が空白の場合は、名称をセットする。<BR>
     * ・修正、削除処理の場合、他の端末で更新されたかチェックするためZoneインスタンス
     * をパラメータに追加する。<BR>
     * 処理にに成功した場合は補完したパラメータ。失敗した場合はnullを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param インスタンスをセットするパラメータ
     * @return 処理に成功した場合はパラメータ。失敗した場合はnullを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conduct the complementarity process of parameter.<BR>
     *  -If the warehouse name is blank, set the name.<BR>
     *  -Append Zone instance to the parameter in order to check whether/not the data
     *   has been modified by other terminals.<BR>
     * It returns the complemented parameter if the process succeeded, or returns false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param  :the parameter to set the instance
     * @return :returns the parameter if the process succeeded, ro returns null if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
     </en>*/
    protected Parameter complementParameter(Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        SoftZoneParameter mParameter = (SoftZoneParameter)param;

        return mParameter;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * ＜ゾーン名称＞<BR>
     * ・禁止文字<BR>
     * ＜ゾーン範囲＞<BR>
     * ・開始棚ベイ＜＝終了棚ベイ<BR>
     * ・開始棚レベル＜＝終了棚レベル<BR>
     * ・ゾーン範囲が指定倉庫の範囲内にあること<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータの内容
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the paramter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * <Zone name><BR>
     *  -Unacceptable letters and symbols<BR>
     * <Zone range><BR>
     *  -Startinf location bay <= ending location bay<BR>
     *  -Starting location level <= ending location level<BR>
     *  -Zone range should be contained within teh s@pecified warehouse range.<BR>
     * @param conn :Connection to connect with database
     * @param param : contents of the parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        SoftZoneParameter mParameter = (SoftZoneParameter)param;

        //<jp>登録処理の場合</jp>
        //<en>In case of registration,</en>
        if (processingKind == M_CREATE)
        {
            //<jp>ゾーン名称</jp>
            //<en>Name of the zone</en>
            if (!check.checkZoneName(mParameter.getZoneName()))
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
            //<jp> 6127006 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6127006 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6127006");
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
     * @param conn :Connection to connect with database
     * @param logpath :path to place the file in which the log will be written when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale)
            throws ScheduleException
    {
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/" + ToolParam.getParam("CONSTRAINT_CHECK_FILE");

        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
            ToolShelfHandler shelfHandle = new ToolShelfHandler(conn);

            //<jp>SHELF表にソフトゾーンが設定されていない場合</jp>
            if (shelfHandle.countNotZeroSoftZone(shelfKey) == 0)
            {
                return true;
            }

            ToolSoftZoneSearchKey zoneKey = new ToolSoftZoneSearchKey();
            SoftZone[] znArray = (SoftZone[])getToolSoftZoneHandler(conn).find(zoneKey);

            //<jp>SoftZoneが設定されていない場合</jp>
            //<en>If the SoftZone has not been set,</en>
            if (znArray.length == 0)
            {
                //<jp>6123258=ソフトゾーンが設定されていません。</jp>
                //<en>6123258=The soft zone has not been set.</en>
                loghandle.write("SoftZone", "SoftZone Table", "6123258");
                //<jp>ソフトゾーンが設定されていない場合は、後のチェックを行わずに抜ける</jp>
                //<en>If the soft zone has not been set, discontinue the checks and exit.</en>
                return false;
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
     * パラメータの重複チェック処理を行ないます。
     *＜登録処理の場合＞<BR>
     *・同一ゾーン範囲の設定が、ため打ちデータ内とZONE表に無いことを確認する。<BR>
     *＜修正処理の場合＞<BR>
     *・リストボックスより同一データが選択されていないことを確認する。<BR>
     *・同一ゾーン範囲の設定が、ため打ちデータ内とZONE表に無いことを確認する。<BR>
     *＜削除処理の場合＞<BR>
     *・リストボックスより同一データが選択されていないことを確認する。<BR>
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータの内容
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.
     * < Registration ><BR>
     * -Check to see that the setting of the identical zone range does not exist in entered data summary or in ZONE table.<BR>
     * < Modification ><BR>
     * -Check to see that identical data has not been selected from the listbox.<BR>
     * -Check to see that the setting of the identical zone range does not exist in entered data summary or in ZONE table.<BR>
     * < Deletion ><BR>
     * -Check to see that identical data has not been selected from the listbox.<BR>
     * It checks the duplication of parameter, then returns true if there was no duplicated data or 
     * returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :Connection to connect with database
     * @param param :contents of the parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        Parameter[] mArray = (Parameter[])getParameters();
        SoftZoneParameter mParam = (SoftZoneParameter)param;

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
     * @param conn :Connection to connect with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public boolean doStart(Connection conn)
            throws ReadWriteException,
                ScheduleException
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
            //<jp> 6127006 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6127006 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ZoneHandler</CODE>インスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <CODE>ZoneHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ZoneHandler</CODE> instance generated at the initialization of this class.
     * @param conn :Connection to connect with database
     * @return <CODE>ZoneHandler</CODE>
     </en>*/
    protected ToolSoftZoneHandler getToolSoftZoneHandler(Connection conn)
    {
        return new ToolSoftZoneHandler(conn);
    }

    /**<jp>
     * メンテナンス修正処理を行います。
     * パラメータ配列のキーとなる項目を元に変更処理を行います。
     * この実装ではZONEINFOの修正は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Modification of ZONEINFO will not be done here.
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
     * 新規登録のゾーンID登録の場合はZONEINFO表とZONE表に
     * 追加し、追加登録の場合はZONE表にのみ登録を行います。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations.
     * If registering a new zone ID, append the ID data in ZONEINFO table and ZONE table. 
     * And if it is an additional registration, register only in ZONE table.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :Connection to connect with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn)
            throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();

            if (mArray.length > 0)
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropZone(conn);

                //<jp>***** SOFTZONE表の更新処理 *****/</jp>
                //<en>***** Update of ZONE table *****/</en>
                SoftZone zone = new SoftZone();
                for (int i = 0; i < mArray.length; i++)
                {
                    SoftZoneParameter castparam = (SoftZoneParameter)mArray[i];

                    int tmpzoneid = castparam.getZoneID();

                    //<jp>***** SOFTZONE表の更新処理 *****/</jp>
                    //<en>***** Update of ZONE table *****/</en>
                    zone.setSoftZoneID(tmpzoneid);
                    zone.setSoftZoneName(castparam.getZoneName());
                    getToolSoftZoneHandler(conn).create(zone);
                    //<jp>***** SOFTZONE表の更新処理ここまで *****/</jp>
                    //<en>***** Update of ZONE table ends here. *****/</en>
                }
                //<jp>***** SOFTZONE表の更新処理ここまで *****/</jp>
                //<en>***** Update of ZONE table and ZONEINFO table end here. *****/</en>
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropZone(conn);
                return true;
            }
        }
        catch (DataExistsException e)
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
     * パラメータ配列のキーとなる項目を元に削除処理を行います。ZONE表のデータが全て削除されたときはそれに付随する
     * ZONEINFO表のデータも削除します。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance deletions.
     * Deletion will be done based on the key items of parameter array. If all data is deleted
     * from ZONE table, the corresponding data in ZONEINFO table will be deleted as well.
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
     * 確認するためのチェックを実装します。ゾーンメンテでは、
     * 追加するパラメータのシリアルナンバーがため打ちデータ内に存在するかを確認します。
     * @param param 今回追加するパラメータ
     * @param array ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing 
     * data from the list box to edit.
     * In zone maintenance process, it checks whether/not the serial no. of appending parameter
     * exists in the entered data summary.
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :return true if identical data exists.
     </en>*/
    private boolean isSameData(SoftZoneParameter param, Parameter[] array)
    {
        //<jp>比較を行うキー</jp>
        //<en>Key to compare</en>
        int orgZoneID = 0;
        int newZoneID = 0;

        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加するパラメータのゾーンID</jp>
            //<en>The zone ID of the parameter being appended in this process</en>
            newZoneID = param.getZoneID();
            for (int i = 0; i < array.length; i++)
            {
                SoftZoneParameter castparam = (SoftZoneParameter)array[i];
                orgZoneID = castparam.getZoneID();
                //<jp>同一ゾーンIDの確認</jp>
                //<en>Check for identical zone IDs</en>
                if (newZoneID == orgZoneID)
                {
                    //<jp> 6123266=すでに入力されています。同一ゾーンを入力することはできません。</jp>
                    setMessage("6123266");
                    return true;
                }
            }
        }
        return false;
    }

    /**<jp>
     * Zoneインスタンスを取得します。
     * @return <code>Zone</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the Zone isntance.
     * @param conn :Connection to connect with database
     * @return :the array of <code>Zone</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private SoftZone[] getSoftZoneArray(Connection conn)
            throws ReadWriteException
    {
        ToolSoftZoneSearchKey softzoneKey = new ToolSoftZoneSearchKey();
        softzoneKey.setSoftZoneIDOrder(1, true);
        //<jp>*** zoneインスタンスを取得 ***</jp>
        //<en>*** Retrieve the zone isntance ***</en>
        SoftZone[] array = (SoftZone[])getToolSoftZoneHandler(conn).find(softzoneKey);
        return array;
    }

    /**<jp>
     * SOFTZONE表のデータを削除します。
     * @param conn データベースとのコネクションオブジェクト
     * @return   削除成功時はtrue、削除失敗時はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除データが存在しなかった場合、通知されます。
     </jp>*/
    /**<en>
     * Delete data from SOFTZONE table.
     * @param conn :Connection to connect with database
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException :
     </en>*/
    private void isDropZone(Connection conn)
            throws ReadWriteException,
                NotFoundException
    {
        wZoneKey = new ToolSoftZoneSearchKey();
        // 削除行が存在する場合のみdropメソッドを呼び出す
        if (getToolSoftZoneHandler(conn).count(wZoneKey) > 0)
        {
            getToolSoftZoneHandler(conn).drop(wZoneKey);
        }
    }
}
//end of class


