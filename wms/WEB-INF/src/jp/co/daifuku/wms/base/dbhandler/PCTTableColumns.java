// $Id: PCTTableColumns.java 3213 2009-03-02 06:59:20Z arai $
package jp.co.daifuku.wms.base.dbhandler;

/**
 * <jp> データベースの列名を保持するインターフェースです。 データベースの列項目を取得するクラスは、このインターフェースをインプリメントしてください。
 * </jp> <en> It is the interface that the column name of the database is held.
 * The class which acquires the column item of the database is to implement this
 * interface. </en>
 */

public abstract class PCTTableColumns
{
    /**
     * DNPCTOPERATIONLOG Table : LOG_DATE
     */
    public static final String DNPCTOPERATIONLOG_LOG_DATE = "LOG_DATE";

    /**
     * DNPCTOPERATIONLOG Table : LOG_DATE_GMT
     */
    public static final String DNPCTOPERATIONLOG_DATE_GMT = "LOG_DATE_GMT";

    /**
     * DNPCTOPERATIONLOG Table : USER_ID
     */
    public static final String DNPCTOPERATIONLOG_USER_ID = "USER_ID";

    /**
     * DNPCTOPERATIONLOG Table : USER_NAME
     */
    public static final String DNPCTOPERATIONLOG_USER_NAME = "USER_NAME";

    /**
     * DNPCTOPERATIONLOG Table : TERMINAL_NUMBER
     */
    public static final String DNPCTOPERATIONLOG_TERMINAL_NUMBER = "TERMINAL_NUMBER";

    /**
     * DNPCTOPERATIONLOG Table : TERMINAL_NAME
     */
    public static final String DNPCTOPERATIONLOG_TERMINAL_NAME = "TERMINAL_NAME";

    /**
     * DNPCTOPERATIONLOG Table : IPADDRESS
     */
    public static final String DNPCTOPERATIONLOG_IPADDRESS = "IPADDRESS";

    /**
     * DNPCTOPERATIONLOG Table : DS_NO
     */
    public static final String DNPCTOPERATIONLOG_DS_NO = "DS_NO";

    /**
     * DNPCTOPERATIONLOG Table : PAGENAMERESOURCEKEY
     */
    public static final String DNPCTOPERATIONLOG_PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * DNPCTOPERATIONLOG Table : LOG_DATE
     */
    public static final String DNPCTOPERATIONLOG_OPERATION_TYPE = "OPERATION_TYPE";

    /**
     * DNPCTOPERATIONLOG Table : DETAIL
     */
    public static final String DNPCTOPERATIONLOG_DETAIL = "DETAIL";

    /**
     * DNPCTOPERATIONLOG Table : ITEM
     */
    public static final String DNPCTOPERATIONLOG_ITEM = "ITEM_";

}