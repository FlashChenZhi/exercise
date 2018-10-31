// $Id: YesNoDialog.java 5527 2009-11-09 08:03:43Z ota $
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * Yes/No/Cancel3つのボタンを備えたダイアログボックスクラスです。<br>
 * タイトル、メッセージ、デフォルトボタン、表示ボタンの制御指定を
 * 行う事ができます。　また、どのボタンが選択されたかは、<code>getAnswer()</code>
 * メソッドで確認します。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/10/30</td><td nowrap>Softecs</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 5527 $, $Date: 2009-11-09 17:03:43 +0900 (月, 09 11 2009) $
 * @author  Softecs
 * @author  Last commit: $Author: ota $
 */

public class YesNoDialog
        extends JDialog
{
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5796982330454779575L;

    //------------------------------------------------------------
    // component
    //------------------------------------------------------------
    private JPanel jContentPane = null;

    private JPanel jButtonPanel = null;

    JButton jYesButton = null;

    JButton jNoButton = null;

    JButton jCancelButton = null;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * <code>KEY_STRING</code><br>
     * リソースを取得する際に用いるキー文字列
     * モジュール名を指定して下さい。
     */
    private static final String KEY_STRING = "YesNoDialog";

    /**
     * <code>BUTTON_YES</code><br>
     * ボタンNo：Yesボタン.
     */
    public static final int BUTTON_YES = 0x01;

    /**
     * <code>BUTTON_YES</code><br>
     * ボタンNo：Noボタン.
     */
    public static final int BUTTON_NO = 0x02;

    /**
     * <code>BUTTON_YES</code><br>
     * ボタンNo：Cancelボタン.
     */
    public static final int BUTTON_CANCEL = 0x04;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;
    private int p_answer;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private Component[] _buttons = {
        jYesButton,
        jNoButton,
        jCancelButton
    };

    private JTextArea jTextArea = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ.
     * @param owner 親フレーム
     * @deprecated 通常このコンストラクタを使用しません。
     */
    @Deprecated
    public YesNoDialog(Frame owner)
    {
        super(owner);
        initialize();
    }

    /**
     * コンストラクタ.
     * @param owner 親フレーム
     * @param title タイトル
     * @param modal モーダル指定
     */
    public YesNoDialog(Frame owner, String title, boolean modal)
    {
        // スーパークラスのコンストラクタをコールします。
        super(owner, title, modal);

        // ウィンドウクローズボタンで終了しない様に設定します。
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // 各コンポーネントを配置します。
        initialize();
        pack();
        setSize(400, 200);

        // ダイアログをスクリーン中央に表示する様にします。
        setLocationRelativeTo(null);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * メッセージ、表示ボタン、デフォルト(フォーカス)ボタンを指定して
     * ダイアログボックスを表示します。
     * @param message メッセージ
     * @param showButton 表示ボタン(ボタンNoの論理和で指定します)
     * @param focusButton デフォルト(フォーカス)ボタン
     */
    public void show(String message, int showButton, int focusButton)
    {
        // ボタンの制御
        int focusBitMap = focusButton;
        int showBitMap = showButton;
        for (Component button : _buttons)
        {
            boolean switchShow = 0 < (showBitMap & 0x01);
            button.setVisible(switchShow);
            button.setFocusable(switchShow);
            showBitMap >>= 1;

            // フォーカスボタンの設定を行います。
            boolean switchFocus = 0 < (focusBitMap & 0x01);
            if (switchFocus)
            {
                button.requestFocusInWindow();
            }
            focusBitMap >>= 1;
        }

        // メッセージ設定
        jTextArea.setText(message);

        //show();
        setVisible(true);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ダイアログボックスで選択されたボタンを確認します。
     * @return ボタンNo
     */
    public int getAnswer()
    {
        return p_answer;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * いずれかの確認ボタンをクリックした時、押下したボタンを保存します。
     */
    protected void answerClicked(int answer)
    {
        p_answer = answer;
        dispose();
    }

    /**
     * キーボードショートカットを登録したルートパネルを生成します。
     */
    @Override
    protected JRootPane createRootPane()
    {
        // キーストロークを生成します。
        KeyStroke cStroke = KeyStroke.getKeyStroke('c');
        KeyStroke yStroke = KeyStroke.getKeyStroke('y');
        KeyStroke nStroke = KeyStroke.getKeyStroke('n');
        KeyStroke CStroke = KeyStroke.getKeyStroke('C');
        KeyStroke YStroke = KeyStroke.getKeyStroke('Y');
        KeyStroke NStroke = KeyStroke.getKeyStroke('N');
        KeyStroke escStoroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        // アクションリスナを生成します。
        ActionListener cListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                btn_Cancel_actionPerformed(actionEvent);
            }
        };

        ActionListener yListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                btn_Yes_actionPerformed(actionEvent);
            }
        };

        ActionListener nListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                btn_No_actionPerformed(actionEvent);
            }
        };

        // 生成したキーストロークとアクションリスナを登録します。
        JRootPane retRootPane = new JRootPane();
        retRootPane.registerKeyboardAction(cListener, cStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        retRootPane.registerKeyboardAction(cListener, CStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        retRootPane.registerKeyboardAction(yListener, yStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        retRootPane.registerKeyboardAction(yListener, YStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        retRootPane.registerKeyboardAction(nListener, nStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        retRootPane.registerKeyboardAction(nListener, NStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        retRootPane.registerKeyboardAction(cListener, escStoroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        return retRootPane;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * このダイアログボックスの初期化を行います。
     */
    private void initialize()
    {
        this.setSize(300, 200);
        setContentPane(getJContentPane());
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                if (jCancelButton.isVisible())
                {
                    btn_Cancel_actionPerformed(null);
                }
                else if (jNoButton.isVisible())
                {
                    btn_No_actionPerformed(null);
                }
                else if (jYesButton.isVisible())
                {
                    btn_Yes_actionPerformed(null);
                }
            }
        });
    }

    /**
     * このダイアログボックスの各コンポーネントを配置します。
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            jContentPane.add(getJButtonPanel(), BorderLayout.SOUTH);
            jContentPane.add(getJTextArea(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * This method initializes jTextArea 
     *  
     * @return javax.swing.JTextArea 
     */
    private JTextArea getJTextArea()
    {
        if (jTextArea == null)
        {
            jTextArea = new JTextArea();
            jTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
            jTextArea.setBackground(new Color(238, 238, 238));
            jTextArea.setEditable(false);
            jTextArea.setLineWrap(true);
            jTextArea.setFocusable(false);
        }
        return jTextArea;
    }

    /**
     * 確認ボタンのパネルを生成して返します。
     * @return javax.swing.JPanel 
     */
    private JPanel getJButtonPanel()
    {
        if (jButtonPanel == null)
        {
            jButtonPanel = new JPanel();
            jButtonPanel.setLayout(new FlowLayout());
            jButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jButtonPanel.add(getJYesButton(), null);
            jButtonPanel.add(getJNoButton(), null);
            jButtonPanel.add(getJCancelButton(), null);
        }
        return jButtonPanel;
    }

    /**
     * Yesボタンを生成して返します。
     * @return javax.swing.JButton 
     */
    private JButton getJYesButton()
    {
        if (jYesButton == null)
        {
            jYesButton = new JButton();
            _buttons[0] = jYesButton;
            jYesButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jYesButton.setText(Resource.getString(KEY_STRING + ".001"));
            jYesButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    btn_Yes_actionPerformed(e);
                }
            });
        }
        return jYesButton;
    }

    /**
     * Noボタンを生成して返します。
     * @return javax.swing.JButton 
     */
    private JButton getJNoButton()
    {
        if (jNoButton == null)
        {
            jNoButton = new JButton();
            _buttons[1] = jNoButton;
            jNoButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jNoButton.setText(Resource.getString(KEY_STRING + ".002"));
            jNoButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    btn_No_actionPerformed(e);
                }
            });
        }
        return jNoButton;
    }

    /**
     * Cancelボタンを生成して返します。
     * @return javax.swing.JButton 
     */
    private JButton getJCancelButton()
    {
        if (jCancelButton == null)
        {
            jCancelButton = new JButton();
            _buttons[2] = jCancelButton;
            jCancelButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            jCancelButton.setText(Resource.getString(KEY_STRING + ".003"));
            jCancelButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    btn_Cancel_actionPerformed(e);
                }
            });
        }
        return jCancelButton;
    }

    //------------------------------------------------------------
    // event handler
    //------------------------------------------------------------
    /**
     * Yesボタンイベントハンドラ
     * @param e アクションイベント
     */
    void btn_Yes_actionPerformed(ActionEvent e)
    {
        answerClicked(BUTTON_YES);
    }

    /**
     * Noボタンイベントハンドラ
     * @param e アクションイベント
     */
    void btn_No_actionPerformed(ActionEvent e)
    {
        answerClicked(BUTTON_NO);
    }

    /**
     * Cancelボタンイベントハンドラ
     * @param e アクションイベント
     */
    void btn_Cancel_actionPerformed(ActionEvent e)
    {
        answerClicked(BUTTON_CANCEL);
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
        return "$Id: YesNoDialog.java 5527 2009-11-09 08:03:43Z ota $";
    }
}
