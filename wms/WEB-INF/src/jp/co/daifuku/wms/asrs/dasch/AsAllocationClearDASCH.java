// $Id: AsAllocationClearDASCH.java 4911 2009-08-25 06:17:19Z ota $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.dasch.AsAllocationClearDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.DeleteStockFinder;
import jp.co.daifuku.wms.base.dbhandler.DeleteStockHandler;
import jp.co.daifuku.wms.base.dbhandler.DeleteStockSearchKey;
import jp.co.daifuku.wms.base.entity.DeleteStock;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * AS/RS搬送データクリア処理の帳票の検索処理を行います。
 *
 * @version $Revision: 4911 $, $Date:: 2009-08-25 15:17:19 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsAllocationClearDASCH
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

    /**
     * クラス返送
     */
    private static final String ALLOCATE_YES = "*";

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
    public AsAllocationClearDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new DeleteStockFinder(getConnection());
        // Initialize record counts
        _finder.open(true);
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
        // get Next entity from finder class
        DeleteStock[] ents = (DeleteStock[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (DeleteStock ent : ents)
        {

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            String allocationFlag = ALLOCATE_YES;
            if (StringUtil.isBlank(ent.getSystemConnKey()))
            {
                allocationFlag = "";
            }
            p.set(ALLOCATION_FLAG, allocationFlag);
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            p.set(LOT_NO, ent.getLotNo());
            p.set(AREA_NO, ent.getAreaNo());
            p.set(LOCATION_NO, ent.getLocationNo());
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            p.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(ent.getStockQty(), enteringQty));
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(ent.getStockQty(), enteringQty));
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));
            p.set(STORAGE_DAY, ent.getDate(Stock.STORAGE_DATE));
            p.set(STORAGE_TIME, ent.getDate(Stock.STORAGE_DATE));
            break;
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
        DeleteStockHandler handler = new DeleteStockHandler(getConnection());

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
        DeleteStock[] ents = (DeleteStock[])_finder.getEntities(start, start + cnt);

        for (DeleteStock ent : ents)
        {
            Params p = new Params();
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            String allocationFlag = ALLOCATE_YES;
            if (StringUtil.isBlank(ent.getSystemConnKey()))
            {
                allocationFlag = "";
            }
            p.set(ALLOCATION_FLAG, allocationFlag);
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            p.set(LOT_NO, ent.getLotNo());
            p.set(AREA_NO, ent.getAreaNo());
            p.set(LOCATION_NO, ent.getLocationNo());
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            p.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(ent.getStockQty(), enteringQty));
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(ent.getStockQty(), enteringQty));
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));
            p.set(STORAGE_DAY, ent.getDate(Stock.STORAGE_DATE));
            p.set(STORAGE_TIME, ent.getDate(Stock.STORAGE_DATE));


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
        DeleteStockSearchKey key = new DeleteStockSearchKey();

        // 検索条件、集約条件をセットする
        // 
        key.setJoin(DeleteStock.ITEM_CODE, Item.ITEM_CODE);

        key.setRegistDate(param.getDate(START_DATE), ">=");

        if (isSet)
        {
            // 登録順
            key.setRegistDateOrder(true);
            key.setSystemConnKeyOrder(true);

            key.setItemCodeCollect();
            key.setCollect(Item.ITEM_NAME);
            key.setLotNoCollect();
            key.setAreaNoCollect();
            key.setLocationNoCollect();
            key.setCollect(Item.ENTERING_QTY);
            key.setStockQtyCollect();
            key.setCollect(Item.JAN);
            key.setCollect(Item.ITF);
            key.setStorageDateCollect();
            key.setSystemConnKeyCollect();

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
