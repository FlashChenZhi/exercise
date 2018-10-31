package jp.co.daifuku.wms.base.util.labeltool.module.sato.print;


import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommandHandler;
import jp.co.daifuku.wms.base.util.labeltool.base.util.SBPLUtil;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBWInversionConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBarCodeConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBarCodeRatioSpecConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBitMapConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLCompositeSymbolConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLFontConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLFormCallerConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLFrameConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLPartCopyConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLQRCodeConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLRulerConverter;
import jp.co.daifuku.wms.label.xmlbeans.BWInversion;
import jp.co.daifuku.wms.label.xmlbeans.BarCode;
import jp.co.daifuku.wms.label.xmlbeans.BarCodeRatioSpec;
import jp.co.daifuku.wms.label.xmlbeans.CompositeSymbol;
import jp.co.daifuku.wms.label.xmlbeans.Font;
import jp.co.daifuku.wms.label.xmlbeans.FormCaller;
import jp.co.daifuku.wms.label.xmlbeans.Frame;
import jp.co.daifuku.wms.label.xmlbeans.Item;
import jp.co.daifuku.wms.label.xmlbeans.PartCopy;
import jp.co.daifuku.wms.label.xmlbeans.QRCode;
import jp.co.daifuku.wms.label.xmlbeans.RepeatDef;
import jp.co.daifuku.wms.label.xmlbeans.Ruler;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument.DAILabel;

import org.apache.xmlbeans.XmlObject;


/**
 * ラベルアイテムオブジェクトとSPBPコマンドの転換クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/18</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  chenjun
 * @author  Last commit: $Author: kitamaki $
 */
