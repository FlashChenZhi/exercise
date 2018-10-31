// $Id: Bank.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * バンク情報を管理するためのクラスです。空棚検索時のバンク決定処理で使用されます。
 * バンク情報は所属する倉庫、アイルステーション、バンクNo、バンク内空棚数などを保持します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class controls the bank information. This will be used in bank designation process when 
 * searching the empty locations. 
 * The bank information preserves data such as the warehouse and aisle station the bank belong to,
 * the bank no. and the number of empty locations in the bank.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Bank extends ToolEntity
{
    // Class fields --------------------------------------------------
    /**<jp>
     * バンクの奥、手前を現すフィールド(手前側バンク）
     </jp>*/
    /**<en>
     * Field of rear/front bank (front)
     </en>*/
    public static final int NEAR = 0 ;

    /**<jp>
     * バンクの奥、手前を現すフィールド(奥側バンク）
     </jp>*/
    /**<en>
     * Field of rear/front bank  (rear bank)
     </en>*/
    public static final int FAR   = 1 ;

    // Class variables -----------------------------------------------
    /**<jp>
     * 倉庫ステーションナンバー
     </jp>*/
    /**<en>
     * Warehouse station no.
     </en>*/
    protected String wWhStationNo;

    /**<jp>
     * アイルステーションナンバー
     </jp>*/
    /**<en>
     * Aisle station no.
     </en>*/
    protected String wAisleStationNo;

    /**<jp>
     * バンクNo
     </jp>*/
    /**<en>
     * Bank no.
     </en>*/
    protected int wBankNo ;

    /**<jp>
     * ペアバンクNo。ダブルディープレイアウト時に対となるバンクNo
     </jp>*/
    /**<en>
     * Paired bank no. -set of 2 bank numbers used in double deep layout.
     </en>*/
    protected int wPairBank ;

    /**<jp>
     * バンクの奥、手前をあらわす区分
     * シングルディープの場合は常に手前になる。
     </jp>*/
    /**<en>
     * The segment to distinguish the front bank/ rear bank
     * In single deep layout, only the front banks will always be used.
     </en>*/
    protected int wSide ;

    /**<jp>
     * バンク内空棚数
     </jp>*/
    /**<en>
     * Number of empty locations in the bank
     </en>*/
    private int wEmptyCount = 0 ;

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>Bank</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct a new <CODE>Bank</CODE>.
     </en>*/
    public Bank()
    {   
    }

    /**<jp>
     * 新しい<CODE>Bank</CODE>を構築します。
     * 検索結果をvector()にセットする時に使用します
     * @param wnum   倉庫ステーション№
     * @param stno   アイルステーション№
     * @param bank   バンク№
     * @param pbank  ペアバンク№
     * @param side   バンクの奥、手前をあらわす区分
     </jp>*/
    /**<en>
     * Construct a new <CODE>Bank</CODE>.
     * This will be used when setting the search result in vector().
     * @param wnum   :warehouse station no.
     * @param stno   :aisle station no.
     * @param bank   :bank no.
     * @param pbank  :paired bank no.
     * @param side   :the segment to distinguish the front bank/ rear bank
     </en>*/
    public Bank    (String            wnum,
                 String            stno,
                 int             bank,
                 int            pbank,
                 int            side)
    {
        wWhStationNo = wnum;
        wAisleStationNo = stno;
        wBankNo = bank;
        wPairBank = pbank;
        wSide = side;
    }

    /**<jp>
     * 新しい<CODE>Bank</CODE>を構築します。
     * 検索結果をvector()にセットする時に使用します
     * このメソッドではバンク内空棚数をセットしています。
     * @param wnum   倉庫ステーション№
     * @param stno   アイルステーション№
     * @param bank   バンク№
     * @param pbank  ペアバンク№
     * @param side   バンクの奥、手前をあらわす区分
     * @param cnt    バンク内空棚数
     </jp>*/
    /**<en>
     * Construct a new <CODE>Bank</CODE>.
     * This will be used when setting the search result in vector().
     * In this method, set the number of empty locations in the bank.
     * @param wnum   :warehouse station no.
     * @param stno   :aisle station no.
     * @param bank   :bank no.
     * @param pbank  :paired bank no.
     * @param side   :the segment to distinguish the front bank/ rear bank
     * @param cnt    :Number of empty locations in the bank
     </en>*/
    public Bank    (String            wnum,
                 String            stno,
                 int             bank,
                 int            pbank,
                 int            side,
                 int            cnt)
    {
        wWhStationNo = wnum;
        wAisleStationNo = stno;
        wBankNo = bank;
        wPairBank = pbank;
        wSide = side;
        wEmptyCount = cnt;
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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }
    
    /**<jp>
     * このステーションが属している倉庫Noを設定します。
     * @param whnum ステーションの属している倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no. that this station belongs to.
     * @param whnum :the warehouse no. that this station belongs to
     </en>*/
    public void setWhStationNo(String whnum)
    {
        wWhStationNo = whnum;
    }

    /**<jp>
     * このステーションが属している倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no. that this station belongs to.
     * @return :warehouse no.
     </en>*/
    public String getWhStationNo()
    {
        return wWhStationNo;
    }

    /**<jp>
     * 入出庫可能アイルステーションNoを設定します。
     * @param anum 入出庫可能アイルステーションNo
     </jp>*/
    /**<en>
     * Set the aisle station no. available for storage/retrieval.
     * @param anum :the aisle station no. available for storage/retrieval
     </en>*/
    public void setAisleStationNo(String anum)
    {
        wAisleStationNo = anum;
    }

    /**<jp>
     * 入出庫可能アイルステーションNoを取得します。
     * @return 入出庫可能アイルステーションNo
     </jp>*/
    /**<en>
     * Retrieve the aisle station no. available for storage/retrieval.
     * @return :the aisle station no. available for storage/retrieval
     </en>*/
    public String getAisleStationNo()
    {
        return wAisleStationNo;
    }

    /**<jp>
     * バンクNoを設定します。
     * @param bank バンクNo
     </jp>*/
    /**<en>
     * Set the bank no.
     * @param bank :bank no.
     </en>*/
    public void setBankNo(int bank)
    {
        wBankNo = bank ;
    }

    /**<jp>
     * バンクNoを返します。
     * @return バンクNo
     </jp>*/
    /**<en>
     * Return the bank no.
     * @return :bank no.
     </en>*/
    public int getBankNo()
    {
        return (wBankNo) ;
    }

    /**<jp>
     * ペアバンクNoを設定します。
     * @param pbank ペアバンクNo
     </jp>*/
    /**<en>
     * Set the paired bank no.
     * @param pbank :paired bank no.
     </en>*/
    public void setPairBank(int pbank)
    {
        wPairBank = pbank ;
    }

    /**<jp>
     * ペアバンクNoを返します。
     * @return ペアバンクNo
     </jp>*/
    /**<en>
     * Return the paired bank no.
     * @return :paired bank no.
     </en>*/
    public int getPairBank()
    {
        return (wPairBank) ;
    }

    /**<jp>
     * 奥棚、手前棚をあらわす区分を設定します。
     * @param side 奥棚、手前棚をあらわす区分
     </jp>*/
    /**<en>
     * Set the segment of rear bank/front bank.
     * @param side :segment of rear bank/front bank
     </en>*/
    public void setSide(int side)
    {
        wSide = side ;
    }

    /**<jp>
     * 奥棚、手前棚をあらわす区分を返します。
     * @return 奥棚、手前棚をあらわす区分
     </jp>*/
    /**<en>
     * Return the segment of rear bank/front bank.
     * @return :segment of rear bank/front bank
     </en>*/
    public int getSide()
    {
        return (wSide) ;
    }

    /**<jp>
     * バンク内空棚数を設定します。
     * @param cnt バンク内空棚数
     </jp>*/
    /**<en>
     * Set the number of empty locations in the bank.
     * @param cnt :number of empty locations in the bank
     </en>*/
    public void setEmptyCount(int cnt)
    {
        wEmptyCount = cnt ;
    }

    /**<jp>
     * バンク内空棚数を返します。
     * @return バンク内空棚数
     </jp>*/
    /**<en>
     * Returns the number of empty locations in the bank.
     * @return :number of empty locations in the bank
     </en>*/
    public int getEmptyCount()
    {
        return (wEmptyCount) ;
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
        buf.append("\nWareHouseStationNo:" + wWhStationNo) ;
        buf.append("\nAisleStationNo:" + wAisleStationNo) ;
        buf.append("\nPairBank:" + wPairBank) ;
        buf.append("\nBank:" + wBankNo) ;
        buf.append("\nSide:" + wSide) ;
        buf.append("\nEmptyCount:" + wEmptyCount) ;

        return (buf.toString()) ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class


