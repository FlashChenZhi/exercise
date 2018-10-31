// $Id: AsUnavailableLocationSCH.java 5589 2009-11-10 01:07:18Z kanda $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCHParams.AREA;
import static jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCHParams.ASRS_LOCATION_STATUS;
import static jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCHParams.LOCATION;
import static jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCHParams.STATION;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * FAとDA のAS/RS 禁止棚設定のスケジュール処理を行います。
 *
 * @version $Revision: 5589 $, $Date: 2009-11-10 10:07:18 +0900 (火, 10 11 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class AsUnavailableLocationSCH
        extends AbstractAsrsSCH
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
    public AsUnavailableLocationSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * AS/RS棚修正登録処理を行います。
     * @param  ps AS/RS禁止棚設定パラメータ
     * @return 正常完了はtrue、条件エラーはfalseを返す。
     * @throws CommonException データベース処理でエラー発生した場合にthrowする
     * 
     */
    public boolean startSCH(ScheduleParams ps)
            throws CommonException
    {
        //ステーションNo取得
        AreaController areacon = new AreaController(getConnection(), getClass());
        String station = areacon.toAsrsLocation(ps.getString(AREA), ps.getString(LOCATION));

        ps.set(STATION, station);

        if (!checkLocation(ps))
        {
            return false;
        }

        //ASRS棚検索クラス生成
        ShelfSearchKey skey = new ShelfSearchKey();
        ShelfHandler hand = new ShelfHandler(getConnection());

        // 検索条件をセット
        skey.setStationNo(station);
        // 検索結果を取得
        Shelf[] wShelf = (Shelf[])hand.find(skey);
        if (!StringUtil.isBlank(wShelf[0].getPairStationNo()))
        {
            // ダブルディープのチェックを行う。
            if (!checkDoubleDeepLocation(ps))
            {
                return false;
            }
        }

        //日次更新中のチェック
        if (!canStart())
        {
            setMessage(getMessage());
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        ShelfAlterKey sAkey = new ShelfAlterKey();

        //検索条件を設定する
        sAkey.setStationNo(station);

        //更新を設定する
        //棚状態(使用可、不可)
        sAkey.updateProhibitionFlag(changeProhibitionFlag(ps.getString(ASRS_LOCATION_STATUS)));

        //更新を実行する
        hand.modify(sAkey);

        //6001006=設定しました。
        setMessage("6001006");
        return true;

    }
    
//    /**
//     * FA用　棚修正登録処理を行います。
//     * @param  ps FA禁止棚設定パラメータ
//     * @return 正常完了はtrue、条件エラーはfalseを返す。
//     * @throws CommonException データベース処理でエラー発生した場合にthrowする
//     * 
//     */
//    public boolean startSCH(FaAsUnavailableLocationSCHParams ps)
//            throws CommonException
//    {
//        //ステーションNo取得
//        AreaController areacon = new AreaController(getConnection(), getClass());
//        String station = areacon.toAsrsLocation(ps.getString(AREA), ps.getString(LOCATION));
//
//        ps.set(STATION, station);
//
//        if (!checkLocation(ps))
//        {
//            return false;
//        }
//
//        //ASRS棚検索クラス生成
//        ShelfSearchKey skey = new ShelfSearchKey();
//        ShelfHandler hand = new ShelfHandler(getConnection());
//
//        // 検索条件をセット
//        skey.setStationNo(station);
//        // 検索結果を取得
//        Shelf[] wShelf = (Shelf[])hand.find(skey);
//        if (!StringUtil.isBlank(wShelf[0].getPairStationNo()))
//        {
//            // ダブルディープのチェックを行う。
//            if (!checkDoubleDeepLocation(ps))
//            {
//                return false;
//            }
//        }
//
//        //日次更新中のチェック
//        if (!canStart())
//        {
//            setMessage(getMessage());
//            return false;
//        }
//
//        // 搬送データクリアチェック
//        if (isAllocationClear())
//        {
//            return false;
//        }
//
//        ShelfAlterKey sAkey = new ShelfAlterKey();
//
//        //検索条件を設定する
//        sAkey.setStationNo(station);
//
//        //更新を設定する
//        //棚状態(使用可、不可)
//        sAkey.updateProhibitionFlag(changeProhibitionFlag(ps.getString(ASRS_LOCATION_STATUS)));
//
//        //更新を実行する
//        hand.modify(sAkey);
//
//        //6001006=設定しました。
//        setMessage("6001006");
//        return true;
//
//    }

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
     * 棚の状態をチェックしメンテナンス可能かどうか判断します。
     * @param param パラメータ
     * @return true：メンテナンス可能
     * @throws ReadWriteException 例外を報告します。
     */
    protected boolean checkLocation(ScheduleParams param)
            throws ReadWriteException
    {
        ShelfSearchKey skey = new ShelfSearchKey();
        ShelfHandler shandler = new ShelfHandler(getConnection());

        // 検索条件をセット
        skey.setStationNo(param.getString(STATION));
        // 検索結果を取得
        Shelf[] wShelf = (Shelf[])shandler.find(skey);

        if (wShelf.length <= 0)
        {
            //MSG=実在する棚Noを入力してください。
            setMessage("6023067");
            return false;
        }

        //更新対象チェック
        if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf[0].getProhibitionFlag())
                && SystemDefine.PROHIBITION_FLAG_NG.equals(param.getString(ASRS_LOCATION_STATUS)))
        {
            //6023149=設定された棚は既に禁止棚です。
            setMessage("6023149");
            return false;
        }
        if (Shelf.PROHIBITION_FLAG_OK.equals(wShelf[0].getProhibitionFlag())
                && SystemDefine.PROHIBITION_FLAG_OK.equals(param.getString(ASRS_LOCATION_STATUS)))
        {
            //6023150=設定された棚は既に使用可能棚です。
            setMessage("6023150");
            return false;
        }

        //禁止棚への変更時、棚状態により条件チェックを行います。
        if (SystemDefine.PROHIBITION_FLAG_NG.equals(param.getString(ASRS_LOCATION_STATUS)))
        {
            //予約棚チェック
            if (SystemDefine.LOCATION_STATUS_FLAG_RESERVATION.equals(wShelf[0].getStatusFlag()))
            {
                //6023086=指定された棚は予約棚のためメンテナンスできません。
                setMessage("6023086");
                return false;
            }

            //空棚の場合チェック不要
            if (SystemDefine.LOCATION_STATUS_FLAG_EMPTY.equals(wShelf[0].getStatusFlag()))
            {
                return true;
            }

            //実棚ならば、引当、異常棚チェック
            PalletHandler plHandler = new PalletHandler(getConnection());
            PalletSearchKey plSearchkey = new PalletSearchKey();

            //検索条件をセット
            plSearchkey.setCurrentStationNo(param.getString(STATION));
            // 検索結果の取得
            Pallet[] wPallet = (Pallet[])plHandler.find(plSearchkey);

            String pltstatus = wPallet[0].getStatusFlag();
            if (SystemDefine.PALLET_STATUS_STORAGE_PLAN.equals(pltstatus)
                    || SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN.equals(pltstatus)
                    || SystemDefine.PALLET_STATUS_RETRIEVAL.equals(pltstatus))
            {
                //6023070=指定された棚は現在引当中です。
                setMessage("6023070");
                return false;
            }
            else if (SystemDefine.PALLET_STATUS_IRREGULAR.equals(pltstatus))
            {
                //6023071=指定された棚は異常棚のため設定できません。
                setMessage("6023071");
                return false;
            }
        }

        return true;
    }

    /**
     * ダブルディープの棚の状態チェックを行います。
     * @param param パラメータ
     * @return true：メンテナンス可能、false: メンテナンス不可
     * @throws CommonException データベース処理でエラー発生した場合にthrowする
     */
    protected boolean checkDoubleDeepLocation(ScheduleParams param)
            throws CommonException
    {
        //ASRS棚検索クラス生成
        ShelfSearchKey skey = new ShelfSearchKey();
        ShelfHandler hand = new ShelfHandler(getConnection());

        // 検索条件をセット
        AreaController areaCon = new AreaController(getConnection(), getClass());
        skey.setWhStationNo(areaCon.getWhStationNo(param.getString(AREA)));
        skey.setStationNo(param.getString(STATION));
        // 検索結果を取得
        Shelf wShelf = (Shelf)hand.findPrimary(skey);

        if (Shelf.PROHIBITION_FLAG_NG.equals(param.getString(ASRS_LOCATION_STATUS)))
        {
            // 禁止棚に設定
            // 手前棚
            if (Shelf.BANK_SELECT_NEAR.equals(wShelf.getSide()))
            {
                if (isLastTemporayLocation(wShelf))
                {
                    // 6023303=ペアの空棚が無くなるので設定できません。
                    setMessage("6023303");
                    return false;
                }

                // 手前棚を禁止棚に設定する場合。
                skey.clear();
                skey.setWhStationNo(wShelf.getWhStationNo());
                skey.setStationNo(wShelf.getPairStationNo());
                Shelf pairShelf = (Shelf)hand.findPrimary(skey);
                if (Shelf.ACCESS_NG_FLAG_NG.equals(pairShelf.getAccessNgFlag()))
                {
                    // 奥棚がアクセス不可棚ならば、true を返す。
                    return true;
                }
                if (Shelf.PROHIBITION_FLAG_NG.equals(pairShelf.getProhibitionFlag()))
                {
                    // 奥棚が禁止棚なのでtrueを返す。
                    return true;
                }
                else
                {
                    if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag()))
                    {
                        // 6023307=奥棚が空棚のため設定できません。
                        setMessage("6023307");
                        return false;
                    }
                    else if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(pairShelf.getStatusFlag()))
                    {
                        // 6023306=奥棚が予約棚のため設定できません。
                        setMessage("6023306");
                        return false;
                    }
                    else
                    {
                        // 奥棚のShelfが実棚なので、パレットの状態を確認し、設定不可の詳細を取得する
                        // 倉庫と棚Noでパレット情報を取得する。
                        Pallet[] plt = searchPallet(pairShelf.getStationNo());
                        if (plt.length != 0)
                        {
                            if (Pallet.PALLET_STATUS_REGULAR.equals(plt[0].getStatusFlag())
                                    && Pallet.ALLOCATION_FLAG_NOT_ALLOCATED.equals(plt[0].getAllocationFlag()))
                            {
                                // 6023309=奥棚が実棚のため設定できません。
                                setMessage("6023309");
                                return false;
                            }
                            else if (Pallet.PALLET_STATUS_IRREGULAR.equals(plt[0].getStatusFlag()))
                            {
                                // 6023308=奥棚が異常棚のため設定できません。
                                setMessage("6023308");
                                return false;
                            }
                            else
                            {
                                // 6023310=奥棚は現在引当中です。
                                setMessage("6023310");
                                return false;
                            }
                        }
                        else
                        {
                            // ここでPalletが無いのは、この処理途中に他で更新が行われた。
                            // このデータは、他の端末で更新されたため処理できません。
                            setMessage("6003006");
                            return false;
                        }
                    }
                }
            }
            else
            {
                // 奥棚
                // 最後のペア空棚かチェックする。
                if (isLastPairEmptyShelf(param.getString(AREA), param.getString(LOCATION)))
                {
                    // 6023303=ペアの空棚が無くなるので設定できません。
                    setMessage("6023303");
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        // 禁止棚の解除
        else
        {
            if (Shelf.BANK_SELECT_FAR.equals(wShelf.getSide()))
            {
                // 奥棚の禁止棚を解除
                skey.clear();
                skey.setWhStationNo(wShelf.getWhStationNo());
                skey.setStationNo(wShelf.getPairStationNo());
                Shelf pairShelf = (Shelf)hand.findPrimary(skey);
                if (Shelf.PROHIBITION_FLAG_NG.equals(pairShelf.getProhibitionFlag()))
                {
                    // 6023304=手前が禁止棚のため設定できません。
                    setMessage("6023304");
                    return false;
                }
                if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag()))
                {
                    // 手前棚が空棚ならば、trueを返す。
                    return true;
                }
                else
                {
                    // 手前棚が空棚以外の場合。
                    if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(wShelf.getStatusFlag()))
                    {
                        // 手前棚が空棚でない状態で、奥棚が空棚ならば、奥棚の禁止は解除出来ない。
                        // 禁止棚の解除不可詳細処理
                        PalletHandler plth = new PalletHandler(getConnection());
                        PalletSearchKey pltKey = new PalletSearchKey();
                        // 手前棚は異常棚かチェックする。
                        pltKey.setWhStationNo(pairShelf.getWhStationNo());
                        pltKey.setCurrentStationNo(pairShelf.getStationNo());
                        pltKey.setStatusFlag(Pallet.PALLET_STATUS_IRREGULAR);
                        if (plth.count(pltKey) >= 1)
                        {
                            // 6023302=手前棚が異常棚のため設定できません。
                            setMessage("6023302");
                        }
                        else
                        {
                            // 6023305=手前が空棚ではないため設定できません。
                            setMessage("6023305");
                        }
                        return false;
                    }
                    else
                    {
                        // 手前棚が空棚でない、かつ、奥棚も空棚でない。
                        return true;
                    }
                }
            }
            else
            {
                // 手前棚の禁止棚を解除
                return true;
            }
        }
    }

    /**
     * 棚No.よりAS/RSパレット情報を検索します
     * @param location 棚No.
     * @return AS/RSパレット情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Pallet[] searchPallet(String location)
            throws CommonException
    {
        PalletHandler wPlHandler = new PalletHandler(this.getConnection());
        PalletSearchKey wPlSearchKey = new PalletSearchKey();
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        CarryInfoSearchKey wCarrySearchKey = carryControl.getEmptyShelfPallet();

        // 検索条件をセットします。
        wPlSearchKey.setCurrentStationNo(location);
        wPlSearchKey.setKey(Pallet.PALLET_ID, wCarrySearchKey, "!=", "", "", true);

        return (Pallet[])wPlHandler.find(wPlSearchKey);
    }

    /**
     * 画面用使用不可情報をDB用使用不可情報に変換します。
     * @param prohibitionFlag 使用不可情報
     * @return 使用不可情報
     */
    protected String changeProhibitionFlag(String prohibitionFlag)
    {
        // 使用可
        if (StringUtil.isBlank(prohibitionFlag))
        {
            return "";
        }
        return prohibitionFlag;
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
