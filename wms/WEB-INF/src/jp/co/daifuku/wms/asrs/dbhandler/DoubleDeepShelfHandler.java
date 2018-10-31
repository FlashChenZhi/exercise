//$Id: DoubleDeepShelfHandler.java 7959 2010-05-26 06:16:38Z ota $
package jp.co.daifuku.wms.asrs.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;


/**
 * SHELFテーブルより<code>DoubleDeepShelf</code>インスタンスの生成を行うために使用されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>M.INOUE</TD><TD>ダブルディープ対応追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7959 $, $Date: 2010-05-26 15:16:38 +0900 (水, 26 5 2010) $
 * @author  $Author: ota $
 */
public class DoubleDeepShelfHandler
        extends ASShelfHandler
{
    // Class fields --------------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection to connect with database
     </en>*/
    private Connection _conn = null;

    // Class variables -----------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     */
    public DoubleDeepShelfHandler(Connection conn)
    {
        super(conn);
        setConnection(conn);
    }

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7959 $,$Date: 2010-05-26 15:16:38 +0900 (水, 26 5 2010) $");
    }

    // Public methods ------------------------------------------------
    /**
     * 空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param blnCCarry 空棚検索条件処理判定 true:条件処理する false:条件処理しない
     * @param listSPossible 空棚数アイル一覧データ
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param minVacantCnt  最小確保空棚数
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     */
    public Shelf findEmptyShelfForDoubleDeep(Aisle tAisle, Zone[] tZone, Boolean blnCCarry, ArrayList listSPossible,
            boolean empLocDeterm, int minVacantCnt)
            throws ReadWriteException,
                InvalidDefineException
    {
        ResultSet rset = null;
        Shelf[] fndStation = null;
        String sqlstring = null;
        try
        {
            String fmtSQL =
                    "SELECT * FROM DMSHELF WHERE" + " PARENT_STATION_NO = {0}" + " AND HARD_ZONE_ID = {1}"
                            + " AND SOFT_ZONE_ID = {2}" + " AND STATUS_FLAG = '" + Shelf.LOCATION_STATUS_FLAG_EMPTY
                            + "'" + " AND PROHIBITION_FLAG = '" + Station.PROHIBITION_FLAG_OK + "'"
                            + " AND ACCESS_NG_FLAG = '" + Shelf.ACCESS_NG_FLAG_OK + "'";

            // 空棚検索用オブジェクト
            Object[] fmtObj = new Object[4];
            fmtObj[0] = DBFormat.format(tAisle.getStationNo());
            // 空棚チェック用オブジェクト
            Object[] checkObj = new Object[4];
            checkObj[0] = DBFormat.format(tAisle.getStationNo());
            String strDirection = "";

            ArrayList<String> palletList = new ArrayList<String>();
            ArrayList<Shelf> shelfList = new ArrayList<Shelf>();

            PalletHandler pltHandle = new PalletHandler(getConnection());
            ShelfHandler slfHandle = new ShelfHandler(getConnection());

            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            CarryInfoSearchKey cskey = carryControl.getEmptyShelfPallet();
            
            for (int i = 0; i < tZone.length; i++)
            {
                Shelf fndShelf = null;

                if (rset != null)
                {
                    // ResultSetがクローズしてない場合は、まずクローズする。
                    rset.close();
                    rset = null;
                    closeStatement();
                }

                String findSQL = fmtSQL;

                checkObj[1] = DBFormat.format(tZone[i].getHardZone().getHardZoneId());
                checkObj[2] = DBFormat.format(tZone[i].getSoftZoneID());

                // INSERT K.Toda 2007/09/14
                // 空棚検索条件処理フラグがtrueの場合、空棚検索する前に事前条件処理を行います
                // 以下のチェックにて対象アイルステーション・ゾーンで入庫可能数が１以上の場合
                // それ以降の空棚検索処理を行います
                if (blnCCarry == true)
                {
                    // 空棚検索条件チェックＯＫＮＧフラグ ture：ＯＫ／false：ＮＧ
                    Boolean blnEmptShelfChkFlag = true;

                    // 空棚数アイルデータ一覧をデータ分、検索します
                    for (int intPCount = 0; intPCount < listSPossible.size(); intPCount++)
                    {
                        // 空棚数アイル一覧データを１行取得
                        String strPossible = String.valueOf(listSPossible.get(intPCount));

                        // ステーション情報の先頭位置を取得
                        int intPoStationStart = strPossible.indexOf("{");
                        intPoStationStart = intPoStationStart + 1;
                        // ステーション情報の終了位置を取得
                        int intPoStationEnd = strPossible.indexOf("}");
                        // ソフトゾーン情報の先頭位置を取得
                        int intPoSoftZoneStart = strPossible.indexOf("[");
                        intPoSoftZoneStart = intPoSoftZoneStart + 1;
                        // ソフトゾーン情報の終了位置を取得
                        int intPoSoftZoneEnd = strPossible.indexOf("]");
                        // ハードゾーン情報の先頭位置を取得
                        int intPoHardZoneStart = strPossible.indexOf("<");
                        intPoHardZoneStart = intPoHardZoneStart + 1;
                        // ハードゾーン情報の終了位置を取得
                        int intPoHardZoneEnd = strPossible.indexOf(">");
                        // 入庫格納数情報の開始位置を取得
                        int intPoQtyStart = strPossible.indexOf("(");
                        intPoQtyStart = intPoQtyStart + 1;
                        // 入庫格納数情報の終了位置を取得
                        int intPoQtyEnd = strPossible.indexOf(")");

                        // 搬送中データテーブルの「ステーション・ハードゾーンNo」が一致する行を検索し
                        // 搬送数分の減算を行います
                        // ＜一致条件＞
                        // 検索アイルステーションと一致
                        // 且つ、検索ゾーンと一致
                        if (strPossible.substring(intPoStationStart, intPoStationEnd).indexOf(tAisle.getStationNo()) >= 0)
                        {
                            if (strPossible.substring(intPoSoftZoneStart, intPoSoftZoneEnd).indexOf(
                                    tZone[i].getSoftZoneID()) >= 0)
                            {
                                if (strPossible.substring(intPoHardZoneStart, intPoHardZoneEnd).indexOf(
                                        tZone[i].getHardZone().getHardZoneId()) >= 0)
                                {
                                    // 検索されたアイルステーション、ゾーンに一致する空棚数（入庫可能数）を取得する
                                    int intSubtractionQty =
                                            Integer.valueOf(strPossible.substring(intPoQtyStart, intPoQtyEnd));

                                    // 検索中に１回でも０件データが取得された場合は
                                    // 今回アイルステーション、ゾーンはＮＧとして事前チェックを終了し
                                    // 空棚検索も次ゾーン処理へ移行する
                                    if (intSubtractionQty == 0)
                                    {
                                        blnEmptShelfChkFlag = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    // 今回アイルステーション、ゾーンはＮＧとして事前チェックを終了し
                    // 空棚検索も次ゾーン処理へ移行する
                    if (blnEmptShelfChkFlag == false)
                    {
                        continue;
                    }
                }

                // ペアであいている棚の奥側の棚を検索します。
                String checkSQL =
                        "SELECT * FROM (" + fmtSQL + ") S1, (" + fmtSQL + ") S2"
                                + " WHERE S1.STATION_NO = S2.PAIR_STATION_NO AND S1.PRIORITY < S2.PRIORITY {3} ";

                strDirection = String.valueOf(tZone[i].getDirection());
                if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
                {
                    // レベル方向検索(HP側から):HP手前
                    checkObj[3] = "ORDER BY S1.BAY_NO, S1.LEVEL_NO, S1.PRIORITY";
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
                {
                    // ベイ方向検索(HP側から):HP下段
                    checkObj[3] = "ORDER BY S1.LEVEL_NO, S1.BAY_NO, S1.PRIORITY";
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
                {
                    // レベル方向検索(OP側から):OP手前
                    checkObj[3] = "ORDER BY S1.BAY_NO DESC, S1.LEVEL_NO, S1.PRIORITY";
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
                {
                    // ベイ方向検索(OP側から):OP下段
                    checkObj[3] = "ORDER BY S1.LEVEL_NO, S1.BAY_NO DESC, S1.PRIORITY";
                }
                else
                {
                    //空棚検索方向が定義外の場合、例外を返す。
                    Object[] tObj = new Object[3];
                    tObj[0] = this.getClass().getName();
                    tObj[1] = "Direction";
                    tObj[2] = String.valueOf(tZone[0].getDirection());
                    String classname = (String)tObj[0];
                    RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
                    throw (new InvalidDefineException(WmsMessageFormatter.format(6006009, tObj[0], tObj[1], tObj[2])));
                }

                sqlstring = SimpleFormat.format(checkSQL, checkObj);

                rset = executeSQL(sqlstring, true);
                Shelf[] checkShelf = makeShelf(rset, 2);
                rset.close();
                rset = null;
                closeStatement();

                findSQL = findSQL + " {3}";
                fmtObj[1] = DBFormat.format(tZone[i].getHardZone().getHardZoneId());
                fmtObj[2] = DBFormat.format(tZone[i].getSoftZoneID());
                strDirection = String.valueOf(tZone[i].getDirection());
                StringBuffer sqlOrder = new StringBuffer();
                if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
                {
                    // レベル方向検索(HP側から)
                    sqlOrder.append("ORDER BY BAY_NO, LEVEL_NO, PRIORITY");
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
                {
                    // ベイ方向検索(HP側から)
                    sqlOrder.append("ORDER BY LEVEL_NO, BAY_NO, PRIORITY");
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
                {
                    // レベル方向検索(OP側から)
                    sqlOrder.append("ORDER BY BAY_NO DESC, LEVEL_NO, PRIORITY");
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
                {
                    // ベイ方向検索(OP側から)
                    sqlOrder.append("ORDER BY LEVEL_NO, BAY_NO DESC, PRIORITY");
                }
                else
                {
                    //空棚検索方向が定義外の場合、例外を返す。
                    Object[] tObj = new Object[3];
                    tObj[0] = this.getClass().getName();
                    tObj[1] = "Direction";
                    tObj[2] = String.valueOf(tZone[i].getDirection());
                    String classname = (String)tObj[0];
                    RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
                    throw (new InvalidDefineException(WmsMessageFormatter.format(6006009, tObj[0], tObj[1], tObj[2])));
                }

                // 更新の場合はロックを行う
                if (empLocDeterm)
                {
                    sqlOrder.append(" FOR UPDATE");
                }
                fmtObj[3] = sqlOrder;

                sqlstring = SimpleFormat.format(findSQL, fmtObj);

                //queryを実行し、結果セットの先頭データよりShelfインスタンスを生成する。
                rset = executeSQL(sqlstring, true);
                // Shelfインスタンスは一つだけ生成する。
                fndStation = (DoubleDeepShelf[])makeShelf(rset, 3);
                // 空棚が最小確保空棚数以下の場合は引当てない
                if (fndStation.length > minVacantCnt)
                {
                    PalletSearchKey pltSrchKey = null;
                    if (checkShelf.length > 1)
                    {
                        // 空きペアが２つ以上ある場合は、奥棚を引当てる。
                        // INSERT C.K
                        while (true)
                        {
                            if (fndStation.length == 0)
                            {
                                break;
                            }
                            for (int j = 0; j < fndStation.length; j++)
                            {
                                // 空棚検索の結果手前棚の場合
                                if (BankSelect.BANK_SELECT_NEAR.equals(fndStation[j].getSide()))
                                {
                                    pltSrchKey = new PalletSearchKey();
                                    pltSrchKey.setCurrentStationNo(fndStation[j].getPairStationNo());
                                    pltSrchKey.setKey(Pallet.PALLET_ID, cskey, "!=", "", "", true);
                                    Pallet[] plt = (Pallet[])pltHandle.find(pltSrchKey);
                                    // 奥棚にパレットがない場合は手前棚に入庫を行わない
                                    if (plt == null || plt.length <= 0)
                                    {
                                        // 奥棚のチェック
                                        if (pairShelfStatusCheck(fndStation[j]))
                                        {
                                            // 奥棚がアクセス不可棚、又は、使用禁止棚ならば手前棚は入庫できる
                                            return fndStation[j];
                                        }
                                        continue;
                                    }

                                    // 奥棚が出庫予約、出庫中の場合は手前棚に入庫を行わない
                                    if (Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(plt[0].getStatusFlag())
                                            || Pallet.PALLET_STATUS_RETRIEVAL.equals(plt[0].getStatusFlag()))
                                    {
                                        continue;
                                    }
                                    else
                                    {
                                        return fndStation[j];
                                    }
                                }
                                // 空棚検索の結果奥棚の場合
                                else
                                {
                                    return fndStation[j];
                                }
                            }
                            // Shelfインスタンスは一つだけ生成する。
                            fndStation = (DoubleDeepShelf[])makeShelf(rset, 3);
                        }
                    }
                    //空ペアが一つの状態で空棚検索の結果その空ペアがなくなる場合はNullを返す。
                    else if (checkShelf.length == 1)
                    {
                        // INSERT C.K
                        while (true)
                        {
                            if (fndStation.length == 0)
                            {
                                break;
                            }
                            for (int j = 0; j < fndStation.length; j++)
                            {
                                if (checkShelf[0].getStationNo().equals(fndStation[j].getStationNo()))
                                {
                                    // 最後のペア棚（最後のペア棚の奥棚）なので、とりあえずパスする。
                                    if (fndShelf == null)
                                    {
                                        // 最初のゾーンにある最後のペア棚を記憶
                                        fndShelf = checkShelf[0];
                                    }
                                    continue;
                                }
                                else
                                {
                                    ShelfSearchKey slfkey = new ShelfSearchKey();
                                    slfkey.setPairStationNo(checkShelf[0].getStationNo());
                                    Shelf[] pairShelf = (Shelf[])slfHandle.find(slfkey);
                                    if (pairShelf[0].getStationNo().equals(fndStation[j].getStationNo()))
                                    {
                                        // 最後のペア棚（最後のペア棚の手前棚）なのでパスする。
                                        continue;
                                    }
                                    else
                                    {
                                        // 空棚検索の結果、奥棚の場合
                                        if (BankSelect.BANK_SELECT_FAR.equals(fndStation[j].getSide()))
                                        {
                                            // 奥棚が空棚は異常なのでパスする。
                                            continue;
                                        }

                                        pltSrchKey = new PalletSearchKey();
                                        pltSrchKey.setCurrentStationNo(fndStation[j].getPairStationNo());
                                        pltSrchKey.setKey(Pallet.PALLET_ID, cskey, "!=", "", "", true);
                                        Pallet[] plt = (Pallet[])pltHandle.find(pltSrchKey);
                                        // 奥棚にパレットがない場合は手前棚に入庫を行わない
                                        if (plt == null || plt.length <= 0)
                                        {
                                            // 奥棚のチェック
                                            if (pairShelfStatusCheck(fndStation[j]))
                                            {
                                                // 奥棚がアクセス不可棚、又は、使用禁止棚ならば手前棚は入庫できる
                                                return fndStation[j];
                                            }
                                            continue;
                                        }

                                        // 奥棚が出庫中の場合は手前棚に入庫を行わない
                                        if (Pallet.PALLET_STATUS_RETRIEVAL.equals(plt[0].getStatusFlag()))
                                        {
                                            continue;
                                        }
                                        else
                                        {
                                            // 奥棚が出庫予約
                                            if (Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(plt[0].getStatusFlag()))
                                            {
                                                // 奥棚のパレットIDと手前棚のShelf情報を記憶する
                                                palletList.add(palletList.size(), plt[0].getPalletId());
                                                shelfList.add(shelfList.size(), fndStation[j]);
                                            }
                                            else
                                            {
                                                // 奥棚は実棚で出庫予定が無いので、棚を返す
                                                return fndStation[j];
                                            }
                                        }
                                    }
                                }
                            }
                            // Shelfインスタンスは一つだけ生成する。
                            fndStation = (DoubleDeepShelf[])makeShelf(rset, 3);
                        }
                    }
                    else
                    {
                        // ペア空棚がまったく無い時は、最後のペア空棚への棚間移動が行われているが、その他の場所に
                        // 空棚で入庫可能な手前棚が有るかも知れないので探す。
                        if (fndStation.length <= 1)
                        {
                            // 最後のペア空棚の手前棚以外に手前棚の空棚が無いので、このゾーンには空棚なし。
                            continue;
                        }

                        // 棚間移動中のペア棚の手前棚以外に１つ以上空棚が有る。
                        while (true)
                        {
                            if (fndStation.length == 0)
                            {
                                break;
                            }
                            for (int j = 0; j < fndStation.length; j++)
                            {
                                pltSrchKey = new PalletSearchKey();
                                pltSrchKey.setCurrentStationNo(fndStation[j].getPairStationNo());
                                pltSrchKey.setKey(Pallet.PALLET_ID, cskey, "!=", "", "", true);
                                Pallet[] plt = (Pallet[])pltHandle.find(pltSrchKey);
                                // 奥棚にパレットがない場合は手前棚に入庫を行わない
                                if (plt == null || plt.length <= 0)
                                {
                                    // 奥棚のチェック
                                    if (pairShelfStatusCheck(fndStation[j]))
                                    {
                                        // 奥棚がアクセス不可棚、又は、使用禁止棚ならば手前棚は入庫できる
                                        return fndStation[j];
                                    }
                                    continue;
                                }

                                // 奥棚が出庫中の場合は手前棚に入庫を行わない
                                if (Pallet.PALLET_STATUS_RETRIEVAL.equals(plt[0].getStatusFlag()))
                                {
                                    // 奥棚に出庫作業中の棚が有る
                                    continue;
                                }
                                else
                                {
                                    // 奥棚が出庫予約
                                    if (Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(plt[0].getStatusFlag()))
                                    {
                                        // 奥棚のパレットIDと手前棚のShelf情報を記憶する
                                        palletList.add(palletList.size(), plt[0].getPalletId());
                                        shelfList.add(shelfList.size(), fndStation[j]);
                                    }
                                    else
                                    {
                                        // 奥棚は実棚で出庫予定が無いので、棚を返す
                                        return fndStation[j];
                                    }
                                }
                            }
                            // Shelfインスタンスは一つだけ生成する。
                            fndStation = (DoubleDeepShelf[])makeShelf(rset, 3);
                        }
                    }
                }
            }

            // ここに来た時は、奥棚が出庫予定で手前棚が空棚、又は、まったく空棚が無い時だけ
            if (palletList.size() >= 1)
            {
                // 奥棚が出庫予約の手前棚の中の最初の１件を返す
                return shelfList.get(0);
            }
        }
        catch (NotFoundException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (NoPrimaryException e)
        {
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        return null;
    }

    /**
     * ダブルディープの場合の棚間移動用の空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * ここでの空棚の考えは奥棚/手前棚ともに空棚の場合です。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone 空棚検索対象ゾーン
     * @param aisleNo 棚間移動元のアイルNo
     * @param hardZoneId 棚間移動元のハードゾーンID
     * @param softZoneId 棚間移動元のソフトゾーンID
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     */
    public Shelf findEmptyShelfForRackToRack(Aisle tAisle, Zone[] tZone, String aisleNo, String hardZoneId,
            String softZoneId)
            throws ReadWriteException,
                InvalidDefineException
    {
        ResultSet rset = null;
        Shelf[] fndStation = null;
        String sqlstring = null;
        try
        {
            String fmtSQL =
                    "SELECT * FROM DMSHELF WHERE" + " PARENT_STATION_NO = {0}" + " AND HARD_ZONE_ID = {1}"
                            + " AND SOFT_ZONE_ID = {2}" + " AND STATUS_FLAG = '" + Shelf.LOCATION_STATUS_FLAG_EMPTY
                            + "'" + " AND PROHIBITION_FLAG = '" + Station.PROHIBITION_FLAG_OK + "'"
                            + " AND ACCESS_NG_FLAG = '" + Shelf.ACCESS_NG_FLAG_OK + "'";

            // ペアであいている棚の奥側の棚を検索します。
            fmtSQL =
                    "SELECT * FROM (" + fmtSQL + ") S1, (" + fmtSQL + ") S2"
                            + " WHERE S1.STATION_NO = S2.PAIR_STATION_NO" + " AND S1.PRIORITY < S2.PRIORITY {3}";

            Object[] fmtObj = new Object[4];

            fmtObj[0] = DBFormat.format(tAisle.getStationNo());
            String strDirection = "";
            for (int i = 0; i < tZone.length; i++)
            {
                fmtObj[1] = DBFormat.format(tZone[i].getHardZone().getHardZoneId());
                fmtObj[2] = DBFormat.format(tZone[i].getSoftZoneID());
                strDirection = String.valueOf(tZone[i].getDirection());
                StringBuffer sqlOrder = new StringBuffer();
                if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
                {
                    // レベル方向検索(HP側から):HP手前
                    sqlOrder.append("ORDER BY S1.BAY_NO, S1.LEVEL_NO, S1.PRIORITY");
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
                {
                    // ベイ方向検索(HP側から):HP下段
                    sqlOrder.append("ORDER BY S1.LEVEL_NO, S1.BAY_NO, S1.PRIORITY");
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
                {
                    // レベル方向検索(OP側から):OP手前
                    sqlOrder.append("ORDER BY S1.BAY_NO DESC, S1.LEVEL_NO, S1.PRIORITY");
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
                {
                    // ベイ方向検索(OP側から):OP下段
                    sqlOrder.append("ORDER BY S1.LEVEL_NO, S1.BAY_NO DESC, S1.PRIORITY");
                }
                else
                {
                    //空棚検索方向が定義外の場合、例外を返す。
                    Object[] tObj = new Object[3];
                    tObj[0] = this.getClass().getName();
                    tObj[1] = "Direction";
                    tObj[2] = String.valueOf(tZone[i].getDirection());
                    String classname = (String)tObj[0];
                    RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
                    throw (new InvalidDefineException(WmsMessageFormatter.format(6006009, tObj[0], tObj[1], tObj[2])));
                }
                sqlOrder.append(" FOR UPDATE");
                fmtObj[3] = sqlOrder;
                
                sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
                //queryを実行し、結果セットの先頭データよりShelfインスタンスを生成する。
                rset = executeSQL(sqlstring, true);
                //Shelfインスタンスは２つ生成する。
                fndStation = makeShelf(rset, 2);
                rset.close();
                rset = null;
                closeStatement();

                int pairCount =
                        (tAisle.getStationNo().equals(aisleNo)
                                && tZone[i].getHardZone().getHardZoneId().equals(hardZoneId) && tZone[i].getSoftZoneID().equals(
                                softZoneId)) ? 1
                                            : 2;

                if (fndStation.length >= pairCount)
                {
                    return fndStation[0];
                }
            }
        }
        catch (NotFoundException e)
        {
            // Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        return null;
    }

    /**<jp>
     * 各アイルステーション・ハードゾーン毎に空棚数（入庫可能数）を取得する。
     * @param parentStationNo アイルステーションNo
     * @param listSPossible 空棚数アイル一覧データ
     * @return ArrayList 入庫可能数格納テーブル
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public ArrayList getVacantCountDoubleDeep(String parentStationNo, ArrayList<String> listSPossible)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    {
        ResultSet rset = null;
        String sqlstring = null;

        try
        {
            String fmtSQL =
                    "SELECT PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID, COUNT(*) VACANT_COUNT "
                            + "FROM DMSHELF "
                            + "WHERE STATION_NO IN "
                            + "("
                            + " SELECT PAIR_STATION_NO FROM DMSHELF"
                            + " WHERE STATION_NO IN"
                            + " ("
                            + "  SELECT PAIR_STATION_NO FROM DMSHELF"
                            + "  WHERE STATUS_FLAG = '"
                            + Shelf.LOCATION_STATUS_FLAG_EMPTY
                            + "' AND"
                            + "  PARENT_STATION_NO = '"
                            + parentStationNo
                            + "'"
                            + " )"
                            + " AND"
                            + " ("
                            + "  STATUS_FLAG = '"
                            + Shelf.LOCATION_STATUS_FLAG_EMPTY
                            + "' OR"
                            + "  ("
                            + "   (STATUS_FLAG = '"
                            + Shelf.LOCATION_STATUS_FLAG_RESERVATION
                            + "'"
                            + "    OR STATUS_FLAG = '"
                            + Shelf.LOCATION_STATUS_FLAG_STORAGED
                            + "'"
                            + "   )"
                            + "   AND"
                            + "   (SELECT COUNT(*) FROM DNPALLET WHERE CURRENT_STATION_NO = DMSHELF.STATION_NO AND"
                            + "     STATUS_FLAG != '" + Pallet.PALLET_STATUS_RETRIEVAL + "'"
                            + "   ) > 0"
                            + "  )"
                            + " )"
                            + ") "
                            + "AND PROHIBITION_FLAG = '" + Shelf.PROHIBITION_FLAG_OK + "' " + "AND ACCESS_NG_FLAG = '"
                            + Shelf.ACCESS_NG_FLAG_OK + "' " + "AND PARENT_STATION_NO = '" + parentStationNo + "' "
                            + "GROUP BY PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID "
                            + "ORDER BY PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID";

            Object[] fmtObj = new Object[1];
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

            //queryを実行し、結果セットの先頭データより結果表のインスタンスを生成する。
            rset = executeSQL(sqlstring, true);

            while (rset.next())
            {
                int simpQty = rset.getInt("VACANT_COUNT");

                // ダブルディープの場合、棚間移動用（ペア棚）が必要なので、確保棚の２棚分減算します。
                simpQty = simpQty - 2;
                simpQty = (simpQty > 0) ? simpQty
                                       : 0;
                listSPossible.add("{" + rset.getString("PARENT_STATION_NO") + "}" + "," + "["
                        + rset.getString("SOFT_ZONE_ID") + "]" + "," + "<" + rset.getString("HARD_ZONE_ID") + ">" + ","
                        + "(" + simpQty + ")");
            }
        }
        catch (SQLException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (NotFoundException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        //<jp> 取得した空棚数の情報を格納します。</jp>
        return listSPossible;
    }

    /**
     * ダブルディープの場合のペア空棚数をカウントします。
     * 指定されたアイル、ゾーンをもとに奥／手前のペアで空いている空棚を１つとしてカウントし、返します。
     * @param tAisle 空棚検索対象アイル
     * @param tZone 空棚検索対象ゾーン
     * @return ペアの空棚数
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     */
    public int countPairEmptyShelf(Aisle tAisle, Zone tZone)
            throws ReadWriteException
    {
        ResultSet rset = null;
        String sqlstring = null;
        try
        {
            String fmtSQL =
                    "SELECT * FROM DMSHELF WHERE" + " PARENT_STATION_NO = {0}" + " AND HARD_ZONE_ID = {1}"
                            + " AND SOFT_ZONE_ID = {2}" + " AND STATUS_FLAG = '" + Shelf.LOCATION_STATUS_FLAG_EMPTY
                            + "'" + " AND PROHIBITION_FLAG = '" + Station.PROHIBITION_FLAG_OK + "'"
                            + " AND ACCESS_NG_FLAG = '" + Shelf.ACCESS_NG_FLAG_OK + "'";

            // ペアであいている棚の奥側の棚を検索します。
            fmtSQL =
                    "SELECT COUNT(*) COUNT FROM (" + fmtSQL + ") S1, (" + fmtSQL + ") S2"
                            + " WHERE S1.STATION_NO = S2.PAIR_STATION_NO AND" + " S1.SIDE = '" + Shelf.BANK_SELECT_FAR
                            + "'";

            Object[] fmtObj = new Object[3];

            fmtObj[0] = DBFormat.format(tAisle.getStationNo());
            fmtObj[1] = DBFormat.format(tZone.getHardZone().getHardZoneId());
            fmtObj[2] = DBFormat.format(tZone.getSoftZoneID());

            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            //queryを実行し、結果セットの先頭データよりShelfインスタンスを生成する。
            rset = executeSQL(sqlstring, true);
            rset.next();
            int count = rset.getInt("COUNT");

            return count;
        }
        catch (SQLException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (NotFoundException e)
        {
            // Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
    }

    // Accessor methods -----------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    /**<en>
     * Retrieve <code>Connection</code> to connect with database.
     * @return :<code>Connection</code> currently preserved
     </en>*/
    public Connection getConnection()
    {
        return _conn;
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param connection 設定するConnection
     </jp>*/
    /**<en>
     * Set <code>Connection</code> to connect with database
     * @param connection :Connection to set
     </en>*/
    public void setConnection(Connection connection)
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                throw new RuntimeException("Connection is null or closed!");
            }
            _conn = connection;
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Can not access to database!");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**
     * <code>ResultSet</code>から、各項目を取り出して、<code>Shelf</code>インスタンスを生成します。
     * @param rset SHELFテーブル検索の結果セット
     * @param maxcreate Shelfインスタンスを生成する個数。0の場合結果セットすべてのインスタンスを生成します。
     * @return 生成されたShelfインスタンスの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Shelf[] makeShelf(ResultSet rset, int maxcreate)
            throws ReadWriteException
    {
        //<jp> Shelfインスタンスの一時領域</jp>
        //<en> temporary store for Shelf instances</en>
        List<DoubleDeepShelf> tempShelfList = new ArrayList<DoubleDeepShelf>();

        //<jp> 検索結果からデータを取得し、Shelfインスタンスを新規作成する。</jp>
        //<en> data get from resultset and make new Shelf instance</en>
        try
        {
            int count = 0;
            while (rset.next())
            {
                //<jp> Shelfインスタンスを生成する。</jp>
                //<en> Generates Shelf instance.</en>
                DoubleDeepShelf tmpShelf = new DoubleDeepShelf(DBFormat.replace(rset.getString("STATION_NO")));
                setShelf(rset, tmpShelf);

                // ペア棚情報をセット
                setPairShelf(tmpShelf);
                tempShelfList.add(tmpShelf);

                //<jp> カウントアップ</jp>
                //<en> count up.</en>
                count++;
                if ((maxcreate != 0) && (count > maxcreate))
                {
                    //<jp> インスタンス生成上限数を超えた場合、ループを抜ける。</jp>
                    //<en> Gets out the loop when the cycle exceeded the ceiling number of of instance generation.</en>
                    break;
                }
            }
        }
        catch (InvalidDefineException e)
        {
            // ペア棚が見つからない場合に通知される。
            throw new ReadWriteException();
        }
        catch (SQLException e)
        {
            Object[] tObj = new Object[1];
            tObj[0] = String.valueOf(e.getErrorCode());
            RmiMsgLogClient.write(new TraceHandler(6007030, e), this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }

        //<jp> 一時領域からShelf配列へインスタンスを移動する。</jp>
        //<en> move instance from List to array of Shelf.</en>
        DoubleDeepShelf[] rstarr = (DoubleDeepShelf[])tempShelfList.toArray(new DoubleDeepShelf[0]);

        return (rstarr);
    }

    // Private methods -----------------------------------------------
    /**
     * 指定されたDoubleDeepShelfインスタンスとバンク情報をもとにペア棚を検索し、その内容をDoubleDeepShelfインスタンスに追加します。
     * ペア棚情報が見つからない場合、InvalidDefineExceptionを返します。
     * @param shf ペア棚情報をセットするDoubleDeepShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException バンク定義よりSHELFテ－ブルにペア棚情報が見つからなかった場合に通知されます。
     * @throws InvalidDefineException バンク定義（BANKSELECT）に不整合がある場合に通知されます。
     * @throws InvalidDefineException 生成するペア棚情報にセットする内容に不整合がある場合に通知されます。
     */
    private void setPairShelf(DoubleDeepShelf shf)
            throws ReadWriteException,
                InvalidDefineException
    {
        //<jp> データベースアクセス用</jp>
        //<en> for database access</en>
        Statement stmt = null;
        ResultSet rset = null;

        //倉庫、アイルステーション、バンクよりペアバンク情報を取得するSQL
        String fmtSQL = "SELECT STATUS_FLAG FROM DMSHELF WHERE PAIR_STATION_NO = {0}";

        Object[] fmtObj = new Object[1];
        fmtObj[0] = DBFormat.format(shf.getStationNo());

        try
        {
            stmt = getConnection().createStatement();
            //ペアバンク、手前・奥棚区分を取得する。
            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

            DEBUG.MSG(DEBUG.HANDLER, sqlstring);

            rset = stmt.executeQuery(sqlstring);
            if (rset.next())
            {
                try
                {
                    //STATUS_FLAGを取得する。
                    shf.setPairStatusFlag(rset.getString("STATUS_FLAG"));
                }
                catch (InvalidStatusException e)
                {
                    //セットする内容に不正があった場合例外を返す。
                    throw new InvalidDefineException();
                }
            }
            else
            {
                //棚情報がない場合、バンク定義エラーなので例外を返す。
                throw new InvalidDefineException();
            }
        }
        catch (SQLException e)
        {
            //エラーログの出力処理も行う。
            Object[] tObj = new Object[1];
            tObj[0] = String.valueOf(e.getErrorCode());
            RmiMsgLogClient.write(new TraceHandler(6007030, e), this.getClass().getName(), tObj);
            //ここで、ReadWriteExceptionをthrowする。
            throw new ReadWriteException(e);
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //エラーログの出力処理も行う。
                Object[] tObj = new Object[1];
                tObj[0] = String.valueOf(e.getErrorCode());
                RmiMsgLogClient.write(new TraceHandler(6007030, e), this.getClass().getName(), tObj);
                //ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
                throw new ReadWriteException(e);
            }
        }
    }

    /**
     * @see AbstractDBHandler#createEntity()
     * @return インスタンスを生成します。
     * @Override オーバーライドします。 
     */
    protected AbstractEntity createEntity()
    {
        return (new DoubleDeepShelf());
    }

    /**
     * 
     * @param checkShelf Shelfインスタンス
     * @return 入庫可能棚ならば、true、可能でないならば、falseを返す。
     * @throws NoPrimaryException 対象のShelfが複数存在する場合に通知されます。
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     */
    private boolean pairShelfStatusCheck(Shelf checkShelf)
            throws NoPrimaryException,
                ReadWriteException
    {
        ShelfHandler slfh = new ShelfHandler(getConnection());
        ShelfSearchKey slfKey = new ShelfSearchKey();
        slfKey.setStationNo(checkShelf.getPairStationNo());
        Shelf slf = (Shelf)slfh.findPrimary(slfKey);
        if ((Shelf.ACCESS_NG_FLAG_NG.equals(slf.getAccessNgFlag()))
                || (Shelf.PROHIBITION_FLAG_NG.equals(slf.getProhibitionFlag())))
        {
            // 奥棚がアクセス不可棚、又は、使用禁止棚ならば手前棚は入庫できる
            return true;
        }

        return false;
    }
}
//end of class
