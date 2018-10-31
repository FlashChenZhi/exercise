// $$Id: MLOcxDispatcher.java 1911 2008-12-11 02:51:48Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.base;





import com.jacob.com.Dispatch;
import com.jacob.com.Variant;



/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */







/**
 * MLOcxDispatcher class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/11</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class MLOcxDispatcher
        extends Dispatch
{
    /**
     * このクラスのコンストラクタです。
     * @throws Exception 異常が発生した場合
     */
    public MLOcxDispatcher()
            throws Exception
    {
        super("MLMECONTROL.MLmeControlCtrl.1");
    }

    /**
     * レイアウトファイル名を取得します。
     *  
     * @return レイアウトファイル名
     */
    public String getLayoutFile()
    {
        return Dispatch.get(this, "LayoutFile").toString();
    }

    /**
     * レイアウトファイル名を設定します。
     * @param pLayoutFile レイアウトファイル名
     */
    public void setLayoutFile(String pLayoutFile)
    {
        Dispatch.put(this, "LayoutFile", pLayoutFile);
    }

    /**
     * プリンタ情報パスを取得します。
     * 
     * @return プリンタ情報パス
     */
    public String getPrnPath()
    {
        return Dispatch.get(this, "PrnPath").toString();
    }

    /**
     * プリンタ情報パスを設定します。
     * 
     * @param pPrnPath プリンタ情報パス
     */
    public void setPrnPath(String pPrnPath)
    {
        Dispatch.put(this, "PrnPath", pPrnPath);
    }

    /**
     * 通信パラメータを取得します。
     * 
     * @return 通信パラメータ
     */
    public String getSetting()
    {
        return Dispatch.get(this, "Setting").toString();
    }

    /**
     * 通信パラメータを設定します。
     * 
     * @param pSetting 通信パラメータ 
     */
    public void setSetting(String pSetting)
    {
        Dispatch.put(this, "Setting", pSetting);
    }

    /**
     * 印字濃度を取得します。
     * 
     * @return 印字濃度
     */
    public String getDarkness()
    {
        return Dispatch.get(this, "Darkness").toString();
    }

    /**
     * 印字濃度を設定します。
     * 
     * @param pDarkness 印字濃度
     */
    public void setDarkness(String pDarkness)
    {
        Dispatch.put(this, "Darkness", pDarkness);
    }

    //ExOutputAckCheck
    public boolean getExOutputAckCheck()
    {
        return Dispatch.get(this, "ExOutputAckCheck").toBoolean();
    }

    public void setExOutputAckCheck(boolean pExOutputAckCheck)
    {
        Dispatch.put(this, "ExOutputAckCheck", new Variant(pExOutputAckCheck));
    }

    //MemoryCard
    public boolean getMemoryCard()
    {
        return Dispatch.get(this, "MemoryCard").toBoolean();
    }

    public void setMemoryCard(boolean pMemoryCard)
    {
        Dispatch.put(this, "MemoryCard", new Variant(pMemoryCard));
    }

    //JobName
    public String getJobName()
    {
        return Dispatch.get(this, "JobName").toString();
    }

    public void setJobName(String pJobName)
    {
        Dispatch.put(this, "JobName", pJobName);
    }

    //LayoutNameCaption
    public String getLayoutNameCaption()
    {
        return Dispatch.get(this, "LayoutNameCaption").toString();
    }

    public void setLayoutNameCaption(String pLayoutNameCaption)
    {
        Dispatch.put(this, "LayoutNameCaption", pLayoutNameCaption);
    }

    //MultiCut
    public int getMultiCut()
    {
        return Dispatch.get(this, "MultiCut").toInt();
    }

    public void setMultiCut(int pMultiCut)
    {
        Dispatch.put(this, "MultiCut", new Variant(pMultiCut));
    }

    //Offset
    public String getOffset()
    {
        return Dispatch.get(this, "Offset").toString();
    }

    public void setOffset(String pOffset)
    {
        Dispatch.put(this, "Offset", pOffset);
    }

    //Protocol
    public int getProtocol()
    {
        return Dispatch.get(this, "Protocol").toInt();
    }

    public void setProtocol(int pProtocol)
    {
        Dispatch.put(this, "Protocol", new Variant(pProtocol));
    }

    //Siwake
    public boolean getSiwake()
    {
        return Dispatch.get(this, "Siwake").toBoolean();
    }

    public void setSiwake(boolean pSiwake)
    {
        Dispatch.put(this, "Siwake", new Variant(pSiwake));
    }

    //Speed
    public String getSpeed()
    {
        return Dispatch.get(this, "Speed").toString();
    }

    public void setSpeed(String pSpeed)
    {
        Dispatch.put(this, "Speed", pSpeed);
    }

    //StatusID
    public int getStatusID()
    {
        return Dispatch.get(this, "StatusID").toInt();
    }

    public void setStatusID(int pStatusID)
    {
        Dispatch.put(this, "StatusID", new Variant(pStatusID));
    }

    //Timeout
    public int getTimeout()
    {
        return Dispatch.get(this, "Timeout").toInt();
    }

    public void setTimeout(int pTimeout)
    {
        Dispatch.put(this, "Timeout", new Variant(pTimeout));
    }

    //TotalQtyCaption
    public Variant getTotalQtyCaption()
    {
        return Dispatch.get(this, "TotalQtyCaption");
    }

    public void setTotalQtyCaption(int pTotalQtyCaption)
    {
        Dispatch.put(this, "TotalQtyCaption", new Variant(pTotalQtyCaption));
    }

    //COMMode
    public int getCOMMode()
    {
        return Dispatch.get(this, "COMMode").toInt();
    }

    public void setCOMMode(int pCOMMode)
    {
        Dispatch.put(this, "COMMode", new Variant(pCOMMode));
    }

    //Formoverlay
    public int getFormoverlay()
    {
        return Dispatch.get(this, "Formoverlay").toInt();
    }

    public void setFormoverlay(int pFormoverlay)
    {
        Dispatch.put(this, "Formoverlay", new Variant(pFormoverlay));
    }

    //OutCut
    public int getOutCut()
    {
        return Dispatch.get(this, "OutCut").toInt();
    }

    public void setOutCut(int pOutCut)
    {
        Dispatch.put(this, "OutCut", new Variant(pOutCut));
    }

    /**
     * 通信ポートをオープンします。
     * 
     * @param nSyncMode 通信モード
     * @return 結果コード<br>
     *         0  正常<br>
     *         1  Setting プロパティの値が不正です。<br>
     *         3  既にオープンされています。<br>
     *         4  ポートオープン時にエラーが発生しました。<br>
     *         12 通信設定と通信プロトコルの組み合わせが未サポートです。
     */
    public int openPort(int nSyncMode)
    {
        return Dispatch.call(this, "OpenPort", new Variant(nSyncMode)).getInt();
    }

    /**
     * 発行処理を行います。
     * 
     * @return 結果コード
     */
    public int output()
    {
        return Dispatch.call(this, "Output").getInt();
    }

    /**
     * 現在使用している通信ポートをクローズします。
     * 
     * @return 結果コード<br>
     *         0 正常<br> 
     *         5 ポートがオープンされていません。<br>
     *         6 ポートクローズ時にエラーが発生しました。
     */
    public int closePort()
    {
        return Dispatch.call(this, "ClosePort").getInt();
    }

}
