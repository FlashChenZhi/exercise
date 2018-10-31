// $$Id: LabelRegistForm.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Dimension ;
import java.awt.Rectangle ;

import javax.swing.JButton ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JTextField ;
import java.awt.Color ;


/**
 * ラベル登録画面のフォームクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/10</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelRegistForm
		extends JPanel
{

	/** <code>jLabel</code>のコメント */
	private JLabel jLabel1 = null ;

	/** <code>jLabel2</code>のコメント */
	private JLabel jLabel2 = null ; //  @jve:decl-index=0:visual-constraint="9,248"

	/** <code>jLabel3</code>のコメント */
	private JLabel jLabel3 = null ;

	/** <code>idField</code>のコメント */
	private JTextField idField = null ;

	/** <code>nameField</code>のコメント */
	private JTextField nameField = null ;

	/** <code>layoutNameField</code>のコメント */
	private JTextField layoutNameField = null ;

	/** <code>modBtn</code>のコメント */
	private JButton loadBtn = null ;

	/** <code>entryBtn</code>のコメント */
	private JButton entryBtn = null ;

	/** <code>endBtn</code>のコメント */
	private JButton endBtn = null ;

	/**
	 * This method initializes 
	 * 
	 */
	public LabelRegistForm()
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
		this.setSize(new Dimension(589, 225)) ;
		this.setLayout(null) ;
		this.setBackground(new Color(204, 204, 255)) ;
		this.add(getJLabel1(), null) ;
		this.add(getJLabel2(), null) ;
		this.add(getJLabel3(), null) ;
		this.add(getIdField(), null) ;
		this.add(getNameField(), null) ;
		this.add(getLayoutNameField(), null) ;
		this.add(getLoadBtn(), null) ;
		this.add(getEntryBtn(), null) ;
		this.add(getEndBtn(), null) ;

		this.add(getJLabel1(), null) ;
		this.add(getLoadBtn(), null) ;
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
			jLabel1.setText("\u7BA1\u7406No") ;
			jLabel1.setBounds(new Rectangle(18, 30, 81, 20)) ;
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
			jLabel2.setBounds(new Rectangle(19, 65, 81, 20)) ;
			jLabel2.setText("\u30E9\u30D9\u30EB\u540D\u79F0") ;
		}
		return jLabel2 ;
	}

	/**
	 * This method initializes jLabel3   
	 *  
	 * @return javax.swing.JLabel   
	 */
	public JLabel getJLabel3()
	{
		if (jLabel3 == null)
		{
			jLabel3 = new JLabel() ;
			jLabel3.setBounds(new Rectangle(19, 100, 151, 20)) ;
			jLabel3.setText("\u30EC\u30A4\u30A2\u30A6\u30C8\u30D5\u30A1\u30A4\u30EB\u540D") ;
		}
		return jLabel3 ;
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
			idField.setBounds(new Rectangle(197, 29, 67, 20)) ;
			idField.setEditable(false) ;
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
			nameField.setBounds(new Rectangle(197, 64, 234, 20)) ;
		}
		return nameField ;
	}

	/**
	 * This method initializes jTextField2  
	 *  
	 * @return javax.swing.JTextField   
	 */
	public JTextField getLayoutNameField()
	{
		if (layoutNameField == null)
		{
			layoutNameField = new JTextField() ;
			layoutNameField.setBounds(new Rectangle(198, 99, 234, 20)) ;
			layoutNameField.setEditable(false) ;
		}
		return layoutNameField ;
	}

	/**
	 * This method initializes jButton  
	 *  
	 * @return javax.swing.JButton  
	 */
	public JButton getLoadBtn()
	{
		if (loadBtn == null)
		{
			loadBtn = new JButton() ;
			loadBtn.setText("\u53D6\u8FBC\u307F") ;
			loadBtn.setBackground(new Color(255, 204, 153)) ;
			loadBtn.setBounds(new Rectangle(450, 98, 125, 20)) ;
		}
		return loadBtn ;
	}

	/**
	 * This method initializes jButton2 
	 *  
	 * @return javax.swing.JButton  
	 */
	public JButton getEntryBtn()
	{
		if (entryBtn == null)
		{
			entryBtn = new JButton() ;
			entryBtn.setBounds(new Rectangle(310, 160, 85, 20)) ;
			entryBtn.setBackground(new Color(255, 204, 153)) ;
			entryBtn.setText("\u767B\u9332") ;
		}
		return entryBtn ;
	}

	/**
	 * This method initializes jButton3 
	 *  
	 * @return javax.swing.JButton  
	 */
	public JButton getEndBtn()
	{
		if (endBtn == null)
		{
			endBtn = new JButton() ;
			endBtn.setBounds(new Rectangle(400, 160, 85, 20)) ;
			endBtn.setBackground(new Color(255, 204, 153)) ;
			endBtn.setText("\u623B\u308B") ;
		}
		return endBtn ;
	}
}
