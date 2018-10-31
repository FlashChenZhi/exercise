// $Id: PatliteOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import jp.co.daifuku.common.RmiMsgLogClient;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * パトライト制御。<br>
 * パトライトのランプを点灯させたり、ブザーを鳴動させたりします。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public final class PatliteOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * パトライト制御ステータス
     */
    public static enum PatliteStatus
    {
        /**
         * <code>ACK</code><br>
         * 正常受付<br>
         */
        ACK,

        /**
         * <code>NAK</code><br>
         * 異常終了<br>
         */
        NAK,
    }

    /**
     * パトライトへのコマンド<br>
     */
    public static enum PatliteCommand
    {
        /**
         * <code>RESET</code><br>
         * リセット<br>
         */
        RESET,

        /**
         * <code>RED</code><br>
         * 赤ランプ点灯<br>
         */
        RED,

        /**
         * <code>YELLOW</code><br>
         * 黄ランプ点灯<br>
         */
        YELLOW,

        /**
         * <code>GREEN</code><br>
         * 緑ランプ点灯<br>
         */
        GREEN,

        /**
         * <code>BLUE</code><br>
         * 青ランプ点灯<br>
         */
        BLUE,

        /**
         * <code>WHITE</code><br>
         * 白ランプ点灯<br>
         */
        WHITE,

        /**
         * <code>RED_BLINK</code><br>
         * 赤ランプ点滅<br>
         */
        RED_BLINK,

        /**
         * <code>YELLOW_BLINK</code><br>
         * 黄ランプ点滅<br>
         */
        YELLOW_BLINK,

        /**
         * <code>GREEN_BLINK</code><br>
         * 緑ランプ点滅<br>
         */
        GREEN_BLINK,

        /**
         * <code>BLUE_BLINK</code><br>
         * 青ランプ点滅<br>
         */
        BLUE_BLINK,

        /**
         * <code>WHITE_BLINK</code><br>
         * 白ランプ点滅<br>
         */
        WHITE_BLINK,

        /**
         * <code>BUZZER1</code><br>
         * ブザー1鳴動<br>
         */
        BUZZER1,

        /**
         * <code>BUZZER2</code><br>
         * ブザー2鳴動<br>
         */
        BUZZER2,

        /**
         * <code>BUZZER3</code><br>
         * ブザー3鳴動<br>
         */
        BUZZER3,
    }

    /**
     * <code>ALL</code><br>
     * 全警告灯を指定する場合に使用します<br>
     */
    public static final String ALL = "AllPatlite";

    private static final String TYPE = "PatliteType";

    private static final String PORT_ID = "PortId";

    private static final String ENABLE = "Enable";

    private static final String DISABLE_STRING = "0";

    /**
     * リセットコマンド
     */
    private static final PatliteCommand[] RESET_COMMAND = {
        PatliteCommand.RESET,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    private static PatliteSettingHolder $holder = null;

    private static List<String> $patlites = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * このコンストラクタでは特に処理を行いません。
     */
    private PatliteOperator()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 指定されたIDの警告灯を初期化します。
     * 
     * @param id 警告灯ID
     * @return パトライト制御ステータス
     */
    public static PatliteStatus reset(String id)
    {
        return set(id, RESET_COMMAND);
    }

    /**
     * 指定されたIDの警告灯に指示を設定します。<br>
     * 指示は配列にして複数同時に与える事ができますが、相反する指示を
     * 行った場合は、後のものが優先されます。
     * 
     * @param id 警告灯ID
     * @param command 指示(配列)
     * @return パトライト制御ステータス
     */
    public static synchronized PatliteStatus set(String id, PatliteCommand[] command)
    {
        // パラメータチェック
        if (null == id || 0 == id.length())
        {
            // 警告灯IDが指定されていません
            PatliteLogger.write(Level.WARNING, "Patlite ID is not specified.");
            return PatliteStatus.NAK;
        }
        if (null == command || 0 == command.length)
        {
            // 指示コマンドが設定されていません
            PatliteLogger.write(Level.WARNING, "Patlite command is not specified.");
            return PatliteStatus.NAK;
        }
        for (PatliteCommand cmd : command)
        {
            if (null == cmd)
            {
                // 警告灯(PAT??)でnullコマンドが指定されています
                StringBuffer message = new StringBuffer("Null command specified in Patlite(");
                message.append(id);
                message.append(")");
                PatliteLogger.write(Level.WARNING, String.valueOf(message));
                return PatliteStatus.NAK;
            }
        }

        // 指定された警告灯をリストアップします
        PatliteStatus ret = setupPatlites(id);
        if (PatliteStatus.NAK == ret)
        {
            return PatliteStatus.NAK;
        }

        boolean operation = true;
        for (String enableId : $patlites)
        {
            // 警告灯IDより機種名を取得して、指示コマンドを生成します
            String type = $holder.getProperty(enableId, TYPE);
            if (null == type)
            {
                operation = false;
                continue;
            }
            PatliteCommandBuilder builder = PatliteCommandBuilder.createCommandBuilder(type);
            if (null == builder)
            {
                // 6026078 = 警告灯：無効設定になっています。
                RmiMsgLogClient.write("6026078", "PatliteOperator");
                // 警告灯設定ファイルの異常があった場合は、NAKを返します
                StringBuffer message = new StringBuffer("Patlite(");
                message.append(type);
                message.append(") is not supported.");
                PatliteLogger.write(Level.WARNING, String.valueOf(message));
                operation = false;
                continue;
            }

            // リセットコマンドと指定された指示コマンドを生成します
            byte[] sendCommand = builder.buildCommand(command);

            // 警告灯IDよりコマンド送信クラスを生成します
            PatliteCommandSender sender = getCommandSender(enableId);
            PatliteStatus status = sender.commandSend(enableId, sendCommand);
            if (PatliteStatus.NAK == status)
            {
                //System.out.println(sender.getResultString());
                PatliteLogger.write(Level.WARNING, sender.getResultString());
                operation = false;
                continue;
            }
        }
        if (!operation)
        {
            return PatliteStatus.NAK;
        }
        return PatliteStatus.ACK;
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
    /**
     * パトライトコマンド送信クラス取得をおこないます。<br>
     * 警告灯IDより使用しているプロトコルを設定ファイルより読み取り、
     * 該当するコマンド送信クラスを生成して返します。
     * 
     * @param id 警告灯ID
     * @return パトライトコマンド送信クラス
     */
    private static PatliteCommandSender getCommandSender(String id)
    {
        // 該当するポートIDが "COM" または "/dev/" で始まっていれば
        // シリアル通信をするものとみなします
        String portId = $holder.getProperty(id, PORT_ID);
        if (portId.startsWith("COM") || portId.startsWith("/dev/"))
        {
            return new PatliteSerialSender($holder);
        }

        // シリアル通信以外は、ソケット通信するものとします
        else
        {
            return new PatliteSocketSender($holder);
        }
    }

    /**
     * 対象パトライトをセットアップします。<br>
     * 指定されたパトライトIDの有効/無効をチェックし
     * 処理対象のパトライトをリストアップします。
     * 
     * @param id パトライトID
     * @return パトライト制御ステータス
     */
    private static PatliteStatus setupPatlites(String id)
    {
        // パトライト設定管理クラスがまだインスタンス化されていなければ
        // ここでインスタンス化します
        if (null == $holder)
        {
            $holder = new PatliteSettingHolder();
        }

        // 既にパトライトのリストアップが終了している場合は、正常復帰します
        if (null != $patlites)
        {
            return PatliteStatus.ACK;
        }

        // 全パトライトが指定された場合は、有効なパトライトを取得します
        $patlites = new ArrayList<String>();
        if (ALL.equals(id))
        {
            List<String> allPatlites = $holder.getAllPatlites();
            for (String defId : allPatlites)
            {
                if (isEnable(defId))
                {
                    $patlites.add(defId);
                }
            }
            if (0 == $patlites.size())
            {
                // 6026078 = 警告灯：無効設定になっています。
                RmiMsgLogClient.write("6026078", "PatliteOperator");
                PatliteLogger.write(Level.WARNING, "There is no Patlite in available.");
                return PatliteStatus.NAK;
            }
            return PatliteStatus.ACK;
        }

        // 有効/無効チェック
        if (!isEnable(id))
        {
            // 6026078 = 警告灯：無効設定になっています。
            RmiMsgLogClient.write("6026078", "PatliteOperator");
            StringBuffer message = new StringBuffer("Patlite(");
            message.append(id);
            message.append(") is not available.");
            PatliteLogger.write(Level.WARNING, String.valueOf(message));
            return PatliteStatus.NAK;
        }
        $patlites.add(id);
        return PatliteStatus.ACK;
    }

    /**
     * 指定されたパトライトの有効/無効をチェックします。
     * 
     * @param id パトライトID
     * @return 有効の場合<code>true</code>を返します
     */
    private static boolean isEnable(String id)
    {
        // 有効/無効チェック
        String enable = $holder.getProperty(id, ENABLE);
        if (null == enable || DISABLE_STRING.equals(enable))
        {
            return false;
        }
        return true;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PatliteOperator.java 87 2008-10-04 03:07:38Z admin $";
    }
}
