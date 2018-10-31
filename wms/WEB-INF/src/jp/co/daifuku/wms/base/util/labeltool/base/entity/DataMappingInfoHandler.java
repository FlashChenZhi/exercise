// $$Id: DataMappingInfoHandler.java 2545 2009-01-06 07:27:47Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Hashtable;

import jp.co.daifuku.wms.base.util.labeltool.LabelParam;
import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument;
import jp.co.daifuku.wms.label.xmlbeans.DBRelationDocument;
import jp.co.daifuku.wms.label.xmlbeans.Label;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem;
import jp.co.daifuku.wms.label.xmlbeans.Relation;
import jp.co.daifuku.wms.label.xmlbeans.Variable;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;




/**
 * DB関連情報管理XMLファイルに関する処理を行うクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/07/01</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2545 $, $Date: 2009-01-06 16:27:47 +0900 (火, 06 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class DataMappingInfoHandler
{
    /** <code>$RAS</code> */
    private static RandomAccessFile $RAS = null;

    /** <code>ファイルロックのインスタンス</code> */
    private static FileLock $FL = null;

    /** <code>ロックファイル最大回数</code> */
    private static int $MAX_TRY_LOCK_COUNTS = 10;

    /** <code>DB関連管理XMLファイル名</code> */
    private static String $FILENAME = LabelInfoUtil.getRemotePath()
            + LabelConstants.DB_RELATION_FILE_NAME;

    /**
     * ファイルが存在しない場合、空白のファイルを新規します。
     * @throws IOException 異常が発生した場合
     */
    private static void init()
            throws IOException
    {
        File mangerXml = new File($FILENAME);

        if (!mangerXml.exists())
        {
            DBRelationDocument xmlDoc = DBRelationDocument.Factory.newInstance();
            xmlDoc.addNewDBRelation();
            xmlDoc.save(mangerXml);
        }
    }

    /**
     * DB関連情報更新時間をを取得します。
     * @return 最新更新時間
     * @throws IOException 異常が発生した場合
     */
    public static String getLastUpdateTime()
            throws IOException
    {
        init();
        File f = new File($FILENAME);
        long modifiedTime = f.lastModified();
        return String.valueOf(modifiedTime);

    }

    /**
     * DB関連情報リストを取得します。
     * 
     * @return DB関連情報の配列
     * @throws IOException 異常が発生した場合
     */
    public static Label[] getMappingInfoList()
            throws IOException
    {
        init();
        File f = new File(LabelParam.DB_RELATION_FILE_PASS);

        try
        {
            DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
            Label[] labels = doc.getDBRelation().getLabelsArray();
            return labels;
        }
        catch (XmlException e)
        {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 指定管理NoのDB管理情報のDB項目リストを取得します。
     * 
     * @param id 管理No
     * @return DB項目リスト
     * @throws XmlException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static ArrayList<String> getTableFields(String id)
            throws XmlException,
                IOException
    {
        init();
        ArrayList<String> fieldes = new ArrayList<String>();
        File f = new File($FILENAME);
        DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
        Label[] lables = doc.getDBRelation().getLabelsArray();
        for (int i = 0; i < lables.length; i++)
        {

            if (lables[i].getId() != null && lables[i].getId().equals(id))
            {
                Relation[] relations = lables[i].getRelationsArray();
                if (relations != null && relations.length > 0)
                {
                    for (int j = 0; j < relations.length; j++)
                    {
                        fieldes.add(relations[j].getDBFieldName());
                    }
                }
                break;
            }
        }

        return fieldes;
    }

    /**
     * 指定管理NoのDB管理情報の可変印字項目リストを取得します。
     * 
     * @param id 管理No
     * @return 可変印字項目リスト
     * @throws XmlException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static ArrayList<String> getTableVariables(String id)
            throws XmlException,
                IOException
    {
        init();
        ArrayList<String> variables = null;
        File f = new File($FILENAME);
        DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
        Label[] lables = doc.getDBRelation().getLabelsArray();
        for (int i = 0; i < lables.length; i++)
        {

            if (lables[i].getId() != null && lables[i].getId().equals(id))
            {
                Relation[] relations = lables[i].getRelationsArray();
                if (relations != null && relations.length > 0)
                {
                    variables = new ArrayList<String>();
                    for (int j = 0; j < relations.length; j++)
                    {
                        variables.add(relations[j].getPrintFieldName());
                    }
                }
                break;
            }
        }
        return variables;
    }

    /**
     * 指定管理Noのラベルに関連するテーブル名を取得します。
     * 
     * @param id 管理No
     * @return 関連テーブル名
     * @throws XmlException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static String getTableName(String id)
            throws XmlException,
                IOException
    {
        init();
        String tableName = "";
        File f = new File($FILENAME);
        DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
        Label[] lables = doc.getDBRelation().getLabelsArray();
        for (int i = 0; i < lables.length; i++)
        {
            if (lables[i].getId() != null && lables[i].getId().equals(id))
            {
                tableName = lables[i].getTableName();
                break;
            }
        }
        return tableName;
    }

    /**
     * 指定管理Noのラベルに関連するdisableFlgを取得します。
     * 
     * @param id 管理No
     * @return disableFlg
     * @throws XmlException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static String getDisableFlg(String id)
            throws XmlException,
                IOException
    {
        init();
        String disableFlg = LabelConstants.FLAG_ON;
        File f = new File($FILENAME);
        DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
        Label[] lables = doc.getDBRelation().getLabelsArray();
        for (int i = 0; i < lables.length; i++)
        {
            if (lables[i].getId() != null && lables[i].getId().equals(id))
            {
                disableFlg = lables[i].getDisableFlag();
                break;
            }
        }
        return disableFlg;
    }

    /**
     * 指定管理NoのDB関連情報を取得します。
     * @param id 指定管理No
     * @return DB関連情報
     * @throws XmlException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static Hashtable<String, String> getMapingInfo(String id)
            throws XmlException,
                IOException
    {
        init();
        Hashtable<String, String> resourceTable = null;
        File f = new File($FILENAME);
        DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
        Label[] lables = doc.getDBRelation().getLabelsArray();
        Label label = null;
        if (lables != null && lables.length > 0)
        {
            for (int i = 0; i < lables.length; i++)
            {
                label = lables[i];
                if (label.getId() != null && label.getId().equals(id))
                {
                    Relation[] relations = lables[i].getRelationsArray();
                    if (relations != null && relations.length > 0)
                    {
                        resourceTable = new Hashtable<String, String>();
                        for (int j = 0; j < relations.length; j++)
                        {
                            resourceTable.put(relations[j].getPrintFieldName(),
                                    relations[j].getDBFieldName());
                        }
                        break;
                    }
                }
            }
        }
        return resourceTable;
    }

    /**
     * 指定管理NoのDB関連情報を削除します。
     * @param id 指定管理No
     * @throws DaiException 異常が発生した場合
     */
    public static void deleteMapingInfo(String id)
            throws DaiException
    {
        deleteMapingInfo(id, null);
    }

    /**
     * 指定管理NoのDB関連情報を削除します。
     * @param id 指定管理No
     * @param lastUpdateTime 最新更新時間
     * @throws DaiException 異常が発生した場合
     */
    public static void deleteMapingInfo(String id, String lastUpdateTime)
            throws DaiException
    {
        try
        {
            init();
            // ラベル情報ファイルをロックする。
            lockFile();

            if (lastUpdateTime != null)
            {
                if (!lastUpdateTime.equals(getLastUpdateTime()))
                {
                    throw new DaiException("ERR0037");
                }
            }

            File f = new File($FILENAME);
            DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
            Label[] lables = doc.getDBRelation().getLabelsArray();
            for (int i = 0; i < lables.length; i++)
            {

                if (lables[i].getId() != null && lables[i].getId().equals(id))
                {
                    lables[i].setNil();
                    doc.getDBRelation().removeLabels(i);
                }
            }
            doc.save(f);
        }
        catch (IOException e)
        {
            throw new DaiException("Err",e);
        }
        catch (DaiException e)
        {
            throw e;
        }
        catch (XmlException e)
        {
            throw new DaiException("Err",e);
        }
        finally
        {
            releaseFileLock();
        }

    }

    /**
     * 指定管理NoのDB関連情報を追加します。
     * @param id 指定管理No
     * @param tableName 関連テーブル名
     * @param data 関連データ
     * @param disableFlg 自動発行無効フラグ
     * @param lastUpdateTime 最新更新時間
     * @throws Exception 異常が発生した場合
     */
    public static void addMapingInfo(String id, String tableName, String[][] data,
            String disableFlg, String lastUpdateTime)
            throws Exception
    {
        try
        {
            // ラベル情報ファイルをロックする。
            lockFile();

            if (!lastUpdateTime.equals(getLastUpdateTime()))
            {
                throw new Exception("ERR0037");
            }

            checkItems(tableName, id, data);

            if (LabelConstants.FLAG_ON.equals(disableFlg))
            {
                File f = new File($FILENAME);
                DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
                Label[] lables = doc.getDBRelation().getLabelsArray();
                for (int i = 0; i < lables.length; i++)
                {
                    if (lables[i].getId() != null && lables[i].getId().equals(id))
                    {
                        lables[i].setDisableFlag(disableFlg);
                        break;
                    }
                }
                XmlOptions xmlOptions = new XmlOptions();
                xmlOptions.setCharacterEncoding("UTF-8");
                xmlOptions.setSavePrettyPrint();
                doc.save(f, xmlOptions);
            }
            else
            {
                File f = new File($FILENAME);
                DBRelationDocument doc = DBRelationDocument.Factory.parse(f);
                Label[] lables = doc.getDBRelation().getLabelsArray();
                for (int i = 0; i < lables.length; i++)
                {
                    if (lables[i].getId() != null && lables[i].getId().equals(id))
                    {
                        lables[i].setNil();
                        doc.getDBRelation().removeLabels(i);
                    }
                }

                Label label = doc.getDBRelation().addNewLabels();
                label.setId(id);
                if (!StringUtil.isEmpty(tableName))
                {
                    label.setTableName(tableName);
                }
                label.setDisableFlag(disableFlg);
                Relation relation = null;
                for (int i = 0; i < data.length; i++)
                {
                    if ("".equals(data[i][1]))
                    {
                        continue;
                    }
                    relation = label.addNewRelations();
                    relation.setPrintFieldName(data[i][0]);
                    relation.setDBFieldName(data[i][1]);
                }
                XmlOptions xmlOptions = new XmlOptions();
                xmlOptions.setCharacterEncoding("UTF-8");
                xmlOptions.setSavePrettyPrint();
                doc.save(f, xmlOptions);

            }

        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            releaseFileLock();
        }

    }

    /**
     * ファイルをロックします。
     * @throws DaiException 異常が発生した場合
     */
    private static void lockFile()
            throws DaiException
    {
        try
        {
            int i;
            String remotePath = EnvDefHandler.getInstance().getDefineValue("serverAddress")
                    + LabelConstants.DIRSEPACHAR
                    + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
            String lockFileName = remotePath
                    + LabelConstants.DIRSEPACHAR
                    + LabelConstants.LOCK_DBFILE_NAME;
            File lockFile = new File(lockFileName);
            if (!lockFile.exists())
            {
                lockFile.createNewFile();
            }
            lockFile.deleteOnExit();
            $RAS = new RandomAccessFile(lockFile, "rw");
            for (i = 0; i < $MAX_TRY_LOCK_COUNTS; i++)
            {
                $FL = $RAS.getChannel().tryLock();
                if ($FL != null && $FL.isValid())
                {
                    return;
                }
                Thread.sleep(50);
            }
            if (i == $MAX_TRY_LOCK_COUNTS)
            {
                throw new DaiException("ERR0030");
            }
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0030",e);
        }
        catch (InterruptedException e)
        {
            throw new DaiException("ERR0030",e);
        }
    }

    /**
     * ファイルロックを解除します。
     */
    private static void releaseFileLock()
    {
        if ($FL != null)
        {
            try
            {
                $FL.release();
                $RAS.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定テーブルのDB項目リストを取得します。
     * 
     * @param tableName テーブル名
     * @return DB項目リスト
     * @throws Exception 異常が発生した場合
     */
    private static ArrayList<String> getTableFieldList(String tableName)
            throws Exception
    {
        ArrayList<String> fieldsList = null;

        fieldsList = DBDefinitionHandler.getTableFields(LabelInfoUtil.getRemotePath()
                + LabelConstants.DB_DEFINATION_FILE_NAME, tableName);

        return fieldsList;
    }

    /**
     * 指定管理Noのラベルの可変印字項目リストを取得します。
     * @param id 管理No
     * @return 可変印字項目リスト
     * @throws Exception 異常が発生した場合
     */
    private static ArrayList<String> getVariableFieldList(String id)
            throws Exception
    {
        DAILabelDocument doc = null;
        LabelItem info = null;
        ArrayList<String> fields = null;
        Variable[] variables = null;

        info = LabelInfoManager.getRemoteLabelInfo(id);

        File f = new File(LabelInfoUtil.getRemoteXMLPath()
                + info.getLayoutName().substring(0,
                        info.getLayoutName().indexOf(LabelConstants.SUFFIX_LAYOUT))
                + LabelConstants.SUFFIX_XML);

        doc = DAILabelDocument.Factory.parse(f);

        variables = doc.getDAILabel().getVariableArray();
        if (variables != null && variables.length > 0)
        {
            fields = new ArrayList<String>();

            for (int i = 0; i < variables.length; i++)
            {
                fields.add(variables[i].getFieldId());
            }
        }
        return fields;
    }

    /**
     * ＤＢマッピング情報をチェックします。
     * @param tableName 関連テーブル名
     * @param id 管理Ｎｏ
     * @param data 関連情報配列
     * @throws Exception 異常が発生した場合
     */
    private static void checkItems(String tableName, String id, String data[][])
            throws Exception
    {
        ArrayList<String> dbFieldsList = getTableFieldList(tableName);

        ArrayList<String> variableList = getVariableFieldList(id);

        if (dbFieldsList != null
                && dbFieldsList.size() > 0
                && variableList != null
                && variableList.size() > 0)
        {
            for (int i = 0; i < data.length; i++)
            {
                if (!variableList.contains(data[i][0]))
                {
                    throw new Exception("ERR0039");
                }

                if ("".equals(data[i][1]))
                {
                    continue;
                }
                else
                {
                    if (!dbFieldsList.contains(data[i][1]))
                    {
                        throw new Exception("ERR0038");
                    }
                }
            }
        }
    }

}
