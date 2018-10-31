// $$Id: LabelInfoEntity.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.entity ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ラベル管理情報エンティティクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/06</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelInfoEntity
{
	/** <code>管理No</code> */
	private String id = null ;

	/** <code>ラベル名称</code> */
	private String name = null ;

	/** <code>レイアウトファイル名</code> */
	private String layoutName = null ;

	/** <code>バージョン番号</code> */
	private String versionNo = null ;

	/** <code>状態</code> */
	private String status = null ;

	/**
	 * 配列に転換するメソッドです。
	 * 
	 * @return 配列
	 */
	public String[] toArray()
	{
		String[] b = new String[4] ;
		b[0] = this.id ;
		b[1] = this.name ;
		b[2] = this.layoutName ;
		b[3] = this.status ;
		return b ;
	}

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
	 * @return layoutNameを返します。
	 */
	public String getLayoutName()
	{
		return layoutName ;
	}

	/**
	 * @param layoutName layoutNameを設定します。
	 */
	public void setLayoutName(String layoutName)
	{
		this.layoutName = layoutName ;
	}

	/**
	 * @return nameを返します。
	 */
	public String getName()
	{
		return name ;
	}

	/**
	 * @param name nameを設定します。
	 */
	public void setName(String name)
	{
		this.name = name ;
	}

	/**
	 * @return statusを返します。
	 */
	public String getStatus()
	{
		return status ;
	}

	/**
	 * @param status statusを設定します。
	 */
	public void setStatus(String status)
	{
		this.status = status ;
	}

	/**
	 * @return versionNoを返します。
	 */
	public String getVersionNo()
	{
		return versionNo ;
	}

	/**
	 * @param versionNo versionNoを設定します。
	 */
	public void setVersionNo(String versionNo)
	{
		this.versionNo = versionNo ;
	}

}
