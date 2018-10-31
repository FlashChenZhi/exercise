// $Id: Id0012Process.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.communication.rft.IdProcess;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.rft.IdExceptionHandler;
import jp.co.daifuku.wms.base.rft.RftInParameter;
import jp.co.daifuku.wms.base.rft.RftOutParameter;


/**
 * RFTからの荷主問合せ(ID0012)に対し、要求電文の分解、応答電文の組立を行う。 <BR>
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  kojima
 * @author  Last commit: $Author: rnakai $
 */

public class Id0012Process
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
    public Id0012Process()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     *  荷主問合せ処理
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
        RftId0012 rftId0012 = (RftId0012)PackageManager.getObject("RftId0012", getClass());
        rftId0012.setReceiveMessage(rdt);

        // 応答電文のインスタンスを生成
        RftId5012 rftId5012 = (RftId5012)PackageManager.getObject("RftId5012", getClass());

        // スケジュールクラスのインスタンスを生成
        Id0012SCH id0012sch = (Id0012SCH)PackageManager.getObject("Id0012SCH", getClass());
        id0012sch.setConnection(_wConn);
        try
        {
            // 荷主用入力パラメータクラス配列を生成
            RftInParameter[] rftInParam = new RftInParameter[1];
            rftInParam[0] = new RftInParameter();

            // 要求電文の内容をパラメータクラスにセット
            // 端末号機No.
            rftInParam[0].setTerminalNo(rftId0012.getRftNo());
            // ユーザID
            rftInParam[0].setUserId(rftId0012.getUserId());
            // 荷主コード
            rftInParam[0].setConsignorCode(rftId0012.getConsignorCode());

            // スケジュールの開始メソッドを呼び出し、開始データを取得
            RftOutParameter[] rftOutParam = (RftOutParameter[])id0012sch.inquirySCH(rftInParam);

            // 取得した荷主データを、応答電文にセット
            rftId5012.setConsignorName(rftOutParam[0].getConsignorName());

            // 応答フラグとエラー詳細をセット
            rftId5012.setAnsCode(RftId5012.ANS_CODE_NORMAL);
            rftId5012.setErrDetails(RftId5012.ErrorDetails.NORMAL);
        }
        catch (NotFoundException ex)
        {
            // 該当データ無し
            rftId5012.setAnsCode(RftId5012.ANS_CODE_NULL);
            rftId5012.setErrDetails(RftId5012.ErrorDetails.NORMAL);
        }
        catch (Exception ex)
        {
            // 想定外の例外発生時
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0012.getRftNo());
            rftId5012.setAnsCode(answer.get(0));
            rftId5012.setErrDetails(answer.get(1));
        }

        /* 必須の応答電文項目をセット */
        //  (STX)
        rftId5012.setSTX();
        //  (SEQ)
        rftId5012.setSEQ(0);
        //  (ID)
        rftId5012.setID(RftId5012.ID);
        //  (端末送信時間)
        rftId5012.setRftSendDate(rftId0012.getRftSendDate());
        //  (送信時刻)
        rftId5012.setServSendDate(new Date());
        //  (端末号機No.)
        rftId5012.setRftNo(rftId0012.getRftNo());
        //  (ユーザID)
        rftId5012.setUserId(rftId0012.getUserId());
        // 荷主コード
        rftId5012.setConsignorCode(rftId0012.getConsignorCode());
        try
        {
            // マスタパッケージ有無フラグ
            if (id0012sch.hasMasterPack())
            {
                // マスタパッケージがある場合
                rftId5012.setMasterPackExistFlag(SystemDefine.PACK_INSTALLED);
            }
            else
            {
                // マスタパッケージがない場合
                rftId5012.setMasterPackExistFlag(SystemDefine.PACK_NOT_INSTALLED);
            }
        }
        catch (Exception e)
        {
            // DBアクセスエラーが発生した場合
            rftId5012.setMasterPackExistFlag("");
        }

        //  (応答フラグ) //このポイント以前で応答フラグはセットしておく事
        if (StringUtil.isBlank(rftId5012.getAnsCode()))
        {
            rftId5012.setAnsCode(RftId5012.ANS_CODE_ERROR);
            rftId5012.setErrDetails(RftId5012.ErrorDetails.INTERNAL_ERROR);
            // 6026117=<{0}号機> ID対応処理例外。致命的エラー。応答フラグ空白で応答電文を送信しようとした為、強制的にエラー応答をセットしました。
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026117, rftId0012.getRftNo()), getClass().getSimpleName());
        }
        //  (ETX)
        rftId5012.setETX();


        // 応答電文の組立(sdtの作成)
        rftId5012.getSendMessage(sdt);
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
        return "$Id: Id0012Process.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}
//end of class


