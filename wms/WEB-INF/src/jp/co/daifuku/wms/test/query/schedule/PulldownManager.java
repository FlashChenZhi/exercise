package jp.co.daifuku.wms.test.query.schedule;

import jp.co.daifuku.bluedog.ui.control.LinkedPullDown;
import jp.co.daifuku.bluedog.ui.control.PullDown;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.test.query.entity.StudentVo;
import org.apache.commons.lang.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-6
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
public class PulldownManager
{
    public static void display(PullDown pul)
    {
        pul.clearItem();
        pul.addItem(CarryInfo.ACCESS_NG_FLAG_OK, null, "Java", true);
        pul.addItem(CarryInfo.ACCESS_NG_FLAG_NG, null, "C#", false);
        pul.addItem(CarryInfo.AISLE_SEARCH_DESCENDING, null, "PHP", false);
        pul.addItem(CarryInfo.CANCEL_REQUEST_DROP, null, "React", false);
        pul.addItem(CarryInfo.BERTH_STATUS_FLAG_COMPLETION, null, "SQL", false);
        pul.addItem(CarryInfo.STATUS_FLAG_MOVE_STORAGE_CANCEL, null, "JavaScript", false);
    }

    public static void fillSortCriteria2(PullDown pul)
    {
        pul.clearItem();
        pul.addItem("1", null, "母工程开始日", true);
        pul.addItem("2", null, "完了预定日", false);
        pul.addItem("3", null, "品名1", false);
        pul.addItem("4", null, "出库指示No", false);
        pul.addItem("5", null, "区域", false);
        pul.addItem("6", null, "订单No", false);
        pul.addItem("7", null, "PR No", false);
        pul.addItem("8", null, "重量", false);
    }

    public static void fillCompetence(PullDown pul)
    {
        pul.clearItem();
        pul.addItem("admin", null, "admin", true);
        pul.addItem("leader", null, "leader", false);
        pul.addItem("worker1", null, "worker1", false);
        pul.addItem("worker2", null, "worker2", false);
    }

    public static void fillRetrievalPlace(PullDown pul)
    {
        pul.clearItem();
        pul.addItem(StringUtils.EMPTY, null, StringUtils.EMPTY, true);
        pul.addItem("1204", null, "1层台车(组装)", false);
        pul.addItem("1203", null, "1层台车(镀金)", false);
        pul.addItem("1206", null, "1层码盘机(压铸)", false);
        pul.addItem("1202", null, "1层码盘机(组装)", false);
        pul.addItem("2202", null, "2层台车", false);
        pul.addItem("1251", null, "1层材料准备设备", false);
        pul.addItem("2204", null, "M2", false);
        pul.addItem("2200", null, "2层组装线", false);
    }

//    public static void fillZone(PullDown pul, boolean all) throws Exception
//    {
//        pul.clearItem();
//
//        StudentService service = new StudentService();
//
//        List<StudentVo> list = service.getZone();
//
//        if (all)
//        {
//            pul.addItem(StringUtils.EMPTY, null, "全部", true);
//        }
//        boolean selected = !all;
//
//        for (StudentVo vo : list)
//        {
//            pul.addItem(String.valueOf(vo.getZoneCode()), null, String.valueOf(vo.getZoneName()), selected);
//            selected = false;
//        }
//    }

//    public static void fillOrderName(PullDown pul)
//    {
//        pul.clearItem();
//        pul.addItem(StringUtils.EMPTY, null, StringUtils.EMPTY, true);
//
//        ReportMapping mapping = new ReportMapping();
//
//        Map<String, String> map = mapping.getReportMap();
//
//        for (String type : map.keySet())
//        {
//            pul.addItem(type, null, map.get(type), false);
//        }
//    }

