// $Id: RecordFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.record;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;

import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;


/**
 * レコードフォーマッタクラスのためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public interface RecordFormatter
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

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

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * エンティティから1行のレコードにフォーマットします。
     * @param ent エンティティ
     * @return ファイルレコード形式の文字列
     */
    public String format(Entity ent);

    /**
     * ファイルレコードからエンティティを生成して返します。
     * @param rec ファイルレコード
     * @return エンティティ
     * @throws RecordFormatException パースに失敗したときスローされます。
     * @throws UnsupportedEncodingException 
     */
    public Entity parse(byte[] rec)
            throws RecordFormatException,
                UnsupportedEncodingException;

    /**
     * ストアメタデータから、必要となるレコードのバイト数を計算して返します。
     * @return 1行の行数(改行コードを含む)
     */
    public int length();

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
}
