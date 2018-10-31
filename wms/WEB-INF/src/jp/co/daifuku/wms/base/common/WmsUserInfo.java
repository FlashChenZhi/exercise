// $Id: WmsUserInfo.java 7692 2010-03-19 08:07:04Z ota $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * WMSの操作を行うユーザ情報を保持するクラスです。
 *
 * @version $Revision: 7692 $, $Date: 2010-03-19 17:07:04 +0900 (金, 19 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */
// UPDATE_SS (2007-07-06)
public class WmsUserInfo
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * ハードウェア区分
     */
    private String _hardwareType;

    /**
     * ログインユーザ情報
     * Part11対応でDS番号、ページキーリソースキーを保持するために使用
     */
    private DfkUserInfo _dfkUserInfo;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ユーザ情報のインスタンスを生成します。
     */
    public WmsUserInfo()
    {
        super();
        _dfkUserInfo = new DfkUserInfo();
    }

    /**
     * コンストラクタ <BR>
     * DfkUserInfoを指定してユーザ情報のインスタンスを生成します。
     * 
     * @param info
     */
    public WmsUserInfo(DfkUserInfo info)
    {
        super();
        _dfkUserInfo = new DfkUserInfo();
        _dfkUserInfo.setDsNumber(info.getDsNumber());
        _dfkUserInfo.setUserId(info.getUserId());
        _dfkUserInfo.setUserName(info.getUserName());
        _dfkUserInfo.setTerminalNumber(info.getTerminalNumber());
        _dfkUserInfo.setTerminalName(info.getTerminalName());
        _dfkUserInfo.setTerminalAddress(info.getTerminalAddress());
        _dfkUserInfo.setPageNameResourceKey(info.getPageNameResourceKey());
    }

    /**
     * 各ユーザ情報を指定してインスタンスを生成します。
     * @param userId ユーザID
     * @param hwType ハードウェア区分
     * <code>SystemDefine.HARDWARE_TYPE_XXXX</code> を指定してください。<br>
     * @see SystemDefine
     * @param terminalNo 端末のIPアドレスまたはホスト。
     */
    private WmsUserInfo(String userId, String hwType, String terminalNo)
    {
        super();
        _dfkUserInfo = new DfkUserInfo();
        setUserId(userId);
        setHardwareType(hwType);
        setTerminalNo(terminalNo);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ユーザ情報を組み立てて返します。
     * @param conn データベースコネクション
     * @param userid ユーザID
     * @param hwType ハードウエアタイプ
     * @param termNo 端末No.
     * 
     * @return ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーまたはキー項目重複
     * @since 2007-05-08
     */
    public static WmsUserInfo buildUserInfo(Connection conn, String userid, String hwType, String termNo)
            throws ReadWriteException
    {
        // prepare handlers
        Com_loginuserHandler userH = new Com_loginuserHandler(conn);
        Com_loginuserSearchKey userKey = new Com_loginuserSearchKey();

        // 端末関連テーブルは RFTとAS/RSで変わるので hwTypeで判定
        Com_terminalHandler termH = new Com_terminalHandler(conn);
        Com_terminalSearchKey termKey = new Com_terminalSearchKey();

        try
        {
            Com_loginuser user = null;
            if (StringUtil.isBlank(userid))
            {
                user = new Com_loginuser();
            }
            else
            {
                // read user info.
                userKey.setUserid(userid);
                user = (Com_loginuser)userH.findPrimary(userKey);
                if (null == user)
                {
                    // ユーザが存在しないときは、ユーザIDをユーザ名として使用
                    user = new Com_loginuser();
                    user.setUsername(userid);
                }
            }

            // read term info
            Com_terminal term = null;
            if (StringUtil.isBlank(termNo))
            {
                term = new Com_terminal();
            }
            else
            {
                termKey.setTerminalnumber(termNo);
                term = (Com_terminal)termH.findPrimary(termKey);
                if (null == term)
                {
                    // 端末が存在しないときは、端末No.をアドレスと名称として使用
                    term = new Com_terminal();
                    term.setTerminaladdress(termNo);
                    term.setTerminalname(termNo);
                }
            }

            // setup user info.
            WmsUserInfo ui = new WmsUserInfo(userid, hwType, termNo);

            ui.setUserName(user.getUsername());
            ui.setTerminalAddress(term.getTerminaladdress());
            ui.setTerminalName(term.getTerminalname());

            return ui;
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * @see java.lang.Object#toString()
     * @return このインスタンスの内容 (表示用)
     */
    @Override
    public String toString()
    {
        StringBuilder buff = new StringBuilder();
        buff.append("UserID:");
        buff.append(getUserId());
        buff.append(",UserName:");
        buff.append(getUserName());
        buff.append(",Hardware:");
        buff.append(getHardwareType());
        buff.append(",TermNo:");
        buff.append(getTerminalNo());
        buff.append(",TermName:");
        buff.append(getTerminalName());
        buff.append(",TermAddr:");
        buff.append(getTerminalAddress());
        return new String(buff);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * ハードウェア区分を返します。
     * @return ハードウェア区分
     */
    public String getHardwareType()
    {
        return _hardwareType;
    }

    /**
     * ハードウェア区分を設定します。
     * @param hardwareType ハードウェア区分<br>
     * <code>SystemDefine.HARDWARE_TYPE_XXXX</code> を指定してください。<br>
     * @see SystemDefine
     */
    public void setHardwareType(String hardwareType)
    {
        if (StringUtil.isBlank(hardwareType))
        {
            return;
        }
        // check type of hardware
        String[] types = {
                SystemDefine.HARDWARE_TYPE_ASRS,
                SystemDefine.HARDWARE_TYPE_LIST,
                SystemDefine.HARDWARE_TYPE_RFT,
                SystemDefine.HARDWARE_TYPE_UNSTART,
        };
        for (int i = 0; i < types.length; i++)
        {
            if (types[i].equals(hardwareType))
            {
                _hardwareType = hardwareType;
                return;
            }
        }
        throw new RuntimeException("Unknown hardware type set. : " + hardwareType);
    }

    /**
     * <jp>画面DS番号を返却します。<br></jp>
     * <en>Returns dnNumebr. <br></en>
     * @return <jp>画面DS番号 &nbsp;&nbsp;</jp><en>dnNumber &nbsp;&nbsp;</en>
     */
    public String getDsNumber()
    {
        return _dfkUserInfo.getDsNumber();
    }

    /**
     * <jp>画面DS番号を設定します。<br></jp>
     * <en>Set dnNumber . <br></en>
     * @param dsNumber <jp>メ画面DS番号&nbsp;&nbsp;</jp><en>dnNumber &nbsp;&nbsp;</en>
     */
    public void setDsNumber(String dsNumber)
    {
        _dfkUserInfo.setDsNumber(dsNumber);
    }

    /**
     * ユーザIDを返します。
     * @return ユーザID
     */
    public String getUserId()
    {
        return _dfkUserInfo.getUserId();
    }

    /**
     * ユーザIDを設定します。
     * @param userId ユーザID
     */
    public void setUserId(String userId)
    {
        _dfkUserInfo.setUserId(userId);
    }

    /**
     * ユーザ名を返します。
     * @return ユーザ名を返します。
     */
    public String getUserName()
    {
        return _dfkUserInfo.getUserName();
    }

    /**
     * ユーザ名を設定します。
     * @param userName ユーザ名
     */
    public void setUserName(String userName)
    {
        _dfkUserInfo.setUserName(userName);
    }

    /**
     * 端末No.を返します。
     * @return 端末No.
     */
    public String getTerminalNo()
    {
        return _dfkUserInfo.getTerminalNumber();
    }

    /**
     * 端末No.を設定します。
     * @param terminalNo 端末No.
     */
    public void setTerminalNo(String terminalNo)
    {
        _dfkUserInfo.setTerminalNumber(terminalNo);
    }

    /**
     * 端末名を返します。
     * @return 端末名を返します。
     */
    public String getTerminalName()
    {
        return _dfkUserInfo.getTerminalName();
    }

    /**
     * 端末名を設定します。
     * @param terminalName 端末名
     */
    public void setTerminalName(String terminalName)
    {
        _dfkUserInfo.setTerminalName(terminalName);
    }

    /**
     * 端末アドレスを返します。
     * @return 端末アドレスを返します。
     */
    public String getTerminalAddress()
    {
        return _dfkUserInfo.getTerminalAddress();
    }

    /**
     * 端末アドレスを設定します。
     * @param terminalAddress 端末アドレス
     */
    public void setTerminalAddress(String terminalAddress)
    {
        _dfkUserInfo.setTerminalAddress(terminalAddress);
    }

    /**
     * ログインユーザ情報を返します。
     * @return ログインユーザ情報を返します。
     */
    public DfkUserInfo getDfkUserInfo()
    {
        return _dfkUserInfo;
    }

    /**
     * ログインユーザ情報を設定します。
     * @param dfkUserInfo ログインユーザ情報
     */
    public void setDfkUserInfo(DfkUserInfo dfkUserInfo)
    {
        _dfkUserInfo = dfkUserInfo;
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
    public static String getVersion()
    {
        return "$Id: WmsUserInfo.java 7692 2010-03-19 08:07:04Z ota $";
    }
}
