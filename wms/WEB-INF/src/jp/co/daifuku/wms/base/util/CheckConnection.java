package jp.co.daifuku.wms.base.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * このメソッドはDBとのコネクションのチェックを行います.<br>
 * コネクションを失っている場合trueを、保持している場合falseを
 * 返します。
 * 
 * @version $Revision: $, $Date: $
 * @author  Last commit: $Author: kanda $
 */
public class CheckConnection
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    private static String checkString = "SELECT 1 FROM DUAL";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    public CheckConnection()
    {
        super();
    }

    /**
     * コネクションの接続チェックを行います。
     * @param conn
     * @return
     */
    public boolean check(Connection conn, boolean dbCheckFlag)
    {
        // db接続チェックフラグがtrueならdb接続OKかチェックする
        if (true == dbCheckFlag)
        {
            try
            {
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(checkString);
                rset.close();
                stmt.close();
            }
            catch (Exception e)
            {
                // 接続を失っている場合
                return true;
            }
        }
        
        // 接続を保持している場合
        return false;
    }
}
