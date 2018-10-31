//$Id: BSRCategory.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.bsr;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteCommand;

/**
 * BSR定義ファイルの管理用クラスです。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public class BSRCategory
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * <code>WMS_RESOURCE</code><br>
     * WMSリソース名<br>
     */
    private static final String WMS_RESOURCE = "WMSParam";

    /**
     * <code>BSR_INI_PATH</code><br>
     * BSR設定ファイルパス名<br>
     */
    private static final String BSR_INI_PATH = "BSR_INI_PATH";

    /** 定義ファイルのパス */
    public static final String INIFILE_PATH = getBSRIniPath();

    private static final String KEY_NAME_LABEL = "NAME_LABEL";

    private static final String KEY_ENABLE = "ENABLE";

    private static final String KEY_USE_PATLITE = "USE_PATLITE";

    private static final String KEY_PATLITE = "PATLITE";

    private static final String KEY_FILE_KEEP = "FILE_KEEP";

    private static final String PARAM_STR_ON = "1";

    private static final String SPLIT_STR = ",";

    /** BSR setting common parameter */
    private static final String COMMON_SECTION = "COMMON";

    /** normal patlite defines */
    private static final String KEY_PAT_COLOR_NORMAL = "PAT_COLOR_NORMAL";

    private static final String KEY_PAT_BLINK_NORMAL = "PAT_BLINK_NORMAL";

    private static final String KEY_BUZZER_NORMAL = "BUZZER_NORMAL";

    /** warning patlite defines */
    private static final String KEY_PAT_COLOR_WARN = "PAT_COLOR_WARN";

    private static final String KEY_PAT_BLINK_WARN = "PAT_BLINK_WARN";

    private static final String KEY_BUZZER_WARN = "BUZZER_WARN";

    /** error patlite defines */
    private static final String KEY_PAT_COLOR_ERROR = "PAT_COLOR_ERROR";

    private static final String KEY_PAT_BLINK_ERROR = "PAT_BLINK_ERROR";

    private static final String KEY_BUZZER_ERROR = "BUZZER_ERROR";

    // 0:青,1:緑,2:黄色,3:白,4:赤
    private static final PatliteCommand[] PAT_COLOR_DEFS = {
        PatliteCommand.BLUE,
        PatliteCommand.GREEN,
        PatliteCommand.YELLOW,
        PatliteCommand.WHITE,
        PatliteCommand.RED,
    };

    private static final PatliteCommand[] PAT_BLINK_COLOR_DEFS = {
        PatliteCommand.BLUE_BLINK,
        PatliteCommand.GREEN_BLINK,
        PatliteCommand.YELLOW_BLINK,
        PatliteCommand.WHITE_BLINK,
        PatliteCommand.RED_BLINK,
    };

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** 定義ファイルハンドル */
    private IniFileOperator _iniFile = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public BSRCategory()
    {
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 設定されているカテゴリIDの一覧を返します。
     * @return カテゴリID一覧
     */
    public String[] getCategoryList()
    {
        String[] secArr = getIniFileOperator().getSections();

        return secArr;
    }

    /**
     * 指定したカテゴリIDの存在をチェックします。
     * @param category カテゴリID
     * @return boolean
     */
    public boolean hasCategory(String category)
    {
        String[] secArr = getIniFileOperator().getSections();
        for (int i = 0; i < secArr.length; i++)
        {
            if (secArr[i].equals(category))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定したカテゴリIDの名称ラベルを取得します。
     * @param category カテゴリID
     * @return 名称ラベル
     */
    public String getNameLabel(String category)
    {
        return getIniFileOperator().get(category, KEY_NAME_LABEL);
    }

    /**
     * 指定したカテゴリIDの使用フラグを取得します。
     * @param category カテゴリID
     * @return 使用フラグ
     */
    public boolean isEnabled(String category)
    {
        return getIniFileOperator().get(category, KEY_ENABLE).equals(PARAM_STR_ON);
    }

    /**
     * 指定したカテゴリIDのパトライト使用フラグを取得します。
     * @param category カテゴリID
     * @return パトライト使用フラグ
     */
    public boolean isUsePatlite(String category)
    {
        return getIniFileOperator().get(category, KEY_USE_PATLITE).equals(PARAM_STR_ON);
    }

    /**
     * 指定したカテゴリIDの使用パトライトID配列を取得します。
     * @param category カテゴリID
     * @return パトライトID配列
     */
    public String[] getPatlites(String category)
    {
        String[] strArr = getIniFileOperator().get(category, KEY_PATLITE).split(SPLIT_STR);
        return strArr;
    }

    /**
     * 指定したカテゴリIDのファイル保持件数を取得します。
     * @param category カテゴリID
     * @return ファイル保持件数
     */
    public int getFileKeepNum(String category)
    {
        String strVal = getIniFileOperator().get(category, KEY_FILE_KEEP);
        return new Integer(strVal).intValue();
    }

    /**
     * ファシリティごとのパトライトコマンドを返します。
     * 
     * @param facility ファシリティコード
     * @return パトライトコマンド
     */
    public PatliteCommand[] getPatliteCommands(int facility)
    {
        IniFileOperator iop = getIniFileOperator();

        // color/buzzer
        String patColor;
        String blink;
        String beepFlg;

        // get color/buzzer def
        switch (facility)
        {
            // 通常完了
            case BSRInfo.NORMAL_COMPLETED:
                patColor = iop.get(COMMON_SECTION, KEY_PAT_COLOR_NORMAL);
                blink = iop.get(COMMON_SECTION, KEY_PAT_BLINK_NORMAL);
                beepFlg = iop.get(COMMON_SECTION, KEY_BUZZER_NORMAL);
                break;

            // 警告完了
            case BSRInfo.WARNINGLY_TERMINATED:
                patColor = iop.get(COMMON_SECTION, KEY_PAT_COLOR_WARN);
                blink = iop.get(COMMON_SECTION, KEY_PAT_BLINK_WARN);
                beepFlg = iop.get(COMMON_SECTION, KEY_BUZZER_WARN);
                break;

            // 異常完了
            case BSRInfo.ABNORMAL_TERMINATED:
                patColor = iop.get(COMMON_SECTION, KEY_PAT_COLOR_ERROR);
                blink = iop.get(COMMON_SECTION, KEY_PAT_BLINK_ERROR);
                beepFlg = iop.get(COMMON_SECTION, KEY_BUZZER_ERROR);
                break;

            // その他
            default:
                return new PatliteCommand[0];
        }

        PatliteCommand[] cmds = buildPatliteCmd(patColor, blink, beepFlg);
        return cmds;
    }

    /**
     * パトライトコマンドを組み立てて返します。
     * 
     * @param patColor パトライト色指定
     * @param blinkFlg 点滅指定 (on/off)
     * @param beepFlg ブザー指定 (on/off)
     * @return パトライト送信用コマンド
     */
    private PatliteCommand[] buildPatliteCmd(String patColor, String blinkFlg, String beepFlg)
    {
        List<PatliteCommand> pcmdList = new ArrayList<PatliteCommand>();

        int color = Integer.parseInt(patColor);
        boolean blink = PARAM_STR_ON.equals(blinkFlg);
        PatliteCommand cmd = (blink) ? PAT_BLINK_COLOR_DEFS[color]
                                    : PAT_COLOR_DEFS[color];

        pcmdList.add(cmd);

        boolean buzzerOn = PARAM_STR_ON.equals(beepFlg);
        if (buzzerOn)
        {
            pcmdList.add(PatliteCommand.BUZZER1);
        }

        return pcmdList.toArray(new PatliteCommand[pcmdList.size()]);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    private IniFileOperator getIniFileOperator()
    {
        try
        {
            if (null == _iniFile)
            {
                _iniFile = new IniFileOperator(INIFILE_PATH);
            }
            return _iniFile;
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Inifile not found.");
        }
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * BSR設定ファイルパス取得
     * BSR設定ファイルのパス名を取得します
     * 該当する情報が取得できない場合は、<code>null</code>を返します
     * @return BSR設定ファイルのパス名
     */
    private static String getBSRIniPath()
    {
        try
        {
            ResourceBundle rb = ResourceBundle.getBundle(WMS_RESOURCE, Locale.getDefault());
            return rb.getString(BSR_INI_PATH);
        }
        catch (MissingResourceException e)
        {
            return null;
        }
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
        return "$Id: BSRCategory.java 87 2008-10-04 03:07:38Z admin $";
    }
}
