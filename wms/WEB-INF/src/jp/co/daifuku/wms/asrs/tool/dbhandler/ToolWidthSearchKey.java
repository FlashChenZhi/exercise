// $Id: ToolWidthSearchKey.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してTEMP_WIDTHテーブルを検索し、TEMP_WIDTHクラスのインスタンスを生成するために使用するキークラスです。
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
 * This is a key class which is used to search TEMP_WIDTH table by using handler class
 * and generate the instance of TEMP_WIDTH class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolWidthSearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String WIDTHID = "WIDTH_ID";

    private static final String WIDTH = "WIDTH";

    private static final String WIDTHNAME = "WIDTH_NAME";

    private static final String WHSTATIONNO = "WH_STATION_NO";

    private static final String MAXSTORAGE = "MAX_STORAGE";

    private static final String STARTBANKNO = "START_BANK_NO";

    private static final String STARTBAYNO = "START_BAY_NO";

    private static final String STARTLEVELNO = "START_LEVEL_NO";

    private static final String ENDBANKNO = "END_BANK_NO";

    private static final String ENDBAYNO = "END_BAY_NO";

    private static final String ENDLEVELNO = "END_LEVEL_NO";

    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            WIDTHID,
            WIDTH,
            WIDTHNAME,
            WHSTATIONNO,
            MAXSTORAGE,
            STARTBANKNO,
            STARTBAYNO,
            STARTLEVELNO,
            ENDBANKNO,
            ENDBAYNO,
            ENDLEVELNO,
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
    public ToolWidthSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 荷幅IDの検索値をセットします。
     * @param width 荷幅IDの検索値
     </jp>*/
    /**<en>
     * Set the search value of load width id.
     * @param id :the search value of load width id
     </en>*/
    public void setWidthId(int id)
    {
        setValue(WIDTHID, id);
    }

    /**<jp>
     * 荷幅IDを検索値を取得します。
     * @return 荷幅ID
     </jp>*/
    /**<en>
     * Retrieve the search value of load width id.
     * @return :load width id
     </en>*/
    public String getWidthId()
    {
        return (String)getValue(WIDTHID);
    }

    /**<jp>
     * 荷幅IDのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load width id.
     * @param num  :prioriry in sort order
     * @param bool true: in ascending order, false: in descending order
     </en>*/
    public void setWidthIdOrder(int num, boolean bool)
    {
        setOrder(WIDTHID, num, bool);
    }

    /**<jp>
     * 荷幅IDのソート順を取得
     * @return 荷幅ID順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load width id.
     * @return :the order of load width id
     </en>*/
    public int getWidthIdOrder()
    {
        return (getOrder(WIDTHID));
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
     * 荷幅名称の検索値をセットします。
     * @param nam 荷幅名称の検索値
     </jp>*/
    /**<en>
     * Set the search value of load width name.
     * @param nam :the search value of load width name
     </en>*/
    public void setWidthName(String nam)
    {
        setValue(WIDTHNAME, nam);
    }

    /**<jp>
     * 荷幅名称を検索値を取得します。
     * @return 荷幅名称
     </jp>*/
    /**<en>
     * Retrieve the search value of load width name.
     * @return :name of load width
     </en>*/
    public String getWidthName()
    {
        return (String)getValue(WIDTHNAME);
    }

    /**<jp>
     * 荷幅名称のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load width name.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setWidthNameOrder(int num, boolean bool)
    {
        setOrder(WIDTHNAME, num, bool);
    }

    /**<jp>
     * 荷幅名称のソート順を取得
     * @return 荷幅名称順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load width name.
     * @return :the order of load width name
     </en>*/
    public int getWidthNameOrder()
    {
        return (getOrder(WIDTHNAME));
    }

    /**<jp>
     * WHSTATIONNOの検索値をセットします。
     * @param stnum 検索する倉庫を識別するステーションNo
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @param stnum :station no. to identify the warehouse to search
     </en>*/
    public void setWHStationNo(String stnum)
    {
        setValue(WHSTATIONNO, stnum);
    }

    /**<jp>
     * WHSTATIONNOを検索値を取得します。
     * @return 倉庫を識別するステーションNo
     </jp>*/
    /**<en>
     * Retrieve the search value of WHSTATIONNO.
     * @return :station no. to identify the warehouse
     </en>*/
    public String getWHStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of WHSTATIONNO.
     * @param num   :prioriry in sort order
     * @param bool  true: in ascending order, false: in descending order
     </en>*/
    public void setWHStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     * @return 倉庫を識別するステーションNo順
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @return :the order of station no. to identify the station no.
     </en>*/
    public int getWHStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    /**<jp>
     * 最大格納数の検索値をセットします。
     * @param hgt 最大格納数の検索値
     </jp>*/
    /**<en>
     * Set the search value of max storage.
     * @param hgt :the search value of max storage
     </en>*/
    public void setMaxStorage(String hgt)
    {
        setValue(MAXSTORAGE, hgt);
    }

    /**<jp>
     * 最大格納数を検索値を取得します。
     * @return 最大格納数
     </jp>*/
    /**<en>
     * Retrieve the search value of max storage.
     * @return :max storage
     </en>*/
    public String getMaxStorage()
    {
        return (String)getValue(MAXSTORAGE);
    }

    /**<jp>
     * 最大格納数のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of max storage.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setMaxStorageOrder(int num, boolean bool)
    {
        setOrder(MAXSTORAGE, num, bool);
    }

    /**<jp>
     * 最大格納数のソート順を取得
     * @return 最大格納数順
     </jp>*/
    /**<en>
     * Retrieve the sort order of max storage
     * @return :max storage
     </en>*/
    public int getMaxStorageOrder()
    {
        return (getOrder(MAXSTORAGE));
    }

    /**<jp>
     * STARTBANKの検索値をセットします。
     * @param sbank STARTBANKの検索値
     </jp>*/
    /**<en>
     * Set the search value of STARTBANKNO.
     * @param sbank :the search value of STARTBANKNO
     </en>*/
    public void setStartBankNo(String sbank)
    {
        setValue(STARTBANKNO, sbank);
    }

    /**<jp>
     * STARTBANKNOを検索値を取得します。
     * @return STARTBANKNO
     </jp>*/
    /**<en>
     * Retrieve the search value of STARTBANKNO.
     * @return STARTBANKNO
     </en>*/
    public String getStartBankNo()
    {
        return (String)getValue(STARTBANKNO);
    }

    /**<jp>
     * STARTBANKNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of STARTBANKNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setStartBankNoOrder(int num, boolean bool)
    {
        setOrder(STARTBANKNO, num, bool);
    }

    /**<jp>
     * STARTBANKNOのソート順を取得
     * @return STARTBANKNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of STARTBANKNO.
     * @return :the order of STARTBANKNO
     </en>*/
    public int getStartBankNoOrder()
    {
        return (getOrder(STARTBANKNO));
    }

    /**<jp>
     * STARTBAYの検索値をセットします。
     * @param sbay STARTBAYNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of STARTBAYNO.
     * @param sbay :the search value of STARTBAYNO
     </en>*/
    public void setStartBayNo(String sbay)
    {
        setValue(STARTBAYNO, sbay);
    }

    /**<jp>
     * STARTBAYNOを検索値を取得します。
     * @return STARTBAYNO
     </jp>*/
    /**<en>
     * Retrieve the search value of STARTBAYNO.
     * @return STARTBAYNO
     </en>*/
    public String getStartBayNo()
    {
        return (String)getValue(STARTBAYNO);
    }

    /**<jp>
     * STARTBAYNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of STARTBAYNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setStartBayNoOrder(int num, boolean bool)
    {
        setOrder(STARTBAYNO, num, bool);
    }

    /**<jp>
     * STARTBAYNOのソート順を取得
     * @return STARTBAYNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of STARTBAYNO.
     * @return :the order of STARTBAYNO
     </en>*/
    public int getStartBayNoOrder()
    {
        return (getOrder(STARTBAYNO));
    }

    /**<jp>
     * STARTLEVELNOの検索値をセットします。
     * @param slvl STARTLEVELNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of STARTLEVELNO.
     * @param slvl :the search value of STARTLEVELNO
     </en>*/
    public void setStartLevelNo(String slvl)
    {
        setValue(STARTLEVELNO, slvl);
    }

    /**<jp>
     * STARTLEVELNOを検索値を取得します。
     * @return STARTLEVELNO
     </jp>*/
    /**<en>
     * Retrieve the search value of STARTLEVELNO.
     * @return STARTLEVELNO
     </en>*/
    public String getStartLevelNo()
    {
        return (String)getValue(STARTLEVELNO);
    }

    /**<jp>
     * STARTLEVELNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of STARTLEVELNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setStartLevelNoOrder(int num, boolean bool)
    {
        setOrder(STARTLEVELNO, num, bool);
    }

    /**<jp>
     * STARTLEVELNOのソート順を取得
     * @return STARTLEVELNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of STARTLEVELNO.
     * @return :the order of STARTLEVELNO
     </en>*/
    public int getStartLevelNoOrder()
    {
        return (getOrder(STARTLEVELNO));
    }

    /**<jp>
     * ENDBANKNOの検索値をセットします。
     * @param ebnk ENDBANKNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of ENDBANKNO.
     * @param ebnk :the search value of ENDBANKNO
     </en>*/
    public void setEndBankNo(String ebnk)
    {
        setValue(ENDBANKNO, ebnk);
    }

    /**<jp>
     * ENDBANKNOを検索値を取得します。
     * @return ENDBANKNO
     </jp>*/
    /**<en>
     * Retrieve the search value of ENDBANKNO.
     * @return ENDBANKNO
     </en>*/
    public String getEndBankNo()
    {
        return (String)getValue(ENDBANKNO);
    }

    /**<jp>
     * ENDBANKNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of ENDBANKNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setEndBankNoOrder(int num, boolean bool)
    {
        setOrder(ENDBANKNO, num, bool);
    }

    /**<jp>
     * ENDBANKNOのソート順を取得
     * @return ENDBANKNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of ENDBAYNO.
     </en>*/
    public int getEndBankNoOrder()
    {
        return (getOrder(ENDBANKNO));
    }

    /**<jp>
     * ENDBAYNOの検索値をセットします。
     * @param ebay ENDBAYNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of ENDBAYNO.
     * @param ebay :the search value of ENDBAYNO
     </en>*/
    public void setEndBayNoNo(String ebay)
    {
        setValue(ENDBAYNO, ebay);
    }

    /**<jp>
     * ENDBAYNOを検索値を取得します。
     * @return ENDBAYNO
     </jp>*/
    /**<en>
     * Retrieve the search value of ENDBAYNO.
     * @return ENDBAYNO
     </en>*/
    public String getEndBayNo()
    {
        return (String)getValue(ENDBAYNO);
    }

    /**<jp>
     * ENDBAYNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of ENDBAYNO.
     * @param num  :prioriry in sort order
     * @param bool true: in ascending order, false: in descending order
     </en>*/
    public void setEndBayNoOrder(int num, boolean bool)
    {
        setOrder(ENDBAYNO, num, bool);
    }

    /**<jp>
     * ENDBAYNOのソート順を取得
     * @return ENDBAYNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of ENDBAYNO.
     </en>*/
    public int getEndBayNoOrder()
    {
        return (getOrder(ENDBAYNO));
    }

    /**<jp>
     * ENDLEVELNOの検索値をセットします。
     * @param elvl ENDLEVELNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of ENDLEVELNO.
     * @param elvl :the search value of ENDLEVELNO.
     </en>*/
    public void setEndLevelNo(String elvl)
    {
        setValue(ENDLEVELNO, elvl);
    }

    /**<jp>
     * ENDLEVELNOを検索値を取得します。
     * @return ENDLEVELNO
     </jp>*/
    /**<en>
     * Retrieve the search value of ENDLEVELNO.
     * @return ENDLEVELNO
     </en>*/
    public String getEndLevelNo()
    {
        return (String)getValue(ENDLEVELNO);
    }

    /**<jp>
     * ENDLEVELNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of ENDLEVELNO.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setEndLevelNoOrder(int num, boolean bool)
    {
        setOrder(ENDLEVELNO, num, bool);
    }

    /**<jp>
     * ENDLEVELNOのソート順を取得
     * @return ENDLEVELNO順
     </jp>*/
    /**<en>
     * Retrieve the sort order of ENDLEVELNO.
     * @return :the order of ENDLEVELNO
     </en>*/
    public int getEndLevelNoOrder()
    {
        return (getOrder(ENDLEVELNO));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

