// $Id: ToolMenuTextHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.common ;


/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;

/**<jp>
 * メニュー設定ツールで使用します。<BR>
 * MenuTextのファイル操作をするクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/15</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used in menu setting tool.<BR>
 * This class operates the MenuText files.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/15</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolMenuTextHandler
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * セクション区切り(開始)
     </jp>*/
    /**<en>
     * Section delimiter (start)
     </en>*/
    private static final String SEC_START = "[";
    /**<jp>
     * セクション区切り(終了)
     </jp>*/
    /**<en>
     * Section delimiter (end)
     </en>*/
    private static final String SEC_END = "]";
    /**<jp>
     * コメント文字
     </jp>*/
    /**<en>
     * Comment characters 
     </en>*/
    private static final String COMMENT_KEY = "#";
    /**<jp>
     * リソースファイルパス
     </jp>*/
    /**<en>
     * Retource file path
     </en>*/
    private String wFileName;
    /**<jp>
     * KEYの区切り文字。デフォルトは"="です。
     </jp>*/
    /**<en>
     * KEY delimiter. Default is "=".
     </en>*/
    private String wSeparate = ToolMenuText.wSeparate;

    /**<jp>
     * ファイル内容格納用Vector
     </jp>*/
    /**<en>
     * Vector for the storage of file contents
     </en>*/
    private Vector lines = new Vector();

    // Public Class method -------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Private Class method ------------------------------------------

    // Constructors --------------------------------------------------
    /**<jp>
     * ファイル名、区切り文字を取得し、ファイルを読み込みます。
     * KEYとなる項目と値の間が"="ではないときに使用します。
     * @param fname ファイル名
     * @throws ReadWriteException ファイルアクセスで例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve the file name and delimiters, then read the file.
     * This is used when there is no "=" between the KEY item and the value.
     * @param fname :file name
     * @throws ReadWriteException 
     </en>*/
    public ToolMenuTextHandler(String fname) throws ReadWriteException
    {
        wFileName = fname;
        read();
    }
    // Public methods ------------------------------------------------

    /**<jp>
     * 指定したセクションのキーを捜し、設定値を返します。
     * @param section 検索するセクション名
     * @return        設定値
     </jp>*/
    /**<en>
     * Search the key of specified section, then return the set value.
     * @param section :name of the section to search
     * @return        :set value
     </en>*/
    public ArrayList find(String section)
    {
        boolean insideSection = false;
        ArrayList list    = new ArrayList();
        
        for (int i = 0 ; i < lines.size() ; i++)
        {
            String str = (String)lines.elementAt(i);

            if (str.length() > 0)
            {
                if (insideSection)
                {
                    //<jp> 別のセクションに移ったら、見つからなかった。</jp>
                    //<jp> 該当セクションは２つ以上はないものとする。</jp>
                    //<en> It could not be found after moving to a different section.</en>
                    //<en> Defines that there is only one corresponding section.</en>
                    if (str.indexOf(SEC_START) == 0)
                    {
                        break;
                    }

                    //<jp> "="を検索</jp>
                    //<en> Search "=".</en>
                    int index = str.indexOf(wSeparate);
                    if (index >= 0)
                    {
                        //<jp> "="までのKeyを取得</jp>
                        //<en> Retrieve the key which is leading "=".</en>
                        String key = str.substring(0, index).trim();
                        //<jp> カテゴリの場合は無視</jp>
                        //<en> Disregard if it is a category.</en>
                        if (key.equals(section))
                        {
                            continue;
                        }
                        //<jp> セクション内で、キーを見つけたら、その値を返す。</jp>
                        //<en> If the Key is found in the section, return that value.</en>
                        if (key.substring(3, 5).equals("00"))
                        {
                            list.add(find(section, key));
                        }
                    }
                }
                else
                {
                    //<jp> セクション区切りを探す</jp>
                    //<en> Search the section delimiter.</en>
                    if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                    {
                        //<jp> セクション名を取得（スペース除去）</jp>
                        //<en> Retrieve the name of the section. (the space will be ommitted.)</en>
                        String tempsection = str.substring(1, str.length() - 1).trim();

                        //<jp> 該当セクションを見つけたら</jp>
                        //<en> If corresponding section is found,</en>
                        if (tempsection.equals(section))
                        {
                            insideSection = true;
                        }
                    }
                }
            }
        }
        return list;
    }


    /**<jp>
     * 指定したセクションのキーを捜し、設定値を返します。
     * @param section 検索するセクション名
     * @param key     機能KEY
     * @return        指定した機能キーに対するボタン行一覧
     </jp>*/
    /**<en>
     * Search the key of the specified section. then return the set value.
     * @param section :name of the section to search
     * @param key     :function KEY
     * @return        :list of the line of buttons which correspond to the function keys specified 
     </en>*/
    public ArrayList find(String section, String key)
    {
        boolean insideSection = false;
        ArrayList list = new ArrayList();
        for (int i = 0 ; i < lines.size() ; i++)
        {
            String str = (String)lines.elementAt(i);

            if (str.length() > 0)
            {
                if (insideSection)
                {
                    //<jp> "="を検索</jp>
                    //<en> Search "=".</en>
                    int index = str.indexOf(wSeparate);
                    if (index >= 0)
                    {
                        //<jp> "="までのKeyを取得</jp>
                        //<en> Retrieve the Key which is leading "=".</en>
                        String tempkey = str.substring(0, index).trim();

                        //<jp> セクション内で、キーを見つけたら、その値を返す。</jp>
                        //<en> If the Key is found in the section, return that value.</en>
                        if (tempkey.substring(0, 3).equals(key.substring(0, 3)))
                        {
                            list.add(convertMenuText(str));
                        }
                    }
                    //<jp> 別のセクションに移ったら、見つからなかった。</jp>
                    //<jp> 該当セクションは２つ以上はないものとする。</jp>
                    //<en> It could not be found after moving to a different section.</en>
                    //<en> Defines that there is only one corresponding section.</en>
                    if (str.indexOf(SEC_START) == 0)
                    {
                        break;
                    }
                }
                else
                {
                    //<jp> セクション区切りを探す</jp>
                    //<en> Search the section delimiter.</en>
                    if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                    {
                        //<jp> セクション名を取得（スペース除去）</jp>
                        //<en>  Retrieve the name of the section. (the space will be ommitted.)</en>
                        String tempsection = str.substring(1, str.length() - 1).trim();

                        //<jp> 該当セクションを見つけたら</jp>
                        //<en> If corresponding section is found,</en>
                        if (tempsection.equals(section))
                        {
                            insideSection = true;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**<jp>
     * 指定したセクションのキーを捜し、設定値を返します。
     * @param section 検索するセクション名
     * @param key     検索するKEY
     * @return        設定値
     </jp>*/
    /**<en>
     * Search the key of the specified section. then return the set value.
     * @param section :name of the section to search
     * @param key     :KEY to search
     * @return        :set value
     </en>*/
    public ToolMenuText findMenuText(String section, String key)
    {
        boolean insideSection = false;

        for (int i = 0 ; i < lines.size() ; i++)
        {
            String str = (String)lines.elementAt(i);

            if (str.length() > 0)
            {
                if (insideSection)
                {
                    //<jp> "="を検索</jp>
                    //<en> Search "=".</en>
                    int index = str.indexOf(wSeparate);
                    if (index >= 0)
                    {
                        //<jp> "="までのKeyを取得</jp>
                        //<en> Retrieve the Key which is leading "=".</en>
                        String tempkey = str.substring(0, index).trim();

                        //<jp> セクション内で、キーを見つけたら、その値を返す。</jp>
                        //<en> If the Key is found in the section, return that value.</en>
                        if (tempkey.equals(key))
                        {
                            return convertMenuText(str);
                        }
                    }
                    //<jp> 別のセクションに移ったら、見つからなかった。</jp>
                    //<jp> 該当セクションは２つ以上はないものとする。</jp>
                    //<en> It could not be found after moving to a different section.</en>
                    //<en> Defines that there is only one corresponding section.</en>
                    if (str.indexOf(SEC_START) == 0)
                    {
                        return null;
                    }
                }
                else
                {
                    //<jp> セクション区切りを探す</jp>
                    //<en> Search the section delimiter.</en>
                    if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                    {
                        //<jp> セクション名を取得（スペース除去）</jp>
                        //<en> Retrieve the name of the section. (the space will be ommitted.)</en>
                        String tempsection = str.substring(1, str.length() - 1).trim();

                        //<jp> 該当セクションを見つけたら</jp>
                        //<en> If corresponding section is found,</en>
                        if (tempsection.equals(section))
                        {
                            insideSection = true;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**<jp>
     * 設定値を変更、KEYがなければ新規追加します。
     * @param section     セクション
     * @param sectionname セクション名
     * @param list      登録内容
     </jp>*/
    /**<en>
     * Modify the set value. If there is no KEY, add a new key.
     * @param section     :section
     * @param sectionname section name
     * @param list      :contents of registration
     </en>*/
    public void set(String section, String sectionname, ArrayList list)
    {
        boolean insideSection = false;
        //<jp> はじめにすでにそのセクション（カテゴリ）が存在する場合は中身をクリアする。</jp>
        //<en> Firstly, if the section(or category) already exists, clear what it contains.</en>
        for (int i = 0 ; i < lines.size(); i++)
        {
            String str = (String)lines.elementAt(i);
            if (str.length() > 0)
            {
                if (insideSection)
                {
                    //<jp> 別のセクションに移ったらbreak</jp>
                    //<en> Break when moved to a different section.</en>
                    if (str.indexOf(SEC_START) == 0)
                    {
                        break;
                    }
                    //<jp> キーがカテゴリ名称であればcontinue</jp>
                    //<en> Continue if the key is the name of a category.</en>
                    int index = str.indexOf(wSeparate);
                    if (index >= 0)
                    {
                        //<jp> "="までのKeyを取得</jp>
                        //<en> Retrieve the Key which is leading "=".</en>
                        String tempkey = str.substring(0, index).trim();
                        if (tempkey.equals(section))
                        {
                            continue;
                        }
                    }
                //    lines.remove(i);
                // replace
                    lines.set(i, "");
                }
                else
                {
                    //<jp> セクション区切りを探す</jp>
                    //<en> Search the section delimiter.</en>
                    if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                    {
                        //<jp> セクション名を取得（スペース除去）</jp>
                        //<en> Retrieve the name of the section. (the space will be ommitted.)</en>
                        String tempsection = str.substring(1, str.length() - 1).trim();
                        //<jp> 該当セクションを見つけたら</jp>
                        //<en> If corresponding section is found,</en>
                        if (tempsection.equals(section))
                        {
                            insideSection = true;
                        }
                    }
                }
            }
        }
        //<jp> 該当セクションがなければ、セクションを作る</jp>
        //<en> If there is no corresponding section, create one.</en>
        if (!insideSection)
        {
            lines.add(SEC_START + section + SEC_END);
            lines.add(section + wSeparate + sectionname);

        }
        //<jp> 登録する</jp>
        //<en> Register.</en>
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
        {
            ArrayList bbb = (ArrayList)iterator.next();
            Iterator iterator3 = bbb.iterator();
            while (iterator3.hasNext())
            {
                ToolMenuText menutext = (ToolMenuText)iterator3.next();
                set(section, menutext.getKey(), menutext.toString());
            }
        }
    }


    /**<jp>
     * 設定値を変更、KEYがなければ新規追加します。
     * @param section     セクション
     * @param key         検索KEY
     * @param value     変更内容
     </jp>*/
    /**<en>
     * Modify the set value. If there is no KEY, add a new key.
     * @param section     :section
     * @param key         :search KEY
     * @param value     :contents of modification
     </en>*/
    public void set(String section, String key, String value)
    {
        boolean insideSection = false;
        int i = 0;
        for (i = 0 ; i < lines.size(); i++)
        {
            String str = (String)lines.elementAt(i);

            if (str.length() > 0)
            {
                if (insideSection)
                {
                    //<jp> "="を検索</jp>
                    //<en> Search "=".</en>
                    int index = str.indexOf(wSeparate);

                    if (index >= 0)
                    {
                        //<jp> "="までのKeyを取得</jp>
                        //<en> Search the key which is leading "="</en>
                        String tempkey = str.substring(0, index).trim();

                        //<jp> セクション内で、キーを見つけたら、値を入れ替える。</jp>
                        //<en> If the key is found in the section, replace the values.</en>
                        if (tempkey.equals(key))
                        {
                            // replace
                            lines.set(i, key + wSeparate + value);
                            break;
                        }
                    }

                    //<jp> 別のセクションに移ったら、その前に値を挿入する。</jp>
                    //<en> When moved to a different section, insert the value to the previous line.</en>
                    if (str.indexOf(SEC_START) == 0)
                    {
                        // insert prev
                        lines.add(i, key + wSeparate + value);
                        lines.add(i + 1, "");
                        break;
                    }
                }
                else
                {
                    //<jp> セクション区切りを探す</jp>
                    //<en> Search the selection delimiter.</en>
                    if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                    {
                        //<jp> セクション名を取得（スペース除去）</jp>
                        //<en> Retrieve the name of the section. (the space will be ommitted.)</en>
                        String tempsection = str.substring(1, str.length() - 1).trim();
                        //<jp> 該当セクションを見つけたら</jp>
                        //<en> If corresponding section is found,</en>
                        if (tempsection.equals(section))
                        {
                            insideSection = true;
                        }
                    }
                }
            }
        }
        //<jp> セクションがあり、最後のセクションで次セクションない場合</jp>
        //<en> If the corresponding section was found, and if it was the last section,</en>
        if (i == lines.size() && insideSection)
        {
            lines.add(key + wSeparate + value);
        }
        //<jp> 該当セクションがなければ、セクションを作る</jp>
        //<en> If there is no corresponding section, create one.</en>
        if (!insideSection)
        {
            lines.add(SEC_START + section + SEC_END);
            lines.add(key + wSeparate + value);

        }
//        flush();
    }

    /**<jp>
     * カテゴリの一覧を返します。
     * @return カテゴリ一覧を配列で返します
     </jp>*/
    /**<en>
     * Return a list of categories.
     * @return :the array of category list
     </en>*/
    public String[] getCategorys()
    {
        Vector secVec = new Vector();
        String[] sectionstr;

        for (int i = 0 ; i < lines.size() ; i++)
        {
            String str = (String)lines.elementAt(i);
            if (str.length() > 0)
            {
                if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                {
                    secVec.addElement(str.substring(1, str.length() - 1).trim());    
                }
            }
        }

        if (secVec == null)
        {
            return null;
        }

        sectionstr = new String[secVec.size()];
        secVec.copyInto(sectionstr);

        return sectionstr;
    }

    /**<jp>
     * 指定したセクションのKEYの一覧を返します。
     * @param section セクション
     * @return セクションのKEY一覧を配列で返します。
     </jp>*/
    /**<en>
     * Return a list of KEYs of the specified section.
     * @param section :section
     * @return :the array of KEY list of the section
     </en>*/
    public String[] getKeys(String section)
    {
        Vector keyVec = new Vector();
        String[] keystr;
        boolean insideSection = false;

        for (int i = 0; i < lines.size() ; i++)
        {
            String str = (String)lines.elementAt(i);

            if (str.length() > 0)
            {
                if (insideSection)
                {
                    //<jp> "="を検索</jp>
                    //<en> Search "=".</en>
                    int index = str.indexOf(wSeparate);

                    //<jp> コメントではなく"="が存在すれば</jp>
                    //<en> If there is no comment but "=" exists,</en>
                    if (str.indexOf(COMMENT_KEY) != 0 && index >= 0)
                    {
                        //<jp> "="までのKeyを取得</jp>
                        //<en> Retrieve the Key which is leading "=",</en>
                        String tempkey = str.substring(0, index).trim();

                        //<jp> セクション内で、キーを見つけたら、Vectorにセット。</jp>
                        //<en> If the key was found in the section, set to Vector.</en>
                        keyVec.addElement(tempkey);    

                    }
                    //<jp> 別のセクションに移ったら、終了。</jp>
                    //<en> Terminate when moved to a different section.</en>
                    if (str.indexOf(SEC_START) == 0)
                    {
                        if (keyVec == null)
                        {
                            return null;
                        }

                        keystr = new String[keyVec.size()];
                        keyVec.copyInto(keystr);

                        return keystr;
                    }
                }
                else
                {
                    //<jp> セクション区切りを探す</jp>
                    //<en> Search the section delimiter.</en>
                    if (str.indexOf(SEC_START) == 0 && str.indexOf(SEC_END) == str.length() - 1)
                    {
                        //<jp> セクション名を取得（スペース除去）</jp>
                        //<en> Retrieve the name of the section. (the space will be ommitted.)</en>
                        String tempsection = str.substring(1, str.length() - 1).trim();

                        //<jp> 該当セクションを見つけたら</jp>
                        //<en> If corresponding section is found,</en>
                        if (tempsection.equals(section))
                        {
                            insideSection = true;
                        }
                    }
                }
            }
        }

        if (keyVec == null)
        {
            return null;
        }

        keystr = new String[keyVec.size()];
        keyVec.copyInto(keystr);

        return keystr;
    }

    /**<jp>
     * INIファイルとしてリソースファイルに書き込みます。
     * set()を呼び出した後は必ずこのメソッドを呼ぶ必要があります。
     * @throws ReadWriteException ファイル操作で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Write in the resource file as INI file.
     * It is necessary that this method should be called always after calling set().
     * @throws ReadWriteException 
     </en>*/
    public void flush() throws ReadWriteException
    {
        try
        {
    //        BufferedWriter writer = new BufferedWriter(new FileWriter(wFileName));


            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(wFileName),
                    "UTF-8"));


            for (int i = 0; i < lines.size(); i++)
            {
                String str = (String)lines.elementAt(i);
                if (str.length() != 0)
                {
                    writer.write(str);
                    writer.newLine();
                }
            }
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 結果セットをマッピング
     </jp>*/
    /**<en>
     * Mapping of result set.
     </en>*/
    protected ToolMenuText convertMenuText(String line)
    {        
        ToolMenuText menu = new ToolMenuText(line);
        return menu;

    }
    // Private methods -----------------------------------------------
    /**<jp>
     * INIファイルとしてリソースファイルを読み込みます。
     * Java -version 1.3.0_02を使用する場合、<CODE>BufferedReader</CODE>の<CODE>ready()</CODE>メソッドは、
     * ファイル最後(EOF)の行も一行とみなしtrueを返します。<BR>
     * その結果、<CODE>readLine()</CODE>はnullを返しnullを<CODE>Vector</CODE>にaddするため、
     * <CODE>Vector</CODE>の最後に格納された文字列を操作するとNullPointerExceptionが発生します。<BR>
     * よって<CODE>readLine()</CODE>でnullを返す場合は<CODE>Vector</CODE>にaddしないようにしています。
     * @throws ReadWriteException ファイル操作で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Read the resource file as INI file.
     * If Java -version 1.3.0_02 is used, <CODE>ready()</CODE> method of <CODE>BufferedReader</CODE>
     * will regard the last line of the file (EOF) as a line to read, and returns true.<BR>
     * As a result, <CODE>readLine()</CODE> will return null and add null to the <CODE>Vector</CODE>;
     * therfore if the string is operated, which was stored in the <CODE>Vector</CODE> lastly, 
     * NullPointerException will occur.<BR>
     * Therefore, if returning null by <CODE>readLine()</CODE>, ensure not to add to <CODE>Vector</CODE>.
     * @throws ReadWriteException 
     </en>*/
    private void read() throws ReadWriteException
    {
        lines.clear();
        try
        {
        //    BufferedReader reader = new BufferedReader(new FileReader(fname));
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(wFileName),
                    "UTF-8"));

            while (reader.ready())
            {
                String str = reader.readLine();
                if (str != null)
                {
                    lines.add(str);
                }
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
    }


    // Debug methods -------------------------------------------------

}
//end of class
