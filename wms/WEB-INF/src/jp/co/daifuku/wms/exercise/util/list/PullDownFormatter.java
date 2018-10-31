package jp.co.daifuku.wms.exercise.util.list;

/**
 * Created by zhangming on 2015/7/23.
 */
public class PullDownFormatter implements FieldFormatter<PulldownParams> {
    @Override
    public String format(PulldownParams obj) throws Exception {
        return obj.getSelectValue();
    }

    @Override
    public PulldownParams unformat(String value) throws Exception {
        PulldownParams pulldownParams = new PulldownParams();
        pulldownParams.setSelectValue(value);
        return pulldownParams;
    }
}
