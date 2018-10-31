package jp.co.daifuku.wms.exercise.util.csv;

import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class CSVGenerator
{
    private CsvData csvInfo = null;
    private String root = StringUtils.EMPTY;

    public CSVGenerator(CsvData csvInfo, String root)
    {
        this(csvInfo, root, true);
    }

    public CSVGenerator(CsvData csvInfo, String root, boolean writeLogFlag)
    {
        this.csvInfo = csvInfo;
        this.root = root;
    }

    public String generateFile()
            throws Exception
    {
        File csvRoot = new File(root);
        if (!csvRoot.exists())
        {
            csvRoot.mkdir();
        }

        String path = generatePath(root);

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        try
        {
            File file = new File(path);

            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "GB2312");
            bw = new BufferedWriter(osw);

            bw.write(csvInfo.generateHead());
            bw.write("\r\n");
            for (int i = 1; i < csvInfo.getMaxRows(); i++)
            {
                String line = csvInfo.makeLine(csvInfo.getValue(i));
                bw.write(line);
                bw.write("\r\n");
            }
            bw.flush();
            bw.close();
            fos.close();
            osw.close();

            return path;
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
                if (fos != null)
                {
                    fos.close();
                }
                if (osw != null)
                {
                    osw.close();
                }
            }
            catch (IOException e)
            {
                throw e;
            }
        }
    }

    private String generatePath(String root)
    {
        String name = csvInfo.getName();
        String path = StringUtils.EMPTY;

        String osName = System.getProperties().getProperty("os.name");
        if (osName.indexOf("Windows") != -1)
        {
            path = root + "\\" + name + ".csv";
        }
        else
        {
            path = root + "/" + name + ".csv";
        }
        return path;
    }


}
