// $Id: Area.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.common.MessageResource;


/**<jp>
 * 倉庫の範囲と出庫引当方法を定義したエリア情報の管理を行なうクラス。
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
 * This class controls the area information which is defined with teh range of warehouse
 * and the allocations rules of retrievals.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/06</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Area extends ToolEntity
{
    // Class fields --------------------------------------------------

    /**
     * エリア種別 : AS/RS
     */
    public static final String AREA_TYPE_ASRS = "1";

    // Class variables -----------------------------------------------

    /**<jp>
     * システム管理区分
     </jp>*/
    protected String wManagementType ;

    /**<jp>
     * エリアNo
     </jp>*/
    protected String wAreaNo ;

    /**<jp>
     * エリア名称
     </jp>*/
    protected String wAreaName ;

    /**<jp>
     * エリア種別
     </jp>*/
    protected String wAreaTypre ;

    /**<jp>
     * 棚管理方式
     </jp>*/
    protected String wLocationType;

    /**<jp>
     * 棚表示形式
     </jp>*/
    protected String wLocationStyle;

    /**<jp>
     * 仮置在庫作成区分
     </jp>*/
    protected String wTemporaryAreaType;

    /**<jp>
     * 移動先仮置エリア
     </jp>*/
    protected String wTemporaryArea;

    /**<jp>
     * 空棚検索方法
     </jp>*/
    protected String wVacantSearchType;

    /**<jp>
     * 倉庫ステーションNo
     </jp>*/
    protected String wWhstationNo;

    /**
     * 登録日時
     */
    protected Date wRegistDate = null;

    /**
     * 登録処理名
     */
    protected String wRegistPname = "";

    /**
     * 最終更新日時
     */
    protected Date wLastUpdateDate = null;

    /**
     * 最終更新処理名
     */
    protected String wLastUpdatePname = "";

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public static String wDelim = MessageResource.DELIM ;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this method.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>Area</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct a new <CODE>Area</CODE>.
     </en>*/
    public Area()
    {
    }

    /**<jp>
     * 新しい<CODE>Area</CODE>を構築します。
     * @param areaid   エリアNo
     </jp>*/
    /**<en>
     * Construct a new <CODE>Area</CODE>.
     * @param areano   :area No
     </en>*/
    public Area(String areano)
    {
        //<jp> インスタンス変数にセット</jp>
        //<en> Set as an instance variable.</en>
        setAreaNo(areano);
    }

    /**<jp>
     * 新しい<CODE>Area</CODE>を構築します。
     * @param managementtype     システム管理区分
     * @param areano             エリアNo
     * @param areaname           エリア名称
     * @param areatype           エリア種別
     * @param locationtype       棚管理方式
     * @param locationstyle      棚表示形式
     * @param temporaryareatype  仮置在庫作成区分
     * @param temporaryarea      移動先仮置エリア
     * @param vacantsearchtype   空棚検索方法
     * @param whstationno        倉庫ステーションNo
     * @param registdate         登録日時
     * @param registpname        登録処理名
     * @param lastupdatedate     最終更新日時
     * @param lastupdatepname    最終更新処理名
     </jp>*/
    public Area(String     managementtype,
                 String    areano,
                 String    areaname,
                 String    areatype,
                 String    locationtype,
                 String    locationstyle,
                 String    temporaryareatype,
                 String    temporaryarea,
                 String    vacantsearchtype,
                 String    whstationno,
                 Date      registdate,
                 String    registpname,
                 Date      lastupdatedate,
                 String    lastupdatepname)
    {
        wManagementType     = managementtype;
        wAreaNo             = areano;
        wAreaName           = areaname;
        wAreaTypre          = areatype;
        wLocationType       = locationtype;
        wLocationStyle      = locationstyle;
        wTemporaryAreaType  = temporaryareatype;
        wTemporaryArea      = temporaryarea;
        wVacantSearchType   = vacantsearchtype;
        wWhstationNo        = whstationno;
        wRegistDate         = registdate;
        wRegistPname        = registpname;
        wLastUpdateDate     = lastupdatedate;
        wLastUpdatePname    = lastupdatepname;
    }
    
    // Public methods ------------------------------------------------

    /**<jp>
     * システム管理区分を設定します。
     * @param kind システム管理区分
     </jp>*/
    public void setManagementType(String kind)
    {
        wManagementType = kind;
    }

    /**<jp>
     * システム管理区分を取得します。
     * @return システム管理区分
     </jp>*/
    public String getManagementType()
    {
        return wManagementType;
    }

    /**<jp>
     * エリアNoを設定します。
     * @param no エリアNo
     </jp>*/
    public void setAreaNo(String no)
    {
        wAreaNo = no;
    }

    /**<jp>
     * エリアNoを取得します。
     * @return エリアNo
     </jp>*/
    public String getAreaNo()
    {
        return wAreaNo;
    }

    /**<jp>
     * エリア名称を設定します。
     * @param nm エリア名称
     </jp>*/
    /**<en>
     * Set the name of the area.
     * @param nm :name of the area
     </en>*/
    public void setAreaName(String nm)
    {
        wAreaName = nm;
    }

    /**<jp>
     * エリア名称を取得します。
     * @return エリア名称
     </jp>*/
    /**<en>
     * Retrieve the name of the area.
     * @return :name of the area
     </en>*/
    public String getAreaName()
    {
        return wAreaName;
    }

    /**<jp>
     * エリア種別を設定します。
     * @param type エリア種別
     </jp>*/
    public void setAreaTypre(String type)
    {
        wAreaTypre = type;
    }

    /**<jp>
     * エリア種別を取得します。
     * @return エリア種別
     </jp>*/
    public String getAreaTypre()
    {
        return wAreaTypre;
    }

    /**<jp>
     * 棚管理方式を設定します。
     * @param type 棚管理方式
     </jp>*/
    public void setLocationType(String type)
    {
        wLocationType = type;
    }

    /**<jp>
     * 棚管理方式を取得します。
     * @return 棚管理方式
     </jp>*/
    public String getLocationType()
    {
        return wLocationType;
    }

    /**<jp>
     * 棚表示形式を設定します。
     * @param style 棚表示形式
     </jp>*/
    public void setLocationStyle(String style)
    {
        wLocationStyle = style;
    }

    /**<jp>
     * 棚表示形式を取得します。
     * @return 棚表示形式
     </jp>*/
    public String getLocationStyle()
    {
        return wLocationStyle;
    }

    /**<jp>
     * 仮置在庫作成区分を設定します。
     * @param type 仮置在庫作成区分
     </jp>*/
    public void setTemporaryAreaType(String type)
    {
        wTemporaryAreaType = type;
    }

    /**<jp>
     * 仮置在庫作成区分を取得します。
     * @return 仮置在庫作成区分
     </jp>*/
    public String getTemporaryAreaType()
    {
        return wTemporaryAreaType;
    }

    /**<jp>
     * 移動先仮置エリアを設定します。
     * @param area 移動先仮置エリア
     </jp>*/
    public void setTemporaryArea(String area)
    {
        wTemporaryArea = area;
    }

    /**<jp>
     * 移動先仮置エリアを取得します。
     * @return 移動先仮置エリア
     </jp>*/
    public String getTemporaryArea()
    {
        return wTemporaryArea;
    }

    /**<jp>
     * 空棚検索方法を設定します。
     * @param type 空棚検索方法
     </jp>*/
    public void setVacantSearchType(String type)
    {
        wVacantSearchType = type;
    }

    /**<jp>
     * 空棚検索方法を取得します。
     * @return 空棚検索方法
     </jp>*/
    public String getVacantSearchType()
    {
        return wVacantSearchType;
    }

    /**<jp>
     * 倉庫ステーションNoを設定します。
     * @param no 倉庫ステーションNo
     </jp>*/
    public void setWhstationNo(String no)
    {
        wWhstationNo = no;
    }

    /**<jp>
     * 倉庫ステーションNoを取得します。
     * @return 倉庫ステーションNo
     </jp>*/
    public String getWhstationNo()
    {
        return wWhstationNo;
    }

    /**
     * 登録日時に値をセットします。
     * @param registdate セットする登録日
     */
    public void setRegistDate(Date registdate)
    {
        wRegistDate = registdate;
    }

    /**
     * 登録日時を取得します。
     * @return 登録日時
     */
    public Date getRegistDate()
    {
        return wRegistDate;
    }

    /**
     * 登録処理名に値をセットします。
     * @param registpname セットする登録処理名
     */
    public void setRegistPname(String registpname)
    {
        wRegistPname = registpname;
    }

    /**
     * 登録処理名を取得します。
     * @return 登録処理名
     */
    public String getRegistPname()
    {
        return wRegistPname;
    }

    /**
     * 最終更新日に値をセットします。
     * @param lastupdatedate セットする最終更新日
     */
    public void setLastUpdateDate(Date lastupdatedate)
    {
        wLastUpdateDate = lastupdatedate;
    }

    /**
     * 最終更新日を取得します。
     * @return 最終更新日
     */
    public Date getLastUpdateDate()
    {
        return wLastUpdateDate;
    }

    /**
     * 最終処理名に値をセットします。
     * @param lastupdatepname セットする最終処理名
     */
    public void setLastUpdatePname(String lastupdatepname)
    {
        wLastUpdatePname = lastupdatepname;
    }

    /**
     * 最終処理名を取得します。
     * @return 最終処理名
     */
    public String getLastUpdatePname()
    {
        return wLastUpdatePname;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

