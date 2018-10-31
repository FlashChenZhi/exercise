// $Id: ToolScheduleInterface.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;


/**<jp>
 * 画面からスケジュール処理を実行するための共通メソッドを宣言したインターフェースです。<BR>
 * 画面の操作に必要なメソッドを規定します<BR>
 * このインターフェースをimplementするクラスは以下の操作を実装します。<BR>
 *   <code>addInitialParameter()</code>パラメータの入力<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/06/03</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the interface which declared the common method for the exection of 
 * schedule processing via screen.<BR>
 * It prescribes the methods required for display operatrion.<BR>
 * The class, which implements this interface, should implement following operations.<BR>
 *   input of <code>addInitialParameter()</code> parameter<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/06/03</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public interface ToolScheduleInterface extends ScheduleInterface
{
    // Class fields --------------------------------------------------
    // Public methods ------------------------------------------------
    /**<jp>
     * 指定されたパラメータを追加します。このメソッドは、ため打ちに初期表示を行うときに使用します。<BR>
     * 内部でのチェック処理は行わずに、そのままパラメータの追加を行います。<BR>
     * @param param 追加するパラメータの内容
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Append the specified parameters. This method will be used for the initial display of entered
     * data summary.<BR>
     * It appends parameters without conducting the internal checks.<BR>
     * @param param :contents of the parameter to add
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the check.
     </en>*/
    public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException;

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

