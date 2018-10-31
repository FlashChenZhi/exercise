// $Id: PackageManager.java 4604 2009-07-03 07:27:23Z shibamoto $
package jp.co.daifuku.wms.base.communication.rft;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;

/**
 * WMSのパッケージおよびそれに対応するJAVAのパッケージを管理する。
 * 
 * @version $Revision: 4604 $, $Date: 2009-07-03 16:27:23 +0900 (金, 03 7 2009) $
 * @author  $Author: shibamoto $
 */
public class PackageManager
{
    // Class fields----------------------------------------------------
    private static final String EXTENDED_POINT = "%%";

    /**
     * パッケージ名一覧
     * ベース(PACKAGE_NAME_BASE)以外は、対応するパッケージが導入
     * されていないと、インスタンス生成時の検索対象になりません。
     * (製番拡張用のパッケージがある場合は、"%%"の位置に拡張パッケージ名がセットされます)
     * (製番拡張用のパッケージは、WmsParamにWMS_EXTENDED_PACKAGE で登録します。
     *  空白の場合は、製番拡張用のパッケージなしと判断します。)
     * (標準パッケージは、"%%"を削除したパッケージ文字列となります。)
     */
    private static final String[] PACKAGE_NAME_BASE = {
        "jp.co.daifuku.wms.%%base.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_RECEVING = {
        "jp.co.daifuku.wms.%%receiving.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_STORAGE = {
        "jp.co.daifuku.wms.%%storage.rft.schedule",
        "jp.co.daifuku.wms.%%inventorychk.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_RETRIEVAL = {
        "jp.co.daifuku.wms.%%retrieval.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_SORTING = {
        "jp.co.daifuku.wms.%%sort.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_SHIPPING = {
        "jp.co.daifuku.wms.%%ship.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_STOCK = {
        "jp.co.daifuku.wms.%%stock.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_ASRS = {
        "jp.co.daifuku.wms.%%asrs.rft.schedule"
    };

    private static final String[] PACKAGE_NAME_MASTER = {
        "jp.co.daifuku.wms.%%master.rft.schedule",
    };

    private static final String[] PACKAGE_NAME_PCTMASTER = {
        "jp.co.daifuku.pcart.%%master.rft",
        "jp.co.daifuku.pcart.%%base.rft"
    };

    // Class variables -----------------------------------------------
    /**
     * JAVAのパッケージ名のリスト
     */
    private static String[] packageList = null;

