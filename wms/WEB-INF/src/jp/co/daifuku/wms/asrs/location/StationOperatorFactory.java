// $Id: StationOperatorFactory.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.lang.reflect.Constructor;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;

/**<jp>
 * StationOperatorクラスと、そのサブクラスを生成します。
 * このクラスでは、StationOperatorおよび、そのサブクラスを区別せずに生成します。
 * 各ステーションサブクラスを直接生成する場合は、それぞれのHandlerクラスを利用してください。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/14</TD><TD>y.kawai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class StationOperatorFactory
        extends Object
{

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**
     * データベース接続用のコネクション
     */
    protected Connection _conn;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }


    /**
     * ステーション番号とクラス名から、StationOperatorインスタンスを生成します。
     * 該当のステーションオペレーターのタイプによって、サブクラスが返されることがあります。
     * StationOperatorのサブクラスを直接インスタンス化することが出来ない(どのサブクラスが
     * 担当すべきか分からない) ステーションのインスタンスを取得するために使用します。
     * 該当のステーションが見つからない場合は、null ポインタが返されます。
     * @param conn      データベース接続用の<code>Connection</code>
     * @param snum      ステーション番号
     * @param className クラス名
     * @return StationOperator(またはそのサブクラス)のインスタンス
     * @throws InvalidDefineException クラス定義が、正しくなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 該当のクラスが見つからなかった場合に通知されます。
     */
    public static StationOperator makeOperator(Connection conn, String snum, String className)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        StationOperatorFactory of = new StationOperatorFactory(conn);
        return (of.getOperator(snum, className));
    }

    // Constructors --------------------------------------------------
    /**
     * データベース接続用のコネクションを指定して、インスタンスを作成します。
     * @param conn データベース接続用のコネクション
     */
    public StationOperatorFactory(Connection conn)
    {
        setConnection(conn);
    }

    // Protected methods ---------------------------------------------
    /**
     * ステーション番号とクラス名から、<code>StationOperator</code>インスタンスを生成し、返します。
     * @param snum      ステーション番号
     * @param className インスタンスを生成する対象のクラス名。
     * <p>例:<br>
     * "jp.co.daifuku.wms.asrs.location.InOutStationOperator"
     * </p>
     * @return <code>StationOperator</code>
     * @throws InvalidDefineException StationOperator以外のインスタンスが生成された場合に通知されます。。
     * @throws InvalidDefineException インスタンスの生成に失敗した場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 該当のクラスが見つからなかった場合に通知されます。
     */
    protected StationOperator getOperator(String snum, String className)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        try
        {
            if (StringUtil.isBlank(className))
            {
                Object[] tObj = new Object[1];
                tObj[0] = className;
                //<jp> 6006003=インスタンスの生成に失敗しました。クラス名={0} {1}</jp>
                //<en> 6006003=Failed to generate the instance. Class name={0} {1}</en>
                RmiMsgLogClient.write(new TraceHandler(6006003, new InvalidDefineException()), getClass().getName(), tObj) ;
                throw new InvalidDefineException(WmsMessageFormatter.format(6006003, tObj));
            }

            //<jp> クラスオブジェクトのロード</jp>
            //<en> load class</en>
            Class lclass = Class.forName(className);

            //<jp> コンストラクタの取得</jp>
            //<jp> set parameter types</jp>
            //<en> パラメータータイプをセット</en>
            //<en> set parameter types</en>
            Class[] typeparams = new Class[2];
            typeparams[0] = Class.forName("java.sql.Connection");
            typeparams[1] = Class.forName("java.lang.String");
            Constructor cconst = lclass.getConstructor(typeparams);

            //<jp> インスタンス作成のパラメータをセット</jp>
            //<en> set actual parameter</en>
            Object[] tparams = new Object[2];
            tparams[0] = _conn;
            tparams[1] = snum;

            //<jp> オブジェクト取得</jp>
            //<en> getting Object</en>
            Object tgt = cconst.newInstance(tparams);
            if (tgt instanceof StationOperator)
            {
                return ((StationOperator)tgt);
            }
            else
            {
                Object[] tObj = new Object[1];
                tObj[0] = "StationOperator";
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "OperatorFactory", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6006008, tObj));
            }
        }
        catch (Exception e)
        {
            //<jp>エラーログの出力処理も行う。</jp>
            //<en>Output of error log.</en>
            Object[] tObj = new Object[1];
            tObj[0] = className;
            //<jp> 6006003=インスタンスの生成に失敗しました。クラス名={0} {1}</jp>
            //<en> 6006003=Failed to generate the instance. Class name={0} {1}</en>
            RmiMsgLogClient.write(new TraceHandler(6006003, e), getClass().getName(), tObj) ;
            throw new InvalidDefineException(WmsMessageFormatter.format(6006003, tObj));
        }
    }

    // Private methods --------------------------------------------------------------
    /**
     * データベース・コネクションを設定します。
     * @param conn データベース・コネクション
     */
    private void setConnection(Connection conn)
    {
        _conn = conn;
    }

}
