// $Id: TimeKeeperUtil.java 5527 2009-11-09 08:03:43Z ota $
package jp.co.daifuku.wms.base.util.timekeeper.util;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import jp.co.daifuku.wms.base.util.timekeeper.InstructionFileHandler;
import jp.co.daifuku.wms.base.util.timekeeper.Interval;
import jp.co.daifuku.wms.base.util.timekeeper.JobParam;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleFileHandler;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleJob;

/**
 * 設定ファイルの編集を行うユーティリティクラスです。<br>
 * この処理はオプション指定によって、自動処理に対する指示を行う事が出来ます。
 * この時、ウィンドウは表示されません。
 *
 * @version $Revision: 5527 $, $Date: 2009-11-09 17:03:43 +0900 (月, 09 11 2009) $
 * @author  Softecs
 * @author  Last commit: $Author: ota $
 */

public class TimeKeeperUtil
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>KEY_STRING</code><br>
     * リソースを取得する際に用いるキー文字列<br>
     * モジュール名を指定して下さい。<br>
     */
    private static final String KEY_STRING = "TimeKeeperUtil"; //  @jve:decl-index=0:

    /**
     * <code>OP_RELOAD</code><br>
     * オプション：自動処理に対して再読込み指示を行います。<br>
     */
    public static final String OP_RELOAD = "-reload";

    /**
     * <code>OP_TERM</code><br>
     * オプション：自動処理に対して終了指示を行います。<br>
     */
    public static final String OP_TERM = "-term";

    /**
     * <code>SPLIT_FIELD</code><br>
     * 時間間隔フィールド分割文字列<br>
     */
    private static final String SPLIT_FIELD = " |\t";

    // private final Color _focusColor = new Color(153, 204, 255) ; //  @jve:decl-index=0:

    private final Color _normalColor = Color.WHITE; //  @jve:decl-index=0:

    private final Color _errorColor = new Color(255, 102, 153); //  @jve:decl-index=0:

    private final Icons ICONS = new Icons();

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;
    private static URLClassLoader $loader = null; //  @jve:decl-index=0:

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private static final InstructionFileHandler INST_HANDLER = new InstructionFileHandler(); //  @jve:decl-index=0:

    private static final ScheduleFileHandler SCHE_HANDLER = new ScheduleFileHandler(); //  @jve:decl-index=0:

    private static boolean $commandMode = false;

    private int _editRow = -1;

    private JobParam _editParam = null;

    //------------------------------------------------------------
    // component
    //------------------------------------------------------------

    private JButton jAddButton = null;

    JButton jCancelButton = null;

    private JLabel jF1Label = null;

    private JLabel jF2Label = null;

    private JLabel jF3Label = null;

    private JLabel jF4Label = null;

    private JLabel jF5Label = null;

    private JLabel jF7Label = null;

    JTextField jF1TextField = null;

    JTextField jF2TextField = null;

    JTextField jF3TextField = null;

    JTextField jF4TextField = null;

    JTextField jF5TextField = null;

    JTextField jF6TextField = null;

    private JButton jSaveButton = null;

    private JButton jEndButton = null;

    private JPanel jMinPanel = null;

    JTable jTable = null;

    private static ReadOnlyTableModel _roTableModel = null;

    JButton jDeleteButton = null;

    private JCheckBox jSimpleIntervalCheck = null;

    boolean packFrame = false;

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * コンストラクタで画面を表示するようにします。
     */
    TimeKeeperUtil()
    {
        TimeKeeperMainFrame frame = new TimeKeeperMainFrame();
        // validate() はサイズを調整する
        // pack() は有効なサイズ情報をレイアウトなどから取得する
        if (packFrame)
        {
            frame.pack();
        }
        else
        {
            frame.validate();
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * メイン処理<BR>
     * 起動アーギュメントによって、画面の表示を制御します。<BR>
     * @param args
     */
    public static void main(final String[] args)
    {
        // 起動アーギュメント(オプション)をチェックします。
        if ((0 != args.length) && (1 != args.length))
        {
            // 起動アーギュメント(オプション)の数が間違っていた場合
            displayUsage();
        }
        else
        {
            // 起動アーギュメント(オプション)を何も指定しなかった場合は
            // 画面を表示して、インタラクティブな処理を行います。
            if (0 == args.length)
            {
                /* TODO 新しく作成したフレームクラスを呼び出すように修正
                 * 将来的には不要ロジックの削除を行う事！
                $commandMode = false;
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        final TimeKeeperUtil application = new TimeKeeperUtil();

                        // ウィンドウを中央に配置します
                        final JFrame frame = application.getJFrame();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                });
                */
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        new TimeKeeperUtil();
                    }
                });
            }
            else
            {
                // コマンド動作モードを設定します。
                $commandMode = true;

                // 再読込み指示オプションが指定されていた場合
                if (OP_RELOAD.equals(args[0]))
                {
                    fireReload();
                }

                // 終了指示オプションが指定されていた場合
                else if (OP_TERM.equals(args[0]))
                {
                    fireTerminate();
                }

                else
                {
                    // 指定オプションが間違っていた場合
                    displayUsage();
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
    /**
     * 再読込指示。<ｂｒ>
     * 自動処理へ再読込指示を行います。
     */
    private static void fireReload()
    {
        if (!$commandMode)
        {
            if ((null != _roTableModel) && _roTableModel.isChange())
            {
                final String title = Resource.getString("YesNoDialog.011");
                final String message = Resource.getString("YesNoDialog.021");
                final int button = YesNoDialog.BUTTON_YES | YesNoDialog.BUTTON_NO;
                final int focus = YesNoDialog.BUTTON_NO;
                final int ret = confirmWithDialog(title, message, button, focus);
                switch (ret)
                {
                    case YesNoDialog.BUTTON_YES:
                        break;

                    case YesNoDialog.BUTTON_NO:
                    case YesNoDialog.BUTTON_CANCEL:
                        return;
                }
            }
        }
        INST_HANDLER.putReload();
    }

    /**
     * 終了指示。<ｂｒ>
     * 自動処理へ終了指示を行います。
     */
    private static void fireTerminate()
    {
        if (!$commandMode)
        {
            final String title = Resource.getString("YesNoDialog.012");
            final String message = Resource.getString("YesNoDialog.022");
            final int button = YesNoDialog.BUTTON_YES | YesNoDialog.BUTTON_NO;
            final int focus = YesNoDialog.BUTTON_NO;
            final int ret = confirmWithDialog(title, message, button, focus);
            switch (ret)
            {
                case YesNoDialog.BUTTON_YES:
                    break;

                case YesNoDialog.BUTTON_NO:
                case YesNoDialog.BUTTON_CANCEL:
                    return;
            }
        }

        INST_HANDLER.putTerminate();
    }

    /**
     * 入力パネル初期化。<ｂｒ>
     * データ入力パネルを初期状態にします。
     */
    void inputPanelClear()
    {
        // 編集中データがある場合は、元に戻します。
        if (0 <= _editRow)
        {
            _roTableModel.insertRow(_editRow, _editParam);
        }

        // 各フィールドを初期化します。
        getJSimpleIntervalCheck().setSelected(false);
        change_actionPerformed();

        getJF1TextField().setText("");
        getJF2TextField().setText("");
        getJF3TextField().setText("");
        getJF4TextField().setText("");
        getJF5TextField().setText("");
        getJF6TextField().setText("");

        getJF1TextField().setBackground(_normalColor);
        getJF2TextField().setBackground(_normalColor);
        getJF3TextField().setBackground(_normalColor);
        getJF4TextField().setBackground(_normalColor);
        getJF5TextField().setBackground(_normalColor);
        getJF6TextField().setBackground(_normalColor);

        // 各コンポーネントを初期化します。
        getJAddButton().setText(Resource.getString(KEY_STRING + ".106"));
        _editRow = -1;
        getJAddButton().setEnabled(false);
        getJDeleteButton().setEnabled(false);
        getJCancelButton().setEnabled(false);

        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.focusPreviousComponent(getJF2TextField());
    }

    /**
     * データ削除。<br>
     * 入力パネルに読み込んだデータを削除します。
     */
    void deleteData()
    {
        if (0 <= _editRow)
        {
            _editRow = -1;
            _roTableModel.setChange(true);
        }

        // 入力パネルに読み込んだ段階で、モデルからはデータを削除
        // しているため、入力パネルの初期化を行うのみでよい。
        _roTableModel.setChange(true);
        getJSaveButton().setIcon(ICONS.NEED_SAVE);
        inputPanelClear();
    }

    /**
     * データ追加。<br>
     * 入力パネルのデータを追加します。
     */
    void addData()
    {
        // ここでデフォルト値を設定します。
        //  inputDefault() ;

        // データ登録時にエラーがあった場合は、登録処理を行いません。
        final boolean ret = inputCheck();
        if (!ret)
        {
            return;
        }

        // 入力パネルのデータを取得します。
        getJSimpleIntervalCheck().setSelected(false);

        final String[] param = new String[6];
        param[0] = getJF1TextField().getText();
        param[1] = getJF2TextField().getText();
        param[2] = getJF3TextField().getText();
        param[3] = getJF4TextField().getText();
        param[4] = getJF5TextField().getText();
        param[5] = getJF6TextField().getText();

        // 更新データの場合は、元の位置に挿入します。
        if (0 <= _editRow)
        {
            _roTableModel.insertRow(_editRow, param);
            _editRow = -1;
        }

        // 新規データの場合は、最後に追加します。
        else
        {
            _roTableModel.addRow(param);
        }
        _roTableModel.setChange(true);
        getJSaveButton().setIcon(ICONS.NEED_SAVE);
        getJAddButton().setEnabled(false);
        inputPanelClear();
    }

    /**
     * デフォルト設定入力。<br>
     * デフォルトの設定値を入力します。
     */
    /*
     private void inputDefault()
     {
     String data = null ;
     String defaultTiming = Resource.getString(KEY_STRING + ".007") ;
     String defaultClass = Resource.getString(KEY_STRING + ".008") ;

     // 分
     data = jF1TextField.getText() ;
     if (0 == data.length())
     {
     jF1TextField.setText(defaultTiming) ;
     }

     // 時
     data = jF2TextField.getText() ;
     if (0 == data.length())
     {
     jF1TextField.setText(defaultTiming) ;
     }

     // 日
     data = jF3TextField.getText() ;
     if (0 == data.length())
     {
     jF1TextField.setText(defaultTiming) ;
     }

     // 月
     data = jF4TextField.getText() ;
     if (0 == data.length())
     {
     jF1TextField.setText(defaultTiming) ;
     }

     // 曜日
     data = jF5TextField.getText() ;
     if (0 == data.length())
     {
     jF1TextField.setText(defaultTiming) ;
     }

     // 実行クラス
     data = jF6TextField.getText() ;
     if (0 == data.length())
     {
     jF1TextField.setText(defaultClass) ;
     }
     }
     */

    /**
     * 入力データチェック。<br>
     * 入力されているデータをチェックして、結果を返します。
     * @return チェックOKの場合は<code>true</code>を返します。
     */
    boolean inputCheck()
    {
        change_actionPerformed();
        // ここで取り消しボタンは有効にします。
        getJCancelButton().setEnabled(true);

        // 入力パネルのデータを取得します。
        final String[] param = new String[6];
        param[0] = getJF1TextField().getText();
        param[1] = getJF2TextField().getText();
        param[2] = getJF3TextField().getText();
        param[3] = getJF4TextField().getText();
        param[4] = getJF5TextField().getText();
        param[5] = getJF6TextField().getText();
        final String[] fields = param[5].split(SPLIT_FIELD);
        final String className = fields[0];

        getJF1TextField().setBackground(_normalColor);
        getJF2TextField().setBackground(_normalColor);
        getJF3TextField().setBackground(_normalColor);
        getJF4TextField().setBackground(_normalColor);
        getJF5TextField().setBackground(_normalColor);
        getJF6TextField().setBackground(_normalColor);

        boolean check = true;
        Component comp = null;

        // 分フィールドチェック
        if (0 == param[0].length())
        {
            check = false;
            getJF1TextField().setBackground(_errorColor);
            comp = getJEndButton();
        }

        // 時～曜日フィールドチェック
        if ((0 != param[1].length()) || (0 != param[2].length()) || (0 != param[3].length())
                || (0 != param[4].length()))
        {
            if (0 == param[1].length())
            {
                check = false;
                getJF2TextField().setBackground(_errorColor);
                if (null == comp)
                {
                    comp = getJF1TextField();
                }
            }
            if (0 == param[2].length())
            {
                check = false;
                getJF3TextField().setBackground(_errorColor);
                if (null == comp)
                {
                    comp = getJF2TextField();
                }
            }
            if (0 == param[3].length())
            {
                check = false;
                getJF4TextField().setBackground(_errorColor);
                if (null == comp)
                {
                    comp = getJF3TextField();
                }
            }
            if (0 == param[4].length())
            {
                check = false;
                getJF5TextField().setBackground(_errorColor);
                if (null == comp)
                {
                    comp = getJF3TextField();
                }
            }
        }

        // 入力が正しく行われていた場合は、時刻間隔文字列として正しいかチェックします。
        if (check)
        {
            final StringBuffer intervalStr = new StringBuffer("");
            for (int i = 0; i < 5; i++)
            {
                if (0 < param[i].length())
                {
                    if (0 != i)
                    {
                        intervalStr.append(" ");
                    }
                    intervalStr.append(param[i]);
                }
            }
            try
            {
                // try to create
                new Interval(intervalStr.toString());
            }
            catch (final Exception e)
            {
                check = false;
                getJF1TextField().setBackground(_errorColor);
                getJF2TextField().setBackground(_errorColor);
                getJF3TextField().setBackground(_errorColor);
                getJF4TextField().setBackground(_errorColor);
                getJF5TextField().setBackground(_errorColor);
                if (null == comp)
                {
                    comp = getJEndButton();
                }
            }
        }

        // 実行クラス名チェック
        if (0 == className.length())
        {
            check = false;
            getJF6TextField().setBackground(_errorColor);
            if (null == comp)
            {
                comp = getJF5TextField();
            }
        }
        else
        {
            if (!isScheduleJob(className))
            {
                check = false;
                getJF6TextField().setBackground(_errorColor);
                if (null == comp)
                {
                    comp = getJF5TextField();
                }
            }
        }

        // フォーカスするコンポーネントが設定されている場合、フォーカス移動します。
        if (null != comp)
        {
            final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            fm.focusNextComponent(comp);
        }

        // チェックOKになった時、追加ボタンを有効にします。
        if (check)
        {
            getJAddButton().setEnabled(true);
        }
        return check;
    }

    /**
     * 指示ファイル読込。<br>
     * 指示ファイルの読み込みを行います。
     */
    void loadFile()
    {
        // 設定ファイルの変更が行われていれば、確認を行います。
        if (_roTableModel.isChange())
        {
            final String title = Resource.getString("YesNoDialog.013");
            final String message = Resource.getString("YesNoDialog.023");
            final int button = YesNoDialog.BUTTON_YES | YesNoDialog.BUTTON_NO;
            final int focus = YesNoDialog.BUTTON_NO;
            final int ret = confirmWithDialog(title, message, button, focus);
            switch (ret)
            {
                case YesNoDialog.BUTTON_YES:
                    break;

                case YesNoDialog.BUTTON_NO:
                case YesNoDialog.BUTTON_CANCEL:
                    return;
            }
        }

        // 設定ファイルを読み込みます。
        _roTableModel.clearAll();
        _roTableModel.addRow(SCHE_HANDLER.getSchedules());
        getJSaveButton().setIcon(ICONS.SAVE);
    }

    /**
     * 指示ファイル保存<BR>
     * 指示ファイルの保存を行います。<BR>
     */
    void saveFile()
    {
        SCHE_HANDLER.replaceAll(_roTableModel.getJobParams());
        _roTableModel.clearAll();
        _roTableModel.addRow(SCHE_HANDLER.getSchedules());
        getJSaveButton().setIcon(ICONS.SAVE);
    }

    /**
     * 終了処理。<br>
     * 終了メニュー、終了ボタンを選択した時の処理です。
     */
    void exit_actionPerformed()
    {
        if (_roTableModel.isChange())
        {
            final String title = Resource.getString("YesNoDialog.010");
            final String message = Resource.getString("YesNoDialog.020");
            final int button = YesNoDialog.BUTTON_YES | YesNoDialog.BUTTON_NO;
            final int focus = YesNoDialog.BUTTON_NO;
            final int ret = confirmWithDialog(title, message, button, focus);
            switch (ret)
            {
                case YesNoDialog.BUTTON_YES:
                    break;

                case YesNoDialog.BUTTON_NO:
                case YesNoDialog.BUTTON_CANCEL:
                    return;
            }
        }
        System.exit(0);
    }

    /**
     * 入力パネルの切り替えを行います。
     */
    void change_actionPerformed()
    {
        final boolean isSimple = getJSimpleIntervalCheck().isSelected();
        if (isSimple)
        {
            jF1Label.setText(Resource.getString(KEY_STRING + ".099"));
            jF2Label.setText(Resource.getString(KEY_STRING + ".034"));
            jF3Label.setText(Resource.getString(KEY_STRING + ".034"));
            jF4Label.setText(Resource.getString(KEY_STRING + ".034"));
            jF5Label.setText(Resource.getString(KEY_STRING + ".034"));
            jF7Label.setText(Resource.getString(KEY_STRING + ".034"));

            getJMinPanel().setToolTipText(null);
            getJF2TextField().setText("");
            getJF3TextField().setText("");
            getJF4TextField().setText("");
            getJF5TextField().setText("");
            /*
            getJF2TextField().setVisible(false);
            getJF3TextField().setVisible(false);
            getJF4TextField().setVisible(false);
            getJF5TextField().setVisible(false);
            */
            getJF2TextField().setEnabled(false);
            getJF3TextField().setEnabled(false);
            getJF4TextField().setEnabled(false);
            getJF5TextField().setEnabled(false);
        }
        else
        {
            jF1Label.setText(Resource.getString(KEY_STRING + ".100"));
            jF2Label.setText(Resource.getString(KEY_STRING + ".101"));
            jF3Label.setText(Resource.getString(KEY_STRING + ".102"));
            jF4Label.setText(Resource.getString(KEY_STRING + ".103"));
            jF5Label.setText(Resource.getString(KEY_STRING + ".104"));
            jF7Label.setText(Resource.getString(KEY_STRING + ".034"));

            getJMinPanel().setToolTipText(Resource.getString(KEY_STRING + ".002"));
            /*
            getJF2TextField().setVisible(true);
            getJF3TextField().setVisible(true);
            getJF4TextField().setVisible(true);
            getJF5TextField().setVisible(true);
            */
            getJF2TextField().setEnabled(true);
            getJF3TextField().setEnabled(true);
            getJF4TextField().setEnabled(true);
            getJF5TextField().setEnabled(true);
        }
    }

    /**
     * This method initializes jAddButton 
     *  
     * @return javax.swing.JButton 
     */
    private JButton getJAddButton()
    {
        if (jAddButton == null)
        {
            jAddButton = new JButton();
            jAddButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jAddButton.setText(Resource.getString(KEY_STRING + ".106"));
            jAddButton.setEnabled(false);
            jAddButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    // 入力パネルのデータを追加します。
                    addData();
                }
            });
            jAddButton.addFocusListener(new java.awt.event.FocusAdapter()
            {
                @Override
                public void focusLost(final java.awt.event.FocusEvent e)
                {
                    if (!jDeleteButton.isEnabled())
                    {
                        if (!jCancelButton.isEnabled())
                        {
                            final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            fm.focusNextComponent(jTable);
                        }
                        else
                        {
                            final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            fm.focusNextComponent(jDeleteButton);
                        }
                    }
                }
            });
        }
        return jAddButton;
    }

    /**
     * This method initializes jCancelButton 
     *  
     * @return javax.swing.JButton 
     */
    private JButton getJCancelButton()
    {
        if (jCancelButton == null)
        {
            jCancelButton = new JButton();
            jCancelButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jCancelButton.setText(Resource.getString(KEY_STRING + ".107"));
            jCancelButton.setEnabled(false);
            jCancelButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    // キャンセルボタンの押下で、入力パネルを初期化します。
                    inputPanelClear();
                }
            });
        }
        return jCancelButton;
    }

    /**
     * This method initializes jDeleteButton 
     *  
     * @return javax.swing.JButton 
     */
    private JButton getJDeleteButton()
    {
        if (jDeleteButton == null)
        {
            jDeleteButton = new JButton();
            jDeleteButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jDeleteButton.setText(Resource.getString(KEY_STRING + ".109"));
            jDeleteButton.setEnabled(false);
            jDeleteButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    // 入力パネルのデータを削除します。
                    deleteData();
                }
            });
        }
        return jDeleteButton;
    }

    /**
     * This method initializes jF1TextField 
     *  
     * @return javax.swing.JTextField 
     */
    private JTextField getJF1TextField()
    {
        if (jF1TextField == null)
        {
            jF1TextField = new JTextField();
            jF1TextField.setColumns(3);
            /*
             jF1TextField.addFocusListener(new java.awt.event.FocusAdapter()
             {
             public void focusGained(java.awt.event.FocusEvent e)
             {
             ((JTextField)e.getSource()).setBackground(_focusColor) ;
             jF1TextField.selectAll() ;
             }

             public void focusLost(java.awt.event.FocusEvent e)
             {
             boolean ret = inputCheck() ;
             if (ret)
             {
             ((JTextField)e.getSource()).setBackground(_normalColor) ;
             }
             }
             }) ;
             */
            jF1TextField.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final boolean ret = inputCheck();
                    if (ret)
                    {
                        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        fm.focusNextComponent(jF1TextField);
                    }
                }
            });
        }
        return jF1TextField;
    }

    /**
     * This method initializes jF2TextField 
     *  
     * @return javax.swing.JTextField 
     */
    private JTextField getJF2TextField()
    {
        if (jF2TextField == null)
        {
            jF2TextField = new JTextField();
            jF2TextField.setColumns(3);
            /*
             jF2TextField.addFocusListener(new java.awt.event.FocusAdapter()
             {
             public void focusGained(java.awt.event.FocusEvent e)
             {
             ((JTextField)e.getSource()).setBackground(_focusColor) ;
             jF2TextField.selectAll() ;
             }

             public void focusLost(java.awt.event.FocusEvent e)
             {
             boolean ret = inputCheck() ;
             if (ret)
             {
             ((JTextField)e.getSource()).setBackground(_normalColor) ;
             }
             }
             }) ;
             */
            jF2TextField.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final boolean ret = inputCheck();
                    if (ret)
                    {
                        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        fm.focusNextComponent(jF2TextField);
                    }
                }
            });
        }
        return jF2TextField;
    }

    /**
     * This method initializes jF3TextField 
     *  
     * @return javax.swing.JTextField 
     */
    private JTextField getJF3TextField()
    {
        if (jF3TextField == null)
        {
            jF3TextField = new JTextField();
            jF3TextField.setColumns(3);
            /*
             jF3TextField.addFocusListener(new java.awt.event.FocusAdapter()
             {
             public void focusGained(java.awt.event.FocusEvent e)
             {
             ((JTextField)e.getSource()).setBackground(_focusColor) ;
             jF3TextField.selectAll() ;
             }

             public void focusLost(java.awt.event.FocusEvent e)
             {
             boolean ret = inputCheck() ;
             if (ret)
             {
             ((JTextField)e.getSource()).setBackground(_normalColor) ;
             }
             }
             }) ;
             */
            jF3TextField.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final boolean ret = inputCheck();
                    if (ret)
                    {
                        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        fm.focusNextComponent(jF3TextField);
                    }
                }
            });
        }
        return jF3TextField;
    }

    /**
     * This method initializes jF4TextField 
     *  
     * @return javax.swing.JTextField 
     */
    private JTextField getJF4TextField()
    {
        if (jF4TextField == null)
        {
            jF4TextField = new JTextField();
            jF4TextField.setColumns(3);
            /*
             jF4TextField.addFocusListener(new java.awt.event.FocusAdapter()
             {
             public void focusGained(java.awt.event.FocusEvent e)
             {
             ((JTextField)e.getSource()).setBackground(_focusColor) ;
             jF4TextField.selectAll() ;
             }

             public void focusLost(java.awt.event.FocusEvent e)
             {
             boolean ret = inputCheck() ;
             if (ret)
             {
             ((JTextField)e.getSource()).setBackground(_normalColor) ;
             }
             }
             }) ;
             */
            jF4TextField.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final boolean ret = inputCheck();
                    if (ret)
                    {
                        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        fm.focusNextComponent(jF4TextField);
                    }
                }
            });
        }
        return jF4TextField;
    }

    /**
     * This method initializes jF5TextField 
     *  
     * @return javax.swing.JTextField 
     */
    private JTextField getJF5TextField()
    {
        if (jF5TextField == null)
        {
            jF5TextField = new JTextField();
            jF5TextField.setColumns(3);
            /*
             jF5TextField.addFocusListener(new java.awt.event.FocusAdapter()
             {
             public void focusGained(java.awt.event.FocusEvent e)
             {
             ((JTextField)e.getSource()).setBackground(_focusColor) ;
             jF5TextField.selectAll() ;
             }

             public void focusLost(java.awt.event.FocusEvent e)
             {
             boolean ret = inputCheck() ;
             if (ret)
             {
             ((JTextField)e.getSource()).setBackground(_normalColor) ;
             }
             }
             }) ;
             */
            jF5TextField.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final boolean ret = inputCheck();
                    if (ret)
                    {
                        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        fm.focusNextComponent(jF5TextField);
                    }
                }
            });
        }
        return jF5TextField;
    }

    /**
     * This method initializes jF6TextField 
     *  
     * @return javax.swing.JTextField 
     */
    private JTextField getJF6TextField()
    {
        if (jF6TextField == null)
        {
            jF6TextField = new JTextField();
            jF6TextField.setColumns(15);
            /*
             jF6TextField.addFocusListener(new java.awt.event.FocusAdapter()
             {
             public void focusGained(java.awt.event.FocusEvent e)
             {
             ((JTextField)e.getSource()).setBackground(_focusColor) ;
             jF6TextField.selectAll() ;
             }

             public void focusLost(java.awt.event.FocusEvent e)
             {
             boolean ret = inputCheck() ;
             if (ret)
             {
             ((JTextField)e.getSource()).setBackground(_normalColor) ;
             }
             }
             }) ;
             */
            jF6TextField.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final boolean ret = inputCheck();
                    if (ret)
                    {
                        final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        fm.focusNextComponent(jF6TextField);
                    }
                }
            });
        }
        return jF6TextField;
    }

    /**
     * This method initializes jSaveButton 
     *  
     * @return javax.swing.JButton 
     */
    private JButton getJSaveButton()
    {
        if (jSaveButton == null)
        {
            jSaveButton = new JButton();
            jSaveButton.setIcon(ICONS.SAVE);
            jSaveButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jSaveButton.setText(Resource.getString(KEY_STRING + ".202"));
            jSaveButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    saveFile();
                }
            });
        }
        return jSaveButton;
    }

    /**
     * This method initializes jEndButton 
     *  
     * @return javax.swing.JButton 
     */
    private JButton getJEndButton()
    {
        if (jEndButton == null)
        {
            jEndButton = new JButton();
            jEndButton.setIcon(ICONS.EXIT);
            jEndButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jEndButton.setText(Resource.getString(KEY_STRING + ".203"));
            jEndButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    exit_actionPerformed();
                }
            });
        }
        return jEndButton;
    }

    /**
     * This method initializes jSecFormatCheck
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJSimpleIntervalCheck()
    {
        if (jSimpleIntervalCheck == null)
        {
            jSimpleIntervalCheck = new JCheckBox();
            jSimpleIntervalCheck.setToolTipText(Resource.getString(KEY_STRING + ".035"));
            jSimpleIntervalCheck.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    change_actionPerformed();
                }
            });
        }
        return jSimpleIntervalCheck;
    }

    /**
     * This method initializes jMinPanel 
     *  
     * @return javax.swing.JPanel 
     */
    private JPanel getJMinPanel()
    {
        if (jMinPanel == null)
        {
            final BorderLayout borderLayout = new BorderLayout();
            borderLayout.setHgap(5);
            borderLayout.setVgap(5);
            jMinPanel = new JPanel();
            jMinPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            jMinPanel.setToolTipText(Resource.getString(KEY_STRING + ".002"));
            jMinPanel.setLayout(borderLayout);
            jMinPanel.add(jF1Label, BorderLayout.NORTH);
            jMinPanel.add(getJF1TextField(), BorderLayout.CENTER);
        }
        return jMinPanel;
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
        return "$Id: TimeKeeperUtil.java 5527 2009-11-09 08:03:43Z ota $";
    }

    /**
     * 本アプリケーションの起動アーギュメント(オプション)の説明表示を行います。
     */
    private static void displayUsage()
    {
        System.out.println("Usage.");
        System.out.println("\t" + KEY_STRING);
        System.out.println("\t" + KEY_STRING + " " + OP_RELOAD);
        System.out.println("\t" + KEY_STRING + " " + OP_TERM);
    }

    /**
     * 確認ダイアログを表示し、押下されたボタンを通知します。
     * @param title ダイアログのタイトル
     * @param message 表示メッセージ
     * @param button 表示ボタン
     * @param focus デフォルト(フォーカス)ボタン
     * @return 押下されたボタン
     */
    private static int confirmWithDialog(final String title, final String message, final int button, final int focus)
    {
        final YesNoDialog dialog = new YesNoDialog(null, title, true);
        dialog.show(message, button, focus);
        final int ret = dialog.getAnswer();
        return ret;
    }

    /**
     * 指定されたクラスが<code>SCheduleJob</code>インターフェイスを
     * 実装しているかチェックします。
     * @param className クラス名
     * @return <code>SCheduleJob</code>インターフェイスを実装していれば
     *    <code>ture</code>を返します。
     */
    private boolean isScheduleJob(final String className)
    {
        if (null == $loader)
        {
            final ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            $loader = new URLClassLoader(listLibFileURL(), systemLoader);
            Thread.currentThread().setContextClassLoader($loader);
        }

        try
        {
            final Class<?> tmpClass = $loader.loadClass(className);
            if (ScheduleJob.class.isAssignableFrom(tmpClass))
            {
                return true;
            }
        }
        catch (final ClassNotFoundException e)
        {
            //   e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * クラスパスに含まれるjarファイルのURLを取得します。
     * @return URL(配列)
     */
    private URL[] listLibFileURL()
    {
        // クラスパスを取得します。
        final String classPath = System.getProperty("java.class.path");
        if ((null == classPath) || (0 == classPath.length()))
        {
            return null;
        }

        // 取得したクラスパスより、検索対象となるjarファイルを取り出します。
        final String sep = System.getProperty("path.separator");
        final String[] paths = classPath.split(sep);
        final List<File> jarList = new ArrayList<File>();
        addList(jarList, paths);

        // 拡張ディレクトリに含まれるjarファイルも検索対象とします。
        final String extdirs = System.getProperty("java.ext.dirs");
        final String[] extPaths = extdirs.split(sep);
        addList(jarList, extPaths);

        // カレントディレクトリに含まれるjarファイルを検索対象とします。
        final String[] current = new String[1];
        current[0] = ".";
        addList(jarList, current);

        // 検索対象のjarファイルをURLに変換します。
        final URL[] jarURLs = new URL[jarList.size()];
        for (int i = 0; i < jarList.size(); i++)
        {
            final File jarFile = jarList.get(i);
            try
            {
                jarURLs[i] = jarFile.toURI().toURL();
            }
            catch (final MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
        return jarURLs;
    }

    /**
     * 指定されたリストオブジェクトに、検索対象ディレクトリより検索した
     * jarライブラリを追加します。
     * @param list リストオブジェクト
     * @param dirs 検索対象ディレクトリ
     */
    private void addList(final List<File> list, final String[] dirs)
    {
        for (final String dir : dirs)
        {
            final File libpath = new File(dir);
            if (libpath.isDirectory())
            {
                final File[] libs = libpath.listFiles(new JarFilenameFilter());
                for (final File lib : libs)
                {
                    list.add(lib);
                }
            }
            else
            {
                list.add(libpath);
            }
        }
    }

    /**
     * 拡張子が.jarで終わっているものをリストするためのフィルタです。
     */
    class JarFilenameFilter
            implements FilenameFilter
    {
        private static final String JAR_EXT = ".jar";

        public boolean accept(final File dir, final String name)
        {
            if (name == null)
            {
                return false;
            }
            final String fname = name.toLowerCase();
            return fname.endsWith(JAR_EXT);
        }

        protected String getName(final File jarfile)
        {
            final String fname = jarfile.getName();
            final int est = fname.lastIndexOf(JAR_EXT);
            return fname.substring(0, est);
        }
    }
}
