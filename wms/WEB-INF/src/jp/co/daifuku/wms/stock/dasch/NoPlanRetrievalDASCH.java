// $Id: NoPlanRetrievalDASCH.java 4808 2009-08-10 06:32:12Z shibamoto $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import static jp.co.daifuku.wms.stock.dasch.NoPlanRetrievalDASCHParams.*;

/**
 * 予定外出庫設定に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4808 $, $Date: 2009-08-10 15:32:12 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class NoPlanRetrievalDASCH
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
    public NoPlanRetrievalDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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

            int enteringQty = ent.getEnteringQty();
            int resultQty = ent.getResultQty();

            // リスト作業No
            p.set(LISTNO, ent.getSettingUnitKey());
            // 出庫日
            p.set(RETRIEVAL_DAY, WmsFormatter.toDate(ent.getWorkDay()));
            // 出庫エリア
            p.set(AREA_NO, ent.getResultAreaNo());
            // 出庫棚
            p.set(LOCATION_NO, ent.getResultLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getItemName());
            // ロットNo
            p.set(LOT_NO, ent.getResultLotNo());
            // ケース入数
            p.set(ENTERING_QTY, enteringQty);
            // 出庫ケース数
            p.set(CASE_QTY, DisplayUtil.getCaseQty(resultQty, enteringQty));
            // 出庫ピース数
            p.set(PIECE_QTY, DisplayUtil.getPieceQty(resultQty, enteringQty));
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
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
        throw new RuntimeException("This method is not implemented.");
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
        // 設定単位キー
        key.setSettingUnitKey(param.getString(SETTING_UKEY));
        // 作業区分：予定外出庫
        key.setJobType(HostSend.JOB_TYPE_NOPLAN_RETRIEVAL);

        if (isSet)
        {
            // set order
            key.setSettingUnitKeyOrder(true);
            key.setResultAreaNoOrder(true);
            key.setResultLocationNoOrder(true);
            key.setItemCodeOrder(true);
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
