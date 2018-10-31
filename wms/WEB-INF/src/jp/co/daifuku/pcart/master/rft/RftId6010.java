// $Id: RftId6010.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

import jp.co.daifuku.wms.base.communication.rft.SendIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * マスタ画像登録商品応答 ID=6010 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id6010の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>            <TH>長さ</TH>    <TH>内容</TH></TR>
 * <tr><td>STX</td>               <td> 1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>           <td> 4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                <td> 4 byte</td> <td>6010</td></tr>
 * <tr><td>端末送信時間</td>      <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>    <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>       <td> 3 byte</td> <td></td></tr>
 * <tr><td>荷主コード</td>        <td> 16 byte</td> <td></td></tr>
 * <tr><td>スキャン商品コード</td><td> 16 byte</td> <td></td></tr>
 * <tr><td>商品コード</td>        <td> 16 byte</td> <td></td></tr>
 * <tr><td>商品名称</td>          <td> 40 byte</td> <td></td></tr>
 * <tr><td>JANコード</td>         <td> 16 byte</td> <td></td></tr>
 * <tr><td>ケースITF</td>         <td> 16 byte</td> <td></td></tr>
 * <tr><td>ボールITF</td>         <td> 16 byte</td> <td></td></tr>
 * <tr><td>ロット入数</td>        <td> 5 byte</td> <td></td></tr>
 * <tr><td>メッセージ</td>        <td> 50 byte</td> <td></td></tr>
 * <tr><td>画像ファイル名</td>    <td> 30 byte</td> <td></td></tr>
 * <tr><td>一覧ファイル名</td>    <td> 30 byte</td> <td></td></tr>
 * <tr><td>ファイルレコード数</td><td> 5 byte</td> <td></td></tr>
 * <tr><td>応答フラグ</td>        <td> 1 byte</td> <td></td></tr>
 * <tr><td>エラー詳細</td>        <td> 2 byte</td> <td></td></tr>
 * <tr><td>ETX</td>               <td> 1 byte</td> <td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/27</TD><TD>N.Arai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class RftId6010
        extends SendIdMessage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 荷主コードのオフセットの定義<BR>
     */
    private static final int OFF_CONSIGNOR_CODE = LEN_HEADER;

    /**
     * スキャン商品コードのオフセットの定義<BR>
     */
    private static final int OFF_SCAN_ITEM_CODE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

    /**
     * 商品コードのオフセットの定義<BR>
     */
    private static final int OFF_ITEM_CODE = OFF_SCAN_ITEM_CODE + LEN_ITEM_CODE;

    /**
     * 商品名称のオフセットの定義<BR>
     */
    private static final int OFF_ITEM_NAME = OFF_ITEM_CODE + LEN_ITEM_CODE;

    /**
     * JANコードのオフセットの定義<BR>
     */
    private static final int OFF_JAN_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME;

    /**
     * ケースITFのオフセットの定義<BR>
     */
    private static final int OFF_CASE_ITF = OFF_JAN_CODE + LEN_JAN_CODE;

    /**
     * ボールITFのオフセットの定義<BR>
     */
    private static final int OFF_BUNDLE_ITF = OFF_CASE_ITF + LEN_ITF;

    /**
     * ロット入数のオフセットの定義<BR>
     */
    private static final int OFF_LOT_ENTERING_QTY = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;

    /**
     * メッセージのオフセットの定義<BR>
     */
    private static final int OFF_MESSAGE = OFF_LOT_ENTERING_QTY + LEN_LOT_ENTERING_QTY;

    /**
     * 画像ファイル名のオフセットの定義<BR>
     */
    private static final int OFF_PICTURE_FILE_NAME = OFF_MESSAGE + LEN_MESSAGE;

    /**
     * 一覧ファイル名のオフセットの定義<BR>
     */
    private static final int OFF_LIST_FILE_NAME = OFF_PICTURE_FILE_NAME + LEN_FILE_NAME;

    /**
     * ファイルレコード数の定義<BR>
     */
    private static final int OFF_FILE_RECORD_NUMBER = OFF_LIST_FILE_NAME + LEN_FILE_NAME;

    /**
     * 応答フラグのオフセットの定義<BR>
     */
    private static final int OFF_ANSWER_CODE = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;

    /**
     * エラー詳細のオフセットの定義<BR>
     */
    private static final int OFF_ERROR_DETAILS = OFF_ANSWER_CODE + LEN_ANSWER_CODE;

    /**
     * ETXのオフセットの定義<BR>
     */
    private static final int OFF_ETX = OFF_ERROR_DETAILS + LEN_ERROR_DETAILS;

    /**
     * ID番号
     */
    public static final String ID = "6010";


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
     * 親クラスのコンストラクタを呼び出した後、
     * 電文の長さをセットします。また、内部バッファを空白で初期化します。
     */
    public RftId6010()
    {
        super();
        _length = OFF_ETX + LEN_ETX;
        initializeBuffer();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * 荷主コードを設定します。<BR>
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        setToBufferLeft(consignorCode, OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
    }

    /**
     * スキャン商品コードを設定します。<BR>
     * @param scanItemCode スキャン商品コード
     */
    public void setScanItemCode(String scanItemCode)
    {
        setToBufferLeft(scanItemCode, OFF_SCAN_ITEM_CODE, LEN_ITEM_CODE);
    }

    /**
     * 商品コードを設定します。<BR>
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        setToBufferLeft(itemCode, OFF_ITEM_CODE, LEN_ITEM_CODE);
    }

    /**
     * 商品名称を設定します。<BR>
     * @param itemName 商品名称
     */
    public void setItemName(String itemName)
    {
        setToBufferLeft(itemName, OFF_ITEM_NAME, LEN_ITEM_NAME);
    }

    /**
     * JANコードを設定します。<BR>
     * @param janCode JANコード
     */
    public void setJanCode(String janCode)
    {
        setToBufferLeft(janCode, OFF_JAN_CODE, LEN_JAN_CODE);
    }

    /**
     * ケースITFを設定します。<BR>
     * @param caseItf ケースITF
     */
    public void setCaseItf(String caseItf)
    {
        setToBufferLeft(caseItf, OFF_CASE_ITF, LEN_ITF);
    }

    /**
     * ボールITFを設定します。<BR>
     * @param bundleItf ボールITF
     */
    public void setBundleItf(String bundleItf)
    {
        setToBufferLeft(bundleItf, OFF_BUNDLE_ITF, LEN_ITF);
    }

    /**
     * ロット入数を設定します。<BR>
     * @param lotQty ロット入数
     */
    public void setLotEnteringQty(int lotQty)
    {
        setToBufferRight(lotQty, OFF_LOT_ENTERING_QTY, LEN_LOT_ENTERING_QTY);
    }

    /**
     * メッセージを設定します。<BR>
     * @param message メッセージ
     */
    public void setMessage(String message)
    {
        setToBufferLeft(message, OFF_MESSAGE, LEN_MESSAGE);
    }

    /**
     * 画像ファイル名を設定します。<BR>
     * @param pictureFileName 画像ファイル名
     */
    public void setPictureFileName(String pictureFileName)
    {
        setToBufferLeft(pictureFileName, OFF_PICTURE_FILE_NAME, LEN_FILE_NAME);
    }

    /**
     * 一覧ファイル名を設定します。<BR>
     * @param listFileName 一覧ファイル名
     */
    public void setListFileName(String listFileName)
    {
        setToBufferLeft(listFileName, OFF_LIST_FILE_NAME, LEN_FILE_NAME);
    }

    /**
     * ファイルレコード数を設定します。<BR>
     * @param fileRecordNumber ファイルレコード数
     */
    public void setFileRecordNumber(int fileRecordNumber)
    {
        setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
    }

    /**
     * 応答フラグを設定します。<BR>
     * @param ansCode 応答フラグ
     */
    public void setAnsCode(String ansCode)
    {
        setToBufferLeft(ansCode, OFF_ANSWER_CODE, LEN_ANSWER_CODE);
    }

    /**
     * エラー詳細を設定します。<BR>
     * @param errDetails エラー詳細
     */
    public void setErrDetails(String errDetails)
    {
        setToBufferLeft(errDetails, OFF_ERROR_DETAILS, LEN_ERROR_DETAILS);
    }

    /**
     * ETXを設定します。<BR>
     */
    public void setETX()
    {
        setToByteBuffer(DEF_ETX, OFF_ETX);
    }

    /**
     * 応答フラグを取得します。<BR>
     * @return 応答フラグ
     */
    public String getAnsCode()
    {
        String ansCode = getFromBuffer(OFF_ANSWER_CODE, LEN_ANSWER_CODE);
        return ansCode.trim();
    }

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
        return "$Id: RftId6010.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
