// $Id: WmsSuperLocationHolder.java 4476 2009-06-23 00:29:03Z okamura $
package jp.co.daifuku.wms.base.util.location;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.location.SuperLocationInfo;
import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * eWarenavi用のエリア情報保持クラスです。<br>
 * このクラスはSuperLocationHolderからインスタンス化されます。
 *
 *
 * @version $Revision: 4476 $, $Date: 2009-06-23 09:29:03 +0900 (火, 23 6 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class WmsSuperLocationHolder
        extends SuperLocationHolder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 再読込間隔 (60秒) */
    public static final long LOAD_INTERVAL = 60000;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * エリア情報を保持するマップを生成します。
     */
    public WmsSuperLocationHolder()
    {
        super();
        Logger logger = Logger.getLogger(WmsSuperLocationHolder.class.getSimpleName());
        logger.log(Level.INFO, "    WmsSuperLocationHolder created.");
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

    /**
     * エリアマスタからエリア情報を読み込みます。
     */
    @Override
    protected void load()
    {
        Logger logger = Logger.getLogger(WmsSuperLocationHolder.class.getSimpleName());
        logger.log(Level.INFO, "    WmsSuperLocationHolder load from DB.");
        Connection conn = null;
        try
        {
            conn = WmsParam.getConnection();

            String sql = "SELECT * FROM DMAREA ORDER BY AREA_NO";
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet r = stmt.executeQuery(sql);

            while (r.next())
            {
                String area = r.getString("AREA_NO");
                String style = r.getString("LOCATION_STYLE");

                WmsSuperLocationInfo wsi = new WmsSuperLocationInfo(area);
                wsi.setLocationStyle(style);

                addSuperLocationInfo(wsi);
            }
            DBUtil.close(r);
            DBUtil.close(stmt);

            // no commit for this transaction
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Database connection error.", e);
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     * この実装ではデフォルトのSuperLocationInfoが作成できないため、
     * null を返します。
     * 
     * @see jp.co.daifuku.foundation.common.location.SuperLocationHolder#getDefaultSuperLocationInfo()
     */
    @Override
    protected SuperLocationInfo getDefaultSuperLocationInfo()
    {
        return null;
    }

    /**
     * 再読込が必要かどうかを判定します。
     * 
     * @see jp.co.daifuku.foundation.common.location.SuperLocationHolder#isExpired(long, long)
     */
    @Override
    protected boolean isExpired(long currTime, long lastLoad)
    {
        //        long interval = currTime - lastLoad;
        //
        //        return (LOAD_INTERVAL < interval);
        
        // 2009/06/18 再読込はせず常にメモリ上のデータを使用するように変更
        return false;
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
        return "$Id: WmsSuperLocationHolder.java 4476 2009-06-23 00:29:03Z okamura $";
    }
}
