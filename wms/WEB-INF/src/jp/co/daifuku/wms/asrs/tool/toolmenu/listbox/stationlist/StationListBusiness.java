// $Id: StationListBusiness.java 834 2008-10-28 06:41:39Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.stationlist;

import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolDatabaseFinder;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolListBoxUtil;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionRet;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.ToolSessionStationRet;

/**
 * ステーション一覧画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 834 $, $Date: 2008-10-28 15:41:39 +0900 (火, 28 10 2008) $
 * @author  $Author: okamura $
 */
public class StationListBusiness
        extends StationList
        implements WMSToolConstants
{
    // Class fields --------------------------------------------------
    /** 
     * エリアIDの受け渡しに使用するキーです
     */
    public static final String AREAID_KEY = "AREAID_KEY";

    // Class variables -----------------------------------------------
    /** 
     * 画面作業区分の受け渡しに使用するキーです
     */
    public static final String MAINSTATION_KEY = "MAINSTATION_KEY";

    /** 
     * 倉庫No.の受け渡しに使用するキーです
     */
    public static final String WHSTATIONNO_KEY = "WHSTATIONNO_KEY";

    /** 
     * 親ステーションNo.の受け渡しに使用するキーです
     */
    public static final String PARENTSTNO_KEY = "PARENTSTNO_KEY";

    /** 
     * ステーションNo.の受け渡しに使用するキーです
     */
    public static final String STATIONNO_KEY = "STATIONNO_KEY";

    /** 
     * ステーション名称の受け渡しに使用するキーです
     */
    public static final String STATIONNAME_KEY = "STATIONNAME_KEY";

    /** 
     * 作業場種別の受け渡しに使用するキーです
     */
    public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";

    /** 
     * アイルステーションNo.の受け渡しに使用するキーです
     */
    public static final String AISLESTATIONNO_KEY = "AISLESTATIONNO_KEY";

    /** 
     * AGCNo.の受け渡しに使用するキーです
     */
    public static final String AGCNO_KEY = "AGCNO_KEY";

    /**<jp> クラス名（入庫専用） </jp>*/
    /**<en> Class name (dedicated for storage)</en>*/
    public static String CLASS_STORAGE = "jp.co.daifuku.wms.asrs.location.StorageStationOperator";

    /**<jp> クラス名（出庫専用） </jp>*/
    /**<en> Class name (dedicated for retrieva)</en>*/
    public static String CLASS_RETRIEVAL = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator";

    /**<jp> クラス名（固定荷受台・自走台車） </jp>*/
    /**<en> Class name (P&D stand, powered cart)</en>*/
    public static String CLASS_INOUTSTATION = "jp.co.daifuku.wms.asrs.location.InOutStationOperator";

    /**<jp> クラス名（コの字入庫） </jp>*/
    /**<en> Class name  (U-shaped storage) </en>*/
    public static String CLASS_FREESTORAGE = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator";

    /**<jp> クラス名（コの字出庫） </jp>*/
    /**<en> Class name  (U-shaped retrieval)</en>*/
    public static String CLASS_FREERETRIEVAL = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator";

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {

            //画面名をセットする
            lbl_ListName.setText(DisplayText.getText("TLE-W9002"));

            //Sessionに取り残されているオブジェクトのコネクションをクローズする
            ToolListBoxUtil.deleteSessionRet(this.getSession());

            //コネクションの取得
            conn = ConnectionManager.getSessionConnection(DATASOURCE_NAME, this);

            ToolFindUtil findutil = new ToolFindUtil(conn);
            //呼び出し元画面でセットされたパラメータの取得
            //<jp> 検索キー（画面作業区分）</jp>
            //<en> search key (whether/not with on-line indication)</en>
            String mainstation = this.request.getParameter(MAINSTATION_KEY);
            //<jp> 検索キー（倉庫No.）</jp>
            //<en> search key (warehouse no.)</en>
            String whstation = this.request.getParameter(WHSTATIONNO_KEY);
            //<jp> 検索キー(親ステーションNo.)</jp>
            //<en> search key (parent station no.)</en>
            String parentstnumber = this.request.getParameter(PARENTSTNO_KEY);
            //<jp> 検索キー(ステーションNo.)</jp>
            //<en> search key (station no.)</en>
            String station = this.request.getParameter(STATIONNO_KEY);
            //<jp> 検索キー(作業場種別)</jp>
            //<en> search key (workshop type)</en>
            String worktype = this.request.getParameter(WORKPLACETYPE_KEY);

            String whstno = null;
            if (whstation != null && !whstation.equals(""))
            {
                whstno = findutil.getWarehouseStationNumber(Integer.parseInt(whstation));
            }
            //SessionItemRet インスタンス生成
            ToolSessionStationRet listbox =
                    new ToolSessionStationRet(conn, station, whstno, parentstnumber, worktype,
                            Integer.parseInt(mainstation), null);
            //Sessionにlistboxを保持
            this.getSession().setAttribute("LISTBOX", listbox);
            //最初のページを表示
            setList(listbox, "first");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            // コネクションをクローズします
            if (conn != null)
            {
                conn.close();
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    // Private methods -----------------------------------------------
    /** <jp>
     * リストセルに値をセットします。
     * @param listbox  ToolSessionStationRet
     * @param actionName String
     * @throws Exception 
     </jp> */
    /** <en>
     * Set data to listcell.
     * @param listbox  ToolSessionStationRet
     * @param actionName String
     * @throws Exception      
     </en> */
    protected void setList(ToolSessionStationRet listbox, String actionName)
            throws Exception
    {
        //ページ情報をセット
        listbox.setActionName(actionName);

        //<jp> 検索結果の件数についてのチェックを行なう</jp>
        //<en> Check the number of search result data.</en>
        String errorMsg = listbox.checkLength();
        //<jp> エラーなし</jp>
        //<en> No errors.</en>
        if (errorMsg == null)
        {
            Station[] station = listbox.getStation();
            int len = 0;
            if (station != null)
            {
                len = station.length;
            }

            if (len > 0)
            {
                //Pagerへ値セット
                pgr_U.setMax(listbox.getLength()); //最大件数
                pgr_U.setPage(listbox.getCondition()); //1Page表示件数
                pgr_U.setIndex(listbox.getCurrent() + 1); //開始位置
                pgr_D.setMax(listbox.getLength()); //最大件数
                pgr_D.setPage(listbox.getCondition()); //1Page表示件数
                pgr_D.setIndex(listbox.getCurrent() + 1); //開始位置

                //行をすべて削除
                lst_StationList.clearRow();

                String ailestno = "";
                String stationno = "";
                String stationname = "";
                String parentstno = "";
                String agcno = "";
                String clname = "";
                //                String workplacetype = "";
                String workplacetypechar = "";

                //Tipで使用する銘板
                String title_StationName = DisplayText.getText("LBL-W9052");
                String title_WorkPlaceType = DisplayText.getText("LBL-W9041");

                for (int i = 0; i < len; i++)
                {
                    if (!station[i].getAisleStationNo().equals(""))
                    {
                        ailestno = station[i].getAisleStationNo();
                    }
                    else
                    {
                        ailestno = DisplayText.getText("LBL-W9036");
                    }

                    //<jp> ステーションNo.</jp>
                    //<en> station no.</en>
                    stationno = station[i].getStationNo();
                    //<jp> ステーション名称</jp>
                    //<en> station name</en>
                    stationname = station[i].getStationName();
                    //<jp> ステーション種別</jp>
                    //<en> station type</en>
                    //                    workplacetype = Integer.toString(station[i].getType());
                    //<jp> 作業場No.／代表ステーションNo.</jp>
                    //<en> workshop no./ main station no.</en>
                    if (!station[i].getParentStationNo().equals(""))
                    {
                        parentstno = station[i].getParentStationNo();
                    }
                    else
                    {
                        parentstno = DisplayText.getText("LBL-W9036");
                    }
                    //<jp> AGCNo.</jp>
                    //<en> AGCNo.</en>
                    agcno = Integer.toString(station[i].getGroupController().getNumber());

                    //<jp> クラス名称</jp>
                    //<en> Class name</en>
                    clname = station[i].getClassName();

                    if (clname.equals(CLASS_STORAGE))
                    {
                        //<jp>入庫専用</jp>
                        //<en>dedicated for the storage</en>
                        workplacetypechar = DisplayText.getText("RDB-W9004");
                    }
                    else if (clname.equals(CLASS_RETRIEVAL))
                    {
                        //<jp>出庫専用</jp>
                        //<en>dedicated for the retrieval</en>
                        workplacetypechar = DisplayText.getText("RDB-W9005");
                    }
                    else if (clname.equals(CLASS_INOUTSTATION))
                    {
                        //<jp>固定荷受台・自走台車</jp>
                        //<en>P&D stand, self-drive cart</en>
                        workplacetypechar = DisplayText.getText("RDB-W9006");
                    }
                    else if (clname.equals(CLASS_FREESTORAGE))
                    {
                        //<jp>コの字（入庫側）</jp>
                        //<en>U-shaped (storage side)</en>
                        workplacetypechar = DisplayText.getText("RDB-W9007");
                    }
                    else if (clname.equals(CLASS_FREERETRIEVAL))
                    {
                        //<jp>コの字（出庫側）</jp>
                        //<en>U-shaped (retrieval side)</en>
                        workplacetypechar = DisplayText.getText("RDB-W9008");
                    }
                    else
                    {
                        //<jp>作業場</jp>
                        //<en>workshop</en>
                        //                        workplacetypechar = DisplayText.getText("RDB-W9026");
                        workplacetypechar = station[i].getStationName();
                    }

                    //行追加
                    //最終行を取得
                    int count = lst_StationList.getMaxRows();
                    lst_StationList.setCurrentRow(count);
                    lst_StationList.addRow();
                    lst_StationList.setValue(0, CollectionUtils.getConnectedString(stationname, ""));
                    lst_StationList.setValue(1, Integer.toString(count + listbox.getCurrent()));
                    lst_StationList.setValue(2, stationno);
                    lst_StationList.setValue(3, workplacetypechar);
                    lst_StationList.setValue(4, ailestno);
                    lst_StationList.setValue(5, agcno);
                    lst_StationList.setValue(6, parentstno);

                    //TOOL TIPをセット
                    ToolTipHelper toolTip = new ToolTipHelper();
                    toolTip.add(title_StationName, stationname);
                    toolTip.add(title_WorkPlaceType, workplacetypechar);

                    //TOOL TIPをセットする    
                    lst_StationList.setToolTip(count, toolTip.getText());
                }
            }
            else
            {
                //Pagerへの値セット
                pgr_U.setMax(0); //最大件数
                pgr_U.setPage(0); //1Page表示件数
                pgr_U.setIndex(0); //開始位置
                pgr_D.setMax(0); //最大件数
                pgr_D.setPage(0); //1Page表示件数
                pgr_D.setIndex(0); //開始位置

                //ヘッダーを隠します
                lst_StationList.setVisible(false);
                //MSG-W9016 = 対象データはありませんでした
                lbl_InMsg.setResourceKey("MSG-W9016");
            }
        }
        else
        {
            //Pagerへの値セット
            pgr_U.setMax(0); //最大件数
            pgr_U.setPage(0); //1Page表示件数
            pgr_U.setIndex(0); //開始位置
            pgr_D.setMax(0); //最大件数
            pgr_D.setPage(0); //1Page表示件数
            pgr_D.setIndex(0); //開始位置

            //ヘッダーを隠します
            lst_StationList.setVisible(false);
            // 検索結果の件数が最大表示件数を超えたかもしくは、データなし
            lbl_InMsg.setText(MessageResource.getMessage(errorMsg));
        }
    }

    // Event handler methods -----------------------------------------
    /** <jp>
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a close button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        //終了処理
        ToolSessionRet sessionret = (ToolSessionRet)this.getSession().getAttribute("LISTBOX");
        if (sessionret != null)
        {
            ToolDatabaseFinder finder = sessionret.getFinder();
            if (finder != null)
            {
                finder.close();
            }
            sessionret.closeConnection();
        }
        this.getSession().removeAttribute("LISTBOX");

        //呼び出し元の画面へ遷移します
        parentRedirect(null);
    }

    /** <jp>
     * Pagerの次へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a next button of Pager is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_U_Next(ActionEvent e)
            throws Exception
    {
        ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
        setList(listbox, "next");
    }

    /** <jp>
     * Pagerの前へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a previous button of Pager is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_U_Prev(ActionEvent e)
            throws Exception
    {
        ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
        setList(listbox, "previous");
    }

    /** <jp>
     *  Pagerの最後ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a last button of Pager is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_U_Last(ActionEvent e)
            throws Exception
    {
        ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
        setList(listbox, "last");
    }

    /** <jp>
     *  Pagerの最初ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a first button of Pager is pushed.    
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        ToolSessionStationRet listbox = (ToolSessionStationRet)this.getSession().getAttribute("LISTBOX");
        setList(listbox, "first");
    }

    /** <jp>
     * リストがクリックされたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when it clicks on the list.    
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_StationList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_StationList.setCurrentRow(lst_StationList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(STATIONNO_KEY, lst_StationList.getValue(2));
        param.setParameter(STATIONNAME_KEY, CollectionUtils.getString(0, lst_StationList.getValue(0)));
        param.setParameter(WORKPLACETYPE_KEY, lst_StationList.getValue(3));
        param.setParameter(AISLESTATIONNO_KEY, lst_StationList.getValue(4));
        param.setParameter(AGCNO_KEY, lst_StationList.getValue(5));

        //終了処理
        btn_Close_U_Click(null);

        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

    /** <jp>
     * Pagerの次へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a next button of Pager is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_D_Next(ActionEvent e)
            throws Exception
    {
        pgr_U_Next(e);
    }

    /** <jp>
     * Pagerの前へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a previous button of Pager is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_D_Prev(ActionEvent e)
            throws Exception
    {
        pgr_U_Prev(e);
    }

    /** <jp>
     *  Pagerの最後ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a last button of Pager is pushed.    
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_D_Last(ActionEvent e)
            throws Exception
    {
        pgr_U_Last(e);
    }

    /** <jp>
     *  Pagerの最初ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a first button of Pager is pushed.    
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void pgr_D_First(ActionEvent e)
            throws Exception
    {
        pgr_U_First(e);
    }

    /** <jp>
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a close button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        btn_Close_U_Click(e);
    }

}
//end of class
