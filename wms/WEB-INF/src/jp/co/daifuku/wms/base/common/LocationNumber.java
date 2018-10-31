// $Id: LocationNumber.java 7655 2010-03-17 09:46:05Z kishimoto $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.handler.Entity;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * 棚No.を管理するためのクラスです。<br>
 * 各種フォーマット済みの棚No.を相互変換するために使用します。<br>
 * AreaController, WmsFormatterで使用しています。<br>
 * <br>
 * <code>parse</code>メソッドよりセットされた棚情報をフォーマットします。<br>
 * フォーマット形式は以下の通りです。<br>
 * <table border=1>
 * <tr><td>メソッド名</td><td>メソッド説明</td><td>返却値例</td></tr>
 * <tr><td>format()</td><td>スケジュールパラメータ形式(=DnStockのDB形式)</td><td>01001001</td></tr>
 * <tr><td>formatDisp(true)</td><td>画面表示形式(フォーマットあり)</td><td>1-01-01</td></tr>
 * <tr><td>formatDisp(false)</td><td>画面表示形式(フォーマットなし)</td><td>10101</td></tr>
 * <tr><td>formatAsrs</td><td>AS/RS形式 (倉庫区分,サブロケーション付き)</td><td>101001001000</td></tr>
 * </table>
 * <br>
 * <br>
 * 
 * <b>使用方法</b>
 * <hr>
 * パラメータ形式をAS/RS形式に変換する場合の例:
 * <pre>
 * Area tarea; // エリア管理表から読み込んだエンティティ
 * LocationNumber locNum = new LocationNumber(tarea);
 * String paramLocation = "01001001"; // 実際はパラメータクラスから取得
 * locNum.parse(paramLocation);
 * String asrsLoc = locNum.formatAsrs();
 * </pre>
 * <hr>
 * AS/RS形式をパラメータ形式に変換する場合の例:
 * <pre>
 * LocationNumber locNum = new LocationNumber();
 * // 倉庫ステーションを特定するため、パースを行います。
 * locNum.parseAsrs(asrsLocation);
 * // フォーマットスタイルを取得するため、Areaを取得します
 * Area tarea = AreaCon.getAreaOfWarehouse(locNum.getWarehouseNo(), WareHouse.WAREHOUSE_NO);
 * locNum.setArea(tarea);
 * 
 * String paramLocation = locNum.format();
 * </pre>
 *
 * @version $Revision: 7655 $, $Date: 2010-03-17 18:46:05 +0900 (水, 17 3 2010) $
 * @author  Last commit: $Author: kishimoto $
 */
