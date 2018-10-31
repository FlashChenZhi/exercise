// $Id: IniFileOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * INI形式ファイルをリソースのように扱うメソッドを提供します。<BR>
 * セクション名、キーを指定して読み書きが可能です。<BR>
 * ただし、INI形式ファイルは指定したフォーマットで作成する必要があります。<BR>
 * セクション名、キー、設定値、キーのコメントのセットで記述して下さい。<BR>
 * 本クラスでは、INI形式ファイルを内部リソースに展開して扱います。　
 * setメソッド等で、内部リソースを変更した場合は、最後にflashメソッドを
 * 呼び出してファイルに書き戻して下さい。　変更が反映されません。<BR>
 * ファイルのエンコーディングには、<CODE>UTF-8</CODE>を使用して下さい。<BR>
 * <BR><BR>
 * リソースファイルのフォーマットは次の通りです。<BR>
 * <FONT COLOR="RED">[SECTION]</FONT><BR>
 * KEY1 = VALUE1<BR>
 * KEY2 = VALUE2<BR>
 * KEY3 = VALUE3<BR>
 * <FONT COLOR="BLUE"># KEY1 : KEY1に対するコメント</FONT><BR>
 * <FONT COLOR="BLUE"># KEY2 : KEY2に対するコメント</FONT><BR>
 * <FONT COLOR="BLUE"># KEY3 : KEY3に対するコメント</FONT><BR>
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/04/04</td><td nowrap>清水　正人</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水　正人
 * @author  Last commit: $Author: admin $
 */

