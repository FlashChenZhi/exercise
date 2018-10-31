// $Id: LstItemDASCH.java 4803 2009-08-10 06:24:09Z shibamoto $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.LstItemDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品検索リストボックスに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4803 $, $Date: 2009-08-10 15:24:09 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstItemDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド 商品名称(<code>ITEM_NAME</code>) */
    private FieldName _ITEM_NAME = new FieldName("", "ITEM_NAME");

    /** 保存用のフィールド 商品コード(<code>ITEM_CODE</code>) */
    private FieldName _ITEM_CODE = new FieldName("", "ITEM_CODE");

    /** 保存用のフィールド ロット入数(<code>LOT_QTY</code>) */
    private FieldName _LOT_QTY = new FieldName("", "_LOT_QTY");

    /** 保存用のフィールド JANコード(<code>JAN</code>) */
    private FieldName _JAN = new FieldName("", "JAN");

    /** 保存用のフィールド ケースITF(<code>ITF</code>) */
    private FieldName _ITF = new FieldName("", "ITF");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public LstItemDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // PCT出庫予定情報
        if (PCTRetrievalInParameter.SEARCH_TABLE_PLAN.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTRetPlanFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchRetPlanKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
        // PCT商品マスタ情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_MASTER.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTItemFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchItemKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
        // PCT出庫作業情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_WORK.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTRetWorkInfoFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchRetWorkInfoKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
        // PCT出庫実績情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_RESULT.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTRetResultFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchRetResultKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        // 次データへ移動
        _current++;

        // 全行数が現在行数より大きい場合はtrueを返却
        // 上記以外はfalseを返却
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        //  エンティティ
        Entity[] ents = _finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (Entity ent : ents)
        {
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(_ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(_ITEM_NAME));
            // ロット入数
            p.set(LOT_QTY, ent.getValue(_LOT_QTY));
            // JANコード
            p.set(JAN, ent.getValue(_JAN));
            // ケースITF
            p.set(ITF, ent.getValue(_ITF));

            // for文抜け(一件のみ取得)
            break;
        }
        // 生成した配列を返却
        return p;
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        // PCT出庫予定情報
        if (PCTRetrievalInParameter.SEARCH_TABLE_PLAN.equals(p.get(SEARCHTABLE)))
        {
            PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchRetPlanKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // PCT商品マスタ情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_MASTER.equals(p.get(SEARCHTABLE)))
        {
            PCTItemHandler handler = new PCTItemHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchItemKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // PCT出庫作業情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_WORK.equals(p.get(SEARCHTABLE)))
        {
            PCTRetWorkInfoHandler handler = new PCTRetWorkInfoHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchRetWorkInfoKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // PCT出庫実績情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_RESULT.equals(p.get(SEARCHTABLE)))
        {
            PCTRetResultHandler handler = new PCTRetResultHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchRetResultKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // 対象データなし
        else
        {
            _total = 0;
            return _total;
        }
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 返却用の空配列を生成
        List<Params> params = new ArrayList<Params>();
        // エンティティの生成
        Entity[] ents = (Entity[])_finder.getEntities(start, start + cnt);

        // 取得した件数分、繰り返す
        for (Entity ent : ents)
        {
            // パラメータ配列の生成
            Params p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(_ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(_ITEM_NAME));
            // ロット入数
            p.set(LOT_QTY, ent.getValue(_LOT_QTY));
            // JANコード
            p.set(JAN, ent.getValue(_JAN));
            // ケースITF
            p.set(ITF, ent.getValue(_ITF));
            // 設定した値を配列に格納
            params.add(p);
        }
        // 設定した配列を返却
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 入力されたパラメータをもとにPCT出庫予定情報を検索するSQL文を発行します。<BR>
     * 検索を行う<code>StoragePlanFinder</code>はインスタンス変数として保持します。<BR>
     * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
     * <BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchRetPlanKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey key = new PCTRetPlanSearchKey();

        // 検索キーのセット
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            if (DBFormat.isPatternMatching(param.getString(ITEM_CODE)))
            {
                // ワイルドカード検索
                key.setItemCode(param.getString(ITEM_CODE));
            }
            else
            {
                // 入力された値以上を条件とする
                key.setItemCode(param.getString(ITEM_CODE), ">=");
            }
        }
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 予定日
        if (!StringUtil.isBlank(param.getString(PLAN_DAY)))
        {
            key.setPlanDay(WmsFormatter.toParamDate(param.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリアでない時は、条件をセットする
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(param.getString(REGULAR_CUSTOMER_CODE)))
        {
            key.setRegularCustomerCode(param.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }
        // 予定オーダーNo.
        if (!StringUtil.isBlank(param.getString(ORDER_NO)))
        {
            key.setPlanOrderNo(param.getString(ORDER_NO));
        }
        // 状態フラグ2
        if (!StringUtil.isBlank(param.getString(STATUS_FLAG2)))
        {
            // 状態フラグ１、状態フラグ２をセット        
            key.setStatusFlag(param.getString(STATUS_FLAG), "=", "(", "", false);
            key.setStatusFlag(param.getString(STATUS_FLAG2), "=", "", ")", false);
        }
        // 状態フラグ1
        else if (!StringUtil.isBlank(param.getString(STATUS_FLAG)))
        {
            // 状態フラグ１をセット
            key.setStatusFlag(param.getString(STATUS_FLAG));
        }
        else
        {
            // 状態フラグ削除以外
            key.setStatusFlag(PCTRetWorkInfo.STATUS_FLAG_DELETE, "!=");
        }

        // 集約キーのセット
        // 商品コード
        key.setItemCodeGroup();
        key.setLotEnteringQtyGroup();
        key.setJanGroup();
        key.setItfGroup();

        if (isSet)
        {
            // 取得キーのセット
            // 商品コード
            key.setCollect(PCTRetPlan.ITEM_CODE, "", _ITEM_CODE);
            // 商品名称
            key.setCollect(PCTRetPlan.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(PCTRetPlan.LOT_ENTERING_QTY, "MAX", _LOT_QTY);
            // JANコード
            key.setCollect(PCTRetPlan.JAN, "MAX", _JAN);
            // ケースITF
            key.setCollect(PCTRetPlan.ITF, "MAX", _ITF);

            // ソートキーのセット
            // 商品コード昇順
            key.setOrder(PCTRetPlan.ITEM_CODE, true);
            // ロット入数昇順
            key.setOrder(PCTRetPlan.LOT_ENTERING_QTY, true);
        }

        return key;
    }

    /**
     * 入力されたパラメータをもとにPCT商品マスタ情報を検索するSQL文を発行します。<BR>
     * 検索を行う<code>StoragePlanFinder</code>はインスタンス変数として保持します。<BR>
     * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
     * <BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchItemKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT商品マスタ情報検索キー
        PCTItemSearchKey key = new PCTItemSearchKey();

        // 検索キーのセット
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            if (DBFormat.isPatternMatching(param.getString(ITEM_CODE)))
            {
                // ワイルドカード検索
                key.setItemCode(param.getString(ITEM_CODE));
            }
            else
            {
                // 入力された値以上を条件とする
                key.setItemCode(param.getString(ITEM_CODE), ">=");
            }
        }
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // ﾛｯﾄ入数
        if (!StringUtil.isBlank(param.getString(LOT_QTY)))
        {
            key.setLotEnteringQty(param.getInt(LOT_QTY));
        }
        // JANコード
        if (!StringUtil.isBlank(param.getString(JAN)))
        {
            key.setJan(param.getString(JAN));
        }
        // ケースITF
        if (!StringUtil.isBlank(param.getString(ITF)))
        {
            key.setItf(param.getString(ITF));
        }

        // 集約キーのセット
        // 商品コード
        key.setItemCodeGroup();
        key.setLotEnteringQtyGroup();
        key.setJanGroup();
        key.setItfGroup();

        if (isSet)
        {
            // 取得キーのセット
            // 商品コード
            key.setCollect(PCTItem.ITEM_CODE, "", _ITEM_CODE);
            // 商品名称
            key.setCollect(PCTItem.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(PCTItem.LOT_ENTERING_QTY, "MAX", _LOT_QTY);
            // JANコード
            key.setCollect(PCTItem.JAN, "MAX", _JAN);
            // ケースITF
            key.setCollect(PCTItem.ITF, "MAX", _ITF);

            // ソートキーのセット
            // 商品コード昇順
            key.setOrder(PCTItem.ITEM_CODE, true);
            // ロット入数昇順
            key.setOrder(PCTItem.LOT_ENTERING_QTY, true);
        }
        return key;
    }

    /**
     * 入力されたパラメータをもとにPCT出庫作業情報を検索するSQL文を発行します。<BR>
     * 検索を行う<code>StoragePlanFinder</code>はインスタンス変数として保持します。<BR>
     * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
     * <BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchRetWorkInfoKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey key = new PCTRetWorkInfoSearchKey();

        // 検索キーのセット
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            if (DBFormat.isPatternMatching(param.getString(ITEM_CODE)))
            {
                // ワイルドカード検索
                key.setItemCode(param.getString(ITEM_CODE));
            }
            else
            {
                // 入力された値以上を条件とする
                key.setItemCode(param.getString(ITEM_CODE), ">=");
            }
        }
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 予定日
        if (!StringUtil.isBlank(param.getString(PLAN_DAY)))
        {
            key.setPlanDay(WmsFormatter.toParamDate(param.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリアでない時は、条件をセットする
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(param.getString(REGULAR_CUSTOMER_CODE)))
        {
            key.setRegularCustomerCode(param.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(param.getString(ORDER_NO)))
        {
            key.setResultOrderNo(param.getString(ORDER_NO));
        }
        // 状態フラグ2
        if (!StringUtil.isBlank(param.getString(STATUS_FLAG2)))
        {
            // 状態フラグ１、状態フラグ２をセット        
            key.setStatusFlag(param.getString(STATUS_FLAG), "=", "(", "", false);
            key.setStatusFlag(param.getString(STATUS_FLAG2), "=", "", ")", false);
        }
        // 状態フラグ1
        else if (!StringUtil.isBlank(param.getString(STATUS_FLAG)))
        {
            // 状態フラグ１をセット
            key.setStatusFlag(param.getString(STATUS_FLAG));
        }
        else
        {
            // 状態フラグ削除以外
            key.setStatusFlag(PCTRetWorkInfo.STATUS_FLAG_DELETE, "!=");
        }
        // 結合条件
        key.setJoin(PCTRetWorkInfo.PLAN_UKEY, PCTRetPlan.PLAN_UKEY);

        // 集約キーのセット
        // 商品コード
        key.setGroup(PCTRetWorkInfo.ITEM_CODE);
        key.setGroup(PCTRetPlan.LOT_ENTERING_QTY);
        key.setGroup(PCTRetPlan.JAN);
        key.setGroup(PCTRetPlan.ITF);

        if (isSet)
        {
            // 取得キーのセット
            // 商品コード
            key.setCollect(PCTRetWorkInfo.ITEM_CODE, "", _ITEM_CODE);
            // 商品名称
            key.setCollect(PCTRetPlan.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(PCTRetPlan.LOT_ENTERING_QTY, "MAX", _LOT_QTY);
            // JANコード
            key.setCollect(PCTRetPlan.JAN, "MAX", _JAN);
            // ケースITF
            key.setCollect(PCTRetPlan.ITF, "MAX", _ITF);

            // ソートキーのセット
            // 商品コード昇順
            key.setOrder(PCTRetWorkInfo.ITEM_CODE, true);
            // ロット入数昇順
            key.setOrder(PCTRetPlan.LOT_ENTERING_QTY, true);
        }

        return key;
    }

    /**
     * 入力されたパラメータをもとにPCT出庫実績情報を検索するSQL文を発行します。<BR>
     * 検索を行う<code>ResultViewFinder</code>はインスタンス変数として保持します。<BR>
     * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
     * <BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchRetResultKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT出庫実績情報検索キー
        PCTRetResultSearchKey key = new PCTRetResultSearchKey();

        // 検索キーのセット
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            if (DBFormat.isPatternMatching(param.getString(ITEM_CODE)))
            {
                // ワイルドカード検索
                key.setItemCode(param.getString(ITEM_CODE));
            }
            else
            {
                // 入力された値以上を条件とする
                key.setItemCode(param.getString(ITEM_CODE), ">=");
            }
        }
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 作業日
        if (!StringUtil.isBlank(param.getString(WORK_DAY)))
        {
            key.setWorkDay(WmsFormatter.toParamDate(param.getDate(WORK_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリアでない時は、条件をセットする
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(param.getString(REGULAR_CUSTOMER_CODE)))
        {
            key.setRegularCustomerCode(param.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(param.getString(ORDER_NO)))
        {
            key.setResultOrderNo(param.getString(ORDER_NO));
        }
        // 状態フラグ削除以外
        key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        // 集約キーのセット
        // 商品コード
        key.setItemCodeGroup();
        key.setLotEnteringQtyGroup();
        key.setJanGroup();
        key.setItfGroup();

        if (isSet)
        {
            // 取得キーのセット
            // 商品コード
            key.setCollect(PCTRetResult.ITEM_CODE, "", _ITEM_CODE);
            // 商品名称
            key.setCollect(PCTRetResult.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(PCTRetResult.LOT_ENTERING_QTY, "MAX", _LOT_QTY);
            // JANコード
            key.setCollect(PCTRetResult.JAN, "MAX", _JAN);
            // ケースITF
            key.setCollect(PCTRetResult.ITF, "MAX", _ITF);

            // ソートキーのセット
            // 商品コード昇順
            key.setItemCodeOrder(true);
            // ロット入数昇順
            key.setOrder(PCTRetResult.LOT_ENTERING_QTY, true);
        }

        return key;
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