public class SBPLLabelExporter
{
    /**
     * ラベルのモデルオブジェクト、外部データ、印刷枚数より、<br>
     * SBPLコマンド文字列を生成します。
     * 
     * @param model ラベルのモデルオブジェクト
     * @param data 外部データ
     * @param copies 印刷枚数
     * @param nocutFlag 印刷終了時にカットしない場合：true、カットする場合：false
     * @param specialData true:特殊データあり false:特殊データなし
     * @return SBPLコマンド文字列
     */
    public static byte[] generateCommand(DAILabel model, Map[] data, int copies, boolean nocutFlag, boolean specialData)
    {
        int totalPages = 1; // 総ページ数
        int maxCounts = 1; // １ページの最大データ数
        int dataCounts = 1;
        byte[] sendData = null;

        if (data != null)
        {
            dataCounts = data.length;
        }

        // 繰返体を取得する。
        RepeatDef repeater = model.getRepeatDef();
        if (repeater != null)
        {
            maxCounts = repeater.getMaxCounts();
            // 総ページ数を計算する。
            totalPages = (int)Math.ceil(new Double(dataCounts) / new Double(maxCounts));
        }

        Item[] items = model.getItemArray();
        for (int i = 0; i < items.length; i++)
        {
            Item item = items[i];
            if (repeater == null)
            {
                if (data == null)
                {
                    // 送信データがNULLの場合
                    if (sendData == null)
                    {
                        sendData = generateItemCommand(item, model, null, 0, 0, copies, nocutFlag, specialData);
                    }
                    // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                    // 可変項目のフィールド定義以外
                    else if (i != items.length - 1)
                    {
                        sendData =
                                SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, null, 0, 0,
                                        copies, nocutFlag, specialData));
                    }
                    // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                    // ItemArrayの最後は、可変項目のフィールド定義となる。
                    // フィールド定義に対し、印字データをセットする必要がない為、最後の引数を固定でfalseとする。
                    else
                    {
                        sendData =
                                SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, null, 0, 0,
                                        copies, nocutFlag, false));
                    }
                }
                else
                {
                    for (int j = 0; j < data.length; j++)
                    {
                        Map[] smallData = new Map[1];
                        smallData[0] = data[j];
                        // 送信データがNULLの場合
                        if (sendData == null)
                        {
                            sendData =
                                    generateItemCommand(item, model, smallData, 0, 0, copies, nocutFlag, specialData);
                        }
                        // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                        // 可変項目のフィールド定義以外
                        else if (i != items.length - 1)
                        {
                            sendData =
                                    SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, smallData, 0,
                                            0, copies, nocutFlag, specialData));
                        }
                        // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                        // ItemArrayの最後は、可変項目のフィールド定義となる。
                        // フィールド定義に対し、印字データをセットする必要がない為、最後の引数を固定でfalseとする。
                        else
                        {
                            sendData =
                                    SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, smallData, 0,
                                            0, copies, nocutFlag, false));
                        }
                    }
                }
            }
            else
            {
                if (item.getFormRegFlag().equals(LabelConstants.FLAG_ON))
                {
                    // 送信データがNULLの場合
                    if (sendData == null)
                    {
                        sendData = generateItemCommand(item, model, null, 0, 0, 1, nocutFlag, specialData);
                    }
                    // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                    // 可変項目のフィールド定義以外
                    else if (i != items.length - 1)
                    {
                        sendData =
                                SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, null, 0, 0, 1,
                                        nocutFlag, specialData));
                    }
                    // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                    // ItemArrayの最後は、可変項目のフィールド定義となる。
                    // フィールド定義に対し、印字データをセットする必要がない為、最後の引数を固定でfalseとする。
                    else
                    {
                        sendData =
                                SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, null, 0, 0, 1,
                                        nocutFlag, false));
                    }
                }
                else
                {
                    for (int j = 1; j <= totalPages; j++)
                    {
                        // 送信データがNULLの場合
                        if (sendData == null)
                        {
                            sendData =
                                    generateItemCommand(item, model, data, j, totalPages, copies, nocutFlag,
                                            specialData);
                        }
                        // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                        // 可変項目のフィールド定義以外
                        if (i != items.length - 1)
                        {
                            sendData =
                                    SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, data, j,
                                            totalPages, copies, nocutFlag, specialData));
                        }
                        // MultiLabelistにて、可変項目を使用している場合のみ対応サンプル
                        // ItemArrayの最後は、可変項目のフィールド定義となる。
                        // フィールド定義に対し、印字データをセットする必要がない為、最後の引数を固定でfalseとする。
                        else
                        {
                            sendData =
                                    SBPLBitMapConverter.concat(sendData, generateItemCommand(item, model, data, j,
                                            totalPages, copies, nocutFlag, false));
                        }
                    }
                }
            }

        }
        System.out.println(new String(sendData));
        return sendData;
    }

    /**
     * 指定ページ番号のデータをもとにアイテムをコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item ラベルアイテム
     * @param model ラベル対象モデル
     * @param data データ
     * @param pageNo ページ番号
     * @param totalPages 総ページ数
     * @param copies 印刷枚数
     * @param nocutFlag 印刷終了時にカットしない場合：true、カットする場合：false
     * @param specialData true:特殊データあり false:特殊データなし
     * @return コマンド文字列
     */
    private static byte[] generateItemCommand(Item item, DAILabel model, Map[] data, int pageNo, int totalPages,
            int copies, boolean nocutFlag, boolean datatype)
    {
        StringBuffer buf = new StringBuffer();
        // アイテム開始文字列を追加
        buf.append(LabelConstants.STX + LabelConstants.ESC + LabelConstants.CMD_ITEM_START);
        // 用紙サイズ文字列を追加
        buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_SIZE));
        buf.append(StringUtil.paddingLeft(String.valueOf(item.getVSize()), '0', 4));
        buf.append(StringUtil.paddingLeft(String.valueOf(item.getHSize()), '0', 4));
        // ジョブ番号文字列を追加
        if (!StringUtil.isEmpty(item.getJobID()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_JOB_ID));
            // ジョブ番号をセット
            //item.setJobID("99");
            buf.append(item.getJobID());
        }
        //?W???u????w?? ジョブ名称文字列を追加
        if (!StringUtil.isEmpty(item.getJobName()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_JOB_NAME));
            buf.append(item.getJobName());
        }
        if (LabelConstants.FLAG_ON.equals(item.getPartEditFlag()))
        {
            // 部分編集追加
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PART_EDIT_FLAG));
        }
        // 印字項目数をHashtableに保持する。
        Hashtable<Integer, XmlObject> elements = SBPLUtil.getAllElements(item);
        // 繰返し定義を取得する。
        RepeatDef repeater = model.getRepeatDef();

        // 各印字項目をコマンド文字列に転換する。
        if (elements.size() > 0)
        {
            for (int i = 0; i < elements.size(); i++)
            {
                // フォント項目の場合
                if (elements.get(i) instanceof Font)
                {
                    Font fontItem = (Font)elements.get(i).copy();
                    buf.append(generateFontCommand(fontItem, repeater, data, pageNo));
                }
                // 罫線項目の場合
                if (elements.get(i) instanceof Ruler)
                {
                    Ruler rulerItem = (Ruler)elements.get(i).copy();
                    buf.append(generateRulerCommand(rulerItem, repeater, data, pageNo));
                }
                // 枠線項目の場合
                if (elements.get(i) instanceof Frame)
                {
                    Frame frameItem = (Frame)elements.get(i).copy();
                    buf.append(generateFrameCommand(frameItem, repeater, data, pageNo));
                }
                // バーコードの場合
                if (elements.get(i) instanceof BarCode)
                {
                    BarCode barCodeItem = (BarCode)elements.get(i).copy();
                    buf.append(generateBarCodeCommand(barCodeItem, repeater, data, pageNo));
                }
                // EAN、UCC合成シンボルの場合
                if (elements.get(i) instanceof CompositeSymbol)
                {
                    CompositeSymbol symbolItem = (CompositeSymbol)elements.get(i).copy();
                    buf.append(generateSymbolCommand(symbolItem, repeater, data, pageNo));
                }
                // フォームオーバレイ呼出しの場合
                if (elements.get(i) instanceof FormCaller)
                {
                    FormCaller formCallerItem = (FormCaller)elements.get(i).copy();
                    buf.append(SBPLFormCallerConverter.toSBPLString(formCallerItem));
                }
                // 部分コピーの場合
                if (elements.get(i) instanceof PartCopy)
                {
                    PartCopy partCopyItem = (PartCopy)elements.get(i).copy();
                    buf.append(SBPLPartCopyConverter.toSBPLString(partCopyItem));
                }
                // 白黒反転印字の場合
                if (elements.get(i) instanceof BWInversion)
                {
                    BWInversion bwItem = (BWInversion)elements.get(i).copy();
                    buf.append(SBPLBWInversionConverter.toSBPLString(bwItem));
                }
                if (elements.get(i) instanceof BarCodeRatioSpec)
                {
                    BarCodeRatioSpec barCodeRatioSpecItem = (BarCodeRatioSpec)elements.get(i).copy();
                    buf.append(SBPLBarCodeRatioSpecConverter.toSBPLString(barCodeRatioSpecItem));
                }
                // QRコードの場合
                if (elements.get(i) instanceof QRCode)
                {
                    QRCode qrCodeItem = (QRCode)elements.get(i).copy();
                    buf.append(generateQRCodeCommand(qrCodeItem, repeater, data, pageNo));
                }
            }
        }
        // カット指定
        if (nocutFlag)
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_CUT_CT));
            buf.append(0);
        }

        byte[] bmpbuf = null;
        if (datatype == true)
        {
            // ビットマップデータの取込
            bmpbuf = SBPLBitMapConverter.getData(item, copies);
            try
            {
                return SBPLBitMapConverter.concat(buf.toString().getBytes("MS932"), bmpbuf);
            }
            catch (UnsupportedEncodingException e)
            {
                return SBPLBitMapConverter.concat(buf.toString().getBytes(), bmpbuf);
            }
        }
        else
        {
            // 印刷枚数を追加
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PRINT_NUMBER));
            buf.append(copies);
            if (LabelConstants.FLAG_ON.equals(item.getFormRegFlag()))
            {
                // フォームオーバレー登録指定追加
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_FORM_REG));
                buf.append(item.getRegisterKey());
            }
            // アイテム終了文字列を追加
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ITEM_END)
                    + LabelConstants.ETX);
        }
        try
        {
            return SBPLBitMapConverter.concat(buf.toString().getBytes("MS932"));
        }
        catch (UnsupportedEncodingException e)
        {
            return SBPLBitMapConverter.concat(buf.toString().getBytes());
        }
    }

    /**
     * 指定ページ番号のフォントをコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item フォント項目
     * @param repeater 繰返し定義
     * @param data データ
     * @param pageNo ページ番号
     * @return コマンド文字列
     */
    private static String generateFontCommand(Font item, RepeatDef repeater, Map[] data, int pageNo)
    {
        int hOffset = 0;
        int vOffset = 0;
        int maxCounts = 1;
        if (repeater != null)
        {
            hOffset = repeater.getHOffset();
            vOffset = repeater.getVOffset();
            maxCounts = repeater.getMaxCounts();
        }

        StringBuffer buf = new StringBuffer("");

        if ((item.getRepeatFlg() != null) && item.getRepeatFlg().equals(LabelConstants.FLAG_ON))
        {
            // 繰返しの場合
            Map[] pageData = SBPLUtil.getPageData(data, pageNo, maxCounts);
            for (int index = 0; index < pageData.length; index++)
            {
                if (!StringUtil.isEmpty(item.getFieldID()))
                {
                    item.setValue((String)pageData[index].get(item.getFieldID()));
                }
                buf.append(SBPLFontConverter.toSBPLString(item));
                item.setHPosition(String.valueOf(Integer.parseInt(item.getHPosition()) + hOffset));
                item.setVPosition(String.valueOf(Integer.parseInt(item.getVPosition()) + vOffset));
            }
        }
        else
        {
            if (!StringUtil.isEmpty(item.getFieldID()))
            {
                // ページ表示の場合
                if (item.getFieldID().contains(LabelConstants.PAGE_ITEM))
                {
                    // 表現形式を判別する。
                    String pattern = item.getFieldID().substring(LabelConstants.PAGE_ITEM.length() + 1);
                    if (pattern.equals(LabelConstants.PAGE_FMT_NUMBER_ONLY))
                    {
                        item.setValue(String.valueOf(pageNo));
                    }
                    else if (pattern.equals(LabelConstants.PAGE_FMT_FULL))
                    {
                        int totalPages = (int)Math.ceil(new Double(data.length) / new Double(repeater.getMaxCounts()));
                        item.setValue(String.valueOf(pageNo) + "/" + String.valueOf(totalPages));
                    }
                }
                // カウントの場合
                else if (!StringUtil.isEmpty(item.getAggregateMode())
                        && item.getAggregateMode().equals(LabelConstants.FLAG_OFF))
                {
                    item.setValue(String.valueOf(data.length));
                }
                // 集計の場合
                else if (!StringUtil.isEmpty(item.getAggregateMode())
                        && item.getAggregateMode().equals(LabelConstants.FLAG_ON))
                {
                    int sumValue = SBPLUtil.getGrandTotal(data, item.getFieldID());
                    item.setValue(String.valueOf(sumValue));
                }
                else
                {

                    item.setValue((String)data[0].get(item.getFieldID()));
                }
            }
            buf.append(SBPLFontConverter.toSBPLString(item));
        }
        return buf.toString();
    }

    /**
     * 指定ページ番号の羅線項目をコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item 羅線項目
     * @param repeater 繰返し定義
     * @param data データ
     * @param pageNo ページ番号
     * @return コマンド文字列
     */
    private static String generateRulerCommand(Ruler item, RepeatDef repeater, Map[] data, int pageNo)
    {
        StringBuffer buf = new StringBuffer("");
        if (!StringUtil.isEmpty(item.getRepeatFlg()) && item.getRepeatFlg().equals(LabelConstants.FLAG_ON))
        {
            // 繰返しの場合
            int hOffset = repeater.getHOffset();
            int vOffset = repeater.getVOffset();
            int maxCounts = repeater.getMaxCounts();
            Map[] pageData = SBPLUtil.getPageData(data, pageNo, maxCounts);
            for (int index = 0; index < pageData.length; index++)
            {
                buf.append(SBPLRulerConverter.toSBPLString(item));
                item.setHPosition(String.valueOf(Integer.parseInt(item.getHPosition()) + hOffset));
                item.setVPosition(String.valueOf(Integer.parseInt(item.getVPosition()) + vOffset));
            }
        }
        else
        {
            buf.append(SBPLRulerConverter.toSBPLString(item));
        }
        return buf.toString();
    }

    /**
     * 指定ページ番号の枠線項目をコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item 枠線項目
     * @param repeater 繰返し定義
     * @param data データ
     * @param pageNo ページ番号
     * @return コマンド文字列
     */
    private static String generateFrameCommand(Frame item, RepeatDef repeater, Map[] data, int pageNo)
    {
        StringBuffer buf = new StringBuffer("");
        if (!StringUtil.isEmpty(item.getRepeatFlg()) && item.getRepeatFlg().equals(LabelConstants.FLAG_ON))
        {
            // 繰返しの場合
            int hOffset = repeater.getHOffset();
            int vOffset = repeater.getVOffset();
            int maxCounts = repeater.getMaxCounts();
            Map[] pageData = SBPLUtil.getPageData(data, pageNo, maxCounts);
            for (int index = 0; index < pageData.length; index++)
            {
                buf.append(SBPLFrameConverter.toSBPLString(item));
                item.setHPosition(String.valueOf(Integer.parseInt(item.getHPosition()) + hOffset));
                item.setVPosition(String.valueOf(Integer.parseInt(item.getVPosition()) + vOffset));
            }
        }
        else
        {
            buf.append(SBPLFrameConverter.toSBPLString(item));
        }
        return buf.toString();
    }

    /**
     * 指定ページ番号のバーコード項目をコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item バーコード項目
     * @param repeater 繰返し定義
     * @param data データ
     * @param pageNo ページ番号
     * @return コマンド文字列
     */
    private static String generateBarCodeCommand(BarCode item, RepeatDef repeater, Map[] data, int pageNo)
    {
        StringBuffer buf = new StringBuffer("");
        if (!StringUtil.isEmpty(item.getRepeatFlg()) && item.getRepeatFlg().equals(LabelConstants.FLAG_ON))
        {
            // 繰返しの場合
            int hOffset = repeater.getHOffset();
            int vOffset = repeater.getVOffset();
            int maxCounts = repeater.getMaxCounts();
            Map[] pageData = SBPLUtil.getPageData(data, pageNo, maxCounts);
            for (int index = 0; index < pageData.length; index++)
            {
                if (!StringUtil.isEmpty(item.getFieldID()))
                {
                    item.setValue((String)pageData[index].get(item.getFieldID()));
                }
                buf.append(SBPLBarCodeConverter.toSBPLString(item));
                item.setHPosition(String.valueOf(Integer.parseInt(item.getHPosition()) + hOffset));
                item.setVPosition(String.valueOf(Integer.parseInt(item.getVPosition()) + vOffset));
            }
        }
        else
        {
            if (!StringUtil.isEmpty(item.getFieldID()))
            {
                item.setValue((String)data[0].get(item.getFieldID()));
            }
            buf.append(SBPLBarCodeConverter.toSBPLString(item));
        }
        return buf.toString();
    }

    /**
     * 指定ページ番号のQRコード項目をコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item QRコード項目
     * @param repeater 繰返し定義
     * @param data データ
     * @param pageNo ページ番号
     * @return コマンド文字列
     */
    private static String generateQRCodeCommand(QRCode item, RepeatDef repeater, Map[] data, int pageNo)
    {
        StringBuffer buf = new StringBuffer("");
        if (!StringUtil.isEmpty(item.getRepeatFlg()) && item.getRepeatFlg().equals(LabelConstants.FLAG_ON))
        {
            // 繰返しの場合
            int hOffset = repeater.getHOffset();
            int vOffset = repeater.getVOffset();
            int maxCounts = repeater.getMaxCounts();
            Map[] pageData = SBPLUtil.getPageData(data, pageNo, maxCounts);
            for (int index = 0; index < pageData.length; index++)
            {
                if (!StringUtil.isEmpty(item.getFieldID()))
                {
                    item.setValue((String)pageData[index].get(item.getFieldID()));
                }
                buf.append(SBPLQRCodeConverter.toSBPLString(item));
                item.setHPosition(String.valueOf(Integer.parseInt(item.getHPosition()) + hOffset));
                item.setVPosition(String.valueOf(Integer.parseInt(item.getVPosition()) + vOffset));
            }
        }
        else
        {
            if (!StringUtil.isEmpty(item.getFieldID()))
            {
                item.setValue((String)data[0].get(item.getFieldID()));
            }
            buf.append(SBPLQRCodeConverter.toSBPLString(item));
        }
        return buf.toString();
    }

    /**
     * 指定ページ番号の合成シンボル項目をコマンド文字列に転換するメソッドです。<br>
     * 
     * @param item 合成シンボル項目
     * @param repeater 繰返し定義
     * @param data データ
     * @param pageNo ページ番号
     * @return コマンド文字列
     */
    private static String generateSymbolCommand(CompositeSymbol item, RepeatDef repeater, Map[] data, int pageNo)
    {
        StringBuffer buf = new StringBuffer("");
        if (!StringUtil.isEmpty(item.getRepeatFlg()) && item.getRepeatFlg().equals(LabelConstants.FLAG_ON))
        {
            // 繰返しの場合
            int hOffset = repeater.getHOffset();
            int vOffset = repeater.getVOffset();
            int maxCounts = repeater.getMaxCounts();
            Map[] pageData = SBPLUtil.getPageData(data, pageNo, maxCounts);
            for (int index = 0; index < pageData.length; index++)
            {
                // 繰返しの場合
                item.setValue((String)pageData[index].get(item.getFieldID()));
                buf.append(SBPLCompositeSymbolConverter.toSBPLString(item));
                item.setHPosition(String.valueOf(Integer.parseInt(item.getHPosition()) + hOffset));
                item.setVPosition(String.valueOf(Integer.parseInt(item.getVPosition()) + vOffset));
            }
        }
        else
        {
            if (!StringUtil.isEmpty(item.getFieldID()))
            {
                item.setValue((String)data[0].get(item.getFieldID()));
            }
            buf.append(SBPLCompositeSymbolConverter.toSBPLString(item));
        }
        return buf.toString();
    }
}
