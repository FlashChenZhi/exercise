// $Id: StockMoveMntDASCH.java 4808 2009-08-10 06:32:12Z shibamoto $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import static jp.co.daifuku.wms.stock.dasch.StockMoveMntDASCHParams.*;

/**
 * 在庫移動RFTメンテナンスに必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4808 $, $Date: 2009-08-10 15:32:12 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class StockMoveMntDASCH
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
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public StockMoveMntDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new MoveWorkInfoFinder(getConnection());
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
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        MoveWorkInfo[] ents = (MoveWorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        
        AreaController areaCon = new AreaController(getConnection(), this.getClass());
        
        for (MoveWorkInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(LISTNO, ent.getSettingUnitKey());
            p.set(RETRIEVAL_AREA_NO, ent.getRetrievalAreaNo());
            p.set(RETRIEVAL_LOCATION_NO, ent.getRetrievalLocationNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            p.set(LOT_NO, ent.getLotNo());
            
            int ent_qty = ((BigDecimal)ent.getValue(Item.ENTERING_QTY)).intValue();
            p.set(ENTERING_QTY, ent_qty);
            //キャンセル時
            if (StringUtil.isBlank(ent.getStorageLocationNo()))
            {
                p.set(MOVEMENT_CASE_QTY, DisplayUtil.getCaseQty(ent.getRetrievalResultQty(), ent_qty));
                p.set(MOVEMENT_PIECE_QTY, DisplayUtil.getPieceQty(ent.getRetrievalResultQty(), ent_qty));
                p.set(STORAGE_AREA_NO, "");
                p.set(STORAGE_LOCATION_NO, DispResources.getText("LBL-W0469"));

            }
            else
            {
                p.set(MOVEMENT_CASE_QTY, DisplayUtil.getCaseQty(ent.getStorageResultQty(), ent_qty));
                p.set(MOVEMENT_PIECE_QTY, DisplayUtil.getPieceQty(ent.getStorageResultQty(), ent_qty));
                p.set(STORAGE_AREA_NO, ent.getStorageAreaNo());
                p.set(STORAGE_LOCATION_NO, areaCon.toDispLocation(ent.getStorageAreaNo(), ent.getStorageLocationNo()));

            }

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
        //throw new ScheduleException("This method is not implemented.");
        MoveWorkInfoHandler handler = new MoveWorkInfoHandler(getConnection());

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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索条件キー
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();

        // 作業No.
        if (param.get(JOB_NO) != null)
        {
            key.setJobNo((String[])param.get(JOB_NO), true);
        }

        key.setJobType(MoveWorkInfo.JOB_TYPE_MOVEMENT);

        // 結合条件の指定
        // 荷主コード
        key.setJoin(MoveWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(MoveWorkInfo.ITEM_CODE, Item.ITEM_CODE);
        
        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 作業No.
            key.setSettingUnitKeyCollect();
            // 移動元エリア
            key.setRetrievalAreaNoCollect();
            // 移動元棚
            key.setRetrievalLocationNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setCollect(Item.ITEM_NAME);
            // ロットNo.
            key.setLotNoCollect();
            // ケース入数
            key.setCollect(Item.ENTERING_QTY);
            // 移動数（出庫数)
            key.setRetrievalResultQtyCollect();
            // 移動数（入庫数)
            key.setStorageResultQtyCollect();
            // 移動入庫エリア(移動先エリア)
            key.setStorageAreaNoCollect();
            // 移動入庫棚(移動先棚)
            key.setStorageLocationNoCollect();

            // 移動元エリア
            key.setRetrievalAreaNoOrder(true);
            // 移動元棚
            key.setRetrievalLocationNoOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // ロットNo.
            key.setLotNoOrder(true);
            // 移動入庫エリア(移動先エリア)
            key.setStorageAreaNoOrder(true);
            // 移動入庫棚(移動先棚)
            key.setStorageLocationNoOrder(true);
   
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
