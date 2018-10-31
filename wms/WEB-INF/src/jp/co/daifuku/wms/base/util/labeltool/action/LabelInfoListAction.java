// $$Id: LabelInfoListAction.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.action ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component ;
import java.io.File ;
import java.util.ArrayList ;

import jp.co.daifuku.wms.base.util.labeltool.base.entity.DataMappingInfoHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.FileManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.LabelInfoManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenController ;
import jp.co.daifuku.wms.base.util.labeltool.controller.ScreenId ;
import jp.co.daifuku.wms.base.util.labeltool.entity.LabelInfoEntity ;
import jp.co.daifuku.wms.base.util.labeltool.gui.LabelInfoListScr ;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem ;


/**
 * ラベル管理情報に関する動作処理を行うクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/13</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelInfoListAction
{
	/** <code>動作の発生元画面</code> */
	private LabelInfoListScr scr = null ;

	/**
	 * このクラスのコンストラクタです。
	 * 
	 * @param form 動作の発生元画面
	 */
	public LabelInfoListAction(Component form)
	{
		scr = (LabelInfoListScr)form ;
	}

	/**
	 * ラベル管理一覧情報を取得します。
	 *  
	 * @return 画面に表示する管理情報データリスト
	 */
	public ArrayList<LabelInfoEntity> getLabelInfoList()
	{
		LabelInfoEntity entity = null ;
		ArrayList<LabelInfoEntity> entityList = new ArrayList<LabelInfoEntity>() ;
		LabelItem[] items = null ;
		try
		{
			items = LabelInfoManager.getRemoteLabelInfoList() ;
			if (items == null)
			{
				return entityList ;
			}
			for (int i = 0; i < items.length; i++)
			{
				entity = new LabelInfoEntity() ;
				entity.setId(String.valueOf(items[i].getId())) ;
				entity.setLayoutName(items[i].getLayoutName()) ;
				entity.setName(items[i].getName()) ;
				entity.setVersionNo(items[i].getVersionNo()) ;

				// サーバ側レイアウトがない場合
				String remoteFileName = LabelInfoUtil.getRemoteLayoutPath()
						+ items[i].getLayoutName() ;
				File remoteFile = new File(remoteFileName) ;
				if (!remoteFile.exists())
				{
					entity.setStatus("-1") ;
				}

				// 論理削除の場合
				else if (!StringUtil.isEmpty(items[i].getDeleteFlag())
						&& items[i].getDeleteFlag().equals("1"))
				{
					entity.setStatus("9") ;
				}
				else
				{
					entity.setStatus("1") ;
					LabelItem localItem = LabelInfoManager.getLocalLabelInfo(items[i].getId()) ;
					if (localItem != null)
					{
						// サーバ側のバージョンＮｏとローカル側のバージョンＮｏが一致する場合、"0"とする。
						if (Integer.parseInt(localItem.getVersionNo()) >= Integer.parseInt(items[i].getVersionNo()))
						{
							String localFileName = LabelInfoUtil.getLocalLayoutPath()
									+ items[i].getLayoutName() ;
							File localFile = new File(localFileName) ;
							if (localFile.exists())
							{

								if (FileManager.compare(localFileName, remoteFileName))
								{
									entity.setStatus("0") ;
								}
								else
								{
									// ローカル側レイアウト変更がある場合。
									entity.setStatus("2") ;
								}
							}
							else
							{
								entity.setStatus("0") ;
							}
						}
						else
						{
							entity.setVersionNo(localItem.getVersionNo()) ;
						}
					}
				}
				entityList.add(entity) ;
			}
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
			return null ;
		}
		return entityList ;
	}

	/**
	 * ラベル管理一覧情報を同期化処理します。
	 *  
	 * @return 画面に表示する管理情報データリスト
	 */
	public ArrayList<LabelInfoEntity> syncLabelInfoList()
	{
		ArrayList<LabelInfoEntity> entityList = new ArrayList<LabelInfoEntity>() ;
		LabelItem[] items = null ;
		try
		{
			items = LabelInfoManager.getRemoteLabelInfoList() ;
			if (items.length == 0)
			{
				return entityList ;
			}
			for (int i = 0; i < items.length; i++)
			{
				LabelInfoEntity entity = new LabelInfoEntity() ;
				entity.setId(String.valueOf(items[i].getId())) ;
				entity.setLayoutName(items[i].getLayoutName()) ;
				entity.setName(items[i].getName()) ;
				entity.setVersionNo(items[i].getVersionNo()) ;

				// サーバ側レイアウトがない場合
				String remoteFileName = LabelInfoUtil.getRemoteLayoutPath()
						+ items[i].getLayoutName() ;
				File remoteFile = new File(remoteFileName) ;
				if (!remoteFile.exists())
				{
					entity.setStatus("-1") ;
				}

				// 論理削除の場合
				else if (!StringUtil.isEmpty(items[i].getDeleteFlag())
						&& items[i].getDeleteFlag().equals("1"))
				{
					entity.setStatus("9") ;
				}
				else
				{

					entity.setStatus("1") ;
					LabelItem localItem = null ;
					localItem = LabelInfoManager.getLocalLabelInfo(String.valueOf(items[i].getId())) ;
					// サーバ側が新しいバージョンがない場合。
					if (localItem != null
							&& (Integer.parseInt(localItem.getVersionNo()) >= Integer.parseInt(items[i].getVersionNo())))
					{
						String localFileName = LabelInfoUtil.getLocalLayoutPath()
								+ items[i].getLayoutName() ;
						File localFile = new File(localFileName) ;
						if (localFile.exists())
						{
							// ローカル側レイアウト変更がある場合。
							if (!FileManager.compare(localFileName, remoteFileName))
							{
								// 確認画面を表示する。
								if (ScreenUtil.showConfirm(scr, "MSG0017") == ScreenUtil.OK_OPTION)
								{
									// ローカル側のファイルにサーバ側のファイルを上書きする。
									FileManager.downloadLayoutFile(items[i].getLayoutName()) ;
									FileManager.downloadXmlFile(items[i].getLayoutName()) ;
									FileManager.downloadEntityFile(items[i].getLayoutName()) ;
									LabelInfoManager.syncLabelInfo(items[i].getId()) ;
									entity.setStatus("0") ;
								}
								else
								{
									entity.setStatus("2") ;
								}
							}
							else
							{
								entity.setStatus("0") ;
							}
						}
						else
						{
							// ローカル側のファイルにサーバ側のファイルを上書きする。
							FileManager.downloadLayoutFile(items[i].getLayoutName()) ;
							FileManager.downloadXmlFile(items[i].getLayoutName()) ;
							FileManager.downloadEntityFile(items[i].getLayoutName()) ;
							entity.setStatus("0") ;
						}
					}
					else
					{
						FileManager.downloadLayoutFile(items[i].getLayoutName()) ;
						FileManager.downloadXmlFile(items[i].getLayoutName()) ;
						FileManager.downloadEntityFile(items[i].getLayoutName()) ;
						LabelInfoManager.syncLabelInfo(items[i].getId()) ;
						entity.setStatus("0") ;
					}
				}
				entityList.add(entity) ;
			}
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
			return null ;
		}
		return entityList ;
	}

	/**
	 * 変更、削除、自動発行設定を行う前にチェック処理を行います。
	 * 
	 * @return 処理を続ける場合、trueを返します。
	 *         処理を中止する場合、falseを返します。
	 */
	public boolean checkLabelInfo()
	{
		String id = scr.getDataList().get(scr.getJTable().getSelectedRow()).getId() ;
		String layoutName = scr.getDataList().get(scr.getJTable().getSelectedRow()).getLayoutName() ;
		try
		{
			switch (LabelInfoManager.getStatus(id))
			{
				case 0:
					return true ;
				case 1:
					// サーバ側最新バージョンがある場合、確認画面を表示する。
					// キャンセルの場合, falseを返す。
					if (ScreenUtil.showConfirm(scr, "MSG0001") == ScreenUtil.CANCEL_OPTION)
					{
						return false ;
					}
					// OKの場合
					// サーバ側最新なレイアウトファイルとXML定義ファイルを受信する。
					FileManager.downloadLayoutFile(layoutName) ;
					FileManager.downloadXmlFile(layoutName) ;
					FileManager.downloadEntityFile(layoutName) ;
					// ローカル側の管理情報を同期する。
					LabelInfoManager.syncLabelInfo(id) ;
					return true ;
				case 2:
					// 論理削除された場合
					ScreenUtil.showError(scr, "ERR0020") ;
					return false ;
				case 3:
					// 物理削除された場合
					ScreenUtil.showError(scr, "ERR0020") ;
					return false ;
				default:
			}
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
			return false ;
		}
		return true ;
	}

	/**
	 * ラベル情報を回復するメソッドです。
	 * 
	 * @param id 管理番号
	 * @return 回復成功した場合に、trueを返します。<br>
	 *         回復失敗した場合に、falseを返します。
	 */
	public boolean recoverLabelInfo(String id)
	{
		try
		{
			// ラベル情報を回復する。
			LabelInfoManager.recoverLabelInfo(id) ;
			LabelItem item = LabelInfoManager.getRemoteLabelInfo(id) ;
			// サーバからレイアウトファイルを受信する。
			FileManager.downloadLayoutFile(item.getLayoutName()) ;
			// サーバからXML定義ファイルを受信する。
			FileManager.downloadXmlFile(item.getLayoutName()) ;
			// サーバからエンティティファイルを受信する。
			FileManager.downloadEntityFile(item.getLayoutName()) ;
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
			return false ;
		}
		return true ;
	}

	/**
	 * レベル削除処理メソッドです。<br>
	 * 
	 * @param id 管理No
	 */
	public void deleteLabel(String id)
	{
		try
		{
			// 確認画面を表示する。
			if (ScreenUtil.showConfirm(scr, "MSG0012") == ScreenUtil.CANCEL_OPTION)
			{
				return ;
			}
			LabelInfoManager.removeLabelInfo(id) ;
			DataMappingInfoHandler.deleteMapingInfo(id) ;
			ScreenUtil.showMessage(scr, "MSG0004") ;
			ScreenController.forwardTo(ScreenId.LABEL_INFO_LIST, null) ;
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
		}
	}

	/**
	 * レベル論理削除処理メソッドです。<br>
	 * 
	 * @param id 管理No
	 */
	public void logicRemoveLabel(String id)
	{
		// 確認画面を表示する。
		if (ScreenUtil.showConfirm(scr, "MSG0012") == ScreenUtil.CANCEL_OPTION)
		{
			return ;
		}
		try
		{
			LabelInfoManager.logicRemoveLabelInfo(id) ;
			ScreenUtil.showMessage(scr, "MSG0004") ;
		}
		catch (DaiException e)
		{
			ScreenUtil.showError(scr, e.getMessage()) ;
		}
		scr.setInitData(null) ;
	}

}
