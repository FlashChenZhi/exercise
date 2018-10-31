// $Id: TimeKeeperDefines.java 7311 2010-03-01 05:39:44Z okayama $
package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.IniFileOperator;

/**
 * 自動処理の定義クラスです。<br>
 * Timekeeper.iniのパスについては、以下のように検索されます。<br>
 * <ol>
 * <li>システムプロパティ <code>INI_FILE</code><br>
 * 例: <code>java -DINI_FILE=c:/abc/def/timekeeper.ini</code>
 * <li><code>CommonParam.properties の TIME_KEEPER_INI_PATH</code> に定義されたパス
 * </ol>
 *
 * @version $Revision: 7311 $, $Date: 2010-03-01 14:39:44 +0900 (月, 01 3 2010) $
 * @author  清水　正人
 * @author  Last commit: $Author: okayama $
 */
public final class TimeKeeperDefines
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** timekeeper.iniのパス指定プロパティ名 */
    private static final String INI_FILE_PROPERTY = "INI_FILE";

    /** timekeeper.iniのパス定義キー (CommonParam) */
    private static final String TIME_KEEPER_INI_PATH = "TIME_KEEPER_INI_PATH";

    /** 自動処理が使用するディレクトリ (timekeeper.ini) */
    public static final String TIMEKEEPER_DIR = "TIMEKEEPER_DIR";

    /** 自動処理設定ファイル (timekeeper.ini)*/
    private static final String SETTING_FILE = "SETTING_FILE";

    /** 指示ファイルパス (timekeeper.ini) */
    private static final String INSTRUCTION_FILE = "INSTRUCTION_FILE";

    /** 設定ファイル再読込指示 */
    public static final String INSTRUCTION_RELOAD = "reload";

    /** 終了指示 */
    public static final String INSTRUCTION_TERMINATE = "terminate";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    private static File $scheduleFile = null;

    private static File $instrunctionFile = null;

    private static final Object $mutex = new Object();

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタは使用しません。
     */
    private TimeKeeperDefines()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * スケジュール定義ファイルを取得します。
     * 
     * @return スケジュール定義ファイル
     * @throws ReadWriteException iniファイルの取得に失敗したときスローされます
     */
    public static final File getScheduleFile()
            throws ReadWriteException
    {
        synchronized ($mutex)
        {
            if (null == $scheduleFile)
            {
                final IniFileOperator iniOp = getTKIniOperator();
                final String dir = iniOp.get(TIMEKEEPER_DIR);
                final String fileName = iniOp.get(SETTING_FILE);

                $scheduleFile = new File(dir, fileName);
            }
            return $scheduleFile;
        }
    }

    /**
     * スケジュール定義ファイルを取得します。
     *
     * @param iniFilePath TimeKeeper.iniファイルパス
     * @return スケジュール定義ファイル
     * @throws ReadWriteException iniファイルの取得に失敗したときスローされます
     */
    public static final File getScheduleFile(String iniFilePath)
            throws ReadWriteException
    {
        synchronized ($mutex)
        {
            final IniFileOperator iniOp = getTKIniOperator(iniFilePath);
            final String dir = iniOp.get(TIMEKEEPER_DIR);
            final String fileName = iniOp.get(SETTING_FILE);

            $scheduleFile = new File(dir, fileName);
            return $scheduleFile;
        }
    }

    /**
     * コマンド指示ファイルを取得します。
     * 
     * @return コマンド指示ファイル
     * @throws ReadWriteException iniファイルの取得に失敗したときスローされます
     */
    public static final File getInstructionFile()
            throws ReadWriteException
    {
        synchronized ($mutex)
        {
            if (null == $instrunctionFile)
            {
                final IniFileOperator iniOp = getTKIniOperator();
                final String dir = iniOp.get(TIMEKEEPER_DIR);
                final String fileName = iniOp.get(INSTRUCTION_FILE);

                $instrunctionFile = new File(dir, fileName);
            }
            return $instrunctionFile;
        }
    }

    /**
     * コマンド指示ファイルを取得します。<BR>
     * 呼び出される度に指示ファイルを取得し直します。
     * 
     * @param iniFilePath TimeKeeper.iniファイルパス
     * @return コマンド指示ファイル
     * @throws ReadWriteException iniファイルの取得に失敗したときスローされます
     */
    public static final File getInstructionFile(String iniFilePath)
            throws ReadWriteException
    {
        synchronized ($mutex)
        {
            final IniFileOperator iniOp = getTKIniOperator(iniFilePath);
            final String dir = iniOp.get(TIMEKEEPER_DIR);
            final String fileName = iniOp.get(INSTRUCTION_FILE);

            $instrunctionFile = new File(dir, fileName);
            return $instrunctionFile;
        }
    }

    /**
     * TimeKeeperの定義を読み込むための IniFileOperatorを
     * 取得して返します。
     * 
     * @return IniFileOperator
     * @throws ReadWriteException iniファイルの取得に失敗したときスローされます
     */
    @SuppressWarnings("deprecation")
    public static IniFileOperator getTKIniOperator()
            throws ReadWriteException
    {
        // find TKIni file path
        final String defaultPath = CommonParam.getParam(TIME_KEEPER_INI_PATH);
        final String iniFilePath = System.getProperty(INI_FILE_PROPERTY, defaultPath);

        if ((null == iniFilePath) || (0 == iniFilePath.length()))
        {
            throw new ReadWriteException("Timekeeper.ini path is not defined.");
        }
        return new IniFileOperator(iniFilePath);
    }

    /**
     * TimeKeeperの定義を読み込むための IniFileOperatorを
     * 取得して返します。
     *
     * @param iniFilePath TimeKeeper.iniファイルパス
     * @return IniFileOperator
     * @throws ReadWriteException iniファイルの取得に失敗したときスローされます
     */
    public static IniFileOperator getTKIniOperator(String iniFilePath)
            throws ReadWriteException
    {
        if ((null == iniFilePath) || (0 == iniFilePath.length()))
        {
            throw new ReadWriteException();
        }
        return new IniFileOperator(iniFilePath);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: TimeKeeperDefines.java 7311 2010-03-01 05:39:44Z okayama $";
    }
}