public class LocationNumber
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * フォーマットパターンの棚部分に使用されている文字(数字)
     */
    public static final char LOCATION_FORMAT_PATTERN_NUMBER = '9';

    /**
     * フォーマットパターンの棚部分に使用されている文字
     */
    public static final char LOCATION_FORMAT_PATTERN_CHAR = 'X';

    /**
     * 数字の正規表現
     */
    public static final String REGEX_NUMBER = "^[0-9]+$";

    /**
     * 文字の正規表現
     */
    public static final String REGEX_STRING = "^[0-9a-zA-Z]+$";

    /**
     * 配列の順：バンク
     */
    public static final int IDX_BANK = 0;

    /**
     * 配列の順：ベイ
     */
    public static final int IDX_BAY = 1;

    /**
     * 配列の順：レベル
     */
    public static final int IDX_LEVEL = 2;

    /**
     * 配列の順：サブロケーション
     */
    public static final int IDX_SUBLOC = 3;

    /**
     * AS/RSの棚定義配列
     */
    private static final int[] ASRS_DEF_LENGTH = {
            WmsParam.WAREHOUSE_LENGTH,
            WmsParam.ASRS_BANK_LENGTH,
            WmsParam.ASRS_BAY_LENGTH,
            WmsParam.ASRS_LEVEL_LENGTH,
            WmsParam.ASRS_SUBLOC_LENGTH,
    };

    /**
     * フォーマット時の0埋め用文字
     */
    private static final String ZERO = "0";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /**
     * ShelfのStationNumberの長さ
     */
    private static volatile int $asrsTotalLength = 0;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    /**
     * 倉庫No.<br>
     * AS/RSへのフォーマット時に使用
     */
    private String _warehouseNo;

    /**
     * フォーマットスタイル
     */
    private String _style;

    /**
     * 現在このクラスで保持している棚情報(_styleにより初期化されます)
     */
    private String[] _location = null;

    /**
     * DnStockの棚定義配列(_styleにより初期化されます)
     */
    private int[] _defLength = null;

    /**
     * DnStockのLocationNoの長さ
     */
    private int _totalLength = 0;

    /**
     * フォーマット定義の区切り文字を格納します。
     */
    private ArrayList<Character> _separateChar = new ArrayList<Character>();

    /**
     * フォーマット定義のパーツを格納します。
     */
    private ArrayList<String> _formatSegment = new ArrayList<String>();

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 該当するエリアに関する情報を保持します。<BR>
     * パラメータのエリア情報には
     * Warehouse.warehouse_no, Area.location_styleがセットされている必要があります。
     * 
     * @param area area.
     * @throws ScheduleException フォーマット定義に棚項目が含まれていない場合に通知されます
     */
    public LocationNumber(Entity area) throws ScheduleException
    {
        _warehouseNo = (String)area.getValue(WareHouse.WAREHOUSE_NO);
        setStyle((String)area.getValue(Area.LOCATION_STYLE));
    }

    /**
     * フォーマットを保持します。<br>
     * 
     * @param style フォーマット
     * @throws ScheduleException フォーマット定義に棚項目が含まれていない場合に通知されます
     */
    public LocationNumber(String style) throws ScheduleException
    {
        _warehouseNo = null;
        setStyle(style);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 引数で渡された棚情報を本クラスの棚情報に保持します。
     * 事前にフォーマット定義がセットされていない場合、このメソッドは使用できません。
     * 
     * @param dispLoc 画面表示の棚No.("1-01-01")
     * @throws LocationFormatException パースできなかった時スローされます。
     */
    public void parseDisp(String dispLoc)
            throws LocationFormatException
    {
        initLocation();

        StringBuilder buff = new StringBuilder();

        // 入力棚のパーツを格納します。
        ArrayList<String> inputSegment = new ArrayList<String>();

        char[] dispLocChars = dispLoc.toCharArray();
        int j = 0;

        // 入力棚をパーツに分けつつ区切り文字がフォーマット定義と同じかチェックします。
        for (int i = 0; i < dispLocChars.length; i++)
        {
            // 区切り文字だった場合(数値・文字・スペース以外だった場合)
            if (!(String.valueOf(dispLocChars[i]).matches(REGEX_NUMBER) || String.valueOf(dispLocChars[i]).matches(
                    REGEX_STRING)))
            {
                // 入力棚の区切り文字の個数が定義よりも多ければエラー
                if (j >= _separateChar.size())
                {
                    throw new LocationFormatException();
                }
                // 区切り文字がフォーマット定義の順番どおりかチェックします。
                if (_separateChar.get(j) != dispLocChars[i])
                {
                    throw new LocationFormatException();
                }
                j++;

                // パーツを格納します。
                inputSegment.add(String.valueOf(buff));
                // バッファをクリアする
                buff = new StringBuilder();
                continue;
            }
            else
            {
                try
                {
                    buff.append(dispLoc.charAt(i));
                }
                catch (IndexOutOfBoundsException e)
                {
                    throw new LocationFormatException();
                }
            }
        }
        if (buff.length() != 0)
        {
            // 最後のパーツを格納します。
            inputSegment.add(String.valueOf(buff));
            buff = new StringBuilder();
        }

        // パーツの数が同じか
        if (_formatSegment.size() != inputSegment.size())
        {
            throw new LocationFormatException();
        }

        // パーツごとに比較していきます。
        for (int i = 0; i < inputSegment.size(); i++)
        {
            // 文字列が数値のとき
            if (String.valueOf(inputSegment.get(i)).matches(REGEX_NUMBER)
                    && String.valueOf(_formatSegment.get(i)).matches(
                            "^" + String.valueOf(LOCATION_FORMAT_PATTERN_NUMBER) + "+$"))
            {
                // 入力された長さが定義以下の長さか
                if (inputSegment.get(i).length() > _formatSegment.get(i).length())
                {
                    throw new LocationFormatException();
                }
                // 入力された長さが定義の長さに足りていないときはゼロ埋めします。
                for (int k = inputSegment.get(i).length(); k < _formatSegment.get(i).length(); k++)
                {
                    inputSegment.set(i, ZERO + inputSegment.get(i));
                }
            }
            // 文字列が文字のとき
            else if (String.valueOf(inputSegment.get(i)).matches(REGEX_STRING)
                    && String.valueOf(_formatSegment.get(i)).matches(
                            "^" + String.valueOf(LOCATION_FORMAT_PATTERN_CHAR) + "+$"))
            {
                if (inputSegment.size() > 1)
                {
                    // 入力された長さが定義された長さと同じか
                    if (inputSegment.get(i).length() != _formatSegment.get(i).length())
                    {
                        throw new LocationFormatException();
                    }
                }
                else
                {
                    // ハイフン無しフォーマットの場合は定義文字数以上の場合のみエラー
                    if (inputSegment.get(i).length() > _formatSegment.get(i).length())
                    {
                        throw new LocationFormatException();
                    }
                }
            }
            else
            {
                throw new LocationFormatException();
            }
        }

        _location = inputSegment.toArray(new String[0]);


    }

    /**
     * 引数で渡された棚情報を本クラスの棚情報に保持します。<br>
     * フォーマットスタイルなしで棚定義配列を元にパースします。
     * 
     * @param paramLoc 棚No.(DnStock形式の棚フォーマット "10101")
     * @throws ParseException パースできなかった時スローされます。
     */
    public void parseParam(String paramLoc)
            throws ParseException
    {
        // check length
        if (paramLoc.length() != _totalLength)
        {
            throw new ParseException("Length mismatch", paramLoc.length());
        }

        initLocation();

        Vector<String> tempLoc = new Vector<String>();
        int charidx = 0;
        for (int i = 0; i < _defLength.length; i++)
        {
            if (paramLoc.length() < (charidx + _defLength[i]))
            {
                initLocation();
                throw new ParseException("Length mismatch", paramLoc.length());
            }
            tempLoc.add(paramLoc.substring(charidx, (charidx + _defLength[i])));
            //_location[i] = paramLoc.substring(charidx, (charidx + _defLength[i]));
            charidx += _defLength[i];
        }

        _location = tempLoc.toArray(new String[0]);
    }

    /**
     * AS/RS用棚No.をパースします。<br>
     * このメソッドでは、エリア情報を必要としませんので、デフォルト
     * コンストラクタで生成したインスタンスを使用することができます。
     * 
     * @param asrsLoc 棚No.(ex:"101001001000")
     * @throws ParseException パースできなかった時スローされます。
     */
    public void parseAsrs(String asrsLoc)
            throws ParseException
    {
        // check length
        if (asrsLoc.length() != $asrsTotalLength)
        {
            throw new ParseException("Length mismatch", asrsLoc.length());
        }

        initLocation();

        Vector<String> tempLoc = new Vector<String>();
        int charidx = 0;
        for (int i = 0; i < ASRS_DEF_LENGTH.length; i++)
        {
            if (asrsLoc.length() < (charidx + ASRS_DEF_LENGTH[i]))
            {
                initLocation();
                throw new ParseException("Length mismatch", asrsLoc.length());
            }

            String pc = asrsLoc.substring(charidx, (charidx + ASRS_DEF_LENGTH[i]));
            charidx += ASRS_DEF_LENGTH[i];

            // AS/RS棚の先頭は倉庫No.
            if (i == 0)
            {
                _warehouseNo = pc;
            }
            // 倉庫No.以降は通常の棚情報にマッピング
            else
            {
                tempLoc.add(pc);
            }
        }

        _location = tempLoc.toArray(new String[0]);

    }

    /**
     * 現在の棚No.をフォーマット定義にもとづいてフォーマットします。
     * 現在の棚がない場合（先にparseされていない）またはフォーマット形式がセットされていない場合は例外を返します。
     * 
     * @return フォーマットされた棚No.("1-01-01")
     * @throws ScheduleException フォーマットできなかった時スローされます。
     */
    public String formatDisp()
            throws ScheduleException
    {
        if (!hasValue())
        {
            // 6026103=フォーマットに必要な棚情報がセットされていないため変換できません。
            RmiMsgLogClient.write(6026103, this.getClass().getSimpleName());
            throw new ScheduleException();
        }

        StringBuilder total = new StringBuilder();
        char[] styleChars = _style.toCharArray();

        // 読み込み対象の棚情報配列番号
        int pidx = 0;

        String sStr = _location[0];
        int sidx = sStr.length();
        StringBuilder buff = new StringBuilder();

        for (int i = 0; i < styleChars.length; i++)
        {
            // 区切り文字だった場合
            char fchar = styleChars[i];
            if (fchar != LOCATION_FORMAT_PATTERN_NUMBER && fchar != LOCATION_FORMAT_PATTERN_CHAR)
            {
                buff.append(fchar);
                total.append(buff);

                if (++pidx < _location.length)
                {
                    sStr = _location[pidx];
                }
                else
                {
                    sStr = "";
                }
                sidx = sStr.length();
                buff = new StringBuilder();

                continue;
            }
            if (--sidx < 0)
            {
                // format length too long, insert zero
                buff.insert(0, ZERO);
            }
            else
            {
                buff.insert(0, sStr.charAt(sidx));
            }
        }
        // last pos
        total.append(buff);
        return new String(total);
    }

    /**
     * 現在の棚No.を DnStock用にフォーマットします。
     * 現在の棚がない場合（先にparseされていない）は例外を返します。
     * 
     * @return DnStock用の棚No.("10101")
     * @throws ScheduleException フォーマットできなかった時スローされます。
     */
    public String formatParam()
            throws ScheduleException
    {
        if (!hasValue())
        {
            // 6026103=フォーマットに必要な棚情報がセットされていないため変換できません。
            RmiMsgLogClient.write(6026103, this.getClass().getSimpleName());
            throw new ScheduleException();
        }

        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < _defLength.length; i++)
        {
            if (_location.length > i)
            {
                buff.append(fillZero(_location[i], _defLength[i]));
            }
            else
            {
                // AS/RSからParamに変換する場合、内部保持する棚と、
                // Paramの棚が一致しないことがあるための処理
                buff.append(fillZero("", _defLength[i]));
            }
        }

        return new String(buff);
    }

    /**
     * クラスが保持している棚を結合して返却します。
     * 
     * @return 結合された棚
     */
    public String getformat()
    {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < _defLength.length; i++)
        {
            if (_location.length > i)
            {
                buff.append(_location[i]);
            }
            else
            {
                // AS/RSからParamに変換する場合、内部保持する棚と、
                // Paramの棚が一致しないことがあるための処理
                buff.append(fillZero("", _defLength[i]));
            }
        }
        return new String(buff);
    }

    /**
     * 現在の棚No.を AS/RS用にフォーマットします。
     * 現在の棚がない場合（先にparseされていない）または倉庫No.がセットされていない場合は例外を返します。
     * 
     * @return AS/RS用の棚No.("101001001000")
     * @throws ScheduleException フォーマットできなかった時スローされます。
     */
    public String formatAsrs()
            throws ScheduleException
    {
        if (!hasValue())
        {
            // 6026103=フォーマットに必要な棚情報がセットされていないため変換できません。
            RmiMsgLogClient.write(6026103, this.getClass().getSimpleName());
            throw new ScheduleException();
        }
        else if (null == _warehouseNo)
        {
            // 6026103=フォーマットに必要な棚情報がセットされていないため変換できません。
            RmiMsgLogClient.write(6026103, this.getClass().getSimpleName());
            throw new ScheduleException();
        }

        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < ASRS_DEF_LENGTH.length; i++)
        {
            int dlen = ASRS_DEF_LENGTH[i];
            if (i == 0)
            {
                // build warehouse number
                buff.append(fillZero(_warehouseNo, dlen));
            }
            else
            {
                if (_location.length > i - 1)
                {
                    buff.append(fillZero(_location[i - 1], dlen));
                }
                else
                {
                    buff.append(fillZero("", dlen));
                }
            }
        }

        return String.valueOf(buff);
    }

    /**
     * 棚の項目長さを調整します。<BR>
     * 指定された棚項目が指定された長さより大きい場合は先頭をカットして返します。<BR>
     * 指定された長さに満たない場合は先頭を0埋めします。<br>
     * 
     * @param pcLoc 棚No.の一項目
     * @param dlen 調整する長さ
     * @return fixed string
     */
    private String fillZero(String pcLoc, int dlen)
    {
        int slen = pcLoc.length();
        if (dlen == slen)
        {
            // no need edit
            return pcLoc;
        }
        else if (dlen > slen)
        {
            // append zero
            StringBuilder buff = new StringBuilder(pcLoc);
            for (int i = 0; i < (dlen - slen); i++)
            {
                buff.insert(0, ZERO);
            }
            return new String(buff);
        }
        else
        {
            // cut
            return pcLoc.substring(slen - dlen);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * locationを返します。<br>
     * bank,bay,level,sublocation が返されます。<br>
     * LOC_IDX_BANK,LOC_IDX_BAY,LOC_IDX_LEVEL,LOC_IDX_SUBLOC をインデックスに
     * 使用してください。
     * 
     * @return locationを返します。
     */
    public String[] getLocation()
    {
        String[] aLoc = new String[_location.length];
        return Arrays.asList(_location).toArray(aLoc);
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 内部棚No.を初期化します。
     */
    protected void initLocation()
    {
        _location = new String[0];

    }

    /**
     * 棚No.がセットされているかどうかチェックします。
     * 
     * @return セットされていればtrue.
     */
    protected boolean hasValue()
    {
        for (int i = 0; i < _location.length; i++)
        {
            if (!StringUtil.isBlank(_location[i]))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * フォーマットスタイルを引数に受け取り、
     * そのフォーマットスタイルより、DnStockの棚形式を作成し、保持します。<BR>
     * 
     * @param style フォーマットスタイル
     * @throws ScheduleException フォーマット定義に棚項目が含まれていない場合に通知されます
     */
    protected void setStyle(String style)
            throws ScheduleException
    {
        if (StringUtil.isBlank(style))
        {
            throw new ScheduleException();
        }
        _style = style;

        StringBuilder buff = new StringBuilder();
        char[] styleChars = _style.toCharArray();

        // 各項目の桁数を保持する
        Vector<Integer> lengthList = new Vector<Integer>();
        // フォーマット定義を区切り文字とパーツに分けて取り出します。
        for (int i = 0; i < styleChars.length; i++)
        {
            // 区切り文字だった場合
            if (styleChars[i] != LOCATION_FORMAT_PATTERN_NUMBER && styleChars[i] != LOCATION_FORMAT_PATTERN_CHAR)
            {
                // 区切り文字が連続した場合、読み飛ばす
                if (buff.length() == 0)
                {
                    continue;
                }
                // パーツを格納します。
                _formatSegment.add(String.valueOf(buff));
                // 区切り文字を格納します。
                _separateChar.add(styleChars[i]);
                // 桁数を保持する
                lengthList.add(buff.length());
                // バッファをクリアする
                buff = new StringBuilder();
                continue;
            }
            else
            {
                try
                {
                    buff.append(_style.charAt(i));
                }
                catch (IndexOutOfBoundsException e)
                {
                    throw new ParseException("Length mismatch", _style.length());
                }
            }
        }
        if (buff.length() != 0)
        {
            // 最後のパーツを格納します。
            _formatSegment.add(String.valueOf(buff));
            // set last position
            lengthList.add(buff.length());
        }

        if (lengthList.size() == 0)
        {
            // フォーマット定義が異常な場合に通知します
            throw new ScheduleException();
        }

        // DnStock棚の定義をセットしlengthを求める
        _defLength = new int[lengthList.size()];
        for (int i = 0; i < lengthList.size(); i++)
        {
            _defLength[i] = lengthList.get(i);

            _totalLength += _defLength[i];
        }


        // AS/RSの棚のlengthを求める
        synchronized (ASRS_DEF_LENGTH)
        {
            // AS/RSの棚のlengthを求める
            if ($asrsTotalLength == 0)
            {
                for (int i = 0; i < ASRS_DEF_LENGTH.length; i++)
                {
                    $asrsTotalLength += ASRS_DEF_LENGTH[i];
                }
            }
        }

        // location情報を初期化
        initLocation();
    }


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
    public static String getVersion()
    {
        return "$Id: LocationNumber.java 7655 2010-03-17 09:46:05Z kishimoto $";
    }
}
