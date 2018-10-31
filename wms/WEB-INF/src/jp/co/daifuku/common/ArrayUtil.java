// $Id: ArrayUtil.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.lang.reflect.Array;
import java.math.BigDecimal;


/**
 * 配列操作のためのユーティリティ・クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/03/19</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public final class ArrayUtil
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

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * do not use constructor.
     */
    private ArrayUtil()
    {

    }


    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * int配列をObject配列(BigDecimal配列)にセットします。
     * @param args 元データ配列
     * @return Object(BigDecimal)配列
     */
    public static Object[] toObjectArray(int[] args)
    {
        int siz = args.length;
        BigDecimal[] retArr = new BigDecimal[siz];
        for (int i = 0; i < siz; i++)
        {
            retArr[i] = new BigDecimal(args[i]);
        }
        return retArr;
    }

    /**
     * long配列をObject配列(BigDecimal配列)にセットします。
     * @param args 元データ配列
     * @return Object(BigDecimal)配列
     */
    public static Object[] toObjectArray(long[] args)
    {
        int siz = args.length;
        BigDecimal[] retArr = new BigDecimal[siz];
        for (int i = 0; i < siz; i++)
        {
            retArr[i] = new BigDecimal(args[i]);
        }
        return retArr;
    }

    /**
     * float配列をObject配列(BigDecimal配列)にセットします。
     * @param args 元データ配列
     * @return Object(BigDecimal)配列
     */
    public static Object[] toObjectArray(float[] args)
    {
        int siz = args.length;
        BigDecimal[] retArr = new BigDecimal[siz];
        for (int i = 0; i < siz; i++)
        {
            retArr[i] = new BigDecimal(String.valueOf(args[i]));
        }
        return retArr;
    }

    /**
     * double配列をObject配列(BigDecimal配列)にセットします。
     * @param args 元データ配列
     * @return Object(BigDecimal)配列
     */
    public static Object[] toObjectArray(double[] args)
    {
        int siz = args.length;
        BigDecimal[] retArr = new BigDecimal[siz];
        for (int i = 0; i < siz; i++)
        {
            retArr[i] = new BigDecimal(String.valueOf(args[i]));
        }
        return retArr;
    }

    /**
     * short配列をObject配列(BigDecimal配列)にセットします。
     * @param args 元データ配列
     * @return Object(BigDecimal)配列
     */
    public static Object[] toObjectArray(short[] args)
    {
        int siz = args.length;
        BigDecimal[] retArr = new BigDecimal[siz];
        for (int i = 0; i < siz; i++)
        {
            retArr[i] = new BigDecimal(args[i]);
        }
        return retArr;
    }

    /**
     * byte配列をObject配列(BigDecimal配列)にセットします。
     * @param args 元データ配列
     * @return Object(BigDecimal)配列
     */
    public static Object[] toObjectArray(byte[] args)
    {
        int siz = args.length;
        BigDecimal[] retArr = new BigDecimal[siz];
        for (int i = 0; i < siz; i++)
        {
            retArr[i] = new BigDecimal(args[i]);
        }
        return retArr;
    }

    /**
     * オブジェクト配列が null または 要素 0 であるかどうかチェックします。
     * @param array チェック対象
     * @return null または 要素0 のとき true.
     */
    public static boolean isEmpty(Object[] array)
    {
        return (0 == length(array));
    }

    /**
     * プリミティブの配列が null または 要素 0 であるかどうかチェックします。
     * 
     * @param array チェック対象配列 (プリミティブ配列)
     * @return null または 要素0 のとき true.
     */
    public static boolean isEmpty(Object array)
    {

        return (0 == length(array));

    }

    /**
     * オブジェクト配列の要素数を返します。<br>
     * 配列が null の場合は 要素数 0 で返されます。
     * 
     * @param array チェック対象配列
     * @return 要素数。array が null のときは 0
     */
    public static int length(Object[] array)
    {
        if (null == array)
        {
            return 0;
        }
        return array.length;
    }


    /**
     * プリミティブ配列の要素数を返します。<br>
     * 配列が null の場合は 要素数 0 で返されます。
     * 
     * @param array チェック対象配列
     * @return 要素数。array が null のときは 0
     */
    public static int length(Object array)
    {
        if (null == array)
        {
            return 0;
        }
        return Array.getLength(array);
    }


    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


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
        return "$Id: ArrayUtil.java 87 2008-10-04 03:07:38Z admin $";
    }
}
