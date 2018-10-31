// $Id: AsrsToolListBoxDefine.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox;

import jp.co.daifuku.wms.base.common.ListBoxDefine;


/**
 * AS/RSパッケージで使用するリストボックスの定義情報を管理するクラスです。
 * 親画面からリストボックスを呼び出すときに指定する検索条件とURLを定義します。
 * 検索キーやリストボックスの追加が発生した場合、本クラスを変更してください。<BR>
 * 1.検索キー<BR>
 * 2.リストボックスのパス<BR>
 * 
 * Designer :  Y.Osawa<BR>
 * Maker :     Y.Osawa<BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Last commit: $Author: admin $
 */
public final class AsrsToolListBoxDefine
        extends ListBoxDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    // リストボックキー定義
    /**
     * 作業場一覧リストボックスのパスです
     */
    public static final String LST_TOOL_WORK_PLACE = "/asrs/tool/listbox/workplacelist/WorkPlaceList.do";

    /**
     * ステーションリスト一覧リストボックスのパスです
     */
    public static final String LST_TOOL_STASION = "/asrs/tool/listbox/stationlist/StationList.do";

    /**
     * 製番一覧リストボックスのパスです
     */
    public static final String LST_TOOL_PRODUCT = "/asrs/tool/listbox/productionlist/ProductionList.do";

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
    private AsrsToolListBoxDefine()
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
        return "$Id: AsrsToolListBoxDefine.java 87 2008-10-04 03:07:38Z admin $";
    }
}
