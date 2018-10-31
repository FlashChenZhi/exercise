// $Id: LoginScr.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.KeyAdapter ;
import java.awt.event.KeyEvent ;
import java.awt.event.KeyListener ;

import jp.co.daifuku.wms.base.util.labeltool.action.LoginAction ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.LoginForm ;


/**
 * ログイン画面のクラスです。
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


public class LoginScr
		extends LoginForm
{
	/** <code>この画面のインスタンス</code> */
	private static LoginScr $scr = null ;

	/** <code>この画面に関する動作処理</code> */
	private LoginAction action = new LoginAction(this) ;

	/** <code>ログインボタン押下イベントリスナー</code> */
	private ActionListener loginBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (action.login())
			{
				ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
			}
			else
			{
				$scr.getTxtUserId().setText("") ;
				$scr.getTxtPassword().setText("") ;
				$scr.getTxtUserId().requestFocusInWindow() ;
			}

		}
	} ;

	/** <code>ユーザID TextBoxのキーボードイベントリスナー</code> */
	private KeyListener txtUserIdKeyListener = new KeyAdapter()
	{
		/**
		 * キーを押しているときに呼び出されます。<br>
		 * ENTキーを押下した時にパスワードTextBoxにフォーカスします。
		 * @param e キーイベント
		 */
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyChar() == (char)10)
			{
				$scr.getTxtPassword().requestFocusInWindow() ;
				$scr.getTxtPassword().selectAll() ;
			}
		}
	} ;

	/** <code>パスワード TextBoxのキーボードイベントリスナー</code> */
	private KeyListener txtPasswordKeyListener = new KeyAdapter()
	{
		/**
		 * キーを押しているときに呼び出されます。<br>
		 * ENTキーを押下した時にパスワードTextBoxにフォーカスします。
		 * @param e キーイベント
		 */
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyChar() == (char)10)
			{
				if (action.login())
				{
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
				}
				else
				{
					$scr.getTxtUserId().setText("") ;
					$scr.getTxtPassword().setText("") ;
					$scr.getTxtUserId().requestFocusInWindow() ;
				}
			}
		}
	} ;


	/** <code>終了ボタン押下イベントリスナー</code> */
	private ActionListener endBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0) ;
		}
	} ;

	/**
	 * この画面のコンストラクタ
	 */
	public LoginScr()
	{
		super() ;
		initComponent() ;
	}

	/**
	 * この画面のインスタンスを返します。
	 * 
	 * @return この画面のインスタンス
	 */
	public static LoginScr getInstance()
	{
		if ($scr == null)
		{
			$scr = new LoginScr() ;
		}
		return $scr ;
	}

	/**
	 * 初期データの設定処理メソッドです。
	 */
	public void setInitData()
	{
		this.getTxtUserId().setText("") ;
		this.getTxtPassword().setText("") ;
		this.getTxtUserId().setRequestFocusEnabled(true) ;
	}

	/**
	 * 初期処理を行います。
	 */
	private void initComponent()
	{
		this.getBtnLogin().addActionListener(loginBtnActionListener) ;
		this.getBtnEnd().addActionListener(endBtnActionListener) ;
		this.getTxtUserId().addKeyListener(txtUserIdKeyListener) ;
		this.getTxtPassword().addKeyListener(txtPasswordKeyListener) ;
	}
}
