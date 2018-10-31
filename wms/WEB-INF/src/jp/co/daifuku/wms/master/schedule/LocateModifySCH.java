// $Id: LocateModifySCH.java,v 1.2 2009/02/24 02:31:30 ose Exp $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static jp.co.daifuku.wms.master.schedule.LocateModifySCHParams.*;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateAlterKey;
import jp.co.daifuku.wms.base.dbhandler.LocateFinder;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 棚マスタ修正・削除のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:31:30 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LocateModifySCH
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
    public LocateModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
    public List<Params> query(LocateModifySCHParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new LocateFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // MasterInParameterにキャスト
        ScheduleParams[] inParams = (ScheduleParams[])ps;

        // 条件チェック、取り込み中チェックを行う
        // 日次更新チェック
        if (!canStart() || isLoadData())
        {
            return false;
        }

        // 棚マスタ更新・削除処理
        LocateHandler lHandler = new LocateHandler(getConnection());

        // 削除条件
        // 棚マスタ関連テーブルチェック
        if (!existRelation(inParams))
        {
            return false;
        }

        // 棚マスタ修正・削除処理を行う。
        // 処理フラグが0：修正の場合
        if (ProcessFlag.UPDATE.equals(inParams[0].getProcessFlag()))
        {
            for (int i = 0; i < inParams.length; i++)
            {
                // 他端末の更新チェックと対象レコードのロック
                if (!isNoModify(inParams[i]))
                {
                    // 選択行をセット
                    setErrorRowIndex(inParams[i].getRowIndex());
                    return false;
                }
            }
            
            LocateAlterKey akey = new LocateAlterKey();
            LocateSearchKey skey = new LocateSearchKey();

            for (int i = 0; i < inParams.length; i++)
            {
                // 既存情報取得
                skey.clear();
                // エリアNo.
                skey.setAreaNo(inParams[i].getString(AREA_NO));
                // 棚No.
                skey.setLocationNo(inParams[i].getString(LOCATION_NO));
                // システム管理区分（通常）
                skey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);

                Locate oldMaster = (Locate)lHandler.findPrimary(skey);

                akey.clear();

                // 棚No.
                akey.setLocationNo(inParams[i].getString(LOCATION_NO));
                // システム管理区分（通常）
                akey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
                // エリアNo.
                akey.setAreaNo(inParams[i].getString(AREA_NO));
                // アイルNo.
                akey.updateAisleNo(inParams[i].getInt(AISLE_NO));
                // 更新処理名を取得
                String pname = this.getClass().getSimpleName();
                // 最終更新処理名
                akey.updateLastUpdatePname(pname);

                lHandler.modify(akey);

                // 更新後情報取得
                skey.clear();
                // エリアNo.
                skey.setAreaNo(inParams[i].getString(AREA_NO));
                // 棚No.
                skey.setLocationNo(inParams[i].getString(LOCATION_NO));
                // システム管理区分（通常）
                skey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);

                Locate newMaster = (Locate)lHandler.findPrimary(skey);

                LocateController aCtl = new LocateController(getConnection(), this.getClass());

                // エリアマスタ改廃履歴登録
                aCtl.insertHistory(oldMaster, newMaster, SystemDefine.UPDATE_KIND_MODIFY, getUserInfo());
            }

            // 修正しました。
            setMessage("6001004");
        }
        // 処理フラグが１:削除の場合
        else if (ProcessFlag.DELETE.equals(inParams[0].getProcessFlag()))
        {
            // 他端末の更新チェックと対象レコードのロック
            if (!isNoModify(inParams[0]))
            {
                // 選択行をセット
                setErrorRowIndex(inParams[0].getRowIndex());
                return false;
            }

            LocateSearchKey skey = new LocateSearchKey();

            // 既存情報取得
            skey.clear();
            // エリアNo.
            skey.setAreaNo(inParams[0].getString(AREA_NO));
            // 棚No.
            skey.setLocationNo(inParams[0].getString(LOCATION_NO));
            // システム管理区分（通常）
            skey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);

            Locate oldMaster = (Locate)lHandler.findPrimary(skey);

            // 削除条件
            skey.clear();
            // エリアNo.
            skey.setAreaNo(inParams[0].getString(AREA_NO));
            // 棚No.
            skey.setLocationNo(inParams[0].getString(LOCATION_NO));
            // システム管理区分（通常）
            skey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);

            lHandler.drop(skey);
            LocateController aCtl = new LocateController(getConnection(), this.getClass());

            // エリアマスタ改廃履歴登録
            aCtl.insertHistory(oldMaster, SystemDefine.UPDATE_KIND_DELETE, getUserInfo());

            // 削除しました。
            setMessage("6001005");
        }
        return true;


    }

    /**
     * startSCHにて処理をまとめているため、未使用。<br>
     * 自動生成されたロジックより呼び出しがあるため、Trueを返す。
     * 
     * @param p  未使用
     * @return  未使用
     * @throws CommonException  
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
		return true;
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        LocateSearchKey searchKey = new LocateSearchKey();

        // 棚No.のFrom-Toチェック
        String[] tmp = WmsFormatter.getFromTo(p.getString(FROM_LOCATE_NO), p.getString(TO_LOCATE_NO));
        String from = tmp[0];
        String to = tmp[1];
        
        // エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            searchKey.setAreaNo(p.getString(AREA_NO));
        }
        // 棚No.(From)
        if (!StringUtil.isBlank(from))
        {
            searchKey.setLocationNo(from, ">=");
        }
        // 棚No.(To)
        if (!StringUtil.isBlank(to))
        {
            searchKey.setLocationNo(to, "<=");
        }

        // 結合条件
        searchKey.setJoin(Locate.AREA_NO, Area.AREA_NO);
        searchKey.setCollect(Area.AREA_NAME);

        searchKey.setCollect(new FieldName(Locate.STORE_NAME, FieldName.ALL_FIELDS));

        // ソート順をセットする
        searchKey.setLocationNoOrder(true);
        
        return searchKey;
    }
    
    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Locate[] entities = (Locate[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Locate ent : entities)
        {
            Params param = new Params();

            // エリアNo
            param.set(AREA_NO, ent.getAreaNo());
            // エリア名称
            param.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            // 棚No.
            param.set(LOCATION_NO, ent.getLocationNo());
            // アイルNo.
            param.set(AISLE_NO, ent.getAisleNo());
            // 最終更新日時(排他チェック用）
            param.set(UPDAY, ent.getLastUpdateDate());

            result.add(param);
        }

        return result;
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
    protected boolean existRelation(ScheduleParams[] checkParams)
            throws CommonException
    {
    	for(Params checkParam:checkParams)
    	{
	        ScheduleParams inParam = (ScheduleParams)checkParam;
	
	        // ----------商品固定棚情報----------
	        if (!existFixedLocateInfo(inParam))
	        {
	            // 6023159=商品固定棚情報に登録されているため、修正・削除できません。
	            setMessage("6023159");
	            setErrorRowIndex(inParam.getRowIndex());
	            return false;
	        }
	
	        // ----------在庫情報----------
	        if (!existStock(inParam))
	        {
	            // 6023161=在庫情報が存在するため、修正・削除できません。
	            setMessage("6023161");
	            setErrorRowIndex(inParam.getRowIndex());
	            return false;
	        }
	
	        // ----------作業情報----------
	        if (!existWorkInfo(inParam))
	        {
	            // 6023160=入出庫作業情報が存在するため、修正・削除できません。
	            setMessage("6023160");
	            setErrorRowIndex(inParam.getRowIndex());
	            return false;
	        }
    	}
        return true;
    }
    
    /**
     * 商品固定棚情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を 返します。<BR>
     * 
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existFixedLocateInfo(ScheduleParams inParam)
            throws CommonException
    {
        FixedLocateInfoHandler fixedLocateInfoHandler = new FixedLocateInfoHandler(getConnection());
        FixedLocateInfoSearchKey fixedLocateInfoKey = new FixedLocateInfoSearchKey();
        // エリア
        fixedLocateInfoKey.setAreaNo(inParam.getString(AREA_NO));
        // 棚No
        fixedLocateInfoKey.setLocationNo(inParam.getString(LOCATION_NO));
        // 検索処理
        if (fixedLocateInfoHandler.count(fixedLocateInfoKey) > 0)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 在庫情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を 返します。<BR>
     * 
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *         検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existStock(ScheduleParams inParam)
            throws CommonException
    {
        StockHandler stockHandler = new StockHandler(getConnection());
        StockSearchKey stockKey = new StockSearchKey();
        // エリア
        stockKey.setAreaNo(inParam.getString(AREA_NO));
        // 棚No
        stockKey.setLocationNo(inParam.getString(LOCATION_NO));
        // 検索処理
        if (stockHandler.count(stockKey) > 0)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 入出庫作業情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を 返します。<BR>
     * 
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合)：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existWorkInfo(ScheduleParams inParam)
            throws CommonException
    {
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey workInfoKey = new WorkInfoSearchKey();
        // エリア
        workInfoKey.setPlanAreaNo(inParam.getString(AREA_NO));
        // 棚No
        workInfoKey.setPlanLocationNo(inParam.getString(LOCATION_NO));
        workInfoKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION, "!=");
        workInfoKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 検索処理
        if (workInfoHandler.count(workInfoKey) > 0)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 棚マスタ同時更新処理チェック<BR>
     * checkParam内の条件に合致する棚マスタ情報が他の端末で更新されていないか確認します。<BR>
     * 他の処理で棚マスタ情報が更新されていなかった場合はtrueを、更新されていた場合はfalseを返します。<BR>
     * @param checkParam 棚マスタチェック条件
     * @return 更新されていない：<CODE>True</CODE><BR>
     *          更新されている：<CODE>False</CODE><BR>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean isNoModify(Params checkParam)
            throws CommonException
    {
        // ScheduleParamsにキャスト
        ScheduleParams inParam = (ScheduleParams)checkParam;
        // 棚マスタの検索
        // 検索キー
        LocateHandler locateHandler = new LocateHandler(getConnection());
        LocateSearchKey locateSKey = new LocateSearchKey();
        // エリア
        locateSKey.setAreaNo(inParam.getString(AREA_NO));
        // 棚No.
        locateSKey.setLocationNo(inParam.getString(LOCATION_NO));
        // 最終更新日
        locateSKey.setKey(Locate.LAST_UPDATE_DATE, inParam.getDate(UPDAY));
        // システム管理区分：ユーザ
        locateSKey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);

        // ロック処理
        try 
        {
            if (locateHandler.findPrimaryForUpdate(locateSKey, LocateHandler.WAIT_SEC_DEFAULT) != null)
            {
                return true;
            }
            
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            String msg = WmsMessageFormatter.format(6023015, WmsFormatter.getNumFormat(inParam.getRowIndex()));
            setMessage(msg);
            return false;
        }
        catch(LockTimeOutException e) 
        {
            // 6023014=No.{0} 他端末で処理中のため、処理を中断しました。
            String msg = WmsMessageFormatter.format(6023014, WmsFormatter.getNumFormat(inParam.getRowIndex()));
            setMessage(msg);
            return false;
        }

    }

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
