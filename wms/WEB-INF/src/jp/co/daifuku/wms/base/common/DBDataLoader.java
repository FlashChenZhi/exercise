// $Id: DBDataLoader.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.common;

import java.io.IOException;
import java.util.Date;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleJob;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * Designer : K.Mukai <BR>
 * Maker :  K.Mukai<BR>
 * <BR>
 * <CODE>ExternalDataLoader</CODE>インターフェースは、データ取込み処理の操作を規定します。<BR>
 * データ取込み処理を行うクラスは本インターフェースを通じてスケジュール処理を行います。
 * 各スケジュール処理はこのインターフェースを<CODE>implement</CODE>し、処理を実装してください。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public interface DBDataLoader
        extends ScheduleJob
{
    /**
     * Get my loading limit from WMSParam.properties
     * 
     * @return My limit
     */
    public int getMyLoadingLimit();

    /**
     * データ取込み処理を行います。データ取込み処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * スケジュール処理が成功した場合はtrueを返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean loadEntity(WarenaviSystemController sysController, HostToEWN inEntity, Params outParameter)
            throws CommonException,
                IOException;

    /**
     * <CODE>load</CODE>メソッドでデータの読み飛ばしが行われた場合に、Trueで戻されます。<BR>
     * @return メッセージの詳細
     */
    public boolean isSkipFlag();

    /**
     * <CODE>load</CODE>メソッドでデータの登録が行われた場合に、Trueで戻されます。<BR>
     * @return メッセージの詳細
     */
    public boolean isRegistFlag();

    /**
     * <CODE>load</CODE>メソッドで取込異常終了の場合に、Trueで戻されます。<BR>
     * @return メッセージの詳細
     */
    public boolean isErrorFlag();

    /**
     * 取込開始日時を返します。<BR>
     * @return 取込開始日時
     */
    public Date getStartDate();

    /**
     * <CODE>load</CODE>メソッドでfalseが戻された場合に、その理由を表示するためのメッセージを取得します。<BR>
     * このメソッドは画面のメッセージエリアへの表示内容を取得するために使用します。<BR>
     * @return メッセージの詳細
     */
    public String getMessage();


    /*
     * Make BSR logging accessible to the SCH
     */
    public void doBSRLog(boolean skip, boolean regist);

}
