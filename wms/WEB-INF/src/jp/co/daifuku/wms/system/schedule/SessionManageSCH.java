// $Id: SessionManageSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.SessionManageSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.util.sessionmanage.SessionManage;

/**
 * セッション管理のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class SessionManageSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 最大セッション情報数
     */
    private static final int MAX_SESSION_INFO = 6;

    /**
     * セッションパラメータ用番号：セッションID
     */
    private static final int LST_SESSION_ID = 0;

    /**
     * セッションパラメータ用番号：クライアント名
     */
    private static final int LST_CLIENT_NAME = 2;

    /**
     * セッションパラメータ用番号：端末状態
     */
    private static final int LST_STATUS = 3;

    /**
     * セッションパラメータ用番号：IPアドレス
     */
    private static final int LST_IP_ADRESS = 4;

    /**
     * セッションパラメータ用番号：RFT号機No
     */
    private static final int LST_RFTNO = 5;

    /**
     * プロセスフラグ：(切断ボタン押下)
     */
    public static final String PROCESS_DISCONNECT = "DISCONNECT";

    /**
     * プロセスフラグ：(切断ボタン押下)
     */
    public static final String PROCESS_RESET = "RESET";

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
    public SessionManageSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // セッション管理クラス生成
        SessionManage sm = new SessionManage();

        // 指定した端末名のセッション情報を取得
        String[][] viewParam = sm.wtsGetSessionList(p.getString(SERVER_NAME));

        // 画面表示用にパラメータクラスにセット
        // ソートもここで行う
        List<Params> result = getDisplayData(viewParam);
        if (result.size() <= 0)
        {
            // (6003011)対象データはありませんでした。
            setMessage("6003011");
        }
        // 取得したパラメータを返却
        return result;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // メッセージ判定
        int failed_cnt = 0;
        // パラメータ生成
        Params param = null;
        // セッション管理クラス生成
        SessionManage sm = null;

        for (int i = 0; i < ps.length; i++)
        {
            // セッション管理クラス生成
            sm = new SessionManage();
            // パラメータ生成
            param = ps[i];

            // プロセスが切断の場合
            if (param.getString(PROCESS_FLAG).equals(PROCESS_DISCONNECT))
            {
                // 切断処理
                failed_cnt = sm.wtsDisconnect(param.getString(SERVER_NAME), param.getString(SESSION_ID));
            }
            // プロセスがリセットの場合
            else
            {
                // リセット処理
                failed_cnt = sm.wtsLogoff(param.getString(SERVER_NAME), param.getString(SESSION_ID));
            }
        }

        // メッセージの設定を行う
        if (param.getString(PROCESS_FLAG).equals(PROCESS_DISCONNECT))
        {
            return setMessageDisConnect(failed_cnt);
        }
        else
        {
            return setMessageReset(failed_cnt);
        }
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
     * 号機No.順にソートをかけた表示情報を取得します。<BR>
     * <BR>
     * @param param セッション情報を保持したString配列
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(String[][] listParams)
            throws ReadWriteException
    {
        // 引数がnullの場合は空配列を返却
        if (listParams == null)
        {
            return new ArrayList<Params>();
        }

        // 号機No.順にソートを変更する
        // 要素-1分、繰り返す
        for (int i = 0; i < listParams.length - 1; i++)
        {
            // 全要素分、繰り返す
            for (int j = 0; j < listParams.length; j++)
            {
                // 号機No.[i]より号機No.[j]の方が小さい場合
                if (listParams[i][LST_RFTNO].compareTo(listParams[j][LST_RFTNO]) > 0)
                {
                    // 号機No.[j]を退避
                    String[] sort = listParams[j];
                    // 号機No.[j]と号機No.[i]を入れ替え
                    listParams[j] = listParams[i];
                    // 号機No.[i]に退避号機No.を代入
                    listParams[i] = sort;
                }
            }
        }

        // パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();

        // 全件繰り返し、情報を取得
        // 但し、listParams[i][1]のユーザ名は取得しない。
        for (int i = 0; i < listParams.length; i++)
        {
            // パラメータ生成
            Params param = new Params();

            // 最大セッション数の場合
            if (listParams[i].length == MAX_SESSION_INFO)
            {
                // 号機No.
                param.set(RFT_NO, listParams[i][LST_RFTNO]);
            }
            // クライアント名
            param.set(CLIENT_NAME, listParams[i][LST_CLIENT_NAME]);
            // IPアドレス
            param.set(IP_ADDRESS, listParams[i][LST_IP_ADRESS]);
            // 端末状態
            param.set(STATUS, listParams[i][LST_STATUS]);
            // セッションID
            param.set(SESSION_ID, listParams[i][LST_SESSION_ID]);

            // 設定したパラメータをパラメータ配列に格納
            result.add(param);
        }
        // 生成したパラメータ配列を返却
        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 切断処理のメッセージ判定<BR>
     * <BR>
     * @param foul メッセージ判定
     * @return 正常の場合:<CODE>true</CODE>
     *          異常の場合:<CODE>false</CODE>
     */
    private boolean setMessageDisConnect(int foul)
    {
        if (0 != foul)
        {
            // (6127020)セッションを切断できませんでした。再度、表示ボタンを押してください。
            setMessage("6127020");
            return false;
        }
        else
        {
            // (6021034)セッションを切断しました。
            setMessage("6021034");
            return true;
        }
    }

    /**
     * リセット処理のメッセージ判定<BR>
     * <BR>
     * @param foul メッセージ判定
     * @return 正常の場合:<CODE>true</CODE>
     *          異常の場合:<CODE>false</CODE>
     */
    private boolean setMessageReset(int foul)
    {
        if (0 != foul)
        {
            // (6127021)セッションをリセットできませんでした。再度、表示ボタンを押してください。
            setMessage("6127021");
            return false;
        }
        else
        {
            // (6021035)セッションをリセットしました。���I�ⵂ��N���C�A���g�����O�I�t���܂����B
            setMessage("6021035");
            return true;
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
