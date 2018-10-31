// $Id: ToolDatabaseFinder.java 87 2008-10-04 03:07:38Z admin $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
/**<jp>
 * クラスをデータベースに保管したり、データベースから情報を取得してインスタンスを生成したりするためのインターフェースです。
 * 画面に一覧表示させる場合にこのクラスをimplementsして<CODE>Entity</CODE>を返すクラスを実装してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is an interface which stores/retrieves classes to/from database in order to
 * generate instances.
 * If displaying of the data list on the screen is required, please implement this class,
 * and implement a class which is to return <CODE>Entity</CODE>.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public interface ToolDatabaseFinder extends ToolEntityHandler
{
    // Class fields --------------------------------------------------
    /**<jp>
     * LISTBOX 検索結果上限数
     </jp>*/
    /**<en>
     * LISTBOX :upper limit number of search results  
     </en>*/
    public static final int MAXDISP = Integer.parseInt(ToolParam.getParam("MAX_NUMBER_OF_DISP_LISTBOX"));

    // Public methods ------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     </jp>*/
    /**<en>
     * Set the <code>Connection</code> to connect with database.
     * @param conn :Connection to set
     </en>*/
//    public void setConnection(Connection conn) ;

    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    /**<en>
     * Retrieve the <code>Connection</code> to connect with database.
     * @return :<code>Connection</code> curently preserved
     </en>*/

//    public Connection getConnection() ;
    /**<jp>
     * ステートメントを生成し、カーソルをオープンします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Generate a statement and open a cursor.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/

    public void open() throws ReadWriteException ;

    /**<jp>
     * データベースの検索結果をエンティティ配列にして返します。
     * @param  start 検索結果の指定された開始位置
     * @param  start 検索結果の指定された終了位置
     * @return エンティティ配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidStatusException 指定された検索結果範囲に異常があった場合通知します。
     </jp>*/
    /**<en>
     * Return the result of database search in form of entity array.
     * @param  start :specified start position of search result
     * @param  end   :specified end position of search result
     * @return :entity array
     * @throws ReadWriteException     :Notifies if error occured in connection with database. 
     * @throws InvalidStatusException :Notifies if error was found in the scope of search specified.
     </en>*/
    public ToolEntity[] getEntitys(int start, int end) throws ReadWriteException, InvalidStatusException ;

    /**<jp>
     * データベースを検索し、エンティティ配列を返します。
     * @return エンティティ配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search database and return the entity array.
     * @return :the entity array
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public ToolEntity[] next() throws ReadWriteException ;

    /**<jp>
     * データベースを検索し、エンティティ配列を返します。
     * @return エンティティ配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search database and return the entity array.
     * @return :the entity array
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public ToolEntity[] back() throws ReadWriteException ;

    /**<jp>
     * ステートメントをクローズします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Close the statement.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public void close() throws ReadWriteException ;


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

