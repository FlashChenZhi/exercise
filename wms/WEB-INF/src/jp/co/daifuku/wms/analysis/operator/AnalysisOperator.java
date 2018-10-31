package jp.co.daifuku.wms.analysis.operator;

import org.jfree.chart.JFreeChart;
import java.sql.Connection;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.wms.analysis.schedule.AnalysisInParameter;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.util.AbcChartCreater;

/**
 * 分析のオペレータクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 * </tbody></table><hr>
 *
 * @version $Revision: 5027 $, $Date: 2009-09-18 11:24:54 +0900 (金, 18 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class AnalysisOperator
        extends AbstractOperator
{
    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public AnalysisOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * ABC分析グラフを描画します。
     * @param iparam 入力パラメータ
     * @param oparam 出力パラメータ
     * @return chart 描画パラメータ
     * @throws Exception 全ての例外を報告します。
     */
    public JFreeChart createChart(AnalysisInParameter iparam, AnalysisOutParameter oparam)
            throws Exception
    {
        // ランクマップ
        // 出荷数に対し、Aランク、Bランク、Cランクのしきい値を設定します。
        double thA = (new Integer(iparam.getThresholdA()).doubleValue()) / 100D;
        double thB = (new Integer(iparam.getThresholdB()).doubleValue()) / 100D;

        AbcChartCreater abcChartCreater = new AbcChartCreater(thA, thB);

        String anaType = iparam.getAnalysisType();

        // 左Ｙ軸のタイトルと折れ線名称
        if (anaType.equals(DispResources.getText("RDB-W1201")))
        {
            abcChartCreater.setLeftAxisTitle(DispResources.getText("RDB-W1201"));
            abcChartCreater.setLineName(DispResources.getText("RDB-W1201"));
        }
        else
        {
            abcChartCreater.setLeftAxisTitle(DispResources.getText("LBL-W1206"));
            abcChartCreater.setLineName(DispResources.getText("LBL-W1206"));
        }
        // 右Ｙ軸のタイトル
        abcChartCreater.setRightAxisTitle(DispResources.getText("LBL-W1215"));
        // 右Ｙ軸のタイトル角度(逆方向に90度回転させる)
        abcChartCreater.setRightAxisTitleAngle(-90);
        // ランクＡのタイトル
        abcChartCreater.setRankAName(DispResources.getText("LBL-W1209"));
        // ランクＢのタイトル
        abcChartCreater.setRankBName(DispResources.getText("LBL-W1210"));
        // ランクＣのタイトル
        abcChartCreater.setRankCName(DispResources.getText("LBL-W1211"));
        // 右Ｙ軸を表示
        abcChartCreater.setRightAxisVisible(true);

        // アイテム数分、ループを行う
        for (int i = 0; i < oparam.getSizeOfAbcAnaList(); i++)
        {
            if (anaType.equals(DispResources.getText("RDB-W1201")))
            {
                // 作業数
                double workingQty = oparam.getWorkingQtyFromAbcAnaList(i);

                abcChartCreater.addItem(workingQty);
            }
            else
            {
                // 出荷回数
                double shippingCnt = oparam.getShippingCntFromAbcAnaList(i);

                abcChartCreater.addItem(shippingCnt);
            }
        }

        // JFreeChart オブジェクトを生成
        JFreeChart chart = abcChartCreater.createChart();

        return chart;
    }
}
