// $Id: InventoryCheckDASCH.java 4807 2009-08-10 06:30:29Z shibamoto $
package jp.co.daifuku.wms.inventorychk.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;

/**
 * 棚卸開始に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4807 $, $Date: 2009-08-10 15:30:29 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class InventoryCheckDASCH
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
    public InventoryCheckDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new InventWorkInfoFinder(getConnection());
        // Initialize record counts
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
        // get Next entity from finder class
        InventWorkInfo[] ents = (InventWorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object

        int ent_qty = 0;
        long case_qty = 0;
        long piece_qty = 0;
        String itemCode = null;
        String itemName = null;


        for (InventWorkInfo ent : ents)
        {
            itemCode = ent.getItemCode();
            itemName = String.valueOf(ent.getValue(Item.ITEM_NAME, ""));

            if (StringUtil.isBlank(itemCode))
            {
                itemName = "";
                ent_qty = 0;
                case_qty = 0;
                piece_qty = 0;
            }
            else
            {
                ent_qty = ((BigDecimal)ent.getValue(Item.ENTERING_QTY)).intValue();
                case_qty =
                        DisplayUtil.getCaseQty(ent.getStockQty(),
                                ((BigDecimal)ent.getValue(Item.ENTERING_QTY)).intValue());
                piece_qty =
                        DisplayUtil.getPieceQty(ent.getStockQty(),
                                ((BigDecimal)ent.getValue(Item.ENTERING_QTY)).intValue());
            }

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(JOB_NO, ent.getSettingUnitKey());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(AREA_NO, ent.getAreaNo());
            p.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME, "")));
            p.set(LOCATION_NO, ent.getLocationNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, itemName);
            p.set(LOT_NO, ent.getLotNo());
            p.set(ENTERING_QTY, ent_qty);
            p.set(STOCK_CASE_QTY, case_qty);
            p.set(STOCK_PIECE_QTY, piece_qty);
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
        InventWorkInfoHandler handler = new InventWorkInfoHandler(getConnection());

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
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();
        // パラメータで受け取ったInventWorkInfoSearchKeyに以下をセットする。
        //荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));

        //条件指定により、検索条件を変更する
        if (InventoryInParameter.COLLECT_STATUS_LISTNO.equals(param.getString(INVENTORY)))
        {
            //リスト作業NO指定
            if (!StringUtil.isBlank(param.getString(LIST_WORK_NO)))
            {
                key.setSettingUnitKey(param.getString(LIST_WORK_NO), "=");
            }
        }
        else
        {
            //エリアNo
            key.setAreaNo(param.getString(AREA_NO));
            
            String[] loc = WmsFormatter.getFromTo(param.getString(LOCATION_FROM), param.getString(LOCATION_TO));
            
            //開始棚
            if (!StringUtil.isBlank(loc[0]))
            {
                key.setLocationNo(loc[0], ">=");
            }

            //終了棚
            if (!StringUtil.isBlank(loc[1]))
            {
                key.setLocationNo(loc[1], "<=");
            }

            //アイテムコード
            if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
            {
                key.setItemCode(param.getString(ITEM_CODE), "=");
            }
            //新規棚卸データのみ表示の場合
            if (param.getBoolean(INVENTORY_ONLY_DISP))
            {
                key.setStockQty(0, "<=");
                key.setItemCode("", "IS NOT NULL");
            }
        }

        key.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        key.setJoin(InventWorkInfo.AREA_NO, Area.AREA_NO);
        key.setJoin(InventWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        key.setJoin(InventWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");


        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setSettingUnitKeyOrder(true);
            key.setAreaNoOrder(true);
            key.setLocationNoOrder(true);
            key.setItemCodeOrder(true);
            key.setLotNoOrder(true);
        }

        //項目取得
        key.setSettingUnitKeyCollect();
        key.setAreaNoCollect();
        key.setCollect(Area.AREA_NAME);
        key.setLocationNoCollect();
        key.setItemCodeCollect();
        key.setCollect(Item.ITEM_NAME);
        key.setLotNoCollect();
        key.setCollect(Item.ENTERING_QTY);
        key.setStockQtyCollect();

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
