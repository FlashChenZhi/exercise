// $Id: AreaRange.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;

/**<jp>
 * エリアが管理する倉庫範囲情報の操作を行なうクラス。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/06</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class operates the information of warehouse range that the area controls.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/06</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class AreaRange extends ToolEntity
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * エリアID
     </jp>*/
    /**<en>
     * Area ID
     </en>*/
    protected int wAreaId ;
    
    /**<jp>
     * 倉庫ステーションNo
     </jp>*/
    /**<en>
     * Warehouse station no.
     </en>*/
    protected String wWHStationNo ;
    
    /**<jp>
     * 作業場No
     </jp>*/
    /**<en>
     * Workshop no.
     </en>*/
    protected String wWPStationNo ;


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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>AreaRange</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct a new <CODE>AreaRange</CODE>.
     </en>*/
    public AreaRange()
    {
    }

    /**<jp>
     * 新しい<CODE>AreaRange</CODE>を構築します。
     * @param areaid   エリアID
     </jp>*/
    /**<en>
     * Construct a new <CODE>AreaRange</CODE>.
     * @param areaid   :area ID
     </en>*/
    public AreaRange(int areaid)
    {
        //<jp> インスタンス変数にセット</jp>
        //<en> Set as an instance variable.</en>
        setAreaId(areaid);
    }
    
    /**<jp>
     * 新しい<CODE>AreaRange</CODE>を構築します。
     * @param areaid     エリアID
     * @param whstnumber 倉庫ステーション№
     * @param wpstnumber 作業場№
     * @param ehandl     インスタンスハンドラ
     </jp>*/
    /**<en>
     * Construct a new <CODE>AreaRange</CODE>.
     * @param areaid     :area ID
     * @param whstnumber :warehouse station no.
     * @param wpstnumber :workshop no.
     * @param ehandl     :instance handler
     </en>*/
    public AreaRange(int     areaid,
                  String whstnumber,
                  String wpstnumber,
                  ToolEntityHandler    ehandl)
    {
        wAreaId             = areaid;
        wWHStationNo = whstnumber;
        wWPStationNo = wpstnumber;
        wHandler         = ehandl;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * エリアIDを設定します。
     * @param id エリアID
     </jp>*/
    /**<en>
     * Set the area ID.
     * @param id :area ID
     </en>*/
    public void setAreaId(int id)
    {
        wAreaId = id;
    }

    /**<jp>
     * エリアIDを取得します。
     * @return エリアID
     </jp>*/
    /**<en>
     * Retrieve the area ID.
     * @return :area ID
     </en>*/
    public int getAreaId()
    {
        return wAreaId;
    }

    /**<jp>
     * 倉庫Noを設定します。
     * @param wh 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no.
     * @param wh :warehouse no.
     </en>*/
    public void setWHStationNo(String wh)
    {
        wWHStationNo = wh;
    }
    
    /**<jp>
     * 倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no.
     * @return :warehouse no.
     </en>*/
    public String getWHStationNo()
    {
        return wWHStationNo;
    }

    /**<jp>
     * 作業場Noを設定します。
     * @param wp 作業場No
     </jp>*/
    /**<en>
     * Set the workshop no.
     * @param wp :workshop no.
     </en>*/
    public void setWPStationNo(String wp)
    {
        wWPStationNo = wp;
    }

    /**<jp>
     * 作業場Noを取得します。
     * @return 作業場No
     </jp>*/
    /**<en>
     * Retrieve the workshop no.
     * @return :workshop no.
     </en>*/
    public String getWPStationNo()
    {
        return wWPStationNo;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

