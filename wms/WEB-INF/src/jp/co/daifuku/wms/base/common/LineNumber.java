// $Id: LineNumber.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Label;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.base.controller.ReceivingPlanController;
import jp.co.daifuku.wms.base.controller.RetrievalPlanController;
import jp.co.daifuku.wms.base.controller.StoragePlanController;
import jp.co.daifuku.wms.base.controller.XDPlanController;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.crossdock.display.planregist.XDPlanRegist2Business;
import jp.co.daifuku.wms.crossdock.display.planregist.XDPlanRegistBusiness;
import jp.co.daifuku.wms.receiving.display.planregist.ReceivingPlanRegist2Business;
import jp.co.daifuku.wms.retrieval.display.planregist.RetrievalPlanRegist2Business;
import jp.co.daifuku.wms.storage.display.planregist.StoragePlanRegist2Business;


public class LineNumber
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * TODO default constructor.
     */
    public LineNumber()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Check if the given Ticket No (Slip No) exists or not.
     * Returns 1 if does not exist. Otherwise the next number.
     * 
     * @param slipNo String:  the given Slip No
     * @param type  int:  1=Receiving, 2=Storage, 3=Retrieval 
     * @param page  Page: one business class of ReceivingPlanRegist2Business or 
     *                    StoragePlanRegist2Busines or RetrievalPlanRegist2Business
     * @param auto  boolean:  Auto_Line_No flag in WMSParam.propeties
     *                        true=auto numbering
     *                        false=not auto numbering
     * @return int the next number 
     * @throws Exception
     *     
     */
    public static int getNextLineNo(String slipNo, int type, Page page, boolean auto)
            throws Exception
    {
        int next = 0;
        Connection conn = null;

        try
        {
            conn = ConnectionManager.getRequestConnection(WMSConstants.DATASOURCE_NAME, page);

            // Check which Plan:  Receiving, Storage, Picking
            if (type == 1)
            {
                next = getNextLineNoForReceivingPlan(conn, page, slipNo, auto);
            }
            else if (type == 2)
            {
                next = getNextLineNoForStoragePlan(conn, page, slipNo, auto);
            }
            else if (type == 3)
            {
                next = getNextLineNoForRetrievalPlan(conn, page, slipNo, auto);
            }
            else if (type == 4)
            {
                next = getNextLineNoForXDPlanReceiving(conn, page, slipNo, auto);
            }
            else if (type == 5)
            {
                next = getNextLineNoForXDPlanShipping(conn, page, slipNo, auto);
            }
            else
            {
                // Unknow type
                // ..... 
                // .....
                // For debug
                System.out.println("\n////////////////////////////////////// ");
                System.out.println("  Unknown type in LineNumber.java        ");
                System.out.println("////////////////////////////////////// \n");
                throw new Exception("Unknown type in LineNumber.java");
            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        finally
        {
            if (conn != null) conn.close();
        }
        return next;
    }


    private static int getNextLineNoForReceivingPlan(Connection conn, Page page, String slipNo, boolean auto)
            throws Exception
    {
        int next = 0;

        ReceivingPlanController receivingController = new ReceivingPlanController(conn, page.getClass());
        // Go to table DNReceivingPlan, check if the given Slip No exists or not.
        ReceivingPlan[] lines = receivingController.getLineNos(slipNo, "0"); // ConsignorCode == 0
        // If eixsts, return the next number.
        if (lines.length != 0)
        {
            if (auto) next = lines[lines.length - 1].getReceiveLineNo() + 1;
            else next = receivingController.getMaxLineNo(slipNo, "0") + 1;
        }
        // If does not exist, return 1
        else next = 1;

        if (WmsParam.AUTO_LINE_NO)
        {
            ((ReceivingPlanRegist2Business)page).txt_LineNo.setReadOnly(true);
        }

        return next;
    }


    private static int getNextLineNoForStoragePlan(Connection conn, Page page, String slipNo, boolean auto)
            throws Exception
    {
        int next = 0;

        StoragePlanController storageController = new StoragePlanController(conn, page.getClass());
        // Go to table DNStoragePlan, check if the given Slip No exists or not.
        StoragePlan[] lines = storageController.getLineNos(slipNo, "0"); // ConsignorCode == 0  
        // If eixsts, return the next number.
        if (lines.length != 0)
        {
            if (auto) next = lines[lines.length - 1].getReceiveLineNo() + 1;
            else next = storageController.getMaxLineNo(slipNo, "0") + 1;
        }
        // If does not exist, return 1
        else next = 1;

        if (WmsParam.AUTO_LINE_NO)
        {
            //((StoragePlanRegist2Business)page).txt_LineNo.setReadOnly(true);
        }

        return next;
    }


    private static int getNextLineNoForRetrievalPlan(Connection conn, Page page, String slipNo, boolean auto)
            throws Exception
    {
        int next = 0;

        RetrievalPlanController retrievalController = new RetrievalPlanController(conn, page.getClass());
        // Go to table DNRetrievalPlan, check if the given Slip No exists or not.
        RetrievalPlan[] lines = retrievalController.getLineNos(slipNo, "0"); // ConsignorCode == 0
        // If eixsts, return the next number.
        if (lines.length != 0)
        {
            if (auto) next = lines[lines.length - 1].getShipLineNo() + 1;
            else next = retrievalController.getMaxLineNo(slipNo, "0") + 1;
        }
        // If does not exist, return 1
        else next = 1;

        if (WmsParam.AUTO_LINE_NO)
        {
            ((RetrievalPlanRegist2Business)page).txt_LineNo.setReadOnly(true);
        }

        return next;
    }


    private static int getNextLineNoForXDPlanReceiving(Connection conn, Page page, String slipNo, boolean auto)
            throws Exception
    {
        int next = 0;

        XDPlanController XDController = new XDPlanController(conn, page.getClass());
        // Go to table DNRetrievalPlan, check if the given Slip No exists or not.
        CrossDockPlan[] lines = XDController.getReceivingLineNos(slipNo, "0"); // ConsignorCode == 0
        // If eixsts, return the next number.
        if (lines.length != 0)
        {
            if (auto) next = lines[lines.length - 1].getReceiveLineNo() + 1;
            else next = XDController.getMaxReceivingLineNo(slipNo, "0") + 1;
        }
        // If does not exist, return 1
        else next = 1;

        if (WmsParam.AUTO_LINE_NO)
        {
            ((XDPlanRegistBusiness)page).txt_LineNo.setReadOnly(true);
        }

        return next;
    }


    private static int getNextLineNoForXDPlanShipping(Connection conn, Page page, String slipNo, boolean auto)
            throws Exception
    {
        int next = 0;

        XDPlanController XDController = new XDPlanController(conn, page.getClass());
        // Go to table DNRetrievalPlan, check if the given Slip No exists or not.
        CrossDockPlan[] lines = XDController.getShippingLineNos(slipNo, "0"); // ConsignorCode == 0
        // If eixsts, return the next number.
        if (lines.length != 0)
        {
            if (auto) next = lines[lines.length - 1].getShipLineNo() + 1;
            else next = XDController.getMaxShippingLineNo(slipNo, "0") + 1;
        }
        // If does not exist, return 1
        else next = 1;

        if (WmsParam.AUTO_LINE_NO)
        {
            ((XDPlanRegist2Business)page).txt_ShipSlipLineNo.setReadOnly(true);
        }

        return next;
    }

    public static int getNextLineNoForReceivingPlan(ArrayList<Integer> ListOfLineNo, Label lbl_InSlip,
            ListCellModel listCellModel, NumberTextBox txt_LineNo, boolean auto, boolean modify, boolean delete,
            ListCellKey COL_NUM_LINENO, Page page)
            throws Exception
    {
        int next = 0;
        int row = 0;
        int col = 0;
        ReceivingPlanRegist2Business business = ((ReceivingPlanRegist2Business)page);
        ListCell listcell = business.lst_ReceivingPlanInput;

        ListCellLine line = listCellModel.get(1);
        if (line != null) col = line.getIndex(COL_NUM_LINENO);
        if (WmsParam.AUTO_LINE_NO)
        {
            if (delete)
            {
                ListOfLineNo.remove(ListOfLineNo.size() - 1);
                if (line != null)
                {
                    for (int i = 0; i < listcell.getMaxRows() - 1; i++)
                    {
                        listcell.setCurrentRow(i + 1);
                        listcell.setValue(col, "" + ListOfLineNo.get(i));
                    }
                }
            }
            if (modify && !delete)
            {
                row = listcell.getHighlightLines()[0];
                listcell.setCurrentRow(row);
                next = Integer.parseInt(listcell.getValue(col));
            }
            else
            {
                if (ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(lbl_InSlip.getText(), 1, page, auto);
                }
                else
                {
                    next = ListOfLineNo.get(ListOfLineNo.size() - 1) + 1;
                }
            }
        }
        else
        {
            if (delete)
            {
                row = listcell.getActiveRow();
                ListOfLineNo.remove(row - 1);
            }
            if (modify && !delete)
            {
                row = listcell.getHighlightLines()[0];
                listcell.setCurrentRow(row);
                next = Integer.parseInt(listcell.getValue(col));
            }
            else
            {
                if (ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(lbl_InSlip.getText(), 1, page, auto);
                }
                else
                {
                    next = getMaxLineNo(ListOfLineNo) + 1;
                }
            }
        }
        return next;
    }


    public static int getNextLineNoForStoragePlan(ArrayList<Integer> ListOfLineNo, Label lbl_InSlip,
            ListCellModel listCellModel, NumberTextBox txt_LineNo, boolean auto, boolean modify, boolean delete,
            ListCellKey COL_NUM_LINENO, Page page)
            throws Exception
    {

        int next = 0;
        int row = 0;
        int col = 0;
        StoragePlanRegist2Business business = ((StoragePlanRegist2Business)page);
        ListCell listcell = business.lst_StoragePlanInput;

        ListCellLine line = listCellModel.get(1);
        if (line != null) col = line.getIndex(COL_NUM_LINENO);
        if (WmsParam.AUTO_LINE_NO)
        {
            if (delete)
            {
                ListOfLineNo.remove(ListOfLineNo.size() - 1);
                if (line != null)
                {
                    for (int i = 0; i < listcell.getMaxRows() - 1; i++)
                    {
                        listcell.setCurrentRow(i + 1);
                        listcell.setValue(col, "" + ListOfLineNo.get(i));
                    }
                }
            }
            if (modify && !delete)
            {
                row = listcell.getHighlightLines()[0];
                listcell.setCurrentRow(row);
                next = Integer.parseInt(listcell.getValue(col));
            }
            else
            {
                if (ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(lbl_InSlip.getText(), 2, page, auto);
                }
                else
                {
                    next = ListOfLineNo.get(ListOfLineNo.size() - 1) + 1;
                }
            }
        }
        else
        {
            if (delete)
            {
                row = listcell.getActiveRow();
                ListOfLineNo.remove(row - 1);
            }
            if (modify && !delete)
            {
                row = listcell.getHighlightLines()[0];
                listcell.setCurrentRow(row);
                next = Integer.parseInt(listcell.getValue(col));
            }
            else
            {
                if (ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(lbl_InSlip.getText(), 2, page, auto);
                }
                else
                {
                    next = getMaxLineNo(ListOfLineNo) + 1;
                }
            }
        }
        return next;


    }


    public static int getNextLineNoForRetrievalPlan(ArrayList<Integer> ListOfLineNo, Label lbl_InSlip,
            ListCellModel listCellModel, NumberTextBox txt_LineNo, boolean auto, boolean modify, boolean delete,
            ListCellKey COL_NUM_LINENO, Page page)
            throws Exception
    {
        int next = 0;
        int row = 0;
        int col = 0;
        RetrievalPlanRegist2Business business = ((RetrievalPlanRegist2Business)page);
        ListCell listcell = business.lst_RetrievalPlanInput;

        ListCellLine line = listCellModel.get(1);
        if (line != null) col = line.getIndex(COL_NUM_LINENO);
        if (WmsParam.AUTO_LINE_NO)
        {
            if (delete)
            {
                ListOfLineNo.remove(ListOfLineNo.size() - 1);
                if (line != null)
                {
                    for (int i = 0; i < listcell.getMaxRows() - 1; i++)
                    {
                        listcell.setCurrentRow(i + 1);
                        listcell.setValue(col, "" + ListOfLineNo.get(i));
                    }
                }
            }
            if (modify && !delete)
            {
                row = listcell.getHighlightLines()[0];
                listcell.setCurrentRow(row);
                next = Integer.parseInt(listcell.getValue(col));
            }
            else
            {
                if (ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(lbl_InSlip.getText(), 3, page, auto);
                }
                else
                {
                    next = ListOfLineNo.get(ListOfLineNo.size() - 1) + 1;
                }
            }
        }
        else
        {
            if (delete)
            {
                row = listcell.getActiveRow();
                ListOfLineNo.remove(row - 1);
            }
            if (modify && !delete)
            {
                row = listcell.getHighlightLines()[0];
                listcell.setCurrentRow(row);
                next = Integer.parseInt(listcell.getValue(col));
            }
            else
            {
                if (ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(lbl_InSlip.getText(), 3, page, auto);
                }
                else
                {
                    next = getMaxLineNo(ListOfLineNo) + 1;
                }
            }
        }
        return next;


    }


    public static int getNextLineNoForXDShippingPlan(HashMap<String, ArrayList<Integer>> map, String slipNo,
            int lineNo, ListCellModel listCellModel, boolean auto, boolean modify, boolean delete,
            ListCellKey COL_NUM_LINENO, ListCellKey COL_NUM_SHIPNO, Page page, int start, String currentShipNo)
            throws Exception
    {
        ArrayList<Integer> ListOfLineNo = (ArrayList<Integer>)map.get(slipNo);
        int next = 0;
        int col = 0;
        int shp = 0;
        XDPlanRegist2Business business = ((XDPlanRegist2Business)page);
        ListCell listcell = business.lst_TcPlanDataRegist;

        ListCellLine line = listCellModel.get(1);
        if (line != null)
        {
            col = line.getIndex(COL_NUM_LINENO);
            shp = line.getIndex(COL_NUM_SHIPNO);
        }
        if (WmsParam.AUTO_LINE_NO)
        {
            if (delete)
            {
                ListOfLineNo.remove(ListOfLineNo.size() - 1);
                if (line != null)
                {
                    reorderLineNo(listcell, slipNo, start, shp, col);
                }
                slipNo = currentShipNo;
                ListOfLineNo = map.get(currentShipNo);
            }
            if (modify && !delete)
            {

                slipNo = currentShipNo;
                ListOfLineNo = (ArrayList<Integer>)map.get(currentShipNo);

                if (ListOfLineNo == null || ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(slipNo, 5, page, auto);
                }
                else
                {
                    next = ListOfLineNo.get(ListOfLineNo.size() - 1) + 1;
                }

            }
            else
            {
                if (ListOfLineNo == null || ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(slipNo, 5, page, auto);
                }
                else
                {
                    next = ListOfLineNo.get(ListOfLineNo.size() - 1) + 1;
                }
            }
        }
        else
        {
            if (delete)
            {
                ListOfLineNo.remove(new Integer(lineNo));
                slipNo = currentShipNo;
                ListOfLineNo = map.get(currentShipNo);
            }
            if (modify && !delete)
            {
                slipNo = currentShipNo;
                ListOfLineNo = map.get(currentShipNo);

                if (ListOfLineNo == null || ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(slipNo, 5, page, auto);
                }
                else
                {
                    next = getMaxLineNo(ListOfLineNo) + 1;
                }

            }
            else
            {
                if (ListOfLineNo == null || ListOfLineNo.size() == 0)
                {
                    next = getNextLineNo(slipNo, 5, page, auto);
                }
                else
                {
                    next = getMaxLineNo(ListOfLineNo) + 1;
                }
            }
        }
        return next;
    }


    /**
     * 
     * @param lists
     * @param lineNo
     * @return
     * @throws Exception
     */
    public static int getMaxLineNo(ArrayList lists)
            throws Exception
    {
        int max = (Integer)lists.get(0);
        for (int i = 1; i < lists.size(); i++)
        {
            if (max < (Integer)lists.get(i)) max = (Integer)lists.get(i);
        }
        return max;
    }


    /**
     * 
     * @param slipNo
     * @param type
     * @param lineNo
     * @param page
     * @return
     * @throws Exception
     */
    public static boolean isLineNoUsed(String slipNo, int type, int lineNo, Page page)
            throws Exception
    {

        Connection conn = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(WMSConstants.DATASOURCE_NAME, page);

            // ReceivingPlanController
            if (type == 1)
            {
                ReceivingPlanController controller = new ReceivingPlanController(conn, page.getClass());
                // Go to table DNReceivingPlan, check if the given Slip No exists or not.
                ReceivingPlan[] lines = controller.getLineNos(slipNo, "0"); // ConsignorCode == 0

                // Check duplicate Line No.
                for (int i = 0; i < lines.length; i++)
                {
                    if (lines[i].getReceiveLineNo() == lineNo)
                    {
                        return true;
                    }
                }
            }
            // StoragePlanController
            else if (type == 2)
            {
                StoragePlanController controller = new StoragePlanController(conn, page.getClass());
                // Go to table DNReceivingPlan, check if the given Slip No exists or not.
                StoragePlan[] lines = controller.getLineNos(slipNo, "0"); // ConsignorCode == 0

                // Check duplicate Line No.
                for (int i = 0; i < lines.length; i++)
                {
                    if (lines[i].getReceiveLineNo() == lineNo)
                    {
                        return true;
                    }
                }
            }
            // RetrievalPlanController
            else if (type == 3)
            {
                RetrievalPlanController controller = new RetrievalPlanController(conn, page.getClass());
                // Go to table DNReceivingPlan, check if the given Slip No exists or not.
                RetrievalPlan[] lines = controller.getLineNos(slipNo, "0"); // ConsignorCode == 0

                // Check duplicate Line No.
                for (int i = 0; i < lines.length; i++)
                {
                    if (lines[i].getShipLineNo() == lineNo)
                    {
                        return true;
                    }
                }

            }
            else if (type == 5)
            {
                XDPlanController controller = new XDPlanController(conn, page.getClass());
                // Go to table DNReceivingPlan, check if the given Slip No exists or not.
                CrossDockPlan[] lines = controller.getShippingLineNos(slipNo, "0"); // ConsignorCode == 0


                // Check duplicate Line No.
                for (int i = 0; i < lines.length; i++)
                {
                    if (lines[i].getShipLineNo() == lineNo)
                    {
                        return true;
                    }
                }
            }
            else
            {
                // Unknow type
                // ..... 
                // .....
                // For debug
                System.out.println("\n////////////////////////////////////// ");
                System.out.println("  Unknown type in LineNumber.java        ");
                System.out.println("////////////////////////////////////// \n");
                throw new Exception("Unknown type in LineNumber.java");

            }


        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        finally
        {
            if (conn != null) conn.close();
        }


        return false;
    }


    public static boolean deleteElement(HashMap<String, ArrayList<Integer>> map, String slipNo, int lineNo)
    {
        ArrayList<Integer> temp = map.get(slipNo);
        if (temp != null)
        {
            if (WmsParam.AUTO_LINE_NO)
            {
                temp.remove(temp.size() - 1);
                return true;
            }
            else
            {
                return temp.remove(new Integer(lineNo));
            }
        }
        else System.out.println("/n +++++++++ the given slip No does not exit in map in deleteElement at LineNumber +++++++++/n");
        return false;
    }


    public static void reorderLineNo(ListCell lst, String slipNo, int start, int col_slipNo, int col_listNo)
    {
        for (int i = start; i < lst.getMaxRows(); i++)
        {
            lst.setCurrentRow(i);
            if (slipNo.compareTo(lst.getValue(col_slipNo)) == 0)
            {
                int num = Integer.parseInt(lst.getValue(col_listNo)) - 1;
                lst.setValue(col_listNo, "" + num);
            }
        }
    }


    public static void addLineNo(HashMap<String, ArrayList<Integer>> map, String slipNo, int lineNo)
    {
        ArrayList<Integer> temp = (ArrayList<Integer>)map.get(slipNo);
        if (temp == null)
        {
            temp = new ArrayList<Integer>();
            map.put(slipNo, temp);
        }
        temp.add(lineNo);
    }


    public static void sortSlipNoAndLineNo(ListCellModel lcm, ListCellKey keySlipNo, ListCellKey keyLineNo,
            HashMap<String, ArrayList<Integer>> map)
    {
        ListCellLine line = null;
        TreeSet<String> tree = new TreeSet<String>();
        ArrayList<ListCellLine> clone = new ArrayList<ListCellLine>();
        Iterator itr = (map.keySet()).iterator();
        while (itr.hasNext())
        {
            tree.add((String)itr.next());
        }
        itr = tree.iterator();
        while (itr.hasNext())
        {
            String slipno = (String)itr.next(); // debug
            ArrayList<Integer> tempLineNo = map.get(slipno);
            for (int i = 0; i < tempLineNo.size(); i++)
            {
                int lineno = tempLineNo.get(i);
                for (int j = 1; j <= lcm.size(); j++)
                {
                    line = lcm.get(j);
                    String lcSlipNo = (String)line.getValue(keySlipNo);
                    int lcLineNo = Integer.parseInt((String)line.getValue(keyLineNo));
                    if (slipno.compareTo(lcSlipNo) == 0 && lineno == lcLineNo)
                    {
                        clone.add(line);
                    }
                }
            }
        }
        for (int i = 1; i <= lcm.size(); i++)
        {
            lcm.set(i, clone.get(i - 1));
        }
    }


    public static boolean isUsedInMap(HashMap<String, ArrayList<Integer>> map, String slipNo, int lineNo)
    {
        ArrayList<Integer> temp = (ArrayList<Integer>)map.get(slipNo);
        if (temp == null)
        {
            temp = new ArrayList<Integer>();
            map.put(slipNo, temp);
        }

        return temp.contains(lineNo);
    }


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: LineNumber.java 3208 2009-03-02 05:42:52Z arai $";
    }
}