public class IniFileOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * セクション区切り(開始)
     */
    private static final String SEC_START = "[";

    /**
     * セクション区切り(終了)
     */
    private static final String SEC_END = "]";

    /**
     * コメント文字
     */
    private static final String COMMENT_KEY = "#";

    /**
     * コメント開始文字
     */
    private static final String COMMENT_START = ":";

    /**
     * デフォルトのKEYセパレータ
     */
    public static final String KEY_SEPARATOR_DEFAULT = "=";

    /**
     * ファイルエンコーディング
     */
    public static final String INI_FILE_ENCODING = "UTF-8";

    /**
     * 検索タイプ
     */
    private final int _TYPE_VALUE = 0;

    private final int _TYPE_COMMENT = 1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;
    /**
     * リソースファイルパス名
     */
    private String _filePath;

    /**
     * KEYの区切り文字<BR>
     * デフォルトは "=" です
     */
    private String _separator = KEY_SEPARATOR_DEFAULT;

    /**
     * リソースファイルを
     * 読み込んだ時のファイルの最新更新時刻
     */
    private long _iniModTime;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * リソースファイル
     */
    private File _File = null;

    /**
     * ファイル内容格納用
     */
    // private Vector _lines = new Vector() ;
    private List<String> _lines = new ArrayList<String>(10000);

    /**
     * range class comments.<br>
     * 範囲を扱うIniFileOperatorの内部クラスです。<br>
     *
     * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
     * <caption><font size="+1"><b>Update History</b></font></caption>
     * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
     * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
     *
     * <!-- 変更履歴 -->
     * <tr><td nowrap>2006/04/04</td><td nowrap>清水　正人</td>
     * <td>Class created.</td></tr>
     *
     * </tbody></table><hr>
     *
     * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
     * @author  清水　正人
     * @author  Last commit: $Author: admin $
     */
    class Range
    {
        private int _start; // 開始

        private int _end; // 終了

        /**
         * デフォルトコンストラクタ
         */
        Range()
        {
            this._start = 0;
            this._end = 0;
        }

        /**
         * コンストラクタ<BR>
         * 開始、終了範囲を指定してインスタンス化します。<BR>
         * @param start 開始<BR>
         * @param end 終了<BR>
         */
        Range(int start, int end)
        {
            this._start = start;
            this._end = end;
        }

        /**
         * 開始設定<BR>
         * @param start 開始<BR>
         */
        void setStart(int start)
        {
            this._start = start;
        }

        /**
         * 開始取得<BR>
         * @return 終了<BR>
         */
        int getStart()
        {
            return _start;
        }

        /**
         * 終了設定<BR>
         * @param end 終了<BR>
         */
        void setEnd(int end)
        {
            this._end = end;
        }

        /**
         * 終了取得<BR>
         * @return 終了<BR>
         */
        int getEnd()
        {
            return _end;
        }
    }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ<BR>
     * INIファイルとKEYセパレータを指定してインスタンス化します。<BR>
     * @param file INIファイル<BR>
     * @param separetor KEYセパレータ<BR>
     * @throws ReadWriteException ReadWriteException<BR>
     */
    public IniFileOperator(File file, String separetor)
            throws ReadWriteException
    {
        // ファイルパスを設定します。
        _File = file;
        _filePath = file.getPath();

        // この時点でのファイルの最新更新時刻を設定します。
        setInitialLastModified();

        // KEYセパレータを設定します。
        if (StringUtil.isBlank(separetor))
        {
            _separator = KEY_SEPARATOR_DEFAULT;
        }
        else
        {
            _separator = separetor;
        }

        // 内部にINIファイルを読み込みます。
        load();
    }

    /**
     * コンストラクタ<BR>
     * INIファイルを指定してインスタンス化します。<BR>
     * KEYセパレータはデフォルトのものが使用されます。<BR>
     * @param file INIファイル<BR>
     * @throws ReadWriteException ReadWriteException<BR>
     */
    public IniFileOperator(File file)
            throws ReadWriteException
    {
        this(file, KEY_SEPARATOR_DEFAULT);
    }

    /**
     * コンストラクタ<BR>
     * ファイルパス名とセパレータを指定してインスタンス化します。<BR>
     * @param fpath ファイルパス名<BR>
     * @param separetor KEYセパレータ<BR>
     * @throws ReadWriteException ReadWriteException<BR>
     */
    public IniFileOperator(String fpath, String separetor)
            throws ReadWriteException
    {
        this(new File(fpath), separetor);
    }

    /**
     * コンストラクタ<BR>
     * ファイルパス名を指定してインスタンス化します。<BR>
     * @param fpath ファイルパス名<BR>
     * @throws ReadWriteException ReadWriteException<BR>
     */
    public IniFileOperator(String fpath)
            throws ReadWriteException
    {
        this(new File(fpath), KEY_SEPARATOR_DEFAULT);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ファイル出力<BR>
     * 内部に保持しているINIファイルリソースを該当ファイルに書き出します。<BR>
     * <CODE>set()</CODE>メソッドで、内部リソースを変更した場合は、この
     * メソッドを最後に呼ばないとファイルに反映されませんので、注意して
     * 下さい。<BR>
     * @exception ReadWriteException ReadWriteException<BR>
     */
    public void flush()
            throws ReadWriteException
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(_File);
            OutputStreamWriter osw = new OutputStreamWriter(fos, INI_FILE_ENCODING);
            BufferedWriter writer = new BufferedWriter(osw);
            for (int i = 0; i < _lines.size(); i++)
            {
                //String str = (String)_lines.elementAt(i) ;
                String str = _lines.get(i);
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * 設定値取得<BR>
     * KEYで指定された設定値を返します。<BR>
     * KEYの検索はセクションに関係なく行われます。<BR>
     * 該当する設定値が存在しない場合は、<CODE>null</CODE>を返します。<BR>
     * @param key 検索KEY値<BR>
     * @return 設定値<BR>
     */
    public String get(String key)
    {
        // KEYを内部リソースより検索します。
        int index = keyIndexOf(key);
        if (index >= 0)
        {
            // 検索したリソースより設定値を取り出して返します。
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            return getValue(str);
        }

        // 該当行が見当たらなければ、nullを返します。
        return null;
    }

    /**
     * 設定値取得<BR>
     * 指定したセクションのキーを検索し、設定値を返します。<BR>
     * 該当する設定値が存在しなかった場合は、<CODE>null</CODE>を返します。<BR>
     * @param section 検索するセクション名<BR>
     * @param key 検索するKEY<BR>
     * @return 設定値<BR>
     */
    public String get(String section, String key)
    {
        // 指定セクション内でKEYを検索します。
        int index = indexOf(section, key, _TYPE_VALUE);
        if (index >= 0)
        {
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            return getValue(str);
        }

        // 該当行が見当たらなければ、nullを返します。
        return null;
    }

    /**
     * セクションを指定せずにキーのコメントを返します。
     * @param key     検索するKEY
     * @return        コメント
     */
    public String getComment(String key)
    {
        // KEYを内部リソースより検索します。
        int index = commentIndexOf(key);
        if (index >= 0)
        {
            // 検索したリソースより設定値を取り出して返します。
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            return getCommentStr(str);
        }

        // 該当行が見当たらなければ、nullを返します。
        return null;
    }

    /**
     * 指定したセクション、キーのコメントを返します。
     * @param section 検索するセクション名
     * @param key     検索するKEY
     * @return        コメント
     */
    public String getComment(String section, String key)
    {
        // 指定セクション内でKEYを検索します。
        int index = indexOf(section, key, _TYPE_COMMENT);
        if (index >= 0)
        {
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            return getCommentStr(str);
        }

        // 該当行が見当たらなければ、nullを返します。
        return null;
    }

    /**
     * セクション一覧取得<BR>
     * 内部リソースに定義されているセクション一覧を配列にして返します。<BR>
     * セクションが全く定義されていない場合は、<CODE>null</CODE>を返します。<BR>
     * @return セクション名一覧<BR>
     */
    public String[] getSections()
    {
        // 内部リソースを全件検索します。
        Vector<String> sectionVec = new Vector<String>();
        for (int i = 0; i < _lines.size(); i++)
        {
            // 検索したリソースがセクションであれば、セクション名を取得します。
            //String str = (String)_lines.elementAt(i) ;
            String str = _lines.get(i);
            if (isSection(str))
            {
                sectionVec.addElement(getSection(str));
            }
        }

        // 1件もセクション名がなければ、nullを返します。
        if (sectionVec.size() == 0)
        {
            sectionVec = null;
            return null;
        }

        // 取得したセクション名を配列にして返します。
        String[] sections = new String[sectionVec.size()];
        sectionVec.copyInto(sections);
        return sections;
    }

    /**
     * KEY一覧取得<BR>
     * セクションに関係なく、全てのKEY一覧を配列にして返します。<BR>
     * KEYが全く定義されていない場合は、<CODE>null</CODE>を返します。<BR>
     * @return KEY一覧<BR>
     */
    public String[] getKeys()
    {
        // 内部リソースを全件検索します。
        Vector<String> keyVec = new Vector<String>();
        for (int i = 0; i < _lines.size(); i++)
        {
            // 検索したリソースが設定行であれば、KEYを取得します。
            //String str = (String)_lines.elementAt(i) ;
            String str = _lines.get(i);
            if (isValue(str))
            {
                keyVec.addElement(getKey(str));
            }
        }

        // 1件も設定行がなければ、nullを返します。
        if (keyVec.size() == 0)
        {
            keyVec = null;
            return null;
        }

        // 取得したKEY名を配列にして返します。
        String[] keys = new String[keyVec.size()];
        keyVec.copyInto(keys);
        return keys;
    }

    /**
     * KEY一覧取得<BR>
     * 指定したセクション内のKEY一覧を配列にして返します。<BR>
     * 該当セクションにKEYが全く定義されていない場合、あるいは
     * 指定セクションそのものが存在しない場合は、<CODE>null</CODE>を返します。<BR>
     * @param section セクション<BR>
     * @return KEY一覧<BR>
     */
    public String[] getKeys(String section)
    {
        // 指定されたセクションが存在しているか検索します。
        Vector<String> keyVec = new Vector<String>();
        Range range = getSectionRange(section);
        if (range != null)
        {
            // 検索範囲内に指定KEYが存在するか検索します。
            for (int i = range.getStart(); i < range.getEnd(); i++)
            {
                //String str = (String)_lines.elementAt(i) ;
                String str = _lines.get(i);
                if (isValue(str))
                {
                    keyVec.addElement(getKey(str));
                }
            }

            // 1件も設定行がなければ、nullを返します。
            if (keyVec.size() == 0)
            {
                keyVec = null;
                return null;
            }

            // 取得したKEY名を配列にして返します。
            String[] keys = new String[keyVec.size()];
            keyVec.copyInto(keys);
            return keys;
        }

        // セクションそのものが定義されていなければ、nullを返します。
        return null;
    }

    /** 
     * 設定値一覧取得<BR>
     * セクションに関係なく、全ての設定値一覧を配列にして返します。<BR>
     * 設定値が全く定義されていない場合は、<CODE>null</CODE>を返します。<BR>
     * @return 設定値一覧<BR>
     */
    public String[] getValues()
    {
        // 内部リソースを全件検索します。
        Vector<String> valueVec = new Vector<String>();
        for (int i = 0; i < _lines.size(); i++)
        {
            // 検索したリソースが設定行であれば、KEYを取得します。
            //String str = (String)_lines.elementAt(i) ;
            String str = _lines.get(i);
            if (isValue(str))
            {
                valueVec.addElement(getValue(str));
            }
        }

        // 1件も設定行がなければ、nullを返します。
        if (valueVec.size() == 0)
        {
            valueVec = null;
            return null;
        }

        // 取得した設定値を配列にして返します。
        String[] values = new String[valueVec.size()];
        valueVec.copyInto(values);
        return values;
    }

    /**
     * 設定値一覧取得<BR>
     * 指定したセクション内の設定値一覧を配列にして返します。<BR>
     * 該当セクションに設定値が全く定義されていない場合、あるいは
     * 指定セクションそのものが存在しない場合は、<CODE>null</CODE>を返します。<BR>
     * @param section セクション<BR>
     * @return 設定値一覧<BR>
     */
    public String[] getValues(String section)
    {
        // 指定されたセクションが存在しているか検索します。
        Vector<String> valueVec = new Vector<String>();
        Range range = getSectionRange(section);
        if (range != null)
        {
            // 検索範囲内に指定KEYが存在するか検索します。
            for (int i = range.getStart(); i < range.getEnd(); i++)
            {
                //String str = (String)_lines.elementAt(i) ;
                String str = _lines.get(i);
                if (isValue(str))
                {
                    valueVec.addElement(getValue(str));
                }
            }

            // 1件も設定行がなければ、nullを返します。
            if (valueVec.size() == 0)
            {
                valueVec = null;
                return null;
            }

            // 取得した設定値を配列にして返します。
            String[] keys = new String[valueVec.size()];
            valueVec.copyInto(keys);
            return keys;
        }

        // セクションそのものが定義されていなければ、nullを返します。
        return null;
    }

    /**
     * 設定値変更<BR>
     * セクションに関係なく、指定されたKEYの設定値を変更します。<BR>
     * 該当するKEYが存在しなければ、新規に作成し、最後に追加します。<BR>
     * @param key 検索KEY<BR>
     * @param value 変更内容<BR>
     */
    public void set(String key, String value)
    {
        // 指定されたKEYを検索します。
        int index = keyIndexOf(key);
        if (index >= 0)
        {
            // 指定KEYが存在した場合は、設定値を変更します。
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            String newValue = replaceValue(str, value);
            _lines.set(index, newValue);
        }
        else
        {
            // 指定KEYが存在しない場合は、最後に KEY=VALUE を追加します。
            _lines.add(valueFormat(key, value));
        }
    }

    /**
     * 設定値変更<BR>
     * 指定されたセクション、KEYの設定値を変更します。<BR>
     * 該当するKEYが存在しなければ、新規に作成し、最後に追加します。<BR>
     * @param section セクション<BR>
     * @param key 検索KEY<BR>
     * @param value 変更内容<BR>
     */
    public void set(String section, String key, String value)
    {
        // 指定設定行が存在しているか検索します。
        int index = indexOf(section, key, _TYPE_VALUE);
        if (index >= 0)
        {
            // 指定KEYが存在した場合は、設定値を変更します。
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            String newValue = replaceValue(str, value);
            _lines.set(index, newValue);
        }
        else
        {
            // 指定されたセクションが存在しているか検索します。
            Range range = getSectionRange(section);
            if (range == null)
            {
                // 指定されたセクションが存在しない場合は、セクションを作成し
                // 最後に KEY=VALUE を追加します。
                _lines.add(sectionFormat(section));
                _lines.add(valueFormat(key, value));
            }
            else
            {
                // 指定KEYが存在しない場合は、該当セクションの最後に KEY=VALUE
                // を追加します。
                _lines.add(range.getEnd(), valueFormat(key, value));
                _lines.add(range.getEnd() + 1, "");
            }
        }
    }

    /**
     * コメントを変更、該当するKEYのコメントがなければ新規追加します。
     * @param section セクション
     * @param key 検索KEY
     * @param comment コメント
     */
    public void setComment(String section, String key, String comment)
    {
        // 指定KEYコメント行が存在しているか検索します。
        int index = indexOf(section, key, _TYPE_COMMENT);
        if (index >= 0)
        {
            // 指定KEYが存在した場合は、設定値を変更します。
            //String str = (String)_lines.elementAt(index) ;
            String str = _lines.get(index);
            String newValue = replaceComment(str, comment);
            _lines.set(index, newValue);

        }
        else
        {
            // 指定されたセクションが存在しているか検索します。
            Range range = getSectionRange(section);
            if (range != null)
            {
                // 指定されたセクションが存在しない場合は、セクションを作成し
                // 最後にKEYコメント行を追加します。
                _lines.add(sectionFormat(section));
                _lines.add(commentFormat(key, comment));
            }
            else
            {
                // 指定KEYが存在しない場合は、該当セクションの最後にKEYコメント
                // 行を追加します。
                _lines.add(range.getEnd(), commentFormat(key, comment));
            }
        }
    }

    /**
     * セクション削除<BR>
     * 指定されたセクションを内部リソースより削除します。<BR>
     * @param section 削除するセクション<BR>
     */
    public void clearSection(String section)
    {
        // 指定されたセクションが存在しているか検索します。
        Range range = getSectionRange(section);
        if (range != null)
        {
            // 指定範囲の要素を削除します。
            for (int i = range.getEnd(); i >= range.getStart(); i--)
            {
                _lines.remove(i);
            }
        }
    }

    /**
     * 設定行削除<BR>
     * 指定したセクション、KEYの設定行を削除します。<BR>
     * 該当する設定行が存在しない場合は何もしません。<BR>
     * @param section セクション<BR>
     * @param key 削除するKEY<BR>
     */
    public void clearKey(String section, String key)
    {
        // 指定された設定行が存在しているか検索します。
        int index = indexOf(section, key, _TYPE_VALUE);
        if (index >= 0)
        {
            // 該当する設定行を削除します。
            _lines.remove(index);
        }

        // 該当セクション、あるいは該当設定行が存在しなければ
        // 何もしないで終了します。
    }

    /**
     * 指定されたキーのコメントを削除します。
     * @param section セクション
     * @param key 削除するコメントのKEY
     */
    public void clearComment(String section, String key)
    {
        // 指定されたKEYコメント行が存在しているか検索します。
        int index = indexOf(section, key, _TYPE_COMMENT);
        if (index >= 0)
        {
            // 該当するKEYコメント行を削除します。
            _lines.remove(index);
        }

        // 該当セクション、あるいは該当設定行が存在しなければ
        // 何もしないで終了します。
    }

    /**
     * 書き込み可能チェック<BR>
     * INIファイルが書き込み可能かを判定します。<BR>
     * @return 書き込み可能な場合<code>true</code><BR>
     * 書き込み不可の場合<code>false</code><BR>
     */
    public boolean canWrite()
    {
        return _File.canWrite();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * INIファイル最新更新時刻設定<BR>
     * INIファイルを内部にロードした時点での最新更新時刻を設定します。<BR>
     */
    public void setInitialLastModified()
    {
        _iniModTime = getLastModified();
    }

    /**
     * INIファイル最新更新時刻取得<BR>
     * INIファイルを内部にロードした時点での最新更新時刻を取得します。<BR>
     * @return 時刻を表す<CODE>long</CODE>値<BR>
     */
    public long getInitialLastModified()
    {
        return _iniModTime;
    }

    /**
     * ファイル最新更新時刻取得<BR>
     * INIファイルの最新更新時刻を返します。<BR>
     * @return ファイルが最後に変更された時刻を表す<CODE>long</CODE>値<BR>
     */
    public long getLastModified()
    {
        return _File.lastModified();
    }

    /**
     * セパレータ設定<BR>
     * KEY項目と値の間の区切り文字をセットします。<BR>
     * @param sep 区切り文字<BR>
     * @deprecated このメソッドは推奨されません。<BR>
     */
    public void setSeparete(String sep)
    {
        _separator = sep;
    }

    /**
     * セパレータ設定<BR>
     * KEY項目と値の間の区切り文字をセットします。<BR>
     * @param sep 区切り文字<BR>
     */
    public void setSeparetor(String sep)
    {
        _separator = sep;
    }

    /**
     * セパレータ取得<BR>
     * KEY項目と値の間の区切り文字を取得します。<BR>
     * @return 区切り文字<BR>
     * @deprecated このメソッドは推奨されません。<BR>
     */
    public String getSeparete()
    {
        return _separator;
    }

    /**
     * セパレータ取得<BR>
     * KEY項目と値の間の区切り文字を取得します。<BR>
     * @return 区切り文字<BR>
     */
    public String getSeparetor()
    {
        return _separator;
    }

    /**
     * ファイル名(パス)設定<BR>
     * ファイル名(パス)をセットします。<BR>
     * @param fname ファイル名(パス)<BR>
     */
    public void setFileName(String fname)
    {
        _filePath = fname;
        _File = new File(_filePath);

        // この時点でのファイルの最新更新時刻を設定します。
        setInitialLastModified();

        // ファイル名(パス)を指定された時、内部リソースを更新します。
        try
        {
            load();
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            _lines.clear();
        }
    }

    /**
     * ファイル名(パス)取得<BR>
     * ファイル名(パス)を取得します。<BR>
     * @return ファイル名(パス)<BR>
     */
    public String getFileName()
    {
        return _filePath;
        //return _File.getName() ;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * INIファイルロード<BR>
     * 指定されたファイルをINIファイル形式ファイルとして、内部にロードします。<BR>
     * Java -version 1.3.0_02を使用する場合、<CODE>BufferedReader</CODE>の
     * <CODE>ready()</CODE>メソッドは、ファイル最後の(EOF)の行とみなしtrueを返します。　
     * その結果、<CODE>readLine()</CODE>はnullを返し<CODE>Vector</CODE>にaddするため、
     * <CODE>Vector</CODE>の最後に格納された文字列を操作するとNullPointerExceptionが
     * 発生します。　よって<CODE>readLine()</CODE>でnullを返す場合は<CODE>Vector</CODE>
     * にaddしないようにしています。<BR>
     * @exception ReadWriteException ReadWriteException
     */
    private void load()
            throws ReadWriteException
    {
        _lines.clear();
        try
        {
            FileInputStream fis = new FileInputStream(_File);
            InputStreamReader isr = new InputStreamReader(fis, INI_FILE_ENCODING);
            BufferedReader reader = new BufferedReader(isr);
            while (reader.ready())
            {
                String str = reader.readLine();
                if (str != null)
                {
                    _lines.add(str);
                }
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // ファイルの入出力エラーが発生しました{0}。
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * KEY検索<BR>
     * 内部リソースより、KEYで指定された情報をもつ要素番号を取得します。<BR>
     * 該当する要素が存在しない場合は、-1を返します。<BR>
     * @param key KEY値<BR>
     * @return 要素番号<BR>
     */
    private int keyIndexOf(String key)
    {
        // セクションに関係なく最初にKEYが一致する内部リソースを検索します。
        int index = indexOf(key);
        if (index >= 0)
        {
            // コメント行は無視します。
            while (true)
            {
                //String str = (String)_lines.elementAt(index) ;
                String str = _lines.get(index);
                if (isValue(str))
                {
                    return index;
                }
                else
                {
                    index = indexOf(key, ++index);
                    if (index == -1)
                    {
                        return -1;
                    }
                }
            }
        }

        // 見つからない場合は、-1を返します。
        return -1;
    }

    /**
     * セクション検索<BR>
     * 内部リソースより、指定されたセクションの要素番号を取得します。<BR>
     * 該当するセクションが存在しない場合は、-1を返します。<BR>
     * @param section 検索セクション<BR>
     * @return 要素番号<BR>
     */
    private int sectionIndexOf(String section)
    {
        // セクション名を内部リソースより検索します。
        int index = indexOf(section);
        if (index >= 0)
        {
            // セクションでない行は無視します。
            while (true)
            {
                //String str = (String)_lines.elementAt(index) ;
                String str = _lines.get(index);
                if (isSection(str))
                {
                    return index;
                }

                index = indexOf(section, index);
                if (index == -1)
                {
                    return -1;
                }
            }
        }

        // 見つからない場合は、-1を返します。
        return -1;
    }

    /**
     * コメント検索<BR>
     * 内部リソースより、指定されたKEYコメントの要素番号を取得します。<BR>
     * 該当するKEYコメントが存在しない場合は、-1を返します。<BR>
     * @param key 検索KEY<BR>
     * @return 要素番号<BR>
     */
    private int commentIndexOf(String key)
    {
        // セクション名を内部リソースより検索します。
        int index = indexOf(key);
        if (index >= 0)
        {
            // KEYコメントでない行は無視します。
            while (true)
            {
                //String str = (String)_lines.elementAt(index) ;
                String str = _lines.get(index);
                if (isKeyComment(str))
                {
                    return index;
                }

                index = indexOf(key, index);
                if (index == -1)
                {
                    return -1;
                }
            }
        }

        // 見つからない場合は、-1を返します。
        return -1;
    }

    /**
     * 文字列検索<BR>
     * 内部リソースを検索し、指定された文字列を持つ要素番号を返します。<BR>
     * 指定された文字列を持つ要素が存在しない場合は、-1を返します。<BR>
     * @param searchStr 検索文字列<BR>
     * @param from 検索開始要素番号<BR>
     * @param to 検索終了要素番号<BR>
     * @return 要素番号<BR>
     */
    private int indexOf(String searchStr, int from, int to)
    {
        // 内部リソースが格納されていなければ、-1を返します。
        if (_lines.size() == 0)
        {
            return -1;
        }

        // 内部リソースの要素数よりも大きな検索開始要素番号が
        // 指定された場合も、-1を返します。
        if (from >= _lines.size())
        {
            return -1;
        }

        // 内部リソースの要素数よりも大きな検索終了要素番号が
        // 指定された場合は、最後まで検索するものとします。
        int toPos = to;
        if (to >= _lines.size())
        {
            toPos = _lines.size();
        }

        // 内部リソースを検索し、指定された文字列を持つ要素を検索します。
        for (int i = from; i < toPos; i++)
        {
            //String strRes = (String)_lines.elementAt(i) ;
            String strRes = _lines.get(i);
            if (!StringUtil.isBlank(strRes))
            {
                // 指定文字列を持つ要素番号を返します。
                if (1 < searchStr.length())
                {
                    String[] text = strRes.split("[^a-zA-Z_0-9]");
                    for (int ix = 0; ix < text.length; ix++)
                    {
                        if (0 == text[ix].length())
                        {
                            continue;
                        }
                        if (text[ix].equals(searchStr))
                        {
                            return i;
                        }
                    }
                }
                else
                {
                    int ix = strRes.indexOf(searchStr);
                    if (ix >= 0)
                    {
                        return i;
                    }
                }
            }
        }

        // 最後まで見つからない場合は、-1を返します。
        return -1;
    }

    /**
     * 文字列検索<BR>
     * 内部リソースを検索し、指定された文字列を持つ要素番号を返します。<BR>
     * 指定された文字列を持つ要素が存在しない場合は、-1を返します。<BR>
     * @param searchStr 検索文字列<BR>
     * @param index 検索開始要素番号<BR>
     * @return 要素番号<BR>
     */
    private int indexOf(String searchStr, int index)
    {
        return indexOf(searchStr, index, _lines.size());
    }

    /**
     * 文字列検索<BR>
     * 内部リソースを検索し、指定された文字列を持つ要素番号を返します。<BR>
     * 指定された文字列を持つ要素が存在しない場合は、-1を返します。<BR>
     * @param searchStr 検索文字列<BR>
     * @return 要素番号<BR>
     */
    private int indexOf(String searchStr)
    {
        return indexOf(searchStr, 0);
    }

    /**
     * 文字列検索<BR>
     * 指定セクション内で、該当KEYの要素番号を返します。<BR>
     * 該当する要素が存在しない場合は、-1を返します。<BR>
     * @param section セクション<BR>
     * @param key 検索KEY<BR>
     * @param type 検索タイプ<BR>
     * 0 : 設定行<BR>
     * 1 : KEYコメント行<BR>
     * @return 要素番号<BR>
     */
    private int indexOf(String section, String key, int type)
    {
        // 指定されたセクションが存在しているか検索します。
        Range range = getSectionRange(section);
        if (range != null)
        {
            // 検索範囲内に指定KEYが存在するか検索します。
            int index = indexOf(key, range.getStart(), range.getEnd());
            if (index >= 0)
            {
                while (true)
                {
                    //String str = (String)_lines.elementAt(index) ;
                    String str = _lines.get(index);
                    switch (type)
                    {
                        case _TYPE_VALUE:
                            if (isValue(str))
                            {
                                return index;
                            }
                            break;

                        case _TYPE_COMMENT:
                            if (isKeyComment(str))
                            {
                                return index;
                            }
                            break;

                        default:
                            return -1;
                    }

                    // 指定タイプの行を引き続き検索します。
                    index = indexOf(key, (index + 1), range.getEnd());
                    if (index == -1)
                    {
                        return -1;
                    }
                }
            }
        }

        // 該当セクションが存在しない場合は、-1を返します。
        return -1;
    }

    /**
     * 設定値取得<BR>
     * 指定したセクションのキーを検索し、設定値を返します。<BR>
     * 該当する設定値が存在しなかった場合は、<CODE>null</CODE>を返します。<BR>
     * @param section 検索するセクション名<BR>
     * @return 範囲<BR>
     */
    private Range getSectionRange(String section)
    {
        // 指定されたセクションが存在しているか検索します。
        int start = sectionIndexOf(section);
        if (start >= 0)
        {
            // 次のセクションの開始をこのセクションの終了とします。
            int end = indexOf(SEC_START, start + 1);
            if (end == -1)
            {
                // 次のセクションが見あたらなかった場合は、このセクションが
                // 最終セクションです。
                end = _lines.size() + 1;
            }

            Range range = new Range(start, (end - 1));
            return range;
        }

        // セクションそのものが定義されていなければ、nullを返します。
        return null;
    }

    /**
     * コメント行チェック<BR>
     * 指定された文字列がコメント行であれば、<CODE>True</CODE>を返します。<BR>
     * @param str 検査文字列<BR>
     * @return コメント行なら<CODE>True</CODE><BR>
     */
    private boolean isComment(String str)
    {
        // コメント文字列で始まっている行なら、コメント行とします。
        int index = str.indexOf(COMMENT_KEY);
        if (index == 0)
        {
            return true;
        }
        return false;
    }

    /**
     * KEYコメントチェック<BR>
     * 指定された文字列がKEYコメント行であれば、<CODE>True</CODE>を返します。<BR>
     * @param str 検査文字列<BR>
     * @return KEYコメント行なら<CODE>True</CODE><BR>
     */
    private boolean isKeyComment(String str)
    {
        if (isComment(str))
        {
            int len = str.indexOf(COMMENT_START);
            if (len > 0)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * セクション行チェック<BR>
     * 指定された文字列がセクション行であれば、<CODE>True</CODE>を返します。<BR>
     * @param str 検査文字列<BR>
     * @return セクション行なら<CODE>True</CODE><BR>
     */
    private boolean isSection(String str)
    {
        // 指定された文字列が"["で始まり"]"で終わっていれば
        // セクション行とします。
        if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == (str.length() - SEC_END.length()))
        {
            return true;
        }
        return false;
    }

    /**
     * 設定行チェック<BR>
     * 指定された文字列が<CODE>KEY = VALUE</CODE>の設定行であれば<CODE>True</CODE>を返します。<BR>
     * @param str 検査文字列<BR>
     * @return 設定行なら<CODE>True</CODE><BR>
     */
    private boolean isValue(String str)
    {
        // 指定された文字列がコメント行であればFalseを返します。
        if (isComment(str))
        {
            return false;
        }

        // 指定された文字列がセクション行であればFalseを返します。
        if (isSection(str))
        {
            return false;
        }

        // 指定された文字列がセパレータ文字列で区切られた行であれば
        // 設定行とみなします。
        int index = str.indexOf(_separator);
        if (index >= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * KEY取得<BR>
     * 指定された文字列より、KEY値を取得します。<BR>
     * <CODE>KEY = VALUE</CODE>　の形式でなかった場合は、空文字を返します。<BR>
     * @param line リソースデータ<BR>
     * @return KEY値<BR>
     */
    private String getKey(String line)
    {
        int index = line.indexOf(_separator);
        if (index >= 0)
        {
            return line.substring(0, index).trim();
        }
        return "";
    }

    /**
     * 値取得<BR>
     * 指定された文字列より、値を取得します。<BR>
     * <CODE>KEY = VALUE</CODE>　の形式でなかった場合は、空文字を返します。<BR>
     * @param line リソースデータ<BR>
     * @return 値<BR>
     */
    private String getValue(String line)
    {
        int index = line.indexOf(_separator);
        if (index >= 0)
        {
            return line.substring(index + _separator.length()).trim();
        }
        return "";
    }

    /**
     * セクション名取得<BR>
     * 指定された文字列より、セクション名を取得します。<BR>
     * @param line リソースデータ<BR>
     * @return セクション名<BR>
     */
    private String getSection(String line)
    {
        // 指定された文字列がセクション行であれば、セクション名のみを
        // 切り出して返します。
        if (isSection(line))
        {
            return line.substring(SEC_START.length(), line.length() - SEC_END.length());
        }
        return "";
    }

    /**
     * コメントKEY取得<BR>
     * KEYコメント行より<CODE>KEY</CODE>を返します。<BR>
     * @param str KEYコメント行<BR>
     * @return <CODE>KEY</CODE><BR>
     */
    private String getCommentKey(String str)
    {
        // 指定された文字列がKEYコメント行であれば、KEY部を取り出します。
        if (isKeyComment(str))
        {
            int len = str.indexOf(COMMENT_START);
            if (len > 0)
            {
                String key = str.substring(COMMENT_KEY.length(), (len - COMMENT_START.length())).trim();
                return key;
            }
        }

        return "";
    }

    /**
     * KEYコメント取得<BR>
     * KEYコメント行よりコメント部を返します。<BR>
     * @param str KEYコメント行<BR>
     * @return コメント<BR>
     */
    private String getCommentStr(String str)
    {
        // 指定された文字列がKEYコメント行であれば、コメント部を取り出します。
        if (isKeyComment(str))
        {
            int len = str.indexOf(COMMENT_START);
            if (len > 0)
            {
                String comment = str.substring((len + COMMENT_START.length()), str.length()).trim();
                return comment;
            }
        }

        return "";
    }

    /**
     * 設定値編集<BR>
     * 指定された設定行の設定値を新しいものに編集しなおします。<BR>
     * 指定された文字列が設定行でなければ、何も編集しないで返します。<BR>
     * @param line 設定行<BR>
     * @param value 設定値<BR>
     * @return 編集後設定行<BR>
     */
    private String replaceValue(String line, String value)
    {
        // 設定行より、KEY値を取り出し、セパレータで区切られた設定行を
        // 再編集します。
        if (isValue(line))
        {
            return valueFormat(getKey(line), value);
        }

        // 指定された文字列が設定行でなければ、そのまま復帰します。
        return line;
    }

    /**
     * コメント編集<BR>
     * 指定されたKEYコメント行のコメントを新しいものに編集しなおします。<BR>
     * 指定された文字列がKEYコメント行でなければ、何も編集しないで返します。<BR>
     * @param line 設定行<BR>
     * @param comment コメント<BR>
     * @return 編集後設定行<BR>
     */
    private String replaceComment(String line, String comment)
    {
        // 設定行より、KEY値を取り出し、セパレータで区切られたKEYコメント行を
        // 再編集します。
        if (isKeyComment(line))
        {
            return commentFormat(getCommentKey(line), comment);
        }

        // 指定された文字列が設定行でなければ、そのまま復帰します。
        return line;
    }

    /**
     * セクション整形<BR>
     * セクションをファイル出力用にフォーマットします。<BR>
     * @param section セクション<BR>
     * @return 整形文字列<BR>
     */
    private String sectionFormat(String section)
    {
        StringBuffer newSection = new StringBuffer(SEC_START);
        newSection.append(section);
        newSection.append(SEC_END);
        return String.valueOf(newSection);
    }

    /**
     * 設定行整形<BR>
     * 設定行をファイル出力用にフォーマットします。<BR>
     * @param key KEY<BR>
     * @param value 設定値<BR>
     * @return 整形文字列<BR>
     */
    private String valueFormat(String key, String value)
    {
        StringBuffer newValue = new StringBuffer(key);
        newValue.append(" ");
        newValue.append(_separator);
        newValue.append(" ");
        newValue.append(value);
        return String.valueOf(newValue);
    }

    /**
     * コメント行整形<BR>
     * コメント行をファイル出力用にフォーマットします。<BR>
     * @param key KEY<BR>
     * @param comment コメント<BR>
     * @return 整形文字列<BR>
     */
    private String commentFormat(String key, String comment)
    {
        StringBuffer newComment = new StringBuffer(COMMENT_KEY);
        newComment.append(" ");
        newComment.append(key);
        newComment.append(" ");
        newComment.append(COMMENT_START);
        newComment.append(comment);
        return String.valueOf(newComment);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: IniFileOperator.java 87 2008-10-04 03:07:38Z admin $";
    }
}
