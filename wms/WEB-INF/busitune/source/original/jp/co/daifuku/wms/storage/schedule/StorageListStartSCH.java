// $Id: StorageListStartSCH.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReplenishShortage;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.storage.schedule.StorageListStartSCHParams;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class StorageListStartSCH
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
    public StorageListStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
            finder = new xxxFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // TODO 処理前のチェックを行う。
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // オペレータパラメータ生成
        xxxInParameter[] inParams = new xxxInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new xxxInParameter();

            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));
        }

        try
        {
            // オペレータ生成
            xxxOperator operator = new xxxOperator(getConnection(), getClass());
            // オペレータ呼び出し
            xxxOutParameter outParam = operator.completeRetrieval(inParams);

            // TODO 画面に合ったメッセージをセットする
            // 開始しました。
            setMessage(WmsMessageFormatter.format(6021021));
            return true;

        }
        //TODO 以下、例外処理。Operatorの処理に合わせてハンドリングを行ってください
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));
            return false;
        }
        catch (RouteException e)
        {
            // TODO AS/RSの場合の例外処理
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return false;
        }
        catch (OperatorException e)
        {
            // Operatorからの例外をcatchし、該当するエラーメッセージを表示する

            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, inParams[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
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
    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        xxxSearchKey searchKey = new xxxSearchKey();

        /* TODO 画面の検索条件に合わせて検索内容をセットする
         * 1.マスタと結合する場合は、外部結合を使用すること

        // set where
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            searchKey.setItemCode(p.getString(ITEM_CODE));
        }
        // 範囲指定を行う場合
        if (p.getBoolean(DESIGNATE_RANGE))
        {
            if (!StringUtil.isBlank(p.getString(LOCATION)))
            {
                searchKey.setLocationNo(p.getString(LOCATION), ">=");
            }
            if (!StringUtil.isBlank(p.getString(TO_LOCATION)))
            {
                searchKey.setLocationNo(p.getString(TO_LOCATION), "<=");
            }
        }
        else if (!StringUtil.isBlank(p.getString(LOCATION)))
        {
            searchKey.setLocationNo(p.getString(LOCATION));
        }

        // set join(Item Table)
        searchKey.setJoin(Stock.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(Stock.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        // set join(Consignor Table)
        searchKey.setJoin(Stock.CONSIGNOR_CODE, "", Consignor.CONSIGNOR_CODE, "(+)");

        // set order by
        searchKey.setItemCodeOrder(true);

        // set collect
        // 全項目取得する場合の指定方法
        searchKey.setCollect(new FieldName(xxxEntity.STORE_NAME, FieldName.ALL_FIELDS));
        searchKey.setCollect(Stock.CONSIGNOR_CODE);
        searchKey.setCollect(Item.ITEM_NAME);

         */
        return searchKey;
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
        xxxEntity[] entities = (xxxEntity)finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (xxxEntity ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            /* TODO
             * 1.Entity#getValueを使用して、商品名称などnullがセットされている可能性がある値を
             *   取得する場合は、引数２つのgetValueメソッドを使用すること。第二引数は""。
             * 2.Entity#getBigDecimalを使用して、nullがセットされている可能性がある数値を
             *   取得する場合は、引数２つのgetValueメソッドを使用すること。
             *   第二引数はnew BigDecimal(0)。
             * 3.ケース・ピースの変換処理は、DisplayUtil#getPieceQtyを使用すること。

            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            param.set(CONSIGNOR_NAME, String.valueOf(ent.getValue(Consignor.CONSIGNOR_NAME, "")));
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
            param.set(ENTERING_QTY, enteringQty);
            // 引当可能ケース数
            param.set(ALLOCATE_CASE_QTY, DisplayUtil.getCaseQty(ent.getAllocationQty(), enteringQty));
            // 引当可能ピース数
            param.set(ALLOCATE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getAllocationQty(), enteringQty));
            */

            result.add(param);
        }

        return result;
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
