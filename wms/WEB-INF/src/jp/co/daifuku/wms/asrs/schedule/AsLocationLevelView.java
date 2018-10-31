//$Id: AsLocationLevelView.java 4559 2009-07-01 02:32:57Z okamura $

/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.io.Serializable;


/**
 * Designer : muneendra y<BR>
 * Maker    : muneendra y<BR> 
 * <BR>
 * ロケーションマスタ情報を受け渡しするためのクラスです。
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>muneendra y</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 2005/09/14
 */
public class AsLocationLevelView
        implements Serializable
{
    /**
     * レベル
     */
    private String _level;

    /**
     * ベイ
     */
    private AsLocationBayView[] _bayView;

    /**
     * levelを返します。
     * @return level を返す
     */
    public String getLevel()
    {
        return _level;
    }

    /**
     * levelを設定します。
     * @param level levelを設定する
     */
    public void setLevel(String level)
    {
        this._level = level;
    }

    /**
     * ロケーション情報の配列を返します。
     * @return ロケーション情報の配列を返す
     */
    public AsLocationBayView[] getBayView()
    {
        return _bayView;
    }

    /**
     * ロケーション情報の配列を設定します。
     * @param bayView ロケーション情報の配列を設定する

     */
    public void setBayView(AsLocationBayView[] bayView)
    {
        this._bayView = bayView;
    }

    /**
     * ロケーション情報
     */
    public class AsLocationBayView
            implements Serializable
    {
        private String _bankNo;

        private String _levelNo;

        private String _bayNo;

        private String _location;

        private String _balloonLocation;

        private String _status;

        private String _accessNgFlg;

        private String _hardzone;

        private String _prohibitFlg;

        private String _pStatus;

        private String _empty;

        private String _wareHouseNo;

        private String _palletId;
        
        private String _softzoneId;
        
        private String _softzoneName;

        /**
         * bankNoを返します。
         * @return bankNo を返す。
         */
        public String getBankNo()
        {
            return _bankNo;
        }

        /**
         * bankNoを設定します。
         * @param bankNo bankNo を設定する。
         */
        public void setBankNo(String bankNo)
        {
            this._bankNo = bankNo;
        }

        /**
         * bayNoを返します。
         * @return bayNo を返す。
         */
        public String getBayNo()
        {
            return _bayNo;
        }

        /**
         * bayNoを設定します。
         * @param bayNo bayNo を設定する。
         */
        public void setBayNo(String bayNo)
        {
            this._bayNo = bayNo;
        }

        /**
         * levelNoを返します。
         * @return levelNo を返す
         */
        public String getLevelNo()
        {
            return _levelNo;
        }

        /**
         * levelNoを設置します。
         * @param levelNo levelNo を設定する。
         */
        public void setLevelNo(String levelNo)
        {
            this._levelNo = levelNo;
        }

        /**
         * locationを返します。
         * @return location を返す。
         */
        public String getLocation()
        {
            return _location;
        }

        /**
         * locationを設定します。
         * @param location location を設定する。
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
         * statusを返します。
         * @return status を返します。
         */
        public String getStatus()
        {
            return _status;
        }

        /**
         * statusを設定します。
         * @param status status を設定する。
         */
        public void setStatus(String status)
        {
            this._status = status;
        }

        /**
         * Accessflgを返します。
         * @return Accessflg を戻す。
         */
        public String getAccessNgFlg()
        {
            return _accessNgFlg;
        }

        /**
         * Accessflgを設定します。
         * @param flg を設定する。
         */
        public void setAccessNgFlg(String flg)
        {
            this._accessNgFlg = flg;
        }

        /**
         * Hardzoneを返します。
         * @return Hardzone を返す。
         */
        public String getHardzone()
        {
            return _hardzone;
        }

        /**
         * Hardzoneを設定します。
         * @param tmp Hardzone を設定する。
         */
        public void setHardzone(String tmp)
        {
            this._hardzone = tmp;
        }

        /**
         * prohibitFlgを返します。
         * @return prohibitFlg を戻す。
         */
        public String getProhibitFlg()
        {
            return _prohibitFlg;
        }

        /**
         * prohibitFlgを設定します。
         * @param prohibitFlg を設定する。
         */
        public void setProhibitFlg(String prohibitFlg)
        {
            this._prohibitFlg = prohibitFlg;
        }

        /**
         * palletStatusを返します。
         * @return palletStatus を戻す。
         */
        public String getPStatus()
        {
            return _pStatus;
        }

        /**
         * pstatusを設定します。
         * @param pstatus を設定する。
         */
        public void setPStatus(String pstatus)
        {
            this._pStatus = pstatus;
        }

        /**
         * emptyを返します。
         * @return empty を戻す。
         */
        public String getEmpty()
        {
            return _empty;
        }

        /**
         * emptyを設定します。
         * @param empty を設定する。
         */
        public void setEmpty(String empty)
        {
            this._empty = empty;
        }

        /**
         * wareHouseNoを返します。
         * @return wareHouseNo を返す。
         */
        public String getWareHouseNo()
        {
            return _wareHouseNo;
        }

        /**
         * wareHouseNoを設定します。
         * @param wareHouseNo wareHouseNo を設定する。
         */
        public void setWareHouseNo(String wareHouseNo)
        {
            this._wareHouseNo = wareHouseNo;
        }

        /**
         * palletIdを返します。
         * @return palletId を返す。
         */
        public String getPalletId()
        {
            return _palletId;
        }

        /**
         * palletIdを設定します。
         * @param palletId palletId を設定する。
         */
        public void setPalletId(String palletId)
        {
            this._palletId = palletId;
        }

        /**
         * ツールチップ用文字列を返す
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

        /**
         * softzoneIdを返します。
         * @return softzoneIdを返します。
         */
        public String getSoftzoneId()
        {
            return _softzoneId;
        }

        /**
         * softzoneIdを設定します。
         * @param softzoneId softzoneId
         */
        public void setSoftzoneId(String softzoneId)
        {
            _softzoneId = softzoneId;
        }

        /**
         * softzoneNameを返します。
         * @return softzoneNameを返します。
         */
        public String getSoftzoneName()
        {
            return _softzoneName;
        }

        /**
         * softzoneNameを設定します。
         * @param softzoneName softzoneName
         */
        public void setSoftzoneName(String softzoneName)
        {
            _softzoneName = softzoneName;
        }
    }
}
//end of class
