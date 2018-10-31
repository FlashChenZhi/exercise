// $Id: ListBoxDefine.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 各パッケージで使用するリストボックスの定義情報を管理するクラスです。<BR>
 * 親画面からリストボックスを呼び出すときに指定する共通のURLまたは受け渡しキーを定義します。<BR>
 * 
 * Designer : M.Sakashita <BR>
 * Maker    : M.Sakashita <BR>
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  Last commit: $Author: arai $
 */
public class ListBoxDefine
        extends Object
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // リストボックスパス定義
    /**
     * Progressのパス
     */
    public static final String LST_PROGRESS = "/Progress.do";

    /**
     * プレビュー表示のパス
     */
    public static final String LST_PREVIEW = "/PDFOut.do";

    /**
     * プレビューファイル名の受け渡しキー
     */
    public static final String PREVIEW_FILEPATH_KEY = "PREVIEW_FILEPATH_KEY";

    /**
     * プレビュー画面タイトルの受け渡しキー
     */
    public static final String PREVIEW_TITLE_KEY = "PREVIEW_TITLE_KEY";

    // PickingCart用
    /**
     * シートダウンロード用パス
     */
    public static final String SHEETDOWNLOAD_PATH = "/jsp/SheetDownLoadDummy.jsp";

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
    protected ListBoxDefine()
    {
        super();
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
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ListBoxDefine.java 3208 2009-03-02 05:42:52Z arai $";
    }
}
