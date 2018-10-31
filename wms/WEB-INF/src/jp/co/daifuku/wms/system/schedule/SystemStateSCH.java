// $Id: SystemStateSCH.java 8096 2015-02-27 02:33:04Z okamura $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;

import static jp.co.daifuku.wms.system.schedule.SystemStateSCHParams.*;

/**
 * システム状態設定のスケジュール処理を行います。
 * 
 * @version $Revision: 8096 $, $Date: 2015-02-27 11:33:04 +0900 (金, 27 2 2015) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class SystemStateSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    /**
     * WareNaviSystem プロダクト定義ファイル名
     */
    private final String wProductFile = "c:/daifuku35/wms/etc/versionInfo.txt";

    /**
     * プロダクト表示開始定義 
     */
    private final String wProductStart = "DISPLAY-START";

    /**
     * プロダクト表示終了定義
     */
    private final String wProductEnd = "DISPLAY-END";


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public SystemStateSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
    	
    	List<Params> result = new ArrayList<Params>();
    	Params param = new Params();
    	
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // 日次更新中チェック
        if (sysController.isDailyUpdating())
        {
            param.set(IN_DAILY_PROCESS, DisplayText.getText("LBL-W0376"));
        }
        else
        {
            param.set(IN_DAILY_PROCESS,DisplayText.getText("LBL-W0375"));
        }

        // 予定データ取込中チェック
        if (sysController.isDataLoading())
        {
            param.set(IN_LOAD_PLAN_DATA,DisplayText.getText("LBL-W0376"));
        }
        else
        {
            param.set(IN_LOAD_PLAN_DATA,DisplayText.getText("LBL-W0375"));
        }

        // 報告データ作成中チェック
        if (sysController.isDataReporting())
        {
            param.set(IN_CREATE_REPORT_DATA,DisplayText.getText("LBL-W0376"));
        }
        else
        {
            param.set(IN_CREATE_REPORT_DATA,DisplayText.getText("LBL-W0375"));
        }

        // 出庫引当中フラグ
        if (sysController.isRetrievalAllocating())
        {
            param.set(IN_RETRIEVAL_ALLOCATE,DisplayText.getText("LBL-W0376"));
        }
        else
        {
            param.set(IN_RETRIEVAL_ALLOCATE,DisplayText.getText("LBL-W0375"));
        }

        // 搬送データクリア中
        if (sysController.isAllocationClear())
        {
            param.set(IN_ALLOCATION_CLEAR,DisplayText.getText("LBL-W0376"));
        }
        else
        {
            param.set(IN_ALLOCATION_CLEAR,DisplayText.getText("LBL-W0375"));
        }
        
        // ホスト通信チェック
        if (sysController.isHostEnabled())
        {
            param.set(IN_HOST_COMMUNICATION,DisplayText.getText("LBL-W0377"));
        }
        else
        {
            param.set(IN_HOST_COMMUNICATION,DisplayText.getText("LBL-W0378"));
        }

//        // パッケージ導入チェック
//        if (createDispPack(sysController) != null)
//        {
//            param.set(IN_IMPLEMENTATION_PACKAGE,createDispPack(sysController));
//        }
        // 予定保持日数取得
        param.set(IN_PLAN_DATA_HOLD_DAYS,sysController.getPlanHoldPeriod());
        // 実績保持日数取得
        param.set(IN_RESULT_DATA_HOLD_DAYS,sysController.getResultHoldPeriod());

        result.add(param);
        // システム環境、定義情報取得
        // Version.txtファイルを読込み、プロダクトを編集する。
        String[] temp = this.getProductname(param);
        
        for (String str : temp)
        {
	        param = new Params();
	        int namepoint = str.indexOf(":");
	        
	        param.set(PRODUCT, str.substring(0, namepoint));
	    	param.set(VERSION, str.substring(namepoint + 1));
	    	
	        result.add(param);
        }
        setMessage("6001013");
        
        return result;
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
     * システム環境定義ファイルより、導入されているOS等のVersion情報を取得します。<BR>
     * @return システム環境情報
     */
    protected String[] getProductname(Params param)
    {
        FileReader fr = null;
        BufferedReader br = null;

        try
        {
            boolean startfind = false;

            fr = new FileReader(wProductFile);
            br = new BufferedReader(fr);

            List<String> listparam = new ArrayList<String>();
            String buf = "";

            while ((buf = br.readLine()) != null)
            {
                if (buf.equals(wProductStart))
                {
                    startfind = true;
                    continue;
                }
                if (buf.equals(wProductEnd))
                {
                    break;
                }

                if (startfind)
                {
                	listparam.add(buf);
                }
            }

            String[] productName = new String[listparam.size()];
            listparam.toArray(productName);

            return productName;
        }
        catch (IOException ex)
        {
            return null;
        }
        finally
        {
            try
            {
                if (fr != null)
                {
                    fr.close();
                }
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException e)
            {
                return null;
            }
        }
    }

//    /**
//     * パッケージ有無によって導入パッケージの表示文字列を作成します。
//     * @param param パッケージ有無をセットしたパラメータ
//     * @return 導入パッケージの文字列
//     */
//    protected String createDispPack(WarenaviSystemController sysController)
//    {
//        // 導入パッケージ用変数
//        String wSystemPackage = "";
//
//        // 入庫パッケージ導入チェック
//        if (sysController.hasStoragePack())
//        {
//            wSystemPackage += DisplayText.getText("LBL-W0367");
//        }
//        // 出庫パッケージ導入チェック
//        if (sysController.hasRetrievalPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0368");
//        }
//        // 在庫パッケージ導入チェック
//        if (sysController.hasStockPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0371");
//        }
//        // AS/RSパッケージ導入チェック
//        if (sysController.hasAsrsPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0373");
//        }
//        // マスタパッケージ導入チェック
//        if (sysController.hasMasterPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0374");
//        }
//        // 入荷パッケージ導入チェック
//        if (sysController.hasReceivingPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0366");
//        }
//        // 仕分パッケージ導入チェック
//        if (sysController.hasSortingPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0369");
//        }
//        // 出荷パッケージ導入チェック
//        if (sysController.hasShippingPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0370");
//        }
//        // 分析パッケージ導入チェック
//        if (sysController.hasAnalysisPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0372");
//        }
//        // クロスドックパッケージ導入チェック
//        if (sysController.hasCrossdockPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0515");
//        }
//        // Pカートパッケージ導入チェック
//        if (sysController.hasPCTInventoryPack() || sysController.hasPCTMasterPack()
//                || sysController.hasPCTReceivingPack() || sysController.hasPCTRetrievalPack())
//        {
//            if (!StringUtil.isBlank(wSystemPackage))
//            {
//                wSystemPackage += " + ";
//            }
//            wSystemPackage += DisplayText.getText("LBL-W0320");
//        }
//
//        return wSystemPackage;
//    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
