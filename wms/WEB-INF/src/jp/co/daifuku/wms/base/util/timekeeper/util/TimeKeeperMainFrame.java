// $Id: TimeKeeperMainFrame.java 1208 2009-03-19 08:25:36Z mshimizu@softecs.jp $
// $LastChangedRevision: 1208 $

package jp.co.daifuku.wms.base.util.timekeeper.util;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import jp.co.daifuku.wms.base.util.timekeeper.InstructionFileHandler;
import jp.co.daifuku.wms.base.util.timekeeper.JobParam;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleFileHandler;

/**
 * 自動処理（TimeKeeper）設定ファイルの編集を行うユーティリティクラスです。
 *
 * @version $Revision: 1208 $, $Date: 2009-03-19 17:25:36 +0900 (木, 19 3 2009) $
 * @author  Softecs
 * @author  Last commit: $Author: mshimizu@softecs.jp $
 */

public class TimeKeeperMainFrame
        extends JFrame
{
    /** <code>serialVersionUID</code> */
    static final long serialVersionUID = 3076887167527807665L;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>KEY_STRING</code><br>
     * リソースを取得する際に用いるキー文字列
     */
    static final String KEY_STRING = "TimeKeeperUtil";

    static final InstructionFileHandler INST_HANDLER = new InstructionFileHandler();

    static final ScheduleFileHandler SCHE_HANDLER = new ScheduleFileHandler();

    final Icons ICONS = new Icons();

    /** 削除ボタンのカラム位置 */
    static final int BUTTON_COLUMN = 0;

    /** 有効チェックボックスのカラム位置 */
    static int BOOLEAN_COLUMN = 1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // process at class loaded.
    //------------------------------------------------------------
    // static
    JPanel jContentPane = null;

    JPanel buttonPanel = null;

    JScrollPane mainPanel = null;

    JTable jTable = null;

    JMenuBar menuBar = null;

    JMenu instructionMenu = null;

    JMenu informationMenu = null;

    JMenu fileMenu = null;

    JMenuItem exitMenuItem = null;

    JMenuItem loadMenuItem = null;

    JMenuItem saveMenuItem = null;

    JMenuItem reloadMenuItem = null;

    JMenuItem terminateMenuItem = null;

    JMenuItem informationMenuItem = null;

    JMenuItem aboutMenuItem = null;

    JPanel jFilePanel = null;

    JPanel jEndPanel = null;

    JButton jLoadButton = null;

    JButton jSaveButton = null;

    JButton jEndButton = null;

    JPanel jScheButtonPanel = null;

    JButton jAddButton = null;

    ReadOnlyTableModel _roTableModel = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * default constructor.
     */
    public TimeKeeperMainFrame()
    {
        super();

        initialize();
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
        return "$Id: TimeKeeperMainFrame.java 1208 2009-03-19 08:25:36Z mshimizu@softecs.jp $";
    }

    /**
     * This method initializes this
     */
    void initialize()
    {
        this.setSize(700, 400);
        setContentPane(getJContentPane());
        setJMenuBar(getMainMenuBar());
        setTitle(KEY_STRING + " $Revision: 1208 $");

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                exitActionPerformed();
            }
        });
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJScheButtonPanel(), BorderLayout.NORTH);
            jContentPane.add(getMainPanel(), BorderLayout.CENTER);
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return jContentPane;
    }

    /**
     * This method initializes buttonPanel
     * 	
     * @return javax.swing.JPanel
     */
    JPanel getButtonPanel()
    {
        if (buttonPanel == null)
        {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            buttonPanel.add(getJFilePanel(), BorderLayout.CENTER);
            buttonPanel.add(getJEndPanel(), BorderLayout.EAST);

        }
        return buttonPanel;
    }

    /**
     * This method initializes mainPanel
     * 	
     * @return javax.swing.JScrollPane
     */
    JScrollPane getMainPanel()
    {
        if (mainPanel == null)
        {
            mainPanel = new JScrollPane();
            mainPanel.setViewportView(getJTable());
            mainPanel.setFocusable(false);
        }
        return mainPanel;
    }

    /**
     * This method initializes jTable 
     *  
     * @return javax.swing.JTable 
     */
    JTable getJTable()
    {
        if (jTable == null)
        {
            jTable = new JTable(getTableModel())
            {
                /** <code>serialVersionUID</code> */
                private static final long serialVersionUID = 1L;

                // 削除チェックボックスが操作された時の処理
                @Override
                public Component prepareEditor(TableCellEditor editor, int row, int column)
                {
                    Component cmp = super.prepareEditor(editor, row, column);
                    if (BOOLEAN_COLUMN == convertColumnIndexToModel(column))
                    {
                        final JobParam param = _roTableModel.getJobParam(row);
                        final boolean update = !param.getInterval().isEnabled();
                        param.getInterval().setEnabled(update);
                        _roTableModel.replaceJobParam(row, param);
                        getJSaveButton().setIcon(ICONS.NEED_SAVE);
                    }
                    return cmp;
                }

                // 削除チェックボックスが操作された時のチェックボックス表示処理
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
                {
                    Component cmp = super.prepareRenderer(renderer, row, column);
                    if (BOOLEAN_COLUMN == convertColumnIndexToModel(column))
                    {
                        JobParam param = _roTableModel.getJobParam(row);
                        final boolean enabled = param.getInterval().isEnabled();
                        JCheckBox cb = (JCheckBox)cmp;
                        cb.setSelected(enabled);
                    }
                    return cmp;
                }
            };
            jTable.addMouseListener(new java.awt.event.MouseAdapter()
            {
                // 削除ボタンを押下した行
                int targetRow = -1;

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent e)
                {
                    // ダブルクリックによりその行の編集を行います。
                    if (2 == e.getClickCount())
                    {
                        final Point pt = e.getPoint();
                        final int vrow = jTable.rowAtPoint(pt);

                        final JobParam param = _roTableModel.getJobParam(vrow);
                        final String className = param.getClassName();
                        final String intervalString = param.getInterval().getTimingString();

                        DetailDialog dialog = new DetailDialog(getThisFrame());
                        final boolean ret = dialog.show(intervalString, className);
                        if (ret)
                        {
                            final JobParam updateParam = dialog.getJobParam();
                            _roTableModel.replaceJobParam(vrow, updateParam);
                            getJSaveButton().setIcon(ICONS.NEED_SAVE);
                        }
                        jTable.repaint();
                    }
                    else
                    {
                        super.mouseClicked(e);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e)
                {
                    // 削除ボタンを押下したときは、その行を記録します。
                    final Point pt = e.getPoint();
                    final int mcol = jTable.convertColumnIndexToModel(jTable.columnAtPoint(pt));
                    final int vrow = jTable.rowAtPoint(pt);
                    targetRow = (BUTTON_COLUMN == mcol) ? vrow
                                                       : -1;
                }

                @Override
                public void mouseReleased(MouseEvent e)
                {
                    // 削除ボタンをリリースしたとき、押下行を同一であれば該当行のJobParamデータを削除します。
                    if (-1 < targetRow)
                    {
                        final Point pt = e.getPoint();
                        final int mcol = jTable.convertColumnIndexToModel(jTable.columnAtPoint(pt));
                        final int vrow = jTable.rowAtPoint(pt);
                        if ((BUTTON_COLUMN == mcol) && (targetRow == vrow))
                        {
                            final String title = Resource.getString("YesNoDialog.014");
                            final String message = Resource.getString("YesNoDialog.024");
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
                            _roTableModel.deleteJobParam(vrow);
                            getJSaveButton().setIcon(ICONS.NEED_SAVE);
                        }
                    }
                    targetRow = -1;
                }

            });
            jTable.addFocusListener(new java.awt.event.FocusAdapter()
            {
                @Override
                public void focusGained(final java.awt.event.FocusEvent e)
                {
                    final KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    fm.focusNextComponent(jTable);
                }
            });
            jTable.setFont(new Font("Dialog", Font.PLAIN, 12));
            jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            jTable.getTableHeader().setReorderingAllowed(false);
            _roTableModel.addRow(SCHE_HANDLER.getSchedules());

            // 生成したテーブルのセル幅を設定します。
            final int[] list = { // 各セルの全体幅に対する比率を設定します。
                        10, // 削除ボタン
                        5, // 有効
                        5, // 秒
                        5, // 分
                        5, // 時
                        5, // 日
                        5, // 月
                        8, // 曜日
                        50
                    // 実行クラス名(最後の比率は「余り」を使用するので意味がない。)
                    };
            int total = jTable.getColumnModel().getTotalColumnWidth();
            final int raito = total / 100;
            for (int i = 0; i < jTable.getColumnModel().getColumnCount() - 1; i++)
            {
                final TableColumn col = jTable.getColumnModel().getColumn(i);
                final int colwidth = list[i] * raito;
                col.setPreferredWidth(colwidth);
                total = total - colwidth;
            }
            jTable.getColumnModel().getColumn(jTable.getColumnModel().getColumnCount() - 1).setPreferredWidth(total);
            jTable.revalidate();
        }

        // 削除ボタンセルの作成
        ButtonColumn buttonColumn = new ButtonColumn();
        TableColumn column = jTable.getColumnModel().getColumn(BUTTON_COLUMN);
        column.setCellRenderer(buttonColumn);
        column.setCellEditor(buttonColumn);
        column.setMinWidth(40);
        column.setResizable(false);

        // チェックボックスセルの作成
        CheckBoxColumn checkColumn = new CheckBoxColumn();
        column = jTable.getColumnModel().getColumn(BOOLEAN_COLUMN);
        column.setCellRenderer(checkColumn);
        column.setCellEditor(checkColumn);
        column.setResizable(false);

        return jTable;
    }

    /**
     * JTableで使用するテーブルモデルを取得します。
     * @return ReadOnlyTableModel
     */
    ReadOnlyTableModel getTableModel()
    {
        if (_roTableModel == null)
        {
            _roTableModel = new ReadOnlyTableModel();
        }
        return _roTableModel;
    }

    /**
     * This method initializes menuBar
     * 	
     * @return javax.swing.JMenuBar
     */
    JMenuBar getMainMenuBar()
    {
        if (menuBar == null)
        {
            menuBar = new JMenuBar();
            menuBar.add(getFileMenu());
            menuBar.add(getInstructionMenu());
            menuBar.add(getInformationMenu());
        }
        return menuBar;
    }

    /**
     * 指示メニューを生成して返します。
     * @return 指示メニュー
     */
    JMenu getInstructionMenu()
    {
        if (instructionMenu == null)
        {
            instructionMenu = new JMenu();
            instructionMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
            instructionMenu.setText(Resource.getString(KEY_STRING + ".010"));
            instructionMenu.add(getReloadMenuItem());
            instructionMenu.add(getTerminateMenuItem());
        }
        return instructionMenu;
    }

    /**
     * 情報メニューを生成して返します。
     * @return 情報メニュー
     */
    JMenu getInformationMenu()
    {
        if (informationMenu == null)
        {
            informationMenu = new JMenu();
            informationMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
            informationMenu.setText(Resource.getString(KEY_STRING + ".030"));
            informationMenu.add(getInformationMenuItem());
            informationMenu.add(getAboutMenuItem());
        }
        return informationMenu;
    }

    /**
     * ファイルメニューを生成して返します。
     * @return ファイルメニュー
     */
    JMenu getFileMenu()
    {
        if (fileMenu == null)
        {
            fileMenu = new JMenu();
            fileMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
            fileMenu.setText(Resource.getString(KEY_STRING + ".020"));
            fileMenu.add(getLoadMenuItem());
            fileMenu.add(getSaveMenuItem());
            fileMenu.add(getExitMenuItem());
        }
        return fileMenu;
    }

    /**
     * 終了メニューアイテムを生成して返します。
     * @return 終了メニューアイテム
     */
    JMenuItem getExitMenuItem()
    {
        if (exitMenuItem == null)
        {
            exitMenuItem = new JMenuItem();
            exitMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            exitMenuItem.setText(Resource.getString(KEY_STRING + ".023"));
            exitMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(final ActionEvent e)
                {
                    exitActionPerformed();
                }
            });
        }
        return exitMenuItem;
    }

    /**
     * 読込みメニューアイテムを生成して返します。
     * @return 読込メニューアイテム
     */
    JMenuItem getLoadMenuItem()
    {
        if (loadMenuItem == null)
        {
            loadMenuItem = new JMenuItem();
            loadMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            loadMenuItem.setText(Resource.getString(KEY_STRING + ".021"));
            loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK, true));
            loadMenuItem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    loadFile();
                }
            });
        }
        return loadMenuItem;
    }

    /**
     * 保存メニューアイテムを生成して返します。
     * @return 保存メニューアイテム
     */
    JMenuItem getSaveMenuItem()
    {
        if (saveMenuItem == null)
        {
            saveMenuItem = new JMenuItem();
            saveMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            saveMenuItem.setText(Resource.getString(KEY_STRING + ".022"));
            saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, true));
            saveMenuItem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    saveFile();
                }
            });
        }
        return saveMenuItem;
    }

    /**
     * 再読込みメニューアイテムを生成して返します。
     * @return 再読込みメニューアイテム
     */
    JMenuItem getReloadMenuItem()
    {
        if (reloadMenuItem == null)
        {
            reloadMenuItem = new JMenuItem();
            reloadMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            reloadMenuItem.setText(Resource.getString(KEY_STRING + ".011"));
            reloadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK, true));
            reloadMenuItem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    fireReload();
                }
            });
        }
        return reloadMenuItem;
    }

    /**
     * 終了指示メニューアイテムを生成して返します。
     * @return 終了指示メニューアイテム
     */
    JMenuItem getTerminateMenuItem()
    {
        if (terminateMenuItem == null)
        {
            terminateMenuItem = new JMenuItem();
            terminateMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            terminateMenuItem.setText(Resource.getString(KEY_STRING + ".012"));
            terminateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK, true));
            terminateMenuItem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    fireTerminate();
                }
            });
        }
        return terminateMenuItem;
    }

    /**
     * 情報メニューアイテムを生成して返します。
     * @return 情報メニューアイテム
     */
    JMenuItem getInformationMenuItem()
    {
        if (informationMenuItem == null)
        {
            informationMenuItem = new JMenuItem();
            informationMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            informationMenuItem.setText(Resource.getString(KEY_STRING + ".030"));
            informationMenuItem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final String title = Resource.getString("InformationDialog.000");
                    final StringBuffer message = new StringBuffer("\n");
                    message.append(Resource.getString("InformationDialog.002"));
                    message.append(" : ");
                    message.append(INST_HANDLER.getTimeKeeperDir());
                    message.append("\n\n");
                    message.append(Resource.getString("InformationDialog.003"));
                    message.append(" : ");
                    message.append(SCHE_HANDLER.getScheduleFileName());
                    message.append("\n\n");
                    message.append(Resource.getString("InformationDialog.004"));
                    message.append(" : ");
                    message.append(INST_HANDLER.getInstructionFileName());
                    message.append("\n");
                    final int button = YesNoDialog.BUTTON_YES;
                    final int focus = YesNoDialog.BUTTON_YES;
                    confirmWithDialog(title, message.toString(), button, focus);
                }
            });
        }
        return informationMenuItem;
    }

    /**
     * Aboutメニューアイテムを生成して返します。
     * @return Aboutメニューアイテム
     */
    JMenuItem getAboutMenuItem()
    {
        if (aboutMenuItem == null)
        {
            aboutMenuItem = new JMenuItem();
            aboutMenuItem.setFont(new Font("Dialog", Font.PLAIN, 12));
            aboutMenuItem.setText(Resource.getString(KEY_STRING + ".001"));
            aboutMenuItem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    final String title = Resource.getString(KEY_STRING + ".001");
                    final StringBuffer message = new StringBuffer("\n\n");
                    message.append("\t");
                    message.append(KEY_STRING);
                    message.append("\n\n\t");
                    message.append("$Revision: 1208 $");
                    final int button = YesNoDialog.BUTTON_YES;
                    final int focus = YesNoDialog.BUTTON_YES;
                    confirmWithDialog(title, message.toString(), button, focus);
                }
            });
        }
        return aboutMenuItem;
    }

    /**
     * This method initializes jFilePanel 
     *  
     * @return javax.swing.JPanel 
     */
    JPanel getJFilePanel()
    {
        if (jFilePanel == null)
        {
            final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = -1;
            gridBagConstraints1.gridy = -1;
            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = -1;
            gridBagConstraints.gridy = -1;
            jFilePanel = new JPanel();
            jFilePanel.setLayout(new BoxLayout(getJFilePanel(), BoxLayout.X_AXIS));
            jFilePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jFilePanel.add(getJLoadButton(), null);
            jFilePanel.add(getJSaveButton(), null);
        }
        return jFilePanel;
    }

    /**
     * This method initializes jEndPanel 
     *  
     * @return javax.swing.JPanel 
     */
    JPanel getJEndPanel()
    {
        if (jEndPanel == null)
        {
            final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = -1;
            gridBagConstraints2.gridy = -1;
            jEndPanel = new JPanel();
            jEndPanel.setLayout(new BoxLayout(getJEndPanel(), BoxLayout.X_AXIS));
            jEndPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            jEndPanel.add(getJEndButton(), null);
        }
        return jEndPanel;
    }

    /**
     * This method initializes jLoadButton 
     *  
     * @return javax.swing.JButton 
     */
    JButton getJLoadButton()
    {
        if (jLoadButton == null)
        {
            jLoadButton = new JButton();
            jLoadButton.setIcon(ICONS.RELOAD);
            jLoadButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jLoadButton.setText(Resource.getString(KEY_STRING + ".201"));
            jLoadButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    loadFile();
                }
            });
        }
        return jLoadButton;
    }

    /**
     * This method initializes jSaveButton 
     *  
     * @return javax.swing.JButton 
     */
    JButton getJSaveButton()
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
    JButton getJEndButton()
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
                    exitActionPerformed();
                }
            });
        }
        return jEndButton;
    }

    /**
     * This method initializes jScheButtonPanel 
     *  
     * @return javax.swing.JPanel 
     */
    JPanel getJScheButtonPanel()
    {
        if (jScheButtonPanel == null)
        {
            jScheButtonPanel = new JPanel();
            jScheButtonPanel.setLayout(new BoxLayout(getJScheButtonPanel(), BoxLayout.X_AXIS));
            jScheButtonPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            jScheButtonPanel.add(getJAddButton(), null);
        }
        return jScheButtonPanel;
    }

    /**
     * This method initializes jAddButton 
     *  
     * @return javax.swing.JButton 
     */
    JButton getJAddButton()
    {
        if (jAddButton == null)
        {
            jAddButton = new JButton();
            jAddButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jAddButton.setText(Resource.getString(KEY_STRING + ".106"));
            jAddButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(final java.awt.event.ActionEvent e)
                {
                    DetailDialog dialog = new DetailDialog(getThisFrame());
                    final boolean ret = dialog.show("", "");
                    if (ret)
                    {
                        final JobParam param = dialog.getJobParam();
                        _roTableModel.addNewJobParam(param);
                        getJSaveButton().setIcon(ICONS.NEED_SAVE);
                    }
                }
            });
        }
        return jAddButton;
    }

    /**
     * 終了メニュー、終了ボタンを選択した時の処理です。
     */
    void exitActionPerformed()
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
     * 自動処理へ再読込指示を行います。
     */
    void fireReload()
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
        INST_HANDLER.putReload();
    }

    /**
     * 自動処理へ終了指示を行います。
     */
    void fireTerminate()
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
        INST_HANDLER.putTerminate();
    }

    /**
     * 確認ダイアログを表示し、押下されたボタンを通知します。
     * @param title ダイアログのタイトル
     * @param message 表示メッセージ
     * @param button 表示ボタン
     * @param focus デフォルト(フォーカス)ボタン
     * @return 押下されたボタン
     */
    static int confirmWithDialog(final String title, final String message, final int button, final int focus)
    {
        final YesNoDialog dialog = new YesNoDialog(null, title, true);
        dialog.show(message, button, focus);
        final int ret = dialog.getAnswer();
        return ret;
    }

    /**
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
        SCHE_HANDLER.reload();
        _roTableModel.addRow(SCHE_HANDLER.getSchedules());
        getJSaveButton().setIcon(ICONS.SAVE);
    }

    /**
     * 指示ファイルの保存を行います。
     */
    void saveFile()
    {
        SCHE_HANDLER.replaceAll(_roTableModel.getJobParams());
        _roTableModel.clearAll();
        _roTableModel.addRow(SCHE_HANDLER.getSchedules());
        getJSaveButton().setIcon(ICONS.SAVE);
    }

    /**
     * セル内の削除ボタンクラスです。
     *
     * @version $Revision: 1208 $, $Date: 2009-03-19 17:25:36 +0900 (木, 19 3 2009) $
     * @author  Softecs
     * @author  Last commit: $Author: mshimizu@softecs.jp $
     */
    class ButtonColumn
            extends AbstractCellEditor
            implements TableCellRenderer, TableCellEditor
    {
        /** <code>serialVersionUID</code> */
        private static final long serialVersionUID = 4874106040412448819L;

        final String LABEL = Resource.getString("TimeKeeperUtil.109");

        final JButton renderButton = new JButton(LABEL);

        final JButton editorButton;

        public ButtonColumn()
        {
            super();
            editorButton = new JButton(new AbstractAction(LABEL)
            {
                /** <code>serialVersionUID</code> */
                private static final long serialVersionUID = -122193044871730273L;

                @SuppressWarnings("synthetic-access")
                public void actionPerformed(ActionEvent e)
                {
                    fireEditingStopped();
                }
            });
            editorButton.setBorder(BorderFactory.createEmptyBorder());
            editorButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            editorButton.setFocusPainted(false);
            editorButton.setRolloverEnabled(false);
            renderButton.setBorder(BorderFactory.createEmptyBorder());
            renderButton.setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
        {
            return renderButton;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            return editorButton;
        }

        public Object getCellEditorValue()
        {
            return LABEL;
        }
    }

    /**
     * セル内の有効チェックボックスクラスです。
     *
     * @version $Revision: 1208 $, $Date: 2009-03-19 17:25:36 +0900 (木, 19 3 2009) $
     * @author  Softecs
     * @author  Last commit: $Author: mshimizu@softecs.jp $
     */
    class CheckBoxColumn
            extends AbstractCellEditor
            implements TableCellRenderer, TableCellEditor
    {
        /** <code>serialVersionUID</code> */
        private static final long serialVersionUID = 6206747303692891306L;

        final JCheckBox renderCheck = new JCheckBox();

        final JCheckBox editorCheck;

        public CheckBoxColumn()
        {
            super();
            editorCheck = new JCheckBox(new AbstractAction()
            {
                /** <code>serialVersionUID</code> */
                private static final long serialVersionUID = 1L;

                @SuppressWarnings("synthetic-access")
                public void actionPerformed(ActionEvent e)
                {
                    fireEditingStopped();
                }
            });
            editorCheck.setBorder(BorderFactory.createEmptyBorder());
            editorCheck.setHorizontalAlignment(SwingConstants.CENTER);
            editorCheck.setFocusPainted(false);
            editorCheck.setRolloverEnabled(false);
            renderCheck.setBorder(BorderFactory.createEmptyBorder());
            renderCheck.setHorizontalAlignment(SwingConstants.CENTER);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
        {
            return renderCheck;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            return editorCheck;
        }

        public Object getCellEditorValue()
        {
            return "";
        }
    }

    /**
     * 自フレームクラスを返します。<br>
     * ダイアログを使用するときに利用します。
     *
     * @return 自分自身を返します。
     */
    JFrame getThisFrame()
    {
        return this;
    }
}
