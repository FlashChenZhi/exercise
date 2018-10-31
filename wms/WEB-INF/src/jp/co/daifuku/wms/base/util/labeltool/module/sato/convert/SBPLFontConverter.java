// $$Id: SBPLFontConverter.java 2950 2009-01-29 01:06:21Z kumano $$
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
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.SBPLUtil;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.parse.SBPLConvertDto;
import jp.co.daifuku.wms.label.xmlbeans.Font;


/**
 * フォント指定オブジェクトに関する転換処理のクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/25</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2950 $, $Date: 2009-01-29 10:06:21 +0900 (木, 29 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLFontConverter
{

    /**
     * コマンド文字列、変数保持エンティティによりフォントの属性値を設定します。<br>
     * 
     * @param item フォントオブジェクト
     * @param cmd フォントのSBPLコマンド
     * @param dto 変数保持エンティティ
     * @throws DaiException 異常が発生した場合
     */
    public static void setProperties(Font item, SBPLCommand cmd, SBPLConvertDto dto)
            throws DaiException
    {
        // 出力順番号
        item.setSeqNo(dto.getItemSeqNo());
        // 横印字位置
        item.setHPosition(dto.getHPosition());
        // 縦印字位置
        item.setVPosition(dto.getVPosition());
        // 回転方向
        item.setRotation(dto.getRotation());
        // 文字間ピッチ
        item.setPitch(dto.getPitch());
        // 横方向拡大比率 
        item.setHEnlargeRatio(dto.getHEnlargeRatio());
        // 縦方向拡大比率
        item.setVEnlargeRatio(dto.getVEnlargeRatio());
        item.setPitchMode(dto.getPitchMode());
        // 連番指定パラメータ�A��
        item.setSequencePara(dto.getSequencePara());
        // 漢字コード
        item.setKanjiCode(dto.getKanjiCode());
        // 行間ピッチ幅
        if (!StringUtil.isEmpty(dto.getLinePitch()))
        {
            item.setLinePitch(dto.getLinePitch());
        }
        item.setRepeatFlg(dto.getRepeatFlg());
        item.setFieldID(dto.getFieldID());
        item.setAggregateMode(dto.getAggregateMode());
        // 横書き漢字指定
        if (SBPLUtil.isKanjiFont(cmd.getName()))
        {
            item.setKanjiCode(LabelConstants.KANJI_CODE_SJIS);
            item.setFontType(cmd.getName());
            item.setKanjiMode(cmd.getParameters().substring(0, 1));
            setFontValue(item, cmd.getParameters().substring(1), true);
        }
        // 横書き半角・全角混在漢字指定
        if (SBPLUtil.isMixedFont(cmd.getName()))
        {
            item.setKanjiCode(LabelConstants.KANJI_CODE_SJIS);
            item.setFontType(cmd.getName());
            item.setKanjiMode(cmd.getParameters().substring(0, 1));
            setFontValue(item, cmd.getParameters().substring(1), false);
        }

        // X20-X23フォント指定スムージングなし
        if (SBPLUtil.isXNoneSmoothingFont(cmd.getName()))
        {
            item.setFontType(cmd.getName());
            setFontValue(item, cmd.getParameters().substring(1), false);
        }

        // X23-X24フォント指定スムージングある
        if (SBPLUtil.isXSmoothingFont(cmd.getName()))
        {
            item.setFontType(cmd.getName());
            item.setSmoothFlag(cmd.getParameters().substring(1, 2));
            setFontValue(item, cmd.getParameters().substring(2), false);
        }
        // ＯＣＲフォント指定
        if (SBPLUtil.isOCRFont(cmd.getName()))
        {
            item.setFontType(cmd.getName());
            setFontValue(item, cmd.getParameters(), false);
        }
        // 海外プリンタ用指定スムージングなし
        if (SBPLUtil.isOtherCountryFont(cmd.getName()))
        {
            item.setFontType(cmd.getName());
            setFontValue(item, cmd.getParameters(), false);
        }
        // 海外プリンタ用フォント指定スムージングある
        if (SBPLUtil.isOtherXSmoothingFont(cmd.getName()))
        {
            item.setFontType(cmd.getName());
            item.setSmoothFlag(cmd.getParameters().substring(0, 1));
            setFontValue(item, cmd.getParameters().substring(1), false);
        }
        // アウトラインフォント
        if (cmd.getName().equals(LabelConstants.CMD_FONTTYPE_OUTLINE))
        {
            String[] fontOutLineValue = dto.getOutlineFontSpec().split(LabelConstants.COMMA_STR);
            item.setFontType(LabelConstants.CMD_OTL_FONT_SHAPE_SPEC);
            item.setOutlineFontType(fontOutLineValue[0]);
            item.setOutlineFontWidth(Integer.parseInt(fontOutLineValue[1]));
            item.setOutlineFontHeight(Integer.parseInt(fontOutLineValue[2]));
            item.setOutlineFontStyle(fontOutLineValue[3]);
            setFontValue(item, cmd.getParameters(), false);
        }
        // ページ番号
        if (dto.getPageFlg().equals(LabelConstants.FLAG_ON))
        {
            item.setFieldID(LabelConstants.PAGE_ITEM + LabelConstants.COMMA_STR + dto.getPageFormat());
        }
        clearDtoValue(dto);
    }

    /**
     * フォントオブジェクトをSBPLコマンド文字列に転換します。
     * 
     * @param fontItem フォントオブジェクト
     * @return SBPL文字列
     */
    public static String toSBPLString(Font fontItem)
    {
        StringBuffer buf = new StringBuffer();
        buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
                + fontItem.getRotation());
        if (!StringUtil.isEmpty(fontItem.getVPosition()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_V_POSITION)
                    + fontItem.getVPosition());
        }
        if (!StringUtil.isEmpty(fontItem.getHPosition()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_H_POSITION)
                    + fontItem.getHPosition());
        }
        if (!StringUtil.isEmpty(fontItem.getPitch()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PITCH) + fontItem.getPitch());
        }
        if (fontItem.getHEnlargeRatio() != 0)
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ENLARGE_RATIO)
                    + StringUtil.paddingLeft(String.valueOf(fontItem.getHEnlargeRatio()), '0', 2)
                    + StringUtil.paddingLeft(String.valueOf(fontItem.getVEnlargeRatio()), '0', 2));
        }
        //�A�Ԑݒ�
        if (!StringUtil.isEmpty(fontItem.getSequencePara()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_SEQUENCE_PARA)
                    + fontItem.getSequencePara());
        }
        if (!StringUtil.isEmpty(fontItem.getKanjiCode()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_KANJI_CODE)
                    + fontItem.getKanjiCode());
        }
        if (!StringUtil.isEmpty(fontItem.getLinePitch()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_LINE_PITCH)
                    + fontItem.getLinePitch());
        }
        // アウトラインフォントの場合
        if (LabelConstants.CMD_OTL_FONT_SHAPE_SPEC.equals(fontItem.getFontType()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_OTL_FONT_SHAPE_SPEC)
                    + fontItem.getOutlineFontType() + LabelConstants.COMMA_STR + fontItem.getOutlineFontWidth()
                    + LabelConstants.COMMA_STR + fontItem.getOutlineFontHeight() + LabelConstants.COMMA_STR
                    + fontItem.getOutlineFontStyle());

            buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_FONTTYPE_OUTLINE)
                    + convertValue(fontItem.getValue()));
        }
        if (!StringUtil.isEmpty(fontItem.getKanjiMode()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(fontItem.getFontType())
                    + fontItem.getKanjiMode());

            buf.append(convertValue(fontItem.getValue()));

        }
        // ＯＣＲフォントの場合
        if (LabelConstants.CMD_FONTTYPE_OA.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_OB.equals(fontItem.getFontType()))
        {
            buf.append(SBPLCommandHandler.getInstance().getCommandName(fontItem.getFontType()));

            buf.append(convertValue(fontItem.getValue()));
        }
        // Ｘ２０、Ｘ２１、Ｘ２２フォントの場合
        if (LabelConstants.CMD_FONTTYPE_X20.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_X21.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_X22.equals(fontItem.getFontType()))
        {
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_START));
            }
            buf.append(SBPLCommandHandler.getInstance().getCommandName(fontItem.getFontType())
                    + LabelConstants.COMMA_STR);
            buf.append(convertValue(fontItem.getValue()));
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_END));
            }
        }
        // Ｘ２３、Ｘ２４フォントの場合
        if (LabelConstants.CMD_FONTTYPE_X23.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_X24.equals(fontItem.getFontType()))
        {
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_START));
            }
            buf.append(SBPLCommandHandler.getInstance().getCommandName(fontItem.getFontType())
                    + LabelConstants.COMMA_STR + fontItem.getSmoothFlag());
            buf.append(convertValue(fontItem.getValue()));
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_END));
            }
        }
        // 海外フォントスムージング無しの場合
        if (LabelConstants.CMD_FONTTYPE_U.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_S.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_M.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_XU.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_XS.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_XM.equals(fontItem.getFontType()))
        {
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_START));
            }
            buf.append(SBPLCommandHandler.getInstance().getCommandName(fontItem.getFontType()));
            buf.append(convertValue(fontItem.getValue()));
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_END));
            }
        }
        // 海外フォントスムージング有りの場合
        if (LabelConstants.CMD_FONTTYPE_XB.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_XL.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_WB.equals(fontItem.getFontType())
                || LabelConstants.CMD_FONTTYPE_WL.equals(fontItem.getFontType())
                        )
        {
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_START));
            }
            buf.append(SBPLCommandHandler.getInstance().getCommandName(fontItem.getFontType())
                    + fontItem.getSmoothFlag());
            buf.append(convertValue(fontItem.getValue()));
            if (LabelConstants.PITCH_MODE_PROP.equals(fontItem.getPitchMode()))
            {
                buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PROP_PITCH_MODE_END));
            }
        }
        return buf.toString();
    }

    /**
     * フォントの印字データを設定します。
     *  
     * @param fontItem フォントオブジェクト
     * @param value 印字データ文字列(SBPL)
     * @param convertCharFlg キャラクタ転換フラグ(jis → shift_jis)
     * @throws DaiException 異常が発生した場合
     */
    private static void setFontValue(Font fontItem, String value, boolean convertCharFlg)
            throws DaiException
    {
        if (convertCharFlg)
        {
            String[] values = value.split(LabelConstants.ENTER_STR);
            if (values.length > 1)
            {
                String tempStr = "";
                for (int i = 0; i < values.length; i++)
                {
                    if (i == 0)
                    {
                        tempStr += SBPLUtil.jisToShiftJIS(values[i]);
                    }
                    else
                    {
                        tempStr += LabelConstants.NEW_LINE_STR + SBPLUtil.jisToShiftJIS(values[i]);
                    }
                }
                fontItem.setValue(tempStr);
            }
            else
            {
                fontItem.setValue(SBPLUtil.jisToShiftJIS(value));
            }
        }
        else
        {
            fontItem.setValue(value.replaceAll(LabelConstants.ENTER_STR, LabelConstants.NEW_LINE_STR));
        }
    }

    /**
     * フォントオブジェクトの印字データ文字列をSBPL印字データ文字列に転換します。
     * @param value フォントオブジェクトの印字データ文字列
     * @return SBPL印字データ文字列
     */
    private static String convertValue(String value)
    {
        if (value == null)
        {
            return "";
        }
        return value.replaceAll(LabelConstants.NEW_LINE_STR, LabelConstants.ENTER_STR);
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
        dto.setPageFlg(LabelConstants.FLAG_OFF);
        dto.setRepeatFlg(LabelConstants.FLAG_OFF);
        dto.setFieldID("");
        dto.setAggregateMode("");
    }
}
