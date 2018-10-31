package jp.co.daifuku.wms.base.util.labeltool.base.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import jp.co.daifuku.wms.base.util.labeltool.gui.form.LabelInfoTableModel;


/**
 * DaiDefaultCellRenderer class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/13</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class DaiDefaultCellRenderer
        extends DefaultTableCellRenderer
{

    /* (非 Javadoc)
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
    {
        // サーバ側新しいバージョンがある場合
        if (table.getValueAt(row, LabelInfoTableModel.STATUS).equals("1"))
        {
            this.setBackground(Color.GREEN);
        }
        // サーバ側論理削除された場合
        else if (table.getValueAt(row, LabelInfoTableModel.STATUS).equals("9"))
        {
            this.setBackground(Color.LIGHT_GRAY);
        }
        // ローカル側レイアウト変更がある場合
        else if (table.getValueAt(row, LabelInfoTableModel.STATUS).equals("2"))
        {
            this.setBackground(Color.PINK);
        }
        // サーバ側レイアウトがない場合
        else if (table.getValueAt(row, LabelInfoTableModel.STATUS).equals("-1"))
        {
            this.setBackground(Color.RED);
        }
        else
        {
            this.setBackground(table.getBackground());
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