    public static void fillRetrievalPlace(PullDown pul, int type)
    {
        pul.clearItem();

        switch (type)
        {
            case 4:
                pul.addItem("1207", null, "1层No Read排出线", true);
                pul.addItem("2203", null, "2层No Read排出线", false);
                pul.addItem("1204", null, "1层台车(组装)", false);
                pul.addItem("1203", null, "1层台车(镀金)", false);
                pul.addItem("1206", null, "1层码盘机(压铸)", false);
                pul.addItem("1202", null, "1层码盘机(组装)", false);
                pul.addItem("2202", null, "2层台车", false);
                break;
            case 3:
                pul.addItem("1201", null, "1层拣选St", true);
                pul.addItem("2201", null, "2层拣选St1", false);
                pul.addItem("2207", null, "2层拣选St2", false);
                break;
            default:
                pul.addItem("1207", null, "1层No Read排出线", true);
                pul.addItem("2203", null, "2层No Read排出线", false);
        }
    }

//    public static void fillReceiverDistinguish(PullDown pul)
//    {
//        pul.clearItem();
//
//        pul.addItem(ReceiverDistinguish.ALL, null, "全部", true);
//        pul.addItem(ReceiverDistinguish.SKU_MASTER, null, "物料主数据(TR600)", false);
//        pul.addItem(ReceiverDistinguish.GUANLIANG_ZHISHI_DATA, null, "关联指示数据(TRX00)", false);
//        pul.addItem(ReceiverDistinguish.MUDIDI_ZHISHI_DATA, null, "目的地指示数据(TRY00)", false);
//        pul.addItem(ReceiverDistinguish.RETRIEVAL_ZHISHI_DATA, null, "出库指示数据(TRZ00)", false);
//        pul.addItem(ReceiverDistinguish.RETRIEVAL_CANCEL_DATA, null, "出库取消数据(TPG00)", false);
//        pul.addItem(ReceiverDistinguish.SYSTEM_MANAGER_DATA, null, "系统管理数据(TPE00)", false);
//    }
//
//    public static void fillCommunicateDistinguish(PullDown pul)
//    {
//        pul.clearItem();
//
//        pul.addItem(CommunicateDistinguish.ALL, null, "全部", true);
//        pul.addItem(CommunicateDistinguish.SEND, null, "送信", false);
//        pul.addItem(CommunicateDistinguish.RECEIVER, null, "收信", false);
//        pul.addItem(CommunicateDistinguish.SKU_MASTER, null, "物料主数据(TR600)", false);
//        pul.addItem(CommunicateDistinguish.INVENTORY_INFO_REPORT, null, "库存信息报告(TRV00)", false);
//        pul.addItem(CommunicateDistinguish.GUANLIAN_ZHISHI_DATA, null, "关联指示数据(TRX00)", false);
//        pul.addItem(CommunicateDistinguish.WEIGHT_REPORT_DATA, null, "计量报告数据(TRW00)", false);
//        pul.addItem(CommunicateDistinguish.MUDIDI_ZHISHI_DATA, null, "目的地指示数据(TRY00)", false);
//        pul.addItem(CommunicateDistinguish.RETRIEVAL_ZHISHI_DATA, null, "出库指示数据(TRZ00)", false);
//        pul.addItem(CommunicateDistinguish.RETRIEVAL_HISTORY_DATA, null, "出库实绩数据(TPA00)", false);
//        pul.addItem(CommunicateDistinguish.INVENTORY_CHAYI_DATA, null, "库存差异数据(TPB00)", false);
//        pul.addItem(CommunicateDistinguish.RETRIEVAL_CANCEL_DATA, null, "出库取消数据(TPG00)", false);
//        pul.addItem(CommunicateDistinguish.RETRIEVAL_CANCEL_HISTORY_DATA, null, "出库取消实绩数据(TPH00)", false);
//        pul.addItem(CommunicateDistinguish.SYSTEM_MANAGER_DATA, null, "系统管理数据(TPE00)", false);
//        pul.addItem(CommunicateDistinguish.SHOUXIN_ERROR_DATA, null, "受信错误数据(TPF00)", false);
//        pul.addItem(CommunicateDistinguish.BUCKET_BAG_GUANLIAN_ZHISHI_DATA, null, "料箱塑料袋关联指示数据(TP700)", false);
//    }

    public static void fillDieMovePlace(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1206", null, "1层码盘机(压铸)", true);
        pul.addItem("1202", null, "1层码盘机(组装)", false);
    }

