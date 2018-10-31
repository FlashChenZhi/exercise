package jp.co.daifuku.wms.exercise.util.csv;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class CsvData
{
    private String name;
    private int columns;
    private int maxRows;
    private int currentRow;
    private Map<Integer, String> headMap;
    private Map<Integer, Map<Integer, String>> bodyMap;

    public CsvData(String name, int columns)
    {
        this.name = name;
        this.columns = columns;
        headMap = new HashMap<Integer, String>();
        bodyMap = new HashMap<Integer, Map<Integer, String>>();
        maxRows = 1;
        currentRow = 0;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getColumns()
    {
        return columns;
    }

    public Map<Integer, String> getHeadMap()
    {
        return headMap;
    }

    public Map<Integer, Map<Integer, String>> getBodyMap()
    {
        return bodyMap;
    }

    public int getMaxRows()
    {
        return maxRows;
    }

    public int getCurrentRow()
    {
        return currentRow;
    }

    public void setCurrentRow(int currentRow)
    {
        this.currentRow = currentRow;
    }

    public void setHeader(int column, String text)
    {
        headMap.put(column, text == null ? StringUtils.EMPTY : text);
    }

    public void addRow()
    {
        bodyMap.put(maxRows++, new HashMap<Integer, String>());
    }

    public void setValue(int column, String text)
    {
        Map value = bodyMap.get(currentRow);
        if (value != null)
        {
            value.put(column, "\"" + (text == null ? StringUtils.EMPTY : text) + "\"");
        }
    }

    public Map<Integer,String> getValue(int rowNum)
    {
        return bodyMap.get(rowNum);
    }

    public String generateHead()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 1; i <= columns ; i++)
        {
           sb.append(headMap.get(i));
           sb.append(",");
        }
        return sb.substring(0,sb.length() -1);
    }

    public String makeLine(Map<Integer, String> value)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 1; i <= columns ; i++)
        {
            sb.append(value.get(i));
            sb.append(",");
        }
        return sb.substring(0,sb.length() -1);
    }
}
