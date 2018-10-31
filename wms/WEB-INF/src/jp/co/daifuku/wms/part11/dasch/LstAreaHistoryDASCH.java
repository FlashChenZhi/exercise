// $Id: LstAreaHistoryDASCH.java,v 1.2 2009/02/24 02:31:57 ose Exp $
package jp.co.daifuku.wms.part11.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.AreaHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.AreaHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaHistoryImpFinder;
import jp.co.daifuku.wms.base.dbhandler.AreaHistoryImpHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaHistoryImpSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaHistorySearchKey;
import jp.co.daifuku.wms.base.entity.AreaHistory;
import jp.co.daifuku.wms.base.entity.AreaHistoryImp;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.part11.listbox.Part11ListBoxDefine;

/**
 * エリアマスタ更新履歴詳細一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:31:57 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstAreaHistoryDASCH
        extends AbstractWmsDASCH
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

    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;
    
    /**
     * DB検索先
     */
    private String _dbtype = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public LstAreaHistoryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // TODO : Implement for export
        // 検索先のテーブルを記憶
        _dbtype = p.getString(LstAreaHistoryDASCHParams.TABLE_NAME);
        // Create Finder Object
        if (Part11ListBoxDefine.DBTYPE_DB.equals(p.getString(LstAreaHistoryDASCHParams.TABLE_NAME)))
        {
            // エリアマスタ更新履歴用
            _finder = new AreaHistoryFinder(getConnection());
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(p.getString(LstAreaHistoryDASCHParams.TABLE_NAME)))
        {
            // 取込用エリアマスタ更新履歴用
            _finder = new AreaHistoryImpFinder(getConnection());
        }
        else 
        {
            setMessage(null);
            throw new ScheduleException();
        }

        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;

    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        Params p = new Params();
        if(Part11ListBoxDefine.DBTYPE_DB.equals(_dbtype)) 
        {
            AreaHistory[] ents = (AreaHistory[])_finder.getEntities(1);
            
            // conver Entity to Param object
            p.set(LstAreaHistoryDASCHParams.LOGDATE_DAY, ents[0].getLogDate());
            p.set(LstAreaHistoryDASCHParams.LOGDATE_TIME, ents[0].getLogDate());
            p.set(LstAreaHistoryDASCHParams.UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ents[0].getUpdateKind()));
            p.set(LstAreaHistoryDASCHParams.USER_ID, ents[0].getUserId());
            p.set(LstAreaHistoryDASCHParams.USER_NAME, ents[0].getUserName());
            p.set(LstAreaHistoryDASCHParams.IP_ADRESS, ents[0].getIpAddress());
            p.set(LstAreaHistoryDASCHParams.TERMINAL_NAME, ents[0].getTerminalName());
            p.set(LstAreaHistoryDASCHParams.AREA_NO, ents[0].getAreaNo());
            p.set(LstAreaHistoryDASCHParams.AREA_NAME, ents[0].getAreaName());
            p.set(LstAreaHistoryDASCHParams.UPDATE_AREA_NAME, ents[0].getUpdateAreaName());
            if (!StringUtil.isBlank(ents[0].getTemporaryAreaType()))
            {
                p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ents[0].getTemporaryAreaType()));
            }
            if (!StringUtil.isBlank(ents[0].getUpdateTempAreaType()))
            {
                p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ents[0].getUpdateTempAreaType()));
            }
            p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA, ents[0].getTemporaryArea());
            p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA, ents[0].getTemporaryArea());
            if (!StringUtil.isBlank(ents[0].getVacantSearchType()))
            {
                p.set(LstAreaHistoryDASCHParams.VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ents[0].getVacantSearchType()));
            }
            if (!StringUtil.isBlank(ents[0].getUpdateVacantSearchType()))
            {
                p.set(LstAreaHistoryDASCHParams.UPDATE_VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ents[0].getUpdateVacantSearchType()));
            }
            p.set(LstAreaHistoryDASCHParams.RECEIVING_AREA, ents[0].getReceivingArea());
            p.set(LstAreaHistoryDASCHParams.UPDATE_RECEIVING_AREA, ents[0].getUpdateReceivingArea());
            
        }
        else if(Part11ListBoxDefine.DBTYPE_IMP.equals(_dbtype)) 
        {
            AreaHistoryImp[] ents = (AreaHistoryImp[])_finder.getEntities(1);
            
            // conver Entity to Param object
            p.set(LstAreaHistoryDASCHParams.LOGDATE_DAY, ents[0].getLogDate());
            p.set(LstAreaHistoryDASCHParams.LOGDATE_TIME, ents[0].getLogDate());
            p.set(LstAreaHistoryDASCHParams.UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ents[0].getUpdateKind()));
            p.set(LstAreaHistoryDASCHParams.USER_ID, ents[0].getUserId());
            p.set(LstAreaHistoryDASCHParams.USER_NAME, ents[0].getUserName());
            p.set(LstAreaHistoryDASCHParams.IP_ADRESS, ents[0].getIpAddress());
            p.set(LstAreaHistoryDASCHParams.TERMINAL_NAME, ents[0].getTerminalName());
            p.set(LstAreaHistoryDASCHParams.AREA_NO, ents[0].getAreaNo());
            p.set(LstAreaHistoryDASCHParams.AREA_NAME, ents[0].getAreaName());
            p.set(LstAreaHistoryDASCHParams.UPDATE_AREA_NAME, ents[0].getUpdateAreaName());
            if (!StringUtil.isBlank(ents[0].getTemporaryAreaType()))
            {
                p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ents[0].getTemporaryAreaType()));
            }
            if (!StringUtil.isBlank(ents[0].getUpdateTempAreaType()))
            {
                p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ents[0].getUpdateTempAreaType()));
            }
            p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA, ents[0].getTemporaryArea());
            p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA, ents[0].getTemporaryArea());
            if (!StringUtil.isBlank(ents[0].getVacantSearchType()))
            {
                p.set(LstAreaHistoryDASCHParams.VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ents[0].getVacantSearchType()));
            }
            if (!StringUtil.isBlank(ents[0].getUpdateVacantSearchType()))
            {
                p.set(LstAreaHistoryDASCHParams.UPDATE_VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ents[0].getUpdateVacantSearchType()));
            }
            p.set(LstAreaHistoryDASCHParams.RECEIVING_AREA, ents[0].getReceivingArea());
            p.set(LstAreaHistoryDASCHParams.UPDATE_RECEIVING_AREA, ents[0].getUpdateReceivingArea());
        }

        // return Param object
        return p;
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        // TODO : Implement for export or listcell
        //throw new ScheduleException("This method is not implemented.");
        DatabaseHandler handler = null;
        if (Part11ListBoxDefine.DBTYPE_DB.equals(p.getString(LstAreaHistoryDASCHParams.TABLE_NAME)))
        {
            // エリアマスタ更新履歴用
            handler = new AreaHistoryHandler(getConnection());
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(p.getString(LstAreaHistoryDASCHParams.TABLE_NAME)))
        {
            // 取込用エリアマスタ更新履歴用
            handler = new AreaHistoryImpHandler(getConnection());
        }

        // find count
        _total = handler.count(createSearchKey(p, false));

