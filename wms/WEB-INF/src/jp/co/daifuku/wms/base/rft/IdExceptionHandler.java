// $Id: IdExceptionHandler.java 2517 2009-01-06 02:23:13Z kishimoto $
package jp.co.daifuku.wms.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.communication.rft.AnswerCode;
import jp.co.daifuku.wms.base.exception.OperatorException;

/**
 * ID処理用 例外ハンドラ.<br>
 * 例外に対応した、応答フラグ、エラー詳細を返すメソッドを実装します。<br>
 * 各IDで共通の例外を扱います。
 *
 * @version $Revision: 2517 $, $Date: 2009-01-06 11:23:13 +0900 (火, 06 1 2009) $
 * @author  taki
 * @author  Last commit: $Author: kishimoto $
 * @since 2008-03-28 フラグの位置定義を追加
 */

public final class IdExceptionHandler
        implements AnswerCode
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** 応答フラグの位置 */
    public static final int POS_RESPONSE_FLAG = 0;

    /** エラー詳細の位置 */
    public static final int POS_ERR_DETAILS = 1;

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
    private IdExceptionHandler()
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

    /** 応答フラグ取得処理
     *  例外に対応した、応答フラグとエラー詳細を返します。
     *  応答フラグとエラー詳細は、応答電文にそのままセットする値です。
     *  
     * @param e         発生した例外クラス
     * @param caller    呼び出し元クラス
     * @param rftNo     号機No.
     * @return 応答フラグ/エラー詳細
     */
    public static List<String> getResponceFlag(Exception e, Class caller, String rftNo)
    {
        List<String> resp = new ArrayList<String>();
        Object[] rftArr = {
            rftNo
        };

        if (e instanceof DataExistsException)
        {
            // 情報を登録しようとした際に、既に同じ情報が登録済みの場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.DB_UNIQUE_KEY_ERROR);
            // 6026106=<{0}号機> ID対応処理例外。データベースに情報を登録しようとした際に、既に同じ情報が登録済みです。{1}
            RmiMsgLogClient.write(new TraceHandler(6026106, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof InvalidDefineException)
        {
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.PARAMETER_ERROR);
            // 6026107=<{0}号機> ID対応処理例外。定義情報（指定したパラメータの値）が異常です。{1}
            RmiMsgLogClient.write(new TraceHandler(6026107, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof LockTimeOutException)
        {
            // 時間内にデータのロックが解除されなかった場合
            resp.add(AnswerCode.ANS_CODE_MAINTENANCE);
            resp.add(AnswerCode.ErrorDetails.NORMAL);
            // 6026108=<{0}号機> ID対応処理例外。データベースの対象データがロックされており、情報の取得や更新ができません。{1}
            RmiMsgLogClient.write(new TraceHandler(6026108, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof NoPrimaryException)
        {
            // 複数件該当するはずの無い問合せで、複数件該当した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.PARAMETER_ERROR);
            // 6026109=<{0}号機> ID対応処理例外。複数件該当するはずのないデータベース検索で、複数件該当しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026109, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof ReadWriteException)
        {
            // データベースまたは、ファイル操作で異常が発生した場合
            // (スタックトレースは、発生元で出力しているので、ここではメッセージのみ出力する。)
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.DB_ACCESS_ERROR);
            // 6026110=<{0}号機> ID対応処理例外。データベースエラーが発生しました。詳細は直前のログを参照してください。
            RmiMsgLogClient.write(6026110, caller.getSimpleName(), rftArr);
        }
        else if (e instanceof ScheduleException)
        {
            // スケジュール処理の実行中に予期しない例外が発生した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.SCHEDULE_ERROR);
            // 6026111=<{0}号機> ID対応処理例外。スケジュール処理の実行中に予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026111, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof OperatorException)
        {
            OperatorException opeEx = (OperatorException)e;
            String errCode = opeEx.getErrorCode();
            if (OperatorException.ERR_ALREADY_UPDATED.equals(errCode))
            {
                // 更新対象データが、既に他で更新されていた場合
                resp.add(AnswerCode.ANS_CODE_ERROR);
                resp.add(AnswerCode.ErrorDetails.UPDATE_FINISH);
                // 6026112=<{0}号機> ID対応処理例外。更新対象データが他で更新されました。RFT状態メンテナンスで更新された可能性があります。{1}
                RmiMsgLogClient.write(new TraceHandler(6026112, e), caller.getSimpleName(), rftArr);
            }
            else if (OperatorException.ERR_OVERFLOW.equals(errCode))
            {
                // オーバーフローが発生した場合
                resp.add(AnswerCode.ANS_CODE_ERROR);
                resp.add(AnswerCode.ErrorDetails.OVERFLOW);
                // 6026113=<{0}号機> ID対応処理例外。オーバーフローが発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6026113, e), caller.getSimpleName(), rftArr);
            }
            else
            {
                // 予期しない例外が発生した場合
                resp.add(AnswerCode.ANS_CODE_ERROR);
                resp.add(AnswerCode.ErrorDetails.INTERNAL_ERROR);
                // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
            }
        }
        else if (e instanceof IdSchException)
        {
            IdSchException idSchEx = (IdSchException)e;
            String errCode = idSchEx.getErrorCode();
            if (IdSchException.PATTERN_NG.equals(errCode))
            {
                // パラメータ内に禁止文字が含まれている場合
                resp.add(AnswerCode.ANS_CODE_ERROR);
                resp.add(AnswerCode.ErrorDetails.PARAMETER_ERROR);
                // 6026114=<{0}号機> ID対応処理例外。入力された値に禁止文字が含まれています。{1}
                RmiMsgLogClient.write(new TraceHandler(6026114, e), caller.getSimpleName(), rftArr);
            }
            else if (IdSchException.DAILY_UPDATING.equals(errCode))
            {
                // 日次更新中の場合
                resp.add(AnswerCode.ANS_CODE_DAILY_UPDATING);
                resp.add(AnswerCode.ErrorDetails.NORMAL);
            }
            else
            {
                // 予期しない例外が発生した場合
                resp.add(AnswerCode.ANS_CODE_ERROR);
                resp.add(AnswerCode.ErrorDetails.INTERNAL_ERROR);
                // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
            }
        }
        else if (e instanceof CommonException)
        {
            // 予期しない例外が発生した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.INTERNAL_ERROR);
            // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof SQLException)
        {
            // データベースで異常が発生した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.DB_ACCESS_ERROR);
            // 6026115=<{0}号機> ID対応処理例外。データベースエラーが発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026115, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof SecurityException)
        {
            // ファイル操作などで、セキュリティ違反が発生した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.I_O_ERROR);
            // 6026116=<{0}号機> ID対応処理例外。ファイル操作などで、セキュリティ違反が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026116, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof IOException)
        {
            // ファイル操作などで、セキュリティ違反が発生した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.I_O_ERROR);
            // 6026116=<{0}号機> ID対応処理例外。ファイル操作などで、セキュリティ違反が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026116, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof IllegalAccessException)
        {
            // クラス生成エラー発生した場合
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.INSTACIATE_ERROR);
            // 6026116=<{0}号機> ID対応処理例外。ファイル操作などで、セキュリティ違反が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026105, e), caller.getSimpleName(), rftArr);
        }
        else
        {
            resp.add(AnswerCode.ANS_CODE_ERROR);
            resp.add(AnswerCode.ErrorDetails.INTERNAL_ERROR);
            // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
        }
        return resp;
    }

    /** 応答メッセージ取得処理
     *  例外に対応した、エラーメッセージ番号を返します。
     *  
     * @param e         発生した例外クラス
     * @param caller    呼び出し元クラス
     * @param rftNo     号機No.
     * @return 応答フラグ/エラー詳細
     */
    public static List<String> getResponceMessage(Exception e, Class caller, String rftNo)
    {
        List<String> resp = new ArrayList<String>();
        Object[] rftArr = {
            rftNo
        };

        if (e instanceof DataExistsException)
        {
            // 情報を登録しようとした際に、既に同じ情報が登録済みの場合
            resp.add("ERR1003");
            resp.add("ERR2004");
            // 6026106=<{0}号機> ID対応処理例外。データベースに情報を登録しようとした際に、既に同じ情報が登録済みです。{1}
            RmiMsgLogClient.write(new TraceHandler(6026106, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof InvalidDefineException)
        {
            resp.add("ERR1003");
            resp.add("ERR2002");
            // 6026107=<{0}号機> ID対応処理例外。定義情報（指定したパラメータの値）が異常です。{1}
            RmiMsgLogClient.write(new TraceHandler(6026107, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof LockTimeOutException)
        {
            // 時間内にデータのロックが解除されなかった場合
            resp.add("MSG1005");
            resp.add("MSG1008");
            // 6026108=<{0}号機> ID対応処理例外。データベースの対象データがロックされており、情報の取得や更新ができません。{1}
            RmiMsgLogClient.write(new TraceHandler(6026108, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof NoPrimaryException)
        {
            // 複数件該当するはずの無い問合せで、複数件該当した場合
            resp.add("ERR1003");
            resp.add("ERR2002");
            // 6026109=<{0}号機> ID対応処理例外。複数件該当するはずのないデータベース検索で、複数件該当しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026109, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof ReadWriteException)
        {
            // データベースまたは、ファイル操作で異常が発生した場合
            // (スタックトレースは、発生元で出力しているので、ここではメッセージのみ出力する。)
            resp.add("ERR1003");
            resp.add("ERR2003");
            // 6026110=<{0}号機> ID対応処理例外。データベースエラーが発生しました。詳細は直前のログを参照してください。
            RmiMsgLogClient.write(6026110, caller.getSimpleName(), rftArr);
        }
        else if (e instanceof ScheduleException)
        {
            // スケジュール処理の実行中に予期しない例外が発生した場合
            resp.add("ERR1003");
            resp.add("ERR2008");
            // 6026111=<{0}号機> ID対応処理例外。スケジュール処理の実行中に予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026111, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof OperatorException)
        {
            OperatorException opeEx = (OperatorException)e;
            String errCode = opeEx.getErrorCode();
            if (OperatorException.ERR_ALREADY_UPDATED.equals(errCode))
            {
                // 更新対象データが、既に他で更新されていた場合
                resp.add("ERR1003");
                resp.add("ERR2021");
                // 6026112=<{0}号機> ID対応処理例外。更新対象データが他で更新されました。RFT状態メンテナンスで更新された可能性があります。{1}
                RmiMsgLogClient.write(new TraceHandler(6026112, e), caller.getSimpleName(), rftArr);
            }
            else if (OperatorException.ERR_OVERFLOW.equals(errCode))
            {
                // オーバーフローが発生した場合
                resp.add("ERR1003");
                resp.add("ERR2022");
                // 6026113=<{0}号機> ID対応処理例外。オーバーフローが発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6026113, e), caller.getSimpleName(), rftArr);
            }
            else
            {
                // 予期しない例外が発生した場合
                resp.add("ERR1003");
                resp.add("ERR2001");
                // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
            }
        }
        else if (e instanceof IdSchException)
        {
            IdSchException idSchEx = (IdSchException)e;
            String errCode = idSchEx.getErrorCode();
            if (IdSchException.PATTERN_NG.equals(errCode))
            {
                // パラメータ内に禁止文字が含まれている場合
                resp.add("ERR1003");
                resp.add("ERR2002");
                // 6026114=<{0}号機> ID対応処理例外。入力された値に禁止文字が含まれています。{1}
                RmiMsgLogClient.write(new TraceHandler(6026114, e), caller.getSimpleName(), rftArr);
            }
            else if (IdSchException.DAILY_UPDATING.equals(errCode))
            {
                // 日次更新中の場合
                resp.add("MSG1004");
                resp.add("MSG1008");
            }
            else
            {
                // 予期しない例外が発生した場合
                resp.add("ERR1003");
                resp.add("ERR2001");
                // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
                RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
            }
        }
        else if (e instanceof CommonException)
        {
            // 予期しない例外が発生した場合
            resp.add("ERR1003");
            resp.add("ERR2001");
            // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof SQLException)
        {
            // データベースで異常が発生した場合
            resp.add("ERR1003");
            resp.add("ERR2003");
            // 6026115=<{0}号機> ID対応処理例外。データベースエラーが発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026115, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof SecurityException)
        {
            // ファイル操作などで、セキュリティ違反が発生した場合
            resp.add("ERR1003");
            resp.add(AnswerCode.ErrorDetails.I_O_ERROR);
            // 6026116=<{0}号機> ID対応処理例外。ファイル操作などで、セキュリティ違反が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026116, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof IOException)
        {
            // ファイル操作などで、セキュリティ違反が発生した場合
            resp.add("ERR1003");
            resp.add("ERR2006");
            // 6026116=<{0}号機> ID対応処理例外。ファイル操作などで、セキュリティ違反が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026116, e), caller.getSimpleName(), rftArr);
        }
        else if (e instanceof IllegalAccessException)
        {
            // クラス生成エラー発生した場合
            resp.add("ERR1003");
            resp.add("ERR2005");
            // 6026116=<{0}号機> ID対応処理例外。ファイル操作などで、セキュリティ違反が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026105, e), caller.getSimpleName(), rftArr);
        }
        else
        {
            resp.add("ERR1003");
            resp.add("ERR2001");
            // 6026015=<{0}号機> ID対応処理例外。予期しない例外が発生しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026015, e), caller.getSimpleName(), rftArr);
        }
        return resp;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: IdExceptionHandler.java 2517 2009-01-06 02:23:13Z kishimoto $";
    }
}
