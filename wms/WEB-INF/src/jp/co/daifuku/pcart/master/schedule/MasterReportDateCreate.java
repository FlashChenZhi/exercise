// $Id: MasterReportDateCreate.java 7247 2010-02-26 05:46:27Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.fileentity.PCTHostItem;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * 商品マスタ報告処理のスケジュール処理を行います。
 * 
 * Designer : Sakashita
 * Maker : Sakashita
 * @version $Revision: 7247 $, $Date: 2010-02-26 14:46:27 +0900 (金, 26 2 2010) $
 * @author  $Author: okayama $
 */
public class MasterReportDateCreate
        extends AbstractReportDataCreator
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public MasterReportDateCreate(Connection conn) throws CommonException
    {
        //インスタンス変数に値をセットします。
        super(conn);

        //処理結果メッセージを初期化します。
        setMessage(null);
    }

    // Public methods ------------------------------------------------
    /**
     * 商品マスタ情報報告データを作成します。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @throws IOException 入出力の例外が発生した場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @return 報告データ作成処理に成功した場合はtrue、報告データが無い、又は、作成に失敗した場合はfalseを返します。
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // 報告データエンティティを指定してファイルハンドラ作成
        PCTHostItem rItemEntity = new PCTHostItem();
        FileHandler handler = AbstractFileHandler.getInstance(rItemEntity);

        //報告データ件数を初期化します。
        setReportCount(0);

        // 商品マスタを検索する為のクラスインスタンスを作成します。
        PCTItemFinder sfinder = new PCTItemFinder(getConnection());

        try
        {
            // 商品マスタ情報の検索キーを作成します。
            PCTItemSearchKey skey = createkey();

            //カーソルオープン
            sfinder.open(true);

            // 作成した検索キーで商品マスタ情報をfinderで検索します。
            if (sfinder.search(skey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
            }

            //検索データが存在する場合は、CSV出力処理を行います。
            // ローカル変数を宣言します。
            PCTItem[] pctItem = null;

            // 商品マスタ情報報告CSVファイルをオープンします
            while (sfinder.hasNext())
            {
                // 検索結果を100件づつ取得します。
                pctItem = (PCTItem[])sfinder.getEntities(RESULT_READ_QTY);

                // 商品マスタ情報報告のCSVファイルへCSVWriteメソッドで出力します。
                for (PCTItem item : pctItem)
                {
                    if (getReportCount() == 0)
                    {
                        // 初回の出力時にファイルのオープンを行います。
                        try
                        {
                            // 実績ファイル名の生成（一時ファイル保存フォルダに作成する)
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));

                            // 最終実績ファイル名のセット
                            setResultFileName(getFileName() + sysTime + getExtention());
                            // 一時保存用ファイルに書き込み
                            // ディレクトリが存在しない場合は作成
                            prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                            handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                            // ファイルを作成したのでエラー発生時には削除
                            deleteFile = true;
                        }
                        catch (ReadWriteException ex)
                        {
                            //6003019=指定されたフォルダは無効です。
                            setMessage("6003019");
                            return false;
                        }
                        handler.clear();
                    }
                    // 商品マスタデータ(CSV)に出力します。
                    writeCsv(handler, rItemEntity, item);
                    setReportCount(getReportCount() + 1);
                }
            }
            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;

            // 6001009=データの書き込みは正常に終了しました。
            setMessage("6001009");
        }
        catch (ReadWriteException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return false;
        }
        finally
        {
            sfinder.close();

            if (handler.isOpen())
            {
                handler.close();
            }

            // ファイル作成後にExceptionが発生した場合など、作成したファイルを削除する
            if (deleteFile)
            {
                deleteFile(new File(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName()));
            }
        }

        return true;
    }

    /**
     * 実績prefix名、ディレクトリを設定します。
     * @throws ReadWriteException データベースアクセスエラー
     */
    public void setResultReportFilePath()
            throws ReadWriteException
    {
        // 報告データファイルの環境情報を取得します。
        try
        {
            acquireExchangeEnvironment(Parameter.DATA_TYPE_PCTITEM_MASTER);
        }
        catch (ScheduleException e)
        {
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**
     * 商品マスタ情報出力を行います。<BR>
     * 
     * @param handler ファイルハンドラ<BR>
     * @param rItemEntity 出力エンティティ<BR>
     * @param item 商品マスタエンティティ<BR>
     * @throws IOException 入出力の例外が発生した場合に通知されます。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected void writeCsv(FileHandler handler, PCTHostItem rItemEntity, PCTItem item)
            throws ReadWriteException
    {
        // 商品マスタ情報データを項目にセットします。
        rItemEntity.setValue(PCTHostItem.LOAD_FLAG, item.getManagementType());
        rItemEntity.setValue(PCTHostItem.CONSIGNOR_CODE, item.getConsignorCode());
        rItemEntity.setValue(PCTHostItem.ITEM_CODE, item.getItemCode());
        rItemEntity.setValue(PCTHostItem.ITEM_NAME, item.getItemName());
        rItemEntity.setValue(PCTHostItem.JAN, item.getJan());
        rItemEntity.setValue(PCTHostItem.ITF, item.getItf());
        rItemEntity.setValue(PCTHostItem.BUNDLE_ITF, item.getBundleItf());
        rItemEntity.setValue(PCTHostItem.LOT_ENTERING_QTY, item.getValue(PCTItem.LOT_ENTERING_QTY));
        rItemEntity.setValue(PCTHostItem.ENTERING_QTY, item.getValue(PCTItem.ENTERING_QTY));
        rItemEntity.setValue(PCTHostItem.BUNDLE_ENTERING_QTY, item.getValue(PCTItem.BUNDLE_ENTERING_QTY));
        rItemEntity.setValue(PCTHostItem.UNIT, item.getValue(PCTItem.UNIT));
        rItemEntity.setValue(PCTHostItem.SINGLE_WEIGHT, item.getValue(PCTItem.SINGLE_WEIGHT));
        rItemEntity.setValue(PCTHostItem.WEIGHT_DISTINCT, item.getValue(PCTItem.WEIGHT_DISTINCT_RATE));
        rItemEntity.setValue(PCTHostItem.LOCATION_NO_1, item.getLocationNo1());
        rItemEntity.setValue(PCTHostItem.ENTERING_QTY_1, item.getValue(PCTItem.ENTERING_QTY_1));
        rItemEntity.setValue(PCTHostItem.LOCATION_NO_2, item.getLocationNo2());
        rItemEntity.setValue(PCTHostItem.ENTERING_QTY_2, item.getValue(PCTItem.ENTERING_QTY_2));
        rItemEntity.setValue(PCTHostItem.LOCATION_NO_3, item.getLocationNo3());
        rItemEntity.setValue(PCTHostItem.ENTERING_QTY_3, item.getValue(PCTItem.ENTERING_QTY_3));
        rItemEntity.setValue(PCTHostItem.LOCATION_NO_4, item.getLocationNo4());
        rItemEntity.setValue(PCTHostItem.ENTERING_QTY_4, item.getValue(PCTItem.ENTERING_QTY_4));
        rItemEntity.setValue(PCTHostItem.INFORMATION, item.getInformation());
        rItemEntity.setValue(PCTHostItem.USE_BY_PERIOD, item.getUseByPeriod());
        rItemEntity.setValue(PCTHostItem.INSTOCK_LIMIT_DATE, item.getInstockLimitDate());
        rItemEntity.setValue(PCTHostItem.SHIPPING_LIMIT_DATE, item.getShippingLimitDate());
        rItemEntity.setValue(PCTHostItem.LATEST_USE_BY_DATE, item.getLatestUseByDate());
        rItemEntity.setValue(PCTHostItem.LATEST_MANUFACUTURE_DATE, item.getLatestManufacutureDate());
        rItemEntity.setValue(PCTHostItem.LATEST_RETRIEVAL_DATE, item.getLatestRetrievalDate());
        rItemEntity.setValue(PCTHostItem.LATEST_STOCK, item.getLatestStock());
        rItemEntity.setValue(PCTHostItem.OLDEST_STOCK, item.getOldestStock());
        rItemEntity.setValue(PCTHostItem.MANAGEMENT_FLAG, item.getManagementFlag());
        rItemEntity.setValue(PCTHostItem.UPPER_QTY, item.getValue(PCTItem.UPPER_QTY));
        rItemEntity.setValue(PCTHostItem.LOWER_QTY, item.getValue(PCTItem.LOWER_QTY));

        // 商品マスタ情報データをCSVに出力します。
        handler.lock();
        handler.create(rItemEntity);
        handler.unLock();

        return;
    }

    /**
     * 商品マスタ情報の検索キーを作成します。<BR>
     * @return 検索キー
     */
    protected PCTItemSearchKey createkey()
    {
        PCTItemSearchKey skey = new PCTItemSearchKey();

        skey.clear();
        skey.setConsignorCodeOrder(true);
        skey.setItemCodeOrder(true);
        skey.setLotEnteringQtyOrder(true);

        return skey;
    }

    // Private methods -----------------------------------------------

    /**
     * 自動生成されたメソッド
     * @return 特に処理なし
     */
    public ReportDataCreator getReportClass()
    {
        return null;
    }

    /**
     * 統計情報の取得を行います。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void statics()
            throws CommonException
    {
        // 取得するテーブルのハンドラーを生成して下さい。
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: MasterReportDateCreate.java 7247 2010-02-26 05:46:27Z okayama $";
    }
}
