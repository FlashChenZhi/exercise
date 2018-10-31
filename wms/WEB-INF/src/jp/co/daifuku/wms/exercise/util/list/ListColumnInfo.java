package jp.co.daifuku.wms.exercise.util.list;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2009-2-9
 * Time: 1:08:45
 * To change this template use File | Settings | File Templates.
 */
public class ListColumnInfo
{
    private int index = 0;
    private String controlType;
    private FieldFormatter formatter = new NullFieldFormatter();
    private boolean balloon = false;
    private String balloonTitleResouce;


    public ListColumnInfo(int index)
    {
        this.index = index;
    }

    public ListColumnInfo(int index, FieldFormatter formatter)
    {
        this.index = index;
        this.formatter = formatter;
    }

    public ListColumnInfo(int index, String balloonTitleResouce)
    {
        this.index = index;
        this.balloon = true;
        this.balloonTitleResouce = balloonTitleResouce;
    }

    public ListColumnInfo(int index, FieldFormatter formatter, String balloonTitleResouce)
    {
        this.index = index;
        this.formatter = formatter;
        this.balloon = true;
        this.balloonTitleResouce = balloonTitleResouce;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getControlType()
    {
        return controlType;
    }

    public void setControlType(String controlType)
    {
        this.controlType = controlType;
    }

    public FieldFormatter getFormatter()
    {
        return formatter;
    }

    public void setFormatter(FieldFormatter formatter)
    {
        this.formatter = formatter;
    }

    public boolean isBalloon()
    {
        return balloon;
    }

    public void setBalloon(boolean balloon)
    {
        this.balloon = balloon;
    }

    public String getBalloonTitleResouce()
    {
        return balloonTitleResouce;
    }

    public void setBalloonTitleResouce(String balloonTitleResouce)
    {
        this.balloonTitleResouce = balloonTitleResouce;
    }

}
