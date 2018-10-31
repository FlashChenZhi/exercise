// $Id: AbstractDataLoaderForJava.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.dbhandler.LoadErrorInfoHandler;
import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * 取り込み処理の１ファイル内における状態などを保持します。<br>
 * たとえば、現在の処理行やスキップしたデータがあるかなどを管理します。<BR>
 * 実際の入力チェックや処理自体はこのクラスを継承したクラスにて行ってください。<BR>
 * <BR>
 * ＜設計について＞<BR>
 * ストアドプロシジャを使用する場合のことを考え、行ごとで共通する処理は
 * AbstractDataLoderではなく本クラスにて実装しています。
 * 取込み処理にてストアドプロシジャを使用する場合は、
 * このクラス以下の処理をストアドプロシジャに置き換えてください。
 * また、ファイルハンドラを使用しての属性チェックは流用し、取込み処理のみストアドプロシジャ化したい場合は
 * 本クラスを使用して行単位にチェックを行い、その後の取込み処理をストアドプロシジャに置き換えてください。
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: okayama $
 */
abstract public class AbstractDataLoaderForJava
        extends AbstractDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * チェックの結果このファイルの処理をどうするか
     */
    public enum RESULT
    {
        /** スキップ */
        SKIP,
        /** 取込可能 */
        LOAD,
        /** 全行ロールバック */
        ROLLBACK;
    }

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
     * コンストラクタ
     * @param exchangeJob 取り込みを行う作業種別
     */
    protected AbstractDataLoaderForJava(String exchangeJob)
    {
        super(exchangeJob);
    }

    /**
     * コンストラクタ
     * @param exchangeJob 取り込みを行う作業種別
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    protected AbstractDataLoaderForJava(String exchangeJob, DfkUserInfo userinfo, Locale locale)
    {
        super(exchangeJob, userinfo, locale);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------
    /**
     * 現在の処理行数
     */
    private int _currentLine = 0;

    /** 処理を行った行数 */
    private int _insertedCount = 0;

    /** 入力チェッククラス */
    private WmsChecker _checker = null;

    /** 現在処理中の行データ */
    private String _currentLineData = null;

    /** LoadErrorInfoハンドラ */
    private LoadErrorInfoHandler _errInfoHandler = null;
    
    /** 対象ファイルのエンティティ */
    private AbstractEntity _entity = null;

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoader2#load(java.io.File)
     */
    @Override
    protected boolean load(File workFile, int max)
            throws CommonException
    {
        _currentLine = 0;
        _insertedCount = 0;
        _checker = new WmsChecker();
        _errInfoHandler = new LoadErrorInfoHandler(getConnectionForError());

        // 取込対象のファイルをオープンする
        _entity = getEntity();
        FileHandler handler = AbstractFileHandler.getInstance(_entity);
        try
        {
            handler.open(workFile.getParent(), workFile.getName());
            handler.read();
            if (handler.length() <= 0)
            {
                // MSG-W0071=レコード数が0件
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_ERROR, "MSG-W0071", "");
                return false;
            }

            // 必要なクラスを初期化する
            init();

            Entity entity = null;
            while (true)
            {
                _currentLineData = "";
                _currentLine++;
                try
                {
                    if ((entity = handler.next()) == null)
                    {
                        _currentLine--;
                        break;
                    }
                }
                // 以下FileHandler.nextメソッド実行時に発生する例外の処理
                catch (RecordFormatException e)
                {
                    // MSG-W0066=データフォーマット不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_ERROR, "MSG-W0066", Integer.toString(e.getErrorOffset()),
                            e.getRecordString());
                    return false;
                }
                // 1行のデータに対する全NG理由が通知されるので、全てのNG内容をログに出力する。
                catch (ValidateException e)
                {
                    if (!canHandlingValidateException(e))
                    {
                        return false;
                    }
                    else
                    {
                        continue;
                    }
                }
                _currentLineData = String.valueOf(entity.getValue(FileHandler.FIELD_ORIGINAL_RECORD));

                // 取込件数が取込最大数を超えていたら取込処理を終了する
                // maxに0以下を指定された場合は件数チェックを行わない
                if (max > 0 && _currentLine > max)
                {
                    // MSG-W0019=取込可能行数超過
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_ERROR, "MSG-W0019", "");
                    return false;
                }

                // 処理を続行するかどうか入力チェックを行う
                switch (check(entity))
                {
                    case ROLLBACK:
                        return false;
                    case SKIP:
                        continue;
                    case LOAD:
                        switch (loadData(entity))
                        {
                            case ROLLBACK:
                                return false;
                            case SKIP:
                                continue;
                            case LOAD:
                                _insertedCount++;
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
        finally
        {
            handler.close();
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoader2#getDataCnt()
     */
    @Override
    protected int getDataCnt()
    {
        return _currentLine;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoader2#getInsertedCnt()
     */
    @Override
    protected int getInsertedCnt()
    {
        return _insertedCount;
    }

    /**
     * 初期化処理を行います。<BR>
     * ファイル行数分同じ処理を行うため、
     * この初期化処理で作成したオブジェクトを再利用してcheckやloadDataを実装してください。<BR>
     *
     * @throws ReadWriteException
     */
    abstract protected void init()
            throws CommonException;

    /**
     * チェック処理を行います <BR>
     * <BR>
     * <b>＜注意事項＞</b><BR>
     * このメソッドでは返り値が <code>RESULT.ROLLBACK</code>になるものを先に実装してください。
     * ROLLBACKになる条件があるにも関わらず、SKIPのチェックを先に行い、
     * そのSKIPのチェックにあてはまってしまった場合、ファイルはROLLBACKされずに
     * 取り込まれてしまいます。
     *
     * @param ent 取込データ
     * @return チェック結果
     * @throws ReadWriteException
     */
    abstract protected RESULT check(Entity ent)
            throws CommonException;

    /**
     * 取込み処理を行います <BR>
     *
     * @param ent 取込みデータ
     */
    abstract protected RESULT loadData(Entity ent)
            throws CommonException;

    /**
     * 統計情報の呼出しを行います。
     *
     * @throws CommonException
     * @throws SQLException
     */
    abstract protected void statics()
            throws CommonException;
    /**
     * 対象Entityの取得
     *
     * @return AbstractEntity 対象Entityの実態
     * @throws CommonException
     */
    abstract protected AbstractEntity getEntity()
            throws CommonException;

    /**
     * エラー時のメッセージを取込エラー情報に登録
     *
     * @param errorLevel エラーレベル
     * @param msg エラー内容を表すメッセージ番号
     * @param itemNo 項目番号
     * @throws CommonException
     */
    protected void insertLoadErrorInfo(String errorLevel, String msg, String itemNo)
            throws CommonException
    {
        insertLoadErrorInfo(errorLevel, msg, itemNo, _currentLineData);
    }

    /**
     * エラー時のメッセージを取込エラー情報に登録
     *
     * @param errorLevel エラーレベル
     * @param msg エラー内容を表すメッセージ番号
     * @param itemNo 項目番号
     * @param lineData 該当行のデータ
     * @throws CommonException
     */
    protected void insertLoadErrorInfo(String errorLevel, String msg, String itemNo, String lineData)
            throws CommonException
    {
        // 取込エラー情報に登録
        LoadErrorInfo errInfo = new LoadErrorInfo();

        errInfo.setStartDate(getStartDate());
        errInfo.setFileName(getSourceFileName());
        errInfo.setJobType(getExchangeJob());
        errInfo.setFileLineNo(_currentLine);
        errInfo.setErrorLevel(errorLevel);
        errInfo.setErrorFlag(msg);
        errInfo.setItemNo(itemNo);
        errInfo.setData(lineData);
        errInfo.setRegistPname(this.getClass().getSimpleName());

        _errInfoHandler.create(errInfo);
    }

    /**
     * 指定項目がyyyyMMddの形式で入力されているかをチェックします。 <BR>
     * チェックした結果処理を中断するか、続行するか、取込みを行うかを返します。<BR>
     *
     * @param field 項目名
     * @param value チェックする値
     * @return <code>RESULT.SKIP</code>:フォーマット不正時、<code>RESULT.LOAD</code>：フォーマットが正しい
     */
    protected RESULT isDay(FieldName field, String value)
            throws CommonException
    {
        // 文字のチェックを行う
        if (!_checker.checkDay(value))
        {
            // MSG-W0066=データフォーマット不正
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0066",
                    _entity.getStoreMetaData().getFieldMetaData(field.getName()).getDescription());
            return RESULT.SKIP;
        }
        return RESULT.LOAD;
    }

    /**
     * 引数で渡されたエンティティの文字列項目に
     * NGParameterText（CommonParam.NG_PARAMETER_TEXT）が含まれているかどうかをチェックします。<BR>
     * チェックした結果処理を中断するか、続行するか、取込みを行うかを返します。<BR>
     *
     * @param ent チェック対象のEntity
     * @return <code>RESULT.SKIP</code>:禁止文字を含む、<code>RESULT.LOAD</code>:禁止文字を含まない。
     */
    protected RESULT hasNGParameterText(AbstractEntity ent)
            throws CommonException
    {
        for (FieldName name : ent.getFieldNames())
        {
            String value = null;
            if (ent.getValue(name) == null)
            {
                continue;
            }
            if (ent.getStoreMetaData().getFieldMetaData(name.getName()).getType() != FieldMetaData.TYPE_STRING)
            {
                continue;
            }
            value = (String)ent.getValue(name);

            if (DisplayText.isPatternMatching(value))
            {
                // MSG-W0024=禁止文字あり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0024",
                        ent.getStoreMetaData().getFieldMetaData(name.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        return RESULT.LOAD;
    }

    /**
     * 先頭文字列が空白かどうかをチェックします。<BR>
     * 空白だった場合はtrue、空白でない場合はfalseを返します。
     *
     * @param field 項目名
     * @param value チェック対象の文字列
     * @return true:先頭文字が空白、false:先頭文字が空白ではない
     */
    protected RESULT isSpaceTopChar(FieldName field, String value)
    {
        return _checker.checkContainNgText(value, 0, field.getName()) ? RESULT.LOAD
                                                                      : RESULT.SKIP;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * ValidateExceptionの中身をチェックしLoadErrorInfoのInsertを行います。
     *
     * @param e ValidateException
     * @return true:警告レベル(処理続行可能) false:エラーレベル(処理続行不可)
     */
    private boolean canHandlingValidateException(ValidateException e)
            throws CommonException
    {
        String errLevel = null;
        String msg = null;
        for (ValidateError err : e.getValidateErrors())
        {
            // LoadErrorInfoの書き込みに使用するパラメータ
            errLevel = null;
            msg = null;

            switch (err.getErrorCode())
            {
                case ValidateError.RETURN_REQUIRE_ERROR:
                    // MSG-W0021=必須項目空白
                    msg = "MSG-W0021";
                    errLevel = SystemDefine.ERROR_LEVEL_WARNING;
                    break;
                case ValidateError.RETURN_TYPE_ERROR:
                    // 指定の型と異なる値がセットされています。
                    // MSG-W0069=データの属性が不正
                    msg = "MSG-W0069";
                    errLevel = SystemDefine.ERROR_LEVEL_ERROR;
                    break;
                case ValidateError.RETURN_LENGTH_ERROR:
                    // MSG-W0067=桁数が不正
                    msg = "MSG-W0067";
                    errLevel = SystemDefine.ERROR_LEVEL_ERROR;
                    break;
                case ValidateError.RETURN_RANGE_ERROR:
                    // 有効範囲外の値が指定されています。
                    // MSG-W0068=データが有効範囲外
                    msg = "MSG-W0068";
                    errLevel = SystemDefine.ERROR_LEVEL_WARNING;
                    break;
                case ValidateError.RETURN_TYPE_CLASS_ERROR:
                    // 文字列の項目に指定属性以外の値がセットされています。
                    // MSG-W0069=データの属性が不正
                    msg = "MSG-W0069";
                    errLevel = SystemDefine.ERROR_LEVEL_ERROR;
                    break;
                case ValidateError.RETURN_SPACE_ERROR:
                    // MSG-W0070=文字の間にスペースあり
                    msg = "MSG-W0070";
                    errLevel = SystemDefine.ERROR_LEVEL_ERROR;
                    break;
                default:
                    break;
            }

            insertLoadErrorInfo(errLevel, msg,
                    _entity.getStoreMetaData().getFieldMetaData(err.getFieldName().getName()).getDescription(), e.getRecordString());
        }
        return errLevel.equals(SystemDefine.ERROR_LEVEL_WARNING) ? true
                                                                 : false;
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
        return "$Id: AbstractDataLoaderForJava.java 7636 2010-03-17 04:11:45Z okayama $";
    }

}
