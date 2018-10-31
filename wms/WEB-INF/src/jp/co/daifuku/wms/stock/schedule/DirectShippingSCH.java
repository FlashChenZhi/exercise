// $Id: DirectShippingSCH.java 6884 2010-01-25 07:54:31Z shibamoto $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.schedule.DirectShippingSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.stock.operator.NoPlanWorkOperator;

/**
 * 入庫即出庫のスケジュール処理を行います。
 *
 * @version $Revision: 6884 $, $Date: 2010-01-25 16:54:31 +0900 (月, 25 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class DirectShippingSCH
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
     * Constructor to create SCH object
     * @param conn Database Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     * @param ui DfkUserInfo
     * @throws CommonException
     */
    public DirectShippingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期データ検索を行います。
     * 
     * @param p 検索条件を指定したパラメータ
     * @return 初期表示用データ
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        Params outParam = new Params();
        if (systemController.hasMasterPack())
        {
            outParam.set(MASTER_FLAG, true);
        }
        else
        {
            outParam.set(MASTER_FLAG, false);
        }

        return outParam;

    }

    /**
     * チェック条件を指定してユーザ入力内容をチェックします。<br>
     * 入力項目および入力済み複数データをチェックします。
     * 
     * @param p チェック内容を保持するパラメータ (画面入力項目)
     * @param ps チェック内容を保持するパラメータ (入力済み複数項目)
     * @return 内容チェックの結果、処理が続行可能なとき true.
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
        // 入力パラメータクラスのキャスト
        StockInParameter inputParam = new StockInParameter(getWmsUserInfo());

        inputParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        inputParam.setItemCode(p.getString(ITEM_CODE));
        inputParam.setEnteringQty(p.getInt(ENTERING_QTY));
        inputParam.setResultCaseQty(p.getInt(SHIPPING_CASE_QTY));
        inputParam.setResultPieceQty(p.getInt(SHIPPING_PIECE_QTY));
        inputParam.setAreaNo(p.getString(SHIPPING_AREA));
        inputParam.setLotNo(p.getString(LOT_NO));

        // マスタチェック
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());
        if (systemController.hasMasterPack())
        {
            // マスタパッケージありの場合
            // 荷主コードが存在するかチェック
            ConsignorHandler consignorHandler = new ConsignorHandler(getConnection());
            ConsignorSearchKey consignorKey = new ConsignorSearchKey();

            consignorKey.setConsignorCode(inputParam.getConsignorCode());

            if (consignorHandler.count(consignorKey) <= 0)
            {
                // 6023040=荷主コードがマスタに登録されていません。
                setMessage("6023040");
                return false;
            }

            // 商品コードが存在するかチェック
            ItemHandler itemHandler = new ItemHandler(getConnection());
            ItemSearchKey itemKey = new ItemSearchKey();

            itemKey.setConsignorCode(inputParam.getConsignorCode());
            itemKey.setItemCode(inputParam.getItemCode());

            if (itemHandler.count(itemKey) <= 0)
            {
                // 6023021=商品コードがマスタに登録されていません。
                setMessage("6023021");
                return false;
            }
        }


        // ケース入数が0かつ入庫ケース数が1以上の場合
        if (inputParam.getEnteringQty() == 0 && inputParam.getResultCaseQty() >= 1)
        {
            // 6023036=ケース入数が0の場合、ケース数は入力できません。
            setMessage("6023036");
            return false;
        }

        // 数量入力チェック
        if (inputParam.getResultCaseQty() == 0 && inputParam.getResultPieceQty() == 0)
        {
            // 6023035=ケース数またはピース数には1以上の値を入力してください。
            setMessage("6023035");
            return false;
        }

        // オーバーフローチェック
        long newQty =
                (long)((long)inputParam.getEnteringQty() * (long)inputParam.getResultCaseQty() + (long)inputParam.getResultPieceQty());
        if (newQty > (long)WmsParam.MAX_STOCK_QTY)
        {
            // 6023258= Picking Qty must be less than {0}
            setMessage(WmsMessageFormatter.format(6023258, MAX_STOCK_QTY_DISP));
            return false;
        }

        // ためうちパラメータクラスのキャスト
        StockInParameter[] lParams = new StockInParameter[ps.length];
        for (int i = 0; i < ps.length; i++)
        {
            lParams[i] = new StockInParameter(getWmsUserInfo());
            lParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            lParams[i].setAreaNo(ps[i].getString(SHIPPING_AREA));
            lParams[i].setLotNo(ps[i].getString(LOT_NO));
        }

        // 表示件数チェック
        if (lParams != null && lParams.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // 6023019 = 件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));
            return false;
        }

        // ためうちエリアとの重複チェック
        if (lParams != null)
        {
            for (StockInParameter lParam : lParams)
            {
                if (inputParam.getItemCode().equals(lParam.getItemCode())
                        && inputParam.getAreaNo().equals(lParam.getAreaNo())
                        && inputParam.getLotNo().equals(lParam.getLotNo()))
                {
                    // 6023020 = 既に同一データが存在するため、入力できません。
                    setMessage("6023020");
                    return false;
                }
            }
        }

        return true;

    }

    /**
     * 画面へ表示するデータを取得します。表示ボタン押下時などの操作に対応するメソッドです。<br>
     * 
     * 該当データが見つからなかった場合、要素数0のリストを返します。<br>
     * 入力条件エラーなどで検索処理が失敗した場合、nullを返します。<br>
     * この場合は getMessage() メソッドを使用して内容を取得することができます。
     * 
     * @param p  検索条件を指定したパラメータ
     * @return 画面表示用データのリスト
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        List<Params> outList = new ArrayList<Params>();

        StockInParameter inParam = new StockInParameter(getWmsUserInfo());

        inParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        inParam.setItemCode(p.getString(ITEM_CODE));

        ItemHandler itemHandler = new ItemHandler(getConnection());
        ItemSearchKey itemKey = new ItemSearchKey();

        // 検索条件をセットする。
        // 荷主コード
        itemKey.setConsignorCode(inParam.getConsignorCode());
        // 商品コード
        itemKey.setItemCode(inParam.getItemCode());

        // 検索する。
        Item[] item = (Item[])itemHandler.find(itemKey);

        // 取得データのチェック
        if (item == null || item.length <= 0)
        {
            return outList;
        }

        // return用のパラメータ
        ScheduleParams outParam = new ScheduleParams();

        // 該当データは一件のはずなので、要素0のみを返す。
        // 荷主コード
        outParam.set(CONSIGNOR_CODE, item[0].getConsignorCode());
        // 商品コード
        outParam.set(ITEM_CODE, item[0].getItemCode());
        // 商品名称
        outParam.set(ITEM_NAME, item[0].getItemName());
        // ケース入数
        outParam.set(ENTERING_QTY, item[0].getEnteringQty());
        // JANコード
        outParam.set(UPC_CODE, item[0].getJan());
        // ケースITF
        outParam.set(CASE_ITF, item[0].getItf());

        outList.add(outParam);

        // 6001019=入力を受け付けました。
        setMessage("6001019");
        return outList;

    }

    /**
     * Receives the items that was input on the screen as a parameter and start schedule.<BR>
     *
     * @param ps Setting items saved in <CODE>ScheduleParams</CODE> array.<BR>
     * @throws CommonException Reports all the exceptions.
     * @return Return true if the schedule ends normally and return false if it failed so.
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        List<StockInParameter> lst = new ArrayList<StockInParameter>();
        for (ScheduleParams p : ps)
        {
            StockInParameter temp = new StockInParameter(getWmsUserInfo());

            int caseQty = p.getInt(SHIPPING_CASE_QTY);
            int pieceQty = p.getInt(SHIPPING_PIECE_QTY);
            int entQty = p.getInt(ENTERING_QTY);

            temp.setRowNo(p.getRowIndex());
            temp.setAreaNo(p.getString(SHIPPING_AREA));
            temp.setLocation(p.getString(SHIPPING_LOCATION));
            temp.setItemCode(p.getString(ITEM_CODE));
            temp.setItemName(p.getString(ITEM_NAME));
            temp.setLotNo(p.getString(LOT_NO));
            temp.setResultLotNo(p.getString(LOT_NO));
            temp.setEnteringQty(entQty);
            temp.setResultCaseQty(caseQty);
            temp.setResultPieceQty(pieceQty);
            temp.setJanCode(p.getString(UPC_CODE));
            temp.setItf(p.getString(CASE_ITF));
            temp.setConsignorCode(p.getString(CONSIGNOR_CODE));
            temp.setHardwareType(p.getString(HARD_WARE_TYPE));

            temp.setResultQty(entQty * caseQty + pieceQty);

            lst.add(temp);

        }

        StockInParameter[] inParam = lst.toArray(new StockInParameter[lst.size()]);

        // 処理実行が可能かどうかチェック
        if (!canStart())
        {
            return false;
        }

        // 予定外入出庫オペレータ生成
        NoPlanWorkOperator operator = new NoPlanWorkOperator(getConnection(), getClass());

        try
        {
            // オペレータ呼び出し
            operator.completeShipping(inParam);

            // 開始しました。
            setMessage("6021021");

            return true;

        }
        catch (LockTimeOutException e)
        {
            // 6027008=このデータは他の端末で更新中のため、処理できません。
            setMessage("6027008");
            return false;
        }

        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParam[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(inParam[e.getErrorLineNo() - 1].getRowNo());
                return false;
            }
            else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                // 6023183=No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023183, inParam[e.getErrorLineNo() - 1].getRowNo(),
                        WmsFormatter.getNumFormat(Long.valueOf(e.getDetailString()))));
                setErrorRowIndex(inParam[e.getErrorLineNo() - 1].getRowNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
     * Returns current repository info for this class
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
