package jp.co.daifuku.pcart.base.controller;

import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 荷主情報を操作するためのコントロールクラスです。
 *
 * @version $Revision: 3358 $, $Date: 2009-03-12 11:40:29 +0900 (木, 12 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: rnakai $
 */

public class PCTConsignorController
        extends AbstractController
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    FieldName CONSIGNOR_NAME = new FieldName("", "CONSIGNOR_NAME");

    /** 保存用のフィールド 荷主コード(<code>CONSIGNOR_CODE</code>) */
    FieldName CONSIGNOR_CODE = new FieldName("", "CONSIGNOR_CODE");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public PCTConsignorController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 荷主名称を取得します。<BR>
     * 該当荷主が存在しなかった場合は0バイトの文字列を返します。<BR>
     * 
     * 検索テーブルについては以下の通りです。
     * InParameter.SEARCH_TABLE_WORK    PCT出庫作業情報から取得します。
     * InParameter.SEARCH_TABLE_PLAN    PCT出庫予定情報から取得します。
     * InParameter.SEARCH_TABLE_RESULT  PCT出庫実績情報から取得します。
     * InParameter.SEARCH_TABLE_MASTER  荷主マスタ情報から取得します。
     * 
     * @param consignorCode 対象の荷主コード
     * @param table 検索テーブル
     * @return 荷主名称
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String getConsignorName(String consignorCode, String table)
            throws ReadWriteException
    {
        String consignorName = "";

        if (PCTRetrievalInParameter.SEARCH_TABLE_WORK.equals(table))
        {
            // PCT出庫作業情報
            consignorName = searchPCTRetWorkInfo(consignorCode);
        }
        else if (PCTRetrievalInParameter.SEARCH_TABLE_PLAN.equals(table))
        {
            // PCT出庫予定情報
            consignorName = searchPCTRetPlan(consignorCode);
        }
        else if (PCTRetrievalInParameter.SEARCH_TABLE_RESULT.equals(table))
        {
            // PCT出庫実績情報
            consignorName = searchPCTRetResult(consignorCode);
        }
        else if (PCTRetrievalInParameter.SEARCH_TABLE_MASTER.equals(table))
        {
            // 荷主マスタ
            consignorName = searchConsignor(consignorCode);
        }
        else
        {
            consignorName = "";
        }

        return consignorName;
    }

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
     * 入力されたパラメータをもとにPCT出庫作業情報を検索するSQL文を発行します。<BR>
     * <BR>
     * 
     * @param consignorCode 荷主コード
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @return 荷主名称
     */
    protected String searchPCTRetWorkInfo(String consignorCode)
            throws ReadWriteException
    {
        // 荷主名称取得
        PCTRetWorkInfoHandler workInfoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey wKey = new PCTRetWorkInfoSearchKey();

        // 取得条件
        wKey.setConsignorCodeCollect();
        wKey.setConsignorCode(consignorCode);
        wKey.setConsignorCodeGroup();
        wKey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", CONSIGNOR_NAME);
        wKey.setJoin(PCTRetWorkInfo.CONSIGNOR_CODE, "", PCTRetPlan.CONSIGNOR_CODE, "");

        try
        {
            PCTRetWorkInfo workInfo = (PCTRetWorkInfo)workInfoHandler.findPrimary(wKey);
            if (workInfo == null)
            {
                return "";
            }
            return String.valueOf(workInfo.getValue(Consignor.CONSIGNOR_NAME, ""));
        }
        catch (NoPrimaryException e)
        {
            return "";
        }
    }

    /**
     * 入力されたパラメータをもとにPCT出庫予定情報を検索するSQL文を発行します。<BR>
     * <BR>
     * 
     * @param consignorCode 荷主コード
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @return 荷主名称
     */
    protected String searchPCTRetPlan(String consignorCode)
            throws ReadWriteException
    {
        // 荷主名称取得
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey pKey = new PCTRetPlanSearchKey();

        // 取得条件
        pKey.setConsignorCode(consignorCode);
        pKey.setConsignorCodeGroup();
        pKey.setConsignorNameCollect("MAX");

        try
        {
            PCTRetPlan plan = (PCTRetPlan)planHandler.findPrimary(pKey);
            if (plan == null)
            {
                return "";
            }
            return plan.getConsignorName();
        }
        catch (NoPrimaryException e)
        {
            return "";
        }
    }

    /**
     * 入力されたパラメータをもとにPCT出庫実績情報を検索するSQL文を発行します。<BR>
     * <BR>
     * 
     * @param consignorCode 荷主コード
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @return 荷主名称
     */
    protected String searchPCTRetResult(String consignorCode)
            throws ReadWriteException
    {
        // 荷主名称取得
        PCTRetResultHandler resultHandler = new PCTRetResultHandler(getConnection());
        PCTRetResultSearchKey rKey = new PCTRetResultSearchKey();

        // 取得条件
        rKey.setConsignorCode(consignorCode);
        rKey.setConsignorCodeGroup();
        rKey.setConsignorNameCollect("MAX");

        try
        {
            PCTRetResult result = (PCTRetResult)resultHandler.findPrimary(rKey);
            if (result == null)
            {
                return "";
            }
            return result.getConsignorName();
        }
        catch (NoPrimaryException e)
        {
            return "";
        }
    }

    /**
     * 入力されたパラメータをもとに荷主マスタ情報を検索するSQL文を発行します。<BR>
     * <BR>
     * 
     * @param consignorCode 荷主コード
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @return 荷主名称
     */
    protected String searchConsignor(String consignorCode)
            throws ReadWriteException
    {
        // 荷主名称取得
        ConsignorHandler consignorHandler = new ConsignorHandler(getConnection());
        ConsignorSearchKey cKey = new ConsignorSearchKey();

        // 取得条件
        cKey.setConsignorCode(consignorCode);
        cKey.setConsignorCodeGroup();
        cKey.setConsignorNameCollect("MAX");

        try
        {
            Consignor consignor = (Consignor)consignorHandler.findPrimary(cKey);
            if (consignor == null)
            {
                return "";
            }
            return consignor.getConsignorName();
        }
        catch (NoPrimaryException e)
        {
            return "";
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
        return "";
    }

}
