// $$Id: RemoteConfigureScr.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.gui ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

import jp.co.daifuku.wms.base.util.labeltool.action.RemoteConfigureAction ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.EnvDefHandler ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.RemoteConfigureForm ;


/**
 * サーバ接続設定画面のクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/17</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class RemoteConfigureScr
		extends RemoteConfigureForm
{
	/** <code>この画面のインスタンス</code> */
	private static RemoteConfigureScr $scr = null ;

	/** <code>この画面に関する動作処理クラス</code> */
	private RemoteConfigureAction action = new RemoteConfigureAction(this) ;

	/** <code>保存ボタン押下イベントリスナーです</code> */
	private ActionListener saveBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (action.save())
			{
				ScreenController.forwardTo(ScreenId.LOGIN, null) ;
			}
		}
	} ;

	/** <code>戻るボタン押下イベントリスナーです</code> */
	private ActionListener backBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
	} ;

	/**
	 * このクラスのコンストラクタです。
	 */
	public RemoteConfigureScr()
	{
		super() ;
		daiIniComponent() ;
	}

	/**
	 * この画面のインスタンスを返します。
	 * 
	 * @return この画面のインスタンス
	 */
	public static RemoteConfigureScr getInstance()
	{
		if ($scr == null)
		{
			$scr = new RemoteConfigureScr() ;
		}
		return $scr ;
	}

	/**
	 * 初期化処理メソッドです。<br>
	 */
	public void daiIniComponent()
	{
		getSaveBtn().addActionListener(saveBtnActionListener) ;
		getBackBtn().addActionListener(backBtnActionListener) ;
	}

	/**
	 * 初期値設定メソッドです。<br>
	 */
	public void setInitData()
	{
		getAddressField().setText(
				EnvDefHandler.getInstance().getDefineValue("serverAddress").substring(2)) ;
		getRootField().setText(EnvDefHandler.getInstance().getDefineValue("rootDirectory")) ;

	}
}
