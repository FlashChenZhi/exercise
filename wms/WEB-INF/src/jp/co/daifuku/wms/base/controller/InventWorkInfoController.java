// $Id: InventWorkInfoController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * 棚卸作業情報コントローラクラスです。
 *
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class InventWorkInfoController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元プログラム
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param pname 呼び出し元プログラム名
     */
    public InventWorkInfoController(Connection conn, Class pname)
    {
        super(conn, pname);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業データのロックを行います。<br>
     * ロックした棚卸作業の先頭作業No.を返します。
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>荷主コード
     * <li>エリア
     * <li>棚
     * <li>商品コード
     * <li>ロットNo.
     * </ol>
     * @param forceStart 強制開始区分
     * @return 作業No
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException 状態フラグが「未作業」以外の場合内容が返されます。
     * @throws NoPrimaryException 作業データ重複
     * @throws LockTimeOutException ロックタイムアウト
     */
    public String lockInventStart(InventWorkInfo tgt, boolean forceStart)
            throws ReadWriteException,
                OperatorException,
                NoPrimaryException,
                LockTimeOutException
    {
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();

        /* 検索条件の指定 */
        // 状態フラグ(削除以外)
        key.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 荷主コード
        key.setConsignorCode(tgt.getConsignorCode());
        // エリア
        key.setAreaNo(tgt.getAreaNo());
        // 棚
        key.setLocationNo(tgt.getLocationNo());
        // 商品コード
        key.setItemCode(tgt.getItemCode());
        // ロットNo.
        key.setLotNo(tgt.getLotNo());

        // 棚卸作業情報ロック
        InventWorkInfo[] inventWorkInfo =
                (InventWorkInfo[])handler.findForUpdate(key, HandlerSysDefines.WAIT_SEC_NOWAIT);

        if (inventWorkInfo.length > 1)
        {
            // 複数件該当した場合
            throw new NoPrimaryException();
        }
        else if (inventWorkInfo.length == 0)
        {
            // 該当するデータ無しの場合
            return null;
        }

        // 該当データの状態フラグをチェック
        if (inventWorkInfo[0].getStatusFlag().equals(SystemDefine.STATUS_FLAG_INVENTORY_WORKING))
        {
            // 状態フラグが「1:作業中」の場合
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        else if (inventWorkInfo[0].getStatusFlag().equals(SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED))
        {
            // 状態フラグが「2:作業済」の場合
            if (!forceStart)
            {
                // 強制開始フラグがFalse
                throw new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
            }
        }
        if (inventWorkInfo[0].getStatusFlag().equals(SystemDefine.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED))
        {
            // 状態フラグが「4:確定済み」の場合
            throw new OperatorException(OperatorException.ERR_INVENT_ALREADY_COMPLETED);
        }

        return inventWorkInfo[0].getJobNo();
    }

    /**
     * RFT作業中データのロックを行います<br>
     * 
     * @param jobNo 作業No
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 更新対象が存在しない
     * @throws LockTimeOutException ロックタイムアウト
     */
    public void lockRftResultStart(String jobNo)
            throws ReadWriteException,
                NotFoundException,
                LockTimeOutException
    {
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();

        /* 検索条件の指定 */
        // 状態フラグ(作業中)
        key.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING);
        // 作業No.
        key.setJobNo(jobNo);

        // 棚卸作業情報ロック
        InventWorkInfo[] inventWorkInfo =
                (InventWorkInfo[])handler.findForUpdate(key, HandlerSysDefines.WAIT_SEC_NOWAIT);

        if (inventWorkInfo.length == 0)
        {
            // 該当するデータ無しの場合
            throw new NotFoundException();
        }
    }

    /**
     * 棚卸作業開始処理を行います。<br>
     * 引数のキー項目から、該当作業の状態フラグを作業中に更新します。
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>作業No. (キー項目)
     * <li>ユーザID
     * <li>端末No.
     * </ol>
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException 作業Noに該当する棚卸作業情報が複数存在する
     * @throws NotFoundException 更新対象が存在しない
     */
    public void startInvent(InventWorkInfo tgt)
            throws ReadWriteException,
                NoPrimaryException,
                NotFoundException
    {
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey sKey = new InventWorkInfoSearchKey();

        /* 検索条件の指定 */
        // 作業No.
        sKey.setJobNo(tgt.getJobNo());

        // 棚卸作業情報検索
        InventWorkInfo inventWorkInfo = (InventWorkInfo)handler.findPrimary(sKey);

        if (inventWorkInfo == null)
        {
            // 該当するデータ無しの場合
            throw new NotFoundException();
        }

        // 棚卸作業情報更新キー
        InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();

        /* 検索条件の指定 */
        // 作業No.
        aKey.setJobNo(tgt.getJobNo());

        /* 更新項目のセット*/
        // 状態フラグ(作業中)
        aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING);
        // 元状態フラグ
        aKey.updatePreviousStatus(inventWorkInfo.getStatusFlag());
        // 棚卸ユーザID
        aKey.updateUserId(tgt.getUserId());
        // 元棚卸ユーザID
        aKey.updatePreUserId(inventWorkInfo.getUserId());
        // 棚卸端末No.
        aKey.updateTerminalNo(tgt.getTerminalNo());
        // 元棚卸端末No.
        aKey.updatePreTerminalNo(inventWorkInfo.getTerminalNo());
        // 最終更新処理名
        aKey.updateLastUpdatePname(getCallerName());

        // 棚卸作業情報更新
        handler.modify(aKey);
    }

    /**
     * 棚卸作業追加処理を行います。<br>
     * 新規棚卸データを状態フラグ=作業中で作成し、追加した作業の作業No.を返します。
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>スケジュールNo.
     * <li>荷主コード
     * <li>エリアNo.
     * <li>棚No.
     * <li>商品コード
     * <li>ロットNo.
     * <li>ユーザID
     * <li>端末No.
     * </ol>
     * @return 作業No
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException 既にデータが登録済みの場合
     */
    public String insertInvent(InventWorkInfo tgt)
            throws ReadWriteException,
                DataExistsException
    {
        // シーケンスハンドラー
        WMSSequenceHandler wHandler = new WMSSequenceHandler(getConnection());

        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 作業No.採番
        String jobNo = wHandler.nextInventJobNo();

        /* 棚卸作業情報エンティティ編集 */
        // 作業No.
        tgt.setJobNo(jobNo);
        // 状態フラグ(作業中)
        tgt.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING);
        // 登録処理名
        tgt.setRegistPname(getCallerName());
        // 最終更新処理名
        tgt.setLastUpdatePname(getCallerName());


        // 棚卸作業情報登録        
        handler.create(tgt);

        return jobNo;
    }

    /**
     * 棚卸作業キャンセル処理を行います。<br>
     * 作業中のデータを元状態に戻します。
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>作業No.
     * </ol>
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException 作業Noに該当する棚卸作業情報が複数存在する
     * @throws NotFoundException 更新対象が存在しない
     */
    public void cancel(InventWorkInfo tgt)
            throws ReadWriteException,
                NoPrimaryException,
                NotFoundException
    {
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey sKey = new InventWorkInfoSearchKey();

        // 棚卸作業情報更新キー
        InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();

        /* 検索条件の指定 */
        // 作業No.
        sKey.setJobNo(tgt.getJobNo());

        // 棚卸作業情報検索
        InventWorkInfo inventWorkInfo = (InventWorkInfo)handler.findPrimary(sKey);

        if (inventWorkInfo == null)
        {
            // 該当するデータ無しの場合
            throw new NotFoundException();
        }

        // 取得した元状態フラグのチェック
        if (StringUtil.isBlank(inventWorkInfo.getPreviousStatus()))
        {
            // 取得した元状態フラグがNullの場合

            /* 削除条件の指定 */
            // キーのクリア
            sKey.clear();
            // 作業No.
            sKey.setJobNo(tgt.getJobNo());
            // 棚卸作業情報削除       
            handler.drop(sKey);
        }
        else
        {
            // 取得した元状態フラグがNull以外の場合
            /* 更新条件の指定 */
            // 作業No.
            aKey.setJobNo(tgt.getJobNo());

            /* 更新内容の指定 */
            // 状態フラグ
            aKey.updateStatusFlag(inventWorkInfo.getPreviousStatus());
            // 元状態フラグ
            aKey.updatePreviousStatus("");
            // 棚卸ユーザID
            aKey.updateUserId(inventWorkInfo.getPreUserId());
            // 元棚卸ユーザID
            aKey.updatePreUserId("");
            // 棚卸端末No.
            aKey.updateTerminalNo(inventWorkInfo.getPreTerminalNo());
            // 元棚卸端末No.
            aKey.updatePreTerminalNo("");
            // 最終更新処理名
            aKey.updateLastUpdatePname(getCallerName());

            // 棚卸作業情報の更新
            handler.modify(aKey);
        }
    }

    /**
     * 棚卸結果入力処理を行います<br>
     * 引数から棚卸結果数を更新し、状態フラグを作業済(完了)に更新します。<br>
     * また、空棚の予定データ(状態フラグは未開始又は作業済）があれば、
     * そのデータの状態フラグを削除に更新します。
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>作業No.
     * <li>棚卸結果数
     * <li>ユーザーID
     * <li>端末No.
     * <li>作業秒数
     * <li>荷主コード
     * <li>エリアNo.
     * <li>棚No.
     * <li>商品コード
     * </ol>
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException 作業Noに該当する棚卸作業情報が複数存在する
     * @throws NotFoundException 更新対象が存在しない
     */
    public void inputResult(InventWorkInfo tgt)
            throws ReadWriteException,
                NoPrimaryException,
                NotFoundException
    {
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報更新キー
        InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();

        /* 対象データを作業済みに更新 */
        /* 更新条件の指定 */
        // (作業No.)
        aKey.setJobNo(tgt.getJobNo());
        
        /* 更新内容の指定 */
        // (状態フラグ:作業済み)
        aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);
        // (元状態フラグ)
        aKey.updatePreviousStatus("");
        // (棚卸結果数)
        aKey.updateResultStockQty(tgt.getResultStockQty());
        // (棚卸ユーザーID)
        aKey.updateUserId(tgt.getUserId());
        // (元棚卸ユーザID)
        aKey.updatePreUserId("");
        // (棚卸端末No.)
        aKey.updateTerminalNo(tgt.getTerminalNo());
        // (元棚卸端末No.)
        aKey.updatePreTerminalNo("");
        // (作業秒数)
        aKey.updateWorkSecond(tgt.getWorkSecond());
        // (最終更新処理名)
        aKey.updateLastUpdatePname(getCallerName());

        // 棚卸作業情報の更新
        handler.modify(aKey);

        if (!StringUtil.isBlank(tgt.getItemCode()))
        {
            /* 空棚を削除に更新 */
            aKey.clear();
            /* 更新条件の指定 */
            // (状態フラグ:未開始 or 作業済み)
            String[] status = {
                SystemDefine.STATUS_FLAG_UNSTART,
                SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED
            };
            aKey.setStatusFlag(status);
            // (荷主コード)
            aKey.setConsignorCode(tgt.getConsignorCode());
            // (エリア)
            aKey.setAreaNo(tgt.getAreaNo());
            // (棚)
            aKey.setLocationNo(tgt.getLocationNo());
            // (商品コード)
            aKey.setItemCode("");
            
            /* 更新内容の指定 */
            // (状態フラグ)
            aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
            // (最終更新処理名)
            aKey.updateLastUpdatePname(getCallerName());
    
            try
            {
                // 棚卸作業情報の更新
                handler.modify(aKey);
            }
            catch (NotFoundException e)
            {
                // 空棚予定が無い場合もあるので、該当しない場合は無視（該当なしでもOK）
            }
        }
    }

    /**
     * 空棚確定処理を行います<br>
     * その棚のデータをロックします。<br>
     * 該当データ無しの場合、範囲チェックで範囲内の場合は、
     * 新規レコード追加（空棚を作業済で作成）します。<br>
     * 該当データ有りの場合は、作業中のデータが無いかチェックし、
     * 未作業又は作業済のデータの棚卸数を0に更新します。
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>荷主コード
     * <li>エリアNo.
     * <li>棚No.
     * <li>ユーザーID
     * <li>端末No.
     * </ol>
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException 既にデータが登録済みの場合
     * @throws OperatorException 状態フラグが「未作業」以外の場合内容が返されます。
     * @throws NotFoundException 更新対象が存在しない
     * @throws NoPrimaryException 作業データ重複
     * @throws LockTimeOutException ロックタイムアウト
     */
    public void inputEmpInvent(InventWorkInfo tgt)
            throws ReadWriteException,
                DataExistsException,
                OperatorException,
                NotFoundException,
                NoPrimaryException,
                LockTimeOutException
    {
        // シーケンスハンドラー
        WMSSequenceHandler wHandler = new WMSSequenceHandler(getConnection());

        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey sKey = new InventWorkInfoSearchKey();

        // 棚卸作業情報更新キー
        InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();

        // 棚卸設定情報コントローラー
        InventSettingController inventSettingController =
                new InventSettingController(super.getConnection(), super.getClass());

        /* 検索条件の指定 */
        // 状態フラグ(削除以外)
        sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 荷主コード
        sKey.setConsignorCode(tgt.getConsignorCode());
        // エリアNo.
        sKey.setAreaNo(tgt.getAreaNo());
        // 棚
        sKey.setLocationNo(tgt.getLocationNo());

        // 棚卸作業情報ロック
        InventWorkInfo[] inventWorkInfo = (InventWorkInfo[])handler.findForUpdate(sKey);

        if (inventWorkInfo.length == 0)
        {
            // 該当するデータ無しの場合
            // 棚卸設定情報コントローラの棚卸範囲チェックメソッドを呼び出す
            String scheduleNo =
                    inventSettingController.rangeCheck(tgt.getConsignorCode(), tgt.getAreaNo(), tgt.getLocationNo());
            // 作業No.採番
            String jobNo = wHandler.nextInventJobNo();

            /* 棚卸作業情報エンティティ編集 */
            // 作業No.
            tgt.setJobNo(jobNo);
            // スケジュールNo.
            tgt.setScheduleNo(scheduleNo);
            // 状態フラグ(作業済み)
            tgt.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);
            // 登録処理名
            tgt.setRegistPname(getCallerName());
            // 最終更新処理名
            tgt.setLastUpdatePname(getCallerName());

            // 棚卸作業情報登録        
            handler.create(tgt);
        }
        else
        {
            // 該当データ有りの場合
            // 作業中データのチェック
            for (int i = 0; i < inventWorkInfo.length; i++)
            {
                if (inventWorkInfo[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_INVENTORY_WORKING))
                {
                    // 状態フラグが「作業中」のデータが存在した場合
                    throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
                }
            }

            // 取得データの状態フラグが、0:未開始、又は、2:作業済 の場合、棚卸作業情報の更新を行う
            for (int i = 0; i < inventWorkInfo.length; i++)
            {
                // キーのクリア
                aKey.clear();

                if (inventWorkInfo[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART)
                        || inventWorkInfo[i].getStatusFlag().equals(
                                SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED))
                {
                    // 取得データの状態フラグが、0:未作業、又は、2:作業済

                    /* 更新条件の指定 */
                    // 作業No.
                    aKey.setJobNo(inventWorkInfo[i].getJobNo());

                    /* 更新内容の指定 */
                    // 状態フラグ(作業済み)
                    aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);
                    // 元状態フラグ
                    aKey.updatePreviousStatus("");
                    // 棚卸結果数
                    aKey.updateResultStockQty(0);
                    // 棚卸ユーザーID
                    aKey.updateUserId(tgt.getUserId());
                    // 元棚卸ユーザID
                    aKey.updatePreUserId("");
                    // 棚卸端末No.
                    aKey.updateTerminalNo(tgt.getTerminalNo());
                    // 元棚卸端末No.
                    aKey.updatePreTerminalNo("");
                    // 作業秒数
                    aKey.updateWorkSecond(0);
                    // 最終更新処理名
                    aKey.updateLastUpdatePname(getCallerName());

                    // 棚卸作業情報の更新
                    handler.modify(aKey);

                }
            }
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: InventWorkInfoController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
