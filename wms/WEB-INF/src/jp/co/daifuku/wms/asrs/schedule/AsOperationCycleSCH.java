// $Id: AsOperationCycleSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.AsOperationCycleSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultFinder;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 稼動実績照会のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class AsOperationCycleSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /**
     * 稼動実績情報ハンドラ
     */
    private InOutResultHandler _iorh = null;

    /**
     * 稼動実績情報検索キー
     */
    private InOutResultSearchKey _iorKey = null;

    /**
     * アイル情報検索キー
     */
    private AisleSearchKey _aisleKey = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public AsOperationCycleSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new InOutResultFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder, p);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    	throws ScheduleException
    {
    	InOutResultSearchKey inOutKey = new InOutResultSearchKey();

        // 副問合せ用 エリア情報検索条件セット
        AreaSearchKey areaKey = new AreaSearchKey();
        if (WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
        {
            areaKey.setAreaType(Area.AREA_TYPE_ASRS);
        }
        else
        {
            areaKey.setAreaNo(p.getString(AREA_NO));
        }
        areaKey.setWhstationNoCollect();

        // 検索条件セット
        inOutKey.setKey(InOutResult.WH_STATION_NO, areaKey);

        // 検索日時
        Date[] tmp = WmsFormatter.getFromTo(p.getDate(FROM_SEARCH_DATE), p.getDate(FROM_SEARCH_TIME), p.getDate(TO_SEARCH_DATE), p.getDate(TO_SEARCH_TIME));
        Date from = tmp[0];
        Date to = tmp[1];
        
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            inOutKey.setRegistDate(from, ">=");
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            inOutKey.setRegistDate(to, "<");
        }

        if (WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
        {
            inOutKey.setKey(Area.AREA_TYPE, Area.AREA_TYPE_ASRS);
        }
        else
        {
            inOutKey.setKey(Area.AREA_NO, p.getString(AREA_NO));
        }

        inOutKey.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);
        inOutKey.setJoin(InOutResult.WH_STATION_NO, Aisle.WH_STATION_NO);
        inOutKey.setJoin(InOutResult.AISLE_STATION_NO, Aisle.STATION_NO);

        // 集約条件
        inOutKey.setGroup(Area.AREA_NO);
        inOutKey.setGroup(Aisle.AISLE_NO);
        inOutKey.setGroup(Aisle.STATION_NO);

        // ソート順
        inOutKey.setOrder(Area.AREA_NO, true);
        inOutKey.setOrder(Aisle.AISLE_NO, true);

        // 取得条件
        inOutKey.setCollect(Area.AREA_NO);
        inOutKey.setCollect(Aisle.STATION_NO);
        inOutKey.setCollect(Aisle.AISLE_NO);

        return inOutKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder, ScheduleParams p)
            throws ReadWriteException, ScheduleException
    {
        // 最大表示件数分検索結果を取得する
    	InOutResult[] entities = (InOutResult[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        // 対象データがある場合アイルごとに入出庫数を求める
        AreaController area = new AreaController(getConnection(), this.getClass());

        _iorh = new InOutResultHandler(getConnection());
        _iorKey = new InOutResultSearchKey();
        _aisleKey = new AisleSearchKey();

        for (InOutResult ent : entities)
        {
            Params param = new Params();

            String areaNo = (String)ent.getValue(Area.AREA_NO);
            String whStationNo = area.getWhStationNo(areaNo);
            String rmNo = (String)ent.getValue(Aisle.AISLE_NO);
            int storageCnt = getWorkCnt(whStationNo, rmNo, p, InOutResult.RESULT_KIND_STORAGE);
            int retrievalCnt = getWorkCnt(whStationNo, rmNo, p, InOutResult.RESULT_KIND_RETRIEVAL);

            // エリアNo.
            param.set(AREA_NO, areaNo);
            // RMNo.
            param.set(AISLE_NO, rmNo);
            // 入庫稼動数
            param.set(STORAGE_COUNT, storageCnt);
            // 出庫稼動数
            param.set(RETRIEVAL_COUNT, retrievalCnt);
            // 総稼動数
            param.set(TOTAL_COUNT, storageCnt + retrievalCnt);
            
            result.add(param);
        }

        return result;	
    }

    /**
     * 引数で指定された倉庫・RMの作業数を返します。<BR>
     * 引数で指定された実績作成区分の作業数を返します。<BR>
     * 
     * @param whStationNo 倉庫ステーションNo.
     * @param rmNo RMNo.
     * @param param 画面からの入力条件 <BR> 検索開始日時と検索終了日時を使用します。<BR>
     * @param resultKind InOutResultの実績作成区分
     * @return 作業件数
     * @throws ReadWriteException DB接続でエラーがあった場合に通知されます。
     */
    protected int getWorkCnt(String whStationNo, String rmNo, ScheduleParams p, String resultKind)
            throws ReadWriteException, ScheduleException 
    {
        _aisleKey.clear();
        _iorKey.clear();

        // カラム定義
        final FieldName wORKCNT = new FieldName(InOutResult.STORE_NAME, "WORK_CNT");

        // 副問合せ用 アイル情報検索条件セット
        _aisleKey.setKey(Aisle.WH_STATION_NO, whStationNo);
        _aisleKey.setAisleNo(rmNo);
        _aisleKey.setStationNoCollect();

        // 検索条件セット
        _iorKey.setKey(InOutResult.AISLE_STATION_NO, _aisleKey);
        _iorKey.setResultKind(resultKind);
        
        // 検索日時
        Date[] tmp = WmsFormatter.getFromTo(p.getDate(FROM_SEARCH_DATE), p.getDate(FROM_SEARCH_TIME), p.getDate(TO_SEARCH_DATE), p.getDate(TO_SEARCH_TIME));
        Date from = tmp[0];
        Date to = tmp[1];
        
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            _iorKey.setRegistDate(from, ">=");
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
        	_iorKey.setRegistDate(to, "<");
        }

        // 取得項目
        // 出庫稼動数
        _iorKey.setCollect(InOutResult.RESULT_KIND, "count({0})", wORKCNT);

        // ソートの昇順
        _iorKey.setOrder(Aisle.AISLE_NO, true);

        // 集約条件
        _iorKey.setGroup(Aisle.AISLE_NO);
        _iorKey.setGroup(Aisle.STATION_NO);

        InOutResult[] ior = (InOutResult[])_iorh.find(_iorKey);
        if (ior == null || ior.length == 0)
        {
            return 0;
        }
        else
        {
            return ((BigDecimal)ior[0].getValue(wORKCNT)).intValue();
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
