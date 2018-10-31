package jp.co.daifuku.wms.exercise.util.xls;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-9
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public interface XLSRow<V>
{
    public Map<Integer,String> getRowMap(V v);
    
    public Map<Integer,String> getHeadMap();
}
