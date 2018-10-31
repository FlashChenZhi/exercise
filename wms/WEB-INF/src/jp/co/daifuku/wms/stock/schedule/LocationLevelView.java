package jp.co.daifuku.wms.stock.schedule;

import java.io.Serializable;

/*
 * Created on 2005/09/14
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * Designer : muneendra y<BR>
 * Maker    : muneendra y<BR> 
 * <BR>
 * ロケーションの設定、取得を行なうクラスです。
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>${date}</TD><TD>muneendra y</TD><TD></TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 2005/09/14
 */
public class LocationLevelView
        implements Serializable
{
    /**
     * レベル
     */
    private String _level;

    /**
     * ロケーションリスト
     */
    private LocationBayView[] _bayView;

    /**
     * レベルを取得します。
     * @return レベル
     */
    public String getLevel()
    {
        return _level;
    }

    /**
     * レベルをセットします。
     * @param level セットするレベル
     */
    public void setLevel(String level)
    {
        this._level = level;
    }

    /**
     * ロケーションリストを取得します。
     * @return ロケーションリスト
     */
    public LocationBayView[] getBayView()
    {
        return _bayView;
    }

    /**
     * ロケーションリストをセットします。
     * @param bayView セットするロケーションリスト
     */
    public void setBayView(LocationBayView[] bayView)
    {
        this._bayView = bayView;
    }

    /**
     * インナークラスです。
     */
    public class LocationBayView
            implements Serializable
    {
        private String _bankNo;

        private String _levelNo;

        private String _bayNo;

        private String _location;

        private String _balloonLocation;

        private String _status;

        private String _accessflg;

        private String _hardzone;

        /**
         * バンクNo.を取得します。
         * @return バンクNo.
         */
        public String getBankNo()
        {
            return _bankNo;
        }

        /**
         * バンクNo.を設定します。
         * @param bankNo セットするバンクNo.
         */
        public void setBankNo(String bankNo)
        {
            this._bankNo = bankNo;
        }

        /**
         * ベイNo.を取得します。
         * @return ベイNo.
         */
        public String getBayNo()
        {
            return _bayNo;
        }

        /**
         * ベイNo.をセットします。
         * @param bayNo セットするベイNo.
         */
        public void setBayNo(String bayNo)
        {
            this._bayNo = bayNo;
        }

        /**
         * レベルNo.を取得します。
         * @return レベルNo.
         */
        public String getLevelNo()
        {
            return _levelNo;
        }

        /**
         * レベルNo.をセットします。
         * @param levelNo セットするレベルNo.
         */
        public void setLevelNo(String levelNo)
        {
            this._levelNo = levelNo;
        }

        /**
         * ロケーションを取得します。
         * @return ロケーション
         */
        public String getLocation()
        {
            return _location;
        }

        /**
         * ロケーションをセットします。
         * @param location セットするロケーション
         */
        public void setLocation(String location)
        {
            this._location = location;
        }

        /**
         * バルーン用のロケーションを取得します。
         * @return balloonLocation バルーン用に編集したロケーション
         */
        public String getBalloonLocation()
        {
            return _balloonLocation;
        }

        /**
         * バルーン用のロケーションをセットします。
         * @param location バルーン用に編集したロケーション
         */
        public void setBalloonLocation(String location)
        {
            this._balloonLocation = location;
        }

        /**
         * 状態を取得します。
         * @return 状態
         */
        public String getStatus()
        {
            return _status;
        }

        /**
         * 状態をセットします。
         * @param status セットする状態
         */
        public void setStatus(String status)
        {
            this._status = status;
        }

        /**
         * アクセスフラグをセットします。
         * @param flg セットするアクセスフラグ
         */
        public void setAccessFlg(String flg)
        {
            this._accessflg = flg;
        }

        /**
         * アクセスフラグを取得します。
         * @return アクセスフラグ
         */
        public String getAccessFlg()
        {
            return _accessflg;
        }

        /**
         * ハードゾーンを取得します。
         * @return ハードゾーン
         */
        public String getHardzone()
        {
            return _hardzone;
        }

        /**
         * ハードゾーンをセットします
         * @param tmp ハードゾーン
         */
        public void setHardzone(String tmp)
        {
            this._hardzone = tmp;
        }

        /**
         * ツールチップ用文字列を取得します。
         * @return ツールチップ用文字列
         */
        public String getTooltip()
        {
            StringBuffer sb = new StringBuffer();
            // 棚表示
            sb.append("\n");
            sb.append(_balloonLocation);
            sb.append("\n");
            return String.valueOf(sb);
        }
    }

}
