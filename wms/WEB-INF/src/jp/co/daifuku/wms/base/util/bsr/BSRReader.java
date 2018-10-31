//$Id: BSRReader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.bsr;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * BSR情報を読み込むためのクラスです。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class BSRReader
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 取得時エラーが発生したときのFACILITYコード */
    public static final int RETURN_ERROR = -1;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String _category = null;

    private File _latestStsFile = null;

    private File _latestLogFile = null;

    private List<Long> _pointerList = new ArrayList<Long>();

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。<br>
     * @param category 対象のカテゴリ
     */
    public BSRReader(String category)
    {
        _category = category;

        // ファイル名の取得
        BSRFile bsrFile = new BSRFile();
        _latestStsFile = bsrFile.getLatestStsFile(_category);
        _latestLogFile = bsrFile.getLatestLogFile(_category);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ログファイルをロードします。
     * @return レコード件数
     */
    public int load()
    {
        return makePointerList();
    }

    /**
     * ログファイルを再ロードします。
     * @return レコード件数
     */
    public int reload()
    {
        _pointerList.clear();
        return load();
    }

    /**
     * 現在の処理状況を取得します。
     * @return 処理状況<br>
     * RETURN_ERROR: ファイルへのアクセスに失敗し、正しい処理状況が取得できないとき。
     */
    public int getFacility()
    {
        RandomAccessFile stsFile = null;
        int retVal = RETURN_ERROR;

        try
        {
            if (_latestStsFile == null)
            {
                return RETURN_ERROR;
            }

            // ファイルのオープン
            stsFile = new RandomAccessFile(_latestStsFile, "r");

            String strLine = stsFile.readUTF();
            retVal = new Integer(strLine).intValue();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            // TODO ロギング

            retVal = RETURN_ERROR;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // TODO ロギング

            retVal = RETURN_ERROR;
        }
        finally
        {
            // ファイルクローズ
            doClose(stsFile);
        }

        return retVal;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 最新のBSR情報を取得します。
     * @return BSR情報
     */
    public BSRInfo getLatest()
    {
        if (_pointerList.size() == 0)
        {
            return null;
        }
        return getBSRInfo(_pointerList.size() - 1);
    }

    /**
     * 次のBSR情報の有無を返します。
     * @param currIndex 現在のインデックス
     * @return boolean
     */
    public boolean hasNext(int currIndex)
    {
        if (_pointerList.size() == 0)
        {
            return false;
        }
        if (currIndex >= (_pointerList.size() - 1))
        {
            return false;
        }
        return true;
    }

    /**
     * 次のBSR情報を取得します。
     * @param currIndex 現在のインデックス
     * @return BSR情報
     */
    public BSRInfo getNext(int currIndex)
    {
        if (_pointerList.size() == 0)
        {
            return null;
        }
        if (currIndex >= (_pointerList.size() - 1))
        {
            return null;
        }
        return getBSRInfo(currIndex + 1);
    }

    /**
     * 前のBSR情報の有無を返します。
     * @param currIndex 現在のインデックス
     * @return boolean
     */
    public boolean hasPrevious(int currIndex)
    {
        if (_pointerList.size() == 0)
        {
            return false;
        }
        if (currIndex <= 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 前のBSR情報を取得します。
     * @param currIndex 現在のインデックス
     * @return BSR情報
     */
    public BSRInfo getPrevious(int currIndex)
    {
        if (_pointerList.size() == 0)
        {
            return null;
        }
        if (currIndex <= 0)
        {
            return null;
        }
        return getBSRInfo(currIndex - 1);
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
    /**
     * ログファイルのレコードポインターリストを作成します。
     * @return レコード数
     */
    private int makePointerList()
    {
        int intRecord = 0; // レコード数

        if (_latestLogFile == null)
        {
            return 0;
        }

        // ファイルのオープン
        RandomAccessFile raFile = null;
        try
        {
            raFile = new RandomAccessFile(_latestLogFile, "r");
        }
        catch (FileNotFoundException e)
        {
            return 0;
        }
        // シーケンシャルリードしながらBSR情報単位のオフセット値をListへ格納
        try
        {
            long lngPointer1 = 0; // レコードの先頭ポインター

            while (true)
            {
                raFile.readUTF(); // １行読み込み（空読み）
                _pointerList.add(new Long(lngPointer1));
                intRecord++; // レコード件数カウント
                // 次のBSR情報先頭ポインタを記憶しておく
                lngPointer1 = raFile.getFilePointer();
            }
        }
        catch (EOFException e)
        {
            // EOFなら終了
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // TODO ロギング
        }
        finally
        {
            // ファイルクローズ
            doClose(raFile);
        }
        return intRecord;
    }

    /**
     * 指定したオフセット位置から１レコード分を読み出します。
     * @param index オフセット位置
     * @return 読み出したBSR情報
     */
    private BSRInfo getBSRInfo(int index)
    {
        // out of range check.
        if (_pointerList.size() <= index)
        {
            return null;
        }
        // BSRInfoを用意
        BSRInfo bsrInfo = new BSRInfo(_category, 0, 0, "", "");

        // ファイルのオープン
        RandomAccessFile logFile = null;
        try
        {
            logFile = new RandomAccessFile(_latestLogFile, "r");

            // 該当BSR情報を読み込む
            Long lngVal = _pointerList.get(index);
            if (null == lngVal)
            {
                // if no seek offset found.
                return null;
            }
            logFile.seek(lngVal.longValue());

            StringBuffer strDescription = new StringBuffer(); // 詳細情報格納用

            String strBuf = logFile.readUTF(); // １行読み込み

            String[] strArr = strBuf.split(BSRInfo.LOG_CRLF);
            for (int i = 0; i < strArr.length - 1; i++)
            {
                String line = strArr[i];
                switch (i)
                {
                    case 0: // 発生日時
                        bsrInfo.setLogDate(line);
                        break;
                    case 1: // 呼び出し元情報
                        bsrInfo.setClassName(line);
                        break;
                    case 2: // 結果区分
                        bsrInfo.setFacility(Integer.parseInt(line));
                        break;
                    case 3: // ステータスコード
                        bsrInfo.setStatus(Integer.parseInt(line));
                        break;
                    default: // 詳細情報（複数行ある）
                        if (!BSRInfo.LOG_DELIMITER.equals(strBuf))
                        {
                            strDescription.append(line);
                            strDescription.append(BSRInfo.LOG_CRLF);
                        }
                        break;
                }
            }
            bsrInfo.setDescription(new String(strDescription));
            bsrInfo.setIndex(index);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            // TODO ロギング
            bsrInfo = null;
        }
        catch (EOFException e)
        {
            // EOFなら終了
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // TODO ロギング
            bsrInfo = null;
        }
        finally
        {
            // ファイルクローズ
            doClose(logFile);
        }
        return bsrInfo;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * ファイルをクローズします。
     * @param raf
     */
    private void doClose(RandomAccessFile raf)
    {
        // ファイルクローズ
        if (raf != null)
        {
            try
            {
                raf.close();
            }
            catch (IOException e)
            {
                // do nothing.
            }
        }
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: BSRReader.java 87 2008-10-04 03:07:38Z admin $";
    }
}
