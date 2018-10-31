// $Id: ToolSQLAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;

/**<jp>
 * This class is used to update table in database.
 * The instance of SQLAlterKey class preserves teh contents and conditions of updates.
 * According to the contents preserved, it composes the SQL string.
 * 組み立てられたSQL文はDataBaseHandlerクラスによって実行され、データベースの更新を行います。
 * 更新内容、更新条件をセットするメソッドは本クラスを継承したクラスによって実装されます。
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
 * This class is used to update table in database.
 * The instance of SQLAlterKey class preserves the contents and conditions of updates.
 * According to the contents preserved, it composes the SQL text.
 * The composed text is executed by DataBaseHandler class and the database will be updated.
 * The method of setting update data and its conditions is implemented by another class 
 * that is the successor of this class.
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
public class ToolSQLAlterKey implements ToolAlterKey
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 検索キー保存用配列
     </jp>*/
    /**<en>
     * Arrya that preserves the search key
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

    //<jp> 指定されたカラムに文字型の検索値をセットします。</jp>
    //<en> Set the string type search value to the selected column.</en>
    public void setValue(String column, String value)
    {
        Key ky = getKey(column);

        //<jp> 検索値にnullまたは空文字列がセットされている場合、空白1バイトをセットし</jp>
        //<jp> 条件文が WHERE XXX = ' 'となるようにする。</jp>
        //<en> In case the null or empty string array is set for search value, set the blank of 1 byte</en>
        //<en> then arrange the form of condition text to be WHERE XXX = ' '.</en>
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
     * Set the string type search value to the selected column.
     * Then connect the selected items in the array using OR condition.
     * column = "ITEM.ITEMKEY";
     * str[0] = "ABC";
     * str[1] = "EFG;
     * If above values are provided, data will be edited as below.
     *  (ITEM.ITEMKEY = 'ABC' OR 'EFG')
     </en>*/
    public void setValue(String column, String[] values)
    {
        Key ky = getKey(column);

        //<jp> 検索値にnullまたは空文字列がセットされている場合、空白1バイトをセットし</jp>
        //<jp> 条件文が WHERE XXX = ' 'となるようにする。</jp>
        //<en> In case the null or empty string array is set for search value, set the blank of 1 byte</en>
        //<en> then arrange the form of condition text to be WHERE XXX = ' '.</en>
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

    //<jp> 指定されたカラムに数値型の検索値をセットします。</jp>
    //<en> Set the numeric search value to the selected column.</en>
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
     * Set the numeric search value to the selected column.
     * Then connect the selected items in the array using OR condition.
     * column = "CARRYINFO.CMDSTATUS";
     * str[0] =  1;
     * str[1] =  2;
     * If above values are provided, data will be edited as below.
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

    //<jp> 指定されたカラムに日付型の検索値をセットします。</jp>
    //<en> Set the date type search value to the specified column.</en>
    public void setValue(String column, Date dtval)
    {
        Key ky = getKey(column);
        ky.setTableValue(dtval);
        setKey(ky);
    }

    //<jp> 指定されたカラムの検索値を取得します。</jp>
    //<en> Get the search value of the specified column.</en>
    public Object getValue(String column)
    {
        Key ky = getKey(column);
        return (ky.getTableValue());
    }

    //<jp> 指定されたカラムに文字型の更新値をセットします。</jp>
    //<en> Set the string type update value to the specified column.</en>
    public void setUpdValue(String column, String value)
    {
        Key ky = getKey(column);
        ky.setUpdValue(value);
        setKey(ky);
    }

    //<jp> 指定されたカラムに数値型の更新値をセットします。</jp>
    //<en> Set the numeric update value to the specified column.</en>
    public void setUpdValue(String column, int intval)
    {
        Key ky = getKey(column);
        ky.setUpdValue(new Integer(intval));
        setKey(ky);
    }

    //<jp> 指定されたカラムに数値型の更新値をセットします。</jp>
    //<en> Set the numeric update value to the specified column.</en>
    public void setUpdValue(String column, long longval)
    {
        Key ky = getKey(column);
        ky.setUpdValue(new Long(longval));
        setKey(ky);
    }

    //<jp> 指定されたカラムに日付型の検索値をセットします。</jp>
    //<en> Set the date type search value to the specified column.</en>
    public void setUpdValue(String column, Date dtval)
    {
        Key ky = getKey(column);
        ky.setUpdValue(dtval);
        setKey(ky);
    }

    //<jp> 指定されたカラムの更新値を取得します。</jp>
    //<en> Get the update value to the specified column.</en>
    public Object getUpdValue(String column)
    {
        Key ky = getKey(column);
        return (ky.getTableValue());
    }

    /**<jp>
     * SQLのWHERE句の生成を行います。
     * 設定されたテーブルに定義されているカラムだけで条件の生成を行います。
     * @param 条件文の生成対象となるテーブル名
     * @param SQLのwhere句に使用する文字列
     </jp>*/
    /**<en>
     * Generate the WHERE phrase of SQL.
     * Conditions are generated only based on the columns defined in set table.
     * @param :name of the table that condition text is generated for
     * @param :string array used in where phrase of SQL
     </en>*/
    public String ReferenceCondition(String tablename)
    {
        //<jp> Exception発生時のLogWriteに使用</jp>
        //<en> this is used in LogWRite when an Exception occurred.</en>
        //StringWriter wSW = new StringWriter();   

        //<jp> Exception発生時のLogWriteに使用</jp>
        //<en> this is used in LogWRite when an Exception occurred.</en>
        //PrintWriter  wPW = new PrintWriter(wSW); 

        //<jp> デリミタ</jp>
        //<en> delimiter</en>
        //String wDelim = MessageResource.DELIM ;  

        StringBuffer stbf = new StringBuffer(256);
        boolean existFlg = false;

        for (int i = 0 ; i < Vec.size() ; i++)
        {
            Key ky = (Key)Vec.get(i);
            if (ky.getTableValue() != null)
            {
                //<jp> AND条件用検索値の編集を行なう -> COLUMN = VALUE</jp>
                //<en> Edit the search value for AND conditions -> COLUMN = VALUE</en>

                //<jp> カラム名からテーブル名を取得する。TableName.ColumnName -> TableName</jp>
                //<en> Get the table name from the column name. TableName.ColumnName -> TableName</en>

                int fp = ky.getTableColumn().indexOf(".");
                if (fp == -1)
                {
                    //<jp> 不定なカラム定義なのでログを出力</jp>
                    //<en> Indefinite column definition. Outpul log.</en>
                    Object[] tObj = new Object[2];
                    tObj[0] = ky.getTableColumn();
                    tObj[1] = ky.getTableColumn();
                    //<jp>6124001 = 指定されたカラムがテーブルに存在しません。COLUMN={0} TABLE={1}</jp>
                    //<en>6124001 = Specified column does not exist in the table. COLUMN={0} TABLE={1}</en>
                    RmiMsgLogClient.write(6124001, LogMessage.F_WARN, this.getClass().getName(), tObj);
                    continue;
                }
                String str = ky.getTableColumn().substring(0, fp);
                //<jp> 呼出パラメータのテーブル名に一致すれば、条件文の対象カラムにする。</jp>
                //<en> If the column matches the table of calling parameter, regard the column as a target</en>
                //<en> for condition text.</en>
                if (str.equals(tablename))
                {
                    stbf.append(ky.getTableColumn());
                    stbf.append(" = ");
                    if (ky.getTableValue() instanceof String)
                    {
                        stbf.append(DBFormat.format((String)ky.getTableValue())) ;
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
            }
            else if (ky.getTableValueArray() != null)
            {
                //<jp> OR条件用検索値の編集を行なう -> (COLUMN = VALUE OR COLUMN = VALUE)</jp>
                //<en> Edit the search value for OR conditions. -> (COLUMN = VALUE OR COLUMN = VALUE)</en>
                StringBuffer wkbf = new StringBuffer(256);
                Object[] valobj = ky.getTableValueArray();
                for (int j = 0 ; j < valobj.length ; j++)
                {
                    if (j > 0)
                    {
                        //<jp> ２件目以降の値にはORを不可</jp>
                        //<en> OR is idsabled for 2nd data and further.</en>
                        wkbf.append(" OR ");
                    }
                    //<jp> 値が文字の場合、パターン照合文字が含まれているか検証を行い、</jp>
                    //<jp> 存在する場合はLIKE検索を使用する。</jp>
                    //<en> In case the characters are given for the value, check if pattern match characters are contained.</en>
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
                        //<en> Bracket off the string type data using single quotations.</en>
                        wkbf.append(DBFormat.format(DBFormat.exchangeKey((String)valobj[j]))) ;
                    }
                    else if (valobj[j] instanceof Date)
                    {
                        //<jp> 日付型の場合はTO_DATE関数を使用</jp>
                        //<en> Use TO_DATE function for date type data.</en>
                        wkbf.append(DBFormat.format((Date)valobj[j])) ;
                    }
                    else
                    {
                        //<jp> 数値型はそのままセット</jp>
                        //<en> Set gte numeric data as it is.</en>
                        wkbf.append(valobj[j]);
                    }
                }
                //<jp> 検索値配列の要素数が0の場合はこのSQL分自体を組み立てない</jp>
                //<en> If there are no items in search value array, SQL wil not be composed.</en>
                if (valobj.length > 0)
                {
                    //<jp> 編集した文字列の前後に ( ) を入れる。</jp>
                    //<en> Place the edited string array in between ( and ).</en>
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
        //<en> Return null if no dat is set for the key value.</en>

        if (existFlg == false)
        {
            return null;
        }

        //<jp> 最後の"AND"は余分なので除去。</jp>
        //<en> "AAND" in the end is unnecessary. Deleted.</en>
        int ep = stbf.toString().lastIndexOf("AND");
        return stbf.toString().substring(0, ep);
    }

    /**<jp>
     * 設定されたキーよりSQLのUPDATE SET句の生成を行います。
     * 指定されたテーブルに定義されているカラムだけで条件の生成を行います。
     * @param 条件文の生成対象となるテーブル名
     * @param SQLのwhere句に使用する文字列
     </jp>*/
    /**<en>
     * Generate the UPDATE SET phrase in SQL based on the set key.
     * Conditions are generated only based on the column defined in specified table.
     * @param :table name that the condition text is generated
     * @param :string array used in where phrase of SQL
     </en>*/
    public String ModifyContents(String tablename)
    {
        StringBuffer stbf = new StringBuffer(256);
        boolean existFlg = false;

        for (int i = 0 ; i < Vec.size() ; i++)
        {
            Key ky = (Key)Vec.get(i);
            //<jp> 各カラムに対してUPDATE対象カラムか検証する。</jp>
            //<en> Examine if each column are the object for UPDATE.</en>
            if (ky.isUpdate())
            {
                //<jp> カラム名からテーブル名を取得する。TableName.ColumnName -> TableName</jp>
                //<en> Get the table name from the column name. TableName.ColumnName -> TableName</en>
                int fp = ky.getTableColumn().indexOf(".");
                if (fp == -1)
                {
                    //<jp> 不定なカラム定義なのでログを出力</jp>
                    //<en> Indefinite column definition. Output log.</en>
                    Object[] tObj = new Object[2] ;
                    tObj[0] = ky.getTableColumn();
                    tObj[1] = ky.getTableColumn();
                    //<jp>6124001 = 指定されたカラムがテーブルに存在しません。COLUMN={0} TABLE={1}</jp>
                    //<en>6124001 = Specified column does not exist in the table. COLUMN={0} TABLE={1}</en>
                    RmiMsgLogClient.write(6124001, LogMessage.F_WARN, this.getClass().getName(), tObj);
                    continue;
                }
                String str = ky.getTableColumn().substring(0, fp);
                //<jp> 呼出パラメータのテーブル名に一致すれば、条件文の対象カラムにする。</jp>
                //<en> If the column matches the table of calling parameter, regard the column as a target</en>
                //<en> for condition text.</en>
                if (str.equals(tablename))
                {
                    //<jp> UPDATE対象カラムであれば設定値を取り出して編集</jp>
                    //<en> If it its the column to be updated, get the set value then edit the data.</en>
                    stbf.append(ky.getTableColumn());
                    stbf.append(" = ");
                    if (ky.getUpdValue() instanceof String)
                    {
                        stbf.append("'" + ky.getUpdValue() + "'");
                    }
                    else if (ky.getUpdValue() instanceof Date)
                    {
                        stbf.append(DBFormat.format((Date)ky.getUpdValue())) ;
                    }
                    else
                    {
                        stbf.append(ky.getUpdValue());
                    }
                    stbf.append(", ");
                    if (existFlg == false)
                    {
                        existFlg = true;
                    }
                }
            }
        }
        //<jp> キー値にデータがセットされていない場合はnullを返す。</jp>
        //<en> Return null if no dat is set for the key value.</en>
        if (existFlg == false)
        {
            return null;
        }

        //<jp> 最後の","は余分なので除去。</jp>
        //<en> Last "," is unnecessary. Deleted.</en>
        int ep = stbf.toString().lastIndexOf(",");
        return stbf.toString().substring(0, ep);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    //<jp> カラム名のセットを行います。</jp>
    //<en> Set the column name.</en>
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
    //<jp> 指定されたキーワード(カラム）に一致するKeyインスタンスを返す</jp>
    //<en> Return the Key instance that matches the specified key word (column).</en>
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

    //<jp> 指定されたキーワード(カラム）に一致するKeyインスタンスに値をセットする</jp>
    //<en> Set the Key instance that matches the specified key word (column).</en>
    protected void setKey(Key key)
    {
        for (int i = 0 ; i < Vec.size() ; i++)
        {
            Key ky = (Key)Vec.get(i);
            if (ky.getTableColumn().equals(key.getTableColumn()))
            {
                ky.setTableValue(key.getTableValue());
                Vec.set(i, ky);
            }
        }
    }
    

    // Inner Class ---------------------------------------------------
    //<jp> キーのカラム、検索値、ソート順、アップデート値を保持する内部クラスです。</jp>
    //<en> This is the innner class that preserves the key column, search value, sorting order and update values.</en>
    protected class Key
    {
        //<jp> カラム</jp>
        //<en> column</en>
        private String  Column;      
        //<jp> AND条件用検索値  COLUMN = VALUE</jp>
        //<en> search value for AND condition  COLUMN = VALUE</en>
        private Object  Value;       
        //<jp> OR条件用検索値  (COLUMN = VALUE OR COLUMN = VALUE)</jp>
        //<en> search value for OR condition (COLUMN = VALUE OR COLUMN = VALUE)</en>
        private Object[] ValueArray; 
        //<jp> アップデート値</jp>
        //<en> Update value</en>
        private Object  UpdValue ;   
        //<jp> UPDATE対象カラム true:UPDATE対象 false:UPDATE未対象</jp>
        //<en> Column to be UPDATEd true: to be UPDATEd, false: not to be UPDATEd</en>
        private boolean Update ;     

        protected Key (String clm) {
            Column        = clm;
            Value         = null;
            ValueArray    = null;
            UpdValue      = null;
            Update        = false;
        }

        //<jp> テーブルのカラムを取得しますします。</jp>
        //<en> Get the column of the table.</en>
        protected String getTableColumn()
        {
            return Column;
        }

        //<jp> テーブルのカラムに対応する検索値をセットします。</jp>
        //<en> Set the search value for the column of the table.</en>
        protected void setTableValue(Object val)
        {
            Value = val;
        }

        //<jp> テーブルのカラムに対応する検索値を取得します。</jp>
        //<en> Get the search value for the column of the table.</en>
        protected Object getTableValue()
        {
            return Value;
        }

        /**<jp>
         * テーブルのカラムに対応する検索値をセットします。
         * OR条件用です。
         </jp>*/
        /**<en>
         * Set the search value for the column of the table.
         * This is for OR condition.
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
         * Get the search value for the column of the table.
         * OThis is for OR condition.
         </en>*/
        protected Object[] getTableValueArray()
        {
            return ValueArray;
        }

        //<jp> テーブルのカラムに対応する更新値をセットします。</jp>
        //<jp> 同時にカラムをUPDATEの対象とします。</jp>
        //<en> Set the update value for the column of the table.</en>
        //<en> Also regard the column as a target to be UPDATEd.</en>
        protected void setUpdValue(Object val)
        {
            Update = true;
            UpdValue = val;
        }

        //<jp> テーブルのカラムに対応する更新値を取得します。</jp>
        //<en> Get the update value for the column of the table.</en>
        protected Object getUpdValue()
        {
            return UpdValue;
        }

        //<jp> カラムがUPDATEの対象となっているか検証します。</jp>
        //<en> Examine to see if the column is the object for UPDATE.</en>
        protected boolean isUpdate()
        {
            return Update;
        }
    }
}
//end of class

