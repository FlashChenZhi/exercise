// $Id: ToolFindUtil.java 7468 2010-03-08 09:06:46Z shibamoto $
package jp.co.daifuku.wms.asrs.tool.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.SoftZone;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;

/**<jp>
 * 画面、帳票で使用する汎用的な検索メソッドを提供します。<BR>
 * 本クラスのは、同一内容の検索メソッドを複数クラスで実装されることを防ぎ、極力一箇所にまとめバグが発生した時に修正し易くする
 * ことを目的としています。このクラスに実装されるメソッドが増えていけば、関連あるメソッドごとに別クラスに外出ししていく予定でいます。
 * くれぐれもあちらこちらのクラスに実装しないで下さい。またメソッドを追加する時は必ず、既に実装されていないかチェックしてください。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/12</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7468 $, $Date: 2010-03-08 18:06:46 +0900 (月, 08 3 2010) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This class provides the search method of general purpose used in screen display 
 * and forms.<BR>
 * It prevents the implementation of search method of identical content by multiple
 * classes and is designed to make it easier to make corrections when bugs occurred
 * by minimizing search as much as possible.
 * As methods implemented in this class increase, it is planned to bind related methods
 * together and respectively transfer to othre classes.
 * Please ensure not to implement methods to the classes all around. Also please ensure
 * to check, when adding methods, if they have already been implemented.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/12</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7468 $, $Date: 2010-03-08 18:06:46 +0900 (月, 08 3 2010) $
 * @author  $Author: shibamoto $
 </en>*/
