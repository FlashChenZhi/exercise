// $Id: ToolEntityHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */ 
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSearchKey;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
/**<jp>
 * インスタンスを保管したり、保管された情報を取得してインスタンスを生成したりするためのインターフェースです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is an interface which stores the instanc, and generages the instance by
 * retrieving the stored information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public interface ToolEntityHandler
{
    // Class variables -----------------------------------------------
    /**<jp>
     * Exception発生時のLogWriteに使用します。
     </jp>*/
    /**<en>
     * This is used in LogWrite when Exception occurs.
     </en>*/
    public StringWriter wSW = new StringWriter();
    /**<jp>
     * Exception発生時のLogWriteに使用します。
     * <pre>
     *     例
     *         try
     *         {
     *             String name = "aiueo";
     *             int    val  = Integer.parseInt( name );
     *         }
     *         catch (Exception e)
     *         {
     *             e.printStackTrace(wPW);
     *             Object[] tObj = new Object[1] ;
     *             tObj[0] = wSW.toString();
     *             RmiMsgLogClient.write(0, LogMessage.F_ERROR, "ItemHandler", tObj);
     *         }
     * </pre>
     </jp>*/
    /**<en>
     * This is used in LogWrite when Exception occurs.
     * <pre>
     *     Ex/
     *         try
     *         {
     *             String name = "aiueo";
     *             int    val  = Integer.parseInt( name );
     *         }
     *         catch (Exception e)
     *         {
     *             e.printStackTrace(wPW);
     *             Object[] tObj = new Object[1] ;
     *             tObj[0] = wSW.toString();
     *             RmiMsgLogClient.write(0, LogMessage.F_ERROR, "ItemHandler", tObj);
     *         }
     * </pre>
     </en>*/
    public PrintWriter  wPW = new PrintWriter(wSW);

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     *     例 String msginfo = "9000000" + wDelim + "Pallet" + wDelim + "Stock" ;
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     *     Ex/ String msginfo = "9000000" + wDelim + "Pallet" + wDelim + "Stock" ;
     </en>*/
    public String wDelim = MessageResource.DELIM ;

    // Public methods ------------------------------------------------
    /**<jp>
     * パラメータに基づいて情報を検索し、オブジェクトを返します。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Search data based on the parameters, then return the object.
     * @param key :Key for the search
     * @return    :the array of created object
     * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
     * @throws ReadWriteException :Notifies if reading from the storage system failed.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws NotFoundException, ReadWriteException ;

    /**<jp>
     * パラメータに基づいて情報を検索し、結果の件数を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Search data based on the parameters, then return the number of results.
     * @param key :Key for the search
     * @return    :number of search results
     * @throws ReadWriteException :Notifies if reading from the storage system failed.
     </en>*/
    public int count(ToolSearchKey key) throws ReadWriteException ;

    /**<jp>
     * 新規情報を保管します。
     * @param tgt 作成する情報を持ったエンティティ・インスタンス
     * @throws DataExistsException 既に、同じ情報が登録済みの場合に通知されます。
     * @throws ReadWriteException 保管機構への書き込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Store the new information.
     * @param tgt :entity instance which has data to create new data from
     * @throws DataExistsException :Notifies if the same data has already been registered.
     * @throws ReadWriteException  :Notifies if writing in the storage system failed.
     </en>*/
    public void create(ToolEntity tgt) throws DataExistsException, ReadWriteException ;

    /**<jp>
     * 保管されている情報を、引数で渡されたエンティティ情報に変更します。
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構への書き込みか、読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the stored data to the entity inforamtion passed through parameter.
     * @param tgt :entity instance which has data to modify
     * @throws NotFoundException  :Notifies if target data to modify cannot be found.
     * @throws ReadWriteException :Notifies if writing/reading of the storage system failed.
     </en>*/
    public void modify(ToolEntity tgt) throws NotFoundException, ReadWriteException ;

    /**<jp>
     *保管されている情報を変更します。変更内容および変更条件はSearchKeyより獲得します。
     * @param key 変更する情報を持ったAlterKeyインスタンス
     * @throws ReadWriteException 保管機構への書き込みか、読み込みに失敗した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify teh stored information. The contents and condition of modification will be 
     * obtained by SearchKey.
     * @param key :AlterKey instance which has data to modify
     * @throws ReadWriteException  :Notifies if writing/reading of the storage system failed.
     * @throws NotFoundException   :Notifies if data to modify cannot be found.
     * @throws InvalidDefineException :Notifies if  contents of the update has not been set.
     </en>*/
    public void modify(ToolAlterKey key) throws NotFoundException, ReadWriteException, InvalidDefineException ;

    /**<jp>
     * パラメータで渡されたエンティティ・インスタンスの情報を削除します。
     * @param tgt 削除する情報を持ったエンティティ・インスタンス
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの削除に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the information of entity instance passed through parameter.
     * @param tgt :entity instance which preserves target data to delete.
     * @throws NotFoundException  :Notifies if target data to delete cannot be found.
     * @throws ReadWriteException :Notifies if deleting of data in storage system failed.
     </en>*/
    public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException ;

    /**<jp>
     * パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの削除に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the information that matches the key passed through parameter.
     * @param key :Key of target data to delete
     * @throws NotFoundException  :Notifies if target data to delete cannot be found.
     * @throws ReadWriteException :Notifies if deleting of data in storage system failed.
     </en>*/
    public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException ;


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

