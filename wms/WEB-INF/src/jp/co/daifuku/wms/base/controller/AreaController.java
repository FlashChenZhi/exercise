// $Id: AreaController.java 7655 2010-03-17 09:46:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.AreaHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * エリアマスタ情報を操作するためのコントロールクラスです。
 *
 * @version $Revision: 7655 $, $Date: 2010-03-17 18:46:05 +0900 (水, 17 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class AreaController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** 仮置在庫作成区分 */
    public enum TEMPORARY_AREA_TYPE
    {
        /** 全ての在庫を作成する */
        CREATE_TMP_ANY(SystemDefine.TEMPORARY_AREA_TYPE_ALL),
        /** 作成しない */
        CREATE_TMP_NONE(SystemDefine.TEMPORARY_AREA_TYPE_NONE), ;

        /**
         * パラメータの仮置在庫作成区分を保持します。
         * @param type セットする仮置在庫作成区分
         */
        TEMPORARY_AREA_TYPE(String type)
        {
            _typeValue = type;
        }

        private String _typeValue;

        /**
         * 仮置在庫作成区分を返します。
         *
         * @return 仮置在庫作成区分
         */
        String getTypeValue()
        {
            return _typeValue;
        }
    }

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private static Map<String, Area> $areaMap = null;

    private static volatile Date $loadTime = new Date();

    /** キャッシュ有効時間 (msec) */
    private static final long CACHE_MSEC = 5000L;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * コンストラクタ内で、データベースのエリアマスタ情報を内部に読み込みます。<br>
     * 読み込み処理は、JVM起動後一度だけ行われるため、
     * 最新の情報を取得する場合は、read()を呼び出してください。
     *
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public AreaController(Connection conn, Class caller) throws ReadWriteException
    {
        super(conn, caller);
        if ($areaMap == null)
        {
            read();
        }
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 棚表示形式(フォーマットスタイル)を取得します。
     *
     * @param areaNo エリアNo
     * @return 棚表示形式
     */
    public String getLocationStyle(String areaNo)
    {
        Area area = getAreaMap().get(areaNo);

        // if not found the area, return null
        if (null == area)
        {
            return null;
        }
        return area.getLocationStyle();
    }

    /**
     * エリアNo.から倉庫ステーションNo.を取得します。
     * @param areaNo エリアNo.
     * @return 倉庫ステーションNo.
     */
    public String getWhStationNo(String areaNo)
    {
        Area area = getAreaMap().get(areaNo);

        // if not found the area, return null
        if (null == area)
        {
            return null;
        }
        return area.getWhstationNo();
    }

    /**
     * Shelf形式の棚ををDnStock用に変換します。<br>
     * AS/RSでの形式 (WH BANK BAY LEVEL SUB) からDnStock用に
     * 形式をフォーマットします。<br>
     *
     * @param asrsLocation AS/RS形式の棚No.("101001001000")
     * @return スケジュール形式の棚No.("10101")<br>
     * フォーマット対象の棚No.の長さが合わないなど、変換できなかったときは
     * AS/RS形式の棚No.がそのまま返されます。
     */
    public String toParamLocation(String asrsLocation)
    {
        if (StringUtil.isBlank(asrsLocation))
        {
            return asrsLocation;
        }

        try
        {
            // 格納区分からエリアを取得する
            String whno = String.valueOf(asrsLocation.charAt(0));

            Area tarea = getAreaWithKey(whno, WareHouse.WAREHOUSE_NO);

            LocationNumber locNum = new LocationNumber(tarea);
            locNum.parseAsrs(asrsLocation);

            return locNum.formatParam();

        }
        catch (RuntimeException e)
        {
            return asrsLocation;
        }
        catch (Exception e)
        {
            return asrsLocation;
        }
    }

    /**
     * DnStock形式の棚をShelf形式の棚No.に変換します。<br>
     * DnStock用からAS/RSでの形式 (WH BANK BAY LEVEL SUB)に
     * 形式をフォーマットします。<br>
     *
     * @param areaNo エリアNo.
     * @param paramLocation スケジュール形式の棚No.("10101")
     * @return AS/RS形式の棚No.("101001001000")<br>
     * フォーマット対象の棚No.の長さが合わないなど、変換できなかったときは
     * スケジュール形式の棚No.がそのまま返されます。
     *
     * @throws ScheduleException 該当する倉庫がなかったときスローされます。
     * @throws NoPrimaryException 該当する倉庫が複数存在するときスローされます。
     */
    public String toAsrsLocation(String areaNo, String paramLocation)
            throws ScheduleException,
                NoPrimaryException
    {
        if (StringUtil.isBlank(areaNo) || StringUtil.isBlank(paramLocation))
        {
            return paramLocation;
        }
        Area tarea = getAreaWithKey(areaNo, Area.AREA_NO);

        // 倉庫とフォーマットスタイルをセット
        LocationNumber locNum = new LocationNumber(tarea);

        try
        {
            locNum.parseParam(paramLocation);
            return locNum.formatAsrs();
        }
        catch (RuntimeException e)
        {
            return paramLocation;
        }
        catch (Exception e)
        {
            return paramLocation;
        }
    }

    /**
     * 仮置きエリアを取得します。<br>
     *
     * @param areaNo エリアNo.
     * @return 仮置きエリアNo.
     * @throws ScheduleException エリアNo.が存在しないか、仮置きエリアが設定されていないときスローされます
     */
    public String getTemporaryArea(String areaNo)
            throws ScheduleException
    {
        Area area = getAreaMap().get(areaNo);

        // if not found the area, it is inconsistency.
        if (null == area)
        {
            throw new ScheduleException();
        }

        // check tomporary storage type
        String tmpType = area.getTemporaryAreaType();
        if (TEMPORARY_AREA_TYPE_NONE.equals(tmpType))
        {
            // it has NO temporary area
            return null;
        }

        // check temporary area for this area.
        String tempAreaNo = area.getTemporaryArea();
        if (StringUtil.isBlank(tempAreaNo))
        {
            throw new ScheduleException();
        }

        // get temoprary area
        Area tempArea = getAreaMap().get(tempAreaNo);
        if (null != tempArea && AREA_TYPE_TEMPORARY.equals(tempArea.getAreaType()))
        {
            // find temporary area.
            return tempArea.getAreaNo();
        }

        // area define is inconsistency.
        throw new ScheduleException();
    }

    /**
     * 移動中エリアを取得します。
     *
     * @return 移動中エリア
     * @throws ScheduleException 移動中エリアが見つからなかったときスローされます
     */
    public String getMovingArea()
            throws ScheduleException
    {
        for (Area area : getAreaMap().values())
        {
            String areaType = area.getAreaType();
            if (AREA_TYPE_MOVING.equals(areaType))
            {
                return area.getAreaNo();
            }
        }
        // no moving area found on master.
        throw new ScheduleException();
    }

    /**
     * 倉庫No.(ステーションNo.)からエリアNo.を取得します。
     *
     * @param whStation 倉庫No.(ステーションNo.)
     * @return エリアNo.
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public String getAreaNoOfWarehouse(String whStation)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(whStation, Area.WHSTATION_NO);
        return tarea.getAreaNo();
    }

    /**
     * エリアの仮置在庫作成区分を取得します。
     *
     * @param areaNo エリアNo.
     * @return 仮置在庫作成区分
     * @throws ScheduleException 該当のエリアが見つからない (定義エラー)
     */
    public TEMPORARY_AREA_TYPE getTemporaryAreaType(String areaNo)
            throws ScheduleException
    {
        Area area = getAreaMap().get(areaNo);
        if (null == area)
        {
            throw new ScheduleException("No area found : " + areaNo);
        }

        String tempType = area.getTemporaryAreaType();

        for (TEMPORARY_AREA_TYPE type : TEMPORARY_AREA_TYPE.values())
        {
            if (type.getTypeValue().equals(tempType))
            {
                return type;
            }
        }
        return null;
    }

    /**
     * エリア名称を取得します。<BR>
     * 該当エリアが存在しなかった場合は0バイトの文字列を返します。<BR>
     *
     * @param areaNo エリアNo.
     * @return エリア名称
     */
    public String getAreaName(String areaNo)
    {
        // CMB-W0023=全エリア
        if (WmsParam.ALL_AREA_NO.equals(areaNo))
        {
            return DisplayText.getText("CMB-W0023");
        }

        Area area = getAreaMap().get(areaNo);
        if (null == area)
        {
            return "";
        }
        return area.getAreaName();

    }

    /**
     * 倉庫No.(ステーションNo.)から空棚検索方法を取得します。<BR>
     * 該当エリアが存在しなかった場合は0バイトの文字列を返します。<BR>
     *
     * @param whStation 倉庫No.(ステーションNo.)
     * @return 空棚検索方法
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public String getVacantSearchType(String whStation)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(whStation, Area.WHSTATION_NO);
        return tarea.getVacantSearchType();
    }

    /**
     * エリアNo.から最大混載数を取得します。
     *
     * @param areaNo エリアNo.
     * @return 最大混載数
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public int getNumMixedOfWarehouse(String areaNo)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(areaNo, Area.AREA_NO);
        BigDecimal numMix = tarea.getBigDecimal(WareHouse.MAX_MIXEDPALLET);
        if (null == numMix)
        {
            throw new ScheduleException();
        }
        return numMix.intValue();
    }

    /**
     * 倉庫No.(ステーションNo.)から自動倉庫運用種別を取得します。
     *
     * @param whStation 倉庫No.(ステーションNo.)
     * @return 自動倉庫運用種別
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public String getEmploymentTypeOfWarehouse(String whStation)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(whStation, Area.WHSTATION_NO);
        return (String)tarea.getValue(WareHouse.EMPLOYMENT_TYPE);
    }

    /**
     * 倉庫No.(ステーションNo.)からフリーアロケーション運用区分を取得します。
     *
     * @param whStation 倉庫No.(ステーションNo.)
     * @return フリーアロケーション運用区分
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public String getFreeAllocationOfWarehouse(String whStation)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(whStation, Area.WHSTATION_NO);
        return (String)tarea.getValue(WareHouse.FREE_ALLOCATION_TYPE);
    }

    /**
     * エリアNo.からエリア種別を取得します。
     *
     * @param areaNo エリアNo.
     * @return エリア種別
     * @throws ScheduleException 該当エリア種別が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public String getAreaType(String areaNo)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(areaNo, Area.AREA_NO);
        return (String)tarea.getValue(Area.AREA_TYPE);
    }

    /**
     * エリアNo.から棚管理方式を取得します。
     *
     * @param areaNo エリアNo.
     * @return 棚管理方式
     * @throws ScheduleException 該当棚管理方式が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     */
    public String getLocationType(String areaNo)
            throws ScheduleException,
                NoPrimaryException
    {
        Area tarea = getAreaWithKey(areaNo, Area.AREA_NO);
        return (String)tarea.getValue(Area.LOCATION_TYPE);
    }

    /**
     * エリアマスタ情報をデータベースから読み込みます。<br>
     * 再読込を行いたいとき、このメソッドを呼び出してください。<br>
     * 読み込んだエリアマスタ情報は、read()を行わない限り内部で
     * キャッシュされています。
     *
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public synchronized void read()
            throws ReadWriteException
    {
        setAreaMap(readArea());
    }

    /**
     * 棚形式をスケジュールパラメータ用(DnStock用)に変換します。<br>
     * 画面表示での形式からスケジュールパラメータ用に形式をフォーマットします。<br>
     * <BR>
     * 例) 1-01-01 -> 10101 (指定エリアのフォーマットスタイルが"9-99-99"の場合)<BR>
     * 注意:パラメータで渡す棚No.と、指定エリアのフォーマットスタイルのハイフンの位置が合っている事。
     *
     * @param areaNo エリアNo
     * @param dispLocation 画面表示用フォーマット済み棚No.(01-001-001)
     * @return スケジュールパラメータ形式の棚No.(01001001)<BR>
     * エリアNoが見つからなかった場合や、桁数が異なる場合は文字列をそのまま返します。
     * @throws ScheduleException フォーマットできなかった時スローされます。
     * @throws LocationFormatException フォーマットできなかった時スローされます。
     */
    public String toParamLocation(String areaNo, String dispLocation)
            throws ScheduleException,
                LocationFormatException
    {
        String format = getLocationStyle(areaNo);

        //エリアNoが見つからなかった場合
        if (format == null)
        {
            return dispLocation;
        }
        return WmsFormatter.toParamLocation(dispLocation, format);
    }

    /**
     * 棚形式を画面表示用に変換します。<br>
     * スケジュールパラメータでの形式 (DnStock用) から画面表示用に
     * 形式をフォーマットします。<br>
     * <BR>
     * 例) 01001001 -> 1-01-01 (指定エリアのフォーマットスタイルが"9-99-99"の場合)
     *
     * @param areaNo エリアNo.
     * @param paramLocation スケジュールパラメータ形式の棚No.
     * @return テキストにセットする形式の棚No<br>
     * エリアNoが見つからなかった場合は文字列をそのまま返します。
     * @throws ScheduleException フォーマットできなかった時スローされます。
     */
    public String toDispLocation(String areaNo, String paramLocation)
            throws ScheduleException
    {
        //棚フォーマットを取得する
        String format = getLocationStyle(areaNo);

        //エリアNoが見つからなかった場合
        if (format == null)
        {
            return paramLocation;
        }
        return WmsFormatter.toDispLocation(paramLocation, format);
    }


    /**
     * 棚形式を画面表示用に変換します。<br>
     * スケジュールパラメータでの形式 (Part11用) から画面表示用に
     * 形式をフォーマットします。<br>
     *
     * @param paramLocation スケジュールパラメータ形式の棚No.
     * @param format 棚表示形式
     * @return テキストにセットする形式の棚No<br>
     * @throws ScheduleException フォーマットできなかった時スローされます。
     */
    public String toDispLocationLog(String paramLocation, String format)
            throws ScheduleException
    {
        //フォーマットが見つからなかった場合
        if (format == null)
        {
            return paramLocation;
        }
        return WmsFormatter.toDispLocation(paramLocation, format);
    }

    /**
     * 指定のエリアに合致した、棚形式になっているかチェックします。<br>
     * 桁数の過不足や、英字が混在する場合は、例外をスローします。<br>
     * (主に、RFTで入力された棚をチェックする為に使用します。)<br>
     * <BR>
     * 例) 指定エリアの棚フォーマットが99-999-999の場合、01001001･･･OK、0100203･･･NG、010020ab･･･NG
     *
     * @param areaNo エリアNo.
     * @param location チェック対象の棚No.(ハイフンなし)
     * @throws ScheduleException 棚変換に失敗した場合に通知されます
     * @throws OperatorException エリアマスタに該当しない場合(ERR_NO_AREA_FOUND)<BR>
     *                            棚形式に合致しない場合(ERR_ILLEGAL_LOCATION_FORMAT)
     */
    public void checkLocateFormat(String areaNo, String location)
            throws ScheduleException,
                OperatorException
    {
        //棚フォーマットを取得する
        String format = getLocationStyle(areaNo);

        //エリアNoが見つからなかった場合
        if (null == format)
        {
            throw new OperatorException(OperatorException.ERR_NO_AREA_FOUND);
        }
        
        // 棚フォーマットをパーツに分ける
        ArrayList<String> formatSeg = new ArrayList<String>();
        StringBuilder buff = new StringBuilder();
        char[] formatChars = format.toCharArray();
        
        for (int i = 0; i < formatChars.length; i++)
        {
            // 数値・文字・スペース以外だった場合
            if (!(String.valueOf(formatChars[i]).matches("^" + String.valueOf(LocationNumber.LOCATION_FORMAT_PATTERN_NUMBER) + "+$") || 
                    String.valueOf(formatChars[i]).matches("^" + String.valueOf(LocationNumber.LOCATION_FORMAT_PATTERN_CHAR) + "+$")))
            {
                // パーツの格納/バッファクリア
                formatSeg.add(String.valueOf(buff));
                buff = new StringBuilder();
            }
            else
            {
                try
                {
                    buff.append(format.charAt(i));
                }
                catch (IndexOutOfBoundsException e)
                {
                    throw new OperatorException(OperatorException.ERR_ILLEGAL_LOCATION_FORMAT);
                }
            }
        }
        if (buff.length() != 0)
        {
            // 最後のパーツを格納します。
            formatSeg.add(String.valueOf(buff));
            buff = new StringBuilder();
        }
        
        // フォーマットが区切り文字なしかつ文字のみの場合
        if (formatSeg.size() == 1 && formatSeg.get(0).matches("^" + String.valueOf(LocationNumber.LOCATION_FORMAT_PATTERN_CHAR) + "+$"))
        {
            // 入力値チェック(桁数が足りなくてもOKとする)
            if (!location.matches(LocationNumber.REGEX_STRING) || location.length() > formatSeg.get(0).length())
            {
                throw new OperatorException(OperatorException.ERR_ILLEGAL_LOCATION_FORMAT);
            }
        }
        else
        {
            // それ以外は桁数が一致すること
            int len = 0;
            for (int i = 0; i < formatSeg.size(); i++)
            {
                len += formatSeg.get(i).length();
            }
            if (len != location.length())
            {
                throw new OperatorException(OperatorException.ERR_ILLEGAL_LOCATION_FORMAT);
            }
            
            // パーツごとに比較していく
            int idx = 0;
            String input_str = "";
            String format_str = "";
            for (int i = 0; i < formatSeg.size(); i++)
            {
                // 入力値から比較パーツを作成
                format_str = formatSeg.get(i);
                input_str = location.substring(idx, idx + format_str.length());
                idx += format_str.length();
                
                // フォーマットが数値のとき
                if (format_str.matches("^" + String.valueOf(LocationNumber.LOCATION_FORMAT_PATTERN_NUMBER) + "+$"))
                {
                    if (!input_str.matches(LocationNumber.REGEX_NUMBER))
                    {
                        throw new OperatorException(OperatorException.ERR_ILLEGAL_LOCATION_FORMAT);
                    }
                }
                // フォーマットが文字のとき
                else if (format_str.matches("^" + String.valueOf(LocationNumber.LOCATION_FORMAT_PATTERN_CHAR) + "+$"))
                {
                    if (!input_str.matches(LocationNumber.REGEX_STRING))
                    {
                        throw new OperatorException(OperatorException.ERR_ILLEGAL_LOCATION_FORMAT);
                    }
                }
                else
                {
                    throw new OperatorException(OperatorException.ERR_ILLEGAL_LOCATION_FORMAT);
                }
            }
        }
        return;
    }

    /**
     * エリアマスタ更新履歴情報登録処理を行います。
     *
     * @param area 対象マスタ情報
     * @param operationKind 操作区分
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException エリアマスタ更新履歴情報登録済み
     */
    public void insertHistory(Area area, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == area)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        AreaHistoryHandler histch = new AreaHistoryHandler(getConnection());
        AreaHistory hist = new AreaHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // システム管理区分
        hist.setManagementType(area.getManagementType());
        // エリアNo
        hist.setAreaNo(area.getAreaNo());
        // エリア種別
        hist.setAreaType(area.getAreaType());
        // 棚管理方法
        hist.setLocationType(area.getLocationType());
        // 棚表示形式
        hist.setLocationStyle(area.getLocationStyle());
        if (SystemDefine.UPDATE_KIND_REGIST.equals(operationKind))
        {
            // エリア名称
            hist.setUpdateAreaName(area.getAreaName());
            // 修正後仮置在庫作成区分
            hist.setUpdateTempAreaType(area.getTemporaryAreaType());
            // 修正後移動先仮置エリア
            hist.setUpdateTempArea(area.getTemporaryArea());
            // 修正後空棚検索方法
            hist.setUpdateVacantSearchType(area.getVacantSearchType());
            // 修正後入荷エリア
            hist.setUpdateReceivingArea(area.getReceivingArea());
        }
        else
        {
            // エリア名称
            hist.setAreaName(area.getAreaName());
            // 仮置在庫作成区分
            hist.setTemporaryAreaType(area.getTemporaryAreaType());
            // 移動先仮置エリア
            hist.setTemporaryArea(area.getTemporaryArea());
            // 空棚検索方法
            hist.setVacantSearchType(area.getVacantSearchType());
            // 入荷エリア
            hist.setReceivingArea(area.getReceivingArea());
        }
        // 倉庫ステーションNo
        hist.setWhstationNo(area.getWhstationNo());

        histch.create(hist);
    }

    /**
     * エリアマスタ更新履歴情報登録処理を行います。
     *
     * @param oldarea 修正前マスタ情報
     * @param newarea 修正後マスタ情報
     * @param operationKind 操作区分
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException エリアマスタ更新履歴情報登録済み
     */
    public void insertHistory(Area oldarea, Area newarea, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == oldarea)
        {
            throw new ReadWriteException();
        }
        if (null == newarea)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        AreaHistoryHandler histch = new AreaHistoryHandler(getConnection());
        AreaHistory hist = new AreaHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // システム管理区分
        hist.setManagementType(oldarea.getManagementType());
        // エリアNo
        hist.setAreaNo(oldarea.getAreaNo());
        // エリア名称(前)
        hist.setAreaName(oldarea.getAreaName());
        // エリア名称(後)
        hist.setUpdateAreaName(newarea.getAreaName());
        // エリア種別
        hist.setAreaType(oldarea.getAreaType());
        // 棚管理方法
        hist.setLocationType(oldarea.getLocationType());
        // 棚表示形式
        hist.setLocationStyle(oldarea.getLocationStyle());
        // 仮置在庫作成区分
        hist.setTemporaryAreaType(oldarea.getTemporaryAreaType());
        // 移動先仮置エリア
        hist.setTemporaryArea(oldarea.getTemporaryArea());
        // 空棚検索方法
        hist.setVacantSearchType(oldarea.getVacantSearchType());
        // 入荷エリア
        hist.setReceivingArea(oldarea.getReceivingArea());
        // 倉庫ステーションNo
        hist.setWhstationNo(oldarea.getWhstationNo());
        // 修正後仮置在庫作成区分
        hist.setUpdateTempAreaType(newarea.getTemporaryAreaType());
        // 修正後移動先仮置エリア
        hist.setUpdateTempArea(newarea.getTemporaryArea());
        // 修正後空棚検索方法
        hist.setUpdateVacantSearchType(newarea.getVacantSearchType());
        // 修正後入荷エリア
        hist.setUpdateReceivingArea(newarea.getReceivingArea());

        histch.create(hist);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * エリアマップを返します。
     * @return エリアマップ
     */
    protected Map<String, Area> getAreaMap()
    {
        try
        {
            if (isExpire())
            {
                read();
            }
        }
        catch (ReadWriteException e)
        {
            // do nothing
        }
        return $areaMap;
    }

    /**
     * エリアマップを設定します。
     * @param areaMap エリアマップ
     */
    protected static void setAreaMap(Map<String, Area> areaMap)
    {
        synchronized ($loadTime)
        {
            $loadTime = new Date();
            $areaMap = areaMap;
        }
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * キャッシュ期限を返します。(1分)
     * @return 期限切れのときtrue.
     */
    protected boolean isExpire()
    {
        long exptime = new Date().getTime() - $loadTime.getTime();

        return (exptime > CACHE_MSEC);
    }

    /**
     * エリアを取得します。
     *
     * @param keyValue 検索する文字列
     * @param keyField 比較対象フィールド
     * @return エリア
     * @throws ScheduleException 該当エリアが見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアが複数存在するときスローされます。
     */
    protected Area getAreaWithKey(String keyValue, FieldName keyField)
            throws ScheduleException,
                NoPrimaryException
    {
        // guard from null pointer exception.
        if (null == keyValue)
        {
            throw new ScheduleException();
        }

        // collect area number for the warehouse
        Set<Area> areaSet = new LinkedHashSet<Area>();
        for (Area area : getAreaMap().values())
        {
            Object whSt = area.getValue(keyField);
            if (keyValue.equals(whSt))
            {
                areaSet.add(area);
            }
        }

        if (areaSet.isEmpty())
        {
            // no area found on master.
            throw new ScheduleException();
        }
        else if (1 < areaSet.size())
        {
            // too many area found for warehouse
            throw new NoPrimaryException();
        }
        // return area
        return areaSet.iterator().next();
    }

    /**
     * エリアマスタを読み込んで、エリアNo.をキーとしたマップを
     * 生成して返します。
     *
     * @return areaMap
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected Map<String, Area> readArea()
            throws ReadWriteException
    {
        AreaHandler ah = new AreaHandler(getConnection());
        AreaSearchKey key = new AreaSearchKey();

        key.setCollect(new FieldName(Area.STORE_NAME, FieldName.ALL_FIELDS));

        boolean hasAsrs;

        try
        {
            WarenaviSystemController navi = new WarenaviSystemController(getConnection(), getClass());

            hasAsrs = navi.hasAsrsPack();
        }
        catch (ScheduleException e)
        {
            return null;
        }

        if (hasAsrs)
        {
            key.setCollect(WareHouse.WAREHOUSE_NO);
            key.setCollect(WareHouse.WAREHOUSE_NAME);
//            key.setCollect(WareHouse.WAREHOUSE_TYPE);
            key.setCollect(WareHouse.MAX_MIXEDPALLET);
            key.setCollect(WareHouse.EMPLOYMENT_TYPE);
            key.setCollect(WareHouse.FREE_ALLOCATION_TYPE);

            key.setJoin(Area.WHSTATION_NO, "", WareHouse.STATION_NO, "(+)");
        }

        Area[] areas = (Area[])ah.find(key);
        Map<String, Area> areaMap = new HashMap<String, Area>();
        for (Area area : areas)
        {
            areaMap.put(area.getAreaNo(), area);
        }
        return areaMap;
    }

    /**
     * エリアマスタ情報をエリアNo.で検索してロックします。
     *
     * @param areaNo エリアNo
     * @return 該当するエリアマスタデータ
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Area[] lock(String areaNo)
            throws LockTimeOutException,
                ReadWriteException
    {
        AreaHandler ch = new AreaHandler(getConnection());
        AreaSearchKey key = new AreaSearchKey();
        key.setAreaNo(areaNo);

        Area[] ents = (Area[])super.retryLock(key, ch);
        if (ents == null)
        {
            // no such records found.
            return new Area[0];
        }
        return ents;
    }

    /**
     * エリアマスタ情報を作成します。
     *
     * @param src エリア情報(未指定の項目はテーブルに保存されません。)
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(Area src, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        AreaHandler ch = new AreaHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }
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
        return "$Id: AreaController.java 7655 2010-03-17 09:46:05Z kishimoto $";
    }
}
