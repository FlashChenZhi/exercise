// $Id: PCTLstItemDASCH.java 4803 2009-08-10 06:24:09Z shibamoto $
package jp.co.daifuku.pcart.master.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.dasch.PCTLstItemDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctFinder;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctHandler;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.WeightDistinct;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品検索リストボックスに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4803 $, $Date: 2009-08-10 15:24:09 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTLstItemDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** 保存用のフィールド 商品名称(<code>ITEM_NAME</code>) */
    private FieldName _ITEM_NAME = new FieldName("", "ITEM_NAME");

    /** 保存用のフィールド 商品コード(<code>ITEM_CODE</code>) */
    private FieldName _ITEM_CODE = new FieldName("", "ITEM_CODE");

    /** 保存用のフィールド ロット入数(<code>LOT_QTY</code>) */
    private FieldName _LOT_QTY = new FieldName("", "_LOT_QTY");

    /** 保存用のフィールド JANコード(<code>JAN</code>) */
    private FieldName _JAN = new FieldName("", "JAN");

    /** 保存用のフィールド ケースITF(<code>ITF</code>) */
    private FieldName _ITF = new FieldName("", "ITF");

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
    public PCTLstItemDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // PCT商品マスタ情報
        if (PCTMasterInParameter.SEARCH_TABLE_MASTER.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTItemFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchItemKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
        // 重量差異情報
        if (PCTMasterInParameter.SEARCH_TABLE_WEIGTHDISTINCT.equals(p.get(SEARCHTABLE)))
        {
            _finder = new WeightDistinctFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchWeightDistinctKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
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
        //  エンティティ
        Entity[] ents = _finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (Entity ent : ents)
        {
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(_ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(_ITEM_NAME));
            // ロット入数
            p.set(LOT_QTY, ent.getValue(_LOT_QTY));
            // JANコード
            p.set(JAN, ent.getValue(_JAN));
            // ケースITF
            p.set(ITF, ent.getValue(_ITF));

            // for文抜け(一件のみ取得)
            break;
        }
        // 生成した配列を返却
        return p;
    }

    /**
     *
     * finder,Connection close
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
        // PCT商品マスタロード中チェック
        if (!itemload())
        {
            return -1;
        }

        // PCT商品マスタ情報
        if (PCTMasterInParameter.SEARCH_TABLE_MASTER.equals(p.get(SEARCHTABLE)))
        {
            PCTItemHandler handler = new PCTItemHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchItemKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // 重量差異マスタ情報
        else if (PCTMasterInParameter.SEARCH_TABLE_WEIGTHDISTINCT.equals(p.get(SEARCHTABLE)))
        {
            WeightDistinctHandler handler = new WeightDistinctHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchWeightDistinctKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // 対象データなし
        else
        {
            _total = 0;
            return _total;
        }
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
        // エンティティの生成
        Entity[] ents = _finder.getEntities(start, start + cnt);

        // 取得した件数分、繰り返す
        for (Entity ent : ents)
        {
            // パラメータ配列の生成
            Params p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(_ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(_ITEM_NAME));
            // ロット入数
            p.set(LOT_QTY, ent.getValue(_LOT_QTY));
            // JANコード
            p.set(JAN, ent.getValue(_JAN));
            // ケースITF
            p.set(ITF, ent.getValue(_ITF));
            // 設定した値を配列に格納
            params.add(p);
        }
        // 設定した配列を返却
        return params;
    }

    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @return 取込みフラグを確認
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    protected boolean itemload()
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため処理できません。
                setMessage(WmsMessageFormatter.format(6024068));
                return false;
            }
        }
        return true;
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
    private SearchKey createSearchItemKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT商品マスタ情報検索キー
        PCTItemSearchKey key = new PCTItemSearchKey();

        // 検索キーのセット
        // 荷主コード検索
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE), ">=");
        }
        // 商品コード(TO)
        if (!StringUtil.isBlank(param.getString(TO_ITEM_CODE)))
        {
            key.setItemCode(param.getString(TO_ITEM_CODE), "<=");
        }
        // ロット入数
        if (!StringUtil.isBlank(param.getString(LOT_QTY)))
        {
            key.setLotEnteringQty(param.getInt(LOT_QTY));
        }
        // JANコード
        if (!StringUtil.isBlank(param.getString(JAN)))
        {
            key.setJan(param.getString(JAN));
        }
        // ケースITF
        if (!StringUtil.isBlank(param.getString(ITF)))
        {
            key.setItf(param.getString(ITF));
        }
        // 最終更新日時
        if (!StringUtil.isBlank(param.getString(LAST_UPDATE_DATE)))
        {
            key.setLastUpdateDate(WmsFormatter.getToSearchDate(param.getDate(LAST_UPDATE_DATE), null), "<=");
        }

        // 集約条件
        key.setItemCodeGroup();
        key.setLotEnteringQtyGroup();
        key.setJanGroup();
        key.setItfGroup();

        if (isSet)
        {
            // ソートキーのセット
            // 商品コード昇順
            key.setItemCodeOrder(true);
            // ロット入数昇順
            key.setLotEnteringQtyOrder(true);

            // 取得キーのセット
            // 取得項目
            key.setCollect(PCTItem.ITEM_CODE, "", _ITEM_CODE);
            // 商品名称
            key.setCollect(PCTItem.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(PCTItem.LOT_ENTERING_QTY, "MAX", _LOT_QTY);
            // JANコード
            key.setCollect(PCTItem.JAN, "MAX", _JAN);
            // ケースITF
            key.setCollect(PCTItem.ITF, "MAX", _ITF);
        }

        return key;
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchWeightDistinctKey(Params param, boolean isSet)
            throws CommonException
    {
        WeightDistinctSearchKey key = new WeightDistinctSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 荷主コード検索
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }

        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            // 商品コードが指定されていたら、それ以降全てを検索
            key.setItemCode(param.getString(ITEM_CODE), ">=");
        }
        // 結合条件の指定(PLAN_UKEY)
        key.setJoin(WeightDistinct.ITEM_CODE, "", PCTItem.ITEM_CODE, "(+)");
        key.setJoin(WeightDistinct.CONSIGNOR_CODE, "", PCTItem.CONSIGNOR_CODE, "(+)");
        key.setJoin(WeightDistinct.LOT_ENTERING_QTY, "", PCTItem.LOT_ENTERING_QTY, "(+)");

        // 集約条件
        key.setItemCodeGroup();
        key.setLotEnteringQtyGroup();
        key.setGroup(PCTItem.JAN);
        key.setGroup(PCTItem.ITF);

        if (isSet)
        {
            // 商品コード昇順
            key.setItemCodeOrder(true);
            // ロット入数昇順
            key.setLotEnteringQtyOrder(true);

            // 取得キーのセット
            // 取得項目
            key.setCollect(WeightDistinct.ITEM_CODE, "", _ITEM_CODE);
            // 商品名称
            key.setCollect(WeightDistinct.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(WeightDistinct.LOT_ENTERING_QTY, "", _LOT_QTY);
            // JANコード
            key.setCollect(PCTItem.JAN, "", _JAN);
            // ケースITF
            key.setCollect(PCTItem.ITF, "", _ITF);
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
