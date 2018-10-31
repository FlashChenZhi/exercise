// $Id: ToolAccessNgShelfSearchKey.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してTEMP_ACCESSNGSHELFテーブルを検索し、TEMP_ACCESSNGSHELFクラスのインスタンスを生成するために使用するキークラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is a key class which is used to search TEMP_ACCESSNGSHELF table by using handler class
 * and generate the instance of TEMP_ACCESSNGSHELF class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolAccessNgShelfSearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String WHSTATIONNO = "WH_STATION_NO";

    private static final String BANKNO = "BANK_NO";

    private static final String BAYNO = "BAY_NO";

    private static final String LEVELNO = "LEVEL_NO";

    private static final String WIDTH = "WIDTH";

    private static final String STARTADDRESSNO = "START_ADDRESS_NO";

    private static final String ENDADDRESSNO = "END_ADDRESS_NO";

    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            WHSTATIONNO,
            BANKNO,
            BAYNO,
            LEVELNO,
            WIDTH,
            STARTADDRESSNO,
            ENDADDRESSNO,
    };

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4122 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * カラム定義を設定します。
     </jp>*/
    /**<en>
     * Define the column definition.
     </en>*/
    public ToolAccessNgShelfSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * WHSTATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     </en>*/
    public void setWarehouseNo(int whnum)
    {
        setValue(WHSTATIONNO, whnum);
    }

    /**<jp>
     * WHSTATIONNOを取得
     </jp>*/
    /**<en>
     * Retrieve the WHSTATIONNO.
     </en>*/
    public String getWarehouseNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of WHSTATIONNO.
     </en>*/
    public void setWarehouseNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of WHSTATIONNO.
     </en>*/
    public int getWarehouseNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    /**<jp>
     * BANKNOの検索値をセットします。
     * @param bank BANKNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of BANKNO.
     * @param bank :the search value of BANKNO
     </en>*/
    public void setBankNo(String bank)
    {
        setValue(BANKNO, bank);
    }

    /**<jp>
     * BANKNOを検索値を取得します。
     * @return BANKNO
     </jp>*/
    /**<en>
     * Retrieve the search value of BANKNO.
     * @return BANKNO
     </en>*/
    public String getBankNo()
    {
        return (String)getValue(BANKNO);
    }

    /**<jp>
     * BANKNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of BANKNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setBankNoOrder(int num, boolean bool)
    {
        setOrder(BANKNO, num, bool);
    }

    /**<jp>
     * BANKNOのソート順を取得
     * @return BANKNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of BANKNO.
     * @return :the order of BANKNO
     </en>*/
    public int getBankNoOrder()
    {
        return (getOrder(BANKNO));
    }

    /**<jp>
     * BAYNOの検索値をセットします。
     * @param bay BAYNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of BAYNO.
     * @param sbay :the search value of BAYNO
     </en>*/
    public void setBayNo(String bay)
    {
        setValue(BAYNO, bay);
    }

    /**<jp>
     * BAYNOを検索値を取得します。
     * @return BAYNO
     </jp>*/
    /**<en>
     * Retrieve the search value of BAYNO.
     * @return BAYNO
     </en>*/
    public String getBayNo()
    {
        return (String)getValue(BAYNO);
    }

    /**<jp>
     * BAYNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of BAYNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setBayNoOrder(int num, boolean bool)
    {
        setOrder(BAYNO, num, bool);
    }

    /**<jp>
     * BAYNOのソート順を取得
     * @return BAYNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of BAYNO.
     * @return :the order of BAYNO
     </en>*/
    public int getBayNoOrder()
    {
        return (getOrder(BAYNO));
    }

    /**<jp>
     * LEVELNOの検索値をセットします。
     * @param lvl LEVELNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of LEVELNO.
     * @param lvl :the search value of LEVELNO
     </en>*/
    public void setLevelNo(String lvl)
    {
        setValue(LEVELNO, lvl);
    }

    /**<jp>
     * LEVELNOを検索値を取得します。
     * @return STARTLEVELNO
     </jp>*/
    /**<en>
     * Retrieve the search value of LEVELNO.
     * @return LEVELNO
     </en>*/
    public String getLevelNo()
    {
        return (String)getValue(LEVELNO);
    }

    /**<jp>
     * LEVELNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of LEVELNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setLevelNoOrder(int num, boolean bool)
    {
        setOrder(LEVELNO, num, bool);
    }

    /**<jp>
     * LEVELNOのソート順を取得
     * @return LEVELNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of LEVELNO.
     * @return :the order of LEVELNO
     </en>*/
    public int getLevelNoOrder()
    {
        return (getOrder(LEVELNO));
    }

    /**<jp>
     * 荷幅の検索値をセットします。
     * @param width 荷幅の検索値
     </jp>*/
    /**<en>
     * Set the search value of load width.
     * @param width :the search value of load width
     </en>*/
    public void setWidth(int width)
    {
        setValue(WIDTH, width);
    }

    /**<jp>
     * 荷幅を検索値を取得します。
     * @return 荷幅
     </jp>*/
    /**<en>
     * Retrieve the search value of load width.
     * @return :load width
     </en>*/
    public String getWidth()
    {
        return (String)getValue(WIDTH);
    }

    /**<jp>
     * 荷幅のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load width.
     * @param num  :prioriry in sort order
     * @param bool true: in ascending order, false: in descending order
     </en>*/
    public void setWidthOrder(int num, boolean bool)
    {
        setOrder(WIDTH, num, bool);
    }

    /**<jp>
     * 荷幅のソート順を取得
     * @return 荷幅順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load width.
     * @return :the order of load width
     </en>*/
    public int getWidthOrder()
    {
        return (getOrder(WIDTH));
    }

    /**<jp>
     * STARTADDRESSNOの検索値をセットします。
     * @param saddress STARTADDRESSNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of STARTADDRESSNO.
     * @param saddress :the search value of STARTADDRESSNO
     </en>*/
    public void setStartAddressNo(String saddress)
    {
        setValue(STARTADDRESSNO, saddress);
    }

    /**<jp>
     * STARTADDRESSNOを検索値を取得します。
     * @return STARTADDRESSNO
     </jp>*/
    /**<en>
     * Retrieve the search value of STARTADDRESSNO.
     * @return STARTADDRESSNO
     </en>*/
    public String getStartAddressNo()
    {
        return (String)getValue(STARTADDRESSNO);
    }

    /**<jp>
     * STARTADDRESSNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of STARTADDRESSNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setStartAddressNoOrder(int num, boolean bool)
    {
        setOrder(STARTADDRESSNO, num, bool);
    }

    /**<jp>
     * STARTADDRESSNOのソート順を取得
     * @return STARTADDRESSNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of STARTADDRESSNO.
     * @return :the order of STARTADDRESSNO
     </en>*/
    public int getStartAddressNoOrder()
    {
        return (getOrder(STARTADDRESSNO));
    }

    /**<jp>
     * ENDADDRESSNOの検索値をセットします。
     * @param eaddress ENDADDRESSNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of ENDADDRESSNO.
     * @param eaddress :the search value of ENDADDRESSNO.
     </en>*/
    public void setEndAddressNo(String eaddress)
    {
        setValue(ENDADDRESSNO, eaddress);
    }

    /**<jp>
     * ENDADDRESSNOを検索値を取得します。
     * @return ENDADDRESSNO
     </jp>*/
    /**<en>
     * Retrieve the search value of ENDADDRESSNO.
     * @return ENDADDRESSNO
     </en>*/
    public String getEndAddressNo()
    {
        return (String)getValue(ENDADDRESSNO);
    }

    /**<jp>
     * ENDADDRESSNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of ENDADDRESSNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setEndAddressNoOrder(int num, boolean bool)
    {
        setOrder(ENDADDRESSNO, num, bool);
    }

    /**<jp>
     * ENDADDRESSNOのソート順を取得
     * @return ENDADDRESSNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of ENDADDRESSNO.
     * @return :the order of ENDADDRESSNO
     </en>*/
    public int getEndAddressNoOrder()
    {
        return (getOrder(ENDADDRESSNO));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

