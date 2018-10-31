// $Id: RftId1031.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;


import jp.co.daifuku.wms.base.communication.rft.RecvIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * マスタ画像登録確定要求 ID=1031 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id1031の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>            <TH>長さ</TH>    <TH>内容</TH></TR>
 * <tr><td>STX</td>               <td>  1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>           <td>  4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                <td>  4 byte</td> <td>1031</td></tr>
 * <tr><td>端末送信時間</td>      <td>  6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>    <td>  6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>       <td>  3 byte</td> <td></td></tr>
 * <tr><td>荷主コード</td>        <td> 16 byte</td> <td></td></tr>
 * <tr><td>商品コード</td>        <td> 16 byte</td> <td></td></tr>
 * <tr><td>ロット入り数</td>      <td>  5 byte</td> <td></td></tr>
 * <tr><td>画像ファイル名</td>    <td> 30 byte</td> <td></td></tr>
 * <tr><td>ETX</td>               <td>  1 byte</td> <td>0x03</td></tr>
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
public class RftId1031
        extends RecvIdMessage
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
     * ロット入り数のオフセットの定義
     */
    private static final int OFF_LOT_ENTERING_QTY = OFF_ITEM_CODE + LEN_ITEM_CODE;

    /**
     * 画像ファイル名のオフセットの定義
     */
    private static final int OFF_PICTURE_FILE_NAME = OFF_LOT_ENTERING_QTY + LEN_LOT_ENTERING_QTY;

    /**
     * ETXのオフセットの定義
     */
    //private static final int OFF_ETX = OFF_PICTURE_FILE_NAME + LEN_FILE_NAME;
    
    /**
     * ID番号
     */
    public static final String ID = "1031";

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
     * インスタンスを生成します。
     */
    public RftId1031()
    {
        super();
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 荷主コードを取得します。
     * @return 荷主コード    
     *
     */
    public String getConsignorCode()
    {
        String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
        return consignorCode.trim();
    }
    
    /**
     * 商品コードを取得します。<BR>
     * @return 商品コード
     */
    public String getItemCode()
    {
        String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE);
        return itemCode.trim();
    }

    /**
     * ロット入り数を取得します。<BR>
     * @return ロット入り数
     */
    public int getLotEnteringQty()
    {
        int lotEnteringQty = getIntFromBuffer(OFF_LOT_ENTERING_QTY, LEN_LOT_ENTERING_QTY);
        return lotEnteringQty;
    }

    /**
     * 画像ファイル名を取得します。<BR>
     * @return 画像ファイル名
     */
    public String getPictureFileName()
    {
        String pictureFileName = getFromBuffer(OFF_PICTURE_FILE_NAME, LEN_FILE_NAME);
        return pictureFileName.trim();
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
