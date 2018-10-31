package jp.co.daifuku.wms.exercise;

/**
 * Created by IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-11
 * Time: 下午2:14
 * To change this template use File | Settings | File Templates.
 */
public enum Message
{
    SET_SUCCESS("9700001", "设定成功"),
    PRINT_SUCCESS("9700002", "打印成功"),
    INQUIRY_RESULT("9700003","有{0}条符合条件的数据"),
    RESULT_TOO_MUCH("9702047", "结果集为{0}条超过允许结果集最大值{1}不可打印或导出"),
    RESULT_ZERO("9702048", "结果集为空"),
    NULL_STORAGE_PLAN("9702049", "入库预约不存在"),
    EXISTS_PALLET("9702050", "托盘号已存在"),
    AUTO_PALLET("9702051", "托盘号为{0}的入库预约为整托预约，入库数量必须等于预定数{1}"),
    QTY_TOO_MUCH("9702052", "设定数量过大"),
    EXISTS_SERIAL_NO("9702053", "序列号已存在"),
    CAN_NOT_MIX_PALLET("9702054", "不能混板"),
    NOT_MATCH_STORAGE_PLAN("9702055", "托盘号或序列号关系和入库预约信息不符"),
    USED_IN_STORAGE_PLAN("9702056", "有入库信息已被未完成的入库预约使用"),
    NULL_STORAGE_INFO("9702057", "入库信息有误"),
    ONLY_ONE_PALLET_PER_SET("9702058", "每次只能设定一块托盘"),
    PLEASE_SELECT_SET_DATA("9702059", "请选择需要设定的数据"),
    ERROR_PALLET("9702060", "托盘状态不正确"),
    MORE_THAN_ONE_SERIAL_CANNOT_MODIFY("9702061", "多箱托盘不可修改在此修改数量，请至弹出窗口维护箱数据"),
    LOADING_STOCK_CANNOT_MODIFY("9702062", "托盘已绑定出库订单，不可维护"),
    EMPTY_PALLET_CANNOT_INPUT("9702063", "空托盘不可输入序列号"),
    EMPTY_PALLET_NO("9702064", "托盘号不可为空"),
    NOT_ENOUGH_STOCK("9702065", "有订单库存不足"),
    CHECK_STATION("9702066", "请选择站台"),
    ONLY_WHEN_OFFLINE("9702067", "请在离线时执行该操作"),
    PICKING_QTY_TOO_MUCH("9702068", "拣选数量过多"),
    SERIAL_PALLET_SELECT_SERIAL("9702069", "序列号管理托盘必须选择序列号"),
    NULL_LOCATION("9702070", "货位号不存在"),
    NULL_STOCK("9702071", "库存不存在"),
    ERROR_STOCK("9702072", "库存状态不正确"),
    OCCUPIED_LOCATION("9702073","货位已被占用"),
    EXISTS_ARTICLE("9702074", "物料编码已存在"),
    NULL_ARTICLE("9702075", "物料编码不存在"),
    ARTICLE_IN_USE("9702076", "物料编码正在使用中"),
    ERROR_BAY("9702077", "输入Bay范围有误"),
    DATA_CHANGED("9702078", "数据发生变化"),
    INQUIRY_ERROR("9702079", "查询时发生错误"),
    SET_ERROR("9702080", "设定时发生错误"),
    NULL_SHIPPER("9702081", "货主不存在"),
    SHORTAGE_QTY_TOO_MUCH("9702082", "缺货数量过多");


    private String code;
    private String message;


    public String getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    Message(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