    public static void fillMovePlace(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1203", null, "1层台车(镀金)", true);
        pul.addItem("1202", null, "1层码盘机(组装)", false);
        pul.addItem("2202", null, "2层台车", false);
        pul.addItem("1206", null, "1层码盘机(压铸)", false);

//        pul.addItem("1202", null, "1层码盘机(组装)", false);
    }

    public static void fillJobDistinguish(PullDown pul)
    {
        pul.clearItem();

//        pul.addItem(StringUtils.EMPTY, null, "全部", true);
//        pul.addItem(InOutResultType.STORAGE, null, "入库", false);
//        pul.addItem(InOutResultType.RETRIEVAL, null, "出库", false);
//        pul.addItem(InOutResultType.MOVE, null, "移库", false);
//        pul.addItem(InOutResultType.MAINTENANCE_SUB, null, "维护减", false);
//        pul.addItem(InOutResultType.MAINTENANCE_ADD, null, "维护增", false);
//        pul.addItem(InOutResultType.MOVE_SUB, null, "移动减", false);
//        pul.addItem(InOutResultType.MOVE_ADD, null, "移动增", false);
//        pul.addItem(InOutResultType.PICKING_SUB, null, "拣选减", false);
//        pul.addItem(InOutResultType.PICKING_ADD, null, "拣选增", false);
//        pul.addItem(InOutResultType.MERGING_SUB, null, "合箱减", false);
//        pul.addItem(InOutResultType.MERGING_ADD, null, "合箱增", false);
    }

    public static void fillFinishLoginStation(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1111", null, "1111", false);
        pul.addItem("1121", null, "1121", false);
        pul.addItem("1151", null, "1151", false);
        pul.addItem("1156", null, "1156", false);
        pul.addItem("2141", null, "2141", false);
        pul.addItem("2161", null, "2161", false);
        pul.addItem("2163", null, "2163", false);
        pul.addItem("2164", null, "2164", false);
        pul.addItem("2165", null, "2165", false);
        pul.addItem("2171", null, "2171", false);
        pul.addItem("2181", null, "2181", false);
    }

    public static void fillWarehousePutawayMouth(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1101", null, "1101", false);
        pul.addItem("1102", null, "1102", false);
    }

    public static void fillWarehouseRetrievalMouth(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1201", null, "1201", false);
        pul.addItem("1202", null, "1202", false);
        pul.addItem("1203", null, "1203", false);
        pul.addItem("1204", null, "1204", false);
        pul.addItem("1205", null, "1205", false);
        pul.addItem("1206", null, "1206", false);
        pul.addItem("1207", null, "1207", false);
        pul.addItem("1208", null, "1208", false);
        pul.addItem("1251", null, "1251", false);
        pul.addItem("2201", null, "2201", false);
        pul.addItem("2202", null, "2202", false);
        pul.addItem("2203", null, "2203", false);
        pul.addItem("2204", null, "2204", false);
        pul.addItem("2207", null, "2207", false);
        pul.addItem("2211", null, "2211", false);
        pul.addItem("2212", null, "2212", false);
        pul.addItem("2213", null, "2213", false);
        pul.addItem("2214", null, "2214", false);
        pul.addItem("2215", null, "2215", false);
        pul.addItem("2216", null, "2216", false);
        pul.addItem("2217", null, "2217", false);
        pul.addItem("2218", null, "2218", false);
        pul.addItem("1231", null, "1231", false);
        pul.addItem("1236", null, "1236", false);
        pul.addItem("1238", null, "1238", false);
        pul.addItem("1223", null, "1223", false);
        pul.addItem("1224", null, "1224", false);
        pul.addItem("1225", null, "1225", false);
        pul.addItem("1227", null, "1227", false);
        pul.addItem("1212", null, "1212", false);
        pul.addItem("1239", null, "1239", false);
        pul.addItem("1264", null, "1264", false);
        pul.addItem("2241", null, "2241", false);
        pul.addItem("2244", null, "2244", false);
        pul.addItem("2246", null, "2246", false);
        pul.addItem("2248", null, "2248", false);
        pul.addItem("2179", null, "2179", false);
        pul.addItem("2186", null, "2186", false);
        pul.addItem("2281", null, "2281", false);
        pul.addItem("2282", null, "2282", false);
        pul.addItem("2283", null, "2283", false);
        pul.addItem("2284", null, "2284", false);
        pul.addItem("2285", null, "2285", false);
        pul.addItem("2286", null, "2286", false);
        pul.addItem("2287", null, "2287", false);
        pul.addItem("2288", null, "2288", false);
    }

