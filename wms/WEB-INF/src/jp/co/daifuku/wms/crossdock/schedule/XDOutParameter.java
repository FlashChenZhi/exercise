package jp.co.daifuku.wms.crossdock.schedule;

import java.util.Date;

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>XDOutParameter</CODE>クラスは、クロスドックパッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスではクロスドックパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>XDOutParameter</CODE>クラスが保持する項目<BR>
 *     予定一意キー<BR>
 *     クロスドック連携キー<BR>
 *     取込単位キー<BR>
 *     ファイル行No.<BR>
 *     状態フラグ<BR>
 *     ホスト取消区分<BR>
 *     予定日<BR>
 *     バッチNo.<BR>
 *     荷主コード<BR>
 *     仕入先コード<BR>
 *     仕入先名称<BR>
 *     入荷伝票No.<BR>
 *     入荷伝票行No.<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     出荷伝票No.<BR>
 *     出荷伝票行No.<BR>
 *     仕分場所<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     予定ロットNo.<BR>
 *     予定数<BR>
 *     入荷実績数<BR>
 *     入荷欠品数<BR>
 *     仕分実績数<BR>
 *     仕分欠品数<BR>
 *     実績数<BR>
 *     欠品数<BR>
 *     実績報告区分<BR>
 *     作業日<BR>
 *     登録区分<BR>
 *     登録日時<BR>
 *     登録処理名<BR>
 *     最終更新日時<BR>
 *     最終更新処理名<BR>
 *     帳票発行フラグ<BR>
 *     処理フラグ<BR>
 *     検索対象テーブル<BR>
 *     予定日（一括削除帳票用）<BR>
 *     取込単位キー（一括削除帳票用）<BR>
 *     最終更新日時（一括削除帳票用）<BR>
 *     ボタン制御フラグ<BR>
 *     マスタ管理導入フラグ<BR>
 *     ケース入数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     取得件数<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *     マスタ検索結果Error<BR>
 *     入荷ケース数（分母）
 *     入荷ケース数（分子）
 *     入荷ピース数（分母）
 *     入荷ピース数（分子）
 *     仕分ケース数（分母）
 *     仕分ケース数（分子）
 *     仕分ピース数（分母）
 *     仕分ピース数（分子）
 *     入荷進捗率
 *     仕分進捗率
 *     前頁有効フラグ
 *     次頁有効フラグ
 *     入荷明細数（分母）
 *     入荷明細数（分子）
 *     仕分明細数（分母）
 *     仕分明細数（分子）
 * <BR>
 * </DIR>
 * 
 * Designer : T.Uehata <BR>
 * Maker : T.Uehata <BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class XDOutParameter
        extends OutParameter
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 商品マスタ
     */
    public static final String MASTER_ITEM = "DMITEM";

    /**
     * 仕入先マスタ
     */
    public static final String MASTER_SUPPLIER = "DMSUPPLIER";


    // Class variables -----------------------------------------------
    /**
     * 予定一意キー
     */
    private String _planUkey;

    /**
     * クロスドック連携キー
     */
    private String _crossDockUkey;

    /**
     * 取込単位キー
     */
    private String _loadUnitKey;

    /**
     * ファイル行No.
     */
    private int _fileLineNo;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * ホスト取消区分
     */
    private String _cancelFlag;

    /**
     * 予定日
     */
    private String _planDay;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 仕入先コード
     */
    private String _supplierCode;

    /**
     * 仕入先名称
     */
    private String _supplierName;

    /**
     * 入荷伝票No.
     */
    private String _receiveTicketNo;

    /**
     * 入荷伝票行No.
     */
    private int _receiveLineNo;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * 出荷伝票No.
     */
    private String _shipTicketNo;

    /**
     * 出荷伝票行No.
     */
    private int _shipLineNo;

    /**
     * 仕分場所
     */
    private String _sortingPlace;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 商品名称
     */
    private String _itemName;

    /**
     * 予定ロットNo.
     */
    private String _planLotNo;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 入荷実績数
     */
    private int _receiveResultQty;

    /**
     * 入荷欠品数
     */
    private int _receiveShortageQty;

    /**
     * 仕分実績数
     */
    private int _sortResultQty;

    /**
     * 仕分欠品数
     */
    private int _sortShortageQty;

    /**
     * 実績数
     */
    private int _resultQty;

    /**
     * 欠品数
     */
    private int _shortageQty;

    /**
     * 実績報告区分
     */
    private String _reportFlag;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 登録区分
     */
    private String _registKind;

    /**
     * 登録日時
     */
    private Date _registDate;

    /**
     * 登録処理名
     */
    private String _registPname;

    /**
     * 最終更新日時
     */
    private Date _lastUpdateDate;

    /**
     * 最終更新処理名
     */
    private String _lastUpdateName;

    /**
     * 帳票発行フラグ
     */
    private boolean _printFlag;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 予定日（一括削除帳票用）
     *
     */
    private String[] _planDays;

    /**
     * 取込単位キー（一括削除帳票用）
     */
    private String[] _loadUnitKeys;

    /**
     * 最終更新日時（一括削除帳票用）
     */
    private java.util.Date[] _lastUpdateDates;

    /**
     * ボタン制御フラグ
     */
    private String _buttonControlFlag;

    /**
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * 取得件数
     */
    private long _count;

    /**
     * 予定ケース数
     */
    private int _planCaseQty;

    /**
     * 予定ピース数
     */
    private int _planPieceQty;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

    /**
     * マスタ検索結果Error<BR>
     */
    private String _masterSearchError;

    /**
     * 入荷ケース数（分母）
     */
    private int _receiveCaseQtyDenominator;

    /**
     * 入荷ケース数（分子）
     */
    private int _receiveCaseQtyMolecule;

    /**
     * 入荷ピース数（分母）
     */
    private int _receivePieceQtyDenominator;

    /**
     * 入荷ピース数（分子）
     */
    private int _receivePieceQtyMolecule;

    /**
     * 仕分ケース数（分母）
     */
    private int _sortCaseQtyDenominator;

    /**
     * 仕分ケース数（分子）
     */
    private int _sortCaseQtyMolecule;

    /**
     * 仕分ピース数（分母）
     */
    private int _sortPieceQtyDenominator;

    /**
     * 仕分ピース数（分子）
     */
    private int _sortPieceQtyMolecule;

    /**
     * 入荷進捗率
     */
    private double _receiveProgress;

    /**
     * 仕分進捗率
     */
    private double _sortProgress;

    /**
     * 前頁有効フラグ
     */
    private boolean _enabledPrevPage;

    /**
     * 次頁有効フラグ
     */
    private boolean _enabledNextPage;

    /**
     * 入荷明細数（分母）
     */
    private int _receiveDetailCountDenominator;

    /**
     * 入荷明細数（分子）
     */
    private int _receiveDetailCountMolecule;

    /**
     * 仕分明細数（分母）
     */
    private int _sortDetailCountDenominator;

    /**
     * 仕分明細数（分子）
     */
    private int _sortDetailCountMolecule;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**
     * バッチNo.を返します
     * @return _batchNo
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * バッチNo.を設定します
     * @param no バッチNo.
     */
    public void setBatchNo(String no)
    {
        _batchNo = no;
    }

    /**
     * ホスト取消区分を返します
     * @return _cancelFlag ホスト取消区分
     */
    public String getCancelFlag()
    {
        return _cancelFlag;
    }

    /**
     * ホスト取消区分を設定します
     * @param flag ホスト取消区分
     */
    public void setCancelFlag(String flag)
    {
        _cancelFlag = flag;
    }

    /**
     * 荷主コードを返します
     * @return _consignorCode 荷主コード
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * 荷主コードを設定します
     * @param code 荷主コード
     */
    public void setConsignorCode(String code)
    {
        _consignorCode = code;
    }

    /**
     * クロスドック連携キーを返します
     * @return _crossDockUkey クロスドック連携キー
     */
    public String getCrossDockUkey()
    {
        return _crossDockUkey;
    }

    /**
     * クロスドック連携キーを設定します
     * @param dockUkey クロスドック連携キー
     */
    public void setCrossDockUkey(String dockUkey)
    {
        _crossDockUkey = dockUkey;
    }

    /**
     * 出荷先コードを返します
     * @return _customerCode 出荷先コード
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 出荷先コードを設定します
     * @param code 出荷先コード
     */
    public void setCustomerCode(String code)
    {
        _customerCode = code;
    }

    /**
     * ファイル行No.を返します
     * @return _fileLineNo ファイル行No.
     */
    public int getFileLineNo()
    {
        return _fileLineNo;
    }

    /**
     * ファイル行No.を設定します
     * @param lineNo ファイル行No.
     */
    public void setFileLineNo(int lineNo)
    {
        _fileLineNo = lineNo;
    }

    /**
     * 商品コードを返します
     * @return _itemCode 商品コード
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードを設定します
     * @param code 商品コード
     */
    public void setItemCode(String code)
    {
        _itemCode = code;
    }

    /**
     * 最終更新日時を返します
     * @return _lastUpdateDate 最終更新日時
     */
    public Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日時を設定します
     * @param updateDate 最終更新日時
     */
    public void setLastUpdateDate(Date updateDate)
    {
        _lastUpdateDate = updateDate;
    }

    /**
     * 最終更新日時（一括削除帳票用）を返します
     * @return _lastUpdateDates 最終更新日時（一括削除帳票用）
     */
    public java.util.Date[] getLastUpdateDates()
    {
        return _lastUpdateDates;
    }

    /**
     * 最終更新日時（一括削除帳票用）を設定します
     * @param updateDates 最終更新日時（一括削除帳票用）
     */
    public void setLastUpdateDates(java.util.Date[] updateDates)
    {
        _lastUpdateDates = updateDates;
    }

    /**
     * 最終更新処理名を返します
     * @return _lastUpdateName 最終更新処理名
     */
    public String getLastUpdateName()
    {
        return _lastUpdateName;
    }

    /**
     * 最終更新処理名を設定します
     * @param updateName 最終更新処理名
     */
    public void setLastUpdateName(String updateName)
    {
        _lastUpdateName = updateName;
    }

    /**
     * 取込単位キーを返します
     * @return _loadUnitKey 取込単位キー
     */
    public String getLoadUnitKey()
    {
        return _loadUnitKey;
    }

    /**
     * 取込単位キーを設定します
     * @param unitKey 取込単位キー
     */
    public void setLoadUnitKey(String unitKey)
    {
        _loadUnitKey = unitKey;
    }

    /**
     * 取込単位キー（一括削除帳票用）を返します
     * @return _loadUnitKeys 取込単位キー（一括削除帳票用）
     */
    public String[] getLoadUnitKeys()
    {
        return _loadUnitKeys;
    }

    /**
     * 取込単位キー（一括削除帳票用）を設定します
     * @param unitKeys 取込単位キー（一括削除帳票用）
     */
    public void setLoadUnitKeys(String[] unitKeys)
    {
        _loadUnitKeys = unitKeys;
    }

    /**
     * 予定日を返します
     * @return _planDay 予定日
     */
    public String getPlanDay()
    {
        return _planDay;
    }

    /**
     * 予定日を設定します
     * @param day 予定日
     */
    public void setPlanDay(String day)
    {
        _planDay = day;
    }

    /**
     * 予定日（一括削除帳票用）を返します
     * @return _planDays 予定日（一括削除帳票用）
     */
    public String[] getPlanDays()
    {
        return _planDays;
    }

    /**
     * 予定日（一括削除帳票用）を設定します
     * @param days 予定日（一括削除帳票用）
     */
    public void setPlanDays(String[] days)
    {
        _planDays = days;
    }

    /**
     * 予定ロットNo.を返します
     * @return _planLotNo 予定ロットNo.
     */
    public String getPlanLotNo()
    {
        return _planLotNo;
    }

    /**
     * 予定ロットNo.を設定します
     * @param lotNo 予定ロットNo.
     */
    public void setPlanLotNo(String lotNo)
    {
        _planLotNo = lotNo;
    }

    /**
     * 予定数を返します
     * @return _planQty 予定数
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * 予定数を設定します
     * @param qty 予定数
     */
    public void setPlanQty(int qty)
    {
        _planQty = qty;
    }

    /**
     * 予定一意キーを返します
     * @return _planUkey 予定一意キー
     */
    public String getPlanUkey()
    {
        return _planUkey;
    }

    /**
     * 予定一意キーを設定します
     * @param ukey 予定一意キー
     */
    public void setPlanUkey(String ukey)
    {
        _planUkey = ukey;
    }

    /**
     * 帳票発行フラグを返します
     * @return _printFlag 帳票発行フラグ
     */
    public boolean isPrintFlag()
    {
        return _printFlag;
    }

    /**
     * 帳票発行フラグを設定します
     * @param flag 帳票発行フラグ
     */
    public void setPrintFlag(boolean flag)
    {
        _printFlag = flag;
    }

    /**
     * 処理フラグを返します
     * @return _processFlag 処理フラグ
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * 処理フラグを設定します
     * @param flag 処理フラグ
     */
    public void setProcessFlag(String flag)
    {
        _processFlag = flag;
    }

    /**
     * 入荷伝票行No.を返します
     * @return _receiveLineNo 入荷伝票行No.
     */
    public int getReceiveLineNo()
    {
        return _receiveLineNo;
    }

    /**
     * 入荷伝票行No.を設定します
     * @param lineNo 入荷伝票行No.
     */
    public void setReceiveLineNo(int lineNo)
    {
        _receiveLineNo = lineNo;
    }

    /**
     * 入荷実績数を返します
     * @return _receiveResultQty 入荷実績数
     */
    public int getReceiveResultQty()
    {
        return _receiveResultQty;
    }

    /**
     * 入荷実績数を設定します
     * @param resultQty 入荷実績数
     */
    public void setReceiveResultQty(int resultQty)
    {
        _receiveResultQty = resultQty;
    }

    /**
     * 入荷欠品数を返します
     * @return _receiveShortageQty 入荷欠品数
     */
    public int getReceiveShortageQty()
    {
        return _receiveShortageQty;
    }

    /**
     * 入荷欠品数を設定します
     * @param shortageQty 入荷欠品数
     */
    public void setReceiveShortageQty(int shortageQty)
    {
        _receiveShortageQty = shortageQty;
    }

    /**
     * 入荷伝票No.を返します
     * @return _receiveTicketNo 入荷伝票No.
     */
    public String getReceiveTicketNo()
    {
        return _receiveTicketNo;
    }

    /**
     * 入荷伝票No.を設定します
     * @param ticketNo 入荷伝票No.
     */
    public void setReceiveTicketNo(String ticketNo)
    {
        _receiveTicketNo = ticketNo;
    }

    /**
     * 登録日時を返します
     * @return _registDate 登録日時
     */
    public Date getRegistDate()
    {
        return _registDate;
    }

    /**
     * 登録日時を設定します
     * @param date 登録日時
     */
    public void setRegistDate(Date date)
    {
        _registDate = date;
    }

    /**
     * 登録区分を返します
     * @return _registKind 登録区分
     */
    public String getRegistKind()
    {
        return _registKind;
    }

    /**
     * 登録区分を設定します
     * @param kind 登録区分
     */
    public void setRegistKind(String kind)
    {
        _registKind = kind;
    }

    /**
     * 登録処理名を返します
     * @return _registPname 登録処理名
     */
    public String getRegistPname()
    {
        return _registPname;
    }

    /**
     * 登録処理名を設定します
     * @param pname  登録処理名
     */
    public void setRegistPname(String pname)
    {
        _registPname = pname;
    }

    /**
     * 実績報告区分を返します
     * @return _reportFlag 実績報告区分
     */
    public String getReportFlag()
    {
        return _reportFlag;
    }

    /**
     * 実績報告区分を設定します
     * @param flag 実績報告区分
     */
    public void setReportFlag(String flag)
    {
        _reportFlag = flag;
    }

    /**
     * 実績数を返します
     * @return _resultQty 実績数
     */
    public int getResultQty()
    {
        return _resultQty;
    }

    /**
     * 実績数を設定します
     * @param qty 実績数
     */
    public void setResultQty(int qty)
    {
        _resultQty = qty;
    }

    /**
     * 検索対象テーブルを返します
     * @return _searchTable 検索対象テーブル
     */
    public String getSearchTable()
    {
        return _searchTable;
    }

    /**
     * 検索対象テーブルを設定します
     * @param table 検索対象テーブル
     */
    public void setSearchTable(String table)
    {
        _searchTable = table;
    }

    /**
     * 出荷伝票行No.を返します
     * @return _shipLineNo 出荷伝票行No.
     */
    public int getShipLineNo()
    {
        return _shipLineNo;
    }

    /**
     * 出荷伝票行No.を設定します
     * @param lineNo 出荷伝票行No.
     */
    public void setShipLineNo(int lineNo)
    {
        _shipLineNo = lineNo;
    }

    /**
     * 出荷伝票No.を返します
     * @return _shipTicketNo 出荷伝票No.
     */
    public String getShipTicketNo()
    {
        return _shipTicketNo;
    }

    /**
     * 出荷伝票No.を設定します
     * @param ticketNo 出荷伝票No.
     */
    public void setShipTicketNo(String ticketNo)
    {
        _shipTicketNo = ticketNo;
    }

    /**
     * 欠品数を返します
     * @return _shortageQty 欠品数
     */
    public int getShortageQty()
    {
        return _shortageQty;
    }

    /**
     * 欠品数を設定します
     * @param qty 欠品数
     */
    public void setShortageQty(int qty)
    {
        _shortageQty = qty;
    }

    /**
     * 仕分場所を返します
     * @return _sortingPlace 仕分場所
     */
    public String getSortingPlace()
    {
        return _sortingPlace;
    }

    /**
     * 仕分場所を設定します
     * @param place 仕分場所
     */
    public void setSortingPlace(String place)
    {
        _sortingPlace = place;
    }

    /**
     * 仕分実績数を返します
     * @return _sortResultQty 仕分実績数
     */
    public int getSortResultQty()
    {
        return _sortResultQty;
    }

    /**
     * 仕分実績数を設定します
     * @param resultQty 仕分実績数
     */
    public void setSortResultQty(int resultQty)
    {
        _sortResultQty = resultQty;
    }

    /**
     * 仕分欠品数を返します
     * @return _sortShortageQty 仕分欠品数
     */
    public int getSortShortageQty()
    {
        return _sortShortageQty;
    }

    /**
     * 仕分欠品数を設定します
     * @param shortageQty 仕分欠品数
     */
    public void setSortShortageQty(int shortageQty)
    {
        _sortShortageQty = shortageQty;
    }

    /**
     * 状態フラグを返します
     * @return _statusFlag 状態フラグ
     */
    public String getStatusFlag()
    {
        return _statusFlag;
    }

    /**
     * 状態フラグを設定します
     * @param flag 状態フラグ
     */
    public void setStatusFlag(String flag)
    {
        _statusFlag = flag;
    }

    /**
     * 仕入先コードを返します
     * @return _supplierCode 仕入先コード
     */
    public String getSupplierCode()
    {
        return _supplierCode;
    }

    /**
     * 仕入先コードを設定します
     * @param code 仕入先コード
     */
    public void setSupplierCode(String code)
    {
        _supplierCode = code;
    }

    /**
     * 作業日を返します
     * @return _workDay 作業日
     */
    public String getWorkDay()
    {
        return _workDay;
    }

    /**
     * 作業日を設定します
     * @param day 作業日
     */
    public void setWorkDay(String day)
    {
        _workDay = day;
    }

    /**
     * ボタン制御フラグを返します
     * @return _buttonControlFlag ボタン制御フラグ
     */
    public String getButtonControlFlag()
    {
        return _buttonControlFlag;
    }

    /**
     * ボタン制御フラグを設定します
     * @param controlFlag ボタン制御フラグ
     */
    public void setButtonControlFlag(String controlFlag)
    {
        _buttonControlFlag = controlFlag;
    }

    /**
     * マスタ管理導入フラグを返します
     * @return _masterFlag マスタ管理導入フラグ
     */
    public boolean isMasterFlag()
    {
        return _masterFlag;
    }

    /**
     * マスタ管理導入フラグを設定します
     * @param flag マスタ管理導入フラグ
     */
    public void setMasterFlag(boolean flag)
    {
        _masterFlag = flag;
    }

    /**
     * 出荷先名称を返します
     * @return _customerName 出荷先名称
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * 出荷先名称を設定します
     * @param name 出荷先名称
     */
    public void setCustomerName(String name)
    {
        _customerName = name;
    }

    /**
     * 商品名称を返します
     * @return _itemName 商品名称
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * 商品名称を設定します
     * @param name 商品名称
     */
    public void setItemName(String name)
    {
        _itemName = name;
    }

    /**
     * 仕入先名称を返します
     * @return _supplierName 仕入先名称
     */
    public String getSupplierName()
    {
        return _supplierName;
    }

    /**
     * 仕入先名称を設定します
     * @param name 仕入先名称
     */
    public void setSupplierName(String name)
    {
        _supplierName = name;
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
     * 取得件数を返します。
     * @return 取得件数を返します。
     */
    public long getCount()
    {
        return _count;
    }

    /**
     * 取得件数を設定します。
     * @param count 取得件数
     */
    public void setCount(long count)
    {
        _count = count;
    }

    /**
     * 予定ケース数を返します。
     * @return 予定ケース数を返します。
     */
    public int getPlanCaseQty()
    {
        return _planCaseQty;
    }

    /**
     * 予定ケース数を設定します。
     * @param planCaseQty 予定ケース数
     */
    public void setPlanCaseQty(int planCaseQty)
    {
        _planCaseQty = planCaseQty;
    }

    /**
     * 予定ピース数を返します。
     * @return 予定ピース数を返します。
     */
    public int getPlanPieceQty()
    {
        return _planPieceQty;
    }

    /**
     * 予定ピース数を設定します。
     * @param planPieceQty 予定ピース数
     */
    public void setPlanPieceQty(int planPieceQty)
    {
        _planPieceQty = planPieceQty;
    }

    /**
     * 実績ケース数を返します。
     * @return 実績ケース数を返します。
     */
    public int getResultCaseQty()
    {
        return _resultCaseQty;
    }

    /**
     * 実績ケース数を設定します。
     * @param resultCaseQty 実績ケース数
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }

    /**
     * 実績ピース数を返します。
     * @return 実績ピース数を返します。
     */
    public int getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * 実績ピース数を設定します。
     * @param resultPieceQty 実績ピース数
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
    }

    /**
     * マスタ検索結果Errorを返します
     * @return _masterSearchError マスタ検索結果Error
     */
    public String getMasterSearchError()
    {
        return _masterSearchError;
    }

    /**
     * マスタ検索結果Errorを設定します
     * @param searchError マスタ検索結果Error
     */
    public void setMasterSearchError(String searchError)
    {
        _masterSearchError = searchError;
    }

    /**
     * 入荷ケース数（分母）を返します
     * @return _receiveCaseQtyDenominator 入荷ケース数（分母）
     */
    public int getReceiveCaseQtyDenominator()
    {
        return _receiveCaseQtyDenominator;
    }

    /**
     * 入荷ケース数（分母）を設定します。
     * @param receiveCaseQtyDenominator 入荷ケース数（分母）
     */
    public void setReceiveCaseQtyDenominator(int receiveCaseQtyDenominator)
    {
        _receiveCaseQtyDenominator = receiveCaseQtyDenominator;
    }

    /**
     * 入荷ケース数（分子）を返します
     * @return _receiveCaseQtyMolecule 入荷ケース数（分子）
     */
    public int getReceiveCaseQtyMolecule()
    {
        return _receiveCaseQtyMolecule;
    }

    /**
     * 入荷ケース数（分子）を設定します。
     * @param receiveCaseQtyMolecule 入荷ケース数（分子）
     */
    public void setReceiveCaseQtyMolecule(int receiveCaseQtyMolecule)
    {
        _receiveCaseQtyMolecule = receiveCaseQtyMolecule;
    }

    /**
     * 入荷ピース数（分母）を返します
     * @return _receiveCaseQtyDenominator 入荷ピース数（分母）
     */
    public int getReceivePieceQtyDenominator()
    {
        return _receivePieceQtyDenominator;
    }

    /**
     * 入荷ピース数（分母）を設定します。
     * @param receivePieceQtyDenominator 入荷ピース数（分母）
     */
    public void setReceivePieceQtyDenominator(int receivePieceQtyDenominator)
    {
        _receivePieceQtyDenominator = receivePieceQtyDenominator;
    }

    /**
     * 入荷ピース数（分子）を返します
     * @return _receivePieceQtyMolecule 入荷ピース数（分子）
     */
    public int getReceivePieceQtyMolecule()
    {
        return _receivePieceQtyMolecule;
    }

    /**
     * 入荷ピース数（分子）を設定します。
     * @param receivePieceQtyMolecule 入荷ピース数（分子）
     */
    public void setReceivePieceQtyMolecule(int receivePieceQtyMolecule)
    {
        _receivePieceQtyMolecule = receivePieceQtyMolecule;
    }

    /**
     * 入荷進捗率を返します
     * @return _receiveProgress 入荷進捗率
     */
    public double getReceiveProgress()
    {
        return _receiveProgress;
    }

    /**
     * 入荷進捗率を設定します。
     * @param receiveProgress 入荷進捗率
     */
    public void setReceiveProgress(double receiveProgress)
    {
        _receiveProgress = receiveProgress;
    }

    /**
     * 仕分ケース数（分母）を返します
     * @return _sortCaseQtyDenominator 仕分ケース数（分母）
     */
    public int getSortCaseQtyDenominator()
    {
        return _sortCaseQtyDenominator;
    }

    /**
     * 仕分ケース数（分母）を設定します。
     * @param sortCaseQtyDenominator 仕分ケース数（分母）
     */
    public void setSortCaseQtyDenominator(int sortCaseQtyDenominator)
    {
        _sortCaseQtyDenominator = sortCaseQtyDenominator;
    }

    /**
     * 仕分ケース数（分子）を返します
     * @return _sortCaseQtyMolecule 仕分ケース数（分子）
     */
    public int getSortCaseQtyMolecule()
    {
        return _sortCaseQtyMolecule;
    }

    /**
     * 仕分ケース数（分子）を設定します。
     * @param sortCaseQtyMolecule 仕分ケース数（分子）
     */
    public void setSortCaseQtyMolecule(int sortCaseQtyMolecule)
    {
        _sortCaseQtyMolecule = sortCaseQtyMolecule;
    }

    /**
     * 仕分ピース数（分母）を返します
     * @return _sortPieceQtyDenominator 仕分ピース数（分母）
     */
    public int getSortPieceQtyDenominator()
    {
        return _sortPieceQtyDenominator;
    }

    /**
     * 仕分ピース数（分母）を設定します。
     * @param sortPieceQtyDenominator 仕分ピース数（分母）
     */
    public void setSortPieceQtyDenominator(int sortPieceQtyDenominator)
    {
        _sortPieceQtyDenominator = sortPieceQtyDenominator;
    }

    /**
     * 仕分ピース数（分子）を返します
     * @return _sortPieceQtyDenominator 仕分ピース数（分子）
     */
    public int getSortPieceQtyMolecule()
    {
        return _sortPieceQtyMolecule;
    }

    /**
     * 仕分ピース数（分子）を設定します。
     * @param sortPieceQtyMolecule 仕分ピース数（分子）
     */
    public void setSortPieceQtyMolecule(int sortPieceQtyMolecule)
    {
        _sortPieceQtyMolecule = sortPieceQtyMolecule;
    }

    /**
     * 仕分進捗率を返します
     * @return _sortProgress 仕分進捗率
     */
    public double getSortProgress()
    {
        return _sortProgress;
    }

    /**
     * 仕分進捗率を設定します。
     * @param sortProgress 仕分進捗率
     */
    public void setSortProgress(double sortProgress)
    {
        _sortProgress = sortProgress;
    }

    /**
     * 次頁有効フラグを返します。
     * 
     * @return _enabledNextPage 次頁有効フラグ
     */
    public boolean getEnabledNextPage()
    {
        return _enabledNextPage;
    }

    /**
     * 次頁有効フラグを設定します。
     * 
     * @param enabledNextPage 次頁有効フラグ
     */
    public void setEnabledNextPage(boolean enabledNextPage)
    {
        _enabledNextPage = enabledNextPage;
    }

    /**
     * 前頁有効フラグを返します。
     * 
     * @return _enabledPrevPage 前頁有効フラグ
     */
    public boolean getEnabledPrevPage()
    {
        return _enabledPrevPage;
    }

    /**
     * 前頁有効フラグを設定します。
     * 
     * @param enabledPrevPage 前頁有効フラグ
     */
    public void setEnabledPrevPage(boolean enabledPrevPage)
    {
        _enabledPrevPage = enabledPrevPage;
    }

    /**
     * 入荷明細数（分母）を返します。
     * 
     * @return _receiveDetailCountDenominator 入荷明細数（分母）
     */
    public int getReceiveDetailCountDenominator()
    {
        return _receiveDetailCountDenominator;
    }

    /**
     * 入荷明細数（分母）を設定します。
     * 
     * @param receiveDetailCountDenominator 入荷明細数（分母）
     */
    public void setReceiveDetailCountDenominator(int receiveDetailCountDenominator)
    {
        _receiveDetailCountDenominator = receiveDetailCountDenominator;
    }

    /**
     * 入荷明細数（分子）を返します。
     * 
     * @return _receiveDetailCountMolecule 入荷明細数（分子）
     */
    public int getReceiveDetailCountMolecule()
    {
        return _receiveDetailCountMolecule;
    }

    /**
     * 入荷明細数（分子）を設定します。
     * 
     * @param receiveDetailCountMolecule 入荷明細数（分子）
     */
    public void setReceiveDetailCountMolecule(int receiveDetailCountMolecule)
    {
        _receiveDetailCountMolecule = receiveDetailCountMolecule;
    }

    /**
     * 仕分明細数（分母）を返します。
     * 
     * @return _sortDetailCountDenominator 仕分明細数（分母）
     */
    public int getSortDetailCountDenominator()
    {
        return _sortDetailCountDenominator;
    }

    /**
     * 仕分明細数（分母）を設定します。
     * 
     * @param sortDetailCountDenominator 仕分明細数（分母）
     */
    public void setSortDetailCountDenominator(int sortDetailCountDenominator)
    {
        _sortDetailCountDenominator = sortDetailCountDenominator;
    }

    /**
     * 仕分明細数（分子）を返します。
     * 
     * @return _sortDetailCountMolecule 仕分明細数（分子）
     */
    public int getSortDetailCountMolecule()
    {
        return _sortDetailCountMolecule;
    }

    /**
     * 仕分明細数（分子）を設定します。
     * 
     * @param sortDetailCountMolecule 仕分明細数（分子）
     */
    public void setSortDetailCountMolecule(int sortDetailCountMolecule)
    {
        _sortDetailCountMolecule = sortDetailCountMolecule;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
