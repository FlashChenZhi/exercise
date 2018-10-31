// $Id: EmergencyReplenishOperator.java 5616 2009-11-10 11:10:18Z shibamoto $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AllocatePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.Entity;


/**
 * 欠品情報に対する補充引当を行うクラスです。<br>
 *
 *
 * @version $Revision: 5616 $, $Date: 2009-11-10 20:10:18 +0900 (火, 10 11 2009) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: shibamoto $
 */
public class EmergencyReplenishOperator
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
     * 出庫の不足数
     */
    private int _retShortQty = 0;

    /**
     * 外部からの引当要求があったか否かを判別するフラグ
     */
    private boolean _isRequire = false;

    /**
     * 外部からの引当要求数
     */
    private int _requireQty = 0;

    /**
     * 欠品情報ハンドラ
     */
    private ShortageInfoHandler _sih = null;

    private ShortageInfoSearchKey _siKey = null;

    private ShortageInfoAlterKey _siAltKey = null;

    /**
     * 商品固定棚情報
     */
    private FixedLocateInfoHandler _fixh = null;

    private FixedLocateInfoSearchKey _fixKey = null;

    /**
     * 引当パターンマスタハンドラ
     */
    private AllocatePriorityHandler _alloch = null;
    private AllocatePrioritySearchKey _allocKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param pattern 引当パタンNo.
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public EmergencyReplenishOperator(Connection conn, String pattern, Class caller)
            throws CommonException
    {
        super(conn, pattern, caller);

        _sih = new ShortageInfoHandler(conn);
        _siKey = new ShortageInfoSearchKey();
        _siAltKey = new ShortageInfoAlterKey();

        _fixh = new FixedLocateInfoHandler(conn);
        _fixKey = new FixedLocateInfoSearchKey();
        
        _alloch = new AllocatePriorityHandler(conn);
        _allocKey = new AllocatePrioritySearchKey();
        
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業種別をセットする
     */
    protected void setJobType()
    {
        setJobType(SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT);
    }

    /**
     * 欠品情報より補充引当を行います。<BR>
     * トランザクションの確定を行う単位でデータをわたしてください<BR>
     * 
     * @param ent 欠品情報(荷主コードと商品コードで集約した欠品情報)
     * @return 引当処理での欠品数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public int allocate(Entity ent)
            throws CommonException
    {
        ShortageInfo sinfo = (ShortageInfo)ent;

        // 欠品数を初期化
        _retShortQty = sinfo.getShortageQty();

        // 既に未完了の補充作業情報が存在する場合今回は補充作業を作成しない
        if (hasMoveWorkInfo("", sinfo.getConsignorCode(), sinfo.getItemCode()))
        {
            // 欠品情報の補充区分を補充指示済みに更新
            updReplenishmentInstructed(sinfo.getConsignorCode(), sinfo.getItemCode(), sinfo.getStartUnitKey());
            return this.getShortageQty();
        }

        // 補充先の固定棚情報を取得
        Entity toLocInfo = getFixedLocateInfo(sinfo.getConsignorCode(), sinfo.getItemCode());

        // 補充点と欠品数の大きいほうを引き当てる
        int allocateQty = calcRequireQty(toLocInfo, sinfo);

        // 在庫引当(ロットは指定無し)
        if (super.allocateStock(sinfo.getConsignorCode(), sinfo.getItemCode(), "", allocateQty, toLocInfo,
                AllocatePriority.REPLENISHMENT_AREA_TYPE_REPLENISHMENT_AREA) > 0)
        {
            // 出庫に必要な数量よりも引当てた数量が少なかった場合、欠品情報を更新する
            if (this.getShortageQty() > 0)
            {
                updReplenishmentOff(sinfo, this.getShortageQty());
            }
        }

        // 要求数を初期化
        _isRequire = false;
        _requireQty = 0;

        return this.getShortageQty();
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
        if (_retShortQty - super.getAllocatedQty() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 引当処理での欠品数を返します。
     * 
     * @return 欠品数
     */
    public int getShortageQty()
    {
        if (_retShortQty - super.getAllocatedQty() < 0)
        {
            return 0;
        }
        return _retShortQty - super.getAllocatedQty();
    }

    /**
     * 外部のクラスから引当の要求数をセットします。
     * 
     * @param reqQty 要求数
     */
    public void setRequireQty(int reqQty)
    {
        _isRequire = true;
        _requireQty = reqQty;
    }


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 引当要求数を求めます。
     * @param toLocInfo 補充先
     * @param plan 欠品情報
     * @return 引当要求数
     * @throws ReadWriteException DBエラー時に通知
     * @throws NoPrimaryException DB検索時に１件以上データがあった場合に通知
     */
    protected int calcRequireQty(Entity toLocInfo, ShortageInfo plan)
            throws ReadWriteException,
                NoPrimaryException
    {
        if (_isRequire)
        {
            return _requireQty;
        }
        else
        {
            if (toLocInfo == null || toLocInfo instanceof Locate)
            {
                return plan.getShortageQty();
            }

            FixedLocateInfo info = (FixedLocateInfo)toLocInfo;

            // 補充し出庫した結果、補充点と同じ在庫数になるように補充数を求める
            // 今回欠品 + 補充点
            int allocateQty = plan.getShortageQty() + info.getReplenishmentQty();

            return allocateQty;
        }

    }

    /**
     * 欠品情報の補充区分を補充指示済みに更新します。<BR>
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param startUnitKey 出庫開始キー
     * @throws NotFoundException 更新対象データが存在しない場合にスローされます。
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    private void updReplenishmentInstructed(String consignorCode, String itemCode, String startUnitKey)
            throws NotFoundException,
                ReadWriteException
    {
        _siAltKey.clear();
        _siAltKey.updateReplenishmentFlag(SystemDefine.REPLENISHMENT_FLAG_INSTRUCTED);
        _siAltKey.updateReplenishmentQty(0);
        _siAltKey.setStartUnitKey(startUnitKey);
        _siAltKey.setConsignorCode(consignorCode);
        _siAltKey.setItemCode(itemCode);

        _sih.modify(_siAltKey);
    }

    /**
     * 欠品情報の補充区分を補充なしに更新します。<BR>
     * 
     * @param siInfo 欠品情報
     * @param replenishShortageQty 補充欠品数
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws NotFoundException 更新対象の欠品情報が存在しない場合にスローされます。
     */
    private void updReplenishmentOff(ShortageInfo siInfo, int replenishShortageQty)
            throws ReadWriteException,
                NotFoundException
    {
        _siKey.clear();
        // set where
        _siKey.setStartUnitKey(siInfo.getStartUnitKey());
        _siKey.setConsignorCode(siInfo.getConsignorCode());
        _siKey.setItemCode(siInfo.getItemCode());

        // set order(バッチの降順、オーダーの降順で補充欠品を設定)
        _siKey.setBatchNoOrder(false);
        _siKey.setOrderNoOrder(false);
        _siKey.setPlanUkeyOrder(false);

        ShortageInfo[] sInfos = (ShortageInfo[])_sih.find(_siKey);

        int shortageQty = replenishShortageQty;

        for (ShortageInfo shortage : sInfos)
        {
            int updQty = shortageQty;
            if (updQty > shortage.getShortageQty())
            {
                updQty = shortage.getShortageQty();
            }

            _siAltKey.clear();
            _siAltKey.setPlanUkey(shortage.getPlanUkey());
            _siAltKey.setLoadUnitKey(shortage.getLoadUnitKey());
            _siAltKey.setStartUnitKey(shortage.getStartUnitKey());
            // 補充ありで欠品情報を作成しているので減算する
            _siAltKey.updateReplenishmentQtyWithColumn(ShortageInfo.REPLENISHMENT_QTY, BigDecimal.valueOf(-1 * updQty));
            // 全く補充できなかった場合(更新後の補充数がゼロ)補充なしに更新
            // 1つでも更新があった場合は、補充ありのままとする
            if (shortage.getReplenishmentQty() - updQty == 0)
            {
                _siAltKey.updateReplenishmentFlag(SystemDefine.REPLENISHMENT_FLAG_OFF); // 補充無し
            }
            _sih.modify(_siAltKey);

            shortageQty -= updQty;
            if (shortageQty == 0)
            {
                break;
            }
        }
    }

    /**
     * 補充先の固定棚情報を取得します。<BR>
     * 指定された荷主コード、商品コードの固定棚情報を返します。<BR>
     * 固定棚情報のエリアは、引当パターンに通常エリアとして登録されており、
     * かつエリアマスタに固定棚管理として登録されている必要があります。<BR>
     * 対象の固定棚情報が複数件存在する場合は優先順位順に取得します。<BR>
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @return 補充先の固定棚情報
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    private Entity getFixedLocateInfo(String consignorCode, String itemCode)
            throws ReadWriteException,
                InvalidDefineException
    {
        _fixKey.clear();
        _allocKey.clear();

        // fixed = DmFixedLocateInfo alloc = DmAllocatePatternNo
        // fixed.consignor_code = consignorCcde and fixed.item_code = itemCode and
        // alloc.allocate_no = 画面指定の引当パターンNo. and
        // alloc.replaniehment_area_type = 通常エリア and
        // alloc.allocate_are = fixed.area_no
        
        // set where
        _fixKey.setConsignorCode(consignorCode);
        _fixKey.setItemCode(itemCode);
        _allocKey.setAllocateNo(getPattern());
        _allocKey.setReplenishmentAreaType(AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA);
        _fixKey.setKey(_allocKey);

        // set join
        _fixKey.setJoin(FixedLocateInfo.AREA_NO, AllocatePriority.ALLOCATE_AREA);

        // set order
        _fixKey.setOrder(AllocatePriority.ALLOCATE_PRIORITY, true);
        _fixKey.setOrder(AllocatePriority.ALLOCATE_AREA, true);
        _fixKey.setOrder(FixedLocateInfo.LOCATION_NO, true);

        FixedLocateInfo[] fixs = (FixedLocateInfo[])_fixh.find(_fixKey);

        // 固定棚マスタに登録されていない場合は、デフォルトの補充先を取得する
        if (fixs == null || fixs.length == 0)
        {
            return getDestLocateFromPattern();
        }
        return fixs[0];
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 画面指定の引当パターンから補充先の棚情報を作成します。
     * 引当パターンの優先順の高い固定棚エリアとデフォルトの棚No.をセットし、返します。
     * 
     * @return 予定エリア
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 商品固定棚情報に不整合データが存在した場合にスローされます。
     */
    private Locate getDestLocateFromPattern()
            throws ReadWriteException,
                InvalidDefineException
    {
        _allocKey.clear();
        // set where
        // 今回の引当パターンに含まれている通常エリアで
        _allocKey.setAllocateNo(getPattern());
        _allocKey.setReplenishmentAreaType(AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA);
        
        // エリア種別が固定棚のもの
        _allocKey.setJoin(AllocatePriority.ALLOCATE_AREA, Area.AREA_NO);
        _allocKey.setKey(Area.LOCATION_TYPE, Area.LOCATION_TYPE_FIXED);
        
        // set order
        _allocKey.setAllocatePriorityOrder(true);
        _allocKey.setAllocateAreaOrder(true);

        AllocatePriority[] allocs = (AllocatePriority[])_alloch.find(_allocKey);

        if (allocs == null || allocs.length == 0)
        {
            throw new InvalidDefineException("AllocatePriority is invalid");
        }
        // 優先順位の高いエリアのエリアNo.を取得
        Locate ret = new Locate();
        ret.setAreaNo(allocs[0].getAllocateArea());
        ret.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
        return ret;
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
        return "$Id: EmergencyReplenishOperator.java 5616 2009-11-10 11:10:18Z shibamoto $";
    }

	@Override
	protected void createWork(AsrsInParameter param, Stock stk, int allocateQty) throws CommonException
	{
		throw new ScheduleException("This method is not implemented.");
	}


}
