package jp.co.daifuku.wms.exercise.util.xls;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-10
 * Time: 上午9:10
 * To change this template use File | Settings | File Templates.
 */
public class XLSReturnObj
{
    private String path;
    private int result = 0;
    private long totalCount = 0;

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }
}
