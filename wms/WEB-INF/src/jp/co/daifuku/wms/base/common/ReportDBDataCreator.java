// $Id: ReportDBDataCreator.java 7733 2010-03-26 06:21:56Z okayama $
package jp.co.daifuku.wms.base.common;

import java.io.IOException;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : T.Nakai<BR>
 * Maker :  T.Nakai<BR>
 * <BR>
 * <CODE>ReportDBDataCreator</CODE>インターフェースは、報告データ処理の操作を規定します。<BR>
 * 報告データ処理を行うクラスは本インターフェースを通じてスケジュール処理を行います。<BR>
 * 各スケジュール処理はこのインターフェースを<CODE>implement</CODE>し、処理を実装してください。<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7733 $, $Date: 2010-03-26 15:21:56 +0900 (金, 26 3 2010) $
 * @author  admin
 * @author  Last commit: $Author: okayama $
 */
public interface ReportDBDataCreator
{
    /**
     * データ報告処理を行います。データ報告処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * スケジュール処理が成功した場合はtrueを返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。<BR>
     */
    public boolean report();

    /**
     * 報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDBDataCreator<BR>
     */
    public ReportDBDataCreator getReportClass();

    /**
     * <CODE>report</CODE>メソッドの報告データの作成有無を通知します。<BR>
     * @return 報告データを作成した場合は、True、報告データが無かった場合は、Falseを返します。<BR>
     */
    public boolean isReport();

    /**
     * <CODE>report</CODE>メソッドでfalseが戻された場合に、その理由を表示するためのメッセージを取得します。<BR>
     * このメソッドは画面のメッセージエリアへの表示内容を取得するために使用します。<BR>
     * @return メッセージの詳細<BR>
     */
    public String getMessage();

    /**
     * 一時ファイルが存在するかどうかチェックを行います<BR>
     * @return 存在する場合：<CODE>true</CODE>、存在しない場合<CODE>false</CODE>
     */
    public boolean isExistTempFile();

    /**
     * 一時ファイルの数を取得します。<BR>
     * @return 一時ファイル数<BR>
     * @throws ReadWriteException
     */
    public int existTempFilesCount()
            throws ReadWriteException;

    /**
     * データ報告実績ファイルの作成処理を行ないます。<BR>
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。<BR>
     * @throws IOException 入出力の例外が発生した場合に通知されます。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    public boolean sendReportFile()
            throws IOException,
                ReadWriteException;

    /**
     * 統計情報取得処理を行います。統計情報処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public void statics()
            throws CommonException;
}
