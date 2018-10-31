//$Id: EntityHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

/**
 * インスタンスを保管したり、保管された情報を取得して
 * インスタンスを生成したりするためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public interface EntityHandler
{
    // Class variables -----------------------------------------------

    /** 検索時の件数指定(無制限) */
    public static final int LIMIT_UNLIMTED = -1;

    // Public methods ------------------------------------------------
    /**
     * パラメータに基づいて情報を検索し、オブジェクトを返します。
     * @param key 検索のためのKey
     * @param limit 検索件数上限値
     * @return 検索結果の件数
     * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管情報からの読み込みに失敗した場合に通知されます。
     */
    public Entity[] find(SearchKey key, int limit)
            throws NotFoundException,
                ReadWriteException;

    /**
     * パラメータに基づいて情報を検索し、結果の件数を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     */
    public int count(SearchKey key)
            throws ReadWriteException;

    /**
     * 新規情報を保管します。
     * @param tgt 作成する情報を持ったエンティティ・インスタンス
     * @throws DataExistsException 既に、同じ情報が登録済みの場合に通知されます。
     * @throws ReadWriteException 保管機構への書き込みに失敗した場合に通知されます。
     */
    public void create(Entity tgt)
            throws DataExistsException,
                ReadWriteException;

    /**
     * 保管されている情報を変更します。変更内容および変更条件はSearchKeyより獲得します。
     * @param key 変更する情報を持ったエンティティ・インスタンス
     * @return 更新対象レコード数
     * @throws ReadWriteException 保管機構への書き込みか、読み込みに失敗した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     */
    public int modify(AlterKey key)
            throws NotFoundException,
                ReadWriteException;

    /**
     * パラメータで渡されたエンティティ・インスタンスの情報を削除します。
     * @param tgt 削除する情報を持ったエンティティ・インスタンス
     * @return 削除対象レコード数
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの削除に失敗した場合に通知されます。
     */
    public int drop(Entity tgt)
            throws NotFoundException,
                ReadWriteException;

    /**
     * パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @return 削除対象レコード数
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの削除に失敗した場合に通知されます。
     */
    public int drop(SearchKey key)
            throws ReadWriteException,
                NotFoundException;

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface
