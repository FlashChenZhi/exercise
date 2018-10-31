// $Id: PCTSystemInParameter.java 3407 2009-03-13 03:59:58Z ose $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * <CODE>PCTSystemInParameter</CODE>クラスは、システムパッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではシステムパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>PCTSystemInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     JANコード<BR>
 *     商品名称<BR>
 *     ロット入数<BR>
 *     ケース入数<BR>
 *     ケースITF<BR>
 *     ボール入数<BR>
 *     ボールITF<BR>
 *     発注単位<BR>
 *     ロケーションNo.1<BR>
 *     入数1<BR>
 *     ロケーションNo.2<BR>
 *     入数2<BR>
 *     ロケーションNo.3<BR>
 *     入数3<BR>
 *     ロケーションNo.4<BR>
 *     入数4<BR>
 *     賞味期限<BR>
 *     入荷限度日<BR>
 *     出荷限度日<BR>
 *     最新賞味期限<BR>
 *     最新製造日<BR>
 *     最新出庫日<BR>
 *     最新在庫<BR>
 *     最古在庫<BR>
 *     管理フラグ<BR>
 *     上限在庫数<BR>
 *     下限在庫数<BR>
 *     最終更新日時<BR>
 *     レベルA最終更新日時<BR>
 *     レベルC最終更新日時<BR>
 *     最終使用日時<BR>
 *     処理フラグ<BR>
 *     途中作業区分<BR>
 *     作業区分<BR>
 *     データ区分<BR>
 *     パッケージ選択<BR>
 *     取込ファイル数<BR>
 *     取込開始日時<BR>
 *     開始終了フラグ<BR>
 *     ホスト取消区分<BR>
 *     取込単位キー<BR>
 *     リストセル行No.<BR>
 *     出庫予定日<BR>
 *     伝票No.<BR>
 *     行No.<BR>
 *     作業枝番<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ロットNo.<BR>
 *     ケース入数<BR>
 *     予定数<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     出荷先分類コード<BR>
 *     得意先コード<BR>
 *     得意先名称<BR>
 *     オーダーNo.<BR>
 *     バッチNo.<BR>
 *     バッチSeqNo.<BR>
 *     出庫予定一意キー<BR>
 *     エリアNo.<BR>
 *     棚<BR>
 *     作業日<BR>
 *     作業日(to)<BR>
 *     ゾーンNo.<BR>
 *     ゾーン名称<BR>
 *     システム管理区分<BR>
 *     管理フラグ<BR>
 *     範囲指定フラグ<BR>
 *     ﾛｯﾄ/H<BR>
 *     ｵｰﾀﾞｰ/H<BR>
 *     行/H<BR>
 *     Aランク基準値<BR>
 *     Bランク基準値<BR>
 *     エリア名称<BR>
 *     棚表示形式<BR>
 *     登録日時<BR>
 *     登録処理名<BR>
 *     最終更新日時(Date)<BR>
 *     最終更新処理名<BR>
 *     基準日<BR>
 *     アイテム情報コメント<BR>
 *     オーダー情報コメント<BR>
 *     作業ランク設定方法<BR>
 *     重量誤差率初期値<BR>
 *     オーダー最大重量<BR>
 *     センター名<BR>
 *     ユーザーID(from)<BR>
 *     ユーザーID(to)<BR>
 *     ロールID<BR>
 *     検索対象テーブル<BR>
 *     ランク<BR>
 *     表示順<BR>
 * </DIR>
 * 
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 *
 * @version $Revision: 3407 $, $Date: 2009-03-13 12:59:58 +0900 (金, 13 3 2009) $
 * @author  $Author: ose $
 */
