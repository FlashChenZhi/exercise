// $Id: FaStorageListDASCH.java 7351 2010-03-04 04:18:36Z kishimoto $
package jp.co.daifuku.wms.storage.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.storage.dasch.FaStorageListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.WorkListFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkListSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.storage.schedule.StorageInParameter;

/**
 * BusiTuneで生成されたDASCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7351 $, $Date:: 2010-03-04 13:18:36 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaStorageListDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 取得フィールド（MAX_REGIST_DATE）
     */
    private static FieldName MAX_REGIST_DATE = new FieldName("", "MAX_REGIST_DATE", "MAX_REGIST_DATE");
    
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
    public FaStorageListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new WorkListFinder(getConnection());
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
        WorkList[] ents = (WorkList[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (WorkList ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(SETTING_UNIT_KEY, ent.getSettingUnitKey());
            p.set(STATION_NO, ent.getSourceStationNo());
            p.set(STATION_NAME, ent.getValue(Station.STATION_NAME));
            p.set(WORK_NO, ent.getWorkNo());
            p.set(LOCATION_NO, ent.getPlanLocationNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getItemName());
            p.set(LOT_NO, ent.getPlanLotNo());
            p.set(WORK_QTY, ent.getPlanQty());
            p.set(STOCK_QTY, ent.getStockQty());
            p.set(AREA_NO, ent.getPlanAreaNo());
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
        WorkListHandler handler = new WorkListHandler(getConnection());

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
        List<Params> params = new ArrayList<Params>();
        WorkList[] ents = (WorkList[])_finder.getEntities(start, start + cnt);

        AreaController area = new AreaController(getConnection(), getClass());
        for (WorkList ent : ents)
        {
            Params p = new Params();
            
            // リスト種別は作業区分、エリア区分から求める
            String type = area.getAreaType(ent.getPlanAreaNo());
            if (Area.AREA_TYPE_ASRS.equals(type))
            {
                if (WorkList.JOB_TYPE_NOPLAN_STORAGE.equals(ent.getJobType()))
                {
                    // AS/RS入庫作業リスト
                    p.set(WORK_TYPE, StorageInParameter.SEARCH_ASRS_STORAGE_LIST);
                    p.set(WORK_TYPE_NAME, DisplayResource.getStorageListName(ent.getJobType(), type));
                }
                else if (WorkList.JOB_TYPE_RESTORING.equals(ent.getJobType()))
                {
                    // AS/RS再入庫作業リスト
                    p.set(WORK_TYPE, StorageInParameter.SEARCH_ASRS_RESTORING_LIST);
                    p.set(WORK_TYPE_NAME, DisplayResource.getStorageListName(ent.getJobType(), type));
                }
            }
            else
            {
                // 平置入庫作業リスト
                p.set(WORK_TYPE, StorageInParameter.SEARCH_FLOOR_STORAGE_LIST);
                p.set(WORK_TYPE_NAME, DisplayResource.getStorageListName(ent.getJobType(), type));
            }
            p.set(SETTING_DATE, ent.getDate(MAX_REGIST_DATE));
            p.set(SETTING_UNIT_KEY, ent.getSettingUnitKey());
            p.set(STATION_NO, ent.getValue(WorkList.SOURCE_STATION_NO));

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
        WorkListSearchKey key = new WorkListSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 設定単位キーを取得
        List<String> key_list = (ArrayList<String>)param.get(SETTING_UNIT_KEY);
        if (key_list != null && !key_list.isEmpty())
        {
            List<String> stlist = (ArrayList<String>)param.get(STATION_NO);

            // 設定画面から呼ばれたときはステーションはセットされていない
            if (stlist == null || stlist.isEmpty())
            {
	            String[] ukeys = new String[key_list.size()];
	            key_list.toArray(ukeys);
	
	            // 設定単位キー
	            key.setSettingUnitKey(ukeys, true);
            }
            else
            {
	            int size = key_list.size();
	            // データが2件以上の場合の処理
	            if (size > 1)
	            {
	                // 1件目の処理
	            	// 設定単位キー
	            	key.setSettingUnitKey(key_list.get(0), "=", "((", "", true);
	                // ステーションNo.
	            	key.setSourceStationNo(stlist.get(0), "=", "", ")", false);	            
	            
	            	// 間の行の処理
		            for (int i = 1; i < size - 1; i ++)
		            {
		                // 設定単位キー
		            	key.setSettingUnitKey(key_list.get(i), "=", "(", "", true);
		                // ステーションNo.
		            	key.setSourceStationNo(stlist.get(i), "=", "", ")", false);
		            }
		            
		            // 最後の行の処理
	                // 設定単位キー
	            	key.setSettingUnitKey(key_list.get(size - 1), "=", "(", "", true);
	                // ステーションNo.
	            	key.setSourceStationNo(stlist.get(size - 1), "=", "", "))", true);	            
	            }
	            // データが1件の場合の処理
	            else
	            {
	                // 設定単位キー
	            	key.setSettingUnitKey(key_list.get(0));
	                // ステーションNo.
	            	key.setSourceStationNo(stlist.get(0));          	
	            }
            }
            key.setJoin(WorkList.SOURCE_STATION_NO, "", Station.STATION_NO, "(+)");
        }
        else
        {
            // 設定単位キーが空の場合は、一覧表示検索
            // エリア
            if (!StringUtil.isBlank(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
          
            // ステーション
            if (!param.getString(STATION_NO).startsWith(StorageInParameter.SELECT_STATION_NONE))
            {
                try
                {
                    Station st = StationFactory.makeStation(getConnection(), param.getString(STATION_NO));
                    
                    // 設定ステーションが作業場の場合のみステーションを限定する
                    if (st instanceof WorkPlace)
                    {
                        WorkPlace wp = (WorkPlace)st;
                        
                        // 作業場にある全ステーションをセット
                        key.setKey(WorkList.SOURCE_STATION_NO, wp.getWPStations(), true);
                    }
                    else if (st instanceof Station)
                    {
                        key.setKey(WorkList.SOURCE_STATION_NO, st.getStationNo());
                    }
                }
                catch (NotFoundException e)
                {
                    // ステーションが見つからなかった場合
                    throw new InvalidDefineException();
                }            	// ステーションNo.の取得
            }
          
            // 作業種別
            if (!StringUtil.isBlank(param.getString(WORK_TYPE)))
            {
                if (StorageInParameter.SEARCH_ALL.equals(param.getString(WORK_TYPE)))
                {
                    // 全て選択時は予定外入庫、再入庫
                    String[] jobtype = {WorkList.JOB_TYPE_NOPLAN_STORAGE, WorkList.JOB_TYPE_RESTORING};
                    key.setJobType(jobtype, true);
                }
                else if (StorageInParameter.SEARCH_ASRS_STORAGE_LIST.equals(param.getString(WORK_TYPE)))
                {
                    // AS/RS入庫作業リストが選択された場合は、予定外入庫
                    key.setJobType(WorkList.JOB_TYPE_NOPLAN_STORAGE);
                    // 予定エリアがAS/RSエリア
                    key.setJoin(WorkList.PLAN_AREA_NO, Area.AREA_NO);
                    key.setKey(Area.AREA_TYPE, Area.AREA_TYPE_ASRS);
                }
                else if (StorageInParameter.SEARCH_ASRS_RESTORING_LIST.equals(param.getString(WORK_TYPE)))
                {
                    // AS/RS再入庫作業リストが選択された場合は、再入庫
                    key.setJobType(WorkList.JOB_TYPE_RESTORING);
                }
                else if (StorageInParameter.SEARCH_FLOOR_STORAGE_LIST.equals(param.getString(WORK_TYPE)))
                {
                    // 平置入庫作業リストが選択された場合は、予定外入庫
                    key.setJobType(WorkList.JOB_TYPE_NOPLAN_STORAGE);
                    // 予定エリアが平置エリア
                    key.setJoin(WorkList.PLAN_AREA_NO, Area.AREA_NO);
                    key.setKey(Area.AREA_TYPE, Area.AREA_TYPE_FLOOR);
                }
            }
          
            // 開始検索日時、終了検索日時
            if (!(StringUtil.isBlank(param.getDate(FROM_SEARCH_DAY)) && StringUtil.isBlank(param.getDate(TO_SEARCH_DAY))))
            {
                Date[] tmp =
                        WmsFormatter.getFromTo(param.getDate(FROM_SEARCH_DAY), param.getDate(FROM_SEARCH_TIME),
                                param.getDate(TO_SEARCH_DAY), param.getDate(TO_SEARCH_TIME));
                key.setRegistDate(tmp[0], ">=");
                key.setRegistDate(tmp[1], "<");
            }
            
            // グループ
            key.setSettingUnitKeyGroup();
            key.setJobTypeGroup();
            key.setPlanAreaNoGroup();
            key.setSourceStationNoGroup();
        }

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            if (key_list != null && !key_list.isEmpty())
            {
                // 印刷時
                // 作業単位キー
                key.setSettingUnitKeyCollect();
                // ステーションNo.
                key.setSourceStationNoCollect();
                // ステーション名称
                key.setCollect(Station.STATION_NAME);
                // 作業No.
                key.setWorkNoCollect();
                // 棚No.
                key.setPlanLocationNoCollect();
                // 商品コード
                key.setItemCodeCollect();
                // 商品名称
                key.setItemNameCollect();
                // ロットNo.
                key.setPlanLotNoCollect();
                // 作業数
                key.setPlanQtyCollect();
                // 在庫数
                key.setStockQtyCollect();
                // エリアNo.
                key.setPlanAreaNoCollect();
                
                // ソート順
                // リスト種別によってソートは変更
                if (StorageInParameter.SEARCH_ASRS_STORAGE_LIST.equals(param.getString(WORK_TYPE))
                        || StorageInParameter.SEARCH_ASRS_RESTORING_LIST.equals(param.getString(WORK_TYPE)))
                {
                    // AS/RS入庫作業リストorAS/RS再入庫作業リスト
                    key.setSourceStationNoOrder(true);
                    key.setWorkNoOrder(true);
                    key.setItemCodeOrder(true);
                    key.setPlanLotNoOrder(true);
                }
                else if (StorageInParameter.SEARCH_FLOOR_STORAGE_LIST.equals(param.getString(WORK_TYPE)))
                {
                    // 平置入庫作業リスト
                    key.setSettingUnitKeyOrder(true);
                    key.setPlanAreaNoOrder(true);
                    key.setPlanLocationNoOrder(true);
                    key.setItemCodeOrder(true);
                    key.setPlanLotNoOrder(true);
                }
            }
            else
            {
                // 一覧表示時
                // 設定単位キー
                key.setSettingUnitKeyCollect();
                // 作業区分
                key.setJobTypeCollect();
                // エリアNo.
                key.setPlanAreaNoCollect();
                // ステーションNo.
                key.setSourceStationNoCollect();
                // 登録日時
                key.setCollect(WorkList.REGIST_DATE, "MAX", MAX_REGIST_DATE);
                
                // ソート順
                key.setOrder(MAX_REGIST_DATE, false);
                key.setSettingUnitKeyOrder(true);
            }
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
