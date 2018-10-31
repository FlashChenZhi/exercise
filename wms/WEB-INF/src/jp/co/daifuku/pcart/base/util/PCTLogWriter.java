// $Id: PCTLogWriter.java 3715 2009-03-19 05:29:25Z tanaka $
package jp.co.daifuku.pcart.base.util;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogHandler;
import jp.co.daifuku.wms.base.entity.PCTOperationLog;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * UserMenuTool のオペレーションログ出力クラス。
 *
 *
 * @version $Revision: 3715 $, $Date: 2009-03-19 14:29:25 +0900 (木, 19 3 2009) $
 * @author  K51401
 * @author  Last commit: $Author: tanaka $
 */


public class PCTLogWriter
{
    private Connection conn = null;


    /**
     * <jp><br></jp>
     * <en>This method creates the access log</en></br>
     * @param conn Connection<jp></jp><en>java.sql.connection &nbsp;&nbsp;</en>
     */
    public PCTLogWriter(Connection conn)
    {
        this.conn = conn;
    }

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    /**
     * クラス名
     */
    private static final String CLASS_NAME = "PCTLogWriter";

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * オペレーションログの登録を行います。
     * 
     * @param userInfo ユーザ情報
     * @param operationType オペレーションタイプ
     * @param itemList 項目リスト
     * @throws SQLException DBエラーが発生した場合にスローされます。
     */
    public void createOperationLog(DfkUserInfo userInfo, int operationType, List itemList)
            throws SQLException
    {
        FieldName[] fn = {
                PCTOperationLog.ITEM_1,
                PCTOperationLog.ITEM_2,
                PCTOperationLog.ITEM_3,
                PCTOperationLog.ITEM_4,
                PCTOperationLog.ITEM_5,
                PCTOperationLog.ITEM_6,
                PCTOperationLog.ITEM_7,
                PCTOperationLog.ITEM_8,
                PCTOperationLog.ITEM_9,
                PCTOperationLog.ITEM_10,
                PCTOperationLog.ITEM_11,
                PCTOperationLog.ITEM_12,
                PCTOperationLog.ITEM_13,
                PCTOperationLog.ITEM_14,
                PCTOperationLog.ITEM_15,
                PCTOperationLog.ITEM_16,
                PCTOperationLog.ITEM_17,
                PCTOperationLog.ITEM_18,
                PCTOperationLog.ITEM_19,
                PCTOperationLog.ITEM_20,
                PCTOperationLog.ITEM_21,
                PCTOperationLog.ITEM_22,
                PCTOperationLog.ITEM_23,
                PCTOperationLog.ITEM_24,
                PCTOperationLog.ITEM_25,
                PCTOperationLog.ITEM_26,
                PCTOperationLog.ITEM_27,
                PCTOperationLog.ITEM_28,
                PCTOperationLog.ITEM_29,
                PCTOperationLog.ITEM_30,
                PCTOperationLog.ITEM_31,
                PCTOperationLog.ITEM_32,
                PCTOperationLog.ITEM_33,
                PCTOperationLog.ITEM_34,
                PCTOperationLog.ITEM_35,
                PCTOperationLog.ITEM_36,
                PCTOperationLog.ITEM_37,
                PCTOperationLog.ITEM_38,
                PCTOperationLog.ITEM_39,
                PCTOperationLog.ITEM_40
        };

        try
        {
            PCTOperationLog operationLog = new PCTOperationLog();

            // ユーザID
            operationLog.setUserId(userInfo.getUserId());
            // ユーザ名
            operationLog.setUserName(userInfo.getUserName());
            // 端末番号
            operationLog.setTerminalNumber(userInfo.getTerminalNumber());
            // 端末名称
            operationLog.setTerminalName(userInfo.getTerminalName());
            // IPアドレス
            operationLog.setIpaddress(userInfo.getTerminalAddress());
            // DS番号
            operationLog.setDsNo(userInfo.getDsNumber());
            // ページ名リソースキー
            operationLog.setPagenameresourcekey(userInfo.getPageNameResourceKey());
            // アクセスタイプ
            operationLog.setOperationType(operationType);
            Date logDate = DbDateUtil.getTimeStamp();
            // 出力日時
            operationLog.setLogDate(logDate);
            // 出力日時(GMT)
            operationLog.setLogDateGmt(logDate);
            // 項目リスト
            for (int i = 0; i < itemList.size(); i++)
            {
                operationLog.setValue(fn[i], itemList.get(i));
            }

            PCTOperationLogHandler handler = new PCTOperationLogHandler(this.conn);
            handler.create(operationLog);
        }
        catch (Exception e)
        {
            RmiMsgLogClient.write(new TraceHandler(6006002, e), CLASS_NAME);
        }


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
        return "$Id: PCTLogWriter.java 3715 2009-03-19 05:29:25Z tanaka $";
    }
}
