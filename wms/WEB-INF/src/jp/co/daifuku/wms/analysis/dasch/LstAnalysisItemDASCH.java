// $Id: LstAnalysisItemDASCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.analysis.dbhandler.InventoryHistFinder;
import jp.co.daifuku.wms.analysis.entity.InventoryHistoryEntity;
import jp.co.daifuku.wms.analysis.schedule.AnalysisInParameter;

import static jp.co.daifuku.wms.analysis.dasch.LstAnalysisItemDASCHParams.*;

/**
 * 商品一覧（分析パッケージ）に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LstAnalysisItemDASCH
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
    // DFKLOOK Finderの変更
    private InventoryHistFinder _finder = null;

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
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public LstAnalysisItemDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        try
        {
            if (_finder != null)
            {
                _finder.close();
            }
            super.close();
        }
        catch (ReadWriteException e)
        {
            // 何もしません。
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
        _finder = new InventoryHistFinder(getConnection());
        // カーソルオープン
        _finder.open();

        _total = _finder.search(setSearchParameter(p));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException データベース接続で発生した例外を通知します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        int end = _total >= start + cnt ? start + cnt : _total;

        List<Params> params = new ArrayList<Params>();
        InventoryHistoryEntity[] ents =
                (InventoryHistoryEntity[])_finder.getEntities(start, end);

        for (InventoryHistoryEntity ent : ents)
        {
            Params p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(start + params.size() + 1));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));

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
     * @return 検索条件を設定したAnalysisInParameter
     */
    private AnalysisInParameter setSearchParameter(Params param)
    {
        // パラメータをセットします。
        AnalysisInParameter inParam = new AnalysisInParameter();
        // 荷主コード
        inParam.setConsignorCode(param.getString(LstAnalysisItemDASCHParams.CONSIGNOR_CODE));
        // 商品コード
        inParam.setItemCode(param.getString(LstAnalysisItemDASCHParams.ITEM_CODE));
        // 検索条件
        inParam.setItemListCondition(param.getString(LstAnalysisItemDASCHParams.SEARCH_COND));
        // リストボックス検索
        inParam.setListboxSearch(true);

        return inParam;
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