    /**
     * コネクション
     */
    private static Connection wConn = null;


    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return "$Revision: 4604 $,$Date: 2009-07-03 16:27:23 +0900 (金, 03 7 2009) $";
    }


    /**
     * パッケージマネージャーを初期化する。
     * @param conn データベース接続用 Connection
     * @param caller 呼び出し元クラス
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     */
    public static void initialize(Connection conn, Class caller)
            throws CommonException
    {
        wConn = conn;

        createPackageList(caller);
        /* ADD Start 2006.10.27 E.Takeda */
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    // コネクションをクローズ
                    if (wConn != null)
                    {
                        wConn.close();
                    }
                }
                catch (SQLException e)
                {
                }
            }
        });
        /* ADD End 2006.10.27 E.Takeda */

    }


    /**
     * 指定されたクラス名のインスタンスを生成して返します。
     * 
     * @param className     生成するインスタンスのクラス名
     * @param caller        呼び出し元クラス
     * @return              新しく生成されたインスタンス
     * @throws IllegalAccessException   インスタンスの生成に失敗した場合に通知されます。
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     */
    public static Object getObject(String className, Class caller)
            throws IllegalAccessException,
                CommonException
    {
        getPackageList(caller);

        for (int i = 0; i < packageList.length; i++)
        {
            try
            {
                Class cl = Class.forName(packageList[i] + "." + className);
                Object obj = cl.newInstance();
                return obj;
            }
            catch (Exception e)
            {
                // インスタンスの生成に失敗した場合は次のパッケージを試す
                continue;
            }
        }

        // 有効な全てのパッケージでインスタンスの生成に失敗した場合は
        // 例外を投げる。
        throw new IllegalAccessException();
    }

    /**
     * DBのWareNavi_Systemテーブルからインストールされているパッケージを調べ、
     * それに対応するJAVAのパッケージ名のリストを生成する。
     * 
     * @param caller        呼び出し元クラス
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     */
    private static void createPackageList(Class caller)
            throws CommonException
    {
        //パッケージ名格納用の可変長配列を生成する。
        List<String> dmyPkgList = new ArrayList<String>();

        // WarenaviSystemControllerの生成
        WarenaviSystemController sysController = new WarenaviSystemController(wConn, caller);

        // 製番用拡張パッケージ
        String extendedPackageName = WmsParam.WMS_EXTENDED_PACKAGE;

        if (!StringUtil.isBlank(extendedPackageName))
        {
            dmyPkgList = addList(dmyPkgList, extendedPackageName + ".", sysController);
        }

        // 標準パッケージ
        dmyPkgList = addList(dmyPkgList, "", sysController);


        // パッケージリストに登録
        packageList = new String[dmyPkgList.size()];
        dmyPkgList.toArray(packageList);
    }

    /**
     * eWareNaviインストールパッケージに対応した、検索対象パッケージ文字列を追加登録
     * 
     * @param dmyPkgList    検索対象パッケージ文字列
     * @param replaceStr    拡張製番用に使用する場合にセットされる文字列
     * @param sysController WareNaviシステムコントローラ
     * @return 検索対象パッケージ文字列
     */
    private static List<String> addList(List<String> dmyPkgList, String replaceStr,
            WarenaviSystemController sysController)
    {

        // システムパッケージは必ず存在するので登録する。
        for (String pName : PACKAGE_NAME_BASE)
        {
            String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
            dmyPkgList.add(packageName);
        }

        // 入荷パッケージの登録。
        if (sysController.hasReceivingPack() || sysController.hasCrossdockPack())
        {
            for (String pName : PACKAGE_NAME_RECEVING)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // 入庫パッケージの登録。
        if (sysController.hasStoragePack())
        {
            for (String pName : PACKAGE_NAME_STORAGE)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // 出庫パッケージの登録。
        if (sysController.hasRetrievalPack())
        {
            for (String pName : PACKAGE_NAME_RETRIEVAL)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // 仕分パッケージの登録。
        if (sysController.hasSortingPack() || sysController.hasCrossdockPack())
        {
            for (String pName : PACKAGE_NAME_SORTING)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // 出荷パッケージの登録。
        if (sysController.hasShippingPack() || sysController.hasCrossdockPack())
        {
            for (String pName : PACKAGE_NAME_SHIPPING)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // 在庫管理パッケージの登録。
        if (sysController.hasStockPack())
        {
            for (String pName : PACKAGE_NAME_STOCK)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // AS/RSパッケージの登録。
        if (sysController.hasAsrsPack())
        {
            for (String pName : PACKAGE_NAME_ASRS)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }
        
        // マスタパッケージの登録。
        if (sysController.hasMasterPack())
        {
            for (String pName : PACKAGE_NAME_MASTER)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        // Pカート用マスタパッケージの登録。
        if (sysController.hasPCTMasterPack())
        {
            for (String pName : PACKAGE_NAME_PCTMASTER)
            {
                String packageName = pName.replaceAll(EXTENDED_POINT, replaceStr);
                dmyPkgList.add(packageName);
            }
        }

        return dmyPkgList;
    }

    /**
     * 現在のシステム設定で有効なWMSのパッケージに対する
     * JAVAのパッケージ名のリストを取得し、呼び出し元に返す。<BR>
     * <CODE>packageList</CODE>が<CODE>null</CODE>の場合のみWareNavi_Systemから
     * インストール状態を取得し、有効なパッケージ名のリストを生成する。<BR>
     * <CODE>null</CODE>でない場合は既に取得したパッケージリストを返す。
     * 
     * @param caller        呼び出し元クラス
     * @return JAVAパッケージ名のリスト  
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     */
    private static String[] getPackageList(Class caller)
            throws CommonException
    {
        if (packageList == null)
        {
            createPackageList(caller);
        }
        return packageList;
    }
}
//end of class
