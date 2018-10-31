// $Id: Interval.java 5527 2009-11-09 08:03:43Z ota $
package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * crontabの様な間隔指定をサポートするためのクラスです。<br>
 * 単純に秒の指定のみも使用できます。
 *
 *
 * @version $Revision: 5527 $, $Date: 2009-11-09 17:03:43 +0900 (月, 09 11 2009) $
 * @author  S.Suzuki
 * @author  Last commit: $Author: ota $
 */
@SuppressWarnings("unchecked")
public class Interval
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** NO id set indicator */
    public static final int ID_NOT_SET = -1;

    private static final String SPLIT_FIELD = " |\t";

    private static final int I_MIN = 0;

    private static final int I_HOUR = 1;

    private static final int I_DAY = 2;

    private static final int I_MONTH = 3;

    private static final int I_WEEK = 4;

    private static final String[] RANGE_ARR = {
        "0-59",
        "0-23",
        "1-31",
        "1-12",
        "0-6"
    };

    private static final long[] SEC_UNIT_ARR = {
        60,
        3600,
        86400,
        2592000,
        604800
    };

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private int _ID = ID_NOT_SET;

    private String _timingString = "";

    private boolean _simpleInterval = true;

    private long _simpleIntervalSec;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private final List<Integer>[] _timingListArr = new List[I_WEEK + 1];

    private Date _lastTiming;

    private long _lastCheckTime;

    private boolean _enabled = true;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 最終実行時刻を初期化します。
     */
    public Interval()
    {
        // nothing to do
        // set last executed and checked
        Date now = new Date();
        setLastTiming(now);
        _lastCheckTime = now.getTime();
    }

    /**
     * このタイミングについて、タイミング定義を指定してインスタンスを生成します。
     *
     * @param defstring String
     *   * <br>フォーマットはcrontabに準じます。
     *<pre>
     * フィールド 指定可能な値
     *            ---------- --------------
     *            分         0-59
     *            時         0-23
     *            日         1-31
     *            月         1-12
     *            曜日       0-6 (0は日曜日, 6が土曜日。)
     *
     *      フィールドにはアスタリスク(*)も指定できる。これはあらゆるフィールドで
     *      ``first-last'' という意味になる。
     *
     *      数値の範囲も指定できる。範囲は二つの数をハイフンでつなげる。指定数値も領域に含まれる。
     *     例えば「時」に 8-11 を指定すると、 8時, 9時, 10時, 11時に実行することになる。
     *
     *      リストもできる。 リストはコンマで区切られた数値 (または範囲) のセットである。
     *      例：``1,2,5,9'', ``0-4,8-12''
     *
     *      間隔値を範囲とともに指定することもできる。範囲の後に ``/<number>'' と指定すると、範
     *      囲 内で指定数値ずつ飛ばすことになる。例えば「時」フィールドに ``0-23/2'' と指定する
     *      と、2時間おきに実行される。間隔はアスタリスクの後にも指定できる。「2時間
     *      おき」といいたければ ``＊/2''とする。
     *
     *      特に指定時刻/日時がなく、単に間隔のみを指定したいときは全てのフィールドに``*''
     *      を指定して、間隔値を指定する。
     *      例 : 3時間 20分間隔
     *           ＊/20  ＊/3     ＊    ＊    ＊
     *
     * もうひとつの指定方法は、単純に秒数をセットする方法です。
     * この指定方法の場合、0以下の数値を指定したときは実行対象外となります。
     * 例 : 30秒間隔
     *           30
     * </pre>
     */
    public Interval(final String defstring)
    {
        this();
        setTimingString(defstring);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 実行時刻が来ているかどうか確認します。
     * <br>最終実行時刻と同一時刻(分単位)のときは、falseが返ります。
     *
     * @param updateTime boolean 実行時刻が来ているとき、同時に最終実行時刻を更新する場合は true.
     * @return boolean 実行時刻が来ているときtrue.
     */
    public boolean isComing(final boolean updateTime)
    {
        if (!isEnabled())
        {
            return false;
        }

        Date now = new Date();
        Date lastTiming = getLastTiming();

        boolean match = false;
        // check same time with last executed.
        if (isSimpleInterval())
        {
            match = checkSimpleInterval(lastTiming, now);
        }
        else
        {
            // check time from now to last checked or executed
            long lastCheck = Math.max(lastTiming.getTime(), _lastCheckTime);

            // make calendar for current time
            Calendar chk = Calendar.getInstance();
            chk.setTime(now);

            while (lastCheck < chk.getTimeInMillis())
            {
                match = checkNormalInterval(lastTiming, chk.getTime());
                if (match)
                {
                    break;
                }
                chk.add(Calendar.MINUTE, -1); // current - 1 minutes
            }
            // update checked time
            if (updateTime)
            {
                _lastCheckTime = now.getTime();
            }
        }

        if (match && updateTime)
        {
            setLastTiming(now);
        }
        return match;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 最終実行時刻をセットします。
     *
     * @param t Date セットする時刻
     */
    public void setLastTiming(final Date t)
    {
        _lastTiming = t;
    }

    /**
     * 現在の日時を最終実行時刻としてセットします。
     */
    public void setLastTiming()
    {
        setLastTiming(new Date());
    }

    /**
     * 最終実行時刻を取得します。
     * @return Date
     */
    public Date getLastTiming()
    {
        return _lastTiming;
    }

    /**
     * 有効なスケジュールかどうかを返します。
     * @return 有効なとき true.
     */
    public boolean isEnabled()
    {
        return _enabled;
    }

    /**
     * 有効なスケジュールかどうかを設定します。
     * @param enabled 有効なとき true
     */
    public void setEnabled(boolean enabled)
    {
        _enabled = enabled;
    }

    /**
     * このインスタンスのID(内部管理用)をセットします。
     *
     * @param id int
     */
    public void setID(final int id)
    {
        _ID = id;
    }

    /**
     * このインスタンスのIDを取得します。
     * @return int
     */
    public int getID()
    {
        return _ID;
    }

    /**
     * タイミング定義文字列をセットします。
     * @param timingdef String
     */
    public void setTimingString(final String timingdef)
    {
        _timingString = timingdef;
        parseTiming();
    }

    /**
     * タイミング定義文字列をセットします。
     * @return String
     */
    public String getTimingString()
    {
        return _timingString;
    }

    /**
     * 単純間隔タイプかどうかを確認します。
     * <br>実行間隔を単純な時間で管理するとき、trueを返します。
     * @return boolean
     */
    public boolean isSimpleInterval()
    {
        return _simpleInterval;
    }

    /**
     * 単純間隔タイプのとき、その間隔を秒数で返します。
     * @return long 実行間隔(秒)
     */
    public long getSimpleIntervalSec()
    {
        return _simpleIntervalSec;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 単純間隔を確認します。<br>
     * 0以下の実行間隔を指定されているときは必ず falseが
     * 返されます。
     *
     * @param t Date 比較する日時
     * @return 実行対象の時 true
     */
    private boolean checkSimpleInterval(final Date lastexec, final Date curr)
    {
        long intervalSec = getSimpleIntervalSec();
        if (0 >= intervalSec)
        {
            return false;
        }

        final long now = curr.getTime();
        final long last = lastexec.getTime();
        final long interval = intervalSec * 1000;
        return (interval <= (now - last));
    }

    /**
     * 通常の間隔指定を確認します。
     *
     * @param t Date 比較する日時
     * @return boolean
     */
    private boolean checkNormalInterval(final Date lastexec, final Date curr)
    {
        final int[] lastEx = divideDateToInt(lastexec);
        final int[] currArr = divideDateToInt(curr);

        // check last check between current time
        // is same date and time (in minuts)
        boolean sameTime = true;
        for (int i = 0; i < currArr.length; i++)
        {
            if (currArr[i] != lastEx[i])
            {
                sameTime = false;
                break;
            }
        }
        if (sameTime)
        {
            return false;
        }

        // check time in timing.
        boolean match = true;
        for (int i = 0; i < currArr.length; i++)
        {
            final Integer tmI = Integer.valueOf(currArr[i]);
            if (!_timingListArr[i].contains(tmI))
            {
                match = false;
                break;
            }
        }
        return match;
    }

    /**
     * タイミング定義を展開します。
     */
    private void parseTiming()
    {
        final List<OneFieldParser> parserList = new ArrayList<OneFieldParser>();

        final String tdef = getTimingString();

        try
        {
            // 単純な秒指定
            final int seconds = Integer.parseInt(tdef);
            _simpleIntervalSec = seconds;
            return;
        }
        catch (final Exception e)
        {
            // cron互換の指定
            final String[] unitarr = tdef.split(SPLIT_FIELD);
            for (int i = 0; i < unitarr.length; i++)
            {
                final String tstr = unitarr[i];
                if (tstr.equals(""))
                {
                    continue;
                }
                final OneFieldParser parser = new OneFieldParser(tstr, RANGE_ARR[i]);
                parserList.add(parser);

                if (!parser.getTimeDefine().equals(OneFieldParser.DEFINE_ALL))
                {
                    _simpleInterval = false;
                }
            }

            // just interval only
            if (_simpleInterval)
            {
                for (int i = 0; i < parserList.size(); i++)
                {
                    final OneFieldParser p = parserList.get(i);
                    if (p.hasInterval())
                    {
                        _simpleIntervalSec += p.getInterval() * SEC_UNIT_ARR[i];
                    }
                }
                return;
            }

            for (int i = 0; i < _timingListArr.length; i++)
            {
                final OneFieldParser p = parserList.get(i);
                List tList = p.parse();
                if (tList.isEmpty())
                {
                    throw new RuntimeException("Invalid interval defined. [" + tdef + "]");
                }
                _timingListArr[i] = tList;
            }
        }
    }

    /**
     * 日時を分,時,日,月,曜日の配列に分割します。
     *
     * @param d Date
     * @return int[]
     */
    private static int[] divideDateToInt(final Date d)
    {
        final int[] tdArr = new int[I_WEEK + 1];
        final Calendar c = Calendar.getInstance();
        c.setTime(d);
        tdArr[I_MIN] = c.get(Calendar.MINUTE);
        tdArr[I_HOUR] = c.get(Calendar.HOUR_OF_DAY);
        tdArr[I_DAY] = c.get(Calendar.DAY_OF_MONTH);
        tdArr[I_MONTH] = c.get(Calendar.MONTH) + 1; // adjust 1 to 12
        tdArr[I_WEEK] = c.get(Calendar.DAY_OF_WEEK) - 1; // adjust 0 as Sunday
        return tdArr;
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
        return "$Id: Interval.java 5527 2009-11-09 08:03:43Z ota $";
    }

    /**
     * for test.
     * @param argv
     * @throws Exception
     */
    public static void main(final String[] argv)
            throws Exception
    {
        final Interval me = new Interval();
        if (true)
        {
            me.setLastTiming(new Date(0));
            String[] defs = {
                "* a b c d",
                "aaa",
                "000",
                "0",
                "* * * * *",
                "2/* * * 4 *",
                "0,6,30-59/6 10,11 2-9,20-30/3 * *",
                "*/30 */2 * * *"
            };
            for (String def : defs)
            {
                try
                {
                    System.out.print("\n" + def + ":  ");
                    me.setTimingString(def);
                    System.out.println("OK");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Thread.sleep(1000);
            }
        }
        else
        {
            Interval.divideDateToInt(new Date());

            //me.setTimingString("0,6,30-59/6 10,11 2-9,20-30/3 * *") ;
            //me.setTimingString("*/30 */2 * * *") ;

            me.setTimingString("*/1 * * * *");

            for (int i = 0; i < 100; i++)
            {
                if (me.isComing(true))
                {
                    System.out.println("Exec!" + new Date());
                }
                Thread.sleep(10000);
            }
        }
    }
}


/**
 * 時間指定の１フィールドを展開するためのクラスです。
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 未入力
 * @version 1.0
 */

class OneFieldParser
{
    /** 全てを対象 */
    public static final String DEFINE_ALL = "*";

    /** 時間間隔を指定する際の区切り */
    public static final String SPLIT_INTERVAL = "/";

    /** 時間範囲を指定する際の区切り */
    public static final String SPLIT_RANGE = "-";

    /** 候補を指定する際の区切り */
    public static final String SPLIT_CSV = ",";

    private final String _defineString;

    private String _timeDefine;

    private final String _maxRange;

    private int _interval = 1;

    private boolean _hasInterval = false;

    /**
     * 時間指定文字列を指定してインスタンスを生成します。
     *
     * @param def String
     * @param maxRange String
     */
    public OneFieldParser(final String def, final String maxRange)
    {
        _defineString = def;
        _maxRange = maxRange;
        if (_defineString.indexOf(SPLIT_INTERVAL) > -1)
        {
            final String[] tmpArr = _defineString.split(SPLIT_INTERVAL);
            _timeDefine = tmpArr[0];
            try
            {
                _interval = Integer.parseInt(tmpArr[1]);
                _hasInterval = true;
            }
            catch (final Exception e)
            {
                // このExceptionは無視します
            }
        }
        else
        {
            _timeDefine = def;
        }
    }

    /**
     * コンストラクタで指定された時間設定をIntegerのリストに
     * 変換します。
     *
     * @return List
     */
    public List<Integer> parse()
    {
        // All of range or selected?
        final String timedef = (_timeDefine.equals(DEFINE_ALL)) ? _maxRange
                                                               : _timeDefine;
        return csvToList(timedef, _interval);
    }

    /**
     * 間隔指定を取得します。
     *
     * @return int
     */
    public int getInterval()
    {
        return _interval;
    }

    /**
     * 間隔指定を取り除いた時間設定を取得します。
     *
     * @return String
     */
    public String getTimeDefine()
    {
        return _timeDefine;
    }

    /**
     * 間隔指定があるかどうかを確認します。
     *
     * @return boolean
     */
    public boolean hasInterval()
    {
        return _hasInterval;
    }

    /**
     * CSV型式で定義されている時間設定をIntegerのリストに変換します。
     * <br>間隔指定は、指定された間隔分スキップしたリストとなります。
     *
     * @param defcsv String 時間定義
     * @param interval int 間隔指定
     * @return List
     */
    private static List<Integer> csvToList(final String defcsv, final int interval)
    {
        final List<Integer> tlist = new ArrayList<Integer>();
        final String[] defarr = defcsv.split(SPLIT_CSV);
        for (final String defpc : defarr)
        {
            // Range specified or One.
            if (defpc.indexOf(SPLIT_RANGE) > -1)
            {
                tlist.addAll(rangeToList(defpc, interval));
            }
            try
            {
                tlist.add(Integer.valueOf(defpc));
            }
            catch (final Exception e)
            {
                // ignore
            }
        }
        return tlist;
    }

    /**
     * "first-end" 型式の範囲指定文字列から、Integerのリストを
     * 作成します。
     * <br>間隔指定は、指定された間隔分スキップしたリストとなります。
     *
     * @param range String 範囲指定文字列
     * @param interval int 間隔指定
     * @return List
     */
    private static List<Integer> rangeToList(final String range, final int interval)
    {
        final List<Integer> tlist = new ArrayList<Integer>();
        final String[] fr = range.split(SPLIT_RANGE);
        try
        {
            final int f = Integer.parseInt(fr[0]);
            final int r = Integer.parseInt(fr[1]);
            final int divc = f % interval;

            for (int i = f; i <= r; i++)
            {
                if (divc == (i % interval))
                {
                    tlist.add(Integer.valueOf(i));
                }
            }
        }
        catch (final Exception e)
        {
            // このExceptionは無視します
        }
        return tlist;
    }
}
