// $Id: InstructionFileHandler.java 6632 2009-12-24 09:49:38Z okayama $
package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;

/**
 * 自動処理で使用する指示ファイルのハンドリングを行うクラスです。<br>
 * 
 * @version $Revision: 6632 $, $Date: 2009-12-24 18:49:38 +0900 (木, 24 12 2009) $
 * @author Softecs
 * @author Last commit: $Author: okayama $
 */

public class InstructionFileHandler
        extends AbstractTKHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /**  <code>TYPE_UNKNOWN</code><br> 指示タイプ：未知の指示 */
    public static final int TYPE_UNKNOWN = 0;

    /**  <code>TYPE_RELOAD</code><br> 指示タイプ：再読込み指示 */
    public static final int TYPE_RELOAD = 1;

    /**  <code>TYPE_TERMINATE</code><br> 指示タイプ：終了指示 */
    public static final int TYPE_TERMINATE = 2;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /** mutex object */
    protected static final Object $mutex = new Object();

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private File _instructionFile = null;

    private HashMap<String, String> _instructions;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ.<br>
     */
    public InstructionFileHandler()
    {
        try
        {
            _instructionFile = TimeKeeperDefines.getInstructionFile();

            if (!_instructionFile.exists())
            {
                resetInstruction();
            }

            // 有効な指示を保持します。
            _instructions = new HashMap<String, String>();
            _instructions.put(TimeKeeperDefines.INSTRUCTION_RELOAD, String.valueOf(TYPE_RELOAD));
            _instructions.put(TimeKeeperDefines.INSTRUCTION_TERMINATE, String.valueOf(TYPE_TERMINATE));
        }
        catch (final ReadWriteException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * コンストラクタ.<br>
     * 
     * @param iniFilePath TimeKeeper.iniファイルパス
     */
    public InstructionFileHandler(String iniFilePath)
    {
        try
        {
            _instructionFile = TimeKeeperDefines.getInstructionFile(iniFilePath);

            if (!_instructionFile.exists())
            {
                resetInstruction();
            }

            // 有効な指示を保持します。
            _instructions = new HashMap<String, String>();
            _instructions.put(TimeKeeperDefines.INSTRUCTION_RELOAD, String.valueOf(TYPE_RELOAD));
            _instructions.put(TimeKeeperDefines.INSTRUCTION_TERMINATE, String.valueOf(TYPE_TERMINATE));
        }
        catch (final ReadWriteException e)
        {
            throw new RuntimeException(e);
        }
    }
    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 指示ファイルに有効な指示が書き込まれているかチェックします。<br>
     * 
     * @return 有効な指示が書き込まれていれば<code>true</code>を返します。
     */
    public boolean checkInstruction()
    {
        final String instruction = getInstruction();
        return _instructions.containsKey(instruction);
    }

    /**
     * 指示ファイルに格納されている指示を読み込みます。<br>
     * 
     * @return 指示<br>
     */
    public String getInstruction()
    {
        final String inst = read();
        return inst;
    }

    /**
     * 指示ファイルに書き込まれている指示タイプを取得します。<br>
     * 
     * @return 指示タイプ<br>
     */
    public int getInstructionType()
    {
        // 無効な指示が書き込まれていた場合は、TYPE_UNKNOWNを返します。
        if (!checkInstruction())
        {
            return TYPE_UNKNOWN;
        }

        // 指示タイプを取得して返します。
        final String inst = getInstruction();
        final String value = _instructions.get(inst);
        return Integer.parseInt(value);
    }

    /**
     * 指示ファイルの内容を初期化します。<br>
     */
    public void resetInstruction()
    {
        write("");
    }

    /**
     * 指示ファイルに指示を書き込みます。<br>
     * 
     * @param inst 指示<br>
     * 
     * @return 書込みが成功した場合は<code>true</code>を返します。<br>
     */
    public boolean putInstruction(final String inst)
    {
        final boolean ret = write(inst);
        return ret;
    }

    /**
     * 指示ファイルに再読込み指示を書き込みます。<br>
     * 
     * @return 書込みが成功した場合は<code>true</code>を返します。<br>
     */
    public boolean putReload()
    {
        return putInstruction(TimeKeeperDefines.INSTRUCTION_RELOAD);
    }

    /**
     * 指示ファイルに終了指示を書き込みます。<br>
     * 
     * @return 書込みが成功した場合は<code>true</code>を返します。<br>
     */
    public boolean putTerminate()
    {
        return putInstruction(TimeKeeperDefines.INSTRUCTION_TERMINATE);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * プロパティ定義されているファイル名を取得します。<br>
     * 
     * @return ファイル名<br>
     */
    public String getInstructionFileName()
    {
        return _instructionFile.getName();
    }

    /**
     * プロパティ定義されているディレクトリ名を取得します。<br>
     * 
     * @return ディレクトリ名<br>
     */
    public String getTimeKeeperDir()
    {
        return _instructionFile.getParent();
    }

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
     * パラメータファイルにより指定されている指示ファイルを読み込んで その内容を返します。（1行目のみ有効とします。）<br>
     * 指示ファイルの読込みに失敗した場合は、<code>null</code>を返します。<br>
     * 
     * @return 読み込んだ指示内容<br>
     */
    private String read()
    {
        RandomAccessFile raf = null; // close in finally block
        BufferedReader reader = null; // close in finally block
        FileLock lock = null;
        try
        {
            synchronized ($mutex)
            {
                raf = new RandomAccessFile(_instructionFile, "rw");
                final FileInputStream is = new FileInputStream(raf.getFD());
                lock = raf.getChannel().lock();
                // 指示ファイルの内容を読み込みます。
                reader = new BufferedReader(new InputStreamReader(is), 1024);
                final String rec = reader.readLine();

                return rec;
            }
        }
        catch (final IOException e)
        {
            // ファイルの入出力エラーが発生しました。{0}
            final String[] ins = {
                _instructionFile.getPath(),
            };
            RmiMsgLogClient.write(6006020, getClass().getName(), ins);
            return null;
        }
        finally
        {
            release(lock);
            close(reader, raf);
        }
    }

    /**
     * パラメータに指定された指示を指示ファイルに書込みます。<br>
     * 
     * @param inst 指示<br>
     * 
     * @return 書込みが成功した場合は<code>true</code>を返します。<br>
     */
    private boolean write(final String inst)
    {
        BufferedWriter writer = null; // close in finally block
        FileLock lock = null;
        try
        {
            synchronized ($mutex)
            {
                // 指示ファイルの内容を読み込みます。
                final FileOutputStream fos = new FileOutputStream(_instructionFile, false);
                lock = fos.getChannel().lock();
                writer = new BufferedWriter(new OutputStreamWriter(fos), 1024);
                writer.write(inst);
                writer.newLine();
                writer.flush();

                return true;
            }
        }
        catch (final IOException e)
        {
            // ファイルの入出力エラーが発生しました。{0}
            final String[] ins = {
                _instructionFile.getPath(),
            };
            RmiMsgLogClient.write(6006020, getClass().getName(), ins);
            return false;
        }
        finally
        {
            release(lock);
            close(writer);
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<br>
     * 
     * @return リビジョン文字列。<br>
     */
    public static String getVersion()
    {
        return "$Id: InstructionFileHandler.java 6632 2009-12-24 09:49:38Z okayama $";
    }
}
