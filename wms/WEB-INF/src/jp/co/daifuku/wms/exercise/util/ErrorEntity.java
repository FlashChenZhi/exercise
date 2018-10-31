package jp.co.daifuku.wms.exercise.util;

/**
 * Created by zhangming on 2015/3/9.
 */
public class ErrorEntity {
    int error;
    Object[] objs;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }
}
