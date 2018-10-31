// $Id: AsWorkMntDASCHOperator.java 4670 2009-07-14 10:58:39Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.exporter.AsrsWorkMaintenanceListParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.asrs.exporter.AsrsWorkMaintenanceListParams;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;


/**
 * このクラスはAS/RS作業メンテナンスリストの発行処理のオペレーションを行います。<BR>
 * <BR>
 * Designer : H.Ohta <BR>
 * Maker    : H.Ohta <BR>
 * @version $Revision: 4670 $, $Date: 2009-07-14 19:58:39 +0900 (火, 14 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsWorkMntDASCHOperator

{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでオペレータークラスを作成します。
     * @param caller 呼び出し元クラス情報
     */
    public AsWorkMntDASCHOperator(Class caller)
    {
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
    /**
     * 帳票出力を行います。<BR>
     * <BR>
     * @param locale ロケール
     * @param ui ユーザー情報
     * @param param 検索条件パラメータ
     * @param conn コネクション
     * @return boolean 正常終了:true 異常終了:false
     */
    public boolean print(Locale locale, DfkUserInfo ui, AsrsInParameter param, Connection conn)
    {

        // 自動発行OFFの場合は帳票を印字しない
        if (!WmsParam.CARRY_MNT_AUTO_REPORT)
        {
            return true;
        }
        boolean flg = conn == null ? false
                                  : true;

        AsWorkMntDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            if (conn == null)
            {
                conn = ConnectionManager.getConnection();
            }

            dasch = new AsWorkMntDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsWorkMntDASCHParams inparam = new AsWorkMntDASCHParams();
            inparam.set(AsWorkMntDASCHParams.CARRY_KEY, param.getCarryKey());
            inparam.set(AsWorkMntDASCHParams.ASRS_PROCESS, param.getProcessStatus());

            // check count.
            dasch.count(inparam);

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("AsrsWorkMaintenanceList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsrsWorkMaintenanceListParams expparam = new AsrsWorkMaintenanceListParams();
                expparam.set(DFK_DS_NO, outparam.get(AsWorkMntDASCHParams.DFK_DS_NO));
                expparam.set(DFK_USER_ID, outparam.get(AsWorkMntDASCHParams.DFK_USER_ID));
                expparam.set(DFK_USER_NAME, outparam.get(AsWorkMntDASCHParams.DFK_USER_NAME));
                expparam.set(SYS_DAY, outparam.get(AsWorkMntDASCHParams.SYS_DAY));
                expparam.set(SYS_TIME, outparam.get(AsWorkMntDASCHParams.SYS_TIME));
                expparam.set(DEST_STATION_NO, outparam.get(AsWorkMntDASCHParams.DEST_STATION_NO));
                expparam.set(JOB_NO, outparam.get(AsWorkMntDASCHParams.JOB_NO));
                expparam.set(WORK_TYPE, outparam.get(AsWorkMntDASCHParams.WORK_TYPE));
                expparam.set(RETRIEVAL_DETAIL, outparam.get(AsWorkMntDASCHParams.RETRIEVAL_DETAIL));
                expparam.set(CMD_STATUS, outparam.get(AsWorkMntDASCHParams.CMD_STATUS));
                expparam.set(SYS_DAY, outparam.get(AsWorkMntDASCHParams.SYS_DAY));
                expparam.set(SYS_TIME, outparam.get(AsWorkMntDASCHParams.SYS_TIME));
                expparam.set(CARRY_KEY, outparam.get(AsWorkMntDASCHParams.CARRY_KEY));
                expparam.set(SCHEDULE_NO, outparam.get(AsWorkMntDASCHParams.SCHEDULE_NO));
                expparam.set(MAINTENANCE_TYPE, outparam.get(AsWorkMntDASCHParams.MAINTENANCE_TYPE));
                expparam.set(ALLOCATION_FLAG, outparam.get(AsWorkMntDASCHParams.ALLOCATION_FLAG));
                expparam.set(ITEM_CODE, outparam.get(AsWorkMntDASCHParams.ITEM_CODE));
                expparam.set(ITEM_NAME, outparam.get(AsWorkMntDASCHParams.ITEM_NAME));
                expparam.set(ENTERING_QTY, outparam.get(AsWorkMntDASCHParams.ENTERING_QTY));
                expparam.set(STOCK_CASE_QTY, outparam.get(AsWorkMntDASCHParams.STOCK_CASE_QTY));
                expparam.set(STOCK_PIECE_QTY, outparam.get(AsWorkMntDASCHParams.STOCK_PIECE_QTY));
                expparam.set(WORK_CASE_QTY, outparam.get(AsWorkMntDASCHParams.WORK_CASE_QTY));
                expparam.set(WORK_PIECE_QTY, outparam.get(AsWorkMntDASCHParams.WORK_PIECE_QTY));
                expparam.set(STORAGE_DAY, outparam.get(AsWorkMntDASCHParams.STORAGE_DAY));
                expparam.set(STORAGE_TIME, outparam.get(AsWorkMntDASCHParams.STORAGE_TIME));
                expparam.set(LOT_NO, outparam.get(AsWorkMntDASCHParams.LOT_NO));
                expparam.set(SOURCE_STATION_NO, outparam.get(AsWorkMntDASCHParams.SOURCE_STATION_NO));

                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();

            }
            return true;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;

        }
        finally
        {
            if (dasch != null)
            {
                if (flg)
                {
                    dasch.closeFinder();
                }
                else
                {
                    dasch.close();
                    DBUtil.close(conn);
                }

            }
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
