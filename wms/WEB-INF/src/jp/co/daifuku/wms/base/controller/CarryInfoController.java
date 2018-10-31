// $Id: CarryInfoController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;


/**
 * 搬送情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  osawa
 * @author  Last commit: $Author: kishimoto $
 */
public class CarryInfoController
        extends AbstractController
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


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public CarryInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 空棚に紐付くパレットを検索する搬送情報検索キーを取得します。
     * 
     * @return 搬送情報検索キー
     */
    public CarryInfoSearchKey getEmptyShelfPallet()
    {
        // 副問い合せキー
        CarryInfoSearchKey caryShKy = new CarryInfoSearchKey();

        caryShKy.setPalletIdCollect();
        caryShKy.setRestoringFlag(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC, "=", "(", "", true);
        caryShKy.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING, "=", "(", "", false);
        caryShKy.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING, "=", "", ")", true);
        caryShKy.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL, "=", "(", "", false);
        caryShKy.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", "", false);
        caryShKy.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", true);
        caryShKy.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "", ")))", false);
        caryShKy.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_UNIT, "=", "(", "", true);
        caryShKy.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL, "=", "", ")", true);
        caryShKy.setPalletIdGroup();

        return caryShKy;
    }

    /**
     * 搬送情報優先順チェック<br>
     * 指定した搬送情報検索キーに搬送情報読み込み順を付加し搬送情報を取得します。<br>
     * 引数で指定した搬送情報と比較し搬送優先順チェックの結果を返します。
     * 
     * @param ci 搬送情報
     * @param carrykey 搬送情報検索キー
     * @return 引数の搬送情報が優先ならばtrue、そうでないならばfalse
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     */
    public boolean checkCarryPriority(CarryInfo ci, CarryInfoSearchKey carrykey)
        throws ReadWriteException
    {
        // 搬送情報読み込み順をセット
        CarryInfoHandler cryh = new CarryInfoHandler(getConnection());
        carrykey.setPriorityOrder(true);
        carrykey.setRegistDateOrder(true);
        carrykey.setCarryKeyOrder(true);
        CarryInfo[] cInfo = (CarryInfo[])cryh.find(carrykey);
        if (cInfo.length > 0)
        {
            // 一致する搬送データが有った時、先頭のデータを参照し、引数の搬送情報より先に搬送が
            // 行なわれる搬送データかチェックする。
            if (cInfo[0].getPriority().compareTo(ci.getPriority()) > 0)
            {
                // 引数の搬送情報の優先区分が小さいので先搬送とし、trueを返す。
                return true;
            }
            else if (cInfo[0].getPriority().equals(ci.getPriority()))
            {
                if (cInfo[0].getRegistDate().compareTo(ci.getRegistDate()) > 0)
                {
                    // 引数の搬送情報の登録日付が小さいので先搬送とし、trueを返す。
                    return true;
                }
                else if (cInfo[0].getRegistDate().equals(ci.getRegistDate()))
                {
                    if (cInfo[0].getCarryKey().compareTo(ci.getCarryKey()) > 0)
                    {
                        // 引数の搬送情報の搬送キーが小さいので先搬送とし、trueを返す。
                        return true;
                    }
                }
            }
        }
        else
        {
            // 指定の条件に合った搬送データが無い時はtrueを返す。
            return true;
        }

        return false;
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
        return "$Id: CarryInfoController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
