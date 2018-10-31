// $Id: PatliteCommandSet.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.BitSet;

/**
 * パトライトコマンドのビット操作を行うクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public class PatliteCommandSet
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final int BIT_SIZE = 8;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private BitSet _command;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データ保持オブジェクトを生成してインスタンスを初期化します。
     * 
     * @param command 操作対象コマンド
     */
    public PatliteCommandSet(byte command)
    {
        super();
        _command = toBitSet(command);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 指定されたビットインデックスのビットを設定します。<br>
     * ビットインデックスは0～7として、それ以外を指定した場合は何も行いません。
     * 
     * @param bitIndex ビットインデックス
     * @param bitOn <code>true</code>でセット
     */
    public void set(int bitIndex, boolean bitOn)
    {
        int idx = convertIndex(bitIndex);
        if (0 <= idx)
        {
            _command.set(idx, bitOn);
        }
    }

    /**
     * 指定されたビットインデックスのビットをONにします。<br> 
     * このメソッドは set(bitIndex, true) と同様です。<br>
     * ビットインデックスは 0～7 として、それ以外を指定した場合は何も行いません。
     * 
     * @param bitIndex ビットインデックス
     */
    public void set(int bitIndex)
    {
        set(bitIndex, true);
    }

    /**
     * 指定されたビットインデックスのビットをOFFにします。<br> 
     * このメソッドは set(bitIndex, false) と同様です。<br>
     * ビットインデックスは 0～7 として、それ以外を指定した場合は何も行いません。
     * 
     * @param bitIndex ビットインデックス
     */
    public void reset(int bitIndex)
    {
        set(bitIndex, false);
    }

    /**
     * 指定したバイトデータを設定します。<br>
     * コマンドセットを1バイトのビットマップとしてセットします。
     * 
     * @param data バイトデータ
     */
    public void set(byte data)
    {
        _command = toBitSet(data);
    }

    /**
     * 指定されたビットインデックスの状態を取得します。<br>
     * ビットインデックスは 0～7 として、それ以外を指定した場合は<code>false</code>
     * を返します。
     * 
     * @param bitIndex ビットインデックス
     * @return 指定されたビットインデックスがONの時、<code>true</code>が返ります。
     */
    public boolean get(int bitIndex)
    {
        int idx = convertIndex(bitIndex);
        if (0 <= idx)
        {
            return _command.get(idx);
        }
        return false;
    }

    /**
     * 現状のコマンドビットセットを1バイトのビットマップで取得します。
     * 
     * @return コマンド
     */
    public byte getValue()
    {
        return toByte(_command);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 保持しているビットセットを取得します。
     * 
     * @return ビットセット
     */
    public BitSet getBitSet()
    {
        return _command;
    }

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
     * パトライトコマンドのビットインデックスに適合する様に変換します。<br>
     * 指定されたビットインデックスが変換範囲外であった場合は、-1を返します。
     * 
     * @param index ビットインデックス
     * @return パトライトコマンド用ビットインデックス
     */
    private int convertIndex(int index)
    {
        if (0 > index || BIT_SIZE <= index)
        {
            return -1;
        }

        return BIT_SIZE - index - 1;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * バイトデータをビットセットへ変換します。
     * 
     * @param data バイトデータ
     * @return ビットセット
     */
    private static synchronized BitSet toBitSet(byte data)
    {
        byte tByte = data;
        BitSet bitset = new BitSet(BIT_SIZE);
        for (int i = 0; i < BIT_SIZE; i++)
        {
            boolean value = (0 < (0x80 & tByte)) ? true
                                                : false;
            bitset.set(i, value);
            tByte <<= 1;
        }
        return bitset;
    }

    /**
     * ビットセットデータをバイトへ変換します。
     * 
     * @param data ビットセットデータ
     * @return バイト
     */
    private static synchronized byte toByte(BitSet data)
    {
        byte retByte = 0x00;
        for (int i = 0; i < BIT_SIZE; i++)
        {
            if (data.get(i))
            {
                retByte |= (0x80 >>> i);
            }
        }
        return retByte;
    }

    /**
     * バイトデータ配列をパトライトコマンドセット配列へ変換します。
     * 
     * @param datas バイトデータ配列
     * @return パトライトコマンドセット配列
     */
    public static synchronized PatliteCommandSet[] toPatliteCommandSets(byte[] datas)
    {
        if (null != datas)
        {
            PatliteCommandSet[] commands = new PatliteCommandSet[datas.length];
            for (int i = 0; i < datas.length; i++)
            {
                commands[i] = new PatliteCommandSet(datas[i]);
            }
            return commands;
        }
        return new PatliteCommandSet[0];
    }

    /**
     * ビットセットデータ配列をバイト配列へ変換します。
     * 
     * @param datas ビットセットデータ配列
     * @return バイト配列
     */
    public static synchronized byte[] toBytes(PatliteCommandSet[] datas)
    {
        if (null != datas)
        {
            byte[] bytes = new byte[datas.length];
            for (int i = 0; i < datas.length; i++)
            {
                bytes[i] = toByte(datas[i].getBitSet());
            }
            return bytes;
        }
        return new byte[0];
    }

    /**
     * 指定されたバイトデータを16進数の文字列へ変換します。
     * 
     * @param data バイトデータ
     * @return 16進数の文字列
     */
    public static synchronized String toHexString(byte[] data)
    {
        boolean first = true;
        StringBuilder buffer = new StringBuilder();
        for (int idata : data)
        {
            if (!first)
            {
                buffer.append(",");
            }

            String hex = Integer.toHexString(idata);
            if (2 > hex.length())
            {
                buffer.append("0");
                buffer.append(hex);
            }
            else if (2 < hex.length())
            {
                buffer.append(hex.substring(hex.length() - 2));
            }
            else
            {
                buffer.append(hex);
            }
            first = false;
        }
        return String.valueOf(buffer);
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PatliteCommandSet.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * パトライトコマンドの文字列表現を返します。
     * 
     * @return 文字列表現
     */
    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < BIT_SIZE; i++)
        {
            String img = _command.get(i) ? "1"
                                        : "0";
            buffer.append(img);
        }
        return String.valueOf(buffer);
    }
}
