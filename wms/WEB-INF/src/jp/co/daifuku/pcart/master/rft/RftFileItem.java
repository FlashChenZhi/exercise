// $Id: RftFileItem.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

import jp.co.daifuku.wms.base.rft.AbstractRftFile;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */



/**
 * 商品一覧ファイル
 * <p>
 * <table border="1">
 * <CAPTION>ファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>         <TH>長さ</TH>    <TH>内容</TH></TR>
 * <tr><td>商品コード</td>     <td>16 byte</td> <td></td></tr>
 * <tr><td>商品名称</td>       <td>40 byte</td> <td>CR + LF</td></tr>
 * <tr><td>ロット入数</td>     <td> 5 byte</td> <td></td></tr>
 * <tr><td>CRLF</td>           <td> 2 byte</td> <td>CRLF</td><tr>
 * </table>
 * </p>
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  kojima
 * @author  Last commit: $Author: arai $
 */


public class RftFileItem extends AbstractRftFile
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /**
     * 商品コードのオフセットの定義
     */
    private static final int OFF_ITEM_CODE = 0;

    /**
     * 商品名称のオフセットの定義
     */
    private static final int OFF_ITEM_NAME = OFF_ITEM_CODE + LEN_ITEM_CODE;

    /**
     * ロット入数のオフセットの定義
     */
    private static final int OFF_LOT_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME;

    /**
     * データファイルの1行の長さ
     */
    private static final int LINE_LENGTH = OFF_LOT_ENTERING_QTY + LEN_LOT_ENTERING_QTY;

    /**
     * 商品一覧ファイル名を表すフィールド
     */
    public static final String ANS_FILE_NAME = "DataList.txt" ;

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
     * 親クラスのコンストラクタを呼び出した後、データファイル１行の長さをセットします。
     */
    public RftFileItem()
    {
        super();
        _lineLength = LINE_LENGTH;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * データバッファに商品コードをセットします。
     * 
     * @param lineNumber    セットする行No.
     * @param itemCode      商品コード
     */
    public void setItemCode(int lineNumber, String itemCode)
    {
        setColumnLeft(lineNumber, itemCode, OFF_ITEM_CODE, LEN_ITEM_CODE);
    }
    
    /**
     * データバッファに商品名称をセットします。
     * 
     * @param lineNumber    セットする行No.
     * @param itemName      商品名称
     */
    public void setItemName(int lineNumber, String itemName)
    {
        setColumnLeft(lineNumber, itemName, OFF_ITEM_NAME, LEN_ITEM_NAME);
    }
    
    /**
     * データバッファにロット入数をセットします。
     * 
     * @param lineNumber    セットする行No.
     * @param lotQty        ロット入数
     */
    public void setLotEnteringQty(int lineNumber, int lotQty)
    {
        setColumnRight(lineNumber, lotQty, OFF_LOT_ENTERING_QTY, LEN_LOT_ENTERING_QTY);
    }
    
    /**
     * ファイル名と号機から送信ファイルのパスを生成します。
     * 
     * @param rftNo RFT号機
     * @return      送信ファイル名(パス付き)
     */
    public String createSendFilePathNameFull(String rftNo)
    {
        return super.createSendFilePathNameFull(rftNo, ANS_FILE_NAME);
    }
    /**
     * ファイル名と号機からIdにセットする送信ファイルのパスを生成します。
     * 
     * @param rftNo RFT号機
     * @return      送信ファイル名(パス付き)
     */
    public String createSendFilePathNameId(String rftNo)
    {
        return super.createSendFilePathNameId(rftNo, ANS_FILE_NAME);
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
        return "$Id: RftFileItem.java 3209 2009-03-02 06:34:19Z arai $";
    }
}

