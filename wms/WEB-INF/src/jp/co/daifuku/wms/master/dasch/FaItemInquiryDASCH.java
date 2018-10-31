// $Id: FaItemInquiryDASCH.java 5255 2009-10-23 07:06:58Z kumano $
package jp.co.daifuku.wms.master.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.dasch.FaItemInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * BusiTuneで生成されたDASCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5255 $, $Date:: 2009-10-23 16:06:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class FaItemInquiryDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public FaItemInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // 商品マスタファインダー
        _finder = new ItemFinder(getConnection());
        _finder.open(isForwardOnly());

        // 生成した検索キーでDB検索
        _finder.search(createSearchKey(p, true));

        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;

        // 全行数が現在行数より大きい場合はtrueを返却
        // 上記以外はfalseを返却
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        // 商品マスタエンティティの生成
        Item[] ents = (Item[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (Item ent : ents)
        {
            /* XLS出力しか対応しない為、コメントアウト
             p.set(DFK_DS_NO, getDsNumber());
             p.set(DFK_USER_ID, getUserId());
             p.set(DFK_USER_NAME, getUserName());
             p.set(SYS_DAY, getPrintDate());
             p.set(SYS_TIME, getPrintDate());
             */
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // ｿﾌﾄｿﾞｰﾝ名称
            p.set(SOFT_ZONE_NAME, ent.getValue(SoftZone.SOFT_ZONE_NAME, ""));
            // 商品区分名称
            p.set(TEMPORARY_TYPE_NAME,
                    DisplayResource.getTemporaryType(String.valueOf(ent.getValue(Item.TEMPORARY_TYPE))));
            p.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            break;
        }
        // 設定した配列を返却
        return p;
    }

    /**
     * 生成したファインダーとコネクションの破棄処理<BR>
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        // 商品マスタデータベースハンドラ
        ItemHandler handler = new ItemHandler(getConnection());

        // 対象のデータ件数を取得
        _total = handler.count(createSearchKey(p, false));

        // 取得したデータ件数を返却
        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 返却用の空配列を生成
        List<Params> params = new ArrayList<Params>();
        // 商品マスタエンティティの生成
        Item[] ents = (Item[])_finder.getEntities(start, start + cnt);
        // パラメータ配列
        Params p = null;

        for (Item ent : ents)
        {
            p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(NO, String.valueOf(params.size() + 1 + start));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // ｿﾌﾄｿﾞｰﾝ名称
            p.set(SOFT_ZONE_NAME, ent.getValue(SoftZone.SOFT_ZONE_NAME, ""));
            // 商品区分名称
            p.set(TEMPORARY_TYPE_NAME,
                    DisplayResource.getTemporaryType(String.valueOf(ent.getValue(Item.TEMPORARY_TYPE))));

            params.add(p);
        }
        // 設定した配列を返却
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        ItemSearchKey key = new ItemSearchKey();

        // 在庫有無で全てが選択された時
        if (MasterInParameter.STOCK_EXISTENCE_ALL.equals(param.getString(STOCK_EXISTENCE)))
        {
            key = createAllSearchKey(param, isSet);
        }
        // 在庫有無で有りが選択された時
        else if (MasterInParameter.STOCK_EXISTENCE_EXISTENCE.equals(param.getString(STOCK_EXISTENCE)))
        {
            key = createExistenceSearchKey(param, isSet);
        }
        // 在庫有無で無しが選択された時
        else
        {
            key = createNoExistenceSearchKey(param, isSet);
        }

        return key;
    }

    /**
     * 商品マスタに登録されている商品の検索条件をセットします。<BR> 
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private ItemSearchKey createAllSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        ItemSearchKey key = new ItemSearchKey();
        // 荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        
        // 商品コードの大小チェック
        String[] item_code = WmsFormatter.getFromTo(param.getString(FROM_ITEM_CODE), param.getString(TO_ITEM_CODE));
        
        // 開始商品コードが入力されている場合
        // 開始商品コード以降全てを検索
        if (!StringUtil.isBlank(item_code[0]))
        {
            key.setItemCode(item_code[0], ">=");
        }
        
        // 終了商品コードが入力されている場合
        // 終了商品コード以前全てを検索
        if (!StringUtil.isBlank(item_code[1]))
        {
            key.setItemCode(item_code[1], "<=");
        }
        
        // DMITEMのｿﾌﾄｿﾞｰﾝとDMSOFTZONEのｿﾌﾄｿﾞｰﾝを外部結合
        key.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 取得項目
            key.setItemCodeCollect();
            key.setItemNameCollect();
            key.setCollect(SoftZone.SOFT_ZONE_NAME);
            key.setTemporaryTypeCollect();
            key.setLastUpdateDateCollect();
            // ソート順
            key.setItemCodeOrder(true);
        }
        return key;
    }

    /**
     * 在庫が有る商品の検索条件をセットします。<BR> 
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private ItemSearchKey createExistenceSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        ItemSearchKey key = new ItemSearchKey();
        // 検索条件をセットする
        // 荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));

        // 商品コードの大小チェック
        String[] item_code = WmsFormatter.getFromTo(param.getString(FROM_ITEM_CODE), param.getString(TO_ITEM_CODE));
        
        // 開始商品コードが入力されている場合
        // 開始商品コード以降全てを検索
        if (!StringUtil.isBlank(item_code[0]))
        {
            key.setItemCode(item_code[0], ">=");
        }
        
        // 終了商品コードが入力されている場合
        // 終了商品コード以前全てを検索
        if (!StringUtil.isBlank(item_code[1]))
        {
            key.setItemCode(item_code[1], "<=");
        }
        
        // DMITEMの商品コードとDMSTOCKのITEM_CODEを外部結合
        key.setJoin(Item.ITEM_CODE, Stock.ITEM_CODE);
        // DMITEMのｿﾌﾄｿﾞｰﾝとDMSOFTZONEのｿﾌﾄｿﾞｰﾝを外部結合
        key.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

        // 集約条件をセットする
        // 在庫に同一商品コードが２つ以上登録されている可能性がある為グループ化
        key.setItemCodeGroup();
        key.setItemNameGroup();
        key.setGroup(SoftZone.SOFT_ZONE_NAME);
        key.setTemporaryTypeGroup();
        key.setLastUpdateDateGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 取得項目
            key.setItemCodeCollect();
            key.setItemNameCollect();
            key.setCollect(SoftZone.SOFT_ZONE_NAME);
            key.setTemporaryTypeCollect();
            key.setLastUpdateDateCollect();

            // ソート順
            key.setItemCodeOrder(true);
        }
        return key;
    }

    /**
     * 在庫が無い商品の検索条件をセットします。<BR> 
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private ItemSearchKey createNoExistenceSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        ItemSearchKey key = new ItemSearchKey();
        // 検索条件をセットする
        
        // 荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        
        // 商品コードの大小チェック
        String[] item_code = WmsFormatter.getFromTo(param.getString(FROM_ITEM_CODE), param.getString(TO_ITEM_CODE));
        
        // 開始商品コードが入力されている場合
        // 開始商品コード以降全てを検索
        if (!StringUtil.isBlank(item_code[0]))
        {
            key.setItemCode(item_code[0], ">=");
        }
        
        // 終了商品コードが入力されている場合
        // 終了商品コード以前全てを検索
        if (!StringUtil.isBlank(item_code[1]))
        {
            key.setItemCode(item_code[1], "<=");
        }
        
        key.setKey(Stock.STOCK_ID, null);
        // DMITEMの商品コードとDMSTOCKのITEM_CODEを外部結合
        key.setJoin(Item.ITEM_CODE, "", Stock.ITEM_CODE, "(+)");
        // DMITEMのｿﾌﾄｿﾞｰﾝとDMSOFTZONEのｿﾌﾄｿﾞｰﾝを外部結合
        key.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 取得項目
            key.setItemCodeCollect();
            key.setItemNameCollect();
            key.setCollect(SoftZone.SOFT_ZONE_NAME);
            key.setTemporaryTypeCollect();
            key.setCollect(Stock.STOCK_ID);
            key.setLastUpdateDateCollect();

            // ソート順
            key.setItemCodeOrder(true);
        }

        return key;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
