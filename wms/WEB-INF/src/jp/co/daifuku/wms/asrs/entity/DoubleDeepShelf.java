// $Id: DoubleDeepShelf.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.entity;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.entity.Shelf;

/**
 * ダブルディープ運用のために必要な情報を追加した棚クラスです。
 * Shelfインスタンスの保持する情報、操作に加えて、バンク内の位置（手前、奥）および、ペア棚の情報を持ちます。
 * こららのペア棚情報は空棚検索時に使用されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/1</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>M.INOUE</TD><TD>ダブルディープ対応追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class DoubleDeepShelf
        extends Shelf
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**
     * ペア棚の棚状態を保持します。
     */
    private String _pairStatusFlag;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**
     * 新しく棚を管理するための<code>DoubleDeepShelf</code>インスタンスを作成します。
     * @param snum  棚のステーション番号(棚番号)
     */
    public DoubleDeepShelf(String snum)
    {
        super();
        setStationNo(snum);
    }

    /**
     * 新しく棚を管理するための<code>DoubleDeepShelf</code>インスタンスを作成します。
     */
    public DoubleDeepShelf()
    {
        super();
    }

    // Public methods ------------------------------------------------

    /**
     * ペア棚の棚状態をセットします。
     * @param status  ペア棚の棚状態
     * @throws InvalidStatusException preの内容が範囲外であった場合に通知します。
     */
    public void setPairStatusFlag(String status)
            throws InvalidStatusException
    {
        if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(status) || Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(status)
                || Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(status))
        {
            _pairStatusFlag = status;
        }
        // 正しくない棚状態を設定しようとした場合は例外を発生させ、棚状態の変更はしない
        else
        {
            // 6006009=範囲外の値を指定されました。セットできません。Class={0} Variable={1} Value={2}
            Object[] tObj = new Object[3];
            tObj[0] = this.getClass().getName();
            tObj[1] = "wPairStatusFlag";
            tObj[2] = status;
            String classname = (String)tObj[0];
            RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
            throw (new InvalidStatusException(WmsMessageFormatter.format(6006009, tObj[0], tObj[1], tObj[2])));
        }
    }

    /**
     * ペア棚の棚状態を取得します。
     * @return   ペア棚の棚状態
     */
    public String getPairStatusFlag()
    {
        return _pairStatusFlag;
    }

    /**
     * ペア棚情報の項目とその値を文字列表現で返します。
     * @return ペア棚情報
     */
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100);
        try
        {
            buf.append(super.toString());
            buf.append("\nPair StatusFlag:" + _pairStatusFlag);
        }
        catch (Exception e)
        {
            // do nothing.
        }

        return String.valueOf(buf);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