public class ToolFindUtil
        extends Object
{
    // Class fields --------------------------------------------------

    // Class private fields ------------------------------------------
    /**<jp> <CODE>データベースとのコネクションオブジェクト</CODE> </jp>*/
    /**<en> <CODE>Connection</CODE> </en>*/
    private Connection wConn = null;

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
        return ("$Revision: 7468 $,$Date: 2010-03-08 18:06:46 +0900 (月, 08 3 2010) $");
    }

    /**<jp>
     * ファイルをコピーします。
     * コピー元のファイルパスが未入力時には空のファイルを生成します。
     * @param  source コピー元のファイルパス
     * @param  dest   コピー先のファイルパス
     * @return ファイルコピーに成功した場合は、true、失敗した場合は、falseを返します。
     </jp>*/
    /**<en>
     * Copy the file.
     * If file path to the copy source, an empty file will be generated.
     * @param  source :file path to the copy source
     * @param  dest   file path to the copy destination
     * @return
     </en>*/
    public static boolean copyFile(String source, String dest)
    {
        try
        {
            int readByte;
            FileOutputStream output = new FileOutputStream(dest);
            if (source != null && source.length() > 0)
            {
                FileInputStream input = new FileInputStream(source);

                while ((readByte = input.read()) != -1)
                {
                    output.write(readByte);
                }
                input.close();
            }
            else
            {
                output.write(0);
            }
            output.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    // Constructors --------------------------------------------------
    /**<jp>
     * <CODE>Connection</CODE>を取得して<CODE>FindUtil</CODE>オブジェクトを構築します。
     * @param conn <CODE>Connection</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>Connection</CODE> and construcrs <CODE>FindUtil</CODE> object.
     * @param conn <CODE>Connection</CODE>
     </en>*/
    public ToolFindUtil(Connection conn)
    {
        wConn = conn;
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 指定された倉庫ステーションナンバーから倉庫ナンバーを取得します。
     * 例   1-> 9000
     * @param  whNo 倉庫ナンバー
     * @return 倉庫ステーションナンバー
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the warehouse number based on the specified warehouse station no.
     * Ex. 1-> 9000
     * @param  whNo :warehouse no.
     * @return :warehouse station no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String getWarehouseStationNumber(int whNo)
            throws ReadWriteException
    {
        ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
        ToolWarehouseHandler handler = new ToolWarehouseHandler(wConn);
        key.setWarehouseNo(whNo);
        Warehouse[] house = (Warehouse[])handler.find(key);
        if (house.length <= 0)
        {
            return "NG";
        }

        return house[0].getStationNo();
    }

    /**<jp>
     * 指定された倉庫ステーションナンバーから倉庫ナンバーを取得します。
     * 例   1-> 9000
     * @param  whStNo 倉庫ナンバー
     * @return 倉庫ステーションナンバー
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the warehouse number based on the specified warehouse station no.
     * Ex. 1-> 9000
     * @param  whStNo :warehouse no.
     * @return :warehouse station no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int getWarehouseNumber(String whStNo)
            throws ReadWriteException
    {
        ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
        ToolWarehouseHandler handler = new ToolWarehouseHandler(wConn);
        key.setWarehouseStationNo(whStNo);
        Warehouse[] house = (Warehouse[])handler.find(key);
        if (house.length <= 0)
        {
            //<jp>見つからない場合は通常使用しない値を返しているけども・・・。</jp>
            //<en>In case the data is not found, irregular value is returned.</en>
            return 9999;
        }

        return house[0].getWarehouseNo();
    }

    /**<jp>
     * 倉庫No.を検索キーにして倉庫名称を返します。
     * @param  stno 倉庫No.
     * @return 倉庫名称
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Return the name of warehouse by using warehouse no. as a search key.
     * @param  whno warehouse no.
     * @return :name of the warehouse
     * @throws ReadWriteException :Notifies of the exception as it is which occurred in database connection.
     </en>*/
    public String getWarehouseName(int whno)
            throws ReadWriteException
    {
        ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
        ToolWarehouseHandler handler = new ToolWarehouseHandler(wConn);
        key.setWarehouseNo(whno);

        Warehouse[] house = (Warehouse[])handler.find(key);

        if (house.length <= 0)
        {
            return "";
        }

        return house[0].getWarehouseName();
    }

    /**<jp>
     * ステーションNo.を検索キーにしてステーション名称を返します。
     * @param  stno ステーションNo.
     * @return ステーション名称
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Return the name of station by using station no. as a search key.
     * @param  stno :station no.
     * @return :name of the station
     * @throws ReadWriteException :Notifies of the exception as it is which occurred in database connection.
     </en>*/
    public String getStationName(String stno)
            throws ReadWriteException
    {
        if (stno == null || stno.equals(""))
        {
            return "";
        }

        ToolStationSearchKey skey = new ToolStationSearchKey();
        ToolStationHandler shdle = new ToolStationHandler(wConn);
        skey.setStationNo(stno);

        Station[] st = (Station[])shdle.find(skey);

        if (st != null && st.length > 0)
        {
            return st[0].getStationName();
        }

        ToolAisleSearchKey akey = new ToolAisleSearchKey();
        ToolAisleHandler ahdle = new ToolAisleHandler(wConn);
        akey.setStationNo(stno);

        if (ahdle.count(akey) > 0)
        {
            return "RM";
        }

        return "";
    }

    /**<jp>
     * 機種コードを検索キーにして機種コード名称を返します。
     * @param  type 機種コード
     * @return 機種コード名称
     </jp>*/
    /**<en>
     * Return the name of the model code  by using the model code as a search key.
     * @param  type :model code
     * @return :name of the model code
     </en>*/
    public String getMachineTypeName(int type)
    {

        switch (type)
        {
            case 11:
                return "11:RM";
            case 15:
                return "15:MSS";
            case 16:
                return "16:TRV";
            case 17:
                return "17:MA";
            case 18:
                return "18:IDR";
            case 21:
                return "21:CO";
            case 22:
                return "22:LFT";
            case 28:
                return "28:PTMATE";
            case 31:
                return "31:MV";
            case 35:
                return "35:FW";
            case 44:
                return "44:SPCS";
            case 45:
                return "45:SPCL";
            case 54:
                return "54:STVS";
            case 55:
                return "55:STVL";
            case 61:
                return "61:DPR";
            case 64:
                return "64:TRS";
            case 65:
                return "65:TRL";
            case 66:
                return "66:STM";
            default:
                return "";
        }
    }

    /**<jp>
     * ZoneIdを検索キーにしてゾーン名称を返します。
     * ゾーン名称が定義されていない場合は0バイトの文字列を返します。
     * 
     * @param  zoneid ゾーンID
     * @return  ゾーン名称
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Return the zone name by using ZoneId as a search key.
     * Return the string of 0 byte if the zone name is undefined.
     * 
     * @param  zoneid :zone ID
     * @return  :zone name
     * @throws ReadWriteException :Notifies of the exception as it is which occurred 
     </en>*/
    public String getHardZoneName(int zoneid)
            throws ReadWriteException
    {
        ToolHardZoneHandler hzh = new ToolHardZoneHandler(wConn);
        ToolHardZoneSearchKey hzk = new ToolHardZoneSearchKey();

        hzk.setHardZoneID(zoneid);
        HardZone[] hardzone = (HardZone[])hzh.find(hzk);
        if (hardzone.length != 0)
        {
            return hardzone[0].getHardZoneName();
        }
        return "";
    }

    /**<jp>
     * ZoneIdを検索キーにしてゾーン名称を返します。
     * ゾーン名称が定義されていない場合は0バイトの文字列を返します。
     * 
     * @param  zoneid ゾーンID
     * @return  ゾーン名称
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Return the zone name by using ZoneId as a search key.
     * Return the string of 0 byte if the zone name is undefined.
     * 
     * @param  zoneid :zone ID
     * @return  :zone name
     * @throws ReadWriteException :Notifies of the exception as it is which occurred 
     </en>*/
    public String getSoftZoneName(int zoneid)
            throws ReadWriteException
    {
        ToolSoftZoneHandler szh = new ToolSoftZoneHandler(wConn);
        ToolSoftZoneSearchKey szk = new ToolSoftZoneSearchKey();

        szk.setSoftZoneID(zoneid);
        SoftZone[] softzone = (SoftZone[])szh.find(szk);
        if (softzone.length != 0)
        {
            return softzone[0].getSoftZoneName();
        }
        return "";
    }

    /**<jp>
     * Shelfを検索し、データが存在するか判断します。
     * @param  whstno 格納区分
     * @param  bank   バンク
     * @param  bay    ベイ
     * @param  level  レベル
     * @return  存在する場合trueを返します。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Search Shelf and deternines whether/not the data exists.
     * @param  warehousenumber :type of storage
     * @param  bank   :bank
     * @param  bay    :bay
     * @param  level  :level
     * @return  :return true if the data exists.
     * @throws ReadWriteException :Notifies of the exception as it is which occurred 
     * in database connection.
     </en>*/
    public boolean isExistShelf(int warehousenumber, int bank, int bay, int level)
            throws ReadWriteException
    {
        try
        {
            ToolShelfSearchKey skey = new ToolShelfSearchKey();
            ToolShelfHandler shdle = new ToolShelfHandler(wConn);

            String whstno = getWarehouseStationNumber(warehousenumber);
            //<jp> 倉庫ステーションNoをセット</jp>
            //<en> Set the warehouse station no.</en>
            skey.setWarehouseStationNo(whstno);
            //<jp> bankをセット</jp>
            //<en> Set bank.</en>
            skey.setBankNo(bank);
            //<jp> bayをセット</jp>
            //<en>Set bay.</en>
            skey.setBayNo(bay);
            //<jp> levelをセット</jp>
            //<en> Set level.</en>
            skey.setLevelNo(level);
            //<jp> 不要な項目は生成しないようにするためこのメソッドを実行する。</jp>
            //<en> This method is executed in order to avoid the </en>
            //<en> generation of unnecessary items.</en>
            shdle.setisScreen();

            if (shdle.count(skey) > 0)
            {
                return true;
            }
            return false;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    /**<jp>
     * 禁止文字チェックを行います。
     * 作業場名称で使用しています。
     * @param  param チェックの対象となるパラメータ
     * @return 禁止文字だった場合True。
     </jp>*/
    /**<en>
     * Check for unacceptable characters and symbols.
     * They are used in workshop name.
     * @param  param :target parameter of check.
     * @return :true if unacceptable characters and symbols were used.
     </en>*/
    public boolean isUndefinedChar(String param)
    {
        //<jp>禁止文字のチェック</jp>
        //<en>Check the unacceptable characters and symbols.</en>
        return (DisplayText.isPatternMatching(param));
    }

    /**<jp>
     * リストボックス用禁止文字チェックを行います。
     * @param  param チェックの対象となるパラメータ
     * @return 禁止文字だった場合True。
     </jp>*/
    /**<en>
     * Check the unacceptable characters and symbols in list box.
     * @param  param :target parameter of check.
     * @return :true if unacceptable characters and symbols were used.
     </en>*/
    public boolean isUndefinedCharForListBox(String param)
    {
        String ptnchar = CommonParam.getParam("PATTERNMATCHING_CHAR");
        String ngchar = CommonParam.getParam("NG_PARAMETER_TEXT");
        //<jp> PATTERNMATCHING_CHAR文字を検出します。</jp>
        //<en> Detect the PATTERNMATCHING_CHAR characters.</en>
        int num = ngchar.indexOf(ptnchar);
        if (num > -1)
        {
            //<jp> PATTERNMATCHING_CHAR文字を省いたものを新たに使用禁止文字として定義します。</jp>
            //<en> Defines that any characters that PATTERNMATCHING_CHAR characters </en>
            //<en> were ommitted shuold be unacceptable.</en>
            ngchar = ngchar.substring(0, num) + ngchar.substring(num + 1);
        }
        //<jp>禁止文字のチェック</jp>
        //<en>Check for unacceptable characters and symbols.</en>
        return (DisplayText.isPatternMatching(param, ngchar));
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

