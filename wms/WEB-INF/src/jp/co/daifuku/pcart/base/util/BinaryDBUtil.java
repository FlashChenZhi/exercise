// $Id: BinaryDBUtil.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * DBでバイナリデータを扱うクラスです。<br>
 *
 *
 * @version $Revision: 6900 $, $Date: 2010-01-25 20:21:11 +0900 (月, 25 1 2010) $
 * @author  971498
 * @author  Last commit: $Author: kumano $
 */


public final class BinaryDBUtil
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
     * コンストラクタ
     */
    private BinaryDBUtil()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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
        return "$Id: BinaryDBUtil.java 6900 2010-01-25 11:21:11Z kumano $";
    }

    /**
     * バイナリデータ(ファイル)を、DBに登録します。<br>
     * 
     * バイナリデータをセットする列の属性は、BLOB です。
     * SQL文字列には、Insert又はUpdate文を指定し、バイナリデータをセットする部分は"?"とします。
     * 例:"INSERT INTO BLOBTEST(ID,FileName,Object) VALUES('1','picturetemp/picture.gif',?)"
     * 
     * ※対象列（?の記述）は１つです。対象とするバイナリデータ（ファイル）も１つです。
     * 
     * @param conn      データベースコネクション
     * @param sqlStr    登録(Insert又はUpdate)する為のSQL文
     * @param filePath  対象のバイナリデータファイル
     * @return      更新行数
     * @throws FileNotFoundException    ファイルが存在しない場合
     * @throws ReadWriteException       データベースアクセスエラー発生時にスローされます。
     */
    public static int setBinaryToDB(Connection conn, String sqlStr, String filePath)
            throws FileNotFoundException,
            ReadWriteException
    {
        // ファイルサイズの取得
        File file = new File(filePath);
        long fileLength = file.length();
        int fLength = (int)fileLength;

        // ファイル内容の取得
        InputStream fin = new FileInputStream(filePath);
        
        int num;
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sqlStr);
    
            stmt.setBinaryStream(1, fin, fLength);
            num = stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, new BinaryDBUtil().getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }

        return num;
    }

    /**
     * DBに登録されているバイナリデータをファイルにします。<br>
     * 
     * バイナリデータがセットされている列の属性は、BLOB です。
     * 検索SQL文字列には、バイナリデータが取得されるSelect文を指定します。
     * バイナリデータが無い(Null)場合でも、空のファイルが作成されます。この時の戻り値は0です。
     * 取得データの最初の列がバイナリデータとなるようなSQL文を指定します。
     * 
     * @param conn          データベースコネクション
     * @param sqlStr        DB検索のSQL文
     * @param filePath      出力先のファイル名(既存のファイルがある場合は上書き)
     * @return      作成したファイルのサイズ
     * @throws ReadWriteException   データベースアクセスエラー発生時にスローされます。
     * @throws NotFoundException    検索の結果該当するデータが無い場合
     * @throws IOException          ファイルの作成や書き込みに失敗した場合
     */
    public static long getBinaryFromDB(Connection conn, String sqlStr, String filePath)
            throws ReadWriteException,
                NotFoundException,
                IOException
    {
        return getBinaryFromDB(conn, sqlStr, 1, filePath);
    }

    /**
     * DBに登録されているバイナリデータをファイルにします。<br>
     * 
     * バイナリデータがセットされている列の属性は、BLOB です。
     * 検索SQL文字列には、バイナリデータが取得されるSelect文を指定します。
     * バイナリデータが無い(Null)場合でも、空のファイルが作成されます。この時の戻り値は0です。
     * columnIndexで、取得データの何列目がバイナリデータか指定します。最初の列であれば1を指定します。
     * 
     * @param conn          データベースコネクション
     * @param sqlStr        DB検索のSQL文
     * @param columnIndex   検索結果の何列目のデータをファイルにするか
     * @param filePath      出力先のファイル名(既存のファイルがある場合は上書き)
     * @return      作成したファイルのサイズ
     * @throws ReadWriteException   データベースアクセスエラー発生時にスローされます。
     * @throws NotFoundException    検索の結果該当するデータが無い場合
     * @throws IOException          ファイルの作成や書き込みに失敗した場合
     */
    public static long getBinaryFromDB(Connection conn, String sqlStr, int columnIndex, String filePath)
            throws ReadWriteException,
                NotFoundException,
                IOException
    {
        DefaultDDBHandler ddbHandler = null;
        ResultSet rs = null;
        InputStream in;
        try
        {
            ddbHandler = new DefaultDDBHandler(conn);
            // SQLの実行
            ddbHandler.execute(sqlStr);
            rs = ddbHandler.getResultSet();
            // 検索結果取り出し
            if (!rs.next())
            {
                throw new NotFoundException();
            }
            in = rs.getBinaryStream(columnIndex);
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, new BinaryDBUtil().getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
        
        //バイト配列を得る為、InputStreamからデータをreadして、ByteArrayOutputStreamにwriteする。
        //※1バイト単位で処理していては時間がかかるので、ある程度まとめて(4096Byte)処理する。
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[4096];
        int bytes;
        while (-1 < (bytes = in.read(b)))
        {
            baos.write(b, 0, bytes);
        }

        //ファイルに書き込む
        File file = new File(filePath);
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(baos.toByteArray());
        fo.close();

        return file.length();
    }
}
