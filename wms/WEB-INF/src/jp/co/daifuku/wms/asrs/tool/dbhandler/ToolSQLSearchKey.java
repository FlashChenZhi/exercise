// $Id: ToolSQLSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.text.DBFormat;

/**<jp>
 * データベースのテーブルを検索するために使用するクラスです。
 * SQLSearchKeyクラスのインスタンスは検索条件を保持し、保持している内容をもとにSQL文の組み立てを行います。
 * 組み立てられたSQL文はDataBaseHandlerクラスによって実行され、データベースの問合せを行います。
 * 検索条件をセットするメソッドは本クラスを継承したクラスによって実装されます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used when searching tables in database.
 * Instance of the SQLSearchKey class preserves the search conditions; it also compose the 
 * SQL string according to the contents preserved.
 * Composed SQL string will be executed by DataBaseHandler calass and carry out the query in database.
 * The method, to which the search conditions will be set, will be implemented by an inherited class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolSQLSearchKey implements ToolSearchKey
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 検索キー保存用配列
     </jp>*/
    /**<en>
     * The array of search key reservation
     </en>*/
    protected Vector Vec = null;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $") ;
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * 更新対象となるテーブル名を取得します。
     * @return    テーブル名
     </jp>*/
    /**<en>
     * Retrieve the name of the table to update.
     * @return    :name of the table
     </en>*/
    public String getUpdateTable()
    {
        return null;
    }

    /**<jp>
     * 指定されたカラムに文字型の検索値をセットします
     </jp>*/
    /**<en>
     * Set the string type search value to the specified column.
     </en>*/
    public void setValue(String column, String value)
    {
        Key ky = getKey(column);

        //<jp> 検索値にnullまたは空文字列がセットされている場合、空白1バイトをセットし</jp>
        //<jp> 条件文が WHERE XXX = ' 'となるようにする。</jp>
        //<en> If null or the empty string has been set as a search value, set spaces of 1 byte</en>
        //<en> and shape the conditional string WHERE XXX = ' '.</en>
        if (value == null)
        {
            ky.setTableValue(" ");
        }
        else if (value.length() == 0)
        {
            ky.setTableValue(" ");
        }
        else
        {
            ky.setTableValue(value);
        }
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムに文字型の検索値をセットします
     * 配列に指定された要素をOR条件でくくります。
     * column = "ITEM.ITEMKEY";
     * str[0] = "ABC";
     * str[1] = "EFG;
     * という値が渡された場合は以下のように編集されます。
     *  (ITEM.ITEMKEY = 'ABC' OR 'EFG')
     </jp>*/
    /**<en>
     * Set the string type search value to the specified column.
     * Put the specified elements together in the array by forming OR conditions.
     * column = "ITEM.ITEMKEY";
     * str[0] = "ABC";
     * str[1] = "EFG;
     * For example, if above values have been given, the string should be edited as below.
     *  (ITEM.ITEMKEY = 'ABC' OR 'EFG')
     </en>*/
    public void setValue(String column, String[] values)
    {
        Key ky = getKey(column);

        //<jp> 検索値にnullまたは空文字列がセットされている場合、空白1バイトをセットし</jp>
        //<jp> 条件文が WHERE XXX = ' 'となるようにする。</jp>
        //<en> If null or the empty string has been set as a search value, set spaces of 1 byte</en>
        //<en> and shape the conditional string WHERE XXX = ' '.</en>
        for (int i = 0 ; i < values.length ; i++)
        {
            if (values[i] == null)
            {
                values[i] = " ";
            }
            else if (values[i].length() == 0)
            {
                values[i] = " ";
            }
        }
        ky.setTableValue(values);
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムに数値型の検索値をセットします。
     </jp>*/
    /**<en>
     * Set the numeric search value to the specified column.
     </en>*/
    public void setValue(String column, int intval)
    {
        Key ky = getKey(column);
        ky.setTableValue(new Integer(intval));
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムに数値型の検索値をセットします。
     * 配列に指定された要素をOR条件でくくります。
     * column = "CARRYINFO.CMDSTATUS";
     * str[0] =  1;
     * str[1] =  2;
     * という値が渡された場合は以下のように編集されます。
     *  (CARRYINFO.CMDSTATUS = 1 OR 2)
     </jp>*/
    /**<en>
     * Set the numeric search value to the specified column.
     * Put the specified elements together in the array by forming OR conditions.
     * column = "CARRYINFO.CMDSTATUS";
     * str[0] =  1;
     * str[1] =  2;
     * For example, if above values have been given, the string should be edited as below.
     *  (CARRYINFO.CMDSTATUS = 1 OR 2)
     </en>*/
    public void setValue(String column, int[] intvals)
    {
        Key ky = getKey(column);
        Integer[] intObjs = new Integer[intvals.length];
        for (int i = 0 ; i < intvals.length ; i++)
        {
            intObjs[i] = new Integer(intvals[i]);
        }
        ky.setTableValue(intObjs);
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムに日付型の検索値をセットします。
     </jp>*/
    /**<en>
     * Set the date type search value to the specified column.
     </en>*/
    public void setValue(String column, Date dtval)
    {
        Key ky = getKey(column);
        ky.setTableValue(dtval);
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムの検索値を取得します。
     </jp>*/
    /**<en>
     * Retrieve the search value of the specified column.
     </en>*/
    public Object getValue(String column)
    {
        Key ky = getKey(column);
        return (ky.getTableValue());
    }

    /**<jp>
     * 指定されたカラムにソート順をセットします。
     </jp>*/
    /**<en>
     * Set the order of sorting to the specified column.
     </en>*/
    public void setOrder(String column, int num, boolean bool)
    {
        Key ky = getKey(column);
        ky.setTableOrder(num);
        ky.setTableDesc(bool);
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムのソート順を取得します。
     </jp>*/
    /**<en>
     * Retrieve the order of sorting to the specified column.
     </en>*/
    public int getOrder(String column)
    {
        Key ky = getKey(column);
        return (ky.getTableOrder());
    }

    /**<jp>
     * 指定されたカラムに文字型の更新値をセットします。
     </jp>*/ 
    /**<en>
     * Set the string type update value to the specified column.
     </en>*/ 
    public void setUpdValue(String column, String value)
    {
        Key ky = getKey(column);

        //<jp> 検索値にnullまたは空文字列がセットされている場合、空白1バイトをセットし</jp>
        //<jp> 条件文が WHERE XXX = ' 'となるようにする。</jp>
        //<en> If null or the empty string has been set as a search value, set spaces of 1 byte</en>
        //<en> and shape the conditional string WHERE XXX = ' '.</en>

        if (value == null)
        {
            ky.setUpdValue(" ");
        }
        else if (value.length() == 0)
        {
            ky.setUpdValue(" ");
        }
        else
        {
            ky.setUpdValue(value);
        }
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムに数値型の更新値をセットします。
     </jp>*/
    /**<en>
     * Set the string type update value to teh specified column.
     </en>*/
    public void setUpdValue(String column, int intval)
    {
        Key ky = getKey(column);
        ky.setUpdValue(new Integer(intval));
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムに日付型の検索値をセットします。
     </jp>*/
    /**<en>
     * Set the date type search value to the specified column.
     </en>*/
    public void setUpdValue(String column, Date dtval)
    {
        Key ky = getKey(column);
        ky.setUpdValue(dtval);
        setKey(ky);
    }

    /**<jp>
     * 指定されたカラムの更新値を取得します。
     </jp>*/
    /**<en>
     * Retrieve the update value of the specified column.
     </en>*/
    public Object getUpdValue(String column)
    {
        Key ky = getKey(column);
        return (ky.getTableValue());
    }

    /**<jp>
     * 設定されたキーよりSQLのWHERE句の生成を行います。
     </jp>*/
    /**<en>
     * Generate the WHERE phrase for SQL based on the set key.
     </en>*/
    public String ReferenceCondition()
    {
        StringBuffer stbf = new StringBuffer(256);
        boolean existFlg = false;

        for (int i = 0 ; i < Vec.size() ; i++)
        {
            Key ky = (Key)Vec.get(i);
            if (ky.getTableValue() != null)
            {
                //<jp> AND条件用検索値の編集を行なう -> COLUMN = VALUE</jp>
                //<en> Edit the search value for AND condition. -> COLUMN = VALUE</en>

                //<jp> 値が文字の場合、パターン照合文字が含まれているか検証を行い、存在する場合はLIKE検索を使用する。</jp>
                //<en> When the letters are given as values, examine whether/not the pattern check letters</en>
                //<en> are included; if they are included, use LIKE search.</en>
                if (ky.getTableValue() instanceof String)
                {
                    String key = (String)ky.getTableValue();
                    key = DBFormat.exchangeKey(key);
                    
                    if (DBFormat.isPatternMatching(key))
                    {
                        stbf.append(" RTRIM(");
                        stbf.append(ky.getTableColumn());
                        stbf.append(") ");
                        stbf.append(" LIKE ");
                    }
                    else
                    {
                        stbf.append(ky.getTableColumn());
                        stbf.append(" = ");
                    }
                }
                else
                {
                    stbf.append(ky.getTableColumn());
                    stbf.append(" = ");
                }
                if (ky.getTableValue() instanceof String)
                {
                    stbf.append(DBFormat.format(DBFormat.exchangeKey((String)ky.getTableValue()))) ;
                }
                else if (ky.getTableValue() instanceof Date)
                {
                    stbf.append(DBFormat.format((Date)ky.getTableValue())) ;
                }
                else
                {
                    stbf.append(ky.getTableValue());
                }
                stbf.append(" AND ");
                if (existFlg == false)
                {
                    existFlg = true;
                }
            }
            else if (ky.getTableValueArray() != null)
            {
                //<jp> OR条件用検索値の編集を行なう -> (COLUMN = VALUE OR COLUMN = VALUE)</jp>
                //<en> Edit the search valud for OR condition.  -> (COLUMN = VALUE OR COLUMN = VALUE)</en>
                StringBuffer wkbf = new StringBuffer(256);
                Object[] valobj = ky.getTableValueArray();
                for (int j = 0 ; j < valobj.length ; j++)
                {
                    if (j > 0)
                    {
                        //<jp> ２件目以降の値にはORを不可</jp>
                        //<en> Add OR to the valud of 2nd data and onward.</en>
                        wkbf.append(" OR ");
                    }
                    //<jp> 値が文字の場合、パターン照合文字が含まれているか検証を行い、</jp>
                    //<jp> 存在する場合はLIKE検索を使用する。</jp>
                    //<en> When the letters are given as values, examine whether/not the pattern check letters</en>
                    //<en> are included; if they are included, use LIKE search.</en>
                    if (valobj[j] instanceof String)
                    {
                        String key = (String)valobj[j];
                        key = DBFormat.exchangeKey(key);
                        if (DBFormat.isPatternMatching(key))
                        {
                            wkbf.append(" RTRIM(");
                            wkbf.append(ky.getTableColumn());
                            wkbf.append(") ");
                            wkbf.append(" LIKE ");
                        }
                        else
                        {
                            wkbf.append(ky.getTableColumn());
                            wkbf.append(" = ");
                        }
                    }
                    else
                    {
                        wkbf.append(ky.getTableColumn());
                        wkbf.append(" = ");
                    }
                    if (valobj[j] instanceof String)
                    {
                        //<jp> 文字型の場合はシングルコーテーションでくくる</jp>
                        //<en> When string type value is given, enclose it between single quotations.</en>
                        wkbf.append(DBFormat.format(DBFormat.exchangeKey((String)valobj[j]))) ;
                    }
                    else if (valobj[j] instanceof Date)
                    {
                        //<jp> 日付型の場合はTO_DATE関数を使用</jp>
                        //<en> When date type value is given, use TO_DATE function.</en>
                        wkbf.append(DBFormat.format((Date)valobj[j])) ;
                    }
                    else
                    {
                        //<jp> 数値型はそのままセット</jp>
                        //<en> When numeric value is given, set it as it is.</en>
                        wkbf.append(valobj[j]);
                    }
                }
                //<jp> 検索値配列の要素数が0の場合はこのSQL分自体を組み立てない</jp>
                //<en> When the number of the elements in the array of search value is 0,</en>
                //<en> this SQL string will not be composed.</en>
                if (valobj.length > 0)
                {
                    //<jp> 編集した文字列の前後に ( ) を入れる。</jp>
                    //<en> Enclose the edited string in ( ).</en>
                    wkbf.insert(0, "(");
                    wkbf.append(")");
                    stbf.append(wkbf);
                    stbf.append(" AND ");
                    if (existFlg == false)
                    {
                        existFlg = true;
                    }
                }
            }
        }
        //<jp> キー値にデータがセットされていない場合はnullを返す。</jp>
        //<en> Return null if no data has been set to the key value.</en>

        if (existFlg == false)
        {
            return null;
        }

        //<jp> 最後の"AND"は余分なので除去。</jp>
        //<en> Ommit the unnecesary "AND" of the end.</en>
        int ep = stbf.toString().lastIndexOf("AND");
        return stbf.toString().substring(0, ep);
    }

    /**<jp>
     * 設定されたキーよりSQLのORDER BY句の生成を行います。
     </jp>*/
    /**<en>
     * Generate the ORDER BY phrase of SQL according to the set key.
     </en>*/
    public String SortCondition()
    {
        Key[] karray = new Key[Vec.size()];

        Vec.copyInto(karray);
        for (int i = 0 ; i < karray.length ; i++)
        {
            for (int j = i ; j < karray.length ; j++)
            {
                if (karray[i].getTableOrder() > karray[j].getTableOrder())
                {
                    Key ktmp = karray[i];
                    karray[i] = karray[j];
                    karray[j] = ktmp;
                }
            }
        }

        StringBuffer stbf = new StringBuffer(256);
        boolean existFlg = false;

        for (int i = 0 ; i < karray.length ; i++)
        {
            if (karray[i].getTableOrder() > 0)
            {
                stbf.append(karray[i].getTableColumn());
                //<jp> 降順の場合、降順のキーワードをセット</jp>
                //<en> If the data is in descending order, Set the key words of descending order.</en>
                if (karray[i].getTableDesc() == false)
                {
                    stbf.append(" DESC");
                }
                stbf.append(", ");
                if (existFlg == false)
                {
                    existFlg = true;
                }
            }
        }            
        //<jp> キー値にデータがセットされていない場合はnullを返す。</jp>
        //<en> Return null if no data has been set to the key value.</en>
        if (existFlg == false)
        {
            return null;
        }

        //<jp> 最後の","は余分なので除去。</jp>
        //<en> Ommit the unnecesary "," of the end.</en>
        int ep = stbf.toString().lastIndexOf(",");
        return stbf.toString().substring(0, ep);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * カラム名のセットを行います。
     </jp>*/
    /**<en>
     * Set the column name.
     </en>*/
    protected void setColumns(String[] columns)
    {
        Vec = new Vector(columns.length);
        for (int i = 0 ; i < columns.length ; i++)
        {
            //<jp> キーカラムセット</jp>
            //<en> Set the key column.</en>
            Vec.addElement(new Key(columns[i]));
        }
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * 指定されたキーワード(カラム）に一致するKeyインスタンスを返す
     </jp>*/
    /**<en>
     * Return the Key instance that matches the specified key word (column).
     </en>*/
    protected Key getKey(String keyword)
    {
        for (int i = 0 ; i < Vec.size() ; i++)
        {
            Key ky = (Key)Vec.get(i);
            if (ky.getTableColumn().equals(keyword))
            {
                return ky;
            }
        }
        return null;
    }

    /**<jp>
     * 指定されたキーワード(カラム）に一致するKeyインスタンスに値をセットする
     </jp>*/
    /**<en>
     * Set a value to the Key instance that matches the specified key word (column).
     </en>*/
    protected void setKey(Key key)
    {
        for (int i = 0 ; i < Vec.size() ; i++)
        {
            Key ky = (Key)Vec.get(i);
            if (ky.getTableColumn().equals(key.getTableColumn()))
            {
                ky.setTableValue(key.getTableValue());
                ky.setTableOrder(key.getTableOrder());
                Vec.set(i, ky);
            }
        }
    }
    

    // Inner Class ---------------------------------------------------
    /**<jp>
     * キーのカラム、検索値、ソート順、アップデート値を保持する内部クラスです。
     </jp>*/
    /**<en>
     * This is the inncer class which preserve key column, search value, the order of sorting
     * and the update value.
     </en>*/
    protected class Key
    {
        //<jp> カラム</jp>
        //<en> column</en>
        private String   Column;
             //<jp> カラム</jp>
        //<en> column</en>
        private Date     dtColumn;
        //<jp> AND条件用検索値  COLUMN = VALUE</jp>
        //<en> search value for AND conditions COLUMN = VALUE</en>
        private Object   Value;
        //<jp> OR条件用検索値  (COLUMN = VALUE OR COLUMN = VALUE)</jp>
        //<en> search value for OR conditions (COLUMN = VALUE OR COLUMN = VALUE)</en>
        private Object[] ValueArray;
        //<jp> ソート順</jp>
        //<en> order of sorting</en>
        private int      Order;
        //<jp> ソート方向 true:昇順 false:降順</jp>
        //<en> direction of sorting - true: ascending order, false: descending order</en>
        private boolean  Desc ;      
        //<jp> アップデート値</jp>
        //<en> update value</en>
        private Object   UpdValue ;  
        //<jp> UPDATE対象カラム true:UPDATE対象 false:UPDATE未対象</jp>
        //<en> column to update - true:to be updated, false:not the target of udpate</en>
        private boolean  Update ;    

        protected Key (String clm) {
            Column        = clm;
            Value         = null;
            ValueArray    = null;
            Order         = 0;
            Desc          = true;
            UpdValue      = null;
            Update        = false;
        }

        /**<jp>
         * テーブルのカラムをセットします。
         </jp>*/
        /**<en>
         * Set the column of the table.
         </en>*/
        protected String getTableColumn()
        {
            return Column;
        }

        /**<jp>
         * テーブルのカラムを取得します。
         </jp>*/
        /**<en>
         * Retrieve the column of the table.
         </en>*/
        protected Date getTableDtColumn()
        {
            return dtColumn;
        }

        /**<jp>
         * テーブルのカラムに対応する検索値をセットします。
         * AND条件用です。
         </jp>*/
        /**<en>
         * Set the search value which corresponds to the table column.
         * This is for AND conditions.
         </en>*/
        protected void setTableValue(Object val)
        {
            Value = val;
        }

        /**<jp>
         * テーブルのカラムに対応する検索値を取得します。
         * AND条件用です。
         </jp>*/
        /**<en>
         * Retrieve the search value that corresponds to the table column.
         * his is for AND conditions.
         </en>*/
        protected Object getTableValue()
        {
            return Value;
        }

        /**<jp>
         * テーブルのカラムに対応する検索値をセットします。
         * OR条件用です。
         </jp>*/
        /**<en>
         * Set the search value which corresponds to the table column.
         * This is for OR conditions.
         </en>*/
        protected void setTableValue(Object[] valarray)
        {
            ValueArray = valarray;
        }

        /**<jp>
         * テーブルのカラムに対応する検索値を取得します。
         * OR条件用です。
         </jp>*/
        /**<en>
         * Set the search value which corresponds to the table column.
         * This is for OR conditions.
         </en>*/
        protected Object[] getTableValueArray()
        {
            return ValueArray;
        }

        /**<jp>
         * テーブルのカラムに対応するソート順をセットします。
         </jp>*/
        /**<en>
         * Set the order of sorting that corresponds to the table column.
         </en>*/
        protected void setTableOrder(int num)
        {
            Order = num;
        }

        /**<jp>
         * テーブルのカラムに対応するソート順を取得します。
         </jp>*/
        /**<en>
         * Retrieve the order of sorting that corresponds to the table column.
         </en>*/
        protected int getTableOrder()
        {
            return Order;
        }

        /**<jp>
         * ソートの昇順・降順をセットします。
         </jp>*/
        /**<en>
         * Set the direction of the sorting (asecending /descending order).
         </en>*/
        protected void setTableDesc(boolean bool)
        {
            Desc = bool;
        }

        /**<jp>
         * ソートの昇順・降順を取得します。
         </jp>*/
        /**<en>
         * Retrieve the direction of the sorting (asecending /descending order).
         </en>*/
        protected boolean getTableDesc()
        {
            return Desc;
        }

        /**<jp>
         * テーブルのカラムに対応する更新値をセットします。
         * 同時にカラムをUPDATEの対象とします。
         </jp>*/
        /**<en>
         * Set the update value that corresponds to the table column.
         * Include the column in targe of the update at the same time.
         </en>*/
        protected void setUpdValue(Object val)
        {
            Update = true;
            UpdValue = val;
        }

        /**<jp>
         * テーブルのカラムに対応する更新値を取得します。
         </jp>*/
        /**<en>
         * Retrieve the update value for the table column.
         </en>*/
        protected Object getUpdValue()
        {
            return UpdValue;
        }

        /**<jp>
         * カラムがUPDATEの対象となっているか検証します。
         </jp>*/
        /**<en>
         * Examine whether/not the column is the target of update.
         </en>*/
        protected boolean isUpdate()
        {
            return Update;
        }
    }
}
//end of class

