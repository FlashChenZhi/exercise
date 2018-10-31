// $Id: SequenceInfo.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db.sequence;

import java.text.DecimalFormat;

import jp.co.daifuku.common.text.SimpleFormat;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * シーケンスオブジェクトのプロパティを保持するクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class SequenceInfo
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String p_name;

    private String p_format;

    private String _numFormat;

    private long p_start;

    private long p_max;

    private int p_cacheSize;

    private boolean p_cycle;

    private boolean p_checked = false;

    private long _lastNumber = Long.MIN_VALUE;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 各プロパティを指定してインスタンスを生成します。
     * 
     * @param name Sequence名
     * @param format 文字列フォーマット (SimpleFormat互換)
     * @param numformat 数値をフォーマットする際のパターン(DecimalFormat互換)<br>
     * DecimalFormat互換のフォーマット文字列を指定します。
     * @param start 開始番号
     * @param max 最大番号
     * @param numcache シーケンスのキャッシュサイズ
     * @param cycle 最大の番号に達したとき開始番号に戻るときはtrue.
     * 
     * @see java.text.DecimalFormat
     */
    public SequenceInfo(String name, String format, String numformat, long start, long max, int numcache, boolean cycle)
    {
        setName(name);
        setFormat(format);
        setNumFormat(numformat);
        setStart(start);
        setMax(max);
        setCacheSize(numcache);
        setCycle(cycle);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 数値のシーケンスを文字列化します。
     * @param num フォーマットする数値
     * @return 文字列化したシーケンス
     */
    public String format(long num)
    {
        // decimal format first.
        String fnumTmp = formatNumber(num);

        // format simple
        String fnum = formatSimple(fnumTmp);

        return fnum;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return formatを返します。
     */
    public String getFormat()
    {
        return p_format;
    }

    /**
     * @param format formatを設定します。
     */
    public void setFormat(String format)
    {
        p_format = format;
    }

    /**
     * 数値フォーマットを返します。
     * @return  数値フォーマット
     */
    public String getNumFormat()
    {
        return _numFormat;
    }

    /**
     * 数値フォーマットを設定します。
     * @param numFormat 数値フォーマット
     */
    public void setNumFormat(String numFormat)
    {
        _numFormat = numFormat;
    }

    /**
     * チェック済みかどうかを返します。
     * @return チェック済みのとき true.
     */
    public synchronized boolean isChecked()
    {
        return p_checked;
    }

    /**
     * チェック済みを設定します。
     * @param checked チェック済み
     */
    public synchronized void setChecked(boolean checked)
    {
        p_checked = checked;
    }

    /**
     * @return nameを返します。
     */
    public String getName()
    {
        return p_name;
    }

    /**
     * @param name nameを設定します。
     */
    public void setName(String name)
    {
        p_name = name;
    }

    /**
     * @return cycleを返します。
     */
    public boolean isCycle()
    {
        return p_cycle;
    }

    /**
     * @param cycle cycleを設定します。
     */
    public void setCycle(boolean cycle)
    {
        p_cycle = cycle;
    }

    /**
     * @return maxを返します。
     */
    public long getMax()
    {
        return p_max;
    }

    /**
     * @param max maxを設定します。
     */
    public void setMax(long max)
    {
        p_max = max;
    }

    /**
     * @return startを返します。
     */
    public long getStart()
    {
        return p_start;
    }

    /**
     * @param start startを設定します。
     */
    public void setStart(long start)
    {
        p_start = start;
    }

    /**
     * 最終取得値を返します。<br>
     * シーケンスがループしたかどうか判定するには、setLastNumber()
     * の返値をチェックするようにしてください。
     * @return 最終取得値
     */
    public synchronized long getLastNumber()
    {
        return _lastNumber;
    }

    /**
     * 最終取得値を設定します。
     * @param newSeq 最終取得値
     * @return 現在の最終シーケンスよりも小さい値をセットしたとき true.<br>
     * シーケンスがループしたことを示します。
     */
    public synchronized boolean setLastNumber(long newSeq)
    {
        boolean looped = (_lastNumber > newSeq);
        _lastNumber = newSeq;

        return looped;
    }

    /**
     * @return cacheSizeを返します。
     */
    public int getCacheSize()
    {
        return p_cacheSize;
    }

    /**
     * @param cacheSize cacheSizeを設定します。
     */
    public void setCacheSize(int cacheSize)
    {
        p_cacheSize = cacheSize;
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
     * シンプルフォーマットを行います。
     * 
     * @param fnumTmp
     * @return シンプルフォーマット済み文字列
     */
    protected String formatSimple(String fnumTmp)
    {
        String fmt = getFormat();
        if (isEmpty(fmt))
        {
            return fnumTmp;
        }
        String[] fp = {
            fnumTmp,
        };
        return SimpleFormat.format(fmt, fp);
    }

    /**
     * 数値の文字列化を行います。
     * 
     * @param num
     * @return フォーマット済み文字列
     */
    protected String formatNumber(long num)
    {
        String fmt = getNumFormat();
        if (isEmpty(fmt))
        {
            return String.valueOf(num);
        }

        DecimalFormat dfmtter = new DecimalFormat(fmt);

        return dfmtter.format(num);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------

    /**
     * 文字列が空かどうかテストします。
     * 
     * @param str テスト対象
     * @return nullまたは長さ0の文字列のときtrue.
     */
    protected boolean isEmpty(String str)
    {
        boolean cont = (str == null) || (str.length() == 0);
        return cont;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: SequenceInfo.java 87 2008-10-04 03:07:38Z admin $";
    }
}
