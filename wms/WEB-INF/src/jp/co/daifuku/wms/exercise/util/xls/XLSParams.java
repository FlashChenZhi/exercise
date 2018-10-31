package jp.co.daifuku.wms.exercise.util.xls;

import java.util.List;

/**
 * Created by zhangming on 2014/12/16.
 */
public class XLSParams<V> {
    private List<V> list;
    private XLSRow<V> xlsRow;

    public List<V> getList() {
        return list;
    }

    public void setList(List<V> list) {
        this.list = list;
    }

    public XLSRow<V> getXlsRow() {
        return xlsRow;
    }

    public void setXlsRow(XLSRow<V> xlsRow) {
        this.xlsRow = xlsRow;
    }
}
