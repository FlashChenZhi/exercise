// $Id: WmsPrinterExporter.java 6168 2009-11-24 00:28:13Z okayama $
package jp.co.daifuku.wms.base.report;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.print.JRPrinterExporter;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.foundation.print.PrinterExporter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PrintHistoryHandler;
import jp.co.daifuku.wms.base.entity.PrintHistory;


/**
 * eWarenaviでの印刷処理クラスです。<BR>
 * このクラスは内部にPrintExporterを保持し、実際の印刷処理は保持しているクラスを使用して行います。
 * このクラスでは、印刷処理時に帳票発行履歴を作成するため使用します。<BR>
 *
 * @version $Revision: 6168 $, $Date: 2009-11-24 09:28:13 +0900 (火, 24 11 2009) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: okayama $
 */


public class WmsPrinterExporter implements PrintExporter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private DfkUserInfo _ui = null;
    private PrintExporter _exporter = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ。
     * 実際の帳票発行を行うクラスとDFKUserInfoを渡しインスタンスを生成する。
     * 
     * @param exporter 
     * @param ui
     */
    public WmsPrinterExporter(PrintExporter exporter, DfkUserInfo ui)
    {
        _exporter = exporter;
        _ui = ui;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.print.PrintExporter#print()
     */
    public File print()
            throws CommonException
    {
        // 帳票発行処理を行う
        File retfile = _exporter.print();
        
        // データ出力処理を行う
        String exportName = null;
        String reportName = null;
        if (_exporter instanceof JRPrinterExporter)
        {
            JRPrinterExporter exporter = (JRPrinterExporter)_exporter;
            exportName = exporter.getExportName();
            reportName = exporter.getLayout().getReportName();
        }
        else if (_exporter instanceof PrinterExporter)
        {
            PrinterExporter exporter = (PrinterExporter)_exporter;
            exportName = exporter.getExportName();
            reportName = exporter.getLayout().getReportName();
        }
        else
        {
            // 6026002=印刷後、帳票発行履歴の作成に失敗しました。印刷処理以外このクラスは使用できません。
            RmiMsgLogClient.write(6026002, this.getClass().getName());
            throw new ClassCastException();
        }
        
        Connection conn = null;
        try
        {
            conn = WmsParam.getConnection();
            PrintHistoryHandler printHistHandler = new PrintHistoryHandler(conn);
            
            PrintHistory ph = new PrintHistory();
            ph.setFileName(retfile.getName());
            ph.setXmlFileName(exportName);
            ph.setListResourcekey(reportName);
            ph.setPrintDate(new Date());
            ph.setPagenameResourcekey(_ui.getPageNameResourceKey());
            ph.setTerminalNo(_ui.getTerminalNumber());
            
            printHistHandler.create(ph);
            
            conn.commit();
            
        }
        catch (CommonException e)
        {
            // 6026001=印刷後、帳票発行履歴の作成に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6026001, e), this.getClass().getName());
            // 印刷後、帳票発行履歴の作成に失敗しました。
            throw e;
        }
        catch (Exception e)
        {
            // 6026001=印刷後、帳票発行履歴の作成に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6026001, e), this.getClass().getName());
            throw new ScheduleException();
        }
        finally
        {
            try
            {
                if (conn != null || !conn.isClosed())
                {
                    conn.rollback();
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                // 6026001=印刷後、帳票発行履歴の作成に失敗しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6026001, e), this.getClass().getName());
                throw new ReadWriteException();
            }
        }
        
        return retfile;
    }
    
    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.print.PrintExporter#rePrint(java.lang.String)
     */
    public File rePrint(String fileName)
            throws CommonException
    {
        return _exporter.rePrint(fileName);
    }
    
    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.da.Exporter#getFile()
     */
    public File getFile()
    {
        return _exporter.getFile();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.da.Exporter#close()
     */
    public void close()
    {
        _exporter.close();
        
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.da.Exporter#open()
     */
    public File open()
            throws CommonException
    {
        return _exporter.open();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.da.Exporter#setUseTempFile(boolean)
     */
    public void setUseTempFile(boolean temp)
    {
        _exporter.setUseTempFile(temp);
        
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.da.Exporter#write(jp.co.daifuku.foundation.common.Params)
     */
    public boolean write(Params rec)
            throws CommonException
    {
        return _exporter.write(rec);
    }
    
    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.da.Exporter#getHeaderRowsCount()
     */
    public int getHeaderRowsCount()
    {
        return _exporter.getHeaderRowsCount();
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
        return "$Id: WmsPrinterExporter.java 6168 2009-11-24 00:28:13Z okayama $";
    }

}

