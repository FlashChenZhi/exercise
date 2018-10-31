package jp.co.daifuku.wms.exercise.util.pager;


import jp.co.daifuku.bluedog.ui.control.Message;

/**
 * Created by IntelliJ IDEA.
 * User: ZhangMing
 * Date: 2010-4-15
 * Time: 17:06:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class UIPager<U, V>
{
    protected Pager<U,V> pager;
    
    public UIPager()
    {
        setPager();
    }
    
    public long getTotalCount(U vo) throws Exception
    {
        return pager.totalCount(vo);
    }

    protected abstract void setPager();


    public PagerReturnObj<V> next(int currentBeginIndex, int countPerPage, U vo) throws Exception
    {
        return convertGeneric2Customized(pager.next(currentBeginIndex, countPerPage, vo));
    }

    public PagerReturnObj<V> previous(int currentBeginIndex, int countPerPage, U vo) throws Exception
    {
        return convertGeneric2Customized(pager.previous(currentBeginIndex, countPerPage, vo));
    }

    public PagerReturnObj<V> last(int countPerPage, U vo) throws Exception
    {
        return convertGeneric2Customized(pager.last(countPerPage, vo));
    }

    public PagerReturnObj<V> first(int countPerPage, U vo) throws Exception
    {
        return convertGeneric2Customized(pager.first(countPerPage, vo));
    }

    public PagerReturnObj<V> current(int currentBeginIndex, int countPerPage, U vo) throws Exception
    {
        return convertGeneric2Customized(pager.current(currentBeginIndex, countPerPage, vo));
    }

    public PagerReturnObj<V> all(U vo,int maxCount,Message message) throws Exception
    {
        return convertGeneric2Customized(pager.all(vo,maxCount,message));
    }

    public PagerReturnObj<V> go(int pageNo,int countPerPage,U vo) throws Exception
    {
        return convertGeneric2Customized(pager.go(pageNo, countPerPage, vo));
    }

    public PagerReturnObj<V> convertGeneric2Customized(GenericPagerReturnObj returnObj)
    {
        PagerReturnObj<V> newReturnObj = new PagerReturnObj<V>();
        newReturnObj.setCountPerPage(returnObj.getCountPerPage());
        newReturnObj.setCurrentBeginIndex(returnObj.getCurrentBeginIndex());
        newReturnObj.setTotalCount(returnObj.getTotalCount());
        newReturnObj.setData(returnObj.getData());
        return newReturnObj;
    }

}
