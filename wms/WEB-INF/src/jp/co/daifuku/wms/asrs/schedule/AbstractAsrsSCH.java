// $Id: AbstractAsrsSCH.java 4770 2009-07-28 06:00:24Z shibamoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * AS/RSパッケージの入出庫スケジュール抽象クラスです。
 * このクラスは共通チェック処理などを実装します。
 * AS/RSに対する入庫、出庫スケジュールを行うクラスは本クラスを継承してください。
 * ただし、WMSSchedulerインターフェースメソッドの実装は行いません。継承クラスにて実装してください。
 * <BR>
 * Designer :K.Mori<BR>
 * Maker:K.Mori<BR>
 * <BR>
 *
 * @version $Revision: 4770 $, $Date: 2009-07-28 15:00:24 +0900 (火, 28 7 2009) $
 * @author  $Author: shibamoto $
 */
public abstract class AbstractAsrsSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ<BR>
     * データベース接続用コネクションをセットします。<BR>
     * @param conn データベースコネクション
     * @param parent 呼び出し元クラス<br>
     *   null が指定された場合は、自身が設定されます。
     * @param locale 対象ロケール<br>
     *   null が指定された場合は、デフォルト・ロケールが設定されます。
     * @param ui ユーザ情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public AbstractAsrsSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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
     * 指定されたステーションに対して入庫作業可能判定を行います。 <BR> 
     * 新規入庫スケジュールで使用するメソッドです。
     * 入庫可能なステーションであればtrue、入庫不可であればfalseを返します。
     * 入庫不可であれば、理由メッセージをメッセージエリアにセットします。
     * <BR>
     * @param stationNo      確認対象ステーションNo.
     * @return 入庫可能なステーションであればtrue、入庫不可であればfalseを返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean storageStationCheck(String stationNo)
            throws CommonException
    {
        // ステーションNo.よりインスタンス取得   
        Station station = getStation(stationNo);

        // グループコントローラのオンラインチェック
        if (!isControllerOnline(station))
        {
            return false;
        }

        // 今回指定されたステーションが作業場であれば、オンラインチェックのみ行い
        // このメソッドによるチェック処理は終了。
        if (!station.isSendable())
        {
            return true;
        }

        // 中断中フラグのチェック
        if (station.isSuspend())
        {
            // 6023063 = ステーションが中断中のため設定できません。
            setMessage("6023063");
            return false;
        }

        // ステーション種別のチェック
        if (!station.isInStation())
        {
            // 6023083 = 入庫作業ができないステーションは選択できません。
            setMessage("6023083");
            return false;
        }

        // 入庫モードのチェック
        if (!isStorageModeEnable(station))
        {
            // 6023084 = ステーションが入庫作業モードではないため設定できません。
            setMessage("6023084");
            return false;
        }

        AisleHandler aisleHandler = new AisleHandler(getConnection());
        AisleSearchKey aisleSearchKey = new AisleSearchKey();

        // 在庫確認中かどうかのチェック
        // アイルステーション番号が定義されていない場合（アイル結合の場合）
        if (StringUtil.isBlank(station.getAisleStationNo()))
        {
            // 倉庫全体より検索を行う。
            aisleSearchKey.clear();
            aisleSearchKey.setWhStationNo(station.getWhStationNo());
            aisleSearchKey.setInventoryCheckFlag(Aisle.INVENTORY_CHECK_FLAG_WORKING);

            if (aisleHandler.count(aisleSearchKey) > 0)
            {
                // 6023066 = 在庫確認中のため設定できません
                setMessage("6023066");
                return false;
            }
        }
        // アイルステーション番号が定義されている場合（アイル独立の場合）
        else
        {
            // 同一アイルより検索を行う。
            aisleSearchKey.clear();
            aisleSearchKey.setStationNo(station.getAisleStationNo());

            Aisle aisle = (Aisle)aisleHandler.findPrimary(aisleSearchKey);
            if (Aisle.INVENTORY_CHECK_FLAG_WORKING.equals(aisle.getInventoryCheckFlag()))
            {
                // 6013102= 在庫確認中のため設定できません
                setMessage("6023066");
                return false;
            }
        }

        return true;
    }

    /**
     * 指定されたステーションに対して出庫作業可能判定を行います。 <BR> 
     * 出庫、積増入庫、クローズ運用での入庫スケジュールで使用するメソッドです。
     * 出庫可能なステーションであればtrue、出庫不可であればfalseを返します。
     * 出庫不可であれば、理由メッセージをメッセージエリアにセットします。
     * リストセル行No指定なしの場合、行Noは0固定。
     * <BR>     
     * @param stationNo      確認対象ステーションNo.
     * @return 出庫可能なステーションであればtrue、出庫不可であればfalseを返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean retrievalStationCheck(String stationNo)
            throws CommonException
    {
        return retrievalStationCheck(stationNo, 0);
    }

    /**
     * 指定されたステーションに対して出庫作業可能判定を行います。 <BR> 
     * 出庫、積増入庫、クローズ運用での入庫スケジュールで使用するメソッドです。
     * 出庫可能なステーションであればtrue、出庫不可であればfalseを返します。
     * 出庫不可であれば、理由メッセージをメッセージエリアにセットします。
     * <BR>     
     * @param stationNo      確認対象ステーションNo.
     * @param rowNo          リストセル行No(ためうちなしの場合、0固定)
     * @return 出庫可能なステーションであればtrue、出庫不可であればfalseを返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean retrievalStationCheck(String stationNo, int rowNo)
            throws CommonException
    {

        // ステーションNo.よりインスタンス取得
        Station station = getStation(stationNo);

        // グループコントローラのオンラインチェック
        if (!isControllerOnline(station))
        {
            return false;
        }

        // 今回指定されたステーションが通常ステーション以外であれば、オンラインチェックのみ行い
        // このメソッドによるチェック処理は終了。
        if (!(Station.WORKPLACE_TYPE_FLOOR.equals(station.getWorkplaceType())))
        {
            return true;
        }

        // 出庫モードのチェック
        if (!isRetrievalModeEnable(station))
        {
            if (rowNo > 0)
            {
                // 6023235 = No.{0} ステーションが出庫作業モードではないため設定できません。
                String msg = WmsMessageFormatter.format(6023235, rowNo);
                setMessage(msg);
            }
            else
            {
                // ステーションが出庫作業モードではないため設定できません
                setMessage("6023065");
            }

            return false;
        }

        // ステーション種別のチェック
        if (!(Station.STATION_TYPE_INOUT.equals(station.getStationType()) || Station.STATION_TYPE_OUT.equals(station.getStationType())))
        {

            if (rowNo > 0)
            {
                // 6023234 = No.{0} 出庫作業ができないステーションは選択できません
                String msg = WmsMessageFormatter.format(6023234, rowNo);
                setMessage(msg);
            }
            else
            {
                // 6023064 = 出庫作業ができないステーションは選択できません
                setMessage("6023064");
            }

            return false;

        }

        // 中断中フラグのチェック
        if (Station.SUSPEND_ON.equals(station.getSuspend()))
        {
            if (rowNo > 0)
            {
                // 6023233 = No.{0} ステーションが中断中のため設定できません。
                String msg = WmsMessageFormatter.format(6023233, rowNo);
                setMessage(msg);
            }
            else
            {
                // 6023063 = ステーションが中断中のため設定できません
                setMessage("6023063");
            }

            return false;
        }

        AisleHandler aisleHandler = new AisleHandler(getConnection());
        AisleSearchKey aisleSearchKey = new AisleSearchKey();

        // 在庫確認中かどうかのチェック
        // アイルステーション番号が定義されていない場合（アイル結合の場合）
        if (StringUtil.isBlank(station.getAisleStationNo()))
        {
            // 倉庫全体より検索を行う。
            aisleSearchKey.setWhStationNo(station.getWhStationNo());
            aisleSearchKey.setInventoryCheckFlag(Aisle.INVENTORY_CHECK_FLAG_WORKING);

            if (aisleHandler.count(aisleSearchKey) > 0)
            {
                if (rowNo > 0)
                {
                    // 6023236 = No.{0} 在庫確認中のため設定できません。
                    String msg = WmsMessageFormatter.format(6023236, rowNo);
                    setMessage(msg);
                }
                else
                {
                    // 6023066 = 在庫確認中のため設定できません
                    setMessage("6023066");
                }

                return false;
            }
        }
        // アイルステーション番号が定義されている場合（アイル独立の場合）
        else
        {
            // 同一アイルより検索を行う。
            aisleSearchKey.setStationNo(station.getAisleStationNo());

            Aisle rAisle = (Aisle)aisleHandler.findPrimary(aisleSearchKey);
            if (Aisle.INVENTORY_CHECK_FLAG_WORKING.equals(rAisle.getInventoryCheckFlag()))
            {
                if (rowNo > 0)
                {
                    // 6023236 = No.{0} 在庫確認中のため設定できません。
                    String msg = WmsMessageFormatter.format(6023236, rowNo);
                    setMessage(msg);
                }
                else
                {
                    // 6023066 = 在庫確認中のため設定できません
                    setMessage("6023066");
                }

                return false;
            }
        }

        return true;
    }

    /**
     * 指定されたステーションに対して在庫確認作業可能判定を行います。 <BR> 
     * 出庫可能なステーションであればtrue、出庫不可であればfalseを返します。
     * 出庫不可であれば、理由メッセージをメッセージエリアにセットします。
     * <BR>     
     * @param stationNo      確認対象ステーションNo.
     * @return 出庫可能なステーションであればtrue、出庫不可であればfalseを返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean inventoryStationCheck(String stationNo)
            throws CommonException
    {

        // ステーションNo.よりインスタンス取得
        Station station = getStation(stationNo);
        String[] stNoArray = null;
        boolean findflag = false;

        if (station instanceof WorkPlace)
        {
            // 取得したステーション情報が作業場の場合、作業場内の
            // ステーションNo.一覧の取得して処理を行う。
            WorkPlace workplace = (WorkPlace)station;
            stNoArray = workplace.getWPStations();
        }
        else
        {
            stNoArray = new String[1];
            stNoArray[0] = stationNo;
        }

        if (!isControllerOnline(station))
        {
            return false;
        }

        for (int i = 0; i < stNoArray.length; i++)
        {
            // 作業場内のステーションを一つずつ取得し、グループコントローラーの状態チェックを行う。
            Station chkst = getStation(stNoArray[i]);

            // 今回指定されたステーションが作業場であれば、オンラインチェックのみ行い
            // このメソッドによるチェック処理は終了。
            if (!chkst.isSendable())
            {
                continue;
            }

            // 中断中フラグのチェック
            if (Station.SUSPEND_ON.equals(chkst.getSuspend()))
            {
                // 6023063 = ステーションが中断中のため設定できません
                setMessage("6023063");
                continue;
            }

            // ステーション種別のチェック
            if (!(Station.STATION_TYPE_INOUT.equals(chkst.getStationType()) || Station.STATION_TYPE_OUT.equals(chkst.getStationType())))
            {
                // 6023064 = 出庫作業ができないステーションは選択できません
                setMessage("6023064");
                continue;

            }

            // 出庫モードのチェック
            if (!isRetrievalModeEnable(chkst))
            {
                // ステーションが出庫作業モードではないため設定できません
                setMessage("6023065");
                continue;
            }
            findflag = true;
            break;
        }
        if (!findflag)
        {
            if (station instanceof WorkPlace)
            {
                // 6023111 = 作業場内に現在搬送可能なステーションがありません。
                setMessage("6023111");
            }

            return false;
        }

        return true;
    }

    /**
     * 入力されたステーションのモードが入庫モードかどうかを判断します。<BR>
     * ステーションのモードが入庫モードでかつモード切替要求中でなければ入庫モードと判断し、trueを返します。<BR>
     * モードを持たないステーションを指定された場合、無条件でtrueを返します。<BR>
     * 本処理はstorageStationCheckメソッドから呼び出されるので、直接呼び出す必要はありません。<BR>
     * @param station ステーションエンティティ
     * @return 入力されたステーションのモードが入庫ならばtrue、それ以外ならfalseを返します。
     */
    protected boolean isStorageModeEnable(Station station)
    {
        if (Station.STATION_TYPE_INOUT.equals(station.getStationType()))
        {
            // モード切替機能あり
            if (Station.MODE_TYPE_AGC_CHANGE.equals(station.getModeType())
                    || Station.MODE_TYPE_AWC_CHANGE.equals(station.getModeType()))
            {
                // 入庫モードかチェック
                if (Station.CURRENT_MODE_STORAGE.equals(station.getCurrentMode()))
                {
                    // モード切替要求中ではない
                    if (Station.MODE_REQUEST_NONE.equals(station.getModeRequest()))
                    {
                        // 入庫モード
                        return true;
                    }
                }
                // 入庫モード以外かモード切替要求中
                return false;
            }
        }

        // モード管理なし
        return true;
    }


    /**
     * 入力されたステーションのモードが出庫モードかどうかを判断します。<BR>
     * ステーションのモードが出庫モードでかつモード切替要求中でなければ出庫モードと判断し、trueを返します。<BR>
     * モードを持たないステーションを指定された場合、無条件でtrueを返します。<BR>
     * 本処理はretrievalStationCheckメソッドから呼び出されるので、直接呼び出す必要はありません。<BR>
     * @param station ステーションエンティティ
     * @return 入力されたステーションのモードが出庫ならばtrueそれ以外ならfalseを返します。
     */
    protected boolean isRetrievalModeEnable(Station station)
    {
        if (Station.STATION_TYPE_INOUT.equals(station.getStationType()))
        {
            // モード切替機能あり
            if (Station.MODE_TYPE_AGC_CHANGE.equals(station.getModeType())
                    || Station.MODE_TYPE_AWC_CHANGE.equals(station.getModeType()))
            {
                // 出庫モードかチェック
                if (Station.CURRENT_MODE_RETRIEVAL.equals(station.getCurrentMode()))
                {
                    // モード切替要求中ではない
                    if (Station.MODE_REQUEST_NONE.equals(station.getModeRequest()))
                    {
                        // 出庫モード
                        return true;
                    }
                }
                // 出庫モード以外かモード切替要求中
                return false;
            }
        }

        // モード管理なし
        return true;
    }

    /** 
     * 入庫用の空パレット用の入力チェックを行います。<BR>
     * 指定された商品コードが空パレットであれば、商品コード、ロットNo.入庫数をもとに、空パレット入力の妥当性チェックを行います。<BR>
     * 入力OKと判断する条件は以下の通りです。<BR>
     * <BR>
     * 1.ケース入数は0<BR>
     * 2.ケース数は0<BR>
     * 3.ピース数は1以上<BR>
     * 4.ロットNo.の入力は不可<BR>
     * <BR>
     * @param  itemcode 商品コード<BR>
     * @param  lotno ロットNo.<BR>
     * @param  entQty ケース入数<BR>
     * @param  caseQty ケース数<BR>
     * @param  pieceQty ピース数<BR>
     * @return 通常の商品か、指定されたitemcodeが空パレットで空パレットの条件を満たしていればtrue<BR>
     *          条件を満たしていなければfalseを返し、メッセージエリアにメッセージをセットします。
     */
    protected boolean checkCorrectEmptyPB(String itemcode, String lotno, int entQty, int caseQty, int pieceQty)
    {

        if (WmsParam.EMPTYPB_ITEMCODE.equals(itemcode))
        {
            if (entQty > 0)
            {
                // 6023074=空パレットの場合、ケース入数は指定できません。
                setMessage("6023074");
                return false;
            }
            if (caseQty > 0)
            {
                // 6023073=空パレットの入庫数に、ケース数は指定できません。ピース数を入力して下さい。
                setMessage("6023073");
                return false;
            }
            if (pieceQty < 1)
            {
                // 6023085=入庫ピース数には1以上の値を入力してください。
                setMessage("6023085");
                return false;
            }

            if (!StringUtil.isBlank(lotno))
            {
                // 6023099=空パレットの場合、ロットNo.は入力できません。
                setMessage("6023099");
                return false;
            }
        }

        return true;
    }

    /**
     * 異常棚用の荷主コード、商品コードが使用されていないかをチェックします。<BR>
     * 入力NGと判断する条件は以下の通りです。<BR>
     * <BR>
     * 1.商品コードが、異常棚用の商品コードの場合<BR>
     * 
     * @param itemcode 商品コード
     * @return 商品コードが通常のコードであればtrueを、異常棚用の商品コードの場合はfalse返します。
     *          falseの場合、メッセージエリアにメッセージをセットします
     */
    protected boolean checkIrregularItem(String itemcode)
    {
        if (WmsParam.IRREGULAR_ITEMCODE.equals(itemcode))
        {
            // 6023078 = {0}は異常棚用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023078, itemcode));
            return false;
        }

        return true;
    }

    /**
     * 簡易直行用の商品コードが使用されていないかをチェックします。<BR>
     * 入力NGと判断する条件は以下の通りです。<BR>
     * <BR>
     * 1.商品コードが、簡易直行用の商品コードの場合<BR>
     * 
     * @param itemcode 商品コード
     * @return 商品コードが通常のコードであればtrueを、簡易直行用の商品コードの場合はfalse返します。
     *          falseの場合、メッセージエリアにメッセージをセットします
     */
    protected boolean checkSimpleDirectTransferItem(String itemcode)
    {
        if (WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(itemcode))
        {
            // 6023259 = {0}は簡易直行用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023259, itemcode));
            return false;
        }

        return true;
    }

    /**
     * グループコントローラのオンラインチェックを行います。<BR>
     * 指定されたステーションが通常ステーションの場合、そのステーションの属するグループコントローラの状態が<BR>
     * オンラインかどうかを判定します。<BR>
     * 指定されたステーションが作業場の場合、属するステーション内のグループコントローラの状態がオンラインか<BR>
     * どうかを判定します。いずれかのステーションがオンラインであればtrue、全てオフラインであればfalseを返します。<BR>
     * 本処理はstorageStationCheck retrievalStationCheckメソッドから呼び出されるので、直接呼び出す必要はありませんが<BR>
     * 必要に応じて使用してください<BR>
     * @param station 作業場
     * @return オンライン : tue オンライン以外 : false
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     * @throws CommonException 作業場の情報取得で問題が発生した場合に通知されます。
     */
    protected boolean isControllerOnline(Station station)
            throws CommonException
    {

        GroupControllerHandler groupHandler = new GroupControllerHandler(getConnection());
        GroupControllerSearchKey groupKey = new GroupControllerSearchKey();

        if (station instanceof WorkPlace)
        {
            // 取得したステーション情報が作業場の場合、作業場内の
            // ステーションNo.一覧の取得して処理を行う。
            WorkPlace workplace = (WorkPlace)station;
            String[] stNoArray = workplace.getWPStations();

            boolean findflag = false;

            for (int i = 0; i < stNoArray.length; i++)
            {
                // 作業場内のステーションを一つずつ取得し、グループコントローラーの状態チェックを行う。
                Station chkst = getStation(stNoArray[i]);
                groupKey.clear();
                groupKey.setControllerNo(chkst.getControllerNo());

                // ステーションが属するグループコントローラNo.で検索を行う
                GroupController[] rGroupControll = (GroupController[])groupHandler.find(groupKey);
                if (rGroupControll == null || rGroupControll.length == 0)
                {
                    throw new InvalidDefineException("groupcontroler is null || zero length");
                }
                // グループコントローラがオンラインならOK
                if (GroupController.GC_STATUS_ONLINE.equals(rGroupControll[0].getStatusFlag()))
                {
                    findflag = true;
                    break;
                }
            }
            if (!findflag)
            {
                // 6023060 = システム状態をオンラインにして下さい
                setMessage("6023060");
                return false;
            }
        }
        else
        {
            // 取得したステーション情報がステーションの場合
            // ステーションが属するグループコントローラNo.で検索を行う
            groupKey.setControllerNo(station.getControllerNo());

            GroupController[] rGroupControll = (GroupController[])groupHandler.find(groupKey);

            if (!GroupController.GC_STATUS_ONLINE.equals(rGroupControll[0].getStatusFlag()))
            {
                // 6023060 = システム状態をオンラインにして下さい
                setMessage("6023060");
                return false;
            }
        }
        return true;
    }

    /**
     * 指定されたステーションNo.から<code>Station</code>クラスのオブジェクトを返します。 <BR> 
     * @param stationNo ステーションNo.
     * @return Stationクラスのオブジェクト
     * @throws CommonException スケジュールエラーが発生した場合に報告されます。
     */
    protected Station getStation(String stationNo)
            throws CommonException
    {
        return StationFactory.makeStation(getConnection(), stationNo);
    }

    /**
     * 指定された搬送ルートの状態より適切なメッセージを返します。<BR>
     * 返されたメッセージは画面メッセージエリア出力用に使用します。
     * statusには<code>RouteController</code>クラス変数として定義されているルート状態を渡します。
     * statusにRouteControllerクラスに定義されていない搬送ルート状態を渡した場合、
     * ScheudleExceptionを通知します。
     * @param status 搬送ルートの状態
     * @return メッセージ
     * @throws InvalidDefineException statusに予期しない搬送ルート状態が渡された場合に通知します
     */
    protected String getRouteErrorMessage(int status)
            throws InvalidDefineException
    {
        String msg = null;
        switch (status)
        {
            case RouteController.OFFLINE:
                // 搬送ルート間に切離中の機器があるため搬送できません。
                msg = "6023108";
                break;
            case RouteController.FAIL:
                // 搬送ルート間に設備異常の機器があるため搬送できません。
                msg = "6023109";
                break;
            case RouteController.NOTFOUND:
                // 搬送ルートが存在しないため搬送できません。
                msg = "6023110";
                break;
            case RouteController.NO_STATION_INTO_WORKPLACE:
                // 作業場内に現在搬送可能なステーションがありません。
                msg = "6023111";
                break;
            case RouteController.LOCATION_EMPTY:
                // 入庫可能な空棚がありません。
                msg = "6023112";
                break;
            case RouteController.AISLE_INVENTORYCHECK:
                // 在庫確認中のため設定できません。
                msg = "6023066";
                break;
            case RouteController.AISLE_EMPTYLOCATIONCHECK:
                // 空棚確認中のため設定出来ません。
                msg = "6023113";
                break;
            case RouteController.AGC_OFFLINE:
                // システム状態をオンラインにして下さい。
                msg = "6023060";
                break;
            default:
                // 範囲外の値を指定されました。
                throw new InvalidDefineException();
        }
        return msg;
    }


    /**
     * 入庫設定時、リストセルエリアと今回入力分の空パレットチェックを行います。<br>
     * @param itemCode 商品コード
     * @param params ためうちエリアのパラメータ
     * @return チェックOKならtrue、それ以外でfalseを返します。
     */
    protected boolean checkAddListEmptyPB(String itemCode, AsrsInParameter[] params)
    {
        // ためうちにデータが無い場合、無条件でtrueを返します。
        if (params == null || params.length == 0)
        {
            return true;
        }

        //1行目が空パレットの場合
        if (WmsParam.EMPTYPB_ITEMCODE.equals(params[0].getItemCode()))
        {
            //空パレットを追加
            if (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode))
            {
                // 6023076=空パレット在庫です。修正処理にて数量変更して下さい。
                setMessage("6023076");
                return false;
            }
            // リストセルデータが空パレットの場合、通常在庫を入力
            else
            {
                // 6023075=通常の在庫と空パレットは混載できません。
                setMessage("6023075");
                return false;
            }
        }

        //入力した商品コードが空パレットの場合
        if (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode))
        {
            for (AsrsInParameter lParam : params)
            {
                if (!WmsParam.EMPTYPB_ITEMCODE.equals(lParam.getItemCode()))
                {
                    // 6023075=通常の在庫と空パレットは混載できません。
                    setMessage("6023075");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 在庫情報と入力データの空パレットチェックを行います。<BR>
     * パラメータには以下のデータをセットしてください。<BR>
     * @param itemCode 商品コード
     * @param stock 在庫情報
     * @param pileEmptyPb 段積み空パレットフラグ(積増可:true 積み増し不可:false)
     * @return boolean チェックOKならtrue、それ以外でfalseを返します。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkAddStockEnptyPB(String itemCode, Stock[] stock, boolean pileEmptyPb)
            throws CommonException
    {
        // 在庫データが無い場合、無条件でtrueを返します。
        if (stock == null || stock.length == 0)
        {
            return true;
        }

        getClass();
        // 空パレットの在庫ならば、配列数は１
        if (WmsParam.EMPTYPB_ITEMCODE.equals(stock[0].getItemCode()))
        {
            //空パレットを追加
            if (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode))
            {
                // 在庫メンテ処理では、空パレット(段積)に空パレットを混載できない
                if (stock[0].getStockQty() > 1 && !pileEmptyPb)
                {
                    // 6023076=空パレット在庫です。修正処理にて数量変更して下さい。
                    setMessage("6023076");
                    return false;
                }
            }
            //空パレット(段積)に通常在庫混在できない
            else if (stock[0].getStockQty() > 1)
            {
                // 6023091=段積みの空パレットに、在庫は混載できません。。
                setMessage("6023091");
                return false;
            }
        }

        //入力した商品コードが空パレットの場合
        if (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode))
        {
            for (Stock lStock : stock)
            {
                if (!WmsParam.EMPTYPB_ITEMCODE.equals(lStock.getItemCode()))
                {
                    //6023075=通常の在庫と空パレットは混載できません。
                    setMessage("6023075");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ダブルディープのペア空棚を確認します。
     * @param areaNo エリアNo
     * @param shelfNo 棚No（入力形式の棚No）
     * @return ペアで空棚ならばtrue、そうでないならば、falseを返します。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isPairEmptyShelf(String areaNo, String shelfNo)
            throws CommonException
    {
        // エリアNoと、棚NoからShelfインスタンスを取得する。
        AreaController areaCtl = new AreaController(getConnection(), getClass());

        Station st = StationFactory.makeStation(getConnection(), areaCtl.toAsrsLocation(areaNo, shelfNo));
        if (!(st instanceof DoubleDeepShelf))
        {
            // ダブルディープの棚では無いので、falseを返す。
            return false;
        }

        DoubleDeepShelf ddShelf = (DoubleDeepShelf)st;
        if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(ddShelf.getStatusFlag())
                && Shelf.PROHIBITION_FLAG_OK.equals(ddShelf.getProhibitionFlag())
                && Shelf.ACCESS_NG_FLAG_OK.equals(ddShelf.getAccessNgFlag()))
        {
            st = StationFactory.makeStation(getConnection(), ddShelf.getPairStationNo());
            DoubleDeepShelf pairShelf = (DoubleDeepShelf)st;
            if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag())
                    && Shelf.PROHIBITION_FLAG_OK.equals(pairShelf.getProhibitionFlag())
                    && Shelf.ACCESS_NG_FLAG_OK.equals(pairShelf.getAccessNgFlag()))
            {
                // ペアの空棚なのでtrueを返す。
                return true;
            }
        }

        return false;
    }

    /**
     * ダブルディープの最後のペア空棚を確認します。
     * 引数で渡された棚が属するゾーンに有るペアの空棚数を返す。
     * @param areaNo エリアNo
     * @param shelfNo 棚No（入力形式の棚No）
     * @return 最後ペア空棚ならばtrue、そうでないならば、falseを返す。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isLastPairEmptyShelf(String areaNo, String shelfNo)
            throws CommonException
    {
        if (!isPairEmptyShelf(areaNo, shelfNo))
        {
            // 棚はペアの空棚ではないので、falseを返す。
            return false;
        }

        // ペアの空棚ならば、ペアの空棚数を取得する。
        // 倉庫ステーションNoと、Shelf形式の棚Noを取得する。
        AreaController areaCtl = new AreaController(getConnection(), getClass());
        ShelfOperator sOp = new ShelfOperator(getConnection());
        int count = sOp.countPairEmptyShelf(areaCtl.getWhStationNo(areaNo), areaCtl.toAsrsLocation(areaNo, shelfNo));
        if (count <= 1)
        {
            // 最後のペア空棚
            return true;
        }

        return false;
    }

    /**
     * ダブルディープの最後の仮置棚を確認します。
     * @param areaNo エリアNo
     * @param shelfNo 棚No（入力形式の棚No）
     * @return 最後仮置棚ならばtrue、そうでないならば、falseを返す。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isLastTemporayLocation(Shelf shelf)
            throws CommonException
    {
        // 同一アイル、ゾーン内に空棚が2つ以上存在するかチェックする
        ShelfHandler slfh = new ShelfHandler(getConnection());
        ShelfSearchKey slfKey = new ShelfSearchKey();

        slfKey.setWhStationNo(shelf.getWhStationNo());
        slfKey.setParentStationNo(shelf.getParentStationNo());
        slfKey.setHardZoneId(shelf.getHardZoneId());
        slfKey.setStationNo(shelf.getStationNo(), "!=");
        slfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        slfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        slfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);

        if (slfh.count(slfKey) >= 2)
        {
            return false;
        }

        return true;
    }
    
    /**
     * ハードウェア区分がAS/RSのユーザ情報を返します。
     * 
     * @return ユーザ情報<br>
     *  ユーザ情報がセットされていない場合は null
     */
    protected WmsUserInfo getWmsUserInfo()
    {
        DfkUserInfo dfkInfo = super.getUserInfo();
        if (dfkInfo == null)
        {
            return null;
        }
        
        WmsUserInfo wmsInfo = new WmsUserInfo(dfkInfo);
        wmsInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);
        
        return wmsInfo;
    }



    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractAsrsSCH.java 4770 2009-07-28 06:00:24Z shibamoto $";
    }

}
