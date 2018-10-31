// $Id$
package jp.co.daifuku.wms.base.rft;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public interface ResultConst
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // Work already completed
    public static final String ALREADY_COMPLETED = "ALREADY_COMPLETED";
    // Not resume when the rest received
    public static final String ALREADY_RESTARTED = "ALREADY_RESTARTED";
    // Disable area
    public static final String AREA_INVALIDITY = "AREA_INVALIDITY";
    // Duplicate area
    public static final String AREA_NO_DUPLICATED = "AREA_NO_DUPLICATED";
    // User authentication fails (password expiration)
    public static final String AUTH_ERR_PWDEXPIRE = "AUTH_ERR_PWDEXPIRE";
    // User authentication fails (user has been locked)
    public static final String AUTH_ERR_USERLOCK = "AUTH_ERR_USERLOCK";
    // User authentication fails (incorrect user or password)
    public static final String AUTH_ERR_USERPASS = "AUTH_ERR_USERPASS";
    // User authentication failed (insufficient privileges)
    public static final String AUTH_ERR_USERPERMISSION = "AUTH_ERR_USERPERMISSION";
    // No Data Found (confirmation)
    public static final String CONFIRM_NODATA = "CONFIRM_NODATA";
    // Skip consignor
    public static final String CONSIGNOR_SKIP = "CONSIGNOR_SKIP";
    // Daily update in progress
    public static final String DAILY_UPDATING = "DAILY_UPDATING";
    // Already registered (DB unique key error)
    public static final String DATA_EXISTS = "DATA_EXISTS";
    // DB Access Error
    public static final String DB_ACCESS_ERROR = "DB_ACCESS_ERROR";
    // Error (Unexpected error)
    public static final String ERROR = "ERROR";
    // Fixed shelf registration
    public static final String FIXED_SHELF_UNREGISTRATION = "FIXED_SHELF_UNREGISTRATION";
    // No Stockpack
    public static final String HAVE_NO_STOCKPACK = "HAVE_NO_STOCKPACK";
    // Incorrect IP address
    public static final String ILLEGAL_IP_ADDRESS = "ILLEGAL_IP_ADDRESS";
    // Shelf format error
    public static final String ILLEGAL_LOCATION_FORMAT = "ILLEGAL_LOCATION_FORMAT";
    // Incorrect terminal number
    public static final String ILLEGAL_TERMINAL_NUMBER = "ILLEGAL_TERMINAL_NUMBER";
    // Working with different data
    public static final String INCOMPLETE_WORK_OTHER = "INCOMPLETE_WORK_OTHER";
    // Working with data
    public static final String INCOMPLETE_WORK_OWN = "INCOMPLETE_WORK_OWN";
    // Error inspection (inspection mode)
    public static final String INSP_CPMODE = "INSP_CPMODE";
    // Inspection error (enter the number 0)
    public static final String INSP_ENTZERO = "INSP_ENTZERO";
    // Inspection error (excess)
    public static final String INSP_EXCESS = "INSP_EXCESS";
    // Inspection errors (product differences)
    public static final String INSP_OTHER = "INSP_OTHER";
    // Inspection error (actual 0)
    public static final String INSP_ZERO = "INSP_ZERO";
    // Complete inspection process
    public static final String INSPECTION_COMPLETE = "INSPECTION_COMPLETE";
    // Inspection mode
    public static final String INSPECTION_MODE = "INSPECTION_MODE";
    // Generate an error class
    public static final String INSTACIATE_ERROR = "INSTACIATE_ERROR";
    // Product Differences
    public static final String ITEM_DIFFERENCE = "ITEM_DIFFERENCE";
    // Product code duplication
    public static final String ITEM_DUPLICATED = "ITEM_DUPLICATED";
    // Work Category (Cycle Count)
    public static final String JOBTYPE_INVENTORY = "JOBTYPE_INVENTORY";
    // Work Category (Relocation Picking)
    public static final String JOBTYPE_MOVE_RETRIEVAL = "JOBTYPE_MOVE_RETRIEVAL";
    // Work Category (Relocation Storage)
    public static final String JOBTYPE_MOVE_STORAGE = "JOBTYPE_MOVE_STORAGE";
    // Work Category (Unplanned Picking)
    public static final String JOBTYPE_NOPLAN_RETRIEVAL = "JOBTYPE_NOPLAN_RETRIEVAL";
    // Work Category (Unplanned Storage)
    public static final String JOBTYPE_NOPLAN_STORAGE = "JOBTYPE_NOPLAN_STORAGE";
    // Work Category (DC Receiving)
    public static final String JOBTYPE_RECEIVING = "JOBTYPE_RECEIVING";
    // Work Category (Storage(Receiving Area))
    public static final String JOBTYPE_RECEIVING_STORAGE = "JOBTYPE_RECEIVING_STORAGE";
    // Work Category (Order Picking)
    public static final String JOBTYPE_RETRIEVAL = "JOBTYPE_RETRIEVAL";
    // Work Category (Shipping Check)
    public static final String JOBTYPE_SHIPPING = "JOBTYPE_SHIPPING";
    // Work Category (Loading)
    public static final String JOBTYPE_SHIPPING_LOAD = "JOBTYPE_SHIPPING_LOAD";
    // Work Category (Consolidation)
    public static final String JOBTYPE_SORTING = "JOBTYPE_SORTING";
    // Work Category (Storage)
    public static final String JOBTYPE_STORAGE = "JOBTYPE_STORAGE";
    // Work Category (Cross Dock Receiving)
    public static final String JOBTYPE_TC_RECEIVING = "JOBTYPE_TC_RECEIVING";
    // Different shelves
    public static final String LOCATION_DIFFERENCE = "LOCATION_DIFFERENCE";
    // Lot No. Duplicate
    public static final String LOT_NO_DUPLICATED = "LOT_NO_DUPLICATED";
    // Under Maintenance
    public static final String MAINTENANCE = "MAINTENANCE";
    // There is work remaining data
    public static final String MORE_WORK = "MORE_WORK";
    // Move focus
    public static final String MOVE_FOCUS = "MOVE_FOCUS";
    // No Data Found
    public static final String NODATA = "NODATA";
    // Normal
    public static final String NORMAL = "NORMAL";
    // Normal (all work completed)
    public static final String NORMAL_COMPLETE = "NORMAL_COMPLETE";
    // Order No difference
    public static final String ORDERNO_DIFFERENCE = "ORDERNO_DIFFERENCE";
    // No duplication Order
    public static final String ORDERNO_DUPLICATED = "ORDERNO_DUPLICATED";
    // User authentication failed (temporary)
    public static final String PASSWORD_TENTATIVE = "PASSWORD_TENTATIVE";
    // Parameter error
    public static final String PATTERN_NG = "PATTERN_NG";
    // In duplicate
    public static final String PLAN_DAY_DUPLICATED = "PLAN_DAY_DUPLICATED";
    // Too many achievements
    public static final String RESULTQTY_OVER = "RESULTQTY_OVER";
    // In order to delivery problems
    public static final String RETRIEVAL_ORDER_WARNING = "RETRIEVAL_ORDER_WARNING";
    // This device, while another user logs in.
    public static final String RFT_ALREADY_WORK = "RFT_ALREADY_WORK";
    // Scheduling Error
    public static final String SCHEDULE_ERROR = "SCHEDULE_ERROR";
    // Shelf registration
    public static final String SHELF_UNREGISTRATION = "SHELF_UNREGISTRATION";
    // Stockout
    public static final String SHORTAGE = "SHORTAGE";
    // Duplicate Suppliers
    public static final String SUPPLIER_DUPLICATED = "SUPPLIER_DUPLICATED";
    // Updated
    public static final String UPDATE_FINISH = "UPDATE_FINISH";
    // Users, working with other terminal
    public static final String USER_ALREADY_WORK = "USER_ALREADY_WORK";
    // User authentication fails (incorrect authentication settings)
    public static final String USER_SETTING_ERROR = "USER_SETTING_ERROR";
    // Type check error
    public static final String VALIDATE_ERROR = "VALIDATE_ERROR";
    // Excess quantity
    public static final String WARNING_EXCESS = "WARNING_EXCESS";
    // Working with other terminal
    public static final String WORKING_INPROGRESS = "WORKING_INPROGRESS";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

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
}
//end of class
