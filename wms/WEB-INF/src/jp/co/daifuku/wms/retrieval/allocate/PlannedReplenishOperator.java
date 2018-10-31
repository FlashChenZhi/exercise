// $Id: PlannedReplenishOperator.java 6285 2009-12-01 10:48:02Z kishimoto $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.dbhandler.ReplenishShortageHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.ReplenishShortage;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DirectDBFinder;


/**
 * 固定棚情報に対する補充引当を行うクラスです。<br>
 *
 * @version $Revision: 6285 $, $Date: 2009-12-01 19:48:02 +0900 (火, 01 12 2009) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: kishimoto $
 */
public class PlannedReplenishOperator
        extends AbstractReplenishOperator
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
    /**
     * 外部からの引当要求があったか否かを判別するフラグ
     */
    private boolean _isRequire = false;

    /**
     * 外部からの引当要求数
     */
    private int _requireQty = 0;

    /**
     * 補充率
     */
    private int _rate = 0;

    /**
     * 在庫情報ハンドラ
     */
    private StockHandler _stkh = null;

    /**
     * 在庫情報検索キー
     */
    private StockSearchKey _stkKey = null;

    /**
     * 補充欠品情報ハンドラ
     */
    private ReplenishShortageHandler _rsh = null;

    /**
     * 補充開始単位キー
     */
    private String _startUnitKey;
    
    /**
     * 今回引当を行う固定在庫情報
     */
    private FixedLocateInfo _fixed = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param rate 補充率
     * @param pattern 引当パタンNo.
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public PlannedReplenishOperator(Connection conn, int rate, String pattern, Class caller)
            throws CommonException
    {
        super(conn, pattern, caller);

        _rate = rate;

        _stkh = new StockHandler(conn);
        _stkKey = new StockSearchKey();

        _rsh = new ReplenishShortageHandler(conn);

        _startUnitKey = getSequence().nextStartUnitKey();

    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 固定棚情報を元に補充引当を行います。<BR>
     * 引数で渡された固定棚情報の荷主コード、商品コードと
     * 画面で指定された引当パタンに含まれるエリアに一致する
     * 在庫を検索し、補充引当を行います。<BR>
     * <BR>
     * 以下のデータ更新を行います。<BR>
     * <div>
     *   在庫の引当 <BR>
     *   移動作業情報の作成 <BR>
     *   <i>AS/RSの場合</i> <BR>
     *   作業情報の作成 <BR>
     *   搬送情報の作成 <BR>
     *   <i>補充結果欠品の場合</i> <BR>
     *   補充欠品情報の作成 <BR>
     * </div>
     * 
     * @param ent 固定棚情報
     * @return 引当処理での欠品数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public int allocate(Entity ent)
            throws CommonException
    {
        FixedLocateInfo fixedLoc = (FixedLocateInfo)ent;
        _fixed = fixedLoc;

        // 既に未完了の補充作業情報が存在する場合今回は補充作業を作成しない
        if (hasMoveWorkInfo(fixedLoc.getAreaNo(), fixedLoc.getConsignorCode(), fixedLoc.getItemCode()))
        {
            setPlanQty(0);
            setAllocatedQty(0);
            return 0;
        }

        // 補充数を求める
        int allocateQty = calcRequireQty(fixedLoc);
        
        if (allocateQty == 0)
        {
            setPlanQty(0);
            setAllocatedQty(0);
            return 0;
        }

        // 在庫引当(ロットは指定無し)
        int shortageQty =
                super.allocateStock(fixedLoc.getConsignorCode(), fixedLoc.getItemCode(), "", allocateQty, fixedLoc,
                        AllocatePriority.REPLENISHMENT_AREA_TYPE_REPLENISHMENT_AREA);

        // 出庫に必要な数量よりも引当てた数量が少なかった場合、補充欠品情報を作成する
        if (shortageQty > 0)
        {
            insertReplenishShortage(fixedLoc, allocateQty, shortageQty, getPattern());
        }

        // 要求数の初期化
        resetRequireQty();

        return this.getShortageQty();
    }

    /**
     * 要求数を初期化します
     */
    private void resetRequireQty()
    {
        _isRequire = false;
        _requireQty = 0;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 補充欠品フラグを返します。
     * 出庫での欠品数に対し、補充数量が不足していた場合にtrueを返します。
     * @return 欠品か否か
     */
    public boolean hasShortage()
    {
        if (super.getShortageQty() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 外部のクラスから引当の要求数をセットします。
     * 
     * @param reqQty 要求数
     */
    public void setRequireQty(int reqQty)
    {
        _requireQty = reqQty;
        _isRequire = true;
    }

    /**
     * 補充開始単位キーを返します。
     * @return 補充開始単位キーを返します。
     */
    public String getStartUnitKey()
    {
        return _startUnitKey;
    }


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 引当対象の在庫情報を検索します。<BR>
     * 荷主コード、商品コード、ロットNo.、をもとに<BR>
     * 対象引当パターンの指定された補充元エリア区分の在庫を検索します。<BR>
     * 検索順は、「引当パタンの優先順位・ロット（空白のものを先に引当てる）・入庫日時」で検索します。<BR>
     * 対象データが存在しない場合はnullを返します。<BR>
     * 又、検索した在庫がASRSの在庫の場合は、紐つくASRSの棚情報の状態が使用可であり、かつ<BR>
     * アクセス可である在庫のみ取得対象とします。<BR>
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param lotNo ロットNo.
     * @param replenishmentAreaType 引当を行うエリア区分
     * @return 検索取得した在庫情報
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected Stock[] searchStocks(String consignorCode, String itemCode, String lotNo, String replenishmentAreaType)
            throws ReadWriteException
    {
        DirectDBFinder ddbfinder = null;
        
        try
        {
            ddbfinder = new DefaultDDBFinder(getConnection(), new Stock());
            
            super.searchStocks(ddbfinder, consignorCode, itemCode, lotNo, replenishmentAreaType);
    
            List<Stock> stkList = new ArrayList<Stock>();
            
            while (ddbfinder.hasNext())
            {
                Stock[] stocks = (Stock[])ddbfinder.getEntities(100);
                for (Stock stock : stocks)
                {
                    if (stock.getAreaNo().equals(_fixed.getAreaNo()))
                    {
                        continue;
                    }
        
                    stkList.add(stock);
                }
            }
    
            if (stkList.size() == 0)
            {
                return null;
            }
            
            return stkList.toArray(new Stock[stkList.size()]);
        }
        finally
        {
            if (ddbfinder != null)
            {
                ddbfinder.close();
            }
        }
    }


    /**
     * 作業種別をセットする
     */
    protected void setJobType()
    {
        setJobType(SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT);
    }

    /**
     * 引当要求数を求めます。<BR>
     * 呼び出し元から数量の指定がない場合は、(補充点 - 棚にある在庫数)を返します。
     * 
     * @param toLocInfo 補充先
     * @return 引当要求数
     * @throws ReadWriteException DBエラー時に通知
     * @throws NoPrimaryException DB検索時に１件以上データがあった場合に通知
     */
    protected int calcRequireQty(FixedLocateInfo toLocInfo)
            throws ReadWriteException,
                NoPrimaryException
    {
        if (_isRequire)
        {
            return _requireQty;
        }
        else
        {
            _stkKey.clear();
            // collect
            _stkKey.setStockQtyCollect("sum");
            // where
            _stkKey.setConsignorCode(toLocInfo.getConsignorCode());
            _stkKey.setItemCode(toLocInfo.getItemCode());
            _stkKey.setAreaNo(toLocInfo.getAreaNo());
            _stkKey.setLocationNo(toLocInfo.getLocationNo());

            Stock[] stks = (Stock[])_stkh.find(_stkKey);

            // 最大保管量 - 棚にある在庫数
            int allocateQty = toLocInfo.getReplenishmentQty() - stks[0].getStockQty();

            return allocateQty;
        }

    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 補充欠品情報を新規作成します。
     * 
     * @param toloc 補充先の固定棚情報
     * @param planQty 補充予定数
     * @param shortageQty 不足数
     * @param pattern 今回の引当で使用した引当パターンNo.
     * @throws ReadWriteException DBエラーがあった場合に通知します。
     * @throws DataExistsException すでに同じ補充欠品情報があった場合に通知します。
     */
    private void insertReplenishShortage(FixedLocateInfo toloc, int planQty, int shortageQty, String pattern)
            throws ReadWriteException,
                DataExistsException
    {
        ReplenishShortage rs = new ReplenishShortage();

        rs.setStartUnitKey(_startUnitKey);
        rs.setConsignorCode(toloc.getConsignorCode());
        rs.setItemCode(toloc.getItemCode());
        rs.setAreaNo(toloc.getAreaNo());
        rs.setLocationNo(toloc.getLocationNo());
        rs.setReplenishmentQty(toloc.getReplenishmentQty());
        rs.setRate(_rate);
        rs.setPlanQty(planQty);
        rs.setShortageQty(shortageQty);
        rs.setAllocateNo(pattern);
        rs.setRegistPname(getCallerName());
        rs.setLastUpdatePname(getCallerName());

        _rsh.create(rs);

    }


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PlannedReplenishOperator.java 6285 2009-12-01 10:48:02Z kishimoto $";
    }

	@Override
	protected void createWork(AsrsInParameter param, Stock stk, int allocateQty) throws CommonException
	{
		throw new ScheduleException("This method is not implemented.");
	}


}
