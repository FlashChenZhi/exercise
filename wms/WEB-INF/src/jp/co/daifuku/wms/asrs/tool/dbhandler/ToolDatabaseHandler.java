// $Id: ToolDatabaseHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
/**<jp>
 * クラスをデータベースに保管したり、データベースから情報を取得してインスタンスを生成したりするためのインターフェースです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is an interface which stores/retrieves classes to/from database to
 * generate instances.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public interface ToolDatabaseHandler extends ToolEntityHandler
{


    // Public methods ------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     </jp>*/
    /**<en>
     * Set the <code>Connection</code> to connect with database.
     * @param conn :Connection to set
     </en>*/
    public void setConnection(Connection conn) ;

    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    /**<en>
     * Retrieve the <code>Connection</code> to connect with database.
     * @return :<code>Connection</code> currently preserved
     </en>*/
    public Connection getConnection() ;
    

    /**<jp>
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search information in database based on the parameter, and return the object.
     * @param key :Key for search
     * @return    :the array of created objects
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException ;

    /**<jp>
     * データベースから、パラメータに基づいて情報を検索し、結果の件数を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search information in database based on the parameter, and return the number of result data.
     * @param key :Key for search
     * @return :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int count(ToolSearchKey key) throws ReadWriteException ;

    /**<jp>
     * データベースに新規情報を作成します。
     * @param tgt 作成する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ情報がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create the information in database.
     * @param tgt :entity instance which preserves the information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException :Notifies if the same data is already registered in database.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException ;

    /**<jp>
     * データベースにある情報を、引数で渡されたエンティティ情報に変更します。
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the information in database to the entity information passed through parameter.
     * @param tgt :entity instance which preserves the information to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException ;

    /**<jp>
     * データベースの情報を変更します。変更内容および変更条件はToolSearchKeyより獲得します。
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the information in database. The contents and conditions of the modification
     * will be gained by ToolSearchKey.
     * @param key :AlterKey instance which preserves the information to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     * @throws InvalidDefineException :Notifies if contents of update has not been set.
     </en>*/
    public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException ;

    /**<jp>
     * データベースから、パラメータで渡されたエンティティ・インスタンスの情報を削除します。
     * @param tgt 削除する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information passed through parameter.
     * @param tgt :entity instance which preserves the information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException ;

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information that match the key passed through parameter.
     * @param key :key for the information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException ;


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

