// $Id: Id0022Process.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.communication.rft.IdProcess;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.rft.IdExceptionHandler;
import jp.co.daifuku.wms.base.rft.IdFileHandler;
import jp.co.daifuku.wms.base.rft.RftInParameter;
import jp.co.daifuku.wms.base.rft.RftOutParameter;


/**
 * RFTからの荷主一覧問合せ(ID0022)に対し、要求電文の分解、応答電文の組立を行う。 <BR>
 * 開始対象が複数件該当した場合には、一覧ファイルの作成を行う。<BR>
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  kojima
 * @author  Last commit: $Author: rnakai $
 */

public class Id0022Process
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
    public Id0022Process()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     *  荷主一覧問合せ処理
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
        RftId0022 rftId0022 = (RftId0022)PackageManager.getObject("RftId0022", getClass());
        rftId0022.setReceiveMessage(rdt);

        // 応答電文のインスタンスを生成
        RftId5022 rftId5022 = (RftId5022)PackageManager.getObject("RftId5022", getClass());

        try
        {
            // 荷主用入力パラメータクラス配列を生成
            RftInParameter[] rftInParam = new RftInParameter[1];
            rftInParam[0] = new RftInParameter();

            // 要求電文の内容をパラメータクラスにセット
            // 端末号機No.
            rftInParam[0].setTerminalNo(rftId0022.getRftNo());
            // ユーザID
            rftInParam[0].setUserId(rftId0022.getUserId());

            // スケジュールクラスのインスタンスを生成
            Id0022SCH id0022SCH = (Id0022SCH)PackageManager.getObject("Id0022SCH", getClass());
            id0022SCH.setConnection(_wConn);

            // スケジュールの開始メソッドを呼び出し、開始データを取得
            RftOutParameter[] rftOutParam = (RftOutParameter[])id0022SCH.inquirySCH(rftInParam);

            // 荷主一覧ファイルを作成
            RftFileConsignor rftFile = (RftFileConsignor)PackageManager.getObject("RftFileConsignor", getClass());

            // 一覧ファイル名を応答電文にセット
            String filePathNameFull = rftFile.createSendFilePathNameFull(rftId0022.getRftNo());
            String filePathNameId = rftFile.createSendFilePathNameId(rftId0022.getRftNo());
            // データをセット
            for (int i = 0; i < rftOutParam.length; i++)
            {
                // 荷主コード
                rftFile.setConsignorCode(i, rftOutParam[i].getConsignorCode());
                // 荷主名称
                rftFile.setConsignorName(i, rftOutParam[i].getConsignorName());
            }
            // ファイルへ出力
            IdFileHandler.write(rftFile.exportFileData(), filePathNameFull);

            // ファイル名とレコード数を応答電文にセット
            rftId5022.setFileName(filePathNameId);
            rftId5022.setFileRecodeNumber(rftFile.getFileRecordTotal());

            // 応答フラグとエラー詳細をセット
            rftId5022.setAnsCode(RftId5022.ANS_CODE_NORMAL);
            rftId5022.setErrDetails(RftId5012.ErrorDetails.NORMAL);
        }
        catch (NotFoundException ex)
        {
            // 該当データ無し
            rftId5022.setAnsCode(RftId5022.ANS_CODE_NULL);
            rftId5022.setErrDetails(RftId5012.ErrorDetails.NORMAL);
        }
        catch (IOException e)
        {
            // ファイルI/Oエラー
            rftId5022.setAnsCode(RftId5022.ANS_CODE_ERROR);
            rftId5022.setErrDetails(RftId5012.ErrorDetails.I_O_ERROR);
        }
        catch (Exception ex)
        {
            // 想定外の例外発生時
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0022.getRftNo());
            rftId5022.setAnsCode(answer.get(0));
            rftId5022.setErrDetails(answer.get(1));
        }

        /* 必須の応答電文項目をセット */
        //  (STX)
        rftId5022.setSTX();
        //  (SEQ)
        rftId5022.setSEQ(0);
        //  (ID)
        rftId5022.setID(RftId5022.ID);
        //  (端末送信時間)
        rftId5022.setRftSendDate(rftId0022.getRftSendDate());
        //  (SERV送信時間)
        rftId5022.setServSendDate(new Date());
        //  (端末号機No.)
        rftId5022.setRftNo(rftId0022.getRftNo());
        //  (ユーザID)
        rftId5022.setUserId(rftId0022.getUserId());

        //  (応答フラグ) //このポイント以前で応答フラグはセットしておく事
        if (StringUtil.isBlank(rftId5022.getAnsCode()))
        {
            rftId5022.setAnsCode(RftId5022.ANS_CODE_ERROR);
            rftId5022.setErrDetails(RftId5022.ErrorDetails.INTERNAL_ERROR);
            // 6026117=<{0}号機> ID対応処理例外。致命的エラー。応答フラグ空白で応答電文を送信しようとした為、強制的にエラー応答をセットしました。
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026117, rftId0022.getRftNo()), getClass().getSimpleName());
        }
        //  (ETX)
        rftId5022.setETX();


        // 応答電文の組立(sdtの作成)
        rftId5022.getSendMessage(sdt);
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
        return "$Id: Id0022Process.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}
//end of class


