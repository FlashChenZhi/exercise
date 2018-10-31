package jp.co.daifuku.wms.base.util;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * ABC チャートを作成する為のユーティリティです。
 * @author K.Fukumori
 */
public class AbcChartCreater
        implements Comparator
{
    /** 入力データを保持するリスト */
    private List _dataList;

    // 集計用の変数
    /** 入力されたデータの総計値 */
    private double _totalValue;

    /** ランクＡのしきい値 [%] */
    private double _thresholdA;

    /** ランクＢのしきい値 [%] */
    private double _thresholdB;

    /** ランクＡ実データのしきい値 */
    private double _thresholdARealValue;

    /** ランクＢ実データのしきい値 */
    private double _thresholdBRealValue;

    /** ランクＡのアイテム数 */
    private int _rankACount;

    /** ランクＢのアイテム数 */
    private int _rankBCount;

    // グラフ設定項目
    /** 左軸タイトル名 */
    private String _leftYAxisTitle = "";

    /** 左軸タイトル角度 */
    private int _leftYAngle = 0;

    /** 右軸タイトル名 */
    private String _rightYAxisTitle = "";

    /** 右軸タイトル角度 */
    private int _rightYAngle = 0;

    /** 折れ線名称 */
    private String _strLineName = "";

    /** ランクA名称 */
    private String _strRankA = "";

    /** ランクB名称 */
    private String _strRankB = "";

    /** ランクC名称 */
    private String _strRankC = "";

    /** グラフタイトル */
    private String _graphTitle = "";

    /** 右側のＹ軸の表示フラグ */
    private boolean _rightYAxisVisible = true;


    /**
     * デフォルトコンストラクタは無効。
     */
    private AbcChartCreater()
    {
        this.clear();
    }

    /**
     * コンストラクタ。
     * @param thresholdA ランクAのしきい値
     * @param thresholdB ランクBのしきい値
     */
    public AbcChartCreater(double thresholdA, double thresholdB)
    {
        this.clear();
        this._thresholdA = thresholdA;
        this._thresholdB = thresholdB;
    }

    /**
     * グラフのタイトルを設定します。
     * @param graphTitle グラフタイトル
     */
    public void setGraphTitle(String graphTitle)
    {
        this._graphTitle = graphTitle;
    }

    /**
     * 左側のＹ軸のタイトルを設定します。
     * @param leftYAxisTitle 左側のＹ軸のタイトル
     */
    public void setLeftAxisTitle(String leftYAxisTitle)
    {
        this._leftYAxisTitle = leftYAxisTitle;
    }

    /**
     * 左側のＹ軸のタイトル角度を設定します。
     * @param leftYAngle 左側のＹ軸のタイトル角度
     */
    public void setLeftAxisTitleAngle(int leftYAngle)
    {
        this._leftYAngle = leftYAngle;
    }

    /**
     * 右側のＹ軸のタイトルを設定します。
     * @param rightYAxisTitle 右側のＹ軸のタイトル
     */
    public void setRightAxisTitle(String rightYAxisTitle)
    {
        this._rightYAxisTitle = rightYAxisTitle;
    }

    /**
     * 右側のＹ軸のタイトル角度を設定します。
     * @param rightYAngle 右側のＹ軸のタイトル角度
     */
    public void setRightAxisTitleAngle(int rightYAngle)
    {
        this._rightYAngle = rightYAngle;
    }

    /**
     * 折れ線名称を設定します。
     * @param strLineName 折れ線名称
     */
    public void setLineName(String strLineName)
    {
        this._strLineName = strLineName;
    }

    /**
     * ランクＡ名称を設定します。
     * @param strRankA ランクＡ名称
     */
    public void setRankAName(String strRankA)
    {
        this._strRankA = strRankA;
    }

    /**
     * ランクＢ名称を設定します。
     * @param strRankB ランクＢ名称
     */
    public void setRankBName(String strRankB)
    {
        this._strRankB = strRankB;
    }

    /**
     * ランクＣ名称を設定します。
     * @param strRankC ランクＣ名称
     */
    public void setRankCName(String strRankC)
    {
        this._strRankC = strRankC;
    }

    /**
     * 右側のＹ軸の表示・非表示を設定します。
     * @param rightYAxisVisible true のとき表示
     */
    public void setRightAxisVisible(boolean rightYAxisVisible)
    {
        this._rightYAxisVisible = rightYAxisVisible;
    }

    /**
     * クリア処理。
     */
    public void clear()
    {
        _dataList = new ArrayList();

        _thresholdA = 0;
        _thresholdB = 0;
        _thresholdARealValue = 0;
        _thresholdBRealValue = 0;
        _rankACount = 0;
        _rankBCount = 0;
        _graphTitle = "";
        _rightYAxisTitle = "";
        _rightYAngle = 0;
        _leftYAxisTitle = "";
        _leftYAngle = 0;
        _strLineName = "";
        _strRankA = "";
        _strRankB = "";
        _strRankC = "";
        _rightYAxisVisible = true;
        _totalValue = 0D;
    }


    /**
     * プロットするアイテムの追加。
     * @param value アイテムの値
     */
    public void addItem(double value)
    {
        // 総計を計算
        _totalValue += value;

        // アイテムをリストに追加
        _dataList.add(new Double(value));
    }

    /**
     * GeneralChart用のABC Chartを作成。
     * @return ABC Chart
     */
    public JFreeChart createChart()
    {
        List graphDataList = analysis();
        // プロットする為のデータを作成
        XYSeries xyseries = new XYSeries(_strLineName);
        Iterator itr = graphDataList.iterator();
        int i = 0;
        while (itr.hasNext())
        {
            double y = ((Double)itr.next()).doubleValue();
            double x = i * 1.0;
            // プロット点の追加
            xyseries.add(x, y);
            i++;
        }

        // コレクションにする
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);

        // データセットよりチャートオブジェクトの作成
        JFreeChart chart = ChartFactory.createXYLineChart(_graphTitle, // グラフタイトル
                "", // X 軸名
                "", // Y 軸名
                xyseriescollection, // データ
                PlotOrientation.VERTICAL, // プロットタイプ
                true, // 凡例
                false, // ツールチップ
                false); // クリッカブル

        // プロットオブジェクトの取得
        XYPlot xyplot = (XYPlot)chart.getPlot();


        // A ランクのエリア描画
        XYAreaRenderer ARenderer =
                createRankAreaRenderer(xyplot, _strRankA, _rankACount, _thresholdARealValue, 1, new Color(255, 180, 0));

        // B ランクのエリア描画
        XYAreaRenderer BRenderer =
                createRankAreaRenderer(xyplot, _strRankB, _rankACount + _rankBCount, _thresholdBRealValue, 2, new Color(
                        255, 230, 20));

        // C ランクのエリア描画
        XYAreaRenderer CRenderer =
                createRankAreaRenderer(xyplot, _strRankC, graphDataList.size() - 1, _totalValue, 3, new Color(250, 250,
                        150));

        // X 軸の設定
        ValueAxis axis = xyplot.getDomainAxis();
        axis.setUpperMargin(0); // 右マージン
        axis.setLowerMargin(0); // 左マージン
        axis.setVisible(false); // 非表示

        //グラフの左側のY軸の設定
        NumberAxis rangeAxisL = (NumberAxis)xyplot.getRangeAxis();
        rangeAxisL.setLabel(_leftYAxisTitle); // 軸ラベルの文字列
        rangeAxisL.setLabelFont(new Font("Default", 0, 14)); // 軸ラベルのフォント
        rangeAxisL.setLabelAngle(_leftYAngle / 180.0 * Math.PI); // ラベルの角度を設定
        rangeAxisL.setUpperBound(_totalValue); // 軸の最大値

        if (_rightYAxisVisible)
        {
            // グラフの右側のY軸の設定
            NumberAxis rangeAxisR = new NumberAxis(_rightYAxisTitle);
            rangeAxisR.setLabelFont(new Font("Default", 0, 14)); // 軸ラベルのフォント
            rangeAxisR.setLabelAngle(_rightYAngle / 180.0 * Math.PI); // 軸ラベルの角度
            rangeAxisR.setUpperBound(100D); // 軸の最大値
            // グラフ関連付ける
            xyplot.setRangeAxis(1, rangeAxisR);
        }

        return chart;
    }

    /**
     * ランクのエリアを作成する
     * @param xyplot グラフの描画（プロット）オブジェクト
     * @param title タイトル
     * @param rankCount ランクのトータル数
     * @param value ランクの最大値
     * @param rendererNum レンダラのインデックス
     * @param color 色
     * @return レンダラー
     */
    private XYAreaRenderer createRankAreaRenderer(XYPlot xyplot, String title, int rankCount, double value,
            int rendererNum, Color color)
    {
        // エリアのデータセットを作成
        XYSeries rankSeries = new XYSeries(title);
        rankSeries.add(0D, value);
        rankSeries.add(rankCount, value);
        XYSeriesCollection rankArea = new XYSeriesCollection(rankSeries);

        // ランクのエリアをレンダラーで追加
        XYAreaRenderer rankRenderer = new XYAreaRenderer();
        xyplot.setDataset(rendererNum, rankArea);
        xyplot.setRenderer(rendererNum, rankRenderer);
        xyplot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

        // 表示色をセット
        rankRenderer.setSeriesPaint(0, color);

        return rankRenderer;
    }


    /**
     * セットされたデータの集計を行う。
     * @return 集計結果を格納したList
     */
    private List analysis()
    {
        List graphDataList = new ArrayList();
        // ゼロ点の追加
        graphDataList.add(new Double(0));

        // 累積値
        double accumulationValue = 0D;

        for (int i = 0; i < _dataList.size(); i++)
        {
            // 累積数を計算
            double value = ((Double)(_dataList.get(i))).doubleValue();
            accumulationValue += value;

            // 累積数をグラフ用のリストに追加
            graphDataList.add(new Double(accumulationValue));

            // ランク分け
            if (accumulationValue / _totalValue <= _thresholdA)
            {
                _rankACount++;
                if (_thresholdARealValue < accumulationValue)
                {
                    _thresholdARealValue = accumulationValue;
                }
            }
            else if (accumulationValue / _totalValue <= _thresholdB)
            {
                _rankBCount++;
                if (_thresholdBRealValue < accumulationValue)
                {
                    _thresholdBRealValue = accumulationValue;
                }
            }
        }

        return graphDataList;
    }

    /**
     * ソート用の比較関数。　Arrays.sort を実行する為に実装。
     * @param o1 オブジェクト１
     * @param o2 オブジェクト２
     * @return 比較結果
     */
    public int compare(Object o1, Object o2)
    {
        Double data1 = (Double)o1;
        Double data2 = (Double)o2;

        return data2.compareTo(data1);
    }

    /**
     * セットされたデータの並べ替えを行います。
     */
    public void sort()
    {
        // Comparator によりソート
        Object[] oa = _dataList.toArray();
        Arrays.sort(oa, this);

        // リストに再セット
        _dataList.clear();
        for (int i = 0; i < oa.length; i++)
        {
            _dataList.add(oa[i]);
        }
    }
}
