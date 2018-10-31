// $Id: MainForm.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.BorderLayout ;
import java.awt.Container ;
import java.awt.Dimension ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;
import java.awt.event.WindowListener ;

import javax.swing.JDialog ;
import javax.swing.UIManager ;
import javax.swing.WindowConstants ;

import jp.co.daifuku.wms.base.util.labeltool.base.entity.Authority ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;


/**
 * メイン画面のクラスです。<br>
 * この画面のコンテイナーを元に、各画面コンポーネントを切り替えて、<br>
 * 画面遷移を実現します。
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
public class MainForm
		extends JDialog
{

	/** <code>この画面のインスタンス</code> */
	private static MainForm $form = null ;

	/** <code>コンテイナー</code> */
	private Container c ;

	/** <code>表示されている画面ＩＤ</code> */
	private ScreenId screenId = null ;

	/** <code>この画面のウィンドウリスナー</code> */
	private WindowListener winListener = new WindowAdapter()
	{

		public void windowClosing(WindowEvent e)
		{
			switch (screenId)
			{
				case ADD_NEW_LABEL_INFO:
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
					break ;
				case UPDATE_LABEL_INFO:
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
					break ;
				case DATA_MAPPING_CONFIG:
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
					break ;
				case REMOTE_CONFIG:
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
					break ;
				default:
					System.exit(0) ;
			}
		}
	} ;

	/**
	 * このクラスのコンストラクターです。<br>
	 * 
	 */
	private MainForm()
	{
		super() ;
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel") ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}

		initialize() ;
	}

	/**
	 * この画面のインスタンスを取得します。<br>
	 * 
	 * @return この画面のインスタンス
	 */
	public static MainForm getInstance()
	{
		if ($form == null)
		{
			$form = new MainForm() ;
		}
		return $form ;
	}

	/**
	 * この画面の初期化処理メソッドです。<br>
	 * 
	 */
	protected void initialize()
	{
		c = this.getContentPane() ;
		this.setResizable(false) ;
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) ;
		this.addWindowListener(winListener) ;
	}

	/**
	 * 子画面を切り替えるメソッドです。<br>
	 * 
	 * @param gamenId 画面ID
	 * @param entity 遷移パラメータ
	 */
	public void showForm(ScreenId gamenId, Object entity)
	{
		this.setVisible(false) ;
		screenId = gamenId ;
		c.removeAll() ;
		String modeTitle = "" ;
		if (Authority.$level != null)
		{
			if (Authority.$level.equals(Authority.USER_LEVEL))
			{
				modeTitle = "User Mode" ;
			}
			if (Authority.$level.equals(Authority.ADMIN_LEVEL))
			{
				modeTitle = "Admin Mode" ;
			}
		}

		switch (gamenId)
		{
			case LOGIN:
				this.setTitle("\u30E6\u30FC\u30B6\u30ED\u30B0\u30A4\u30F3") ;
				this.setSize(300, 225) ;
				LoginScr loginScr = LoginScr.getInstance() ;
				loginScr.setInitData() ;
				c.add(loginScr, BorderLayout.CENTER) ;
				break ;
			case LABEL_INFO_LIST:
				this.setTitle("\u30E9\u30D9\u30EB\u7BA1\u7406\u60C5\u5831\u4E00\u89A7\u7167\u4F1A"
						+ "-"
						+ modeTitle) ;
				this.setSize(new Dimension(800, 600)) ;
				LabelInfoListScr listScr = LabelInfoListScr.getInstance() ;
				listScr.setInitData(null) ;
				c.add(listScr, null) ;
				break ;
			case ADD_NEW_LABEL_INFO:
				this.setTitle("\u30E9\u30D9\u30EB\u65B0\u898F\u767B\u9332") ;
				this.setSize(589, 225) ;
				LabelRegistScr registScr = LabelRegistScr.getInstance() ;
				registScr.setInitData(entity) ;
				c.add(registScr, BorderLayout.CENTER) ;
				break ;
			case UPDATE_LABEL_INFO:
				this.setTitle("\u30E9\u30D9\u30EB\u60C5\u5831\u5909\u66F4" + "-" + modeTitle) ;
				this.setSize(589, 225) ;
				registScr = LabelRegistScr.getInstance() ;
				registScr.setInitData(entity) ;
				c.add(registScr, BorderLayout.CENTER) ;
				break ;
			case DATA_MAPPING_CONFIG:
				this.setTitle("\u81EA\u52D5\u767A\u884C\u8A2D\u5B9A") ;
				this.setSize(502, 404) ;
				DataMappingConfigureScr raberuJidouHakkouScr = DataMappingConfigureScr.getInstance() ;
				raberuJidouHakkouScr.initComponent(entity) ;
				c.add(raberuJidouHakkouScr, BorderLayout.CENTER) ;
				break ;
			case REMOTE_CONFIG:
				this.setTitle("\u30B5\u30FC\u30D0\u63A5\u7D9A\u8A2D\u5B9A") ;
				this.setSize(426, 181) ;
				RemoteConfigureScr raberuIPadressSetScr = RemoteConfigureScr.getInstance() ;
				raberuIPadressSetScr.setInitData() ;
				c.add(raberuIPadressSetScr, BorderLayout.CENTER) ;
				break ;
			default:
				break ;
		}
		this.update(this.getGraphics()) ;
		this.setLocationRelativeTo(null) ;
		this.setVisible(true) ;
	}
}
