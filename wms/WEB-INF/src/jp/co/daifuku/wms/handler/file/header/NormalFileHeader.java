// $Id: NormalFileHeader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.header;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;


/**
 * 追記型の固定長ファイルに対するヘッダクラスです。<br>
 * 実際にはファイルにヘッダを書き込みません。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class NormalFileHeader
        extends AbstractFileHeader
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
    /**
     * デフォルトコンストラクタ
     */
    public NormalFileHeader()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void init()
            throws ReadWriteException
    {
        setReadPointer(POINTER_INIT);
        setWritePointer(POINTER_INIT);
    }

    /**
     * {@inheritDoc}
     * NormalFileHeaderでは、ヘッダレコードを持たないため、何も行いません。
     */
    protected void parseHeader(String rec)
    {
        // do nothing.
    }

    /**
     * {@inheritDoc}
     */
    public void read()
            throws ReadWriteException
    {
        // point to start of the file for normal file.
        setReadPointer(0);
    }

    /**
     * {@inheritDoc}
     * NormalFileHeaderでは、ヘッダレコードを持たないため、何も行いません。
     */
    public void write()
            throws ReadWriteException
    {
        // do nothing.
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public int getNextRead(boolean updateHeader)
    {
        int rp = getReadPointer();
        setReadPointer(rp + 1);
        return rp;
    }

    /**
     * {@inheritDoc}
     */
    public int getPreviousRead()
            throws ReadWriteException
    {
        int rp = getReadPointer();
        if (rp == 0)
        {
            return RP_NO_RECORD_TO_READ;
        }
        rp--;
        setReadPointer(rp);
        return rp;
    }

    /**
     * {@inheritDoc}
     * NormalFileHeaderでは常に最終レコードの次にレコード追加されるため
     * このメソッドは意味を成しません。
     */
    public int getNextWrite()
    {
        // this method does not need for normal file.
        // anytime write to end of the file.
        return 0;
    }

    /**
     * {@inheritDoc}
     * NormalFileHeaderでは、ヘッダレコードを持たないため、常に0を返します。
     */
    public int length()
    {
        return 0;
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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: NormalFileHeader.java 87 2008-10-04 03:07:38Z admin $";
    }
}
