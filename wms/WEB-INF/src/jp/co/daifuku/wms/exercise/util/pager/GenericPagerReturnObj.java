package jp.co.daifuku.wms.exercise.util.pager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2009-6-15
 * Time: 12:38:34
 * To change this template use File | Settings | File Templates.
 */
public class GenericPagerReturnObj<T> extends BasePagerReturnObj
{
    private List<T> data = new ArrayList<T>();

    public List<T> getData()
    {
        return data;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }

}
