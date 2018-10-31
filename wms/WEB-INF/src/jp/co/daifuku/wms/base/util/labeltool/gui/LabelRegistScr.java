// $Id: LabelRegistScr.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

import jp.co.daifuku.wms.base.util.labeltool.action.LabelRegistAction ;
import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.Authority ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.FileManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.LabelInfoManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.entity.LabelInfoEntity ;
import jp.co.daifuku.wms.base.util.labeltool.entity.WorkingEntity ;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.LabelRegistForm ;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem ;


/**
 * ラベル情報の登録、変更、削除を行う画面です。<br>
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
public class LabelRegistScr
		extends LabelRegistForm
{
	/** <code>この画面のインスタンス</code> */
	private static LabelRegistScr $scr = null ;

	/** <code>この画面に関する動作処理</code> */
	private LabelRegistAction action = new LabelRegistAction(this) ;

	/** <code>画面遷移時に保持するパラメータ</code> */
	private WorkingEntity entity = null ;

	/** <code>作業用の情報データ</code> */
	private LabelItem item = LabelItem.Factory.newInstance() ;

	/** <code>取込み/レイアウト修正ボタン押下イベントリスナー</code> */
	private ActionListener loadBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (entity.getMode().equals("1"))
			{
				action.loadLayout() ;
			}
			// 変更の場合、マルチラベルリスト編集ツールを呼び出す。
			else if (entity.getMode().equals("2"))
			{
				String layoutFileName = LabelConstants.LOCAL_DATA_PATH
						+ LabelConstants.DIRSEPACHAR
						+ LabelConstants.LAYOUT_PATH
						+ LabelConstants.DIRSEPACHAR
						+ getLayoutNameField().getText() ;
				action.execEditTool(layoutFileName) ;
			}
		}
	} ;

	/** <code>登録／変更ボタンを押下イベントリスナーです</code> */
	private ActionListener registerBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			LabelInfoEntity info = new LabelInfoEntity() ;
			info.setId(item.getId()) ;
			info.setName(getNameField().getText()) ;
			info.setLayoutName(getLayoutNameField().getText()) ;
			info.setVersionNo(item.getVersionNo()) ;
			// 新規の場合
			if (entity.getMode().equals("1"))
			{
				action.addNew(info) ;
			}
			// 変更の場合
			else if (entity.getMode().equals("2"))
			{
				if (action.checkBeforeUpdate(info))
				{
					// 確認画面を表示する。
					if (ScreenUtil.showConfirm($scr, "MSG0009") == ScreenUtil.CANCEL_OPTION)
					{
						return ;
					}
					// ラベル情報変更を行う。
					action.update(info) ;
				}
			}
		}
	} ;

	/** <code>戻るボタン押下イベントリスナーです。</code> */
	private ActionListener endBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (entity.getMode().equals("1"))
			{
				if (!StringUtil.isEmpty(getLayoutNameField().getText()))
				{
					FileManager.deleteLocalLayoutFile(getLayoutNameField().getText().trim()) ;
				}
			}
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
	} ;

	/**
	 * この画面のコンストラクタ
	 */
	public LabelRegistScr()
	{
		super() ;
		initComponent() ;
	}

	/**
	 * この画面のインスタンスを返します。
	 * 
	 * @return この画面のインスタンス
	 */
	public static LabelRegistScr getInstance()
	{
		if ($scr == null)
		{
			$scr = new LabelRegistScr() ;
		}
		return $scr ;
	}

	/**
	 * 画面初期化処理メソッドです。
	 * 
	 */
	public void initComponent()
	{
		this.getEntryBtn().addActionListener(registerBtnActionListener) ;
		this.getLoadBtn().addActionListener(loadBtnActionListener) ;
		this.getEndBtn().addActionListener(endBtnActionListener) ;
	}

	/**
	 * 初期データの設定処理メソッドです。
	 * @param obj 渡すパラメータ
	 */
	public void setInitData(Object obj)
	{
		entity = (WorkingEntity)obj ;
		if (entity.getMode().equals("1"))
		{
			// 新規の場合
			this.getIdField().setText("") ;
			this.getNameField().setText("") ;
			this.getLayoutNameField().setText("") ;
			this.getLoadBtn().setText("\u53D6\u8FBC\u307F") ;
			this.getEntryBtn().setText("\u767B\u9332") ;
			this.getNameField().setEnabled(true) ;
			this.getLoadBtn().setVisible(true) ;
		}
		else if (entity.getMode().equals("2"))
		{
			// 変更の場合
			try
			{
				item = LabelInfoManager.getRemoteLabelInfo(entity.getId()) ;
				this.getIdField().setText(item.getId()) ;
				this.getNameField().setText(item.getName()) ;
				this.getLayoutNameField().setText(item.getLayoutName()) ;
				this.getLoadBtn().setVisible(true) ;
				this.getLoadBtn().setText("\u30EC\u30A4\u30A2\u30A6\u30C8\u4FEE\u6B63") ;
				this.getEntryBtn().setText("\u66F4\u65B0") ;
				if (Authority.$level.equals(Authority.USER_LEVEL))
				{
					this.getNameField().setEnabled(false) ;
				}
				else
				{
					this.getNameField().setEnabled(true) ;
				}
			}
			catch (DaiException e)
			{
				ScreenUtil.showError($scr, e.getMessage()) ;
			}
		}
	}
}
