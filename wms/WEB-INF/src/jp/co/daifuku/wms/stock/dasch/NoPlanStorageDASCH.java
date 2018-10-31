package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.stock.dasch.NoPlanStorageDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 予定外入庫設定に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:43:46 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class NoPlanStorageDASCH
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
    public NoPlanStorageDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new HostSendFinder(getConnection());
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
        HostSend[] ents = (HostSend[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (HostSend ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(LISTNO, ent.getSettingUnitKey());
            p.set(STORAGE_DAY, WmsFormatter.toDate(ent.getWorkDay()));
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getItemName());
            p.set(AREA_NO, ent.getResultAreaNo());
            p.set(LOCATION_NO, ent.getResultLocationNo());
            p.set(LOT_NO, ent.getResultLotNo());
            p.set(ENTERING_QTY, ent.getEnteringQty());
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getResultQty(), ent.getEnteringQty()));
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getResultQty(), ent.getEnteringQty()));
            p.set(JAN, ent.getJan());
            p.set(ITF, ent.getItf());

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
        HostSendHandler handler = new HostSendHandler(getConnection());

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
        HostSend[] ents = (HostSend[])_finder.getEntities(start, start + cnt);

        for (HostSend ent : ents)
        {
            Params p = new Params();

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(LISTNO, ent.getSettingUnitKey());
            p.set(STORAGE_DAY, WmsFormatter.toDate(ent.getWorkDay()));
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getItemName());
            p.set(AREA_NO, ent.getResultAreaNo());
            p.set(LOCATION_NO, ent.getResultLocationNo());
            p.set(LOT_NO, ent.getResultLotNo());
            p.set(ENTERING_QTY, ent.getEnteringQty());
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getResultQty(), ent.getEnteringQty()));
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getResultQty(), ent.getEnteringQty()));
            p.set(JAN, ent.getJan());
            p.set(ITF, ent.getItf());

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
        HostSendSearchKey key = new HostSendSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 副問合わせ用
        HostSendSearchKey skey = new HostSendSearchKey();

        // 副問合せ（該当するリスト作業Noを検索）
        // 荷主コード
        skey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 作業区分
        if (!StringUtil.isBlank(param.getString(JOB_TYPE)))
        {
            skey.setJobType(param.getString(JOB_TYPE));
        }
        // ハード区分
        if (!StringUtil.isBlank(param.getString(HARD_WARE_TYPE)))
        {
            skey.setHardwareType(param.getString(HARD_WARE_TYPE));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_DATE)))
        {
            skey.setRegistDate(param.getDate(SEARCH_DATE), ">=");
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getString(TO_SEARCH_DATE)))
        {
            skey.setRegistDate(param.getDate(TO_SEARCH_DATE), "<");
        }
        // エリア
        if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
        {
            skey.setResultAreaNo(param.getString(AREA_NO));
        }
        // 棚
        if (!StringUtil.isBlank(param.getString(LOCATION_NO)))
        {
            skey.setResultLocationNo(param.getString(LOCATION_NO));
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            skey.setItemCode(param.getString(ITEM_CODE));
        }
        // リスト作業No
        if (!StringUtil.isBlank(param.getString(SETTING_UNIT_KEY)))
        {
            skey.setSettingUnitKey(param.getString(SETTING_UNIT_KEY));
        }

        skey.setSettingUnitKeyCollect();
        skey.setSettingUnitKeyGroup();

        // 副問合せの該当行を取得
        key.setKey(HostSend.SETTING_UNIT_KEY, skey);

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // リスト作業No
            key.setSettingUnitKeyOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // エリア
            key.setResultAreaNoOrder(true);
            // 入庫棚
            key.setResultLocationNoOrder(true);
            // ロットNo.
            key.setResultLotNoOrder(true);
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
