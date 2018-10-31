// $Id: AbstractSequenceHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db.sequence;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * 順序オブジェクトを管理するためのハンドラクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractSequenceHandler
        implements SequenceHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** チェックNGの際のリトライ回数 (無制限) */
    public static final long RETRY_UNLIMITED = -1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;
    private Connection p_connection;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コネクションを指定してインスタンスを生成します。
     * @param conn データベースコネクション
     */
    public AbstractSequenceHandler(Connection conn)
    {
        super();
        setConnection(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public long getNext(SequenceInfo seqinf)
            throws ReadWriteException
    {
        return getNext(seqinf, RETRY_UNLIMITED);
    }

    /**
     * {@inheritDoc}
     */
    public long getNext(SequenceInfo seqinf, long retry)
            throws ReadWriteException
    {
        long seq = readNext(seqinf);

        synchronized (seqinf)
        {
            // シーケンスがループしているかチェック
            boolean isMinValue = (seq <= seqinf.getStart());
            boolean looped = seqinf.setLastNumber(seq) || isMinValue;

            if (looped)
            {
                // ループしたため、チェック済みフラグをリセット
                seqinf.setChecked(false);
            }

            // チェック済みシーケンスは、そのまま使用
            if (seqinf.isChecked())
            {
                return seq;
            }
        }

        // 使用済み最大シーケンスを取得
        long maxSeq = getMaxSequence(seqinf);

        // 取得したシーケンスが、既に使用されている最大シーケンスよりも
        // 小さい場合は、別のシーケンスを再取得します。
        if (seq <= maxSeq)
        {
            int cnt = 0;
            while (!callChecker(seqinf, seq))
            {
                if (0 < retry && ++cnt > retry)
                {
                    //<jp>採番できなかった場合</jp>
                    //<jp>6016102 = 致命的なエラーが発生しました。{0}</jp>
                    //<en>If the number could not be obtained,</en>
                    //<en>6016102 = Fatal error occured.{0}</en>
                    String[] mparam = {
                        "Error occured while getting the " + seqinf.getName() + ". Count of loop was over.",
                    };
                    RmiMsgLogClient.write(6016102, "AbstractSequenceHandler", mparam);
                    throw (new ReadWriteException());
                }
                seq = readNext(seqinf);
            }
        }
        else
        {
            seqinf.setChecked(true);
        }

        return seq;
    }

    /**
     * データベースから、次のシーケンスを読み込みます。<br>
     * このメソッドの中では内容チェックは行いません。
     * 
     * @param seqinf シーケンス情報
     * @return シーケンスの文字列表現
     * @throws ReadWriteException
     */
    protected long readNext(SequenceInfo seqinf)
            throws ReadWriteException
    {

        ResultSet result = null;
        Statement stmt = null;
        try
        {
            String seqname = seqinf.getName();
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            String sSQLf = "SELECT {0}.NEXTVAL FROM DUAL";
            String[] sqlp = {
                seqname,
            };
            String sSQL = SimpleFormat.format(sSQLf, sqlp);

            DEBUG.MSG(DEBUG.HANDLER, sSQL);

            result = stmt.executeQuery(sSQL);

            if (result.next())
            {
                long seq = result.getLong(1);
                result.close();
                stmt.close();
                return seq;
            }
            else
            {
                result.close();
                stmt.close();
                // FIXME logging.  failed getting next sequence from dual.
                // failed getting next sequence from dual.
                throw new ReadWriteException();
            }
        }
        catch (SQLException e)
        {
            throw handleSQLException(e);
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    /**
     * シーケンス最大値を取得します。
     * @param seqinf シーケンス情報
     * @return シーケンス最大値
     * @throws ReadWriteException
     */
    protected long getMaxSequence(SequenceInfo seqinf)
            throws ReadWriteException
    {
        String seqname = seqinf.getName();
        try
        {
            Method method = getMaxSeqMethod(seqinf);

            Object[] args = {
                getConnection(),
                seqinf
            };

            // BigDecimal method(Connection conn, SequenceInfo seqinf)
            Object ret = method.invoke(this, args);
            DEBUG.MSG(DEBUG.HANDLER, "getMaxSequence for " + seqname + " result:" + ret);
            if (ret instanceof BigDecimal)
            {
                return ((BigDecimal)ret).longValue();
            }
            else
            {
                throw new RuntimeException("getMaxSequence method should be return a BigDecimal object.");
            }
        }
        catch (NoSuchMethodException e)
        {
            // ignore no method.
            DEBUG.MSG(DEBUG.HANDLER, "No getMaxSequence method for " + seqname);
            return seqinf.getStart();
        }
        catch (Exception e)
        {
            throw handleSQLException(e);
        }
    }

    /**
     * チェッカメソッドを呼び出して、その結果を返します。
     * 
     * @param seqinf シーケンス情報
     * @param nextSeq チェック対象のシーケンス
     * @return チェックがOKのときtrue.
     * @throws ReadWriteException
     */
    protected boolean callChecker(SequenceInfo seqinf, long nextSeq)
            throws ReadWriteException
    {
        String seqname = seqinf.getName();
        try
        {
            Method checker = getChecker(seqinf);

            Object[] args = {
                getConnection(),
                seqinf,
                new Long(nextSeq),
            };

            // boolean checker(Connection conn, SequenceInfo seqinf, long nextSeq)
            Object ret = checker.invoke(this, args);

            DEBUG.MSG(DEBUG.HANDLER, "Check method for " + seqname + " result:" + ret);

            if (ret instanceof Boolean)
            {
                return ((Boolean)ret).booleanValue();
            }
            else
            {
                throw new RuntimeException("Chekcer method should be return a Boolean object.");
            }
        }
        catch (NoSuchMethodException e)
        {
            // ignore no check method.
            DEBUG.MSG(DEBUG.HANDLER, "No check method for " + seqname);
            return true;
        }
        catch (Exception e)
        {
            throw handleSQLException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void reset(SequenceInfo seqinf)
            throws ReadWriteException
    {
        String sname = seqinf.getName();
        long st = seqinf.getStart();
        long max = seqinf.getMax();
        int cache = seqinf.getCacheSize();
        String cyc = (seqinf.isCycle()) ? "CYCLE"
                                       : "";

        try
        {
            // drop sequence
            Statement stmt = HandlerUtil.createStatement(getConnection(), false, true);

            String sSQL = "DROP SEQUENCE " + sname;
            stmt.execute(sSQL);
            HandlerUtil.closeStatement(stmt);
        }
        catch (SQLException e)
        {
            // ignore exception at drop sequence.
        }

        try
        {
            // create sequence
            Statement stmtc = HandlerUtil.createStatement(getConnection(), false, true);

            String sSQLf = "CREATE SEQUENCE {0} START WITH {1} MINVALUE {1} MAXVALUE {2} {3} CACHE {4}";

            String[] params = {
                sname,
                String.valueOf(st),
                String.valueOf(max),
                cyc,
                String.valueOf(cache),
            };
            String sSQL = SimpleFormat.format(sSQLf, params);

            DEBUG.MSG(DEBUG.HANDLER, sSQL);

            stmtc.execute(sSQL);
            HandlerUtil.closeStatement(stmtc);
        }
        catch (SQLException e)
        {
            throw handleSQLException(e);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public Connection getConnection()
    {
        return p_connection;
    }

    /**
     * {@inheritDoc}
     */
    public void setConnection(Connection conn)
    {
        p_connection = conn;
    }

    /**
     * {@inheritDoc}
     */
    public Method getChecker(SequenceInfo seqinf)
            throws SecurityException,
                NoSuchMethodException
    {
        Class[] params = CHECKER_PARAMS;

        String mname = seqinf.getName().toLowerCase() + CHECKER_POSTFIX;
        Method checker = getClass().getMethod(mname, params);
        return checker;
    }

    /**
     * {@inheritDoc}
     */
    public Method getMaxSeqMethod(SequenceInfo seqinf)
            throws SecurityException,
                NoSuchMethodException
    {
        Class[] params = MAX_PARAMS;

        String mname = seqinf.getName().toLowerCase() + MAX_POSTFIX;
        Method method = getClass().getMethod(mname, params);
        return method;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * SQL例外発生時にロギングとReadWriteExceptionの生成を
     * 行います。
     * 
     * @param e SQL例外
     * @return ReadWriteException
     */
    protected ReadWriteException handleSQLException(Exception e)
    {
        e.printStackTrace();

        // 6006002=データベースエラーが発生しました。{0}
        String[] eparam = {
            "AbstractSequenceHandler"
        };
        RmiMsgLogClient.write(new TraceHandler(6006002, e), eparam[0]);
        return (new ReadWriteException(e));
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
        return "$Id: AbstractSequenceHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
