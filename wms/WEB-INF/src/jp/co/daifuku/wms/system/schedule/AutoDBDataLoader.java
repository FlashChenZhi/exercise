package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.AbstractDBDataLoader;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.fileentity.Receiving;
import jp.co.daifuku.wms.handler.AbstractEntity;

/**
 * <CODE>DBDataLoader</CODE>クラスは、自動取込み処理を行うクラスです。<BR>
 * <CODE>AbstractDBDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * 自動取込でexecuteを呼び出すための仮想クラスです。
 * 取込処理は各パッケージのLOADERクラスで行う。
 * <BR>
 * @version $Revision: 7652 $, ) $
 * @author  wms
 * @author  Last commit: $Author: okayama $
 */
public class AutoDBDataLoader
        extends AbstractDBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    
    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    
    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    
    /**
     * インスタンスを生成します。
     */
    public AutoDBDataLoader()
    {
        setUserInfo(getAutoUserInfo());
    }
    
    /**
     * EnvironmentInformation.propertiesに定義してある各データ取込キー名を取得します。
     * 
     * @return データ取込キー
     */
    @Override
    protected String getDataLoadKey()
    {
        return null;
    }

    /**
     * Make BSR logging accessible to the SCH
     * @param skip スキップフラグ
     * @param regist 登録フラグ
     */
    public void doBSRLog(boolean skip, boolean regist)
    {
    }

    /**
     * Get my loading limit from WMSParam.properties
     * 
     * @return My limit
     */
    public int getMyLoadingLimit()
    {
        return 0;
    }

    /**
     * 自動取込で使用するダミークラスのため、実際には各パッケージのLOADERが呼び出される。
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity HostToEWNエンティティ
     * @param outParameter パラメータ
     * @return 実行結果（正常：true 異常：false）
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean loadEntity(WarenaviSystemController sysController, HostToEWN inEntity, Params outParameter)
            throws CommonException,
                IOException
    {
        return false;
    }

    /**
     * ファイルの自動取込開始時に呼ばれます。<BR>
     * コネクションの生成とコミット・クローズはこのメソッドで行ないます。<BR>
     * 取込処理が完了したら取込チェックリストの発行を行ないます。<BR>
     * @param args  このメソッドの引数
     */
    public void execute(String[] args)
    {
        super.execute(args);
    }

    /**
     * 自動処理時にuserInfoをセットするメソッドです。
     * @return ユーザ情報
     */
    protected DfkUserInfo getAutoUserInfo()
    {
        DfkUserInfo userInfo = new DfkUserInfo();
        //DS番号
        userInfo.setDsNumber(DsNumberDefine.DS_AUTOLOAD);
        // ユーザID
        userInfo.setUserId(WmsParam.SYS_USER_ID);
        // ユーザ名称
        userInfo.setUserName(WmsParam.SYS_USER_NAME);
        // 端末No
        userInfo.setTerminalNumber(SERVER_TERMINAL_NO);
        // 端末名称
        userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        // 端末IPアドレス
        userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        // リソース番号
        userInfo.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AUTOLOAD);

        return userInfo;
    }
    
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return null;
    }
}
