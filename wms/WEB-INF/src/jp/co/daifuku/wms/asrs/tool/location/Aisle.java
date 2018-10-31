//$Id: Aisle.java 5301 2009-10-28 05:36:02Z ota $

package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;

/**<jp>
 * アイルを管理するためのクラスです。アイルはStationクラスを継承し、
 * アイル号機Noを識別するためのステーションNoを持ちます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to control aisles. The aisles inherits the Station class and 
 * preserves the station no. to identify the aisle machine no.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class Aisle extends Station
{
    // Class fields --------------------------------------------------
    /**<jp>
     * フォークの種類をあらわすフィールド（シングルディープ）
     </jp>*/
    /**<en>
     * Field of fork type (single deep)
     </en>*/
    public static final int    SINGLE_DEEP = 0 ;

    /**<jp>
     * フォークの種類をあらわすフィールド（ダブルディープ）
     </jp>*/
    /**<en>
     * Field of fork type (double deep)
     </en>*/
    public static final int    DOUBLE_DEEP = 1 ;

    // Class variables -----------------------------------------------
    /**<jp>
     * アイルNoを保持します。
     </jp>*/
    /**<en>
     * Preserve the aisle no.
     </en>*/
    private int wAisleNo ;

    /**<jp>
     * フォーク種別を保持します。
     </jp>*/
    /**<en>
     * Preserve the fork type.
     </en>*/
    private int wDoubleDeepKind ;
    
    /**<jp>
     * 最大搬送可能数を保持します。
     </jp>*/
    private int wMAxCarry ;

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**<jp>
     * アイル情報を管理するための<code>Aisle</code>インスタンスを作成します。
     * アイルステーションNoは自分自身のステーションNoをセットします。
     * @param snum  アイルステーション番号
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create the <code>Aisle</code> instance in order to control the aisle information.
     * The aisle station no. will set its own station no.
     * @param snum  :aisle station no.
     </en>*/
    public Aisle(String snum)
    {
        super(snum) ;
        wAisleStationNo = snum;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 5301 $,$Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $") ;
    }

    /**<jp>
     * アイルNoを設定します。
     * @param  aile アイルNo
     </jp>*/
    /**<en>
     * Set the aisle no.
     * @param  aile :aisle no.
     </en>*/
    public void setAisleNo(int aile)
    {
        wAisleNo = aile ;
    }

    /**<jp>
     * アイルNoを返します。
     * @return   アイルNo
     </jp>*/
    /**<en>
     * Return the aisle no.
     * @return   :aisle no.
     </en>*/
    public int getAisleNo()
    {
        return (wAisleNo) ;
    }

    /**<jp>
     * フォーク種別を設定します。
     * @param  type フォーク種別
     * @throws InvalidStatusException typeの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the fork type.
     * @param  type :fork type
     * @throws InvalidStatusException :Notifies is the contents of type is invalid.
     </en>*/
    public void setDoubleDeepKind(int type) throws InvalidStatusException
    {
        //<jp> 状態のチェック</jp>
        //<en> Check the status.</en>
        switch(type)
        {
            //<jp> 正しい状態の一覧</jp>
            //<en> List of correct status</en>
            case SINGLE_DEEP:
            case DOUBLE_DEEP:
                wDoubleDeepKind = type;
                break ;
                
            //<jp> 正しくない状態を設定しようとした場合は例外を発生させ、状態の変更はしない</jp>
            //<en> If incorrect status is to set, it lets the exception occurs; it will not modify the status.</en>
            default:
                //<jp> 定義されていないステーション状態です。</jp>
                //<en> This station status is undefined.</en>
                throw new InvalidStatusException() ;
        }
    }

    /**<jp>
     * フォーク種別を取得します。
     * @return   フォーク種別
     </jp>*/
    /**<en>
     * Retrieve the fork type.
     * @return   :fork type
     </en>*/
    public int getDoubleDeepKind()
    {
        return wDoubleDeepKind;
    }

    /**<jp>
     * 入出庫可能アイルステーションNoを取得します。
     * Aisleクラスの場合、自分自身のステーションNoを返します。
     * @return 入出庫可能アイルステーションNo
     </jp>*/
    /**<en>
     * Retrieve the aisle station no. available for sotrage/retrieval.
     * If it is the Aisle class, it will return the own station no.
     * @return :the aisle station no. available for sotrage/retrieval
     </en>*/
    public String getAisleStationNo()
    {
        return wStationNo;
    }

    /**<jp>
     * 最大搬送可能数を設定します。
     * @param  maxCarry 最大搬送可能数
     </jp>*/
    public void setMaxCarry(int maxCarry)
    {
        wMAxCarry = maxCarry ;
    }

    /**<jp>
     * 最大搬送可能数を返します。
     * @return   最大搬送可能数
     </jp>*/
    public int getMaxCarry()
    {
        return (wMAxCarry) ;
    }
    
    /**<jp>
     * 文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        buf.append("\nStation Number:" + wStationNo) ;
        buf.append("\nWarehouseStationNo:" + wWhStationNo) ;
        buf.append("\nAisleNo:" + wAisleNo) ;
        buf.append("\nDoubleDeepKind:" + wDoubleDeepKind) ;
        buf.append("\nStatus:" + wStatus) ;
        buf.append("\nInventoryCheckFlag:" + wInventoryCheckFlag) ;
        buf.append("\nAisleStationNo:" + wAisleStationNo) ;

        return (buf.toString()) ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

