// $Id: Id1031Process.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.communication.rft.IdProcess;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.rft.IdExceptionHandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Miyake <BR>
 * Maker : Y.Miyake <BR>
 * <BR>
 * RFTからのマスタ画像登録確定要求送信(ID1031)に対し、送信電文の分解、画像ファイルの登録、応答電文の組立を行う。 <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/04/09</TD><TD>Y.Miyake</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  miyake
 * @author  Last commit: $Author: arai $
 */
public class Id1031Process
        extends IdProcess
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
     * コンストラクタ<BR>
     */
    public Id1031Process()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *  マスタ画像登録確定要求処理
     * @param rdt 受信電文
     * @param sdt 送信電文
     * @throws Exception 何らかの例外が発生した場合に通知されます。
     * @see jp.co.daifuku.wms.base.communication.rft.IdProcess#processReceivedId(byte[], byte[])
     */
    @Override
    public void processReceivedId(byte[] rdt, byte[] sdt)
            throws Exception
    {
        // 要求電文のインスタンスを生成
        RftId1031 rftId1031 = (RftId1031)PackageManager.getObject("RftId1031", getClass());
        rftId1031.setReceiveMessage(rdt);

        // 応答電文のインスタンスを生成
        RftId6031 rftId6031 = (RftId6031)PackageManager.getObject("RftId6031", getClass());

        try
        {
            // 要求電文の内容を入力パラメータクラスにセット
            PCTMasterInParameter pctInParam = new PCTMasterInParameter();
            
            // 要求電文の内容をパラメータクラスにセット
            //  (端末号機No.)
            pctInParam.setTerminalNo(rftId1031.getRftNo());
            //  (荷主コード)
            pctInParam.setConsignorCode(rftId1031.getConsignorCode());
            //  (商品コード)
            pctInParam.setItemCode(rftId1031.getItemCode());
            //  (ロット入数)
            pctInParam.setEnteringQty(rftId1031.getLotEnteringQty());
            //  (画像ファイル名)
            pctInParam.setFileName(rftId1031.getPictureFileName());

            // スケジュールクラスのインスタンスを生成
            Id1031SCH id1031sch = (Id1031SCH)PackageManager.getObject("Id1031SCH", getClass());
            id1031sch.setConnection(_wConn);

            // スケジュールの確定処理を呼び出す
            id1031sch.completeSCH(pctInParam, null);

            // 応答フラグとエラー詳細をセット
            rftId6031.setAnsCode(RftId6031.ANS_CODE_NORMAL);
            rftId6031.setErrDetails(RftId6031.ErrorDetails.NORMAL);
        }
        catch (Exception ex)
        {
            // 想定外のIdSchException発生時
            // エラーコード取得
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId1031.getRftNo());
            // 応答フラグセット
            rftId6031.setAnsCode(answer.get(0));
            // エラー詳細セット
            rftId6031.setErrDetails(answer.get(1));
        }

        // 必須の応答電文項目をセット
        //  (STX)
        rftId6031.setSTX();
        //  (SEQ)
        rftId6031.setSEQ(0);
        //  (ID)
        rftId6031.setID(RftId6031.ID);
        //  (端末送信時間)
        rftId6031.setRftSendDate(rftId1031.getRftSendDate());
        //  (サーバー送信時間)
        rftId6031.setServSendDate(new Date());
        //  (端末号機No.)
        rftId6031.setRftNo(rftId1031.getRftNo());
        //  (応答フラグ) //このポイント以前で応答フラグはセットしておく事
        if (StringUtil.isBlank(rftId6031.getAnsCode()))
        {
            rftId6031.setAnsCode(RftId6031.ANS_CODE_ERROR);
            rftId6031.setErrDetails(RftId6031.ErrorDetails.INTERNAL_ERROR);
            // 6026117=<{0}号機> ID対応処理例外。致命的エラー。応答フラグ空白で応答電文を送信しようとした為、強制的にエラー応答をセットしました。
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026117, rftId1031.getRftNo()), getClass().getSimpleName());
        }
        //  (ETX)
        rftId6031.setETX();

        // トランザクションを確定
        try
        {
            if (RftId6031.ANS_CODE_NORMAL.equals(rftId6031.getAnsCode()))
            {
                try
                {
                    //コミット
                    _wConn.commit();
                }
                catch (Exception ex)
                {
                    //ロールバック
                    _wConn.rollback();
                    throw ex;
                }
            }
            else
            {
                //ロールバック
                _wConn.rollback();
            }
        }
        catch (Exception ex)
        {
            // 想定外のIdSchException発生時
            // エラーコード取得
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId1031.getRftNo());
            // 応答フラグセット
            rftId6031.setAnsCode(answer.get(0));
            // エラー詳細セット
            rftId6031.setErrDetails(answer.get(1));
        }

        // 応答電文の組立(sdtの作成)
        rftId6031.getSendMessage(sdt);
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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: Id1031Process.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
