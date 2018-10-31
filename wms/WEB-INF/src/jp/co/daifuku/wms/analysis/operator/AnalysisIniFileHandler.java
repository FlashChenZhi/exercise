// $Id: AnalysisIniFileHandler.java 882 2008-10-29 00:23:34Z tanaka $
package jp.co.daifuku.wms.analysis.operator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 分析系設定ファイルハンドラーです。<BR>
 * 休憩時間配列を扱うメソッドも実装しています。<BR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>Y.Kato</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 882 $, $Date: 2008-10-29 09:23:34 +0900 (水, 29 10 2008) $
 * @author  $Author: tanaka $
 */
public class AnalysisIniFileHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // デフォルト履歴データ生成最終日
    private static final String DEFAULT_LAST_DATE = "19700101000000000";

    /** パラメータ設定ファイル */
    private static final String INI_FILENAME = WmsParam.ANALYSIS_INI_PATH;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String p_ThresholdA = "50"; // Aランクしきい値

    private String p_ThresholdB = "70"; // Bランクしきい値

    private String p_ABCAnalysisMinItemNum = "3"; // ABC分析用最小アイテム数

    private String p_InstockWorkerNum = "1"; // 入荷作業者数

    private String p_InstockWkStartTime = "00:00"; // 入荷作業開始時刻

    private String p_InstockSecPerItem = "10.0"; // 入荷商品毎時間

    private String p_InstockSecPerItemOrig = "10.0"; // 入荷商品毎時間（ロード値）

    private String p_InstockSecPerItemPrev = "10.0"; // 入荷商品毎時間（前回値）

    private String p_InstockSecPerPiece = "10.0"; // 入荷ピース毎時間

    private String p_InstockSecPerPieceOrig = "10.0"; // 入荷ピース毎時間（ロード値）

    private String p_InstockSecPerPiecePrev = "10.0"; // 入荷ピース毎時間（前回値）

    private String p_StorageWorkerNum = "1"; // 入庫作業者数

    private String p_StorageWkStartTime = "00:00"; // 入庫作業開始時刻

    private String p_StorageSecPerItem = "10.0"; // 入庫商品毎時間

    private String p_StorageSecPerItemOrig = "10.0"; // 入庫商品毎時間（ロード値）

    private String p_StorageSecPerItemPrev = "10.0"; // 入庫商品毎時間（前回値）

    private String p_StorageSecPerPiece = "10.0"; // 入庫ピース毎時間

    private String p_StorageSecPerPieceOrig = "10.0"; // 入庫ピース毎時間（ロード値）

    private String p_StorageSecPerPiecePrev = "10.0"; // 入庫ピース毎時間（前回値）

    private String p_RetrievalWorkerNum = "1"; // 出庫作業者数

    private String p_RetrievalWkStartTime = "00:00"; // 出庫作業開始時刻

    private String p_RetrievalSecPerItem = "10.0"; // 出庫商品毎時間

    private String p_RetrievalSecPerItemOrig = "10.0";// 出庫商品毎時間（ロード値）

    private String p_RetrievalSecPerItemPrev = "10.0";// 出庫商品毎時間（前回値）

    private String p_RetrievalSecPerPiece = "10.0"; // 出庫ピース毎時間

    private String p_RetrievalSecPerPieceOrig = "10.0"; // 出庫ピース毎時間（ロード値）

    private String p_RetrievalSecPerPiecePrev = "10.0"; // 出庫ピース毎時間（前回値）

    private String p_SortingWorkerNum = "1"; // 仕分作業者数

    private String p_SortingWkStartTime = "00:00"; // 仕分作業開始時刻

    private String p_SortingSecPerItem = "10.0"; // 仕分商品毎時間

    private String p_SortingSecPerItemOrig = "10.0"; // 仕分商品毎時間（ロード値）

    private String p_SortingSecPerItemPrev = "10.0"; // 仕分商品毎時間（前回値）

    private String p_SortingSecPerPiece = "10.0"; // 仕分ピース毎時間

    private String p_SortingSecPerPieceOrig = "10.0"; // 仕分ピース毎時間（ロード値）

    private String p_SortingSecPerPiecePrev = "10.0"; // 仕分ピース毎時間（前回値）

    private String p_ShippingWorkerNum = "1"; // 出荷作業者数

    private String p_ShippingWkStartTime = "00:00"; // 出荷作業開始時刻

    private String p_ShippingSecPerItem = "10.0"; // 出荷商品毎時間

    private String p_ShippingSecPerItemOrig = "10.0"; // 出荷商品毎時間（ロード値）

    private String p_ShippingSecPerItemPrev = "10.0"; // 出荷商品毎時間（前回値）

    private String p_ShippingSecPerPiece = "10.0"; // 出荷ピース毎時間

    private String p_ShippingSecPerPieceOrig = "10.0"; // 出荷ピース毎時間（ロード値）

    private String p_ShippingSecPerPiecePrev = "10.0"; // 出荷ピース毎時間（前回値）

    private List<String> p_BreakTimeArray; // 休憩時間配列

    private String p_HistoryMakedDate = ""; // 最終履歴データ生成日

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * <code>AnalysisIniFileHandler</code>分析系設定ファイルハンドラコンストラクター
     */
    public AnalysisIniFileHandler()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * <code>load</code>iniファイル読み込み
     * @return 結果
     */
    public boolean load()
    {
        try
        {
            IniFileOperator io = new IniFileOperator(INI_FILENAME);

            p_ThresholdA = io.get("ThresholdA");
            p_ThresholdB = io.get("ThresholdB");

            p_ABCAnalysisMinItemNum = io.get("ABCAnalysisMinItemNum");

            p_InstockWorkerNum = io.get("InstockWorkerNum");
            p_InstockWkStartTime = io.get("InstockWkStartTime");
            p_InstockSecPerItem = io.get("InstockSecPerItem");
            p_InstockSecPerItemOrig = p_InstockSecPerItem;
            p_InstockSecPerItemPrev = io.get("InstockSecPerItemPrev");
            p_InstockSecPerPiece = io.get("InstockSecPerPiece");
            p_InstockSecPerPieceOrig = p_InstockSecPerPiece;
            p_InstockSecPerPiecePrev = io.get("InstockSecPerPiecePrev");

            p_StorageWorkerNum = io.get("StorageWorkerNum");
            p_StorageWkStartTime = io.get("StorageWkStartTime");
            p_StorageSecPerItem = io.get("StorageSecPerItem");
            p_StorageSecPerItemOrig = p_StorageSecPerItem;
            p_StorageSecPerItemPrev = io.get("StorageSecPerItemPrev");
            p_StorageSecPerPiece = io.get("StorageSecPerPiece");
            p_StorageSecPerPieceOrig = p_StorageSecPerPiece;
            p_StorageSecPerPiecePrev = io.get("StorageSecPerPiecePrev");

            p_RetrievalWorkerNum = io.get("RetrievalWorkerNum");
            p_RetrievalWkStartTime = io.get("RetrievalWkStartTime");
            p_RetrievalSecPerItem = io.get("RetrievalSecPerItem");
            p_RetrievalSecPerItemOrig = p_RetrievalSecPerItem;
            p_RetrievalSecPerItemPrev = io.get("RetrievalSecPerItemPrev");
            p_RetrievalSecPerPiece = io.get("RetrievalSecPerPiece");
            p_RetrievalSecPerPieceOrig = p_RetrievalSecPerPiece;
            p_RetrievalSecPerPiecePrev = io.get("RetrievalSecPerPiecePrev");

            p_SortingWorkerNum = io.get("SortingWorkerNum");
            p_SortingWkStartTime = io.get("SortingWkStartTime");
            p_SortingSecPerItem = io.get("SortingSecPerItem");
            p_SortingSecPerItemOrig = p_SortingSecPerItem;
            p_SortingSecPerItemPrev = io.get("SortingSecPerItemPrev");
            p_SortingSecPerPiece = io.get("SortingSecPerPiece");
            p_SortingSecPerPieceOrig = p_SortingSecPerPiece;
            p_SortingSecPerPiecePrev = io.get("SortingSecPerPiecePrev");

            p_ShippingWorkerNum = io.get("ShippingWorkerNum");
            p_ShippingWkStartTime = io.get("ShippingWkStartTime");
            p_ShippingSecPerItem = io.get("ShippingSecPerItem");
            p_ShippingSecPerItemOrig = p_ShippingSecPerItem;
            p_ShippingSecPerItemPrev = io.get("ShippingSecPerItemPrev");
            p_ShippingSecPerPiece = io.get("ShippingSecPerPiece");
            p_ShippingSecPerPieceOrig = p_ShippingSecPerPiece;
            p_ShippingSecPerPiecePrev = io.get("ShippingSecPerPiecePrev");

            p_BreakTimeArray = new ArrayList<String>();
            String strTemp;
            DecimalFormat df = new DecimalFormat("00");
            for (int i = 1; i <= WmsParam.BREAKTIME_MAX; i++)
            {
                strTemp = "BreakTime" + df.format(i);
                p_BreakTimeArray.add(io.get(strTemp));
            }

            p_HistoryMakedDate = io.get("HistoryMakedDate");
        }
        catch (ReadWriteException e)
        {
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());
            return false;
        }
        return true;
    }

    /**
     * <code>saveAll</code>iniファイル保存(全保存)
     * @return true：保存に成功した場合  false：そうでない場合
     */
    public boolean saveAll()
    {
        saveAbcAnalysis();
        saveWorkingTimeSimu();
        saveBreakTime();
        return true;
    }

    /**
     * <code>saveAbcAnalysis</code>iniファイル保存(ABC分析系)
     * @return true：保存に成功した場合  false：そうでない場合
     */
    public boolean saveAbcAnalysis()
    {
        try
        {
            IniFileOperator io = new IniFileOperator(INI_FILENAME);

            io.set("ThresholdA", p_ThresholdA);
            io.set("ThresholdB", p_ThresholdB);

            io.flush();
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        return true;
    }

    /**
     * <code>saveWorkingTimeSimu</code>iniファイル保存(作業時間予測系)
     * @return true：保存に成功した場合  false：そうでない場合
     */
    public boolean saveWorkingTimeSimu()
    {
        try
        {
            IniFileOperator io = new IniFileOperator(INI_FILENAME);

            // 商品毎時間、ピース毎時間がロード値から変更されていたら前回値を保存します。
            if ((!StringUtil.isBlank(p_InstockSecPerItem) && !p_InstockSecPerItem.equals(p_InstockSecPerItemOrig))
                    || (!StringUtil.isBlank(p_InstockSecPerPiece) && !p_InstockSecPerPiece.equals(p_InstockSecPerPieceOrig))
                    || (!StringUtil.isBlank(p_StorageSecPerItem) && !p_StorageSecPerItem.equals(p_StorageSecPerItemOrig))
                    || (!StringUtil.isBlank(p_StorageSecPerPiece) && !p_StorageSecPerPiece.equals(p_StorageSecPerPieceOrig))
                    || (!StringUtil.isBlank(p_RetrievalSecPerItem) && !p_RetrievalSecPerItem.equals(p_RetrievalSecPerItemOrig))
                    || (!StringUtil.isBlank(p_RetrievalSecPerPiece) && !p_RetrievalSecPerPiece.equals(p_RetrievalSecPerPieceOrig))
                    || (!StringUtil.isBlank(p_SortingSecPerItem) && !p_SortingSecPerItem.equals(p_SortingSecPerItemOrig))
                    || (!StringUtil.isBlank(p_SortingSecPerPiece) && !p_SortingSecPerPiece.equals(p_SortingSecPerPieceOrig))
                    || (!StringUtil.isBlank(p_ShippingSecPerItem) && !p_ShippingSecPerItem.equals(p_ShippingSecPerItemOrig))
                    || (!StringUtil.isBlank(p_ShippingSecPerPiece) && !p_ShippingSecPerPiece.equals(p_ShippingSecPerPieceOrig)))
            {
                p_InstockSecPerItemPrev = p_InstockSecPerItemOrig;
                p_InstockSecPerPiecePrev = p_InstockSecPerPieceOrig;
                p_StorageSecPerItemPrev = p_StorageSecPerItemOrig;
                p_StorageSecPerPiecePrev = p_StorageSecPerPieceOrig;
                p_RetrievalSecPerItemPrev = p_RetrievalSecPerItemOrig;
                p_RetrievalSecPerPiecePrev = p_RetrievalSecPerPieceOrig;
                p_SortingSecPerItemPrev = p_SortingSecPerItemOrig;
                p_SortingSecPerPiecePrev = p_SortingSecPerPieceOrig;
                p_ShippingSecPerItemPrev = p_ShippingSecPerItemOrig;
                p_ShippingSecPerPiecePrev = p_ShippingSecPerPieceOrig;
            }

            if (!StringUtil.isBlank(p_InstockWorkerNum))
            {
                io.set("InstockWorkerNum", p_InstockWorkerNum);
            }
            if (!StringUtil.isBlank(p_InstockWkStartTime))
            {
                io.set("InstockWkStartTime", p_InstockWkStartTime);
            }
            if (!StringUtil.isBlank(p_InstockSecPerItem))
            {
                io.set("InstockSecPerItem", p_InstockSecPerItem);
            }
            if (!StringUtil.isBlank(p_InstockSecPerItemPrev))
            {
                io.set("InstockSecPerItemPrev", p_InstockSecPerItemPrev);
            }
            if (!StringUtil.isBlank(p_InstockSecPerPiece))
            {
                io.set("InstockSecPerPiece", p_InstockSecPerPiece);
            }
            if (!StringUtil.isBlank(p_InstockSecPerPiecePrev))
            {
                io.set("InstockSecPerPiecePrev", p_InstockSecPerPiecePrev);
            }

            if (!StringUtil.isBlank(p_StorageWorkerNum))
            {
                io.set("StorageWorkerNum", p_StorageWorkerNum);
            }
            if (!StringUtil.isBlank(p_StorageWkStartTime))
            {
                io.set("StorageWkStartTime", p_StorageWkStartTime);
            }
            if (!StringUtil.isBlank(p_StorageSecPerItem))
            {
                io.set("StorageSecPerItem", p_StorageSecPerItem);
            }
            if (!StringUtil.isBlank(p_StorageSecPerItemPrev))
            {
                io.set("StorageSecPerItemPrev", p_StorageSecPerItemPrev);
            }
            if (!StringUtil.isBlank(p_StorageSecPerPiece))
            {
                io.set("StorageSecPerPiece", p_StorageSecPerPiece);
            }
            if (!StringUtil.isBlank(p_StorageSecPerPiecePrev))
            {
                io.set("StorageSecPerPiecePrev", p_StorageSecPerPiecePrev);
            }

            if (!StringUtil.isBlank(p_RetrievalWorkerNum))
            {
                io.set("RetrievalWorkerNum", p_RetrievalWorkerNum);
            }
            if (!StringUtil.isBlank(p_RetrievalWkStartTime))
            {
                io.set("RetrievalWkStartTime", p_RetrievalWkStartTime);
            }
            if (!StringUtil.isBlank(p_RetrievalSecPerItem))
            {
                io.set("RetrievalSecPerItem", p_RetrievalSecPerItem);
            }
            if (!StringUtil.isBlank(p_RetrievalSecPerItemPrev))
            {
                io.set("RetrievalSecPerItemPrev", p_RetrievalSecPerItemPrev);
            }
            if (!StringUtil.isBlank(p_RetrievalSecPerPiece))
            {
                io.set("RetrievalSecPerPiece", p_RetrievalSecPerPiece);
            }
            if (!StringUtil.isBlank(p_RetrievalSecPerPiecePrev))
            {
                io.set("RetrievalSecPerPiecePrev", p_RetrievalSecPerPiecePrev);
            }

            if (!StringUtil.isBlank(p_SortingWorkerNum))
            {
                io.set("SortingWorkerNum", p_SortingWorkerNum);
            }
            if (!StringUtil.isBlank(p_SortingWkStartTime))
            {
                io.set("SortingWkStartTime", p_SortingWkStartTime);
            }
            if (!StringUtil.isBlank(p_SortingSecPerItem))
            {
                io.set("SortingSecPerItem", p_SortingSecPerItem);
            }
            if (!StringUtil.isBlank(p_SortingSecPerItemPrev))
            {
                io.set("SortingSecPerItemPrev", p_SortingSecPerItemPrev);
            }
            if (!StringUtil.isBlank(p_SortingSecPerPiece))
            {
                io.set("SortingSecPerPiece", p_SortingSecPerPiece);
            }
            if (!StringUtil.isBlank(p_SortingSecPerPiecePrev))
            {
                io.set("SortingSecPerPiecePrev", p_SortingSecPerPiecePrev);
            }

            if (!StringUtil.isBlank(p_ShippingWorkerNum))
            {
                io.set("ShippingWorkerNum", p_ShippingWorkerNum);
            }
            if (!StringUtil.isBlank(p_ShippingWkStartTime))
            {
                io.set("ShippingWkStartTime", p_ShippingWkStartTime);
            }
            if (!StringUtil.isBlank(p_ShippingSecPerItem))
            {
                io.set("ShippingSecPerItem", p_ShippingSecPerItem);
            }
            if (!StringUtil.isBlank(p_ShippingSecPerItemPrev))
            {
                io.set("ShippingSecPerItemPrev", p_ShippingSecPerItemPrev);
            }
            if (!StringUtil.isBlank(p_ShippingSecPerPiece))
            {
                io.set("ShippingSecPerPiece", p_ShippingSecPerPiece);
            }
            if (!StringUtil.isBlank(p_ShippingSecPerPiecePrev))
            {
                io.set("ShippingSecPerPiecePrev", p_ShippingSecPerPiecePrev);
            }

            io.flush();
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        return true;
    }

    /**
     * <code>saveBreakTime</code>iniファイル保存(休憩時間系)
     * @return true：保存に成功した場合  false：そうでない場合
     */
    public boolean saveBreakTime()
    {
        // 開始時刻順にソートする。
        Collections.sort(p_BreakTimeArray);

        try
        {
            IniFileOperator io = new IniFileOperator(INI_FILENAME);

            String strTemp;
            DecimalFormat df = new DecimalFormat("00");
            for (int i = 0; i < WmsParam.BREAKTIME_MAX; i++)
            {
                strTemp = "BreakTime" + df.format(i + 1);
                if (i < p_BreakTimeArray.size())
                {
                    io.set(strTemp, p_BreakTimeArray.get(i));
                }
                else
                {
                    io.set(strTemp, "");
                }
            }

            io.flush();
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        return true;
    }

    /**
     * <code>isBreakTime</code>休憩時間中かどうかを返す
     * @param time 判定対象時刻
     * @return true：休憩時間中  false：そうでない場合
     */
    public boolean isBreakTime(String time)
    {
        String strTemp;
        String[] strTime;

        for (int i = 0; i < WmsParam.BREAKTIME_MAX; i++)
        {
            if (i < p_BreakTimeArray.size())
            {
                strTemp = p_BreakTimeArray.get(i);
                strTime = strTemp.split(",");
                if (strTime[0] != null && !strTime[0].equals(""))
                {
                    if (strTime[0].compareTo(strTime[1]) <= 0)
                    {
                        // 開始＜＝終了の場合
                        if (time.compareTo(strTime[0]) >= 0 && time.compareTo(strTime[1]) < 0)
                        {
                            return true;
                        }
                    }
                    else
                    {
                        // 開始＞終了の場合（０時またがり）
                        if ((time.compareTo(strTime[0]) >= 0 && time.compareTo("24:00") < 0)
                                || (time.compareTo("00:00") >= 0 && time.compareTo(strTime[1]) < 0))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * <code>getBreakEndTime</code>休憩終了時刻を算出して返します。
     * @param time 休憩開始時刻
     * @param sec 作業所要時間（秒）
     * @return 休憩終了時刻
     */
    public String getWorkEndTime(String time, long sec)
    {
        String strTemp;
        long paraSec = sec;
        // 休憩開始時刻を０時からの経過秒数に変換します。
        long secStart = strTimeToSec(time);

        // 休憩開始時刻を１分づつ増やしながら作業所要時間を減算します。
        // ただし、その時刻が休憩時間中であれば作業所要時間を減算しません。
        // 最終的に作業所要時間がマイナスになった時点の時刻が作業終了時刻となります。
        while (paraSec > 0)
        {
            strTemp = secTostrTime(secStart);
            if (!isBreakTime(strTemp))
            {
                paraSec -= 60;
            }
            secStart += 60;
            secStart %= 3600 * 24;
        }
        return secTostrTime(secStart);
    }

    /**
     * <code>saveHistoryMakedDate</code>iniファイル保存(最終履歴データ生成日)
     * @return true：保存に成功した場合  false：そうでない場合
     */
    public boolean saveHistoryMakedDate()
    {
        try
        {
            IniFileOperator io = new IniFileOperator(INI_FILENAME);

            io.set("HistoryMakedDate", p_HistoryMakedDate);

            io.flush();
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        return true;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * Aランクしきい値を返す
     *
     * @return Aランクしきい値
     */
    public String getThresholdA()
    {
        return p_ThresholdA;
    }

    /**
     * Aランクしきい値をセットする
     * @param val Aランクしきい値
     *
     */
    public void setThresholdA(String val)
    {
        p_ThresholdA = val;
    }

    /**
     * Bランクしきい値を返す
     *
     * @return Bランクしきい値
     */
    public String getThresholdB()
    {
        return p_ThresholdB;
    }

    /**
     * ABC分析用最小アイテム数を返す
     *
     * @return ABC分析用最小アイテム数
     */
    public String getABCAnalysisMinItemNum()
    {
        return p_ABCAnalysisMinItemNum;
    }

    /**
     * Bランクしきい値をセットする
     * @param val Bランクしきい値
     *
     */
    public void setThresholdB(String val)
    {
        p_ThresholdB = val;
    }

    /**
     * 入荷作業者数を返す
     * @return 入荷作業者数
     */
    public String getInstockWorkerNum()
    {
        return p_InstockWorkerNum;
    }

    /**
     * 入荷作業者数をセットする
     * @param num 入荷作業者数
     */
    public void setInstockWorkerNum(String num)
    {
        p_InstockWorkerNum = num;
    }

    /**
     * 入荷作業開始時刻を返す
     * @return 入荷作業開始時刻
     */
    public String getInstockWkStartTime()
    {
        return p_InstockWkStartTime;
    }

    /**
     * 入荷作業開始時刻をセットする
     * @param tim 入荷作業開始時刻
     */
    public void setInstockWkStartTime(String tim)
    {
        p_InstockWkStartTime = tim;
    }

    /**
     * 入荷商品毎時間を返す
     * @return 入荷商品毎時間
     */
    public String getInstockSecPerItem()
    {
        return p_InstockSecPerItem;
    }

    /**
     * 入荷商品毎時間（前回値）を返す
     * @return 入荷商品毎時間（前回値）
     */
    public String getInstockSecPerItemPrev()
    {
        return p_InstockSecPerItemPrev;
    }

    /**
     * 入荷商品毎時間をセットする
     * @param sec 入荷商品毎時間
     */
    public void setInstockSecPerItem(String sec)
    {
        p_InstockSecPerItem = sec;
    }

    /**
     * 入荷ピース毎時間を返す
     * @return 入荷作数ピース毎時間
     */
    public String getInstockSecPerPiece()
    {
        return p_InstockSecPerPiece;
    }

    /**
     * 入荷ピース毎時間（前回値）を返す
     * @return 入荷作数ピース毎時間（前回値）
     */
    public String getInstockSecPerPiecePrev()
    {
        return p_InstockSecPerPiecePrev;
    }

    /**
     * 入荷ピース毎時間をセットする
     * @param sec 入荷ピース毎時間
     */
    public void setInstockSecPerPiece(String sec)
    {
        p_InstockSecPerPiece = sec;
    }

    /**
     * 入庫作業者数を返す
     * @return 入庫作業者数
     */
    public String getStorageWorkerNum()
    {
        return p_StorageWorkerNum;
    }

    /**
     * 入庫作業者数をセットする
     * @param num 入庫作業者数
     */
    public void setStorageWorkerNum(String num)
    {
        p_StorageWorkerNum = num;
    }

    /**
     * 入庫作業開始時刻を返す
     * @return 入庫作業開始時刻
     */
    public String getStorageWkStartTime()
    {
        return p_StorageWkStartTime;
    }

    /**
     * 入庫作業開始時刻をセットする
     * @param tim 入庫作業開始時刻
     */
    public void setStorageWkStartTime(String tim)
    {
        p_StorageWkStartTime = tim;
    }

    /**
     * 入庫商品毎時間を返す
     * @return 入庫商品毎時間
     */
    public String getStorageSecPerItem()
    {
        return p_StorageSecPerItem;
    }

    /**
     * 入庫商品毎時間（前回値）を返す
     * @return 入庫商品毎時間（前回値）
     */
    public String getStorageSecPerItemPrev()
    {
        return p_StorageSecPerItemPrev;
    }

    /**
     * 入庫商品毎時間をセットする
     * @param sec 入庫商品毎時間
     */
    public void setStorageSecPerItem(String sec)
    {
        p_StorageSecPerItem = sec;
    }

    /**
     * 入庫ピース毎時間を返す
     * @return 入庫ピース毎時間
     */
    public String getStorageSecPerPiece()
    {
        return p_StorageSecPerPiece;
    }

    /**
     * 入庫ピース毎時間（前回値）を返す
     * @return 入庫ピース毎時間（前回値）
     */
    public String getStorageSecPerPiecePrev()
    {
        return p_StorageSecPerPiecePrev;
    }

    /**
     * 入庫ピース毎時間をセットする
     * @param sec 入庫ピース毎時間
     */
    public void setStorageSecPerPiece(String sec)
    {
        p_StorageSecPerPiece = sec;
    }

    /**
     * 出庫作業者数を返す
     * @return 出庫作業者数
     */
    public String getRetrievalWorkerNum()
    {
        return p_RetrievalWorkerNum;
    }

    /**
     * 出庫作業者数をセットする
     * @param num 出庫作業者数
     */
    public void setRetrievalWorkerNum(String num)
    {
        p_RetrievalWorkerNum = num;
    }

    /**
     * 出庫作業開始時刻を返す
     * @return 出庫作業開始時刻
     */
    public String getRetrievalWkStartTime()
    {
        return p_RetrievalWkStartTime;
    }

    /**
     * 出庫作業開始時刻をセットする
     * @param tim 出庫作業開始時刻
     */
    public void setRetrievalWkStartTime(String tim)
    {
        p_RetrievalWkStartTime = tim;
    }

    /**
     * 出庫商品毎時間を返す
     * @return 出庫商品毎時間
     */
    public String getRetrievalSecPerItem()
    {
        return p_RetrievalSecPerItem;
    }

    /**
     * 出庫商品毎時間（前回値）を返す
     * @return 出庫商品毎時間（前回値）
     */
    public String getRetrievalSecPerItemPrev()
    {
        return p_RetrievalSecPerItemPrev;
    }

    /**
     * 出庫商品毎時間をセットする
     * @param sec 出庫商品毎時間
     */
    public void setRetrievalSecPerItem(String sec)
    {
        p_RetrievalSecPerItem = sec;
    }

    /**
     * 出庫ピース毎時間を返す
     * @return 出庫ピース毎時間
     */
    public String getRetrievalSecPerPiece()
    {
        return p_RetrievalSecPerPiece;
    }

    /**
     * 出庫ピース毎時間（前回値）を返す
     * @return 出庫ピース毎時間（前回値）
     */
    public String getRetrievalSecPerPiecePrev()
    {
        return p_RetrievalSecPerPiecePrev;
    }

    /**
     * 出庫ピース毎時間をセットする
     * @param sec 出庫ピース毎時間
     */
    public void setRetrievalSecPerPiece(String sec)
    {
        p_RetrievalSecPerPiece = sec;
    }

    /**
     * 仕分作業者数を返す
     * @return 仕分作業者数
     */
    public String getSortingWorkerNum()
    {
        return p_SortingWorkerNum;
    }

    /**
     * 仕分作業者数をセットする
     * @param num 仕分作業者数
     */
    public void setSortingWorkerNum(String num)
    {
        p_SortingWorkerNum = num;
    }

    /**
     * 仕分作業開始時刻を返す
     * @return 仕分作業開始時刻
     */
    public String getSortingWkStartTime()
    {
        return p_SortingWkStartTime;
    }

    /**
     * 仕分作業開始時刻をセットする
     * @param tim 仕分作業開始時刻
     */
    public void setSortingWkStartTime(String tim)
    {
        p_SortingWkStartTime = tim;
    }

    /**
     * 仕分商品毎時間を返す
     * @return 仕分商品毎時間
     */
    public String getSortingSecPerItem()
    {
        return p_SortingSecPerItem;
    }

    /**
     * 仕分商品毎時間（前回値）を返す
     * @return 仕分商品毎時間（前回値）
     */
    public String getSortingSecPerItemPrev()
    {
        return p_SortingSecPerItemPrev;
    }

    /**
     * 仕分商品毎時間をセットする
     * @param sec 仕分商品毎時間
     */
    public void setSortingSecPerItem(String sec)
    {
        p_SortingSecPerItem = sec;
    }

    /**
     * 仕分ピース毎時間を返す
     * @return 仕分ピース毎時間
     */
    public String getSortingSecPerPiece()
    {
        return p_SortingSecPerPiece;
    }

    /**
     * 仕分ピース毎時間（前回値）を返す
     * @return 仕分ピース毎時間（前回値）
     */
    public String getSortingSecPerPiecePrev()
    {
        return p_SortingSecPerPiecePrev;
    }

    /**
     * 仕分ピース毎時間をセットする
     * @param sec 仕分ピース毎時間
     */
    public void setSortingSecPerPiece(String sec)
    {
        p_SortingSecPerPiece = sec;
    }

    /**
     * 出荷作業者数を返す
     * @return 出荷作業者数
     */
    public String getShippingWorkerNum()
    {
        return p_ShippingWorkerNum;
    }

    /**
     * 出荷作業者数をセットする
     * @param num 出荷作業者数
     */
    public void setShippingWorkerNum(String num)
    {
        p_ShippingWorkerNum = num;
    }

    /**
     * 出荷作業開始時刻を返す
     * @return 出荷作業開始時刻
     */
    public String getShippingWkStartTime()
    {
        return p_ShippingWkStartTime;
    }

    /**
     * 出荷作業開始時刻をセットする
     * @param tim 出荷作業開始時刻
     */
    public void setShippingWkStartTime(String tim)
    {
        p_ShippingWkStartTime = tim;
    }

    /**
     * 出荷商品毎時間を返す
     * @return 出荷商品毎時間
     */
    public String getShippingSecPerItem()
    {
        return p_ShippingSecPerItem;
    }

    /**
     * 出荷商品毎時間（前回値）を返す
     * @return 出荷商品毎時間（前回値）
     */
    public String getShippingSecPerItemPrev()
    {
        return p_ShippingSecPerItemPrev;
    }

    /**
     * 出荷商品毎時間をセットする
     * @param sec 出荷商品毎時間
     */
    public void setShippingSecPerItem(String sec)
    {
        p_ShippingSecPerItem = sec;
    }

    /**
     * 出荷ピース毎時間を返す
     * @return 出荷ピース毎時間
     */
    public String getShippingSecPerPiece()
    {
        return p_ShippingSecPerPiece;
    }

    /**
     * 出荷ピース毎時間（前回値）を返す
     * @return 出荷ピース毎時間（前回値）
     */
    public String getShippingSecPerPiecePrev()
    {
        return p_ShippingSecPerPiecePrev;
    }

    /**
     * 出荷ピース毎時間をセットする
     * @param sec 出荷ピース毎時間
     */
    public void setShippingSecPerPiece(String sec)
    {
        p_ShippingSecPerPiece = sec;
    }

    /**
     * 休憩開始終了時刻配列をクリアする
     *
     */
    public void clearBreakTime()
    {
        p_BreakTimeArray.clear();
    }

    /**
     * 休憩開始終了時刻を返す
     * @param idx 配列index
     * @return 休憩開始終了時間(HH:MM,HH:MM)
     */
    public String getBreakTime(int idx)
    {
        return p_BreakTimeArray.get(idx);
    }

    /**
     * 休憩開始終了時刻をセットする
     * @param idx 配列index
     * @param sTime 休憩開始時刻(HH:MM)
     * @param eTime 休憩終了時刻(HH:MM)
     */
    public void setBreakTime(int idx, String sTime, String eTime)
    {
        String strTemp = sTime + "," + eTime;
        if (strTemp.length() < 11)
        {
            return;
        }
        if (idx >= p_BreakTimeArray.size())
        {
            p_BreakTimeArray.add(strTemp);
        }
        else
        {
            p_BreakTimeArray.set(idx, strTemp);
        }
    }

    /**
     * 最終履歴データ生成日を返します。<br>
     * 設定内容に異常を検出した場合はデフォルトの日時を返します。
     *
     * @return 最終履歴データ生成日
     */
    public Date getHistoryMakedDate()
    {
        String historyMakedDate = p_HistoryMakedDate;
        if (StringUtil.isBlank(historyMakedDate))
        {
            historyMakedDate = DEFAULT_LAST_DATE;
        }
        return WmsFormatter.toDateTime(historyMakedDate);
    }

    /**
     * 最終履歴データ生成日をセットします。
     *
     * @param val 最終履歴データ生成日
     */
    public void setHistoryMakedDate(Date val)
    {
        //p_HistoryMakedDate = WmsFormatter.toParamDateTime(val, WmsFormatter.MILISEC_LENGTH);
        p_HistoryMakedDate = WmsFormatter.toParamDate(val) + 
                            WmsFormatter.toParamTime(val, WmsFormatter.MILISEC_LENGTH);
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * <code>strTimeToSec</code>HH:MM文字列を０時からの経過秒数へ変換する。
     * @param strTime 時刻文字列(HH:MM)
     * @return 経過秒数
     */
    private long strTimeToSec(String strTime)
    {
        String[] strTemp;
        strTemp = strTime.split(":");
        long hour = new Long(strTemp[0]).longValue() * 3600;
        long minute = new Long(strTemp[1]).longValue() * 60;
        return hour + minute;
    }

    /**
     * <code>secTostrTime</code>０時からの経過秒数をHH:MM文字列へ変換する。
     * @param sec ０時からの経過秒数
     * @return 変換後時刻文字列(HH:MM)
     */
    private String secTostrTime(long sec)
    {
        long hour = sec / 3600;
        long minute = (long)(Math.ceil(((double)sec % 3600) / 60));
        DecimalFormat df = new DecimalFormat("00");
        String strTemp = df.format(hour) + ":" + df.format(minute);
        return strTemp;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 882 $,$Date: 2008-10-29 09:23:34 +0900 (水, 29 10 2008) $");
    }
}
//end of class
