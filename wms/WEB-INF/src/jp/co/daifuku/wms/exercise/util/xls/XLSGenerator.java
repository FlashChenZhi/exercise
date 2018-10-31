package jp.co.daifuku.wms.exercise.util.xls;

import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.exercise.util.common.Const;
import jp.co.daifuku.wms.exercise.util.pager.PagerReturnObj;
import jp.co.daifuku.wms.exercise.util.pager.UIPager;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-9
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
public class XLSGenerator<U, V>
{
    private Map<String,XLSParams<V>> map;
    private UIPager<U, V> pager;
    private String fileName;
    private U u;
    private XLSRow<V> row;
    private int maxCount = Const.MAX_EXCEL_PRINT_COUNT;

    public int getMaxCount()
    {
        return maxCount;
    }

    public XLSGenerator(UIPager<U, V> pager, String fileName, U u, XLSRow<V> row)
    {
        this.pager = pager;
        this.fileName = fileName;
        this.row = row;
        this.u = u;
    }

    public XLSGenerator(Map<String,XLSParams<V>> map, String fileName)
    {
        this.map = map;
        this.fileName = fileName;
    }

    public XLSGenerator(UIPager<U, V> pager, String fileName, U u,int maxCount, XLSRow<V> row)
    {
        this.pager = pager;
        this.fileName = fileName;
        this.row = row;
        this.u = u;
        this.maxCount = maxCount;
    }

    public XLSReturnObj generateFile()
            throws Exception
    {
        WritableWorkbook data = null;
        int count = 5000;
        int sheetCount = 60000;
        XLSReturnObj rtnobj = new XLSReturnObj();
        try
        {
            String ddir = WmsParam.PREVIEW_DATA_FILE_PATH;
            File dir = new File(ddir);
            if (!dir.exists())
            {
                dir.mkdir();
            }

            String path = ddir + fileName + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xls";

            data = Workbook.createWorkbook(new File(path));

            WritableSheet sheet = null;

            WritableCellFormat cf = new WritableCellFormat();
            cf.setBorder(Border.ALL, BorderLineStyle.THIN);


            if(pager != null) {
                long totalCount = pager.getTotalCount(u);
                if(totalCount == 0)
                {
                    rtnobj.setResult(1);
                    return rtnobj;
                }
                if(totalCount > maxCount)
                {
                    rtnobj.setResult(2);
                    rtnobj.setTotalCount(totalCount);
                    return rtnobj;
                }
                boolean first = true;
                int i = 0;
                int j = 0;
                int sheetnum = 1;
                while (true) {
                    List<V> list;
                    if (first) {
                        PagerReturnObj<V> rtnObj = pager.first(count, u);
                        first = false;
                        list = rtnObj.getData();
                    } else {
                        PagerReturnObj<V> rtnObj = pager.next(j, count, u);
                        j += count;
                        list = rtnObj.getData();
                    }

                    if (list.isEmpty()) {
                        break;
                    }


                    for (V v : list) {
                        if (i % sheetCount == 0) {
                            sheet = data.createSheet("Sheet" + sheetnum, sheetnum++);
                            writeHead(sheet);

                            i = 1;
                        }

                        Map<Integer, String> rowMap = row.getRowMap(v);

                        for (int col : rowMap.keySet()) {
                            Label label = new Label(col, i, rowMap.get(col), cf);
                            sheet.addCell(label);
                        }
                        i++;
                    }
                }
            }else if(map != null)
            {
                int sheetnum = 1;

                for(String sheetName : map.keySet()) {
                    XLSParams<V> params = map.get(sheetName);
                    row = params.getXlsRow();
                    sheet = data.createSheet(sheetName, sheetnum++);
                    writeHead(sheet);
                    int i = 1;
                    for (V v : params.getList()) {
                        Map<Integer, String> rowMap = row.getRowMap(v);

                        for (int col : rowMap.keySet()) {
                            Label label = new Label(col, i, rowMap.get(col), cf);
                            sheet.addCell(label);
                        }
                        i++;
                    }
                }
            }


            data.write();

            rtnobj.setPath(path);

            return rtnobj;
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if (data != null)
                {
                    data.close();
                }
            }
            catch (IOException e)
            {
                throw e;
            }
        }
    }

    private void writeHead(WritableSheet sheet) throws WriteException
    {
        int i = 0;
        Map<Integer, String> headMap = row.getHeadMap();
        WritableCellFormat cf = new WritableCellFormat();
        cf.setBackground(Colour.LIGHT_GREEN);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        for (int col : headMap.keySet())
        {
            Label label = new Label(col, 0, headMap.get(col), cf);

            sheet.addCell(label);
        }
    }
}
