// $Id: WorkingUnitQtyMainteSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.analysis.schedule.WorkingUnitQtyMainteSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.analysis.schedule.WorkingUnitQtyMainteSCHParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingUnit;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 作業単位数設定のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class WorkingUnitQtyMainteSCH
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
    /**
     * 荷主マスタコントローラークラス
     */
    private ConsignorController _consignorController = null;

    /**
     * 商品マスタコントローラークラス
     */
    private ItemController _itemController = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public WorkingUnitQtyMainteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        
        _consignorController = new ConsignorController(conn, parent);
        _itemController = new ItemController(conn, parent);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** 
     * マスタ登録チェックを行います。<BR>
     * 正常の時はtrue、異常の時はfalseを返します。
     *
     * @param searchParam <CODE>WorkingUnitQtyMainteSCHParams</CODE>クラスのインスタンス。<BR>
     * @return 入力チェックの結果を返します。正常はtrue、異常はfalse<BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(WorkingUnitQtyMainteSCHParams param)
            throws CommonException
    {
        // 日次更新処理中チェック
        if (isDailyUpdate())
        {
            // (6023011)日次更新を行っているため、処理できません。
            setMessage("6023011");

            // 問題がある場合はfalseを返却
            return false;
        }

        // 荷主コード存在チェック
        if (!_consignorController.exists(param.getString(CONSIGNOR_CODE)))
        {
            // (6023040)荷主コードがマスタに登録されていません。
            setMessage("6023040");

            // 問題がある場合はfalseを返却
            return false;
        }

        // 商品コード存在チェック
        if (!_itemController.exists(param.getString(ITEM_CODE), param.getString(CONSIGNOR_CODE)))
        {
            // (6023021)商品コードがマスタに登録されていません。
            setMessage("6023021");

            // 問題がある場合はfalseを返却
            return false;
        }

        // 問題無い場合はtrueを返却
        return true;
    }

    /**
     * 画面目から入力された荷主コード＆商品コードをキーにして作業単位数マスタより作業単位数を返します。<BR>
     * 詳しい動作はクラス説明を参照してください。
     *
     * @param searchParam 表示データ取得条件を持つ<CODE>AnalysisInParameter</CODE>クラスのインスタンス。<BR>
     *         <CODE>AnalysisInParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @return 検索結果を持つ<CODE>WorkingUnitQtyMainteParameter</CODE>インスタンスの配列。<BR>
     *         該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public List<Params> query(WorkingUnitQtyMainteSCHParams param)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;

        try
        {
            // エリアマスタファインダークラスを生成
            finder = new WorkingUnitFinder(getConnection());
            finder.open(true);

            // 検索処理実行
            if (!canLowerDisplay(finder.search(createSearchKey(param))))
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
     * 画面から入力された内容をパラメータとして受け取り、<BR>
     * 作業単位数マスタ登録・更新のスケジュールを開始します。<BR>
     * スケジュール処理が成功した場合はtrueを返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
     * 詳しい動作はクラス説明の項を参照してください。
     *
     * @param startParams 設定内容を持つ<CODE>AnalysisInParameter</CODE>クラスのインスタンスの配列。 <BR>
     *         <CODE>AnalysisInParameter</CODE>インスタンス以外が指定された場合<BR>
     *         <CODE>ScheduleException</CODE>をスローします。<BR>
     *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
     * @return スケジュール結果
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean startSCH(WorkingUnitQtyMainteSCHParams ps)
            throws CommonException
    {
        // 作業単位数マスタデータベースハンドラ
        WorkingUnitHandler handler = new WorkingUnitHandler(getConnection());
        // 作業単位数マスタ検索キー
        WorkingUnitSearchKey wKey = new WorkingUnitSearchKey();
        // 処理名を取得します。
        String cName = getClass().getSimpleName();

        // 荷主コード
        wKey.setConsignorCode(ps.getString(CONSIGNOR_CODE));
        // 商品コード
        wKey.setItemCode(ps.getString(ITEM_CODE));

        // 作業単位数が0の場合
        if (ps.getInt(WORK_UNIT_QTY) <= 0)
        {
            try
            {
                handler.drop(wKey);
            }
            catch (NotFoundException e)
            {
                // 対象データが存在しない場合は、無視します。
            }

            // (6001008)デフォルトにしました。
            setMessage("6001008");

            // 正常の場合はfalseを返却
            return true;
        }

        // 更新対象データを検索します。
        try
        {
            // 検索
            WorkingUnit[] wuArr = (WorkingUnit[])handler.findForUpdate(wKey);

            // 更新対象データが見当たらなかった場合
            boolean notFound = (null == wuArr) || (0 == wuArr.length);
            if (notFound)
            {
                // 作業単位数マスタ(新規登録)
                create(ps, handler, cName);

                // (6001003)登録しました。
                setMessage("6001003");
            }
            else
            {
                // 作業単位数マスタ(更新)
                update(ps, handler, cName);

                // (6001004)修正しました。
                setMessage("6001004");
            }
            // 正常の場合はtrueを返却
            return true;
        }
        // データがロックされていた場合
        catch (LockTimeOutException e)
        {
            // (6023114)他端末で処理中のため、処理を中断しました。
            setMessage("6023114");

            // 異常の場合はfalseを返却
            return false;
        }
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            //(6023114)他端末で処理中のため、処理を中断しました。
            setMessage("6023114");

            // 異常の場合はfalseを返却
            return false;
        }
        // データが重複した場合
        catch (DataExistsException e)
        {
            // (6023114)他端末で処理中のため、処理を中断しました。
            setMessage("6023114");

            // 異常の場合はfalseを返却
            return false;
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
    /**
     * 検索条件をセットします。<BR>
     * <BR>
     * @param param 検索条件を含むScheduleParams
     * @return aKey
     */
    protected SearchKey createSearchKey(WorkingUnitQtyMainteSCHParams param)
    {
        // 作業単位数マスタ検索キー
        WorkingUnitSearchKey wKey = new WorkingUnitSearchKey();

        // 検索キーのセット
        // 荷主コード
        wKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 商品コード
        wKey.setItemCode(param.getString(ITEM_CODE));

        // 取得キーセット
        // 荷主コード
        wKey.setCollect(WorkingUnit.CONSIGNOR_CODE);
        // 商品コード
        wKey.setCollect(WorkingUnit.ITEM_CODE);
        // 作業単位数
        wKey.setCollect(WorkingUnit.WORK_UNIT_QTY);

        // セットした検索キーを返却
        return wKey;
    }

    /**
     * 表示情報を取得します。<BR>
     * <BR>
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws CommonException
    {
        // 最大表示件数分検索結果を取得する
        WorkingUnit[] entities = (WorkingUnit[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を作成
        List<Params> result = new ArrayList<Params>();

        // 取得データ件数分、繰り返す
        for (WorkingUnit ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 作業単位数
            param.set(WORK_UNIT_QTY, ent.getWorkUnitQty());

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
     * システム定義情報より作業日を取得します。
     * @return 作業日(YYYYMMDD)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private String getWorkDate()
            throws CommonException
    {
        // システム定義コントローラー
        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());

        // 作業日を返却
        return controller.getWorkDay();
    }

    /**
     * パラメータで指定された商品コードの作業単位数マスタを新規に登録します。
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param workUnitQty 作業単位数
     * @param handler ハンドラ
     * @param processName プロセス名
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private void create(ScheduleParams ps, WorkingUnitHandler handler, String cName)
            throws CommonException
    {
        // 作業単位数エンティティ
        WorkingUnit wkUnit = new WorkingUnit();

        // 荷主コード
        wkUnit.setConsignorCode(ps.getString(CONSIGNOR_CODE));
        // 商品コード
        wkUnit.setItemCode(ps.getString(ITEM_CODE));
        // 作業単位数
        wkUnit.setWorkUnitQty(ps.getInt(WORK_UNIT_QTY));
        // 最終使用日
        wkUnit.setLastUseWorkDay(getWorkDate());
        // 登録処理名
        wkUnit.setRegistPname(cName);
        // 最終更新処理名
        wkUnit.setLastUpdatePname(cName);

        // 作業単位数マスタ(新規登録)
        handler.create(wkUnit);
    }

    /**
     * パラメータで指定された商品コードの作業単位数マスタを更新します。
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param workUnitQty 作業単位数
     * @param handler ハンドラ
     * @param processName プロセス名
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private void update(ScheduleParams ps, WorkingUnitHandler handler, String cName)
            throws CommonException
    {
        // 作業単位数マスタ更新キー
        WorkingUnitAlterKey aKey = new WorkingUnitAlterKey();

        // 荷主コード
        aKey.setConsignorCode(ps.getString(CONSIGNOR_CODE));
        // 商品コード
        aKey.setItemCode(ps.getString(ITEM_CODE));
        // 作業単位数
        aKey.updateWorkUnitQty(ps.getInt(WORK_UNIT_QTY));
        // 最終使用日
        aKey.updateLastUseWorkDay(getWorkDate());
        // 最終更新処理名
        aKey.updateLastUpdatePname(cName);

        // 作業単位数マスタ(更新)
        handler.modify(aKey);
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
