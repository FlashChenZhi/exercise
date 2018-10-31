// $Id: ReadOnlyTableModel.java 5527 2009-11-09 08:03:43Z ota $
package jp.co.daifuku.wms.base.util.timekeeper.util;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to
 * license terms.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import jp.co.daifuku.wms.base.util.timekeeper.JobParam;

/**
 * 編集不可テーブルモデルクラスです。<br>
 * 
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%"> <caption><font
 * size="+1"><b>Update History</b></font></caption> <tbody><tr bgcolor="#CCCCFF"
 * class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 * 
 * <!-- 変更履歴 --> <tr><td nowrap>2006/10/31</td><td nowrap>Softecs</td> <td>Class
 * created.</td></tr>
 * 
 * </tbody></table><hr>
 * 
 * @version $Revision: 5527 $, $Date: 2009-11-09 17:03:43 +0900 (月, 09 11 2009) $
 * @author Softecs
 * @author Last commit: $Author: ota $
 */

public class ReadOnlyTableModel
        extends DefaultTableModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 1L;

    /** <code>SPLIT_FIELD</code><br> 時間間隔フィールド分割文字列 */
    private static final String SPLIT_FIELD = " |\t";

    /** 削除ボタンのカラム位置 */
    private static final int BUTTON_COLUMN = 0;

    /** 有効チェックボックスのカラム位置 */
    private static int BOOLEAN_COLUMN = 1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // デフォルトのテーブルヘッダ
    // TimeKeeperUtilクラスのリソースより取得します。
    private static final String[] COLUMNS_HEADER = {
        Resource.getString("TimeKeeperUtil.110"),
        Resource.getString("TimeKeeperUtil.111"),
        Resource.getString("TimeKeeperUtil.099"),
        Resource.getString("TimeKeeperUtil.100"),
        Resource.getString("TimeKeeperUtil.101"),
        Resource.getString("TimeKeeperUtil.102"),
        Resource.getString("TimeKeeperUtil.103"),
        Resource.getString("TimeKeeperUtil.104"),
        Resource.getString("TimeKeeperUtil.105")
    };

    /** 行カラム数 */
    public static final int COLUMNS = COLUMNS_HEADER.length;

    // このモデルが扱うデータリストです。
    // JobParamクラスのインスタンスを扱います。
    private final List<JobParam> _list = new ArrayList<JobParam>();

    // データ変更フラグ
    private boolean _change = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ.
     */
    public ReadOnlyTableModel()
    {
        super(COLUMNS_HEADER, 0);
    }

    /**
     * テーブルのヘッダ文字列を指定してインスタンス化します。
     * 
     * @param headers ヘッダ文字列
     */
    public ReadOnlyTableModel(final String[] headers)
    {
        super(COLUMNS_HEADER, 0);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * セルの編集が可能かどうかをチェックします。
     * このメソッドをオーバーライドして、必ず<code>false</code>を
     * 返す事により、編集不可なJTableを実現します。<br>
     * ただし、削除ボタン、有効チェックボックスのセルに関しては<code>true</code>を
     * 返すようにします。
     */
    @Override
    public boolean isCellEditable(final int row, final int column)
    {
        final boolean buttonColumn = (BUTTON_COLUMN == column);
        final boolean checkColumn = (BOOLEAN_COLUMN == column);
        return buttonColumn || checkColumn;
    }

    /**
     * 内部データを全て初期化します。
     */
    public void clearAll()
    {
        _list.clear();
        setRowCount(0);
        setChange(false);
    }

    /**
     * テーブルの行データを追加します。
     * 
     * @param param JobParamデータ
     */
    public void addRow(final JobParam param)
    {
        _list.add(param);
        final String[] rowData = param2row(param);
        super.addRow(rowData);
    }

    /**
     * テーブルの行データを追加します。
     * 
     * @param params JobParamデータ(配列)
     */
    public void addRow(final JobParam[] params)
    {
        for (final JobParam param : params)
        {
            addRow(param);
        }
    }

    /**
     * テーブルの行データを追加します。
     * 
     * @param param 入力データ(配列)
     */
    public void addRow(final String[] param)
    {
        final JobParam jobParam = row2param(param);
        addRow(jobParam);
    }

    /**
     * 指定された行にデータを挿入します。
     * 
     * @param index 挿入位置
     * @param param 挿入データ
     */
    public void insertRow(final int index, final JobParam param)
    {
        _list.add(index, param);
        final String[] rowData = param2row(param);
        super.insertRow(index, rowData);
    }

    /**
     * 指定された行にデータを挿入します。
     * 
     * @param index 挿入位置
     * @param param 挿入データ
     */
    public void insertRow(final int index, final String[] param)
    {
        final JobParam jobParam = row2param(param);
        insertRow(index, jobParam);
    }

    /**
     * 指定されたインデックスの行データを削除します。
     * 
     * @param index 削除位置
     */
    @Override
    public void removeRow(final int index)
    {
        _list.remove(index);
        super.removeRow(index);
    }

    /**
     * 内部に保持しているデータ数を取得します。
     * 
     * @return データ数
     */
    public int size()
    {
        return _list.size();
    }

    /**
     * 内部に保持しているJobParamデータを取得します。<br>
     * 指定された取得位置にデータが存在しない場合は<code>null</code>を 返します。
     * 
     * @param index 取得位置
     * @return JobParamデータ
     */
    public JobParam getJobParam(final int index)
    {
        // 指定された位置にデータが存在しない場合はnullを返します。
        if ((0 > index) || (size() < index))
        {
            return null;
        }

        final JobParam param = _list.get(index);
        return param;
    }

    /**
     * 内部に保持しているJobParamデータを取得します。
     * 
     * @return JobParamデータ(配列)
     */
    public JobParam[] getJobParams()
    {
        return _list.toArray(new JobParam[_list.size()]);
    }

    /**
     * 新規に指定されたJobParamデータを登録します。
     *
     * @param param JobParamデータ
     * @return 登録されたJobParamデータ
     */
    public JobParam addNewJobParam(JobParam param)
    {
        addRow(param);
        setChange(true);
        return param;
    }

    /**
     * 内部に保持しているJobParamデータを置き換えます。<br>
     * 指定された取得位置にデータが存在しない場合は<code>null</code>を 返します。
     *
     * @param index 置き換え位置
     * @param param JobParamデータ
     * @return 置き換え前のJobParamデータ
     */
    public JobParam replaceJobParam(final int index, JobParam param)
    {
        // 指定された位置にデータが存在しない場合はnullを返します。
        if ((0 > index) || (size() < index))
        {
            return null;
        }

        final JobParam oldParam = getJobParam(index);
        //_list.set(index, param);
        removeRow(index);
        insertRow(index, param);
        setChange(true);
        return oldParam;
    }

    /**
     * インデクスで指定されたJobParamデータを削除します。<br>
     * 指定された取得位置にデータが存在しない場合は<code>null</code>を 返します。
     *
     * @param index インデックス
     * @return 削除されたJobParamデータ
     */
    public JobParam deleteJobParam(final int index)
    {
        // 指定された位置にデータが存在しない場合はnullを返します。
        if ((0 > index) || (size() < index))
        {
            return null;
        }

        final JobParam oldParam = getJobParam(index);
        removeRow(index);
        setChange(true);
        return oldParam;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 保持しているデータが変更されたかどうかチェックします。
     * 
     * @return データが変更されている場合は<code>true</code>を返します。
     */
    public boolean isChange()
    {
        return _change;
    }

    /**
     * 保持しているデータが変更された事を示すプロパティを設定します。
     * 
     * @param change データ変更時は<code>true</code>を設定します。
     */
    public void setChange(final boolean change)
    {
        _change = change;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * JobParamクラスからテーブルに格納するString[]に変換します。
     * 
     * @param param JobParam
     * @return rowに格納する文字列配列
     */
    public String[] param2row(final JobParam param)
    {
        final String[] rowData = new String[COLUMNS];
        Arrays.fill(rowData, "");

        // JobParamよりクラス名、タイミング文字列を取得します。
        final String className = param.getClassName();
        final String intervalStr = param.getInterval().getTimingString();
        final String[] fields = intervalStr.split(SPLIT_FIELD);

        final int paramFirstIdx = 2; // カラム2からパラメータの位置

        int colidx = paramFirstIdx;

        if (1 == fields.length)
        {
            // set second only
            rowData[colidx++] = fields[0];
        }
        else
        {
            // set as crontab
            rowData[colidx++] = "";
            // 取り出した文字列をrow配列に格納します。
            for (String fldStr : fields)
            {
                rowData[colidx++] = fldStr;
            }
        }

        // パラメータが設定されていれば、クラス名の後に追加します。
        StringBuilder b = new StringBuilder(className);
        for (String arg : param.getArgs())
        {
            b.append(" ");
            b.append(arg);
        }
        final int idxClass = COLUMNS - 1;
        rowData[idxClass] = String.valueOf(b);
        return rowData;
    }

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * テーブルに格納するString[]からJobParamクラスに変換します。
     * 
     * @param rowData rowに格納する文字列配列
     * @return JobParam
     */
    private JobParam row2param(final String[] rowData)
    {
        // 指定されたrowデータが規定されているものと違う場合
        // nullを返します。
        if (COLUMNS != rowData.length)
        {
            // 内部エラー
            return null;
        }

        // 設定値よりクラス名、パラメータ、タイミング文字列を取得します。
        final String[] fields = rowData[COLUMNS - 1].split(SPLIT_FIELD);
        final String className = fields[0];
        final String[] args = new String[fields.length - 1];
        for (int i = 0; i < fields.length - 1; i++)
        {
            args[i] = fields[i + 1];
        }
        StringBuilder intervalStr = new StringBuilder();
        for (int i = 0; i < 5; i++)
        {
            if (0 < rowData[i].length())
            {
                if (0 != i)
                {
                    intervalStr.append(" ");
                }
                intervalStr.append(rowData[i]);
            }
        }

        // 取り出した文字列をrow配列に格納します。
        String interval = String.valueOf(intervalStr);
        final JobParam param = new JobParam(interval.trim(), className, args, true);
        return param;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ReadOnlyTableModel.java 5527 2009-11-09 08:03:43Z ota $";
    }
}
