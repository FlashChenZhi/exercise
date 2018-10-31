// $Id: PCTPulldownController.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;


/**
 * 可変プルダウンメニューの表示データ取得を行います。<br>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/07</TD><TD>T.Kishimoto</TD><TD>新規作成</TD></TR>
 * <TR><TD>2007/07/05</TD><TD>Y.Okamura</TD><TD>Stationから作業場、エリアを逆算するよう修正</TD></TR>
 * </TABLE>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  Last commit: $Author: arai $
 */


public class PCTPulldownController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * プルダウン用デリミタ
     */
    public static final String PDDELIM = ",";

    /**
     * ロールIDの先頭文字列
     */
    public enum Distribution
    {
        /** なし */
        UNUSE,
        /** 全て */
        ALL
    }

    /**
     * 初期表示フラグ（初期表示する項目を表す）
     */
    private static final String SELECT_ON = "1";

    /**
     * 初期表示フラグ（初期表示しない項目を表す）
     */
    private static final String SELECT_OFF = "0";

    /**
     * pulldownKey：ユーザー
     */
    private static final String PULL_USER_KEY = "UserPulldown";

    /**
     * pulldownKey：バッチSEQNo
     */
    private static final String PULL_BATCHSEQ_KEY = "BatchSeqPulldown";


    //------------------------------------------------------------
    // class variables (prefix '$')
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 端末No.
     */
    private String _terminalNo = null;

    /**
     * <CODE>Locale</CODE>オブジェクト
     */
    @SuppressWarnings("all")
    private Locale _locale = null;

    /**
     * 検索したプルダウンデータ
     */
    private Map<String, ArrayList<String>> _pulldownDataTable = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * また、端末No.とLocaleを保持とプルダウンデータの初期化を行います。<br>
     * @param conn データベースコネクション
     * @param terminalNo 端末No.
     * @param locale Localeオブジェクト
     * @param caller 呼び出し元クラス
     */
    public PCTPulldownController(Connection conn, String terminalNo, Locale locale, Class caller)
    {
        super(conn, caller);

        _terminalNo = terminalNo;
        _locale = locale;
        _pulldownDataTable = new Hashtable<String, ArrayList<String>>();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ロールプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データ、「全ロール」の表示有無フラグを与えます。
     * 
     * Value,Text,ForignKey,SelectedというデータをArrayListに追加します。<BR>
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     * 
     * @param selected 初期表示を行うロールID。指定しない場合は""を入力してください。
     * @param isAll 「全ロール」を表示する場合はtrue。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public String[] getRollPulldown(String selected, boolean isAll)
            throws CommonException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_USER_KEY + String.valueOf(isAll);
        // 検索済みの場合、初期表示のデータをセットして返す
        if (_pulldownDataTable.containsKey(key))
        {
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        Com_loginuser[] users = getRoll();

        // 該当データがなければ、nullを返す。
        if (users == null || users.length == 0)
        {
            return new String[0];
        }

        // 「全て」をArrayListに追加する
        if (isAll)
        {
            // 「全て」はリソースより取得する。
            String all = DisplayText.getText("CMB-W0004");
            pulldownData.add(0, WmsParam.ALL_AREA_NO + PDDELIM + all + PDDELIM + WmsParam.ALL_AREA_NO + PDDELIM
                    + SELECT_OFF);
        }

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        // ロールIDをArrayListに追加する。
        for (Com_loginuser user : users)
        {
            // Value値のセット
            value = user.getRoleid();
            // Text値のセット
            text = user.getRoleid();
            // ForignKey値のセットする。
            forignKey = value;
            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
    }

    /**
     * バッチSeqNoプルダウンを表示するためのデータを<code>String</code>の配列にて返します。<BR>
     * 初期表示の選択データを与えます。<BR>
     * Value,Text,ForignKey,SelectedというデータをArrayListに追加します。<BR>
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。<BR>
     * 最初にこのメソッドが呼ばれた際に使用するデータを、プルダウンの種別をキーにHashTableに保持します。２度目以降の
     * 利用では、保持したデータをそのまま返します。
     * 
     * @param selected 初期表示を行うバッチSeqNo.。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String[] getBatchSeqPulldown(String selected)
            throws ReadWriteException
    {
        String seldata = "";
        // selectedがnullでない場合は、seldataにセットする。
        if (selected != null)
        {
            seldata = selected;
        }

        // プルダウンデータが検索済みかをチェック
        // HashTableのキーを作成
        String key = PULL_BATCHSEQ_KEY;
        // 検索済みの場合
        if (_pulldownDataTable.containsKey(key))
        {
            // 初期表示のデータをセットして返す
            return changeToSelected(_pulldownDataTable.get(key), seldata, "");
        }
        // プルダウンデータが検索済みかをチェックここまで

        PCTRetPlan[] plans = getBatchSeqNo();

        // 該当データがなければ、nullを返す。
        if (plans == null || plans.length == 0)
        {
            return new String[0];
        }

        ArrayList<String> pulldownData = new ArrayList<String>();

        // Value値
        String value = "";
        // Text値
        String text = "";
        // ForignKey値
        String forignKey = "";

        pulldownData.add(WmsParam.ALL_BATCH_SEQ_NO + PDDELIM + text + PDDELIM + DisplayText.getText("PUL-T0012")
                + PDDELIM + SELECT_ON);

        // バッチSeqNo.をArrayListに追加する。
        for (PCTRetPlan plan : plans)
        {
            value = plan.getBatchSeqNo();
            forignKey = plan.getBatchSeqNo();

            pulldownData.add(value + PDDELIM + text + PDDELIM + forignKey + PDDELIM + SELECT_OFF);
        }

        // プルダウンデータを保持する
        _pulldownDataTable.put(key, pulldownData);

        return changeToSelected(pulldownData, seldata, "");
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
    /**
     * ロールの検索を行います。<br>
     * 
     * @return 検索結果
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Com_loginuser[] getRoll()
            throws CommonException
    {
        Com_loginuserHandler handler = new Com_loginuserHandler(getConnection());
        Com_loginuserSearchKey sKey = new Com_loginuserSearchKey();

        //------------------------------------------------------------
        // 集約条件の指定
        //------------------------------------------------------------
        // ロールID
        sKey.setRoleidGroup();

        //------------------------------------------------------------
        // 取得項目の指定
        //------------------------------------------------------------
        // ロールID
        sKey.setRoleidCollect();

        // 検索を行い、結果を返す。
        return (Com_loginuser[])handler.find(sKey);
    }

    /**
     * 引数で指定された項目の初期表示フラグを"1"に変更します。<BR>
     * ArrayList内の他の項目がすでに"1"の場合はそれを"0"に変更します。<BR>
     * ArrayList内にselectedで指定したvalueが存在しない場合は、全ての初期表示フラグが"0"となります。<BR>
     * 連動プルダウンの場合は、子プルダウンのVALUEだけでは表示すべき項目を識別できません。そこで、
     * parentに親項目のVALUEをセットします。連動プルダウンで無い場合は""をセットします。<BR>
     * 倉庫-ステーション連動プルダウンで子（ステーションプルダウン）にこのメソッドを適用する場合、
     * 倉庫9000のステーション1301を初期表示する場合はselected="1301"、parent="9000"と指定します。
     * 
     * @param array 変更を行うArrayList
     * @param selected 初期表示したい項目（Valueを指定する）
     * @param parent 初期表示する項目の親プルダウンのVALUE。
     * @return <code>String</code>の配列
     */
    protected String[] changeToSelected(ArrayList<String> array, String selected, String parent)
    {
        for (int i = 0; i < array.size(); i++)
        {
            StringTokenizer stk = new StringTokenizer((String)array.get(i), ",", false);
            String value = stk.nextToken();
            String name = stk.nextToken();
            String parentValue = stk.nextToken();
            if (value.equals(selected))
            {
                // 親項目が無効な場合
                if (parent.equals(""))
                {
                    array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_ON);
                }
                // 親項目が有効な場合
                else
                {
                    if (parentValue.equals(parent))
                    {
                        array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_ON);
                    }
                    else
                    {
                        array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_OFF);
                    }
                }
            }
            else
            {
                array.set(i, value + PDDELIM + name + PDDELIM + parentValue + PDDELIM + SELECT_OFF);
            }
        }

        String[] str = new String[array.size()];
        array.toArray(str);
        return str;
    }

    /**
     * バッチSeqNoの検索を行います。<br>
     * @return 検索結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected PCTRetPlan[] getBatchSeqNo()
            throws ReadWriteException
    {
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey sKey = new PCTRetPlanSearchKey();

        // 取得条件
        sKey.setBatchSeqNoCollect();
        // ソート順
        sKey.setBatchSeqNoOrder(true);
        // 集約条件
        sKey.setBatchSeqNoGroup();

        // 検索を行い、結果を返す。
        return (PCTRetPlan[])handler.find(sKey);
    }

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
        return "$Id: PCTPulldownController.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
