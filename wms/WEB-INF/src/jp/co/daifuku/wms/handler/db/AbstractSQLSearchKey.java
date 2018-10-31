//$Id: AbstractSQLSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.handler.AbstractFindKey;
import jp.co.daifuku.wms.handler.Conditions;
import jp.co.daifuku.wms.handler.FindKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * データベースのテーブルを検索するために使用するクラスです。
 * SQLSearchKeyクラスのインスタンスは検索条件を保持し、保持している内容をもとにSQL文の組み立てを行います。
 * 組み立てられたSQL文はDatabasehandlerクラスによって実行され、データベースの問合せを行います。
 * 検索条件をセットするメソッドは本クラスを継承したクラスによって実装されます。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractSQLSearchKey
        extends AbstractFindKey
        implements SQLSearchKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private List p_collectConditionList = null;

    private List p_groupConditionList = null;

    private List p_orderConditionList = null;

    private List p_joinConditionList = null;

    private List _collectTableList = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ストア名を指定してインスタンスを初期化します。
     * @param storename 対象ストア名
     */
    public AbstractSQLSearchKey(String storename)
    {
        setStoreName(storename);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void setCollect(FieldName field)
    {
        setCollect(field, "", null);
    }

    /**
     * {@inheritDoc}
     */
    public void setCollect(FieldName field, String sqlFunc, FieldName saveField)
    {
        String alias = field.getAlias();
        if (FieldName.ALL_FIELDS.equals(alias))
        {
            String myStore = getStoreName();
            String colStore = field.getStoreName();
            if ((null == myStore) || !myStore.equals(colStore))
            {
                throw new RuntimeException("ALL FIELD must set own table only.");
            }
        }
        String sqlFunction = (sqlFunc == null) ? ""
                                              : sqlFunc;
        String[] formats = sqlFunction.split("\\{0\\}");
        int fcnt = formats.length;
        String prefix = (fcnt > 0) ? formats[0]
                                  : "";
        String postfix = (fcnt > 1) ? formats[1]
                                   : "";

        Conditions cond = new Conditions(field);
        cond.setPrefix(prefix);
        cond.setPostfix(postfix);
        cond.setSaveFieldName(saveField);

        List list = getCollectConditionList();
        if (list == null)
        {
            list = createUniqueList();
            setCollectConditionList(list);
        }
        if (!list.contains(cond))
        {
            // save tablename with collect condition
            String sname = field.getStoreName();
            addCollectTable(sname);

            // add collect condition
            list.add(cond);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setGroup(FieldName field)
    {
        Conditions cond = new Conditions(field);

        List list = getGroupConditionList();
        if (list == null)
        {
            list = createUniqueList();
            setGroupConditionList(list);
        }
        list.add(cond);

        // 副作用ありのため廃止 at 2006-04-19
        // setCollect(field) ;
    }

    /**
     * {@inheritDoc}
     */
    public void setOrder(FieldName field, boolean ascOrder)
    {
        Conditions cond = new Conditions(field);
        cond.setSortOrder(ascOrder);

        List list = getOrderConditionList();
        if (list == null)
        {
            list = createUniqueList();
            setOrderConditionList(list);
        }
        list.add(cond);
    }

    /**
     * {@inheritDoc}
     */
    public void setJoin(FieldName field1, String field1Postfix, FieldName field2, String field2Postfix)
    {
        Conditions cond1 = new Conditions(field1);
        cond1.setPostfix(field1Postfix);

        Conditions cond2 = new Conditions(field2);
        cond2.setPostfix(field2Postfix);

        List list = getJoinConditionList();
        if (list == null)
        {
            list = createList();
            setJoinConditionList(list);
        }
        synchronized (list)
        {
            list.add(cond1);
            list.add(cond2);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setJoin(FieldName field1, FieldName field2)
    {
        setJoin(field1, null, field2, null);
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(FindKey key)
    {
        super.setKey(key);

        if (key instanceof AbstractSQLSearchKey)
        {
            AbstractSQLSearchKey sqlkey = (AbstractSQLSearchKey)key;
            p_collectConditionList = copyListUnique(sqlkey.p_collectConditionList);
            p_groupConditionList = copyListUnique(sqlkey.p_groupConditionList);
            p_joinConditionList = copyList(sqlkey.p_joinConditionList);
            p_orderConditionList = copyListUnique(sqlkey.p_orderConditionList);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        super.clear();

        clearCollect();
        clearGroup();
        clearOrder();
        clearJoin();
    }

    /**
     * {@inheritDoc}
     */
    public void clearCollect()
    {
        setCollectConditionList(null);
        _collectTableList = null;
    }

    /**
     * {@inheritDoc}
     */
    public void clearGroup()
    {
        setGroupConditionList(null);
    }

    /**
     * {@inheritDoc}
     */
    public void clearOrder()
    {
        setOrderConditionList(null);
    }

    /**
     * {@inheritDoc}
     */
    public void clearJoin()
    {
        setJoinConditionList(null);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public List getCollectConditionList()
    {
        return p_collectConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public void setCollectConditionList(List collectConditionList)
    {
        p_collectConditionList = collectConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public List getGroupConditionList()
    {
        return p_groupConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public void setGroupConditionList(List groupConditionList)
    {
        p_groupConditionList = groupConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public List getOrderConditionList()
    {
        return p_orderConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public void setOrderConditionList(List sortConditionList)
    {
        p_orderConditionList = sortConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public List getJoinConditionList()
    {
        return p_joinConditionList;
    }

    /**
     * {@inheritDoc}
     */
    public void setJoinConditionList(List joinFields)
    {
        p_joinConditionList = joinFields;
    }

    /**
     * 取得条件が指定されているテーブルであるがどうか確認します。
     * @param tablename 確認するテーブル名
     * @return 取得条件が指定されていればtrue.
     */
    protected boolean isCollectTable(String tablename)
    {
        if (_collectTableList == null)
        {
            // not registerd if list is null
            return false;
        }
        boolean isCollected = _collectTableList.contains(tablename);
        return isCollected;
    }

    /**
     * 取得条件が指定されているテーブルを設定します。
     * @param tablename テーブル名
     */
    protected void addCollectTable(String tablename)
    {
        // do not regist blank tablename
        if (StringUtil.isBlank(tablename))
        {
            return;
        }
        String tbname = tablename.toUpperCase();

        // check already registed
        if (!isCollectTable(tbname))
        {
            // create list if it is null
            if (_collectTableList == null)
            {
                _collectTableList = new ArrayList();
            }
            // add tablename for collect setting
            _collectTableList.add(tbname);
        }
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 元のリストからユニークリストを生成して返します。
     * @param srcList コピー元リスト
     * @return srcListのコピー (重複項目を除く)
     */
    protected List copyListUnique(List srcList)
    {
        if (srcList == null || srcList.size() == 0)
        {
            return null;
        }
        List newList = createUniqueList();
        newList.addAll(srcList);

        return newList;
    }

    /**
     * 元のリストから新しいリストを生成して返します。
     * @param srcList コピー元リスト
     * @return srcListのコピー
     */
    protected List copyList(List srcList)
    {
        if (srcList == null || srcList.size() == 0)
        {
            return null;
        }
        List newList = createList();
        newList.addAll(srcList);

        return newList;
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
        return "$Id: AbstractSQLSearchKey.java 87 2008-10-04 03:07:38Z admin $";
    }
}
