// $Id: DetailDialog.java 1208 2009-03-19 08:25:36Z mshimizu@softecs.jp $
// $LastChangedRevision: 1208 $

package jp.co.daifuku.wms.base.util.timekeeper.util;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.util.timekeeper.JobParam;

/**
 * TimeKeeperの作業パラメータを入力するダイアログクラスです。
 *
 * @version $Revision: 1208 $, $Date: 2009-03-19 17:25:36 +0900 (木, 19 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: mshimizu@softecs.jp $
 */

public class DetailDialog
        extends JDialog
{
    /** <code>BUTTON_GROUP</code> */
    static final ButtonGroup BUTTON_GROUP = new ButtonGroup();

    /**
     * <code>KEY_STRING</code><br>
     * リソースを取得する際に用いるキー文字列
     */
    static final String KEY_STRING = "DetailDialog";

    static final String SEPARATOR = " ";

    static final long serialVersionUID = 1L;

    JPanel jContentPane = null;

    JPanel mainPanel = null;

    JPanel buttonPanel = null;

    JButton btnOK = null;

    JButton btnCancel = null;

    JRadioButton rbSimple = null;

    JRadioButton rbCron = null;

    JTextField txtSimpleSec = null;

    JLabel lblsec = null;

    JTextField txtcronmin = null;

    JTextField txtcronhour = null;

    JLabel lblhour = null;

    JTextField txtcronday = null;

    JLabel lblday = null;

    JTextField txtcronmonth = null;

    JLabel lblmonth = null;

    JTextField txtcrondow = null;

    JLabel lbldow = null;

    JTextField txtClassname = null;

    JLabel lblClassname = null;

    JTextField[] _cronInputs;

    boolean _answer;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * default constructor.
     * @param owner owner
     */
    public DetailDialog(Frame owner)
    {
        super(owner, true);
        initialize();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        clear();
    }

    /**
     * show this dialog.
     * 
     * @param set
     * @param classname
     * @return true: ok, false: cancel
     */
    public boolean show(String set, String classname)
    {
        setLocationRelativeTo(getParent());

        clear();

        boolean isSimple = true;
        try
        {
            Integer.parseInt(set);
            txtSimpleSec.setText(set);
        }
        catch (Exception e)
        {
            isSimple = false;
            String[] defs = set.split(" ");
            int idx = 0;
            for (String def : defs)
            {
                _cronInputs[idx++].setText(def);
            }
        }
        txtClassname.setText(classname);

        // focus control
        final boolean simpleFocus = isSimple;
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                if (simpleFocus)
                {
                    rbSimple.doClick();
                }
                else
                {
                    rbCron.doClick();
                }
            }
        });

        setVisible(true);

        return _answer;
    }

    /**
     * clear input.
     */
    void clear()
    {
        txtSimpleSec.setText("");

        JTextField[] cflds = {
            getTxtcronmin(),
            getTxtcronhour(),
            getTxtcronday(),
            getTxtcronmonth(),
            getTxtcrondow(),
        };
        for (JTextField fld : cflds)
        {
            fld.setText("");
        }
        _cronInputs = cflds;
    }

    /**
     * This method initializes this
     */
    void initialize()
    {
        this.setSize(600, 320);
        setResizable(false);
        setTitle(Resource.getString("DetailDialog.000"));
        setContentPane(getJContentPane());
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                cancel();
            }
        });
    }

    /**
     * This method initializes txtClassname 
     *  
     * @return javax.swing.JTextField   
     */
    JTextField getTxtClassname()
    {
        if (txtClassname == null)
        {
            txtClassname = new JTextField();
            txtClassname.setPreferredSize(new Dimension(200, 24));
        }
        return txtClassname;
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
            jContentPane.add(getMainPanel(), BorderLayout.CENTER);
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return jContentPane;
    }

    /**
     * This method initializes mainPanel
     * 
     * @return javax.swing.JPanel
     */
    JPanel getMainPanel()
    {
        if (mainPanel == null)
        {
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.anchor = GridBagConstraints.CENTER;
            gridBagConstraints13.insets = new Insets(30, 0, 0, 0);
            gridBagConstraints13.gridy = 6;
            lblClassname = new JLabel();
            lblClassname.setText(Resource.getString(KEY_STRING + ".016"));
            lblClassname.setFont(new Font("Dialog", Font.PLAIN, 12));
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.gridy = 6;
            gridBagConstraints12.weightx = 1.0;
            gridBagConstraints12.insets = new Insets(30, 0, 0, 8);
            gridBagConstraints12.gridwidth = 2;
            gridBagConstraints12.gridx = 1;
            GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
            gridBagConstraints111.gridx = 1;
            gridBagConstraints111.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints111.gridy = 5;
            lbldow = new JLabel();
            lbldow.setText(Resource.getString(KEY_STRING + ".015"));
            lbldow.setFont(new Font("Dialog", Font.PLAIN, 12));
            lbldow.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.gridy = 5;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.insets = new Insets(0, 8, 0, 8);
            gridBagConstraints10.gridx = 2;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 1;
            gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridy = 4;
            lblmonth = new JLabel();
            lblmonth.setText(Resource.getString(KEY_STRING + ".014"));
            lblmonth.setFont(new Font("Dialog", Font.PLAIN, 12));
            lblmonth.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 4;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.insets = new Insets(0, 8, 0, 8);
            gridBagConstraints8.gridx = 2;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 3;
            lblday = new JLabel();
            lblday.setText(Resource.getString(KEY_STRING + ".013"));
            lblday.setFont(new Font("Dialog", Font.PLAIN, 12));
            lblday.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 3;
            gridBagConstraints6.weightx = 1.0;
            gridBagConstraints6.insets = new Insets(0, 8, 0, 8);
            gridBagConstraints6.gridx = 2;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 1;
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 2;
            lblhour = new JLabel();
            lblhour.setText(Resource.getString(KEY_STRING + ".012"));
            lblhour.setFont(new Font("Dialog", Font.PLAIN, 12));
            lblhour.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 2;
            gridBagConstraints4.weightx = 1.0;
            gridBagConstraints4.insets = new Insets(0, 8, 0, 8);
            gridBagConstraints4.gridx = 2;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 1;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.ipadx = 0;
            gridBagConstraints3.insets = new Insets(0, 8, 0, 8);
            gridBagConstraints3.gridx = 2;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.anchor = GridBagConstraints.CENTER;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 1;
            lblsec = new JLabel();
            lblsec.setText(Resource.getString(KEY_STRING + ".011"));
            lblsec.setFont(new Font("Dialog", Font.PLAIN, 12));
            lblsec.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = GridBagConstraints.NONE;
            gridBagConstraints11.gridy = 0;
            gridBagConstraints11.weightx = 0.0;
            gridBagConstraints11.anchor = GridBagConstraints.WEST;
            gridBagConstraints11.insets = new Insets(0, 8, 5, 0);
            gridBagConstraints11.ipady = 0;
            gridBagConstraints11.gridx = 2;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.anchor = GridBagConstraints.EAST;
            gridBagConstraints.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints.ipady = 20;
            gridBagConstraints.gridy = 0;
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getRbSimple(), gridBagConstraints);
            mainPanel.add(getRbCron(), gridBagConstraints1);
            mainPanel.add(getTxtSimpleSec(), gridBagConstraints11);
            mainPanel.add(lblsec, gridBagConstraints2);
            mainPanel.add(getTxtcronmin(), gridBagConstraints3);
            mainPanel.add(getTxtcronhour(), gridBagConstraints4);
            mainPanel.add(lblhour, gridBagConstraints5);
            mainPanel.add(getTxtcronday(), gridBagConstraints6);
            mainPanel.add(lblday, gridBagConstraints7);
            mainPanel.add(getTxtcronmonth(), gridBagConstraints8);
            mainPanel.add(lblmonth, gridBagConstraints9);
            mainPanel.add(getTxtcrondow(), gridBagConstraints10);
            mainPanel.add(lbldow, gridBagConstraints111);
            mainPanel.add(getTxtClassname(), gridBagConstraints12);
            mainPanel.add(lblClassname, gridBagConstraints13);
        }
        return mainPanel;
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
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(20);
            flowLayout.setAlignment(FlowLayout.RIGHT);
            buttonPanel = new JPanel();
            buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            buttonPanel.setLayout(flowLayout);
            buttonPanel.add(getBtnCancel(), null);
            buttonPanel.add(getBtnOK(), null);
        }
        return buttonPanel;
    }

    /**
     * This method initializes btnOK
     * 
     * @return javax.swing.JButton
     */
    JButton getBtnOK()
    {
        if (btnOK == null)
        {
            btnOK = new JButton();
            btnOK.setText(Resource.getString(KEY_STRING + ".001"));
            btnOK.setFont(new Font("Dialog", Font.PLAIN, 12));
            btnOK.setPreferredSize(new Dimension(100, 26));
            btnOK.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    accept();
                }
            });
        }
        return btnOK;
    }

    /**
     * ok clicked.
     */
    void accept()
    {
        if (validateInput())
        {
            _answer = true;
            dispose();
        }
    }

    /**
     * check input parameters.
     */
    boolean validateInput()
    {
        try
        {
            JobParam jobParam = createJobParam();
            jobParam.getInterval();
            // intervalが正しく生成できればOK
            return true;
        }
        catch (RuntimeException e)
        {
            // interval が生成できなかったのでNG
            YesNoDialog dialog = new YesNoDialog(null, "Not valid input", true);
            dialog.show(Resource.getString("YesNoDialog.025"), YesNoDialog.BUTTON_YES, 0);
            return false;
        }
    }

    /**
     * This method initializes btnCancel
     * 
     * @return javax.swing.JButton
     */
    JButton getBtnCancel()
    {
        if (btnCancel == null)
        {
            btnCancel = new JButton();
            btnCancel.setText(Resource.getString(KEY_STRING + ".003"));
            btnCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
            btnCancel.setPreferredSize(new Dimension(100, 26));
            btnCancel.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    cancel();
                }
            });
        }
        return btnCancel;
    }

    /**
     * cancel clicked.
     */
    void cancel()
    {
        _answer = false;
        dispose();
    }

    /**
     * This method initializes rbSimple
     * 
     * @return javax.swing.JRadioButton
     */
    JRadioButton getRbSimple()
    {
        if (rbSimple == null)
        {
            JToggleButton.ToggleButtonModel toggleButtonModel = new JToggleButton.ToggleButtonModel();
            toggleButtonModel.setGroup(BUTTON_GROUP);
            rbSimple = new JRadioButton();
            rbSimple.setText(Resource.getString(KEY_STRING + ".004"));
            rbSimple.setFont(new Font("Dialog", Font.PLAIN, 12));
            rbSimple.setHorizontalAlignment(SwingConstants.RIGHT);
            rbSimple.setPreferredSize(new Dimension(100, 24));
            rbSimple.setRequestFocusEnabled(false);
            rbSimple.setModel(toggleButtonModel);
            rbSimple.setHorizontalTextPosition(SwingConstants.LEFT);
            rbSimple.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    changeType(false);
                    txtSimpleSec.requestFocus();
                }
            });
        }
        return rbSimple;
    }

    /**
     * This method initializes rbCron
     * 
     * @return javax.swing.JRadioButton
     */
    JRadioButton getRbCron()
    {
        if (rbCron == null)
        {
            JToggleButton.ToggleButtonModel toggleButtonModel1 = new JToggleButton.ToggleButtonModel();
            toggleButtonModel1.setGroup(BUTTON_GROUP);
            rbCron = new JRadioButton();
            rbCron.setText(Resource.getString(KEY_STRING + ".005"));
            rbCron.setFont(new Font("Dialog", Font.PLAIN, 12));
            rbCron.setPreferredSize(new Dimension(100, 24));
            rbCron.setHorizontalAlignment(SwingConstants.RIGHT);
            rbCron.setRequestFocusEnabled(false);
            rbCron.setModel(toggleButtonModel1);
            rbCron.setHorizontalTextPosition(SwingConstants.LEFT);
            rbCron.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    changeType(true);
                    txtcronmin.requestFocus();
                }
            });
        }
        return rbCron;
    }

    /**
     * change input type.
     * 
     * @param b true:cron type
     */
    void changeType(boolean b)
    {
        txtSimpleSec.setEnabled(!b);
        for (JTextField fld : _cronInputs)
        {
            fld.setEnabled(b);
        }
    }

    /**
     * This method initializes txtSimpleSec
     * 
     * @return javax.swing.JTextField
     */
    JTextField getTxtSimpleSec()
    {
        if (txtSimpleSec == null)
        {
            txtSimpleSec = new JTextField();
            txtSimpleSec.setPreferredSize(new Dimension(40, 24));
            txtSimpleSec.addKeyListener(new java.awt.event.KeyAdapter()
            {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e)
                {
                    if (!validateNumber(e))
                    {
                        e.consume();
                    }
                }
            });
            txtSimpleSec.addFocusListener(new java.awt.event.FocusAdapter()
            {
                @Override
                public void focusLost(java.awt.event.FocusEvent e)
                {
                    String sstr = txtSimpleSec.getText();
                    // if (!sstr.isEmpty())
                    if (!StringUtil.isBlank(sstr))
                    {
                        String reformat = "";
                        try
                        {
                            int intv = Integer.parseInt(sstr);
                            reformat = String.valueOf(intv);
                        }
                        catch (RuntimeException ex)
                        {
                            // do nothing
                        }
                        txtSimpleSec.setText(reformat);
                    }
                }
            });
        }
        return txtSimpleSec;
    }

    /**
     * validate key is number or not.
     * @param e key event
     * @return ture, if key is number
     */
    protected boolean validateNumber(KeyEvent e)
    {
        char key = e.getKeyChar();
        if (key == ' ')
        {
            // スペースはNG
            return false;
        }
        // if (String.valueOf(key).trim().isEmpty())
        if (StringUtil.isBlank(String.valueOf(key).trim()))
        {
            // 文字ではないときはOKとする (BS, DELなど)
            return true;
        }
        boolean isNum = ((key >= '0') && (key <= '9'));
        return isNum;
    }

    /**
     * validate key is number or not.
     * @param e key event
     * @return ture, if key is number
     */
    protected boolean validateCron(KeyEvent e)
    {
        char key = e.getKeyChar();
        // if (String.valueOf(key).trim().isEmpty())
        if (StringUtil.isBlank(String.valueOf(key).trim()))
        {
            // 文字ではないときはOKとする (BS, DELなど)
            return true;
        }
        if (validateNumber(e))
        {
            // 数値はOK
            return true;
        }
        switch (key)
        {
            case ',':
            case '-':
            case '/':
            case '*':
                // 入力可能な文字
                return true;
            default:
                // その他の文字はNG
                return false;
        }
    }

    /**
     * This method initializes txtcronmin
     * 
     * @return javax.swing.JTextField
     */
    JTextField getTxtcronmin()
    {
        if (txtcronmin == null)
        {
            txtcronmin = new JTextField();
            txtcronmin.setPreferredSize(new Dimension(200, 24));
            txtcronmin.addKeyListener(new java.awt.event.KeyAdapter()
            {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e)
                {
                    if (!validateCron(e))
                    {
                        e.consume();
                    }
                }
            });
        }
        return txtcronmin;
    }

    /**
     * This method initializes txtcronhour
     * 
     * @return javax.swing.JTextField
     */
    JTextField getTxtcronhour()
    {
        if (txtcronhour == null)
        {
            txtcronhour = new JTextField();
            txtcronhour.setPreferredSize(new Dimension(200, 24));
            txtcronhour.addKeyListener(new java.awt.event.KeyAdapter()
            {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e)
                {
                    if (!validateCron(e))
                    {
                        e.consume();
                    }
                }
            });
        }
        return txtcronhour;
    }

    /**
     * This method initializes txtcronday
     * 
     * @return javax.swing.JTextField
     */
    JTextField getTxtcronday()
    {
        if (txtcronday == null)
        {
            txtcronday = new JTextField();
            txtcronday.setPreferredSize(new Dimension(200, 24));
            txtcronday.addKeyListener(new java.awt.event.KeyAdapter()
            {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e)
                {
                    if (!validateCron(e))
                    {
                        e.consume();
                    }
                }
            });
        }
        return txtcronday;
    }

    /**
     * This method initializes txtcronmonth
     * 
     * @return javax.swing.JTextField
     */
    JTextField getTxtcronmonth()
    {
        if (txtcronmonth == null)
        {
            txtcronmonth = new JTextField();
            txtcronmonth.setPreferredSize(new Dimension(200, 24));
            txtcronmonth.addKeyListener(new java.awt.event.KeyAdapter()
            {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e)
                {
                    if (!validateCron(e))
                    {
                        e.consume();
                    }
                }
            });
        }
        return txtcronmonth;
    }

    /**
     * This method initializes txtcrondow
     * 
     * @return javax.swing.JTextField
     */
    JTextField getTxtcrondow()
    {
        if (txtcrondow == null)
        {
            txtcrondow = new JTextField();
            txtcrondow.setPreferredSize(new Dimension(200, 24));
            txtcrondow.addKeyListener(new java.awt.event.KeyAdapter()
            {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e)
                {
                    if (!validateCron(e))
                    {
                        e.consume();
                    }
                }
            });
        }
        return txtcrondow;
    }

    /**
     * 入力された作業パラメータを取得します。<br>
     * 正しく入力されていない時にこのメソッドを使用すると、nullが返ります。
     *
     * @return 作業パラメータ
     */
    public JobParam getJobParam()
    {
        return (_answer) ? createJobParam()
                        : null;
    }

    /**
     * create job param.
     * @return job param.
     */
    private JobParam createJobParam()
    {
        String timing;
        if (getRbSimple().isSelected())
        {
            timing = validateEmpty(getTxtSimpleSec());
            if (1 > Integer.parseInt(timing))
            {
                throw new RuntimeException("Interval too small.");
            }
        }
        else
        {
            StringBuffer buffer = new StringBuffer(getTxtcronmin().getText());
            buffer.append(SEPARATOR);
            String hour = validateEmpty(getTxtcronhour());
            buffer.append(hour);
            buffer.append(SEPARATOR);
            String day = validateEmpty(getTxtcronday());
            buffer.append(day);
            buffer.append(SEPARATOR);
            String month = validateEmpty(getTxtcronmonth());
            buffer.append(month);;
            buffer.append(SEPARATOR);
            String week = validateEmpty(getTxtcrondow());
            buffer.append(week);
            timing = String.valueOf(buffer);
        }

        final String className = getTxtClassname().getText();
        final JobParam param = new JobParam(timing, className, null, true);
        return param;
    }

    /**
     * @param txtcronhour2
     * @return
     */
    private String validateEmpty(JTextField fld)
    {
        String v = fld.getText().trim();
        // if (v.isEmpty())
        if (StringUtil.isBlank(v))
        {
            throw new RuntimeException("No imput");
        }
        return v;
    }
} //  @jve:decl-index=0:visual-constraint="10,10"
