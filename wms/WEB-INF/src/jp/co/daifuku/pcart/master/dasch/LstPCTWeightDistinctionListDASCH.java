// $Id: LstPCTWeightDistinctionListDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.master.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.dasch.LstPCTWeightDistinctionListDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctFinder;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctHandler;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctSearchKey;
import jp.co.daifuku.wms.base.entity.WeightDistinct;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 重量差異リスト発行一覧のスケジュール処理を行います。
 *
 * @version $Revision: 4689 $, $Date:: 2009-07-16 09:23:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTWeightDistinctionListDASCH
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
    public LstPCTWeightDistinctionListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new WeightDistinctFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;
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
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        WeightDistinct[] ents = (WeightDistinct[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (WeightDistinct ent : ents)
        {

            // 差を算出
            BigDecimal singleWeight = new BigDecimal(String.valueOf(ent.getSingleWeight()));
            BigDecimal correctWeight = new BigDecimal(String.valueOf(ent.getCorrectWeight()));
            singleWeight = singleWeight.subtract(correctWeight);

            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getConsignorName());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // 入数
            p.set(QTY, ent.getLotEnteringQty());
            // 単重量
            p.set(SINGLE_WEIGHT, ent.getSingleWeight());
            // 重量
            p.set(WEIGHT, ent.getInspectWeight());
            // 数量
            p.set(AMOUNT, ent.getInspectQty());
            // 訂正数量
            p.set(CORRECT_QTY, ent.getCorrectQty());
            // 訂正単重量
            p.set(CORRECT_WEIGHT, WmsFormatter.getNumFormat(ent.getCorrectWeight()));
            // 差
            p.set(DIFFERENCE, WmsFormatter.getNumFormat(singleWeight.doubleValue()));
            // 発生日
            p.set(OCCUR_DAY, WmsFormatter.toDispDate(ent.getRegistDate(), getLocale()));
            // 発生時刻
            p.set(OCCUR_TIME, WmsFormatter.toDispTime(ent.getRegistDate(), getLocale()));
            // 号機No.
            p.set(RFT_NO, ent.getTerminalNo());
            // 間口
            p.set(FRONTAGE, ent.getLocationNo());
            // ユーザID
            p.set(USER_ID, ent.getUserId());
            // ユーザ名称
            p.set(USER_NAME, ent.getUserName());
        }
        // return Pram objstc
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
        // 重量差異情報データベースハンドラ
        WeightDistinctHandler handler = new WeightDistinctHandler(getConnection());

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
        WeightDistinct[] ents = (WeightDistinct[])_finder.getEntities(start, start + cnt);

        for (WeightDistinct ent : ents)
        {
            Params p = new Params();
            // 差を算出
            BigDecimal singleWeight = new BigDecimal(String.valueOf(ent.getSingleWeight()));
            BigDecimal correctWeight = new BigDecimal(String.valueOf(ent.getCorrectWeight()));
            singleWeight = singleWeight.subtract(correctWeight);

            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getConsignorName());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // 入数
            p.set(QTY, ent.getLotEnteringQty());
            // 単重量
            p.set(SINGLE_WEIGHT, ent.getSingleWeight());
            // 重量
            p.set(WEIGHT, ent.getInspectWeight());
            // 数量
            p.set(AMOUNT, ent.getInspectQty());
            // 訂正数量
            p.set(CORRECT_QTY, ent.getCorrectQty());
            // 訂正単重量
            p.set(CORRECT_WEIGHT, WmsFormatter.getNumFormat(ent.getCorrectWeight()));
            // 差
            p.set(DIFFERENCE, WmsFormatter.getNumFormat(singleWeight.doubleValue()));
            // 発生日
            p.set(OCCUR_DAY, WmsFormatter.toDispDate(ent.getRegistDate(), getLocale()));
            // 発生時刻
            p.set(OCCUR_TIME, WmsFormatter.toDispTime(ent.getRegistDate(), getLocale()));
            // 号機No.
            p.set(RFT_NO, ent.getTerminalNo());
            // 間口
            p.set(FRONTAGE, ent.getLocationNo());
            // ユーザID
            p.set(USER_ID, ent.getUserId());
            // ユーザ名称
            p.set(USER_NAME, ent.getUserName());


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
        WeightDistinctSearchKey key = new WeightDistinctSearchKey();

        String fromDate = WmsFormatter.toParamDate(param.getDate(FROM_OCCUR_DAY));
        String fromTime = WmsFormatter.toParamTime(param.getDate(FROM_OCCUR_TIME));
        String toDate = WmsFormatter.toParamDate(param.getDate(TO_OCCUR_DAY));
        String toTime = WmsFormatter.toParamTime(param.getDate(TO_OCCUR_TIME));
        int comparison = 0;

        // 出力日時
        if (!StringUtil.isBlank(fromDate) && !StringUtil.isBlank(toDate))
        {
            comparison = fromDate.compareTo(toDate);
            if (0 == comparison)
            {
                comparison = fromTime.compareTo(toTime);
                if (0 == comparison)
                {
                    key.setRegistDate(WmsFormatter.getSearchFromDate(fromDate, fromTime), ">=", "", "", true);
                    key.setRegistDate(WmsFormatter.getSearchToDate(toDate, toTime), "<", "", "", true);
                }
                else
                {
                    if (0 > comparison)
                    {
                        key.setRegistDate(WmsFormatter.getSearchFromDate(fromDate, fromTime), ">=", "", "", true);
                        key.setRegistDate(WmsFormatter.getSearchFromDate(toDate, toTime), "<", "", "", true);
                    }
                    else
                    {
                        key.setRegistDate(WmsFormatter.getSearchFromDate(toDate, toTime), ">=", "", "", true);
                        key.setRegistDate(WmsFormatter.getSearchFromDate(fromDate, fromTime), "<", "", "", true);
                    }

                }
            }
            else
            {
                if (0 > comparison)
                {
                    key.setRegistDate(WmsFormatter.getSearchFromDate(fromDate, fromTime), ">=", "", "", true);
                    key.setRegistDate(WmsFormatter.getSearchFromDate(toDate, toTime), "<", "", "", true);
                }
                else
                {
                    key.setRegistDate(WmsFormatter.getSearchFromDate(toDate, toTime), ">=", "", "", true);
                    key.setRegistDate(WmsFormatter.getSearchFromDate(fromDate, fromTime), "<", "", "", true);
                }
            }
        }
        else
        {
            if (!StringUtil.isBlank(fromDate))
            {
                key.setRegistDate(WmsFormatter.getSearchFromDate(fromDate, fromTime), ">=", "", "", true);
            }
            else
            {
                if (!StringUtil.isBlank(toDate))
                {
                    key.setRegistDate(WmsFormatter.getSearchFromDate(toDate, toTime), "<", "", "", true);
                }
            }
        }

        // 検索条件
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }

        if (isSet)
        {
            // ソート順
            key.setItemCodeOrder(true);
            key.setRegistDateOrder(false);
            key.setTerminalNoOrder(true);
            key.setLocationNoOrder(true);

            // 取得条件
            key.setConsignorCodeCollect();
            key.setConsignorNameCollect();
            key.setItemCodeCollect();
            key.setItemNameCollect();
            key.setLotEnteringQtyCollect();
            key.setSingleWeightCollect();
            key.setInspectWeightCollect();
            key.setInspectQtyCollect();
            key.setCorrectQtyCollect();
            key.setCorrectWeightCollect();
            key.setRegistDateCollect();
            key.setTerminalNoCollect();
            key.setLocationNoCollect();
            key.setUserIdCollect();
            key.setUserNameCollect();
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
