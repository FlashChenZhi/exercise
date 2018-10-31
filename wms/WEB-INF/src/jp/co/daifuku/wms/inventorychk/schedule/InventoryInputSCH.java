// $Id: InventoryInputSCH.java 4812 2009-08-10 11:05:22Z kumano $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.schedule.InventoryInputSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.InventSettingController;
import jp.co.daifuku.wms.base.controller.InventWorkInfoController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventSettingFinder;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 棚卸結果入力のスケジュール処理を行います。
 * 
 * @version $Revision: 4812 $, $Date: 2009-08-10 20:05:22 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class InventoryInputSCH
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
    public InventoryInputSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
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
            finder = new InventWorkInfoFinder(getConnection());
            finder.open(true);

            // search db and get display count.
            int dispCount = finder.search(createSearchKey(p));
            // check and set message by the display count. 
            if (!canLowerDisplay(dispCount))
            {
                return new ArrayList<Params>();
            }

            return getDisplayData(finder);
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {

        InventoryInParameter param = null;

        List<InventoryInParameter> listParam = new ArrayList<InventoryInParameter>();

        for (ScheduleParams startParam : ps)
        {
            param = new InventoryInParameter(getWmsUserInfo());
            param.setSettingUnitKey(startParam.getString(LIST_WORK_NO));
            param.setConditionSelect(startParam.getBoolean(CONDITION_SELECT));
            param.setNewDispFlag(startParam.getBoolean(NEWDATE_FLAG));
            param.setAreaNo(startParam.getString(AREA_NO));
            param.setLocationNo(startParam.getString(LOCATION_NO));
            param.setItemCode(startParam.getString(ITEM_CODE));
            param.setLotNo(startParam.getString(LOT_NO));
            param.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            param.setProcessFlag(startParam.getString(PROCESS_FLAG));

            param.setStatusFlag(startParam.getString(STATUSFLAG));

            param.setEnteringQty(startParam.getInt(ENTERING_QTY));

            param.setStockCaseQty(startParam.getInt(STOCK_CASE_QTY));
            param.setStockPieceQty(startParam.getInt(STOCK_PIECE_QTY));

            param.setStockQty(startParam.getInt(STOCK_PIECE_QTY)
                    + (startParam.getInt(STOCK_CASE_QTY) * startParam.getInt(ENTERING_QTY)));

            param.setResultCaseQty(startParam.getInt(RESULT_CASE_QTY));
            param.setResultPieceQty(startParam.getInt(RESULT_PIECE_QTY));

            param.setInventoryInsResultQtyLong(startParam.getInt(RESULT_PIECE_QTY)
                    + (startParam.getInt(RESULT_CASE_QTY) * startParam.getInt(ENTERING_QTY)));

            param.setAddDataFlag(startParam.getBoolean(NEWDATE_FLAG));

            param.setTerminalNo(getWmsUserInfo().getTerminalNo());
            param.setUserId(getWmsUserInfo().getUserId());
            param.setRowNo(startParam.getRowIndex());
            param.setMasterFlag(startParam.getBoolean(MASTER));
            listParam.add(param);
        }

        InventoryInParameter[] inparam = new InventoryInParameter[listParam.size()];
        listParam.toArray(inparam);

        // 棚卸結果入力
        if (InventoryInParameter.PROCESS_FLAG_INVENTORY_INPUT.equals(inparam[0].getProcessFlag()))
        {
            if (input(inparam))
            {
                return true;
            }
        }
        // 帳票印刷
        else if (InventoryInParameter.PROCESS_FLAG_INVENTORY_PRINT.equals(inparam[0].getProcessFlag()))
        {
            if (print(inparam[0]))
            {
                return true;
            }
        }
        // 新規データ追加情報削除処理
        else if (InventoryInParameter.PROCESS_FLAG_INVENTORY_DELETE.equals(inparam[0].getProcessFlag()))
        {
            if (delete(inparam[0]))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * パラメータを条件に検索したデータ件数を返します。<BR>
     * エリアNo.からエリアタイプを判断しフリーエリアの場合false、それ以外の場合trueを返す。
     * <BR>
     * @param checkParam 検索条件パラメータ
     * @return 対象データの件数
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    public boolean check(Parameter checkParam)
            throws CommonException
    {
        InventoryInParameter param = (InventoryInParameter)checkParam;


        AreaHandler areHnd = new AreaHandler(getConnection());
        AreaSearchKey aSkey = new AreaSearchKey();
        aSkey.setAreaNo(param.getAreaNo());
        aSkey.setLocationType(Area.LOCATION_TYPE_FREE);

        // フリーエリアの場合
        if (areHnd.count(aSkey) > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * パラメータを条件に検索したデータ件数を返します。<BR>
     * エリアNo.からエリアタイプを判断しフリーエリアの場合false、それ以外の場合trueを返す。
     * <BR>
     * @param p 検索条件パラメータ
     * @return 対象データの件数
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        AreaHandler areHnd = new AreaHandler(getConnection());
        AreaSearchKey aSkey = new AreaSearchKey();
        aSkey.setAreaNo(p.getString(AREA_NO));
        aSkey.setLocationType(Area.LOCATION_TYPE_FREE);

        // フリーエリアの場合
        if (areHnd.count(aSkey) > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> initCheck()
            throws CommonException
    {
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        Params param = new Params();
        param.set(STOCK_MASTER, systemController.hasMasterPack());

        List<Params> result = new ArrayList<Params>();

        result.add(param);

        return result;
    }

    /**
     * パラメータを条件に検索したデータ件数を返します。<BR>
     * <BR>
     * @param p 検索条件パラメータ
     * @return 対象データの件数
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    public int count(ScheduleParams p)
            throws CommonException
    {
        //パラメータで指定された内容を渡して、印刷処理のインスタンスを生成する
        InventoryInParameter param = null;

        param = new InventoryInParameter(getWmsUserInfo());
        param.setSettingUnitKey(p.getString(LIST_WORK_NO));
        param.setAreaNo(p.getString(AREA_NO));
        param.setLocationNo(p.getString(LOCATION_FROM));
        param.setLocationNoTo(p.getString(LOCATION_TO));
        param.setItemCode(p.getString(ITEM_CODE));
        param.setConsignorCode(p.getString(CONSIGNOR_CODE));


        InventSettingFinder finder = null;
        int count = 0;
        try
        {
            finder = new InventSettingFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            count = finder.search(createSettingSearchKey(param));

            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(count))
            {
                // データが無い場合は処理終了
                return count;
            }

            // 6023203=対象データはありませんでした。データを追加してください。
            setMessage("6023203");

            // 検索結果をParameter配列に変換して取得
            return count;

        }
        finally
        {
            closeFinder(finder);
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();

        //荷主コード
        key.setConsignorCode(p.getString(CONSIGNOR_CODE));

        //条件指定により、検索条件を変更する
        if (InventoryInParameter.COLLECT_STATUS_LISTNO.equals(p.getString(CONDITION_SELECT)))
        {
            //リスト作業NO指定
            if (!StringUtil.isBlank(p.getString(LIST_WORK_NO)))
            {
                key.setSettingUnitKey(p.getString(LIST_WORK_NO), "=");
            }
        }
        else
        {
            //エリアNo
            key.setAreaNo(p.getString(AREA_NO));

            if (!StringUtil.isBlank(p.getString(LOCATION_NO)))
            {
                key.setLocationNo(p.getString(LOCATION_NO), "=");
            }
            else
            {
                String[] loc = WmsFormatter.getFromTo(p.getString(LOCATION_FROM), p.getString(LOCATION_TO));

                //開始棚
                if (!StringUtil.isBlank(loc[0]))
                {

                    key.setLocationNo(loc[0], ">=");

                }
                //終了棚
                if (!StringUtil.isBlank(loc[1]))
                {

                    key.setLocationNo(loc[1], "<=");

                }
                //商品コード
                if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
                {
                    key.setItemCode(p.getString(ITEM_CODE), "=");
                }
            }

            //新規データのみ表示時
            if (p.getBoolean(INVENTORY_ONLY_DISP))
            {
                key.setStockQty(0, "<=");
                key.setItemCode("", "IS NOT NULL");
            }
        }
        //削除以外
        key.setStatusFlag(InventWorkInfo.STATUS_FLAG_DELETE, "!=");

        key.setJoin(InventWorkInfo.AREA_NO, Area.AREA_NO);
        key.setJoin(InventWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        key.setJoin(InventWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");

        /* ソート順の指定 */
        // エリア
        key.setAreaNoOrder(true);
        // 棚
        key.setOrder(InventWorkInfo.LOCATION_NO, true);
        // 商品コード
        key.setOrder(InventWorkInfo.ITEM_CODE, true);
        //ロットNo.
        key.setOrder(InventWorkInfo.LOT_NO, true);

        //項目取得
        key.setSettingUnitKeyCollect();
        key.setStatusFlagCollect();
        key.setAreaNoCollect();
        key.setLocationNoCollect();
        key.setItemCodeCollect();
        key.setCollect(Item.ITEM_NAME);
        key.setLotNoCollect();
        key.setCollect(Item.ENTERING_QTY);
        key.setStockQtyCollect();
        key.setResultStockQtyCollect();

        return key;
    }

    /**
     * 棚卸作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param inparam 入力パラメータ
     * @return 棚卸作業メンテナンスを取得するための検索キークラスのインスタンス
     */
    protected SearchKey createSettingSearchKey(InventoryInParameter inparam)
    {
        InventSettingSearchKey key = new InventSettingSearchKey();

        //荷主コード
        key.setConsignorCode(inparam.getConsignorCode());

        //エリアNo
        if (!StringUtil.isBlank(inparam.getAreaNo()))
        {
            key.setAreaNo(inparam.getAreaNo());
        }

        String[] loc = WmsFormatter.getFromTo(inparam.getLocationNo(), inparam.getLocationNoTo());
        //開始棚
        if (!StringUtil.isBlank(loc[0]))
        {

            key.setToLocationNo(loc[0], ">=");

        }
        //終了棚
        if (!StringUtil.isBlank(loc[1]))
        {

            key.setFromLocationNo(loc[1], "<=");

        }

        key.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);

        return key;
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
        InventWorkInfo[] entities = (InventWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (InventWorkInfo ent : entities)
        {
            Params param = new Params();

            // リスト作業No
            param.set(LIST_WORK_NO, ent.getSettingUnitKey());
            // 状態フラグ(表示)
            param.set(STATUS_FLAG, DisplayResource.getInventWorkStatus(ent.getStatusFlag()));
            // 状態フラグ
            param.set(STATUSFLAG, ent.getStatusFlag());
            // エリア
            param.set(AREA_NO, ent.getAreaNo());
            // 棚
            param.set(LOCATION_NO, ent.getLocationNo());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            //ロットNo
            param.set(LOT_NO, ent.getLotNo());

            param.set(NEWDATE_FLAG, false);
            // 空棚用
            if (StringUtil.isBlank(param.getString(ITEM_CODE)))
            {
                //ケース入数
                param.set(ENTERING_QTY, 0);
                //在庫ケース数
                param.set(STOCK_CASE_QTY, 0);
                //在庫ピース数
                param.set(STOCK_PIECE_QTY, 0);
                //棚卸ケース数
                param.set(RESULT_CASE_QTY, 0);
                //棚卸ピース数
                param.set(RESULT_PIECE_QTY, 0);
            }
            else
            {
                //商品名称
                param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
                //ケース入数
                String scaseQty = String.valueOf(ent.getValue(Item.ENTERING_QTY, 0));
                int entQty = Integer.parseInt(scaseQty);
                param.set(ENTERING_QTY, entQty);
                //在庫数
                int stockQty = ent.getStockQty();
                //在庫ケース数
                param.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(stockQty, entQty));
                //在庫ピース数
                param.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(stockQty, entQty));
                //棚卸数
                int inventQty = ent.getResultStockQty();
                //棚卸ケース数
                param.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(inventQty, entQty));
                //棚卸ピース数
                param.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(inventQty, entQty));
            }
            result.add(param);
        }

        return result;
    }


    /**
     * 帳票印刷処理を行います。<BR>
     * @param startParams 設定内容が含まれたパラメータクラスの配列
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean print(Parameter startParams)
            throws CommonException
    {
        return true;
    }

    /**
     * 棚卸結果をDBに書き込みます。<BR>
     * @param startParams 入力パラメータ
     * @return 処理が正常に完了場合はtrue、失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean input(Parameter[] startParams)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        InventoryInParameter[] param = (InventoryInParameter[])startParams;

        //コントローラクラスの実装
        InventWorkInfoController inventWcon = new InventWorkInfoController(getConnection(), getClass());
        InventSettingController inventScon = new InventSettingController(getConnection(), getClass());

        //ユーザID、TerminalNo取得
        String terminal = param[0].getTerminalNo();
        String userId = param[0].getUserId();

        int i = 0;
        try
        {
            for (i = 0; i < param.length; i++)
            {

                // 結果在庫数が在庫上限数異常の場合
                if (param[i].getInventoryInsResultQtyLong() > (long)WmsParam.MAX_STOCK_QTY)
                {
                    setErrorRowIndex(param[i].getRowNo());
                    // 6023186=棚卸数には在庫上限数{0}以下の値を入力してしてください。
                    setMessage(WmsMessageFormatter.format(6023186, WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
                    return false;
                }

                //新規棚卸データ時の処理
                if (!param[i].isAddDataFlag())
                {
                    if (!checkTerm(param[i]))
                    {
                        setErrorRowIndex(param[i].getRowNo());
                        // No. {0} 他端末で処理されたため、処理を中断しました。
                        setMessage(WmsMessageFormatter.format(6023015, param[i].getRowNo()));
                        return false;
                    }
                }

                InventWorkInfo iWorkInfo = new InventWorkInfo();

                //エンティティクラスにパラメータを格納する
                if (param[i].getSettingUnitKey() != null)
                {
                    iWorkInfo.setSettingUnitKey(param[i].getSettingUnitKey());
                }
                iWorkInfo.setTerminalNo(terminal);
                iWorkInfo.setUserId(userId);
                iWorkInfo.setConsignorCode(param[i].getConsignorCode());
                iWorkInfo.setAreaNo(param[i].getAreaNo());
                iWorkInfo.setLocationNo(param[i].getLocationNo());
                iWorkInfo.setItemCode(param[i].getItemCode());
                iWorkInfo.setLotNo(param[i].getLotNo());

                iWorkInfo.setStockQty(param[i].getStockQty());
                iWorkInfo.setResultStockQty((int)(param[i].getInventoryInsResultQtyLong()));

                //新規棚卸データ時の処理
                if (param[i].isAddDataFlag())
                {
                    //作業データのロック
                    if (inventWcon.lockInventStart(iWorkInfo, true) == null)
                    {
                        iWorkInfo.setScheduleNo(inventScon.rangeCheck(param[i].getConsignorCode(),
                                param[i].getAreaNo(), param[i].getLocationNo()));

                        //指定された棚卸設定情報をロックします。
                        inventScon.lockInventSetting(iWorkInfo.getScheduleNo());
                        //商品マスタチェック
                        if (!param[i].isMasterFlag())
                        {
                            chkItem(param[i], userId);
                        }
                        // 棚卸作業追加
                        iWorkInfo.setJobNo(inventWcon.insertInvent(iWorkInfo));
                        // 棚卸結果入力
                        inventWcon.inputResult(iWorkInfo);
                    }
                    else
                    {
                        // 新規登録されたデータが既に登録済みの場合
                        // 6403068=入力されたデータに既存データと重複しているデータがあります。
                        setMessage("6403068");
                        setErrorRowIndex(param[i].getRowNo());
                        return false;
                    }
                }
                else
                {
                    // 作業データのロック
                    iWorkInfo.setJobNo(inventWcon.lockInventStart(iWorkInfo, true));
                    // 棚卸結果入力
                    inventWcon.inputResult(iWorkInfo);
                }
            }

            // 6001006=設定しました。
            setMessage("6001006");

            return true;
        }
        catch (NoPrimaryException e)
        {
            //No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, param[i].getRowNo()));
            setErrorRowIndex(param[i].getRowNo());
            return false;
        }
        catch (NotFoundException e)
        {
            //No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, param[i].getRowNo()));
            setErrorRowIndex(param[i].getRowNo());
            return false;
        }
        catch (LockTimeOutException e)
        {
            //No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, param[i].getRowNo()));
            setErrorRowIndex(param[i].getRowNo());
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_INVENT_LOCATION_OUTSIDE_RANGE.equals(e.getErrorCode()))
            {
                //6023132=指定された棚は棚卸中でありません。
                setMessage("6023132");
                setErrorRowIndex(param[i].getRowNo());
                return false;
            }
            //「他端末で作業中」か「棚卸結果確定済み」
            else if (OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_INVENT_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, param[i].getRowNo()));
                setErrorRowIndex(param[i].getRowNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }


    /**
     * 設定後の新規追加データを削除し、ステータスを削除に変更した棚卸情報を未作業に戻す。
     * @param startParam 入力パラメータ
     * @return 処理が正常に完了場合はtrue、失敗した場合はfalseを返します。
     * @throws NotFoundException 
     * @throws ReadWriteException 
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean delete(InventoryInParameter startParam)
            throws CommonException
    {

        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler inventHandler = new InventWorkInfoHandler(getConnection());
        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey sKey = new InventWorkInfoSearchKey();

        try
        {
            // 新規追加データ削除
            sKey.setAreaNo(startParam.getAreaNo());
            sKey.setLocationNo(startParam.getLocationNo());
            sKey.setConsignorCode(startParam.getConsignorCode());
            sKey.setItemCode(startParam.getItemCode());
            sKey.setLotNo(startParam.getLotNo());
            sKey.setStatusFlag(InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);
            inventHandler.drop(sKey);
        }
        catch (ReadWriteException e)
        {
            //No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, startParam.getRowNo()));
            setErrorRowIndex(startParam.getRowNo());
            return false;
        }
        catch (NotFoundException e)
        {
            //No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, startParam.getRowNo()));
            setErrorRowIndex(startParam.getRowNo());
            return false;
        }
        // フリーエリア以外の場合
        if (check(startParam) && checkStock(startParam))
        {
            // 空棚棚卸作業情報を未作業に戻す。
            InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();
            try
            {
                aKey.setAreaNo(startParam.getAreaNo());
                aKey.setLocationNo(startParam.getLocationNo());
                aKey.setConsignorCode(startParam.getConsignorCode());
                aKey.setItemCode("");
                aKey.setLotNo("");
                aKey.setStatusFlag(InventoryInParameter.STATUS_FLAG_INVENTORY_DELETE);
                aKey.updateStatusFlag(InventoryInParameter.STATUS_FLAG_INVENTORY_UNSTART);

                inventHandler.modify(aKey);
            }

            catch (ReadWriteException e)
            {
                //No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, startParam.getRowNo()));
                setErrorRowIndex(startParam.getRowNo());
                return false;
            }
            catch (NotFoundException e)
            {
                //No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, startParam.getRowNo()));
                setErrorRowIndex(startParam.getRowNo());
                return false;
            }
        }
        // 6001005=削除しました。
        setMessage("6001005");

        return true;
    }

    /**
     * 他端末で更新されたかチェックを行います。<BR>
     * @param checkParam 状態フラグ
     * @return boolean true:他端末で更新されていない false:他端末で更新されている
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkTerm(Parameter checkParam)
            throws CommonException
    {

        InventoryInParameter param = (InventoryInParameter)checkParam;

        // 棚卸作業情報検索
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();
        InventWorkInfoFinder finder = null;

        try
        {
            finder = new InventWorkInfoFinder(getConnection());
            finder.open(true);
            // エリアが指定の場合
            if (!StringUtil.isBlank(param.getAreaNo()))
            {
                key.setAreaNo(param.getAreaNo());
                key.setLocationNo(param.getLocationNo());
                key.setItemCode(param.getItemCode());
            }
            // 設定単位キー指定の場合
            else if (!StringUtil.isBlank(param.getSettingUnitKey()))
            {
                key.setSettingUnitKey(param.getSettingUnitKey());
            }

            int count = finder.search(key);

            // 該当データが存在しない場合、他端末で更新されたとみなす
            if (count <= 0)
            {
                return false;
            }
            else
            {
                return true;
            }

        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 荷主マスタ、商品マスタチェックを行います。<BR>
     * マスタに情報がなかった場合、登録を行います。
     * @param param パラメータ
     * @param userId ユーザ名
     * @throws CommonException データベース例外が発生した場合に通知されます。
     */
    protected void chkItem(InventoryInParameter param, String userId)
            throws CommonException
    {
        // 荷主マスタの登録又は更新
        ConsignorController cc = new ConsignorController(getConnection(), getClass());
        Consignor newCons = new Consignor();
        newCons.setConsignorCode(param.getConsignorCode());
        newCons.setConsignorName(param.getConsignorName());
        cc.autoCreate(newCons, param.getWmsUserInfo());

        // 商品マスタの登録又は更新
        ItemController ic = new ItemController(getConnection(), getClass());
        Item newItem = new Item();
        newItem.setConsignorCode(param.getConsignorCode());
        newItem.setItemCode(param.getItemCode());
        newItem.setItemName(param.getItemName());
        newItem.setJan(param.getJanCode());
        newItem.setEnteringQty(param.getEnteringQty());
        newItem.setBundleEnteringQty(param.getBundleEnteringQty());
        newItem.setItf(param.getItf());
        newItem.setBundleItf(param.getBundleItf());
        ic.autoCreate(newItem, param.getWmsUserInfo());
    }

    /**
     * 同一棚に同一商品コードが存在するかチェックを行います。<BR>
     * 新規データ追加の設定により削除された空棚情報が存在するかチェックを行う。
     * @param param 状態フラグ
     * @return boolean true:存在する場合 false:存在しない場合
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkStock(InventoryInParameter param)
            throws CommonException
    {
        // 棚卸作業情報の検索
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();
        InventWorkInfoFinder finder = null;


        try
        {
            finder = new InventWorkInfoFinder(getConnection());
            finder.open(true);

            if (!StringUtil.isBlank(param.getAreaNo()))
            {
                // エリア
                key.setAreaNo(param.getAreaNo());
                // 棚
                key.setLocationNo(param.getLocationNo());
                // 商品コード
                String item = null;
                key.setItemCode(item);
                // ロットNo.
                //                key.setLotNo(param.getLotNo());
            }
            else if (!StringUtil.isBlank(param.getSettingUnitKey()))
            {
                key.setSettingUnitKey(param.getSettingUnitKey());
            }
            key.setStatusFlag(InventoryInParameter.STATUS_FLAG_INVENTORY_DELETE);
            int count = finder.search(key);

            // 削除された空棚情報が存在しない場合
            if (count <= 0)
            {
                return false;
            }
            // 存在する場合
            else
            {
                return true;
            }
        }
        finally
        {
            closeFinder(finder);
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
