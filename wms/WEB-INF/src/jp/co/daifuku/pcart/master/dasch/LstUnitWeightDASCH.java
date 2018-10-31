// $Id: LstUnitWeightDASCH.java 4803 2009-08-10 06:24:09Z shibamoto $
package jp.co.daifuku.pcart.master.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.dasch.LstUnitWeightDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 単重量一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4803 $, $Date: 2009-08-10 15:24:09 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstUnitWeightDASCH
        extends AbstractWmsDASCH
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
    public LstUnitWeightDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // Create Finder Object
        _finder = new PCTItemFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;

        setMessage("6001013");
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
        _current++;
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
        throw new ScheduleException("This method is not implemented.");
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
        //throw new ScheduleException("This method is not implemented.");
        PCTItemHandler handler = new PCTItemHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        PCTItem[] ents = (PCTItem[])_finder.getEntities(start, start + cnt);

        for (PCTItem ent : ents)
        {
            Params p = new Params();
            // 単重量
            p.set(SINGLE_WEIGHT, WmsFormatter.getNumFormat(ent.getSingleWeight()));
            // 誤差率
            p.set(WEIGHT_DISTINCT_RATE, ent.getWeightDistinctRate());
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        PCTItemSearchKey key = new PCTItemSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        if (!StringUtil.isBlank(param.getString(FROM_SINGLE_WEIGHT))
                && !StringUtil.isBlank(param.getString(TO_SINGLE_WEIGHT)))
        {
            int comparison = param.getString(FROM_SINGLE_WEIGHT).compareTo(param.getString(TO_SINGLE_WEIGHT));
            if (0 < comparison)
            {
                // 範囲重量
                key.setSingleWeight(Double.parseDouble(param.getString(TO_SINGLE_WEIGHT)), ">=");
                key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), "<=");
            }
            else
            {
                // 範囲重量
                key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), ">=");
                key.setSingleWeight(Double.parseDouble(param.getString(TO_SINGLE_WEIGHT)), "<=");
            }
        }
        else
        {
            if (!StringUtil.isBlank(param.getString(FROM_SINGLE_WEIGHT)))
            {
                key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), ">=");
            }
            else if (!StringUtil.isBlank(param.getString(TO_SINGLE_WEIGHT)))
            {
                key.setSingleWeight(Double.parseDouble(param.getString(TO_SINGLE_WEIGHT)), "<=");
            }
        }

        if (isSet)
        {
            // 取得順序
            // 単重量の昇順
            key.setSingleWeightOrder(true);
            // 荷主コードの昇順
            key.setConsignorCodeOrder(true);
            // 商品コードの昇順
            key.setItemCodeOrder(true);
            // ロット入数の昇順
            key.setLotEnteringQtyOrder(true);
            // 取得項目
            // 単重量
            key.setSingleWeightCollect();
            // 誤差率
            key.setWeightDistinctRateCollect();
            // 荷主コード
            key.setConsignorCodeCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setItemNameCollect();
            // ロット入数
            key.setLotEnteringQtyCollect();
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
