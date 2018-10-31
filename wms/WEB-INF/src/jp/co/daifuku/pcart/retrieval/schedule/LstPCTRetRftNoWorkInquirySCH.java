// $Id: LstPCTRetRftNoWorkInquirySCH.java 4270 2009-05-13 03:57:38Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.LstPCTRetRftNoWorkInquirySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * Pカート別作業照会一覧のスケジュール処理を行います。
 *
 * @version $Revision: 4270 $, $Date: 2009-05-13 12:57:38 +0900 (水, 13 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTRetRftNoWorkInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド 得意先名称(<code>_REGULAR_CUSTOMER_NAME</code>) */
    private FieldName _REGULAR_CUSTOMER_NAME = new FieldName("", "REGULAR_CUSTOMER_NAME");

    /** 保存用のフィールド 出荷先名称(<code>_CUSTOMER_NAME</code>) */
    private FieldName _CUSTOMER_NAME = new FieldName("", "CUSTOMER_NAME");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public LstPCTRetRftNoWorkInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new PCTRetWorkInfoFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
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
    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return PCTRetWorkInfoSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();


        // set where
        // 号機No.
        if (!StringUtil.isBlank(p.getString(PCART_RFT_NO)))
        {
            searchKey.setKey(Rft.RFT_NO, p.getString(PCART_RFT_NO));
        }

        // 状態フラグ
        searchKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING, "=", "(", "", false);
        searchKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED, "=", "", ")", true);

        // set join(PCTRetWorkInfo Table)
        searchKey.setJoin(Rft.SETTING_UNIT_KEY, PCTRetWorkInfo.SETTING_UNIT_KEY);
        // set join(PCTRetPlan Table)
        searchKey.setJoin(PCTRetWorkInfo.PLAN_UKEY, PCTRetPlan.PLAN_UKEY);

        // set group by
        // 得意先コード
        searchKey.setRegularCustomerCodeGroup();
        // 出荷先コード
        searchKey.setCustomerCodeGroup();

        // set order by
        // 得意先コード
        searchKey.setRegularCustomerCodeOrder(true);
        // 出荷先コード
        searchKey.setCustomerCodeOrder(true);

        // set collect
        // 得意先コード
        searchKey.setRegularCustomerCodeCollect();
        // 得意先名称
        searchKey.setCollect(PCTRetPlan.REGULAR_CUSTOMER_NAME, "MAX", _REGULAR_CUSTOMER_NAME);
        // 出荷先コード
        searchKey.setCustomerCodeCollect();
        // 出荷先名称
        searchKey.setCollect(PCTRetPlan.CUSTOMER_NAME, "MAX", _CUSTOMER_NAME);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        PCTRetWorkInfo[] entities = (PCTRetWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (PCTRetWorkInfo ent : entities)
        {
            Params param = new Params();

            // 得意先コード
            param.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            param.set(REGULAR_CUSTOMER_NAME, String.valueOf(ent.getValue(_REGULAR_CUSTOMER_NAME, "")));
            // 出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            param.set(CUSTOMER_NAME, String.valueOf(ent.getValue(_CUSTOMER_NAME, "")));

            result.add(param);
        }

        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
