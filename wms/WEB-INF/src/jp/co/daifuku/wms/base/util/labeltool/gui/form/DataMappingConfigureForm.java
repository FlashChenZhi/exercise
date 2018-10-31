// $$Id: DataMappingConfigureForm.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Rectangle ;

import javax.swing.DefaultCellEditor ;
import javax.swing.JButton ;
import javax.swing.JCheckBox ;
import javax.swing.JComboBox ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.JTextField ;

/**
 * ラベル印字項目とDB項目を関連する画面フォームです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/14</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class DataMappingConfigureForm
		extends JPanel
{

	/** <code>jLabel</code> */
	private JLabel jLabel = null ;

	/** <code>jLabel1</code> */
	private JLabel jLabel1 = null ;

	/** <code>idField</code> */
	private JTextField idField = null ;

	/** <code>nameField</code> */
	private JTextField nameField = null ;

	/** <code>jLabel2</code> */
	private JLabel jLabel2 = null ;

	/** <code>tableCombox</code> */
	private JComboBox tableCombox = null ;

	/** <code>saveBtn</code> */
	private JButton saveBtn = null ;

	/** <code>deleteBtn</code> */
	private JButton deleteBtn = null ;

	/** <code>endBtn</code> */
	private JButton endBtn = null ;

	/** <code>jScrollPane</code> */
	private JScrollPane jScrollPane = null ;

	/** <code>jTable</code> */
	private JTable jTable = null ;

	/** <code>JCheckBox</code> */
	private JCheckBox jCheckBox = null ;

	/**
	 * This method initializes this
	 * 
	 */
	public DataMappingConfigureForm()
	{
		super() ;
		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize()
	{
		this.setSize(new Dimension(502, 404)) ;
		this.setLayout(null) ;
		this.setBackground(new Color(204, 204, 255)) ;
		getJCheckBox().setBackground(new Color(204, 204, 255)) ;
		this.add(getJLabel(), null) ;
		this.add(getJLabel1(), null) ;
		this.add(getIdField(), null) ;
		this.add(getNameField(), null) ;
		this.add(getJLabel2(), null) ;
		this.add(getTableCombox(), null) ;
		this.add(getJCheckBox(), null) ;
		this.add(getDeleteBtn(), null) ;
		this.add(getSaveBtn(), null) ;
		this.add(getEndBtn(), null) ;
		this.add(getJScrollPane(), null) ;
	}

	/**
	 * This method initializes jLabel   
	 *  
	 * @return javax.swing.JLabel   
	 */
	public JLabel getJLabel()
	{
		if (jLabel == null)
		{
			jLabel = new JLabel() ;
			jLabel.setBounds(new Rectangle(60, 30, 88, 20)) ;
			jLabel.setText("\u7BA1\u7406No") ;
		}
		return jLabel ;
	}

	/**
	 * This method initializes jLabel1   
	 *  
	 * @return javax.swing.JLabel   
	 */
	public JLabel getJLabel1()
	{
		if (jLabel1 == null)
		{
			jLabel1 = new JLabel() ;
			jLabel1.setBounds(new Rectangle(60, 55, 88, 20)) ;
			jLabel1.setText("\u30E9\u30D9\u30EB\u540D") ;
		}
		return jLabel1 ;
	}

	/**
	 * This method initializes jLabel2   
	 *  
	 * @return javax.swing.JLabel   
	 */
	public JLabel getJLabel2()
	{
		if (jLabel2 == null)
		{
			jLabel2 = new JLabel() ;
			jLabel2.setBounds(new Rectangle(60, 105, 100, 20)) ;
			jLabel2.setText("\u95A2\u9023\u30C6\u30FC\u30D6\u30EB\u540D") ;
		}
		return jLabel2 ;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getIdField()
	{
		if (idField == null)
		{
			idField = new JTextField() ;
			idField.setBounds(new Rectangle(150, 30, 80, 20)) ;
			idField.setEnabled(false) ;
			idField.setEditable(false) ;
			idField.setText("") ;
		}
		return idField ;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getNameField()
	{
		if (nameField == null)
		{
			nameField = new JTextField() ;
			nameField.setBounds(new Rectangle(150, 55, 213, 20)) ;
			nameField.setEnabled(false) ;
			nameField.setEditable(false) ;
			nameField.setText("") ;
		}
		return nameField ;
	}

	/**
	 * This method initializes JCheckBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JCheckBox getJCheckBox()
	{
		if (jCheckBox == null)
		{
			jCheckBox = new JCheckBox("\u81EA\u52D5\u767A\u884C\u7121\u52B9") ;
			jCheckBox.setBounds(new Rectangle(146, 80, 132, 20)) ;
		}
		return jCheckBox ;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getTableCombox()
	{
		if (tableCombox == null)
		{
			tableCombox = new JComboBox() ;
			tableCombox.setBounds(new Rectangle(150, 105, 213, 20)) ;
		}
		return tableCombox ;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getDeleteBtn()
	{
		if (deleteBtn == null)
		{
			deleteBtn = new JButton() ;
			deleteBtn.setBounds(new Rectangle(250, 335, 72, 20)) ;
			deleteBtn.setBackground(new Color(255, 204, 153)) ;
			deleteBtn.setText("\u524A\u9664") ;
		}
		return deleteBtn ;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getSaveBtn()
	{
		if (saveBtn == null)
		{
			saveBtn = new JButton() ;
			saveBtn.setBounds(new Rectangle(130, 335, 72, 20)) ;
			saveBtn.setBackground(new Color(255, 204, 153)) ;
			saveBtn.setText("\u4FDD\u5B58") ;
		}
		return saveBtn ;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getEndBtn()
	{
		if (endBtn == null)
		{
			endBtn = new JButton() ;
			endBtn.setBounds(new Rectangle(370, 335, 72, 20)) ;
			endBtn.setBackground(new Color(255, 204, 153)) ;
			endBtn.setText("\u623B\u308B") ;
		}
		return endBtn ;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane() ;
			jScrollPane.setBounds(new Rectangle(60, 140, 310, 165)) ;
			jScrollPane.setViewportView(getJTable()) ;
		}
		return jScrollPane ;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	public JTable getJTable()
	{
		if (jTable == null)
		{
			jTable = new JTable(new DataMappingTableModel(null)) ;
			jTable.getTableHeader().setBackground(Color.LIGHT_GRAY) ;
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getTableHeader().setResizingAllowed(false) ;
			jTable.setRowHeight(20) ;
			jTable.setBackground(Color.WHITE) ;
			JComboBox c = new JComboBox() ;
			c.addItem("") ;
			jTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(c)) ;
			jTable.setRowHeight(18) ;
			jTable.repaint() ;

		}
		return jTable ;
	}

}
