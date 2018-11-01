package jp.co.daifuku.wms.test.query.listproxy;

import jp.co.daifuku.bluedog.ui.control.ScrollListCell;
import jp.co.daifuku.wms.exercise.util.list.*;

import java.util.HashMap;
import java.util.Map;

public class StudentListProxy extends ListProxy {

    public StudentListProxy(ScrollListCell listcell) {
        super(listcell);
    }

    @Override
    protected Map<String, ListColumnInfo> getColunmInfos() {
        Map<String, ListColumnInfo> map = new HashMap<String, ListColumnInfo>();
        int i=1;

        map.put("name", new ListColumnInfo(i++));
        map.put("number", new ListColumnInfo(i++));
        map.put("sex", new ListColumnInfo(i++));
        map.put("telephone", new ListColumnInfo(i++));
        map.put("fromDate", new ListColumnInfo(i++));
        map.put("fromTime", new ListColumnInfo(i++));
        map.put("toDate", new ListColumnInfo(i++));
        map.put("toTime", new ListColumnInfo(i++));
        map.put("major", new ListColumnInfo(i++));
        map.put("address", new ListColumnInfo(i++));
        map.put("hobby", new ListColumnInfo(i++));
        map.put("score", new ListColumnInfo(i++));
        map.put("grade", new ListColumnInfo(i++));
        return map;
    }

}
