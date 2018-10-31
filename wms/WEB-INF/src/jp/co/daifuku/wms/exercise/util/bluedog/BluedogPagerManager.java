package jp.co.daifuku.wms.exercise.util.bluedog;

import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.exercise.util.common.Const;
import jp.co.daifuku.wms.exercise.util.pager.BasePagerReturnObj;

/**
 * Created by IntelliJ IDEA.
 * User: lenovo
 * Date: 2010-11-10
 * Time: 10:50:30
 * To change this template use File | Settings | File Templates.
 */
public class BluedogPagerManager
{
    private Pager[] pagers;
    private NumberTextBox pageNo;

    public BluedogPagerManager(Pager... pagers)
      {
          this(null, pagers);
      }

    public BluedogPagerManager(NumberTextBox pageNo, Pager... pagers)
    {
        this.pageNo = pageNo;
        this.pagers = pagers;
    }

    public void setValue(BasePagerReturnObj returnObj)
      {
            for (int i = 0; i < pagers.length; i++)
            {
                  pagers[i].setIndex((int)returnObj.getCurrentBeginIndex() + 1);
                  pagers[i].setPage((int)returnObj.getCountPerPage());
                  pagers[i].setMax((int)returnObj.getTotalCount());
            }

//          if(pageNo!=null)
//          {
//              pageNo.setInt((int) (returnObj.getCurrentBeginIndex()/returnObj.getCountPerPage() + 1));
//          }
      }

      public void init()
      {
            for (int i = 0; i < pagers.length; i++)
            {
                  pagers[i].setIndex(0);
                  pagers[i].setPage(Const.COUNT_PER_PAGE);
                  pagers[i].setMax(0);
            }
      }
}
