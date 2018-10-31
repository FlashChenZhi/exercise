// $Id: Part11ListBoxDefine.java 7476 2010-03-09 01:56:22Z okayama $
package jp.co.daifuku.wms.part11.listbox;

import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.ListBoxDefine;


/**
 * 入庫パッケージで使用するリストボックスの定義情報を管理するクラスです。
 * 親画面からリストボックスを呼び出すときに指定する検索条件とURLを定義します。
 * 検索キーやリストボックスの追加が発生した場合、本クラスを変更してください。<BR>
 * 1.検索キー<BR>
 * 2.リストボックスのパス<BR>
 * 
 * Designer :  K.Mori<BR>
 * Maker :     K.Mori<BR>
 * @version $Revision: 7476 $, $Date: 2010-03-09 10:56:22 +0900 (火, 09 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public final class Part11ListBoxDefine
        extends ListBoxDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    // リストボックキー定義

    /**
     * 表示用開始日の受け渡しに使用するキーです。
     */
    public static final String DISPFROMDAY_KEY = "DISPFROMDAY_KEY";

    /**
     * 表示用開始時間の受け渡しに使用するキーです。
     */
    public static final String DISPFROMTIME_KEY = "DISPFROMTIME_KEY";

    /**
     * 表示用終了日の受け渡しに使用するキーです。
     */
    public static final String DISPTODAY_KEY = "DISPTODAY_KEY";

    /**
     * 表示用終了時間の受け渡しに使用するキーです。
     */
    public static final String DISPTOTIME_KEY = "DISPTOTIME_KEY";

    /**
     * ユーザIDの受け渡しに使用するキーです
     */
    public static final String USERID_KEY = "USERID_KEY";

    /**
     * DS番号の受け渡しに使用するキーです
     */
    public static final String DSNUMBER_KEY = "DSNUMBER_KEY";

    /**
     * エリアNoの受け渡しに使用するキーです
     */
    public static final String AREA_NO = "AREA_NO";

    /**
     * 棚Noの受け渡しに使用するキーです
     */
    public static final String LOCATION_NO = "LOCATION_NO";

    /**
     * 商品コードの受け渡しに使用するキーです
     */
    public static final String ITEM_CODE = "ITEM_CODE";

    /**
     * ロットNoの受け渡しに使用するキーです
     */
    public static final String LOT_NO = "LOT_NO";

    /**
     * 棚フォーマットの受け渡しに使用するキーです。
     */
    public static final String STYLE_KEY = "STYLE_KEY";

    /**
     * 画面名称(リソースキー)の受け渡しに使用するキーです
     */
    public static final String PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * ＤＢ区分(データベース表区分)の受け渡しに使用するキーです
     */
    public static final String DBTYPE_KEY = "DBTYPE_KEY";

    /**
     * ＤＢ区分(データベース表)の受け渡しに使用するキーです
     */
    public static final String TABLE_NAME = "TABLE_NAME";

    /**
     * ＤＢ区分：ＤＢ
     */
    public static final String DBTYPE_DB = "0";

    /**
     * ＤＢ区分：ＴＥＭＰ
     */
    public static final String DBTYPE_IMP = "1";

    /**
     * 更新区分：登録
     */
    public static final String UPDATE_KIND_REGIST = "1";

    /**
     * 更新区分：修正
     */
    public static final String UPDATE_KIND_MODIFY = "2";

    /**
     * 更新区分：削除
     */
    public static final String UPDATE_KIND_DELETE = "3";

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
    private Part11ListBoxDefine()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 更新区分値より、名称を取得するメソッドです。 <BR>
     * <BR>
     * @param devision 更新区分値
     * @return String   更新区分名称を返却します
     */
    public static String getDevisionName(String devision)
    {
        if (UPDATE_KIND_REGIST.equals(devision))
        {
            return DisplayText.getText("LBL-W8045");
        }
        else if (UPDATE_KIND_MODIFY.equals(devision))
        {
            return DisplayText.getText("LBL-W8046");
        }
        else if (UPDATE_KIND_DELETE.equals(devision))
        {
            return DisplayText.getText("LBL-W8047");
        }
        else
        {
            return (devision);
        }
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
        return "$Id: Part11ListBoxDefine.java 7476 2010-03-09 01:56:22Z okayama $";
    }
}
