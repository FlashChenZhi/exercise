// $$Id: LabelRegistAction.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.action ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component ;
import java.io.IOException ;

import javax.swing.JFileChooser ;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.EnvDefHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.FileManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.LabelInfoManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.base.gui.LayoutFileFilter ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.entity.LabelInfoEntity ;
import jp.co.daifuku.wms.base.util.labeltool.gui.LabelRegistScr ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.parse.SBPLLabelConvertManager ;


/**
 * ラベル登録画面に関する処理クラスです。<br>
 * ラベルの新規、変更と削除を行います。
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
public class LabelRegistAction
{

	/** <code>動作の発生元画面</code> */
	private LabelRegistScr scr = null ;

	/**
	 * このクラスのコンストラクタです。
	 * 
	 * @param form 動作の発生元画面
	 */
	public LabelRegistAction(Component form)
	{
		scr = (LabelRegistScr)form ;
	}

	/**
	 * レベル新規処理メソッドです。<br>
	 * 
	 * @param entity ラベル情報エンティティ
	 */
	public void addNew(LabelInfoEntity entity)
	{
		// ラベル名が空白場合エラーとする。
		if (StringUtil.isEmpty(scr.getNameField().getText()))
		{
			ScreenUtil.showError(scr, "ERR0013") ;
			return ;
		}
		// レイアウトファイル名が空白場合エラーとする。
		if (StringUtil.isEmpty(scr.getLayoutNameField().getText()))
		{
			ScreenUtil.showError(scr, "ERR0014") ;
			return ;
		}
		// 確認画面を表示する。
		if (ScreenUtil.showConfirm(scr, "MSG0011") == ScreenUtil.CANCEL_OPTION)
		{
			return ;
		}
		String layoutFileName = LabelInfoUtil.getLocalLayoutPath() + entity.getLayoutName() ;
		String xmlFileName = (LabelInfoUtil.getLocalXMLPath() + entity.getLayoutName()).replaceAll(
				LabelConstants.SUFFIX_LAYOUT, LabelConstants.SUFFIX_XML) ;
		String entityName = (entity.getLayoutName()).replaceAll(LabelConstants.SUFFIX_LAYOUT, "") ;

		SBPLLabelConvertManager compiler = new SBPLLabelConvertManager() ;
		try
		{
			// ラベルレイアウトファイルからXML定義ファイルを生成する。
			compiler.convertLayoutToXmlFile(layoutFileName, xmlFileName) ;
			// エンティティクラスファイルを生成する。
			compiler.generateEntity(xmlFileName, entityName) ;

			// ラベル定義情報を追加する。
			LabelInfoManager.addNewLabelInfo(entity) ;
			ScreenUtil.showMessage(scr, "MSG0002") ;
			// 成功した場合、一覧照会画面に戻る。
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
		}

	}

	/**
	 * 更新前に、必要なチェック処理を行います。
	 * 
	 * @param entity ラベル情報エンティティ
	 * @return エラー無し場合、trueを返します。<br>
	 *         エラーがある場合、falseを返します。
	 */
	public boolean checkBeforeUpdate(LabelInfoEntity entity)
	{
		// ラベル名が空白場合エラーとする。
		if (StringUtil.isEmpty(scr.getNameField().getText()))
		{
			ScreenUtil.showError(scr, "ERR0013") ;
			return false ;
		}
		try
		{
			switch (LabelInfoManager.getStatus(entity.getId()))
			{
				case 1:
					// 最新バージョンがある場合
					ScreenUtil.showError(scr, "ERR0021") ;
					return false ;
				case 2:
					// 論理削除された場合
					ScreenUtil.showError(scr, "ERR0020") ;
					return false ;
				case 3:
					// 物理削除された場合
					ScreenUtil.showError(scr, "ERR0020") ;
					return false ;
				default:
					return true ;
			}
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
			return false ;
		}
	}

	/**
	 * レベル変更処理メソッドです。<br>
	 * 
	 * @param entity ラベル情報エンティティ
	 */
	public void update(LabelInfoEntity entity)
	{
		try
		{
			// ラベル定義情報を更新する。
			LabelInfoManager.updateLabelInfo(entity) ;
			ScreenUtil.showMessage(scr, "MSG0003") ;
			// 成功した場合、一覧照会画面に戻る。
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
		}
		catch (Exception e)
		{
			// ERR0041=予期せぬエラーが発生しました。
			ScreenUtil.showError(scr, "ERR0041") ;
		}
	}

	/**
	 * マルチラベルリスト編集ツールを呼びだすメソッド。<br>
	 *  
	 * @param fileName レイアウトファイル名
	 */
	public void execEditTool(String fileName)
	{
		Runtime runtime = Runtime.getRuntime() ;
		String[] commandArgs = {
				EnvDefHandler.getInstance().getDefineValue("ML_APP_PATH"),
				fileName
		} ;
		try
		{
			runtime.exec(commandArgs) ;
		}
		catch (IOException e)
		{
			ScreenUtil.showError(scr, "ERR0016") ;

		}
	}

	/**
	 * レイアウトファイルを取り込メソッドです。<br>
	 * 
	 */
	public void loadLayout()
	{
		JFileChooser fc = new JFileChooser() ;
		fc.removeChoosableFileFilter(fc.getFileFilter()) ;
		fc.setFileFilter(new LayoutFileFilter()) ;
		fc.setDialogTitle("") ;
		int returnVal = fc.showOpenDialog(scr.getLoadBtn()) ;
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			String selFileName = fc.getSelectedFile().getName() ;
			try
			{
				// 選択されたレイアウトファイル名がラベル管理情報に存在する場合、エラーとする。
				if (LabelInfoManager.isLayoutFileNameExist(selFileName))
				{
					ScreenUtil.showError(scr, "ERR0017") ;
					return ;
				}
				// レイアウトファイルを解析できるかを判別する。
				SBPLLabelConvertManager parser = new SBPLLabelConvertManager() ;
				parser.convertLayoutToDoc(fc.getSelectedFile().getPath()) ;
				// レイアウトファイルを作業フォルダにコピーする。
				FileManager.copyFile(fc.getSelectedFile().getPath(),
						LabelInfoUtil.getLocalLayoutPath() + selFileName) ;
				scr.getLayoutNameField().setText(selFileName) ;
			}
			catch (DaiException e)
			{
				ScreenUtil.showError(scr, e.getMessage()) ;
			}
			catch (IOException e)
			{
				ScreenUtil.showError(scr, "ERR0003") ;
			}
		}
	}
}
