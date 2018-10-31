// $Id: OperatorException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.exception;

import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.wms.asrs.location.RouteController;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * オペレータクラスが生成する例外です。<br>
 * 作業データの処理が正常に完了できない場合に、原因と詳細を保持して
 * 画面・端末に通知するための例外です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class OperatorException
        extends CommonException
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** エラーコード (予定日重複) */
    public static final String ERR_PLAN_DAY_DUPLICATED = "00";

    /** エラーコード (エリア+棚 重複) */
    public static final String ERR_AREA_LOCATION_DUPLICATED = "01";

    /** エラーコード (ロットNo 重複) */
    public static final String ERR_LOT_NO_DUPLICATED = "03";

    /** エラーコード (仕入先 重複) */
    public static final String ERR_SUPPLIER_DUPLICATED = "04";

    /** エラーコード (作業No. 重複) */
    public static final String ERR_JOB_NO_DUPLICATED = "05";

    /** エラーコード (商品コード重複) */
    public static final String ERR_ITEM_DUPLICATED = "06";

    /** エラーコード (伝票No 重複) */
    public static final String ERR_TICKET_NO_DUPLICATED = "07";

    /** エラーコード (他端末で更新済み) */
    public static final String ERR_ALREADY_UPDATED = "10";

    /** エラーコード (他端末で作業中) */
    public static final String ERR_WORKING_INPROGRESS = "11";

    /** エラーコード (作業完了済み) */
    public static final String ERR_ALREADY_COMPLETED = "12";

    /** エラーコード (未開始データが存在した) */
    public static final String ERR_NOT_START_EXISTS = "13";

    /** エラーコード (出庫可能数不足) */
    public static final String ERR_SHORTAGE_ALLOCATION_QTY = "20";

    /** エラーコード (オーバーフロー) */
    public static final String ERR_OVERFLOW = "21";

    /** エラーコード (すでに引当済) */
    public static final String ERR_ALREADY_ALLOCATED = "22";

    /** エラーコード (エリアなし) */
    public static final String ERR_NO_AREA_FOUND = "30";

    /** エラーコード (AS/RSエリア) */
    public static final String ERR_ASRS_AREA_FOUND = "31";

    /** エラーコード (棚マスタなし) */
    public static final String ERR_NO_LOCATION_FOUND = "32";

    /** エラーコード (商品固定棚なし) */
    public static final String ERR_FIXED_ITEM_LOCATION_FOUND = "33";

    /** エラーコード (棚フォーマットエラー) */
    public static final String ERR_ILLEGAL_LOCATION_FORMAT = "34";

    /** エラーコード (棚卸作業範囲外の棚) */
    public static final String ERR_INVENT_LOCATION_OUTSIDE_RANGE = "40";

    /** エラーコード (棚卸結果確定済み) */
    public static final String ERR_INVENT_ALREADY_COMPLETED = "41";

    /** エラーコード (棚卸範囲重複) */
    public static final String ERR_INVENT_LOCATION_DUPLICATED = "42";

    /** ルートチェックエラー (ルートステータスに詳細がセットされます) */
    public static final String ERR_ROUTE = "50";
    
    /** エラーコード（同一パレットに開始されていない搬送あり） */
    public static final String ERR_EXIST_UNSTART_CARRYINFO = "51";
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private String _errorCode;

    private Object _detail;

    private int _errorLineNo = 0;

    private int _routeStatus = RouteController.ACTIVE;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 詳細情報なしでオペレータ例外を生成します。
     */
    public OperatorException()
    {
        this("");
    }

    /**
     * エラーコード情報を指定してオペレータ例外を生成します。
     * @param errorCode エラーコード
     */
    public OperatorException(String errorCode)
    {
        this(errorCode, null);
    }

    /**
     * エラーコード情報および詳細を指定してオペレータ例外を生成します。
     * @param errorCode エラーコード
     * @param detail 詳細情報
     */
    public OperatorException(String errorCode, Object detail)
    {
        setErrorCode(errorCode);
        setDetail(detail);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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

    /**
     * 問題発生時の行番号を返します。
     * @return 行番号を返します。(1スタート)
     */
    public int getErrorLineNo()
    {
        return _errorLineNo;
    }

    /**
     * 問題発生時の行番号を設定します。
     * @param errorLineNo 行番号 (1スタート)
     */
    public void setErrorLineNo(int errorLineNo)
    {
        _errorLineNo = errorLineNo;
    }

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

    /**
     * ルートステータスを返します。<br>
     * エラーコードが ERR_ROUTE の場合にだけ取得可能です。
     * 
     * @return ルートステータス
     */
    public int getRouteStatus()
    {
        return _routeStatus;
    }

    /**
     * ルートステータスを設定します。<br>
     * エラーコードに ERR_ROUTE をセットするとき、このステータスもセットしてください。
     * 
     * @param routeStatus ルートステータス
     */
    public void setRouteStatus(int routeStatus)
    {
        _routeStatus = routeStatus;
    }

    /**
     * エラーの内容を表示用に編集します。
     * @return 表示用の内容
     */
    @Override
    public String toString()
    {
        StringBuilder buff = new StringBuilder("OperatorException\n");
        buff.append("ErrorCode:");
        buff.append(_errorCode);
        buff.append(",ErrorLine:");
        buff.append(_errorLineNo);
        buff.append(",Detail:");
        buff.append(_detail);
        return new String(buff);
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
    /**
     * テスト。
     * @param args not used.
     */
    public static void main(String[] args)
    {
        arrayDetailSample();
        listDetailSample();
    }

    /**
     * 複数項目(配列)のリスト形式サンプル。
     */
    private static void arrayDetailSample()
    {
        // 複数の項目をリストにしてOperatorExceptionをスローする
        List<String[]> dlist = new ArrayList<String[]>();

        for (int i = 0; i < 3; i++)
        {
            // 行データの生成
            String[] rowdata = {
                String.valueOf("AREA" + i),
                String.valueOf("LOCATION" + i),
            };
            // リストへのセット
            dlist.add(rowdata);
        }
        // 例外の生成
        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
        // 例外への詳細セット
        ex.setDetail(dlist);

        // 例外のスロー (サンプルではコメント)
        // throw ex ;

        // ----- スローされた側の取り出し
        List<Object[]> detailList = ex.getDetailObjectArrayList();
        for (Object[] rowdata : detailList)
        {
            // rowdata は1行のデータを表す配列
            for (Object col : rowdata)
            {
                // col が 1項目を表す
                System.out.print(String.valueOf(col));
                System.out.print(",");
            }
            System.out.println();
        }
    }

    /**
     * 単一項目のリスト形式サンプル。
     */
    private static void listDetailSample()
    {
        // 項目をリストにしてOperatorExceptionをスローする
        List<String> dlist = new ArrayList<String>();

        for (int i = 0; i < 3; i++)
        {
            // データの生成
            String rowdata = "WORK_DAY " + i;
            // リストへのセット
            dlist.add(rowdata);
        }
        // 例外の生成
        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
        // 例外への詳細セット
        ex.setDetail(dlist);

        // 例外のスロー (サンプルではコメント)
        // throw ex ;

        // ----- スローされた側の取り出し
        List<Object> detailList = ex.getDetailList();
        for (Object rowdata : detailList)
        {
            System.out.println(String.valueOf(rowdata));
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: OperatorException.java 87 2008-10-04 03:07:38Z admin $";
    }
}
