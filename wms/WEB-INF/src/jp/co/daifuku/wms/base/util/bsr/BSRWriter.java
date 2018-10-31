//$Id: BSRWriter.java 6260 2009-12-01 01:54:44Z okamura $
package jp.co.daifuku.wms.base.util.bsr;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.util.patlite.PatliteOperator;
import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteCommand;

/**
 * BSR情報を書き込むためのクラスです。
 *
 *
 * @version $Revision: 6260 $, $Date: 2009-12-01 10:54:44 +0900 (火, 01 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public final class BSRWriter
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
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
     * インスタンスを生成します。<br>
     */
    private BSRWriter()
    {
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * BSR情報を書き込みます。
     * @param category カテゴリーID
     * @param facility 結果区分
     * @param status ステータスコード
     * @param description 詳細メッセージ
     * @param caller 呼び出し元クラス名
     * @throws ReadWriteException BSr情報の書き込みに失敗した際にスローされます
     */
    public static void write(String category, int facility, int status, String description, Class caller)
            throws ReadWriteException
    {
        // BSR定義ファイルのロード
        BSRCategory bc = new BSRCategory();

        if (bc.hasCategory(category) && bc.isEnabled(category))
        {
            // 警告灯点灯処理
            if (bc.isUsePatlite(category))
            {
                PatliteCommand[] commands = bc.getPatliteCommands(facility);
                if (0 < commands.length)
                {
                    for (String id : bc.getPatlites(category))
                    {
                        PatliteOperator.set(id, commands);
                        // debugSet(id, commands);
                    }
                }
            }
        }
    }

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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Patlite debug.
     * 
     * @param id patlite id
     * @param commands patlite commands
     */
    static void debugSet(String id, PatliteCommand[] commands)
    {
        System.out.println("Send to Patlite:" + id);
        for (PatliteCommand cmd : commands)
        {
            System.out.println("\tCommand:" + cmd);
        }
        System.out.println("---");
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: BSRWriter.java 6260 2009-12-01 01:54:44Z okamura $";
    }
}
