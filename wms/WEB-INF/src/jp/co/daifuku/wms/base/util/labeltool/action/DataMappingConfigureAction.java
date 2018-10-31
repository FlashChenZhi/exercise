// $Id: DataMappingConfigureAction.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.action ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.awt.Component ;
import java.io.File ;
import java.util.ArrayList ;
import java.util.Hashtable ;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.DBDefinitionHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.DataMappingInfoHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.LabelInfoManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.gui.DataMappingConfigureScr ;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument ;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem ;
import jp.co.daifuku.wms.label.xmlbeans.Variable ;


/**
 * 印字項目とDB項目を関連する画面に関する動作処理クラスです。<br>
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
public class DataMappingConfigureAction
{

	/** <code>動作の発生元画面</code> */
	private DataMappingConfigureScr scr = null ;

	/**
	 * このクラスのコンストラクタです。
	 * 
	 * @param form 動作の発生元画面
	 */
	public DataMappingConfigureAction(Component form)
	{
		scr = (DataMappingConfigureScr)form ;
	}

	/**
	 * 初期情報取得処理です。<br>
	 * 
	 * @param id 管理No
	 * @param messageFlg メッセージ表示フラグ
	 */
	public void getMappingInfo(String id, String messageFlg)
	{
		//関連関係取得
		Hashtable<String, String> resourceTable = null ;
		ArrayList<String> mapFieldsList = null ;
		ArrayList<String> mapVariableNames = null ;
		String tableName = "" ;
		try
		{
			//DB関連XMLから関連情報を取得
			resourceTable = DataMappingInfoHandler.getMapingInfo(id) ;
			//DB関連XMLから関連データ項目名をリストに作成
			mapFieldsList = DataMappingInfoHandler.getTableFields(id) ;
			//DB関連XMLから印字項目変数名をリストに作成
			mapVariableNames = DataMappingInfoHandler.getTableVariables(id) ;
			//DB関連XMLから関連テーブル名を取得
			tableName = DataMappingInfoHandler.getTableName(id) ;
			//DB関連XML更新時間を取得            
			scr.setLastUpdateTime(DataMappingInfoHandler.getLastUpdateTime()) ;
			//DB関連XMLDISABLEFLGを取得  
			scr.setDisableFlg(DataMappingInfoHandler.getDisableFlg(id)) ;
			if (resourceTable == null)
			{
				//DB関連XMLの中に対応情報がないの場合、新規
				resourceTable = new Hashtable<String, String>() ;
			}
		}
		catch (Exception e)
		{
			if (!messageFlg.equals(LabelConstants.FLAG_ON))
			{
				ScreenUtil.showError(scr, "ERR0011") ;
			}
			return ;
		}

		ArrayList<String> tableList = null ;
		ArrayList<String> fieldsList = null ;
		ArrayList<String> fields = null ;
		//dbDef.xmlからテーブル名をリストに作成
		tableList = getTableNameList() ;
		if (tableList == null)
		{
			return ;
		}
		//tableNameによって、dbDef.xmlにエレメントを取得して、リストに作成
		fieldsList = getTableFieldList(tableName) ;
		if (fieldsList == null)
		{
			return ;
		}
		//管理Noによって、該当XML定義ファイルから変数項目を取得して、リストに作成
		fields = getVariableFieldList(id) ;
		if (fields == null)
		{
			return ;
		}

		scr.setLabelVariableList(fields) ;
		scr.setTableList(tableList) ;
		scr.setTableName(tableName) ;
		scr.setDbFieldsList(fieldsList) ;
		scr.setMappingInfoTable(resourceTable) ;

		//scr.setTableNameCombo();
		scr.setTableItemCombo() ;

		if (!checkItems(mapFieldsList, mapVariableNames))
		{
			if (!messageFlg.equals(LabelConstants.FLAG_ON))
			{
				ScreenUtil.showError(scr, "MSG0007") ;
			}
			scr.setTableName("") ;
			scr.clearMappingInfo() ;
		}
	}

	/**
	 * テーブル名の変更処理を行うメソッドです。<br>
	 * 
	 * @param id 管理No
	 * @param tableName 変更先テーブル名
	 */
	public void changeTable(String id, String tableName)
	{
		try
		{
			String mapingTableName = DataMappingInfoHandler.getTableName(id) ;
			if (tableName.equals(mapingTableName))
			{
				//関連関係取得
				Hashtable<String, String> resourceTable = new Hashtable<String, String>() ;
				ArrayList<String> mapFieldsList = null ;
				ArrayList<String> mapVariableNames = null ;
				String table2Name = "" ;
				//scr.clearTableNameCombo();

				//DB関連XMLから関連情報を取得
				resourceTable = DataMappingInfoHandler.getMapingInfo(id) ;
				//DB関連XMLから関連データ項目名をリストに作成
				mapFieldsList = DataMappingInfoHandler.getTableFields(id) ;
				//DB関連XMLから印字項目変数名をリストに作成
				mapVariableNames = DataMappingInfoHandler.getTableVariables(id) ;
				//DB関連XMLから関連テーブル名を取得
				table2Name = DataMappingInfoHandler.getTableName(id) ;
				//DB関連XMLDISABLEFLGを取得
				scr.setDisableFlg(DataMappingInfoHandler.getDisableFlg(id)) ;

				if (resourceTable == null)
				{
					//DB関連XMLの中に対応情報がないの場合、新規
					resourceTable = new Hashtable<String, String>() ;
				}
				ArrayList<String> tableList = null ;
				ArrayList<String> fieldsList = null ;
				ArrayList<String> fields = null ;

				//dbDef.xmlからテーブル名をリストに作成
				tableList = getTableNameList() ;
				//tableNameによって、dbDef.xmlにエレメントを取得して、リストに作成
				fieldsList = getTableFieldList(table2Name) ;
				//管理Noによって、該当XML定義ファイルから変数項目を取得して、リストに作成
				fields = getVariableFieldList(id) ;

				scr.setLabelVariableList(fields) ;
				scr.setTableList(tableList) ;
				scr.setDbFieldsList(fieldsList) ;
				scr.setMappingInfoTable(resourceTable) ;

				//scr.setTableNameCombo();
				scr.setTableItemCombo() ;

				if (!checkItems(mapFieldsList, mapVariableNames))
				{
					scr.clearSelection() ;
					return ;
				}
			}
			else
			{
				if (scr.getMappingInfoTable() != null)
				{
					scr.getMappingInfoTable().clear() ;
				}
				ArrayList<String> fieldsList = null ;
				//たテーブル一覧
				fieldsList = getTableFieldList(tableName) ;
				//印字項目一覧取得
				ArrayList<String> fields = null ;
				fields = getVariableFieldList(id) ;
				//DB関連XMLDISABLEFLGを設定
				scr.setDisableFlg(LabelConstants.FLAG_OFF) ;
				scr.setLabelVariableList(fields) ;
				scr.setDbFieldsList(fieldsList) ;
				scr.setTableItemCombo() ;
			}
		}
		catch (Exception e)
		{
			ScreenUtil.showError(scr, "ERR0011") ;
			return ;
		}
	}

	/**
	 * 設定保存を行うメソッドです。<br>
	 * 
	 * @param id 管理No
	 * @return 結果
	 */
	public String save(String id)
	{
		if (ScreenUtil.showConfirm(scr, "MSG0013") == ScreenUtil.CANCEL_OPTION)
		{
			return LabelConstants.FLAG_OFF ;
		}

		boolean flg = false ;
		String[][] data = new String[scr.getJTable().getRowCount()][2] ;
		for (int i = 0; i < scr.getJTable().getRowCount(); i++)
		{
			if (!"".equals((String)scr.getJTable().getValueAt(i, 1)))
			{
				flg = true ;
			}
			data[i][0] = (String)scr.getJTable().getValueAt(i, 0) ;
			data[i][1] = (String)scr.getJTable().getValueAt(i, 1) ;
		}
		if (!flg)
		{
			ScreenUtil.showMessage(scr, "ERR0022") ;
			return LabelConstants.FLAG_OFF ;
		}
		try
		{
			String disableFlg = LabelConstants.FLAG_OFF ;
			if (scr.getJCheckBox().isSelected())
			{
				disableFlg = LabelConstants.FLAG_ON ;
			}
			DataMappingInfoHandler.addMapingInfo(id, scr.getTableName(), data, disableFlg,
					scr.getLastUpdateTime()) ;
			ScreenUtil.showMessage(scr, "MSG0005") ;
			return LabelConstants.FLAG_ON ;
		}
		catch (Exception e)
		{
			if (e.getMessage() != null)
			{
				ScreenUtil.showMessage(scr, e.getMessage()) ;
			}
			else
			{
				ScreenUtil.showError(scr, "ERR0012") ;
			}
			return LabelConstants.FLAG_OFF ;
		}
	}

	/**
	 * 削除を行うメソッドです。<br>
	 * 
	 * @param id 管理No
	 * @return 結果
	 */
	public String delete(String id)
	{
		try
		{
			if (ScreenUtil.showConfirm(scr, "MSG0013") == ScreenUtil.CANCEL_OPTION)
			{
				return LabelConstants.FLAG_OFF ;
			}
			DataMappingInfoHandler.deleteMapingInfo(id, scr.getLastUpdateTime()) ;
			ScreenUtil.showMessage(scr, "MSG0005") ;
			return LabelConstants.FLAG_ON ;
		}
		catch (Exception e)
		{
			if (e.getMessage() != null)
			{
				ScreenUtil.showMessage(scr, e.getMessage()) ;
			}
			else
			{
				ScreenUtil.showError(scr, "ERR0012") ;
			}
			return LabelConstants.FLAG_OFF ;
		}
	}

	/**
	 * 関連情報のDB項目と印字項目情報内容の関連チェックを行います。
	 * 
	 * @param mapFieldsList 関連情報のDB項目情報
	 * @param mapVariableList 関連情報の印字項目情報
	 * @return 具合の場合、trueを返します。<br>
	 *         不具合の場合、falseを返します。
	 */
	private boolean checkItems(ArrayList<String> mapFieldsList, ArrayList<String> mapVariableList)
	{
		if (scr.getTableList() != null && scr.getTableName() != null)
		{
			if (!"".equals(scr.getTableName()) && !scr.getTableList().contains(scr.getTableName()))
			{
				return false ;
			}
		}
		if (scr.getDbFieldsList() != null && mapFieldsList != null)
		{
			for (int i = 0; i < mapFieldsList.size(); i++)
			{
				// 関連情報のDB項目名がDB定義に存在しない場合、エラーとする。
				if (!scr.getDbFieldsList().contains(mapFieldsList.get(i)))
				{
					return false ;
				}
			}
		}
		if (mapVariableList != null && scr.getLabelVariableList() != null)
		{
			if (!StringUtil.isEmpty(scr.getTableName()))
			{
				// DB関連情報の可変項目数　> ラベル定義情報の可変項目数の場合、エラーとする。
				if (mapVariableList.size() > scr.getLabelVariableList().size())
				{
					return false ;
				}
				for (int i = 0; i < mapVariableList.size(); i++)
				{
					// 関連情報の可変印字項目名がラベルXML定義情報に存在しない場合、エラーとする。
					if (!scr.getLabelVariableList().contains(mapVariableList.get(i)))
					{
						return false ;
					}
				}
			}
		}
		return true ;
	}

	/**
	 * DB定義XMLファイルからテーブル名のリストを取得します。
	 * 
	 * @return テーブル名リスト
	 */
	private ArrayList<String> getTableNameList()
	{
		ArrayList<String> tableList = null ;
		try
		{
			tableList = DBDefinitionHandler.getTableNames(LabelInfoUtil.getRemotePath()
					+ LabelConstants.DB_DEFINATION_FILE_NAME) ;
		}
		catch (Exception e)
		{
			ScreenUtil.showError(scr, "ERR0010") ;
		}
		return tableList ;
	}

	/**
	 * 指定テーブルのDB項目リストを取得します。
	 * 
	 * @param tableName テーブル名
	 * @return DB項目リスト
	 */
	private ArrayList<String> getTableFieldList(String tableName)
	{
		ArrayList<String> fieldsList = new ArrayList<String>() ;
		if (StringUtil.isEmpty(tableName))
		{
			return fieldsList ;
		}
		try
		{
			fieldsList = DBDefinitionHandler.getTableFields(LabelInfoUtil.getRemotePath()
					+ LabelConstants.DB_DEFINATION_FILE_NAME, tableName) ;
		}
		catch (Exception e)
		{
			ScreenUtil.showError(scr, "ERR0002") ;
			return null ;
		}
		return fieldsList ;
	}

	/**
	 * 指定管理Noのラベルの可変印字項目リストを取得します。
	 * @param id 管理No
	 * @return 可変印字項目リスト
	 */
	private ArrayList<String> getVariableFieldList(String id)
	{
		DAILabelDocument doc = null ;
		LabelItem info = null ;
		ArrayList<String> fields = null ;
		Variable[] variables = null ;
		try
		{
			info = LabelInfoManager.getRemoteLabelInfo(id) ;
			File f = new File(LabelInfoUtil.getRemoteXMLPath()
					+ info.getLayoutName().substring(0,
							info.getLayoutName().indexOf(LabelConstants.SUFFIX_LAYOUT))
					+ LabelConstants.SUFFIX_XML) ;
			doc = DAILabelDocument.Factory.parse(f) ;
			variables = doc.getDAILabel().getVariableArray() ;
			if (variables != null && variables.length > 0)
			{
				fields = new ArrayList<String>() ;
				for (int i = 0; i < variables.length; i++)
				{
					fields.add(variables[i].getFieldId()) ;
				}
			}
		}
		catch (Exception e)
		{
			ScreenUtil.showError(scr, "ERR0002") ;
		}
		return fields ;
	}
}
