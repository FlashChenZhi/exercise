// $Id: IdSchException.java 4606 2009-07-03 07:28:29Z shibamoto $
package jp.co.daifuku.wms.base.rft;

import java.util.List;

import jp.co.daifuku.common.CommonException;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * IDスケジュール処理で異常が発生した場合に使用する例外です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4606 $, $Date: 2009-07-03 16:28:29 +0900 (金, 03 7 2009) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This exception is used when abnormal definitions are made.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4606 $, $Date: 2009-07-03 16:28:29 +0900 (金, 03 7 2009) $
 * @author  $Author: shibamoto $
 </en>*/
public class IdSchException
        extends CommonException
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 日次更新処理中 */
    public static final String DAILY_UPDATING = "01";

    /** 端末番号不正 */
    public static final String ILLEGAL_TERMINAL_NUMBER = "02";

    /** エラーコード (ロットNo. 重複) */
    public static final String ERR_LOT_NO_DUPLICATED = "03";

    // DAC Develpment
    /** Multiple areas */
    public static final String ERR_AREA_NO_DUPLICATED = "04";

    /** 在庫管理パッケージ無し */
    public static final String HAVE_NO_STOCKPACK = "05";

    /** 起動時起動受信 */
    public static final String ALREADY_STARTED = "10";

    /** 未起動時終了受信 */
    public static final String ALREADY_STOPPED = "11";

    /** 休憩時休憩受信 */
    public static final String ALREADY_RESTED = "12";

    /** 未休憩時再開受信 */
    public static final String ALREADY_RESTARTED = "13";

    /** 禁止文字チェックでNG (パラメータに禁止文字が含まれている) */
    public static final String PATTERN_NG = "14";

    /** 実績数チェックでNG (WmsParam.MAX_TOTAL_QTYを超過する) */
    public static final String RESULT_QTY_NG = "15";

    /**
     * 認証エラー用インターフェース
     */
    public interface Authentication
    {
        /** 認証の設定が不正 */
        //パスワード認証しない設定の時に、パスワードがセットされていた場合
        public static final String SETTING_ERROR = "20";

        /** 仮パスワード */
        public static final String PASSWORD_TENTATIVE = "25";

        /** ユーザIDもしくはパスワードが不正 **/
        public static final String AUTH_ERR_USERPASS = "26";
        
        /** ユーザロック **/
        public static final String AUTH_ERR_USERLOCK = "27";
        
        /** パスワード有効期限切れ **/
        public static final String AUTH_ERR_PWDEXPIRE = "28";
        
        /** ログイン権限なし **/
        public static final String AUTH_ERR_USERPERMISSION = "29";
    }

    /** 作業中データあり */
    public static final String WORKING_DATA_EXISTS = "30";

    /** 作業中データあり(別作業) */
    public static final String OTHER_WORKING_DATA_EXISTS = "31";
    
    /**
     * 作業開始エラー用インターフェース
     */
    public interface StartError
    {
        /** 作業者は他端末で作業中 */
        public static final String USER_ALREADY_WORK = "40";

        /** 端末は別ユーザログイン中 */
        public static final String TERMINAL_ALREADY_LOGIN = "41";
    }

    /** IPアドレス不正 */
    public static final String ILLEGAL_IP_ADDRESS = "50";
    
    /** クラス生成エラー */
    public static final String INSTACIATE_ERROR = "51";

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private String _errorCode;

    private Object _detail;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**<jp>
     * 詳細メッセージを指定しないで Exception を構築します
     </jp>*/
    /**<en>
     * Constructs the Exception without specifying the detail message.
     </en>*/
    public IdSchException()
    {
        super();
    }

    /**<jp>
     * メッセージ付きの例外を作成します。
     * @param errorCode  エラーコード
     </jp>*/
    /**<en>
     * Creates exception with message attached.
     * @param errorCode  detail message
     </en>*/
    public IdSchException(String errorCode)
    {
        this(errorCode, null);
    }

    /**
     * エラーコード情報および詳細を指定して例外を生成します。
     * @param errorCode エラーコード
     * @param detail 詳細情報
     */
    public IdSchException(String errorCode, Object detail)
    {
        setErrorCode(errorCode);
        setDetail(detail);
    }

    /**
     * 発生元の例外を保存する例外を作成します。
     * 
     * @param cause 例外の発生元
     */
    protected IdSchException(Throwable cause)
    {
        super(cause);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 詳細情報を通常のリストとして取得します。<br>
     * 詳細情報が List でない場合は、ClassCastException が発生します。<br>
     * 
     * <hr><code><pre>
     *        // 項目をリストにしてOperatorExceptionをスローする
     *        List<String> dlist = new ArrayList<String>();
     *
     *        for (int i = 0; i < 3; i++)
     *        {
     *            // データの生成
     *            String rowdata = "WORK_DAY " + i;
     *            // リストへのセット
     *            dlist.add(rowdata);
     *        }
     *        // 例外の生成
     *        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
     *        // 例外への詳細セット
     *        ex.setDetail(dlist);
     *
     *        // 例外のスロー (サンプルではコメント)
     *        // throw ex ;
     *
     *        // ----- スローされた側の取り出し
     *        List＜Object> detailList = ex.getDetailList();
     *        for (Object rowdata : detailList)
     *        {
     *            System.out.println(String.valueOf(rowdata));
     *        }
     *        
     * </pre></code><hr>
     * 
     * @return 詳細情報 (内容は Object の一覧)
     * @throws ClassCastException 詳細情報が List でない場合にスローされます。<br>
     * これは OperatorException の生成元クラスと、受取側クラスの設計の不一致です。
     */
    @SuppressWarnings("unchecked")
    public List<Object> getDetailList()
            throws ClassCastException
    {
        List<Object> detList = (List<Object>)_detail;
        return detList;
    }

    /**
     * 詳細情報を配列のリストとして取得します。<br>
     * 詳細情報が List でない場合は、ClassCastException が発生します。<br>
     * 
     * <hr><code><pre>
     *        // 複数の項目をリストにしてOperatorExceptionをスローする
     *        List<String[]> dlist = new ArrayList<String[]>();
     *
     *        for (int i = 0; i < 3; i++)
     *        {
     *            // 行データの生成
     *            String[] rowdata = {
     *                String.valueOf("AREA" + i),
     *                String.valueOf("LOCATION" + i),
     *            };
     *            // リストへのセット
     *            dlist.add(rowdata);
     *        }
     *        // 例外の生成
     *        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
     *        // 例外への詳細セット
     *        ex.setDetail(dlist);
     *
     *        // 例外のスロー (サンプルではコメント)
     *        // throw ex ;
     *
     *        // ----- スローされた側の取り出し
     *        List＜Object[]> detailList = ex.getDetailObjectArrayList();
     *        for (Object[] rowdata : detailList)
     *        {
     *            // rowdata は1行のデータを表す配列
     *            for (Object col : rowdata)
     *            {
     *                // col が 1項目を表す
     *                System.out.print(String.valueOf(col));
     *                System.out.print(",");
     *            }
     *            System.out.println();
     *        }
     * </pre></code><hr>
     * 
     * @return 詳細情報 (内容は Object の一覧)
     * @throws ClassCastException 詳細情報が List でない場合にスローされます。<br>
     * これは OperatorException の生成元クラスと、受取側クラスの設計の不一致です。
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getDetailObjectArrayList()
            throws ClassCastException
    {
        List<Object[]> detList = (List<Object[]>)_detail;
        return detList;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * エラーコードを返します。
     * @return エラーコード
     */
    public String getErrorCode()
    {
        return _errorCode;
    }

    /**
     * エラーコードを設定します。
     * @param errorCode エラーコード
     */
    public void setErrorCode(String errorCode)
    {
        _errorCode = errorCode;
    }

    /**
     * 詳細情報をセットします。
     * 
     * @param detail 詳細情報
     */
    public void setDetail(Object detail)
    {
        _detail = detail;
    }

    /**
     * 詳細情報を文字列として取得します。<br>
     * 詳細情報が String でない場合も、文字列化します。
     * 
     * @return 詳細情報の文字列表現
     */
    public String getDetailString()
    {
        return String.valueOf(_detail);
    }


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    /**<en>
     * Returns the version of this class.
     * @return version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4606 $,$Date: 2009-07-03 16:28:29 +0900 (金, 03 7 2009) $");
    }


}
//end of class
