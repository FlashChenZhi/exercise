// $Id: LstFolderSelectBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.listbox.folderselect;

import java.io.File;
import java.io.FilenameFilter;

import jp.co.daifuku.bluedog.util.PulldownHelper;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.master.listbox.PCTMasterListBoxDefine;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;


/**
 * 格納フォルダ検索リストボックスクラスです。<BR>
 * 親画面から入力した格納フォルダを基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  親画面から入力した格納フォルダをキーにして検索し、<BR>
 *  プルダウンに表示します。<BR>
 * <BR>
 * </DIR>
 * 2.「選択」ボタン（<CODE>lst_TicketNoSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  プルダウンに表示されているフォルダ名を親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>h.yoshida</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * Designer : h.yoshida <BR>
 * Maker : h.yoshida <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class LstFolderSelectBusiness
        extends LstFolderSelect
        implements WMSConstants
{
    // Class fields --------------------------------------------------
    // メッセージ表示用リソース番号
    /**
     * 指定されたフォルダは無効です。
     */
    protected static final int MSG_INVALID_DIRECTORY = 6003019;

    /**
     * 画面名称表示用リソース番号
     */
    protected static final String DISP_TITLE = "TLE-W1501";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // 画面名セット
        lbl_ListName.setText(DisplayText.getText(DISP_TITLE));
        // パラメータを取得
        // 格納フォルダ
        String folder = request.getParameter(PCTMasterListBoxDefine.FOLDER_KEY);
        // 「参照」ボタンフラグ
        // どの「参照」ボタンが押下されたか判断するため
        String flug = request.getParameter(PCTMasterListBoxDefine.BTNFLUG_KEY);

        viewState.setString(PCTMasterListBoxDefine.BTNFLUG_KEY, flug);

        // フォルダ名が空白なら環境設定ファイルから取得する。
        if (StringUtil.isBlank(folder))
        {
            IniFileOperator iniFile = new IniFileOperator(WmsParam.ENVIRONMENT);
            // 取り込みデータ(PCT入荷)
            if (PCTMasterListBoxDefine.LOAD_PCTINSTOCK.equals(flug))
            {
                folder =
                        iniFile.get(PCTMasterListBoxDefine.DATALOAD_FOLDER,
                                PCTMasterListBoxDefine.TYPE_KEY[PCTMasterListBoxDefine.DATATYPE_PCTINSTOCK]);
            }
            // 取り込みデータ(PCT出庫)
            else if (PCTMasterListBoxDefine.LOAD_PCTRETRIEVAL.equals(flug))
            {
                folder =
                        iniFile.get(PCTMasterListBoxDefine.DATALOAD_FOLDER,
                                PCTMasterListBoxDefine.TYPE_KEY[PCTMasterListBoxDefine.DATATYPE_PCTRETRIEVAL]);
            }
            // マスタデータ取り込み(PCT商品マスタ)
            else if (PCTMasterListBoxDefine.LOAD_PCTITEM.equals(flug))
            {
                folder =
                        iniFile.get(PCTMasterListBoxDefine.DATALOAD_FOLDER,
                                PCTMasterListBoxDefine.TYPE_KEY[PCTMasterListBoxDefine.DATATYPE_PCTITEM]);
            }
            // 報告データ(PCT入荷)
            else if (PCTMasterListBoxDefine.REPORT_PCTINSTOCK.equals(flug))
            {
                folder =
                        iniFile.get(PCTMasterListBoxDefine.REPORTDATA_FOLDER,
                                PCTMasterListBoxDefine.TYPE_KEY[PCTMasterListBoxDefine.DATATYPE_PCTINSTOCK]);
            }
            // 報告データ(PCT出庫)
            else if (PCTMasterListBoxDefine.REPORT_PCTRETRIEVAL.equals(flug))
            {
                folder =
                        iniFile.get(PCTMasterListBoxDefine.REPORTDATA_FOLDER,
                                PCTMasterListBoxDefine.TYPE_KEY[PCTMasterListBoxDefine.DATATYPE_PCTRETRIEVAL]);
            }
            // 報告データ(PCT棚卸)
            else if (PCTMasterListBoxDefine.REPORT_PCTINVENTORY.equals(flug))
            {
                folder =
                        iniFile.get(PCTMasterListBoxDefine.REPORTDATA_FOLDER,
                                PCTMasterListBoxDefine.TYPE_KEY[PCTMasterListBoxDefine.DATATYPE_PCTINVENT]);
            }
        }

        // 開始時のパスを読み込む
        File file = (new File(folder));
        String[] folderList = getFolderList(file.getAbsolutePath());

        PulldownHelper.setPullDown(pul_Folder, folderList);
    }

    // Package methods -----------------------------------------------

    // Event handler methods -----------------------------------------
    /**
     * プルダウンボックスの内容が変更された時の処理を行います。
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    public void pul_Folder_Change(ActionEvent e)
            throws Exception
    {
        // サブディレクトリを読み直す
        File fil = (new File(pul_Folder.getSelectedValue()));
        String[] folderList = getFolderList(fil.getAbsolutePath());

        // プルダウンのデータを更新
        pul_Folder.clearItem();
        PulldownHelper.setPullDown(pul_Folder, getFolderPullDown(folderList));
    }

    /**
     * 「上へ」ボタンを押したときの処理を行います。 <BR>
     *  <BR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Up_Click(ActionEvent e)
            throws Exception
    {
        File fil = (new File(pul_Folder.getSelectedValue())).getParentFile();
        if (fil != null)
        {
            // サブディレクトリを読み直す
            String[] folderList = getFolderList(fil.getAbsolutePath());

            // プルダウンのデータを更新
            pul_Folder.clearItem();
            PulldownHelper.setPullDown(pul_Folder, getFolderPullDown(folderList));
        }
    }

    /**
     * 「選択」ボタンを押したときの処理を行います。 <BR>
     *  <BR>
     *  ファイルパスをセットして親画面に遷移します。
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Select_Click(ActionEvent e)
            throws Exception
    {
        // 親画面に返却するパラメータセット
        ForwardParameters param = new ForwardParameters();

        // パラメータセット
        // フォルダ
        param.setParameter(PCTMasterListBoxDefine.FOLDER_KEY, pul_Folder.getSelectedValue());
        // 参照ボタンフラグ
        param.setParameter(PCTMasterListBoxDefine.BTNFLUG_KEY, viewState.getString(PCTMasterListBoxDefine.BTNFLUG_KEY));

        // 親画面に遷移する
        parentRedirect(param);
    }

    /**
     * 「閉じる」ボタンを押したときの処理を行います。 <BR>
     *  <BR>
     * リストボックスを閉じ、親画面へ遷移します。 <BR>
     *  <BR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // 親画面に戻る
        parentRedirect(null);
    }

    // Private methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * フォルダ一覧を取得します。
     * @param  currentFolder  取得するフォルダのパス
     * @return String[]       取得したフォルダの数
     */
    protected String[] getFolderList(String currentFolder)
    {

        // ファイルリスト取得
        File file = new File(currentFolder);

        String[] list;

        // 指定されたパスが読み込み可能なら、サブディレクトリ検索
        if (file.canRead())
        {
            list = file.list(new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    // ディレクトリのみ取得
                    return ((new File(dir, name)).isDirectory());
                }
            });

            // 取得した値がNullならパスは無効
            if (list == null)
            {
                list = new String[0];
                message.setMsgResourceKey(WmsMessageFormatter.format(MSG_INVALID_DIRECTORY));
            }
        }
        else
        {
            // パスが無効ならエラーメッセージ設定
            list = new String[0];
            message.setMsgResourceKey(WmsMessageFormatter.format(MSG_INVALID_DIRECTORY));
        }

        // ドライブリストを取得
        File[] roots = File.listRoots();

        // １番目には指定されたフォルダを設定
        String[] res = new String[list.length + roots.length + 1];
        res[0] = file.getAbsolutePath();

        // ドライブリストがプルダウンに表示される場合、上へボタンを押下できなくする。
        for (int i = 0; i < roots.length; i++)
        {
            if (res[0].equals(roots[i].getPath()))
            {
                // ｢上へ｣ボタンを押下できなくする
                btn_Up.setEnabled(false);
                break;
            }
            else
            {
                // ｢上へ｣ボタンを押せるようにする。
                btn_Up.setEnabled(true);
            }
        }

        // 最後がピリオドなら除外
        if (res[0].length() > 0 && (res[0].substring(res[0].length() - 1).equals(".")))
        {
            res[0] = res[0].substring(0, res[0].length() - 1);
        }

        // 最後がパス区切りでなければ、パス区切り追加
        if (res[0].length() > 0 && (!res[0].substring(res[0].length() - 1).equals(File.separator)))
        {
            res[0] = res[0] + File.separator;
        }

        //  ファイルを配列に追加
        int cnt;
        for (cnt = 0; cnt < list.length; cnt++)
        {
            res[cnt + 1] = res[0] + list[cnt];
        }

        // ルート一覧追加
        for (int i = 0; i < roots.length; i++)
        {
            res[++cnt] = roots[i].getAbsolutePath();
        }
        return res;
    }

    /**
     * フォルダ一覧文字配列から、プルダウンセット用配列取得
     *
     * @param  folders  取得するフォルダのパス
     * @return String[]       取得したフォルダの数
     */
    protected String[] getFolderPullDown(String[] folders)
    {
        String[] files = new String[folders.length];
        for (int cnt = 0; cnt < folders.length; cnt++)
        {
            files[cnt] = folders[cnt] + "," + folders[cnt] + ",0,0";
        }
        return files;
    }
}
//end of class
