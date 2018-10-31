// $Id: LabelInfoListForm.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Font ;
import java.awt.Point ;
import java.awt.Rectangle ;

import javax.swing.JButton ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.ListSelectionModel ;

/**
 * ラベル情報一覧照会画面です。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/04</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class LabelInfoListForm
		extends JPanel
{

	/** <code>jScrollPane</code>のコメント */
	private JScrollPane jScrollPane = null ;

	/** <code>jTable</code>のコメント */
	private JTable jTable = null ;

	/** <code>syncBtn</code>のコメント */
	private JButton syncBtn = null ;

	/** <code>addNewBtn</code>のコメント */
	private JButton addNewBtn = null ;

	/** <code>endBtn</code>のコメント */
	private JButton endBtn = null ;

	/** <code>configureBtn</code> */
	private JButton configureBtn = null ;

	/** <code>btnRefresh</code> */
	private JButton btnRefresh = null ;

	/**
	 * This is the default constructor
	 */
	public LabelInfoListForm()
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
		this.setLayout(null) ;
		this.setSize(new Dimension(800, 600)) ;
		this.setBackground(new Color(204, 204, 255)) ;
		this.add(getJScrollPane(), null) ;
		this.add(getSyncBtn(), null) ;
		this.add(getAddNewBtn(), null) ;
		this.add(getEndBtn(), null) ;

		this.add(getConfigureBtn(), null) ;
		this.add(getBtnRefresh(), null) ;
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
			jScrollPane.setBounds(new Rectangle(40, 40, 750, 360)) ;
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
			LabelInfoTableModel mt = new LabelInfoTableModel(null) ;
			jTable = new JTable(mt) ;
			jTable.getTableHeader().setBackground(Color.LIGHT_GRAY) ;
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getTableHeader().setResizingAllowed(false) ;
			jTable.setRowHeight(20) ;
			jTable.setBackground(Color.WHITE) ;
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
			jTable.setRowSelectionAllowed(true) ;
			jTable.setFont(new Font("\uff2d\uff33 \u30b4\u30b7\u30c3\u30af", Font.PLAIN, 14)) ;
			jTable.setBounds(new Rectangle(0, 0, 720, 100)) ;
			jTable.setSelectionBackground(new Color(0, 102, 204)) ;
			jTable.setSelectionForeground(Color.white) ;
			jTable.setCellSelectionEnabled(false) ;
		}
		return jTable ;
	}

	/**
	 * This method initializes syncBtn 
	 *  
	 * @return javax.swing.JButton  
	 */
	public JButton getSyncBtn()
	{
		if (syncBtn == null)
		{
			syncBtn = new JButton() ;
			syncBtn.setBackground(new Color(255, 204, 153)) ;
			syncBtn.setLocation(new Point(170, 450)) ;
			syncBtn.setSize(new Dimension(140, 25)) ;
			syncBtn.setText("\u6700\u65B0\u306B\u66F4\u65B0") ;
		}
		return syncBtn ;
	}

	/**
	 * This method initializes addNewBtn 
	 *  
	 * @return javax.swing.JButton  
	 */
	public JButton getAddNewBtn()
	{
		if (addNewBtn == null)
		{
			addNewBtn = new JButton() ;
			addNewBtn.setBackground(new Color(255, 204, 153)) ;
			addNewBtn.setLocation(new Point(320, 450)) ;
			addNewBtn.setSize(new Dimension(140, 25)) ;
			addNewBtn.setText("\u30E9\u30D9\u30EB\u65B0\u898F") ;
		}
		return addNewBtn ;
	}

	/**
	 * This method initializes jButton7 
	 *  
	 * @return javax.swing.JButton  
	 */
	public JButton getEndBtn()
	{
		if (endBtn == null)
		{
			endBtn = new JButton() ;
			endBtn.setBackground(new Color(255, 204, 153)) ;
			endBtn.setLocation(new Point(620, 450)) ;
			endBtn.setSize(new Dimension(140, 25)) ;
			endBtn.setText("\u7D42\u4E86") ;
		}
		return endBtn ;
	}

	/**
	 * This method initializes configureBtn
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getConfigureBtn()
	{
		if (configureBtn == null)
		{
			configureBtn = new JButton() ;
			configureBtn.setBackground(new Color(255, 204, 153)) ;
			configureBtn.setLocation(new Point(470, 450)) ;
			configureBtn.setSize(new Dimension(140, 25)) ;
			configureBtn.setText("\u63A5\u7D9A\u8A2D\u5B9A") ;
		}
		return configureBtn ;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnRefresh()
	{
		if (btnRefresh == null)
		{
			btnRefresh = new JButton() ;
			btnRefresh.setBackground(new Color(255, 204, 153)) ;
			btnRefresh.setLocation(new Point(20, 450)) ;
			btnRefresh.setSize(new Dimension(140, 25)) ;
			btnRefresh.setText("\u540C\u671F\u5316") ;
		}
		return btnRefresh ;
	}
}