// $Id: RemoteConfigureForm.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Rectangle ;

import javax.swing.JButton ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JTextField ;


/**
 * サーバ接続設定画面のフォームクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/15</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class RemoteConfigureForm
		extends JPanel
{

	/** <code>jLabel</code> */
	private JLabel jLabel = null ;

	/** <code>jLabel1</code> */
	private JLabel jLabel1 = null ;

	/** <code>addressField</code> */
	private JTextField addressField = null ;

	/** <code>rootField</code> */
	private JTextField rootField = null ;

	/** <code>saveBtn</code> */
	private JButton saveBtn = null ;

	/** <code>backBtn</code> */
	private JButton backBtn = null ;

	/**
	 * This method initializes 
	 * 
	 */
	public RemoteConfigureForm()
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
		this.setSize(new Dimension(426, 181)) ;
		jLabel1 = new JLabel() ;
		jLabel1.setBounds(new Rectangle(60, 55, 88, 20)) ;
		jLabel1.setText("\u4FDD\u5B58\u5834\u6240") ;
		jLabel = new JLabel() ;
		jLabel.setBounds(new Rectangle(60, 30, 88, 20)) ;
		jLabel.setText("IPアドレス") ;
		this.setLayout(null) ;
		this.setBackground(new Color(204, 204, 255)) ;
		this.add(jLabel, null) ;
		this.add(jLabel1, null) ;
		this.add(getAddressField(), null) ;
		this.add(getRootField(), null) ;
		this.add(getSaveBtn(), null) ;
		this.add(getBackBtn(), null) ;
	}

	/**
	 * This method initializes addressField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getAddressField()
	{
		if (addressField == null)
		{
			addressField = new JTextField() ;
			addressField.setBounds(new Rectangle(155, 30, 140, 20)) ;
			addressField.setText("") ;
		}
		return addressField ;
	}

	/**
	 * This method initializes rootField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getRootField()
	{
		if (rootField == null)
		{
			rootField = new JTextField() ;
			rootField.setBounds(new Rectangle(155, 54, 140, 20)) ;
			rootField.setText("") ;
		}
		return rootField ;
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
			saveBtn.setBounds(new Rectangle(178, 113, 72, 23)) ;
			saveBtn.setBackground(new Color(255, 204, 153)) ;
			saveBtn.setText("\u4FDD\u5B58") ;
		}
		return saveBtn ;
	}

	/**
	 * This method initializes backBtn
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getBackBtn()
	{
		if (backBtn == null)
		{
			backBtn = new JButton() ;
			backBtn.setBounds(new Rectangle(284, 113, 74, 23)) ;
			backBtn.setBackground(new Color(255, 204, 153)) ;
			backBtn.setText("\u623B\u308B") ;
		}
		return backBtn ;
	}

}
