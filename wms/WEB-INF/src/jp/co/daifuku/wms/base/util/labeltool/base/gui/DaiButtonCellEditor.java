// $Id: DaiButtonCellEditor.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.gui;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import jp.co.daifuku.wms.base.util.labeltool.base.entity.Authority;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.LabelInfoTableModel;


/**
 * ラベル情報一覧テーブルの値のエディタです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/05</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class DaiButtonCellEditor
        extends AbstractCellEditor
        implements TableCellEditor
{
    /** <code>editButton</code> */
    private JButton editButton = null;

    /**
     * このクラスのコンストラクタです。
     * 
     */
    public DaiButtonCellEditor()
    {
        editButton = new JButton();
    }

    /**
     * ボタンのActionListenerを追加するメソッドです。
     * @param l ActionListener
     */
    public void addActionListener(ActionListener l)
    {
        editButton.addActionListener(l);
    }

    /**
     * エディタの初期値 value を設定します。
     * @param table 編集するエディタを照会する JTable。 null も可
     * @param value 編集されるセルの値。 値を解釈および描画する方法はエディタによって異なる。 <br>
     *              たとえば、値が文字列 "true" の場合は文字列として描画される。 <br>
     *              またはチェックされたチェックボックスとして描画される。null も有効な値
     * @param isSelected セルがハイライトで描画されている場合は true
     * @param row 編集されるセルの行
     * @param column 編集されるセルの列 
     * @return 編集のためのコンポーネント
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
            int row, int column)
    {
        String status = (String)table.getValueAt(row, LabelInfoTableModel.STATUS);
        if (column == LabelInfoTableModel.UPDATE)
        {
            if (status.equals("9"))
            {
                // 論理削除された場合、ボタン文字を「回復」とする。
                editButton.setText("\u56DE\u5FA9");
                editButton.setActionCommand("RECOVER");
            }
            else
            {
                // それ以外場合、ボタン文字を「変更」とする。
                editButton.setText("\u5909\u66F4");
                editButton.setActionCommand("UPDATE");
            }
            if (status.equals("1")
                    || (status.equals("9") && Authority.$level.equals(Authority.USER_LEVEL))
                    || status.equals("-1"))
            {
                // サーバ側新しいバージョンがある場合、
                // 又はユーザモードで論理削除状態場合、変更と回復不可とする。
                editButton.setEnabled(false);
            }
            else
            {
                editButton.setEnabled(true);
            }
        }
        if (column == LabelInfoTableModel.DELETE)
        {
            if (status.equals("9"))
            {
                editButton.setText("\u7269\u7406\u524A\u9664");
                editButton.setActionCommand("REMOVE");
            }
            else
            {
                editButton.setText("\u524A\u9664");
                editButton.setActionCommand("LOGICREMOVE");
            }
            if ((status.equals("9") && Authority.$level.equals(Authority.USER_LEVEL))
                    || status.equals("-1"))
            {
                editButton.setEnabled(false);
            }
            else
            {
                editButton.setEnabled(true);
            }
        }
        if (column == LabelInfoTableModel.AUTO_PRINT_SETTING)
        {
            editButton.setText("\u81EA\u52D5\u767A\u884C\u8A2D\u5B9A");
            if (Authority.$level.equals(Authority.USER_LEVEL)
                    || status.equals("9")
                    || status.equals("-1"))
            {
                editButton.setEnabled(false);
            }
            else
            {
                editButton.setEnabled(true);
            }
        }
        return editButton;
    }

    /**
     * エディタに保持された値を返します。
     * @return エディタに保持された値

     */
    public Object getCellEditorValue()
    {
        return null;
    }

}
