// $Id: ZoneSelector.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * Zoneインスタンスを取得、保存するためのインターフェースです。
 * ゾーンとは、倉庫を一定のルールに基づいて分割・管理するための単位です。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is an interface used to obtain and store the Zone instance.
 * Zone is the control unit of space which is gained by deviding the space of warehouse according to
 * a certain rule.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public interface ZoneSelector
{
    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------
    /**<jp>
     * ゾーンを検索し、返します。
     * 候補のゾーンが複数ある場合は、その優先順に従って、配列として返されます。<br>
     * このメソッドは<code>WareHouse</code>から呼ばれるために用意されています。
     * 実際に棚検索を行うためには、<code>WareHouse</code>を利用してください。
     * @param plt ゾーン検索対象のPalletインスタンス
     * @param wh ゾーン検索対象倉庫のWareHouseインスタンス
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクト
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException パラメータが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Search the zone and return the result.
     * If there are more than one possible zones, return them in fprm of array according to the 
     * prioritized order. <br>
     * This method is prepared for <code>WareHouse</code> to call.
     * If actual location search is wanted, please use <code>WareHouse</code>.
     * @param plt :Pallet instance subject to zone search
     * @param wh  :Warehouse instance subject to zone search
     * @return    :Object of <code>Zone</code> created based on parameter
     * @throws ReadWriteException :Notifies if it failed to access data.
     * @throws InvalidDefineException :Notifies if the parameter is illegal.
     </en>*/
    public Zone[] select(Pallet plt, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException;

    /**<jp>
     * ゾーンを検索し、返します。
     * 候補のゾーンが複数ある場合は、その優先順に従って、配列として返されます。<br>
     * このメソッドは<code>WareHouse</code>から呼ばれるために用意されています。
     * 実際に棚検索を行うためには、<code>WareHouse</code>を利用してください。
     * @param hzone ハードゾーン
     * @param szone ソフトゾーン
     * @param wh ゾーン検索対象倉庫のWareHouseインスタンス
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクト
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException パラメータが不正な場合に通知されます。
     </jp>*/
    public Zone[] selectZone(String hzone, String szone, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException;

    /**<jp>
     * ゾーンを検索し、返します。
     * 候補のゾーンが複数ある場合は、その優先順に従って、配列として返されます。<br>
     * 空棚数のカウント時に使用します。
     * @param consignorCode 空棚検索の対象となるconsignorCode（現在未使用）
     * @param itemCode 空棚検索の対象となるitemCode（現在未使用）
     * @param height ゾーン検索対象の荷高
     * @param wh ゾーン検索対象倉庫のWareHouseインスタンス
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクト
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException パラメータが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Search zone and return its result.
     * If there are more than one possible zones, return them in fprm of array according to the 
     * prioritized order. <br>
     * This will be used when counting the empty locations.
     * @param consignorCode :temKey subject to empty location search(unused at moment)
     * @param itemCode :temKey subject to empty location search(unused at moment)
     * @param height :load height subject to zone search
     * @param wh     : Warehouse instance of the warehouse subject to zone search
     * @return       :Object of <code>Zone</code> created based on parameter
     * @throws ReadWriteException :Notifies if it failed to access data.
     * @throws InvalidDefineException :Notifies if the parameter is illegal.
     </en>*/
    public Zone[] selectcount(String consignorCode, String itemCode, int height, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException;

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

