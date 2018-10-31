// $Id: RftAddSCH.java 4147 2009-04-14 09:07:25Z kumano $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import static jp.co.daifuku.wms.system.schedule.RftAddSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftFinder;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.RftSetting;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * RFT追加設定のスケジュール処理を行います。
 *
 * @version $Revision: 4147 $, $Date: 2009-04-14 18:07:25 +0900 (火, 14 4 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class RftAddSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** DMRFTSetting登録項目 日本語 */
    private static final String JA_JP = "ja_JP";

    /** DMRFTSetting登録項目　英語 */
    private static final String EN_US = "en_US";

    /** DMRFTSetting登録項目　中国語 */
    private static final String ZH_CN = "zh_CN";

    /** 登録 */
    private static final String CREATE = "CREATE";

    /** 修正 */
    private static final String MODIFY = "MODIFY";

    /** 削除 */
    private static final String DELETE = "DELETE";

    /**
     * DMRFT・DMRFTSettingテーブル 初期値項目
     */
    private static final String[] INITIALVALUE = {
            "00",
            "0",
            "1",
            "2",
            ""
    };

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
    public RftAddSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
     *          該当レコードがみつかった場合は要素数0のリストを返します。<BR>
     *          該当レコードがみつからなかった場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new RftFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
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

    /**
     * 画面から入力された内容をパラメータとして受け取り、チェックを行います。<BR>
     *
     * @param param 入力パラメータ
     * @return 削除対象データがない場合はfalseを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams param)
            throws CommonException
    {
        if (machineNumberCheck(param))
        {
            // 6006007=すでに同一データが存在するため、登録できません。
            setMessage("6006007");
            return false;
        }
        return true;

    }
    
    /**
     * 画面から入力された内容をパラメータとして受け取り、チェックを行います。<BR>
     *
     * @param param 入力パラメータ
     * @return 削除対象データがない場合はfalseを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean checkrft(ScheduleParams param)
            throws CommonException
    {
        if (rftAssortCheck(param))
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return false;
        }
        return true;

    }
    /**
     * RFT起動中チェックを行います。
     * 
     * @param param 入力パラメータ
     * @return 削除対象号機が、起動中でない場合はtrueを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean rftStateCheck(ScheduleParams param)
            throws CommonException
    {
        RftHandler wRftHandler = new RftHandler(this.getConnection());
        RftSearchKey wRftSearchKey = new RftSearchKey();
        try
        {
            wRftSearchKey.setRftNo(param.getString(MACHINE_NUMBER));
            Rft[] entity = (Rft[])wRftHandler.find(wRftSearchKey);

            if (entity.length == 0)
            {
                // 6003014=削除対象データがありませんでした。
                setMessage("6003014");
                return false;
            }
            // RFTが起動中の場合は、falseを返します。
            else if (SystemDefine.RFT_STATUS_FLAG_START.equals(entity[0].getStatusFlag()))
            {
                return false;
            }
            // RFTが停止中の場合は、trueを返します。
            else if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(entity[0].getStatusFlag()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return false;
        }
    }


    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。<BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 処理前のチェックを行う。
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 登録の場合
        if (CREATE.equals(ps[0].get(BTN_DISTINCTION)))
        {
            // IPアドレス重複チェック
            if (!ipAdressCheck(ps[0]))
            {
                // 6006007=すでに同一データが存在するため、登録できません。
                setMessage("6006007");
                return false;
            }
            // 画面のRFT種別にて、HTを選択した時のDMRFTテーブルへの登録処理です。
            if (SystemDefine.TERMINAL_TYPE_HT.equals(ps[0].getString(RFT_ASSORT)))
            {
                registRft(ps[0]);
                registRftSetting(ps[0]);
                // 6001003=登録しました。
                setMessage("6001003");
            }
            // 画面のRFT種別にて、Pカートを選択した時のDMRFTテーブルへの登録処理です。
            else if (SystemDefine.TERMINAL_TYPE_PCART.equals(ps[0].getString(RFT_ASSORT)))
            {
                registRft(ps[0]);
                // 6001003=登録しました。
                setMessage("6001003");
            }
            // 画面のRFT種別にて、画像登録用HTを選択した時のDMRFTテーブルへの登録処理です。
            else if (SystemDefine.TERMINAL_TYPE_CAMERA_HT.equals(ps[0].getString(RFT_ASSORT)))
            {
                registRft(ps[0]);
                // 6001003=登録しました。
                setMessage("6001003");
            }
        }
        // 修正の場合
        else if (MODIFY.equals(ps[0].get(BTN_DISTINCTION)))
        {
            // RFTのロックを行います。
            Rft[] rft = lock(ps[0].getString(MACHINE_NUMBER), ps[0].getString(TERMINALTYPE));
            if (rft == null)
            {
                return false;
            }
            // 画面のRFT種別にて、HTを選択した時のDMRFTテーブルへの登録処理です。
            if (SystemDefine.TERMINAL_TYPE_HT.equals(ps[0].getString(TERMINALTYPE)))
            {
                modifyRft(ps[0]);
                modifyRftSetting(ps[0]);
                // 6001004=修正しました。
                setMessage("6001004");
            }
            // 画面のRFT種別にて、Pカートを選択した時のDMRFTテーブルへの登録処理です。
            else if (SystemDefine.TERMINAL_TYPE_PCART.equals(ps[0].getString(TERMINALTYPE)))
            {
                modifyRft(ps[0]);
                // 6001004=修正しました。
                setMessage("6001004");
            }
            // 画面のRFT種別にて、画像登録用HTを選択した時のDMRFTテーブルへの登録処理です。
            else if (SystemDefine.TERMINAL_TYPE_CAMERA_HT.equals(ps[0].getString(TERMINALTYPE)))
            {
                modifyRft(ps[0]);
                // 6001004=修正しました。
                setMessage("6001004");
            }
        }
        // 削除の場合
        else if (DELETE.equals(ps[0].get(BTN_DISTINCTION)))
        {
            // RFT起動中チェック
            if (!rftStateCheck(ps[0]))
            {
                // 6023608=RFTは既に起動中です。
                setMessage("6023695");
                return false;
            }
            // RFTのロックを行います。
            Rft[] rft = lock(ps[0].getString(MACHINE_NUMBER), ps[0].getString(TERMINALTYPE));
            if (rft == null)
            {
                return false;
            }
            // DMRFTテーブルの該当号機No情報を削除します。
            if (!deleteDmRft(this.getConnection(), ps[0]))
            {
                return false;
            }
            // 画面のRFT種別にて、HTを選択した時の処理です。
            if (SystemDefine.TERMINAL_TYPE_HT.equals(ps[0].getString(TERMINALTYPE)))
            {
                // DMRftSettingテーブルの該当号機No情報を削除します。
                if (!deleteDmRftSetting(this.getConnection(), ps[0]))
                {
                    return false;
                }
            }
            // 削除しました。
            setMessage("6001005");
        }
        return true;
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
     * 画面から入力されたパラメータからDB内に登録されている号機No.の検索を行います。
     * 
     * @param param 入力パラメータ
     * @return 同一商品が存在しない場合はfalse、存在した場合はtrueを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean machineNumberCheck(ScheduleParams param)
            throws CommonException
    {
        RftHandler wRftHandler = new RftHandler(this.getConnection());
        RftSearchKey wRftSearchKey = new RftSearchKey();
        wRftSearchKey.setRftNo(param.getString(MACHINE_NUMBER));
        try
        {
            if (wRftHandler.count(wRftSearchKey) > 0)
            {
                return true;
            }
            return false;
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return false;
        }
    }

    /**
     * 画面から入力されたパラメータからDB内に登録されている号機No.の検索を行います。
     * 
     * @param param 入力パラメータ
     * @return 同一商品が存在しない場合はfalse、存在した場合はtrueを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean rftAssortCheck(ScheduleParams param)
            throws CommonException
    {
        RftHandler wRftHandler = new RftHandler(this.getConnection());
        RftSearchKey wRftSearchKey = new RftSearchKey();
        wRftSearchKey.setRftNo(param.getString(MACHINE_NUMBER));
        wRftSearchKey.setTerminalType(param.getString(RFT_ASSORT));
        try
        {
            if (wRftHandler.count(wRftSearchKey) > 0)
            {
                return false;
            }
            return true;
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return false;
        }
    }
    
    /**
     * 画面から入力されたパラメータからDB内に登録されている号機No.の検索を行います。
     * 
     * @param param 入力パラメータ
     * @return 同一商品が存在しない場合はtrue、存在した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean ipAdressCheck(ScheduleParams param)
            throws CommonException
    {
        RftHandler wRftHandler = new RftHandler(this.getConnection());
        RftSearchKey wRftSearchKey = new RftSearchKey();
        wRftSearchKey.setIpAddress(param.getString(IP_ADDRESS));
        try
        {
            if (wRftHandler.count(wRftSearchKey) > 0)
            {
                return false;
            }
            return true;
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return false;
        }
    }

    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        RftSearchKey searchKey = new RftSearchKey();
        searchKey.setRftNo(p.getString(MACHINE_NUMBER));

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @param params 入力パラメータ
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder, ScheduleParams params)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Rft[] entities = (Rft[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Rft ent : entities)
        {
            Params param = new Params();
            // IPアドレス
            param.set(IP_ADDRESS, ent.getIpAddress());
            param.set(RFT_ASSORT, ent.getTerminalType());

            // 画面のRFT種別にて、HTを選択した時のDMRFTテーブルへの登録処理です。
            if (SystemDefine.TERMINAL_TYPE_HT.equals(params.getString(RFT_ASSORT)))
            {

                // 返却データをセットする
                RftSettingHandler handler = new RftSettingHandler(this.getConnection());
                RftSettingSearchKey searchKey = new RftSettingSearchKey();
                searchKey.setRftNo(params.getString(MACHINE_NUMBER));
                RftSetting[] e = (RftSetting[])handler.find(searchKey);
                for (RftSetting rftsetting : e)
                {
                    // 言語
                    if (RftSetting.KEY_LOCALE.equals(rftsetting.getKey()))
                    {
                        if (JA_JP.equals(rftsetting.getValue()))
                        {
                            param.set(LANGUAGE, "0");
                        }
                        else if (EN_US.equals(rftsetting.getValue()))
                        {
                            param.set(LANGUAGE, "1");
                        }
                        else if (ZH_CN.equals(rftsetting.getValue()))
                        {
                            param.set(LANGUAGE, "2");
                        }
                        else
                        {
                            throw new RuntimeException();
                        }
                    }
                    // 機種
                    if (RftSetting.KEY_TERMINAL_TYPE.equals(rftsetting.getKey()))
                    {
                        param.set(MODEL, rftsetting.getValue());
                    }
                    // TC入荷
                    if (RftSetting.KEY_TC_RECEIVING_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_TC_RECEIVING, changeboolean(rftsetting.getValue()));
                    }
                    // DC入荷
                    if (RftSetting.KEY_RECEIVING_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_DC_RECEIVING, changeboolean(rftsetting.getValue()));
                    }
                    // 入庫(入荷エリア)
                    if (RftSetting.KEY_RECEIVING_STORAGE_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_STORAGE_RECEIVING, changeboolean(rftsetting.getValue()));
                    }
                    // 入庫
                    if (RftSetting.KEY_STORAGE_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_STORAGE, changeboolean(rftsetting.getValue()));
                    }
                    // オーダ出庫
                    if (RftSetting.KEY_RETRIEVAL_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_ORDER_RETRIEVAL, changeboolean(rftsetting.getValue()));
                    }
                    // 仕分
                    if (RftSetting.KEY_SORTING_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_SORT, changeboolean(rftsetting.getValue()));
                    }
                    // 出荷検品(出荷先別)
                    if (RftSetting.KEY_SHIPPING_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_SHIPPING_PICK, changeboolean(rftsetting.getValue()));
                    }
                    // 出荷積込
                    if (RftSetting.KEY_SHIPPING_LOAD_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_SHIPPING_LOADING, changeboolean(rftsetting.getValue()));
                    }
                    // 予定外入庫
                    if (RftSetting.KEY_NOPLAN_STORAGE_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_NO_PLAN_STORAGE, changeboolean(rftsetting.getValue()));
                    }
                    // 予定外出庫
                    if (RftSetting.KEY_NOPLAN_RETRIEVAL_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_NO_PLAN_RETRIEVAL, changeboolean(rftsetting.getValue()));
                    }
                    // 棚卸
                    if (RftSetting.KEY_INVENTORY_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_INVENTRY, changeboolean(rftsetting.getValue()));
                    }
                    // 移動出庫
                    if (RftSetting.KEY_MOVE_RETRIEVAL_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_RELOCATINO_RETRIEV, changeboolean(rftsetting.getValue()));
                    }
                    // 移動入庫
                    if (RftSetting.KEY_MOVE_STORAGE_MENU.equals(rftsetting.getKey()))
                    {
                        param.set(WORK_KIND_RELOCATINO_STORAGE, changeboolean(rftsetting.getValue()));
                    }
                }
            }
            result.add(param);
        }

        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * DMRFTテーブルに登録処理を行うメソッドです。
     * 
     * @param params 入力パラメータ
     * @throws CommonException ユーザ定義の例外を通知します
     */
    private void registRft(ScheduleParams params)
            throws CommonException
    {
        try
        {
            Rft regRft = new Rft();
            RftHandler wRftHandler = new RftHandler(this.getConnection());
            regRft.setRftNo(params.getString(MACHINE_NUMBER));
            regRft.setJobType(INITIALVALUE[0]);
            regRft.setStatusFlag(INITIALVALUE[1]);
            regRft.setTerminalType(params.getString(RFT_ASSORT));
            regRft.setIpAddress(params.getString(IP_ADDRESS));
            regRft.setRadioFlag(INITIALVALUE[1]);
            regRft.setJobDetails(INITIALVALUE[1]);
            regRft.setRegistPname(this.getClass().getSimpleName());
            regRft.setLastUpdatePname(this.getClass().getSimpleName());
            wRftHandler.create(regRft);
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            throw e;
        }
    }

    /**
     * DMRFTテーブルに修正処理を行うメソッドです。
     * 
     * @param params 入力パラメータ
     * @throws CommonException ユーザ定義の例外を通知します
     */
    private void modifyRft(ScheduleParams params)
            throws CommonException
    {
        try
        {
            RftAlterKey regRft = new RftAlterKey();
            RftHandler wRftHandler = new RftHandler(this.getConnection());
            regRft.setRftNo(params.getString(MACHINE_NUMBER));
            regRft.updateJobType(INITIALVALUE[0]);
            regRft.updateStatusFlag(INITIALVALUE[1]);
            regRft.updateTerminalType(params.getString(RFT_ASSORT));
            regRft.updateIpAddress(params.getString(IP_ADDRESS));
            regRft.updateRadioFlag(INITIALVALUE[1]);
            regRft.updateJobDetails(INITIALVALUE[1]);
            regRft.updateRegistPname(this.getClass().getSimpleName());
            regRft.updateLastUpdatePname(this.getClass().getSimpleName());
            wRftHandler.modify(regRft);
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            throw e;
        }
    }

    /**
     * DMRFTSettingテーブルに登録処理を行うメソッドです。
     * 
     * @param params 入力パラメータ
     * @throws CommonException ユーザ定義の例外を通知します
     */
    private void registRftSetting(ScheduleParams params)
            throws CommonException
    {
        // RFT号機No
        String no = params.getString(MACHINE_NUMBER);
        try
        {
            // パラメータの言語情報を、テーブル登録情報に変換します。
            regtest(no, RftSetting.KEY_LOCALE, changeLocale(params.getInt(LANGUAGE)));

            // パスワードを登録します。
            regtest(no, RftSetting.KEY_PASSWORD_INPUT_MODE, String.valueOf(false));
            // TerminalTypeを登録します。
            regtest(no, RftSetting.KEY_TERMINAL_TYPE, params.getString(MODEL));

            // 入荷関係を登録します。
            // ReceivingConsignorを登録します。
            regtest(no, RftSetting.KEY_RECEIVING_CONSIGNOR, INITIALVALUE[4]);
            // ReceivingItfToJanを登録します。
            regtest(no, RftSetting.KEY_RECEIVING_ITFTOJAN, String.valueOf(false));
            // ReceivingCPModeを登録します。
            regtest(no, RftSetting.KEY_RECEIVING_CP_MODE, INITIALVALUE[3]);

            // 入庫関係を登録します。
            // StorageConsignorを登録します。
            regtest(no, RftSetting.KEY_STORAGE_CONSIGNOR, INITIALVALUE[4]);
            // StorageItfToJanを登録します。
            regtest(no, RftSetting.KEY_STORAGE_ITFTOJAN, String.valueOf(false));
            // StorageCPModeを登録します。
            regtest(no, RftSetting.KEY_STORAGE_CP_MODE, INITIALVALUE[3]);
            // StorageInspectionModeを登録します。
            regtest(no, RftSetting.KEY_STORAGE_INSPECTION_MODE, String.valueOf(false));

            // 出庫関係を登録します。
            // RetrievalConsignorを登録します。
            regtest(no, RftSetting.KEY_RETRIEVAL_CONSIGNOR, INITIALVALUE[4]);
            // RetrievalItfToJanを登録します。
            regtest(no, RftSetting.KEY_RETRIEVAL_ITFTOJAN, String.valueOf(false));
            // RetrievalCPModeを登録します。
            regtest(no, RftSetting.KEY_RETRIEVAL_CP_MODE, INITIALVALUE[3]);
            // RetrievalInspectionModeを登録します。
            regtest(no, RftSetting.KEY_RETRIEVAL_INSPECTION_MODE, String.valueOf(false));

            // 仕分関係を登録します。
            // SortingConsignorを登録します。
            regtest(no, RftSetting.KEY_SORTING_CONSIGNOR, INITIALVALUE[4]);
            // SortingItfToJanを登録します。
            regtest(no, RftSetting.KEY_SORTING_ITFTOJAN, String.valueOf(false));
            // SortingCPModeを登録します。
            regtest(no, RftSetting.KEY_SORTING_CP_MODE, INITIALVALUE[3]);
            // SortingInspectionModeを登録します。
            regtest(no, RftSetting.KEY_SORTING_INSPECTION_MODE, String.valueOf(false));

            // 出荷関係を登録します。
            // ShippingConsignorを登録します。
            regtest(no, RftSetting.KEY_SHIPPING_CONSIGNOR, INITIALVALUE[4]);
            // ShippingItfToJanを登録します。
            regtest(no, RftSetting.KEY_SHIPPING_ITFTOJAN, String.valueOf(false));
            // ShippingCPModeを登録します。
            regtest(no, RftSetting.KEY_SHIPPING_CP_MODE, INITIALVALUE[3]);

            // 移動出庫関係を登録します。
            // MoveRetrievalConsignorを登録します。
            regtest(no, RftSetting.KEY_MOVERETRIEVAL_CONSIGNOR, INITIALVALUE[4]);
            // MoveRetrievalItfToJanを登録します。
            regtest(no, RftSetting.KEY_MOVERETRIEVAL_ITFTOJAN, String.valueOf(false));
            // MoveRetrievalCPModeを登録します。
            regtest(no, RftSetting.KEY_MOVERETRIEVAL_CP_MODE, INITIALVALUE[3]);
            // MoveRetrievalInspectionModeを登録します。
            regtest(no, RftSetting.KEY_MOVERETRIEVAL_INSPECTION_MODE, String.valueOf(false));

            // 移動入庫関係を登録します。
            // MoveStorageConsignorを登録します。
            regtest(no, RftSetting.KEY_MOVESTORAGE_CONSIGNOR, INITIALVALUE[4]);
            // MoveStorageItfToJanを登録します。
            regtest(no, RftSetting.KEY_MOVESTORAGE_ITFTOJAN, String.valueOf(false));
            // MoveStorageCPModeを登録します。
            regtest(no, RftSetting.KEY_MOVESTORAGE_CP_MODE, INITIALVALUE[3]);
            // MoveStorageInspectionModeを登録します。
            regtest(no, RftSetting.KEY_MOVESTORAGE_INSPECTION_MODE, String.valueOf(false));

            // 棚卸関係を登録します。
            // InventoryConsignorを登録します。
            regtest(no, RftSetting.KEY_INVENTORY_CONSIGNOR, INITIALVALUE[4]);
            // InventoryItfToJanを登録します。
            regtest(no, RftSetting.KEY_INVENTORY_ITFTOJAN, String.valueOf(false));
            // InventoryCPModeを登録します。
            regtest(no, RftSetting.KEY_INVENTORY_CP_MODE, INITIALVALUE[3]);
            // InventoryInspectionModeを登録します。
            regtest(no, RftSetting.KEY_INVENTORY_INSPECTION_MODE, String.valueOf(false));

            // 予定外入庫関係を登録します。
            // NoPlanStorageConsignorを登録します。
            regtest(no, RftSetting.KEY_NOPLANSTORAGE_CONSIGNOR, INITIALVALUE[4]);
            // NoPlanStorageItfToJanを登録します。
            regtest(no, RftSetting.KEY_NOPLANSTORAGE_ITFTOJAN, String.valueOf(false));
            // NoPlanStorageCPModeを登録します。
            regtest(no, RftSetting.KEY_NOPLANSTORAGE_CP_MODE, INITIALVALUE[3]);
            // NoPlanStorageInspectionModeを登録します。
            regtest(no, RftSetting.KEY_NOPLANSTORAGE_INSPECTION_MODE, String.valueOf(false));

            // 予定外出庫関係を登録します。
            // NoPlanRetrievalConsignorを登録します。
            regtest(no, RftSetting.KEY_NOPLANRETRIEVAL_CONSIGNOR, INITIALVALUE[4]);
            // NoPlanRetrievalItfToJanを登録します。
            regtest(no, RftSetting.KEY_NOPLANRETRIEVAL_ITFTOJAN, String.valueOf(false));
            // NoPlanRetrievalCPModeを登録します。
            regtest(no, RftSetting.KEY_NOPLANRETRIEVAL_CP_MODE, INITIALVALUE[3]);
            // NoPlanRetrievalInspectionModeを登録します。
            regtest(no, RftSetting.KEY_NOPLANRETRIEVAL_INSPECTION_MODE, String.valueOf(false));

            // TC入荷メニューを登録します。
            regtest(no, RftSetting.KEY_TC_RECEIVING_MENU, changeString(params.getString(WORK_KIND_TC_RECEIVING)));
            // DC入荷メニューを登録します。
            regtest(no, RftSetting.KEY_RECEIVING_MENU, changeString(params.getString(WORK_KIND_DC_RECEIVING)));
            // 入庫(入荷エリア)メニューを登録します。 
            regtest(no, RftSetting.KEY_RECEIVING_STORAGE_MENU,
                    changeString(params.getString(WORK_KIND_STORAGE_RECEIVING)));
            // 入庫メニューを登録します。         
            regtest(no, RftSetting.KEY_STORAGE_MENU, changeString(params.getString(WORK_KIND_STORAGE)));
            // オーダー出庫メニューを登録します。         
            regtest(no, RftSetting.KEY_RETRIEVAL_MENU, changeString(params.getString(WORK_KIND_ORDER_RETRIEVAL)));
            // 仕分メニューを登録します。         
            regtest(no, RftSetting.KEY_SORTING_MENU, changeString(params.getString(WORK_KIND_SORT)));
            // 出荷検品(出荷先別)メニューを登録します。        
            regtest(no, RftSetting.KEY_SHIPPING_MENU, changeString(params.getString(WORK_KIND_SHIPPING_PICK)));
            // 出荷積込メニューを登録します。         
            regtest(no, RftSetting.KEY_SHIPPING_LOAD_MENU, changeString(params.getString(WORK_KIND_SHIPPING_LOADING)));
            // 予定外入庫メニューを登録します。         
            regtest(no, RftSetting.KEY_NOPLAN_STORAGE_MENU, changeString(params.getString(WORK_KIND_NO_PLAN_STORAGE)));
            // 予定外出庫メニューを登録します。         
            regtest(no, RftSetting.KEY_NOPLAN_RETRIEVAL_MENU,
                    changeString(params.getString(WORK_KIND_NO_PLAN_RETRIEVAL)));
            // 棚卸メニューを登録します。
            regtest(no, RftSetting.KEY_INVENTORY_MENU, changeString(params.getString(WORK_KIND_INVENTRY)));
            // 移動出庫メニューを登録します。
            regtest(no, RftSetting.KEY_MOVE_RETRIEVAL_MENU,
                    changeString(params.getString(WORK_KIND_RELOCATINO_RETRIEV)));
            // 移動入庫メニューを登録します。
            regtest(no, RftSetting.KEY_MOVE_STORAGE_MENU, changeString(params.getString(WORK_KIND_RELOCATINO_STORAGE)));
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            throw e;
        }
    }

    /**
     * DMRFTSettingテーブルに修正処理を行うメソッドです。
     * 
     * @param params 入力パラメータ
     * @throws CommonException ユーザ定義の例外を通知します
     */
    private void modifyRftSetting(ScheduleParams params)
            throws CommonException
    {
        // RFT号機No
        String no = params.getString(MACHINE_NUMBER);
        try
        {
            // パラメータの言語情報を、テーブル登録情報に変換します。
            modtest(no, RftSetting.KEY_LOCALE, changeLocale(params.getInt(LANGUAGE)));
            // TerminalTypeを登録します。
            modtest(no, RftSetting.KEY_TERMINAL_TYPE, params.getString(MODEL));

            // TC入荷メニューを登録します。
            modtest(no, RftSetting.KEY_TC_RECEIVING_MENU, changeString(params.getString(WORK_KIND_TC_RECEIVING)));
            // DC入荷メニューを登録します。
            modtest(no, RftSetting.KEY_RECEIVING_MENU, changeString(params.getString(WORK_KIND_DC_RECEIVING)));
            // 入庫(入荷エリア)メニューを登録します。 
            modtest(no, RftSetting.KEY_RECEIVING_STORAGE_MENU,
                    changeString(params.getString(WORK_KIND_STORAGE_RECEIVING)));
            // 入庫メニューを登録します。         
            modtest(no, RftSetting.KEY_STORAGE_MENU, changeString(params.getString(WORK_KIND_STORAGE)));
            // オーダー出庫メニューを登録します。         
            modtest(no, RftSetting.KEY_RETRIEVAL_MENU, changeString(params.getString(WORK_KIND_ORDER_RETRIEVAL)));
            // 仕分メニューを登録します。         
            modtest(no, RftSetting.KEY_SORTING_MENU, changeString(params.getString(WORK_KIND_SORT)));
            // 出荷検品(出荷先別)メニューを登録します。        
            modtest(no, RftSetting.KEY_SHIPPING_MENU, changeString(params.getString(WORK_KIND_SHIPPING_PICK)));
            // 出荷積込メニューを登録します。         
            modtest(no, RftSetting.KEY_SHIPPING_LOAD_MENU, changeString(params.getString(WORK_KIND_SHIPPING_LOADING)));
            // 予定外入庫メニューを登録します。         
            modtest(no, RftSetting.KEY_NOPLAN_STORAGE_MENU, changeString(params.getString(WORK_KIND_NO_PLAN_STORAGE)));
            // 予定外出庫メニューを登録します。         
            modtest(no, RftSetting.KEY_NOPLAN_RETRIEVAL_MENU,
                    changeString(params.getString(WORK_KIND_NO_PLAN_RETRIEVAL)));
            // 棚卸メニューを登録します。
            modtest(no, RftSetting.KEY_INVENTORY_MENU, changeString(params.getString(WORK_KIND_INVENTRY)));
            // 移動出庫メニューを登録します。
            modtest(no, RftSetting.KEY_MOVE_RETRIEVAL_MENU,
                    changeString(params.getString(WORK_KIND_RELOCATINO_RETRIEV)));
            // 移動入庫メニューを登録します。
            modtest(no, RftSetting.KEY_MOVE_STORAGE_MENU, changeString(params.getString(WORK_KIND_RELOCATINO_STORAGE)));

        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            throw e;
        }
    }

    /**
     * 登録処理を行うメソッドです。
     * 
     * @param no 号機No.
     * @param key DB登録フィールド名
     * @param value DB登録内容
     * @throws CommonException ユーザ定義の例外を通知します
     */
    private void regtest(String no, String key, String value)
            throws CommonException
    {
        try
        {
            RftSettingHandler wRftHandler = new RftSettingHandler(this.getConnection());

            RftSetting regRftSetting = new RftSetting();
            regRftSetting.setRftNo(no);
            regRftSetting.setKey(key);
            regRftSetting.setValue(value);
            regRftSetting.setLastUpdatePname(this.getClass().getSimpleName());
            regRftSetting.setRegistPname(this.getClass().getSimpleName());
            wRftHandler.create(regRftSetting);
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            throw e;
        }
    }

    /**
     * 修正処理を行うメソッドです。
     * 
     * @param no 号機No.
     * @param key DB登録フィールド名
     * @param value DB登録内容
     * @throws CommonException ユーザ定義の例外を通知します
     */
    private void modtest(String no, String key, String value)
            throws CommonException
    {
        try
        {
            RftSettingHandler wRftHandler = new RftSettingHandler(this.getConnection());

            RftSettingAlterKey regRftSetting = new RftSettingAlterKey();
            regRftSetting.setRftNo(no);
            regRftSetting.setKey(key);
            regRftSetting.updateValue(value);
            regRftSetting.setLastUpdatePname(this.getClass().getSimpleName());
            regRftSetting.setRegistPname(this.getClass().getSimpleName());
            wRftHandler.modify(regRftSetting);
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            throw e;
        }
    }

    /**
     * 入力パラメータのtrueを0。
     * falseを1に変換するメソッドです。
     * 
     * @param param 入力パラメータ
     * @return result trueなら0。falseなら1を返します。
     */
    private String changeString(String param)
    {
        String result = null;
        if (param.equals("true"))
        {
            result = INITIALVALUE[2];
            return result;
        }
        else
        {
            result = INITIALVALUE[1];
            return result;
        }
    }

    /**
     * 画面入力パラメータをDB登録用の内容に変換します。
     * 
     * @param param 入力パラメータ
     * @return DB入力内容を渡します。
     */
    private String changeLocale(int param)
    {
        if (param == 00)
        {
            return JA_JP;
        }
        else if (param == 01)
        {
            return EN_US;
        }
        else if (param == 02)
        {
            return ZH_CN;
        }
        return null;
    }

    /**
     * DB検索結果の0,1をtrueもしくはfalseに変換します。
     * 
     * @param flag DB検索結果
     * @return trueもしくはfalseを返します。
     */
    private boolean changeboolean(String flag)
    {
        if ("0".equals(flag))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * パラメータでわたされた、端末No.をキーに、DMRft, DMRftSettingをロックします。<BR>
     * また、他端末から更新・ロックされていないかをチェックします。<BR>
     * 
     * @param rftNo 号機No.
     * @param terminalType RFT種別
     * @return true:ロック成功、false:ロック失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private Rft[] lock(String rftNo, String terminalType)
            throws CommonException
    {
        Rft[] rft = null;

        try
        {
            // DMRFTをロック
            RftHandler rftHand = new RftHandler(getConnection());
            RftSearchKey rftKey = new RftSearchKey();

            if (SystemDefine.TERMINAL_TYPE_HT.equals(terminalType))
            {
                if (!WmsParam.ALL_RFT_NO.equals(rftNo))
                {
                    rftKey.setRftNo(rftNo);
                    rftKey.setJoin(Rft.RFT_NO, RftSetting.RFT_NO);
                }
                rftKey.setTerminalType(SystemDefine.TERMINAL_TYPE_HT);

                rftKey.setRftNoCollect();
                rftKey.setJobTypeCollect();
                rftKey.setRftNoGroup();

                rft = (Rft[])rftHand.findForUpdate(rftKey, RftHandler.WAIT_SEC_NOWAIT);
            }
            else
            {
                if (!WmsParam.ALL_RFT_NO.equals(rftNo))
                {
                    rftKey.setRftNo(rftNo);
                }

                rftKey.setRftNoCollect();
                rftKey.setJobTypeCollect();

                rft = (Rft[])rftHand.findForUpdate(rftKey, RftHandler.WAIT_SEC_NOWAIT);
            }
            if (rft.length == 0)
            {
                // 6006038=対象データはありませんでした。
                setMessage("6003011");
                return null;
            }
        }
        catch (LockTimeOutException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return null;
        }

        return rft;

    }

    /**
     * DMRFTテーブルの該当号機No.の削除処理を行います。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param params 入力パラメータ
     * @throws CommonException ユーザ定義の例外を通知します
     * @return 削除処理が行われたらtrueを返します。
     */
    private boolean deleteDmRft(Connection conn, ScheduleParams params)
            throws CommonException
    {
        RftHandler wRftHandler = new RftHandler(conn);
        RftSearchKey wRftSearchKey = new RftSearchKey();
        try
        {
            wRftSearchKey.setRftNo(params.getString(MACHINE_NUMBER));

            if (wRftHandler.count(wRftSearchKey) > 0)
            {
                wRftHandler.drop(wRftSearchKey);
                return true;
            }
            // 6126020=削除できませんでした。
            setMessage("6126020");
            return false;
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return false;
        }
    }

    /**
     * DMRFTSettingテーブルの該当号機No.の削除処理を行います。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param params 入力パラメータ
     * @throws CommonException ユーザ定義の例外を通知します
     * @return 削除処理が行われたらtrueを返します。
     */
    private boolean deleteDmRftSetting(Connection conn, ScheduleParams params)
            throws CommonException
    {
        RftSettingHandler wRftSettingHandler = new RftSettingHandler(conn);
        RftSettingSearchKey wRftSettingSearchKey = new RftSettingSearchKey();
        try
        {
            wRftSettingSearchKey.setRftNo(params.getString(MACHINE_NUMBER));

            if (wRftSettingHandler.count(wRftSettingSearchKey) > 0)
            {
                wRftSettingHandler.drop(wRftSettingSearchKey);
                return true;
            }
            // 6126020=削除できませんでした。
            setMessage("6126020");
            return false;
        }
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return false;
        }
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
