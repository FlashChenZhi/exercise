// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.dasch.AsWorkMntExporterDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.TerminalArea;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * AS/RS作業メンテナンスに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class AsWorkMntExporterDASCH
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
    public AsWorkMntExporterDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // AS/RSステーション情報コントローラの生成
        StationController station = new StationController(getConnection(), getClass());
        // エリア情報コントローラの生成
        AreaController area = new AreaController(getConnection(), this.getClass());
        // 返却パラメータを生成
        Params p = new Params();

        // AS/RS搬送情報を一件取得
        CarryInfo[] ents = (CarryInfo[])_finder.getEntities(1);
        for (CarryInfo ent : ents)
        {
            // 帳票またはXLSの必須情報
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            // 入庫時は、ステーションから棚への搬送
            // 搬送先にはアイルをセットする
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ent.getCarryFlag()))
            {
                try
                {
                    p.set(TO_LOCATION_NO, area.getAreaNoOfWarehouse(ent.getDestStationNo()));
                }
                catch (CommonException e)
                {
                    p.set(TO_LOCATION_NO, ent.getDestStationNo());
                }

                // 搬送元ステーションNo.
                p.set(FROM_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getAisleStationNo());
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
                p.set(FROM_STATION_NO, ent.getAisleStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getDestStationNo());
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
                p.set(FROM_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getDestStationNo());
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
                p.set(FROM_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getDestStationNo());
                // ステーションNo.
                p.set(STATION_NO, "");

            }
            else
            {
                // 棚
                p.set(LOCATION_NO, "");
                // 搬送元ステーションNo.
                p.set(FROM_STATION_NO, "");
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, "");
                // ステーションNo.
                p.set(STATION_NO, "");
            }

            try
            {
                // ステーション名称
                if (!StringUtil.isBlank(p.getString(STATION_NO)))
                {
                    String stationName = station.getAsStationName(p.getString(STATION_NO));
                    p.set(STATION_NAME, stationName);
                }
                else
                {
                    p.set(STATION_NAME, "");
                }
            }
            catch (CommonException e)
            {
                e.printStackTrace();
            }

            // 優先区分名称
            p.set(PRIORITY_FLAG, DisplayResource.getPriority(ent.getPriority()));
            // 搬送区分/名称
            p.set(TRANSPORT_TYPE, DisplayResource.getCarryFlag(ent.getCarryFlag()));
            // 出庫指示詳細
            String detail = DisplayResource.getFaRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_UNKNOWN);
            String retDetail = ent.getRetrievalDetail();
            if (SystemDefine.RETRIEVAL_DETAIL_PICKING.equals(retDetail))
            {
                detail = DisplayResource.getFaRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_PICKING);
            }
            if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(retDetail))
            {
                detail = DisplayResource.getFaRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_UNIT);
            }
            p.set(RETRIEVAL_COMMAND_DETAIL, detail);
            // 作業No.
            p.set(WORK_NO, ent.getWorkNo());
            // エリアNo.
            String areaNo = String.valueOf(ent.getValue(Stock.AREA_NO, ""));
            p.set(WORK_AREA, areaNo);
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(WorkInfo.ITEM_CODE, ""));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // 作業数
            p.set(WORK_QTY, ent.getBigDecimal(WorkInfo.PLAN_QTY, new BigDecimal(0)));
            // ロットNo.
            p.set(LOT_NO, String.valueOf(ent.getValue(WorkInfo.PLAN_LOT_NO, "")));

            try
            {
                // ロケーションNo.
                String paramLoc = area.toParamLocation(p.getString(LOCATION_NO));
                p.set(LOCATION_NO, toDispLocation(getConnection(), areaNo, paramLoc));

                // ロケーションNo.
                paramLoc = area.toParamLocation(p.getString(TO_LOCATION_NO));
                p.set(TO_LOCATION_NO, toDispLocation(getConnection(), areaNo, paramLoc));
            }
            catch (CommonException e)
            {
                e.printStackTrace();
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
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // AS/RSステーション情報コントローラの生成
        StationController station = new StationController(getConnection(), getClass());
        // エリア情報コントローラの生成
        AreaController area = new AreaController(getConnection(), this.getClass());
        // 返却パラメータ配列の生成
        List<Params> params = new ArrayList<Params>();

        // AS/RS搬送情報を指定件数取得
        CarryInfo[] ents = (CarryInfo[])_finder.getEntities(start, start + cnt);
        for (CarryInfo ent : ents)
        {
            // 返却パラメータの生成
            Params p = new Params();

            // 入庫時は、ステーションから棚への搬送
            // 搬送先にはアイルをセットする
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ent.getCarryFlag()))
            {
                try
                {
                    p.set(TO_LOCATION_NO, area.getAreaNoOfWarehouse(ent.getDestStationNo()));
                }
                catch (CommonException e)
                {
                    p.set(TO_LOCATION_NO, ent.getDestStationNo());
                }

                // 搬送元ステーションNo.
                p.set(FROM_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getAisleStationNo());
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
                p.set(FROM_STATION_NO, ent.getAisleStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getDestStationNo());
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
                p.set(FROM_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getDestStationNo());
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
                p.set(FROM_STATION_NO, ent.getSourceStationNo());
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, ent.getDestStationNo());
                // ステーションNo.
                p.set(STATION_NO, "");

            }
            else
            {
                // 棚
                p.set(LOCATION_NO, "");
                // 搬送元ステーションNo.
                p.set(FROM_STATION_NO, "");
                // 搬送先ステーションNo.
                p.set(TO_STATION_NO, "");
                // ステーションNo.
                p.set(STATION_NO, "");
            }

            try
            {
                // ステーション名称
                if (!StringUtil.isBlank(p.getString(STATION_NO)))
                {
                    String stationName = station.getAsStationName(p.getString(STATION_NO));
                    p.set(STATION_NAME, stationName);
                }
                else
                {
                    p.set(STATION_NAME, "");
                }
            }
            catch (CommonException e)
            {
                e.printStackTrace();
            }

            // 優先区分名称
            p.set(PRIORITY_FLAG, DisplayResource.getPriority(ent.getPriority()));
            // 搬送区分/名称
            p.set(TRANSPORT_TYPE, DisplayResource.getCarryFlag(ent.getCarryFlag()));
            // 出庫指示詳細
            String detail = DisplayResource.getFaRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_UNKNOWN);
            String retDetail = ent.getRetrievalDetail();
            if (SystemDefine.RETRIEVAL_DETAIL_PICKING.equals(retDetail))
            {
                detail = DisplayResource.getFaRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_PICKING);
            }
            if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(retDetail))
            {
                detail = DisplayResource.getFaRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_UNIT);
            }
            p.set(RETRIEVAL_COMMAND_DETAIL, detail);
            // 作業No.
            p.set(WORK_NO, ent.getWorkNo());
            // エリアNo.
            String areaNo = String.valueOf(ent.getValue(Stock.AREA_NO, ""));
            p.set(WORK_AREA, areaNo);
            // ロットNo.
            p.set(LOT_NO, String.valueOf(ent.getValue(WorkInfo.PLAN_LOT_NO, "")));

            try
            {
                // ロケーションNo.
                String paramLoc = area.toParamLocation(p.getString(LOCATION_NO));
                p.set(LOCATION_NO, toDispLocation(getConnection(), areaNo, paramLoc));

                // ロケーションNo.
                paramLoc = area.toParamLocation(p.getString(TO_LOCATION_NO));
                p.set(TO_LOCATION_NO, toDispLocation(getConnection(), areaNo, paramLoc));
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
     * 入力されたパラメータをもとに該当するステーションを返します。<BR>
     * 
     * @param param 検索条件を保持したAsrsInParameterオブジェクト
     * @return Station配列
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    protected Station[] searchStation(Params p)
            throws ReadWriteException,
                NotFoundException
    {
        // AS/RSステーション情報ハンドラの生成
        StationHandler handler = new StationHandler(getConnection());
        // AS/RSステーション情報検索キーの生成
        StationSearchKey stationKey = new StationSearchKey();

        // ステーションが空白の場合は全ステーションを検索する
        if (!StringUtil.isBlank(p.getString(STATION_NO)))
        {
            // 該当ステーションNo.一覧
            ArrayList<String> stationArray = new ArrayList<String>();

            // 全ステーションが指定されている場合は作業場に紐づくステーションを取得
            if (WmsParam.ALL_STATION.equals(p.getString(STATION_NO)))
            {
                // 取得したステーション情報が作業場の場合、関連するステーション情報を取得します。
                Station[] workStation = (Station[])getWorkStation(p.getString(WORK_PLACE), p.getString(TERMINAL_NO));
                for (Station wp : workStation)
                {
                    stationArray.add(wp.getStationNo());
                }
            }
            // ステーションが指定されている場合はパラメータのステーション指定で検索
            else
            {
                stationArray.add(p.getString(STATION_NO));
            }

            String[] station = new String[stationArray.size()];
            stationArray.toArray(station);

            // Stationの検索条件をセットする。
            stationKey = setSearchKey(stationKey, station, p.getString(TERMINAL_NO));

            // 取得条件
            stationKey.setStationNoCollect();
        }

        // 並び順
        // 親ステーションNo.
        stationKey.setParentStationNoOrder(true);
        // ステーションNo.
        stationKey.setStationNoOrder(true);

        // 検索を行う
        Station[] stations = (Station[])handler.find(stationKey);
        if (ArrayUtil.isEmpty(stations))
        {
            throw new NotFoundException();
        }
        // 取得したAS/RSステーション情報を返却
        return stations;
    }

    /**
     * 渡された作業場（ステーション）に紐づくステーションの配列を取得します。<BR>
     * 引数で指定した条件のステーション配列を返します（代表ステーション含む）。
     * 
     * @param station <CODE>ステーションNo</CODE>
     * @param terminalNo 端末No.
     * @return 引数で指定した条件のステーション配列
     * @throws ReadWriteException データベースに対する処理で<BR>発生した場合に通知します。
     */
    protected Station[] getWorkStation(String station, String terminalNo)
            throws ReadWriteException
    {
        // AS/RSステーション情報ハンドラの生成
        StationHandler stHandler = new StationHandler(getConnection());
        // AS/RSステーション情報検索キーの生成
        StationSearchKey stKey = new StationSearchKey();

        // 作業場が指定されなかった場合は全体作業場を取得
        stKey.clear();
        if (StringUtil.isBlank(station) || WmsParam.ALL_STATION.equals(station))
        {
            stKey.setWorkplaceType(SystemDefine.WORKPLACE_TYPE_FLOOR, "!=");
            stKey.setParentStationNo((String)null);
        }
        else
        {
            stKey.setStationNo(station);
        }
        // 検索を実行
        Station[] st = (Station[])stHandler.find(stKey);

        // ステーション情報格納配列
        ArrayList<Station> array = new ArrayList<Station>();

        // ステーション情報の件数分繰り返す
        for (int i = 0; i < st.length; i++)
        {
            // 通常ステーションの場合
            if (!SystemDefine.WORKPLACE_TYPE_FLOOR.equals(st[i].getWorkplaceType()))
            {
                // 親ステーションNo.
                stKey.clear();
                stKey.setParentStationNo(st[i].getStationNo());

                // 復帰したステーション情報をステーション一覧に追加します
                String[] stationArray = null;
                Station[] loopStation = (Station[])stHandler.find(stKey);
                for (int j = 0; j < loopStation.length; j++)
                {
                    Station[] poolStation = (Station[])getWorkStation(loopStation[j].getStationNo(), terminalNo);
                    stationArray = new String[poolStation.length];
                    for (int k = 0; k < poolStation.length; k++)
                    {
                        array.add(poolStation[k]);
                        stationArray[k] = poolStation[k].getStationNo();
                    }
                }

                // 代表ステーション
                if (SystemDefine.WORKPLACE_TYPE_MAIN_STATION.equals(st[i].getWorkplaceType()))
                {
                    // 代表ステーションに紐づくステーションが端末で表示可能かチェック
                    stKey.clear();
                    stKey = setSearchKey(stKey, stationArray, terminalNo);

                    // 表示可能なステーションが1件でも存在すればステーション一覧に追加
                    if (stHandler.count(stKey) > 0)
                    {
                        array.add(st[i]);

                    }
                }
            }
            else
            {
                // ステーション情報をステーション一覧に追加します
                array.add(st[i]);
            }
        }
        // 返却するステーション配列を生成
        Station[] workStation = new Station[array.size()];
        array.toArray(workStation);

        return workStation;
    }

    /**
     * 渡された作業場（ステーション）に紐づくステーションの配列を取得します。
     * 引数で指定した条件のステーション配列を返します（代表ステーション含む）。
     * 
     * @param stationKey ステーション検索キー
     * @param station    ステーションNo配列
     * @param terminalNo 端末No.
     * @return ステーション検索キー
     */
    protected StationSearchKey setSearchKey(StationSearchKey stationKey, String[] station, String terminalNo)
    {
        // 検索条件
        // ステーションNo.
        stationKey.setStationNo(station, true);
        // ステーション種別(入庫)
        stationKey.setStationType(SystemDefine.STATION_TYPE_IN, "=", "(", "", false);
        // ステーション種別(出庫)
        stationKey.setStationType(SystemDefine.STATION_TYPE_OUT, "=", "", "", false);
        // ステーション種別(入出庫兼用)
        stationKey.setStationType(SystemDefine.STATION_TYPE_INOUT, "=", "", ")", true);
        // 送信可能区分
        stationKey.setSendable(SystemDefine.SENDABLE_TRUE);
        // 作業場種別
        stationKey.setWorkplaceType(SystemDefine.WORKPLACE_TYPE_FLOOR, "=", "((", "", true);
        // 端末No.
        stationKey.setKey(TerminalArea.TERMINAL_NO, terminalNo, "=", "", ")", false);
        // 作業場種別
        stationKey.setWorkplaceType(SystemDefine.WORKPLACE_TYPE_MAIN_STATION, "=", "", ")", true);

        // 結合条件
        // AS/RSステーション情報.ステーションNo.とAS/RS端末エリア情報.ステーションNo.
        stationKey.setJoin(Station.STATION_NO, "", TerminalArea.STATION_NO, "(+)");

        // 生成した検索キーの返却
        return stationKey;
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
        // エリア情報コントローラの生成
        AreaController area = new AreaController(conn, this.getClass());

        //棚フォーマットを取得する
        String format = area.getLocationStyle(areaNo);

        //エリアNoが見つからなかった場合
        if (format == null)
        {
            return paramLocation;
        }

        // 引数が無かった場合
        if (StringUtil.isBlank(paramLocation))
        {
            return "";
        }

        try
        {
            // フォーマット
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
     * 検索条件のセットを行います。
     * 
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、<BR>出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // AS/RS搬送情報検索キーの生成
        CarryInfoSearchKey key = new CarryInfoSearchKey();

        // 検索条件
        // ステーション情報取得
        Station[] stations = (Station[])searchStation(param);
        for (int i = 0; i < stations.length; i++)
        {
            // ステーションNo.の取得
            String stationNo = stations[i].getStationNo();
            if (1 == stations.length)
            {
                // 搬送元ステーションNo.
                key.setSourceStationNo(stationNo, "=", "((", "", false);
                // 搬送先ステーションNo.
                key.setDestStationNo(stationNo, "=", "", "))", false);
            }
            else if (i == 0)
            {
                // 搬送元ステーションNo.
                key.setSourceStationNo(stationNo, "=", "((", "", false);
                // 搬送先ステーションNo.
                key.setDestStationNo(stationNo, "=", "", ")", false);
            }
            else if (i == stations.length - 1)
            {
                // 搬送元ステーションNo.
                key.setSourceStationNo(stationNo, "=", "(", "", false);

                // 作業場、ステーションともに全て指定時は棚間移動データも表示する
                if (WmsParam.ALL_STATION.equals(param.getString(WORK_PLACE))
                        && WmsParam.ALL_STATION.equals(param.getString(STATION_NO)))
                {
                    // 搬送先ステーションNo.
                    key.setDestStationNo(stationNo, "=", "", ")", false);
                    key.setCarryFlag(SystemDefine.CARRY_FLAG_RACK_TO_RACK, "=", "", ")", true);
                }
                else
                {
                    // 搬送先ステーションNo.
                    key.setDestStationNo(stationNo, "=", "", "))", true);
                }
            }
            else
            {
                // 搬送元ステーションNo.
                key.setSourceStationNo(stationNo, "=", "(", "", false);
                // 搬送先ステーションNo.
                key.setDestStationNo(stationNo, "=", "", ")", false);
            }
        }

        // 集約条件
        // 優先区分
        key.setPriorityGroup();
        // 搬送キー
        key.setCarryKeyGroup();
        // 搬送区分
        key.setCarryFlagGroup();
        // 搬送状態
        key.setCmdStatusGroup();
        // 作業種別
        key.setWorkTypeGroup();
        // 出庫指示詳細
        key.setRetrievalDetailGroup();
        // 作業No.
        key.setWorkNoGroup();
        // 搬送元ステーションNo.
        key.setSourceStationNoGroup();
        // 搬送先ステーションNo.
        key.setDestStationNoGroup();
        // アイルステーションNo.
        key.setAisleStationNoGroup();
        // 出庫ロケーションNo.
        key.setRetrievalStationNoGroup();
        // エリアNo.
        key.setGroup(Stock.AREA_NO);
        // 商品コード
        key.setGroup(WorkInfo.ITEM_CODE);
        // 商品名称
        key.setGroup(Item.ITEM_NAME);
        // 予定数
        key.setGroup(WorkInfo.PLAN_QTY);
        // ロットNo.
        key.setGroup(WorkInfo.PLAN_LOT_NO);

        // 結合条件
        // AS/RS搬送情報.パレットIDと在庫情報.パレットID
        key.setJoin(CarryInfo.PALLET_ID, Stock.PALLET_ID);
        // AS/RS搬送情報.搬送キーと作業情報.システム接続キー
        key.setJoin(CarryInfo.CARRY_KEY, "", WorkInfo.SYSTEM_CONN_KEY, "(+)");
        // 作業情報.商品コードと商品情報.商品コード
        key.setJoin(WorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");

        if (isSet)
        {
            // 取得条件
            // 優先区分
            key.setPriorityCollect();
            // 搬送キー
            key.setCarryKeyCollect();
            // 搬送区分
            key.setCarryFlagCollect();
            // 搬送状態
            key.setCmdStatusCollect();
            // 作業種別
            key.setWorkTypeCollect();
            // 出庫指示詳細
            key.setRetrievalDetailCollect();
            // 作業No.
            key.setWorkNoCollect();
            // 搬送元ステーションNo.
            key.setSourceStationNoCollect();
            // 搬送先ステーションNo.
            key.setDestStationNoCollect();
            // アイルステーションNo.
            key.setAisleStationNoCollect();
            // 出庫ロケーションNo.
            key.setRetrievalStationNoCollect();
            // エリアNo.
            key.setCollect(Stock.AREA_NO);
            // 商品コード
            key.setCollect(WorkInfo.ITEM_CODE);
            // 商品名称
            key.setCollect(Item.ITEM_NAME);
            // 予定数(集約)
            key.setCollect(WorkInfo.PLAN_QTY);
            // ロットNo.
            key.setCollect(WorkInfo.PLAN_LOT_NO);

            // 並び順
            // 優先区分
            key.setPriorityOrder(true);
            // 搬送キー
            key.setCarryKeyOrder(true);
        }
        // 生成した検索キーを返却
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
