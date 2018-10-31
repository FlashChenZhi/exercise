// $Id: RftController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.authentication.HandyAuthentication;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.rft.IdSchException;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * RFT管理情報コントローラクラスです。
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  073019
 * @author  Last commit: $Author: kishimoto $
 */
public class RftController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public RftController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ユーザ情報取得処理を行います。<BR>
     * パラメータの号機No.に該当するRFT管理情報を検索しIPアドレスを取得する。<BR>
     * 又、パラメータのユーザIDに該当するログインユーザ設定情報を検索しユーザ名を取得する。<BR>
     * 取得した内容を<code>WmsUserInfo</code>にセットし返す。<BR>
     * 
     * @param rftNo 号機No.
     * @param userId ユーザID
     * @return WMSユーザ情報<BR>
     *          DMRFTに該当データが無い場合はnullを返す。<BR>
     *          COM_LOGINUSERに該当データが無い場合はユーザ名に空白をセットして返す。
     * @throws ReadWriteException データベース処理でエラーが発生した場合にスローされます。
     * @throws NoPrimaryException パラメータの号機No.のRFT管理情報が複数件あった場合にスローされます。
     */
    public WmsUserInfo getUserInfo(String rftNo, String userId)
            throws ReadWriteException,
                NoPrimaryException
    {
        WmsUserInfo userInfo = new WmsUserInfo(new DfkUserInfo());

        // set user info (or default value)
        userInfo.setTerminalNo(rftNo); // 端末No.
        userInfo.setUserId(userId); // ユーザID
        userInfo.setTerminalName(""); // 端末名
        userInfo.setUserName(""); // USER NAME

        RftSearchKey key = new RftSearchKey();
        // key.setRftNoCollect();
        key.setIpAddressCollect();

        // set keys
        key.setRftNo(rftNo);

        Rft rft = (Rft)new RftHandler(getConnection()).findPrimary(key);

        if (null == rft)
        {
            return null;
        }
        userInfo.setTerminalAddress(rft.getIpAddress()); // 端末アドレス

        // ハードウェア区分･･･固定で2:RFTをセット。DMRFTのTerminalType(00:HT)とは違うので注意。
        userInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_RFT);

        // ログインユーザ設定情報の検索を行う

        Com_loginuserSearchKey uidKey = new Com_loginuserSearchKey();
        uidKey.setUsernameCollect();

        uidKey.setUserid(userId);
        Com_loginuser user = (Com_loginuser)(new Com_loginuserHandler(getConnection())).findPrimary(uidKey);
        if (null != user)
        {
            String userName = user.getUsername();
            if (!StringUtil.isBlank(userName))
            {
                userInfo.setUserName(userName);
            }
        }
        return userInfo;
    }

    /**
     * ユーザ認証処理を行います。<BR>
     * emanagerを使って、ユーザ認証を行います。
     * 
     * @param userId    ユーザID
     * @param password  パスワード
     * @param ipAddress IPアドレス
     * @param rftNo     号機No.
     * @return ログインUser情報
     * @throws IdSchException               仮パスワードエラーの場合(エラーコード"25"(PASSWORD_TENTATIVE))
     * @throws SQLException                 データベース処理で例外が発生
     * @throws EmAuthenticationException    認証エラーが発生した場合
     */
    public DfkUserInfo rftAuthenticate(String userId, String password, String ipAddress, String rftNo)
            throws IdSchException,
                SQLException,
                EmAuthenticationException
    {
        HandyAuthentication hAuth = new HandyAuthentication();
        DfkUserInfo dfkUserInfo = hAuth.handyAuthenticate(userId, password, ipAddress, rftNo);

        // ユーザロールのチェック。指定されたロール以外は、権限エラーとする。
        ArrayList<String> possibleRoles = WmsParam.RFT_WORKABLE_ROLES;
        if (!possibleRoles.contains(dfkUserInfo.getRoleID()))
        {
            throw new EmAuthenticationException("", EmAuthenticationException.AUTH_ERR_USERPERMISSION);
        }

        // 仮パスワードチェック
        if (EmConstants.AUTHENTICATION_DUMMYPASSWORD == hAuth.getResultStatus())
        {
            throw new IdSchException(IdSchException.Authentication.PASSWORD_TENTATIVE);
        }
        return dfkUserInfo;
    }

    /**
     * RFT作業区分を更新します。
     * 
     * @param userId 検索ユーザID
     * @param rftNo 検索号機No.
     * @param jobType 更新作業区分
     * @return boolean 
     * @throws Exception
     */
    public boolean updateRftJobType(String userId, String rftNo, String jobType)
            throws Exception
    {

        RftHandler rftHand = new RftHandler(getConnection());
        RftAlterKey key = new RftAlterKey();
        key.setUserId(userId);
        key.setRftNo(rftNo);
        key.updateJobType(jobType);

        rftHand.modify(key);

        return true;

    }

    /**
     * RFT作業中データ保存処理を行います。<br>
     * RFTで作業中にしたデータをRFT管理情報に保存します。
     * 
     * @param rftNo                 号機No.
     * @param userId                ユーザID
     * @param settingUnitKey        設定単位キー
     * @throws NotFoundException    変更すべき情報が見つからない場合にスローされます。
     * @throws ReadWriteException   データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException  既に、同じ情報が登録済みの場合にスローされます。
     */
    public void updateSettingUnitKey(String rftNo, String userId, String settingUnitKey)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException

    {
        // RFT管理情報の更新
        RftHandler rftHandler = new RftHandler(getConnection());
        RftAlterKey akey = new RftAlterKey();
        //更新条件
        // (号機No)
        akey.setRftNo(rftNo);
        // (ユーザID)
        akey.setUserId(userId);
        //更新項目
        akey.updateSettingUnitKey(settingUnitKey);
        akey.updateLastUpdatePname(getCallerName());

        rftHandler.modify(akey);
    }

    /**
     * 設定単位キー取得します。<br>
     * RFT管理情報から、RFTNo.をキーに、設定単位キーを取得します。
     * 
     * @param rftNo 号機No.
     * @return 設定単位キー(値がセットされていなかった場合は""が返る)
     * @throws NoPrimaryException   複数件該当するはずの無い問合せで、複数件該当した場合
     * @throws ReadWriteException   データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException    対象レコードが見つからない場合にスローされます。
     */
    public String getSettingUnitKey(String rftNo)
            throws NoPrimaryException,
                ReadWriteException,
                ScheduleException
    {
        // 設定単位キー取得
        RftSearchKey key = new RftSearchKey();
        key.setRftNo(rftNo);

        Rft rft = (Rft)new RftHandler(getConnection()).findPrimary(key);

        if (rft == null)
        {
            throw new ScheduleException();
        }

        return rft.getSettingUnitKey();
    }

    /**
     * RFT作業中データクリア処理を行います。<br>
     * RFT管理情報の設定単位キーを空白に更新します。
     * 
     * @param rftNo                 号機No.
     * @throws NotFoundException    変更すべき情報が見つからない場合にスローされます。
     * @throws ReadWriteException   データベースアクセスエラーが発生したときスローされます。
     */
    public void eraseWorkingData(String rftNo)
            throws NotFoundException,
                ReadWriteException
    {
        // RFT管理情報の更新
        RftHandler rftHandler = new RftHandler(getConnection());
        RftAlterKey akey = new RftAlterKey();

        String pname = getCallerName();
        akey.setRftNo(rftNo);
        akey.updateSettingUnitKey(new String());
        akey.updateLastUpdatePname(pname);

        rftHandler.modify(akey);
    }

    /**
     * RFTの作業中データが存在するか否かを取得します。
     * 
     * @param rftNo 号機No.
     * @return 存在する場合:True、存在しない場合:Falseを返します。
     * @throws NoPrimaryException   複数件該当するはずの無い問合せで、複数件該当した場合
     * @throws ReadWriteException   データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException    対象レコードが見つからない場合にスローされます。
     */
    public boolean isWorkingData(String rftNo)
            throws NoPrimaryException,
                ReadWriteException,
                ScheduleException
    {
        // 検索
        RftSearchKey key = new RftSearchKey();
        key.setRftNo(rftNo);
        Rft rft = (Rft)new RftHandler(getConnection()).findPrimary(key);

        if (rft == null)
        {
            throw new ScheduleException();
        }

        // 設定単位キーが空の場合
        if (StringUtil.isBlank(rft.getSettingUnitKey()))
        {
            return false;
        }
        
        // オーダー出庫
        if (Rft.JOB_TYPE_RETRIEVAL.equals(rft.getJobType()) && 
                Rft.JOB_DETAIL_RETRIEVALORDER.equals(rft.getJobDetails()))
        {
            return true;
        }
        // 仕分け
        else if (Rft.JOB_TYPE_SORTING.equals(rft.getJobType()))
        {
            return true;
        }
        // 出荷
        else if (Rft.JOB_TYPE_SHIPPING.equals(rft.getJobType()) &&
                Rft.JOB_DETAIL_CUSTOMERSHIPPING.equals(rft.getJobDetails()))
        {
            return true;
        }
        return false;
    }
    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


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
    public static String getVersion()
    {
        return "$Id: RftController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}
