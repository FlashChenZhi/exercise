// $Id: SoftZoneMasterInquirySCH.java 4992 2009-09-07 09:43:10Z okayama $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.master.schedule.SoftZoneMasterInquirySCHParams.*;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.TreeMap;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView;
import jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView.AsLocationBayView;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.ShelfFinder;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.util.location.WmsLocationFormat;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * ソフトゾーン問合せのスケジュール処理を行うためのクラスです。
 *
 * @version $Revision: 4992 $, $Date:: 2009-09-07 18:43:10 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class SoftZoneMasterInquirySCH
        extends AbstractSCH
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
    private ShelfHandler _shelfHandl = null;

    private ShelfSearchKey _shelfKey = null;


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
    public SoftZoneMasterInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
    public AsLocationLevelView[] queryShelf(ScheduleParams p)
            throws CommonException
    {
        int bankNo = Integer.parseInt(p.getString(BANK_NO));
        String areaNo = p.getString(AREA_NO);

        AbstractDBFinder finder = null;
        try
        {
            // インスタンス生成
            createInstance();

            finder = new ShelfFinder(getConnection());
            finder.open(true);

            // エリアに対応した倉庫ステーションNo.を取得
            AreaController areaControl = new AreaController(getConnection(), getClass());
            String whStNo = areaControl.getWhStationNo(areaNo);

            // フォーマット取得
            WmsLocationFormat locFormat = new WmsLocationFormat(areaControl.getLocationStyle(areaNo));

            // 存在チェック
            if (!check(p, whStNo))
            {
                return null;
            }

            // Shelfにデータのない使用不可棚を表示するため、
            // ベイ・レベルの最大値からベイ数・レベル数を推測し、棚配列を作成する
            int maxBay = getMaxBay(bankNo, whStNo);
            int maxLevel = getMaxLevel(bankNo, whStNo);

            // 棚配列
            AsLocationLevelView[] levelViews = new AsLocationLevelView[maxLevel];
            AsLocationBayView[] bayViews = new AsLocationBayView[maxBay];

            // データ検索
            int dataCnt = finder.search(createSearchKey(p, whStNo));
            if (dataCnt == 0)
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return null;
            }

            // データ取得
            Shelf[] dispLocate = (Shelf[])finder.getEntities(dataCnt);

            // 数値フォーマット(000:3桁)
            DecimalFormat df = new DecimalFormat("000");

            // 取得パラメータ配列ナンバー
            int locCnt = 0;
            // 表示レベルナンバー
            int dispLevel = maxLevel;

            // レベル分繰り返し
            for (int lc = 0; lc < maxLevel; lc++)
            {
                levelViews[lc] = new AsLocationLevelView();
                levelViews[lc].setLevel(df.format(dispLevel));
                bayViews = new AsLocationBayView[maxBay];

                // ベイ分繰り返し
                for (int j = 0; j < maxBay; j++)
                {
                    bayViews[j] = levelViews[lc].new AsLocationBayView();

                    if (locCnt < dispLocate.length)
                    {
                        //表示位置と棚No.の比較
                        if (dispLevel == dispLocate[locCnt].getLevelNo() && (j + 1) == dispLocate[locCnt].getBayNo())
                        {
                            bayViews[j].setBankNo(df.format(dispLocate[locCnt].getBankNo()));
                            bayViews[j].setLevelNo(df.format(dispLocate[locCnt].getLevelNo()));
                            bayViews[j].setBayNo(df.format(dispLocate[locCnt].getBayNo()));
                            bayViews[j].setLocation(dispLocate[locCnt].getStationNo());
                            bayViews[j].setBalloonLocation(locFormat.format(
                                    areaControl.toParamLocation(dispLocate[locCnt].getStationNo()), areaNo));
                            bayViews[j].setStatus(dispLocate[locCnt].getStatusFlag());
                            bayViews[j].setAccessNgFlg(dispLocate[locCnt].getAccessNgFlag());
                            bayViews[j].setProhibitFlg(dispLocate[locCnt].getProhibitionFlag());
                            bayViews[j].setEmpty((String)dispLocate[locCnt].getValue(Pallet.EMPTY_FLAG, ""));
                            bayViews[j].setWareHouseNo(dispLocate[locCnt].getWhStationNo());
                            bayViews[j].setPalletId((String)dispLocate[locCnt].getValue(Pallet.PALLET_ID, ""));
                            bayViews[j].setSoftzoneId(dispLocate[locCnt].getSoftZoneId());
                            bayViews[j].setSoftzoneName((String)dispLocate[locCnt].getValue(SoftZone.SOFT_ZONE_NAME, ""));

                            locCnt++;
                        }
                        // 使用不可棚（Shelfにデータなし）の場合
                        else
                        {
                            bayViews[j].setBankNo(null);
                            bayViews[j].setBayNo(df.format(j + 1));
                            bayViews[j].setLevelNo(null);
                            bayViews[j].setLocation(null);
                            bayViews[j].setBalloonLocation("-1");
                            bayViews[j].setStatus("-1");
                            bayViews[j].setAccessNgFlg("-1");
                            bayViews[j].setProhibitFlg("-1");
                            bayViews[j].setPStatus("-1");
                            bayViews[j].setEmpty("-1");
                            bayViews[j].setWareHouseNo(null);
                            bayViews[j].setPalletId(null);
                            bayViews[j].setSoftzoneId(null);
                            bayViews[j].setSoftzoneName(null);
                        }
                    }
                    // 表示した棚数がShelfのデータ数を超えた場合は使用不可棚
                    else
                    {
                        bayViews[j].setBankNo(null);
                        bayViews[j].setBayNo(df.format(j + 1));
                        bayViews[j].setLevelNo(null);
                        bayViews[j].setLocation(null);
                        bayViews[j].setBalloonLocation("-1");
                        bayViews[j].setStatus("-1");
                        bayViews[j].setAccessNgFlg("-1");
                        bayViews[j].setProhibitFlg("-1");
                        bayViews[j].setPStatus("-1");
                        bayViews[j].setEmpty("-1");
                        bayViews[j].setWareHouseNo(null);
                        bayViews[j].setPalletId(null);
                        bayViews[j].setSoftzoneId(null);
                        bayViews[j].setSoftzoneName(null);
                    }
                }
                levelViews[lc].setBayView(bayViews);
                dispLevel--;
            }
            return levelViews;
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }


    /**
     * ソフトゾーンを取得します。<BR>
     * パラメータで渡されたエリアNo.、バンクNo.をキーに
     * 該当棚に存在するソフトゾーンID、名称を取得します。<BR>
     * 棚にソフトゾーンが登録されているのに
     * ソフトゾーンマスタに存在しない場合は
     * ソフトゾーン未対応の棚とみなして、名称「Unuse」を返します。<BR>
     * 
     * @param p
     * @return ソフトゾーン
     * @throws CommonException
     */
    public SoftZone[] getSoftZon(ScheduleParams p)
            throws CommonException
    {
        int bankNo = Integer.parseInt(p.getString(BANK_NO));
        String areaNo = p.getString(AREA_NO);

        createInstance();

        // -------------------
        // 棚に割り付けられていてソフトゾーンマスタに登録されているソフトゾーンを取得
        // collect
        _shelfKey.setSoftZoneIdCollect();
        _shelfKey.setCollect(SoftZone.SOFT_ZONE_NAME, "MAX", SoftZone.SOFT_ZONE_NAME);
        // where
        _shelfKey.setBankNo(bankNo);
        _shelfKey.setKey(Area.AREA_NO, areaNo);
        _shelfKey.setJoin(Area.WHSTATION_NO, Shelf.WH_STATION_NO);
        // ソフトゾーンマスタにない場合は検索対象外とする。
        _shelfKey.setJoin(Shelf.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");
        _shelfKey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        _shelfKey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        _shelfKey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        _shelfKey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");
        // order by
        _shelfKey.setSoftZoneIdOrder(true);
        // group by
        _shelfKey.setSoftZoneIdGroup();
        // select
        Shelf[] shelfs = (Shelf[])_shelfHandl.find(_shelfKey);

        TreeMap<String, SoftZone> temp = new TreeMap<String, SoftZone>();
        for (int i = 0; i < shelfs.length; i++)
        {
            SoftZone zone = new SoftZone();
            zone = new SoftZone();
            zone.setSoftZoneId(shelfs[i].getSoftZoneId());
            zone.setSoftZoneName((String)shelfs[i].getValue(SoftZone.SOFT_ZONE_NAME, shelfs[i].getSoftZoneId()));

            temp.put(zone.getSoftZoneId(), zone);
        }

        return temp.values().toArray(new SoftZone[temp.size()]);
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
     * エリアNo.、バンクNo.をキーに
     * 棚情報と、ソフトゾーン名称を取得します。
     * ソフトゾーンマスタにデータがない場合でも、
     * エンティティを取得します。
     *
     * @param p 検索条件を含むScheduleParams
     * @return 検索キー
     */
    protected SearchKey createSearchKey(ScheduleParams p, String whStation)
    {
        // set where
        _shelfKey.clear();
        _shelfKey.setWhStationNo(whStation);
        _shelfKey.setBankNo(p.getInt(BANK_NO));
        _shelfKey.setJoin(Shelf.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");
        _shelfKey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        _shelfKey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        _shelfKey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        _shelfKey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // set order by
        _shelfKey.setLevelNoOrder(false);
        _shelfKey.setBayNoOrder(true);

        // set collect
        // 全項目取得する場合の指定方法
        _shelfKey.setCollect(new FieldName(Shelf.STORE_NAME, FieldName.ALL_FIELDS));
        _shelfKey.setCollect(SoftZone.SOFT_ZONE_NAME);

        return _shelfKey;
    }

    /**
     * 表示データがあるかのチェックを行うメソッドです。<BR>
     * @param searchParam 表示データ取得条件を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。<BR>
     * @param whStation エリアに対応した倉庫ステーションNo.
     * @return true:表示データあり,false:表示データなし
     * @throws CommonException 全ての例外を報告します。 
     */
    protected boolean check(Params searchParam, String whStation)
            throws CommonException
    {
        SoftZoneMasterInquirySCHParams param = (SoftZoneMasterInquirySCHParams)searchParam;

        ShelfSearchKey slfkey = new ShelfSearchKey();
        ASShelfHandler asSlfh = new ASShelfHandler(getConnection());

        // 検索条件セット
        slfkey.clear();
        // 倉庫ステーション、バンク指定
        slfkey.setWhStationNo(whStation);
        slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
        slfkey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // ロケーション情報取得
        int count = asSlfh.count(slfkey);

        // 該当データがない場合
        if (count == 0)
        {
            // 上限を超えるデータを取得
            // 検索条件セット
            slfkey.clear();
            // 倉庫ステーション、バンク指定
            slfkey.setWhStationNo(whStation);
            slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
            slfkey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, ">", "(", "", false);
            slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, ">", "", ")", true);

            // 上限を超えるデータがない場合
            if (asSlfh.count(slfkey) == 0)
            {
                // メッセージをセット
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
            }
            // 上限を超えるデータがある場合
            else
            {
                // 6023230 = 該当データは存在しませんが上限を超える棚が存在します。
                setMessage("6023230");
            }
            return false;
        }
        // 該当データが複数件ある場合
        else
        {
            // 上限を超えるデータを取得
            // 検索条件セット
            slfkey.clear();
            // 倉庫ステーション、バンク指定
            slfkey.setWhStationNo(whStation);
            slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
            slfkey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, ">", "(", "", false);
            slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, ">", "", ")", true);

            if (asSlfh.count(slfkey) == 0)
            {
                // 6001013 = 表示しました。
                setMessage("6001013");
                return true;
            }
            else
            {
                // 6023229 = 表示しましたが上限を超える棚が存在します。
                setMessage("6023229");
                return true;
            }
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * DBアクセスのハンドラ類を生成します。
     *
     */
    private void createInstance()
    {
        _shelfHandl = new ShelfHandler(getConnection());
        _shelfKey = new ShelfSearchKey();
    }

    /**
     * 該当倉庫・バンクの最大レベルを取得します
     * 
     * @param bankNo
     * @param whStNo
     * @return 最大レベル
     * @throws ReadWriteException
     * @throws NoPrimaryException
     */
    private int getMaxLevel(int bankNo, String whStNo)
            throws ReadWriteException,
                NoPrimaryException
    {
        _shelfKey.clear();
        _shelfKey.setWhStationNo(whStNo);
        _shelfKey.setBankNo(bankNo);
        _shelfKey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        _shelfKey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");
        _shelfKey.setLevelNoCollect("MAX");
        Shelf levelNumMax = (Shelf)_shelfHandl.findPrimary(_shelfKey);
        int maxLevel = levelNumMax.getLevelNo();
        return maxLevel;
    }

    /**
     * 該当倉庫・バンクの最大レベルを取得します
     * 
     * @param bankNo
     * @param whStNo
     * @return 最大ベイ
     * @throws ReadWriteException
     * @throws NoPrimaryException
     */
    private int getMaxBay(int bankNo, String whStNo)
            throws ReadWriteException,
                NoPrimaryException
    {
        _shelfKey.clear();
        _shelfKey.setWhStationNo(whStNo);
        _shelfKey.setBankNo(bankNo);
        _shelfKey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        _shelfKey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        _shelfKey.setBayNoCollect("MAX");
        Shelf bayNumMax = (Shelf)_shelfHandl.findPrimary(_shelfKey);
        int maxBay = bayNumMax.getBayNo();
        return maxBay;
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
