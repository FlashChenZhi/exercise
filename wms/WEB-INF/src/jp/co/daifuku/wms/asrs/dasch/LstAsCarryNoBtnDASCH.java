// $Id: LstAsCarryNoBtnDASCH.java 6256 2009-11-30 09:50:50Z okayama $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.LstAsCarryNoBtnDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 残作業一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 6256 $, $Date: 2009-11-30 18:50:50 +0900 (月, 30 11 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstAsCarryNoBtnDASCH
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
    public LstAsCarryNoBtnDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new CarryInfoFinder(getConnection());
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
        CarryInfoHandler handler = new CarryInfoHandler(getConnection());
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

        CarryInfo[] ents = (CarryInfo[])_finder.getEntities(start, start + cnt);
        StationController station = new StationController(getConnection(), getClass());
        AreaController area = new AreaController(getConnection(), this.getClass());

        for (CarryInfo ent : ents)
        {
            Params p = new Params();

            // ボタン番号
            p.set(NO, String.valueOf(params.size() + 1 + start));

            // 入庫時は、ステーションから棚への搬送
            // 搬送先にはアイルをセットする
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ent.getCarryFlag()))
            {
                try
                {
                    p.set(LOCATION_NO, area.getAreaNoOfWarehouse(ent.getDestStationNo()));
                    p.set(TO_LOCATION_NO, area.getAreaNoOfWarehouse(ent.getDestStationNo()));
                }
                catch (CommonException e)
                {
                    p.set(LOCATION_NO, ent.getDestStationNo());
                    p.set(TO_LOCATION_NO, ent.getDestStationNo());
                }

                // 搬送元ステーションNo.
                p.set(SOURCE_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(DEST_STATION_NO, ent.getAisleStationNo());
                // ステーションNo.
                p.set(STATION_NO, ent.getSourceStationNo());
            }
            // 出庫時は、棚からステーションへの搬送
            // 搬送元にはアイルをセットする
            else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ent.getCarryFlag()))
            {
                String locationno = null;
                // 在庫確認の場合は出庫ロケーションNo.をセット
                if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ent.getRetrievalDetail()))
                {
                    locationno = ent.getRetrievalStationNo();
                }
                // 在庫確認以外の場合は搬送元ステーションをセット
                else
                {
                    locationno = ent.getSourceStationNo();
                }

                if (!StringUtil.isBlank(locationno))
                {
                    p.set(LOCATION_NO, locationno);
                }
                else
                {
                    p.set(LOCATION_NO, "");
                }
                // 搬送元ステーションNo.
                p.set(SOURCE_STATION_NO, ent.getAisleStationNo());
                // 搬送先ステーションNo.
                p.set(DEST_STATION_NO, ent.getDestStationNo());
                // ステーションNo.
                p.set(STATION_NO, ent.getDestStationNo());

            }
            // 直行時は、ステーションからステーションへの移動
            // ロケーションNo.にはなにも表示しない
            // 搬送元、搬送先には各ステーションNo.をセットする
            // 画面上、ステーションNo.にはなにも表示しない
            else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ent.getCarryFlag()))
            {
                // 棚
                p.set(LOCATION_NO, "");
                // 搬送元ステーションNo.
                p.set(SOURCE_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(DEST_STATION_NO, ent.getDestStationNo());
                // ステーションNo.
                p.set(STATION_NO, "");

            }
            // 棚間移動時は、棚から棚への移動
            // 画面上、ロケーションNo.にはなにも表示しない
            // 搬送元搬送先にはアイルをセットする
            // ステーションはなにもセットしない
            else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ent.getCarryFlag()))
            {
                // 棚
                if (!StringUtil.isBlank(ent.getRetrievalStationNo()))
                {
                    p.set(LOCATION_NO, ent.getRetrievalStationNo());
                }
                else
                {
                    p.set(LOCATION_NO, "");
                }
                // 搬送元ステーションNo.
                p.set(SOURCE_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(DEST_STATION_NO, ent.getDestStationNo());
                // ステーションNo.
                p.set(STATION_NO, "");

            }
            else
            {
                // 棚
                p.set(LOCATION_NO, "");
                // 搬送元ステーションNo.
                p.set(SOURCE_STATION_NO, "");
                // 搬送先ステーションNo.
                p.set(DEST_STATION_NO, "");
                // ステーションNo.
                p.set(STATION_NO, "");
            }

            try
            {
                // 搬送元ステーション名称
                if (!StringUtil.isBlank(p.getString(SOURCE_STATION_NO)))
                {
                    String sourceStationName = station.getAsStationName(p.getString(SOURCE_STATION_NO));
                    p.set(SOURCE_STATION_NAME, sourceStationName);
                }
                else
                {
                    p.set(SOURCE_STATION_NAME, "");
                }

                // 搬送先ステーション名称
                if (!StringUtil.isBlank(p.getString(DEST_STATION_NO)))
                {
                    String destStationName = station.getAsStationName(p.getString(DEST_STATION_NO));
                    p.set(DEST_STATION_NAME, destStationName);
                }
                else
                {
                    p.set(DEST_STATION_NAME, "");
                }

                // ステーション名称
                if (!StringUtil.isBlank(p.getString(STATION_NO)))
                {
                    String stationName = station.getAsStationName(p.getString(STATION_NO));
                    p.set(STATION_NO, stationName);
                }
                else
                {
                    p.set(STATION_NO, "");
                }
            }
            catch (CommonException e)
            {

                e.printStackTrace();
            }

            // 優先区分名称
            p.set(PRIORITY_NAME, DisplayResource.getPriority(ent.getPriority()));
            // 搬送区分名称
            p.set(CARRY_FLAG_NAME, DisplayResource.getCarryFlag(ent.getCarryFlag()));
            // 搬送状態名称
            p.set(CMD_STATUS_NAME, DisplayResource.getCmdStatus(ent.getCmdStatus()));
            // 作業種別名称
            p.set(WORK_TYPE_NAME, DisplayResource.getWorkType(ent.getWorkType()));
            // 出庫指示詳細
            String detail = DisplayResource.getFaRetrievalDetail(ent.getRetrievalDetail());
            p.set(RETRIEVAL_DETAIL_NAME, detail);
            // 搬送キー
            p.set(CARRY_KEY, ent.getCarryKey());
            // 作業No.
            p.set(WORK_NO, ent.getWorkNo());
            // エリアNo.
            String areaNo = String.valueOf(ent.getValue(Stock.AREA_NO, ""));
            p.set(AREA_NO, String.valueOf(areaNo));
            // スケジュールNo.
            p.set(SCHEDULE_NO, ent.getScheduleNo());

            try
            {
                // ロケーションNo.
                String paramLoc = area.toParamLocation(p.getString(LOCATION_NO));
                p.set(LOCATION_NO, toDispLocation(getConnection(), areaNo, paramLoc));

                // ロケーションNo.
                paramLoc = area.toParamLocation(p.getString(TO_LOCATION_NO));
                p.set(TO_LOCATION_NO, toDispLocation(getConnection(), areaNo, paramLoc));

                // 搬送元/搬送先
                p.set(SOURCE_DEST, station.getDispStationNo(p.getString(SOURCE_STATION_NO)) + "->"
                        + station.getDispStationNo(p.getString(DEST_STATION_NO)));
            }
            catch (CommonException e)
            {
                e.printStackTrace();
            }

            params.add(p);
        }

        return params;
    }

    /**
     * 棚形式を画面表示用に変換します。<BR>
     * スケジュールパラメータでの形式 (DnStock用) から画面表示用に
     * 形式をフォーマットします。<BR>
     * <BR>
     * 例) 01001001 -> 1-01-01 (指定エリアのフォーマットスタイルが"9-99-99"の場合)
     * 
     * @param areaNo エリアNo.
     * @param paramLocation スケジュールパラメータ形式の棚No.
     * @return テキストにセットする形式の棚No<BR>
     * エリアNoが見つからなかった場合は文字列をそのまま返します。
     * @throws CommonException フォーマットできなかった時スローされます。
     */
    protected String toDispLocation(Connection conn, String areaNo, String paramLocation)
            throws CommonException
    {
        AreaController area = new AreaController(conn, this.getClass());
        //棚フォーマットを取得する
        String format = area.getLocationStyle(areaNo);

        //エリアNoが見つからなかった場合
        if (format == null)
        {
            return paramLocation;
        }

        if (StringUtil.isBlank(paramLocation))
        {
            return "";
        }

        try
        {
            LocationNumber loc = new LocationNumber(format);
            loc.parseParam(paramLocation);
            return loc.formatDisp();
        }
        catch (RuntimeException e)
        {
            return paramLocation;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @throws CommonException 例外が発生した場合に通知します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        CarryInfoSearchKey key = new CarryInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 集約条件
        key.setCarryKeyGroup();
        key.setPriorityGroup();
        key.setCarryFlagGroup();
        key.setCmdStatusGroup();
        key.setWorkTypeGroup();
        key.setRetrievalDetailGroup();
        key.setWorkNoGroup();
        key.setSourceStationNoGroup();
        key.setDestStationNoGroup();
        key.setAisleStationNoGroup();
        key.setRetrievalStationNoGroup();
        key.setGroup(Stock.AREA_NO);
        key.setScheduleNoGroup();

        key.setJoin(CarryInfo.PALLET_ID, Stock.PALLET_ID);

        if (isSet)
        {
            // ソート順
            key.setPriorityOrder(true);
            key.setCarryKeyOrder(true);

            // 取得条件
            key.setPriorityCollect();
            key.setCarryKeyCollect();
            key.setCarryFlagCollect();
            key.setCmdStatusCollect();
            key.setWorkTypeCollect();
            key.setRetrievalDetailCollect();
            key.setWorkNoCollect();
            key.setSourceStationNoCollect();
            key.setDestStationNoCollect();
            key.setAisleStationNoCollect();
            key.setRetrievalStationNoCollect();
            key.setCollect(Stock.AREA_NO);
            key.setScheduleNoCollect();
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
