// $Id: LstItemDASCH.java 6322 2009-12-03 01:27:57Z okamura $
package jp.co.daifuku.wms.base.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.base.dasch.LstItemDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.base.common.InParameter;

/**
 * 商品検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 6322 $, $Date: 2009-12-03 10:27:57 +0900 (木, 03 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class LstItemDASCH
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
    public LstItemDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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

        // 現在点の初期化
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
        // 次データへ移動
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
        throw new ScheduleException("This method is not implemented.");
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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 商品マスタエンティティの生成
        Item[] ents = (Item[])_finder.getEntities(start, start + cnt);
        // 返却用の空配列を生成
        List<Params> params = new ArrayList<Params>();
        // パラメータ配列
        Params p = null;

        // 取得した件数分、繰り返す
        for (Item ent : ents)
        {
            // パラメータ配列の生成
            p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // 入数
            p.set(ENTERING_QTY, ent.getEnteringQty());
            // JANコード
            p.set(JAN, ent.getValue(Item.JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(Item.ITF, ""));
            // 上限在庫数
            p.set(UPPER_QTY, ent.getUpperQty());
            // 下限在庫数
            p.set(LOWER_QTY, ent.getLowerQty());
            // ソフトゾーンID
            p.set(SOFT_ZONE_ID, ent.getSoftZoneId());
            // ソフトゾーン名称
            p.set(SOFT_ZONE_NAME, ent.getValue(SoftZone.SOFT_ZONE_NAME, ""));
            // 商品区分
            p.set(TEMPORARY_TYPE, ent.getValue(Item.TEMPORARY_TYPE));
            // 商品区分名称
            p.set(TEMPORARY_TYPE_NAME, DisplayResource.getTemporaryType(p.getString(TEMPORARY_TYPE)));

            // 設定データを配列に格納
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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // 商品マスタ検索キー
        ItemSearchKey sKey = new ItemSearchKey();

        // 検索キーのセット
        // 荷主コード
        sKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        
        if(param.getBoolean(IS_ITEM_CODE_SEARCH))
        {
	        // 開始終了フラグのチェック
	        if (!StringUtil.isBlank(param.getString(FROM_TO_FLAG)))
	        {
	            if (InParameter.FROM_TO_FLAG_FROM.equals(param.getString(FROM_TO_FLAG)))
	            {
	                // 開始側の検索の場合
	                if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
	                {
	                    // 開始商品コードが入力されている場合
	                    // 開始商品コード以降全てを検索
	                    sKey.setItemCode(param.getString(ITEM_CODE), ">=");
	                }
	                if (!StringUtil.isBlank(param.getString(TO_ITEM_CODE)))
	                {
	                    // 終了商品コードが入力されている場合
	                    // 終了商品コード以前全てを検索
	                    sKey.setItemCode(param.getString(TO_ITEM_CODE), "<=");
	                }
	            }
	            else if (InParameter.FROM_TO_FLAG_TO.equals(param.getString(FROM_TO_FLAG)))
	            {
	                // 終了側の検索の場合
	                if (!StringUtil.isBlank(param.getString(TO_ITEM_CODE)))
	                {
	                    // 終了商品コードが入力されている場合
	                    // 終了商品コード以降全てを検索
	                    sKey.setItemCode(param.getString(TO_ITEM_CODE), ">=");
	                }
	                else if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
	                {
	                    // 開始商品コードが入力されている場合
	                    // 開始商品コード以降全てを検索
	                    sKey.setItemCode(param.getString(ITEM_CODE), ">=");
	                }
	            }
	        }
	        else
	        {
	            // 範囲指定の無い検索の場合
	            if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
	            {
	                if (DBFormat.isPatternMatching(param.getString(ITEM_CODE)))
	                {
	                    // ワイルドカード検索
	                    sKey.setItemCode(param.getString(ITEM_CODE));
	                }
	                else
	                {
	                    sKey.setItemCode(param.getString(ITEM_CODE), ">=");
	                }
	            }
	        }
        }
        else
        {
	        // 商品名称
	        if (!StringUtil.isBlank(param.getString(ITEM_NAME)))
	        {
	            if (DBFormat.isPatternMatching(param.getString(ITEM_NAME)))
	            {
	                // ワイルドカード検索
	                sKey.setItemName(param.getString(ITEM_NAME));
	            }
	            else
	            {
	                sKey.setItemName(param.getString(ITEM_NAME), ">=");
	            }        	
	        }
        }
        
        sKey.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

        // 件数確認ではない場合
        if (isSet)
        {
            sKey.setCollect(new FieldName(Item.STORE_NAME, FieldName.ALL_FIELDS));
            sKey.setCollect(SoftZone.SOFT_ZONE_NAME);

            // ソートキーのセット
            if(param.getBoolean(IS_ITEM_CODE_SEARCH))
            {
	            // 商品コード
	            sKey.setItemCodeOrder(true);
            }
            else
            {
            	// 商品名称
	            sKey.setItemNameOrder(true);
            }
        }
        // 設定した検索キーを返却
        return sKey;
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
