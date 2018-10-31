package jp.co.daifuku.common;

/**
 * Set EMF hedder.
 */
public final class EMFWriteCreator
        extends Object
{

    /**
     * Delimitation of record(CR,LF)
     */
    protected static final String RE = "\r\n";

    /**
     * Set EMF Hedder 
     * @param stbuff ヘッダー用文字列
     */
    public static void setHedder(StringBuffer stbuff)
    {
        String host = getHostName();
        String port = getPortNo();
        String paPrinter = getPAPrinter();

        stbuff.append("<start>" + RE);
        stbuff.append("VrSetPrinter=\"" + host + ":" + port + ":" + paPrinter + ",EMF\"" + RE);
        stbuff.append("<end>" + RE);
    }

    /**
     * Get Palite PortNo
     * @return CommonParamのポートNo
     */
    private static String getPortNo()
    {
        return CommonParam.getParam("EMF_PALITE_PORT");
    }

    /**
     * Get Palite HostName
     * @return CommonParamのホスト名称
     */
    private static String getHostName()
    {
        return CommonParam.getParam("EMF_PALITE_HOST");
    }

    /**
     * Get EMF target PAlite Printer
     * @return CommonParamのEMFプリンタ
     */
    private static String getPAPrinter()
    {
        return CommonParam.getParam("EMF_PRINTER");
    }

}
