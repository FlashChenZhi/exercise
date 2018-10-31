// $Id: RftUtil.java 2764 2009-01-19 02:51:46Z kishimoto $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.rft.RftConst;


/**
 * 
 * RFTユーティリティクラス<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/06</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2764 $, $Date: 2009-01-19 11:51:46 +0900 (月, 19 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kishimoto $
 */
public class RftUtil
{
    /** <code>ファイルやID電文のエンコーディング文字列(文字列<->Byte配列 変換)</code> */
    public static final String DATA_ENCODING_STRING = "shift_jis";

    /**
     * このメソッドは日時の差異を計算し、秒数で返す。
     * @param startTime 開始日時刻
     * @param endTime 終了日時刻
     * @return 時間の差異を秒数で返す
     */
    public static int calculateTimeDifference(Date startTime, Date endTime)
    {
        int timeDiff = 0;
        if (startTime != null)
        {
            timeDiff = (int)((endTime.getTime() - startTime.getTime()) / 1000);
        }
        return timeDiff;
    }

    /**
     * 指定文字列を指定バイト数で分割します。<br>
     * 分割バイト数前後に２バイト文字があった場合<br>
     * 次の文字列に含まれます。<br>
     *
     * @param value 文字列
     * @param bytelength 分割バイト数
     * @return String[] 分割文字列
     *  例）
     *  byteSplit( "AAAAあAAあ", 5 )
     *  ⇒ String[0] "AAAA"
     *  String[1] "あAA"
     *  String[2] "あ"
     *
     *  byteSplit( null, 5 )
     *  ⇒ String[0] ""（空文字）
     *
     *  byteSplit( "AAAAあAAあ", 0以下 )
     *  ⇒ String[0] ""（空文字）
     */
    public static String[] byteSplit(String value, int bytelength)
    {
        Vector<String> vecStr = new Vector<String>();

        int startPos = 0;
        int endPos = 0;
        int length = 0;
        String str = value;

        if (str == null)
        {
            vecStr.addElement("");
        }
        else if (bytelength <= 0)
        {
            vecStr.addElement("");
        }
        else
        {

            int strLen = 0;
            try
            {
                strLen = str.getBytes(DATA_ENCODING_STRING).length;
            }
            catch (UnsupportedEncodingException e)
            {
                strLen = str.getBytes().length;
            }

            // 空行
            if (strLen == 0)
            {
                vecStr.addElement("");

                // 指定されたサイズ以下
            }
            else if (strLen <= bytelength)
            {
                vecStr.addElement(str);

            }
            else
            {

                // size以上の場合は文字数分長さをチェックします。
                while (endPos < str.length())
                {

                    // 1文字取り出す
                    String sTarget = str.substring(endPos, endPos + 1);

                    // 1文字のバイト数
                    int n;
                    try
                    {
                        n = sTarget.getBytes(DATA_ENCODING_STRING).length;
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        n = sTarget.getBytes().length;
                    }

                    // sizeバイトを越えてしまったら String生成
                    if (length + n > bytelength)
                    {
                        vecStr.addElement(str.substring(startPos, endPos));
                        startPos = endPos;
                        length = 0;
                    }
                    else
                    {
                        length += n;
                        ++endPos;
                    }
                }
                if (endPos > startPos)
                {
                    vecStr.addElement(str.substring(startPos, endPos));
                }
            }
        }

        String[] resultArray = new String[vecStr.size()];
        vecStr.copyInto(resultArray);
        return resultArray;
    }

    /**
     * 引数で渡された文字列を指定のバイト数の文字列にして返す。<br>
     * 受け取った文字列を、指定のByte数にして返します。<br>
     * 受け取った文字列長が小さい場合は、右側に空白をセットし、長い場合は、カットします。<br>
     * (カットする際は、２バイト文字が半分に切られないように考慮します。)<br>
     * 例)byteMakePadRight("abcあ",4)で返される文字列は("abc ")<br>
     *      byteMakePadRight("abc",5)で返される文字列は("abc  ")です。
     *        
     * @param value 対象の文字列
     * @param bytelength 作成する文字列のバイト数
     * @param charset 文字セット
     * @return 作成した文字列
     */
    public static String byteMakePadRight(String value, int bytelength, Charset charset)
    {
        // 文字数が指定バイト数より大きかったら指定バイト数にカットします
        String splitValue = byteSplit(value, bytelength)[0];

        // 指定バイト数になるように空白文字を詰めます</jp>
        StringBuffer sb = new StringBuffer();
        // 文字列の長さを取得します。
        int strLen = 0;
        try
        {
            strLen = splitValue.getBytes(RftConst.DATA_ENCODING_STRING).length;
        }
        catch (UnsupportedEncodingException e)
        {
            strLen = splitValue.getBytes().length;
        }

        if (strLen < bytelength)
        {
            for (int loop = strLen; loop < bytelength; loop++)
            {
                sb.append(" ");
            }
        }
        return splitValue + sb.toString();
    }

