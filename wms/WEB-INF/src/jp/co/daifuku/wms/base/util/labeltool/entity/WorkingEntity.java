// $Id: WorkingEntity.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.entity ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * WorkingEntity class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
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


public class WorkingEntity
{
	/** <code>ラベル登録画面に渡す遷移モード</code> */
	private String mode = null ;

	/** <code>ラベル登録画面に渡す管理No</code> */
	private String id = null ;

	/**
	 * @return idを返します。
	 */
	public String getId()
	{
		return id ;
	}

	/**
	 * @param id idを設定します。
	 */
	public void setId(String id)
	{
		this.id = id ;
	}

	/**
	 * @return modeを返します。
	 */
	public String getMode()
	{
		return mode ;
	}

	/**
	 * @param mode modeを設定します。
	 */
	public void setMode(String mode)
	{
		this.mode = mode ;
	}


}