    public static void fillWarehousePickingStation(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1231", null, "1231", false);
        pul.addItem("2241", null, "2241", false);
        pul.addItem("2246", null, "2246", false);
    }

    public static void fillJobZoneByNoPutawayJob(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1", null, "1层 压铸保管货架", true);
        pul.addItem("2", null, "1层 压铸保管区域", false);
        pul.addItem("3", null, "2层 组装BZ(托盘货架)", false);
        pul.addItem("4", null, "2层 组装BZ(塑料箱货架)", false);
        pul.addItem("5", null, "2层 ET品・涂装A品BZ(托盘货架)", false);
        pul.addItem("6", null, "2层 ET品・涂装A品BZ(塑料箱货架)", false);
    }

    public static void fillJobZoneByNoRetrievalJob(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1", null, "1层 压铸保管货架", true);
        pul.addItem("2", null, "1层 压铸保管区域", false);
        pul.addItem("3", null, "2层 组装BZ(托盘货架)", false);
        pul.addItem("4", null, "2层 组装BZ(塑料箱货架)", false);
        pul.addItem("5", null, "2层 ET品・涂装A品BZ(托盘货架)", false);
        pul.addItem("6", null, "2层 ET品・涂装A品BZ(塑料箱货架)", false);
    }

    public static void fillJobZoneByBag(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1", null, "1层 镀金完了小批次品临时保管(塑料袋)", true);
        pul.addItem("2", null, "2层 组装完了小批次品临时保管(塑料袋)", false);
        pul.addItem("3", null, "1层 自动仓库拣选St临时保管(塑料袋)", false);
        pul.addItem("4", null, "2层 自动仓库拣选St临时保管(塑料袋)", false);
    }

    public static void fillJobZoneByBucket(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1", null, "1层 镀金完了小批次品临时保管(塑料箱)", true);
        pul.addItem("2", null, "2层 组装完了小批次品临时保管(塑料箱)", false);
        pul.addItem("3", null, "1层 自动仓库拣选St临时保管(塑料箱)", false);
        pul.addItem("4", null, "2层 自动仓库拣选St临时保管(塑料箱)", false);
    }

    public static void fillWarehouseJobStation(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("1201", null, "1层拣选St", true);
        pul.addItem("2201", null, "2层拣选St1", false);
        pul.addItem("2207", null, "2层拣选St2", false);
    }

