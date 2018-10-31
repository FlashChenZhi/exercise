// $Id: AbstractSQLGenerator.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.handler.Conditions;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * SQL文を生成するための抽象クラスです。<br>
 * 各データベースに対応するために、このクラスを継承したクラスを用意
 * してください。
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public abstract class AbstractSQLGenerator
        implements SQLGenerator
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final int BUFFER_SIZE = 512;

    private static final String ZERO = "0";

    private static final int DEFAULT_DATE_PREC = 0;

    //------------------------------
    // 記号一覧
    //------------------------------
    /** 記号 (フィールド区切り文字) */
    public static final int SYM_DLM_FIELD = 0;

    /** 記号 (命令区切り文字) */
    public static final int SYM_DLM_OPERATE = 1;

    /** 記号 (EQUAL) */
    public static final int SYM_OPR_EQUAL = 2;

    /** 記号 (NOT EQUAL) */
    public static final int SYM_OPR_NOT_EQUAL = 3;

    /** 記号 (ALL) */
    public static final int SYM_OPR_ALL = 4;

    /** 記号 (左括弧) */
    public static final int SYM_OPR_LEFT_BRACE = 5;

    /** 記号 (右括弧) */
    public static final int SYM_OPR_RIGHT_BRACE = 6;

    /* THIS IS SAMPLE
     private static final String[] SQL_SYMBOLS = {
     ",",
     " ",
     "=",
     "!=",
     "*",
     "(",
     ")",
     } ;
     */

    //------------------------------
    // 命令一覧
    //------------------------------
    /** SQL (SELECT) */
    public static final int STX_SELECT = 0;

    /** SQL (COUNT) */
    public static final int STX_COUNT = 1;

    /** SQL (UPDATE) */
    public static final int STX_UPDATE = 2;

    /** SQL (INSERT) */
    public static final int STX_INSERT = 3;

    /** SQL (DELETE) */
    public static final int STX_DELETE = 4;

    /** SQL (FROM) */
    public static final int STX_FROM = 5;

    /** SQL (WHERE) */
    public static final int STX_WHERE = 6;

    /** SQL (GROUP BY) */
    public static final int STX_GROUP_BY = 7;

    /** SQL (ORDER BY) */
    public static final int STX_ORDER_BY = 8;

    /** SQL (LIKE) */
    public static final int STX_LIKE = 9;

    /** SQL (FOR UPDATE) */
    public static final int STX_FOR_UPDATE = 10;

    /** SQL (IS NULL) */
    public static final int STX_IS_NULL = 11;

    /** SQL (IS NOT NULL) */
    public static final int STX_IS_NOT_NULL = 12;

    /** SQL (AND) */
    public static final int STX_AND = 13;

    /** SQL (OR) */
    public static final int STX_OR = 14;

    /** SQL (IN) */
    public static final int STX_IN = 15;

    /** SQL (NOT IN) */
    public static final int STX_NOT_IN = 16;

    /** SQL (ASC) */
    public static final int STX_ASC = 17;

    /** SQL (DESC) */
    public static final int STX_DESC = 18;

    /** SQL (WAIT) */
    public static final int STX_WAIT = 19;

    /** SQL (NOWAIT) */
    public static final int STX_NOWAIT = 20;

    /** SQL (INTO) */
    public static final int STX_INTO = 21;

    /** SQL (VALUES) */
    public static final int STX_VALUES = 22;

    /** SQL (NULL) */
    public static final int STX_NULL = 23;

    /** SQL (SET) */
    public static final int STX_SET = 24;

    /* THIS IS SAMPLE
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
     "FOR UPDATE"
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
     } ;
     */

    /** エスケープ文字 */
    public static final String ESCAPE_CHAR = "$";

    /** SQL (ESCAPE) */
    public static final String LIKE_ESCAPE = "ESCAPE '" + ESCAPE_CHAR + "'";

    /** アンダーバー */
    public static final String UNDERSCORE = "_";

    /** パーセント */
    public static final String PERCENT = "%";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;
    // TODO Timestanp型のSQL生成でフィールド精度を取得するために追加
    private StoreMetaData _storeMetaData = null;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

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
    protected AbstractSQLGenerator(StoreMetaData metaData)
    {
        _storeMetaData = metaData;
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * データベースタイプに応じたSQLジェネレータを返します。
     * @param dbmsType StoreMetaDataに設定されているデータベースタイプ
     * @param metaData ストアメタデータ
     * @return 対応するSQLジェネレータ
     */
    public static SQLGenerator getInstance(String dbmsType, StoreMetaData metaData)
    {
        String tdb = dbmsType.trim().toLowerCase();
        if (DBMS_TYPE_ORACLE.equals(tdb))
        {
            return new OracleSQLGenerator(metaData);
        }
        else if (DBMS_TYPE_INFORMIX.equals(tdb))
        {
            return null;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getWhereClause(SQLSearchKey key)
    {
        List<Conditions> condList = key.getLimitConditionList();
        List<Conditions> joinc = key.getJoinConditionList();

        return createWhere(condList, joinc);
    }

    /**
     * {@inheritDoc}
     */
    public String getFINDSQL(SQLSearchKey key)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        // get list of conditions
        List<Conditions> collList = key.getCollectConditionList();
        List<Conditions> condList = key.getLimitConditionList();
        List<Conditions> grpList = key.getGroupConditionList();
        List<Conditions> orderList = key.getOrderConditionList();
        List<Conditions> joinList = key.getJoinConditionList();

        List[] allList = {
                collList,
                condList,
                grpList,
                orderList,
                joinList,
        };
        List<String> storeNameList = createStoreNameList(allList);

        // get symbol and syntax
        String op = getSymbols()[SYM_DLM_OPERATE];
        String select = getSQLSyntaxes()[STX_SELECT];

        // build select SQL
        sqlBuff.append(select);
        sqlBuff.append(op);
        sqlBuff.append(createCollect(storeNameList, key));
        sqlBuff.append(op);
        sqlBuff.append(createFrom(storeNameList, key));
        sqlBuff.append(op);
        sqlBuff.append(createWhere(condList, joinList));
        sqlBuff.append(op);
        sqlBuff.append(createGroup(grpList));
        sqlBuff.append(op);
        sqlBuff.append(createOrder(orderList));

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public String getFINDFORUPDATESQL(SQLSearchKey key)
    {
        return getFINDFORUPDATEWAITSQL(key, 0);
    }

    /**
     * {@inheritDoc}
     */
    public String getFINDFORUPDATEWAITSQL(SQLSearchKey key, int waitSec)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        // get list of conditions
        List<Conditions> collList = key.getCollectConditionList();
        List<Conditions> condList = key.getLimitConditionList();
        List<Conditions> orderList = key.getOrderConditionList();
        List<Conditions> joinList = key.getJoinConditionList();

        List[] allList = {
                collList,
                condList,
                orderList,
                joinList,
        };
        List<String> storeNameList = createStoreNameList(allList);

        // get symbol and syntax
        String op = getSymbols()[SYM_DLM_OPERATE];

        String[] stxes = getSQLSyntaxes();
        String select = stxes[STX_SELECT];
        String forUpdate = stxes[STX_FOR_UPDATE];

        // build select SQL
        sqlBuff.append(select);
        sqlBuff.append(op);
        sqlBuff.append(createCollect(storeNameList, key));
        sqlBuff.append(op);
        sqlBuff.append(createFrom(storeNameList, key));
        sqlBuff.append(op);
        sqlBuff.append(createWhere(condList, joinList));
        sqlBuff.append(op);
        sqlBuff.append(createOrder(orderList));
        sqlBuff.append(op);
        sqlBuff.append(forUpdate);
        sqlBuff.append(op);

        if (waitSec < 0)
        {
            sqlBuff.append(stxes[STX_NOWAIT]);
        }
        else
        {
            if (waitSec != 0)
            {
                sqlBuff.append(stxes[STX_WAIT]);
                sqlBuff.append(op);
                sqlBuff.append(waitSec);
            }
        }

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public String getCOUNTSQL(SQLSearchKey key)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        // get list of conditions
        List<Conditions> collList = key.getCollectConditionList();
        List<Conditions> condList = key.getLimitConditionList();
        List<Conditions> grpList = key.getGroupConditionList();
        List<Conditions> joinList = key.getJoinConditionList();

        List[] allList = {
                collList,
                condList,
                grpList,
                joinList,
        };
        List<String> storeNameList = createStoreNameList(allList);

        // get symbol and syntax
        String op = getSymbols()[SYM_DLM_OPERATE];
        String select = getSQLSyntaxes()[STX_SELECT];

        // build select SQL
        sqlBuff.append(select);
        sqlBuff.append(op);
        sqlBuff.append(createCountCollect(collList, grpList));
        sqlBuff.append(op);
        sqlBuff.append(createFrom(storeNameList, key));
        sqlBuff.append(op);
        sqlBuff.append(createWhere(condList, joinList));
        sqlBuff.append(op);
        sqlBuff.append(createGroup(grpList));

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * SQL文を実行した結果、何行返されるかを取得するためのSQLを生成します。
     * @param key 検索キー
     * @return 行数を返すSQL文
     */
    public String getRecCOUNTSQL(SQLSearchKey key)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        String[] idc = getSymbols();
        String op = getSymbols()[SYM_DLM_OPERATE];
        String select = getSQLSyntaxes()[STX_SELECT];

        sqlBuff.append(select);
        sqlBuff.append(op);
        sqlBuff.append(getSQLSyntaxes()[STX_COUNT]);
        sqlBuff.append(idc[SYM_OPR_LEFT_BRACE]);
        sqlBuff.append(idc[SYM_OPR_ALL]);
        sqlBuff.append(idc[SYM_OPR_RIGHT_BRACE]);
        sqlBuff.append(op);
        sqlBuff.append(getSQLSyntaxes()[STX_FROM]);
        sqlBuff.append(op);
        sqlBuff.append(idc[SYM_OPR_LEFT_BRACE]);

        sqlBuff.append(getFINDSQL(key));

        sqlBuff.append(idc[SYM_OPR_RIGHT_BRACE]);

        return String.valueOf(sqlBuff);
    }

    /**
     * {@inheritDoc}
     */
    public String getINSERTSQL(Entity insertValue)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        Map valueMap = insertValue.getValueMap();
        String table = insertValue.getStoreMetaData().getName();

        // get symbol and syntax
        String[] idc = getSymbols();
        String[] stxes = getSQLSyntaxes();
        // String fdlm = idc[SYM_DLM_FIELD] ;
        String brLeft = idc[SYM_OPR_LEFT_BRACE];
        String brRight = idc[SYM_OPR_RIGHT_BRACE];
        String op = idc[SYM_DLM_OPERATE];

        // build select SQL
        sqlBuff.append(stxes[STX_INSERT]); // INSERT
        sqlBuff.append(op);
        sqlBuff.append(stxes[STX_INTO]); // INTO
        sqlBuff.append(op);
        sqlBuff.append(table); // table
        sqlBuff.append(op);
        sqlBuff.append(brLeft); // (
        sqlBuff.append(createInsertList(valueMap, false));
        sqlBuff.append(brRight); // )
        sqlBuff.append(op);
        sqlBuff.append(stxes[STX_VALUES]); // VALUES
        sqlBuff.append(op);
        sqlBuff.append(brLeft); // (
        sqlBuff.append(createInsertList(valueMap, true));
        sqlBuff.append(brRight); // )

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public String getUPDATESQL(SQLAlterKey upkey)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        List<Conditions> condList = upkey.getLimitConditionList();
        List<Conditions> updList = upkey.getUpdateValueList();

        // get symbol and syntax
        String[] idc = getSymbols();
        String[] stxes = getSQLSyntaxes();
        // String fdlm = idc[SYM_DLM_FIELD] ;
        // String brLeft = idc[SYM_OPR_LEFT_BRACE] ;
        // String brRight = idc[SYM_OPR_RIGHT_BRACE] ;
        String op = idc[SYM_DLM_OPERATE];

        sqlBuff.append(stxes[STX_UPDATE]); // UPDATE
        sqlBuff.append(op);

        // sqlBuff.append(getStoreNameFromCondList(updList)) ; // table name to update
        sqlBuff.append(upkey.getStoreName());

        sqlBuff.append(op);
        sqlBuff.append(stxes[STX_SET]); // SET
        sqlBuff.append(op);
        sqlBuff.append(createSetClause(updList)); // COL=VAL,COL=VAL,...
        sqlBuff.append(op);
        sqlBuff.append(createWhere(condList, null)); // WHERE COL=VAL

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public String getDELETESQL(Entity delEntity)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        List<Conditions> condList = createConditionList(delEntity);
        String table = delEntity.getStoreMetaData().getName();

        // get symbol and syntax
        String[] idc = getSymbols();
        String[] stxes = getSQLSyntaxes();
        String op = idc[SYM_DLM_OPERATE];

        // delete from XXXX where xxx='fff' and yyy='99
        sqlBuff.append(stxes[STX_DELETE]);
        sqlBuff.append(op);
        sqlBuff.append(stxes[STX_FROM]);
        sqlBuff.append(op);
        sqlBuff.append(table);
        sqlBuff.append(op);
        sqlBuff.append(createWhere(condList, null)); // WHERE COL=VAL

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public String getDELETESQL(SQLSearchKey delKey)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        // get list of conditions
        List<Conditions> condList = delKey.getLimitConditionList();

        // get symbol and syntax
        String[] idc = getSymbols();
        String[] stxes = getSQLSyntaxes();
        String op = idc[SYM_DLM_OPERATE];

        // build select SQL
        sqlBuff.append(stxes[STX_DELETE]);
        sqlBuff.append(op);
        sqlBuff.append(stxes[STX_FROM]);
        sqlBuff.append(op);

        String snames = getStoreNameFromCondList(condList);
        if (snames == null)
        {
            snames = delKey.getStoreName();
        }
        sqlBuff.append(snames);
        sqlBuff.append(op);
        sqlBuff.append(createWhere(condList, null));

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public int countIntensiveFunction(SQLSearchKey key)
    {
        List<Conditions> ccList = key.getCollectConditionList();
        if (null == ccList)
        {
            return 0;
        }

        for (Conditions cond : ccList)
        {
            String pfx = cond.getPrefix();
            int numiFunc = countIntensiveFunction(pfx);
            if (0 < numiFunc)
            {
                return numiFunc;
            }
        }
        return 0;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 集約関数の一覧を返します。
     * @return 集約関数の一覧 (大文字)
     */
    abstract String[] getIntensiveFunctions();

    /**
     * 文字列の中に集約関数があるかどうか調べます。
     *
     * @param func 対象文字列
     * @return 集約関数ありのとき true.
     */
    int countIntensiveFunction(String func)
    {
        if (null == func || 0 == func.trim().length())
        {
            return 0;
        }

        String funcStr = func.trim().toUpperCase();
        String[] intensiveFuncs = getIntensiveFunctions();

        int numFunc = 0;
        int spos = 0;
        for (int i = 0; i < intensiveFuncs.length;)
        {
            String iFunc = intensiveFuncs[i];
            int funcIdx = funcStr.indexOf(iFunc, spos);
            if (-1 == funcIdx)
            {
                // NOT FOUND THE FUNCTION
                i++;
            }
            else
            {
                // FOUND THE FUNCTION
                spos = funcIdx + iFunc.length();
                numFunc++;
                i = 0;
            }
        }
        return numFunc;
    }

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * UPDATE用の set 句を生成します。
     * @param updList 更新内容リスト
     * @return set句
     */
    protected String createSetClause(List<Conditions> updList)
    {
        if (isEmpty(updList))
        {
            // no values for update. (ERROR!)
            return null;
        }
        String[] idc = getSymbols();
        String fdlm = idc[SYM_DLM_FIELD];
        String eq = idc[SYM_OPR_EQUAL];
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        for (int i = 0; i < updList.size(); i++)
        {
            if (i > 0)
            {
                sqlBuff.append(fdlm);
            }
            Conditions upcond = updList.get(i);
            sqlBuff.append(upcond.getFieldName());
            sqlBuff.append(eq);

            // 更新内容によってフォーマットを変更
            Object upvalue = upcond.getValue();
            if (upvalue instanceof Conditions)
            {
                Conditions upcolvalue = (Conditions)upvalue;
                sqlBuff.append(upcolvalue.getFieldName().getFullName());

                Object val = upcolvalue.getValue();
                if (val instanceof BigDecimal)
                {
                    BigDecimal bdval = (BigDecimal)val;
                    String cadd = buildDecimalString(bdval);
                    sqlBuff.append(cadd);
                }
                else
                {
                    // no additional value
                }
            }
            else
            {
                // Timestamp型対応のため、フィールド名追加
                sqlBuff.append(upcond.getPrefix());
                Object value = upcond.getValue();
                FieldName field = upcond.getFieldName();
                sqlBuff.append(formatValue(field, value));
                sqlBuff.append(upcond.getPostfix());
            }
        }
        return String.valueOf(sqlBuff);
    }

    /**
     * カラムへの加減算を行うためのSET句を組み立てます。
     * @param bdval 加減算の値
     * @return Set句
     */
    protected String buildDecimalString(BigDecimal bdval)
    {
        StringBuffer fbuff = new StringBuffer();
        int scale = bdval.scale();
        // add plus
        fbuff.append("+0");
        for (int s = 0; s < scale; s++)
        {
            if (s == 0)
            {
                fbuff.append(".");
            }
            fbuff.append(ZERO);
        }
        fbuff.append(";-0");
        for (int s = 0; s < scale; s++)
        {
            if (s == 0)
            {
                fbuff.append(".");
            }
            fbuff.append(ZERO);
        }
        DecimalFormat fmt = new DecimalFormat(String.valueOf(fbuff));
        String mdfmt = fmt.format(bdval);
        return mdfmt;
    }

    /**
     * Conditionsのリストから、使用されているストアを取得します。
     *
     * @param condList Conditionsのリスト
     * @return ストア名
     */
    protected String getStoreNameFromCondList(List<Conditions> condList)
    {
        if (isEmpty(condList))
        {
            // no values for. (ERROR!)
            return null;
        }
        Conditions cond = condList.get(0);
        return cond.getFieldName().getStoreName();
    }

    /**
     * Insert用のフィールドまたは値の名一覧(CSV形式)を返します。
     *
     * @param valueMap フィールドとInsert用値のマップ
     * @param forValue 値の一覧を取得するときtrue.
     * @return フィールドまたは値の羅列 (Insert用)
     */
    protected String createInsertList(Map valueMap, boolean forValue)
    {
        if (isEmpty(valueMap))
        {
            // no values to insert. (ERROR!)
            return null;
        }

        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        String fdm = getSymbols()[SYM_DLM_FIELD];
        FieldName[] flds = (FieldName[])valueMap.keySet().toArray(new FieldName[0]);

        for (int i = 0; i < flds.length; i++)
        {
            if (i > 0)
            {
                sqlBuff.append(fdm);
            }

            if (forValue)
            {
                // Timestamp型対応のため、フィールド名追加
                Object value = valueMap.get(flds[i]);
                sqlBuff.append(formatValue(flds[i], value));
            }
            else
            {
                sqlBuff.append(flds[i].getName());
            }
        }
        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * Count用のSQL文 (取得部)を生成します。
     *
     * @param collList 取得項目リスト
     * @param grpList Group by項目リスト
     * @return Count用SQL文 (取得部)
     */
    protected String createCountCollect(List<Conditions> collList, List grpList)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        String[] idc = getSymbols();
        // String fdlm = idc[SYM_DLM_FIELD] ;
        String brLeft = idc[SYM_OPR_LEFT_BRACE];
        String brRight = idc[SYM_OPR_RIGHT_BRACE];
        // String op = idc[SYM_DLM_OPERATE] ;

        String[] stxes = getSQLSyntaxes();

        sqlBuff.append(stxes[STX_COUNT]); // COUNT
        sqlBuff.append(brLeft); // (

        boolean withCollect = !isEmpty(collList);
        boolean withGrp = !isEmpty(grpList);

        String pickCol = idc[SYM_OPR_ALL]; // default pickup is *
        if (withCollect)
        {
            Conditions ky = (Conditions)collList.get(0);
            pickCol = ky.getFieldName().getFullName();
        }

        if (withGrp)
        {
            // count with grouping
            sqlBuff.append(stxes[STX_COUNT]); // COUNT
            sqlBuff.append(brLeft); // (
            sqlBuff.append(pickCol);
            sqlBuff.append(brRight); // )
        }
        else
        {
            // set first column name
            sqlBuff.append(pickCol);
        }
        sqlBuff.append(brRight); // )

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * SQL文の取得部を生成します。
     *
     * @param storeNameList 対象ストア名の一覧
     * @param key 検索用のSQLSearchKey
     * @return SQL文 (取得部)
     */
    protected String createCollect(List<String> storeNameList, SQLSearchKey key)
    {
        List<Conditions> collList = key.getCollectConditionList();

        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        if (isEmpty(collList))
        {
            // return all columns collect
            sqlBuff.append(key.getStoreName());
            sqlBuff.append(".");
            sqlBuff.append(getSymbols()[SYM_OPR_ALL]);
            return new String(sqlBuff);
        }

        String[] idc = getSymbols();

        List<String> tableList = new ArrayList<String>();
        boolean first = true;
        for (int i = 0; i < collList.size(); i++)
        {
            Conditions ky = collList.get(i);
            if (!first)
            {
                sqlBuff.append(idc[SYM_DLM_FIELD]); // ,
            }
            else
            {
                first = false;
            }

            FieldName fld = ky.getFieldName();
            String tableName = fld.getStoreName();
            if (!tableList.contains(tableName))
            {
                tableList.add(tableName);
            }

            // 取得条件が明記されている場合は、取得条件＋獲得カラムを編集する。
            if (ky.getPrefix().trim().length() > 0)
            {
                // PREFIX
                sqlBuff.append(ky.getPrefix());
                // (
                sqlBuff.append(idc[SYM_OPR_LEFT_BRACE]);
                // FULL NAME OF COLUMN
                sqlBuff.append(fld.getFullName());
                // )
                sqlBuff.append(idc[SYM_OPR_RIGHT_BRACE]);
                // POSTFIX
                sqlBuff.append(ky.getPostfix());
            }
            else
            {
                // FULL NAME OF COLUMN
                sqlBuff.append(fld.getFullName());
            }
            sqlBuff.append(idc[SYM_DLM_OPERATE]);

            // all field not need save field
            String alias = fld.getAlias();
            if (!FieldName.ALL_FIELDS.equals(alias))
            {
                // NAME OF COLUMN
                FieldName saveFld = ky.getSaveFieldName();
                if (saveFld == null || saveFld.getName().length() == 0)
                {
                    // no save field defined, use the column name.
                    sqlBuff.append(alias);
                }
                else
                {
                    sqlBuff.append(saveFld.getAlias());
                }
            }
        }
        // create all column collect
        // storeNameList:   table name list for FROM
        // tableList:       table name list for collect names
        if (storeNameList.size() == 1)
        {
            String tbn = (String)storeNameList.get(0);
            if (!tableList.contains(tbn))
            {
                if (sqlBuff.length() > 0)
                {
                    sqlBuff.append(idc[SYM_DLM_FIELD]); // ,
                }
                sqlBuff.append(tbn);
                sqlBuff.append(".");
                sqlBuff.append(getSymbols()[SYM_OPR_ALL]);
            }
        }
        return new String(sqlBuff);
    }

    /**
     * SQL文 (条件部) を生成します。
     *
     * @param condList 条件リスト
     * @param joinList 結合条件リスト
     * @return SQL文 (条件部)
     */
    protected String createWhere(List<Conditions> condList, List<Conditions> joinList)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        String[] stx = getSQLSyntaxes();
        String[] idc = getSymbols();

        // WHERE
        sqlBuff.append(stx[STX_WHERE]);
        sqlBuff.append(idc[SYM_DLM_OPERATE]);
        boolean first = true;

        if (condList != null)
        {
            Conditions ky = null;
            for (int i = 0; i < condList.size(); i++)
            {
                // ADD AND/OR for Next where clause.
                if (!first)
                {
                    sqlBuff.append(idc[SYM_DLM_OPERATE]);
                    String nxt = (ky.isANDtoNext()) ? stx[STX_AND]
                                                   : stx[STX_OR];
                    sqlBuff.append(nxt);
                    sqlBuff.append(idc[SYM_DLM_OPERATE]);
                }
                else
                {
                    first = false;
                }

                ky = condList.get(i);
                FieldName field = ky.getFieldName();
                Object refValue = ky.getValue();

                // FUNC or BRACE
                sqlBuff.append(ky.getPrefix());

                // FULL COLUMN NAME
                sqlBuff.append(field.getFullName());
                sqlBuff.append(idc[SYM_DLM_OPERATE]);

                if (refValue instanceof String)
                {
                    // 文字型
                    sqlBuff.append(formatStringWhere(ky, refValue));
                }
                else if (refValue instanceof Date)
                {
                    // 日付型
                    sqlBuff.append(formatCompare(ky, refValue));
                }
                else if (refValue instanceof SQLSearchKey)
                {
                    // 副問い合わせ
                    sqlBuff.append(formatSubQuery(ky, (SQLSearchKey)refValue));
                }
                else
                {
                    // その他
                    sqlBuff.append(formatCompare(ky, refValue));
                }
                // 後ろ括弧
                sqlBuff.append(ky.getPostfix());
                sqlBuff.append(idc[SYM_DLM_OPERATE]);
            }
        }

        // WHERE FOR JOIN
        if (joinList != null)
        {
            Conditions j1 = null;
            Conditions j2 = null;

            for (int i = 0; i < joinList.size(); i += 2)
            {
                if (!first)
                {
                    sqlBuff.append(idc[SYM_DLM_OPERATE]);
                    sqlBuff.append(stx[STX_AND]);
                    sqlBuff.append(idc[SYM_DLM_OPERATE]);
                }
                else
                {
                    first = false;
                }

                j1 = joinList.get(i);
                j2 = joinList.get(i + 1);
                if (j1 == null || j2 == null)
                {
                    continue;
                }

                sqlBuff.append(j1.getFieldName().getFullName());
                sqlBuff.append(j1.getPostfix());
                sqlBuff.append(idc[SYM_OPR_EQUAL]);
                sqlBuff.append(j2.getFieldName().getFullName());
                sqlBuff.append(j2.getPostfix());
            }
        }

        if (first)
        {
            // RETURN space if no "WHERE" clause.
            return "";
        }
        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * GROUP BY句を生成します。
     *
     * @param grpList グループ対象のConditionsリスト
     * @return GROUP BY句
     */
    protected String createGroup(List<Conditions> grpList)
    {
        return formatFieldCSV(getSQLSyntaxes()[STX_GROUP_BY], grpList);
    }

    /**
     * ORDER BY句を生成します。
     *
     * @param orderList ソート対象のConditionsリスト
     * @return ORDER BY句
     */
    protected String createOrder(List<Conditions> orderList)
    {
        if (isEmpty(orderList))
        {
            return "";
        }
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        String[] stx = getSQLSyntaxes();
        String[] idc = getSymbols();

        sqlBuff.append(stx[STX_ORDER_BY]);

        boolean first = true;
        for (int i = 0; i < orderList.size(); i++)
        {
            // ADD , for Next group column.
            if (!first)
            {
                sqlBuff.append(idc[SYM_DLM_FIELD]);
            }
            else
            {
                sqlBuff.append(idc[SYM_DLM_OPERATE]);
                first = false;
            }

            Conditions ky = orderList.get(i);
            FieldName field = ky.getFieldName();

            sqlBuff.append(field.getFullName());
            sqlBuff.append(idc[SYM_DLM_OPERATE]);
            String asc = (ky.isSortOrder()) ? stx[STX_ASC]
                                           : stx[STX_DESC];
            sqlBuff.append(asc);
        }
        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * FROM句を生成します。
     *
     * @param storeNameList すべてのStoreNameリスト
     * @param skey 検索キー
     * @return FROM句
     */
    protected String createFrom(List<String> storeNameList, SQLSearchKey skey)
    {
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        String fromInit = getSQLSyntaxes()[STX_FROM];
        sqlBuff.append(fromInit);

        String[] idc = getSymbols();
        String fdlm = idc[SYM_DLM_FIELD];
        String op = idc[SYM_DLM_OPERATE];

        boolean first = true;
        for (int i = 0; i < storeNameList.size(); i++)
        {
            String store = storeNameList.get(i);
            if (StringUtil.isBlank(store))
            {
                continue;
            }
            if (!first)
            {
                sqlBuff.append(fdlm);
            }
            else
            {
                sqlBuff.append(op);
                first = false;
            }
            sqlBuff.append(store);
        }

        // 結果として全くFrom句が出来なかった場合の対処
        String sql = String.valueOf(sqlBuff).trim();

        if (sql.length() == fromInit.length())
        {
            sqlBuff.append(op);
            sqlBuff.append(skey.getStoreName());
            sql = String.valueOf(sqlBuff);
        }

        return sql;
    }

    /**
     * ストアメタデータ取得
     * <!--
     * Timestanp型のSQL生成でフィールド精度を取得するために追加
     * -->
     * @return ストアメタデータ
     */
    protected StoreMetaData getStoreMetaData()
    {
        return _storeMetaData;
    }

    //------------------------------------------------------------
    // utility methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * Entityから条件リストを生成します。<br>
     * Entityにセットされている Field = Value の条件で、条件リストを生成します。
     *
     * @param ent 生成元Entity
     * @return 条件リスト
     */
    protected List<Conditions> createConditionList(Entity ent)
    {
        Map valueMap = ent.getValueMap();
        FieldName[] flds = (FieldName[])valueMap.keySet().toArray(new FieldName[0]);

        List<Conditions> cList = new ArrayList<Conditions>();
        for (int i = 0; i < flds.length; i++)
        {
            Object val = valueMap.get(flds[i]);
            if (val != null)
            {
                flds[i].setStoreName(ent.getStoreMetaData().getName());
                Conditions cond = new Conditions(flds[i]);
                cond.setValue(val);
                cList.add(cond);
            }
        }
        return cList;
    }

    /**
     * Conditionsのリストから対象のストア名一覧を生成します。
     * @param targets Conditionsのリスト
     * @return ストア名(String)のリスト
     */
    protected List<String> createStoreNameList(List[] targets)
    {
        List<String> svList = Collections.synchronizedList(new ArrayList<String>());
        for (int i = 0; i < targets.length; i++)
        {
            List tList = targets[i];
            if (tList == null)
            {
                continue;
            }
            for (int j = 0; j < tList.size(); j++)
            {
                Conditions cond = (Conditions)tList.get(j);
                String store = cond.getFieldName().getStoreName();
                if (!svList.contains(store))
                {
                    svList.add(store);
                }
            }
        }

        return svList;
    }

    /**
     * 他の表からの取得条件文を編集します。
     * @param key 検索のためのKey
     * @return 作成された取得条件文
     */
    protected String inSelectConstants(SQLSearchKey key)
    {
        SQLGenerator gen = this;

        return gen.getFINDSQL(key);
    }

    /**
     * SQLSearchKeyをつかった副問い合わせ文のフォーマットを行う
     * @param ky
     * @param refValue
     * @return 副問い合わせを含むWHERE句
     */
    protected String formatSubQuery(Conditions ky, SQLSearchKey refValue)
    {
        String[] stx = getSQLSyntaxes();
        String[] idc = getSymbols();

        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        // 副問い合わせ
        sqlBuff.append(idc[SYM_DLM_OPERATE]);

        String opr = ky.getCompareOperator().trim();
        if (opr.equals(idc[SYM_OPR_NOT_EQUAL].trim()))
        {
            sqlBuff.append(stx[STX_NOT_IN]);
            sqlBuff.append(idc[SYM_DLM_OPERATE]);
            sqlBuff.append(idc[SYM_OPR_LEFT_BRACE]);
        }
        else if (opr.equals(idc[SYM_OPR_EQUAL].trim()))
        {
            sqlBuff.append(stx[STX_IN]);
            sqlBuff.append(idc[SYM_DLM_OPERATE]);
            sqlBuff.append(idc[SYM_OPR_LEFT_BRACE]);
        }
        else
        {
            sqlBuff.append(opr);
            sqlBuff.append(idc[SYM_DLM_OPERATE]);
            sqlBuff.append(idc[SYM_OPR_LEFT_BRACE]);
        }
        sqlBuff.append(inSelectConstants(refValue));
        sqlBuff.append(idc[SYM_OPR_RIGHT_BRACE]);

        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * 文字が検索対象の時のWHERE句フォーマットを行います。
     *
     * @param ky
     * @param refValue
     * @return 文字列用のWHERE句
     */
    protected String formatStringWhere(Conditions ky, Object refValue)
    {
        String[] stx = getSQLSyntaxes();
        String[] idc = getSymbols();

        // 値が文字の場合、パターン照合が含まれているか検証を行い、
        // 存在する場合はLIKE検索を使用する。
        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);
        sqlBuff.append(idc[SYM_DLM_OPERATE]);

        String refStr = String.valueOf(refValue);
        if (DBFormat.isPatternMatching(refStr))
        {
            // LIKE
            sqlBuff.append(stx[STX_LIKE]);
            sqlBuff.append(idc[SYM_DLM_OPERATE]);

            // LIKE ESCAPE
            refStr = likeEscape(refStr);

            // VALUE
            sqlBuff.append(DBFormat.format((refStr.replaceAll("[" + HandlerSysDefines.PATTERNMATCHING_CHAR + "]", "%"))));

            // ESCAPE
            sqlBuff.append(idc[SYM_DLM_OPERATE]);
            sqlBuff.append(LIKE_ESCAPE);
        }
        else
        {
            sqlBuff.append(formatCompare(ky, refValue));
        }
        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * LIKE 検索で"_"か"%"が使用された場合、エスケープを行います。
     *
     * @param value 検索文字
     * @return エスケープ済み検索文字
     */
    private String likeEscape(String value)
    {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < value.length(); i++)
        {
            String str = String.valueOf(value.charAt(i));
            if (UNDERSCORE.equals(str) || PERCENT.equals(str) || ESCAPE_CHAR.equals(str))
            {
                buf.append(ESCAPE_CHAR);
            }
            buf.append(str);
        }
        return buf.toString();
    }

    /**
     * フィールド名(FULLNAME)のCSV形式をフォーマットします。
     *
     * @param prefix 先頭につける文字列
     * @param fldList 対象フィールドConditionsのリスト
     * @return CSV形式
     */
    protected String formatFieldCSV(String prefix, List<Conditions> fldList)
    {
        if (isEmpty(fldList))
        {
            return "";
        }

        String[] idc = getSymbols();

        StringBuffer sqlBuff = new StringBuffer(BUFFER_SIZE);

        sqlBuff.append(prefix);

        boolean first = true;
        for (int i = 0; i < fldList.size(); i++)
        {
            // ADD , for Next group column.
            if (!first)
            {
                sqlBuff.append(idc[SYM_DLM_FIELD]);
            }
            else
            {
                sqlBuff.append(idc[SYM_DLM_OPERATE]);
                first = false;
            }

            Conditions ky = fldList.get(i);
            FieldName field = ky.getFieldName();
            sqlBuff.append(field.getFullName());
        }
        String sql = String.valueOf(sqlBuff).trim();
        return sql;
    }

    /**
     * 通常の比較文字をフォーマットします。
     * @param ky
     * @param refValue
     * @return 比較文字 + DB用フォーマット済み文字列
     */
    protected String formatCompare(Conditions ky, Object refValue)
    {
        String[] stx = getSQLSyntaxes();
        String[] idc = getSymbols();

        String op = ky.getCompareOperator();
        if (refValue == null || String.valueOf(refValue).trim().length() == 0)
        {
            String rop = (op.equals(idc[SYM_OPR_EQUAL])) ? stx[STX_IS_NULL]
                                                        : stx[STX_IS_NOT_NULL];
            return idc[SYM_DLM_OPERATE] + rop;
        }
        else
        {
            // TODO Timestamp型対応のため、フィールド名追加
            FieldName field = ky.getFieldName();
            return op + formatValue(field, refValue);
        }
    }

    /**
     * オブジェクトの内容に従って、SQLで使用できる形式にフォーマットします。
     * @param field 対象フィールド
     * @param val 対象の値
     * @param withKeyword SQLキーワード付の値を判定してフォーマットしないとき true.<br>
     * false を指定すると 内容がStringのとき、SQLキーワードが入っていてもフォーマットして返します。
     * @return フォーマット済み文字列
     */
    protected String formatValue(FieldName field, Object val, boolean withKeyword)
    {
        if (val instanceof String)
        {
            String strVal = (String)val;
            // キーワードが含まれるときは、フォーマットを行わない
            boolean noFmt = isSQLKeyword(strVal);
            String iStr = (noFmt && withKeyword) ? strVal
                                                : DBFormat.format(strVal);

            return iStr;
        }
        else if (val instanceof Timestamp)
        {
            return DBFormat.format((Timestamp)val);
        }
        else if (val instanceof Date)
        {
            return DBFormat.format((Date)val);
        }
        else if (val instanceof Number)
        {
            return String.valueOf(val);
        }
        else if (val instanceof FieldName)
        {
            FieldName fld = (FieldName)val;
            return fld.getFullName();
        }
        else if (val == null)
        {
            return getSQLSyntaxes()[STX_NULL];
        }
        else
        {
            return DBFormat.format(String.valueOf(val));
        }
    }

    /**
     * オブジェクトの内容に従って、SQLで使用できる形式にフォーマットします。<br>
     * 内容がStringのとき、SQLキーワードが入っていてもフォーマットして返します。
     *
     * @param field 対象フィールド
     * @param val 対象の値
     * @return フォーマット済み文字列
     */
    protected String formatValue(FieldName field, Object val)
    {
        return formatValue(field, val, false);
    }

    /**
     * SQLのキーワードを含むかどうか判定します。
     *
     * @param val 判定する文字列
     * @return SQLのキーワードのときtrue.
     */
    protected boolean isSQLKeyword(String val)
    {
        String[] funcs = getSQLKeywords();

        String ckStr = val.trim().toUpperCase();
        for (int i = 0; i < funcs.length; i++)
        {
            String fstr = funcs[i].trim().toUpperCase();
            if (ckStr.startsWith(fstr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * フィールド精度取得<br>
     * 指定されたフィールドの精度を取得します。
     * このメソッドは、Timestamp型の精度を取得するのに用いられます。
     * @param field フィールド
     * @return 精度
     */
    protected int getPrecision(FieldName field)
    {
        StoreMetaData storeMetaData = getStoreMetaData();
        if (null != storeMetaData)
        {
            FieldMetaData metaData = storeMetaData.getFieldMetaData(field.getName());
            if (null != metaData)
            {
                return metaData.getLength().intValue();
            }
        }
        return DEFAULT_DATE_PREC;
    }

    /**
     * コレクションが空であるか調べます。
     * @param c 対象コレクション
     * @return コレクションが null もしくは要素が0 のとき true.
     */
    protected boolean isEmpty(Collection c)
    {
        return (null == c) || c.isEmpty();
    }

    /**
     * マップが空であるか調べます。
     * @param m 対象マップ
     * @return マップが null もしくは要素が0 のとき true.
     */
    protected boolean isEmpty(Map m)
    {
        return (null == m) || m.isEmpty();
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractSQLGenerator.java 8053 2013-05-15 01:00:52Z kishimoto $";
    }
}
