// $$Id: ScreenId.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.controller ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 画面IDの列挙クラスです。<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/29</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public enum ScreenId {

	/** <code>ログイン画面ID</code> */
	LOGIN,
	/** <code>ラベル情報一覧照会画面ID</code> */
	LABEL_INFO_LIST,
	/** <code>ラベル情報新規登録画面ID</code> */
	ADD_NEW_LABEL_INFO,
	/** <code>ラベル情報変更画面ID</code> */
	UPDATE_LABEL_INFO,
	/** <code>自動発行設定画面ID</code> */
	DATA_MAPPING_CONFIG,
	/** <code>サーバ側接続設定画面ID</code> */
	REMOTE_CONFIG
}