    public static void fillPickingStation(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, StringUtils.EMPTY, true);
        pul.addItem("1201", null, "1层拣选St", false);
        pul.addItem("2201", null, "2层拣选St1", false);
        pul.addItem("2207", null, "2层拣选St2", false);
    }

    public static void fillHour(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("24", null, "24", true);
        pul.addItem("23", null, "23", false);
        pul.addItem("22", null, "22", false);
        pul.addItem("21", null, "21", false);
        pul.addItem("20", null, "20", false);
        pul.addItem("19", null, "19", false);
        pul.addItem("18", null, "18", false);
        pul.addItem("17", null, "17", false);
        pul.addItem("16", null, "16", false);
        pul.addItem("15", null, "15", false);
        pul.addItem("14", null, "14", false);
        pul.addItem("13", null, "13", false);
        pul.addItem("12", null, "12", false);
        pul.addItem("11", null, "11", false);
        pul.addItem("10", null, "10", false);
        pul.addItem("09", null, "09", false);
        pul.addItem("08", null, "08", false);
        pul.addItem("07", null, "07", false);
        pul.addItem("06", null, "06", false);
        pul.addItem("05", null, "05", false);
        pul.addItem("04", null, "04", false);
        pul.addItem("03", null, "03", false);
        pul.addItem("02", null, "02", false);
        pul.addItem("01", null, "01", false);
    }

    public static void fillMinute(PullDown pul)
    {
        pul.clearItem();

        pul.addItem("00", null, "00", true);
        pul.addItem("10", null, "10", false);
        pul.addItem("20", null, "20", false);
        pul.addItem("30", null, "30", false);
        pul.addItem("40", null, "40", false);
        pul.addItem("50", null, "50", false);
    }

    public static void fillPalletTypeAndDepartment(PullDown pul, LinkedPullDown linkPul) throws Exception
    {
        linkPul.clearItem();

        pul.addItem("A", null, "A:WOODEN PALLET", true);
        pul.addItem("B", null, "B:PLASTIC PALLET", false);
        pul.addItem("C", null, "C:OTHER PALLET", false);

        linkPul.addItem("A", "1", null, "1:压铸 510", true);
        linkPul.addItem("A", "2", null, "2:冲压 520", false);
        linkPul.addItem("A", "3", null, "3:组装 53*", false);
        linkPul.addItem("A", "4", null, "4:涂装 540", false);
        linkPul.addItem("A", "5", null, "5:镀金 55*", false);
        linkPul.addItem("A", "6", null, "6:组立脱屑 560", false);
        linkPul.addItem("A", "9", null, "9:外注包胶 590", false);
        linkPul.addItem("B", "1", null, "1:压铸 510", true);
        linkPul.addItem("B", "2", null, "2:冲压 520", false);
        linkPul.addItem("B", "3", null, "3:组装 53*", false);
        linkPul.addItem("B", "4", null, "4:涂装 540", false);
        linkPul.addItem("B", "5", null, "5:镀金 55*", false);
        linkPul.addItem("B", "6", null, "6:组立脱屑 560", false);
        linkPul.addItem("B", "9", null, "9:外注包胶 590", false);
        linkPul.addItem("C", "1", null, "1:压铸 510", true);
        linkPul.addItem("C", "2", null, "2:冲压 520", false);
        linkPul.addItem("C", "3", null, "3:组装 53*", false);
        linkPul.addItem("C", "4", null, "4:涂装 540", false);
        linkPul.addItem("C", "5", null, "5:镀金 55*", false);
        linkPul.addItem("C", "6", null, "6:组立脱屑 560", false);
        linkPul.addItem("C", "9", null, "9:外注包胶 590", false);

        pul.addChild(linkPul);
    }

