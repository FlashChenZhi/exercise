//$Id: LocateSettingSCH.java 6299 2009-12-02 06:06:40Z fukuwa $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateFinder;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import static jp.co.daifuku.wms.master.schedule.LocateSettingSCHParams.*;

/**
 * 棚マスタ一括設定のスケジュール処理を行います。
 *
 * @version $Revision: 6299 $, $Date: 2009-12-02 15:06:40 +0900 (水, 02 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: fukuwa $
 */
public class LocateSettingSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * フォーマット時の0埋め用文字
     */
    private static final String ZERO = "0";

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
    public LocateSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 削除チェック<BR>
     * checkParam内の条件に合致する棚情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに棚情報が存在する場合はtrueを、見つからない場合はfalseを返します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * <BR>
     * @param checkParam チェック内容
     * @return 正常：true　異常：false
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */  
    public boolean check(LocateSettingSCHParams checkParam)
            throws CommonException
    {
        try
        {

   
        	AreaController areaController = new AreaController(getConnection(), getClass());  
        	int[] fromLocate = null;
        	int[] toLocate = null;

	        // 棚No.を数字の配列に直し、数字のみかをチェックする       

	        fromLocate = getIntShelfFormatArray(checkParam.getString(ST_LOCATE), checkParam.getString(AREA));
	        toLocate = getIntShelfFormatArray(checkParam.getString(ED_LOCATE), checkParam.getString(AREA));
        
	        // 設定区分を取得
	        String settingDivision = checkParam.getString(GROUP_LOCATION_SETTING);
	        
	        
	        // 棚No.範囲の大小チェックを行う
	        for (int i = 0; i < fromLocate.length; i++)
	        {
	        	if (fromLocate[i] > toLocate[i])
	        	{
	        		//　検索範囲を確認してください。
	        		setMessage("6123020");
	        		return false;
	        	}
	        }
	        
	
	    	// 合計の棚数を計算
	        int noOfShelves = 1;
	
	        for(int i = 0, count = 1; i < fromLocate.length; i++) 
	        {
		      	count = toLocate[i] - fromLocate[i]+1;
		      	if(count == 0)
		      	{
		      		count = 1;
		      	}       	
		      	noOfShelves *= count; 
		    }
	      
		    // 棚マスタ一括設定上限チェック
		    if (WmsParam.LOCATION_MAX < noOfShelves)
		    {
		    	// 6023232=棚マスタ一括設定の上限数{0}以下の棚数で設定を行ってください。
				setMessage(WmsMessageFormatter.format(6023232, WmsParam.LOCATION_MAX));
				return false;				
		    }

	        // 削除の場合
	        if(MasterInParameter.PROCESS_FLAG_DELETE.equals(settingDivision))
	        {
	        	String style = areaController.getLocationStyle(checkParam.getString(AREA));
	    		// areaNoと同じ棚フォーマットで区切られた配列を用意する。 
	    		String[] locationStyle = new String[style.split("\\W").length];	
	    		// areaNo.の棚フォーマットの列を入れる。
	    		locationStyle = style.split("\\W");
	    	
	            // 範囲内の棚No.を所得する
	            String[] range = getAllShelfNoInRange(fromLocate, toLocate, 0, locationStyle);          
	    
	            
	            int count = 0;                    
	            for (int i= 0; i < range.length ; i++)
	            {   
	            	// checkParam内の条件に合致する棚情報が他のテーブルに存在しないか確認します。
	        		if (!this.existRelation(checkParam, range[i]))
	                {
	                    //6023003=削除を行うデータが使用されています。
	                    return false;
	                }
	
	                // ハンドラインスタンス生成
	                LocateFinder locateFinder = null;
	                
	                // 削除対象データがあるか確認
	                try
	                {
	                    LocateSearchKey locateSearchKey = new LocateSearchKey();
	                    locateFinder = new LocateFinder(getConnection());
	                    // 削除条件
	                    // エリア
	                    locateSearchKey.setAreaNo(checkParam.getString(AREA));
	                    // 棚
	                    locateSearchKey.setLocationNo(range[i]);
	                    // システム管理区分
	                    locateSearchKey.setManagementType(Item.MANAGEMENT_TYPE_USER);
	                    
	                    locateFinder.open(true);
	                    if (locateFinder.search(locateSearchKey) != 0)
	                    {
	
	                        count++;
	                    }
	                }
	                finally
	                {
	                    closeFinder(locateFinder);
	                }                
	            }
	            
	            if (count == 0)
	            {
	                // 6003014=削除対象データがありませんでした。
	                setMessage("6003014");
	                return false;
	            }
	        }

        return true;
        }
        catch (NumberFormatException e)
        {
        	// 数字のみの棚フォーマットしか一括作業はできません。
        	setMessage("6123019");
        	return false;
        }
        // 棚マスタ一括設定上限チェック
        catch  (OutOfMemoryError e)

        {
			// 6023232=棚マスタ一括設定の上限数{0}以下の棚数で設定を行ってください。
			setMessage(WmsMessageFormatter.format(6023232, WmsParam.LOCATION_MAX));
			return false;			
        }
        
    }


    /**
     * 棚マスタ一括設定スケジュール開始処理<BR>
     * startParamsで指定するParameterクラスはMasterInParameter型であること。<BR>
     * @param startParams 開始条件
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(LocateSettingSCHParams startParams)
            throws CommonException
    {
    	try{
	        // 設定区分を取得
	        String settingDivision = startParams.getString(GROUP_LOCATION_SETTING);
	        // 開始棚Noのセーブ
	        String saveLocation = startParams.getString(ST_LOCATE);
	
	        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
	
	        // 日次更新チェック
	        if (!canStart() || isLoadData())
	        {
	            return false;
	        }
	
	        // 棚マスタハンドラ
	        LocateHandler locateHandler = new LocateHandler(getConnection());
	        LocateController aCtl = new LocateController(getConnection(), this.getClass());
	
	        int[] fromLocate = getIntShelfFormatArray(startParams.getString(ST_LOCATE), startParams.getString(AREA));
	        int[] toLocate = getIntShelfFormatArray(startParams.getString(ED_LOCATE), startParams.getString(AREA));
	        
	        AreaController areaController = new AreaController(getConnection(), getClass());
	        
	        String style = areaController.getLocationStyle(startParams.getString(AREA));
			// areaNoと同じ棚フォーマットで区切られた配列を用意する。 
			String[] locationStyle = new String[style.split("\\W").length];	
			// areaNo.の棚フォーマットの列を入れる。
			locationStyle = style.split("\\W");
	
			
	        // 範囲内の棚No.を所得する
	        String[] range = getAllShelfNoInRange(fromLocate, toLocate, 0, locationStyle); 
	        
	        // 棚マスタ一括登録処理を行う。
	        // 処理フラグが9：登録の場合
	        if (MasterInParameter.PROCESS_FLAG_REGIST.equals(settingDivision))
	        {
	
	
	            for(int i = 0; i < range.length; i++)
	            {
	            
	                // 棚No.をセット
	                startParams.set(ST_LOCATE, range[i]);
	
	                // 重複チェック
	                if (isRegist(startParams, range[i]))
	                {
	                    startParams.set(ST_LOCATE, saveLocation);
	                    return false;
	                }
	
	
	                // 在庫存在チェック
	                Boolean isStock = checkStock(startParams);
	
	                Locate locate = new Locate();
	                // エリア
	                locate.setAreaNo(startParams.getString(AREA));
	                // ロケーション
	                locate.setLocationNo(startParams.getString(ST_LOCATE));
	                // アイルNo.
	                locate.setAisleNo(startParams.getInt(AISLE_NO));
	                
	                
	                // Stringのバンク･ベイ・レベルを取得
	                int[] bankBayLevel = getIntShelfFormatArray(range[i], startParams.getString(AREA));
	               
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
	            	
	                // システム管理区分：ユーザ
	                locate.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);;
	                // 状態フラグ
	                if (isStock)
	                {
	                    // 実棚
	                    locate.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_STORAGED);
	                }
	                else
	                {
	                    // 空棚
	                    locate.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_EMPTY);
	                }
	
	                String pname = this.getClass().getSimpleName();
	                // 登録処理名
	                locate.setRegistPname(pname);
	                // 最終更新処理名
	                locate.setLastUpdatePname(pname);
	
	                locateHandler.create(locate);
	
	
	                // エリアマスタ改廃履歴登録
	                aCtl.insertHistory(locate, SystemDefine.UPDATE_KIND_REGIST, ui);
	
	            }
	            // 6001003=登録しました。
	            setMessage("6001003");
	        }
	        // 処理フラグが1：削除の場合
	        else
	        {
	        	for(int i = 0; i < range.length; i++)
	            {
		            
		            LocateSearchKey locateSearchKey = new LocateSearchKey();
		            // 削除条件
		            // エリア
		            locateSearchKey.setAreaNo(startParams.getString(AREA));
		            // 棚
		            locateSearchKey.setLocationNo(range[i]);
		            // システム管理区分
		            locateSearchKey.setManagementType(Item.MANAGEMENT_TYPE_USER);
		
		            // 既存情報取得
		            Locate locate = (Locate)locateHandler.findPrimary(locateSearchKey);
		
		            if (locate != null)
		            {
		                locateHandler.drop(locateSearchKey);
		
		                // エリアマスタ改廃履歴登録
		                aCtl.insertHistory(locate, SystemDefine.UPDATE_KIND_DELETE, ui);
		            }
	            }
	            // 削除しました。
	            setMessage("6001005");
	        }
	        return true;
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
    
    
    /**
     * 棚No.のString配列取得処理<jp>
     * @param location 入力棚
     * @param areaNo 入力エリア
     * @return バンク･ベイ･レベルが格納されている配列を返す
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected String[] getStringShelfFormatArray(String location, String areaNo)
            throws CommonException
    {

        AreaController areaController = new AreaController(getConnection(), getClass());

        // 棚フォーマットスタイルを取得
        String style = areaController.getLocationStyle(areaNo);

        String[] locationStyle = new String[style.split("\\W").length];
        String[] locationNo = new String[style.split("\\W").length];

        locationStyle = style.split("\\W");

        int size = 0;

        //  バンク･ベイ･レベルを取得
        for (int i = 0; i < locationStyle.length; i++)
        {
        	locationNo[i] = location.substring(size, size + locationStyle[i].length());
            size = size + locationStyle[i].length();
        }

        // バンク･ベイ･レベルが格納されている配列を返す
        return locationNo;
    }

    /**
     * 棚マスタ関連テーブルチェック<BR>
     * checkParam内の条件に合致する棚情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに棚情報が存在する場合は<CODE>true</CODE>を、<BR>
     * 見つからない場合はfalseを返します。<BR>
     * 画面側の削除事前チェック処理で使用します。<BR>
     * @param checkParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existRelation(LocateSettingSCHParams checkParam, String shelfNo)
            throws CommonException
    {
        // ----------商品固定棚情報----------
        if (!existFixedLocateInfo(checkParam, shelfNo))
        {
            // 6023159=商品固定棚情報に登録されているため、修正・削除できません。
            setMessage("6023159");
            return false;
        }
        // ----------在庫情報----------
        if (!existStock(checkParam, shelfNo))
        {
            // 6023161=在庫情報が存在するため、修正・削除できません。
            setMessage("6023161");
            return false;
        }
        // ----------作業情報----------
        if (!existWorkInfo(checkParam, shelfNo))
        {
            // 6023160=入出庫作業情報が存在するため、修正・削除できません。
            setMessage("6023160");
            return false;
        }
        return true;
    }

    /**
     * 重複チェック<BR>
     * checkParam内の条件に合致する棚マスタ情報が既に存在しないかチェックを行ないます<BR>
     * 棚マスタ情報にデータが存在した場合はtrueを、存在しない場合はfalseを返します。<BR>
     * 
     * @param checkParam チェック条件
     * @return 該当データが存在する：<CODE>True</CODE><BR>
     *          該当データが存在しない：<CODE>False</CODE><BR>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean isRegist(LocateSettingSCHParams checkParam, String shelfNo)
            throws CommonException
    {
        // 棚マスタの検索
        // 検索キー
        LocateHandler locateHandler = new LocateHandler(getConnection());
        LocateSearchKey locateSearchKey = new LocateSearchKey();
        // エリア
        locateSearchKey.setAreaNo(checkParam.getString(AREA));
        // 棚
        locateSearchKey.setLocationNo(shelfNo);

        // 検索処理
        if (locateHandler.count(locateSearchKey) > 0)
        {
            // 重複データあり
            // 6023154=入力された棚Noは既に登録されています。
            setMessage("6023154");
            return true;

        }
        return false;
    }

    /**
     * 棚No.から在庫を検索し在庫情報が存在するかチェックします。<BR>
     * 在庫が存在した場合true、無かった場合falseを返します。<BR>
     * @param param 検索条件
     * @return boolean 在庫が存在した場合true、無かった場合false
     * @throws CommonException 全ての例外をスローします。
     */
    protected Boolean checkStock(LocateSettingSCHParams param)
            throws CommonException
    {
        StockHandler stockHndl = new StockHandler(getConnection());
        StockSearchKey stockShky = new StockSearchKey();
        //検索条件をセット
        stockShky.setAreaNo(param.getString(AREA));
        stockShky.setLocationNo(param.getString(ST_LOCATE));

        // 検索結果を取得
        Stock[] stock = (Stock[])stockHndl.find(stockShky);
        if (stock == null || stock.length == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 商品固定棚情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を
     * 返します。<BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existFixedLocateInfo(LocateSettingSCHParams inParam, String shelfNo)
            throws CommonException
    {
        FixedLocateInfoHandler fixedLocateInfoHandler = new FixedLocateInfoHandler(getConnection());
        FixedLocateInfoSearchKey fixedLocateInfoSearchKey = new FixedLocateInfoSearchKey();
        // エリア
        fixedLocateInfoSearchKey.setAreaNo(inParam.getString(AREA));
        // 棚
        // 棚No.
        fixedLocateInfoSearchKey.setLocationNo(shelfNo);

        // 検索処理
        if (fixedLocateInfoHandler.count(fixedLocateInfoSearchKey) > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 在庫情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を
     * 返します。<BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existStock(LocateSettingSCHParams inParam, String shelfNo)
            throws CommonException
    {
        StockHandler stockHandler = new StockHandler(getConnection());
        StockSearchKey stockSearchKey = new StockSearchKey();
        // エリア
        stockSearchKey.setAreaNo(inParam.getString(AREA));
        // 棚
        // 棚No.
        stockSearchKey.setLocationNo(shelfNo);

        // 検索処理
        if (stockHandler.count(stockSearchKey) > 0)
        {
            return false;
        }
        return true;
    }


    /**
     * 作業情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を
     * 返します。<BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existWorkInfo(LocateSettingSCHParams inParam, String shelfNo)
            throws CommonException
    {
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey workInfoSearchKey = new WorkInfoSearchKey();
        // エリア
        workInfoSearchKey.setPlanAreaNo(inParam.getString(AREA));
        // 棚No.
        workInfoSearchKey.setPlanLocationNo(shelfNo);

        // 状態フラグ(削除、完了を除く）
        workInfoSearchKey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=", "(", "", true);
        workInfoSearchKey.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION, "!=", "", ")", true);
        // 検索処理
        if (workInfoHandler.count(workInfoSearchKey) > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 棚No.フォーマット処理
     * 棚No.の長さが入力桁数未満の場合、「0」で左詰めします。
     * @param locationNo フォーマット前の棚No.
     * @param locateLength 入力桁数
     * @return formatLocate フォーマット後の棚No.
     */
    protected String formatLocate(int locationNo, int locateLength)
    {

        StringBuilder buff = new StringBuilder(String.valueOf(locationNo));
        StringBuilder total = new StringBuilder();
        int sidx = String.valueOf(locationNo).length();
        for (int i = 0; i < locateLength; i++)
        {
            if (++sidx <= locateLength)
            {
                // format length too long, insert zero
                buff.insert(0, ZERO);
            }
        }
        // last pos
        total.append(buff);
        return new String(total);

    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    
    /**
     * このメソッドは配列に保存されている数字から（from～to）
     * 範囲内の値をすべて割り出し、Stringの配列で返す。
     * 最小棚から最大棚のを値を全部引き出すなどの為に使う
     * 注意：
     * 取得範囲が広すぎる場合は、
     * java.lang.OutOfMemoryError: Java heap space
     * エラーがでます。
     * 
     * @param from
     * @param to
     * @param x
     */
	private String[] getAllShelfNoInRange(int[] from, int[] to, int x, String [] shelfFormat)
	throws CommonException
	{		
		// 末端まで行ったら処理終わり
		if (x == from.length)
		{
			return new String[0];
		}
		
		// 今処理している箇所（区分）。
		ArrayList<String> current = new ArrayList<String>();
		for (int i = from[x]; i <= to[x] ; i++)
		{
			// int型なので区切りごとに０が足りない場合は０埋めを行ってから追加
			current.add(formatLocate(i, shelfFormat[x].length()));
			
		}
		
		// 今処理している箇所（区分）よりもネストの深い部分の配列一覧を取得する
		String[] children = getAllShelfNoInRange(from, to, x+1, shelfFormat);
		// 対象データがない＝今処理している箇所が末端なので、current配列を返す
		if (ArrayUtil.isEmpty(children))
		{
			return current.toArray(new String[current.size()]);
			
		}
		// 対象データがある＝今処理している箇所よりも末端がさらにある
		else
		{
			ArrayList<String> retVec = new ArrayList<String>();
			// 今処理している箇所とさらに末端で作成した配列とを
			// 連結する（＝「今処理している箇所」*「末端」分のサイズのArrayListができる）
			for (String m : current.toArray(new String[current.size()]))
			{
				for (String child : children)
				{
					// 新しいVectorに追加する。
					retVec.add(m + child);
				}
			}
			
			// 各区分をつないて返す
			return retVec.toArray(new String[retVec.size()]);
			
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
