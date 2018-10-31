// $Id: Id1010SCH.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

import java.io.File;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.base.controller.PCTItemController;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.pcart.master.schedule.PCTMasterOutParameter;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.AbstractIdSCH;
import jp.co.daifuku.wms.base.rft.IdSchException;
import jp.co.daifuku.wms.handler.SearchKey;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * PCT商品情報問合せスケジュール処理<BR>
 *
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  Kojima
 * @author  Last commit: $Author: arai $
 */
public class Id1010SCH
        extends AbstractIdSCH
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


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public Id1010SCH()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * PCT商品情報問合せを行います。<BR>
     * 
     * @param inquiryParams 問合せ条件
     * @return 取得データ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public OutParameter[] inquirySCH(InParameter[] inquiryParams)
            throws CommonException
    {
        PCTMasterInParameter inParam = (PCTMasterInParameter)inquiryParams[0];

        // 禁止文字チェック スキャンコード
        if (DisplayText.isPatternMatching(inParam.getScanCode()))
        {
            throw new IdSchException(IdSchException.PATTERN_NG);
        }

        //PCT商品マスタコントローラ
        PCTItemController itemc = new PCTItemController(_wConn, getClass());
        PCTItem[] item = null;
        if (StringUtil.isBlank(inParam.getItemCode()))
        {
            /* スキャン商品コードから、商品コードを取得し、その値で商品情報を検索 */
            String consignorCode = inParam.getConsignorCode();
            String scanCode = inParam.getScanCode();
            try
            {
                for (int i = 0; i < PCTItemController.MAX_SCAN_INDEX; i++)
                {
    
                    // use item code if scan index is 0.
                    String keyItem = (i == 0) ? scanCode
                                             : itemc.getItemCode(consignorCode, scanCode, i);
    
                    // item not found, then try next.
                    if (StringUtil.isBlank(keyItem))
                    {
                        continue;
                    }
    
                    // SearchKey定義
                    SearchKey skey = selectItem(consignorCode, keyItem);
    
                    // 商品情報の検索
                    item = (PCTItem[])new PCTItemHandler(_wConn).find(skey);
                    // 該当データがあった場合は、その時点でループを止める
                    if (!ArrayUtil.isEmpty(item))
                    {
                        if (item.length > 1)
                        {
                            throw new DuplicateOperatorException();
                        }
                        break;
                    }
                }
            }
            catch (DuplicateOperatorException ex)
            {
                List<String[]> dupList = itemc.getDupItems(consignorCode, scanCode);
                ex.setErrorCode(OperatorException.ERR_ITEM_DUPLICATED);
                ex.setDetail(dupList);
                throw ex;
            }
        }
        else
        {
            /* 商品コードが判明している場合、その値で商品情報を検索 */
            String consignorCode = inParam.getConsignorCode();
            String inItemCode = inParam.getItemCode();
            int enteringQty = inParam.getLotEnteringQty();
            
            // SearchKey定義
            SearchKey skey = selectItem(consignorCode, inItemCode, enteringQty);

            // 商品情報の検索
            item = (PCTItem[])new PCTItemHandler(_wConn).find(skey);
        }
        
        /* 該当する商品が、存在しない場合はNotFoundExceptionをスローする */
        if (ArrayUtil.isEmpty(item))
        {
            throw new NotFoundException();
        }

        /* 取得データを出力パラメータにセット */
        PCTMasterOutParameter returnParam = new PCTMasterOutParameter();
        returnParam.setConsignorCode(item[0].getConsignorCode());
        returnParam.setConsignorName((String)item[0].getValue(Consignor.CONSIGNOR_NAME));
        returnParam.setItemCode(item[0].getItemCode());
        returnParam.setItemName(item[0].getItemName());
        returnParam.setJan(item[0].getJan());
        returnParam.setItf(item[0].getItf());
        returnParam.setBundleItf(item[0].getBundleItf());
        returnParam.setLotEnteringQty(item[0].getLotEnteringQty());
        returnParam.setMessage(item[0].getInformation());

        /* 商品画像を取得し、画像ファイルを作成する */
        try
        {
            // ファイル名
            String filePath = createSendFilePathNameFull(inParam.getTerminalNo(), inParam.getFileName());
            // 画像取得＆ファイル作成
            boolean picRes = itemc.getItemPicture(item[0].getConsignorCode(), item[0].getItemCode(), item[0].getLotEnteringQty(), filePath);
            if (picRes)
            {
                String idPath = createSendFilePathNameId(inParam.getTerminalNo(), inParam.getFileName());
                returnParam.setFileName(idPath);
            }
            else
            {
                returnParam.setFileName("");
            }
        }
        catch (Exception e)
        {
            returnParam.setFileName("");
        }

        /* OutParameterの配列を作成 */
        PCTMasterOutParameter[] outParameter = new PCTMasterOutParameter[1];
        outParameter[0] = returnParam;
        return outParameter;
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
     * PCT商品マスタを検索するキーを作成します。
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param enteringQty ロット入数
     * @throws CommonException 例外が発生した場合に通知されます。
     * @return masterFlag マスタ管理の有無
     * 
     */
    protected PCTItemSearchKey selectItem(String consignorCode, String itemCode, int enteringQty)
            throws CommonException
    {
        // 商品情報検索
        PCTItemSearchKey sKey = new PCTItemSearchKey();

        /* 検索条件の指定 */
        // 荷主コード
        sKey.setConsignorCode(consignorCode);
        // 商品コード
        sKey.setItemCode(itemCode);
        // ロット入数
        sKey.setLotEnteringQty(enteringQty);

        // 結合条件
        // 荷主マスタ.荷主コード
        sKey.setJoin(PCTItem.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);

        // 検索項目
        // 荷主コード
        sKey.setConsignorCodeCollect();
        // 荷主名称
        sKey.setCollect(Consignor.CONSIGNOR_NAME);
        // 商品コード
        sKey.setItemCodeCollect();
        // 商品名称
        sKey.setItemNameCollect();
        // JANコード
        sKey.setJanCollect();
        // ケースITF
        sKey.setItfCollect();
        // ボールITF
        sKey.setBundleItfCollect();
        // ロット入数
        sKey.setLotEnteringQtyCollect();
        // ケース入数
        sKey.setEnteringQtyCollect();
        // ボール入数
        sKey.setBundleEnteringQtyCollect();
        // メッセージ
        sKey.setInformationCollect();

        return sKey;
    }

    /**
     * PCT商品マスタを検索するキーを作成します。
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @throws CommonException 例外が発生した場合に通知されます。
     * @return masterFlag マスタ管理の有無
     * 
     */
    protected PCTItemSearchKey selectItem(String consignorCode, String itemCode)
            throws CommonException
    {
        // 商品情報検索
        PCTItemSearchKey sKey = new PCTItemSearchKey();

        /* 検索条件の指定 */
        // 荷主コード
        sKey.setConsignorCode(consignorCode);
        // 商品コード
        sKey.setItemCode(itemCode);

        // 結合条件
        // 荷主マスタ.荷主コード
        sKey.setJoin(PCTItem.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);

        // 検索項目
        // 荷主コード
        sKey.setConsignorCodeCollect();
        // 荷主名称
        sKey.setCollect(Consignor.CONSIGNOR_NAME);
        // 商品コード
        sKey.setItemCodeCollect();
        // 商品名称
        sKey.setItemNameCollect();
        // JANコード
        sKey.setJanCollect();
        // ケースITF
        sKey.setItfCollect();
        // ボールITF
        sKey.setBundleItfCollect();
        // ロット入数
        sKey.setLotEnteringQtyCollect();
        // ケース入数
        sKey.setEnteringQtyCollect();
        // ボール入数
        sKey.setBundleEnteringQtyCollect();
        // メッセージ
        sKey.setInformationCollect();

        return sKey;
    }

    /**
     * ファイル名と号機から送信ファイルのパスを生成します。<BR>
     * また、対象のディレクトリが存在しない場合は、送信用と受信用のディレクトリを作成します。
     * 
     * @param rftNo     RFT号機
     * @param fileName  ファイル名
     * @return 送信ファイル名(パス付き)
     */
    protected String createSendFilePathNameFull(String rftNo, String fileName)
    {
        String filePathName = WmsParam.DAIDATA + createSendFilePathNameId(rftNo, fileName);
        return filePathName;
    }
    
    /**
     * ファイル名と号機からIDにセットする送信ファイルのパスを生成します。<BR>
     * 
     * @param rftNo     RFT号機
     * @param fileName  ファイル名
     * @return Idにセットする送信ファイル名(パス付き)
     */
    protected String createSendFilePathNameId(String rftNo, String fileName)
    {
        return WmsParam.RFTSEND + rftNo + File.separatorChar + fileName;
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
        return "$Id: Id1010SCH.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
