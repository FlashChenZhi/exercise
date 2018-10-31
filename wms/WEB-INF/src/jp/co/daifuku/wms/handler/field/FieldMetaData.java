//$Id: FieldMetaData.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.handler.field;

import jp.co.daifuku.wms.handler.field.validator.DateFieldValidator;
import jp.co.daifuku.wms.handler.field.validator.FieldValidator;
import jp.co.daifuku.wms.handler.field.validator.NumberFieldValidator;
import jp.co.daifuku.wms.handler.field.validator.StringFieldValidator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * データベースやファイルのフィールドを管理するためのクラスです。
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class FieldMetaData
        extends AbstractMetaData
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 文字格納クラス (数字) */
    public static final int TYPECLASS_NUMERIC = 0;

    /** 文字格納クラス (英数字) */
    public static final int TYPECLASS_ALPHABETIC = 1;

    /** 文字格納クラス (記号含む英数字) */
    public static final int TYPECLASS_ASCII = 2;

    /** 文字格納クラス (マルチバイト文字) */
    public static final int TYPECLASS_MULTI_BYTE = 3;

    /** 格納クラス (数値) */
    public static final int TYPE_NUMBER = 0;

    /** 格納クラス (文字) */
    public static final int TYPE_STRING = 1;

    /** 格納クラス (日時) */
    public static final int TYPE_TIMESTAMP = 2;

    /** 格納クラスに対応するバリデータ */
    private static final Class[] VALIDATORS = {
        NumberFieldValidator.class,
        StringFieldValidator.class,
        DateFieldValidator.class,
    };

    /** 長さ未指定 */
    public static final Float LENGTH_UNLIMITED = Float.valueOf("-1");

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** フィールド位置 (SEQファイル用) */
    private Integer p_position = null;

    /** フィールド長 */
    private Float p_length = null;

    /** フィールドタイプ (格納クラス) */
    private Integer p_type = null;

    /** フィールドクラス (文字格納クラス) */
    private Integer p_class = null;

    /** フォーマットパターン */
    private String p_formatPattern = null;

    /** 空白可スイッチ */
    private Boolean p_enableSpace = null;

    /** 必須フラグ */
    private Boolean p_require = null;

    /** 範囲設定 */
    private String p_rangeDefine = null;

    /** バリデータ */
    private FieldValidator p_validator = null;

    /** 自動更新項目フラグ */
    private Boolean p_autoUpdate = null;

    /** 注釈説明 */
    private String p_description = null;

    /** フィールドの使用/未使用フラグ */
    private Boolean p_enabled = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 保持している内容の文字列表現を返します。
     * @return 文字列表現
     */
    public String toString()
    {
        StringBuffer buff = new StringBuffer(super.toString());

        buff.append(",Pos:");
        buff.append(getPosition());
        buff.append(",Length:");
        buff.append(getLength());
        buff.append(",Type:");
        buff.append(getType());
        buff.append(",Class:");
        buff.append(getTypeClass());
        buff.append(",Pattern:");
        buff.append(getFormatPattern());
        buff.append(",Space:");
        buff.append(isEnableSpace());
        buff.append(",Require:");
        buff.append(isRequire());
        buff.append(",Range:");
        buff.append(getRange());
        buff.append(",Description:");
        buff.append(getDescription());

        return buff.toString();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return classを返します。
     */
    public int getTypeClass()
    {
        if (p_class == null)
        {
            // Default is "ANY"
            return TYPECLASS_MULTI_BYTE;
        }
        return p_class.intValue();
    }

    /**
     * @param typeClass classを設定します。
     */
    public void setTypeClass(int typeClass)
    {
        setTypeClass(typeClass, true);
    }

    /**
     * @param typeClass classを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setTypeClass(int typeClass, boolean overwrite)
    {
        if (p_class == null || overwrite)
        {
            p_class = new Integer(typeClass);
        }
    }

    /**
     * タイプクラスが設定されているかどうかを返します。
     * 
     * @return 設定されているときtrue.
     */
    public boolean hasTypeClass()
    {
        return (p_class != null);
    }

    /**
     * @return enableSpaceを返します。
     */
    public boolean isEnableSpace()
    {
        if (p_enableSpace == null)
        {
            // Default is "enable"
            return true;
        }
        return p_enableSpace.booleanValue();
    }

    /**
     * @param enableSpace enableSpaceを設定します。
     */
    public void setEnableSpace(boolean enableSpace)
    {
        setEnableSpace(enableSpace, true);
    }

    /**
     * @param enableSpace enableSpaceを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setEnableSpace(boolean enableSpace, boolean overwrite)
    {
        if (p_enableSpace == null || overwrite)
        {
            p_enableSpace = Boolean.valueOf(enableSpace);
        }
    }

    /**
     * 空白可能が指定されているかどうかを返します。
     * 
     * @return 指定されているときtrue.
     */
    public boolean hasEnableSpace()
    {
        return (p_enableSpace != null);
    }

    /**
     * @return lengthを返します。
     */
    public Float getLength()
    {
        if (p_length == null)
        {
            // Default is "unlimited"
            return LENGTH_UNLIMITED;
        }
        return p_length;
    }

    /**
     * @param len lengthを設定します。
     */
    public void setLength(String len)
    {
        setLength(len, true);
    }

    /**
     * @param len lengthを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setLength(String len, boolean overwrite)
    {
        if (p_length == null || overwrite)
        {
            p_length = Float.valueOf(len);
        }
    }

    /**
     * @return positionを返します。
     */
    public int getPosition()
    {
        if (p_position == null)
        {
            // Default is 0
            return 0;
        }
        return p_position.intValue();
    }

    /**
     * positionを設定します。
     * @param position
     */
    public void setPosition(int position)
    {
        setPosition(position, true);
    }

    /**
     * @param position positionを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setPosition(int position, boolean overwrite)
    {
        if (p_position == null || overwrite)
        {
            p_position = new Integer(position);
        }
    }

    /**
     * 位置が指定されているかどうかを返します。
     * 
     * @return 位置が指定されているときtrue.
     */
    public boolean hasPosition()
    {
        return (p_position != null);
    }

    /**
     * @return typeを返します。
     */
    public int getType()
    {
        if (p_type == null)
        {
            // Default type is "String"
            return TYPE_STRING;
        }
        return p_type.intValue();
    }

    /**
     * @param type typeを設定します。
     */
    public void setType(int type)
    {
        setType(type, true);
    }

    /**
     * @param type typeを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setType(int type, boolean overwrite)
    {
        if (p_type == null || overwrite)
        {
            try
            {
                setValidator((FieldValidator)VALIDATORS[type].newInstance());
            }
            catch (Exception e)
            {
                throw new RuntimeException("Invalid type [" + type + "] defined on " + getName());
            }
            p_type = new Integer(type);
        }
    }

    /**
     * タイプが指定されているかどうかを返します。
     * 
     * @return タイプが指定されているときtrue.
     */
    public boolean hasType()
    {
        return (p_type != null);
    }

    /**
     * @return formatPatternを返します。
     */
    public String getFormatPattern()
    {
        return p_formatPattern;
    }

    /**
     * @param formatPattern formatPatternを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setFormatPattern(String formatPattern, boolean overwrite)
    {
        if (p_formatPattern == null || overwrite)
        {
            p_formatPattern = formatPattern;
        }
    }

    /**
     * フォーマットパターンが指定されているかどうかを返します。
     * 
     * @return 指定されているときtrue.
     */
    public boolean hasFormatPattern()
    {
        return (p_formatPattern != null);
    }

    /**
     * Require指定されているかどうかを返します。
     * 
     * @return 指定されているときtrue.
     */
    public boolean hasRequire()
    {
        return (p_require != null);
    }

    /**
     * @return requireを返します。
     */
    public boolean isRequire()
    {
        if (p_require == null)
        {
            // Default is "NOT Required"
            return false;
        }
        return p_require.booleanValue();
    }

    /**
     * @param require requireを設定します。
     */
    public void setRequire(boolean require)
    {
        setRequire(require, true);
    }

    /**
     * @param require requireを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setRequire(boolean require, boolean overwrite)
    {
        if (p_require == null || overwrite)
        {
            p_require = Boolean.valueOf(require);
        }
    }

    /**
     * @return rangeを返します。
     */
    public String getRange()
    {
        boolean noRange = FieldDefine.EMPTY_PROPERTY.equals(p_rangeDefine);
        String range = noRange ? null
                              : p_rangeDefine;
        return range;
    }

    /**
     * @param range rangeを設定します。
     */
    public void setRange(String range)
    {
        setRange(range, true);
    }

    /**
     * @param range rangeを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setRange(String range, boolean overwrite)
    {
        if (p_rangeDefine == null || overwrite)
        {
            p_rangeDefine = range;
        }
    }

    /**
     * 範囲指定されているかどうかを返します。
     * 
     * @return 指定されているときtrue.
     */
    public boolean hasRange()
    {
        return null != p_rangeDefine;
    }

    /**
     * 自動更新を取得します。
     * 
     * @return 自動更新のとき true.
     */
    public boolean isAutoUpdate()
    {
        if (p_autoUpdate == null)
        {
            // Default is "No auto update"
            return false;
        }
        return p_autoUpdate.booleanValue();
    }

    /**
     * 自動更新を設定します
     * @param autoUpdate
     */
    public void setAutoUpdate(boolean autoUpdate)
    {
        setAutoUpdate(autoUpdate, true);
    }

    /**
     * 自動更新を設定します
     * @param autoUpdate
     * @param overwrite
     */
    public void setAutoUpdate(boolean autoUpdate, boolean overwrite)
    {
        if (p_autoUpdate == null || overwrite)
        {
            p_autoUpdate = Boolean.valueOf(autoUpdate);
        }
    }

    /**
     * 自動更新指定されているかどうかを返します。
     * 
     * @return 指定されているときtrue.
     */
    public boolean hasAutoUpdate()
    {
        return (p_autoUpdate != null);
    }

    /**
     * 有効・無効を取得します。
     * 
     * @return 有効のとき true.
     */
    public boolean isEnabled()
    {
        if (p_enabled == null)
        {
            // Default is "enabled"
            return true;
        }
        return p_enabled.booleanValue();
    }

    /**
     * 有効・無効を設定します
     * @param enable 有効の時true
     */
    public void setEnabled(boolean enable)
    {
        setEnabled(enable, true);
    }

    /**
     * 有効・無効を設定します
     * 
     * @param enable 有効の時true
     * @param overwrite 現在の状態を上書きするときtrue
     */
    public void setEnabled(boolean enable, boolean overwrite)
    {
        if (p_enabled == null || overwrite)
        {
            p_enabled = Boolean.valueOf(enable);
        }
    }

    /**
     * 有効・無効指定されているかどうかを返します。
     * 
     * @return 指定されているときtrue.
     */
    public boolean hasEnabled()
    {
        return (p_enabled != null);
    }

    /**
     * @return validatorを返します。
     */
    public FieldValidator getValidator()
    {
        return p_validator;
    }

    /**
     * @param validator validatorを設定します。
     */
    public void setValidator(FieldValidator validator)
    {
        p_validator = validator;
    }

    /**
     * 精度を整数部桁数と小数部桁数に分割します。 
     * @return 整数部,小数部の桁数
     */
    public int[] getScales()
    {
        String strs = String.valueOf(getLength());
        String[] strScs = strs.split("\\.");

        int decLength = Integer.parseInt(strScs[0]);
        int pointLength = Integer.parseInt(strScs[1]);
        int[] scales = {
            decLength,
            pointLength,
        };
        return scales;
    }

    /**
     * バイト数としてトータルの長さを返します。
     * 
     * @return フィールドのバイト数
     */
    public int getByteLength()
    {
        int[] scales = getScales();
        int decLen = scales[0];
        int ptLen = scales[1];

        int byteLen = decLen;
        if (ptLen > 0)
        {
            byteLen += ptLen + 1;
        }
        return byteLen;
    }

    /**
     * @return descriptionを返します。
     */
    public String getDescription()
    {
        return p_description;
    }

    /**
     * @param description descriptionを設定します。
     */
    public void setDescription(String description)
    {
        p_description = description;
    }

    /**
     * 注釈を設定します
     * @param autoUpdate
     * @param overwrite
     */
    public void setDescription(String autoUpdate, boolean overwrite)
    {
        if (p_description == null || overwrite)
        {
            p_description = autoUpdate;
        }
    }
    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: FieldMetaData.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}
