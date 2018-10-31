package jp.co.daifuku.wms.exercise.util.list;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangming on 2014/12/29.
 */
public class PulldownParams {
    private Map<String,String> availablePulItems;
    private Map<String,String> pulItems;
    private List<String> pulSeq;
    private String selectValue;

    public Map<String, String> getAvailablePulItems() {
        return availablePulItems;
    }

    public void setAvailablePulItems(Map<String, String> availablePulItems) {
        this.availablePulItems = availablePulItems;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }

    public Map<String, String> getPulItems() {
        return pulItems;
    }

    public void setPulItems(Map<String, String> pulItems) {
        this.pulItems = pulItems;
    }

    public List<String> getPulSeq() {
        return pulSeq;
    }

    public void setPulSeq(List<String> pulSeq) {
        this.pulSeq = pulSeq;
    }
}
