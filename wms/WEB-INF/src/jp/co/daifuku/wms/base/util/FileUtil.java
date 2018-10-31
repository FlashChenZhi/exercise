// $Id: FileUtil.java 6659 2010-01-07 05:22:44Z okamura $
package jp.co.daifuku.wms.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */



/**
 * ファイルの操作処理を行います。
 *
 * @version $Revision: 6659 $, $Date: 2010-01-07 14:22:44 +0900 (木, 07 1 2010) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: okamura $
 */
public class FileUtil extends Object
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
    
    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 引数で指定されたファイルを存在チェックを行ったうえで、削除します。
     * 存在チェック時にファイルが見つからなかった場合は、削除できたことにします。
     * ハンドリングは呼び出し元にて行ってください。
     * 
     * @param delFile 削除対象ファイル
     * @return true:削除できた false:削除できなかった
     */
    public static boolean delete(File delFile)
    {
        if (!delFile.exists())
        {
            return true;
        }
        // ファイルの削除を行なう
        if (delFile.delete())
        {
            return true;
        }

        return false;
    }
    
    /**
     * コピー元の<code>srcPath</code>から、コピー先の<code>destPath</code>へ
     * ファイルのコピーを行います。
     * 
     * @param srcPath    コピー元のパス
     * @param destPath    コピー先のパス
     * @throws IOException    何らかの入出力処理例外が発生した場合
     */
    public static void copyFile(String srcPath, String destPath) throws IOException 
    {
        
        FileChannel srcChannel = new FileInputStream(srcPath).getChannel();
        FileChannel destChannel = new FileOutputStream(destPath).getChannel();
        try
        {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        }
        finally
        {
            srcChannel.close();
            destChannel.close();
        }

    }

    /**
     * ファイルの拡張子を変更します。<BR>
     * 変更前の拡張子と変更後の拡張子を指定することによって、その文字列の置き換えを行います。
     * 
     * @param file 拡張子を変更するファイル
     * @param orgExtention 変更前拡張子
     * @param destExtention 変更後拡張子
     * @return 拡張子が変更できた場合：変更後ファイル、変更できなかった場合：変更前ファイル
     */
    public static File changeExtention(File file, String orgExtention, String destExtention)
    {
        if (!file.exists())
        {
            return null;
        }

        // ファイル名を取得する
        StringBuilder fileName = new StringBuilder(file.getPath());
        // 指定された拡張子が含まれない場合はもとファイル名を返す
        if (fileName.indexOf(orgExtention) < 0)
        {
            return null;
        }
        // ファイル名の中で最後に含まれるorgExtentionを探し
        // destExtentionに置き換える
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        fileName.replace(fileName.lastIndexOf(orgExtention), fileName.length(), (destExtention + fmt.format(new Date())));
        
        if (file.renameTo(new File(fileName.toString())))
        {
            return file;
        }
        else
        {
            return null;
        }
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
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: FileUtil.java 6659 2010-01-07 05:22:44Z okamura $";
    }
    
}

