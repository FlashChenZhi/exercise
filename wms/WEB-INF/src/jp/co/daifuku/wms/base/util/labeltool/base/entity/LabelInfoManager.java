// $$Id: LabelInfoManager.java 2903 2009-01-23 01:16:09Z kumano $$
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
import java.util.List;

import jp.co.daifuku.wms.base.util.labeltool.LabelParam;
import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;
import jp.co.daifuku.wms.base.util.labeltool.entity.LabelInfoEntity;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.parse.SBPLLabelConvertManager;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument;
import jp.co.daifuku.wms.label.xmlbeans.LabelItem;
import jp.co.daifuku.wms.label.xmlbeans.Variable;
import jp.co.daifuku.wms.label.xmlbeans.XMLManageDocument;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;


/**
 * ラベル管理情報に関する処理するクラスです<br>
 * ラベル情報の追加、変更、削除を行います。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/06</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2903 $, $Date: 2009-01-23 10:16:09 +0900 (金, 23 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelInfoManager
{
    /** <code>$RAS</code> */
    private static RandomAccessFile $RAS = null;

    /** <code>ファイルロックのインスタンス</code> */
    private static FileLock $FL = null;

    /** <code>ロックファイル最大回数</code> */
    private static int $MAX_TRY_LOCK_COUNTS = 10;

    /**
     * サーバ側ラベル管理情報を取得するメソッドです。<br>
     * 
     * @return ラベル管理情報エンティティのアレー
     * @throws DaiException 異常が発生した場合
     */
    public static LabelItem[] getRemoteLabelInfoList()
            throws DaiException
    {
        String remoteFileName = LabelInfoUtil.getRemotePath() + LabelConstants.LABEL_MANAGE_FILE_NAME;
        File serverFile = new File(remoteFileName);
        if (serverFile.getParentFile().isDirectory())
        {
            return getLabelInfoList(remoteFileName);
        }
        else
        {
            throw new DaiException("ERR0001");
        }
    }

    /**
     * ラベル印刷時に、ローカル側ラベル管理情報を取得するメソッドです。
     * 
     * @return
     * @throws DaiException
     * @throws IOException ファイル存在なしエラー
     */
    public static LabelItem[] actionPrintGetLocalLabelInfoList()
            throws DaiException,
                IOException
    {
        String remoteFileName = LabelParam.DB_MANAGE_FILE_PASS;
        File serverFile = new File(remoteFileName);
        if (serverFile.getParentFile().isDirectory())
        {
            return actionPrintGetLabelInfoList(remoteFileName);
        }
        else
        {
            throw new DaiException("ERR0001");
        }
    }

    /**
     * ローカル側ラベル管理情報を取得するメソッドです。<br>
     * 
     * @return ラベル管理情報エンティティのアレー
     * @throws DaiException 
     * @throws DaiException 異常が発生した場合
     */
    public static LabelItem[] getLocalLabelInfoList()
            throws DaiException
    {
        String localPath = LabelConstants.LOCAL_DATA_PATH;
        File localFile = new File(localPath);
        if (!localFile.exists())
        {
            localFile.mkdirs();
        }
        String localFileName = localPath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        return getLabelInfoList(localFileName);
    }

    /**
     * ローカル側指定管理Noのラベル情報を取得するメソッドです。<br>
     * 
     * @param id 管理No
     * @return ラベル管理情報
     * @throws DaiException 異常が発生した場合
     */
    public static LabelItem getLocalLabelInfo(String id)
            throws DaiException
    {
        LabelItem[] infoList = getLocalLabelInfoList();
        if (infoList != null && infoList.length > 0)
        {
            for (int i = 0; i < infoList.length; i++)
            {
                if (id.equals(infoList[i].getId()))
                {
                    return infoList[i];
                }
            }
        }
        return null;
    }

    /**
     * サーバ側指定管理Noのラベル情報を取得するメソッドです。<br>
     * 
     * @param id 管理No
     * @return ラベル管理情報
     * @throws DaiException 異常が発生した場合
     */
    public static LabelItem getRemoteLabelInfo(String id)
            throws DaiException
    {
        LabelItem[] infoList = getRemoteLabelInfoList();
        if (infoList != null && infoList.length > 0)
        {
            for (int i = 0; i < infoList.length; i++)
            {
                if (id.equals(infoList[i].getId()))
                {
                    return infoList[i];
                }
            }
        }
        return null;
    }

    /**
     * ラベル印刷実行時に、サーバ側指定管理Noのラベル情報を取得するメソッドです。<br>
     * 
     * @param id 管理No
     * @return ラベル管理情報
     * @throws DaiException 異常が発生した場合
     */
    public static LabelItem actionPrintGetLabelInfo(String id)
            throws DaiException,
                IOException
    {
        LabelItem[] infoList = actionPrintGetLocalLabelInfoList();
        if (infoList != null && infoList.length > 0)
        {
            for (int i = 0; i < infoList.length; i++)
            {
                if (id.equals(infoList[i].getId()))
                {
                    return infoList[i];
                }
            }
        }
        return null;
    }

    /**
     * ラベル情報を追加するメソッドです。<br>
     * 
     * @param labelInfo ラベル管理情報データ
     * @throws DaiException 異常が発生した場合
     */
    public static void addNewLabelInfo(LabelInfoEntity labelInfo)
            throws DaiException
    {
        LabelItem newItem = LabelItem.Factory.newInstance();
        newItem.setVersionNo("001");
        newItem.setName(labelInfo.getName());
        newItem.setLayoutName(labelInfo.getLayoutName());
        // サーバ側の管理情報を追加する。
        addRemoteLabelInfo(newItem);
    }

    /**
     * ラベル情報を更新するメソッドです。<br>
     * 
     * @param labelInfo ラベル管理情報データ
     * @throws DaiException 異常が発生した場合
     */
    public static void updateLabelInfo(LabelInfoEntity labelInfo)
            throws DaiException
    {
        LabelItem targetItem = LabelItem.Factory.newInstance();
        targetItem.setId(labelInfo.getId());
        targetItem.setVersionNo(labelInfo.getVersionNo());
        targetItem.setName(labelInfo.getName());
        targetItem.setLayoutName(labelInfo.getLayoutName());
        targetItem.setDeleteFlag("0");
        // サーバ側の管理情報を更新する。
        updateRemoteLabelInfo(targetItem);
    }

    /**
     * ローカル側の管理情報とサーバ側と同期処理を行うメソッドです。<br>
     * 
     * @param id 管理No
     * @throws DaiException 異常が発生した場合
     */
    public static void syncLabelInfo(String id)
            throws DaiException
    {
        LabelItem remoteItem;
        remoteItem = getRemoteLabelInfo(id);
        // サーバ側管理情報がない場合、ローカル側の対応する情報を削除する。
        if (remoteItem == null)
        {
            removeLocalLabelInfo(id);
        }
        else
        {
            LabelItem localItem = getLocalLabelInfo(id);
            if (localItem == null)
            {
                // ローカル側管理情報がない場合、サーバ側の対応する情報で追加する。
                addLocalLabelInfo(remoteItem);
            }
            else
            {
                // ローカル側管理情報がある場合、サーバ側の対応する情報で更新する。
                updateLocalLabelInfo(remoteItem);
            }
        }
    }

    /**
     * ラベル情報の状態を取得するメソッドです。<br>
     * 
     * @param id 管理No
     * @return 0 : ローカル側がサーバ側と一致する場合
     *         1 : サーバ側最新バージョンがある場合
     *         2 : サーバ側情報が論理削除された場合
     *         3 : サーバ側情報が物理削除された場合         
     * @throws DaiException 異常が発生した場合
     */
    public static int getStatus(String id)
            throws DaiException
    {
        LabelItem remoteItem = getRemoteLabelInfo(id);
        if (remoteItem == null)
        {
            return 3;
        }
        LabelItem localItem = getLocalLabelInfo(id);
        if ((localItem != null) && localItem.getDeleteFlag().equals("1"))
        {
            return 2;
        }
        if (localItem != null)
        {
            if (Integer.parseInt(localItem.getVersionNo()) >= Integer.parseInt(remoteItem.getVersionNo()))
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        return 0;
    }

    /**
     * レイアウトファイル名が管理情報に存在するかをチェックするメソッドです。<br>
     * 
     * @param fileName レイアウトファイル名 
     * @return ファイル名が存在する場合、trueを返します。<br>
     *         存在しない場合、falseを返します。
     * @throws DaiException 異常が発生した場合
     */
    public static boolean isLayoutFileNameExist(String fileName)
            throws DaiException
    {
        LabelItem[] items = getRemoteLabelInfoList();
        if (items == null)
        {
            return false;
        }
        for (int i = 0; i < items.length; i++)
        {
            if (items[i].getLayoutName().equals(fileName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * ラベル管理情報XMLファイルよりラベル管理情報を取得するメソッドです。<br>
     * ファイルが存在しない場合は、空的なファイルを生成します。
     * 
     * @param fileName ラベル管理情報XMLファイル名称
     * @return ラベル情報エンティティのアレー
     * @throws DaiException 異常が発生した場合
     */
    private static LabelItem[] getLabelInfoList(String fileName)
            throws DaiException
    {
        XMLManageDocument doc = null;
        File managerXml = new File(fileName);
        // ファイルが存在しない場合は、空白のファイルを新規する。
        if (!managerXml.exists())
        {
            try
            {
                managerXml.createNewFile();
                doc = XMLManageDocument.Factory.newInstance();
                doc.addNewXMLManage();
                XmlOptions xmlOptions = new XmlOptions();
                xmlOptions.setCharacterEncoding("UTF-8");
                xmlOptions.setSavePrettyPrint();
                doc.save(managerXml, xmlOptions);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                throw new DaiException("ERR0015", e);
            }
        }
        LabelItem[] labelItems = null;

        try
        {
            doc = XMLManageDocument.Factory.parse(managerXml);
        }
        catch (XmlException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();

        }
        labelItems = doc.getXMLManage().getLabelItemArray();

        return labelItems;
    }

    /**
     * ラベル管理情報XMLファイルよりラベル管理情報を取得するメソッドです。<br>
     * 
     * @param fileName ラベル管理情報XMLファイル名称
     * @return ラベル情報エンティティのアレー
     * @throws DaiException 異常が発生した場合
     * @throws IOException ファイル存在なしエラー
     */
    private static LabelItem[] actionPrintGetLabelInfoList(String fileName)
            throws DaiException,
                IOException
    {
        XMLManageDocument doc = null;
        File managerXml = new File(fileName);
        LabelItem[] labelItems = null;

        try
        {
            doc = XMLManageDocument.Factory.parse(managerXml);
        }
        catch (XmlException e)
        {
            e.printStackTrace();
            return null;
        }
        labelItems = doc.getXMLManage().getLabelItemArray();

        return labelItems;
    }

    /**
     * 管理Noを採番するメソッドです。<br>
     * 
     * @param items ラベル情報配列
     * @return 最新管理No
     * @throws DaiException 異常が発生した場合
     */
    private static String fetchMaxId(LabelItem[] items)
            throws DaiException
    {
        int maxId = 0;
        if (items != null && items.length > 0)
        {
            for (int i = 0; i < items.length; i++)
            {
                if (Integer.parseInt(items[i].getId()) > maxId)
                {
                    maxId = Integer.parseInt(items[i].getId());
                }
            }
        }
        return StringUtil.paddingLeft(Integer.toString(maxId + 1), '0', 3);
    }

    /**
     * サーバ側ラベル情報を追加するメソッドです。<br>
     * 
     * @param labelInfo ラベル情報
     * @throws DaiException 異常が発生した場合
     */
    public static void addRemoteLabelInfo(LabelItem labelInfo)
            throws DaiException
    {
        String remotePath =
                EnvDefHandler.getInstance().getDefineValue("serverAddress") + LabelConstants.DIRSEPACHAR
                        + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
        String remoteFileName = remotePath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        try
        {
            // ラベル情報ファイルをロックする。
            lockFile();
            if (isLayoutFileNameExist(labelInfo.getLayoutName()))
            {
                throw new DaiException("ERR0017");
            }
            // レイアウトファイルをサーバ側に送信する。
            FileManager.uploadLayoutFile(labelInfo.getLayoutName());
            // XML定義ファイルをサーバ側に送信する。
            FileManager.uploadXmlFile(labelInfo.getLayoutName());
            // エンティティファイルをサーバ側に送信する。
            FileManager.uploadEntityFile(labelInfo.getLayoutName());

            // 最新番号を採番する。
            String maxId = fetchMaxId(getRemoteLabelInfoList());
            labelInfo.setId(maxId);
            // サーバ側のラベル情報追加を行う
            addLabelInfo(labelInfo, remoteFileName);
            // ローカル側の管理情報を同期する。
            syncLabelInfo(maxId);
        }
        catch (DaiException e)
        {
            throw e;
        }
        finally
        {
            releaseFileLock();
        }
    }

    /**
     * ローカル側ラベル情報を追加するメソッドです。<br>
     * 
     * @param labelInfo ラベル情報
     * @throws DaiException 異常が発生した場合
     */
    private static void addLocalLabelInfo(LabelItem labelInfo)
            throws DaiException
    {
        String localPath = LabelConstants.LOCAL_DATA_PATH;
        String localFileName = localPath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        addLabelInfo(labelInfo, localFileName);
    }

    /**
     * ラベル情報を管理XMLファイルに追加するメソッドです。<br>
     * 
     * @param labelInfo ラベル情報
     * @param xmlFileName XMLファイル名
     * @throws DaiException 異常が発生した場合
     */
    private static void addLabelInfo(LabelItem labelInfo, String xmlFileName)
            throws DaiException
    {
        File xml = new File(xmlFileName);
        try
        {
            XMLManageDocument doc = XMLManageDocument.Factory.parse(xml);
            LabelItem newItem = doc.getXMLManage().addNewLabelItem();
            newItem.setId(labelInfo.getId());
            newItem.setName(labelInfo.getName());
            newItem.setLayoutName(labelInfo.getLayoutName());
            newItem.setVersionNo(labelInfo.getVersionNo());
            newItem.setDeleteFlag("0");

            XmlOptions xmlOptions = new XmlOptions();
            xmlOptions.setCharacterEncoding("UTF-8");
            xmlOptions.setSavePrettyPrint();
            doc.save(xml, xmlOptions);
        }
        catch (XmlException e)
        {
            throw new DaiException("Err", e);
        }
        catch (IOException e)
        {
            throw new DaiException("Err", e);
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
            String remotePath =
                    EnvDefHandler.getInstance().getDefineValue("serverAddress") + LabelConstants.DIRSEPACHAR
                            + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
            String lockFileName = remotePath + LabelConstants.DIRSEPACHAR + LabelConstants.LOCK_FILE_NAME;
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
            throw new DaiException("ERR0030", e);
        }
        catch (InterruptedException e)
        {
            throw new DaiException("ERR0030", e);
        }
    }

    /**
     * サーバ側ラベル情報を更新するメソッドです。<br>
     * 
     * @param labelInfo ラベル情報
     * @throws DaiException 異常が発生した場合
     */
    private static void updateRemoteLabelInfo(LabelItem labelInfo)
            throws DaiException
    {
        String remotePath =
                EnvDefHandler.getInstance().getDefineValue("serverAddress") + LabelConstants.DIRSEPACHAR
                        + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
        String remoteFileName = remotePath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        try
        {
            // ラベル情報ファイルをロックする。
            lockFile();
            int result = getStatus(labelInfo.getId());
            // 最新バージョンがある場合、エラーとする。
            if (result == 1)
            {
                throw new DaiException("ERR0021");
            }
            // 論理削除、又は物理削除された場合、エラーとする。
            if (result == 2 || result == 3)
            {
                throw new DaiException("ERR0020");
            }

            // XML定義ファイルを生成する。
            String layoutFileName = LabelInfoUtil.getLocalLayoutPath() + labelInfo.getLayoutName();
            String xmlFileName =
                    (LabelInfoUtil.getLocalXMLPath() + labelInfo.getLayoutName()).replaceAll(
                            LabelConstants.SUFFIX_LAYOUT, LabelConstants.SUFFIX_XML);
            SBPLLabelConvertManager compiler = new SBPLLabelConvertManager();
            if (Authority.$level.equals(Authority.USER_LEVEL))
            {
                // ユーザモードの場合に、サーバ側の可変項目で書き換えす。
                String remoteLayoutFileName = LabelInfoUtil.getRemoteLayoutPath() + labelInfo.getLayoutName();
                DAILabelDocument localDoc = compiler.convertLayoutToDoc(layoutFileName);
                DAILabelDocument remoteDoc = compiler.convertLayoutToDoc(remoteLayoutFileName);
                Variable[] curVars = remoteDoc.getDAILabel().getVariableArray();
                List<String> varList = new ArrayList<String>();
                if (curVars != null)
                {
                    for (int i = 0; i < curVars.length; i++)
                    {
                        varList.add(curVars[i].getFieldId());
                    }
                }
                Variable[] newVars = localDoc.getDAILabel().getVariableArray();
                if (newVars != null)
                {
                    // 可変項目の数が増えた場合、エラーとする。
                    if (newVars.length > varList.size())
                    {
                        throw new DaiException("ERR0031");
                    }
                    // 可変項目名が変更された場合、エラーとする。
                    for (int i = 0; i < newVars.length; i++)
                    {
                        if (!varList.contains(newVars[i].getFieldId()))
                        {
                            throw new DaiException("ERR0031");
                        }
                    }
                }
                // サーバ側の可変項目を保留する。
                localDoc.getDAILabel().setVariableArray(curVars);
                // XML定義ファイルを生成する。
                compiler.saveToXml(localDoc, xmlFileName);
            }
            else if (Authority.$level.equals(Authority.ADMIN_LEVEL))
            {
                // 管理者モード場合、直接XML定義ファイルを生成する。
                compiler.convertLayoutToXmlFile(layoutFileName, xmlFileName);
                // 管理者モードのみ、エンティティクラスファイルを生成する。
                String entityName = (labelInfo.getLayoutName()).replaceAll(LabelConstants.SUFFIX_LAYOUT, "");
                compiler.generateEntity(xmlFileName, entityName);
            }

            FileManager.uploadLayoutFile(labelInfo.getLayoutName());
            FileManager.uploadXmlFile(labelInfo.getLayoutName());
            if (Authority.$level.equals(Authority.ADMIN_LEVEL))
            {
                // 管理モードの場合、エンティティファイルをサーバ側に送信する。
                FileManager.deleteRemoteEntityFile(labelInfo.getLayoutName());
                FileManager.uploadEntityFile(labelInfo.getLayoutName());
            }
            // バージョン番号をアップする。
            labelInfo.setVersionNo(StringUtil.paddingLeft(
                    String.valueOf(Integer.parseInt(labelInfo.getVersionNo()) + 1), '0', 3));
            // ラベル情報更新を行う。
            updateLabelInfo(labelInfo, remoteFileName);
            // ローカル側の管理情報を同期する。
            syncLabelInfo(labelInfo.getId());
        }
        catch (DaiException e)
        {
            throw e;
        }
        finally
        {
            releaseFileLock();
        }
    }

    /**
     * ローカル側ラベル情報を更新するメソッドです。<br>
     * 
     * @param labelInfo ラベル情報
     * @throws DaiException 異常が発生した場合
     */
    private static void updateLocalLabelInfo(LabelItem labelInfo)
            throws DaiException
    {
        String localPath = LabelConstants.LOCAL_DATA_PATH;
        String localFileName = localPath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        updateLabelInfo(labelInfo, localFileName);
    }

    /**
     * 管理XMLファイルにラベル情報を更新するメソッドです。<br>
     * 
     * @param labelInfo ラベル情報
     * @param xmlFileName XMLファイル名
     * @throws DaiException 異常が発生した場合
     */
    private static void updateLabelInfo(LabelItem labelInfo, String xmlFileName)
            throws DaiException
    {
        File xml = new File(xmlFileName);
        try
        {
            XMLManageDocument doc = XMLManageDocument.Factory.parse(xml);
            LabelItem[] items = doc.getXMLManage().getLabelItemArray();
            if (items == null)
            {
                return;
            }
            for (int i = 0; i < items.length; i++)
            {
                if (items[i].getId().equals(labelInfo.getId()))
                {
                    items[i].setId(labelInfo.getId());
                    items[i].setName(labelInfo.getName());
                    items[i].setLayoutName(labelInfo.getLayoutName());
                    items[i].setVersionNo(labelInfo.getVersionNo());
                    items[i].setDeleteFlag(labelInfo.getDeleteFlag());
                    XmlOptions xmlOptions = new XmlOptions();
                    xmlOptions.setCharacterEncoding("UTF-8");
                    xmlOptions.setSavePrettyPrint();
                    doc.save(xml, xmlOptions);
                    break;
                }
            }
        }
        catch (XmlException e)
        {
            throw new DaiException("ERR0007", e);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0007", e);
        }
    }

    /**
     * ラベル情報を削除するメソッドです。<br>
     * 
     * @param id 管理No
     * @throws DaiException 異常が発生した場合
     */
    public static void removeLabelInfo(String id)
            throws DaiException
    {
        String remotePath =
                EnvDefHandler.getInstance().getDefineValue("serverAddress") + LabelConstants.DIRSEPACHAR
                        + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
        String remoteFileName = remotePath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        try
        {
            // ラベル情報ファイルをロックする。
            lockFile();
            // ラベル情報削除を行う。
            removeLabelInfo(id, remoteFileName);
            // ローカル側の管理情報を同期する。
            syncLabelInfo(id);
        }
        catch (DaiException e)
        {
            throw e;
        }
        finally
        {
            releaseFileLock();
        }
    }

    /**
     * サーバ側ラベル情報を論理削除するメソッドです。<br>
     * 
     * @param id 管理番号
     * @throws DaiException 異常が発生した場合
     */
    public static void logicRemoveLabelInfo(String id)
            throws DaiException
    {
        String remotePath =
                EnvDefHandler.getInstance().getDefineValue("serverAddress") + LabelConstants.DIRSEPACHAR
                        + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
        String remoteFileName = remotePath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        try
        {
            // ラベル情報ファイルをロックする。
            lockFile();
            // ラベル情報論理削除を行う。
            logicRemoveLabelInfo(id, remoteFileName);
            // 更新したTempファイルの内容をラベル情報にコピーする。
            syncLabelInfo(id);
        }
        catch (DaiException e)
        {
            throw e;
        }
        finally
        {
            releaseFileLock();
        }
    }

    /**
     * ラベル情報を回復するメソッドです。<br>
     * 
     * @param id 管理番号
     * @throws DaiException 異常が発生した場合
     */
    public static void recoverLabelInfo(String id)
            throws DaiException
    {
        String remotePath =
                EnvDefHandler.getInstance().getDefineValue("serverAddress") + LabelConstants.DIRSEPACHAR
                        + EnvDefHandler.getInstance().getDefineValue("rootDirectory");
        String remoteFileName = remotePath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        try
        {
            // ラベル情報ファイルをロックする。
            lockFile();
            // ラベル情報回復を行う。
            recoverLabelInfo(id, remoteFileName);
            // ローカル側の管理情報を同期する。
            syncLabelInfo(id);
        }
        catch (DaiException e)
        {
            throw e;
        }
        finally
        {
            releaseFileLock();
        }
    }

    /**
     * ローカル側ラベル情報を削除するメソッドです。<br>
     * 
     * @param id 管理No
     * @throws DaiException 異常が発生した場合
     */
    private static void removeLocalLabelInfo(String id)
            throws DaiException
    {
        String localPath = LabelConstants.LOCAL_DATA_PATH;
        String localFileName = localPath + LabelConstants.DIRSEPACHAR + LabelConstants.LABEL_MANAGE_FILE_NAME;
        removeLabelInfo(id, localFileName);
    }

    /**
     * 管理XMLファイルにラベル情報を物理削除するメソッドです。<br>
     * 
     * @param id 管理No
     * @param xmlFileName XMLファイル名
     * @throws DaiException 異常が発生した場合
     */
    private static void removeLabelInfo(String id, String xmlFileName)
            throws DaiException
    {
        File xml = new File(xmlFileName);
        try
        {
            XMLManageDocument doc = XMLManageDocument.Factory.parse(xml);
            LabelItem[] items = doc.getXMLManage().getLabelItemArray();
            if (items == null)
            {
                throw new DaiException("ERR0020");
            }
            int i;
            for (i = 0; i < items.length; i++)
            {
                // 該当する情報が存在、かつ論理削除状態である場合のみ、物理削除を行う。 
                if (items[i].getId().equals(id))
                {
                    if (items[i].getDeleteFlag().equals("1"))
                    {
                        String layoutName = items[i].getLayoutName();
                        doc.getXMLManage().removeLabelItem(i);
                        XmlOptions xmlOptions = new XmlOptions();
                        xmlOptions.setCharacterEncoding("UTF-8");
                        xmlOptions.setSavePrettyPrint();
                        doc.save(xml);

                        FileManager.deleteRemoteLayoutFile(layoutName);
                        FileManager.deleteRemoteXMLFile(layoutName);
                        FileManager.deleteRemoteEntityFile(layoutName);

                        FileManager.deleteLocalLayoutFile(layoutName);
                        FileManager.deleteLocalXMLFile(layoutName);
                        FileManager.deleteLocalEntityFile(layoutName);
                        break;
                    }
                    else
                    {
                        throw new DaiException("ERR0034");
                    }
                }
            }
            // 該当情報が既に物理削除された場合、エラーとする。
            if (i == items.length)
            {
                throw new DaiException("ERR0020");
            }
        }
        catch (XmlException e)
        {
            throw new DaiException("ERR0008", e);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0008", e);
        }
    }

    /**
     * 管理XMLファイルにラベル情報を論理削除するメソッドです。<br>
     * 
     * @param id 管理No
     * @param xmlFileName XMLファイル名
     * @throws DaiException 異常が発生した場合
     */
    private static void logicRemoveLabelInfo(String id, String xmlFileName)
            throws DaiException
    {
        File xml = new File(xmlFileName);
        try
        {
            XMLManageDocument doc = XMLManageDocument.Factory.parse(xml);
            LabelItem[] items = doc.getXMLManage().getLabelItemArray();
            if (items == null)
            {
                throw new DaiException("ERR0020");
            }
            int i;
            for (i = 0; i < items.length; i++)
            {
                if (items[i].getId().equals(id))
                {
                    if (items[i].getDeleteFlag().equals("1"))
                    {
                        throw new DaiException("ERR0020");
                    }
                    items[i].setDeleteFlag("1");
                    XmlOptions xmlOptions = new XmlOptions();
                    xmlOptions.setCharacterEncoding("UTF-8");
                    xmlOptions.setSavePrettyPrint();
                    doc.save(xml);
                    break;
                }
            }
            if (i == items.length)
            {
                throw new DaiException("ERR0020");
            }
        }
        catch (XmlException e)
        {
            throw new DaiException("ERR0008", e);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0008", e);
        }
    }

    /**
     * 管理XMLファイルにラベル情報を回復するメソッドです。<br>
     * 
     * @param id 管理No
     * @param xmlFileName XMLファイル名
     * @throws DaiException 異常が発生した場合
     */
    private static void recoverLabelInfo(String id, String xmlFileName)
            throws DaiException
    {
        File xml = new File(xmlFileName);
        try
        {
            XMLManageDocument doc = XMLManageDocument.Factory.parse(xml);
            LabelItem[] items = doc.getXMLManage().getLabelItemArray();
            if (items == null)
            {
                return;
            }
            int i;
            for (i = 0; i < items.length; i++)
            {
                // 該当する情報が存在、かつ論理削除状態である場合のみ、回復を行う。 
                if (items[i].getId().equals(id))
                {
                    if (items[i].getDeleteFlag().equals("1"))
                    {
                        items[i].setDeleteFlag("0");
                        items[i].setVersionNo("001");
                        XmlOptions xmlOptions = new XmlOptions();
                        xmlOptions.setCharacterEncoding("UTF-8");
                        xmlOptions.setSavePrettyPrint();
                        doc.save(xml);
                        break;
                    }
                    else
                    {
                        throw new DaiException("ERR0035");
                    }
                }
            }
            // 該当情報が既に物理削除された場合、エラーとする。
            if (i == items.length)
            {
                throw new DaiException("ERR0020");
            }
        }
        catch (XmlException e)
        {
            throw new DaiException("ERR0033", e);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0033", e);
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
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
}
