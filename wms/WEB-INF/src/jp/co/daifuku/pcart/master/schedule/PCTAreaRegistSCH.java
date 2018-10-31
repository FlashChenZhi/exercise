// $Id: PCTAreaRegistSCH.java 6303 2009-12-02 07:09:46Z okamura $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTAreaRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * エリアマスタ登録のスケジュール処理を行います。
 *
 * @version $Revision: 6303 $, $Date: 2009-12-02 16:09:46 +0900 (水, 02 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class PCTAreaRegistSCH
        extends AbstractSCH
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
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public PCTAreaRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * エリアマスタ登録処理<BR>
     * startParamで指定された内容をもとに、エリアマスタの登録処理を行う。<BR>
     * 正常完了した場合はtrueを、問題があり処理を中断した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常に登録処理が終了した場合：<CODE>true</CODE>
     *          登録処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(PCTAreaRegistSCHParams startParam)
            throws CommonException
    {
        // エリアマスタエンティティ
        Area area = new Area();
        // エリアマスタデータベースハンドラ
        AreaHandler aHandler = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey akey = new AreaSearchKey();

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                // 異常の場合はfalseを返却
                return false;
            }

            // 重複チェック(DMArea内)
            // エリアNo
            akey.setAreaNo(startParam.getString(AREA_NO));

            // 既に存在する場合
            if (aHandler.count(akey) > 0)
            {
                // (6023153)入力されたエリアNo.は既に登録されています。
                setMessage("6023153");

                // 異常の場合はfalseを返却
                return false;
            }

            // システム定義チェック
            if (WmsParam.ALL_AREA_NO.equals(startParam.getString(AREA_NO)))
            {
                // (6023222)入力された{0}はシステムで使用しているため登録できません。
                setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W9915")));

                // 異常の場合はfalseを返却
                return false;
            }

            // 登録データのセット
            // システム管理区分
            area.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // エリアNo
            area.setAreaNo(startParam.getString(AREA_NO));
            // エリア名称
            area.setAreaName(startParam.getString(AREA_NAME));
            // エリア種別
            area.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
            // 棚管理方式
            area.setLocationType(SystemDefine.LOCATION_TYPE_FREE);
            // 棚表示方式
            area.setLocationStyle(WmsParam.DEFAULT_LOCATE_STYLE);
            // 仮置在庫作成区分
            area.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_NONE);
            // 移動先仮置エリア
            area.setTemporaryArea(null);
            // 空棚検索方法
            area.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_BANK_VERTICAL);
            // 倉庫ステーションNo.
            area.setWhstationNo(null);
            // 入荷エリア
            area.setReceivingArea(null);
            // 登録処理名
            area.setRegistPname(cName);
            // 最終更新処理名
            area.setLastUpdatePname(cName);

            // エリアマスタ(新規登録)
            aHandler.create(area);

            // (6001003)登録しました。
            setMessage("6001003");

            // 正常の場合はtrueを返却
            return true;
        }
        // DBアクセスエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");

            // 異常の場合はfalseを返却
            return false;
        }
        // データ重複エラーが発生した場合
        catch (DataExistsException e)
        {
            // (6023153)入力されたエリアNo.は既に登録されています。
            setMessage("6023153");

            // 異常の場合はfalseを返却
            return false;
        }
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
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
