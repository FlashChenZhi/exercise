package jp.co.daifuku.wms.sort.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.sort.dasch.SortCompleteDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.sort.schedule.SortInParameter;

/**
 * 仕分一括確定に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:42:50 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class SortCompleteDASCH
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
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public SortCompleteDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // TODO : Implement for export
        // Create Finder Object
        _finder = new SortWorkInfoFinder(getConnection());
        // Initialize record counts
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
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        SortWorkInfo[] ents = (SortWorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (SortWorkInfo ent : ents)
        {
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(LOT_NO, ent.getPlanLotNo());
            // ケース入数
            p.set(ENTERING_QTY, (ent.getBigDecimal(Item.ENTERING_QTY, BigDecimal.valueOf(0))).intValue());
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), (ent.getBigDecimal(Item.ENTERING_QTY,
                    BigDecimal.valueOf(0))).intValue()));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), (ent.getBigDecimal(Item.ENTERING_QTY,
                    BigDecimal.valueOf(0))).intValue()));
            // JANコード
            p.set(JAN, ent.getValue(Item.JAN));
            // ケースITF
            p.set(ITF, ent.getValue(Item.ITF));
            // 仕分場所
            p.set(SORT_PLACE, ent.getSortingPlace());
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, String.valueOf(ent.getValue(Customer.CUSTOMER_NAME, "")));
            // 作業状態
            p.set(STATUS_FLAG, ent.getStatusFlag());
            // 作業状態名称
            p.set(STATUS_FLAG_NAME, DisplayResource.getWorkingStatus(ent.getStatusFlag()));
            // ハードウェア区分
            p.set(HARD_WARE_TYPE, ent.getHardwareType());
            // ハードウェア区分名称
            p.set(HARD_WARE_TYPE_NAME, DisplayResource.getHardwareType(ent.getHardwareType()));
            // ユーザ名称
            p.set(USER_NAME, String.valueOf(ent.getValue(Com_loginuser.USERNAME, "")));
            // 端末No.
            p.set(TERMINAL_NO, ent.getTerminalNo());
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
        // TODO : Implement for export or listcell
        //throw new ScheduleException("This method is not implemented.");
        SortWorkInfoHandler handler = new SortWorkInfoHandler(getConnection());

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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        SortWorkInfo[] ents = (SortWorkInfo[])_finder.getEntities(start, start + cnt);

        for (SortWorkInfo ent : ents)
        {
            Params p = new Params();

            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(LOT_NO, ent.getPlanLotNo());
            // ケース入数
            p.set(ENTERING_QTY, (ent.getBigDecimal(Item.ENTERING_QTY, BigDecimal.valueOf(0))).intValue());
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), (ent.getBigDecimal(Item.ENTERING_QTY,
                    BigDecimal.valueOf(0))).intValue()));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), (ent.getBigDecimal(Item.ENTERING_QTY,
                    BigDecimal.valueOf(0))).intValue()));
            // JANコード
            p.set(JAN, ent.getValue(Item.JAN));
            // ケースITF
            p.set(ITF, ent.getValue(Item.ITF));
            // 仕分場所
            p.set(SORT_PLACE, ent.getSortingPlace());
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, String.valueOf(ent.getValue(Customer.CUSTOMER_NAME, "")));
            // 作業状態
            p.set(STATUS_FLAG, ent.getStatusFlag());
            // 作業状態名称
            p.set(STATUS_FLAG_NAME, DisplayResource.getWorkingStatus(ent.getStatusFlag()));
            // ハードウェア区分
            p.set(HARD_WARE_TYPE, ent.getHardwareType());
            // ハードウェア区分名称
            p.set(HARD_WARE_TYPE_NAME, DisplayResource.getHardwareType(ent.getHardwareType()));
            // ユーザ名称
            p.set(USER_NAME, String.valueOf(ent.getValue(Com_loginuser.USERNAME, "")));
            // 端末No.
            p.set(TERMINAL_NO, ent.getTerminalNo());

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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        SortWorkInfoSearchKey key = new SortWorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 予定日
        if (!StringUtil.isBlank(String.valueOf(param.getString(PLAN_DAY))))
        {
            key.setPlanDay(String.valueOf(WmsFormatter.toParamDate(param.getDate(PLAN_DAY))));
        }

        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }

        // バッチNo
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }

        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }

        // ロットNo
        if (!StringUtil.isBlank(param.getString(LOT_NO)))
        {
            key.setPlanLotNo(param.getString(LOT_NO));
        }

        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }

        // 状態フラグ
        if (!StringUtil.isBlank(param.getString(STATUS_FLAG))
                && !param.getString(STATUS_FLAG).equals(SortInParameter.SEARCH_ALL))
        {
            key.setStatusFlag(param.getString(STATUS_FLAG));
        }
        else
        {
            // 該当しない場合は削除以外
            key.setStatusFlag(SortWorkInfo.STATUS_FLAG_DELETE, "!=");
        }

        // 商品マスタ結合
        // 商品マスタ未登録時もエラーとしないために、(+)結合を行います。
        key.setJoin(SortWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        key.setJoin(SortWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");

        // 出荷先マスタ結合
        // 出荷先マスタ未登録時もエラーとしないために、(+)結合を行います。
        key.setJoin(SortWorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        key.setJoin(SortWorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // ログインユーザ設定結合
        // ログインユーザ設定未登録時もエラーとしないために、(+)結合を行います。
        key.setJoin(SortWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // 集約条件定義
        //予定日
        key.setPlanDayGroup();
        // バッチNo
        key.setBatchNoGroup();
        // 商品コード
        key.setItemCodeGroup();
        // 予定ロットNo
        key.setPlanLotNoGroup();
        // 仕分場所
        key.setSortingPlaceGroup();
        // 出荷先コード
        key.setCustomerCodeGroup();
        // 設定単位キー
        key.setSettingUnitKeyGroup();
        // 作業状態
        key.setStatusFlagGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 取得項目定義
            // 予定日
            key.setPlanDayCollect();
            // バッチNo
            key.setBatchNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setCollect(Item.ITEM_NAME, "MAX", Item.ITEM_NAME);
            // 予定数(合計値)
            key.setPlanQtyCollect("SUM");
            // ケース入数
            key.setCollect(Item.ENTERING_QTY, "MAX", Item.ENTERING_QTY);
            // 予定ロットNo.
            key.setPlanLotNoCollect();
            // 仕分場所
            key.setSortingPlaceCollect();
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCollect(Customer.CUSTOMER_NAME, "MAX", Customer.CUSTOMER_NAME);
            // JANコード
            key.setCollect(Item.JAN, "MAX", Item.JAN);
            // ケースITF
            key.setCollect(Item.ITF, "MAX", Item.ITF);
            // 作業状態
            key.setStatusFlagCollect();
            // 作業形態
            key.setHardwareTypeCollect("MAX");
            // ユーザID
            // 一意情報を取得したいので、一応MAX値を取得するようにしておきます
            key.setUserIdCollect("MAX");
            // ユーザ名称
            key.setCollect(Com_loginuser.USERNAME, "MAX", Com_loginuser.USERNAME);
            // 端末No
            key.setTerminalNoCollect("MAX");

            // 取得順序定義
            // 予定日
            key.setPlanDayOrder(true);
            // バッチNo
            key.setBatchNoOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // ロットNo.
            key.setPlanLotNoOrder(true);
            // 仕分場所
            key.setSortingPlaceOrder(true);
            // 出荷先コード
            key.setCustomerCodeOrder(true);
            // 作業状態
            key.setStatusFlagOrder(true);


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
