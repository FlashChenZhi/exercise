// $Id: AbstractRftFile.java 3213 2009-03-02 06:59:20Z arai $
package jp.co.daifuku.wms.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.DataColumn;
import jp.co.daifuku.wms.base.communication.rft.IdMessage;
import jp.co.daifuku.wms.base.communication.rft.Idutils;

/**
 * ファイルデータを保持するクラスの基底クラスです。<br>
 * サブクラスのコンストラクタでは、必ず _lineLength に
 * 1行のバイト数をセットしてください。<br>
 * バイト数は、改行コードを含みません。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ssuzuki@softecs.jp
 * @author  Last commit: $Author: arai $
 * @since 2008-03-28 全面改訂
 */

public abstract class AbstractRftFile
        implements DataColumn
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * データファイルの1行の長さ (サブクラスで1行のバイト数をセットしてください)<br>
     * この長さには、CRLFを含みません。
     */
    // TODO privateに変更し、abstract setLength() に変更すること 2008-03-28
    protected int _lineLength;

    /** ファイルデータ(データバッファ) */
    private List<byte[]> _fileData = new ArrayList<byte[]>();

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルト・コンストラクタでは特別な処理を行いません。
     */
    protected AbstractRftFile()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ファイル名と号機から送信ファイルのパスを生成します。
     * @param rftNo RFT号機
     * @param fileName ファイル名
     * @return 送信ファイル名(パス付き)
     * @since 2008-03-28 static化
     */
    public static String createSendFilePathNameFull(String rftNo, String fileName)
    {
        File path = new File(WmsParam.DAIDATA, createSendFilePathNameId(rftNo, fileName));
        return path.getAbsolutePath();
    }

    /**
     * ファイル名とRFT号機からIDにセットする送信ファイルのパスを生成します。
     * @param rftNo RFT号機
     * @param fileName ファイル名
     * @return Idにセットする送信ファイル名(パス付き)
     * @since 2008-03-28 static化
     */
    public static String createSendFilePathNameId(String rftNo, String fileName)
    {
        File path = new File(WmsParam.RFTSEND + rftNo, fileName);
        return path.getPath();
    }

    /**
     * IDから受け取ったファイル名から、受信ファイルのパスを生成します。
     * @param fileName  ファイル名
     * @return Idにセットする送信ファイル名(パス付き)
     * @since 2008-03-28 static化
     */
    public static String createRecvFilePathName(String fileName)
    {
        File path = new File(WmsParam.DAIDATA, fileName);
        return path.getAbsolutePath();
    }

    /**
     * RFT号機のみから送信ファイルのパスを生成します。<br>
     * サブクラスでは、このメソッド内部でファイル名を補完してから
     * createSendFilePathNameFull(String rftNo, String fileName) を
     * 呼び出してください。
     * @param rftNo RFT号機
     * @return 送信ファイル名(パス付き)
     * @since 2008-03-28 追加
     */
    public abstract String createSendFilePathNameFull(String rftNo);

    /**
     * RFT号機からIDにセットする送信ファイルのパスを生成します。<br>
     * サブクラスでは、このメソッド内部でファイル名を補完してから
     * createSendFilePathNameId(String rftNo, String fileName) を
     * 呼び出してください。
     * @param rftNo RFT号機
     * @return Idにセットする送信ファイル名(パス付き)
     * @since 2008-03-28 追加
     */
    public abstract String createSendFilePathNameId(String rftNo);

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 保持しているデータ件数を返します。
     * @return データ件数
     */
    // TODO getRecordCount() などシンプルな名称に変更すること 2008-03-28
    public int getFileRecordTotal()
    {
        return _fileData.size();
    }

    /**
     * 保持しているデータバッファを返します。<br>
     * 返されたリストが変更された場合に影響を受けないよう、
     * このメソッドではコピーを作成して返します。
     * 
     * @return  ファイルデータ(データバッファ)
     * @since 2008-03-28 リストをコピーするよう変更
     */
    // TODO export() などシンプルな名称に変更すること 2008-03-28
    public List<byte[]> exportFileData()
    {
        return new ArrayList<byte[]>(_fileData);
    }

    /**
     * データバッファにデータをセットします。<br>
     * 外部のリストが変更された場合に影響を受けないよう、
     * このメソッドではコピーを作成します。
     * 
     * @param fileDataList  ファイルデータ(バイト配列のリスト)
     * @since 2008-03-28 リストをコピーするよう変更
     */
    // TODO import() などシンプルな名称に変更すること 2008-03-28
    public void importFileData(List<byte[]> fileDataList)
    {
        _fileData = new ArrayList<byte[]>(fileDataList);
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 保持データに指定行のフィールドがあるかチェックし、無い場合は作成します。
     * 行No.(lineNum)は0から開始。(lineNum=0が１行目)
     * 
     * @param lineNumber 行No.
     * @since 2008-03-28 配列の初期化にArrays.fill()を使用するよう変更
     */
    protected void createLineData(int lineNumber)
    {
        int size = getFileRecordTotal();
        if (lineNumber >= size)
        {
            for (int i = size; i <= lineNumber; i++)
            {
                byte[] lineData = new byte[_lineLength];
                Arrays.fill(lineData, (byte)' ');
                _fileData.add(lineData);
            }
        }
    }

    /**
     * 指定行の指定されたオフセットに指定された長さのデータをセットします。<br>
     * データの桁数が小さい時は、左詰めでセットされます。<br>
     * セットするデータは文字列で指定します。<br>
     * 
     * @param lineNumber セットする行(lineNum=0が１行目)
     * @param data セットする文字列データ
     * @param offset データのオフセット
     * @param length データの長さ
     * @since 2008-03-28 配列のコピーにSystem.arraycopy()を使用するよう変更
     */
    protected void setColumnLeft(int lineNumber, String data, int offset, int length)
    {
        createLineData(lineNumber);

        byte[] lineData = _fileData.get(lineNumber);
        byte[] byteData = Idutils.createByteDataLeft(data, length);

        // copy byteData to lineData 
        System.arraycopy(byteData, 0, lineData, offset, length);
    }

    /**
     * 指定行の指定されたオフセットに指定された長さのデータをセットします。<br>
     * データの桁数が小さい時は、右詰めでセットされます。<br>
     * セットするデータは文字列で指定します。<br>
     * 
     * @param lineNumber セットする行(lineNum=0が１行目)
     * @param data セットする文字列データ
     * @param offset データのオフセット
     * @param length データの長さ
     * @since 2008-03-28 配列のコピーにSystem.arraycopy()を使用するよう変更
     */
    protected void setColumnRight(int lineNumber, String data, int offset, int length)
    {
        createLineData(lineNumber);

        byte[] lineData = _fileData.get(lineNumber);
        byte[] byteData = Idutils.createByteDataRight(data, length);

        // copy byteData to lineData 
        System.arraycopy(byteData, 0, lineData, offset, length);
    }

    /**
     * 指定行の指定されたオフセットに指定された長さのデータをセットします。<br>
     * データの桁数が小さい時は、右詰めでセットされます。<br>
     * 
     * @param lineNumber セットする行(lineNum=0が１行目)
     * @param data セットする文字列データ
     * @param offset データのオフセット
     * @param length データの長さ
     * @since 2008-03-28 setColumnRight(String用)を呼び出すよう変更
     */
    protected void setColumnRight(int lineNumber, int data, int offset, int length)
    {
        // call setColumnRight() with String converted value
        setColumnRight(lineNumber, String.valueOf(data), offset, length);
    }

    /**
     * 指定行の指定されたオフセットから指定された長さのデータを取得し、
     * 文字列で返します。<br>
     * 文字列は trim() されて返されます。
     * 
     * @param lineNumber データを取得する行(lineNum=0が１行目)
     * @param offset データのオフセット
     * @param length データの長さ
     * @return 取得したデータを文字列で返します
     * @throws ScheduleException 行番号が不正な場合に通知されます。
     * @since 2008-03-28 Stringへの変換に IdMessage.ENCODE を使用するよう変更
     */
    protected String getColumnString(int lineNumber, int offset, int length)
            throws ScheduleException
    {
        // 指定の行が、保持しているデータバッファに存在するかチェック
        if (0 > lineNumber || getFileRecordTotal() <= lineNumber)
        {
            throw new ScheduleException();
        }
        try
        {
            byte[] lineData = _fileData.get(lineNumber);
            String strValue = new String(lineData, offset, length, IdMessage.ENCODE);
            return strValue.trim();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e); // usually not occurs
        }
    }

    /**
     * 指定行の指定されたオフセットから指定された長さのデータを取得し、
     * intで返します。<br>
     * 
     * @param lineNumber データを取得する行(lineNum=0が１行目)
     * @param offset データのオフセット
     * @param length データの長さ
     * @return 取得したデータをintで返す
     * @throws ScheduleException 行番号が不正な場合に通知されます。
     * @throws NumberFormatException 値が数値変換できない場合に通知されます。
     * @since 2008-03-28 intをStringへ変換して getColumnString() を呼び出すよう変更
     */
    protected int getColumnInt(int lineNumber, int offset, int length)
            throws ScheduleException,
                NumberFormatException
    {
        String intString = getColumnString(lineNumber, offset, length);
        return Integer.parseInt(intString);
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
        return "$Id: AbstractRftFile.java 3213 2009-03-02 06:59:20Z arai $";
    }
}
