//$Id: OracleSQLGenerator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import oracle.sql.ROWID;

/**
 * OracleデータベースのSQL文を生成するためのクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class OracleSQLGenerator
        extends AbstractSQLGenerator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final String[] SQL_SYNTAXES = {
        "SELECT",
        "COUNT",
        "UPDATE",
        "INSERT",
        "DELETE",
        "FROM",
        "WHERE",
        "GROUP BY",
        "ORDER BY",
        "LIKE",
        "FOR UPDATE",
        "IS NULL",
        "IS NOT NULL",
        "AND",
        "OR",
        "IN",
        "NOT IN",
        "ASC",
        "DESC",
        "WAIT",
        "NOWAIT",
        "INTO",
        "VALUES",
        "NULL",
        "SET",
    };

    private static final String[] SQL_SYMBOLS = {
        ",",
        " ",
        "=",
        "!=",
        "*",
        "(",
        ")",
    };

    private static final String[] SQL_KEYWORDS = {
        // "NULL",
        "TO_DATE",
        "TO_CHAR",
        "TO_TIMESTAMP",
    };

    private static final String LOCK_SQL = "LOCK TABLE {0} IN EXCLUSIVE MODE {1}";

    /**
     * Timestamp型を利用する精度のしきい値
     */
    private static final int THRESHOLD_VALUE = 3;

    /** 集約関数一覧 */
    private static final String[] INTENSIVE_FUNCTIONS = {
        "AVG",
        "COLLECT",
        "CORR",
        "COUNT",
        "COVAR_POP",
        "COVAR_SAMP",
        "CUME_DIST",
        "DENSE_RANK",
        "FIRST",
        "GROUP_ID",
        "GROUPING",
        "LAST",
        "MAX",
        "MEDIAN",
        "MIN",
        "PERCENTILE_CONT",
        "PERCENTILE_DISC",
        "PERCENT_RANK",
        "RANK",
        "REGR_",
        "STATS_BINOMIAL_TEST",
        "STATS_CROSSTAB",
        "STATS_F_TEST",
        "STATS_KS_TEST",
        "STATS_MODE",
        "STATS_MW_TEST",
        "STATS_ONE_WAY_ANOVA",
        "STATS_T_TEST_",
        "STATS_WSR_TEST",
        "STDDEV",
        "STDDEV_POP",
        "STDDEV_SAMP",
        "SUM",
        "VAR_POP",
        "VAR_SAMP",
        "VARIANCE",
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ<br>
     * ストアメタデータを指定してインスタンス化します<br>
     * このストアメタデータはTimestamp型のSQL生成でフィールド精度を
     * 取得するために使用されます
     * @param metaData ストアメタデータ
     */
    public OracleSQLGenerator(StoreMetaData metaData)
    {
        super(metaData);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public String getLOCKSQL(String storeName, int waitSec)
    {
        String wait = (waitSec > 0) ? ""
                                   : SQL_SYNTAXES[STX_NOWAIT];

        String sec = (waitSec > 0) ? String.valueOf(waitSec)
                                  : "";
        String[] prm = {
            storeName,
            wait,
            sec,
        };
        return SimpleFormat.format(LOCK_SQL, prm).trim();
    }

    /**
     * {@inheritDoc}
     */
    public String getFINDSQL(SQLSearchKey key)
    {
        String sql = super.getFINDSQL(key);

        return sql;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public String[] getSymbols()
    {
        return SQL_SYMBOLS;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getSQLSyntaxes()
    {
        return SQL_SYNTAXES;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getSQLKeywords()
    {
        return SQL_KEYWORDS;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 集約関数の一覧を返します。
     * @return 集約関数の一覧
     */
    @Override
    String[] getIntensiveFunctions()
    {
        return INTENSIVE_FUNCTIONS;
    }

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * オブジェクトの内容に従って、SQLで使用できる形式にフォーマットします。<br>
     * OracleのROWIDに対応しています。
     * @param field 対象フィールド
     * @param val 対象の値
     * @return フォーマット済み文字列
     */
    protected String formatValue(FieldName field, Object val)
    {
        Object fval = val;
        if (val instanceof ROWID)
        {
            ROWID ridv = (ROWID)val;
            fval = ridv.stringValue();
        }
        // TODO OracleではTimestamp型が用意されているため、フィールドの
        // 精度によって使い分けます
        else if (val instanceof Timestamp)
        {
            return format(getPrecision(field), (Timestamp)val);
        }
        else if (val instanceof Date)
        {
            return format(getPrecision(field), (Date)val);
        }
        return super.formatValue(field, fval);
    }

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
        return "$Id: OracleSQLGenerator.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * 日付型データをデータベースの日付型フィールドにセットするために、フォーマットします。<br>
     * 精度によって、ORACLEのTO_DATE関数、TO_TIMESTAMP関数を使用する形に編集します。<br>
     * ただし与えられた文字列が<code>null</code>だった場合、文字列"null"を返します。
     * @param precision 精度
     * @param dat  フォーマットの対象となるDate型データを指定します。
     * @return     フォーマットされた文字列を返します。
     */
    private String format(int precision, Date dat)
    {
        String str = null;
        // フォーマット対象のデータがSysDate型であった場合は、DBのシステム日付を利用する
        // ものとして、精度に応じてOracleのキーワードを返します。
        if (dat instanceof SysDate)
        {
            if (THRESHOLD_VALUE > precision)
            {
                return "SYSDATE";
            }
            else
            {
                return "SYSTIMESTAMP";
            }
        }
        else
        {
            if (THRESHOLD_VALUE > precision)
            {
                SimpleDateFormat sformatter = new SimpleDateFormat("yyyyMMddHHmmss");
                StringBuffer stbf = new StringBuffer(32);
                // TO_DATE("yyyymmddhhmmdd（実際には数値が入る）", 'yyyymmddhh24miss')の形に編集
                stbf.append("TO_DATE('" + sformatter.format(dat) + "', 'yyyymmddhh24miss')");
                str = String.valueOf(stbf);
            }
            else
            {
                SimpleDateFormat sformatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                StringBuffer stbf = new StringBuffer(64);
                stbf.append("TO_TIMESTAMP('" + sformatter.format(dat) + "', 'yyyymmddhh24missff3')");
                str = String.valueOf(stbf);
            }
        }
        return str;
    }

}