//    public static void fillPrinter(PullDown pul, boolean all) throws Exception
//    {
//        pul.clearItem();
//
//        PrinterSubstituteSettingLoginService service = new PrinterSubstituteSettingLoginService();
//
//        List<PrinterVo> list = service.getPrinter(PrinterType.LASER);
//
//        boolean selected = all;
//
//        for (PrinterVo vo : list)
//        {
//            pul.addItem(vo.getPrinterName(), null, vo.getPrinterName(), selected);
//            selected = false;
//        }
//    }
//
//    public static void fillLabelPrinter(PullDown pul, boolean all) throws Exception
//    {
//        pul.clearItem();
//
//        PrinterSubstituteSettingLoginService service = new PrinterSubstituteSettingLoginService();
//
//        List<PrinterVo> list = service.getPrinter(PrinterType.MOBILE);
//
//        boolean selected = all;
//
//        for (PrinterVo vo : list)
//        {
//            pul.addItem(vo.getPrinterName(), null, vo.getPrinterName(), selected);
//            selected = false;
//        }
//    }
//
//    public static void fillSatoPrinter(PullDown pul, boolean all) throws Exception
//    {
//        pul.clearItem();
//
//        PrinterSubstituteSettingLoginService service = new PrinterSubstituteSettingLoginService();
//
//        List<PrinterVo> list = service.getPrinter(PrinterType.SATO);
//
//        boolean selected = all;
//
//        for (PrinterVo vo : list)
//        {
//            pul.addItem(vo.getPrinterName(), null, vo.getPrinterName(), selected);
//            selected = false;
//        }
//    }
//
//    public static void fillTerminalNo(PullDown pul) throws Exception
//    {
//        pul.clearItem();
//
//        SendGoodsInstructionBookAgainPrintService service = new SendGoodsInstructionBookAgainPrintService();
//
//        List<SendGoodsInstructionBookAgainPrintVo> list = service.getTerminalNoAndName();
//
//        boolean selected = true;
//
//        for (SendGoodsInstructionBookAgainPrintVo vo : list)
//        {
//            pul.addItem(vo.getTerminalNo(), null, vo.getTerminalName(), selected);
//            selected = false;
//        }
//    }
//
//    public static void fillAllStationNo(PullDown pul) throws Exception
//    {
//        pul.clearItem();
//        pul.addItem(StringUtils.EMPTY, null, "全部", true);
//        pul.addItem("9000", null, "9000", false);
//        pul.addItem("BZ", null, "BZ", false);
//        JobMaintenanceService service = new JobMaintenanceService();
//        List<String[]> list = service.getStations();
//        for (String[] strs : list)
//        {
//            pul.addItem(strs[0], null, strs[1], false);
//        }
//    }
//
//    public static void fillBankPul(PullDown pul) throws Exception
//    {
//        pul.clearItem();
//
//        ForbidLocationSettingService service = new ForbidLocationSettingService();
//
//        List<Integer> list = service.getBlanks();
//
//        boolean selected = true;
//
//        for (int bank : list)
//        {
//            pul.addItem(String.valueOf(bank), null, String.valueOf(bank), selected);
//            selected = false;
//        }
//    }
//
//    public static void fillAisle(PullDown pul) throws Exception
//    {
//        pul.clearItem();
//
//        RetrievalCancelService service = new RetrievalCancelService();
//
//        List<String> list = service.getAisleList();
//
//        boolean selected = true;
//
//        for (String aisle : list)
//        {
//            pul.addItem(aisle, null, aisle, selected);
//            selected = false;
//        }
//    }
//
//    public static void fillPrintCategory(PullDown pul) throws Exception
//    {
//        pul.clearItem();
//
//        pul.addItem(PrinterType.LASER, null, "LASER", true);
//        pul.addItem(PrinterType.MOBILE, null, "MOBILE", false);
//        pul.addItem(PrinterType.SATO, null, "SATO", false);
//    }

    public static void fillType(PullDown pul) throws Exception
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1", null, "MF", false);
        pul.addItem("2", null, "PF", false);
        pul.addItem("3", null, "VF", false);
    }

    public static void fillSeqCondition(PullDown pul) throws Exception
    {
        pul.clearItem();

        pul.addItem("1", null, "line2", true);
        pul.addItem("2", null, "出库指示No", false);
        pul.addItem("3", null, "PRNo", false);
        pul.addItem("4", null, "生产开始日", false);
        pul.addItem("5", null, "出库设定时间", false);
        pul.addItem("6", null, "进度(升序)", false);
        pul.addItem("7", null, "进度(降序)", false);
    }

    public static void fillExportStation(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1239", null, "1239", false);
        pul.addItem("2248", null, "2248", false);
        pul.addItem("2289", null, "2289", false);
        pul.addItem("2186", null, "2186", false);
    }

    public static void fillExportType(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1", null, "NO READ", false);
        pul.addItem("2", null, "NO PLAN", false);
    }

    public static void fillPriority(PullDown pul)
    {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("0", null, "无", false);
        pul.addItem("1", null, "普通", false);
        pul.addItem("2", null, "组出库中", false);
        pul.addItem("3", null, "优先", false);
    }

    public static void fillSystem(PullDown pul) {
        pul.clearItem();

        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("SMART", null, "SMART", false);
        pul.addItem("ETPicking", null, "ETPicking", false);

    }

    public static void fillProject(PullDown pul, boolean blank) {
        if(blank){
            pul.addItem(StringUtils.EMPTY, null, "全部", true);
        }
        pul.addItem("1",null,"1:脱屑工程(自动)",!blank);
        pul.addItem("2",null,"2:脱屑工程(手动)",false);
        pul.addItem("3",null,"3:镀金",false);
        pul.addItem("4",null,"4:1FL组装工程",false);
        pul.addItem("5",null,"5:2FL组装工程",false);
        pul.addItem("6",null,"6:M2工程",false);
        pul.addItem("7",null,"7:涂装工程",false);
        pul.addItem("8",null,"8:Location Transfer/Stock Out",false);
        pul.addItem("9",null,"9:加工工程(闵行)",false);
        pul.addItem("10",null,"10:拉头发文明戏(闵行)",false);
        pul.addItem("11",null,"11:到外注的出库",false);
    }

    public static void fillCutOutUnit(PullDown pul, boolean blank) {
        if(blank){
            pul.addItem(StringUtils.EMPTY, null, "全部", true);
        }
        pul.addItem("1",null,"1:PR No.单位(镀金工程、涂装工程、外注发货)",!blank);
        pul.addItem("2",null,"2:line1-line2-区域单位(组装工程)",false);
        pul.addItem("3",null,"3:line1的第1位-物料单位(加工工程（闵行）)",false);
        pul.addItem("4",null,"4:物料单位(M2工程、脱屑工程)",false);
        pul.addItem("5",null,"5:订单No.单位(拉头出荷（闵行）)",false);
        pul.addItem("6",null,"6:物料-料箱单位(脱屑自动投入流水线)",false);
        pul.addItem("7",null,"7:物料－数量单位(Location Transfer/Stock Out)",false);
        pul.addItem("8",null,"8:整箱出库",false);
        pul.addItem("9",null,"9:胴体和cover以PR单位汇总,引手不汇总",false);
        pul.addItem("10",null,"10:只有胴体以PR单位汇总,cover、引手不汇总",false);
        pul.addItem("11",null,"11:只有cover以PR单位汇总,胴体、引手不汇总",false);
        pul.addItem("12",null,"12:对Wings指示的仕上工程以切出条件汇总",false);
    }

    public static void fillPart(PullDown pul) {
        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("1",null,"部品",false);
        pul.addItem("2",null,"半成品(胴体)",false);
        pul.addItem("3",null,"半成品(引手)",false);
        pul.addItem("4",null,"半成品(cover)",false);
        pul.addItem("5",null,"半成品(其他)",false);
        pul.addItem("6",null,"半成品(全部)",false);
    }

    public static void fillBCRStatus(PullDown pul) {
        pul.addItem(StringUtils.EMPTY, null, "全部", true);
        pul.addItem("0",null,"正常",false);
        pul.addItem("1",null,"No Read",false);
    }

