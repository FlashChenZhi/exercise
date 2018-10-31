// $Id: ToolLoadSizeSearchKey.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してTEMP_LOADSIZEテーブルを検索し、TEMP_LOADSIZEクラスのインスタンスを生成するために使用するキークラスです。
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
 * This is a key class which is used to search TEMP_LOADSIZE table by using handler class
 * and generate the instance of TEMP_LOADSIZE class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolLoadSizeSearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String LOADSIZE = "LOAD_SIZE";

    private static final String LOADSIZENAME = "LOAD_SIZE_NAME";

    private static final String LENGTH = "LENGTH";

    private static final String HEIGHT = "HEIGHT";

    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            LOADSIZE,
            LOADSIZENAME,
            LENGTH,
            HEIGHT,
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
    public ToolLoadSizeSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 荷姿の検索値をセットします。
     * @param loadsize 荷姿の検索値
     </jp>*/
    /**<en>
     * Set the search value of load size.
     * @param hgt :the search value of load size
     </en>*/
    public void setLoadSize(int loadsize)
    {
        setValue(LOADSIZE, loadsize);
    }

    /**<jp>
     * 荷姿を検索値を取得します。
     * @return 荷姿
     </jp>*/
    /**<en>
     * Retrieve the search value of load size.
     * @return :load size
     </en>*/
    public int getLoadSize()
    {
        Integer intobj = (Integer)getValue(LOADSIZE);
        return (intobj.intValue());
    }

    /**<jp>
     * 荷姿のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load size.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setLoadSizeOrder(int num, boolean bool)
    {
        setOrder(LOADSIZE, num, bool);
    }

    /**<jp>
     * 荷姿のソート順を取得
     * @return 荷姿順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load size
     * @return :load size
     </en>*/
    public int getLoadSizeOrder()
    {
        return (getOrder(LOADSIZE));
    }

    /**<jp>
     * 荷姿名称の検索値をセットします。
     * @param nam 荷姿名称の検索値
     </jp>*/
    /**<en>
     * Set the search value of load size name.
     * @param nam :the search value of load size name
     </en>*/
    public void setLoadSizeName(String nam)
    {
        setValue(LOADSIZENAME, nam);
    }

    /**<jp>
     * 荷姿名称を検索値を取得します。
     * @return 荷姿名称
     </jp>*/
    /**<en>
     * Retrieve the search value of load size name.
     * @return :name of load size
     </en>*/
    public String getLoadSizeName()
    {
        return (String)getValue(LOADSIZENAME);
    }

    /**<jp>
     * 荷姿名称のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load size name.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setLoadSizeNameOrder(int num, boolean bool)
    {
        setOrder(LOADSIZENAME, num, bool);
    }

    /**<jp>
     * 荷姿名称のソート順を取得
     * @return 荷姿名称順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load size name.
     * @return :the order of load size name
     </en>*/
    public int getLoadSizeNameOrder()
    {
        return (getOrder(LOADSIZENAME));
    }

    /**<jp>
     * 荷長の検索値をセットします。
     * @param length 荷長の検索値
     </jp>*/
    /**<en>
     * Set the search value of load length.
     * @param hgt :the search value of load length
     </en>*/
    public void setLength(int length)
    {
        setValue(LENGTH, length);
    }

    /**<jp>
     * 荷長を検索値を取得します。
     * @return 荷長
     </jp>*/
    /**<en>
     * Retrieve the search value of load length.
     * @return :load length
     </en>*/
    public int getLength()
    {
        Integer intobj = (Integer)getValue(LENGTH);
        return (intobj.intValue());
    }

    /**<jp>
     * 荷長のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load length.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setLengthOrder(int num, boolean bool)
    {
        setOrder(LENGTH, num, bool);
    }

    /**<jp>
     * 荷長のソート順を取得
     * @return 荷長順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load length
     * @return :load length
     </en>*/
    public int getLengthOrder()
    {
        return (getOrder(LENGTH));
    }

    /**<jp>
     * 荷高の検索値をセットします。
     * @param hgt 荷高の検索値
     </jp>*/
    /**<en>
     * Set the search value of load height.
     * @param hgt :the search value of load height
     </en>*/
    public void setHeight(int hgt)
    {
        setValue(HEIGHT, hgt);
    }

    /**<jp>
     * 荷高を検索値を取得します。
     * @return 荷高
     </jp>*/
    /**<en>
     * Retrieve the search value of load height.
     * @return :load height
     </en>*/
    public int getHeight()
    {
        Integer intobj = (Integer)getValue(HEIGHT);
        return (intobj.intValue());
    }

    /**<jp>
     * 荷高のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of load height.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setHeightOrder(int num, boolean bool)
    {
        setOrder(HEIGHT, num, bool);
    }

    /**<jp>
     * 荷高のソート順を取得
     * @return 荷高順
     </jp>*/
    /**<en>
     * Retrieve the sort order of load height
     * @return :load height
     </en>*/
    public int getHeightOrder()
    {
        return (getOrder(HEIGHT));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

