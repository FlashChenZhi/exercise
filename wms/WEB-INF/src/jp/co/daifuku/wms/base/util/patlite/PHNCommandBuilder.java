// $Id: PHNCommandBuilder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteCommand;

/**
 * 警告灯タイプ PHN のコマンドを生成します。<br>
 * 相反するコマンドが指示された場合は、後の指定されたコマンドが
 * 優先されます。　また、未確認のコマンド指示に対しては無視します。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public class PHNCommandBuilder
        extends PatliteCommandBuilder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>POS_DATA</code><br>
     * データ位置<br>
     */
    private static final int POS_DATA = 1;

    /*
     * 動作設定<br>
     */
    /**
     * LEDユニット 赤 点灯
     */
    private static final int CONF_RED = 0;

    /**
     * LEDユニット 黄 点灯
     */
    private static final int CONF_YELLOW = 1;

    /**
     * LEDユニット 緑 点灯
     */
    private static final int CONF_GREEN = 2;

    /**
     * ブザー1 吹鳴
     */
    private static final int CONF_BUZZER1 = 3;

    /**
     * ブザー2 吹鳴
     */
    private static final int CONF_BUZZER2 = 4;

    /**
     * LEDユニット 赤 点滅
     */
    private static final int CONF_RED_BLINK = 5;

    /**
     * LEDユニット 黄 点滅
     */
    private static final int CONF_YELLOW_BLINK = 6;

    /**
     * LEDユニット 緑 点滅
     */
    private static final int CONF_GREEN_BLINK = 7;

    /**
     * <code>OFF_PATTERN</code><br>
     * OFFパターン<br>
     */
    private static final byte OFF_PATTERN = 0x00;

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
     * このコンストラクタでは特に処理を行いません。
     */
    public PHNCommandBuilder()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * パトライト制御より受け取ったコマンドを、対象機種のコマンドに
     * 変換して返します。
     * 
     * @param command パトライト制御よりのコマンド
     * @return コマンド列(機種依存)
     */
    @Override
    byte[] buildCommand(PatliteCommand[] command)
    {
        // パトライト制御より受け取ったコマンドをPHN用のコマンド列に
        // 変換します
        // PHNのコマンドは2バイトのキャラクタ列で "W" に続いて1バイトの
        // データで構成されています
        byte[] byteCommand = {
            'W',
            OFF_PATTERN,
        };
        PatliteCommandSet[] build = PatliteCommandSet.toPatliteCommandSets(byteCommand);

        for (PatliteCommand srcCommand : command)
        {
            switch (srcCommand)
            {
                // リセット
                case RESET:
                    build[POS_DATA].set(OFF_PATTERN);
                    break;

                // 赤ランプ点灯
                case RED:
                    setCommand(build, false, CONF_RED_BLINK);
                    setCommand(build, true, CONF_RED);
                    break;

                // 黄ランプ点灯
                case YELLOW:
                    setCommand(build, false, CONF_YELLOW_BLINK);
                    setCommand(build, true, CONF_YELLOW);
                    break;

                // 緑ランプ点灯
                case GREEN:
                    setCommand(build, false, CONF_GREEN_BLINK);
                    setCommand(build, true, CONF_GREEN);
                    break;

                // 青ランプ点灯
                case BLUE:
                    // 現状サポートしていません
                    break;

                // 白ランプ点灯
                case WHITE:
                    // 現状サポートしていません
                    break;

                // 赤ランプ点滅
                case RED_BLINK:
                    setCommand(build, false, CONF_RED);
                    setCommand(build, true, CONF_RED_BLINK);
                    break;

                // 黄ランプ点滅
                case YELLOW_BLINK:
                    setCommand(build, false, CONF_YELLOW);
                    setCommand(build, true, CONF_YELLOW_BLINK);
                    break;

                // 緑ランプ点滅
                case GREEN_BLINK:
                    setCommand(build, false, CONF_GREEN);
                    setCommand(build, true, CONF_GREEN_BLINK);
                    break;

                // 青ランプ点滅
                case BLUE_BLINK:
                    // 現状サポートしていません
                    break;

                // 白ランプ点滅
                case WHITE_BLINK:
                    // 現状サポートしていません
                    break;

                // ブザー1鳴動
                case BUZZER1:
                    setCommand(build, false, CONF_BUZZER2);
                    setCommand(build, true, CONF_BUZZER1);
                    break;

                // ブザー2鳴動
                case BUZZER2:
                    setCommand(build, false, CONF_BUZZER1);
                    setCommand(build, true, CONF_BUZZER2);
                    break;

                // ブザー3鳴動
                case BUZZER3:
                    // 現状サポートしていません
                    break;

                default:
                    // 何も設定しません(無視します)
                    break;
            }
        }

        return PatliteCommandSet.toBytes(build);
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
     * コマンドを編集します。
     * 
     * @param cmd 編集対象コマンド
     * @param value ONにするとき、<code>true</code>を指定します。
     * @param pos コマンド設定位置
     */
    private void setCommand(PatliteCommandSet[] cmd, boolean value, int pos)
    {
        cmd[POS_DATA].set(pos, value);
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
        return "$Id: PHNCommandBuilder.java 87 2008-10-04 03:07:38Z admin $";
    }
}
