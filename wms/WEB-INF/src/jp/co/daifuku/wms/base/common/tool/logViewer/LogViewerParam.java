//$Id: LogViewerParam.java 8075 2014-09-19 07:16:57Z okayama $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

/** Designer :  <BR>
 * Maker :   <BR>
 * <BR>
 * 環境設定ファイルクラス<BR>
 * javaプロパティファイルの環境設定ファイルから情報を取得するためのクラスです。<BR>
 * <BR>
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  $Author: okayama $
 */
public class LogViewerParam
{
    /**
     * このクラスのバージョンを返します
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return "$Revision: 8075 $,$Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $";
    }

    /**
     * LogViewerのパス
     */
    public static final String LogViewerPath = "C:\\daifuku\\wms\\bin\\LogViewer";

    /**
     * LogViewerのconfパス
     */
    public static final String ConfPath = LogViewerPath + "\\" + "conf";

    /**
     * Windowの横の長さ
     */
    public static final int DefaultWindowWidth = 1024;

    /**
     * Windowの高さ
     */
    public static final int DefaultWindowHeight = 768;

    /**
     * メッセージの長さ
     */
    public static final int DefaultTraceMessageWidth = 3500;

    /**
     * デフォルトのリソースのパス
     */
    public static String resourcePath;

    /**
     * 通信トレースログ一覧照会画面の最大表示行数
     */
    public static int MaxLineCnt;

    /**
     * 通信トレースログファイル名（RFT用）
     */
    public static String TraceLogFileName;

    /**
     * 通信トレースログファイル名（AS21用）
     */
    public static String AS21TraceLogFileName;

    /**
     * ID項目設定ファイルのファイル名
     */
    public static String IDFileName;

    /**
     * 画面背景色
     */
    public static int[] BackColor;

    /**
     * ウィンドウサイズ（幅）
     */
    public static int WindowWidth;

    /**
     * ウィンドウサイズ（高さ）
     */
    public static int WindowHeight;

    /**
     * ＩＤ桁数
     */
    public static int IdFigure;

    /**
     * 詳細画面STX表示文字列
     */
    public static String DisplaySTX;

    /**
     * 詳細画面ETX変換文字列
     */
    public static String DisplayETX;

    /**
     * RFTのトレースログが置かれているパス名
     */
    public static String RftLogPath;

    /**
     * 詳細表示画面内容欄表示幅
     */
    public static int TraceMessageWidth;

    /**
     * プロパティ情報
     */
    protected static Properties prop = new Properties();

    /**
     * 設定ファイルを読み込み、各パラメータを初期化します。
     * @throws Exception リソースファイルの読み込みに失敗した場合に通知されます。
     */
    public static void initialize()
            throws Exception
    {
        resourcePath = ConfPath + "\\" + OperationMode.getPropertyFileName();

        InputStream is = null;
        try
        {
            is = new FileInputStream(resourcePath);
            prop.load(is);

            MaxLineCnt = getIntParam("MaxLineCnt");
            if (OperationMode.mode == OperationMode.RFT)
            {
                TraceLogFileName = getParam("TraceLogFileName");
            }
            else
            {
                AS21TraceLogFileName = getParam("AS21TraceLogFileName");
            }
            IDFileName = getParam("IDFileName");
            BackColor = getRgbParam("BackColor");
            WindowWidth = getIntParam("WindowWidth");
            WindowHeight = getIntParam("WindowHeight");
            IdFigure = getIntParam("IdFigure");
            DisplaySTX = getParam("DisplaySTX");
            DisplayETX = getParam("DisplayETX");
            RftLogPath = getParam("RftLogPath");
            TraceMessageWidth = getIntParam("TraceMessageWidth");
        }
        catch (Exception e)
        {
            MaxLineCnt = TraceLogFile.MAX_DISPLAY;
            if (OperationMode.mode == OperationMode.RFT)
            {
                TraceLogFileName = TraceLogFile.TraceLogFileName;
                IdFigure = TraceLogFile.DefaultIdFigure;
            }
            else
            {
                AS21TraceLogFileName = As21TraceLogFile.AS21TraceLogFileName;
                IdFigure = As21TraceLogFile.DefaultIdFigure;
            }
            IDFileName = IdLayout.IdLayoutFileName;
            BackColor = getRgbParam("BackColor");
            WindowWidth = DefaultWindowWidth;
            WindowHeight = DefaultWindowHeight;
            DisplaySTX = "[";
            DisplayETX = "]";
            RftLogPath = ".";
            TraceMessageWidth = DefaultTraceMessageWidth;
            throw new Exception("6006015");
        }
        finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }

    /**
     * キーから、パラメータの内容を文字列表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     */
    private static String getParam(String key)
    {
        return prop.getProperty(key);
    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    private static int getIntParam(String key)
    {
        return Integer.parseInt(getParam(key));
    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    private static int[] getRgbParam(String key)
    {
        int[] intRGB = new int[3];

        try
        {
            String strColor = getParam(key);
            // StringTokenizerオブジェクトの生成
            StringTokenizer tokColor = new StringTokenizer(strColor, ",");

            int i = 0;
            while (tokColor.hasMoreTokens())
            {
                intRGB[i] = Integer.parseInt(tokColor.nextToken());
                if (intRGB[i] < 0 || intRGB[i] > 255)
                {
                    throw new Exception();
                }
                i++;
            }

            if (i != 3)
            {
                throw new Exception();
            }
        }
        catch (Exception e)
        {
            // int変換に失敗した場合、0<255の場合は基本背景色を設定
            intRGB[0] = 218;
            intRGB[1] = 217;
            intRGB[2] = 240;
        }
        return intRGB;
    }
}
