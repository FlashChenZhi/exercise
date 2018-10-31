// $Id: ScheduleInterface.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

/**<jp>
 * 画面からスケジュール処理を実行するための共通メソッドを宣言したインターフェースです。<BR>
 * 画面の操作に必要なメソッドを規定します<BR>
 * このインターフェースをimplementするクラスは以下の操作を実装します。<BR>
 *   <code>query()</code>問合せまたは再表示<BR>
 *   <code>print()</code>印刷要求<BR>
 *   <code>clear()</code>画面入力データクリア<BR>
 *   <code>addParameter()</code>パラメータの入力<BR>
 *   <code>changeParameter()</code>入力済みパラメータの修正<BR>
 *   <code>getParameters()</code>全ての入力済みパラメータの取得<BR>
 *   <code>getUpdatingParameter()</code>修正中の入力済みパラメータの取得<BR>
 *   <code>isUpdating()</code>修正中かどうかの判断<BR>
 *   <code>startScheduler()</code>処理の開始（設定）<BR>
 *   <code>getMessage()</code>メッセージの取得<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/02/16</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the interface that declares the common implementation method for schedule processing 
 * via display.<BR>
 * It regulates the method required for display operation.<BR>
 * The class, implementing this interface, executes the following operations.<BR>
 *   <code>query()</code>Inquiry or redisplay<BR>
 *   <code>print()</code>Request printing<BR>
 *   <code>clear()</code>Clearing data entered on the screen<BR>
 *   <code>addParameter()</code>Input of parameter<BR>
 *   <code>changeParameter()</code>Updating of input parameter<BR>
 *   <code>getParameters()</code>Getting all input parameters<BR>
 *   <code>getUpdatingParameter()</code>Getting all updating parameter<BR>
 *   <code>isUpdating()</code>Determining whether/not is updating<BR>
 *   <code>startScheduler()</code>Starting the processing(setting)<BR>
 *   <code>getMessage()</code>Getting messages<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/02/16</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public interface ScheduleInterface
{
	// Class fields --------------------------------------------------
	/**<jp>
	 * パラメータの変更位置を示すインデックスの初期値として使用する。
	 </jp>*/
	/**<en>
	 * This is used as initial value of index which indicates the parameter position of changes.
	 </en>*/
	public static final int NO_UPDATING = -1;

	// Public methods ------------------------------------------------
	/**<jp>
	 * 画面へ表示するデータを取得します。検索、再表示などの操作に対応するメソッドです<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param 地域コードがセットされた<code>Locale</code>オブジェクト
	 * @param searchParam 検索条件をもつ<code>Parameter</code>インターフェースを実装したクラス
	 * @return 検索結果が含まれた<code>Parameter</code>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Gets data to display. This method supports operations such as search or redisplay.<BR>
	 * @param conn Connection object with database.
	 * @param locale <code>Locale</code> object set with the local code
	 * @param searchParam The class that implements <code>Parameter</code> interface with search conditions
	 * @return The class that implements <code>Parameter</code> interface including the outcome of search.
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 帳票発行要求を行ないます。印刷、作業リスト発行などの操作に対応するメソッドです。
	 * パラメータを必要としない印刷要求時に使用します<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param 地域コードがセットされた<code>Locale</code>オブジェクト
	 * @return 印刷処理の結果。成功した場合はtrue、失敗した場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Requests the issue of forms. This method supports operations such as printing or issueing of job lists.
	 * This is used for requests of printing requiring no parameters.<BR>
	 * @param conn Connection object with database
	 * @param locale <code>Locale</code> object, set with local code
	 * @return Outcome of the printing job; it returns 'true' if succeeded and 'false' if failed.
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public boolean print(Connection conn, Locale locale) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 帳票発行要求を行ないます。印刷、作業リスト発行などの操作に対応するメソッドです。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param 地域コードがセットされた<code>Locale</code>オブジェクト
	 * @param listParam 帳票発行パラメータをもつ<code>Parameter</code>インターフェースを実装したクラス
	 * @return 印刷処理の結果。成功した場合はtrue、失敗した場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Requests the issue of forms. This method supports operations such as printing or issueing of job lists.<BR>
	 * @param conn Connection object with database
	 * @param locale <code>Locale</code> object, set with local code
	 * @param listParam The class that implements <code>Parameter</code>interface which has parameter of forms issue
	 * @return Outcome of printing job; it returns 'true' if succeeded and 'false' if failed.
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public boolean print(Connection conn, Locale locale, Parameter listParam) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 指定されたパラメータの内容について入力チェックを行ないます。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param param スケジュールに必要な情報をもつ<code>Parameter</code>インターフェースを実装したクラス
     * @return 成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Checks the entry of specified parameter contents<BR>
	 * @param conn Connection object with database
	 * @param param The class that implements <code>Parameter</code>interface which has required data for schedule.
     * @return it returns 'true' if succeeded and 'false' if failed.
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public boolean addParameter(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 修正される場所(index)を保持します。
	 * パラメータ自身の入替えはaddParameterが呼ばれたときに行います。
	 * @return 必ずtrueを返します。
	 * @param index 修正するパラメータ位置
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Preserves the positions to update (index).
	 * Switching of parameter itself shall be performed when addParameter is called.
	 * @return Always returning 'true'.
	 * @param index Position of parameters to update
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public boolean changeParameter(int index) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 指定された位置のパラメータを削除します。<BR>
	 * 正常にパラメータの削除が行なわれた場合はtrueを返します。<BR>
	 * 修正中の場合は削除できない事を通知するためのメッセージをセットし、falseを返します。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param index 削除するパラメータ位置
	 * @return 削除に成功した場合はtrueを、修正中のため削除できない場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Deletes the parameter in the specified position<BR>
	 * It returns 'true' if the parameter has been deleted in normal process.<BR>
	 * If the parameter is being updated, it sets the notification message that cannot be deleted then returns 'false'.<BR>
	 * @param conn Connection object with database
	 * @param index Position of parameters to delete
	 * @return It returns 'true' if succeeded deleting. It returns 'false' if failed due to updating procedure.
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public boolean removeParameter(Connection conn, int index) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 保持されている全てのパラメータを削除します。<BR>
	 * 正常に全パラメータの削除が行なわれた場合はtrueを返します。<BR>
	 * 修正中の場合は削除できない事を通知するためのメッセージをセットし、falseを返します。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @return 削除に成功した場合はtrueを、修正中のため削除できない場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Deletes all parameters preserved.<BR>
	 * It returns 'true' if all parameters has been deleted in normal process.<BR>
	 * If the parameter is being updated, it sets the notification message that cannot be deleted then returns 'false'.<BR>
	 * @param conn Connection object with database
	 * @return It returns 'true' if succeeded deleting. It returns 'false' if failed due to updating procedure.
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected exceptions occurs during the checking process.
	 </en>*/
	public boolean removeAllParameters(Connection conn) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * このインターフェースを実装したクラスが保持するスケジュールパラメータの配列を返します。
	 * 修正中の場合、修正対象となる入力済みパラメータ以外の入力済みパラメータ配列を返します。
	 * @return スケジュールパラメータの配列
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Returns array of schedule parameter that this class, implementing this interface, preserves.
	 * If under updating process, it returns all parameter array entered except for those updating.
	 * @return Schedule parameter array
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected error occurs during the parameter checking.
	 </en>*/
	public Parameter[] getParameters() throws ReadWriteException, ScheduleException;

