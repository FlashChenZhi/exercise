package jp.co.daifuku.wms.base.util;


/**
 * 一定時間処理を止めます。
 */
public class Wait
{
    /**
     * メインメソッド
     * @param argv コマンドライン引数
     */
    public static void main(String[] argv)
    {
        try
        {
            long waitTime = Integer.parseInt(argv[0]) * 1000;
            Thread.sleep(waitTime);
        }
        catch (Exception e)
        {
        }
    }
}
