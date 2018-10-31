// $Id: Icons.java 8075 2014-09-19 07:16:57Z okayama $
package jp.co.daifuku.wms.base.util.timekeeper.util;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * ユーザインターフェースで使用するアイコンを保持するクラスです。<br>
 * アイコンファイルは、本クラス配置ディレクトリ以下の"images"
 * ディレクトリに保存してください。
 *
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */
public final class Icons
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** アイコン保存先ディレクトリ */
    public static final String ICON_DIR = "./images/";

    /** ツリーノードアイコン */
    public Icon DEFAULT_NODE;

    /** 編集アイコン */
    public Icon EDIT;

    /** 終了アイコン */
    public Icon EXIT;

    /** ファイル検索アイコン */
    public Icon FIND_FILE;

    /** リーフノードアイコン */
    public Icon LEAF_NODE;

    /** 保存要求アイコン */
    public Icon NEED_SAVE;

    /** 新規作成アイコン */
    public Icon NEW;

    /** 開ノードアイコン */
    public Icon OPEN_NODE;

    /** 再読込アイコン */
    public Icon RELOAD;

    /** 削除アイコン */
    public Icon REMOVE;

    /** 更新アイコン */
    public Icon UPDATE;

    /** セーブアイコン */
    public Icon SAVE;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    public Icons()
    {
        super();

        /** ツリーノードアイコン */
        DEFAULT_NODE = getImageIcon("defaultNode.gif");

        /** 編集アイコン */
        EDIT = getImageIcon("edit.gif");

        /** 終了アイコン */
        EXIT = getImageIcon("exit.gif");

        /** ファイル検索アイコン */
        FIND_FILE = getImageIcon("findFile.gif");

        /** リーフノードアイコン */
        LEAF_NODE = getImageIcon("leafNode.gif");

        /** 保存要求アイコン */
        NEED_SAVE = getImageIcon("needSave.gif");

        /** 新規作成アイコン */
        NEW = getImageIcon("new.gif");

        /** 開ノードアイコン */
        OPEN_NODE = getImageIcon("openNode.gif");

        /** 再読込アイコン */
        RELOAD = getImageIcon("reload.gif");

        /** 削除アイコン */
        REMOVE = getImageIcon("remove.gif");

        /** 更新アイコン */
        UPDATE = getImageIcon("update.gif");

        /** セーブアイコン */
        SAVE = getImageIcon("save.gif");
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * イメージアイコンを取得します。<br>
     * 対象となるイメージアイコンが見あたらなかった場合や
     * イメージの読込に失敗した場合は、nullが返ります。
     * @param iconFile アイコンファイル名
     * @return イメージアイコン
     */
    private ImageIcon getImageIcon(String iconFile)
    {
        final String resourcePath = ICON_DIR + iconFile;
        final URL url = getClass().getResource(resourcePath);
        if (null != url)
        {
            InputStream is = null;
            try
            {
                final File targetFile = new File(url.toURI());
                final int size = (int)targetFile.length();
                byte[] bytes = new byte[size];
                is = getClass().getResourceAsStream(resourcePath);
                is.read(bytes);
                return new ImageIcon(bytes);
            }
            catch (Exception e)
            {
                // ここでは何もしません。
            }
            finally
            {
                try
                {
                    if (is != null)
                    {
                        is.close();
                    }
                }
                catch (IOException e)
                {
                    // 失敗した場合は何もしない
                }
            }
        }
        return null;
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
        return "$Id: Icons.java 8075 2014-09-19 07:16:57Z okayama $";
    }
}
