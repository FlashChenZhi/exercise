// $Id: LoginForm.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui.form ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.GridBagConstraints ;
import java.awt.Rectangle ;

import javax.swing.JButton ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JPasswordField ;
import javax.swing.JTextField ;
import java.awt.Color ;


/**
 * LoginForm class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/08/04</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class LoginForm
		extends JPanel
{

	/** <code>jLabel</code> */
	private JLabel jLabel = null ;

	/** <code>txtUserId</code> */
	private JTextField txtUserId = null ;

	/** <code>jLabel1</code> */
	private JLabel jLabel1 = null ;

	/** <code>txtPassword</code> */
	private JPasswordField txtPassword = null ;

	/** <code>btnLogin</code> */
	private JButton btnLogin = null ;

	/** <code>btnEnd</code> */
	private JButton btnEnd = null ;

	/**
	 * This is the default constructor
	 */
	public LoginForm()
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
		jLabel1 = new JLabel() ;
		jLabel1.setBounds(new Rectangle(33, 69, 86, 16)) ;
		jLabel1.setText("\u30D1\u30B9\u30EF\u30FC\u30C9\uFF1A") ;
		GridBagConstraints gridBagConstraints = new GridBagConstraints() ;
		gridBagConstraints.gridx = 0 ;
		gridBagConstraints.gridy = 0 ;
		jLabel = new JLabel() ;
		jLabel.setText("\u30E6\u30FC\u30B6\uFF29\uFF24\uFF1A") ;
		jLabel.setBounds(new Rectangle(33, 31, 85, 16)) ;
		this.setSize(300, 200) ;
		this.setLayout(null) ;
		this.setBackground(new Color(204, 204, 255)) ;
		this.add(jLabel, gridBagConstraints) ;
		this.add(getTxtUserId(), null) ;
		this.add(jLabel1, null) ;
		this.add(getTxtPassword(), null) ;
		this.add(getBtnLogin(), null) ;
		this.add(getBtnEnd(), null) ;
	}

	/**
	 * This method initializes txtUserId
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getTxtUserId()
	{
		if (txtUserId == null)
		{
			txtUserId = new JTextField() ;
			txtUserId.setBounds(new Rectangle(136, 29, 103, 23)) ;
		}
		return txtUserId ;
	}

	/**
	 * This method initializes txtPassword
	 * 
	 * @return javax.swing.JPasswordField
	 */
	public JPasswordField getTxtPassword()
	{
		if (txtPassword == null)
		{
			txtPassword = new JPasswordField() ;
			txtPassword.setBounds(new Rectangle(136, 67, 104, 22)) ;
		}
		return txtPassword ;
	}

	/**
	 * This method initializes btnLogin
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnLogin()
	{
		if (btnLogin == null)
		{
			btnLogin = new JButton() ;
			btnLogin.setBounds(new Rectangle(33, 125, 90, 25)) ;
			btnLogin.setBackground(new Color(255, 204, 153)) ;
			btnLogin.setText("\u30ED\u30B0\u30A4\u30F3") ;
		}
		return btnLogin ;
	}

	/**
	 * This method initializes btnEnd
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnEnd()
	{
		if (btnEnd == null)
		{
			btnEnd = new JButton() ;
			btnEnd.setBounds(new Rectangle(164, 125, 90, 25)) ;
			btnEnd.setBackground(new Color(255, 204, 153)) ;
			btnEnd.setText("\u7D42\u4E86") ;
		}
		return btnEnd ;
	}

}