//    public static void fillDateFlag(PullDown pul) throws Exception {
//        pul.clearItem();
//        pul.addItem(StringUtils.EMPTY, null, "全部", true);
//
//        DateFlagMaintenanceService service = new DateFlagMaintenanceService();
//        List<DateFlagMaintenanceDetail> list = service.getDateFlagList2();
//        for (DateFlagMaintenanceDetail detail : list) {
//            pul.addItem(detail.getCode(), null, detail.getName(), false);
//        }
//    }

    public static void fillSeqCondition2(PullDown pul) throws Exception
    {
        pul.clearItem();

        pul.addItem("1", null, "区域", true);
        pul.addItem("2", null, "物料", false);
        pul.addItem("3", null, "总库存数量多的顺序", false);
        pul.addItem("4", null, "总库存数量少的顺序", false);
    }

    public static void fillStockArea(PullDown pul) {
        pul.clearItem();

        pul.addItem("A", null, "自动仓库", true);
        pul.addItem("T", null, "料箱保管区域:T", false);
        pul.addItem("B", null, "料箱保管区域:B", false);
        pul.addItem("L", null, "1楼料箱保管区域1：L", false);
        pul.addItem("N", null, "2楼料箱保管区域1:N", false);
        pul.addItem("O", null, "2楼料箱保管区域2:O", false);
    }

//    public static void fillStorageArea(PullDown pul) {
//        pul.clearItem();
//
//        pul.addItem(StringUtils.EMPTY, null, "全部", true);
//        pul.addItem(ItemYKK.ASRS_ITEM,null,"自动库",false);
//        pul.addItem(ItemYKK.FLAT_ITEM,null,"平库",false);
//    }
}
