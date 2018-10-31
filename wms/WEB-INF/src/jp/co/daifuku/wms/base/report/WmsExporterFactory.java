// $Id: WmsExporterFactory.java 5271 2009-10-26 07:04:25Z okamura $
package jp.co.daifuku.wms.base.report;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.foundation.da.AbstractExpFactory;
import jp.co.daifuku.foundation.print.JRPreviewExporter;
import jp.co.daifuku.foundation.print.JRPrinterExporter;
import jp.co.daifuku.foundation.print.PrintExporter;

/**
 * eWarenavi用のエクスポートクラスの生成を行います。
 *
 *
 * @version $Revision: 5271 $, $Date: 2009-10-26 16:04:25 +0900 (月, 26 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class WmsExporterFactory
        extends AbstractExpFactory
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /**
     * 帳票エンジン(SVF or JR)
     */
    private static final String BT_REPORT_ENGINE = "BT_REPORT_ENGINE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * 帳票エンジンがSVFかどうか
     */
    boolean isSvfEngine = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * エクスポートクラスの生成に必要な情報を取得します。
     * 
     * @param locale ロケール
     * @param ui ユーザ情報<br>
     * 端末からの呼び出しでない場合は null をセットします。
     */
    public WmsExporterFactory(Locale locale, DfkUserInfo ui)
    {
        super(locale, ui);

        isSvfEngine = "SVF".equals(CommonParam.getParam(BT_REPORT_ENGINE));
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    @Override
    public PrintExporter newPrinterExporter(String expName, boolean autoprint)
    {
        PrintExporter pe = isSvfEngine ? super.newPrinterExporter(expName, autoprint)
                           : newJRPrinterExporter(expName, autoprint);
        return new WmsPrinterExporter(pe, getUserInfo());

    }


    @Override
    public PrintExporter newPVExporter(String expName, HttpSession session)
    {
        return isSvfEngine ? super.newPVExporter(expName, session)
                          : newJRPVExporter(expName, session);
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
    /**
     * JasperReportsを使用する印刷用Exporterを生成します。
     * @param expName エクスポート名
     * @param autoprint 自動プリントの場合true
     * @return JasperReportsを使用する印刷用のPrintExporter
     */
    private PrintExporter newJRPrinterExporter(String expName, boolean autoprint)
    {
        String ddir = (autoprint) ? CommonParam.getParam(CKEY_PR_AUTOPRINT_FILE_DIR)
                                 : CommonParam.getParam(CKEY_PR_FILE_DIR);
        File outDir = createOutDir(ddir, null);

        Locale locale = getLocale();
        String pr = getPrinterName();

        JRPreviewExporter exp = new JRPrinterExporter(expName, outDir, locale, pr);
        exp.setUseTempFile(true);

        return exp;
    }

    /**
     * JasperReportsを使用するプレビュー用Exporterを生成します。
     * @param expName エクスポート名
     * @param session HttpSession
     * @return JasperReportsを使用するプレビュー用のPrintExporter
     */
    private PrintExporter newJRPVExporter(String expName, HttpSession session)
    {
        String ddir = CommonParam.getParam(CKEY_PR_PREVIEW_FILE_DIR);
        File outDir = createOutDir(ddir, session);

        PrintExporter exp = new JRPreviewExporter(expName, outDir, getLocale());
        exp.setUseTempFile(null == session);

        return exp;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WmsExporterFactory.java 5271 2009-10-26 07:04:25Z okamura $";
    }
}
