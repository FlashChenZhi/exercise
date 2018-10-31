// $Id: LogExpImpSet.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Serializable;
import java.util.Date;


/**
 * Part11ログ エクスポート インポート設定に関するエンティティです。
 *
 *
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  Last commit: $Author: admin $
 */
public class LogExpImpSet
        extends BaseEntity
        implements Serializable
{
    /**
     * エクスポート用テーブル名
     */
    private String exportTable;

    /**
     * インポート用テーブル名
     */
    private String importTable;

    /**
     * テーブル名リソースキー
     */
    private String tableResourceKey;

    /**
     * ファイルプレフィックス
     */
    private String csvFilePrefix;

    /**
     * 取込日時
     */
    private Date importDate;

    /**
     * 取込ファイル名
     */
    private String importFileName;

    /**
     * 表示順
     */
    private int dispOrder;

    /**
     * エクスポートファイル名
     */
    private String exportFileName;

    /**
     * エクスポートファイルに保持するログデータの出力日時の期限
     */
    private Date exportFileLogDateTo;

    /**
     * 前回エクスポートデータの対象日時の期限
     */
    private Date nextExportLogDateFrom;

    /**
     * マスタリストボックスURI
     */
    private String masterUri;

    /**
     * マスタ種別
     */
    private int masterFlag;

    /**
     * lastExportLogDateToを返します。
     * @return lastExportLogDateToを返します。
     */
    public Date getNextExportLogDateFrom()
    {
        return nextExportLogDateFrom;
    }


    /**
     * lastExportLogDateToを設定します。
     * @param nextExportLogDateFrom nextExportLogDateFrom
     */
    public void setNextExportLogDateFrom(Date nextExportLogDateFrom)
    {
        this.nextExportLogDateFrom = nextExportLogDateFrom;
    }


    /**
     * exportFileLogDateToを返します。
     * @return exportFileLogDateToを返します。
     */
    public Date getExportFileLogDateTo()
    {
        return exportFileLogDateTo;
    }


    /**
     * exportFileLogDateToを設定します。
     * @param exportFileLogDateTo exportFileLogDateTo
     */
    public void setExportFileLogDateTo(Date exportFileLogDateTo)
    {
        this.exportFileLogDateTo = exportFileLogDateTo;
    }


    /**
     * exportFileNameを返します。
     * @return exportFileNameを返します。
     */
    public String getExportFileName()
    {
        return exportFileName;
    }


    /**
     * exportFileNameを設定します。
     * @param exportFileName exportFileName
     */
    public void setExportFileName(String exportFileName)
    {
        this.exportFileName = exportFileName;
    }


    /**
     * csvFilePrefixを返します。
     * @return csvFilePrefixを返します。
     */
    public String getCsvFilePrefix()
    {
        return csvFilePrefix;
    }


    /**
     * csvFilePrefixを設定します。
     * @param csvFilePrefix csvFilePrefix
     */
    public void setCsvFilePrefix(String csvFilePrefix)
    {
        this.csvFilePrefix = csvFilePrefix;
    }


    /**
     * dispOrderを返します。
     * @return dispOrderを返します。
     */
    public int getDispOrder()
    {
        return dispOrder;
    }


    /**
     * dispOrderを設定します。
     * @param dispOrder dispOrder
     */
    public void setDispOrder(int dispOrder)
    {
        this.dispOrder = dispOrder;
    }


    /**
     * exportTableを返します。
     * @return exportTableを返します。
     */
    public String getExportTable()
    {
        return exportTable;
    }


    /**
     * exportTableを設定します。
     * @param exportTable exportTable
     */
    public void setExportTable(String exportTable)
    {
        this.exportTable = exportTable;
    }


    /**
     * importDateを返します。
     * @return importDateを返します。
     */
    public Date getImportDate()
    {
        return importDate;
    }


    /**
     * importDateを設定します。
     * @param importDate importDate
     */
    public void setImportDate(Date importDate)
    {
        this.importDate = importDate;
    }


    /**
     * importFileNameを返します。
     * @return importFileNameを返します。
     */
    public String getImportFileName()
    {
        return importFileName;
    }


    /**
     * importFileNameを設定します。
     * @param importFileName importFileName
     */
    public void setImportFileName(String importFileName)
    {
        this.importFileName = importFileName;
    }


    /**
     * imprtTableを返します。
     * @return imprtTableを返します。
     */
    public String getImportTable()
    {
        return importTable;
    }


    /**
     * imprtTableを設定します。
     * @param improtTable improtTable
     */
    public void setImportTable(String improtTable)
    {
        this.importTable = improtTable;
    }


    /**
     * tableResourceKeyを返します。
     * @return tableResourceKeyを返します。
     */
    public String getTableResourceKey()
    {
        return tableResourceKey;
    }


    /**
     * tableResourceKeyを設定します。
     * @param tableResourceKey tableResourceKey
     */
    public void setTableResourceKey(String tableResourceKey)
    {
        this.tableResourceKey = tableResourceKey;
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
        return "$Id: LogExpImpSet.java 3965 2009-04-06 02:55:05Z admin $";
    }

    /**
     * マスタ種別を返します。
     * @return マスタ種別
     */
    public int getMasterFlag()
    {
        return masterFlag;
    }

    /**
     * マスタ種別を設定します。
     * @param masterFlag マスタ種別
     */
    public void setMasterFlag(int masterFlag)
    {
        this.masterFlag = masterFlag;
    }

    /**
     * マスタリストボックスURIを返します。
     * @return マスタリストボックスURI
     */
    public String getMasterUri()
    {
        return masterUri;
    }

    /**
     * マスタリストボックスURIを設定します。
     * @param masterUri マスタリストボックスURI
     */
    public void setMasterUri(String masterUri)
    {
        this.masterUri = masterUri;
    }
}
