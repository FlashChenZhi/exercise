// $$Id: SBPLLabelPrintManager.java 7996 2011-07-06 00:52:24Z kitamaki $$
package jp.co.daifuku.wms.base.util.labeltool.module.sato.print;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.IOException;
import java.util.Map;

import jp.co.daifuku.wms.base.util.labeltool.LabelParam;
import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.PrinterAdapter;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.LabelInfoManager;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommandHandler;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.SatoLabelConstants;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem;

import org.apache.xmlbeans.XmlException;

/**
 * ラベル印刷を行うクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/30</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  chenjun
 * @author  Last commit: $Author: kitamaki $
 */
public class SBPLLabelPrintManager
{
    /**
     * ラベルを１枚発行します。
     * 
     * @param printer プリンタインターフェース
     * @param id 管理番号
     * @param data 外部データ
     * @return 状態コード
     * @throws DaiException 異常が発生した場合
     * @throws IOException ファイル生成時エラー
     */
    public String printLabel(PrinterAdapter printer, String id, Map[] data)
            throws DaiException,
                IOException
    {
        return printLabel(printer, id, data, 1);
    }

    /**
     * ラベルを発行します。
     * カッター対応プリンタの場合、1枚毎に用紙がカットされます。
     * 
     * @param printer プリンタインターフェース
     * @param id 管理番号
     * @param data 外部データ
     * @param copies 印刷枚数
     * @return 状態コード
     * @throws DaiException 異常が発生した場合
     * @throws IOException ファイル生成時エラー
     */
    public String printLabel(PrinterAdapter printer, String id, Map[] data, int copies)
            throws DaiException,
                IOException
    {
        return printLabel(printer, id, data, copies, false);
    }

