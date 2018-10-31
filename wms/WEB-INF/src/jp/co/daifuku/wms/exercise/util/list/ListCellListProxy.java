package jp.co.daifuku.wms.exercise.util.list;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.ControlColorSupport;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.ui.web.ToolTipHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Zhouyue
 * Date: 2010-5-5
 * Time: 16:11:45
 * Copyright Daifuku Shanghai Ltd.
 */
public abstract class ListCellListProxy<T>
{
    private ListCell listcell = null;

    private Field fields[] = null;
    private Map<String, Method> getterMap = null;
    private Map<String, Method> setterMap = null;
    private Map<String, ListColumnInfo> colunmInfoMap = null;


    private Logger logger = LogManager.getLogger(ListProxy.class);

    public ListCellListProxy(ListCell listcell)
    {
        this.listcell = listcell;
        colunmInfoMap = getColunmInfos();
    }

    public void check(String columnName, boolean isChecked)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }

        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        for (int i = 0; i < this.listcell.getMaxRows() - 1; i++)
        {
            check(i + 1, columnInfo.getIndex(), isChecked);
        }
    }

    public void check(int rowIndex, String columnName, boolean isChecked)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }

        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        check(rowIndex, columnInfo.getIndex(), isChecked);

    }

    private void check(int rowIndex, int columnIndex, boolean checked)
    {
        listcell.setCurrentRow(rowIndex);
        listcell.setChecked(columnIndex, checked);
    }

    private void setCellEnabled(int rowIndex, int columnIndex, boolean isEnabled)
    {
        listcell.setCurrentRow(rowIndex);
        listcell.setCellEnabled(columnIndex, isEnabled);
    }

    public void setCellEnabled(int rowIndex, String columnName, boolean isEnabled)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }

        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        setCellEnabled(rowIndex, columnInfo.getIndex(), isEnabled);
    }

    public void setColumnEnabled(String columnName, boolean isEnabled)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }
        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        for (int i = 0; i < this.listcell.getMaxRows() - 1; i++)
        {
            setCellEnabled(i + 1, columnInfo.getIndex(), isEnabled);
        }
    }

    public void setColumnHidden(String columnName, boolean isHidden)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }
        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        this.listcell.setColumnHidden(columnInfo.getIndex(),isHidden);
        this.listcell.setToolTipHidden(columnInfo.getIndex(),isHidden);
    }

    private void setCellReadOnly(int rowIndex, int columnIndex, boolean isReadOnly)
    {
        listcell.setCurrentRow(rowIndex);
        listcell.setCellReadOnly(columnIndex, isReadOnly);
    }

    public void setCellReadOnly(int rowIndex, String columnName, boolean isReadOnly)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }
        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        setCellReadOnly(rowIndex, columnInfo.getIndex(), isReadOnly);
    }

    public void setColumnReadOnly(String columnName, boolean isReadOnly)
    {
        if (!colunmInfoMap.containsKey(columnName))
        {
            return;
        }
        ListColumnInfo columnInfo = colunmInfoMap.get(columnName);
        for (int i = 0; i < this.listcell.getMaxRows() - 1; i++)
        {
            setCellReadOnly(i + 1, columnInfo.getIndex(), isReadOnly);
        }
    }

    public int getMaxRows()
    {
        return listcell.getMaxRows();
    }

    public int getActiveRow()
    {
        return listcell.getActiveRow();
    }

    public int getActiveCol()
    {
        return listcell.getActiveCol();
    }

    public void setHighlight(int row)
    {
        if (listcell == null)
        {
            return;
        }
        listcell.setHighlight(row);
    }

    public void addHighlight(int row)
    {
        if (listcell == null)
        {
            return;
        }
        listcell.addHighlight(row);
    }

    public void addHighlight(int row, ControlColorSupport color)
    {
        if (listcell == null)
        {
            return;
        }
        listcell.addHighlight(row, color);
    }

    public void clearRow()
    {
        if (listcell == null)
        {
            return;
        }
        listcell.clearRow();
    }

    public int[] getHighlights()
    {
        if (listcell == null)
        {
            return null;
        }
        return listcell.getHighlightLines();
    }

    public int getColumnIndex(String key)
    {
        return colunmInfoMap.get(key).getIndex();
    }

    public void remove(int rowNum)
    {
        listcell.removeRow(rowNum);
    }

    public void clearHighlights()
    {
        if (listcell == null)
        {
            return;
        }
        listcell.resetHighlight();
    }


    public void set(int rowNum, T entity) throws Exception
    {
        if (listcell == null)
        {
            return;
        }
        listcell.setCurrentRow(rowNum);

        if (entity == null)
        {
            return;
        }

        if (fields == null)
        {
            fields = entity.getClass().getDeclaredFields();
        }

        if (getterMap == null)
        {
            getterMap = createGetterMap(entity.getClass(), fields);
        }

        ToolTipHelper toolTip = new ToolTipHelper();
        boolean hasToolTip = false;

        for (int i = 0; i < fields.length; i++)
        {
            if (!getterMap.containsKey(fields[i].getName()))
            {
                continue;
            }

            if (!colunmInfoMap.containsKey(fields[i].getName()))
            {
                logger.info(String.format("no colunm info for field '%s' is found. field is ignored.", fields[i].getName()));
                continue;
            }

            try
            {
                ListColumnInfo info = colunmInfoMap.get(fields[i].getName());

                String t = fields[i].getType().toString();

                if (fields[i].getType() == PulldownParams.class) {
                    PulldownParams params = (PulldownParams) getterMap.get(fields[i].getName()).invoke(entity);
                    Map<String, String> availablePulItems = params.getAvailablePulItems();
                    Map<String, String> pulItems = params.getPulItems();
                    List<String> pulSeq = params.getPulSeq();

                    if (!pulItems.isEmpty()) {
                        List<PullDownItem> pulldownData = new ArrayList<PullDownItem>();

                        StringBuffer sb = new StringBuffer();
                        for (String value : pulSeq) {
                            if(availablePulItems.containsKey(value)) {
                                sb.append("\r\n");
                                sb.append(availablePulItems.get(value));
                            }
                        }
                        for(String value :pulSeq){
                            PullDownItem itm = new PullDownItem();
                            itm.setValue(value);
                            itm.setText(pulItems.get(value));

                            pulldownData.add(itm);
                        }
                        listcell.setPullDownItems(info.getIndex(), pulldownData);
                        listcell.setValue(info.getIndex(), params.getSelectValue());
                        if (info.isBalloon()) {
                            String title = "";
                            if (StringUtils.isNotBlank(info.getBalloonTitleResouce())) {
                                title = DispResourceMap.getText(info.getBalloonTitleResouce());
                            }
                            toolTip.add(title, sb.toString());
                            hasToolTip = true;
                        }
                    }
                }else if (fields[i].getType().toString().equals("boolean") || fields[i].getType() == Boolean.class)
                {
                    listcell.setChecked(info.getIndex(), (Boolean) getterMap.get(fields[i].getName()).invoke(entity));
                } else
                {
                    listcell.setValue(info.getIndex(), info.getFormatter().format(getterMap.get(fields[i].getName()).invoke(entity)));
                    if (info.isBalloon())
                    {
                        toolTip.add(DispResourceMap.getText(info.getBalloonTitleResouce()), info.getFormatter().format(getterMap.get(fields[i].getName()).invoke(entity)));
                        hasToolTip = true;
                    }
                }
            }
            catch (IllegalAccessException e)
            {

            }
            catch (InvocationTargetException e)
            {

            }
        }
        if (hasToolTip)
        {
            listcell.setToolTip(rowNum, toolTip.getText());
        }
    }

    public void add(T entity) throws Exception
    {
        if (listcell == null)
        {
            return;
        }
        listcell.addRow();
        int rowNum = listcell.getMaxRows() - 1;

        set(rowNum, entity);
    }

    public void addAll(List<T> entityList) throws Exception
    {
        if (entityList == null || entityList.size() == 0)
        {
            return;
        }

        for (T entity : entityList)
        {
            add(entity);
        }
    }

    public void bind(List<T> entityList) throws Exception
    {
        listcell.clearRow();
        addAll(entityList);
    }

    private Map<String, Method> createGetterMap(Class clazz, Field[] fields)
    {
        Map<String, Method> getterMap = new HashMap<String, Method>();

        for (int i = 0; i < fields.length; i++)
        {
            Method getter = getGetter(clazz, fields[i].getName());

            if (getter == null)
            {
                logger.info(String.format("no getter for field '%s' is found. getting field is ignored.", fields[i].getName()));
                continue;
            }

            if (!Modifier.isPublic(getter.getModifiers()))
            {
                logger.info(String.format("getter for field '%s' is not declared public. getting field is ignored.", fields[i].getName()));
                continue;
            }

            getterMap.put(fields[i].getName(), getter);
        }

        return getterMap;
    }

    private Method getGetter(Class clazz, String fieldName)
    {
        Method getter = null;
        try
        {
            getter = clazz.getMethod(fieldName);
        }
        catch (NoSuchMethodException e)
        {
        }

        if (getter == null)
        {
            String methodName = "get" + String.valueOf(Character.toUpperCase(fieldName.charAt(0)));
            if (fieldName.length() > 1)
            {
                methodName += fieldName.substring(1);
            }

            try
            {
                getter = clazz.getMethod(methodName);
            }
            catch (NoSuchMethodException e)
            {
            }
        }

        if (getter == null)
        {
            String methodName = "is" + String.valueOf(Character.toUpperCase(fieldName.charAt(0)));
            if (fieldName.length() > 1)
            {
                methodName += fieldName.substring(1);
            }

            try
            {
                getter = clazz.getMethod(methodName);
            }
            catch (NoSuchMethodException e)
            {
            }
        }

        return getter;
    }

    private Map<String, Method> createSetterMap(Class clazz, Field[] fields)
    {
        Map<String, Method> setterMap = new HashMap<String, Method>();

        for (int i = 0; i < fields.length; i++)
        {
            Method setter = getSetter(clazz, fields[i].getName(), fields[i].getType());

            if (setter == null)
            {
                logger.info(String.format("no setter for field '%s' is found. setting field is ignored.", fields[i].getName()));
                continue;
            }

            if (!Modifier.isPublic(setter.getModifiers()))
            {
                logger.info(String.format("setter for field '%s' is not declared public. setting field is ignored.", fields[i].getName()));
                continue;
            }

            setterMap.put(fields[i].getName(), setter);
        }

        return setterMap;
    }

    private Method getSetter(Class clazz, String fieldName, Class<?> type)
    {
        Method setter = null;

        if (setter == null)
        {
            String methodName = "set" + String.valueOf(Character.toUpperCase(fieldName.charAt(0)));
            if (fieldName.length() > 1)
            {
                methodName += fieldName.substring(1);
            }

            try
            {
                setter = clazz.getMethod(methodName, type);
            }
            catch (NoSuchMethodException e)
            {
            }
        }
        return setter;
    }

    public List<T> getAll(Class<T> clazz) throws Exception
    {
        return getAll(clazz, new RowFilter<T>()
        {
            public boolean filte(T entity)
            {
                return true;
            }
        });
    }

    public List<T> getAll(Class<T> clazz, RowFilter<T> filter) throws Exception
    {
        List<T> list = new ArrayList<T>();
        for (int i = 1; i < listcell.getMaxRows(); i++)
        {
            if (filter.filte(get(i, clazz)))
            {
                list.add(this.get(i, clazz));
            }
        }
        return list;
    }


    public T get(int index, Class<T> clazz) throws Exception
    {
        T entity = null;
        try
        {
            Constructor ct = clazz.getConstructor();
            entity = (T) ct.newInstance();
        }
        catch (NoSuchMethodException e)
        {
            logger.error(String.format("no default constructor for class '%s'", clazz.getName()));
        }
        catch (InvocationTargetException e)
        {
        }
        catch (IllegalAccessException e)
        {
        }
        catch (InstantiationException e)
        {
        }

        if (entity == null)
        {
            return null;
        }

        if (fields == null)
        {
            fields = entity.getClass().getDeclaredFields();
        }

        if (setterMap == null)
        {
            setterMap = createSetterMap(entity.getClass(), fields);
        }

        listcell.setCurrentRow(index);

        for (int i = 0; i < fields.length; i++)
        {
            if (!setterMap.containsKey(fields[i].getName()))
            {
                continue;
            }

            if (!colunmInfoMap.containsKey(fields[i].getName()))
            {
                logger.info(String.format("no colunm info for field '%s' is found. field is ignored.", fields[i].getName()));
                continue;
            }

            try
            {
                ListColumnInfo info = colunmInfoMap.get(fields[i].getName());

                if(fields[i].getType() == PulldownParams.class)
                {
                    PulldownParams value = new PulldownParams();
                    value.setSelectValue(listcell.getValue(info.getIndex()));
                    setterMap.get(fields[i].getName()).invoke(entity, value);
                }
                else if (fields[i].getType().toString().equals("boolean") || fields[i].getType() == Boolean.class)
                {
                    boolean value = listcell.getChecked(info.getIndex());
                    setterMap.get(fields[i].getName()).invoke(entity, fields[i].getType().isPrimitive() ? value : Boolean.valueOf(value));
                } else if (fields[i].getType().isPrimitive())
                {
                    String value = listcell.getValue(info.getIndex());
                    if (fields[i].getType().toString().equals("int"))
                    {
                        setterMap.get(fields[i].getName()).invoke(entity, Integer.parseInt(info.getFormatter().unformat(value).toString()));
                    } else if (fields[i].getType().toString().equals("long"))
                    {
                        setterMap.get(fields[i].getName()).invoke(entity, Long.parseLong(info.getFormatter().unformat(value).toString()));
                    }
                } else
                {
//                    Class formatterClazz = Class.forName("aa");
//                    Constructor ct = formatterClazz.getConstructor();
//                    Object formatter = ct.newInstance();
//                    info.setFormatter((FieldFormatter)formatter);

                    String value = listcell.getValue(info.getIndex());
                    if (fields[i].getType() == BigDecimal.class)
                    {
                        Object v = info.getFormatter().unformat(value);
                        
                        if(v instanceof  BigDecimal)
                        {
                            setterMap.get(fields[i].getName()).invoke(entity, info.getFormatter().unformat(value));
                        }else if(v instanceof  String)
                        {
                            setterMap.get(fields[i].getName()).invoke(entity, new BigDecimal((String) info.getFormatter().unformat(value)));
                        }
                    } else
                    {

                        setterMap.get(fields[i].getName()).invoke(entity, fields[i].getType().cast(info.getFormatter().unformat(value)));
                    }
                }
            }
            catch (IllegalAccessException e)
            {
            }
            catch (InvocationTargetException e)
            {
            }
            catch (ClassCastException e)
            {
            }
//            catch (ClassNotFoundException e)
//            {
//            }
//            catch (NoSuchMethodException e)
//            {
//            }
//            catch (InstantiationException e)
//            {
//            }
        }
        return entity;
    }

    protected abstract Map<String, ListColumnInfo> getColunmInfos();
}
