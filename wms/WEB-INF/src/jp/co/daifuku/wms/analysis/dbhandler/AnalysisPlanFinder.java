//$Id: AnalysisPlanFinder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.dbhandler;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.analysis.entity.Plan;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * データベースから各予定データ表を検索するためのクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/11/06</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class AnalysisPlanFinder
        extends AbstractDBFinder
{
    // Class filelds -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Class valiable ------------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     */
    public AnalysisPlanFinder(Connection conn)
    {
        super(conn, "DNSTORAGEPLAN");
    }

    // Public methods ------------------------------------------------

    /**
     * 全予定データを検索し、作業予定日取得します。
     * WareNavi_Systemテーブルを検索し、導入パッケージのみ予定日を取得します。
     * 
     * 検索条件：
     * 指定作業日（オプションにて範囲検索）
     * 状態フラグが未開始であること
     * 
     * @param workDate 作業日
     * @return 検索結果の件数
     * @throws CommonException データベースとの接続で発生した例外をそのまま通知します。
     */
    public int search(String workDate)
            throws CommonException
    {
        int count = 0;
        ResultSet countret = null;

        String tableName = "ALL PLAN_TABLE";

        try
        {
            // where条件-----------------------
            StringBuffer range = new StringBuffer(" where ");
            // 作業日入力あり
            if (!StringUtil.isBlank(workDate))
            {
                range.append(" plan_day >= '" + workDate + "' AND ");
            }
            range.append(" status_flag = '");

            WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getClass());
            boolean isInstock = wsc.hasReceivingPack();
            boolean isShipping = wsc.hasShippingPack();
            boolean isSorting = wsc.hasSortingPack();
            boolean isStorage = wsc.hasStoragePack();
            boolean isRetrieval = wsc.hasRetrievalPack();
            if (!isInstock && !isShipping && !isSorting && !isStorage && !isRetrieval)
            {
                return 0;
            }

            // union---------------------------
            // (select plan_day from dninstockplan where status_flag='0')
            // union
            // (select plan_day from dnshippingplan where status_flag='0')
            // union ......
            StringBuffer union = new StringBuffer();
            union.append("(");
            if (isInstock)
            {
                union.append(" (select plan_day from dnreceivingplan ");
                union.append(range);
                union.append(SystemDefine.STATUS_FLAG_UNSTART);
                union.append("' )");
                if (isShipping || isSorting || isStorage || isRetrieval)
                {
                    union.append(" union ");
                }
            }
            if (isShipping)
            {
// TODO okamura 2008/7/18
// 出荷パッケージには未対応のためコメントアウト
//                StringBuffer range2 = new StringBuffer(" where ");
//                // 作業日入力あり
//                if (!StringUtil.isBlank(workDate))
//                {
//                    range2.append(" plan_day >= '" + workDate + "' AND ");
//                }
//                range2.append(" work_status_flag = '");
//
//                union.append(" (select plan_day from dnshipplan ");
//                union.append(range2);
//                union.append(SystemDefine.STATUS_FLAG_UNSTART);
//                union.append("' )");
//                if (isSorting || isStorage || isRetrieval)
//                {
//                    union.append(" union ");
//                }
            }
            if (isSorting)
            {
// TODO okamura 2008/7/18
// 仕分パッケージには未対応のためコメントアウト
//                union.append(" (select plan_day from dnsortplan ");
//                union.append(range);
//                union.append(SystemDefine.STATUS_FLAG_UNSTART);
//                union.append("' )");
//                if (isStorage || isRetrieval)
//                {
//                    union.append(" union ");
//                }
            }
            if (isStorage)
            {
                union.append(" (select plan_day from dnstorageplan ");
                union.append(range);
                union.append(SystemDefine.STATUS_FLAG_UNSTART);
                union.append("' )");
                if (isRetrieval)
                {
                    union.append(" union ");
                }
            }
            if (isRetrieval)
            {
                union.append(" (select plan_day from dnretrievalplan ");
                union.append(range);
                union.append(SystemDefine.STATUS_FLAG_UNSTART);
                union.append("' )");
            }
            union.append(")");

            StringBuffer fmtCountSQL = new StringBuffer("SELECT COUNT(plan_day) COUNT FROM ");
            StringBuffer fmtSQL = new StringBuffer("SELECT plan_day FROM ");
            StringBuffer groupBySQL = new StringBuffer(" group by plan_day");
            StringBuffer orderBySQL = new StringBuffer(" order by plan_day");

            String sqlcountstring = String.valueOf(fmtCountSQL.append(union));
            DEBUG.MSG(DEBUG.HANDLER, sqlcountstring);
            Statement sstmt = HandlerUtil.createStatement(getConnection(), false, isForwardOnly());
            setStatement(sstmt);
            countret = getStatement().executeQuery(sqlcountstring);
            while (countret.next())
            {
                count = countret.getInt("COUNT");
            }
            String sqlstring = String.valueOf(fmtSQL.append(union).append(groupBySQL).append(orderBySQL));
            DEBUG.MSG(DEBUG.HANDLER, sqlstring);
            setResultSet(getStatement().executeQuery(sqlstring));
        }
        catch (SQLException e)
        {
            //6006002=データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, e), tableName);
            //throw (new ReadWriteException("6006002" + MessageResource.DELIM + tableName));
            throw new ReadWriteException(e);
        }
        return count;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractEntity createEntity()
    {
        return (new Plan());
    }

    // Private methods -----------------------------------------------

}
//end of class
