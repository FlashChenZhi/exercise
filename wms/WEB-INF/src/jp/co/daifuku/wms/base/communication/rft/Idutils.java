// $Id: Idutils.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import jp.co.daifuku.common.text.StringUtil;

/**
 * 電文の作成、およびデータファイルの作成時に使用するユーティリティ関数を
 * 提供するクラスです。<br>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author $Author: admin $
 * @since 2008-03-28 全面改訂
 */
public final class Idutils
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 一意に決まらない場合の文字 */
    public static final String DUP_STRING = "*";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成する必要はありません。
     */
    private Idutils()
    {
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 指定されたデータの不足桁数をスペースで埋め、指定された長さのバイト列を返す。<BR>
     * 左詰め用。(文字列から、バイト配列生成)
     * 
     * @param   data        取得してきたデータ(文字列)
     * @param   length      ファイル書込み時のデータ幅
     * @return  右側に不足桁数分の空白が埋められたバイト配列
     */
    public static byte[] createByteDataLeft(String data, int length)
    {
        return alignLeft(toByteArray(data), length);
    }

    /**
     * 指定されたデータの不足桁数をスペースで埋め、指定された長さのバイト列を返す。<BR>
     * 右詰め用。(文字列から、バイト配列生成)
     * 
     * @param   data            取得してきたデータ((文字列))
     * @param   length      ファイル書込み時のデータ幅
     * @return  左側に不足桁数分の空白が埋められたバイト配列
     */
    public static byte[] createByteDataRight(String data, int length)
    {
        return alignRight(toByteArray(data), length);
    }

    /**
     * 指定されたデータの不足桁数をスペースで埋め、指定された長さのバイト列を返す。<BR>
     * 右詰め用。(intから、バイト配列生成)
     * 
     * @param   data            取得してきたデータ(int)
     * @param   length      ファイル書込み時のデータ幅
     * @return  左側に不足桁数分の空白が埋められたバイト配列
     */
    public static byte[] createByteDataRight(int data, int length)
    {
        return createByteDataRight(String.valueOf(data), length);
    }

    /**
     * 一意に決定できない場合の文字列を生成して返します。
     * 
     * @param length 生成文字列長
     * @return 一意でない場合の文字列
     */
    public static String createDupString(int length)
    {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            buf.append(DUP_STRING);
        }
        return String.valueOf(buf);
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
    /**
     * Stringをバイト配列に変換します。<br>
     * 
     * @param data 変換元文字列
     * @return IdMessage.ENCODE に従って変換されたバイト配列
     */
    private static byte[] toByteArray(String data)
    {
        try
        {
            String src = (StringUtil.isBlank(data)) ? ""
                                                   : data;
            return src.getBytes(IdMessage.ENCODE);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e); // usually not occurs
        }
    }

    /**
     * 指定されたデータの不足桁数をスペースで埋め、指定された長さのバイト列を返す。<BR>
     * 元データが指定された長さ以上ある場合、後方からセットされて行き、先頭部分がカットされる。
     * 右詰め用。(バイト配列から、バイト配列生成)
     * 
     * @param data 取得してきたデータ(バイト配列)
     * @param length ファイル書込み時のデータ幅
     * @return 左側に不足桁数分の空白が埋められたバイト配列
     */
    private static byte[] alignRight(byte[] data, int length)
    {
        byte[] retBytes = new byte[length];
        Arrays.fill(retBytes, (byte)' ');

        int offSet = data.length;
        for (int i = length - 1; i >= 0; i--)
        {
            offSet--;
            if (offSet < 0)
            {
                break;
            }
            retBytes[i] = data[offSet];
        }
        return retBytes;
    }

    /**
     * 取得してきたデータの不足桁数をスペースで埋め、指定された長さのバイト列を返す。<BR>
     * 元データが指定された長さ以上ある場合、先頭からセットされて行き、後ろがカットされる。
     * 左詰め用。(バイト配列から、バイト配列生成)
     * 
     * @param   data        取得してきたデータ(バイト配列)
     * @param   length      ファイル書込み時のデータ幅
     * @return  右側に不足桁数分の空白が埋められたバイト配列
     */
    private static byte[] alignLeft(byte[] data, int length)
    {
        byte[] retBytes = new byte[length];
        Arrays.fill(retBytes, (byte)' ');

        for (int i = 0; i < data.length; i++)
        {
            if (i >= length)
            {
                break;
            }
            retBytes[i] = data[i];
        }

        return retBytes;
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
        return "$Id: Idutils.java 87 2008-10-04 03:07:38Z admin $";
    }
}
//end of class