    /**
     * ラベルを発行します。
     * 
     * @param printer プリンタインターフェース
     * @param id 管理番号
     * @param data 外部データ
     * @param copies 印刷枚数
     * @param nocutFlag 印刷終了時にカットしない場合：true、カットする場合：false
     * @return 状態コード
     * @throws DaiException 異常が発生した場合
     * @throws IOException ファイル生成時エラー
     */
    public String printLabel(PrinterAdapter printer, String id, Map[] data, int copies, boolean nocutFlag)
            throws DaiException,
                IOException
    {
        LabelItem info = LabelInfoManager.actionPrintGetLabelInfo(id);
        // 論理削除された場合に、発行しない。
        if (info.getDeleteFlag().equals("1"))
        {
            return null;
        }
        String xmlFileName =
                (LabelParam.XML_FILE_PASS + info.getLayoutName()).replace(LabelConstants.SUFFIX_LAYOUT,
                        LabelConstants.SUFFIX_XML);
        // 特殊データなし
        boolean bitMapData = false;

        printer.connect();
        String ret = null;
        for (Map mapData : data)
        {
            Map[] createData = new Map[1];
            createData[0] = mapData;
            byte[] cmdStr = generateCommand(xmlFileName, createData, copies, nocutFlag, bitMapData);
            ret = printer.getStatus();
            if (ret.equals(SatoLabelConstants.STATUS_ONLINE_MESSAGE_WAIT_NOERR)
                    || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_PRINT_NOERR)
                    || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_WAIT_NOERR)
                    || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_COMPILATION_NOERR))
            {
                ret = printer.sendCommand(cmdStr);
                System.out.println("SendCommand");
            }
            else
            {
                throw new DaiException(ret);
            }
        }
        printer.close();
        return ret;
    }

    /**
     * ビットマップデータを含むラベルを発行します。
     * 
     * @param printer プリンタインターフェース
     * @param id 管理番号
     * @param data 外部データ
     * @param copies 印刷枚数
     * @param nocutFlag 印刷終了時にカットしない場合：true、カットする場合：false
     * @return 状態コード
     * @throws DaiException 異常が発生した場合
     * @throws IOException ファイル生成時エラー
     */
    public String printBitMapLabel(PrinterAdapter printer, String id, Map[] data, int copies, boolean nocutFlag)
            throws DaiException,
                IOException
    {
        LabelItem info = LabelInfoManager.actionPrintGetLabelInfo(id);
        // 論理削除された場合に、発行しない。
        if (info.getDeleteFlag().equals("1"))
        {
            return null;
        }
        String xmlFileName =
                (LabelParam.XML_FILE_PASS + info.getLayoutName()).replace(LabelConstants.SUFFIX_LAYOUT,
                        LabelConstants.SUFFIX_XML);
        // 特殊データあり
        boolean bitMapData = true;

        printer.connect();
        String ret = null;
        for (Map mapData : data)
        {
            Map[] createData = new Map[1];
            createData[0] = mapData;
            byte[] cmdStr = generateCommand(xmlFileName, createData, copies, nocutFlag, bitMapData);
            ret = printer.getStatus();
            if (ret.equals(SatoLabelConstants.STATUS_ONLINE_MESSAGE_WAIT_NOERR)
                    || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_PRINT_NOERR)
                    || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_WAIT_NOERR)
                    || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_COMPILATION_NOERR))
            {
                ret = printer.sendCommand(cmdStr);
                System.out.println("SendCommand");
            }
            else
            {
                throw new DaiException(ret);
            }
        }
        printer.close();
        return ret;
    }

    /**
     * 搬出カット動作指定コマンドを発行します。
     * 
     * @param printer プリンタインターフェース
     * @return 状態コード
     * @throws DaiException 異常が発生した場合
     * @throws IOException ファイル生成時エラー
     */
    public String cut(PrinterAdapter printer)
            throws DaiException,
                IOException
    {
        printer.connect();
        String ret = printer.getStatus();
        if (ret.equals(SatoLabelConstants.STATUS_ONLINE_MESSAGE_WAIT_NOERR)
                || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_PRINT_NOERR)
                || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_WAIT_NOERR)
                || ret.equals(SatoLabelConstants.STATUS_ONLINE_IN_COMPILATION_NOERR))
        {
            // コマンド文字列作成
            StringBuffer buf = new StringBuffer();
            // アイテム開始文字列を追加
            buf.append(LabelConstants.STX + LabelConstants.ESC + LabelConstants.CMD_ITEM_START);
            // カット文字列を追加
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_CUT_NC));
            // アイテム終了文字列を追加
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ITEM_END)
                    + LabelConstants.ETX);
            String cmdStr = buf.toString();
            //ret = printer.sendCommand(cmdStr);
            ret = printer.sendCommand(cmdStr.getBytes());
            System.out.println(cmdStr);
            System.out.println("SendCommand");
        }
        else
        {
            throw new DaiException(ret);
        }
        printer.close();
        return ret;
    }


    /**
     * ラベルのXML定義ファイルと外部データを元に、<br>
     * SBPLコマンド文字列を生成します。
     * 
     * @param xmlFileName XML定義ファイル名
     * @param data 外部データ
     * @param copies 印刷枚数
     * @param nocutFlag 印刷終了時にカットしない場合：true、カットする場合：false
     * @param specialData true:特殊データあり false:特殊データなし
     * @return SBPLコマンド
     * @throws DaiException 異常が発生した場合
     * @throws IOException 
     */
    private byte[] generateCommand(String xmlFileName, Map[] data, int copies, boolean nocutFlag, boolean specialData)
            throws DaiException,
                IOException
    {
        File xmlFile = new File(xmlFileName);
        byte[] cmdStr = null;
        DAILabelDocument doc = null;
        try
        {
            doc = DAILabelDocument.Factory.parse(xmlFile);
            cmdStr = SBPLLabelExporter.generateCommand(doc.getDAILabel(), data, copies, nocutFlag, specialData);
            return cmdStr;
        }
        catch (XmlException e)
        {
            throw new DaiException("Err", e);
        }
    }
}
