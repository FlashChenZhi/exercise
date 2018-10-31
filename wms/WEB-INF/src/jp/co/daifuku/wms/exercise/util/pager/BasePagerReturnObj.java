package jp.co.daifuku.wms.exercise.util.pager;

/**
 * Author: Zhouyue
 * Date: 2009-6-16
 * Time: 15:31:23
 * Copyright Daifuku Shanghai Ltd.
 */
public class BasePagerReturnObj
{
      private long totalCount;
      private long currentBeginIndex;
      private long countPerPage;

      public void setTotalCount(long totalCount)
      {
            this.totalCount = totalCount;
      }

      public void setCurrentBeginIndex(long currentBeginIndex)
      {
            this.currentBeginIndex = currentBeginIndex;
      }

      public void setCountPerPage(long countPerPage)
      {
            this.countPerPage = countPerPage;
      }

      public long getTotalCount()
      {
            return totalCount;
      }

      public long getCurrentBeginIndex()
      {
            return currentBeginIndex;
      }

      public long getCountPerPage()
      {
            return countPerPage;
      }
}
