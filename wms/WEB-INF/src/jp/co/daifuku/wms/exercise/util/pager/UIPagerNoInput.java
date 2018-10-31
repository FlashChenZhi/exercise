package jp.co.daifuku.wms.exercise.util.pager;


import jp.co.daifuku.bluedog.ui.control.Message;

/**
 * Created by IntelliJ IDEA.
 * User: ZhangMing
 * Date: 2010-4-16
 * Time: 11:16:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class UIPagerNoInput<T>
{
    protected Pager<T,T> pager;

    public UIPagerNoInput()
    {
        setPager();
    }

    protected abstract void setPager();


    public PagerReturnObj<T> next(int currentBeginIndex, int countPerPage) throws Exception
    {
        return convertGeneric2Customized(pager.next(currentBeginIndex, countPerPage,null));
    }

    public PagerReturnObj<T> previous(int currentBeginIndex, int countPerPage) throws Exception
    {
        return convertGeneric2Customized(pager.previous(currentBeginIndex, countPerPage, null));
    }

    public PagerReturnObj<T> last(int countPerPage) throws Exception
    {
        return convertGeneric2Customized(pager.last(countPerPage, null));
    }

    public PagerReturnObj<T> first(int countPerPage) throws Exception
    {
        return convertGeneric2Customized(pager.first(countPerPage, null));
    }

    public PagerReturnObj<T> current(int currentBeginIndex, int countPerPage) throws Exception
    {
        return convertGeneric2Customized(pager.current(currentBeginIndex, countPerPage, null));
    }

    public PagerReturnObj<T> all(int maxCount,Message message) throws Exception
    {
        return convertGeneric2Customized(pager.all(null,maxCount,message));
    }

    public PagerReturnObj<T> go(int pageNo,int countPerPage) throws Exception
    {
        return convertGeneric2Customized(pager.go(pageNo, countPerPage, null));
    }

    public PagerReturnObj<T> convertGeneric2Customized(GenericPagerReturnObj returnObj)
    {
        PagerReturnObj<T> newReturnObj = new PagerReturnObj<T>();
        newReturnObj.setCountPerPage(returnObj.getCountPerPage());
        newReturnObj.setCurrentBeginIndex(returnObj.getCurrentBeginIndex());
        newReturnObj.setTotalCount(returnObj.getTotalCount());
        newReturnObj.setData(returnObj.getData());
        return newReturnObj;
    }
}
