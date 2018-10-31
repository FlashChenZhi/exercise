package jp.co.daifuku.emanager.util;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.NoPoolConnection;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;

/**
 * 
 * Part11設定に関する情報を提供します。
 * 設定情報はstatic変数としてキャッシュするので
 * 変更を反映する場合はJavaVMの再起動が必要です。
 *
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  073019
 * @author  Last commit: $Author: admin $
 */
public class P11Config
{

    /** <jp>認証ログをDB出力するかどうかのフラグ &nbsp;&nbsp;</jp><en>Flag for authentication log on DB. &nbsp;&nbsp;</en> */
    private static boolean dBLogginAuthLog;

    /** <jp>メンテナンスログをDB出力するかどうかのフラグ &nbsp;&nbsp;</jp><en>Flag for maintenance log on DB. &nbsp;&nbsp;</en> */
    private static boolean dBLogginMainteLog;

    /**
     * Part11ログを出力するかどうかのフラグ
     */
    private static boolean part11Log;

    /**
     * アクセスログを出力するかどうかのフラグ
     */
    private static boolean accessLog;

    /**
     * オペレーションログを出力するかどうかのフラグ
     */
    private static boolean operationLog;

    /**
     * マスタ改廃ログを出力するかどうかのフラグ
     */
    private static boolean masterLog;

    /**
     * 在庫更新履歴ログを出力するかどうかのフラグ
     */
    private static boolean stockFlag;

    /**
     * ログデータ保持日数
     */
    private static int dbLogHoldDays;

    /**
     * ログファイル作成期間
     */
    private static int csvLogHoldDays;

    /**
     * ログファイルディスク保持期間
     */
    private static int hdLogHoldYears;

    /**
     * ログファイルディスク保持期間
     */
    private static boolean screenLoginFlag;

    /**
     * <jp>初期化処理 <br></jp>
     * <en>Initialize <br></en>
     */
    static
    {
        Connection conn = null;
        try
        {
            try
            {
                conn = EmConnectionHandler.getConnection();
            }
            catch (SQLException e)
            {
                String path = EmProperties.getProperty(EmConstants.EMPARAMKEY_DATASOURCEXML_PATH);
                String dataSourceName = EmProperties.getProperty(EmConstants.EMPARAMKEY_DATASOURCENAME);
                // poolから取得できない場合は直接取得する
                conn = NoPoolConnection.getConnection(path, dataSourceName);
            }

            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();

            dBLogginMainteLog = authEntity.getUserMaintLogFlag();
            dBLogginAuthLog = authEntity.getAuthLogFlag();
            part11Log = authEntity.getPart11Flag();
            accessLog = authEntity.getAccessLogFlag();
            operationLog = authEntity.getOperationLogFlag();
            masterLog = authEntity.getMasterLogFlag();
            stockFlag = authEntity.getStockLogFlag();
            dbLogHoldDays = authEntity.getDbLogHoldDays();
            csvLogHoldDays = authEntity.getCsvLogHoldDays();
            hdLogHoldYears = authEntity.getHdLogHoldYears();
            screenLoginFlag = authEntity.getScreenLoginCheckFlag();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /**
     * accessLogを返します。
     * @return accessLogを返します。
     */
    public static boolean isAccessLog()
    {
        return accessLog;
    }

    /**
     * csvLogHoldDaysを返します。
     * @return csvLogHoldDaysを返します。
     */
    public static int getCsvLogHoldDays()
    {
        return csvLogHoldDays;
    }

    /**
     * dbLogHoldDaysを返します。
     * @return dbLogHoldDaysを返します。
     */
    public static int getDbLogHoldDays()
    {
        return dbLogHoldDays;
    }

    /**
     * hdLogHoldYearsを返します。
     * @return hdLogHoldYearsを返します。
     */
    public static int getHdLogHoldYears()
    {
        return hdLogHoldYears;
    }

    /**
     * isDBLogginAuthLogを返します。
     * @return isDBLogginAuthLogを返します。
     */
    public static boolean isDBLogginAuthLog()
    {
        return dBLogginAuthLog;
    }

    /**
     * isDBLogginMainteLogを返します。
     * @return isDBLogginMainteLogを返します。
     */
    public static boolean isDBLogginMainteLog()
    {
        return dBLogginMainteLog;
    }

    /**
     * masterLogを返します。
     * @return masterLogを返します。
     */
    public static boolean isMasterLog()
    {
        return masterLog;
    }

    /**
     * oerationLogを返します。
     * @return oerationLogを返します。
     */
    public static boolean isOperationLog()
    {
        return operationLog;
    }

    /**
     * par11Logを返します。
     * @return par11Logを返します。
     */
    public static boolean isPart11Log()
    {
        return part11Log;
    }

    /**
     * stockFlagを返します。
     * @return stockFlagを返します。
     */
    public static boolean isStockFlag()
    {
        return stockFlag;
    }

    /**
     * stockFlagを返します。
     * @return stockFlagを返します。
     */
    public static boolean isScreenLoginFlag()
    {
        return screenLoginFlag;
    }
}
