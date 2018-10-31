// $Id: Id1010Process.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.pcart.master.schedule.PCTMasterOutParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.communication.rft.IdProcess;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.IdExceptionHandler;
import jp.co.daifuku.wms.base.rft.IdFileHandler;


/**
 * RFTからのマスタ画像登録商品問合せ(ID1010)に対し、送信電文の分解、応答電文の組立を行う。 <BR>
 * 対象が複数件該当した場合には、一覧ファイルの作成を行う。<BR>
 * 該当したデータに画像ファイルがある場合は、画像ファイルも作成する。<BR>
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  kojima
 * @author  Last commit: $Author: arai $
 */

public class Id1010Process
        extends IdProcess
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * 画像ファイル名
     */
    public static final String PICTURE_FILE_NAME = "Picture.jpg";

    
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
     * コンストラクタ<BR>
     */
    public Id1010Process()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     *  マスタ画像登録商品問合せ処理
     * @param rdt 受信電文
     * @param sdt 送信電文
     * @throws Exception 何らかの例外が発生した場合に通知されます。
     * @see jp.co.daifuku.wms.base.communication.rft.IdProcess#processReceivedId(byte[], byte[])
     */
    @Override
    public void processReceivedId(byte[] rdt, byte[] sdt)
            throws Exception
    {
        // 問合せ電文のインスタンスを生成
        RftId1010 rftId1010 = (RftId1010)PackageManager.getObject("RftId1010", getClass());
        rftId1010.setReceiveMessage(rdt);

        // 応答電文のインスタンスを生成
        RftId6010 rftId6010 = (RftId6010)PackageManager.getObject("RftId6010", getClass());

        try
        {
            // 商品問合せ用入力パラメータクラス配列を生成
            PCTMasterInParameter[] pctInParam = new PCTMasterInParameter[1];
            pctInParam[0] = new PCTMasterInParameter();

            // 問合せ電文の内容をパラメータクラスにセット
            //  (端末号機No.)
            pctInParam[0].setTerminalNo(rftId1010.getRftNo());
            //  (荷主コード)
            pctInParam[0].setConsignorCode(rftId1010.getConsignorCode());
            //  (スキャン商品コード)
            pctInParam[0].setScanCode(rftId1010.getScanItemCode());
            //  (商品コード)
            pctInParam[0].setItemCode(rftId1010.getItemCode());
            //  (ロット入数)
            pctInParam[0].setLotEnteringQty(rftId1010.getLotEnteringQty());
            //  (画像ファイル名)
            pctInParam[0].setFileName(PICTURE_FILE_NAME);
            
            // スケジュールクラスのインスタンスを生成
            Id1010SCH id1010sch = (Id1010SCH)PackageManager.getObject("Id1010SCH", getClass());
            id1010sch.setConnection(_wConn);

            // スケジュールの問合せメソッドを呼び出し、データを取得
            PCTMasterOutParameter[] rftOutParam = (PCTMasterOutParameter[])id1010sch.inquirySCH(pctInParam);

            // 取得した開始データを、応答電文にセット
            rftId6010 = setIdFromOutParameter(rftOutParam[0], rftId1010, rftId6010);

            // 応答フラグとエラー詳細をセット
            rftId6010.setAnsCode(RftId6010.ANS_CODE_NORMAL);
            rftId6010.setErrDetails(RftId6010.ErrorDetails.NORMAL);
            
        }
        catch (NotFoundException ex)
        {
            // 該当データ無し
            rftId6010.setAnsCode(RftId6010.ANS_CODE_NULL);
            rftId6010.setErrDetails(RftId6010.ErrorDetails.NORMAL);
        }
        catch (DuplicateOperatorException ex)
        {
            try
            {
                String errCode = ex.getErrorCode();
                // 商品コード重複
                if (OperatorException.ERR_ITEM_DUPLICATED.equals(errCode))
                {
                    // 例外クラスから重複データを取得
                    List<Object> detailList = ex.getDetailList();
                    // 商品複時の処理
                    rftId6010 = duplicateItemProcess(detailList, rftId1010.getRftNo(), rftId6010);
                    // 応答フラグとエラー詳細をセット
                    rftId6010.setAnsCode(RftId6010.ANS_CODE_SOME_DATA);
                    rftId6010.setErrDetails(RftId6010.ErrorDetails.ITEM_DUPLICATED);
                }
                else
                {
                    throw ex;
                }

            }
            catch (IOException e)
            {
                // ファイルI/Oエラー
                rftId6010.setAnsCode(RftId6010.ANS_CODE_ERROR);
                rftId6010.setErrDetails(RftId6010.ErrorDetails.I_O_ERROR);
            }
            catch (Exception ex2)
            {
                List<String> answer = IdExceptionHandler.getResponceFlag(ex2, getClass(), rftId1010.getRftNo());
                rftId6010.setAnsCode(answer.get(0));
                rftId6010.setErrDetails(answer.get(1));
            }
        }
        catch (Exception ex)
        {
            // 想定外の例外発生時
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId1010.getRftNo());
            rftId6010.setAnsCode(answer.get(0));
            rftId6010.setErrDetails(answer.get(1));
        }

        /* 必須の応答電文項目をセット */
        //  (STX)
        rftId6010.setSTX();
        //  (SEQ)
        rftId6010.setSEQ(0);
        //  (ID)
        rftId6010.setID(RftId6010.ID);
        //  (端末送信時間)
        rftId6010.setRftSendDate(rftId1010.getRftSendDate());
        //  (SERV送信時間)
        rftId6010.setServSendDate(new Date());
        //  (端末号機No.)
        rftId6010.setRftNo(rftId1010.getRftNo());

        //  (応答フラグ) //このポイント以前で応答フラグはセットしておく事
        if (StringUtil.isBlank(rftId6010.getAnsCode()))
        {
            rftId6010.setAnsCode(RftId6010.ANS_CODE_ERROR);
            rftId6010.setErrDetails(RftId6010.ErrorDetails.INTERNAL_ERROR);
            // 6026117=<{0}号機> ID対応処理例外。致命的エラー。応答フラグ空白で応答電文を送信しようとした為、強制的にエラー応答をセットしました。
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026117, rftId1010.getRftNo()), getClass().getSimpleName());
        }
        //  (ETX)
        rftId6010.setETX();

        // 応答電文の組立(sdtの作成)
        rftId6010.getSendMessage(sdt);
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
     * 応答電文にデータをセットします。
     * パラメータで受け取った応答電文に、値をセットして返します。
     * 
     * @param outParam 開始データのセットされた出力パラメータクラス
     * @param rftId1010 要求電文
     * @param rftId6010 データをセットする応答電文
     * @return          応答電文
     */
    protected RftId6010 setIdFromOutParameter(PCTMasterOutParameter outParam, RftId1010 rftId1010, RftId6010 rftId6010)
    {
        //  (荷主コード)
        rftId6010.setConsignorCode(outParam.getConsignorCode());
        //  (スキャン商品コード)
        rftId6010.setScanItemCode(rftId1010.getScanItemCode());
        //  (商品コード)
        rftId6010.setItemCode(outParam.getItemCode());
        //  (商品名称)
        rftId6010.setItemName(outParam.getItemName());
        //  (JANコード)
        rftId6010.setJanCode(outParam.getJan());
        //  (ケースITF)
        rftId6010.setCaseItf(outParam.getItf());
        //  (ボールITF)
        rftId6010.setBundleItf(outParam.getBundleItf());
        //  (ロット入数)
        rftId6010.setLotEnteringQty(outParam.getLotEnteringQty());
        //  (メッセージ)
        rftId6010.setMessage(outParam.getMessage());
        //  (画像ファイル名)
        rftId6010.setPictureFileName(outParam.getFileName());
        //  (一覧ファイル名)
        rftId6010.setListFileName("");
        //  (ファイルレコード数)
        rftId6010.setFileRecordNumber(0);

        return rftId6010;
    }

    /**
     * 商品が重複したときの処理です。
     * 一覧ファイルを作成し、応答電文に必要なデータをセットします。
     * 
     * @param detailList    ファイルに出力するデータ
     * @param rftNo         RFT号機No.
     * @param rftId6010     データをセットする応答電文
     * @return      応答電文
     * @throws CommonException          何らかの例外が発生した場合に通知されます。
     * @throws IllegalAccessException   インスタンスの生成に失敗した場合に通知されます。
     * @throws IOException              ファイル入出力エラー発生時に通知されます。
     */
    protected RftId6010 duplicateItemProcess(List<Object> detailList, String rftNo, RftId6010 rftId6010)
            throws CommonException,
                IllegalAccessException,
                IOException
    {
        // エリア棚一覧ファイルのインスタンスを生成
        RftFileItem rftFileItem =
                (RftFileItem)PackageManager.getObject("RftFileItem", getClass());

        // 一覧ファイル名
        String filePathNameFull = rftFileItem.createSendFilePathNameFull(rftNo);
        String filePathNameId = rftFileItem.createSendFilePathNameId(rftNo);

        // データをセット
        for (int i = 0; i < detailList.size(); i++)
        {
            Object[] lineData = (Object[])detailList.get(i);

            // 商品コードをセット
            rftFileItem.setItemCode(i, (String)lineData[0]);
            // 商品名称をセット
            rftFileItem.setItemName(i, (String)lineData[1]);
            // ロット入数をセット
            rftFileItem.setLotEnteringQty(i, Integer.parseInt((String)lineData[2]));
        }
        // ファイルへ出力
        IdFileHandler.write(rftFileItem.exportFileData(), filePathNameFull);

        // 応答電文項目をセット
        //  (一覧ファイル名)
        rftId6010.setListFileName(filePathNameId);
        //  (ファイルレコード数)
        rftId6010.setFileRecordNumber(rftFileItem.getFileRecordTotal());

        return rftId6010;
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
        return "$Id: Id1010Process.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
//end of class


