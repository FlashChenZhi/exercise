// $Id: LocationInquirySCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;
import java.util.List;
import java.text.DecimalFormat;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.util.location.WmsLocationFormat;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.stock.schedule.LocationLevelView.LocationBayView;

import static jp.co.daifuku.wms.stock.schedule.LocationInquirySCHParams.*;

/**
 * 棚状態照会のスケジュール処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LocationInquirySCH
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
    public LocationInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // DFKLOOK query()は無処理に変更
        return null;
    }

    // DFKLOOK ここから追加
    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * ロケーション情報取得を取得します。
     * @param searchParam 表示データ取得条件を持つ<CODE>LocationInquirySCHParams</CODE>クラスのインスタンス。<BR>
     *         <CODE>IdmControlParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public LocationLevelView[] getLevelViewData(Params searchParam)
            throws ReadWriteException
    {
        // DBハンドラを生成
        LocateHandler loch = new LocateHandler(getConnection());
        LocateSearchKey skey = new LocateSearchKey();
        AreaController areaCtl = new AreaController(getConnection(), getClass());
        
        LocationInquirySCHParams param = (LocationInquirySCHParams)searchParam;

        // 存在チェック
        if (!check(searchParam))
        {
            return null;
        }

        // 検索条件セット
        skey.clear();
        // エリア、バンク指定
        skey.setAreaNo(param.getString(AREA_NO));
        skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
        skey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // レベルNoの降順
        skey.setLevelNoOrder(false);
        // ベイNoの昇順
        skey.setBayNoOrder(true);

        // ロケーション情報取得
        Locate[] locEnt = (Locate[])loch.find(skey);
        if (locEnt == null || locEnt.length <= 0)
        {
            // メッセージをセット
            // 6003011=対象データはありませんでした。
            setMessage("6003011");

            return null;
        }

        // ベイ件数取得
        skey.clear();
        // エリア、バンク指定
        skey.setAreaNo(param.getString(AREA_NO));
        skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
        skey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        // 最大ベイナンバー
        skey.setBayNoCollect("MAX");
        Entity[] bayNumMax = loch.find(skey);
        Locate[] entBay = (Locate[])bayNumMax;

        int maxBay = entBay[0].getBayNo();

        // レベル件数取得
        skey.clear();
        // エリア、バンク指定
        skey.setAreaNo(param.getString(AREA_NO));
        skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");
        // 最大ベイナンバー
        skey.setLevelNoCollect("MAX");
        Entity[] levelNumMax = loch.find(skey);
        Locate[] entLevel = (Locate[])levelNumMax;

        int levelCountMax = entLevel[0].getLevelNo();

        //取得パラメータ配列ナンバー
        int locCnt = 0;
        //表示レベルナンバー
        int dispLevel = levelCountMax;

        LocationLevelView[] levelViews = new LocationLevelView[levelCountMax];
        DecimalFormat df = new DecimalFormat("000");

        WmsLocationFormat wmsLoc = new WmsLocationFormat(areaCtl.getLocationStyle(param.getString(AREA_NO)));

        for (int lc = 0; lc < levelCountMax; lc++)
        {
            // 配列の初期化
            levelViews[lc] = new LocationLevelView();
            levelViews[lc].setLevel(df.format(dispLevel));

            LocationBayView[] bayViews = new LocationBayView[maxBay];
            for (int j = 0; j < maxBay; j++)
            {
                bayViews[j] = levelViews[lc].new LocationBayView();

                // 表示件数とカウント数の比較
                if (locCnt < locEnt.length)
                {
                    //表示位置と棚No.の比較
                    if (dispLevel == locEnt[locCnt].getLevelNo() && (j + 1) == locEnt[locCnt].getBayNo())
                    {
                        bayViews[j].setBankNo(df.format(locEnt[locCnt].getBankNo()));
                        bayViews[j].setBayNo(df.format(locEnt[locCnt].getBayNo()));
                        bayViews[j].setLevelNo(df.format(locEnt[locCnt].getLevelNo()));
                        String location = locEnt[locCnt].getLocationNo();
                        bayViews[j].setLocation(location);
                        bayViews[j].setBalloonLocation(wmsLoc.format(location, param.getString(AREA_NO)));
                        bayViews[j].setStatus(locEnt[locCnt].getStatusFlag());
                        locCnt++;
                    }
                    else
                    {
                        bayViews[j].setBankNo(null);
                        bayViews[j].setLevelNo(null);
                        bayViews[j].setBayNo(df.format(j + 1));
                        bayViews[j].setLocation(null);
                        bayViews[j].setBalloonLocation("-1");
                        bayViews[j].setStatus("-1");
                    }
                }
                else
                {
                    bayViews[j].setBankNo(null);
                    bayViews[j].setLevelNo(null);
                    bayViews[j].setBayNo(df.format(j + 1));
                    bayViews[j].setLocation(null);
                    bayViews[j].setBalloonLocation("-1");
                    bayViews[j].setStatus("-1");
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
     * @param searchParam 表示データ取得条件を持つ<CODE>LocationInquirySCHParams</CODE>クラスのインスタンス。<BR>
     * @return true:表示データあり,false:表示データなし
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean check(Params searchParam)
            throws ReadWriteException
    {
        LocationInquirySCHParams param = (LocationInquirySCHParams)searchParam;

        // DBハンドラを生成
        LocateHandler loch = new LocateHandler(getConnection());
        LocateSearchKey skey = new LocateSearchKey();

        // 検索条件セット
        skey.clear();
        // エリア、バンク指定
        skey.setAreaNo(param.getString(AREA_NO));
        skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
        skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // レベルNoの昇順
        skey.setLevelNoOrder(true);
        // ベイNoの昇順
        skey.setBayNoOrder(true);

        // ロケーション情報取得
        Locate[] locEnt = (Locate[])loch.find(skey);

        // 該当データがない場合
        if (locEnt == null || locEnt.length <= 0)
        {

            // 上限を超えるデータを取得
            // 検索条件セット
            skey.clear();
            // エリア、バンク指定
            skey.setAreaNo(param.getString(AREA_NO));
            skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
            skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, ">", "(", "", false);
            skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, ">", "", ")", true);

            // レベルNoの昇順
            skey.setLevelNoOrder(true);
            // ベイNoの昇順
            skey.setBayNoOrder(true);

            Locate[] locateEnt = (Locate[])loch.find(skey);

            // 上限を超えるデータがない場合
            if (locateEnt == null || locateEnt.length <= 0)
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
        else if (locEnt.length == 1)
        {
            // ベイが「0」かチェック
            if (locEnt[0].getBayNo() == 0)
            {
                // レベルが「0」かチェック
                if (locEnt[0].getLevelNo() == 0)
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
            else if (locEnt[0].getLevelNo() == 0)
            {
                // メッセージをセット
                // 6023225=該当データはレベルが「0」の棚しか存在しないため表示できません。
                setMessage("6023225");

                return false;
            }
            else
            {
                // 上限を超えるデータを取得
                // 検索条件セット
                skey.clear();
                // エリア、バンク指定
                skey.setAreaNo(param.getString(AREA_NO));
                skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
                skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, ">", "(", "", false);
                skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, ">", "", ")", true);

                // レベルNoの昇順
                skey.setLevelNoOrder(true);
                // ベイNoの昇順
                skey.setBayNoOrder(true);

                Locate[] locateEnt = (Locate[])loch.find(skey);
                if (locateEnt == null || locateEnt.length <= 0)
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
        // 該当データが複数件ある場合
        else
        {
            // ベイが「0」かチェック
            if (locEnt[0].getBayNo() == 0)
            {
                // レベルが「0」かチェック
                if (locEnt[0].getLevelNo() == 0)
                {
                    if (checkCount(param))
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
                    if (checkCount(param))
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
            else if (locEnt[0].getLevelNo() == 0)
            {
                if (checkCount(param))
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
                skey.clear();
                // エリア、バンク指定
                skey.setAreaNo(param.getString(AREA_NO));
                skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
                skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, ">", "(", "", false);
                skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, ">", "", ")", true);

                // レベルNoの昇順
                skey.setLevelNoOrder(true);
                // ベイNoの昇順
                skey.setBayNoOrder(true);

                Locate[] locateEnt = (Locate[])loch.find(skey);
                if (locateEnt == null || locateEnt.length <= 0)
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
    }

    /**
     * 表示データがあるかのチェックを行うメソッドです。<BR>
     * @param searchParam 表示データ取得条件を持つ<CODE>LocationInquirySCHParams</CODE>クラスのインスタンス。<BR>
     * @return true:表示データあり,false:表示データなし
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean checkCount(Params searchParam)
            throws ReadWriteException
    {
        LocationInquirySCHParams param = (LocationInquirySCHParams)searchParam;

        // DBハンドラを生成
        LocateHandler loch = new LocateHandler(getConnection());
        LocateSearchKey skey = new LocateSearchKey();

        // 検索条件セット
        skey.clear();
        // エリア、バンク指定
        skey.setAreaNo(param.getString(AREA_NO));
        skey.setBankNo(Integer.parseInt((param.getString(BANK_NO))));
        skey.setBayNo(WmsParam.LOCATION_STATUS_MIN_BAY, ">=");
        skey.setBayNo(WmsParam.LOCATION_STATUS_MAX_BAY, "<=");
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MIN_LEVEL, ">=");
        skey.setLevelNo(WmsParam.LOCATION_STATUS_MAX_LEVEL, "<=");

        // レベルNoの降順
        skey.setLevelNoOrder(false);
        // ベイNoの昇順
        skey.setBayNoOrder(true);

        // ロケーション情報取得
        Locate[] locEnt = (Locate[])loch.find(skey);

        if (locEnt == null || locEnt.length <= 0)
        {
            return false;
        }

        return true;
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
