// $Id: LstUserIdDASCH.java 4803 2009-08-10 06:24:09Z shibamoto $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.LstUserIdDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * ユーザー一覧リストボックスに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4803 $, $Date:: 2009-08-10 15:24:09 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstUserIdDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド ユーザID(<code>USER_ID</code>) */
    private FieldName _USER_ID = new FieldName("", "USER_ID");

    /** 保存用のフィールド ユーザ名称(<code>USER_NAME</code>) */
    private FieldName _USER_NAME = new FieldName("", "USER_NAME");

    // private String _instanceVar ;
    private final String _DAIFUKU_USER = "DAIFUKU";

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
    public LstUserIdDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // PCT出庫作業情報
        if (PCTRetrievalInParameter.SEARCH_TABLE_WORK.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTRetWorkInfoFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchRetWorkInfoKey(p, true));

            // 現在点の初期化
            _current = -1;
        }
        // PCT出庫実績情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_RESULT.equals(p.get(SEARCHTABLE)))
        {
            _finder = new PCTRetResultFinder(getConnection());
            // Initialize record counts
            _finder.open(isForwardOnly());
            // 生成した検索キーでDB検索
            _finder.search(createSearchRetResultKey(p, true));

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
        throw new ScheduleException("This method is not implemented.");
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
        // PCT出庫作業情報
        if (PCTRetrievalInParameter.SEARCH_TABLE_WORK.equals(p.get(SEARCHTABLE)))
        {
            PCTRetWorkInfoHandler handler = new PCTRetWorkInfoHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchRetWorkInfoKey(p, false));

            // 取得したデータ件数を返却
            return _total;
        }
        // PCT出庫実績情報
        else if (PCTRetrievalInParameter.SEARCH_TABLE_RESULT.equals(p.get(SEARCHTABLE)))
        {
            PCTRetResultHandler handler = new PCTRetResultHandler(getConnection());

            // 対象情報のデータ件数を取得
            _total = handler.count(createSearchRetResultKey(p, false));

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
        List<Params> params = new ArrayList<Params>();
        Entity[] ents = (Entity[])_finder.getEntities(start, start + cnt);

        for (Entity ent : ents)
        {
            Params p = new Params();
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // ユーザID
            p.set(USER_ID, ent.getValue(_USER_ID));
            // ユーザ名称
            p.set(USER_NAME, ent.getValue(_USER_NAME));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------    
    /**
     * 入力されたパラメータをもとにPCT出庫作業情報を検索するSQL文を発行します。<BR>
     * 検索を行う<code>StoragePlanFinder</code>はインスタンス変数として保持します。<BR>
     * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
     * <BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchRetWorkInfoKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey key = new PCTRetWorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // ユーザID
        if (!StringUtil.isBlank(param.getString(USER_ID)))
        {
            key.setUserId(param.getString(USER_ID), ">=", "", "", true);
        }

        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 予定日
        if (!StringUtil.isBlank(param.getString(PLAN_DAY)))
        {
            key.setPlanDay(WmsFormatter.toParamDate(param.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリアでない時は、条件をセットする
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(param.getString(REGULAR_CUSTOMER_CODE)))
        {
            key.setRegularCustomerCode(param.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(param.getString(ORDER_NO)))
        {
            key.setResultOrderNo(param.getString(ORDER_NO));
        }

        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }

        // 状態フラグ
        if (InParameter.SEARCH_ALL.equals(param.getString(JOB_STATUS)))
        {
            // 全ての場合はセットしない
        }
        else
        {
            // 状態フラグをセット
            key.setStatusFlag(param.getString(JOB_STATUS));
        }


        // ユーザIDが指定されていたら、それ以降全てを検索
        key.setUserId(_DAIFUKU_USER, "!=", "", "", true);

        key.setJoin(PCTRetWorkInfo.USER_ID, Com_loginuser.USERID);

        key.setUserIdGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setUserIdOrder(true);

            // ユーザーID
            key.setCollect(PCTRetWorkInfo.USER_ID, "", _USER_ID);

            // ユーザー名称
            key.setCollect(Com_loginuser.USERNAME, "MAX", _USER_NAME);

        }

        return key;
    }

    /**
     * 入力されたパラメータをもとにPCT出庫実績情報を検索するSQL文を発行します。<BR>
     * 検索を行う<code>ResultViewFinder</code>はインスタンス変数として保持します。<BR>
     * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
     * <BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchRetResultKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT出庫実績情報検索キー
        PCTRetResultSearchKey key = new PCTRetResultSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // ユーザID
        if (!StringUtil.isBlank(param.getString(USER_ID)))
        {
            key.setUserId(param.getString(USER_ID), ">=", "", "", true);
        }

        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 作業日
        if (!StringUtil.isBlank(param.getString(WORK_DAY)))
        {
            key.setWorkDay(WmsFormatter.toParamDate(param.getDate(WORK_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリアでない時は、条件をセットする
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(param.getString(REGULAR_CUSTOMER_CODE)))
        {
            key.setRegularCustomerCode(param.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(param.getString(ORDER_NO)))
        {
            key.setResultOrderNo(param.getString(ORDER_NO));
        }

        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }

        // 状態フラグ
        if (InParameter.SEARCH_ALL.equals(param.getString(JOB_STATUS)))
        {
            // 全ての場合はセットしない
        }
        else
        {
            // 状態フラグをセット
            key.setStatusFlag(param.getString(JOB_STATUS));
        }

        // ユーザIDが指定されていたら、それ以降全てを検索
        key.setUserId(_DAIFUKU_USER, "!=", "", "", true);

        key.setUserIdGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setUserIdOrder(true);

            // ユーザーID
            key.setCollect(PCTRetResult.USER_ID, "", _USER_ID);

            // ユーザー名称
            key.setCollect(PCTRetResult.USER_NAME, "MAX", _USER_NAME);

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
