// $Id: HaiSurfExceptionHandler.java 4656 2009-07-14 05:07:28Z kishimoto $
package jp.co.daifuku.wms.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.wms.base.communication.rft.AnswerCode;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.base.util.RftUtil;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.busitune.rft.haisurf.HSResult;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 * ID処理用 例外ハンドラ.<br>
 * 例外に対応した、処理結果、メッセージを返すメソッドを実装します。<br>
 * 各IDで共通の例外を扱います。
 *
 * @version $Revision: 4656 $, $Date: 2009-07-14 14:07:28 +0900 (火, 14 7 2009) $
 * @author  taki
 * @author  Last commit: $Author: kishimoto $
 * @since 2008-03-28 フラグの位置定義を追加
 */

public final class HaiSurfExceptionHandler
        implements AnswerCode
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
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    private HaiSurfExceptionHandler()
    {
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------

    /** アクション結果取得処理
     *  例外に対応した、処理結果を返します。
     *  例外に対応した、エラーメッセージ番号を設定します。
     *  
     * @param e 発生した例外クラス
     * @param caller 呼び出し元クラス
     * @param dsf DataSheetFactoryクラス
     * @param locale Locale
     * @return HSResult
     */
    public static HSResult getResult(Exception e, Class caller, DataSheetFactory dsf, Locale locale)
    {
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        Object[] rftArr = {
                termInfoDataSheet.getString(TermInfoDataSheet.TERMINAL_NO)
        };

        if (e instanceof DataExistsException)
        {
            // 情報を登録しようとした際に、既に同じ情報が登録済みの場合
            // 6046001=<{0}号機> データベースに情報を登録しようとした際に、既に同じ情報が登録済みです。{1}
            RmiMsgLogClient.write(new TraceHandler(6046001, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.DATA_EXISTS, new String[]{"6047001", "6047012"});
        }
        else if (e instanceof InvalidDefineException)
        {
            // 6046002=<{0}号機> 定義情報（指定したパラメータの値）が異常です。{1}
            RmiMsgLogClient.write(new TraceHandler(6046002, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.PATTERN_NG, new String[]{"6047001", "6047010"});
        }
        else if (e instanceof LockTimeOutException)
        {
            // 時間内にデータのロックが解除されなかった場合
            // 6046003=<{0}号機> データベースの対象データがロックされており、情報の取得や更新ができません。{1}
            RmiMsgLogClient.write(new TraceHandler(6046003, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.MAINTENANCE, new String[]{"6043005", "6043008"});
        }
        else if (e instanceof NoPrimaryException)
        {
            // 複数件該当するはずの無い問合せで、複数件該当した場合
            // 6046004=<{0}号機> ID対応処理例外。複数件該当するはずのないデータベース検索で、複数件該当しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6046004, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.PATTERN_NG, new String[]{"6047001", "6047010"});
        }
        else if (e instanceof ReadWriteException)
        {
            // データベースまたは、ファイル操作で異常が発生した場合
            // (スタックトレースは、発生元で出力しているので、ここではメッセージのみ出力する。)
            // 6046005=<{0}号機> データベースエラーが発生しました。詳細は直前のログを参照してください。
            RmiMsgLogClient.write(6046005, caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.DB_ACCESS_ERROR, new String[]{"6047001", "6047011"});
        }
        else if (e instanceof ScheduleException)
        {
            // スケジュール処理の実行中に予期しない例外が発生した場合
            // 6046006=<{0}号機> ID対応処理例外。スケジュール処理の実行中に予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6046006, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.SCHEDULE_ERROR, new String[]{"6047001", "6047016"});
        }
        else if (e instanceof ValidateException)
        {
            // DB設定値不正
            // 6046002=<{0}号機> 定義情報（指定したパラメータの値）が異常です。{1}
            RmiMsgLogClient.write(new TraceHandler(6046002, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.PATTERN_NG, new String[]{"6047001", "6047015"});
        }
        else if (e instanceof DuplicateOperatorException)
        {
            DuplicateOperatorException opEx = (DuplicateOperatorException)e;
            String errCode = opEx.getErrorCode();
            
            // 予定日重複
            if (OperatorException.ERR_PLAN_DAY_DUPLICATED.equals(errCode))
            {
                // 複数の予定日が該当しました。/作業する商品の予定日を選択してください。
                return new HSResult(ResultConst.PLAN_DAY_DUPLICATED, new String[]{"6041005", "6041008"});
            }
            // 仕入先重複
            else if (OperatorException.ERR_SUPPLIER_DUPLICATED.equals(errCode))
            {
                // 複数の仕入先で該当しました。/作業する商品の仕入先を選択してください。
                return new HSResult(ResultConst.SUPPLIER_DUPLICATED, new String[]{"6041007", "6041009"});
            }
            // ロットNo.重複
            else if (OperatorException.ERR_LOT_NO_DUPLICATED.equals(errCode))
            {
                // 複数のロットNo.が該当しました。/作業する商品のロットNo.を選択してください。
                return new HSResult(ResultConst.LOT_NO_DUPLICATED, new String[]{"6041006", "6041010"});
            }
            // 商品コード重複
            else if (OperatorException.ERR_ITEM_DUPLICATED.equals(errCode))
            {
                // 商品コードが複数件該当しました。/商品マスタを確認してください。
                return new HSResult(ResultConst.ITEM_DUPLICATED, new String[]{"6043019", "6043020"});
            }
            else
            {
                // 予期しない例外が発生した場合
                // 6046009=<{0}号機> 予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6046009, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.ERROR, new String[]{"6047001", "6047009"});
            }
        }
        else if (e instanceof OperatorException)
        {
            OperatorException opeEx = (OperatorException)e;
            String errCode = opeEx.getErrorCode();
            if (OperatorException.ERR_ALREADY_UPDATED.equals(errCode))
            {
                // 更新対象データが、既に他で更新されていた場合
                // 6046007=<{0}号機> 更新対象データが他で更新されました。RFT状態メンテナンスで更新された可能性があります。{1}
                RmiMsgLogClient.write(new TraceHandler(6046007, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.UPDATE_FINISH, new String[]{"6047001", "6047029"});
            }
            else if (OperatorException.ERR_OVERFLOW.equals(errCode))
            {
                // オーバーフローが発生した場合
                // 6046008=<{0}号機> オーバーフローが発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6046008, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.RESULTQTY_OVER, new String[]{"6047001", "6047030"});
            }
            else if (OperatorException.ERR_WORKING_INPROGRESS.equals(errCode))
            {
                // この作業データは、他端末で作業中です。
                return new HSResult(ResultConst.WORKING_INPROGRESS, new String[]{"6043002"});
            }
            else if (OperatorException.ERR_ALREADY_COMPLETED.equals(errCode))
            {
                // この作業データは、既に完了しています。
                return new HSResult(ResultConst.ALREADY_COMPLETED, new String[]{"6043003"});
            }
            else if (OperatorException.ERR_NO_AREA_FOUND.equals(errCode))
            {
                // エリア無し
                return new HSResult(ResultConst.AREA_INVALIDITY, new String[]{"6047032"});
            }
            else if (OperatorException.ERR_ASRS_AREA_FOUND.equals(errCode))
            {
                // ASRSエリア
                // エリア無し
                return new HSResult(ResultConst.AREA_INVALIDITY, new String[]{"6047032"});
            }
            else if (OperatorException.ERR_NO_LOCATION_FOUND.equals(errCode))
            {
                // 棚未登録
                return new HSResult(ResultConst.SHELF_UNREGISTRATION);
            }
            else if (OperatorException.ERR_FIXED_ITEM_LOCATION_FOUND.equals(errCode))
            {
                // 固定棚未登録
                return new HSResult(ResultConst.FIXED_SHELF_UNREGISTRATION);
            }
            else if (OperatorException.ERR_ILLEGAL_LOCATION_FORMAT.equals(errCode))
            {
                // 棚フォーマットエラー
                // 入力された棚No.は無効です
                return new HSResult(ResultConst.ILLEGAL_LOCATION_FORMAT, new String[]{"6047031"});
            }
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(errCode))
            {
                // 出庫可能数エラー
                if (Integer.valueOf(opeEx.getDetailString()) == 0)
                {
                    // 更新対象データが、既に他で更新されていた場合
                    // 6046007=<{0}号機> 更新対象データが他で更新されました。RFT状態メンテナンスで更新された可能性があります。{1}
                    RmiMsgLogClient.write(new TraceHandler(6046007, e), caller.getSimpleName(), rftArr);
                    return new HSResult(ResultConst.UPDATE_FINISH, new String[]{"6047001", "6045001"});
                }
                else
                {
                    // 出庫可能数超過
                    String param[] = new String[1];
                    param[0] = opeEx.getDetailString();
                    String msg = MessageResource.getMessage("6045003", param, locale);
                    // 6046008=<{0}号機> オーバーフローが発生しました。{1}
                    RmiMsgLogClient.write(new TraceHandler(6046008, e), caller.getSimpleName(), rftArr);
                    return new HSResult(ResultConst.RESULTQTY_OVER, new String[]{"6045002", msg});
                }
            }
            else
            {
                // 予期しない例外が発生した場合
                // 6046009=<{0}号機> 予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6046009, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.ERROR, new String[]{"6047001", "6047009"});
            }
        }
        else if (e instanceof IdSchException)
        {
            IdSchException idSchEx = (IdSchException)e;
            String errCode = idSchEx.getErrorCode();
            
            // IPアドレス不正
            if (IdSchException.ILLEGAL_IP_ADDRESS.equals(errCode))
            {
                // 6046114=指定されたIPアドレスが見つかりません。 IPアドレス={0}
                RmiMsgLogClient.write(new TraceHandler(6046114, e), caller.getSimpleName());
                return new HSResult(ResultConst.ILLEGAL_IP_ADDRESS, new String[]{"6047002"});
            }

            // 起動時起動受信
            if (IdSchException.ALREADY_STARTED.equals(errCode))
            {
                return new HSResult(ResultConst.NORMAL);
            }
            // 未起動時終了受信
            else if (IdSchException.ALREADY_STOPPED.equals(errCode))
            {
                return new HSResult(ResultConst.NORMAL);
            }
            // 休憩時休憩受信
            else if (IdSchException.ALREADY_RESTED.equals(errCode))
            {
                return new HSResult(ResultConst.NORMAL);
            }
            // 未休憩時再開受信
            else if (IdSchException.ALREADY_RESTARTED.equals(errCode))
            {
                // サーバ処理でエラー発生。/[他端末で更新済み]
                return new HSResult(ResultConst.ALREADY_RESTARTED, new String[]{"6047001", "6047029"});
            }
            // ユーザ認証失敗（認証の設定が不正）
            else if (IdSchException.Authentication.SETTING_ERROR.equals(errCode))
            {
                // ユーザ名又はパスワードが不正です。/[認証の設定が不正です。]
                return new HSResult(ResultConst.USER_SETTING_ERROR, new String[]{"6043013", "6047021"});
            }
            // ユーザ認証失敗（仮パスワード）
            else if (IdSchException.Authentication.PASSWORD_TENTATIVE.equals(errCode))
            {
                // ユーザ名又はパスワードが不正です。/[仮パスワードです。]
                return new HSResult(ResultConst.PASSWORD_TENTATIVE, new String[]{"6043013", "6047026"});
            }
            // ユーザ認証失敗（ユーザ又はパスワード不正）
            else if (IdSchException.Authentication.AUTH_ERR_USERPASS.equals(errCode))
            {
                // ユーザ名又はパスワードが不正です。
                return new HSResult(ResultConst.AUTH_ERR_USERPASS, new String[]{"6043013"});
            }
            // ユーザ認証失敗（ロックされたユーザ）
            else if (IdSchException.Authentication.AUTH_ERR_USERLOCK.equals(errCode))
            {
                // ユーザ名又はパスワードが不正です。/[ロックされたユーザです。]
                return new HSResult(ResultConst.AUTH_ERR_USERLOCK, new String[]{"6043013", "6047023"});
            }
            // ユーザ認証失敗（パスワード期限切れ）
            else if (IdSchException.Authentication.AUTH_ERR_PWDEXPIRE.equals(errCode))
            {
                // ユーザ名又はパスワードが不正です。/[パスワード期限切れ]
                return new HSResult(ResultConst.AUTH_ERR_PWDEXPIRE, new String[]{"6043013", "6047024"});
            }
            // ユーザ認証失敗（権限不足）
            else if (IdSchException.Authentication.AUTH_ERR_USERPERMISSION.equals(errCode))
            {
                // ユーザ名又はパスワードが不正です。/[権限不足です。]
                return new HSResult(ResultConst.AUTH_ERR_USERPERMISSION, new String[]{"6043013", "6047025"});
            }
            // 作業者は他端末で作業中
            else if (IdSchException.StartError.USER_ALREADY_WORK.equals(errCode))
            {
                // 該当する作業者は別端末で作業中です。
                return new HSResult(ResultConst.USER_ALREADY_WORK, new String[]{"6043014"});
            }
            // 端末は別ユーザログイン中
            else if (IdSchException.StartError.TERMINAL_ALREADY_LOGIN.equals(errCode))
            {
                // この端末は別作業者により作業中の状態です。
                return new HSResult(ResultConst.USER_ALREADY_WORK, new String[]{"6043015"});
            }
            // 作業中データあり
            else if (IdSchException.WORKING_DATA_EXISTS.equals(errCode))
            {
                commonDataSheet.setValue(CommonDataSheet.USER_NAME, idSchEx.getDetailString());
                // 強制中断されたデータがあります。この作業を再開しますか？
                return new HSResult(ResultConst.INCOMPLETE_WORK_OWN, new String[]{"6041013"});
            }
            // 作業中データあり(別作業)
            else if (IdSchException.OTHER_WORKING_DATA_EXISTS.equals(errCode))
            {
                List<Object> detail = idSchEx.getDetailList();
                commonDataSheet.setValue(CommonDataSheet.USER_NAME, detail.get(0));
                String workingName = RftUtil.getWorkingName(String.valueOf(detail.get(1)), locale);
                // 別作業で強制中断されたデータがあり続行できません。
                return new HSResult(ResultConst.INCOMPLETE_WORK_OTHER, new String[]{"6041014", workingName});
            }
            // 号機No不正
            else if (IdSchException.ILLEGAL_TERMINAL_NUMBER.equals(errCode))
            {
                // サーバ処理でエラー発生。/[号機番号不正です。]
                // 6046115=指定された端末No.が見つかりません。端末No.={0}
                RmiMsgLogClient.write(new TraceHandler(6046115, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.ILLEGAL_TERMINAL_NUMBER, new String[]{"6047001", "6047018"});
            }
            else if (IdSchException.PATTERN_NG.equals(errCode))
            {
                // パラメータ内に禁止文字が含まれている場合
                // 6046010=<{0}号機> 入力された値に禁止文字が含まれています。{1}
                RmiMsgLogClient.write(new TraceHandler(6046010, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.PATTERN_NG, new String[]{"6047001", "6047010"});
            }
            else if (IdSchException.DAILY_UPDATING.equals(errCode))
            {
                // 日次更新中の場合
                return new HSResult(ResultConst.DAILY_UPDATING, new String[]{"6043004", "6043008"});
            }
            else if (IdSchException.RESULT_QTY_NG.equals(errCode))
            {
                // 実績数オーバー
                // オーバーフロー/1件の作業予定の実績数の合計が在庫可能数を超えています。
                return new HSResult(ResultConst.RESULTQTY_OVER, new String[]{"6043024", "6043025"});
            }
            else if (IdSchException.HAVE_NO_STOCKPACK.equals(errCode))
            {
                // 在庫管理パッケージなし
                return new HSResult(ResultConst.HAVE_NO_STOCKPACK, new String[]{"6043017"});
            }
            // ロットNo.重複
            else if (IdSchException.ERR_LOT_NO_DUPLICATED.equals(errCode))
            {
                // 複数のロットNo.が該当しました。/作業する商品のロットNo.を選択してください。
                return new HSResult(ResultConst.LOT_NO_DUPLICATED, new String[]{"6041006", "6041010"});
            }
            // エリアNo.重複
            else if (IdSchException.ERR_AREA_NO_DUPLICATED.equals(errCode))
            {
                // 複数のエリアが該当しました。/作業する商品のエリアを選択してください。
                return new HSResult(ResultConst.AREA_NO_DUPLICATED, new String[]{"6041018", "6041019"});
            }
            else if (IdSchException.INSTACIATE_ERROR.equals(errCode))
            {
                // クラス生成エラー発生した場合
                // 6046113=<{0}号機> 処理されない例外が発生しました。クラスのインスタンス生成に失敗した可能性があります。{1}
                RmiMsgLogClient.write(new TraceHandler(6046113, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.INSTACIATE_ERROR, new String[]{"6047001", "6047013"});
            }
            else
            {
                // 予期しない例外が発生した場合
                // 6046009=<{0}号機> 予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6046009, e), caller.getSimpleName(), rftArr);
                return new HSResult(ResultConst.ERROR, new String[]{"6047001", "6047009"});
            }
        }
        else if (e instanceof EmAuthenticationException)
        {
            EmAuthenticationException ex = (EmAuthenticationException)e;
            int errCode = ex.getErrorCode();
            
            switch (errCode)
            {
                // ユーザ認証失敗（ユーザ又はパスワード不正）
                case EmAuthenticationException.AUTH_ERR_USERPASS:
                    // ユーザ名又はパスワードが不正です。
                    return new HSResult(ResultConst.AUTH_ERR_USERPASS, new String[]{"6043013"});
                    
                // ユーザ認証失敗（ロックされたユーザ）
                case EmAuthenticationException.AUTH_ERR_USERLOCK:
                    // ユーザ名又はパスワードが不正です。/[ロックされたユーザです。]
                    return new HSResult(ResultConst.AUTH_ERR_USERLOCK, new String[]{"6043013", "6047023"});
                    
                // ユーザ認証失敗（パスワード期限切れ）
                case EmAuthenticationException.AUTH_ERR_PWDEXPIRE:
                    // ユーザ名又はパスワードが不正です。/[パスワード期限切れ]
                    return new HSResult(ResultConst.AUTH_ERR_PWDEXPIRE, new String[]{"6043013", "6047024"});
                    
                // ユーザ認証失敗（権限不足）
                case EmAuthenticationException.AUTH_ERR_USERPERMISSION:
                    // ユーザ名又はパスワードが不正です。/[権限不足です。]
                    return new HSResult(ResultConst.AUTH_ERR_USERPERMISSION, new String[]{"6043013", "6047025"});
                    
                // その他
                default:
                    // 6026015=<{0}号機> 予期しない例外が発生しました。{1}
                    RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
                    return new HSResult(ResultConst.ERROR, new String[]{"6047001", "6047009"});
            }
        }
        else if (e instanceof NotFoundException)
        {
            return new HSResult(ResultConst.NODATA);
        }
        else if (e instanceof CommonException)
        {
            // 予期しない例外が発生した場合
            // 6046009=<{0}号機> 予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6046009, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.ERROR, new String[]{"6047001", "6047009"});
        }
        else if (e instanceof SQLException)
        {
            // データベースで異常が発生した場合
            // 6046011=<{0}号機> データベースエラーが発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6046011, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.DB_ACCESS_ERROR, new String[]{"6047001", "6047011"});
        }
        else if (e instanceof IllegalAccessException)
        {
            // クラス生成エラー発生した場合
            // 6046113=<{0}号機> 処理されない例外が発生しました。クラスのインスタンス生成に失敗した可能性があります。{1}
            RmiMsgLogClient.write(new TraceHandler(6046113, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.INSTACIATE_ERROR, new String[]{"6047001", "6047013"});
        }
        else
        {
            // 6046009=<{0}号機> 予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6046009, e), caller.getSimpleName(), rftArr);
            return new HSResult(ResultConst.ERROR, new String[]{"6047001", "6047009"});
        }
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: HaiSurfExceptionHandler.java 4656 2009-07-14 05:07:28Z kishimoto $";
    }
}
