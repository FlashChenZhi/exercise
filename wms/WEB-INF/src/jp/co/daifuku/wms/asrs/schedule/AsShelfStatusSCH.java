// $Id: AsShelfStatusSCH.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsShelfStatusSCHParams.*;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView.AsLocationBayView;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.util.location.WmsLocationFormat;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 棚状態照会のスケジュール処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class AsShelfStatusSCH
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
    public AsShelfStatusSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // DFKLOOK query()は無処理に変更
        return null;
    }

    // DFKLOOK ここから追加
    /**
     * 表示データの検索を行います。
     * @param searchParam 設定内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンス。
     * @return ロケーションマスタ情報の配列
     * @throws CommonException 予期しない例外が発生した場合に通知されます。
     */
    public AsLocationLevelView[] getLevelViewData(Params searchParam)
            throws CommonException
    {
        AsShelfStatusSCHParams param = (AsShelfStatusSCHParams)searchParam;

        ShelfHandler slfh = new ShelfHandler(getConnection());
        ShelfSearchKey slfkey = new ShelfSearchKey();

        AreaController areaCtl = new AreaController(getConnection(), getClass());

        // エリアに対応した倉庫ステーションNo.を取得
        String whStation = areaCtl.getWhStationNo(param.getString(AREA_NO));

        // 存在チェック
        if (!check(param, whStation))
        {
            return null;
        }

        // AS/RS棚情報を取得する
        Shelf[] dispLocate = getShelfData(whStation, Integer.parseInt(param.getString(BANK_NO)));
        if (ArrayUtil.isEmpty(dispLocate))
        {
            // メッセージをセット
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return null;
        }

        // ベイの最大数を求める
        slfkey.clear();
        // 指定倉庫のみ
        slfkey.setWhStationNo(whStation);
        // 指定バンクのみ
        slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
        slfkey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        slfkey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        // 最大ベイナンバー
        slfkey.setBayNoCollect("MAX");
        Shelf bayNumMax = (Shelf)slfh.findPrimary(slfkey);
        int maxBay = bayNumMax.getBayNo();

        // レベルの最大数を求める
        slfkey.clear();
        // 指定倉庫のみ
        slfkey.setWhStationNo(whStation);
        // 指定バンクのみ
        slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
        slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");
        // 最大レベルナンバー
        slfkey.setLevelNoCollect("MAX");
        Shelf levelNumMax = (Shelf)slfh.findPrimary(slfkey);
        int maxLevel = levelNumMax.getLevelNo();

        AsLocationLevelView[] levelViews = new AsLocationLevelView[maxLevel];
        DecimalFormat df = new DecimalFormat("000");

        WmsLocationFormat wmsLoc = new WmsLocationFormat(areaCtl.getLocationStyle(param.getString(AREA_NO)));

        //取得パラメータ配列ナンバー
        int locCnt = 0;
        //表示レベルナンバー
        int dispLevel = maxLevel;

        for (int lc = 0; lc < maxLevel; lc++)
        {
            // 配列の初期化
            levelViews[lc] = new AsLocationLevelView();
            levelViews[lc].setLevel(df.format(dispLevel));
            AsLocationBayView[] bayViews = new AsLocationBayView[maxBay];
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
                        String location = dispLocate[locCnt].getStationNo();
                        bayViews[j].setLocation(location);
                        String dispLocation = areaCtl.toParamLocation(dispLocate[locCnt].getStationNo());
                        bayViews[j].setBalloonLocation(wmsLoc.format(dispLocation, param.getString(AREA_NO)));
                        bayViews[j].setStatus(dispLocate[locCnt].getStatusFlag());
                        bayViews[j].setAccessNgFlg(dispLocate[locCnt].getAccessNgFlag());
                        bayViews[j].setProhibitFlg(dispLocate[locCnt].getProhibitionFlag());
                        bayViews[j].setPStatus((String)dispLocate[locCnt].getValue(new FieldName(Pallet.STORE_NAME,
                                "P_STATUS_FLAG"), ""));
                        bayViews[j].setEmpty((String)dispLocate[locCnt].getValue(Pallet.EMPTY_FLAG, ""));
                        bayViews[j].setWareHouseNo(dispLocate[locCnt].getWhStationNo());
                        bayViews[j].setPalletId((String)dispLocate[locCnt].getValue(Pallet.PALLET_ID, ""));

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
                }
            }
            levelViews[lc].setBayView(bayViews);
            dispLevel--;
        }

        return levelViews;
    }

    // DFKLOOK ここまで追加

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
     * @return SearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // DFKLOOK createSearchKey()は無処理に変更
        return null;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // DFKLOOK getDisplayData()は無処理に変更
        return null;
    }

    // DFKLOOK ここから追加
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
        AsShelfStatusSCHParams param = (AsShelfStatusSCHParams)searchParam;

        ShelfSearchKey slfkey = new ShelfSearchKey();
        ASShelfHandler asSlfh = new ASShelfHandler(getConnection());

        // 検索条件セット
        slfkey.clear();
        // 倉庫ステーション、バンク指定
        slfkey.setWhStationNo(whStation);
        slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
        slfkey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // レベルNoの昇順
        slfkey.setLevelNoOrder(true);
        // ベイNoの昇順
        slfkey.setBayNoOrder(true);

        // ロケーション情報取得
        Shelf[] sheEnt = (Shelf[])asSlfh.find(slfkey);

        // 該当データがない場合
        if (ArrayUtil.isEmpty(sheEnt))
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
        // 該当データが1件の場合
        else if (sheEnt.length == 1)
        {
            // ベイが「0」かチェック
            if (sheEnt[0].getBayNo() == 0)
            {
                // レベルが「0」かチェック
                if (sheEnt[0].getLevelNo() == 0)
                {
                    // メッセージをセット
                    // 6023223=該当データはベイ、レベルが「0」の棚しか存在しないため表示できません。
                    setMessage("6023223");
                    return false;
                }
                else
                {
                    // メッセージをセット
                    // 6023224=該当データはベイが「0」の棚しか存在しないため表示できません。
                    setMessage("6023224");
                    return false;
                }
            }
            // レベルが「0」かチェック
            else if (sheEnt[0].getLevelNo() == 0)
            {
                // メッセージをセット
                // 6023225=該当データはレベルが「0」の棚しか存在しないため表示できません。
                setMessage("6023225");
                return false;
            }
        }
        // 該当データが複数件ある場合
        else
        {
            // ベイが「0」かチェック
            if (sheEnt[0].getBayNo() == 0)
            {
                // レベルが「0」かチェック
                if (sheEnt[0].getLevelNo() == 0)
                {
                    if (checkCount(param, whStation))
                    {
                        // メッセージをセット
                        // 6023226=表示しましたがベイ、レベルが「0」の棚が存在します。
                        setMessage("6023226");
                        return true;
                    }
                    else
                    {
                        // メッセージをセット
                        // 6023231=該当データはベイもしくはレベルが「0」の棚しか存在しないため表示できません。
                        setMessage("6023231");
                        return false;
                    }
                }
                else
                {
                    if (checkCount(param, whStation))
                    {
                        // メッセージをセット
                        // 6023227=表示しましたがベイが「0」の棚が存在します。
                        setMessage("6023227");
                        return true;
                    }
                    else
                    {
                        // メッセージをセット
                        // 6023231=該当データはベイもしくはレベルが「0」の棚しか存在しないため表示できません。
                        setMessage("6023231");
                        return false;
                    }
                }
            }
            // レベルが「0」かチェック
            else if (sheEnt[0].getLevelNo() == 0)
            {
                if (checkCount(param, whStation))
                {
                    // メッセージをセット
                    // 6023228=表示しましたがレベルが「0」の棚が存在します。
                    setMessage("6023228");
                    return true;
                }
                else
                {
                    // メッセージをセット
                    // 6023231=該当データはベイもしくはレベルが「0」の棚しか存在しないため表示できません。
                    setMessage("6023231");
                    return false;
                }
            }
            // 上記以外
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

        // 6001013 = 表示しました。
        setMessage("6001013");
        // 表示データがある場合
        return true;
    }

    /**
     * 表示データがあるかのチェックを行うメソッドです。<BR>
     * @param searchParam 表示データ取得条件を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンス。<BR>
     * @param whStation エリアに対応した倉庫ステーションNo.
     * @return true:表示データあり,false:表示データなし
     * @throws CommonException 全ての例外を報告します。
     */
    protected boolean checkCount(Params searchParam, String whStation)
            throws CommonException
    {
        AsShelfStatusSCHParams param = (AsShelfStatusSCHParams)searchParam;

        ShelfSearchKey slfkey = new ShelfSearchKey();
        ASShelfHandler asSlfh = new ASShelfHandler(getConnection());

        // 検索条件セット
        slfkey.clear();
        // 倉庫ステーション、バンク指定
        slfkey.setWhStationNo(whStation);
        slfkey.setBankNo(Integer.parseInt(param.getString(BANK_NO)));
        slfkey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        slfkey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        slfkey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // AS/RS棚情報に該当データがない場合
        if (asSlfh.count(slfkey) == 0)
        {
            return false;
        }

        return true;
    }

    /**
     * AS/RS棚データを取得します。<BR>
     * 同一棚に戻らないパターンの戻り入庫中のパレットは除外して取得します。<BR>
     * @param wareHouseNo 倉庫No.
     * @param bankNo      バンクNo.
     * @return AS/RS棚データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected Shelf[] getShelfData(String wareHouseNo, int bankNo)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        StringBuffer sql = null;
        // 検索条件をセット
        // 同一棚に再入庫しないステーションへ出庫したピッキング出庫または積増入庫で、
        // 出庫した棚が空棚（出庫完了または到着または開始（搬送区分が入庫））のパレット、
        // またはユニット出庫の出庫完了状態のパレットは除外して検索する
        sql = new StringBuffer();
        sql.append("SELECT * FROM");
        sql.append(" (");

        // CarryInfo、Palletとまったく結びつかない空棚
        sql.append(" SELECT");
        sql.append(" DMSHELF.STATION_NO STATION_NO,");
        sql.append(" DMSHELF.WH_STATION_NO WH_STATION_NO,");
        sql.append(" DMSHELF.BANK_NO BANK_NO,");
        sql.append(" DMSHELF.BAY_NO BAY_NO,");
        sql.append(" DMSHELF.LEVEL_NO LEVEL_NO,");
        sql.append(" DMSHELF.STATUS_FLAG STATUS_FLAG,");
        sql.append(" DMSHELF.ACCESS_NG_FLAG ACCESS_NG_FLAG,");
        sql.append(" DMSHELF.PROHIBITION_FLAG PROHIBITION_FLAG,");
        sql.append(" NULL P_STATUS_FLAG,");
        sql.append(" NULL EMPTY_FLAG,");
        sql.append(" NULL PALLET_ID");
        sql.append(" FROM DMSHELF");
        sql.append(" WHERE");
        sql.append(" STATUS_FLAG = ");
        sql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_EMPTY));
        sql.append(" AND DMSHELF.WH_STATION_NO = ");
        sql.append(DBFormat.format(wareHouseNo));
        sql.append(" AND DMSHELF.BANK_NO = ");
        sql.append(bankNo);
        sql.append(" AND DMSHELF.BAY_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_BAY);
        sql.append(" AND DMSHELF.BAY_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_BAY);
        sql.append(" AND DMSHELF.LEVEL_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_LEVEL);
        sql.append(" AND DMSHELF.LEVEL_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_LEVEL);

        sql.append(" UNION ");

        // CarryInfo、Palletと結びつく実棚
        sql.append("SELECT");
        sql.append(" DMSHELF.STATION_NO STATION_NO,");
        sql.append(" DMSHELF.WH_STATION_NO WH_STATION_NO,");
        sql.append(" DMSHELF.BANK_NO BANK_NO,");
        sql.append(" DMSHELF.BAY_NO BAY_NO,");
        sql.append(" DMSHELF.LEVEL_NO LEVEL_NO,");
        sql.append(" DMSHELF.STATUS_FLAG STATUS_FLAG,");
        sql.append(" DMSHELF.ACCESS_NG_FLAG ACCESS_NG_FLAG,");
        sql.append(" DMSHELF.PROHIBITION_FLAG PROHIBITION_FLAG,");
        sql.append(" DNPALLET.STATUS_FLAG P_STATUS_FLAG,");
        sql.append(" DNPALLET.EMPTY_FLAG EMPTY_FLAG,");
        sql.append(" DNPALLET.PALLET_ID PALLET_ID");
        sql.append(" FROM DMSHELF,");
        sql.append(" (");
        sql.append(" SELECT DNPALLET.STATUS_FLAG,");
        sql.append(" DNPALLET.EMPTY_FLAG,");
        sql.append(" DNPALLET.CURRENT_STATION_NO,");
        sql.append(" DNPALLET.PALLET_ID");
        sql.append(" FROM DNPALLET");
        sql.append(" WHERE");
        sql.append(" DNPALLET.PALLET_ID NOT IN");
        sql.append(" (");
        sql.append("SELECT");
        sql.append(" DNCARRYINFO.PALLET_ID");
        sql.append(" FROM DNCARRYINFO");
        sql.append(" WHERE");
        sql.append(" (");
        sql.append("DNCARRYINFO.RESTORING_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC));
        sql.append(" AND (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        sql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_PICKING));
        sql.append(" OR DNCARRYINFO.RETRIEVAL_DETAIL = ");
        sql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING)).append(")");
        sql.append(" AND (DNCARRYINFO.CMD_STATUS = ");
        sql.append(DBFormat.format(CarryInfo.CMD_STATUS_COMP_RETRIEVAL));
        sql.append(" OR DNCARRYINFO.CMD_STATUS = ");
        sql.append(DBFormat.format(CarryInfo.CMD_STATUS_ARRIVAL));
        sql.append(" OR (DNCARRYINFO.CMD_STATUS = ");
        sql.append(DBFormat.format(CarryInfo.CMD_STATUS_START));
        sql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE)).append(")))");
        sql.append(" OR ");
        sql.append("(");
        sql.append("DNCARRYINFO.RETRIEVAL_DETAIL = ");
        sql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_UNIT));
        sql.append(" AND DNCARRYINFO.CMD_STATUS = ");
        sql.append(DBFormat.format(CarryInfo.CMD_STATUS_COMP_RETRIEVAL)).append(")");
        sql.append(" OR ");
        sql.append("(");
        sql.append("DNCARRYINFO.RESTORING_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC));
        sql.append(" AND DNCARRYINFO.RETRIEVAL_DETAIL = ");
        sql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK));
        sql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        sql.append(" AND (DNCARRYINFO.CMD_STATUS = ");
        sql.append(DBFormat.format(CarryInfo.CMD_STATUS_COMP_RETRIEVAL));
        sql.append(" OR DNCARRYINFO.CMD_STATUS = ");
        sql.append(DBFormat.format(CarryInfo.CMD_STATUS_ARRIVAL)).append(")");
        sql.append(" AND DNCARRYINFO.RETRIEVAL_STATION_NO <> DNCARRYINFO.SOURCE_STATION_NO");
        sql.append(")");
        sql.append(" GROUP BY DNCARRYINFO.PALLET_ID");
        sql.append(")");
        sql.append(") DNPALLET");
        sql.append(" WHERE");
        sql.append(" DMSHELF.STATION_NO = DNPALLET.CURRENT_STATION_NO");
        sql.append(" AND DMSHELF.STATUS_FLAG <> ");
        sql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_EMPTY));
        sql.append(" AND DMSHELF.WH_STATION_NO = ");
        sql.append(DBFormat.format(wareHouseNo));
        sql.append(" AND DMSHELF.BANK_NO = ");
        sql.append(bankNo);
        sql.append(" AND DMSHELF.BAY_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_BAY);
        sql.append(" AND DMSHELF.BAY_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_BAY);
        sql.append(" AND DMSHELF.LEVEL_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_LEVEL);
        sql.append(" AND DMSHELF.LEVEL_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_LEVEL);

        sql.append(" UNION ");

        // 棚間移動 入庫予約の棚
        sql.append(" SELECT");
        sql.append(" DMSHELF.STATION_NO STATION_NO,");
        sql.append(" DMSHELF.WH_STATION_NO WH_STATION_NO,");
        sql.append(" DMSHELF.BANK_NO BANK_NO,");
        sql.append(" DMSHELF.BAY_NO BAY_NO,");
        sql.append(" DMSHELF.LEVEL_NO LEVEL_NO,");
        sql.append(" DMSHELF.STATUS_FLAG STATUS_FLAG,");
        sql.append(" DMSHELF.ACCESS_NG_FLAG ACCESS_NG_FLAG,");
        sql.append(" DMSHELF.PROHIBITION_FLAG PROHIBITION_FLAG, ");
        sql.append(DBFormat.format(Pallet.PALLET_STATUS_STORAGE_PLAN));
        sql.append(" P_STATUS_FLAG,");
        sql.append(" DNPALLET.EMPTY_FLAG EMPTY_FLAG,");
        sql.append(" DNPALLET.PALLET_ID PALLET_ID");
        sql.append(" FROM DMSHELF, DNPALLET, DNCARRYINFO");
        sql.append(" WHERE");
        sql.append(" DNCARRYINFO.DEST_STATION_NO = DMSHELF.STATION_NO");
        sql.append(" AND DNPALLET.PALLET_ID = DNCARRYINFO.PALLET_ID");
        sql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RACK_TO_RACK));
        sql.append(" AND DMSHELF.STATUS_FLAG = ");
        sql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_RESERVATION));
        sql.append(" AND DMSHELF.WH_STATION_NO = ");
        sql.append(DBFormat.format(wareHouseNo));
        sql.append(" AND DMSHELF.BANK_NO = ");
        sql.append(bankNo);
        sql.append(" AND DMSHELF.BAY_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_BAY);
        sql.append(" AND DMSHELF.BAY_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_BAY);
        sql.append(" AND DMSHELF.LEVEL_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_LEVEL);
        sql.append(" AND DMSHELF.LEVEL_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_LEVEL);

        sql.append(" UNION ");

        // 在庫確認で棚間移動が発生した、棚間移動中と移動後の元棚
        sql.append(" SELECT");
        sql.append(" DMSHELF.STATION_NO STATION_NO,");
        sql.append(" DMSHELF.WH_STATION_NO WH_STATION_NO,");
        sql.append(" DMSHELF.BANK_NO BANK_NO,");
        sql.append(" DMSHELF.BAY_NO BAY_NO,");
        sql.append(" DMSHELF.LEVEL_NO LEVEL_NO,");
        sql.append(" DMSHELF.STATUS_FLAG STATUS_FLAG,");
        sql.append(" DMSHELF.ACCESS_NG_FLAG ACCESS_NG_FLAG,");
        sql.append(" DMSHELF.PROHIBITION_FLAG PROHIBITION_FLAG, ");
        sql.append(DBFormat.format(Pallet.PALLET_STATUS_RETRIEVAL));
        sql.append(" P_STATUS_FLAG,");
        sql.append(" DNPALLET.EMPTY_FLAG EMPTY_FLAG,");
        sql.append(" DNPALLET.PALLET_ID PALLET_ID");
        sql.append(" FROM DMSHELF, DNPALLET, DNCARRYINFO");
        sql.append(" WHERE");
        // 2010/08/02 Y.Osawa UPD ST
//        sql.append(" ((");
//        sql.append(" DNCARRYINFO.RETRIEVAL_STATION_NO = DMSHELF.STATION_NO");
//        sql.append(" AND DNCARRYINFO.RETRIEVAL_DETAIL = ");
//        sql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK));
//        sql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
//        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
//        sql.append(" AND DNCARRYINFO.RETRIEVAL_STATION_NO <> DNCARRYINFO.SOURCE_STATION_NO");
//        sql.append(" AND DNPALLET.PALLET_ID = DNCARRYINFO.PALLET_ID");
//        sql.append(") OR (");
//        sql.append(" DNCARRYINFO.RETRIEVAL_STATION_NO = DMSHELF.STATION_NO");
//        sql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
//        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RACK_TO_RACK));
//        sql.append(" AND DMSHELF.STATUS_FLAG = ");
//        sql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_RESERVATION));
//        sql.append(" AND DNPALLET.PALLET_ID = DNCARRYINFO.PALLET_ID");
//        sql.append("))");
        sql.append(" (");
        sql.append(" DNCARRYINFO.RETRIEVAL_STATION_NO = DMSHELF.STATION_NO");
        sql.append(" AND DNPALLET.PALLET_ID = DNCARRYINFO.PALLET_ID");
        sql.append(" AND ((");
        sql.append(" DNCARRYINFO.RETRIEVAL_DETAIL = ");
        sql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK));
        sql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        sql.append(" AND DNCARRYINFO.RETRIEVAL_STATION_NO <> DNCARRYINFO.SOURCE_STATION_NO");
        sql.append(") OR (");
        sql.append(" DNCARRYINFO.CARRY_FLAG = ");
        sql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RACK_TO_RACK));
        sql.append(" AND DMSHELF.STATUS_FLAG = ");
        sql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_RESERVATION));
        sql.append(")))");
        // 2010/08/02 Y.Osawa UPD ED
        sql.append(" AND DMSHELF.WH_STATION_NO = ");
        sql.append(DBFormat.format(wareHouseNo));
        sql.append(" AND DMSHELF.BANK_NO = ");
        sql.append(bankNo);
        sql.append(" AND DMSHELF.BAY_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_BAY);
        sql.append(" AND DMSHELF.BAY_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_BAY);
        sql.append(" AND DMSHELF.LEVEL_NO >= ");
        sql.append(WmsParam.LOCATION_STATUS_MIN_LEVEL);
        sql.append(" AND DMSHELF.LEVEL_NO <= ");
        sql.append(WmsParam.LOCATION_STATUS_MAX_LEVEL);
        sql.append(")");
        sql.append(" ORDER BY LEVEL_NO DESC, BAY_NO ASC");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            return (Shelf[])ddbHandler.query(String.valueOf(sql), new Shelf());
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    // DFKLOOK ここまで追加

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
