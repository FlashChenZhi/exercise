// $Id: WarehouseCreater.java 5301 2009-10-28 05:36:02Z ota $
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
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Area;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DbDateUtil;

/**<jp>
 * 倉庫設定を行なうクラスです。
 * AbstractCreaterを継承し、倉庫設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class operates the settings of the warehouse.
 * It inherits the AbstractCreater. and implements the processes required to set up the warehouses.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class WarehouseCreater
        extends AbstractCreater
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------

    //private Locale wLocale = null;

    /**<jp>
     * 製番フォルダ
     </jp>*/
    /**<en>
     * Product number folder
     </en>*/
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
        return ("$Revision: 5301 $,$Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このクラスの初期化を行ないます。初期化時に<CODE>ReStoringHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public WarehouseCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position. <BR>
     * @param conn  :connetion object to connect with database.
     * @param index :position of the deleting parameter
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(Connection conn, int index)
            throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        try
        {
            wParamVec.remove(index);
            //<jp> 削除しました</jp>
            //<en> Deleted the data.</en>
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
     * @param conn :connection object with database
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeAllParameters(Connection conn)
            throws ScheduleException
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
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param <code>Locale</code> オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
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
     * @param conn データベースとのコネクションオブジェクト
     * @param <code>Locale</code> オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn :connection object to connect with database
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
        //<jp>ロケールの保持を行う</jp>
        //<en>Conduct the preservation of locale.</en>
        //wLocale = locale;
        //<jp>他のメソッドではファイル名を外部から渡すことができないので</jp>
        //<jp>ここでセットする。</jp>
        //<en>File name should be set here, as it cannot be provided from external in other methods.</en>
        wFilePath = ((WarehouseParameter)searchParam).getFilePath();

        Warehouse[] array = getWarehouseArray(conn);

        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス作成</jp>
        //<en>Create the entity class for display.</en>
        WarehouseParameter dispData = null;
        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new WarehouseParameter();
                dispData.setWarehouseNumber(array[i].getWarehouseNo());
                dispData.setStationNo(array[i].getStationNo());
                dispData.setWarehouseName(array[i].getWarehouseName());
                dispData.setEmploymentType(array[i].getEmploymentType());
                dispData.setFreeAllocationType(array[i].getFreeAllocationType());
                dispData.setMaxMixedQuantity(array[i].getMaxMixedPallet());
                dispData.setLocationSearchType(array[i].getLocationSearchType());
                dispData.setAisleSearchType(array[i].getAisleSearchType());

                Area area = getArea(conn, array[i].getStationNo());
                dispData.setAreaNo(area.getAreaNo());
                dispData.setVacantSearchType(area.getVacantSearchType());
                dispData.setTemporaryAreaType(area.getTemporaryAreaType());
                dispData.setTemporaryArea(area.getTemporaryArea());
                vec.addElement(dispData);
            }
            WarehouseParameter[] fmt = new WarehouseParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータ
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the parameter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * @param conn  :connection object to connect with database
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public boolean check(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        WarehouseParameter mParameter = (WarehouseParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        if (processingKind == M_CREATE)
        {
            //<jp> ステーションNoのチェック(禁止文字)</jp>
            //<en> Check the station no. (unacceptable letters and symbols)</en>
            if (!check.checkStationNo(mParameter.getStationNo()))
            {
                //<jp> 異常内容をセットする</jp>
                //<en>Set the contents of the error.</en>
                setMessage(check.getMessage());
                return false;
            }

            // システム定義チェック(ステーション)
            if (WmsParam.ALL_STATION.equals(mParameter.getStationNo()))
            {
                // 6023222=入力された{0}はシステムで使用しているため登録できません。
                setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W0303")));
                return false;
            }

            //<jp> 倉庫名称のチェック</jp>
            //<en> Check the warehouse name.</en>
            if (!check.checkWarehouseName(mParameter.getWarehouseName()))
            {
                //<jp> 異常内容をセットする</jp>
                //<en>Set the contents of the error.</en>
                setMessage(check.getMessage());
                return false;
            }

            //<jp> 最大混載数のチェック</jp>
            //<en> Check the max. mix-load quantity.</en>
            if (!check.checkMaxMixedQuantity(mParameter.getMaxMixedQuantity()))
            {
                //<jp> 異常内容をセットする</jp>
                //<en>Set the contents of the error.</en>
                setMessage(check.getMessage());
                return false;
            }

            // エリアNoがエリア情報に登録されているか、エリア情報をチェックします。
            if (check.isExistAreaNo(mParameter.getAreaNo()))
            {
                //<jp> エリアがすでに登録されていた場合は、異常内容をセットする</jp>
                //<en>Set the contents of the error.</en>
                setMessage(check.getMessage());
                return false;
            }

            // システム定義チェック(エリア)
            if (WmsParam.ALL_AREA_NO.equals(mParameter.getAreaNo()))
            {
                // 6023222=入力された{0}はシステムで使用しているため登録できません。
                setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W9915")));
                return false;
            }

            // 仮置在庫作成区分と移動先仮置エリアのチェックを行います。
            if (!mParameter.getTemporaryAreaType().equals(SystemDefine.TEMPORARY_AREA_TYPE_NONE))
            {
                if (mParameter.getTemporaryArea().trim().length() > 0)
                {
                    // エリアNoがエリア情報に登録されているか、エリア情報をチェックします。
                    if (!check.isExistAreaNo(mParameter.getTemporaryArea()))
                    {
                        //<jp> エリアがすでに登録されていた場合は、異常内容をセットする</jp>
                        setMessage(check.getMessage());
                        return false;
                    }

                    // エリアNoがエリア情報に仮置エリアで登録されているか、エリア情報をチェックします。
                    if (!check.isExistTemporaryAreaNo(mParameter.getTemporaryArea()))
                    {
                        //<jp> 仮置エリアで無いならば、異常内容をセットする</jp>
                        setMessage(check.getMessage());
                        return false;
                    }
                }
                else
                {
                    // 6123288=移動先仮置エリアを入力してください。
                    setMessage("6123288");
                    return false;
                }
            }
            else
            {
                if (mParameter.getTemporaryArea().trim().length() > 0)
                {
                    // 6123289=仮置在庫を作成しないが指定されているので、移動先仮置エリアは入力できません。
                    setMessage("6123289");
                    return false;
                }
            }

            // システム定義チェック(移動先仮置エリア)
            if (WmsParam.ALL_AREA_NO.equals(mParameter.getTemporaryArea()))
            {
                // 6023222=入力された{0}はシステムで使用しているため登録できません。
                setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W9917")));
                return false;
            }

            return true;
        }
        //<jp> 処理区分が異常</jp>
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
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale)
            throws ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);

        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/ " + ToolParam.getParam("CONSTRAINT_CHECK_FILE");

        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey();
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehouseKey);
            //<jp>Warehouseが設定されていない場合</jp>
            //<en>In case the Warehouse has not been set,</en>
            if (warehouseArray.length == 0)
            {
                //<jp>6123182 = 倉庫が設定されていません</jp>
                //<en>6123182 = The warehouse has not been set.</en>
                loghandle.write("Warehouse", "Warehouse Table", "6123182");
                return false;
            }

            for (Warehouse warehouse : (Warehouse[])warehouseArray)
            {
                // 倉庫ステーションNoで一時エリア情報を取得します。
                ToolAreaSearchKey areaKey = new ToolAreaSearchKey();
                areaKey.setWhStationNo(warehouse.getStationNo());
                ToolAreaHandler areaHandler = new ToolAreaHandler(conn);
                Area[] areaArray = (Area[])areaHandler.find(areaKey);

                if (areaArray.length == 0)
                {
                    // 6023120=倉庫ステーションNo={0}はエリアマスタに存在しません。
                    loghandle.write("WareHouse", "Area Table", "6023120" + wDelim + warehouse.getStationNo());
                    errorFlag = false;
                    continue;
                }

                // エリアNoがエリア情報に登録されているか、エリア情報をチェックします。
                if (check.isExistAreaNo(areaArray[0].getAreaNo()))
                {
                    //<jp> エリアNoがエリアに登録されている場合は、異常内容をセットする</jp>
                    //<en>Set the contents of the error.</en>
                    loghandle.write("WareHouse", "Area Table", check.getMessage());
                    errorFlag = false;
                }

                // 仮置在庫作成区分と移動先仮置エリアのチェックを行います。
                if (!areaArray[0].getTemporaryAreaType().equals(SystemDefine.TEMPORARY_AREA_TYPE_NONE))
                {
                    if (areaArray[0].getTemporaryArea().trim().length() > 0)
                    {
                        // エリアNoがエリア情報に登録されているか、エリア情報をチェックします。
                        if (!check.isExistAreaNo(areaArray[0].getTemporaryArea()))
                        {
                            //<jp> エリアがすでに登録されていた場合は、異常内容をセットする</jp>
                            loghandle.write("WareHouse", "Area Table", check.getMessage());
                            errorFlag = false;
                        }

                        // エリアNoがエリア情報に仮置エリアで登録されているか、エリア情報をチェックします。
                        if (!check.isExistTemporaryAreaNo(areaArray[0].getTemporaryArea()))
                        {
                            //<jp> 仮置エリアで無いならば、異常内容をセットする</jp>
                            loghandle.write("WareHouse", "Area Table", check.getMessage());
                            errorFlag = false;
                        }
                    }
                    else
                    {
                        // 6123288=移動先仮置エリアを入力してください。
                        loghandle.write("WareHouse", "Area Table", "6123288");
                        errorFlag = false;
                    }
                }
                else
                {
                    if (areaArray[0].getTemporaryArea().trim().length() > 0)
                    {
                        // 6123289=仮置在庫を作成しないが指定されているので、移動先仮置エリアは入力できません。
                        loghandle.write("WareHouse", "Area Table", "6123289");
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
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());

        }
        return errorFlag;
    }

    /**<jp>
     * パラメータの重複チェック処理を行ないます。
     * 格納区分とステーションNo.、及び、エリアNoはそれぞれユニークなデータでなければいけません。
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、ある場合はfalseを返します。<BR>
     * ため打ちデータとの比較、STATIOYTYPE表との比較を行い同一の格納区分、ステーションNo.、エリアNo.がないかを
     * チェックします。<BR>
     * STATIONTYPE表のチェックで指定されたステーションNo.かつ指定されたハンドラクラス以外のもので検索しているのは
     * 「修正」の場合に入力を可能にするためです。<BR>
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @param param チェックするパラメータ
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.
     * Each storage type and the station no. must be unique data.
     * It checks the duplication of parameter, then returns true if there was no duplicated data or 
     * returns false if there were any duplication.<BR>
     * Compare the data with ented data summary and with STATIOYTYPE table in order to check if there
     * are no identical storage types or station no.<BR>
     * In STATIONTYPE table checking, search is conducted over data excluded of specified station no. and 
     * specified hander class in order to enable the data input in case of data modificaiton.<BR>
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn  :Connection to connect with database
     * @param param :parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        Parameter[] mArray = (Parameter[])getParameters();
        WarehouseParameter mParam = (WarehouseParameter)param;

        //<jp> 同一データチェック</jp>
        //<en> Check the identical data.</en>
        if (isSameData(mParam, mArray))
        {
            //<jp> 同一データが存在する場合は失敗。</jp>
            //<en> Failure if any identical data is found.</en>
            return false;
        }

        //<jp> STATIONTYPE表に同一のデータがないかチェック</jp>
        //<en> Check to see if there are no identical data in STATIONTYPE table.</en>
        String newStationNo = mParam.getStationNo();
        if (getToolWarehouseHandler(conn).isStationType(newStationNo, ToolWarehouseHandler.WAREHOUSE_HANDLE))
        {
            //<jp>6123122 すでに登録されています。同一ステーションNoを登録することはできません。</jp>
            //<en>6123122 The data is laready registered; Cannot register the identical station no. </en>
            setMessage("6123122");
            return false;
        }

        //<jp>修正の時のみチェックする。</jp>
        //<en>Check only when modifing data.</en>
        if (getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
        {
            //<jp>*** 修正の時、キー項目は変更不可とする ***</jp>
            //<en>*** Key items are not to be modifiable when modifing data. ***</en>
            int newWarehouseNo = mParam.getWarehouseNumber();
            int newEmploymentType = mParam.getEmploymentType();

            //<jp>ため打ちの中のキー項目(格納区分、ステーションNo.、倉庫種別、自動倉庫運用種別)</jp>
            //<en>Key items for enterd data summary (storage type, station no., warehouse type, </en>
            //<en>operation type of automated warehouse)</en>
            int orgWarehouseNo = 0;
            String orgStationNo = "";
            int orgEmploymentType = 0;

            Parameter[] mAllArray = (Parameter[])getAllParameters();
            for (int i = 0; i < mAllArray.length; i++)
            {
                WarehouseParameter castparam = (WarehouseParameter)mAllArray[i];
                //<jp>ため打ちに保持しているキー項目の値をセット</jp>
                //<en>Set the value of Key items preserved in entered data summary.</en>
                orgWarehouseNo = castparam.getWarehouseNumber();
                orgStationNo = castparam.getStationNo();
                orgEmploymentType = castparam.getEmploymentType();

                if (newWarehouseNo == orgWarehouseNo && newStationNo.equals(orgStationNo)
                        && newEmploymentType == orgEmploymentType)
                {
                    return true;
                }
            }
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
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolWarehouseHandler</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>ToolWarehouseHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolWarehouseHandler</CODE> instance generated at the initialization of this class.
     * @param conn :Connection to connect with database
     * @return <CODE>ToolWarehouseHandler</CODE>
     </en>*/
    protected ToolWarehouseHandler getToolWarehouseHandler(Connection conn)
    {
        return new ToolWarehouseHandler(conn);
    }

    /**<jp>
     * <CODE>ToolAreaHandler</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>ToolAreaHandler</CODE>
     </jp>*/
    protected ToolAreaHandler getToolAreaHandler(Connection conn)
    {
        return new ToolAreaHandler(conn);
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
    protected Parameter complementParameter(Parameter param)
            throws ReadWriteException,
                ScheduleException
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
     * メンテナンス登録処理を行います。再入庫予定データの登録は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations. The scheduled restorage data is not registered in this process.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            WarehouseParameter castparam = null;
            if (mArray.length > 0)
            {
                //<jp>表のデータを全部削除！(WAREHOUSE表とSTATIONTYPE表)</jp>
                //<en>Delete all data from the table. (from WAREHOUSE table and STATIONTYPE table)</en>
                getToolWarehouseHandler(conn).truncate();
                //<jp>表のデータを全部削除！(AREA表)</jp>
                getToolAreaHandler(conn).truncate();
                Warehouse warehouse = new Warehouse();
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (WarehouseParameter)mArray[i];

                    warehouse.setWarehouseNo(castparam.getWarehouseNumber());
                    warehouse.setStationNo(castparam.getStationNo());
                    warehouse.setWarehouseName(castparam.getWarehouseName());
                    warehouse.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);

                    //<jp> 自動倉庫運用種別は画面の入力値を設定</jp>
                    //<en> Set up the input valud for the display in case of automated warehouse operation.</en>
                    warehouse.setEmploymentType(castparam.getEmploymentType());

                    warehouse.setFreeAllocationType(castparam.getFreeAllocationType());
                    warehouse.setLocationSearchType(castparam.getLocationSearchType());
                    warehouse.setAisleSearchType(castparam.getAisleSearchType());
                    warehouse.setMaxMixedPallet(castparam.getMaxMixedQuantity());
                    getToolWarehouseHandler(conn).create(warehouse);

                    // エリア表に画面の入力値を設定
                    Area area = new Area();
                    area.setManagementType(SystemDefine.MANAGEMENT_TYPE_SYSTEM);
                    area.setAreaNo(castparam.getAreaNo());
                    area.setAreaName(castparam.getWarehouseName());
                    area.setAreaTypre(SystemDefine.AREA_TYPE_ASRS);
                    area.setLocationType(SystemDefine.LOCATION_TYPE_MASTER);
                    area.setLocationStyle(ToolParam.getParam("LOCATION_STYLE"));
                    area.setTemporaryAreaType(castparam.getTemporaryAreaType());
                    area.setTemporaryArea(castparam.getTemporaryArea());
                    area.setVacantSearchType(castparam.getVacantSearchType());
                    area.setWhstationNo(castparam.getStationNo());
                    area.setRegistDate(DbDateUtil.getTimeStamp());
                    area.setRegistPname(getClass().getSimpleName());
                    area.setLastUpdateDate(DbDateUtil.getTimeStamp());
                    area.setLastUpdatePname(getClass().getSimpleName());
                    getToolAreaHandler(conn).create(area);
                }
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！(WAREHOUSE表とSTATIONTYPE表)</jp>
                //<en>Delete all data from the table. (from WAREHOUSE table and STATIONTYPE table)</en>
                getToolWarehouseHandler(conn).truncate();
                //<jp>表のデータを全部削除！(AREA表)</jp>
                getToolAreaHandler(conn).truncate();
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
     * 追加するパラメータの格納区分とステーションNo.がため打ちデータ内に存在するかを確認します。
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    同一データが存在するかしないか（true:存在する false:存在しない）
     </jp>*/
    /**<en>
     * Chech whether/not the storage type and the station no. to append exist in entered data summary.
     * @param param  :the parameter which will be appended in this process
     * @param array  :entered data summary (pool)
     * @return       ;whether/not the identical data exist. (true: identical exist, false: it does not exist)
     </en>*/
    private boolean isSameData(WarehouseParameter param, Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int newWarehouseNo = 99999;
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        int orgWarehouseNo = 99999;
        String newStationNo = "";
        String orgStationNo = "";
        String newAreaNo = "";
        String orgAreaNo = "";

        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加する格納区分で比較する</jp>
            //<en>Compare the dtorage type which is papended in this process.</en>
            newWarehouseNo = param.getWarehouseNumber();
            newStationNo = param.getStationNo();
            newAreaNo = param.getAreaNo();

            for (int i = 0; i < array.length; i++)
            {
                WarehouseParameter castparam = (WarehouseParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgWarehouseNo = castparam.getWarehouseNumber();
                orgStationNo = castparam.getStationNo();
                //<jp>同一格納区分の確認</jp>
                //<en>Check the identical storage type.</en>
                if (newWarehouseNo == orgWarehouseNo)
                {
                    //<jp>6123015 = 既に入力されています。同一格納区分を入力することはできません</jp>
                    //<en>6123015 = The data is already entered; Cannot enter the identical storage type.</en>
                    setMessage("6123015");
                    return true;
                }
                //<jp>同一ステーションNo.のチェック</jp>
                //<en>Check the identical station no. </en>
                if (newStationNo.equals(orgStationNo))
                {
                    //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません</jp>
                    //<en>6123016 = The data is already entered; Cannot enter the identical station no.</en>
                    setMessage("6123016");
                    return true;
                }
                orgAreaNo = castparam.getAreaNo();
                //<jp>同一エリアNoの確認</jp>
                if (newAreaNo.equals(orgAreaNo))
                {
                    //<jp>6123245=すでに入力されています。同一エリアNoを入力することはできません。</jp>
                    setMessage("6123245");
                    return true;
                }
            }
        }
        return false;
    }

    /**<jp>
     * 倉庫順、倉庫ステーションNo.順に並べ替えしたWarehouseインスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <code>Warehouse</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the Warehouse instance which were sorted in order of warehouse and warehouse station no..
     * @param conn Databse Connection Object
     * @return :the array of <code>Warehouse</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Warehouse[] getWarehouseArray(Connection conn)
            throws ReadWriteException
    {
        ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey();
        warehouseKey.setWarehouseNoOrder(1, true);
        warehouseKey.setWarehouseStationNoOrder(2, true);
        //<jp>*** Warehouseインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Warehouse instance. ***</en>
        Warehouse[] array = (Warehouse[])getToolWarehouseHandler(conn).find(warehouseKey);
        return array;
    }

    /**<jp>
     * 倉庫ステーションNo.対応したAreaインスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @param whno 倉庫ステーションNo
     * @return <code>Area</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    private Area getArea(Connection conn, String whno)
            throws ReadWriteException
    {
        ToolAreaSearchKey areaKey = new ToolAreaSearchKey();
        areaKey.setAreaType(Area.AREA_TYPE_ASRS);
        areaKey.setWhStationNo(whno);
        //<jp>*** Warehouseインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Warehouse instance. ***</en>
        Area[] array = (Area[])new ToolAreaHandler(conn).find(areaKey);
        if (array.length >= 1)
        {
            return array[0];
        }
        return null;
    }
}
//end of class

