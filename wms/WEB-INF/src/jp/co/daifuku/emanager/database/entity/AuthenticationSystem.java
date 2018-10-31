// $Id: AuthenticationSystem.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <jp>認証システム情報のエンティティです。<br>
 * </jp> <en> This entity provides AuthenticationSystem information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class AuthenticationSystem
        extends BaseEntity
        implements Serializable
{

    /**
     * <jp>ログイン認証ID &nbsp;&nbsp;</jp> <en>Authentication ID &nbsp;&nbsp;</en>
     */
    private String authenticationId = null;

    /**
     * <jp>ユーザメンテナンスログを出力するかのフラグ &nbsp;&nbsp;</jp> <en>This flag indicates
     * enables logging the maintenance log in database &nbsp;&nbsp;</en>
     */
    private boolean userMaintLogFlag;

    /**
     * <jp>認証ログを出力するかのフラグ &nbsp;&nbsp;</jp> <en>This flag indicates enables
     * logging the authentication log in database &nbsp;&nbsp;</en>
     */
    private boolean authLogFlag;

    /**
     * <jp>自動ログインを有効にするかどうかのフラグ &nbsp;&nbsp;</jp> <en>Flag to indicate if auto
     * login is allowed &nbsp;&nbsp;</en>
     */
    private boolean autoLoginFlag;

    /**
     * <jp>管理者発行のパスワードを仮パスワードとするかのフラグ &nbsp;&nbsp;</jp> <en>This flag indicates
     * enables dummy password function. &nbsp;&nbsp;</en>
     */
    private boolean dummyPassFlag;

    /**
     * <jp>指定回数以上ログイン認証エラー時に画面をロックする時間 [秒] &nbsp;&nbsp;</jp> <en>Screen lock
     * time[s] if the user entered is wrong password. &nbsp;&nbsp;</en>
     */
    private int faieldLoginScreenLockTime;

    /**
     * <jp>指定回数以上ログイン認証エラーの場合ユーザをロックするかのフラグ &nbsp;&nbsp;</jp> <en>This flag
     * enables the user to be locked if user failed to login.&nbsp;&nbsp;</en>
     */
    private boolean faieldLoginUserLockFlag;

    /**
     * <jp>指定回数以上ログイン認証エラーの場合画面をロックするまでの回数 &nbsp;&nbsp;</jp> <en>The number of
     * attempts user allowed to fail before locking the screen &nbsp;&nbsp;</en>
     */
    private int failedLoginscreenLockCount;

    /**
     * <jp>認証ミス猶予回数 &nbsp;&nbsp;</jp> <en>Maximum number of times user allowed
     * to faile the login &nbsp;&nbsp;</en>
     */
    private int failLoginAttem;

    /**
     * <jp>IPの範囲制限を行なうかのフラグ &nbsp;&nbsp;</jp> <en>This flag enables IP range
     * check. &nbsp;&nbsp;</en>
     */
    private boolean ipRangeCheckFlag;

    /**
     * <jp>IPの有効範囲（最大） &nbsp;&nbsp;</jp> <en>Max IP range &nbsp;&nbsp;</en>
     */
    private String iprangeMax;

    /**
     * <jp>IPの有効範囲（最小） &nbsp;&nbsp;</jp> <en>Min IP range &nbsp;&nbsp;</en>
     */
    private String iprangeMin;

    /**
     * <jp>同時ログインユーザ最大数 &nbsp;&nbsp;</jp> <en>Maximum number of user in system.
     * &nbsp;&nbsp;</en>
     */
    private int loginMax;

    /**
     * <jp>メニュー表示タイプ &nbsp;&nbsp;</jp> <en>Main menu display type &nbsp;&nbsp;</en>
     */
    private int mainMenuShowType;

    /**
     * <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp> <en>Password change interval days.
     * &nbsp;&nbsp;</en>
     */
    private int passChangeInterval;

    /**
     * <jp>パスワード有効期限切れ間近を警告する残り日数 &nbsp;&nbsp;</jp> <en>Number of days before
     * the user has to be shown the password expirey alert &nbsp;&nbsp;</en>
     */
    private int passExpireAlertDays;

    /**
     * <jp>パスワードの有効期限をチェックするかのフラグ &nbsp;&nbsp;</jp> <en>Flag to indicate if the
     * password is allowed to expire &nbsp;&nbsp;</en>
     */
    private boolean passExpireFlag;

    /**
     * <jp>パスワードの変更時、同一パスワードかチェックするパスワードの履歴回数 &nbsp;&nbsp;</jp> <en> Maximum
     * number of times a user allowed to change same password from history
     * &nbsp;&nbsp;</en>
     */
    private int passLogCheckTime;

    /**
     * <jp>パスワードの最低文字数 &nbsp;&nbsp;</jp> <en>Minimum password length
     * &nbsp;&nbsp;</en>
     */
    private int passMinLength;

    /**
     * <jp>パスワードの安全性チェックを行なうかのフラグ &nbsp;&nbsp;</jp> <en>To check the password
     * set rules &nbsp;&nbsp;</en>
     */
    private boolean passSafetyCheckFlag;

    /**
     * <jp>同一ユーザが複数ログインできるかの設定を行なうためのフラグ &nbsp;&nbsp;</jp> <en>Flag to check if
     * the same user can be login plural &nbsp;&nbsp;</en>
     */
    private boolean sameLoginUserFlag;

    /**
     * <jp>画面ごとにログインチェックを行なうかのフラグ &nbsp;&nbsp;</jp> <en>Flag to enable login
     * check before showing the function screen &nbsp;&nbsp;</en>
     */
    private boolean screenLoginCheckFlag;

    /**
     * <jp>端末切替設定を行なうかのフラグ &nbsp;&nbsp;</jp> <en>Flag to check if the terminal
     * is allowed to change &nbsp;&nbsp;</en>
     */
    private boolean terminalChangeFlag;

    /**
     * <jp>端末登録されていない端末からのアクセスを防ぐかのフラグ &nbsp;&nbsp;</jp> <en>Flag to limit the
     * terminal is not registered &nbsp;&nbsp;</en>
     */
    private boolean terminalCheckFlag;

    /**
     * <jp>端末ごとにログインできるユーザ制限を行なうかのフラグ &nbsp;&nbsp;</jp> <en>Flag to limit the
     * user on each terminal. &nbsp;&nbsp;</en>
     */
    private boolean terminalUserCheckFlag;

    /**
     * <jp>adminロールユーザでも端末メニュー表示の制限を行なうかどうかのフラグ &nbsp;&nbsp;</jp> 
     * <en>terminalAdminRoleCheckFlag &nbsp;&nbsp;</en>
     */
    private boolean terminalAdminRoleCheckFlag;


    /**
     * <jp> &nbsp;&nbsp;</jp> 
     * <en>Specifies the HttpSesson timeout time, in seconds, &nbsp;&nbsp;</en>
     */
    private int sessionTimeoutTime;

    /**
     * 同一ユーザ作成禁止期間
     */
    private int sameUserCreateBlockPeriod;

    /**
     * アクセスログ出力設定フラグ
     */
    private boolean accessLogFlag;

    /**
     * 操作ログ出力設定フラグ
     */
    private boolean operationLogFlag;

    /**
     * マスタ改廃ログ出力設定フラグ
     */
    private boolean masterLogFlag;

    /**
     * 在庫情報ログ出力設定フラグ
     */
    private boolean stockLogFlag;

    /**
     * ログデータ保持日数
     */
    private int dbLogHoldDays;

    /**
     * ログファイル作成単位
     */
    private int csvLogHoldDays;

    /**
     * ログファイルディスク保持期間
     */
    private int hdLogHoldYears;

    /**
     * Part11ログ出力設定フラグ
     */
    private boolean part11Flag;

    /**
     * <jp> &nbsp;&nbsp;</jp> 
     * <en>Specifies the HttpSesson timeout time, in seconds, &nbsp;&nbsp;</en>
     */
    private Date workDate = null;

    /**
     * <jp>ログイン認証IDを取得します。<br>
     * </jp> <en>Returns authentication ID.<br>
     * </en>
     * 
     * @return <jp>ログイン認証ID &nbsp;&nbsp;</jp><en>Authentication ID
     *         &nbsp;&nbsp;</en>
     */
    public String getAuthenticationId()
    {
        return authenticationId;
    }


    /**
     * <jp>自動ログインを有効にするかどうかのフラグを取得します。<br>
     * </jp> <en>Returns autoLoginFlag. <br>
     * </en>
     * 
     * @return autoLoginFlag
     */
    public boolean getAutoLoginFlag()
    {
        return autoLoginFlag;
    }

    /**
     * <jp>管理者発行のパスワードを仮パスワードとするかのフラグを取得します。<br>
     * </jp> <en>Returns DummyPassFlag.<br>
     * </en>
     * 
     * @return <jp>管理者発行のパスワードを仮パスワードとするかのフラグ &nbsp;&nbsp;</jp><en>Dummy
     *         password flag &nbsp;&nbsp;</en>
     */
    public boolean getDummyPassFlag()
    {
        return dummyPassFlag;
    }

    /**
     * <jp>規定回数以上ログイン認証エラーの場合、ユーザをロックするかのフラグを取得します。<br>
     * </jp> <en>Returns faieldLoginScreenLockTime.<br>
     * </en>
     * 
     * @return <jp>規定回数以上ログイン認証エラーの場合、ユーザをロックするかのフラグ &nbsp;&nbsp;</jp><en>faieldLoginScreenLockTime
     *         &nbsp;&nbsp;</en>
     */
    public int getFaieldLoginScreenLockTime()
    {
        return faieldLoginScreenLockTime;
    }

    /**
     * <jp>指定回数以上ログイン認証エラーの場合ユーザをロックするかのフラグを取得します。<br>
     * </jp> <en>Returns faieldLoginUserLockFlag.<br>
     * </en>
     * 
     * @return <jp>指定回数以上ログイン認証エラーの場合ユーザをロックするかのフラグ &nbsp;&nbsp;</jp><en>Authentication
     *         ID &nbsp;&nbsp;</en>
     */
    public boolean getFaieldLoginUserLockFlag()
    {
        return faieldLoginUserLockFlag;
    }

    /**
     * <jp>指定回数以上ログイン認証エラーの場合画面をロックするまでの回数を取得します。<br>
     * </jp> <en>Returns failedLoginscreenLockCount.<br>
     * </en>
     * 
     * @return failedLoginscreenLockCount
     */
    public int getFailedLoginscreenLockCount()
    {
        return failedLoginscreenLockCount;
    }

    /**
     * <jp>認証ミス猶予回数を取得します。<br>
     * </jp> <en>Returns failed login attemptes. <br>
     * </en>
     * 
     * @return failLoginAttem
     */
    public int getFailLoginAttem()
    {
        return failLoginAttem;
    }

    /**
     * <jp>IPの範囲制限を行なうかのフラグを取得します。<br>
     * </jp> <en>Returns ipRangeCheckFlag.<br>
     * </en>
     * 
     * @return ipRangeCheckFlag
     */
    public boolean getIpRangeCheckFlag()
    {
        return ipRangeCheckFlag;
    }

    /**
     * <jp>IPの有効範囲（最大）を取得します。<br>
     * </jp> <en>Returns iprangeMax.<br>
     * </en>
     * 
     * @return iprangeMax
     */
    public String getIprangeMax()
    {
        return iprangeMax;
    }

    /**
     * <jp>IPの有効範囲（最小）を取得します。<br>
     * </jp> <en>Returns iprangeMin.<br>
     * </en>
     * 
     * @return iprangeMin
     */
    public String getIprangeMin()
    {
        return iprangeMin;
    }

    /**
     * <jp>同時ログインユーザ最大数を取得します。<br>
     * </jp> <en>Returns loginMax.<br>
     * </en>
     * 
     * @return loginMax
     */
    public int getLoginMax()
    {
        return loginMax;
    }

    /**
     * <jp>メニュー表示タイプを取得します。<br>
     * </jp> <en>Returns mainMenuShowType.<br>
     * </en>
     * 
     * @return mainMenuShowType
     */
    public int getMainMenuShowType()
    {
        return mainMenuShowType;
    }

    /**
     * <jp>パスワード有効期間日数を取得します。<br>
     * </jp> <en>Returns password change interval peroid. <br>
     * </en>
     * 
     * @return passChangeInterval
     */
    public int getPassChangeInterval()
    {
        return passChangeInterval;
    }

    // /**
    // * <jp><br></jp>
    // * <en>Returns restrictionPlaceFlag.<br></en>
    // *
    // * @return restrictionPlaceFlag
    // */
    // public boolean getRestrictionPlaceFlag() {
    // return restrictionPlaceFlag;
    // }

    /**
     * <jp>パスワード有効期限切れ間近を警告する残り日数を取得します。<br>
     * </jp> <en>Returns passExpireAlertDays. <br>
     * </en>
     * 
     * @return passExpireAlertDays
     */
    public int getPassExpireAlertDays()
    {
        return passExpireAlertDays;
    }

    /**
     * <jp>パスワードの有効期限をチェックするかのフラグを取得します。<br>
     * </jp> <en>Returns passExpireFlag.<br>
     * </en>
     * 
     * @return passExpireFlag
     */
    public boolean getPassExpireFlag()
    {
        return passExpireFlag;
    }

    /**
     * <jp>パスワードの変更時、同一パスワードかチェックするパスワードの履歴回数を取得します。<br>
     * </jp> <en>Returns passLogCheckTime.<br>
     * </en>
     * 
     * @return passLogCheckTime
     */
    public int getPassLogCheckTime()
    {
        return passLogCheckTime;
    }

    /**
     * <jp>パスワードの最低文字数を取得します。<br>
     * </jp> <en>Returns passMinLength. <br>
     * </en>
     * 
     * @return passMinLength
     */
    public int getPassMinLength()
    {
        return passMinLength;
    }

    /**
     * <jp>パスワードの安全性チェックを行なうかのフラグを取得します。<br>
     * </jp> <en>Returns passSafetyCheckFlag.<br>
     * </en>
     * 
     * @return passSafetyCheckFlag
     */
    public boolean getPassSafetyCheckFlag()
    {
        return passSafetyCheckFlag;
    }

    /**
     * <jp>同一ユーザが複数ログインできるかの設定を行なうためのフラグを取得します。<br>
     * </jp> <en>Returns sameLoginUserFlag. <br>
     * </en>
     * 
     * @return sameLoginUserFlag
     */
    public boolean getSameLoginUserFlag()
    {
        return sameLoginUserFlag;
    }

    /**
     * <jp>画面ごとにログインチェックを行なうかのフラグを取得します。<br>
     * </jp> <en>Returns screenLoginCheckFlag.<br>
     * </en>
     * 
     * @return screenLoginCheckFlag
     */
    public boolean getScreenLoginCheckFlag()
    {
        return screenLoginCheckFlag;
    }

    /**
     * <jp>端末切替設定を行なうかのフラグを取得します。<br>
     * </jp> <en>Returns terminalChangeFlag. <br>
     * </en>
     * 
     * @return terminalChangeFlag
     */
    public boolean getTerminalChangeFlag()
    {
        return terminalChangeFlag;
    }

    /**
     * <jp>端末登録されていない端末からのアクセスを防ぐかのフラグを取得します。<br>
     * </jp> <en>Returns terminalCheckFlag.<br>
     * </en>
     * 
     * @return terminalCheckFlag
     */
    public boolean getTerminalCheckFlag()
    {
        return terminalCheckFlag;
    }

    /**
     * <jp>端末ごとにログインできるユーザ制限を行なうかのフラグを取得します。<br>
     * </jp> <en>Returns terminalUserCheckFlag. <br>
     * </en>
     * 
     * @return terminalUserCheckFlag
     */
    public boolean getTerminalUserCheckFlag()
    {
        return terminalUserCheckFlag;
    }


    /**
     * <jp>ログイン認証IDを設定します。<br>
     * </jp> <en>Sets Authentication ID. <br>
     * </en>
     * 
     * @param authenticationId
     *            <jp>ログイン認証ID &nbsp;&nbsp;</jp><en>Authentication ID.
     *            &nbsp;&nbsp;</en>
     */
    public void setAuthenticationId(String authenticationId)
    {
        this.authenticationId = authenticationId;
    }


    /**
     * <jp>自動ログインを有効にするかどうかのフラグを設定します。<br>
     * </jp> <en>Sets autoLoginFlag. <br>
     * </en>
     * 
     * @param autoLoginFlag -
     *            <en>autoLoginFlag to be set.</en>
     */
    public void setAutoLoginFlag(boolean autoLoginFlag)
    {
        this.autoLoginFlag = autoLoginFlag;
    }

    /**
     * <jp>管理者発行のパスワードを仮パスワードとするかのフラグを設定します。<br>
     * </jp> <en>Sets DummyPassFlag. <br>
     * </en>
     * 
     * @param dummyPassFlag -
     *            <en>DummyPassFlag to be set.</en>
     */
    public void setDummyPassFlag(boolean dummyPassFlag)
    {
        this.dummyPassFlag = dummyPassFlag;
    }

    /**
     * <jp>規定回数以上ログイン認証エラーの場合、ユーザをロックするかのフラグを設定します。<br>
     * </jp> <en>Sets FaieldLoginScreenLockFlag. <br>
     * </en>
     * 
     * @param faieldLoginScreenLockTime -
     *            <en>FaieldLoginScreenLockFlag to be set.</en>
     */
    public void setFaieldLoginScreenLockTime(int faieldLoginScreenLockTime)
    {
        this.faieldLoginScreenLockTime = faieldLoginScreenLockTime;
    }

    /**
     * <jp>指定回数以上ログイン認証エラーの場合ユーザをロックするかのフラグを設定します。<br>
     * </jp> <en>Sets faieldLoginUserLockFlag. <br>
     * </en>
     * 
     * @param faieldLoginUserLockFlag -
     *            <en>faieldLoginUserLockFlag to be set.</en>
     */
    public void setFaieldLoginUserLockFlag(boolean faieldLoginUserLockFlag)
    {
        this.faieldLoginUserLockFlag = faieldLoginUserLockFlag;
    }

    /**
     * <jp>指定回数以上ログイン認証エラーの場合画面をロックするまでの回数を設定します。<br>
     * </jp> <en>Sets failedLoginscreenLockCount. <br>
     * </en>
     * 
     * @param failedLoginscreenLockCount -
     *            <en>failedLoginscreenLockCount to be set.</en>
     */
    public void setFailedLoginscreenLockCount(int failedLoginscreenLockCount)
    {
        this.failedLoginscreenLockCount = failedLoginscreenLockCount;
    }

    /**
     * <jp>認証ミス猶予回数を設定します。<br>
     * </jp> <en>Sets failed login attemptes. <br>
     * </en>
     * 
     * @param failLoginAttem -
     *            <en>failLoginAttem to be set.</en>
     */
    public void setFailLoginAttem(int failLoginAttem)
    {
        this.failLoginAttem = failLoginAttem;
    }

    /**
     * <jp>IPの範囲制限を行なうかのフラグを設定します。<br>
     * </jp> <en>Sets ipRangeCheckFlag. <br>
     * </en>
     * 
     * @param ipRangeCheckFlag -
     *            <en>ipRangeCheckFlag to be set.</en>
     */
    public void setIpRangeCheckFlag(boolean ipRangeCheckFlag)
    {
        this.ipRangeCheckFlag = ipRangeCheckFlag;
    }

    /**
     * <jp>IPの有効範囲（最大）を設定します。<br>
     * </jp> <en>Sets iprangeMax. <br>
     * </en>
     * 
     * @param iprangeMax -
     *            <en>iprangeMax to be set.</en>
     */
    public void setIprangeMax(String iprangeMax)
    {
        this.iprangeMax = iprangeMax;
    }

    /**
     * <jp>IPの有効範囲（最小）を設定します。<br>
     * </jp> <en>Sets iprangeMin. <br>
     * </en>
     * 
     * @param iprangeMin -
     *            <en>iprangeMin to be set.</en>
     */
    public void setIprangeMin(String iprangeMin)
    {
        this.iprangeMin = iprangeMin;
    }

    /**
     * <jp>同時ログインユーザ最大数を設定します。<br>
     * </jp> <en>Sets loginMax. <br>
     * </en>
     * 
     * @param loginMax -
     *            <en>loginMax to be set.</en>
     */
    public void setLoginMax(int loginMax)
    {
        this.loginMax = loginMax;
    }

    /**
     * <jp>メニュー表示タイプを設定します。<br>
     * </jp> <en>Sets mainMenuShowType. <br>
     * </en>
     * 
     * @param mainMenuShowType -
     *            <en>mainMenuShowType to be set.</en>
     */
    public void setMainMenuShowType(int mainMenuShowType)
    {
        this.mainMenuShowType = mainMenuShowType;
    }

    /**
     * <jp>パスワード有効期間日数を設定します。<br>
     * </jp> <en>Sets password change interval peroid. <br>
     * </en>
     * 
     * @param pwdChangeInterval -
     *            <en>password change interval peroid to be set.</en>
     */
    public void setPassChangeInterval(int pwdChangeInterval)
    {
        this.passChangeInterval = pwdChangeInterval;
    }

    /**
     * <jp>パスワード有効期限切れ間近を警告する残り日数を設定します。<br>
     * </jp> <en>Sets passExpireAlertDays. <br>
     * </en>
     * 
     * @param passExpireAlertDays -
     *            <en>passExpireAlertDays.</en>
     */
    public void setPassExpireAlertDays(int passExpireAlertDays)
    {
        this.passExpireAlertDays = passExpireAlertDays;
    }

    /**
     * <jp>パスワードの有効期限をチェックするかのフラグを設定します。<br>
     * </jp> <en>Sets passExpireFlag. <br>
     * </en>
     * 
     * @param passExpireFlag -
     *            <en>passExpireFlag to be set.</en>
     */
    public void setPassExpireFlag(boolean passExpireFlag)
    {
        this.passExpireFlag = passExpireFlag;
    }

    /**
     * <jp>パスワードの変更時、同一パスワードかチェックするパスワードの履歴回数を設定します。<br>
     * </jp> <en>Sets passLogCheckTime. <br>
     * </en>
     * 
     * @param passLogCheckTime -
     *            <en>passLogCheckTime to be set.</en>
     */
    public void setPassLogCheckTime(int passLogCheckTime)
    {
        this.passLogCheckTime = passLogCheckTime;
    }

    /**
     * <jp>パスワードの最低文字数を設定します。<br>
     * </jp> <en>Sets passMinLength. <br>
     * </en>
     * 
     * @param passMaxLength -
     *            <en>passMinLength to be set.</en>
     */
    public void setPassMinLength(int passMaxLength)
    {
        this.passMinLength = passMaxLength;
    }

    /**
     * <jp>パスワードの安全性チェックを行なうかのフラグを設定します。<br>
     * </jp> <en>Sets passSafetyCheckFlag. <br>
     * </en>
     * 
     * @param passSafetyCheckFlag -
     *            <en>passSafetyCheckFlag to be set.</en>
     */
    public void setPassSafetyCheckFlag(boolean passSafetyCheckFlag)
    {
        this.passSafetyCheckFlag = passSafetyCheckFlag;
    }

    /**
     * <jp>同一ユーザが複数ログインできるかの設定を行なうためのフラグを設定します。<br>
     * </jp> <en>Sets sameLoginUserFlag. <br>
     * </en>
     * 
     * @param sameLoginUserFlag -
     *            <en>sameLoginUserFlag to be set.</en>
     */
    public void setSameLoginUserFlag(boolean sameLoginUserFlag)
    {
        this.sameLoginUserFlag = sameLoginUserFlag;
    }

    /**
     * <jp>画面ごとにログインチェックを行なうかのフラグを設定します。<br>
     * </jp> <en>Sets screenLoginCheckFlag. <br>
     * </en>
     * 
     * @param screenLoginCheckFlag -
     *            <en>screenLoginCheckFlag to be set.</en>
     */
    public void setScreenLoginCheckFlag(boolean screenLoginCheckFlag)
    {
        this.screenLoginCheckFlag = screenLoginCheckFlag;
    }

    /**
     * <jp>端末切替設定を行なうかのフラグを設定します。<br>
     * </jp> <en>Sets terminalChangeFlag. <br>
     * </en>
     * 
     * @param terminalChangeFlag -
     *            <en>terminalChangeFlag to be set.</en>
     */
    public void setTerminalChangeFlag(boolean terminalChangeFlag)
    {
        this.terminalChangeFlag = terminalChangeFlag;
    }

    /**
     * <jp>端末登録されていない端末からのアクセスを防ぐかのフラグを設定します。<br>
     * </jp> <en>Sets terminalCheckFlag . <br>
     * </en>
     * 
     * @param terminalCheckFlag -
     *            <en>terminalCheckFlag to be set.</en>
     */
    public void setTerminalCheckFlag(boolean terminalCheckFlag)
    {
        this.terminalCheckFlag = terminalCheckFlag;
    }

    /**
     * <jp>端末ごとにログインできるユーザ制限を行なうかのフラグを設定します。<br>
     * </jp> <en>Sets terminalUserCheckFlag. <br>
     * </en>
     * 
     * @param terminalUserCheckFlag -
     *            <en>terminalUserCheckFlag to be set.</en>
     */
    public void setTerminalUserCheckFlag(boolean terminalUserCheckFlag)
    {
        this.terminalUserCheckFlag = terminalUserCheckFlag;
    }


    /**
     * <jp></jp>
     *  <en>Returns HttpSession timeout in seconds<br></en>
     * 
     * @return <jp></jp><en>HttpSession timeout in seconds &nbsp;&nbsp;</en>
     */
    public int getSessionTimeoutTime()
    {
        return sessionTimeoutTime;
    }

    /**
     * <jp><br></jp>
     *  <en>Sets HttpSession timeout in seconds <br></en>
     * 
     * @param sessionTimeoutTime -
     *            <en>HttpSession timeout in seconds./en>
     */
    public void setSessionTimeoutTime(int sessionTimeoutTime)
    {
        this.sessionTimeoutTime = sessionTimeoutTime;
    }

    /**
     * <jp><br>
     * </jp> <en>Returns workDate.<br>
     * </en>
     * 
     * @return <jp> &nbsp;&nbsp;</jp><en>workDate  &nbsp;&nbsp;</en>
     */
    public Date getWorkDate()
    {
        return this.workDate;
    }

    /**
     * <jp><br>
     * </jp> <en>Sets workDate.<br>
     * </en>
     * 
     * @param workDate
     *            <jp>更新ユーザ &nbsp;&nbsp;</jp><en>workDate &nbsp;&nbsp;</en>
     */
    public void setWorkDate(Date workDate)
    {
        this.workDate = workDate;
    }

    /**
     * <jp></jp>
     *  <en>Returns terminalAdminRoleCheckFlag<br></en>
     * 
     * @return <jp></jp><en>terminalAdminRoleCheckFlag &nbsp;&nbsp;</en>
     */
    public boolean getTerminalAdminRoleCheckFlag()
    {
        return terminalAdminRoleCheckFlag;
    }

    /**
     * <jp><br></jp>
     *  <en>Sets terminalAdminRoleCheckFlag<br></en>
     * 
     * @param terminalAdminRoleCheckFlag -
     *            <en>terminalAdminRoleCheckFlag./en>
     */
    public void setTerminalAdminRoleCheckFlag(boolean terminalAdminRoleCheckFlag)
    {
        this.terminalAdminRoleCheckFlag = terminalAdminRoleCheckFlag;
    }


    /**
     * <jp></jp>
     *  <en>Returns accessLogFlag<br></en>
     * 
     * @return <jp></jp><en>accessLogFlag &nbsp;&nbsp;</en>
     */
    public boolean getAccessLogFlag()
    {
        return accessLogFlag;
    }


    /**
     * <jp>アクセスログ出力設定フラグを設定します。<br>
     * </jp> <en>Sets accessLogFlag . <br>
     * </en>
     * 
     * @param accessLogFlag
     *            <jp>アクセスログ出力設定フラグ &nbsp;&nbsp;</jp><en>accessLogFlag.
     *            &nbsp;&nbsp;</en>
     */
    public void setAccessLogFlag(boolean accessLogFlag)
    {
        this.accessLogFlag = accessLogFlag;
    }

    /**
     * <jp></jp>
     *  <en>Returns csvLogHoldDays<br></en>
     * 
     * @return <jp></jp><en>csvLogHoldDays &nbsp;&nbsp;</en>
     */

    public int getCsvLogHoldDays()
    {
        return csvLogHoldDays;
    }

    /**
     * <jp>ログファイル作成単位を設定します。<br>
     * </jp> <en>Sets csvLogHoldDays <br>
     * </en>
     * 
     * @param csvLogHoldDays
     *            <jp>ログファイル作成単位 &nbsp;&nbsp;</jp><en>csvLogHoldDays.
     *            &nbsp;&nbsp;</en>
     */


    public void setCsvLogHoldDays(int csvLogHoldDays)
    {
        this.csvLogHoldDays = csvLogHoldDays;
    }


    /**
     * <jp></jp>
     *  <en>Returns dbLogHoldDays<br></en>
     * 
     * @return <jp></jp><en>terminaldbLogHoldDaysAdminRoleCheckFlag &nbsp;&nbsp;</en>
     */
    public int getDbLogHoldDays()
    {
        return dbLogHoldDays;
    }


    /**
     * <jp>ログデータ保持日数を設定します。<br>
     * </jp> <en>Sets dbLogHoldDays. <br>
     * </en>
     * 
     * @param dbLogHoldDays
     *            <jp>ログデータ保持日数 &nbsp;&nbsp;</jp><en>dbLogHoldDays.
     *            &nbsp;&nbsp;</en>
     */
    public void setDbLogHoldDays(int dbLogHoldDays)
    {
        this.dbLogHoldDays = dbLogHoldDays;
    }

    /**
     * <jp></jp>
     *  <en>Returns hdLogHoldDays<br></en>
     * 
     * @return <jp></jp><en>hdLogHoldDays &nbsp;&nbsp;</en>
     */
    public int getHdLogHoldYears()
    {
        return hdLogHoldYears;
    }

    /**
     * <jp>ログファイルディスク保持期間を設定します。<br>
     * </jp> <en>Sets hdLogHoldDays. <br>
     * </en>
     * 
     * @param hdLogHoldDays
     *            <jp>ログファイルディスク保持期間 &nbsp;&nbsp;</jp><en>hdLogHoldDays .
     *            &nbsp;&nbsp;</en>
     */
    public void setHdLogHoldYears(int hdLogHoldDays)
    {
        this.hdLogHoldYears = hdLogHoldDays;
    }


    /**
     * <jp></jp>
     *  <en>Returns masterLogFlag<br></en>
     * 
     * @return <jp></jp><en>masterLogFlag &nbsp;&nbsp;</en>
     */
    public boolean getMasterLogFlag()
    {
        return masterLogFlag;
    }

    /**
     * <jp>マスタ改廃ログ出力設定フラグを設定します。<br>
     * </jp> <en>Sets masterLogFalg. <br>
     * </en>
     * 
     * @param masterLogFlag
     *            <jp>マスタ改廃ログ出力設定フラグ &nbsp;&nbsp;</jp><en>masterLog.
     *            &nbsp;&nbsp;</en>
     */
    public void setMasterLogFlag(boolean masterLogFlag)
    {
        this.masterLogFlag = masterLogFlag;
    }


    /**
     * <jp></jp>
     *  <en>Returns operationLogFlag<br></en>
     * 
     * @return <jp></jp><en>operationLogFlag &nbsp;&nbsp;</en>
     */
    public boolean getOperationLogFlag()
    {
        return operationLogFlag;
    }

    /**
     * <jp>操作ログ出力設定フラグを設定します。<br>
     * </jp> <en>Sets operationLogFlag. <br>
     * </en>
     * 
     * @param operationLog
     *            <jp>操作ログ出力設定フラグ &nbsp;&nbsp;</jp><en>operationLogFlag.
     *            &nbsp;&nbsp;</en>
     */

    public void setOperationLogFlag(boolean operationLog)
    {
        this.operationLogFlag = operationLog;
    }


    /**
     * <jp></jp>
     *  <en>Returns sameUserCreateBlockPeriod<br></en>
     * 
     * @return <jp></jp><en>sameUserCreateBlockPeriod &nbsp;&nbsp;</en>
     */
    public int getSameUserCreateBlockPeriod()
    {
        return sameUserCreateBlockPeriod;
    }


    /**
     * <jp>同一ユーザ作成禁止期間を設定します。<br>
     * </jp> <en>Sets sameUserCreateBlockPeriod. <br>
     * </en>
     * 
     * @param sameUserCreateBlockPeriod
     *            <jp>同一ユーザ作成禁止期間 &nbsp;&nbsp;</jp><en>sameUserCreateBlockPeriod.
     *            &nbsp;&nbsp;</en>
     */
    public void setSameUserCreateBlockPeriod(int sameUserCreateBlockPeriod)
    {
        this.sameUserCreateBlockPeriod = sameUserCreateBlockPeriod;
    }


    /**
     * <jp></jp>
     *  <en>Returns stockLogFlag<br></en>
     * 
     * @return <jp></jp><en>stockLogFlag &nbsp;&nbsp;</en>
     */
    public boolean getStockLogFlag()
    {
        return stockLogFlag;
    }


    /**
     * <jp>在庫情報出力設定フラグを設定します。<br>
     * </jp> <en>Sets stockLogFlag. <br>
     * </en>
     * 
     * @param stockLogFlag
     *            <jp>在庫情報出力設定フラグ &nbsp;&nbsp;</jp><en>stockLogFlag.
     *            &nbsp;&nbsp;</en>
     */
    public void setStockLogFlag(boolean stockLogFlag)
    {
        this.stockLogFlag = stockLogFlag;
    }

    /**
     * <jp>認証ログを出力するかのフラグを取得します。<br>
     * </jp> <en>Returns AuthLogFlag.<br>
     * </en>
     * 
     * @return <jp>認証ログを出力するかのフラグ &nbsp;&nbsp;</jp><en>Authentication logging
     *         flag &nbsp;&nbsp;</en>
     */
    public boolean getAuthLogFlag()
    {
        return authLogFlag;
    }

    /**
     * <jp>認証ログを出力するかのフラグを設定します。<br>
     * </jp> <en>Sets AuthLogFlag <br>
     * </en>
     * 
     * @param authLogFlag -
     *            <en>AuthLogFlag( to be set.</en>
     */
    public void setAuthLogFlag(boolean authLogFlag)
    {
        this.authLogFlag = authLogFlag;
    }

    /**
     * <jp>ユーザメンテナンスログを出力するかのフラグを取得します。<br>
     * </jp> <en>Returns userMaintLogFlag.<br>
     * </en>
     * 
     * @return userMaintLogFlag
     */
    public boolean getUserMaintLogFlag()
    {
        return userMaintLogFlag;
    }

    /**
     * <jp>ユーザメンテナンスログを出力するかのフラグを設定します。<br>
     * </jp> <en>Sets userMaintLogFlag. <br>
     * </en>
     * 
     * @param userMaintLogFlag -
     *            <en>userMaintLogFlag to be set.</en>
     */
    public void setUserMaintLogFlag(boolean userMaintLogFlag)
    {
        this.userMaintLogFlag = userMaintLogFlag;
    }

    /**
     * <jp>Part11ログを出力するかのフラグを取得します。<br>
     * </jp> <en>Returns part11Flag.<br>
     * </en>
     * 
     * @return part11Flag
     */
    public boolean getPart11Flag()
    {
        return part11Flag;
    }

    /**
     * <jp>Part11ログを出力するかのフラグを設定します。<br>
     * </jp> <en>Sets part11Flag. <br>
     * </en>
     * 
     * @param part11Flag -
     *            <en>part11Flag to be set.</en>
     */
    public void setPart11Flag(boolean part11Flag)
    {
        this.part11Flag = part11Flag;
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the entity values string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- AuthenticationSystem Entity ------------------------\n");
        sb.append(" AuthenticationId           : " + this.authenticationId + "\n");
        sb.append(" LoginMax                   : " + this.loginMax + "\n");
        sb.append(" SameLoginUserFlag          : " + this.sameLoginUserFlag + "\n");
        sb.append(" TerminalUserCheckFlag      : " + this.terminalUserCheckFlag + "\n");
        sb.append(" TerminalCheckFlag          : " + this.terminalChangeFlag + "\n");
        sb.append(" ScreenLoginCheckFlag       : " + this.screenLoginCheckFlag + "\n");
        sb.append(" FailedLoginScreenLockTime  : " + this.faieldLoginScreenLockTime + "\n");
        sb.append(" FailedLoginScreenLockCount : " + this.failedLoginscreenLockCount + "\n");
        sb.append(" FailedLoginUserLockFlag    : " + this.faieldLoginUserLockFlag + "\n");
        sb.append(" TerminalCheckFlag          : " + this.terminalCheckFlag + "\n");
        sb.append(" IpRangeCheckFlag           : " + this.ipRangeCheckFlag + "\n");
        sb.append(" IpRangeMax                 : " + this.iprangeMax + "\n");
        sb.append(" IpRangeMin                 : " + this.iprangeMin + "\n");
        sb.append(" DummyPasswordFlag          : " + this.dummyPassFlag + "\n");
        sb.append(" PasswordSaftyCheckFlag     : " + this.passSafetyCheckFlag + "\n");
        sb.append(" PasswordExpireFlag         : " + this.passExpireFlag + "\n");
        sb.append(" PasswordLogCheckTime       : " + this.passLogCheckTime + "\n");
        sb.append(" PasswordMinLength          : " + this.passMinLength + "\n");
        sb.append(" FailedLoginAttempts        : " + this.failLoginAttem + "\n");
        sb.append(" PwdChangeInterval          : " + this.passChangeInterval + "\n");
        sb.append(" PasswordExpireAlertDays    : " + this.passExpireAlertDays + "\n");
        sb.append(" AutoLoginFlag              : " + this.autoLoginFlag + "\n");
        sb.append(" SameUserBlockPeriod        : " + this.sameUserCreateBlockPeriod + "\n");
        sb.append(" AccessLogFlag              : " + this.accessLogFlag + "\n");
        sb.append(" OperationLogFlag           : " + this.operationLogFlag + "\n");
        sb.append(" MasterLogFlag              : " + this.masterLogFlag + "\n");
        sb.append(" StockLogFlag               : " + this.stockLogFlag + "\n");
        sb.append(" DbLogHoldDays              : " + this.dbLogHoldDays + "\n");
        sb.append(" CsvLogHoldDays             : " + this.csvLogHoldDays + "\n");
        sb.append(" HdLogHoldYears             : " + this.hdLogHoldYears + "\n");
        sb.append(" Part11Flag                 : " + this.part11Flag + "\n");
        sb.append(" MainMenuType               : " + this.mainMenuShowType + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }


}
