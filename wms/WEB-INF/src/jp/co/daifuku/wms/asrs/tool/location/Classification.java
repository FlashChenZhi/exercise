// $Id: Classification.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/**<jp>
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 </jp>*/
/**<en>
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 </en>*/

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * 商品マスタの商品分類情報の管理と操作を行うためのクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/05</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class controls and operates the information of item classifications of 
 * the article name master.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/08/05</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Classification extends ToolEntity
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 混載許可を表すフィールド(混載可能)
     </jp>*/
    /**<en>
     * Field of permission for the mix-loading (mix-loading accepted)
     </en>*/
    public static final int POSSIBLE = 1 ;
    
    /**<jp>
     * 混載許可を表すフィールド(混載不可)
     </jp>*/
    /**<en>
     * Field of permission for the mix-loading (mix-loading not accepted)
     </en>*/
    public static final int IMPOSSIBLE = 2 ;

    // Class variables -----------------------------------------------
    /**<jp>
     * 分類ID
     </jp>*/
    /**<en>
     * classification ID
     </en>*/
    protected int wClassificationId = 0 ;

    /**<jp>
     * 分類名称
     </jp>*/
    /**<en>
     * name of the classification
     </en>*/
    protected String wName = "" ;

    /**<jp>
     * 混載許可フラグ
     </jp>*/
    /**<en>
     * Flag of accepted mix-loading
     </en>*/
    protected int wForbiddenMix = POSSIBLE ;

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
     * 新しい<CODE>Classification</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct a new <CODE>Classification</CODE>.
     </en>*/
    public Classification()
    {
    }

    /**<jp>
     * 新しい<CODE>Classification</CODE>を構築します。
     * 検索結果をvector()にセットする時に使用します
     * @param classid 分類ID
     * @param name    分類名称
     * @param mix     混載許可フラグ
     </jp>*/
    /**<en>
     * Construct a new <CODE>Classification</CODE>.
     * This will be used when setting the search result in vector().
     * @param classid :classification ID
     * @param name    :name of the classification
     * @param mix     :flag of accepted mix-loading
     </en>*/
    public Classification(int        classid, 
                          String    name,
                          int         mix
                         )
    {
        //<jp> インスタンス変数にセット</jp>
        //<en> Set as an instance variable.</en>
        setClassificationId(classid);
        setName(name);
        setForbiddenMix(mix);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 分類IDに値をセットします。
     * @param classid セットする分類ID
     </jp>*/
    /**<en>
     * Set teh valud of classification ID.
     * @param classid :classification ID to set
     </en>*/
    public void setClassificationId(int classid)
    {
        wClassificationId = classid ;
    }

    /**<jp>
     * 分類IDを取得します。
     * @return 分類ID
     </jp>*/
    /**<en>
     * Retrieve the classification ID.
     * @return :classification ID
     </en>*/
    public int getClassificationId()
    {
        return wClassificationId ;
    }

    /**<jp>
     * 分類名称に値をセットします。
     * @param name セットする分類名称
     </jp>*/
    /**<en>
     * Set teh value of name of the classification.
     * @param name :name of the classification to set
     </en>*/
    public void setName(String name) 
    {
        wName = name ;
    }

    /**<jp>
     * 分類名称を取得します。
     * @return 分類名称
     </jp>*/
    /**<en>
     * Retrieve the name of the classification.
     * @return :name of the classification
     </en>*/
    public String getName()
    {
        return wName ;
    }

    /**<jp>
     * 混載許可フラグに値をセットします。
     * @param mix セットする混載許可フラグ
     </jp>*/
    /**<en>
     * Set the value of flag of accepted mix-loading.
     * @param mix :flag of accepted mix-loading to set
     </en>*/
    public void setForbiddenMix(int mix)
    {
        wForbiddenMix = mix ;
    }

    /**<jp>
     * 混載許可フラグを取得します。
     * @return 混載許可フラグ
     </jp>*/
    /**<en>
     * Retrieve the flag of accepted mix-loading.
     * @return flag of accepted mix-loading
     </en>*/
    public int getForbiddenMix()
    {
        return wForbiddenMix ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

