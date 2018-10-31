package jp.co.daifuku.wms.exercise.util.query;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-25
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class Tuple
{
    public boolean isArray()
    {
        return isArray;
    }

    public void setArray(boolean array)
    {
        isArray = array;
    }

    private boolean isArray;
    private Object value;

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public ValueType getValueType()
    {
        return valueType;
    }

    public void setValueType(ValueType valueType)
    {
        this.valueType = valueType;
    }

    private ValueType valueType;

    public Tuple(Object value, ValueType valueType)
    {
           this(value, valueType, false);
    }

    public Tuple(Object value, ValueType valueType, boolean isArray)
    {
        this.value = value;
        this.valueType = valueType;
        this.isArray = isArray;
    }
}
