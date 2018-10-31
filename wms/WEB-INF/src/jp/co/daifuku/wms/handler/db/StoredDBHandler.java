// $Id: StoredDBHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * ストアドプロシジャー呼び出しのためのデータベースハンドラです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public class StoredDBHandler
        extends DefaultDDBHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

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
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn データベースコネクション
     */
    public StoredDBHandler(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 返値のあるストアドプロシジャーを実行します。<br>
     * このメソッドでは、特殊なステートメントを使用しますので、
     * getResultSet()メソッドで結果セットは取得できません。<br>
     * またステートメントはこのメソッド内でクローズされますので、
     * executeProcedure()だけを使用した場合には close()は不要です。
     * 
     * @param SQL 実行するSQL文
     * @param outparms 返値パラメータの定義(パラメータ順にあわせる必要があります)
     * @throws ReadWriteException SQLの実行に失敗したときスローされます。
     */
    public void executeProcedure(String SQL, DBStoredParameter[] outparms)
            throws ReadWriteException
    {
        CallableStatement stmt = null;
        try
        {
            stmt = getConnection().prepareCall(SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            // setup out parameters if outparams set.
            if ((outparms != null))
            {
                setupParameters(outparms, stmt);
            }

            // execute stored procedure
            stmt.execute();

            // get return values from procedure if outparms set.
            if (outparms != null)
            {
                pickupValues(outparms, stmt);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException();
            throw ex;
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

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

    /**
     * ストアドプロシジャーの呼び出し結果からOutパラメータを取得します。
     * @param procParams ストアドプロシジャーとのパラメータ
     * @param stmt プロシジャー呼び出し用CallableStatement
     * @throws SQLException
     */
    protected void pickupValues(DBStoredParameter[] procParams, CallableStatement stmt)
            throws SQLException
    {
        for (int i = 0; i < procParams.length; i++)
        {
            DBStoredParameter p = procParams[i];
            if (p == null || !p.isOutParam())
            {
                continue;
            }

            Object retvalue = null;
            switch (p.getType())
            {
                case DBStoredParameter.TYPE_DATE:
                    retvalue = stmt.getTimestamp(i + 1);
                    break;
                case DBStoredParameter.TYPE_NUMBER:
                    retvalue = stmt.getBigDecimal(i + 1);
                    break;
                case DBStoredParameter.TYPE_STRING:
                default:
                    retvalue = String.valueOf(stmt.getObject(i + 1));
                    break;
            }
            p.setValue(retvalue);
        }
    }

    /**
     * ストアドプロシジャーの呼び出し前に、IN/OUTパラメータをステートメントに
     * 設定します。
     * 
     * @param procParams ストアドプロシジャーとのパラメータ
     * @param stmt プロシジャー呼び出し用CallableStatement
     * @throws SQLException
     * @throws ReadWriteException
     */
    protected void setupParameters(DBStoredParameter[] procParams, CallableStatement stmt)
            throws SQLException,
                ReadWriteException
    {
        for (int i = 0; i < procParams.length; i++)
        {
            DBStoredParameter p = procParams[i];
            if (p == null)
            {
                continue;
            }

            if (p.isOutParam())
            {
                switch (p.getType())
                {
                    // Number
                    case DBStoredParameter.TYPE_NUMBER:
                        stmt.registerOutParameter(i + 1, p.getType(), p.getScale());
                        break;
                    // Date,String or Others
                    case DBStoredParameter.TYPE_DATE:
                    case DBStoredParameter.TYPE_STRING:
                    default:
                        stmt.registerOutParameter(i + 1, p.getType());
                        break;
                }
            }
            if (p.isInParam())
            {
                switch (p.getType())
                {
                    // DATE
                    case DBStoredParameter.TYPE_DATE:
                        Date dval = (Date)p.getValue();
                        Timestamp tval = new Timestamp(dval.getTime());
                        stmt.setTimestamp(i + 1, tval);
                        break;
                    // String,Number or Others
                    case DBStoredParameter.TYPE_STRING:
                    case DBStoredParameter.TYPE_NUMBER:
                    default:
                        stmt.setObject(i + 1, p.getValue());
                        break;
                }
            }
        }
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
        return "$Id: StoredDBHandler.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args)
            throws Exception
    {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String cstr = "jdbc:oracle:thin:1521:dss6";
        Connection conn = DriverManager.getConnection(cstr, "scott", "tiger");

        /*
         CREATE OR REPLACE PROCEDURE empno_exist_check
         (emp_no IN EMP.EMPNO%TYPE,
         check_out out NUMBER,
         dummy1 in out VARCHAR2,
         dummy2 out DATE,
         dummy3 out VARCHAR2)
         IS
         e_no EMP.EMPNO%TYPE;
         BEGIN
         dummy3 := dummy1 ;
         dummy1 := 'DUMMY1 FROM SP';
         dummy2 := sysdate ;
         SELECT EMPNO INTO e_no FROM EMP WHERE EMPNO = emp_no;
         check_out := 1;
         EXCEPTION
         WHEN NO_DATA_FOUND THEN
         check_out := 0;
         END empno_exist_check;
         */
        {
            StoredDBHandler sth = new StoredDBHandler(conn);
            DBStoredParameter p1 = new DBStoredParameter(DBStoredParameter.TYPE_NUMBER, DBStoredParameter.PARAM_OUT);
            DBStoredParameter p2 = new DBStoredParameter(DBStoredParameter.TYPE_STRING, DBStoredParameter.PARAM_INOUT);
            p2.setValue("VALUE FROM JAVA");
            DBStoredParameter p3 = new DBStoredParameter(DBStoredParameter.TYPE_DATE, DBStoredParameter.PARAM_OUT);
            DBStoredParameter p4 = new DBStoredParameter(DBStoredParameter.TYPE_STRING, DBStoredParameter.PARAM_OUT);
            String SQL = "{call empno_exist_check(7521, ?, ?, ?, ?)}";
            DBStoredParameter[] sParms = {
                p1,
                p2,
                p3,
                p4,
            };
            sth.executeProcedure(SQL, sParms);

            for (int i = 0; i < sParms.length; i++)
            {
                System.out.println(sParms[i].getValue());
            }
        }
        /*
         CREATE OR REPLACE function testfunc
         (emp_no IN EMP.EMPNO%TYPE,
         check_out out NUMBER,
         dummy1 in out VARCHAR2,
         dummy2 out DATE,
         dummy3 out VARCHAR2)
         return varchar2 is dt varchar2(200);
         e_no EMP.EMPNO%TYPE;
         BEGIN
         dummy3 := dummy1 ;
         dummy1 := 'DUMMY1 FROM SP';
         dummy2 := sysdate ;
         check_out := 0;

         dt := 'RETURN VALUE' ;
         return dt ;
         END testfunc;

         */
        {
            System.out.println("---------");
            StoredDBHandler sth = new StoredDBHandler(conn);

            DBStoredParameter retP = new DBStoredParameter(DBStoredParameter.TYPE_STRING, DBStoredParameter.PARAM_OUT);

            DBStoredParameter p1 = new DBStoredParameter(DBStoredParameter.TYPE_NUMBER, DBStoredParameter.PARAM_OUT);
            DBStoredParameter p2 = new DBStoredParameter(DBStoredParameter.TYPE_STRING, DBStoredParameter.PARAM_INOUT);
            p2.setValue("VALUE FROM JAVA");
            DBStoredParameter p3 = new DBStoredParameter(DBStoredParameter.TYPE_DATE, DBStoredParameter.PARAM_OUT);
            DBStoredParameter p4 = new DBStoredParameter(DBStoredParameter.TYPE_STRING, DBStoredParameter.PARAM_OUT);
            String SQL = "{? = call testfunc(7521, ?, ?, ?, ?)}";
            DBStoredParameter[] sParms = {
                retP,
                p1,
                p2,
                p3,
                p4,
            };
            sth.executeProcedure(SQL, sParms);

            for (int i = 0; i < sParms.length; i++)
            {
                System.out.println(sParms[i].getValue());
            }
        }
    }
}
