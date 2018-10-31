// $Id: PCTItemDelete3SCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTItemDelete2SCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.pcart.base.util.DBUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTSystem;

/**
 * 商品マスタ削除(SAVE)のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date:: 2009-05-12 14:33:55 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTItemDelete3SCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * エクスポーターアプリケーションを指定(パスが通ってない場合はフルパス指定すること)
     */
    private static final String EXPORTER = "exp";

    /**
     * DBユーザー
     */
    private static final String USER = "wms";

    /**
     * DBユーザーのパスワード
     */
    private static final String PASSWORD = "wms";

    /**
     * SID
     */
    private static final String SID = "wms";

    /**
     * 出力先ログファイル
     */
    private static final String LOGFILENAME = "c:\\test.log";

    /**
     * 出力テーブル名（複数指定はカンマ区切り）
     */
    private static final String TABLES_TEMP = "DMPCTITEM";


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
    public PCTItemDelete3SCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * スケジュールを開始します。
     * PCTシステム情報の商品マスタ取込みフラグを更新します。
     * 正しく更新できた場合はTrueを、更新できなかった場合はメッセージをセットしFalseを返します。
     * @param startParams 更新内容
     * @return 正しく更新できた場合はTrueを、更新できなかった場合はメッセージをセットしFalseを返します。
     * @throws CommonException 発生した全ての例外を通知します。
     */
    public boolean startSCH(PCTItemDelete2SCHParams startParams)
            throws CommonException
    {
        boolean saveFlag = false;
        boolean errorFlag = true;

        try
        {
            // PCT商品マスタ取込フラグをSAVE中に更新
            if (updatePCTMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_SAVE))
            {
                doCommit(this.getClass());
            }
            else
            {
                return false;
            }

            saveFlag = true;

            // EXPORT
            if (DBUtil.DBCmd(EXPORTER, USER, PASSWORD, SID, TABLES_TEMP, startParams.getString(DATA_FILE_FOLDER)
                    + startParams.getString(DATA_FILE_NAME), LOGFILENAME) != 0)

            {
                return false;
            }

            errorFlag = false;

            // 6001025=セーブ処理が完了しました。
            setMessage(WmsMessageFormatter.format(6001025));
            return true;
        }
        finally
        {
            // 正常に処理が行われなかった場合はrollback
            if (errorFlag)
            {
                doRollBack(this.getClass());
            }

            // PCT商品マスタ取込フラグがこのクラスから更新されたか判断
            if (saveFlag)
            {
                // PCT商品マスタ取込フラグを停止中に更新
                if (updatePCTMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_STOP))
                {
                    doCommit(this.getClass());
                }
                else
                {
                    return false;
                }
            }
        }
    }

    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @param searchParam
     *            本メソッドでは使用しないのでnullをセットしてください。
     * @return 結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    public PCTMasterOutParameter initFind(Parameter searchParam)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {

        // 出力パラメータ
        PCTMasterOutParameter outParam = new PCTMasterOutParameter();

        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_SAVE.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024069=セーブ処理中のため作業できません。
                setMessage(WmsMessageFormatter.format(6024069));
                outParam.setPctMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_SAVE);
                return outParam;
            }

            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため作業できません。
                setMessage(WmsMessageFormatter.format(6024068));
                outParam.setPctMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_LOAD);
                return outParam;
            }
        }
        return null;

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
     * 報告データ作成中フラグを停止中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean updatePCTMasterLoadFlag(String flag)
            throws CommonException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemAlterKey wAKey = new PCTSystemAlterKey();

        try
        {
            // PCT商品マスタ取込フラグを更新
            wAKey.updatePctmasterLoadFlag(flag);
            wAKey.updateLastUpdatePname(this.getClass().getSimpleName());

            wHandler.modify(wAKey);

            return true;
        }
        catch (NotFoundException e)
        {
            return false;
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