/* 2003.05.28 tahara change start */

	/**<jp>
	 * このインターフェースを実装したクラスが保持するスケジュールパラメータの配列を返します。
	 * 修正中データも返します。
	 * @return スケジュールパラメータの配列
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Returns array of schedule parameter that this class, implementing this interface, preserves.
	 * Also returning the updating data in process.
	 * @return Schedule parameter array
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected error occurs during the parameter checking.
	 </en>*/
	public Parameter[] getAllParameters() throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 修正中データの行Ｎｏを返します。
	 * @return ためうちデータ内の修正位置を返します。
	 </jp>*/
	/**<en>
	 * Returning the line numbers of updating data.
	 * @return It returns the updating position in summary data.
	 </en>*/
	public int changeLineNo();

/* 2003.05.28 tahara change end */

	/**<jp>
	 * 現在修正対象となっている入力済みパラメータを返します。
	 * @return スケジュールパラメータの配列
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
	 </jp>*/
	/**<en>
	 * Returns the entered parameters requiring the update at moment.
	 * @return Schedule parameter array
	 * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected error occurs during the parameter checking.
	 </en>*/
	public Parameter getUpdatingParameter() throws ReadWriteException, ScheduleException;

	/**<jp>
	 * 入力データが現在修正中かどうかを返します。
	 * 修正中であればtrue、修正中でなければfalseを返します。
	 * @return 入力データの修正中であればtrue、修正中でなければfalse
	 </jp>*/
	/**<en>
	 * Returns whether/not the entered data currently being updated.
	 * It returns 'ture' if being updated and 'false' if not.
	 * @return True if entered data is being updated; False if not being updated.
	 </en>*/
	public boolean isUpdating();

	/**<jp>
	 * スケジュールを開始します。<BR>
	 * スケジュール処理が正常した場合はtrueを返します。<BR>
	 * スケジュール処理が失敗した場合はfalseを返します。<BR>
	 * 修正中の場合はスケジュールは開始できない事を通知するためのメッセージをセットし、falseを返します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 </jp>*/
	/**<en>
	 * Starts scheduling.<BR>
	 * It returns 'true' if normal schedule processing is done.<BR>
	 * It returns 'false' if schedule process failes.<BR>
	 * If is updating, it sets message notifying schedule cannot be started during updating then returns 'false'.<BR>
     * @param conn Connection object with database
     * @throws ReadWriteException It notifies if abnormality occurs during the connection with database.
	 * @throws ScheduleException It notifies if unexpected error occurs during schedule processing.
	 * @return It returns 'true' if normal schedule processing is done, or 'false' if failed or scheduling cannot be done.
	 </en>*/
	public abstract boolean startScheduler(Connection conn) throws ReadWriteException, ScheduleException;

	/**<jp>
	 * メッセージ格納エリアにセットされたメッセージを返します。
	 * このインターフェースで定義されているメソッドを実行後、このメソッドを呼出す事で、
	 * 詳細メッセージを取得することが出来ます。
	 * @return メッセージの詳細
	 </jp>*/
	/**<en>
	 * Returns the messages that have been set in the message storage area
	 * Detailed message can be obtained by calling this method after executing the method defined by this interface. 
	 * @return detail of the message
	 </en>*/
	public String getMessage();


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

