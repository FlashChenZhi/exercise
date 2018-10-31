// $Id: PatliteSettingHolder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.IniFileOperator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * パトライト設定内容を管理するためのクラスです。<br>
 * パトライト設定ファイルの内容を保持します。<br>
 * 該当するリソースファイルが見当たらなかったり、パトライト設定
 * ファイルの読み込みに失敗した場合は、内部に何も保持しないまま
 * インスタンス化されます。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public class PatliteSettingHolder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>WMS_RESOURCE</code><br>
     * WMSリソース名<br>
     */
    private static final String WMS_RESOURCE = "WMSParam";

    /**
     * <code>PATLITE_INI_PATH</code><br>
     * パトライト設定ファイル<br>
     */
    private static final String PATLITE_INI_PATH = "PATLITE_INI_PATH";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    private static Map<String, String> $patliteSettings =
            Collections.synchronizedMap(new HashMap<String, String>(1024));

    private static List<String> $allPatlites = new ArrayList<String>();

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * パトライト設定情報を読み込んで保持します。
     */
    public PatliteSettingHolder()
    {
        super();
        load();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * パトライト設定ファイルに設定されている内容を取得します。
     * 
     * @param id パトライトID
     * @param key キー
     * @return 設定値
     */
    public String getProperty(String id, String key)
    {
        String hashKey = buildKey(id, key);
        String value = $patliteSettings.get(hashKey);
        return value;
    }

    /**
     * パトライト設定ファイルに設定されている内容をint型として取得します。<br>
     * 注意:
     * 該当する設定内容が、int型として表現できない内容であった場合は -1
     * を返します。<br>故意に -1 を設定していた場合と見分けがつきません。
     * 
     * @param id パトライトID
     * @param key キー
     * @return 設定値
     */
    public int getIntProperty(String id, String key)
    {
        /*
         String hashKey = buildKey(id, key);
         String value = $patliteSettings.get(hashKey);
         */
        String value = getProperty(id, key);
        try
        {
            int intValue = Integer.parseInt(value);
            return intValue;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    /**
     * パトライト設定ファイルに設定されている全パトライトのリストを取得します。
     * 
     * @return 全パトライトリスト
     */
    public List<String> getAllPatlites()
    {
        return $allPatlites;
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
     * パトライトの設定ファイルを読み込み、内部に保持します
     * 該当するリソースファイルが見当たらなかったり、パトライト設定
     * ファイルの読み込みに失敗した場合は、内部に何も保持しないまま
     * インスタンス化されます。
     */
    private void load()
    {
        try
        {
            // 設定ファイルを読み込む準備をします
            ResourceBundle rb = ResourceBundle.getBundle(WMS_RESOURCE, Locale.getDefault());
            String iniPath = rb.getString(PATLITE_INI_PATH);
            IniFileOperator ifo = new IniFileOperator(new File(iniPath));

            // 設定ファイルに含まれている全セクションを読み取ります。
            String[] sections = ifo.getSections();
            for (String section : sections)
            {
                $allPatlites.add(section);
                String[] keys = ifo.getKeys(section);
                for (String key : keys)
                {
                    // [セクション]キー をハッシュマップのキーにします
                    String hashKey = buildKey(section, key);
                    // 設定値を取得します
                    String value = ifo.get(section, key);
                    // 読み取った設定値をハッシュマップに格納します
                    $patliteSettings.put(hashKey, value);
                }
            }
        }
        catch (MissingResourceException e)
        {
            // 何もしません。
            // 該当するリソースファイルが見当たらなかった場合は
            // パトライト設定ファイルを内部に保持しません
        }
        catch (ReadWriteException e)
        {
            // 何もしません。
            // リソースファイルの読み込みに失敗した場合は
            // パトライト設定ファイルを内部に保持しません
        }
    }

    /**
     * 内部のハッシュマップで使用するキーを生成します。<br>
     * ハッシュマップのキーは [パトライトID]キー とします。
     * 
     * @param id パトライトID
     * @param key キー
     * @return ハッシュマップキー
     */
    private String buildKey(String id, String key)
    {
        StringBuffer hashKey = new StringBuffer("[");
        hashKey.append(id);
        hashKey.append("]");
        hashKey.append(key);
        return String.valueOf(hashKey);
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
        return "$Id: PatliteSettingHolder.java 87 2008-10-04 03:07:38Z admin $";
    }
}
