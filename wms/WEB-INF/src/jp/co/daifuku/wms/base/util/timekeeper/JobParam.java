// $Id: JobParam.java 5527 2009-11-09 08:03:43Z ota $
package jp.co.daifuku.wms.base.util.timekeeper;

import java.util.Arrays;
import java.util.List;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 自動処理の設定内容を保持するクラスです。<br>
 *
 * @version $Revision: 5527 $, $Date: 2009-11-09 17:03:43 +0900 (月, 09 11 2009) $
 * @author  清水　正人
 * @author  Last commit: $Author: ota $
 */

public class JobParam
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
    // private String p_Name ;
    private String _className = null;

    private String[] _args = null;

    private Interval _interval = null;

    private ScheduleJob _job = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 実行タイミング文字列と自動実行を行うクラス名を
     * 指定してインスタンス化します。
     * 
     * @param timingString 実行タイミング文字列
     * @param className 自動実行を行うクラス名
     * @param args パラメータ(使用しません)
     * @param enable 有効なjobのとき true
     */
    public JobParam(final String timingString, final String className, final String[] args, boolean enable)
    {
        setTimingString(timingString, enable);
        setClassName(className);
        setArgs(args);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 実行時間に到達しているかどうかをチェックします。
     * 
     * @return 実行時間に到達していた場合は<code>true</code>を返します。
     */
    public boolean isTime()
    {
        return getInterval().isComing(true);
    }

    /**
     * クラスおよび引数が同じであれば同じ内容と見なします。
     * 
     * @param job2 比較対象
     * @return 同じ場合 true.
     */
    public boolean equals(JobParam job2)
    {
        if (!getClassName().equals(job2.getClassName()))
        {
            return false;
        }
        String[] t1 = getArgs();
        String[] t2 = job2.getArgs();

        // t1 and t2 is not null (getArgs is not return null)
        List<String> t1List = Arrays.asList(t1);
        List<String> t2List = Arrays.asList(t2);
        return t1List.equals(t2List);
    }

    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof JobParam)
        {
            return equals((JobParam)obj);
        }
        return super.equals(obj);
    }

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        String[] args = getArgs();
        int ahash = 0;

        for (String arg : args)
        {
            ahash += arg.hashCode();
        }
        String className = getClassName();
        int hash = className.hashCode() + ahash;
        return hash;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 自動実行を行うクラス名を設定します。
     * 
     * @param className 実行クラス名
     */
    public void setClassName(final String className)
    {
        _className = className;
    }

    /**
     * 自動実行を行うクラス名を取得します。
     * 
     * @return 実行クラス名
     */
    public String getClassName()
    {
        return _className;
    }

    /**
     * 自動実行を行うクラスに対するパラメータを設定します。
     * 
     * @param args パラメータ
     */
    public void setArgs(final String[] args)
    {
        _args = args;
    }

    /**
     * 自動実行を行うクラスに対するパラメータを取得します。<br>
     * このメソッドでは nullは返されません。パラメータが
     * 未設定の場合は、要素数が0の配列が返されます。<br>
     * 従って、null チェックは不要です。
     * 
     * @return パラメータ
     */
    public String[] getArgs()
    {
        if (null == _args)
        {
            return new String[0];
        }
        return _args;
    }

    /**
     * 実行タイミング文字列設定
     * 実行タイミング文字列を設定します。
     * @param timingString 実行タイミング文字列
     * @param enable 有効な時 true
     */
    public void setTimingString(final String timingString, boolean enable)
    {
        _interval = new Interval(timingString);
        _interval.setEnabled(enable);
    }

    /**
     * Interval設定
     * cronの様な時間指定を管理するクラスIntervalを設定します。
     * @param interval Intervalクラス
     */
    public void setInterval(final Interval interval)
    {
        _interval = interval;
    }

    /**
     * Interval取得
     * cronの様な時間指定を管理するクラスIntervalを取得します。
     * @return Intervalクラス
     */
    public Interval getInterval()
    {
        return _interval;
    }

    /**
     * @return Interval設定が有効の時 true を返します。
     */
    public boolean isEnabled()
    {
        return _interval.isEnabled();
    }

    /**
     * 実行クラス設定
     * 自動実行を行うクラスへの参照を設定します。
     * @param job 実行クラス
     */
    public void setScheduleJob(final ScheduleJob job)
    {
        _job = job;
    }

    /**
     * 実行クラス取得
     * 自動実行を行うクラスへの参照を取得します。
     * @return 実行クラス
     */
    public ScheduleJob getScheduleJob()
    {
        return _job;
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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: JobParam.java 5527 2009-11-09 08:03:43Z ota $";
    }
}
