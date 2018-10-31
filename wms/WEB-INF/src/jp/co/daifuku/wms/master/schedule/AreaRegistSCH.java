// $Id: AreaRegistSCH.java 6754 2010-01-21 07:59:29Z kanda $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.AreaRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * エリアマスタ登録のスケジュール処理を行います。
 * 
 * @version $Revision: 6754 $, $Date: 2010-01-21 16:59:29 +0900 (木, 21 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class AreaRegistSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    
    /**
     * フリー管理の棚フォーマットで桁数チェックが必要のない数の閾値です。
     */
    private static int freeAreaPermNumber = 8;
    
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
    public AreaRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * エリアマスタ登録処理<BR>
     * startParamsで指定された内容をもとに、エリアマスタの登録処理を行う。<BR>
     * 正常完了した場合はtrueを、問題があり処理を中断した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常に登録処理が終了した場合：<CODE>true</CODE>
     *          登録処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(AreaRegistSCHParams startParam)
            throws CommonException
    {
        // エリアマスタエンティティ
        Area area = new Area();
        // エリアマスタデータベースハンドラ
        AreaHandler aHandler = new AreaHandler(getConnection());
        // エリアマスタ検索キー
        AreaSearchKey akey = new AreaSearchKey();
        // エリアマスタコントローラー
        AreaController aCtl = new AreaController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {

            // 棚フォーマットのエラー条件を検索          
            if (!checkLocationStyleFormat(startParam))
            {
                // 異常の場合はfalseを返却
                return false;
            }


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
            area.setAreaType(startParam.getString(AREA_TYPE));
            // 棚管理方式
            area.setLocationType(startParam.getString(LOCATION_TYPE));
            // 棚表示方式
            area.setLocationStyle(startParam.getString(LOCATION_STYLE).toUpperCase());
            // 仮置在庫作成区分(画面にてチェックされている場合)
            if (startParam.getBoolean(MOVE_TEMPORARY_STORAGE))
            {
                // 移動先仮置エリア
                area.setTemporaryArea(startParam.getString(TEMPORARY_AREA));
                area.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_ALL);
            }
            else
            {
                // 移動先仮置エリア
                area.setTemporaryArea(null);
                area.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_NONE);
            }
            // 空棚検索方法
            area.setVacantSearchType(startParam.getString(VACANT_SEARCH_TYPE));
            // 倉庫ステーションNo.
            area.setWhstationNo(null);
            // 入荷エリア(画面にてチェックされている場合)
            if (startParam.getBoolean(CHK_RECEIVING_AREA))
            {
                area.setReceivingArea(startParam.getString(RECEIVING_AREA));
            }
            else
            {
                area.setReceivingArea(null);
            }
            // 登録処理名
            area.setRegistPname(cName);
            // 最終更新処理名
            area.setLastUpdatePname(cName);

            // エリアマスタ(新規登録)
            aHandler.create(area);


            // エリアマスタ更新履歴情報(新規登録)
            aCtl.insertHistory(area, SystemDefine.UPDATE_KIND_REGIST, getWmsUserInfo().getDfkUserInfo());

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

    /**
     * 棚フォーマットの入力チェックをします。<BR>
     * <BR>
     * @param AreaRegistSCHParams チェック条件
     * @return 入力に不正がある場合（処理の続行が不可能な場合）：<CODE>false</CODE>
     *         入力に問題ない場合（処理の続行が可能な場合）:<CODE>true</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean checkLocationStyleFormat(AreaRegistSCHParams check)
            throws CommonException
    {
        String locationStyle = check.getString(LOCATION_STYLE);

        // 入力がない場合
        if (StringUtil.isBlank(locationStyle))
        {
            return false;
        }
        // 最初か最後がハイフンの時
        if (locationStyle.charAt(0) == '-' || locationStyle.charAt(locationStyle.length() - 1) == '-')
        {
            // (6047031)棚フォーマットエラーです。
            setMessage("6023697");
            return false;
        }

        char c;
        int noOfHyphens = 0, noOfChars = 0;
        for (int i = 0; i < locationStyle.length(); i++)
        {
            // 指定以外の文字が入力された場合
            c = Character.toUpperCase(locationStyle.charAt(i));
            if (c != 'X' && c != '9' && c != '-')
            {
                // (6047031)棚フォーマットエラーです。
                setMessage("6023697");
                return false;
            }

            // 配列のチェック項目は文字列の長さの一つ前までで完了する
            if (i < locationStyle.length() - 1)
            {
                // ハイフンが連続で入力された場合
                if (c == '-' && locationStyle.charAt(i + 1) == '-')
                {
                    // (6047031)棚フォーマットエラーです。
                    setMessage("6023697");
                    return false;
                }
                // ９とXがハイフンの区分内で混じった場合（i.e.　X9-9X9）
                if (c == '9' && Character.toUpperCase(locationStyle.charAt(i + 1)) == 'X')
                {
                    // (6047031)棚フォーマットエラーです。
                    setMessage("6023697");
                    return false;
                }
                if (c == 'X' && Character.toUpperCase(locationStyle.charAt(i + 1)) == '9')
                {
                    // (6047031)棚フォーマットエラーです。
                    setMessage("6023697");
                    return false;
                }
            }
            // 文字が入っているかチェック
            if (c == 'X')
            {
                noOfChars++;
            }
            // ハイフンが何個あるかチェック
            if (c == '-')
            {
                noOfHyphens++;
            }
        }

        // 棚マスタ管理の場合、数字とフォーマットに制限有（ハイフン2つに数字のみ）
        if (SystemDefine.LOCATION_TYPE_MASTER.equals(check.getString(LOCATION_TYPE))
                || SystemDefine.LOCATION_TYPE_FIXED.equals(check.getString(LOCATION_TYPE)))
        {
            if (noOfHyphens < 2 || noOfChars > 0)
            {
                // (6023284)棚フォーマットエラーです。棚マスタ管理、または固定棚管理の場合、ハイフン2つ以上と数字のみになります。
                setMessage("6023284");
                return false;
            }

        }
        
        // 平置フリー管理の棚の桁数、フォーマットチェックを行います。
        if(!checkFreeLocale(locationStyle.length(), noOfHyphens))
        {
            //棚フォーマットエラーです。フリー管理棚の場合、入力可能桁数はハイフンを抜いて8桁以下です。
            setMessage("6023295");
            return false;
        }
        
        return true;

    }
    
    /**
     * 平置フリー管理棚の桁数、フォーマットチェックを行います。
     * 9桁の場合、"-"が一つ以上含まれていること
     * 10桁の場合、"-"が二つ以上含まれていること
     * @param  locStyleLeng 入力された棚フォーマットの長さ
     * @param  hyphens 入力棚フォーマットに含まれるハイフンの数
     * @return 入力可の値であったとき：<CODE>true</CODE>
     *          入力不可の値であったとき：<CODE>false</CODE>
     */
    private boolean checkFreeLocale(int locStyleLeng, int hyphens)
        throws CommonException
    {
        //棚フォーマットの長さからハイフンを引いた数が8以下ならば、trueを返します。
        if((locStyleLeng - hyphens) <= freeAreaPermNumber)
        {
            return true;
        }
        return false;
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
