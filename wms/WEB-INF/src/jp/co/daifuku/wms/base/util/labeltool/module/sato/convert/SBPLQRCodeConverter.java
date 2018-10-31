// $$Id: SBPLQRCodeConverter.java 7360 2010-03-04 12:09:55Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.module.sato.convert;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommand;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommandHandler;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.parse.SBPLConvertDto;
import jp.co.daifuku.wms.label.xmlbeans.QRCode;


/**
 * QRコードに関する転換処理のクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2009/12/14</td><td nowrap>S.Yoshida</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7360 $, $Date: 2010-03-04 21:09:55 +0900 (木, 04 3 2010) $
 * @author  S.Yoshida
 * @author  Last commit: $Author: kumano $
 */
public class SBPLQRCodeConverter
{
    /**
     * コマンド文字列、変数保持エンティティによりQRコードの各属性値を設定します。
     * 
     * @param item QRコードオブジェクト
     * @param qrCmd QRコードのSBPLコマンド
     * @param valCmd QRコードデータのSBPLコマンド
     * @param dto 変数保持エンティティ
     */
    public static void setProperties(QRCode item, SBPLCommand qrCmd, SBPLCommand valCmd, SBPLConvertDto dto)
    {
        // 出力順番号
        item.setSeqNo(dto.getItemSeqNo());
        // 横印字位置
        item.setHPosition(dto.getHPosition());
        // 縦印字位置
        item.setVPosition(dto.getVPosition());
        // 回転方向
        item.setRotation(dto.getRotation());
        // 繰り返しフラグ
        item.setRepeatFlg(dto.getRepeatFlg());
        // フィールドID
        item.setFieldID(dto.getFieldID());
        // QRコード種
        item.setQRCodeType(qrCmd.getName());
        // エラーコレクションレベル
        item.setErrCollectionLevel(qrCmd.getParameters().substring(1, 2));
        // セルサイズ
        item.setCellSize(Integer.parseInt(qrCmd.getParameters().substring(3, 5)));

        if (LabelConstants.CMD_QRCODE_2D30.equals(qrCmd.getName())
                || LabelConstants.CMD_QRCODE_2D31.equals(qrCmd.getName()))
        {
            // データ設定モード
            item.setDataMode(qrCmd.getParameters().substring(6, 7));
            // 連結モード
            item.setConnectionMode(qrCmd.getParameters().substring(8, 9));

            if (LabelConstants.CONNECTION_MODE_ON.equals(item.getConnectionMode()))
            {
                // 連結モード分割数
                item.setSplitCounts(Integer.parseInt(qrCmd.getParameters().substring(10, 12)));
                // 連結モード通番
                item.setSerialNumber(Integer.parseInt(qrCmd.getParameters().substring(13, 15)));
                // 連結モードパリティデータ
                item.setParityDdata(qrCmd.getParameters().substring(16));
            }
        }

        if (LabelConstants.CMD_QRCODE_DS.equals(valCmd.getName()))
        {
            // 入力モード
            item.setInputMode(valCmd.getParameters().substring(0, 1));
            // データ
            item.setValue(valCmd.getParameters().substring(2));
        }

        if (LabelConstants.CMD_QRCODE_DN.equals(valCmd.getName()))
        {
            // データ桁数
            item.setDataDigit(Integer.parseInt(valCmd.getParameters().substring(0, 4)));
            // データ
            item.setValue(valCmd.getParameters().substring(5));
        }
        clearDtoValue(dto);
    }

    /**
     * QRコードオブジェクトをSBPLコマンド文字列に転換します。
     * 
     * @param qrCodeitem QRコードオブジェクト 
     * @return SBPLコマンド文字列
     */
    public static String toSBPLString(QRCode qrCodeitem)
    {
        StringBuffer buf = new StringBuffer();

        // 回転方向
        buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
                + qrCodeitem.getRotation());
        // 縦印字位置
        if (!StringUtil.isEmpty(qrCodeitem.getVPosition()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_V_POSITION)
                    + qrCodeitem.getVPosition());
        }
        // 横印字位置
        if (!StringUtil.isEmpty(qrCodeitem.getHPosition()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_H_POSITION)
                    + qrCodeitem.getHPosition());
        }
        // QRコード種
        buf.append(SBPLCommandHandler.getInstance().getCommandName(qrCodeitem.getQRCodeType()));
        // エラーコレクションレベル
        buf.append(LabelConstants.COMMA_STR + qrCodeitem.getErrCollectionLevel());
        // セルサイズ
        buf.append(LabelConstants.COMMA_STR + StringUtil.paddingLeft(String.valueOf(qrCodeitem.getCellSize()), '0', 2));

        if (LabelConstants.CMD_QRCODE_2D30.equals(qrCodeitem.getQRCodeType())
                || LabelConstants.CMD_QRCODE_2D31.equals(qrCodeitem.getQRCodeType()))
        {
            // データ設定モード
            buf.append(LabelConstants.COMMA_STR + qrCodeitem.getDataMode());
            // 連結モード
            buf.append(LabelConstants.COMMA_STR + qrCodeitem.getConnectionMode());

            if (LabelConstants.CONNECTION_MODE_ON.equals(qrCodeitem.getConnectionMode()))
            {
                // 連結モード分割数
                buf.append(LabelConstants.COMMA_STR
                        + StringUtil.paddingLeft(String.valueOf(qrCodeitem.getSplitCounts()), '0', 2));
                // 連結モード通番
                buf.append(LabelConstants.COMMA_STR
                        + StringUtil.paddingLeft(String.valueOf(qrCodeitem.getSerialNumber()), '0', 2));
                // 連結モードパリティデータ
                buf.append(LabelConstants.COMMA_STR + qrCodeitem.getParityDdata());
            }
        }

        if (!StringUtil.isEmpty(qrCodeitem.getInputMode()))
        {
            // データ部コマンド
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_QRCODE_DS));
            // 入力モード
            buf.append(qrCodeitem.getInputMode());
            // 印字データ
            buf.append(LabelConstants.COMMA_STR + qrCodeitem.getValue());
        }
        else
        {
            // データ部コマンド
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_QRCODE_DN));
            // データ桁数
            buf.append(StringUtil.paddingLeft(String.valueOf(qrCodeitem.getValue().getBytes().length), '0', 4));
            // 印字データ
            buf.append(LabelConstants.COMMA_STR + qrCodeitem.getValue());
        }
        return buf.toString();
    }

    /**
     * 変数保持用Dtoに一部属性を初期化します。
     * 
     * @param dto 変数保持用Dto
     */
    private static void clearDtoValue(SBPLConvertDto dto)
    {
        dto.setHPosition("");
        dto.setVPosition("");
        dto.setSequencePara("");
        dto.setCharType("");
        dto.setRepeatFlg(LabelConstants.FLAG_OFF);
        dto.setFieldID("");
    }
}
