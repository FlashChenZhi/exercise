//$Id: Range.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 範囲を保持・チェックするためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class Range
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 範囲外のときの返値 */
    public static final int RANGE_OUT = 1;

    /** 範囲内のときの返値 */
    public static final int RANGE_VALID = 0;

    /** 列挙定義の区切り文字 */
    public static final String DELIMITER_ENUMERATE = ",";

    /** 範囲定義の区切り文字 */
    public static final String DELIMITER_RANGE = "~";

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private List p_enumerateList;

    private List p_rangeList;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 定義を解釈してインスタンスを初期化します。
     * @param rangeDef 範囲定義
     */
    public Range(String rangeDef)
    {
        parse(rangeDef);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 文字列として定義されている範囲を解釈します。
     * @param rangeDef 定義文字列
     */
    public void parse(String rangeDef)
    {
        String[] eSimple = rangeDef.split(DELIMITER_ENUMERATE);
        TreeSet eSet = new TreeSet();
        List rList = new ArrayList();
        for (int i = 0; i < eSimple.length; i++)
        {
            if (0 > eSimple[i].indexOf(DELIMITER_RANGE))
            {
                eSet.add(createSubject(eSimple[i]));
            }
            else
            {
                OneRange range = createOneRange(eSimple[i]);
                if (range != null)
                {
                    rList.add(range);
                }
            }
        }
        // 列挙リストは contains()をオーバーライドしたものが
        // 必要な場合があるのでサブクラスに作成させる場合あり
        setEnumerateList(createEnumList(eSet));

        setRangeList(rList);
    }

    /**
     * 範囲内にあるかどうかチェックします。
     * 
     * @param value チェックする値
     * @return 範囲内のとき: RANGE_VALID
     */
    public int inRange(Comparable value)
    {
        List eList = getEnumerateList();

        if (eList.contains(value))
        {
            return RANGE_VALID;
        }

        List rList = getRangeList();
        for (int i = 0; i < rList.size(); i++)
        {
            OneRange range = (OneRange)rList.get(i);
            int cRet = range.inRange(value);
            if (cRet == RANGE_VALID)
            {
                return RANGE_VALID;
            }
        }
        return RANGE_OUT;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return enumerateListを返します。
     */
    protected List getEnumerateList()
    {
        return p_enumerateList;
    }

    /**
     * @param enumerateList enumerateListを設定します。
     */
    protected void setEnumerateList(List enumerateList)
    {
        p_enumerateList = enumerateList;
    }

    /**
     * @return rangeListを返します。
     */
    protected List getRangeList()
    {
        return p_rangeList;
    }

    /**
     * @param rangeList rangeListを設定します。
     */
    protected void setRangeList(List rangeList)
    {
        p_rangeList = rangeList;
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
     * 列挙用のリストを作成します。<br>
     * contains()メソッドが正しく動作するListクラスを作成するように
     * オーバーライドします。
     *  
     * @param set 列挙セット
     * @return 列挙用のリスト
     */
    protected abstract List createEnumList(Set set);

    /**
     * 比較対象を生成します。
     * @param piece
     * @return 比較対象
     */
    protected abstract Comparable createSubject(String piece);

    /**
     * 範囲指定を生成します。
     * 
     * @param define 定義文字列
     * @return 範囲指定
     */
    protected OneRange createOneRange(String define)
    {
        String[] rSimple = define.split(DELIMITER_RANGE);
        if (rSimple.length < 2)
        {
            rSimple = createOpenRange(define);
        }

        Comparable min = createSubject(rSimple[0]);
        Comparable max = createSubject(rSimple[1]);
        return new OneRange(min, max);
    }

    /**
     * 一方が開放されている範囲を生成します。
     * 
     * @param define 定義文字列
     * @return 範囲指定
     */
    protected String[] createOpenRange(String define)
    {
        String[] rSimple = {
            null,
            null,
        };
        int dp = define.indexOf(DELIMITER_RANGE);
        if (dp == 0)
        {
            // max value defined
            rSimple[1] = define.substring(dp + 1);
        }
        else
        {
            // min value defined
            rSimple[0] = define.substring(0, dp);
        }
        return rSimple;
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
        return "$Id: Range.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * 一対の範囲を保持・チェックするクラスです。
     *
     * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
     * @author  ss
     * @author  Last commit: $Author: admin $
     */
    static class OneRange
    {
        static final int RANGE_SMALL = -1;

        static final int RANGE_LARGE = 1;

        private Comparable _minValue;

        private Comparable _maxValue;

        OneRange(Comparable min, Comparable max)
        {
            _minValue = min;
            _maxValue = max;
        }

        int inRange(Comparable value)
        {
            if (value == null)
            {
                return RANGE_VALID;
            }

            // null means W/O check
            if (_minValue != null)
            {
                if (0 > value.compareTo(_minValue))
                {
                    return RANGE_SMALL;
                }
            }
            if (_maxValue != null)
            {
                if (0 < value.compareTo(_maxValue))
                {
                    return RANGE_LARGE;
                }
            }
            return RANGE_VALID;
        }

        /**
         * 最小値と最大値を文字列化します。
         * @return 文字列表現
         */
        public String toString()
        {
            return "MIN:" + _minValue + ",MAX:" + _maxValue;
        }
    }
}
