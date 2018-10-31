//$Id: ASLocationSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.handler.db.AbstractSQLSearchKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * StationFactoryにて使用します。<BR>
 * テーブル名を指定せずに、検索列の指定を行います。<BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>2005/11/11</b></td><td><b>Y.Okamura</b></td><td><b>Create this class</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class ASLocationSearchKey
        extends AbstractSQLSearchKey
{
    //------------------------------------------------------------
    // class variables (p_Prefix '$')
    //------------------------------------------------------------
    /**
     * 検索キーフィールド：ステーションNo.
     */
    private FieldName _stationNo = null;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // properties (p_Prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (p_Prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象のテーブル名とカラムのリストを準備して、インスタンスを
     * 生成します。
     * @param storeName テーブル名
     */
    public ASLocationSearchKey(String storeName)
    {
        super(storeName);
        _stationNo = new FieldName(getStoreName(), "STATION_NO");
    }

    /**
     * ステーションNoの検索値をセット
     * @param arg 文字列の検索値をセットします。
     * @throws ReadWriteException 不正な値を設定した場合に通知されます。
     */
    public void setStationNo(String arg)
            throws ReadWriteException
    {
        setKey(_stationNo, arg);
    }

    /**
     * ステーションNoの検索値をセット
     * @param arg 文字列の検索配列をセットします。
     * @throws ReadWriteException 不正な値を設定した場合に通知されます。
     */
    public void setStationNo(String[] arg)
            throws ReadWriteException
    {
        setKey(_stationNo, arg);
    }

    /**
     * ステーションNoの検索値をセット
     * @param arg 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等 比較条件
     * @throws ReadWriteException 不正な値を設定した場合に通知されます。
     */
    public void setStationNo(String arg, String compcode)
            throws ReadWriteException
    {
        setKey(_stationNo, arg, compcode, "", "", true);
    }

    /**
     * ステーションNoの検索値をセット
     * @param arg 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等 比較条件
     * @param leftParen 左括弧数
     * @param rightParen 右括弧数
     * @param andOr 「AND」「OR」比較条件 (true = AND)
     * @throws ReadWriteException 不正な値を設定した場合に通知されます。
     */
    public void setStationNo(String arg, String compcode, String leftParen, String rightParen, boolean andOr)
            throws ReadWriteException
    {
        setKey(_stationNo, arg, compcode, leftParen, rightParen, andOr);
    }

    /**
     * ステーションNoのソート順をセット
     * @param bool 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean bool)
    {
        setOrder(_stationNo, bool);
    }

    /**
     * ステーションNoのグループ順をセット
     */
    public void setStationNoGroup()
    {
        setGroup(_stationNo);
    }

    /**
     * ステーションNoの情報取得順を取得
     * 引数無しの場合は、空文字をセットするパターン
     */
    public void setStationNoCollect()
    {
        setCollect(_stationNo);
    }

    /**
     * ステーションNoの情報取得順を取得
     * @param compcode 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)
     * @param saveField 保存先のフィールド (COUNTなどで別のフィールドに結果を
     * 保存したいときに使用します。未使用の時は、null または "" を指定します)
     */
    public void setStationNoCollect(String compcode, FieldName saveField)
    {
        setCollect(_stationNo, compcode, saveField);
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
        return "$Id: ASLocationSearchKey.java 87 2008-10-04 03:07:38Z admin $";
    }
}
