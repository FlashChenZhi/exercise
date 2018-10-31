// $Id: DaiButtonCellRenderer.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.gui;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import jp.co.daifuku.wms.base.util.labeltool.base.entity.Authority;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.LabelInfoTableModel;

/**
 * DaiButtonRenderer class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 * 
 * <hr>
 * <table border="1" cellpadding="3" cellspacing="0" width="90%"> <caption><font
 * size="+1"><b>Update History</b></font></caption> <tbody>
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td>
 * <td><b>Author</b></td>
 * <td><b>Comment</b></td>
 * </tr>
 * 
 * <!-- 変更履歴 -->
 * <tr>
 * <td nowrap>2008/05/31</td>
 * <td nowrap>chenjun</td>
 * <td>Class created.</td>
 * </tr>
 * 
 * </tbody></table>
 * <hr>
 * 
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author chenjun
 * @author Last commit: $Author: kumano $
 */

public class DaiButtonCellRenderer
        extends JButton
        implements TableCellRenderer
{

    /**
     * このクラスのコンストラクタです。
     */
    public DaiButtonCellRenderer()
    {
        super();
    }

    /* (非 Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
    {
        String status = (String)table.getValueAt(row, LabelInfoTableModel.STATUS);
        if (column == LabelInfoTableModel.UPDATE)
        {
            if (status.equals("9"))
            {
                this.setText("\u56DE\u5FA9");
            }
            else
            {
                this.setText("\u5909\u66F4");
            }
            if (status.equals("1")
                    || (status.equals("9") && Authority.$level.equals(Authority.USER_LEVEL))
                    || status.equals("-1"))
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }
        }
        if (column == LabelInfoTableModel.DELETE)
        {
            if (status.equals("9"))
            {
                this.setText("\u7269\u7406\u524A\u9664");
            }
            else
            {
                this.setText("\u524A\u9664");
            }
            if ((status.equals("9") && Authority.$level.equals(Authority.USER_LEVEL))
                    || status.equals("-1"))
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }

        }
        if (column == LabelInfoTableModel.AUTO_PRINT_SETTING)
        {
            this.setText("\u81EA\u52D5\u767A\u884C\u8A2D\u5B9A");
            if (Authority.$level.equals(Authority.USER_LEVEL)
                    || status.equals("9")
                    || status.equals("-1"))
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }
        }

        return this;
    }
}
