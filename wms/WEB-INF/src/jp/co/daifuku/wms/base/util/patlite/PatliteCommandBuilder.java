// $Id: PatliteCommandBuilder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteCommand;


/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * パトライトの機種に応じたコマンドの生成を行う抽象クラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */


public abstract class PatliteCommandBuilder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String PRE_FIX = "jp.co.daifuku.wms.base.util.patlite.";

    private static final String POST_FIX = "CommandBuilder";

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
     * デフォルトコンストラクタ
     */
    protected PatliteCommandBuilder()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * パトライトコマンド生成クラス生成。<br>
     * 機種名を指定してパトライトコマンド生成クラスをインスタンス化します。<br>
     * 生成されるクラス名は、機種名 + "CommandBuilder" です。<br>
     * 該当するクラスが存在しない場合は、<code>null</code>を返します。
     * @param type 機種名
     * @return パトライトコマンド生成クラス
     */
    public static PatliteCommandBuilder createCommandBuilder(String type)
    {
        // パトライトIDに対応するクラス名を生成します
        StringBuffer className = new StringBuffer(PRE_FIX);
        className.append(type);
        className.append(POST_FIX);

        try
        {
            // 生成したクラス名からインスタンスを生成します
            Object newClass = Class.forName(String.valueOf(className)).newInstance();
            if (newClass instanceof PatliteCommandBuilder)
            {
                // インスタンス化したクラスがPatliteCommandBuilderの
                // 派生クラスであれば、これを返します
                return (PatliteCommandBuilder)newClass;
            }
            return null;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * コマンド生成。<br>
     * パトライト制御より受け取ったコマンドを、対象機種のコマンドに
     * 変換して返します
     * @param command パトライト制御よりのコマンド
     * @return コマンド列(機種依存)
     */
    abstract byte[] buildCommand(PatliteCommand[] command);

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

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
        return "$Id: PatliteCommandBuilder.java 87 2008-10-04 03:07:38Z admin $";
    }
}
