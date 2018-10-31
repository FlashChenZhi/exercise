// $Id: DataMappingConfigureScr.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.gui ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.ItemEvent ;
import java.awt.event.ItemListener ;
import java.util.ArrayList ;
import java.util.Hashtable ;

import javax.swing.DefaultCellEditor ;
import javax.swing.JCheckBox ;
import javax.swing.JComboBox ;
import javax.swing.table.DefaultTableModel ;

import jp.co.daifuku.wms.base.util.labeltool.action.DataMappingConfigureAction ;
import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.entity.LabelInfoEntity ;
import jp.co.daifuku.wms.base.util.labeltool.gui.form.DataMappingConfigureForm ;


/**
 * 印字項目とDB項目を関連する画面です。<br>
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
public class DataMappingConfigureScr
		extends DataMappingConfigureForm
{
	/** <code>この画面のインスタンス</code> */
	private static DataMappingConfigureScr $scr = null ;

	/** <code>画面に保持した選択テーブル名</code> */
	private String tableName = "" ;

	/** <code>tableItemCombox</code> */
	private JComboBox tableItemCombox = null ;

	/** <code>画面に保持したテーブル名リスト</code> */
	private ArrayList<String> tableList = null ;

	/** <code>画面に保持したDB定義に指定テーブルのDB項目リスト</code> */
	private ArrayList<String> dbFieldsList = null ;

	/** <code>画面に保持したラベルXML定義に可変印字項目リスト</code> */
	private ArrayList<String> labelVariableList = null ;

	/** <code>画面に保持したDB関連情報</code> */
	private Hashtable<String, String> mappingInfoTable = null ;

	/** <code>画面に保持したDB関連情報更新時間</code> */
	private String lastUpdateTime = null ;

	/** <code>削除ボタン利用可否フラグ</code> */
	private boolean delBtnEnableFlg = true ;

	/** <code>disableFlg</code> */
	private String disableFlg = null ;

	/** <code>保持した自動発行無効チェックボックス初期状態</code> */
	private boolean firstFlg ;

	/** <code>checkDisableFlg</code> */
	private boolean checkDisableFlg ;

	/** <code>firstTableName</code> */
	private String firstTableName = "" ;

	/** <code>この画面に関する動作処理</code> */
	private DataMappingConfigureAction action = new DataMappingConfigureAction(this) ;

	/** <code>削除ボタン押下イベントリスナーです。</code> */
	private ActionListener deleteBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String flg = action.delete(getIdField().getText()) ;

			if (flg.equals(LabelConstants.FLAG_ON))
			{
				getDeleteBtn().setEnabled(true) ;
				delBtnEnableFlg = true ;
				getJTable().setEnabled(true) ;
				getSaveBtn().setEnabled(true) ;
				ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
			}
		}
	} ;

	/** <code>保存ボタン押下イベントリスナーです。</code> */
	private ActionListener saveBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String flg = action.save(getIdField().getText()) ;

			if (flg.equals(LabelConstants.FLAG_ON))
			{
				getDeleteBtn().setEnabled(true) ;
				delBtnEnableFlg = true ;
				getJTable().setEnabled(true) ;
				getSaveBtn().setEnabled(true) ;
				ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
			}
		}
	} ;

	/** <code>戻るボタン押下イベントリスナーです。</code> */
	private ActionListener backBtnActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			getDeleteBtn().setEnabled(true) ;
			delBtnEnableFlg = true ;
			getJTable().setEnabled(true) ;
			getSaveBtn().setEnabled(true) ;
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
	} ;


	/** <code>JCheckBox selectイベントリスナーです。</code> */
	private ItemListener itemStateListener = new ItemListener()
	{
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.DESELECTED)
			{
				enableSet() ;
				if (getTableCombox().getSelectedIndex() == 0)
				{
					getSaveBtn().setEnabled(false) ;
				}
				firstFlg = false ;
			}
			else if (e.getStateChange() == ItemEvent.SELECTED)
			{
				if (!firstFlg)
				{
					checkDisableFlg = true ;
					getTableCombox().setSelectedItem(firstTableName) ;
					checkDisableFlg = false ;
					if (ScreenUtil.showConfirm($scr, "MSG0018") == ScreenUtil.CANCEL_OPTION)
					{
						((JCheckBox)e.getSource()).setSelected(false) ;
						return ;
					}
				}
				disableSet() ;
				firstFlg = true ;
			}
		}

	} ;

	/** <code>テーブル名コンボの値の変更を監視するリスナー</code> */
	private ItemListener cmbTableNameItemListener = new ItemListener()
	{
		public void itemStateChanged(ItemEvent e)
		{
			if (delBtnEnableFlg)
			{
				getDeleteBtn().setEnabled(true) ;
			}
			getJTable().setEnabled(true) ;
			getSaveBtn().setEnabled(true) ;
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				// リスト内容が空白の場合、処理を中止する。
				if (((JComboBox)e.getSource()).getItemCount() <= 1)
				{
					return ;
				}
				// テーブル名が空白で選択された場合。
				if ("".equals(e.getItem()))
				{
					if (!StringUtil.isEmpty($scr.tableName))
					{
						if (!checkDisableFlg)
						{
							if (ScreenUtil.showConfirm($scr, "MSG0019") == ScreenUtil.CANCEL_OPTION)
							{
								((JComboBox)e.getSource()).setSelectedItem($scr.tableName) ;
								return ;
							}
						}
						tableItemCombox = new JComboBox() ;
						tableItemCombox.addItem("") ;
						if (!(tableList == null
								|| getMappingInfoTable() == null
								|| getDbFieldsList() == null || getLabelVariableList() == null))
						{
							setTableValue(null, getLabelVariableList()) ;
						}
						$scr.tableName = (String)e.getItem() ;
						getSaveBtn().setEnabled(false) ;
						getJTable().setEnabled(false) ;
						getDeleteBtn().setEnabled(false) ;
						return ;
					}
				}
				else
				{
					if (!StringUtil.isEmpty($scr.tableName))
					{
						if ($scr.tableName.equals(e.getItem()))
						{
							return ;
						}
						else
						{
							if (!checkDisableFlg)
							{
								// テーブル名が変更選択された場合。
								if (ScreenUtil.showConfirm($scr, "MSG0006") == ScreenUtil.CANCEL_OPTION)
								{
									((JComboBox)e.getSource()).setSelectedItem($scr.tableName) ;
									return ;
								}
							}
						}
					}
					action.changeTable(getIdField().getText(), (String)e.getItem()) ;
					$scr.tableName = (String)e.getItem() ;
					// テーブル名リストをコンボにセットする。
					setTableNameCombo() ;
					if (tableList == null)
					{
						getSaveBtn().setEnabled(false) ;
					}
					else if (getMappingInfoTable() == null
							|| getDbFieldsList() == null
							|| getLabelVariableList() == null)
					{
						getSaveBtn().setEnabled(false) ;
					}
					else
					{
						setTableValue(getMappingInfoTable(), getLabelVariableList()) ;
					}

					if (LabelConstants.FLAG_ON.equals(getDisableFlg()))
					{
						getJCheckBox().setSelected(true) ;
					}
					else
					{
						getJCheckBox().setSelected(false) ;
					}
				}
			}

		}
	} ;


	/**
	 * このクラスのコンストラクタです。 
	 * 
	 */
	public DataMappingConfigureScr()
	{
		super() ;
		daiIniComponent() ;
	}


	/**
	 * この画面のインスタンスを取得します。
	 * 
	 * @return この画面のインスタンス
	 */
	public static DataMappingConfigureScr getInstance()
	{
		if ($scr == null)
		{
			$scr = new DataMappingConfigureScr() ;
		}
		return $scr ;
	}


	/**
	 * 初期化処理を行います。
	 */
	private void daiIniComponent()
	{
		getSaveBtn().addActionListener(saveBtnActionListener) ;
		getEndBtn().addActionListener(backBtnActionListener) ;
		getDeleteBtn().addActionListener(deleteBtnActionListener) ;
		getJCheckBox().addItemListener(itemStateListener) ;
		tableItemCombox = new JComboBox() ;
		tableItemCombox.addItem("") ;
		getTableCombox().addItemListener(cmbTableNameItemListener) ;
	}

	/**
	 * 画面初期化
	 * @param obj 遷移パラメータ
	 */
	public void initComponent(Object obj)
	{
		getJCheckBox().setSelected(false) ;
		firstFlg = true ;
		getSaveBtn().setEnabled(true) ;
		getSaveBtn().setEnabled(true) ;
		getJTable().setEnabled(true) ;
		getTableCombox().setEnabled(true) ;

		DefaultTableModel tableModel = (DefaultTableModel)getJTable().getModel() ;
		tableModel.setRowCount(0) ;

		LabelInfoEntity entity = (LabelInfoEntity)obj ;
		getIdField().setText(entity.getId()) ;
		getNameField().setText(entity.getLayoutName()) ;

		action.getMappingInfo(entity.getId(), LabelConstants.FLAG_OFF) ;
		// テーブル名リストをコンボにセットする。
		setTableNameCombo() ;
		if (StringUtil.isEmpty($scr.tableName))
		{
			getDeleteBtn().setEnabled(false) ;
			delBtnEnableFlg = false ;
		}
		if (tableList == null)
		{
			getSaveBtn().setEnabled(false) ;
		}
		else if (getMappingInfoTable() == null
				|| getDbFieldsList() == null
				|| getLabelVariableList() == null)
		{
			getSaveBtn().setEnabled(false) ;
		}
		else
		{
			setTableValue(getMappingInfoTable(), getLabelVariableList()) ;
		}

		if (LabelConstants.FLAG_ON.equals(getDisableFlg()))
		{
			getJCheckBox().setSelected(true) ;
			getSaveBtn().setEnabled(false) ;
		}
		else
		{
			getJCheckBox().setSelected(false) ;
		}

	}

	/**
	 * DB関連情報を画面テーブルに表示します。
	 * 
	 * @param mapInfo DB関連情報
	 * @param fields ラベル定義の可変印字項目情報
	 */
	private void setTableValue(Hashtable<String, String> mapInfo, ArrayList<String> fields)
	{
		String[] tableData = new String[2] ;
		getJTable().removeAll() ;
		DefaultTableModel tableModel = (DefaultTableModel)getJTable().getModel() ;
		tableModel.setRowCount(0) ;
		for (int i = 0; i < fields.size(); i++)
		{
			tableData[0] = fields.get(i) ;
			if (mapInfo != null)
			{
				String temp = mapInfo.get(fields.get(i)) ;
				if (temp == null || "".equals(temp))
				{
					tableData[1] = "" ;
				}
				else
				{
					tableData[1] = temp ;
				}
			}
			else
			{
				tableData[1] = "" ;
			}
			tableModel.addRow(tableData) ;
		}
		getJTable().getColumnModel().getColumn(1).setCellEditor(
				new DefaultCellEditor(tableItemCombox)) ;
		getJTable().invalidate() ;
		getJTable().repaint() ;
	}

	/**
	 * クリアします。
	 */
	public void clearSelection()
	{
		tableItemCombox.setSelectedIndex(0) ;
		if (getMappingInfoTable() != null)
		{
			getMappingInfoTable().clear() ;
		}
	}

	/**
	 * テーブル名リストをコンボボックスに設定します。
	 */
	public void setTableNameCombo()
	{
		getTableCombox().removeAllItems() ;
		getTableCombox().addItem("") ;
		if (tableList != null)
		{
			for (int i = 0; i < tableList.size(); i++)
			{
				getTableCombox().addItem(tableList.get(i)) ;
			}
			getTableCombox().setSelectedItem(tableName) ;
		}
	}

	/**
	 * DB項目リストをコンボボックスに設定します。
	 */
	public void setTableItemCombo()
	{

		tableItemCombox = new JComboBox() ;
		tableItemCombox.addItem("") ;

		if (getDbFieldsList() != null)
		{
			for (int i = 0; i < getDbFieldsList().size(); i++)
			{
				tableItemCombox.addItem(getDbFieldsList().get(i)) ;
			}
		}
	}

	/**
	 * DB関連情報をクリアします。
	 */
	public void clearMappingInfo()
	{
		tableItemCombox.removeAllItems() ;
		tableItemCombox.addItem("") ;
		tableItemCombox.setSelectedIndex(0) ;
		if (getMappingInfoTable() != null)
		{
			getMappingInfoTable().clear() ;
		}
		getTableCombox().setSelectedIndex(0) ;
	}

	/**
	 * jcheckBox disable
	 */
	public void disableSet()
	{
		getJTable().setEnabled(false) ;
		getTableCombox().setEnabled(false) ;
		action.getMappingInfo(getIdField().getText(), LabelConstants.FLAG_ON) ;

		if (tableList != null
				&& getMappingInfoTable() != null
				&& getDbFieldsList() != null
				&& getLabelVariableList() != null)
		{
			setTableValue(getMappingInfoTable(), getLabelVariableList()) ;
		}

	}

	/**
	 * jcheckBox enable
	 */
	public void enableSet()
	{
		getJTable().setEnabled(true) ;
		getTableCombox().setEnabled(true) ;
		getSaveBtn().setEnabled(true) ;

		if (tableList == null)
		{
			getSaveBtn().setEnabled(false) ;
		}
		else if (getMappingInfoTable() == null
				|| getDbFieldsList() == null
				|| getLabelVariableList() == null)
		{
			getSaveBtn().setEnabled(false) ;
		}
	}

	/**
	 * @return tableNameを返します。
	 */
	public String getTableName()
	{
		return tableName ;
	}


	/**
	 * @param tableName tableNameを設定します。
	 */
	public void setTableName(String tableName)
	{
		this.tableName = tableName ;
		this.firstTableName = tableName ;
	}


	/**
	 * @return tableListを返します。
	 */
	public ArrayList<String> getTableList()
	{
		return tableList ;
	}


	/**
	 * @param tableList tableListを設定します。
	 */
	public void setTableList(ArrayList<String> tableList)
	{
		this.tableList = tableList ;
	}


	/**
	 * @return dbFieldsListを返します。
	 */
	public ArrayList<String> getDbFieldsList()
	{
		return dbFieldsList ;
	}


	/**
	 * @param dbFieldsList dbFieldsListを設定します。
	 */
	public void setDbFieldsList(ArrayList<String> dbFieldsList)
	{
		this.dbFieldsList = dbFieldsList ;
	}


	/**
	 * @return labelVariableListを返します。
	 */
	public ArrayList<String> getLabelVariableList()
	{
		return labelVariableList ;
	}


	/**
	 * @param labelVariableList labelVariableListを設定します。
	 */
	public void setLabelVariableList(ArrayList<String> labelVariableList)
	{
		this.labelVariableList = labelVariableList ;
	}


	/**
	 * @return mappingInfoTableを返します。
	 */
	public Hashtable<String, String> getMappingInfoTable()
	{
		return mappingInfoTable ;
	}


	/**
	 * @param mappingInfoTable mappingInfoTableを設定します。
	 */
	public void setMappingInfoTable(Hashtable<String, String> mappingInfoTable)
	{
		this.mappingInfoTable = mappingInfoTable ;
	}


	/**
	 * @return lastUpdateTimeを返します。
	 */
	public String getLastUpdateTime()
	{
		return lastUpdateTime ;
	}

	/**
	 * @param lastUpdateTime lastUpdateTimeを設定します。
	 */
	public void setLastUpdateTime(String lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime ;
	}

	/**
	 * @return disableFlgを返します。
	 */
	public String getDisableFlg()
	{
		return disableFlg ;
	}

	/**
	 * @param disableFlg disableFlgを設定します。
	 */
	public void setDisableFlg(String disableFlg)
	{
		this.disableFlg = disableFlg ;
	}

}
