// $Id: AreaModifySCH.java 5282 2009-10-27 10:49:25Z fukuwa $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.AreaModifySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AllocatePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AreaFinder;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * エリアマスタ修正・削除のスケジュール処理を行います。
 * 
 * @version $Revision: 5282 $, $Date: 2009-10-27 19:49:25 +0900 (火, 27 10 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: fukuwa $
 */
public class AreaModifySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public AreaModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ２画面遷移入力チェック<BR>
     * checkParamの内容をもとに、該当データの存在チェックを行う。<BR>
     * 該当データが存在した場合はtrueを返す。<BR>
     * パラメータの内容に問題がある場合はfalseを返す。<BR>
     * <BR>
     * @param checkParam 検索条件
     * @return 該当データが存在する場合：<CODE>true</CODE>
     *          該当データが存在しない場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean nextCheck(AreaModifySCHParams checkParam)
            throws CommonException
    {
        // エリアマスタデータベースハンドラ
        AreaHandler aHandler = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey akey = new AreaSearchKey();

        //検索キーのセット
        // エリアNo
        akey.setAreaNo(checkParam.getString(AREA_NO));
        // エリア種別(AS/RS以外)
        akey.setAreaType(Area.AREA_TYPE_ASRS, "!=");

        // エリアマスタの検索
        if (aHandler.count(akey) > 0)
        {
            // データが存在する場合はtrueを返却
            return true;
        }

        // (6003011)対象データはありませんでした。
        setMessage("6003011");

        // データが存在しない場合はfalseを返却
        return false;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            // エリアマスタファインダークラスを生成
            finder = new AreaFinder(getConnection());
            finder.open(true);
            
            // 検索処理実行
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                // 存在しなかった場合は空配列を返却
                return new ArrayList<Params>();
            }

            // 画面表示用にパラメータクラスにセットし返却
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * 修正入力チェック<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 修正データが使用されていない場合：<CODE>true</CODE>
     *          修正データが使用されている場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean check(AreaModifySCHParams checkParam)
            throws CommonException
    {
        // 修正処理の場合
        if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(checkParam.getString(PROCESS_FLAG)))
        {
            // 他テーブル存在チェック
            if (!this.existRelation(checkParam))
            {
                // (MSG-W0010)修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");

                // データが使用されている場合はfalseを返却
                return false;
            }
            // 仮置エリア・入荷エリア指定チェック
            if (!existArea(checkParam))
            {
                // (MSG-W0010)修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");

                // データが使用されている場合はfalseを返却
                return false;
            }
        }
        // 削除処理の場合
        else if (MasterInParameter.PROCESS_FLAG_DELETE.equals(checkParam.getString(PROCESS_FLAG)))
        {
            // 仮置エリア・入荷エリア指定チェック
            if (!existArea(checkParam))
            {
                // (MSG-W0058)削除を行うデータが仮置エリアまたは入荷エリア使用されています。削除を行いますか？
                setDispMessage("MSG-W0058");

                // データが使用されている場合はfalseを返却
                return false;
            }
            //
            if (!this.existAreaNothing(checkParam))
            {
                // (MSG-W0059)移動中エリア、仮置エリア、入荷エリア以外のエリアが存在しなくなります。削除を行いますか？
                setDispMessage("MSG-W0059");

                // データが使用されている場合はfalseを返却
                return false;
            }
        }
        // データが使用されていない場合はtrueを返却
        return true;
    }

    /**
     * エリアマスタ修正･削除スケジュール開始処理<BR>
     * startParamsで指定するParameterクラスはAreaModifySCHParams型であること。<BR>
     * <BR>
     * @param startParam 修正、削除内容
     * @return 正常に修正削除処理が終了した場合：<CODE>true</CODE>
     *          修正削除処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(AreaModifySCHParams startParam)
            throws CommonException
    {
        // エリアマスタエンティティ配列
        Area[] areaEntity = null;
        // エリアマスタデータベースハンドラ
        AreaHandler sHandler = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey skey = new AreaSearchKey();
        // エリアマスタ更新キー
        AreaAlterKey akey = new AreaAlterKey();
        // エリアマスタコントローラー
        AreaController aCtl = new AreaController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中・排他チェック
            if (!canStart() || isLoadData() || !isNoModify(startParam))
            {
                return false;
            }

            // 検索キーのセット
            // 検索キーのクリア
            skey.clear();
            // エリアNo
            skey.setAreaNo(startParam.getString(AREA_NO));

            // 上記条件より一件のみ取得
            Area oldMaster = (Area)sHandler.findPrimary(skey);

            // 修正処理
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(startParam.getString(PROCESS_FLAG)))
            {
                // 検索キーのセット
                // エリアNo
                akey.setAreaNo(startParam.getString(AREA_NO));

                // 更新キーのセット
                // エリア名称
                akey.updateAreaName(startParam.getString(AREA_NAME));
                // 移動先仮置エリア
                akey.updateTemporaryArea(startParam.getString(TEMPORARY_AREA));
                // 仮置在庫作成区分
                if (startParam.getBoolean(MOVE_TEMPORARY_STORAGE))
                {
                    akey.updateTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_ALL);
                }
                else
                {
                    akey.updateTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_NONE);
                }
                // 空棚検索条件
                akey.updateVacantSearchType(startParam.getString(VACANT_SEARCH_TYPE));
                // 入荷エリア
                akey.updateReceivingArea(startParam.getString(RECEIVING_AREA));
                // 最終更新処理名
                akey.updateLastUpdatePname(cName);

                // エリアマスタ(更新)
                sHandler.modify(akey);

                // 更新後のエリアマスタ取得
                // 検索キーのクリア
                skey.clear();
                // 検索キーのセット
                // エリアNo
                skey.setAreaNo(startParam.getString(AREA_NO));

                // 上記検索より一件のみ取得
                Area newMaster = (Area)sHandler.findPrimary(skey);


                // エリアマスタ更新履歴情報(新規登録)
                aCtl.insertHistory(oldMaster, newMaster, SystemDefine.UPDATE_KIND_MODIFY,
                        getWmsUserInfo().getDfkUserInfo());

                // (6001004)修正しました。
                setMessage("6001004");
            }
            // 削除処理
            else
            {
                // 削除可能データかチェックを行う
                if (!checkDelete(startParam))
                {
                    // 異常の場合はfalseを返却
                    return false;
                }

                // 検索キーのセット
                // エリアNo
                skey.setAreaNo(startParam.getString(AREA_NO));

                // 取得キーのセット
                // システム管理
                skey.setCollect(Area.MANAGEMENT_TYPE);

                // 上記条件より対象データのシステム管理区分を取得
                areaEntity = (Area[])sHandler.find(skey);
                if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(areaEntity[0].getManagementType()))
                {
                    // (6023158)システム管理エリアのため、修正・削除できません
                    setMessage("6023158");

                    // 異常の場合はfalseを返却
                    return false;
                }

                // 削除キーのセット
                // エリアNo
                skey.setAreaNo(startParam.getString(AREA_NO));

                // エリアマスタ(削除)
                sHandler.drop(skey);


                // エリアマスタ更新履歴情報(新規登録)
                aCtl.insertHistory(oldMaster, SystemDefine.UPDATE_KIND_DELETE, getWmsUserInfo().getDfkUserInfo());

                // (6001005)削除しました。
                setMessage("6001005");
            }
            // 正常の場合はtrueを返却
            return true;
        }
        // DBアクセスエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");

            // 異常の場合はfalseを返却
            return false;
        }
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage("6023004");

            // 異常の場合はfalseを返却
            return false;
        }
        // データがロックされていた場合
        catch (LockTimeOutException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage("6023004");

            // 異常の場合はfalseを返却
            return false;
        }
    }

    /**
     * 削除可能データかチェックを行ないます。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 警告がない場合：<CODE>true</CODE>
     *          警告がある場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean checkDelete(AreaModifySCHParams checkParam)
            throws CommonException
    {
        // 他テーブル存在チェック
        if (!this.existRelation(checkParam))
        {
            // (6023003)削除を行うデータが使用されています。
            setMessage("6023003");

            // データが使用されている場合はfalseを返却
            return false;
        }
        // データが使用されていない場合はtrueを返却
        return true;
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
    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // エリアマスタ検索キー
        AreaSearchKey akey = new AreaSearchKey();

        // 検索キーのセット
        // エリアNo
        akey.setAreaNo(p.getString(AREA_NO));

        // 取得キーセット
        // エリア名称
        akey.setCollect(Area.AREA_NO);
        // エリア名称
        akey.setCollect(Area.AREA_NAME);
        // エリア種別
        akey.setCollect(Area.AREA_TYPE);
        // 棚管理方式
        akey.setCollect(Area.LOCATION_TYPE);
        // 移動先仮置エリア
        akey.setCollect(Area.TEMPORARY_AREA);
        // 空棚検索条件
        akey.setCollect(Area.VACANT_SEARCH_TYPE);
        // 入荷エリア
        akey.setCollect(Area.RECEIVING_AREA);
        // 最終更新日時
        akey.setCollect(Area.LAST_UPDATE_DATE);
        // 登録日時
        akey.setCollect(Area.REGIST_DATE);       
        // 棚フォーマット
        akey.setCollect(Area.LOCATION_STYLE);     
        // セットした検索キーを返却
        return akey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Area[] entities = (Area[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を作成
        List<Params> result = new ArrayList<Params>();

        // 取得データ件数分、繰り返す
        for (Area ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // エリアNo.
            param.set(AREA_NO, ent.getAreaNo());
            // エリア種別
            param.set(AREA_TYPE, ent.getAreaType());
            // 棚管理方式
            param.set(LOCATION_TYPE, ent.getLocationType());
            // エリア名称
            param.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            // 移動先仮置エリア
            param.set(TEMPORARY_AREA, ent.getValue(Area.TEMPORARY_AREA, ""));
            // 空棚検索条件
            param.set(VACANT_SEARCH_TYPE, ent.getVacantSearchType());
            // 入荷エリア
            param.set(RECEIVING_AREA, ent.getValue(Area.RECEIVING_AREA, ""));
            // 最終更新日
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // 棚フォーマット
            param.set(LOCATION_STYLE, ent.getLocationStyle());

            // セットした返却データを配列に格納
            result.add(param);
        }
        // 設定されたリストを返却
        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * エリアマスタ同時更新処理チェック<BR>
     * 対象データが他の端末で更新されていないか確認します。<BR>
     * <BR> 
     * @param checkParam エリアマスタチェック条件
     * @return 更新されていない場合：<CODE>true</CODE>
     *          更新されている場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isNoModify(AreaModifySCHParams checkParam)
            throws CommonException
    {
        // エリアマスタデータベースハンドラ
        AreaHandler aHandler = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey akey = new AreaSearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        akey.setAreaNo(checkParam.getString(AREA_NO));
        // 最終更新日(引数の最終更新日と同一)
        akey.setKey(Area.LAST_UPDATE_DATE, checkParam.getDate(LAST_UPDATE_DATE));

        // 検索処理
        if (aHandler.count(akey) > 0)
        {
            // 更新されていない場合はtrueを返却
            return true;
        }

        // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください。
        setMessage("6023004");

        // 更新されている場合はfalseを返却
        return false;
    }

    /**
     * エリアマスタ関連テーブルチェック<BR>
     * 対象データがが他のテーブルに存在しないか確認します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRelation(AreaModifySCHParams checkParam)
            throws CommonException
    {
        // 在庫情報データ存在チェック
        if (!existStock(checkParam))
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // 在庫更新履歴情報データ存在チェック
        if (!existStockHistory(checkParam))
        {
            // データが存在する場合はfalseを返却
            return false;
        }

        // 入出庫作業情報データ存在チェック
        if (!existWorkInfo(checkParam))
        {
            // データが存在する場合はfalseを返却
            return false;
        }

        // 引当パターン情報データ存在チェック
        if (!existAllocatePriority(checkParam))
        {
            // データが存在する場合はfalseを返却
            return false;
        }

        // 棚マスタ情報データ存在チェック
        if (!existLocate(checkParam))
        {
            // データが存在する場合はfalseを返却
            return false;
        }

        // 商品固定棚情報データ存在チェック
        if (!existFixedLocateInfo(checkParam))
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 在庫情報データ存在チェック<BR>
     * <BR> 
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existStock(AreaModifySCHParams inParam)
            throws CommonException
    {
        // 在庫情報データベースハンドラ
        StockHandler stockHndlr = new StockHandler(getConnection());
        // 在庫情報検索キー
        StockSearchKey stockKey = new StockSearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        stockKey.setAreaNo(inParam.getString(AREA_NO));

        // 検索処理
        if (stockHndlr.count(stockKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * 在庫更新履歴情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existStockHistory(AreaModifySCHParams inParam)
            throws CommonException
    {
        // 在庫更新履歴情報データベースハンドラ
        StockHistoryHandler stockHistoryHndlr = new StockHistoryHandler(getConnection());
        // 在庫更新履歴情報検索キー
        StockHistorySearchKey stockHistoryKey = new StockHistorySearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        stockHistoryKey.setAreaNo(inParam.getString(AREA_NO));

        // 検索処理
        if (stockHistoryHndlr.count(stockHistoryKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * 入出庫作業情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existWorkInfo(AreaModifySCHParams inParam)
            throws CommonException
    {
        // 入出庫作業情報データベースハンドラ
        WorkInfoHandler workInfoHndlr = new WorkInfoHandler(getConnection());
        // 入出庫作業情報検索キー
        WorkInfoSearchKey workInfoKey = new WorkInfoSearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        workInfoKey.setPlanAreaNo(inParam.getString(AREA_NO));
        // 状態フラグ(完了ではない)
        workInfoKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION, "!=");
        // 状態フラグ(削除ではない)
        workInfoKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (workInfoHndlr.count(workInfoKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * 引当パターン情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existAllocatePriority(AreaModifySCHParams inParam)
            throws CommonException
    {
        // 引当パターン情報データベースハンドラ
        AllocatePriorityHandler allocateHndlr = new AllocatePriorityHandler(getConnection());
        // 引当パターン情報検索キー
        AllocatePrioritySearchKey allocateKey = new AllocatePrioritySearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        allocateKey.setAllocateArea(inParam.getString(AREA_NO));

        // 検索処理
        if (allocateHndlr.count(allocateKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * 棚マスタ情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existLocate(AreaModifySCHParams inParam)
            throws CommonException
    {
        // 棚マスタデータベースハンドラ
        LocateHandler locateHndlr = new LocateHandler(getConnection());
        // 棚マスタ検索キー
        LocateSearchKey locateKey = new LocateSearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        locateKey.setAreaNo(inParam.getString(AREA_NO));

        // 検索処理
        if (locateHndlr.count(locateKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * 商品固定棚情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existFixedLocateInfo(AreaModifySCHParams inParam)
            throws CommonException
    {
        // 商品固定棚情報データベースハンドラ
        FixedLocateInfoHandler fixedLocateHndlr = new FixedLocateInfoHandler(getConnection());
        // 商品固定棚情報コントローラー
        FixedLocateInfoSearchKey fixedLocateKey = new FixedLocateInfoSearchKey();

        // 検索キーのセット
        // エリアNo.(引数のエリアNo.と同一)
        fixedLocateKey.setAreaNo(inParam.getString(AREA_NO));

        // 検索処理
        if (fixedLocateHndlr.count(fixedLocateKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * エリアマスタ情報データ存在チェック<BR>
     * 対象データのエリアが仮置エリア、または入荷エリアに設定されているかチェックを行う。<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existArea(AreaModifySCHParams inParam)
            throws CommonException
    {
        // エリアマスタデータベースハンドラ
        AreaHandler areaHndlr = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey areaKey = new AreaSearchKey();

        // 検索キーのセット
        // 仮置エリア(引数のエリアと同一)
        areaKey.setTemporaryArea(inParam.getString(AREA_NO), "=", "(", "", false);
        // 入荷エリア(引数のエリアと同一)
        areaKey.setReceivingArea(inParam.getString(AREA_NO), "=", "", ")", true);

        // 検索処理
        if (areaHndlr.count(areaKey) > 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }

    /**
     * エリアマスタ情報データ存在チェック<BR>
     * エリア種別(7：入荷エリア、8：移動中エリア、9：仮置エリア)以外の件数チェックを行う。<BR>
     * 但し、対象データのエリアは含まない。<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が1件以上の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が0件以下の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existAreaNothing(AreaModifySCHParams inParam)
            throws CommonException
    {
        // エリアマスタデータベースハンドラ
        AreaHandler areaHndlr = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey areaKey = new AreaSearchKey();

        // 検索キーのセット
        // エリア種別(移動エリアではない)
        areaKey.setAreaType(SystemDefine.AREA_TYPE_MOVING, "!=");
        // エリア種別(仮置エリアではない)
        areaKey.setAreaType(SystemDefine.AREA_TYPE_TEMPORARY, "!=");
        // エリア種別(入荷エリアではない)
        areaKey.setAreaType(SystemDefine.AREA_TYPE_RECEIVING, "!=");
        // エリアNo(引数のエリアNo.ではない)
        areaKey.setAreaNo(inParam.getString(AREA_NO), "!=");

        // 検索処理
        if (areaHndlr.count(areaKey) >= 1)
        {
            // データが存在している場合はtrueを返却
            return true;
        }
        // データが存在しない場合はfalseを返却
        return false;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
