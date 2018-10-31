// $Id: RftFileConsignor.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

import jp.co.daifuku.wms.base.rft.AbstractRftFile;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */



/**
 * 荷主一覧ファイル
 * <p>
 * <table border="1">
 * <CAPTION>ファイルの各行の構造</CAPTION>
 * <TR><TH>項目名</TH>         <TH>長さ</TH>    <TH>内容</TH></TR>
 * <tr><td>荷主コード</td>     <td>16 byte</td> <td></td></tr>
 * <tr><td>荷主名称</td>       <td>40 byte</td> <td>CR + LF</td></tr>
 * <tr><td>CRLF</td>           <td> 2 byte</td> <td>CRLF</td><tr>
 * </table>
 * </p>
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  kojima
 * @author  Last commit: $Author: rnakai $
 */


public class RftFileConsignor extends AbstractRftFile
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /**
     * 荷主コードのオフセットの定義
     */
    private static final int OFF_CONSIGNOR_CODE = 0;

    /**
     * 荷主名称のオフセットの定義
     */
    private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

    /**
     * データファイルの1行の長さ
     */
    private static final int LINE_LENGTH = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;

    /**
     * 荷主一覧ファイル名を表すフィールド
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
    public RftFileConsignor()
    {
        super();
        _lineLength = LINE_LENGTH;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * データバッファに荷主コードをセットします。
     * 
     * @param lineNumber    セットする行No.
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(int lineNumber, String consignorCode)
    {
        setColumnLeft(lineNumber, consignorCode, OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
    }
    
    /**
     * データバッファに荷主名称をセットします。
     * 
     * @param lineNumber セットする行No.
     * @param consignorName 荷主名称
     */
    public void setConsignorName(int lineNumber, String consignorName)
    {
        setColumnLeft(lineNumber, consignorName, OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME);
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
        return "$Id: RftFileConsignor.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}

