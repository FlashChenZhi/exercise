// $Id: PCTFileFilter.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.base.util;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FilenameFilter;

import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * PCTデータファイルに使用するフィルタクラスです。<br>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  syoshida
 * @author  Last commit: $Author: arai $
 */
public class PCTFileFilter
    implements FilenameFilter
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
     * コンストラクタ
     */
    public PCTFileFilter()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * フォルダpath にある ファイルnameを処理の対象にする場合にはtrue、
     * 無視したい場合には false を返します。<br>
     * @param path ファイルパス
     * @param name ファイ名
     */
    public boolean accept(File path, String name)
    {
        // ファイル拡張子を取得
        int index = name.lastIndexOf('.');
        String fileExtent = name.substring(index + 1).toLowerCase();
        
        // 処理対象となる拡張子
        String extension = WmsParam.DMPDATA_FILE_NAME.substring(
                WmsParam.DMPDATA_FILE_NAME.lastIndexOf('.') + 1).toLowerCase();
        // 処理対象となるファイル名
        String fileName = WmsParam.DMPDATA_FILE_NAME.substring(
                0, WmsParam.DMPDATA_FILE_NAME.lastIndexOf('.'));
         
        if (!extension.equals(fileExtent))
        {
            return false;
        }
        
        if (name.indexOf(fileName) == -1)
        {
            return false;
        }
        return true;
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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTFileFilter.java 3209 2009-03-02 06:34:19Z arai $";
    }
}

