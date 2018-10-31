// $Id: LocateRegistSCH.java 5743 2009-11-12 13:56:21Z fukuwa $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.LocateRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * 棚マスタ登録のスケジュール処理を行います。
 * 
 * @version $Revision: 5743 $, $Date: 2009-11-12 22:56:21 +0900 (木, 12 11 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: fukuwa $
 */
public class LocateRegistSCH
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
    public LocateRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 棚マスタ登録処理<BR>
     * startParamsで指定された、パラメータの配列の内容をもとに、棚マスタ登録処理を行う。<BR>
     * 正常完了した場合はtrueを、問題があり処理を中断した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常に登録処理が終了した場合：<CODE>true</CODE>
     *          登録処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(LocateRegistSCHParams startParam)
            throws CommonException
    {
        // 棚マスタエンティティクラス
        Locate locate = new Locate();
        // 棚マスタデータベースハンドラ
        LocateHandler locateHandler = new LocateHandler(getConnection());
        // 棚マスタ検索キー
        LocateSearchKey searchKey = new LocateSearchKey();
        // 棚マスタコントローラー
        LocateController lCtl = new LocateController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }

            // 重複チェック(DMLocate内)
            // エリアNo.
            searchKey.setAreaNo(startParam.getString(AREA_NO));
            // 棚No.
            searchKey.setLocationNo(startParam.getString(LOCATE_NO));

            // 既に存在する場合
            if (locateHandler.count(searchKey) > 0)
            {
                // (6023154)入力された棚No.は既に登録されています
                setMessage("6023154");

                // 異常の場合はfalseを返却
                return false;
            }

            // システム管理区分（通常）
            locate.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // エリアNo.
            locate.setAreaNo(startParam.getString(AREA_NO));
            // 棚No.
            locate.setLocationNo(startParam.getString(LOCATE_NO));
            // アイルNo.
            locate.setAisleNo(startParam.getInt(AISLE_NO));

            // Stringのバンク･ベイ・レベルを取得
            int[] bankBayLevel = getIntShelfFormatArray(startParam.getString(LOCATE_NO), startParam.getString(AREA_NO));
           
            // 棚フォーマットによって挿入
        	if( bankBayLevel.length > 2)
        	{
            	// バンク
        	    locate.setBankNo(bankBayLevel[SystemDefine.BANK]);
                // ベイ
        	    locate.setBayNo(bankBayLevel[SystemDefine.BAY]);
                // レベル
        	    locate.setLevelNo(bankBayLevel[SystemDefine.LEVEL]);
        	}

            // 在庫情報を検索し、入力棚に在庫が存在するかチェックします。
            Boolean isStocks = checkStock(startParam);

            //状態フラグ
            if (isStocks)
            {
                // 実棚
                locate.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_STORAGED);
            }
            else
            {
                // 空棚
                locate.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_EMPTY);
            }
            // 登録処理名
            locate.setRegistPname(cName);
            // 最終更新処理名
            locate.setLastUpdatePname(cName);

            // 棚マスタ(新規登録)
            locateHandler.create(locate);

            // 棚マスタ更新履歴情報(新規登録)
            lCtl.insertHistory(locate, SystemDefine.UPDATE_KIND_REGIST, getWmsUserInfo().getDfkUserInfo());

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
            // (6023154)入力された棚No.は既に登録されています
            setMessage("6023154");

            // 異常の場合はfalseを返却
            return false;
        }
        catch ( NumberFormatException e)
        {
        	// (6023280)棚フォーマットに数字が含まれているため、登録できません。
            setMessage("6023280");

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
     * 棚No.から在庫を検索し在庫情報が存在するかチェックします。<BR>
     * 在庫が存在した場合true、無かった場合falseを返します。<BR>
     * <BR>
     * @param param 検索条件
     * @return boolean 在庫が存在した場合:<CODE>true</CODE>
     *                  在庫が存在しなかった場合：<CODE>false</CODE>
     * @throws CommonException 全ての例外をスローします。
     */
    private boolean checkStock(LocateRegistSCHParams param)
            throws CommonException
    {
        // 在庫エンティティ
        Stock[] stock = null;
        // 在庫情報データベースハンドラ
        StockHandler stockHndl = new StockHandler(getConnection());
        // 在庫情報検索キー
        StockSearchKey stockShky = new StockSearchKey();

        //検索キーをセット
        // エリアNo.
        stockShky.setAreaNo(param.getString(AREA_NO));
        // 棚No.
        stockShky.setLocationNo(param.getString(LOCATE_NO));

        // 検索結果を取得
        // 在庫情報エンティティ
        stock = (Stock[])stockHndl.find(stockShky);
        if (stock == null || stock.length == 0)
        {
            // データが存在しない場合はfalseを返却
            return false;
        }
        // データが存在する場合はtrueを返却
        return true;
    }
    
    /**
     * 棚No.のInt配列取得処理<jp>
     * @param location 入力棚
     * @param areaNo 入力エリア
     * @return バンク･ベイ･レベルが格納されている配列を返す
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    
    protected int[] getIntShelfFormatArray(String location, String areaNo)
    throws CommonException
	{
    	// 棚フォーマットスタイルを取得
		AreaController areaController = new AreaController(getConnection(), getClass());				
		String style = areaController.getLocationStyle(areaNo);
		
		// areaNoと同じ棚フォーマットで区切られた配列を用意する。 
		String[] locationStyle = new String[style.split("\\W").length];
		int[] locationNo = new int[style.split("\\W").length];
		
		// areaNo.の棚フォーマットの列を入れる。
		locationStyle = style.split("\\W");
		
		
		for(int i = 0, size = 0; i < locationStyle.length ; i++ )
		{			
			// 数字のみかチェック。そうでない場合はNumberFormatExceptionをスローする
			locationNo[i] = Integer.parseInt(locationStyle[i]);
			
			locationNo[i] = Integer.parseInt(location.substring(size, size + locationStyle[i].length()));
			size = size + locationStyle[i].length();
		}
				
		// バンク･ベイ･レベルが格納されている配列を返す
		return locationNo;
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
