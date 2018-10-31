package jp.co.daifuku.wms.replenish.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCHParams.*;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 補充作業リスト発行に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 7585 $, $Date: 2010-03-15 20:21:44 +0900 (月, 15 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class ReplenishWorkDASCH
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
    public ReplenishWorkDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
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

    }
    
    /**
     * 次のデータが存在するかどうかを判定します。
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
     * データを1件返します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        
        AreaController areCont = new AreaController(getConnection(), this.getClass());
        // get Next entity from finder class
        MoveWorkInfo[] ents = (MoveWorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (MoveWorkInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            
            // 設定単位キー
            p.set(JOB_NO, ent.getSettingUnitKey());
            // 補充元エリア
            p.set(REP_RETRIEVAL_AREA_NO, ent.getRetrievalAreaNo());
            // 補充元エリア(名称)
            p.set(REP_RETRIEVAL_AREA_NAME, (String)ent.getValue(Area.AREA_NAME, ""));
            // 作業区分
            p.set(JOB_TYPE, DisplayResource.getJobType(ent.getJobType()));
            // 補充元棚
            p.set(REP_RETRIEVAL_LOCATION_NO, ent.getRetrievalLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, (String)ent.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, ent.getLotNo());
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            // 補充先エリア
            p.set(REP_STORAGE_AREA_NO, ent.getStorageAreaNo());
            // 補充先棚
            String loc = WmsFormatter.toDispLocation(ent.getStorageLocationNo(), areCont.getLocationStyle(ent.getStorageAreaNo()));
            p.set(REP_LOCATION_NO, loc);

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
        MoveWorkInfoHandler handler = new MoveWorkInfoHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        throw new RuntimeException("This method is not implemented.");
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param key 移動作業情報キー
     * @throws ReadWriteException データベース処理でエラーが発生した場合にthrowします。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
    {
        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();

        // 取得条件
        key.setSettingUnitKey((String[])param.get(SETTING_UKEYS), true);

        // 結合条件
        key.setJoin(MoveWorkInfo.RETRIEVAL_AREA_NO, Area.AREA_NO);
        key.setJoin(MoveWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(MoveWorkInfo.ITEM_CODE, Item.ITEM_CODE);

        if (isSet)
        {
            // 取得項目
            key.setSettingUnitKeyCollect();
            key.setRetrievalAreaNoCollect();
            key.setCollect(Area.AREA_NAME);
            key.setRetrievalLocationNoCollect();
            key.setItemCodeCollect();
            key.setCollect(Item.ITEM_NAME);
            key.setLotNoCollect();
            key.setCollect(Item.ENTERING_QTY);
            key.setPlanQtyCollect();
            key.setStorageAreaNoCollect();
            key.setStorageLocationNoCollect();
            key.setJobTypeCollect();
            
            
            // パラメータで受け取った移動作業情報検索キーにソート順を以下にセットする
            key.setSettingUnitKeyOrder(true);
            key.setRetrievalAreaNoOrder(true);
            key.setRetrievalLocationNoOrder(true);
            key.setItemCodeOrder(true);
            key.setLotNoOrder(true);
            key.setStorageAreaNoOrder(true);
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
