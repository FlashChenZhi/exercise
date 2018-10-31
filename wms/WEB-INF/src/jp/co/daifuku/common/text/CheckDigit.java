// $Id$
package jp.co.daifuku.common.text;

/*
 * Copyright(c) 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.text.StringUtil;


/**
 * チェックデジットを計算するユーティリティクラスです。
 *
 *
 * @version $Revision: $, $Date: $
 * @author  Last commit: $Author: ss $
 */
public class CheckDigit
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンス化できません。
     */
    private CheckDigit()
    {
        // can't create a instance
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 指定されたコードにMod10Weight3のチェックデジットを付与して返します。
     * 
     * @param code チェックデジットを付与するコード
     * @return チェックデジットが付与された文字列
     * @throws RuntimeException 指定されたコードが長さ0の文字列、もしくは数値形式の文字列ではない場合
     */
    public static String addModulas10Weight3(String code)
    {
        if (StringUtil.isBlank(code) || !isNumeric(code))
        {
            throw new RuntimeException("[" + code + "] is not numeric.");
        }
        int total = 0;
        boolean weight3 = true;
        for (int i = code.length() - 1; i >= 0; i--)
        {
            int num = code.charAt(i) - NUM0; // asciiコードから数値を求めます
            total += weight3
                ? 3 * num
                : num;
            weight3 = !weight3;
        }
        int mod = total % 10;
        String result = (mod == 0)
            ? code + "0"
            : code + (10 - mod);

        return result;
    }

    /**
     * 数値0を表すコード
     */
    private static final int NUM0 = 0x30;

    /**
     * 数値9を表すコード
     */
    private static final int NUM9 = 0x39;

    /**
     * 指定された文字列が数値のみで構成されているかどうかを判定します。
     * 
     * @param num 判定する文字列
     * @return 数値形式のみで構成されている場合はtrue、それ以外はfalse
     */
    private static boolean isNumeric(String num)
    {
        for (int i = 0; i < num.length(); i++)
        {
            char ch = num.charAt(i);
            boolean isNum = ch >= NUM0 && ch <= NUM9;
            if (!isNum)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * メインメソッド(テスト用)です。
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println(addModulas10Weight3("312313131312"));
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------

}
