// $Id: ReceivingAreaOperator.java 5029 2009-09-18 05:53:17Z shibamoto $
package jp.co.daifuku.wms.receiving.operator;

import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.controller.RftController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkerResultController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.receiving.schedule.ReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * Designer : G.Muhl <BR>
 * Maker : G.Muhl <BR>
 * <BR>
 * 
 * <BR>
 * 商品コードを元に商品固定棚より入荷エリアの検索を行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/26</TD><TD>Y.Miyake</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5029 $, $Date: 2009-09-18 14:53:17 +0900 (金, 18 9 2009) $
 * @author  taki
 * @author  Last commit: $Author: shibamoto $
 */
public class ReceivingAreaOperator
        extends AbstractOperator
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
     * Receiving area handler
     */
    private AreaHandler _AreaHndl;

    /**
     * Receiving area search key
     */
    private AreaSearchKey _AreaSKey;

    /**
     * Master Supplier controller
     */
    private AreaController _AreaCtrl;

    /**
     * Fixed location info handler
     */
    private FixedLocateInfoHandler _FixH;

    /**
     * Fixed Location Info Search Key
     */
    FixedLocateInfoSearchKey _FixKey;

    StockHandler _StkHandler;

    StockSearchKey _StksKey;

    // Location master information controller
    LocateController _LController;

    // Receiving operater
    ReceivingOperator _StOperator;

    // Work Information Controller
    RftController _RController;

    /* System Definition Controller */
    WarenaviSystemController _WController;

    WorkerResultController _WOperator;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     */
    public ReceivingAreaOperator(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);

        // Generates DB handler and controller
        _AreaHndl = new AreaHandler(conn);
        _AreaSKey = new AreaSearchKey();
        _AreaCtrl = new AreaController(conn, this.getClass());
        _FixH = new FixedLocateInfoHandler(conn);
        _FixKey = new FixedLocateInfoSearchKey();
        _StkHandler = new StockHandler(conn);
        _StksKey = new StockSearchKey();
        _LController = new LocateController(conn, this.getClass());
        _StOperator = new ReceivingOperator(conn, this.getClass());
        _RController = new RftController(conn, this.getClass());
        _WController = new WarenaviSystemController(conn, this.getClass());
        _WOperator = new WorkerResultController(conn, this.getClass());
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 商品コードを元に商品固定棚に登録されている入荷エリアの一覧を取得します。<BR>
     * 
     * @param inquiryParams 入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public OutParameter[] inquirySCH(InParameter inquiryParams)
            throws ReadWriteException,
                NotFoundException
    {
        // set key for searching area information
        _AreaSKey.setAreaNoCollect();
        _AreaSKey.setAreaNameCollect();
        _AreaSKey.setReceivingAreaCollect();
        _AreaSKey.setAreaTypeCollect();

        // set groupings to return distinct results
        _AreaSKey.setAreaNoGroup();
        _AreaSKey.setAreaNameGroup();
        _AreaSKey.setReceivingAreaGroup();
        _AreaSKey.setAreaTypeGroup();


        // get item code from in parameters
        ReceivingInParameter vpReceivingInParam = (ReceivingInParameter)inquiryParams;
        String itemCode = vpReceivingInParam.getItemCode();
        String consignorCode = vpReceivingInParam.getConsignorCode();

        // collect area_no from fixedLocation table
        _FixKey.setAreaNoCollect();

        // where item_code in record = given item code
        _FixKey.setKey(FixedLocateInfo.ITEM_CODE, itemCode, "=", "", "", true);
        _FixKey.setKey(FixedLocateInfo.CONSIGNOR_CODE, consignorCode, "=", "", "", true);

        // for results
        Area[] areaInfo = null;
        boolean foundRecvArea = false;
        // search 
        FixedLocateInfo[] fixedAreaInfo = (FixedLocateInfo[])_FixH.find(_FixKey);
        if (fixedAreaInfo != null && fixedAreaInfo.length > 0)
        {
            foundRecvArea = true;
            // get first areaNumber found
            String fixedAreaNo = fixedAreaInfo[0].getAreaNo();

            //          temp area = null & area# = fixedAreaNo.
            _AreaSKey.setKey(Area.RECEIVING_AREA, null, "<>", "", "", true);
            _AreaSKey.setKey(Area.AREA_NO, fixedAreaNo, "=", "", "", true);

            // Sorting order
            _AreaSKey.setAreaNoOrder(true);

            areaInfo = (Area[])_AreaHndl.find(_AreaSKey);

            if (areaInfo == null || areaInfo.length == 0)
            {
                foundRecvArea = false;
            }
        }
        // item code is not in dedicated location - look if exists in stock
        else if (fixedAreaInfo == null || fixedAreaInfo.length == 0)
        {
            foundRecvArea = true;
            //          get area location from stock

            _StksKey.setAreaNoCollect();
            _StksKey.setKey(Stock.ITEM_CODE, itemCode, "=", "", "", true); //where item_code = given item code
            _StksKey.setKey(Stock.CONSIGNOR_CODE, consignorCode, "=", "", "", true);
            _StksKey.setStorageDateOrder(false);
            _StksKey.setAreaNoOrder(true);

            //          stock search
            Stock[] stockInfo = (Stock[])_StkHandler.find(_StksKey);
            if (stockInfo != null && stockInfo.length > 0)
            {
                String stockAreaNo = stockInfo[0].getAreaNo();
                _AreaSKey.setJoin(Stock.AREA_NO, Area.AREA_NO);
                _AreaSKey.setKey(Area.AREA_NO, stockAreaNo, "=", "(", "", true);
                _AreaSKey.setKey(Area.RECEIVING_AREA, null, "<>", "", ")", false); //where receiving area field is not null
                _AreaSKey.setKey(Area.AREA_NO, stockAreaNo, "=", "(", "", true);
                _AreaSKey.setKey(Area.AREA_TYPE, SystemDefine.AREA_TYPE_RECEIVING, "=", "", ")", false);

                // Sorting order
                _AreaSKey.setAreaNoOrder(true);

                areaInfo = (Area[])_AreaHndl.find(_AreaSKey);
                if (areaInfo == null || areaInfo.length == 0)
                {
                    foundRecvArea = false;
                }
            }
            else if (stockInfo == null || stockInfo.length == 0)// stockInfo == 0 or null
            {
                foundRecvArea = false;
            }
        }
        if (!foundRecvArea)
        {// not in fixedLocation, not in Stock - default
            _AreaSKey.clearKeys();
            _AreaSKey.clearJoin();

            _AreaSKey.setKey(Area.RECEIVING_AREA, null, "<>", "", "", false); //where receiving area field is not null

            // Sorting order
            _AreaSKey.setAreaNoOrder(true);

            areaInfo = (Area[])_AreaHndl.find(_AreaSKey);
            if (areaInfo == null || areaInfo.length == 0)
            {
                throw new NotFoundException();
            }
            // item does not exist in stock - send default receiving location
        }

        // Parse output into out parameter
        ReceivingOutParameter[] outParam = new ReceivingOutParameter[areaInfo.length];
        for (int i = 0; i < areaInfo.length; i++)
        {
            outParam[i] = new ReceivingOutParameter();

            if (areaInfo[i].getAreaType().equals(SystemDefine.AREA_TYPE_RECEIVING)) //should never happen because I didn't include receiving area, only areas WITH receiving area
            {
                outParam[i].setAreaNo("");
                outParam[i].setAreaName("");
                outParam[i].setReceivingAreaNo(areaInfo[i].getAreaNo());
                outParam[i].setReceivingAreaName(areaInfo[i].getAreaName());
            }
            else
            {
                outParam[i].setAreaNo(areaInfo[i].getAreaNo());
                outParam[i].setAreaName(areaInfo[i].getAreaName());
                outParam[i].setReceivingAreaNo(areaInfo[i].getReceivingArea());
                outParam[i].setReceivingAreaName(_AreaCtrl.getAreaName(areaInfo[i].getReceivingArea()));
            }
        }

        return outParam;
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


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns a revision of this class.
     * @return String of a revision
     */
    public static String getVersion()
    {
        return "$Id: ReceivingAreaOperator.java 5029 2009-09-18 05:53:17Z shibamoto $";
    }
}
