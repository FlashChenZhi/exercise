// $Id: AccessNgShelfCreater.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAccessNgShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAccessNgShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWidthHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWidthSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.AccessNgShelf;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.location.Width;

/**<jp>
 * アクセス不可棚のメンテナンス処理を行なうクラスです。
 * AbstractCreaterを継承し、荷幅の設定処理に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class processes the width maintenance.
 * It inherits the AbstractCreater, and implements the processes required for width setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class AccessNgShelfCreater
        extends AbstractCreater
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * <CODE>ToolAccessNgShelfSearchKey</CODE>インスタンス
     </jp>*/
    /**<en>
     * <CODE>ToolAccessNgShelfSearchKey</CODE> instance
     </en>*/
    protected ToolAccessNgShelfSearchKey wAccessNgShelfKey = null;

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
        AccessNgShelf[] array = getAccessNgShelfArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        AccessNgShelfParameter dispData = null;
        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new AccessNgShelfParameter();
                dispData.setWareHouseStationNo(array[i].getWareHouseStationNo());
                dispData.setBankNo(array[i].getBankNo());
                dispData.setBayNo(array[i].getBayNo());
                dispData.setLevelNo(array[i].getLevelNo());
                dispData.setWidth(array[i].getWidth());
                dispData.setStartAddressNo(array[i].getStartAddressNo());
                dispData.setEndAddressNo(array[i].getEndAddressNo());
                vec.addElement(dispData);
            }
            AccessNgShelfParameter[] fmt = new AccessNgShelfParameter[vec.size()];
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
    public AccessNgShelfCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * パラメータの補完処理を行います。<BR>
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
        AccessNgShelfParameter mParameter = (AccessNgShelfParameter)param;

        return mParameter;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * ＜荷幅名称＞<BR>
     * ・禁止文字<BR>
     * ＜荷幅範囲＞<BR>
     * ・開始棚ベイ＜＝終了棚ベイ<BR>
     * ・開始棚レベル＜＝終了棚レベル<BR>
     * ・荷幅範囲が指定倉庫の範囲内にあること<BR>
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
     * <Width name><BR>
     *  -Unacceptable letters and symbols<BR>
     * <Width range><BR>
     *  -Startinf location bay <= ending location bay<BR>
     *  -Starting location level <= ending location level<BR>
     *  -Width range should be contained within teh s@pecified warehouse range.<BR>
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
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        AccessNgShelfParameter mParameter = (AccessNgShelfParameter)param;

        //<jp>登録処理の場合</jp>
        //<en>In case of registration,</en>
        if (processingKind == M_CREATE)
        {
            //<jp> WAREHOUSE表に登録されているかチェック</jp>
            //<en> Check whether/not the data is registered in WAREHOUSE table.</en>
            ToolWarehouseHandler whhandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey whkey = new ToolWarehouseSearchKey();
            String whstno = mParameter.getWareHouseStationNo();
            whkey.setWarehouseStationNo(whstno);
            if (whhandle.count(whkey) <= 0)
            {
                //<jp> 倉庫情報がありません。倉庫設定画面で登録してください</jp>
                //<en> There is no information for the warehouse. Please register in screen for the wareshouse setting.</en>
                setMessage("6123100");
                return false;
            }

            //<jp>指定された棚が倉庫に存在するかを確認</jp>
            //<en>Check if the specified data is registered in warehouse table.</en>
            int bank = mParameter.getBankNo();
            int bay = mParameter.getBayNo();
            int level = mParameter.getLevelNo();
            ToolShelfSearchKey skey = new ToolShelfSearchKey();
            ToolShelfHandler shdle = new ToolShelfHandler(conn);

            //<jp> 倉庫ステーションNoをセット</jp>
            //<en> Set the warehouse station no.</en>
            skey.setWarehouseStationNo(mParameter.getWareHouseStationNo());
            //<jp> bankをセット</jp>
            //<en> Set bank.</en>
            if (bank > 0)
            {
                skey.setBankNo(bank);
            }
            //<jp> bayをセット</jp>
            //<en>Set bay.</en>
            if (bay > 0)
            {
                skey.setBayNo(bay);
            }
            //<jp> levelをセット</jp>
            //<en> Set level.</en>
            if (level > 0)
            {
                skey.setLevelNo(level);
            }
            if (shdle.count(skey) == 0)
            {
                //<jp>6123037 指定された棚No.は倉庫内に存在しません</jp>
                //<en>6123037 The location no. specified does not exist in the warehouse.</en>
                setMessage("6123037");
                return false;
            }

            //<jp>アクセス不可棚範囲確認</jp>
            //<en>Check the load width range.</en>
            if (!checkAccessNgShelfRange(conn, mParameter))
            {
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
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/" + ToolParam.getParam("CONSTRAINT_CHECK_FILE");

        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            AccessNgShelfParameter accessNgShelf = new AccessNgShelfParameter();

            ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
            ToolWarehouseHandler warehouseHandler = new ToolWarehouseHandler(conn);

            // フリーアロケーション用倉庫を検索
            key.setFreeAllocationType(Warehouse.FREE_ALLOCATION_ON);
            if (warehouseHandler.count(key) == 0)
            {
                // フリーアロケーション用倉庫が存在しない場合はアクセス不可棚情報のチェックを行わない
                return true;
            }

            ToolAccessNgShelfSearchKey ansKey = new ToolAccessNgShelfSearchKey();
            AccessNgShelf[] ansArray = (AccessNgShelf[])getToolAccessNgShelfHandler(conn).find(ansKey);

            //<jp>AccessNgShelfが設定されていない場合</jp>
            //<en>If the AccessNgShelf has not been set,</en>
            if (ansArray.length == 0)
            {
                //<jp>アクセス不可棚設定がされていない場合は以下のチェックを行わずにぬけます</jp>
                return true;
            }

            for (int i = 0; i < ansArray.length; i++)
            {
                //<jp>*** 倉庫ステーションNoのチェック(AccessNgShelf表) ***</jp>
                //<jp>ACCESSNGSHELF表の倉庫ステーションNo.がWAREHOUSE表に存在するか確認</jp>
                //<en>*** Check the warehouse station no. (AccessNgShelf table) ***</en>
                //<en>Check whether/not the warehouse station no. in ACCESSNGSHELF table exists in WAREHOUSE table.</en>
                String warehouseStationNo = ansArray[i].getWareHouseStationNo();
                if (!check.isExistFreeAllocationOnWarehouseStationNo(warehouseStationNo))
                {
                    loghandle.write("AccessNgShelf", "Warehouse Table", check.getMessage());
                    errorFlag = false;
                }

                //<jp>*** 荷幅のチェック(AccessNgShelf表) ***</jp>
                //<jp>ACCESSNGSHELF表の荷幅がWIDTH表に存在するか確認</jp>
                //<en>*** Check the width (AccessNgShelf table) ***</en>
                //<en>Check whether/not the width in ACCESSNGSHELF table exists in WIDTH table.</en>
                int width = ansArray[i].getWidth();
                if (!check.isExistWidth(width))
                {
                    loghandle.write("AccessNgShelf", "Width Table", check.getMessage());
                    errorFlag = false;
                }

                //<jp>*** 開始アドレス・終了アドレスのチェック(AccessNgShelf表) ***</jp>
                //<jp> ① 開始アドレス・終了アドレスの値チェック（0以上か？）</jp>
                //<jp> ② 開始アドレス・終了アドレスの大小チェック</jp>
                //<en>*** Check for starting/ending addresses (AccessNgShelf table) ***</en>
                //<en> 1/ Check the values of starting/ending addresses</en>
                //<en>    The values must be 0 or greater.</en>
                //<en> 2/ Check the numeric numbers to see if that of starting point is aways smaller than ending point. </en>
                int staddress = ansArray[i].getStartAddressNo();
                int edaddress = ansArray[i].getEndAddressNo();

                if (!check.checkAddress(staddress, edaddress))
                {
                    loghandle.write("AccessNgShelf", "AccessNgShelf Table", check.getMessage());
                    errorFlag = false;
                }

                //<jp>*** アクセス不可棚設定範囲のチェック(Shelf表) ***</jp>
                //<jp>ACCESSNGSHELF表の格納区分、バンク、ベイ、レベル、開始アドレスル・終了アドレスの範囲チェック</jp>
                //<jp>SHELF表に存在するか確認</jp>
                int bank = ansArray[i].getBankNo();
                int bay = ansArray[i].getBayNo();
                int level = ansArray[i].getLevelNo();
                //<jp> 倉庫ステーションNo.</jp>
                //<en> warehouse station no.</en>
                accessNgShelf.setWareHouseStationNo(warehouseStationNo);
                //<jp> バンク</jp>
                //<en> bank</en>
                accessNgShelf.setBankNo(bank);
                //<jp> ベイ</jp>
                //<en> bay</en>
                accessNgShelf.setBayNo(bay);
                //<jp> レベル</jp>
                //<en> level</en>
                accessNgShelf.setLevelNo(level);
                //<jp> 荷幅/jp>
                //<en> width</en>
                accessNgShelf.setWidth(ansArray[i].getWidth());
                //<jp> 開始アドレス</jp>
                //<en> starting level</en>
                accessNgShelf.setStartAddressNo(staddress);
                //<jp> 終了アドレス</jp>
                //<en> ending level</en>
                accessNgShelf.setEndAddressNo(edaddress);

                if (!checkAccessNgShelfRange(conn, accessNgShelf))
                {
                    loghandle.write("AccessNgShelf", "Shelf Table", this.getMessage());
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
     * パラメータの重複チェック処理を行ないます。
     *＜登録処理の場合＞<BR>
     *・同一ゾーン範囲の設定が、ため打ちデータ内とWIDTH表に無いことを確認する。<BR>
     *＜修正処理の場合＞<BR>
     *・リストボックスより同一データが選択されていないことを確認する。<BR>
     *・同一ゾーン範囲の設定が、ため打ちデータ内とWIDTH表に無いことを確認する。<BR>
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
     * -Check to see that the setting of the identical width range does not exist in entered data summary or in WIDTH table.<BR>
     * < Modification ><BR>
     * -Check to see that identical data has not been selected from the listbox.<BR>
     * -Check to see that the setting of the identical width range does not exist in entered data summary or in WIDTH table.<BR>
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
        AccessNgShelfParameter mParam = (AccessNgShelfParameter)param;

        //<jp>同一データチェック</jp>
        //<en>Check the identical data.</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }

        //<jp>最小範囲チェック</jp>
        //<en>Check the min. range.</en>
        if (isZeroSameData(mParam))
        {
            return false;
        }

        //<jp>荷幅範囲チェック</jp>
        //<en>Check the width range.</en>
        if (!checkAccessNgShelfRange(conn, mParam, mArray))
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
     * このクラスの初期化時に生成した<CODE>AccessNgShelfHandler</CODE>インスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <CODE>AccessNgShelfHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>AccessNgShelfHandler</CODE> instance generated at the initialization of this class.
     * @param conn :Connection to connect with database
     * @return <CODE>AccessNgShelfHandler</CODE>
     </en>*/
    protected ToolAccessNgShelfHandler getToolAccessNgShelfHandler(Connection conn)
    {
        return new ToolAccessNgShelfHandler(conn);
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
     * メンテナンス修正処理を行います。
     * パラメータ配列のキーとなる項目を元に変更処理を行います。
     * この実装ではWIDTHの修正は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Modification of WIDTH will not be done here.
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
     * WIDTH表とSHELF表に登録を行います。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations.
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
                isDropAccessNgShelf(conn);

                //<jp>***** ACCESSNGSHELF表の更新処理 *****/</jp>
                //<en>***** Update of ACCESSNGSHELF table *****/</en>
                AccessNgShelf accessngshelf = new AccessNgShelf();
                for (int i = 0; i < mArray.length; i++)
                {
                    AccessNgShelfParameter castparam = (AccessNgShelfParameter)mArray[i];

                    accessngshelf.setWareHouseStationNo(castparam.getWareHouseStationNo());
                    accessngshelf.setBankNo(castparam.getBankNo());
                    accessngshelf.setBayNo(castparam.getBayNo());
                    accessngshelf.setLevelNo(castparam.getLevelNo());
                    accessngshelf.setWidth(castparam.getWidth());
                    accessngshelf.setStartAddressNo(castparam.getStartAddressNo());
                    accessngshelf.setEndAddressNo(castparam.getEndAddressNo());

                    getToolAccessNgShelfHandler(conn).create(accessngshelf);
                }
                //<jp>***** ACCESSNGSHELF表の更新処理ここまで *****/</jp>
                //<en>***** Update of ACCESSNGSHELF table end here. *****/</en>
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropAccessNgShelf(conn);
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
     * パラメータ配列のキーとなる項目を元に削除処理を行います。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance deletions.
     * Deletion will be done based on the key items of parameter array. 
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
     * アクセス不可棚設定における、指定アドレス範囲のチェックを行います。このメソッドでは<br>
     * 1. 倉庫で定義されているアドレスの範囲内であること<BR>
     * のチェックを行います。すべて正しい場合はTrueを返します。
     * @param conn データベースとのコネクションオブジェクト
     * @param param AccessNgShelfParameter
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    private boolean checkAccessNgShelfRange(Connection conn, AccessNgShelfParameter param)
            throws ReadWriteException
    {
        //<jp> バンク</jp>
        //<en> bank</en>
        int bank = param.getBankNo();
        //<jp> ベイ</jp>
        //<en> bay</en>
        int bay = param.getBayNo();
        //<jp> レベル</jp>
        //<en> level</en>
        int level = param.getLevelNo();
        //<jp> 開始アドレス</jp>
        //<en> starting address</en>
        int stAddress = param.getStartAddressNo();
        //<jp> 終了アドレス</jp>
        //<en> ending address</en>
        int edAddress = param.getEndAddressNo();
        // 倉庫ステーションNo.
        String whstno = param.getWareHouseStationNo();
        // 荷幅
        int width = param.getWidth();

        ToolCommonChecker check = new ToolCommonChecker(conn);
        if (!check.checkAddress(stAddress, edAddress))
        {
            setMessage(check.getMessage());
            return false;
        }

        ToolWidthHandler widthHandle = new ToolWidthHandler(conn);
        ToolWidthSearchKey widthKey = new ToolWidthSearchKey();
        widthKey.setWHStationNo(whstno);
        widthKey.setWidth(width);

        //<jp>指定された荷幅、棚が荷幅情報に存在するかを確認</jp>
        Width[] widthArray = (Width[])widthHandle.find(bank, bay, level, widthKey);

        if (widthArray.length == 0)
        {
            //<jp> 6123238=指定された棚、荷幅は荷幅情報に存在しません。</jp>
            setMessage("6123238");
            return false;
        }

        //<jp>指定されたアドレス範囲が荷幅情報の範囲内にあることを確認</jp>
        //<en>Check whether/not teh specified range is included in location range.</en>
        int maxStorage = widthArray[0].getMaxStorage();
        // 開始アドレス
        if (maxStorage < stAddress || 1 > stAddress)
        {
            //<jp>6123239=アドレスの範囲が倉庫の範囲を超えています</jp>
            setMessage("6123239");
            return false;
        }
        // 終了アドレス
        if (maxStorage < edAddress || 1 > edAddress)
        {
            //<jp>6123239=アドレスの範囲が倉庫の範囲を超えています</jp>
            setMessage("6123239");
            return false;
        }
        return true;
    }

    /**<jp>
     * ため打ちデータ内とWIDTH表に重複した荷幅範囲の有無を確認します。
     * すでに、重複した荷幅かつ荷幅範囲で設定されたデータがある場合はfalseを返します。
     * @param conn データベースとのコネクションオブジェクト
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    重複範囲で設定されたデータが存在する場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not there is the width range overlapping the entered data summary and 
     * WIDTH table.
     * Return false if data is found which has been set over duplicate width and width range.
     * @param conn :Connection to connect with database
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :returns false if there is data which has been set over duplicate width range.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private boolean checkAccessNgShelfRange(Connection conn, AccessNgShelfParameter param, Parameter[] array)
            throws ReadWriteException
    {
        System.out.println(">> AccessNgShelfCreater.java  checkAccessNgShelfRange(Connection conn, AccessNgShelfParameter param, Parameter[] array)");

        // 格納区分
        int whno = param.getWarehouseNo();
        //<jp> バンク</jp>
        //<en> bank</en>
        int bank = param.getBankNo();
        //<jp> ベイ</jp>
        //<en> bay</en>
        int bay = param.getBayNo();
        //<jp> レベル</jp>
        //<en> level</en>
        int level = param.getLevelNo();
        // 荷幅
        int width = param.getWidth();
        //<jp> 開始アドレス</jp>
        //<en> starting address</en>
        int stAddress = param.getStartAddressNo();
        //<jp> 終了アドレス</jp>
        //<en> ending address</en>
        int edAddress = param.getEndAddressNo();

        //<jp>*** ため打ちデータとのチェック ***</jp>
        //<jp>ため打ちデータが存在する場合</jp>
        //<en>*** Check with entered data summary. ***</en>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                AccessNgShelfParameter castparam = (AccessNgShelfParameter)array[i];
                //<jp>*** 範囲の確認 ***</jp>
                //<jp>同一棚、同一荷幅出なければチェックしない</jp>
                // 倉庫No.
                if (whno != castparam.getWarehouseNo())
                {
                    continue;
                }

                // バンク
                if (bank != castparam.getBankNo() && bank != 0 && castparam.getBankNo() != 0)
                {
                    continue;
                }

                // ベイ
                if (bay != castparam.getBayNo() && bay != 0 && castparam.getBayNo() != 0)
                {
                    continue;
                }

                // レベル
                if (level != castparam.getLevelNo() && level != 0 && castparam.getLevelNo() != 0)
                {
                    continue;
                }

                // 荷幅
                if (width != castparam.getWidth())
                {
                    continue;
                }

                // アドレス範囲が重複しているかチェックする
                if (!(stAddress > castparam.getEndAddressNo() || edAddress < castparam.getStartAddressNo()))
                {
                    //<jp>6123240=アドレス範囲が重複しているため設定できません。</jp>
                    //<en>6123240=Cannot set; the address range overlaps.</en>
                    setMessage("6123240");
                    return false;
                }
            }
        }
        System.out.println("<< AccessNgShelfCreater.java  checkAccessNgShelfRange(Connection conn, AccessNgShelfParameter param, Parameter[] array)  !002");
        return true;
    }

    /**<jp>
     * リストボックスより編集するデータを選択するときに、同一のデータが選択されたことを
     * 確認するためのチェックを実装します。荷幅メンテでは、
     * 追加するパラメータのシリアルナンバーがため打ちデータ内に存在するかを確認します。
     * @param param 今回追加するパラメータ
     * @param array ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing 
     * data from the list box to edit.
     * In width maintenance process, it checks whether/not the serial no. of appending parameter
     * exists in the entered data summary.
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :return true if identical data exists.
     </en>*/
    private boolean isSameData(AccessNgShelfParameter param, Parameter[] array)
    {
        return false;
    }

    /**<jp>
     * AccessNgShelfインスタンスを取得します。
     * @return <code>AccessNgShelf</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the AccessNgShelf isntance.
     * @param conn :Connection to connect with database
     * @return :the array of <code>AccessNgShelf</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private AccessNgShelf[] getAccessNgShelfArray(Connection conn)
            throws ReadWriteException
    {
        ToolAccessNgShelfSearchKey widthKey = new ToolAccessNgShelfSearchKey();
        widthKey.setWarehouseNoOrder(1, true);
        widthKey.setBankNoOrder(2, true);
        widthKey.setBayNoOrder(3, true);
        widthKey.setLevelNoOrder(4, true);
        widthKey.setWidthOrder(5, true);
        //<jp>*** AccessNgShelfインスタンスを取得 ***</jp>
        //<en>*** Retrieve the AccessNgShelf isntance ***</en>
        AccessNgShelf[] array = (AccessNgShelf[])getToolAccessNgShelfHandler(conn).find(widthKey);
        return array;
    }

    /**<jp>
     * WIDTH表のデータを削除します。
     * @param conn データベースとのコネクションオブジェクト
     * @return   削除成功時はtrue、削除失敗時はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除データが存在しなかった場合、通知されます。
     </jp>*/
    /**<en>
     * Delete data from WIDTH table.
     * @param conn :Connection to connect with database
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException :
     </en>*/
    private void isDropAccessNgShelf(Connection conn)
            throws ReadWriteException,
                NotFoundException
    {
        wAccessNgShelfKey = new ToolAccessNgShelfSearchKey();
        // 削除行が存在する場合のみdropメソッドを呼び出す
        if (getToolAccessNgShelfHandler(conn).count(wAccessNgShelfKey) > 0)
        {
            getToolAccessNgShelfHandler(conn).drop(wAccessNgShelfKey);
        }
    }

    /**<jp>
     * バンク・ベイ・レベルの入力値が0以上であるかのチェックを実装します。
     * @param param 今回追加するパラメータ
     * @return    0以下のデータが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implements the checks to see if the input value for bank/bay/level is 0 or greater.
     * @param param  :the parameter which will be appended in this process
     * @return       :return true if the data less than 0 is found.
     </en>*/
    private boolean isZeroSameData(AccessNgShelfParameter param)
    {
        if (param.getStartAddressNo() <= 0 || param.getEndAddressNo() <= 0)
        {
            //<jp> 6123071 = 範囲には１以上の値を指定してください</jp>
            //<en> 6123071 = Please specify values which is greater than 1 (inclusive) for the range.</en>
            setMessage("6123071");
            return true;
        }
        return false;
    }
}
//end of class