//        if(_total == 0) 
//        {
//            // 6003011=対象データはありませんでした。
//            setMessage("6003011");
//        }
//        else if(_total > CommonParam.getIntParam("MAX_NUMBER_OF_DISP_LISTBOX")) 
//        {
//            // 6001023={0}件該当しました。表示可能件数を超えるため、先頭{1}件の情報を表示します。
//            setMessage(
//                    WmsMessageFormatter.format(6001023, WmsFormatter.getNumFormat(_total),
//                    WmsFormatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_DISP_LISTBOX"))));
//        }
//        else
//        {
//            // 6001022={0}件該当しました。
//            setMessage(WmsMessageFormatter.format(6001022, WmsFormatter.getNumFormat(_total)));
//        }
        
        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        
        if(Part11ListBoxDefine.DBTYPE_DB.equals(_dbtype)) 
        {
            AreaHistory[] ents = (AreaHistory[])_finder.getEntities(start, start + cnt);
            
            // conver Entity to Param object
            for (AreaHistory ent : ents)
            {
                start++;
                
                Params p = new Params();
                p.set(LstAreaHistoryDASCHParams.NO, start);
                p.set(LstAreaHistoryDASCHParams.LOGDATE_DAY, ent.getLogDate());
                p.set(LstAreaHistoryDASCHParams.LOGDATE_TIME, ent.getLogDate());
                p.set(LstAreaHistoryDASCHParams.UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ent.getUpdateKind()));
                p.set(LstAreaHistoryDASCHParams.USER_ID, ent.getUserId());
                p.set(LstAreaHistoryDASCHParams.USER_NAME, ent.getUserName());
                p.set(LstAreaHistoryDASCHParams.IP_ADRESS, ent.getIpAddress());
                p.set(LstAreaHistoryDASCHParams.TERMINAL_NAME, ent.getTerminalName());
                p.set(LstAreaHistoryDASCHParams.AREA_NO, ent.getAreaNo());
                p.set(LstAreaHistoryDASCHParams.AREA_NAME, ent.getAreaName());
                p.set(LstAreaHistoryDASCHParams.UPDATE_AREA_NAME, ent.getUpdateAreaName());
                if (!StringUtil.isBlank(ent.getTemporaryAreaType()))
                {
                    p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ent.getTemporaryAreaType()));
                }
                if (!StringUtil.isBlank(ent.getUpdateTempAreaType()))
                {
                    p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ent.getUpdateTempAreaType()));
                }
                p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA, ent.getTemporaryArea());
                p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA, ent.getUpdateTempArea());
                if (!StringUtil.isBlank(ent.getVacantSearchType()))
                {
                    p.set(LstAreaHistoryDASCHParams.VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ent.getVacantSearchType()));
                }
                if (!StringUtil.isBlank(ent.getUpdateVacantSearchType()))
                {
                    p.set(LstAreaHistoryDASCHParams.UPDATE_VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ent.getUpdateVacantSearchType()));
                }
                p.set(LstAreaHistoryDASCHParams.RECEIVING_AREA, ent.getReceivingArea());
                p.set(LstAreaHistoryDASCHParams.UPDATE_RECEIVING_AREA, ent.getUpdateReceivingArea());
                
                params.add(p);
                
            }
        }
        else if(Part11ListBoxDefine.DBTYPE_IMP.equals(_dbtype))
        {
            AreaHistoryImp[] ents = (AreaHistoryImp[])_finder.getEntities(start, start + cnt);
            
            // conver Entity to Param object
            for (AreaHistoryImp ent : ents)
            {
                start++;
                
                Params p = new Params();
                p.set(LstAreaHistoryDASCHParams.NO, start);
                p.set(LstAreaHistoryDASCHParams.LOGDATE_DAY, ent.getLogDate());
                p.set(LstAreaHistoryDASCHParams.LOGDATE_TIME, ent.getLogDate());
                p.set(LstAreaHistoryDASCHParams.UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ent.getUpdateKind()));
                p.set(LstAreaHistoryDASCHParams.USER_ID, ent.getUserId());
                p.set(LstAreaHistoryDASCHParams.USER_NAME, ent.getUserName());
                p.set(LstAreaHistoryDASCHParams.IP_ADRESS, ent.getIpAddress());
                p.set(LstAreaHistoryDASCHParams.TERMINAL_NAME, ent.getTerminalName());
                p.set(LstAreaHistoryDASCHParams.AREA_NO, ent.getAreaNo());
                p.set(LstAreaHistoryDASCHParams.AREA_NAME, ent.getAreaName());
                p.set(LstAreaHistoryDASCHParams.UPDATE_AREA_NAME, ent.getUpdateAreaName());
                if (!StringUtil.isBlank(ent.getTemporaryAreaType()))
                {
                    p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ent.getTemporaryAreaType()));
                }
                if (!StringUtil.isBlank(ent.getUpdateTempAreaType()))
                {
                    p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA_TYPE, DisplayText.getText("WAREHOUSE_TEMPORARYAREATYPE_" + ent.getUpdateTempAreaType()));
                }
                p.set(LstAreaHistoryDASCHParams.TEMPORARY_AREA, ent.getTemporaryArea());
                p.set(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA, ent.getUpdateTempArea());
                if (!StringUtil.isBlank(ent.getVacantSearchType()))
                {
                    p.set(LstAreaHistoryDASCHParams.VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ent.getVacantSearchType()));
                }
                if (!StringUtil.isBlank(ent.getUpdateVacantSearchType()))
                {
                    p.set(LstAreaHistoryDASCHParams.UPDATE_VACANT_SEARCH_TYPE, DisplayText.getText("WAREHOUSE_VACANTSEARCHTYPE_" + ent.getUpdateVacantSearchType()));
                }
                p.set(LstAreaHistoryDASCHParams.RECEIVING_AREA, ent.getReceivingArea());
                p.set(LstAreaHistoryDASCHParams.UPDATE_RECEIVING_AREA, ent.getUpdateReceivingArea());
                
                params.add(p);
                
            }
        }

        return params;
    }

    
    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     */
    private SearchKey createSearchKey(Params param, boolean isSet) throws CommonException
    {
        // 検索日時の補完処理
        Date[] tmp =
                WmsFormatter.getFromTo(WmsFormatter.toDate(param.getString(LstAreaHistoryDASCHParams.DISPFROMDAY_KEY)),
                        WmsFormatter.toTime(param.getString(LstAreaHistoryDASCHParams.DISPFROMTIME_KEY)),
                        WmsFormatter.toDate(param.getString(LstAreaHistoryDASCHParams.DISPTODAY_KEY)),
                        WmsFormatter.toTime(param.getString(LstAreaHistoryDASCHParams.DISPTOTIME_KEY)));
        Date from = tmp[0];
        Date to = tmp[1];
        
        if (Part11ListBoxDefine.DBTYPE_DB.equals(param.getString(LstAreaHistoryDASCHParams.TABLE_NAME)))
        {
            // エリアマスタ更新履歴用
            AreaHistorySearchKey key = new AreaHistorySearchKey();

            // 検索条件、集約条件をセットする
            // where, group by
            // 開始出力日
            if (!StringUtil.isBlank(from))
            {
                key.setLogDate(from, ">=");
            }
            // 終了出力日
            if (!StringUtil.isBlank(to))
            {
                key.setLogDate(to, "<=");
            }
            // ユーザID
            if (!StringUtil.isBlank(param.getString(LstAreaHistoryDASCHParams.USERID_KEY)))
            {
                key.setUserId(param.getString(LstAreaHistoryDASCHParams.USERID_KEY));
            }
            // DS番号
            if (!StringUtil.isBlank(param.getString(LstAreaHistoryDASCHParams.DSNUMBER_KEY)))
            {
                key.setDsNo(param.getString(LstAreaHistoryDASCHParams.DSNUMBER_KEY));
            }
        
            if (isSet)
            {
                // OrderByや、collect項目を記載する
                // 読込み順序指定
                // 出力日時の昇順
                key.setLogDateOrder(true);
            }

            return key;
        
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(param.getString(LstAreaHistoryDASCHParams.TABLE_NAME)))
        {
            // 取込用エリアマスタ更新履歴用
            AreaHistoryImpSearchKey key = new AreaHistoryImpSearchKey();
            
            // 検索条件、集約条件をセットする
            // where, group by
            // 開始出力日
            if (!StringUtil.isBlank(from))
            {
                key.setLogDate(from, ">=");
            }
            // 終了出力日
            if (!StringUtil.isBlank(to))
            {
                key.setLogDate(to, "<=");
            }
            // ユーザID
            if (!StringUtil.isBlank(param.getString(LstAreaHistoryDASCHParams.USERID_KEY)))
            {
                key.setUserId(param.getString(LstAreaHistoryDASCHParams.USERID_KEY));
            }
            // DS番号
            if (!StringUtil.isBlank(param.getString(LstAreaHistoryDASCHParams.DSNUMBER_KEY)))
            {
                key.setDsNo(param.getString(LstAreaHistoryDASCHParams.DSNUMBER_KEY));
            }
        
            if (isSet)
            {
                // OrderByや、collect項目を記載する
                // 読込み順序指定
                // 出力日時の昇順
                key.setLogDateOrder(true);
            }

            return key;
        }
        else 
        {
            // DBTypeがセットされているはずなので、ここは通らないはず
            throw new ScheduleException();
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
