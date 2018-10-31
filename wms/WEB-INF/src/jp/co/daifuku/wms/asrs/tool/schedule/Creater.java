// $Id: Creater.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
/**<jp>
 * 環境設定を行なう共通インターフェースです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is a common interface which conducts the environment setting.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public interface Creater
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 処理区分の値（メソッドを外部から呼び出すときと処理区分を指定しないときに指定します。）
     </jp>*/
    /**<en>
     * Value of process type ( to specify when calling the method externally and 
     * when the process type is not specified.)
     </en>*/
    public static final int M_NOPROCESS = 0;
    /**<jp>
     * 処理区分の値（問合せ）
     </jp>*/
    /**<en>
     * Value of process type (query)
     </en>*/
    public static final int M_QUERY = 4;
    /**<jp>
     * 処理区分の値（修正）
     </jp>*/
    /**<en>
     * Value of process type (modification)
     </en>*/
    public static final int M_MODIFY = 1;
    
    /**<jp>
     * 処理区分の値（登録）
     </jp>*/
    /**<en>
     * Value of process type (registration)
     </en>*/
    public static final int M_CREATE = 2;
    
    /**<jp>
     * 処理区分の値（削除）
     </jp>*/
    /**<en>
     * Value of process type (deletion)
     </en>*/
    public static final int M_DELETE = 3;

    // Class variables -----------------------------------------------
    // Class method --------------------------------------------------
    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面へ表示するデータを取得します。<BR>
     * @param <code>Locale</code> オブジェクト
     * @param searchParam スケジュールパラメータ
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param <code>Locale</code> object
     * @param searchParam :schedule parameter
     * @return :array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException;

    /**<jp>
     * 指定されたパラメータを追加します。このメソッドは、ため打ちに初期表示を行うときに使用します。<BR>
     * 内部でのチェック処理は行わずに、そのままパラメータの追加を行います。<BR>
     * @param param 追加するパラメータの内容
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Append a specified parameter. This method will be used at the initial display in entered data summary.<BR>
     * The parameter will be appended without processing internal checks.<BR>
     * @param param :contents of appending parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the check.
     </en>*/
    public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException;


    /**<jp>
     * 指定されたパラメータを追加します。<BR>
     * パラメータの追加に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
     * パラメータの追加に失敗した場合、その理由を<code>getMessage</code>で取得できます。<BR>
     * @param param 追加するパラメータの内容
     * @return パラメータの追加に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Append a specified parameter. <BR>
     * It returns true if the parameter was appended successfully, or false if failed.<BR>
     * If parameter was not appended, the reason can be obtained by <code>getMessage</code>.<BR>
     * @param param :contents of parameter to append
     * @return :returns true if the parameter was appended successfully, or false if failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the check.
     </en>*/
    public boolean addParameter(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;

    /**<jp>
     * 指定された位置のパラメータを置き換えます。<BR>
     * パラメータの修正に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
     * パラメータの修正に失敗した場合、その理由を<code>getMessage</code>で取得できます。<BR>
     * @param index 修正するパラメータ位置
     * @param param 修正するパラメータの内容
     * @return パラメータの修正に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Replace the parameter of specified position.<BR>
     * It returns true if modification of parameter succeeded, or returns false if it failed.<BR>
     * If modification of parameter failed, the reason can be retrieved by <code>getMessage</code>. <BR>
     * @param index :position (index) of the parameter to be modified
     * @param param :contents of the parameter to be modified
     * @return :returns true if modification of parameter succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public boolean changeParameter(Connection conn, int index, Parameter param) throws ReadWriteException, ScheduleException;

    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param index 削除するパラメータ位置
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position.<BR>
     * @param index :position (index) of the parameter to be deleted
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     * @throws ScheduleException  :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(Connection conn, int index) throws ReadWriteException, ScheduleException;

    /**<jp>
     * 全てのパラメータを削除します。<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the all parameters.<BR>
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public void removeAllParameters(Connection conn) throws ReadWriteException, ScheduleException;

    /**<jp>
     * このインターフェースを実装したクラスが保持するパラメータの配列を返します。
     * @return パラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the array of the parameter that the class implemented with this interface preserves.
     * @return :parrameter array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public Parameter[] getParameters() throws ReadWriteException, ScheduleException;

/* 2003.05.29 tahara change start */
    /**<jp>
     * このインターフェースを実装したクラスが保持するパラメータの配列を返します。
     * 修正中データも返します。
     * @return パラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the array of the parameter that the class implemented with this interface preserves.
     * Also returns the data in modificaiton.
     * @return :the array of parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public Parameter[] getAllParameters() throws ReadWriteException, ScheduleException;

/* 2003.05.29 tahara change end */

    /**<jp>
     * このインターフェースを実装したクラスが保持するパラメータの配列を返します。
     * @return パラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the array of the parameter that the class implemented with this interface preserves.
     * @return :the array of parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public Parameter getUpdatingParameter() throws ReadWriteException, ScheduleException;
    /**<jp>
     * メンテナンス処理を開始します。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Start the maintenance process.
     * Return true if maintenance process succeeded, or false if failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public boolean startMaintenance(Connection conn) throws ReadWriteException, ScheduleException;

    /**<jp>
     * メンテナンス処理中に発生した内容についてのメッセージを取得します。
     * @return メッセージ
     </jp>*/
    /**<en>
     * Retrieve the message concerning what occurred during the maintenance process.
     * @return :message
     </en>*/
    public String getMessage();

    /**<jp>
     * 修正中フラグをセットします。
     * @param flag 修正中フラグ
     </jp>*/
    /**<en>
     * Set the updating flag.
     * @param flag :updating flag
     </en>*/
    public void setUpdatingFlag(int flag);
    /**<jp>
     * 修正中フラグを取得します
     * @return 修正中フラグ
     </jp>*/
    /**<en>
     * Retrieve the updating flag.
     * @return :updating flag
     </en>*/
    public int getUpdatingFlag();

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

