// $Id: Id1031SCH.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.master.rft;

import java.io.FileNotFoundException;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.pcart.base.controller.PCTItemController;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.rft.AbstractIdSCH;
import jp.co.daifuku.wms.base.rft.IdSchException;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * マスタ画像登録確定スケジュール処理<BR>
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  Kojima
 * @author  Last commit: $Author: arai $
 */
public class Id1031SCH
        extends AbstractIdSCH
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
     * コンストラクタ
     */
    public Id1031SCH()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** 
     * マスタ画像登録確定スケジュール
     * 
     * @param compIdParam       完了データ(電文内容)。<CODE>InParameter</CODE>クラスを継承したクラス
     * @param compFileParams    完了データ(ファイル内容)。<CODE>InParameter</CODE>クラスを継承したクラス(配列)(未使用)
     * @throws CommonException  例外が発生した場合に通知されます。
     */
    public void completeSCH(InParameter compIdParam, InParameter[] compFileParams)
            throws CommonException
    {
        // 日次処理中チェック
        WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getClass());
        if (wsc.isDailyUpdating())
        {
            throw new IdSchException(IdSchException.DAILY_UPDATING, "");
        }

        // マスタSAVE、LOAD中チェック、データ取込チェック
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);
        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_SAVE.equals(pctSystem.getPctmasterLoadFlag())
                    || PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag())
                    || wsc.isDataLoading())
            {
                throw new LockTimeOutException();
            }
        }

        PCTMasterInParameter compParam = (PCTMasterInParameter)compIdParam;

        //PCT商品マスタコントローラ
        PCTItemController itemc = new PCTItemController(_wConn, getClass());

        // ファイル名
        String filePath = createRecvFilePathName(compParam.getFileName());
        try
        {
            // 画像登録
            itemc.setItemPicture(compParam.getConsignorCode(), compParam.getItemCode(), compParam.getEnteringQty(),
                    filePath);
        }
        catch (FileNotFoundException e)
        {
            throw new ReadWriteException();
        }
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
    /**
     * IDから受け取ったファイル名から、受信ファイルのパスを生成します。<BR>
     * 
     * @param fileName  ファイル名
     * @return Idにセットする送信ファイル名(パス付き)
     */
    public String createRecvFilePathName(String fileName)
    {
        return WmsParam.DAIDATA + fileName;
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
        return "$Id: Id1031SCH.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
