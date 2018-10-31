package jp.co.daifuku.wms.exercise.util.pager;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhangMing
 * Date: 2010-4-15
 * Time: 16:39:46
 * To change this template use File | Settings | File Templates.
 */
public class PagerReturnObj<T>  extends BasePagerReturnObj
{
    private List<T> data;

    public List<T> getData()
    {
        return data;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }
}
