// $Id: PCTItemDeleteSCH.java 4301 2009-05-15 06:40:45Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTItemDeleteSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品マスタ削除のスケジュール処理を行います。
 *
 * @version $Revision: 4301 $, $Date: 2009-05-15 15:40:45 +0900 (金, 15 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTItemDeleteSCH
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
    public PCTItemDeleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // ダイレクトDBハンドラ
        DefaultDDBFinder ddbFinder = null;
        try
        {
            ddbFinder = new DefaultDDBFinder(getConnection(), new PCTItem());
            ddbFinder.open(true);

            // 検索処理実行
            ddbFinder.search(String.valueOf(createSearchKey(p)));

            // PCT商品マスタ取得結果
            List<Params> listParam = new ArrayList<Params>();

            // リストセル（ためうちエリア）最大表示件数フラグ
            boolean maxDisp = false;
            int count = 0;
            while (ddbFinder.hasNext())
            {
                PCTItem[] entities = (PCTItem[])ddbFinder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);

                if (!maxDisp)
                {
                    for (int i = 0; i < entities.length; i++)
                    {
                        Params param = new Params();

                        // リストセル（ためうちエリア）最大表示件数以下であればリストに追加する
                        // 商品コード
                        param.set(ITEM_CODE, entities[i].getValue(new FieldName("", "ITEM_CODE")));
                        // 荷主コード
                        param.set(CONSIGNOR_CODE, entities[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                        // ロット入数
                        param.set(LOT_QTY, entities[i].getBigDecimal(new FieldName("", "LOT_ENTERING_QTY")).intValue());
                        // 商品名称
                        param.set(ITEM_NAME, entities[i].getValue(new FieldName("", "ITEM_NAME"), ""));
                        // 最終更新日時
                        param.set(LAST_UPDATE_DATE, entities[i].getDate(new FieldName("", "LAST_UPDATE_DATE")));

                        // 取得した結果を配列に追加
                        listParam.add(param);

                        // 結果がリスト最大件数に達した場合はフラグON            
                        if ((count + i + 1) == WmsParam.MAX_NUMBER_OF_DISP)
                        {
                            maxDisp = true;
                            break;
                        }
                    }
                }

                count = count + entities.length;
            }

            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(count))
            {
                return new ArrayList<Params>();
            }

            return listParam;
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(ddbFinder);
        }
    }

    /**
     * 商品マスタ削除開始処理<BR>
     * startParamsで指定するParameterクラスはPCTMasterInParameter型であること。<BR>
     * 
     * @param startParams
     *            開始条件
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }
        // PCT商品マスタ取込フラグチェック
        if (!itemload())
        {
            return false;
        }

        // 取り込み中チェック
        if (isLoadData())
        {
            return false;
        }

        // 商品マスタ用
        PCTItemHandler iHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey iKey = new PCTItemSearchKey();
        // 予定情報用
        PCTRetPlanHandler pHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey pKey = new PCTRetPlanSearchKey();

        int i = 0;
        try
        {
            // パラメータの配列数分削除処理を実行
            for (i = 0; i < ps.length; i++)
            {
                // 予定情報検索キー
                pKey.clear();
                pKey.setItemCode(ps[i].getString(ITEM_CODE));
                pKey.setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
                pKey.setLotEnteringQty(ps[i].getInt(LOT_QTY));

                // 商品マスタ削除キー
                iKey.clear();
                iKey.setItemCode(ps[i].getString(ITEM_CODE));
                iKey.setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
                iKey.setLotEnteringQty(ps[i].getInt(LOT_QTY));

                // 予定情報に存在する場合
                if (pHandler.count(pKey) != 0)
                {
                    // No.{0} 他端末で処理されたため、処理を中断しました。
                    setErrorRowIndex(ps[i].getRowIndex());
                    setMessage(WmsMessageFormatter.format(6023015, ps[i].getRowIndex()));
                    return false;
                }
                // ロック処理
                if (iHandler.findPrimaryForUpdate(iKey, PCTItemHandler.WAIT_SEC_NOWAIT) == null)
                {
                    // No.{0} 他端末で処理されたため、処理を中断しました。
                    setErrorRowIndex(ps[i].getRowIndex());
                    setMessage(WmsMessageFormatter.format(6023015, ps[i].getRowIndex()));
                    return false;
                }
                // 削除用
                iHandler.drop(iKey);
            }

            // 6001005=削除しました。
            setMessage(WmsMessageFormatter.format(6001005));
            return true;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理されたため、処理を中断しました。
            setErrorRowIndex(ps[i].getRowIndex());
            setMessage(WmsMessageFormatter.format(6023015, ps[i].getRowIndex()));
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
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    protected boolean itemload()
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_SAVE.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024069=セーブ処理中のため処理できません。
                setMessage(WmsMessageFormatter.format(6024069));
                return false;
            }

            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため処理できません。
                setMessage(WmsMessageFormatter.format(6024068));
                return false;
            }
        }
        return true;
    }

    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return PCTItemSearchKey
     */
    protected StringBuffer createSearchKey(ScheduleParams p)
    {
        String updateDate = WmsFormatter.toParamDateTime(p.getDate(LAST_UPDATE_DATE), 3);

        // PCT商品マスタとPCT出庫予定情報の差異情報取得
        StringBuffer sql = null;
        sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("    DMPCTITEM.ITEM_CODE ");
        sql.append("    ,DMPCTITEM.CONSIGNOR_CODE ");
        sql.append("    ,DMPCTITEM.LOT_ENTERING_QTY ");
        sql.append("    ,DMPCTITEM.LAST_UPDATE_DATE ");
        sql.append("    ,DMPCTITEM.ITEM_NAME ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("        DMPCTITEM.ITEM_CODE ");
        sql.append("        ,DMPCTITEM.CONSIGNOR_CODE ");
        sql.append("        ,DMPCTITEM.LOT_ENTERING_QTY ");
        sql.append("    FROM ");
        sql.append("        DMPCTITEM ");
        sql.append("    WHERE ");

        // 開始最終更新日時
        sql.append("        DMPCTITEM.LAST_UPDATE_DATE < TO_TIMESTAMP(");
        sql.append(DBFormat.format(updateDate)).append(", 'yyyymmddhh24missff3')+1 ");

        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            sql.append("        AND DMPCTITEM.CONSIGNOR_CODE = ");
            sql.append(DBFormat.format(p.getString(CONSIGNOR_CODE))).append(" ");
        }
        // 商品コード大小チェック
        String[] temp = WmsFormatter.getFromTo(p.getString(FROM_ITEM_CODE), p.getString(TO_ITEM_CODE));
        String from = temp[0];
        String to = temp[1];
        // 開始商品コード
        if (!StringUtil.isBlank(from))
        {
            sql.append("        AND DMPCTITEM.ITEM_CODE >= ");
            sql.append(DBFormat.format(from)).append(" ");
        }
        // 終了商品コード
        if (!StringUtil.isBlank(to))
        {
            sql.append("        AND DMPCTITEM.ITEM_CODE <= ");
            sql.append(DBFormat.format(to)).append(" ");
        }
        sql.append("    MINUS ");
        sql.append("    SELECT ");
        sql.append("        DNPCTRETPLAN.ITEM_CODE ");
        sql.append("        ,DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("        ,DNPCTRETPLAN.LOT_ENTERING_QTY ");
        sql.append("    FROM ");
        sql.append("        DNPCTRETPLAN ");
        sql.append("    WHERE ");
        sql.append("        DNPCTRETPLAN.STATUS_FLAG != 9 ");
        sql.append("    ) DIFF ");
        sql.append("    ,DMPCTITEM ");
        sql.append("WHERE ");
        sql.append("    DMPCTITEM.ITEM_CODE = DIFF.ITEM_CODE ");
        sql.append("    AND DMPCTITEM.CONSIGNOR_CODE = DIFF.CONSIGNOR_CODE ");
        sql.append("    AND DMPCTITEM.LOT_ENTERING_QTY = DIFF.LOT_ENTERING_QTY ");

        // ソート順の設定
        sql.append("ORDER BY ");
        sql.append("    DMPCTITEM.LAST_UPDATE_DATE ");
        sql.append("    ,DMPCTITEM.CONSIGNOR_CODE ");
        sql.append("    ,DMPCTITEM.ITEM_CODE ");
        sql.append("    ,DMPCTITEM.LOT_ENTERING_QTY ");

        return sql;
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
