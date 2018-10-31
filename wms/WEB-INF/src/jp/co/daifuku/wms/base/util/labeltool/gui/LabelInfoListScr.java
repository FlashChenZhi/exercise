// $Id: LabelInfoListScr.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.util.ArrayList ;

import javax.swing.JButton ;
import javax.swing.table.TableColumn ;

import jp.co.daifuku.wms.base.util.labeltool.action.LabelInfoListAction ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.Authority ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.LabelInfoManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.base.gui.DaiButtonCellEditor ;
import jp.co.daifuku.wms.base.util.labeltool.base.gui.DaiButtonCellRenderer ;
import jp.co.daifuku.wms.base.util.labeltool.base.gui.DaiDefaultCellRenderer ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.entity.LabelInfoEntity ;
import jp.co.daifuku.wms.base.util.labeltool.entity.WorkingEntity ;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.LabelInfoListForm ;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.LabelInfoTableModel ;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem ;


/**
 * ラベル管理情報一覧照会画面のクラスです<br>
 * ラベル管理情報を表示します。
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
public class LabelInfoListScr
		extends LabelInfoListForm
{
	/** <code>この画面のインスタンス</code> */
	private static LabelInfoListScr $listScr = null ;

	/** <code>この画面に保持するデータリストです。</code> */
	private ArrayList<LabelInfoEntity> dataList = null ;

	/** <code>この画面に関する動作処理クラス</code> */
	private LabelInfoListAction action = new LabelInfoListAction(this) ;

	/** <code>最新に更新ボタン押下のイベントリスナーです。</code> */
	private ActionListener syncBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// 管理情報を同期する。
			ArrayList<LabelInfoEntity> dataList2 = dataList ;
			dataList = action.syncLabelInfoList() ;
			if (dataList == null)
			{
				dataList = dataList2 ;
			}
			refreshTable() ;
		}
	} ;

	/** <code>同期化ボタン押下のイベントリスナーです。</code> */
	private ActionListener refreshBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			setInitData(null) ;
		}
	} ;

	/** <code>ラベル新規ボタン押下のイベントリスナーです。</code> */
	private ActionListener addNewBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			WorkingEntity entity = new WorkingEntity() ;
			entity.setMode("1") ;
			entity.setId("") ;
			ScreenController.forwardTo(ScreenId.ADD_NEW_LABEL_INFO, entity) ;
		}
	} ;


	/** <code>終了ボタン押下イベントリスナーです</code> */
	private ActionListener endBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0) ;
		}
	} ;

	/** <code>変更ボタン押下イベントリスナーです</code> */
	private ActionListener updBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// 更新の場合
			if (((JButton)e.getSource()).getActionCommand().equals("UPDATE"))
			{
				if (action.checkLabelInfo())
				{
					String id = dataList.get(getJTable().getSelectedRow()).getId() ;
					WorkingEntity entity = new WorkingEntity() ;
					entity.setMode("2") ;
					entity.setId(id) ;
					// ラベル情報更新画面に遷移する。
					ScreenController.forwardTo(ScreenId.UPDATE_LABEL_INFO, entity) ;
				}
				else
				{
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
				}
			}
			// 回復の場合
			else if (((JButton)e.getSource()).getActionCommand().equals("RECOVER"))
			{
				if (ScreenUtil.showConfirm($listScr, "MSG0015") == ScreenUtil.CANCEL_OPTION)
				{
					return ;
				}
				String id = dataList.get(getJTable().getSelectedRow()).getId() ;
				if (action.recoverLabelInfo(id))
				{
					ScreenUtil.showMessage($listScr, "MSG0016") ;
					ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
				}
			}
		}
	} ;

	/** <code>削除ボタン押下イベントリスナーです</code> */
	private ActionListener delBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (((JButton)e.getSource()).getActionCommand().equals("REMOVE"))
			{
				String id = dataList.get(getJTable().getSelectedRow()).getId() ;
				action.deleteLabel(id) ;
			}
			else if (((JButton)e.getSource()).getActionCommand().equals("LOGICREMOVE"))
			{
				String id = dataList.get(getJTable().getSelectedRow()).getId() ;
				action.logicRemoveLabel(id) ;
			}
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
	} ;

	/** <code>自動発行設定ボタン押下イベントリスナーです</code> */
	private ActionListener autoBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (action.checkLabelInfo())
			{
				LabelItem info = null ;
				String id = dataList.get(getJTable().getSelectedRow()).getId() ;
				try
				{
					info = LabelInfoManager.getRemoteLabelInfo(id) ;
				}
				catch (DaiException e1)
				{
				}

				LabelInfoEntity entity = new LabelInfoEntity() ;
				entity.setId(info.getId()) ;
				entity.setLayoutName(info.getLayoutName()) ;
				ScreenController.forwardTo(ScreenId.DATA_MAPPING_CONFIG, entity) ;
			}
			else
			{
				ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
			}

		}
	} ;

	/** <code>保存場所設定ボタン押下イベントリスナーです</code> */
	private ActionListener configBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			ScreenController.forwardTo(ScreenId.REMOTE_CONFIG, null) ;
		}
	} ;

	/**
	 * このクラスのコンストラクタです。<br>
	 * 
	 */
	public LabelInfoListScr()
	{
		super() ;
		daiInitComponent() ;
	}

	/**
	 * この画面のインスタンスを取得するメソッドです。<br>
	 *  
	 * @return この画面のインスタンス
	 */
	public static LabelInfoListScr getInstance()
	{
		if ($listScr == null)
		{
			$listScr = new LabelInfoListScr() ;
		}
		return $listScr ;
	}

	/**
	 * 初期データの設定処理メソッドです。
	 * @param obj 渡すパラメータ
	 */
	public void setInitData(Object obj)
	{
		if (Authority.$level.equals(Authority.USER_LEVEL))
		{
			getAddNewBtn().setEnabled(false) ;
			getConfigureBtn().setEnabled(false) ;
		}
		else
		{
			getAddNewBtn().setEnabled(true) ;
			getConfigureBtn().setEnabled(true) ;
		}
		// 最新管理情報を取得する。
		dataList = action.getLabelInfoList() ;
		if (dataList == null)
		{
			$listScr.getAddNewBtn().setEnabled(false) ;
			dataList = new ArrayList<LabelInfoEntity>() ;
		}
		refreshTable() ;
	}

	/**
	 * 画面初期化処理メソッドです。 
	 */
	private void daiInitComponent()
	{
		// 同期化ボタンのイベントリスナーをセットする
		getBtnRefresh().addActionListener(refreshBtnActionListener) ;
		// 最新に更新ボタンのイベントリスナーをセットする
		getSyncBtn().addActionListener(syncBtnActionListener) ;
		// 新規ボタンのイベントリスナーをセットする
		getAddNewBtn().addActionListener(addNewBtnActionListener) ;
		// 終了ボタンのイベントリスナーをセットする
		getEndBtn().addActionListener(endBtnActionListener) ;
		//
		getConfigureBtn().addActionListener(configBtnActionListener) ;
	}

	/**
	 * 一覧内容を更新します。
	 */
	private void refreshTable()
	{
		if (dataList == null)
		{
			dataList = new ArrayList<LabelInfoEntity>() ;
		}
		String[][] a = new String[dataList.size()][4] ;
		for (int i = 0; i < dataList.size(); i++)
		{
			a[i] = dataList.get(i).toArray() ;
		}
		((LabelInfoTableModel)getJTable().getModel()).setDataVector(a) ;
		TableColumn tc = getJTable().getColumnModel().getColumn(LabelInfoTableModel.UPDATE) ;
		tc.setCellRenderer(new DaiButtonCellRenderer()) ;
		tc.setCellEditor(new DaiButtonCellEditor()) ;
		((DaiButtonCellEditor)tc.getCellEditor()).addActionListener(updBtnActionListener) ;

		tc = getJTable().getColumnModel().getColumn(LabelInfoTableModel.DELETE) ;
		tc.setCellRenderer(new DaiButtonCellRenderer()) ;
		tc.setCellEditor(new DaiButtonCellEditor()) ;
		((DaiButtonCellEditor)tc.getCellEditor()).addActionListener(delBtnActionListener) ;

		tc = getJTable().getColumnModel().getColumn(LabelInfoTableModel.AUTO_PRINT_SETTING) ;
		tc.setCellRenderer(new DaiButtonCellRenderer()) ;
		tc.setCellEditor(new DaiButtonCellEditor()) ;
		((DaiButtonCellEditor)tc.getCellEditor()).addActionListener(autoBtnActionListener) ;

		tc = getJTable().getColumnModel().getColumn(LabelInfoTableModel.ID) ;
		tc.setCellRenderer(new DaiDefaultCellRenderer()) ;
		tc = getJTable().getColumnModel().getColumn(LabelInfoTableModel.LABEL_NAME) ;
		tc.setCellRenderer(new DaiDefaultCellRenderer()) ;
		tc = getJTable().getColumnModel().getColumn(LabelInfoTableModel.LAYOUT_NAME) ;
		tc.setCellRenderer(new DaiDefaultCellRenderer()) ;

		TableColumn firsetColumn0 = getJTable().getColumnModel().getColumn(LabelInfoTableModel.ID) ;
		firsetColumn0.setPreferredWidth(55) ;
		firsetColumn0.setMaxWidth(55) ;
		firsetColumn0.setMinWidth(55) ;

		TableColumn firsetColumn1 = getJTable().getColumnModel().getColumn(
				LabelInfoTableModel.LABEL_NAME) ;
		firsetColumn1.setPreferredWidth(150) ;
		firsetColumn1.setMaxWidth(150) ;
		firsetColumn1.setMinWidth(150) ;

		TableColumn firsetColumn2 = getJTable().getColumnModel().getColumn(2) ;
		firsetColumn2.setPreferredWidth(200) ;
		firsetColumn2.setMaxWidth(200) ;
		firsetColumn2.setMinWidth(200) ;

		getJTable().getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0) ;
		getJTable().getTableHeader().getColumnModel().getColumn(3).setMinWidth(0) ;
		TableColumn firsetColumn3 = getJTable().getColumnModel().getColumn(3) ;
		firsetColumn3.setMaxWidth(0) ;

		TableColumn firsetColumn4 = getJTable().getColumnModel().getColumn(4) ;
		firsetColumn4.setPreferredWidth(100) ;
		firsetColumn4.setMaxWidth(100) ;
		firsetColumn4.setMinWidth(100) ;

		TableColumn firsetColumn5 = getJTable().getColumnModel().getColumn(5) ;
		firsetColumn5.setPreferredWidth(100) ;
		firsetColumn5.setMaxWidth(100) ;
		firsetColumn5.setMinWidth(100) ;
	}

	/**
	 * この画面に保持された一覧情報データを取得します。
	 * 
	 * @return この画面に保持された一覧情報データ
	 */
	public ArrayList<LabelInfoEntity> getDataList()
	{
		return this.dataList ;
	}
}