    /**
     * 指定された文字列が1行表示可能か否かを取得します。
     * @param value 文字列
     * @param bytelength 長さ
     * @return true:指定された長さ以下、false:指定された長さ以上
     */
    public static boolean isDispLength(String value, int bytelength)
    {
        String str = value;
        int strLen = 0;

        try
        {
            strLen = str.getBytes(DATA_ENCODING_STRING).length;
        }
        catch (UnsupportedEncodingException e)
        {
            strLen = str.getBytes().length;
        }

        // 空行
        if (strLen == 0)
        {
            return true;
        }
        // 指定されたサイズ以下
        else if (strLen <= bytelength)
        {
            return true;
        }

        return false;
    }

    /**
     * 作業区分の名称を取得します。 <BR>
     * <CODE>SystemDefine.JOB_TYPE_RECEIVING</CODE> : 入荷 <BR>
     * <CODE>SystemDefine.JOB_TYPE_STORAGE</CODE> : 入庫  <BR>
     * <CODE>SystemDefine.JOB_TYPE_RETRIEVAL</CODE> : 出庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_SORTING</CODE> : 仕分 <BR>
     * <CODE>SystemDefine.JOB_TYPE_SHIPPING</CODE> : 出荷 <BR>
     * <CODE>SystemDefine.JOB_TYPE_MOVEMENT_STORAGE</CODE> : 移動入庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL</CODE> : 移動出庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_NOPLAN_STORAGE</CODE> : 予定外入庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL</CODE> : 予定外出庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_INVENTORY</CODE> : 棚卸 <BR>
     * @param jobType 作業区分
     * @param locale ロケール
     * @return 作業区分名称
     */
    public static String getJobTypeName(String jobType, Locale locale)
    {

        if (SystemDefine.JOB_TYPE_RECEIVING.equals(jobType))
        {
            // 入荷
            return DispResources.getText(locale, "CMB-W0012");
        }
        else if (SystemDefine.JOB_TYPE_STORAGE.equals(jobType))
        {
            // 入庫
            return DispResources.getText(locale, "CMB-W0010");
        }
        else if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            // 出庫
            return DispResources.getText(locale, "CMB-W0011");
        }
        else if (SystemDefine.JOB_TYPE_SORTING.equals(jobType))
        {
            // 仕分
            return DispResources.getText(locale, "CMB-W0013");
        }
        else if (SystemDefine.JOB_TYPE_SHIPPING.equals(jobType))
        {
            // 出荷
            return DispResources.getText(locale, "CMB-W0014");
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT.equals(jobType))
        {
            // 移動
            return DispResources.getText(locale, "CMB-W0039");
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT_STORAGE.equals(jobType))
        {
            // 移動入庫
            return DispResources.getText(locale, "CMB-W0016");
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL.equals(jobType))
        {
            // 移動出庫
            return DispResources.getText(locale, "CMB-W0017");
        }
        else if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(jobType))
        {
            // 予定外入庫
            return DispResources.getText(locale, "CMB-W0018");
        }
        else if (SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL.equals(jobType))
        {
            // 予定外出庫
            return DispResources.getText(locale, "CMB-W0019");
        }
        else if (SystemDefine.JOB_TYPE_INVENTORY.equals(jobType))
        {
            // 棚卸
            return DispResources.getText(locale, "CMB-W0015");
        }
        else
        {
            return "";
        }
    }

    /**
     * 作業中ID番号から、その作業名を取得します。<br>
     * @param idString 作業中ID番号
     * @param locale ロケール
     * @return 作業名
     */
    public static String getWorkingName(String idString, Locale locale)
    {
        if (RftConst.DC_RECEIVING.equals(idString))
        {
            // DC入荷
            return DispResources.getText(locale, "MENU-R1002");
        }
        else if (RftConst.TC_RECEIVING.equals(idString))
        {
            // TC入荷
            return DispResources.getText(locale, "MENU-R1001");
        }
        else if (RftConst.STORAGE.equals(idString))
        {
            // 入庫
            return DispResources.getText(locale, "MENU-R1004");
        }
        else if (RftConst.RETRIEVAL_ORDER.equals(idString))
        {
            // オーダー出庫
            return DispResources.getText(locale, "MENU-R1005");
        }
        else if (RftConst.SORTING.equals(idString))
        {
            // 仕分
            return DispResources.getText(locale, "MENU-R1006");
        }
        else if (RftConst.SHIPPING_CUSTOMER.equals(idString))
        {
            // 出荷検品
            return DispResources.getText(locale, "MENU-R1007");
        }
        else if (RftConst.SHIPPING_LOAD.equals(idString))
        {
            // 出荷積込
            return DispResources.getText(locale, "MENU-R1008");
        }
        else if (RftConst.MOVE_STORAGE.equals(idString))
        {
            // 移動入庫
            return DispResources.getText(locale, "MENU-R1013");
        }
        else if (RftConst.INVENTORY.equals(idString))
        {
            // 棚卸
            return DispResources.getText(locale, "MENU-R1011");
        }
        else if (RftConst.RECEIVING_STORAGE.equals(idString))
        {
            // 入庫(入荷エリア)
            return DispResources.getText(locale, "MENU-R1003");
        }

        return "";
    }

    /**
     * 作業区分、作業詳細区分より作業IDを取得します。
     * @param jobType 作業区分
     * @param jobDetails 作業詳細区分
     * @return 作業ID
     */
    public static String getJobId(String jobType, String jobDetails)
    {
        if (SystemDefine.JOB_TYPE_RECEIVING.equals(jobType))
        {
            if (SystemDefine.JOB_DETAIL_TCRECEIVING.equals(jobDetails))
            {
                // TC入荷
                return RftConst.TC_RECEIVING;
            }
            // DC入荷
            return RftConst.DC_RECEIVING;
        }
        else if (SystemDefine.JOB_TYPE_STORAGE.equals(jobType))
        {
            if (SystemDefine.JOB_DETAIL_STORAGE.equals(jobDetails))
            {
                // 入庫
                return RftConst.STORAGE;
            }
            // DC入庫
           return RftConst.RECEIVING_STORAGE;
        }
        else if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            // オーダー出庫
            return RftConst.RETRIEVAL_ORDER;
        }
        else if (SystemDefine.JOB_TYPE_SORTING.equals(jobType))
        {
            // 仕分
            return RftConst.SORTING;
        }
        else if (SystemDefine.JOB_TYPE_SHIPPING.equals(jobType))
        {
            if (SystemDefine.JOB_DETAIL_ITEMSHIPPING.equals(jobDetails))
            {
                // 出荷(商品単位)
                return RftConst.SHIPPING_ITEM;
            }
            else if (SystemDefine.JOB_DETAIL_CUSTOMERSHIPPING.equals(jobDetails))
            {
                // 出荷(出荷先単位)
                return RftConst.SHIPPING_CUSTOMER;
            }
            // 出荷積込
            return RftConst.SHIPPING_LOAD;
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT_STORAGE.equals(jobType))
        {
            if (SystemDefine.JOB_DETAIL_DCSTORAGE.equals(jobDetails))
            {
                // DC入庫
                return RftConst.RECEIVING_STORAGE;
            }
            // 移動入庫
            return RftConst.MOVE_STORAGE;
        }
        else if (SystemDefine.JOB_TYPE_INVENTORY.equals(jobType))
        {
            // 棚卸
            return RftConst.INVENTORY;
        }
        return "";
    }
    
    /**
     * 棚No文字列からエリアNo部分のみを返す。
     * 
     * @param areaLocNo 棚No
     * @return エリアNo部分の文字列
     */
    public static String getAreaNo(String areaLocNo)
    {
        if (areaLocNo.trim().length() >= RftConst.AREANO_LENGTH)
        {
            return areaLocNo.substring(0, RftConst.AREANO_LENGTH);
        }
        else
        {
            return areaLocNo.substring(0, areaLocNo.length());
        }
    }

    /**
     * 棚No文字列からロケーションNo部分のみを返す。
     * 
     * @param areaLocNo
     *            棚No
     * @return ロケーションNo部分の文字列
     */
    public static String getLocNo(String areaLocNo)
    {
        if (areaLocNo.trim().length() >= RftConst.AREANO_LENGTH)
        {
            return areaLocNo.substring(RftConst.AREANO_LENGTH);
        }
        else
        {
            return "";
        }
    }
}