public class PCTSystemInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 検索対象テーブル : 荷主マスタ
     */
    public static final String SEARCH_TABLE_CONSIGNOR = "1";
    
    /**
     * 処理フラグ：設定
     */
    public static final String SETTING = "1";
    

    // Class variables -----------------------------------------------
    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * JANコード
     */
    private String _jan;

    /**
     * 商品名称
     */
    private String _itemName;

    /**
     * ロット入数
     */
    private int _lotEnteringQty;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

    /**
     * ボールITF
     */
    private String _bundleItf;

    /**
     * 発注単位
     */
    private int _unit;

    /**
     * ロケーションNo.1
     */
    private String _locationNo1;

    /**
     * 入数1
     */
    private int _enteringQty1;

    /**
     * ロケーションNo.2
     */
    private String _locationNo2;

    /**
     * 入数2
     */
    private int _enteringQty2;

    /**
     * ロケーションNo.3
     */
    private String _locationNo3;

    /**
     * 入数3
     */
    private int _enteringQty3;

    /**
     * ロケーションNo.4
     */
    private String _locationNo4;

    /**
     * 入数4
     */
    private int _enteringQty4;

    /**
     * 賞味期間
     */
    private String _useByPeriod;

    /**
     * 入荷限度日
     */
    private String _instockLimitDate;

    /**
     * 出荷限度日
     */
    private String _shippingLimitDate;

    /**
     * 最新賞味期限
     */
    private String _latestUseByDate;

    /**
     * 最新製造日
     */
    private String _latestManufacutureDate;

    /**
     * 最新出庫日
     */
    private String _latestRetrievalDate;

    /**
     * 最新在庫
     */
    private String _latestStock;

    /**
     * 最古在庫
     */
    private String _oldestStock;

    /**
     * 管理フラグ
     */
    private String _managementFlg;

    /**
     * 上限在庫数
     */
    private int _upperQty;

    /**
     * 下限在庫数
     */
    private int _lowerQty;

    /**
     * 最終更新日時
     */
    private Date _lastUpdateDate;

    /**
     * レベルAの最終更新日時
     */
    private Date _lastUpdateDateA;

    /**
     * レベルCの最終更新日時
     */
    private Date _lastUpdateDateC;

    /**
     * 最終使用日時
     */
    private String _lastUsedDate;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * 途中作業区分
     */
    private String _workOnTheWay;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * データ区分
     */
    private String _dataType;

    /**
     * パッケージ選択
     */
    private boolean _packageSelect;

    /**
     * 取込ファイル数
     */
    private int _loadFiles_count;

    /**
     * 取込開始日時
     */
    private java.util.Date _loadStartDate;

    /**
     * ホスト取消区分
     */
    private String _cancelFlag;

    /**
     * 取込単位キー
     */
    private String _loadUnitKey;

    /**
     * 出庫予定日
     */
    private String _retrievalPlanDay;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 伝票No.
     */
    private String _ticketNo;

    /**
     * 行No.
     */
    private int _lineNo;

    /**
     * 作業枝番
     */
    private int _branchNo;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * 出荷先分類コード
     */
    private String _customerCategory;

    /**
     * 得意先コード
     */
    private String _regularCustomerCode;

    /**
     * 得意先名称
     */
    private String _regularCustomerName;

    /**
     * オーダーNo.
     */
    private String _orderNo;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * バッチSeqNo.
     */
    private String _batchSeqNo;

    /**
     * 出庫予定一意キー
     */
    private String _planUkey;

    /**
     * 開始終了フラグ
     */
    private String _fromToFlag;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * 棚
     */
    private String _location;

    /**
     * 棚(to)
     */
    private String _toLocation;

    /**
     * 作業日
     */
    private String _workDate;

    /**
     * 作業日(to)
     */
    private String _toWorkDate;

    /**
     * ゾーンNo.
     */
    private String _zoneNo;

    /**
     * ゾーン名称
     */
    private String _zoneName;

    /**
     * ボタン区分
     */
    private String _buttonKubn;

    /**
     * PCT入荷予定データ格納フォルダ
     */
    private String _pctRetrievalLoadData_Folder;

    /**
     * PCT出庫予定データ格納フォルダ
     */
    private String _pctInstockLoadData_Folder;

    /**
     * PCT商品マスタデータ格納フォルダ
     */
    private String _pctItemLoadData_Folder;

    /**
     * PCT入荷予定データファイル名
     */
    private String _pctRetrievalLoadData_Filename;

    /**
     * PCT出庫予定データファイル名
     */
    private String _pctInstockLoadData_Filename;

    /**
     * PCT商品マスタデータファイル名
     */
    private String _pctItemLoadData_Filename;

    /**
     * PCT出庫実績格納フォルダ
     */
    private String _pctRetrievalReport_Folder;

    /**
     * PCT入荷実績格納フォルダ
     */
    private String _pctInstockReport_Folder;

    /**
     * PCT出庫実績格納ファイル名
     */
    private String _pctRetrievalReport_Filename;

    /**
     * PCT入荷実績格納ファイル名
     */
    private String _pctInstockReport_Filename;

    /**
     * PCT入荷実績格納単位
     */
    private String _pctInstockReport_Type;

    /**
     * PCT入荷実績_選択
     */
    private boolean _selectPctInstockReport;

    /**
     * PCT出庫実績_選択
     */
    private boolean _selectPctRetrievalReport;

    /**
     * PCT棚卸実績_選択
     */
    private boolean _selectPctInventoryReport;

    /**
     * PCT出庫実績単位
     */
    private String _pctRetrievalReport_Type;

    /**
     * PCT棚卸実績フォルダ名
     */
    private String _pctInventoryReport_Folder;

    /**
     * PCT棚卸実績ファイル名
     */
    private String _pctInventoryReport_Filename;

    /**
     * PCT出庫予定格納ファイル名
     */
    private String _pctRetrievalLoad_Filename;

    /**
     * PCT入庫予定格納ファイル名
     */
    private String _pctInstockLoad_Filename;

    /**
     * システム管理区分
     */
    private String _managementType;

    /**
     * 管理フラグ
     */
    private String _managementFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * ﾛｯﾄ/H
     */
    private double _lotPerHour;

    /**
     * ｵｰﾀﾞｰ/H
     */
    private double _orderPerHour;

    /**
     * 行/H
     */
    private double _itemPerHour;

    /**
     * Aランク基準値
     */
    private int _aRankStandardValue;

    /**
     * Bランク基準値
     */
    private int _bRankStandardValue;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * 棚表示形式
     */
    private String _locationStyle;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 登録処理名
     */
    private String _registPname;

    /**
     * 最終更新日時(Date)
     */
    private java.util.Date _lastUpdate;

    /**
     * 最終更新処理名
     */
    private String _lastUpdatePname;

    /**
     * 表示条件
     */
    private String _displayCondition;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 基準日
     */
    private String _useByDate;

    /**
     * アイテム情報コメント
     */
    private String _itemInfo;

    /**
     * オーダー情報コメント
     */
    private String _orderInfo;

    /**
     * 作業ランク設定方法
     */
    private String _workRankSetType;

    /**
     * 重量誤差率初期値
     */
    private int _weightErrorRateInit;

    /**
     * オーダー最大重量
     */
    private int _orderMaxWeight;

    /**
     * センター名
     */
    private String _centerName;

    /**
     * ユーザーID(from)
     */
    private String _fromUserId;

    /**
     * ユーザーID(to)
     */
    private String _toUserId;

    /**
     * ロールID
     */
    private String _roleId;

    /** 
     * 検索対象テーブル
     */
    private String _searchTable;

    /** 
     * ランク
     */
    private String _rank;

    /**
     * 表示順
     */
    private String _orderDisplay;
    
    /**
     * PCT商品マスタ取込フラグ
     */
    private String _itemLoad;


    /**
     * 未作業情報：削除する
     */
    public static final String UNSTART_DELETE = "0";

    /**
     * 未作業情報：作業情報を持ち越す、
     */
    public static final String UNSTART_HOLD = "1";

    /**
     * 未作業情報：作業情報を持ち越す、
     */
    public static final String PROCESS_FLAG_NON_PROCESS = "0";

    /**
     * 途中作業：キャンセル
     */
    public static final String RFT_WORK_CANSEL = "1";

    /**
     * 途中作業：確定
     */
    public static final String RFT_WORK_COMFIRM = "2";

    /**
     * 状態フラグ：作業中
     */
    public static final String STATUS_FLAG_EXIST_WORKING = "1";

    /**
     * 状態フラグ：作業完了
     */
    public static final String STATUS_FLAG_EXIST_WORKED = "2";

    /**
     * 状態フラグ：完了
     */
    public static final String STATUS_FLAG_COMPLETION = "4";

    /**
     * 状態フラグ：メンテ完了
     */
    public static final String STATUS_FLAG_MAINTENANCE_COMPLETION = "5";

    /**
     * 状態フラグ：削除
     */
    public static final String STATUS_FLAG_DELETE = "9";

    /**
     * ファイルパス最大文字数
     */
    public static final int FILE_PATH_MAXLENGTH = 255;

    /**
     * 作業区分 : 未作業
     */
    public static final String JOB_TYPE_UNSTART = "00";

    /**
     * 作業区分 : 入荷
     */
    public static final String JOB_TYPE_RECEVING = "01";

    /**
     * 作業区分 : 出荷
     */
    public static final String JOB_TYPE_SHIPPING = "05";

    /**
     * 作業ランク設定方法：手動
     */
    public static final String WORK_RANK_SET_TYPE_MANUAL = "0";

    /**
     * 作業ランク設定方法：自動
     */
    public static final String WORK_RANK_SET_TYPE_AUTO = "1";

    /**
     * ランクA基準値の下限値
     */
    public static final int RANK_A_LOWER_VALUE = 101;

    /**
     * ランクB基準値の上限値
     */
    public static final int RANK_B_UPPER_VALUE = 99;

    /**
     * リストセルに表示するランク一覧テーブル
     */
    public static final String[] RANK_TABLE = {
            SystemDefine.RANK_NO_A,
            SystemDefine.RANK_NO_B,
            SystemDefine.RANK_NO_C
    };

    /**
     * リストセルに表示するレベル一覧テーブル
     */
    public static final String[] LEVEL_TABLE = {
            SystemDefine.LEVEL_NO_A,
            SystemDefine.LEVEL_NO_B,
            SystemDefine.LEVEL_NO_C
    };

    /**
     * 表示順 : 号機順
     */
    public static final String ODERDISPLAY_RFTNO = "0";

    /**
     * 表示順 : エリア順
     */
    public static final String ODERDISPLAY_AREA = "1";


    // Public methods ------------------------------------------------

    /**
     * コンストラクタ <BR>
     */
    public PCTSystemInParameter()
    {
        super();
    }
    
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public PCTSystemInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }
        
    /**
     * lastUpdatePnameを設定します。
     * @param lastUpdatePname lastUpdatePname
     */
    public void setLastUpdatePname(String lastUpdatePname)
    {
        _lastUpdatePname = lastUpdatePname;
    }

    /**
     * aRankStandardValueを返します。
     * @return aRankStandardValueを返します。
     */
    public int getARankStandardValue()
    {
        return _aRankStandardValue;
    }

    /**
     * aRankStandardValueを設定します。
     * @param rankStandardValue aRankStandardValue
     */
    public void setARankStandardValue(int rankStandardValue)
    {
        _aRankStandardValue = rankStandardValue;
    }

    /**
     * bRankStandardValueを返します。
     * @return bRankStandardValueを返します。
     */
    public int getBRankStandardValue()
    {
        return _bRankStandardValue;
    }

    /**
     * bRankStandardValueを設定します。
     * @param rankStandardValue bRankStandardValue
     */
    public void setBRankStandardValue(int rankStandardValue)
    {
        _bRankStandardValue = rankStandardValue;
    }

    /**
     * 荷主コードを返します。
     * @return 荷主コードを返します。
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * 荷主コードを設定します。
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
    }

    /**
     * 荷主名称を返します。
     * @return 荷主名称を返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * 荷主名称を設定します。
     * @param consignorName 荷主名称
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
    }

    /**
     * 商品コードを返します。
     * @return 商品コードを返します。
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードを設定します。
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * JANコードを返します。
     * @return JANコードを返します。
     */
    public String getJan()
    {
        return _jan;
    }

    /**
     * JANコードを設定します。
     * @param jan JANコード
     */
    public void setJan(String jan)
    {
        _jan = jan;
    }

    /**
     * 商品名称を返します。
     * @return 商品名称を返します。
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * 商品名称を設定します。
     * @param itemName 商品名称
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * ロット入数を返します。
     * @return ロット入数を返します。
     */
    public int getLotEnteringQty()
    {
        return _lotEnteringQty;
    }

    /**
     * ロット入数を設定します。
     * @param lotEnteringQty ロット入数
     */
    public void setLotEnteringQty(int lotEnteringQty)
    {
        _lotEnteringQty = lotEnteringQty;
    }

    /**
     * ケース入数を返します。
     * @return ケース入数を返します。
     */
    public int getEnteringQty()
    {
        return _enteringQty;
    }

    /**
     * ケース入数を設定します。
     * @param enteringQty ケース入数
     */
    public void setEnteringQty(int enteringQty)
    {
        _enteringQty = enteringQty;
    }

    /**
     * ケースITFを返します。
     * @return ケースITFを返します。
     */
    public String getItf()
    {
        return _itf;
    }

    /**
     * ケースITFを設定します。
     * @param itf ケースITF
     */
    public void setItf(String itf)
    {
        _itf = itf;
    }

    /**
     * ボール入数を返します。
     * @return ボール入数を返します。
     */
    public int getBundleEnteringQty()
    {
        return _bundleEnteringQty;
    }

    /**
     * ボール入数を設定します。
     * @param bundleEnteringQty ボール入数
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        _bundleEnteringQty = bundleEnteringQty;
    }

    /**
     * ボールITFを返します。
     * @return ボールITFを返します。
     */
    public String getBundleItf()
    {
        return _bundleItf;
    }

    /**
     * ボールITFを設定します。
     * @param bundleItf ボールITF
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
    }

    /**
     * 発注単位を返します。
     * @return 発注単位を返します。
     */
    public int getUnit()
    {
        return _unit;
    }

    /**
     * 発注単位を設定します。
     * @param unit 発注単位
     */
    public void setUnit(int unit)
    {
        _unit = unit;
    }

    /**
     * ロケーションNo.1を返します。
     * @return ロケーションNo.1を返します。
     */
    public String getLocationNo1()
    {
        return _locationNo1;
    }

    /**
     * ロケーションNo.1を設定します。
     * @param locationNo1 ロケーションNo.1
     */
    public void setLocationNo1(String locationNo1)
    {
        _locationNo1 = locationNo1;
    }

    /**
     * 入数1を返します。
     * @return 入数1を返します。
     */
    public int getEnteringQty1()
    {
        return _enteringQty1;
    }

    /**
     * 入数1を設定します。
     * @param enteringQty1 入数1
     */
    public void setEnteringQty1(int enteringQty1)
    {
        _enteringQty1 = enteringQty1;
    }

    /**
     * ロケーションNo.2を返します。
     * @return ロケーションNo.2を返します。
     */
    public String getLocationNo2()
    {
        return _locationNo2;
    }

    /**
     * ロケーションNo.2を設定します。
     * @param locationNo2 ロケーションNo.2
     */
    public void setLocationNo2(String locationNo2)
    {
        _locationNo2 = locationNo2;
    }

    /**
     * 入数2を返します。
     * @return 入数2を返します。
     */
    public int getEnteringQty2()
    {
        return _enteringQty2;
    }

    /**
     * 入数2を設定します。
     * @param enteringQty2 入数2
     */
    public void setEnteringQty2(int enteringQty2)
    {
        _enteringQty2 = enteringQty2;
    }

    /**
     * ロケーションNo.3を返します。
     * @return ロケーションNo.3を返します。
     */
    public String getLocationNo3()
    {
        return _locationNo3;
    }

    /**
     * ロケーションNo.3を設定します。
     * @param locationNo3 ロケーションNo.3
     */
    public void setLocationNo3(String locationNo3)
    {
        _locationNo3 = locationNo3;
    }

    /**
     * 入数3を返します。
     * @return 入数3を返します。
     */
    public int getEnteringQty3()
    {
        return _enteringQty3;
    }

    /**
     * 入数3を設定します。
     * @param enteringQty3 入数3
     */
    public void setEnteringQty3(int enteringQty3)
    {
        _enteringQty3 = enteringQty3;
    }

    /**
     * ロケーションNo.4を返します。
     * @return ロケーションNo.4を返します。
     */
    public String getLocationNo4()
    {
        return _locationNo4;
    }

    /**
     * ロケーションNo.4を設定します。
     * @param locationNo4 ロケーションNo.4
     */
    public void setLocationNo4(String locationNo4)
    {
        _locationNo4 = locationNo4;
    }

    /**
     * 入数4を返します。
     * @return 入数4を返します。
     */
    public int getEnteringQty4()
    {
        return _enteringQty4;
    }

    /**
     * 入数4を設定します。
     * @param enteringQty4 入数4
     */
    public void setEnteringQty4(int enteringQty4)
    {
        _enteringQty4 = enteringQty4;
    }

    /**
     * 賞味期間を返します。
     * @return 賞味期間を返します。
     */
    public String getUseByPeriod()
    {
        return _useByPeriod;
    }

    /**
     * 賞味期間を設定します。
     * @param useByPeriod 賞味期間
     */
    public void setUseByPeriod(String useByPeriod)
    {
        _useByPeriod = useByPeriod;
    }

    /**
     * 入荷限度日を返します。
     * @return 入荷限度日を返します。
     */
    public String getInstockLimitDate()
    {
        return _instockLimitDate;
    }

    /**
     * 入荷限度日を設定します。
     * @param instockLimitDate 入荷限度日
     */
    public void setInstockLimitDate(String instockLimitDate)
    {
        _instockLimitDate = instockLimitDate;
    }

    /**
     * 出荷限度日を返します。
     * @return 出荷限度日を返します。
     */
    public String getShippingLimitDate()
    {
        return _shippingLimitDate;
    }

    /**
     * 出荷限度日を設定します。
     * @param shippingLimitDate 出荷限度日
     */
    public void setShippingLimitDate(String shippingLimitDate)
    {
        _shippingLimitDate = shippingLimitDate;
    }

    /**
     * 最新賞味期限を返します。
     * @return 最新賞味期限を返します。
     */
    public String getLatestUseByDate()
    {
        return _latestUseByDate;
    }

    /**
     * 最新賞味期限を設定します。
     * @param latestUseByDate 最新賞味期限
     */
    public void setLatestUseByDate(String latestUseByDate)
    {
        _latestUseByDate = latestUseByDate;
    }

    /**
     * 最新製造日を返します。
     * @return 最新製造日を返します。
     */
    public String getLatestManufacutureDate()
    {
        return _latestManufacutureDate;
    }

    /**
     * 最新製造日を設定します。
     * @param latestManufacutureDate 最新製造日
     */
    public void setLatestManufacutureDate(String latestManufacutureDate)
    {
        _latestManufacutureDate = latestManufacutureDate;
    }

    /**
     * 最新出庫日を返します。
     * @return 最新出庫日を返します。
     */
    public String getLatestRetrievalDate()
    {
        return _latestRetrievalDate;
    }

    /**
     * 最新出庫日を設定します。
     * @param latestRetrievalDate 最新出庫日
     */
    public void setLatestRetrievalDate(String latestRetrievalDate)
    {
        _latestRetrievalDate = latestRetrievalDate;
    }

    /**
     * 最新在庫を返します。
     * @return 最新在庫を返します。
     */
    public String getLatestStock()
    {
        return _latestStock;
    }

    /**
     * 最新在庫を設定します。
     * @param latestStock 最新在庫
     */
    public void setLatestStock(String latestStock)
    {
        _latestStock = latestStock;
    }

    /**
     * 最古在庫を返します。
     * @return 最古在庫を返します。
     */
    public String getOldestStock()
    {
        return _oldestStock;
    }

    /**
     * 最古在庫を設定します。
     * @param oldestStock 最古在庫
     */
    public void setOldestStock(String oldestStock)
    {
        _oldestStock = oldestStock;
    }

    /**
     * 管理フラグを返します。
     * @return 管理フラグを返します。
     */
    public String getManagementFlg()
    {
        return _managementFlg;
    }

    /**
     * 管理フラグを設定します。
     * @param managementFlg 管理フラグ
     */
    public void setManagementFlg(String managementFlg)
    {
        _managementFlg = managementFlg;
    }

    /**
     * 上限在庫数を返します。
     * @return 上限在庫数を返します。
     */
    public int getUpperQty()
    {
        return _upperQty;
    }

    /**
     * 上限在庫数を設定します。
     * @param upperQty 上限在庫数
     */
    public void setUpperQty(int upperQty)
    {
        _upperQty = upperQty;
    }

    /**
     * 下限在庫数を返します。
     * @return 下限在庫数を返します。
     */
    public int getLowerQty()
    {
        return _lowerQty;
    }

    /**
     * 下限在庫数を設定します。
     * @param lowerQty 下限在庫数
     */
    public void setLowerQty(int lowerQty)
    {
        _lowerQty = lowerQty;
    }

    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdateDate 最終更新日時
     */
    public void setLastUpdateDate(Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * レベルAの最終更新日時を返します。
     * @return レベルAの最終更新日時を返します。
     */
    public Date getLastUpdateDateA()
    {
        return _lastUpdateDateA;
    }

    /**
     * レベルAの最終更新日時を設定します。
     * @param lastUpdateDateA レベルAの最終更新日時
     */
    public void setLastUpdateDateA(Date lastUpdateDateA)
    {
        _lastUpdateDateA = lastUpdateDateA;
    }

    /**
     * レベルCの最終更新日時を返します。
     * @return レベルCの最終更新日時を返します。
     */
    public Date getLastUpdateDateC()
    {
        return _lastUpdateDateC;
    }

    /**
     * レベルCの最終更新日時を設定します。
     * @param lastUpdateDateC レベルCの最終更新日時
     */
    public void setLastUpdateDateC(Date lastUpdateDateC)
    {
        _lastUpdateDateC = lastUpdateDateC;
    }


    /**
     * 最終使用日を返します。
     * @return 最終使用日を返します。
     */
    public String getLastUsedDate()
    {
        return _lastUsedDate;
    }

    /**
     * 最終使用日を設定します。
     * @param lastUsedDate 最終使用日
     */
    public void setLastUsedDate(String lastUsedDate)
    {
        _lastUsedDate = lastUsedDate;
    }

    /**
     * 処理フラグを返します。
     * @return 処理フラグを返します。
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * 処理フラグを設定します。
     * @param processFlag 処理フラグ
     */
    public void setProcessFlag(String processFlag)
    {
        _processFlag = processFlag;
    }

    /**
     * 途中作業区分を返します。
     * @return 途中作業区分を返します。
     */
    public String getWorkOnTheWay()
    {
        return _workOnTheWay;
    }

    /**
     * 途中作業区分を設定します。
     * @param workOnTheWay 途中作業区分
     */
    public void setWorkOnTheWay(String workOnTheWay)
    {
        _workOnTheWay = workOnTheWay;
    }

    /**
     * 作業区分を返します。
     * @return 作業区分を返します。
     */
    public String getJobType()
    {
        return _jobType;
    }

    /**
     * 作業区分を設定します。
     * @param jobType 作業区分
     */
    public void setJobType(String jobType)
    {
        _jobType = jobType;
    }

    /**
     * データ区分を返します。
     * @return データ区分を返します。
     */
    public String getDataType()
    {
        return _dataType;
    }

    /**
     * データ区分を設定します。
     * @param dataType データ区分
     */
    public void setDataType(String dataType)
    {
        _dataType = dataType;
    }

    /**
     * パッケージ選択を返します。
     * @return パッケージ選択を返します。
     */
    public boolean getPackageSelect()
    {
        return _packageSelect;
    }

    /**
     * パッケージ選択を設定します。
     * @param packageSelect パッケージ選択
     */
    public void setPackageSelect(boolean packageSelect)
    {
        _packageSelect = packageSelect;
    }

    /**
     * 取込ファイル数を返します。
     * @return 取込ファイル数を返します。
     */
    public int getLoadFilesCount()
    {
        return _loadFiles_count;
    }

    /**
     * 取込ファイル数を設定します。
     * @param loadFilesCount 取込ファイル数
     */
    public void setLoadFilesCount(int loadFilesCount)
    {
        _loadFiles_count = loadFilesCount;
    }

    /**
     * 取込開始日時を返します。
     * @return 取込開始日時を返します。
     */
    public java.util.Date getLoadStartDate()
    {
        return _loadStartDate;
    }

    /**
     * 取込開始日時を設定します。
     * @param loadStartDate 取込開始日時
     */
    public void setLoadStartDate(java.util.Date loadStartDate)
    {
        _loadStartDate = loadStartDate;
    }

    /**
     * ホスト取消区分を返します。
     * @return ホスト取消区分を返します。
     */
    public String getCancelFlag()
    {
        return _cancelFlag;
    }

    /**
     * ホスト取消区分を設定します。
     * @param cancelFlag ホスト取消区分
     */
    public void setCancelFlag(String cancelFlag)
    {
        _cancelFlag = cancelFlag;
    }

    /**
     * 取込単位キーを返します。
     * @return 取込単位キーを返します。
     */
    public String getLoadUnitKey()
    {
        return _loadUnitKey;
    }

    /**
     * 取込単位キーを設定します。
     * @param loadUnitKey 取込単位キー
     */
    public void setLoadUnitKey(String loadUnitKey)
    {
        _loadUnitKey = loadUnitKey;
    }

    /**
     * 出庫予定日を返します。
     * @return 出庫予定日を返します。
     */
    public String getRetrievalPlanDay()
    {
        return _retrievalPlanDay;
    }

    /**
     * 出庫予定日を設定します。
     * @param retrievalPlanDay 出庫予定日
     */
    public void setRetrievalPlanDay(String retrievalPlanDay)
    {
        _retrievalPlanDay = retrievalPlanDay;
    }

    /**
     * リストセル行No.を返します。
     * @return リストセル行No.を返します。
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * リストセル行No.を設定します。
     * @param rowNo リストセル行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
     * 伝票No.を返します。
     * @return 伝票No.を返します。
     */
    public String getTicketNo()
    {
        return _ticketNo;
    }

    /**
     * 伝票No.を設定します。
     * @param ticketNo 伝票No.
     */
    public void setTicketNo(String ticketNo)
    {
        _ticketNo = ticketNo;
    }

    /**
     * 行No.を返します。
     * @return 行No.を返します。
     */
    public int getLineNo()
    {
        return _lineNo;
    }

    /**
     * 行No.を設定します。
     * @param lineNo 行No.
     */
    public void setLineNo(int lineNo)
    {
        _lineNo = lineNo;
    }

    /**
     * 作業枝番を返します。
     * @return 作業枝番を返します。
     */
    public int getBranchNo()
    {
        return _branchNo;
    }

    /**
     * 作業枝番を設定します。
     * @param branchNo 作業枝番
     */
    public void setBranchNo(int branchNo)
    {
        _branchNo = branchNo;
    }

    /**
     * JANコードを返します。
     * @return JANコードを返します。
     */
    public String getJanCode()
    {
        return _janCode;
    }

    /**
     * JANコードを設定します。
     * @param janCode JANコード
     */
    public void setJanCode(String janCode)
    {
        _janCode = janCode;
    }

    /**
     * ロットNo.を返します。
     * @return ロットNo.を返します。
     */
    public String getLotNo()
    {
        return _lotNo;
    }

    /**
     * ロットNo.を設定します。
     * @param lotNo ロットNo.
     */
    public void setLotNo(String lotNo)
    {
        _lotNo = lotNo;
    }

    /**
     * 予定数を返します。
     * @return 予定数を返します。
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * 予定数を設定します。
     * @param planQty 予定数
     */
    public void setPlanQty(int planQty)
    {
        _planQty = planQty;
    }

    /**
     * 出荷先コードを設定します。
     * @param customerCode 出荷先コード
     */
    public void setCustomerCode(String customerCode)
    {
        _customerCode = customerCode;
    }

    /**
     * 出荷先コードを返します。
     * @return 出荷先コードを返します。
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 出荷先名称を設定します。
     * @param customerName 出荷先名称
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
    }

    /**
     * 出荷先名称を返します。
     * @return 出荷先名称を返します。
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * 出荷先分類を設定します。
     * @param customerCategory 出荷先分類
     */
    public void setCustomerCategory(String customerCategory)
    {
        _customerCategory = customerCategory;
    }

    /**
     * 出荷先分類を返します。
     * @return 出荷先分類を返します。
     */
    public String getCustomerCategory()
    {
        return _customerCategory;
    }

    /**
     * 得意先コードを設定します。
     * @param regularCustomerCode 得意先コード
     */
    public void setRegularCustomerCode(String regularCustomerCode)
    {
        _regularCustomerCode = regularCustomerCode;
    }

    /**
     * 得意先コードを返します。
     * @return 得意先コードを返します。
     */
    public String getRegularCustomerCode()
    {
        return _regularCustomerCode;
    }

    /**
     * 得意先名称を設定します。
     * @param regularCustomerName 得意先名称
     */
    public void setRegularCustomerName(String regularCustomerName)
    {
        _regularCustomerName = regularCustomerName;
    }

    /**
     * @return 得意先名称を返します。
     * 得意先名称を返します。
     */
    public String getRegularCustomerName()
    {
        return _regularCustomerName;
    }

    /**
     * オーダーNoを設定します。
     * @param orderNo オーダーNo
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
    }

    /**
     * オーダーNoを返します。
     * @return オーダーNo.
     */
    public String getOrderNo()
    {
        return _orderNo;
    }

    /**
     * バッチNo.を設定します。
     * @param batchNo バッチNo.
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
    }

    /**
     * バッチNo.を返します。
     * @return バッチNo.を返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * バッチSeqNo.を設定します。
     * @param batchSeqNo バッチSeqNo.
     */
    public void setBatchSeqNo(String batchSeqNo)
    {
        _batchSeqNo = batchSeqNo;
    }

    /**
     * バッチSeqNo.を返します。
     * @return バッチSeqNo.を返します。
     */
    public String getBatchSeqNo()
    {
        return _batchSeqNo;
    }

    /**
     * 出庫予定一意キーを設定します。
     * @param planUkey 出庫予定一意キー
     */
    public void setPlanUkey(String planUkey)
    {
        _planUkey = planUkey;
    }

    /**
     * 出庫予定一意キーを返します。
     * @return 出庫予定一意キーを返します。
     */
    public String getPlanUkey()
    {
        return _planUkey;
    }

    /**
     * 開始終了フラグを返します。
     * @return 開始終了フラグを返します。
     */
    public String getFromToFlag()
    {
        return _fromToFlag;
    }

    /**
     * 開始終了フラグを設定します。
     * @param fromToFlag 開始終了フラグ
     */
    public void setFromToFlag(String fromToFlag)
    {
        _fromToFlag = fromToFlag;
    }

    /**
     * エリアNo.を返します。
     * @return エリアNo.を返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアNo.を設定します。
     * @param areaNo エリアNo.
     */
    public void setAreaNo(String areaNo)
    {
        _areaNo = areaNo;
    }

    /**
     * 棚を返します。
     * @return 棚を返します。
     */
    public String getLocation()
    {
        return _location;
    }

    /**
     * 棚を設定します。
     * @param location 棚
     */
    public void setLocation(String location)
    {
        _location = location;
    }

    /**
     * 棚(to)を返します。
     * @return 棚(to)を返します。
     */
    public String getToLocation()
    {
        return _toLocation;
    }

    /**
     * 棚(to)を設定します。
     * @param toLocation 棚(to)
     */
    public void setToLocation(String toLocation)
    {
        _toLocation = toLocation;
    }

    /**
     * 作業日を返します。
     * @return 作業日を返します。
     */
    public String getWorkDate()
    {
        return _workDate;
    }

    /**
     * 作業日を設定します。
     * @param workDay 作業日
     */
    public void setWorkDate(String workDay)
    {
        _workDate = workDay;
    }


    /**
     * ゾーンNo.を返します。
     * @return ゾーンNo.を返します。
     */
    public String getZoneNo()
    {
        return _zoneNo;
    }

    /**
     * ゾーンNo.を設定します。
     * @param zoneNo ゾーンNo.
     */
    public void setZoneNo(String zoneNo)
    {
        _zoneNo = zoneNo;
    }

    /**
     * ゾーン品名称を返します。
     * @return 商品名称を返します。
     */
    public String getZoneName()
    {
        return _zoneName;
    }

    /**
     * ゾーン品名称を設定します。
     * @param zoneName ゾーン名称
     */
    public void setZoneName(String zoneName)
    {
        _zoneName = zoneName;
    }

    /**
     * ボタン区分を返します。
     * @return ボタン区分を返します。
     */
    public String getButtonKubn()
    {
        return _buttonKubn;
    }

    /**
     * ボタン区分を設定します。
     * @param buttonKubn ボタン区分
     */
    public void setButtonKubn(String buttonKubn)
    {
        _buttonKubn = buttonKubn;
    }

    /**
     * PCT入荷予定データ格納フォルダを返します。
     * @return PCT入荷予定データ格納フォルダを返します。
     */
    public String getPCTInstockLoadData_Folder()
    {
        return _pctInstockLoadData_Folder;
    }

    /**
     * PCT入荷予定データ格納フォルダを設定します。
     * @param pctInstockLoadDataFolder PCT入荷予定データ格納フォルダ
     */
    public void setPCTInstockLoadData_Folder(String pctInstockLoadDataFolder)
    {
        _pctInstockLoadData_Folder = pctInstockLoadDataFolder;
    }

    /**
     * PCT出庫予定データ格納フォルダを返します。
     * @return PCT出庫予定データ格納フォルダを返します。
     */
    public String getPCTRetrievalLoadData_Folder()
    {
        return _pctRetrievalLoadData_Folder;
    }

    /**
     * PCT出庫予定データ格納フォルダを設定します。
     * @param pctRetrievalLoadDataFolder PCT出庫予定データ格納フォルダ
     */
    public void setPCTRetrievalLoadData_Folder(String pctRetrievalLoadDataFolder)
    {
        _pctRetrievalLoadData_Folder = pctRetrievalLoadDataFolder;
    }

    /**
     * PCT商品マスタデータ格納フォルダを返します。
     * @return PCT商品マスタデータ格納フォルダを返します。
     */
    public String getPCTItemLoadData_Folder()
    {
        return _pctItemLoadData_Folder;
    }

    /**
     * PCT商品マスタデータ格納フォルダを設定します。
     * @param pctItemLoadDataFolder PCT商品マスタデータ格納フォルダ
     */
    public void setPCTItemLoadData_Folder(String pctItemLoadDataFolder)
    {
        _pctItemLoadData_Folder = pctItemLoadDataFolder;
    }

    /**
     * PCT入荷予定データファイル名を返します。
     * @return PCT入荷予定データファイル名を返します。
     */
    public String getPCTInstockLoadData_Filename()
    {
        return _pctInstockLoadData_Filename;
    }

    /**
     * PCT入荷予定データファイル名を設定します。
     * @param pctInstockLoadDataFilename PCT入荷予定データファイル名
     */
    public void setPCTInstockLoadData_Filename(String pctInstockLoadDataFilename)
    {
        _pctInstockLoadData_Filename = pctInstockLoadDataFilename;
    }

    /**
     * PCT出庫予定データファイル名を返します。
     * @return PCT出庫予定データファイル名を返します。
     */
    public String getPCTRetrievalLoadData_Filename()
    {
        return _pctRetrievalLoadData_Filename;
    }

    /**
     * PCT出庫予定データファイル名を設定します。
     * @param pctRetrievalLoadDataFilename PCT出庫予定データファイル名
     */
    public void setPCTRetrievalLoadData_Filename(String pctRetrievalLoadDataFilename)
    {
        _pctRetrievalLoadData_Filename = pctRetrievalLoadDataFilename;
    }

    /**
     * PCT商品マスタデータファイル名を返します。
     * @return PCT商品マスタデータファイル名を返します。
     */
    public String getPCTItemLoadData_Filename()
    {
        return _pctItemLoadData_Filename;
    }

    /**
     * PCT商品マスタデータファイル名を設定します。
     * @param pctItemLoadDataFilename PCT商品マスタデータファイル名
     */
    public void setPCTItemLoadData_Filename(String pctItemLoadDataFilename)
    {
        _pctItemLoadData_Filename = pctItemLoadDataFilename;
    }

    /**
     * PCT出庫実績格納フォルダを返します。
     * @return PCT出庫実績格納フォルダを返します。
     */
    public String getPCTRetrievalReport_Folder()
    {
        return _pctRetrievalReport_Folder;
    }

    /**
     * PCT出庫実績格納フォルダを設定します。
     * @param pctRetrievalReportFolder PPCT出庫実績格納フォルダ
     */
    public void setPCTRetrievalReport_Folder(String pctRetrievalReportFolder)
    {
        _pctRetrievalReport_Folder = pctRetrievalReportFolder;
    }

    /**
     * PCT入荷実績格納フォルダを返します。
     * @return PCT入荷実績格納フォルダを返します。
     */
    public String getPCTInstockReport_Folder()
    {
        return _pctInstockReport_Folder;
    }

    /**
     * PCT入荷実績格納フォルダを設定します。
     * @param pctInstockReportFolder PPCT入荷実績格納フォルダ
     */
    public void setPCTInstockReport_Folder(String pctInstockReportFolder)
    {
        _pctInstockReport_Folder = pctInstockReportFolder;
    }

    /**
     * PCT出庫実績格納ファイル名を返します。
     * @return PCT出庫実績格納ファイル名を返します。
     */
    public String getPCTRetrievalReport_Filename()
    {
        return _pctRetrievalReport_Filename;
    }

    /**
     * PCT出庫実績格納ファイル名を設定します。
     * @param pctRetrievalReportFilename PCT出庫実績格納ファイル名
     */
    public void setPCTRetrievalReport_Filename(String pctRetrievalReportFilename)
    {
        _pctRetrievalReport_Filename = pctRetrievalReportFilename;
    }

    /**
     * PCT入荷実績格納ファイル名を返します。
     * @return PCT入荷実績格納ファイル名を返します。
     */
    public String getPCTInstockReport_Filename()
    {
        return _pctInstockReport_Filename;
    }

    /**
     * PCT入荷実績格納ファイル名を設定します。
     * @param pctInstockReportFilename PCT入荷実績格納ファイル名
     */
    public void setPCTInstockReport_Filename(String pctInstockReportFilename)
    {
        _pctInstockReport_Filename = pctInstockReportFilename;
    }

    /**
     * PCT入荷報告単位を返します。
     * @return PCT入荷報告単位を返します。
     */
    public String getPCTInstockReport_Type()
    {
        return _pctInstockReport_Type;
    }

    /**
     * PCT入荷報告単位を設定します。
     * @param pctInstockReportType PCT入荷報告単位
     */
    public void setPCTInstockReport_Type(String pctInstockReportType)
    {
        _pctInstockReport_Type = pctInstockReportType;
    }

    /**
     * PCT入荷実績_選択を返します。
     * @return PCT入荷実績_選択を返します。
     */
    public boolean isSelectPCTInstockReport()
    {
        return _selectPctInstockReport;
    }

    /**
     * PCT棚卸実績_選択を返します。
     * @return PCT入荷実績_選択を返します。
     */
    public boolean isSelectPCTInventoryReport()
    {
        return _selectPctInventoryReport;
    }

    /**
     * PCT出庫実績_選択を返します。
     * @return PCT出庫実績_選択を返します。
     */
    public boolean isSelectPCTRetrievalReport()
    {
        return _selectPctRetrievalReport;
    }

    /**
     * PCT入荷実績_選択を設定します。
     * @param selectPctInstockReport PCT入荷実績_選択
     */
    public void setSelectPCTInstockReport(boolean selectPctInstockReport)
    {
        _selectPctInstockReport = selectPctInstockReport;
    }

    /**
     * PCT出庫実績_選択を設定します。
     * @param selectPctRetrievalReport PCT出庫実績_選択
     */
    public void setSelectPCTRetrievalReport(boolean selectPctRetrievalReport)
    {
        _selectPctRetrievalReport = selectPctRetrievalReport;
    }

    /**
     * PCT棚卸実績_選択を設定します。
     * @param selectPctInventoryReport PCT出庫実績_選択
     */
    public void setSelectPCTInventoryReport(boolean selectPctInventoryReport)
    {
        _selectPctInventoryReport = selectPctInventoryReport;
    }

    /**
     * PCT出庫報告単位を返します。
     * @return PCT出庫報告単位を返します。
     */
    public String getPCTRetrievalReport_Type()
    {
        return _pctRetrievalReport_Type;
    }

    /**
     * PCT出庫報告単位を設定します。
     * @param pctRetrievalReportType PCT出庫報告単位
     */
    public void setPCTRetrievalReport_Type(String pctRetrievalReportType)
    {
        _pctRetrievalReport_Type = pctRetrievalReportType;
    }

    /**
     * PCT棚卸実績格納フォルダを返します。
     * @return 棚卸実績格納フォルダを返します。
     */
    public String getPCTInventoryReport_Folder()
    {
        return _pctInventoryReport_Folder;
    }

    /**
     * PCT棚卸実績格納フォルダを設定します。
     * @param pctInventoryReportFolder 棚卸実績格納フォルダ
     */
    public void setPCTInventoryReport_Folder(String pctInventoryReportFolder)
    {
        _pctInventoryReport_Folder = pctInventoryReportFolder;
    }

    /**
     * PCT棚卸実績格納ファイル名を設定します。
     * @param pctInventoryReportFilename 棚卸実績格納ファイル名
     */
    public void setPCTInventoryReport_Filename(String pctInventoryReportFilename)
    {
        _pctInventoryReport_Filename = pctInventoryReportFilename;
    }

    /**
     * PCT棚卸実績格納ファイル名を返します。
     * @return PCT棚卸実績格納ファイル名を返します。
     */
    public String getPCTInventoryReport_Filename()
    {
        return _pctInventoryReport_Filename;
    }

    /**
     * PCT出庫予定格納ファイル名を設定します。
     * @param pctRetrievalLoadFilename 出庫実績格納ファイル名
     */
    public void setPCTRetrievalLoad_Filename(String pctRetrievalLoadFilename)
    {
        _pctRetrievalLoad_Filename = pctRetrievalLoadFilename;
    }

    /**
     * PCT出庫実績格納ファイル名を返します。
     * @return PCT棚卸実績格納ファイル名を返します。
     */
    public String getPCTRetrievalLoad_Filename()
    {
        return _pctRetrievalLoad_Filename;
    }

    /**
     * PCT入庫予定格納ファイル名を設定します。
     * @param pctInstockLoadFilename 出庫実績格納ファイル名
     */
    public void setPCTInstockLoad_Filename(String pctInstockLoadFilename)
    {
        _pctInstockLoad_Filename = pctInstockLoadFilename;
    }

    /**
     * PCT入庫実績格納ファイル名を返します。
     * @return PCT入庫実績格納ファイル名を返します。
     */
    public String getPCTInstockLoad_Filename()
    {
        return _pctInstockLoad_Filename;
    }

    /**
     * システム管理区分を設定します。
     * @param managementType システム管理区分
     */
    public void setManagementType(String managementType)
    {
        _managementType = managementType;
    }

    /**
     * システム管理区分を返します。
     * @return システム管理区分を返します。
     */
    public String getManagementType()
    {
        return _managementType;
    }

    /**
     * システム管理フラグを設定します。
     * @param managementFlag システム管理フラグ
     */
    public void setManagementFlag(String managementFlag)
    {
        _managementFlag = managementFlag;
    }

    /**
     * システム管理フラグを返します。
     * @return システム管理フラグを返します。
     */
    public String getManagementFlag()
    {
        return _managementFlag;
    }

    /**
     * 終了作業日を返します。
     * @return 終了作業日を返します。
     */
    public String getToWorkDate()
    {
        return _toWorkDate;
    }

    /**
     * 終了作業日を設定します。
     * @param toWorkDate 終了作業日
     */
    public void setToWorkDate(String toWorkDate)
    {
        _toWorkDate = toWorkDate;
    }

    /**
     * 範囲フラグを返します。
     * @return 範囲フラグを返します。
     */
    public boolean isRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲フラグを設定します。
     * @param rangeFlag 範囲フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * 行/Hを返します。
     * @return 行/Hを返します。
     */
    public double getItemPerHour()
    {
        return _itemPerHour;
    }

    /**
     * 行/Hを設定します。
     * @param itemPerHour 行/H
     */
    public void setItemPerHour(double itemPerHour)
    {
        _itemPerHour = itemPerHour;
    }

    /**
     * ﾛｯﾄ/Hを返します。
     * @return ﾛｯﾄ/Hを返します。
     */
    public double getLotPerHour()
    {
        return _lotPerHour;
    }

    /**
     * ﾛｯﾄ/Hを設定します。
     * @param lotPerHour ﾛｯﾄ/H
     */
    public void setLotPerHour(double lotPerHour)
    {
        _lotPerHour = lotPerHour;
    }

    /**
     * ｵｰﾀﾞｰ/Hを返します。
     * @return ｵｰﾀﾞｰ/Hを返します。
     */
    public double getOrderPerHour()
    {
        return _orderPerHour;
    }

    /**
     * ｵｰﾀﾞｰ/Hを設定します。
     * @param orderPerHour ｵｰﾀﾞｰ/H
     */
    public void setOrderPerHour(double orderPerHour)
    {
        _orderPerHour = orderPerHour;
    }

    /**
     * エリア名称を返します。
     * @return エリア名称を返します。
     */
    public String getAreaName()
    {
        return _areaName;
    }

    /**
     * エリア名称を設定します。
     * @param areaName エリア名称
     */
    public void setAreaName(String areaName)
    {
        _areaName = areaName;
    }

    /**
     * 棚表示形式を返します。
     * @return 棚表示形式を返します。
     */
    public String getLocationStyle()
    {
        return _locationStyle;
    }

    /**
     * 棚表示形式を設定します。
     * @param locationStyle 棚表示形式
     */
    public void setLocationStyle(String locationStyle)
    {
        _locationStyle = locationStyle;
    }

    /**
     * 登録日時を返します。
     * @return 登録日時を返します。
     */
    public java.util.Date getRegistDate()
    {
        return _registDate;
    }

    /**
     * 登録日時を設定します。
     * @param registDate  登録日時
     */
    public void setRegistDate(java.util.Date registDate)
    {
        _registDate = registDate;
    }

    /**
     * 登録処理名を返します。
     * @return 登録処理名を返します。
     */
    public String getRegistPname()
    {
        return _registPname;
    }

    /**
     * 登録処理名を設定します。
     * @param registPname  登録処理名
     */
    public void setRegistProcess(String registPname)
    {
        _registPname = registPname;
    }

    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public java.util.Date getLastUpdate()
    {
        return _lastUpdate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdate  最終更新日時
     */
    public void setLastUpdate(java.util.Date lastUpdate)
    {
        _lastUpdate = lastUpdate;
    }

    /**
     * 最終更新処理名を返します。
     * @return 最終更新処理名を返します。
     */
    public String getLastUpdatePname()
    {
        return _lastUpdatePname;
    }

    /**
     * 最終更新処理名を設定します。
     * @param lastUpdatePname  最終更新処理名
     */
    public void setLastUpdateProcess(String lastUpdatePname)
    {
        _lastUpdatePname = lastUpdatePname;
    }

    /**
     * 表示条件を返します。
     * @return 表示条件を返します。
     */
    public String getDisplayCondition()
    {
        return _displayCondition;
    }

    /**
     * 表示条件を設定します。
     * @param displayCondition 表示条件
     */
    public void setDisplayCondition(String displayCondition)
    {
        _displayCondition = displayCondition;
    }

    /**
     * 作業日を返します。
     * @return 作業日を返します。
     */
    public String getWorkDay()
    {
        return _workDay;
    }

    /**
     * 作業日を設定します。
     * @param workDay 作業日
     */
    public void setWorkDay(String workDay)
    {
        _workDay = workDay;
    }

    /**
     * 基準日を返します。
     * @return 基準日を返します。
     */
    public String getUseByDate()
    {
        return _useByDate;
    }

    /**
     * 基準日を設定します。
     * @param useByDate 基準日
     */
    public void setUseByDate(String useByDate)
    {
        _useByDate = useByDate;
    }

    /**
     * アイテム情報コメントを返します。
     * @return アイテム情報コメントを返します。
     */
    public String getItemInfo()
    {
        return _itemInfo;
    }

    /**
     * アイテム情報コメントを設定します。
     * @param itemInfo アイテム情報コメント
     */
    public void setItemInfo(String itemInfo)
    {
        _itemInfo = itemInfo;
    }

    /**
     * オーダー情報コメントを設定します。
     * @param orderInfo オーダー情報コメント
     */
    public void setOrderInfo(String orderInfo)
    {
        _orderInfo = orderInfo;
    }

    /**
     * オーダー情報コメントを返します。
     * @return オーダー情報コメントを返します。
     */
    public String getOrderInfo()
    {
        return _orderInfo;
    }

    /**
     * 作業ランク設定方法を返します。
     * @return 作業ランク設定方法を返します。
     */
    public String getWorkRankSetType()
    {
        return _workRankSetType;
    }

    /**
     * 作業ランク設定方法を設定します。
     * @param workRankSetType 作業ランク設定方法
     */
    public void setWorkRankSetType(String workRankSetType)
    {
        _workRankSetType = workRankSetType;
    }

    /**
     * 重量誤差率初期値を返します。
     * @return 重量誤差率初期値を返します。
     */
    public int getWeightErrorRateInit()
    {
        return _weightErrorRateInit;
    }

    /**
     * 重量誤差率初期値を設定します。
     * @param weightErrorRateInit 重量誤差率初期値
     */
    public void setWeightErrorRateInit(int weightErrorRateInit)
    {
        _weightErrorRateInit = weightErrorRateInit;
    }

    /**
     * オーダー最大重量を返します。
     * @return オーダー最大重量を返します。
     */
    public int getOrderMaxWeight()
    {
        return _orderMaxWeight;
    }

    /**
     * オーダー最大重量を設定します。
     * @param orderMaxWeight オーダー最大重量
     */
    public void setOrderMaxWeight(int orderMaxWeight)
    {
        _orderMaxWeight = orderMaxWeight;
    }

    /**
     * センター名を返します。
     * @return センター名を返します。
     */
    public String getCenterName()
    {
        return _centerName;
    }

    /**
     * センター名を設定します。
     * @param centerName センター名
     */
    public void setCenterName(String centerName)
    {
        _centerName = centerName;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * 二つのオブジェクトを比較します。<br>
     * 両方ともNULLの時は同じものと見なします。
     *
     * @param o1 比較対象1
     * @param o2 比較対象2
     * @return 同じ内容のオブジェクトまたは両方ともnullのときtrue.
     */
    protected boolean equals(Object o1, Object o2)
    {
        if (o1 == null && o2 == null)
        {
            // 両方 null なら 同じものとみなす
            return true;
        }
        if (o1 == null)
        {
            // 片方だけ null なら異なったものと見なす
            return false;
        }
        if (o2 == null)
        {
            // 片方だけ null なら異なったものと見なす
            return false;
        }
        // 両方とも値を持つ場合は、内容の比較を行う
        return o1.equals(o2);
    }

    /**
     * fromUserIdを返します。
     * @return fromUserIdを返します。
     */
    public String getFromUserId()
    {
        return _fromUserId;
    }

    /**
     * fromUserIdを設定します。
     * @param fromUserId fromUserId
     */
    public void setFromUserId(String fromUserId)
    {
        _fromUserId = fromUserId;
    }

    /**
     * roleIdを返します。
     * @return roleIdを返します。
     */
    public String getRoleId()
    {
        return _roleId;
    }

    /**
     * roleIdを設定します。
     * @param roleId roleId
     */
    public void setRoleId(String roleId)
    {
        _roleId = roleId;
    }

    /**
     * toUserIdを返します。
     * @return toUserIdを返します。
     */
    public String getToUserId()
    {
        return _toUserId;
    }

    /**
     * toUserIdを設定します。
     * @param toUserId toUserId
     */
    public void setToUserId(String toUserId)
    {
        _toUserId = toUserId;
    }

    /**
     * 検索対象テーブルを設定します。
     * @param searchTable 検索対象テーブル
     */
    public void setSearchTable(String searchTable)
    {
        _searchTable = searchTable;
    }

    /**
     * 検索対象テーブルを返します。
     * @return 検索対象テーブルを返します。
     */
    public String getSearchTable()
    {
        return _searchTable;
    }

    /**
     * ランクを返します。
     * @return ランクを返します。
     */
    public String getRank()
    {
        return _rank;
    }

    /**
     * ランクを設定します。
     * @param rank ランク
     */
    public void setRank(String rank)
    {
        _rank = rank;
    }

    /**
     * 表示順を返します
     * @return 表示順を返します
     */
    public String getOrderDisplay()
    {
        return _orderDisplay;
    }

    /**
     * 表示順を設定します
     * @param display 表示順を設定します
     */
    public void setOrderDisplay(String display)
    {
        _orderDisplay = display;
    }
    
    /**
     * PCT商品マスタ取込フラグを返します
     * @return PCT商品マスタ取込フラグを返します
     */
    public String getItemLoad()
    {
        return _itemLoad;
    }

    /**
     * PCT商品マスタ取込フラグを設定します
     * @param itemLoad PCT商品マスタ取込フラグを設定します
     */
    public void setItemLoad(String itemLoad)
    {
        _itemLoad = itemLoad;
    }


    // Private methods -----------------------------------------------
}
//end of class
