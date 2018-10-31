// $Id: PCTUserResultOrderInfo.java 5738 2009-11-12 13:28:08Z kumano $
// Handler v3.8
package jp.co.daifuku.pcart.retrieval.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;


/**
 * 実績集計照会用のエンティティクラスです。
 * 
 * @version $Revision: 5738 $, $Date: 2009-11-12 22:28:08 +0900 (木, 12 11 2009) $
 * @author  matsuse
 * @author  Last commit: $Author: kumano $
 */
public class PCTUserResultOrderInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /**
     * 作業者数
     */
    public static final FieldName COUNT_WORKER = new FieldName(PCTPickingResult.STORE_NAME, "COUNT_WORKER");

    /**
     * 端末数
     */
    public static final FieldName COUNT_TERMINAL = new FieldName(PCTPickingResult.STORE_NAME, "COUNT_TERMINAL");

    /**
     * 最小開始時刻
     */
    public static final FieldName MIN_WORK_STARTTIME = new FieldName(PCTPickingResult.STORE_NAME, "MIN_WORK_STARTTIME");

    /**
     * 最大終了時刻
     */
    public static final FieldName MAX_WORK_ENDTIME = new FieldName(PCTPickingResult.STORE_NAME, "MAX_WORK_ENDTIME");

    /**
     * レベル
     */
    public static final FieldName LEVEL = new FieldName(PCTPickingResult.STORE_NAME, "LEVEL");
    /**
     * 生産率
     */
    public static final FieldName PRODUCTION_RATE = new FieldName(PCTPickingResult.STORE_NAME, "PRODUCTION_RATE");

    /**
     * ランク
     */
    public static final FieldName RANK = new FieldName(PCTPickingResult.STORE_NAME, "RANK");

    /**
     * 中断時間
     */
    public static final FieldName STOP_TIME = new FieldName(PCTPickingResult.STORE_NAME, "STOP_TIME");

    /**
     * 箱数／オーダー
     */
    public static final FieldName BOX_ORDER = new FieldName(PCTPickingResult.STORE_NAME, "BOX_ORDER");
    
    /**
     * 箱数／予定オーダー
     */
    public static final FieldName BOX_PLAN_ORDER = new FieldName(PCTPickingResult.STORE_NAME, "BOX_PLAN_ORDER");

    /**
     * 行数／オーダー
     */
    public static final FieldName LINE_ORDER = new FieldName(PCTPickingResult.STORE_NAME, "LINE_ORDER");
    
    /**
     * 行数／予定オーダー
     */
    public static final FieldName LINE_PLAN_ORDER = new FieldName(PCTPickingResult.STORE_NAME, "LINE_PLAN_ORDER");

    /**
     * ロット数／行
     */
    public static final FieldName LOT_LINE = new FieldName(PCTPickingResult.STORE_NAME, "LOT_LINE");

    /**
     * バラ数／行
     */
    public static final FieldName PIECE_LINE = new FieldName(PCTPickingResult.STORE_NAME, "PIECE_LINE");

    /**
     * ミス率
     */
    public static final FieldName MISS_PER = new FieldName(PCTPickingResult.STORE_NAME, "MISS_PER");

    /**
     * オーダー数(Total)
     */
    public static final FieldName ORDER_TOTAL = new FieldName(PCTPickingResult.STORE_NAME, "ORDER_TOTAL");

    /**
     * 予定オーダー数(Total)
     */
    public static final FieldName PLAN_ORDER_TOTAL = new FieldName(PCTPickingResult.STORE_NAME, "PLAN_ORDER_TOTAL");
    
    /**
     * 箱数(Total)
     */
    public static final FieldName BOX_TOTAL = new FieldName(PCTPickingResult.STORE_NAME, "BOX_TOTAL");

    /**
     * 行数(Total)
     */
    public static final FieldName LINE_TOTAL = new FieldName(PCTPickingResult.STORE_NAME, "LINE_TOTAL");

    /**
     * 作業数量(Total)
     */
    public static final FieldName WORK_TOTAL = new FieldName(PCTPickingResult.STORE_NAME, "WORK_TOTAL");

    /**
     * 作業数量バラ(Total)
     */
    public static final FieldName PIECE_TOTAL = new FieldName(PCTPickingResult.STORE_NAME, "PIECE_TOTAL");

    /**
     * オーダー数(/H)
     */
    public static final FieldName ORDER_HOUR = new FieldName(PCTPickingResult.STORE_NAME, "ORDER_HOUR");
    
    /**
     * 予定オーダー数(/H)
     */
    public static final FieldName PLAN_ORDER_HOUR = new FieldName(PCTPickingResult.STORE_NAME, "PLAN_ORDER_HOUR");

    /**
     * 箱数(/H)
     */
    public static final FieldName BOX_HOUR = new FieldName(PCTPickingResult.STORE_NAME, "BOX_HOUR");

    /**
     * 行数(/H)
     */
    public static final FieldName LINE_HOUR = new FieldName(PCTPickingResult.STORE_NAME, "LINE_HOUR");

    /**
     * 作業数量(/H)
     */
    public static final FieldName WORK_HOUR = new FieldName(PCTPickingResult.STORE_NAME, "WORK_HOUR");

    /**
     * 作業数量バラ(/H)
     */
    public static final FieldName PIECE_HOUR = new FieldName(PCTPickingResult.STORE_NAME, "PIECE_HOUR");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    /**
     * 明細データ
     */
    private PCTPickingResult _detail;

    /**
     * 集計データ
     */
    private PCTPickingResult _total;

    /**
     * 件数
     */
    private int _count;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * カラム名リストを準備しインスタンスを生成します。
     */
    public PCTUserResultOrderInfo()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 明細データを設定します。
     * @param detail 明細データ 
     */
    public void setDetail(PCTPickingResult detail)
    {
        _detail = detail;
    }

    /**
     * 明細データを取得します。
     * @return 明細データ
     */
    public PCTPickingResult getDetail()
    {
        return _detail;
    }

    /**
     * 集計データを設定します。
     * @param total 集計データ
     */
    public void setTotal(PCTPickingResult total)
    {
        _total = total;
    }

    /**
     * 集計データを取得します。
     * @return 集計データ
     */
    public PCTPickingResult getTotal()
    {
        return _total;
    }

    /**
     * 件数を設定します。
     * @param count 件数
     */
    public void setCount(int count)
    {
        _count = count;
    }

    /**
     * 件数を返します。
     * @return 件数
     */
    public int getCount()
    {
        return _count;
    }

    /**
     * ストアメタデータを返します。
     * @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return null;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
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
        return "$Id: PCTUserResultOrderInfo.java 5738 2009-11-12 13:28:08Z kumano $";
    }

}
