// $Id: RftId6031.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

import jp.co.daifuku.wms.base.communication.rft.SendIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * マスタ画像登録確定応答 ID=6031 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id6031の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>                      <TH>長さ</TH>   <TH>内容</TH></TR>
 * <tr><td>STX</td>                         <td> 1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>                     <td> 4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                          <td> 4 byte</td> <td>6031</td></tr>
 * <tr><td>端末送信時間</td>                <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>              <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>                 <td> 3 byte</td> <td></td></tr>
 * <tr><td>荷主コード</td>                  <td>16 byte</td> <td></td></tr>
 * <tr><td>商品コード</td>                  <td>16 byte</td> <td></td></tr>
 * <tr><td>応答フラグ</td>                  <td> 1 byte</td> <td>0:正常 1:あり</td></tr>
 * <tr><td>エラー詳細</td>                  <td> 2 byte</td> <td></td></tr> 
 * <tr><td>ETX</td>                         <td> 1 byte</td> <td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/27</TD><TD>T.kojima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class RftId6031
        extends SendIdMessage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 荷主コードのオフセットの定義
     */
    private static final int OFF_CONSIGNOR_CODE = LEN_HEADER;

    /**
     * 商品コードのオフセットの定義
     */
    private static final int OFF_ITEM_CODE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

    /**
     * 応答フラグのオフセットの定義
     */
    private static final int OFF_ANSWER_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE;

    /**
     * エラー詳細のオフセットの定義
     */
    private static final int OFF_ERROR_DETAILS = OFF_ANSWER_CODE + LEN_ANSWER_CODE;

    /**
     * ETXのオフセットの定義
     */
    private static final int OFF_ETX = OFF_ERROR_DETAILS + LEN_ERROR_DETAILS;

    /**
     * ID番号
     */
    public static final String ID = "6031";


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 親クラスのコンストラクタを呼び出した後、
     * 電文の長さをセットします。また、内部バッファを空白で初期化します。
     */
    public RftId6031()
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
     * 荷主コードを設定します。
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        setToBufferLeft(consignorCode, OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
    }

    /**
     * 商品コードを設定します。
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        setToBufferLeft(itemCode, OFF_ITEM_CODE, LEN_ITEM_CODE);
    }

    /**
     * 応答フラグを設定します。
     * @param   ansCode     設定する応答フラグ
     */
    public void setAnsCode(String ansCode)
    {
        setToBufferLeft(ansCode, OFF_ANSWER_CODE, LEN_ANSWER_CODE);
    }

    /**
     * エラー詳細を設定します。
     * @param errDetails    設定するエラー詳細
     */
    public void setErrDetails(String errDetails)
    {
        setToBufferLeft(errDetails, OFF_ERROR_DETAILS, LEN_ERROR_DETAILS);
    }

    /**
     * ETXを設定します。
     */
    public void setETX()
    {
        setToByteBuffer(DEF_ETX, OFF_ETX);
    }

    /**
     * 応答フラグを取得します。
     * @return  応答フラグ
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
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 3209 $,$Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $");
    }
}
//end of class
