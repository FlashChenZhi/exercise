// $Id: CreaterFactory.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;

/**<jp>
 * メンテナンスを行なうメンテナンスクラスの呼出しを行なうクラスです。
 * 画面側アプリケーションより、指定されたスケジューラーIDを元に使用するスケジュールを決定し、実行します。
 * スケジュールがリモートサーバー上で実行されるか、ローカルの同一VM上で実行されるかは、SCHEDULETYPE表の定義を
 * もとに決定します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to invoke the maintentance class which performs the maintenance.
 * It determines which schedule to use, based on the specified shceduler ID, via diplay applications 
 * and execute the process.
 * It determines the schedule should be executed whether over remopte server or on the same local VM,
 * according to the definition of SCHEDULETYPE table.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>torigaki</TD><TD>The logic which passes a message is added.<BR>
 * A logic has been added here so that a message could be acquired from the creater instance.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class CreaterFactory
        implements ToolScheduleInterface
{
    // Class fields --------------------------------------------------

    /**<jp> 倉庫設定 </jp>*/
    /**<en> Warehouse setting </en>*/
    public static final int WAREHOUSE = 1;

    /**<jp> 端末情報設定 </jp>*/
    /**<en> Terminal information setting </en>*/
    public static final int TERMINALINFORMATION = 2;

    /**<jp> ユーザー設定 </jp>*/
    /**<en> User setting </en>*/
    public static final int AWCUSER = 3;

    /**<jp> 端末エリア設定 </jp>*/
    /**<en> Terminal area setting </en>*/
    public static final int TERMINALAREA = 4;

    /**<jp> グループコントローラ設定 </jp>*/
    /**<en> Group controller setting </en>*/
    public static final int GROUPCONTROLLER = 5;

    /**<jp> ソフトゾーン設定 </jp>*/
    /**<en> Soft zone setting </en>*/
    public static final int SOFTZONE = 6;

    /**<jp> アイル設定 </jp>*/
    /**<en> Aisle setting </en>*/
    public static final int AISLE = 7;

    /**<jp> ステーション設定 </jp>*/
    /**<en> Station setting </en>*/
    public static final int STATION = 8;

    /**<jp> 端末メニュー設定 </jp>*/
    /**<en> Terminal menu setting </en>*/
    public static final int TERMINALMENU = 9;

    /**<jp> 機器情報設定 </jp>*/
    /**<en> Machine information setting  </en>*/
    public static final int MACHINE = 10;

    /**<jp> ユーザーメニュー設定 </jp>*/
    /**<en> User menu setting </en>*/
    public static final int AWCMENU = 11;

    /**<jp> ハードゾーン設定 </jp>*/
    /**<en> Hard zone setting </en>*/
    public static final int HARDZONE = 12;

    /**<jp> 搬送ルート設定 </jp>*/
    /**<en> Carry route setting </en>*/
    public static final int ROUTE = 13;

    /**<jp> 使用不可棚設定 </jp>*/
    /**<en> Unavailable location setting </en>*/
    public static final int UNAVAILABLELOCATION = 14;

    /**<jp> 作業場設定 </jp>*/
    /**<en> Workshop setting </en>*/
    public static final int WORKPLACE = 15;

    /**<jp> ダミーステーション設定 </jp>*/
    /**<en> Dummy station setting </en>*/
    public static final int DUMMYSTATION = 16;

    /**<jp> ハードゾーン設定（個別） </jp>*/
    /**<en> Hard zone setting (individulally) </en>*/
    public static final int INDIVIDUALLYHARDZONE = 17;

    /**<jp> 分類設定 </jp>*/
    /**<en> Clasificaiton setting </en>*/
    public static final int CLASSIFICATION = 18;

    /**<jp> エリア設定 </jp>*/
    /**<en> Area setting </en>*/
    public static final int AREA = 19;

    /**<jp> ソフトゾーン設定（個別） </jp>*/
    /**<en> Soft zone setting (individual) </en>*/
    public static final int INDIVIDUALLYSOFTZONE = 20;

    /**<jp>    ステーション設定（平置き・全体） </jp>*/
    /**<en>    Station setting (floor storage/ general) </en>*/
    public static final int FLOORALL = 21;

    /**<jp> ゾーン関連設定 </jp>*/
    /**<en> Zone association setting </en>*/
    public static final int ZONERELATION = 22;

    /**<jp> 荷姿設定 </jp>*/
    /**<en> Load size setting </en>*/
    public static final int LOADSIZE = 23;

    /**<jp> 荷幅設定 </jp>*/
    /**<en> Load width setting </en>*/
    public static final int WIDTH = 24;

    /**<jp> ソフトゾーン設定（優先順） </jp>*/
    /**<en> Soft zone setting (priority) </en>*/
    public static final int SOFTZONEPRIORITY = 25;

    /**<jp> ソフトゾーン設定（範囲） </jp>*/
    /**<en> Soft zone setting (priority) </en>*/
    public static final int SOFTZONERANGE = 26;

    /**<jp> アクセス不可棚設定 </jp>*/
    /**<en> Soft zone setting (priority) </en>*/
    public static final int ACCESSNGSHELF = 27;

    // Class variables -----------------------------------------------

    /**<jp>
     * このインスタンスが保持するCreaterインスタンス。
     </jp>*/
    /**<en>
     * The Creater instance that this instance preserves.
     </en>*/
    protected Creater wCreater = null;

    /**<jp>
     * このSchedulerFactoryインスタンスが使用するスケジューラーのスケジューラーID。
     </jp>*/
    /**<en>
     * Scheduler ID of the scheduler that this SchedulerFactory instance uses.
     </en>*/
    protected int wId = 0;

    /**<jp>
     * Schedulerクラスから受け取ったメッセージを格納する。
     </jp>*/
    /**<en>
     * Store the messages received from Scheduler class.
     </en>*/
    protected String wMessage = "";

    /**<jp>
     * 修正中フラグ
     </jp>*/
    /**<en>
     * Updating flag
     </en>*/
    //    protected int wUpdating = NO_UPDATING;
    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured
     </en>*/
    public static String wDelim = MessageResource.DELIM;

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
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param id 生成するスケジューラーID
     * @param kind 処理区分
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 指定されたスケジューラーIDがSCHEDULETYPE表に存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Initialize this class. 
     * @param conn :connection object with database
     * @param id   :scheduler ID to generate
     * @param kind :process type
     * @throws ScheduleException  :Notifies if specified scheduler ID does not exist in SCHEDULETYPE table.
     </en>*/
    public CreaterFactory(Connection conn, int id, int kind) throws ScheduleException
    {
        wMessage = "";

        createInstance(conn, id, kind);
        wCreater.setUpdatingFlag(NO_UPDATING);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面へ表示するデータを取得します。<BR>
     * @param conn データベース接続用 Connection
     * @param searchParam 検索パラメータ
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn Databse Connection Object
     * @param  locale object the local code has been set to
     * @param searchParam :search parameter
     * @return :the array of the schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam)
            throws ReadWriteException,
                ScheduleException
    {
        Parameter[] ret = wCreater.query(conn, locale, searchParam);
        wMessage = wCreater.getMessage();
        return ret;
    }

    /**<jp>
     * このインターフェースを実装したクラスが保持するスケジュールパラメータの件数を返します。
     * @return スケジュールパラメータの件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the number of schedule parameters that the class implemented with this interface preserves.
     * @return :the number of schedule parameters
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public int getParametersLength()
            throws ReadWriteException,
                ScheduleException
    {
        return wCreater.getParameters().length;
    }

    /**<jp>
     * 帳票発行要求を行ないます。作業リスト発行時に実行します。<BR>
     * @param conn データベース接続用 Connection
     * @param 地域コードがセットされた<code>Locale</code>オブジェクト
     * @return 印刷処理の結果。成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Submit request of form issue. This will be executed when issuing the work list.<BR>
     * @param conn Database connection Object
     * @param  locale object the local code has been set to
     * @return :result of print job. Return true if the printing succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean print(Connection conn, Locale locale)
            throws ReadWriteException,
                ScheduleException
    {
        return true;
    }

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
     * @param conn Database connection Object
     * @param locale object
     * @param listParam :schedule parameter
     * @return :result of print job
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean print(Connection conn, Locale locale, Parameter listParam)
            throws ReadWriteException,
                ScheduleException
    {
        return true;
    }

    /**<jp>
     * このインターフェースを実装したクラスが保持するスケジュールパラメータの配列を返します。
     * 修正中データが存在する場合、修正中データを抜いた配列を返します。
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the schedule parameters that the class implemented with this interface preserves.
     * If there is any updating data, the array excluded of those updating data will be returned.
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public Parameter[] getParameters()
            throws ReadWriteException,
                ScheduleException
    {
        return wCreater.getParameters();

    }

    /* 2003.05.29 tahara change start */
    /**<jp>
     * このインターフェースを実装したクラスが保持するスケジュールパラメータの配列を返します。
     * 修正中データも返します。
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the schedule parameters that the class implemented with this interface preserves.
     * Also return the updating data.
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public Parameter[] getAllParameters()
            throws ReadWriteException,
                ScheduleException
    {
        return wCreater.getAllParameters();
    }

    /**<jp>
     * 修正中データの行Ｎｏを返します。
     * @return ためうちデータ内の修正位置を返します。
     </jp>*/
    /**<en>
     * Return the line no. of updating data.
     * @return :returns the position of the modificaiton in entered data summary.
     </en>*/
    public int changeLineNo()
    {
        return wCreater.getUpdatingFlag();
    }


    /* 2003.05.29 tahara change end */

    /**<jp>
     * 現在修正対象となっている入力済みパラメータを返します。
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the entered parameters currently targeted to modify.
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public Parameter getUpdatingParameter()
            throws ReadWriteException,
                ScheduleException
    {
        return wCreater.getUpdatingParameter();
    }

    /**<jp>
     * 入力データが現在修正中かどうかを返します。
     * 修正中であればtrue、修正中でなければfalseを返します。
     * @return 入力データの修正中であればtrue、修正中でなければfalse
     </jp>*/
    /**<en>
     * Return whether/not the input data is being modified at moment.
     * Return true if data is being modified, or false if not.
     * @return :return true if data is being modified, or false if not.
     </en>*/
    public boolean isUpdating()
    {
        if (wCreater.getUpdatingFlag() == NO_UPDATING)
        {
            return false;
        }
        return true;
    }

    /**<jp>
     * 指定されたパラメータの内容について入力チェックを行ないます。
     * 正常であれば指定されたパラメータを保持します。
     * 入力チェックの実装はScheduler実装クラスが保持するScheduleChcker実装クラスによって
     * 行なわれます。実行後、チェッカークラスが生成したメッセージをメッセージエリアに格納します。
     * @param conn データベース接続用 Connection
     * @param param スケジュールパラメータ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the entered data for the specified parameter.
     * If normally processed, the specified parameter should be preserved.
     * The check for inpu data will be carried out by ScheduleChcker implementation class that Scheduler
     * implementation class preserves.
     * After execution, the message generated by the checker class store the messages in the message area.
     * @param conn Database connection Object
     * @param param :schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean addParameter(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        //<jp> 修正中でない場合は追加処理を行う。</jp>
        //<en> Conduct the additional process if data is not being modified.</en>
        if (wCreater.getUpdatingFlag() == NO_UPDATING)
        {
            if (wCreater.addParameter(conn, param))
            {
                //<jp> 入力を受け付けました</jp>
                //<en> The data entry was accepted.</en>
                wMessage = "6121006";
                return true;
            }
        }
        else
        {
            //<jp> 修正処理</jp>
            //<en> Modification process</en>
            if (wCreater.changeParameter(conn, wCreater.getUpdatingFlag(), param))
            {
                //<jp> 修正しました</jp>
                //<en> Modified data.</en>
                wMessage = "6121002";
                //<jp> 置換に成功すればインデックス値は初期状態に戻す。</jp>
                //<en> If the parameter was replaced successfully, the index value will be </en>
                //<en> reset to initial value.</en>
                wCreater.setUpdatingFlag(NO_UPDATING);
                return true;
            }
        }
        //<jp> チェック失敗時のメッセージを取得してセット</jp>
        //<en> Retrieve the message for check failure and set.</en>
        wMessage = wCreater.getMessage();
        return false;
    }

    /**<jp>
     * 指定されたパラメータを保持します。
     * @param param スケジュールパラメータ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Preserve the specified parameter.
     * @param param :schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean addInitialParameter(Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        if (wCreater.addInitialParameter(param))
        {
            return true;
        }
        //<jp> チェック失敗時のメッセージを取得してセット</jp>
        //<en> Retrieve the message for check failure and set.</en>
        wMessage = wCreater.getMessage();
        return false;
    }

    /**<jp>
     * 修正される場所(index)を保持します。
     * パラメータ自身の入れ替えはaddParameterが呼ばれたときに行います。
     * @param index 修正するパラメータ位置
     * @param param スケジュールパラメータ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Preserve the position (index) of data modification.
     * Parameters will be switched when addParameter is called.
     * @param index :index of the parameter to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean changeParameter(int index)
            throws ReadWriteException,
                ScheduleException
    {
        wMessage = "";
        wCreater.setUpdatingFlag(index);
        return true;
    }

    /**<jp>
     * 指定された位置のパラメータを削除します。
     * @param conn データベース接続用 Connection
     * @param index 削除するパラメータ位置
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of specified position.
     * @param conn Database connection Object
     * @param index :index of the parameter to be deleted
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean removeParameter(Connection conn, int index)
            throws ReadWriteException,
                ScheduleException
    {
        //<jp> 修正に成功したか失敗したかを見るために削除前、削除後のため打ちの配列の長さを比べます。 2003/11/17 by岡村</jp>
        //<en> Compare the length of the arrays of the data before deletion and that of after deletion, </en>
        //<en> in order to check if the modificaiton succeeded.</en>
        int beforeLength = 0;
        int afterLength = 0;
        //<jp> 修正前</jp>
        //<en> Before modification</en>
        Parameter[] beforeArray = (Parameter[])getAllParameters();
        beforeLength = beforeArray.length;
        //<jp> 削除実行、メッセージをセット</jp>
        //<en> Set the message nefor deleting data.</en>
        wCreater.removeParameter(conn, index);
        wMessage = wCreater.getMessage();

        //<jp> 修正後</jp>
        //<en> After modification</en>
        Parameter[] afterArray = (Parameter[])getAllParameters();
        afterLength = afterArray.length;
        //<jp> 修正に成功したら以下の処理を行う。</jp>
        //<en> If modification succeeded, carry out the following process;</en>
        if (beforeLength != afterLength)
        {
            //<jp> 修正中の行よりも上の行が削除された場合は修正中行番号を(-1)する</jp>
            //<en> If the line above the modification target line was deleted, the line no. of</en>
            //<en> the modification target line should be renewed by subtracing 1. (current line no. -1).</en>
            if (wCreater.getUpdatingFlag() > index)
            {
                wCreater.setUpdatingFlag(wCreater.getUpdatingFlag() - 1);
            }
            //<jp> 修正中の行が削除された場合は修正中フラグをOFFにする。</jp>
            //<en> If the modification target line itself was deleted, alter the updating flag to 'OFF'.</en>
            else if (wCreater.getUpdatingFlag() == index)
            {
                wCreater.setUpdatingFlag(NO_UPDATING);
            }
        }
        return true;

    }

    /**<jp>
     * 全パラメータを削除します。
     * @param conn データベース接続用 Connection
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all the parameters.
     * @param conn Database connection Object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public boolean removeAllParameters(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        wMessage = "";
        if (getAllParameters().length != 0)
        {
            wCreater.removeAllParameters(conn);
            //<jp> 修正中フラグをOFFにする。</jp>
            //<en> Alter the updating flag to 'OFF'.</en>
            wCreater.setUpdatingFlag(NO_UPDATING);
        }
        wMessage = wCreater.getMessage();
        return true;

    }

    /**<jp>
     * スケジュールを開始します。
     * @param conn データベース接続用 Connection
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Start the schedule process.
     * @param conn Database connection Object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exceptions occur during teh schedule procesing.
     </en>*/
    public boolean startScheduler(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        try
        {
            //<jp> 処理実行</jp>
            //<en> Carry out the processing.</en>
            boolean status = wCreater.startMaintenance(conn);
            wMessage = wCreater.getMessage();
            if (status)
            {
                schCommit(conn);
            }
            else
            {
                schRollBack(conn);
            }
            return status;
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
        catch (ScheduleException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        finally
        {
            schRollBack(conn);
        }
    }

    /**<jp>
     * メッセージ格納エリアにセットされたメッセージを返します。
     </jp>*/
    /**<en>
     * Return the message which has been set in message storage area.
     </en>*/
    public String getMessage()
    {
        return wMessage;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * スケジューラーIDより<code>Creater</code>インターフェースを実装したクラスの
     * インスタンスを生成します
     * @param conn データベース接続用 Connection
     * @param id スケジューラーID
     * @param kind 処理区分
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 指定されたスケジューラーIDが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Generate the instance of the class implemetned with <code>Creater</code> interface
     * according to the scheduler ID.
     * @param conn Database connection Object
     * @param id    :scheduler ID
     * @param kind  :proess type
     * @throws ScheduleException  :Notifies if the specifeid scheduler ID does not exist.
     </en>*/
    protected void createInstance(Connection conn, int id, int kind)
            throws ScheduleException
    {
        //<jp>倉庫設定</jp>
        //<en>Warehouse setting</en>
        if (id == WAREHOUSE)
        {
            wCreater = new WarehouseCreater(conn, kind);
        }
        //<jp>グループコントローラー設定</jp>
        //<en>Group contrrleer setting</en>
        else if (id == GROUPCONTROLLER)
        {
            wCreater = new GroupControllerCreater(conn, kind);
        }
        //<jp>ハードゾーン設定</jp>
        //<en>Hard zone setting</en>
        else if (id == HARDZONE)
        {
            wCreater = new HardZoneCreater(conn, kind);
        }
        //<jp>アイル設定</jp>
        //<en>Aisle setting</en>
        else if (id == AISLE)
        {
            wCreater = new AisleCreater(conn, kind);
        }
        //<jp>ステーション設定</jp>
        //<en>Station setting</en>
        else if (id == STATION)
        {
            wCreater = new StationCreater(conn, kind);
        }
        //<jp>機器情報設定</jp>
        //<en>Machine information setting</en>
        else if (id == MACHINE)
        {
            wCreater = new MachineCreater(conn, kind);
        }
        //<jp>搬送ルート設定</jp>
        //<en>Carry route setting</en>
        else if (id == ROUTE)
        {
            wCreater = new RouteCreater(conn, kind);
        }
        //<jp>使用不可棚設定</jp>
        //<en>Unavailable location setting</en>
        else if (id == UNAVAILABLELOCATION)
        {
            wCreater = new UnavailableLocationCreater(conn, kind);
        }
        //<jp>作業場設定</jp>
        //<en>Workshop setting</en>
        else if (id == WORKPLACE)
        {
            wCreater = new WorkPlaceCreater(conn, kind);
        }
        //<jp>ダミーステーション設定</jp>
        //<en>Dummy station setting</en>
        else if (id == DUMMYSTATION)
        {
            wCreater = new DummyStationCreater(conn, kind);
        }
        //<jp>ハードゾーン設定（個別）</jp>
        //<en>Hard zone setting (individual)</en>
        else if (id == INDIVIDUALLYHARDZONE)
        {
            wCreater = new IndividuallyHardZoneCreater(conn, kind);
        }
        //<jp>荷姿設定</jp>
        //<en>Load size setting</en>
        else if (id == LOADSIZE)
        {
            wCreater = new LoadSizeCreater(conn, kind);
        }
        //<jp>荷幅設定</jp>
        //<en>Load width setting</en>
        else if (id == WIDTH)
        {
            wCreater = new WidthCreater(conn, kind);
        }
        //<jp>ソフトゾーン設定</jp>
        //<en>Soft zone setting</en>
        else if (id == SOFTZONE)
        {
            wCreater = new SoftZoneCreater(conn, kind);
        }
        //<jp>ソフトゾーン優先順設定</jp>
        //<en>Soft zone priority setting</en>
        else if (id == SOFTZONEPRIORITY)
        {
            wCreater = new SoftZonePriorityCreater(conn, kind);
        }
        //<jp>ソフトゾーン範囲設定</jp>
        //<en>Soft zone range setting</en>
        else if (id == SOFTZONERANGE)
        {
            wCreater = new SoftZoneRangeCreater(conn, kind);
        }
        //<jp>アクセス不可棚設定</jp>
        //<en>Access Ng Shelf setting</en>
        else if (id == ACCESSNGSHELF)
        {
            wCreater = new AccessNgShelfCreater(conn, kind);
        }
        else
        {
            throw new ScheduleException("Can't find SchedulerID.");
        }
    }

    /**<jp>
     * データベース・コネクションを設定します。
     * @param conn データベース・コネクション
     </jp>*/
    /**<en>
     * Set the database conection.
     * @param conn :database connection
     </en>*/

    // Private methods -----------------------------------------------
    /**<jp>
     * スケジュールの確定処理を行ないます。具体的にはトランザクションをcommitします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Process the confirmation of the schedule. Concretely it commits the transaction.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @param conn Database connection Object
     </en>*/
    private void schCommit(Connection conn)
            throws ReadWriteException
    {
        try
        {
            conn.commit();
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), (String)this.getClass().getName());
            schRollBack(conn);
            throw new ReadWriteException();
        }
    }

    /**<jp>
     * スケジュールの取り消し処理を行ないます。具体的にはトランザクションをrollbackします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Process the cancelation of the schedule. Concretely it rollbacks the transaction.
     * @param conn Database connection Object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private void schRollBack(Connection conn)
            throws ReadWriteException
    {
        try
        {
            conn.rollback();
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), (String)this.getClass().getName());
            throw new ReadWriteException();
        }
    }
}
//end of class

