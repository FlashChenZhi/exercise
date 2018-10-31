// $Id: PCTItemMasterInquiryDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.master.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import static jp.co.daifuku.pcart.master.dasch.PCTItemMasterInquiryDASCHParams.*;

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
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品マスタリスト発行に必要なリストボックスや帳票の検索を行います。
 *
 * @version $Revision: 4689 $, $Date:: 2009-07-16 09:23:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTItemMasterInquiryDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** カラム定義 商品画像(<code>ITEM_PICTURE</code>) */
    private static final FieldName ITEM_PICTURE = new FieldName(PCTItem.STORE_NAME, "ITEM_PICTURE");

    /** カラム定義 商品画像(あり・なし)(<code>PICTURE_FLAG</code>) */
    private static final FieldName PICTURE_FLAG = new FieldName(PCTItem.STORE_NAME, "PICTURE_FLAG");

    /** フィールド定義(<code>_PICTURE</code>) */
    private static final FieldName _PICTURE = new FieldName("", "NVL2(DMPCTITEM.ITEM_PICTURE,'true','false')");

    /** 商品画像有無フラグ(全て) */
    private int ALL_ITEM_PICTURE = 0;

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
    public PCTItemMasterInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // Create Finder Object
        _finder = new PCTItemFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
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
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        PCTItem[] ents = (PCTItem[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (PCTItem ent : ents)
        {
            // 出力日付
            p.set(SYS_DAY, getPrintDate());
            // 出力時間
            p.set(SYS_TIME, getPrintDate());
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getConsignorName());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
            p.set(ITF, ent.getItf());
            // 棚No.
            p.set(LOCATION_NO, ent.getLocationNo1());
            // 単重量
            p.set(SINGLE_WEIGHT, ent.getSingleWeight());
            // 重量誤差率
            p.set(WEIGHT_DISTINCT_RATE, ent.getWeightDistinctRate());
            // 最大検品単位数
            p.set(MAX_INSPECTION_UNIT_QTY, ent.getMaxInspectionUnitQty());
            // 最終更新日時
            p.set(LAST_UPDATE, ent.getLastUpdateDate());
            // 最終使用日
            p.set(LAST_USED_DATE, ent.getLastUsedDate());
            // 商品画像
            p.set(ITEM_PICTURE_REGIST,
                    PCTDisplayUtil.getItemPictureString(Boolean.valueOf(String.valueOf(ent.getValue(PICTURE_FLAG)))));
            // メッセージ
            p.set(MESSAGE, ent.getInformation());

            break;
        }
        // return Pram objstc
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
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException NoPrimaryException 一意項目が重複している場合にスローされます。
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
        PCTItemHandler handler = new PCTItemHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        PCTItem[] ents = (PCTItem[])_finder.getEntities(start, start + cnt);

        for (PCTItem ent : ents)
        {
            Params p = new Params();
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_CODE, ent.getConsignorName());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
            p.set(ITF, ent.getItf());
            // 棚No.
            p.set(LOCATION_NO, ent.getLocationNo1());
            // 単重量
            p.set(SINGLE_WEIGHT, ent.getSingleWeight());
            // 重量誤差率
            p.set(WEIGHT_DISTINCT_RATE, ent.getWeightDistinctRate());
            // 最大検品単位数
            p.set(MAX_INSPECTION_UNIT_QTY, ent.getMaxInspectionUnitQty());
            // 最終更新日時
            p.set(LAST_UPDATE, ent.getLastUpdateDate());
            // 最終使用日
            p.set(LAST_USED_DATE, ent.getLastUsedDate());
            // 商品画像
            p.set(ITEM_PICTURE_REGIST,
                    PCTDisplayUtil.getItemPictureString(Boolean.valueOf(String.valueOf(ent.getValue(PICTURE_FLAG)))));
            // メッセージ
            p.set(MESSAGE, ent.getInformation());

            params.add(p);
        }
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
        PCTItemSearchKey key = new PCTItemSearchKey();

        int comparison = 0;

        // 商品マスタチェック
        if (!itemload())
        {
            return null;
        }
        // 荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));

        // 商品コード
        if (!StringUtil.isBlank(param.getString(FROM_ITEM_CODE)) && !StringUtil.isBlank(param.getString(TO_ITEM_CODE)))
        {
            comparison = param.getString(FROM_ITEM_CODE).compareTo(param.getString(TO_ITEM_CODE));
            if (0 == comparison)
            {
                key.setItemCode(param.getString(FROM_ITEM_CODE), "=");
            }
            else
            {
                if (0 > comparison)
                {
                    key.setItemCode(param.getString(FROM_ITEM_CODE), ">=");
                    key.setItemCode(param.getString(TO_ITEM_CODE), "<=");
                }
                else
                {
                    key.setItemCode(param.getString(TO_ITEM_CODE), ">=");
                    key.setItemCode(param.getString(FROM_ITEM_CODE), "<=");
                }
            }


        }
        else
        {
            if (!StringUtil.isBlank(param.getString(FROM_ITEM_CODE)))
            {
                key.setItemCode(param.getString(FROM_ITEM_CODE), ">=");
            }
            else
            {
                if (!StringUtil.isBlank(param.getString(TO_ITEM_CODE)))
                {
                    key.setItemCode(param.getString(TO_ITEM_CODE), "<=");
                }
            }
        }

        // ロット入数
        if (!StringUtil.isBlank(param.getString(LOT_QTY)))
        {
            key.setLotEnteringQty(param.getInt(LOT_QTY));
        }

        // JAN
        if (!StringUtil.isBlank(param.getString(JAN)))
        {
            key.setJan(param.getString(JAN));
        }

        // ケースITF
        if (!StringUtil.isBlank(param.getString(ITF)))
        {
            key.setItf(param.getString(ITF));
        }

        // 単重量
        if (!StringUtil.isBlank(param.getString(FROM_SINGLE_WEIGHT))
                && !StringUtil.isBlank(param.getString(TO_SINGLE_WEIGHT)))
        {
            comparison = param.getString(FROM_SINGLE_WEIGHT).compareTo(param.getString(TO_SINGLE_WEIGHT));
            if (0 == comparison)
            {
                key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), "=");
            }
            else
            {
                if (0 > comparison)
                {
                    key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), ">=");
                    key.setSingleWeight(Double.parseDouble(param.getString(TO_SINGLE_WEIGHT)), "<=");
                }
                else
                {
                    key.setSingleWeight(Double.parseDouble(param.getString(TO_SINGLE_WEIGHT)), ">=");
                    key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), "<=");
                }
            }
        }
        else
        {
            if (!StringUtil.isBlank(param.getString(FROM_SINGLE_WEIGHT)))
            {
                key.setSingleWeight(Double.parseDouble(param.getString(FROM_SINGLE_WEIGHT)), ">=");
            }
            else
            {
                if (!StringUtil.isBlank(param.getString(TO_SINGLE_WEIGHT)))
                {
                    key.setSingleWeight(Double.parseDouble(param.getString(TO_SINGLE_WEIGHT)), "<=");
                }
            }
        }

        // ロケーションNo.
        if (!StringUtil.isBlank(param.getString(FROM_LOCATION_NO))
                && !StringUtil.isBlank(param.getString(TO_LOCATION_NO)))
        {
            comparison = param.getString(FROM_LOCATION_NO).compareTo(param.getString(TO_LOCATION_NO));
            if (0 == comparison)
            {
                key.setLocationNo1(param.getString(FROM_LOCATION_NO), "=");
            }
            else
            {
                if (0 > comparison)
                {
                    key.setLocationNo1((param.getString(FROM_LOCATION_NO)), ">=");
                    key.setLocationNo1((param.getString(TO_LOCATION_NO)), "<=");
                }
                else
                {
                    key.setLocationNo1(param.getString(TO_LOCATION_NO), ">=");
                    key.setLocationNo1(param.getString(FROM_LOCATION_NO), "<=");
                }
            }
        }
        else
        {
            if (!StringUtil.isBlank(param.getString(FROM_LOCATION_NO)))
            {
                key.setLocationNo1(param.getString(FROM_LOCATION_NO), ">=");
            }
            else
            {
                if (!StringUtil.isBlank(param.getString(TO_LOCATION_NO)))
                {
                    key.setLocationNo1(param.getString(TO_LOCATION_NO), "<=");
                }
            }
        }

        // 商品画像有無
        if (param.getInt(ITEM_PICTURE_REGIST) != ALL_ITEM_PICTURE)
        {
            if (PCTDisplayUtil.getItemPictureFlag(PCTDisplayUtil.getItemPicture(param.getInt(ITEM_PICTURE_REGIST))))
            {
                key.setKey(_PICTURE, "TRUE");
            }
            else
            {
                key.setKey(_PICTURE, "FALSE");
            }
        }

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 商品コード昇順
            key.setItemCodeOrder(true);
            // ロットNo.昇順
            key.setLotEnteringQtyOrder(true);

            key.setCollect(ITEM_PICTURE, "NVL2({0},'TRUE','FALSE')", PICTURE_FLAG);
            key.setConsignorCodeCollect();
            key.setConsignorNameCollect();
            key.setCollect(PCTItem.ITEM_CODE);
            key.setCollect(PCTItem.ITEM_NAME);
            key.setCollect(PCTItem.INFORMATION);
            key.setCollect(PCTItem.JAN);
            key.setCollect(PCTItem.ITF);
            key.setCollect(PCTItem.LOCATION_NO_1);
            key.setCollect(PCTItem.LAST_USED_DATE);
            key.setCollect(PCTItem.LAST_UPDATE_DATE);
            key.setCollect(PCTItem.LOT_ENTERING_QTY);
            key.setCollect(PCTItem.WEIGHT_DISTINCT_RATE);
            key.setCollect(PCTItem.MAX_INSPECTION_UNIT_QTY);
            key.setCollect(PCTItem.SINGLE_WEIGHT);
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
