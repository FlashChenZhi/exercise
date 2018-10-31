package jp.co.daifuku.common.trace;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TraceViewFrame
        extends JFrame
{
    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 2411514796219251174L;

    private static final int WIN_WIDTH = 900;

    private static final int WIN_HEIGHT = 1200;

    private TraceRecord[] _traces;

    DefaultListSelectionModel selection = new DefaultListSelectionModel();

    JPanel contentPane;

    JMenuBar jMenuBar1 = new JMenuBar();

    JMenu jMenuFile = new JMenu();

    JMenuItem jMenuFileExit = new JMenuItem();

    JLabel statusBar = new JLabel();

    JPanel mainPane = new JPanel();

    BorderLayout borderLayout1 = new BorderLayout();

    JPanel outlinePane = new JPanel();

    BorderLayout borderLayout2 = new BorderLayout();

    JPanel detailPane = new JPanel();

    BorderLayout borderLayout3 = new BorderLayout();

    JTextField txtFileName = new JTextField();

    BorderLayout borderLayout4 = new BorderLayout();

    JButton btnOpenFile = new JButton();

    Border border1;

    TitledBorder titledBorder1;

    Border border2;

    Border border3;

    Border border4;

    TitledBorder titledBorder2;

    Border border5;

    Border border6;

    javax.swing.table.DefaultTableModel titleModel;

    JSplitPane jSplitPane1 = new JSplitPane();


    JScrollPane titleScrollPane = new JScrollPane();

    JTable lstTitles = new JTable();

    JPanel detailBottomPane = new JPanel();

    JPanel revPane = new JPanel();

    BorderLayout borderLayout5 = new BorderLayout();


    Border border7;

    JTextField txtRevision = new JTextField();

    BorderLayout borderLayout6 = new BorderLayout();

    JScrollPane exPane = new JScrollPane();

    JTextArea txtException = new JTextArea();


    //Construct the frame
    /**
     * フレームを初期化します。
     */
    public TraceViewFrame()
    {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try
        {
            modelSetup();
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void modelSetup()
    {
        titleModel = new javax.swing.table.DefaultTableModel();
        titleModel.addColumn("Date");
        titleModel.addColumn("Log Class");
    }

    //Component initialization
    private void jbInit()
            throws Exception
    {
        contentPane = (JPanel)this.getContentPane();

        border7 =
                BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                        Color.white, new Color(99, 99, 99), new Color(142, 142, 142)), BorderFactory.createEmptyBorder(
                        4, 4, 4, 4));
        selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        border1 =
                BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(99, 99, 99),
                        new Color(142, 142, 142));
        titledBorder1 =
                new TitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white,
                        new Color(99, 99, 99), new Color(142, 142, 142)), "Trace File");
        border2 =
                BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142,
                        142, 142)), BorderFactory.createEmptyBorder(4, 4, 4, 4));
        border3 = BorderFactory.createCompoundBorder(titledBorder1, BorderFactory.createEmptyBorder(4, 4, 4, 4));
        border4 =
                BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(99, 99, 99),
                        new Color(142, 142, 142));
        titledBorder2 = new TitledBorder(border4, "Exceptions");
        border5 =
                BorderFactory.createCompoundBorder(
                        new TitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white,
                                new Color(99, 99, 99), new Color(142, 142, 142)), "Exceptions"),
                        BorderFactory.createEmptyBorder(4, 4, 4, 4));
        border6 =
                BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                        Color.white, new Color(99, 99, 99), new Color(142, 142, 142)), BorderFactory.createEmptyBorder(
                        4, 4, 4, 4));

        setFrameSize();

        this.setTitle("TraceView");
        statusBar.setDebugGraphicsOptions(0);
        statusBar.setText(" ");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new Frame1_jMenuFileExit_ActionAdapter(this));

        btnOpenFile.setText("Select Trace File");
        btnOpenFile.addActionListener(new Frame1_btnOpenFile_actionAdapter(this));

        txtFileName.setText("");
        txtFileName.addActionListener(new Frame1_txtFileName_actionAdapter(this));
        txtFileName.setFont(new java.awt.Font("DialogInput", 0, 14));
        txtFileName.setToolTipText("Hit Enter key to open the file.");

        lstTitles.setFont(new java.awt.Font("DialogInput", 0, 13));
        lstTitles.setToolTipText("");
        lstTitles.setCellSelectionEnabled(false);

        lstTitles.setIntercellSpacing(new Dimension(1, 4));
        lstTitles.setRowMargin(5);
        lstTitles.setRowHeight(lstTitles.getRowHeight() + 6);


        lstTitles.setRowSelectionAllowed(true);
        lstTitles.setSelectionForeground(Color.black);
        lstTitles.setSelectionModel(selection);
        lstTitles.setShowVerticalLines(true);
        lstTitles.addKeyListener(new Frame1_lstTitles_keyAdapter(this));
        lstTitles.addMouseListener(new Frame1_lstTitles_mouseAdapter(this));

        detailBottomPane.setLayout(borderLayout5);
        revPane.setBorder(border7);
        revPane.setDebugGraphicsOptions(0);
        revPane.setLayout(borderLayout6);

        txtRevision.setFont(new java.awt.Font("DialogInput", 0, 12));
        txtRevision.setDoubleBuffered(true);
        txtRevision.setBackground(SystemColor.info);
        txtRevision.setEditable(false);
        txtRevision.setToolTipText("Class Revision");

        txtException.setFont(new java.awt.Font("DialogInput", 0, 14));
        txtException.setDoubleBuffered(true);
        txtException.setToolTipText("");
        txtException.setCaretPosition(0);
        txtException.setEditable(false);
        txtException.setText("");
        txtException.setTabSize(4);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuFile);
        this.setJMenuBar(jMenuBar1);

        mainPane.setDebugGraphicsOptions(0);
        mainPane.setLayout(borderLayout2);

        outlinePane.setLayout(borderLayout4);
        outlinePane.setBorder(border3);

        contentPane.setLayout(borderLayout1);
        contentPane.add(mainPane, BorderLayout.CENTER);
        contentPane.add(statusBar, BorderLayout.SOUTH);

        mainPane.add(outlinePane, BorderLayout.NORTH);
        mainPane.add(detailPane, BorderLayout.CENTER);

        detailPane.setLayout(borderLayout3);
        detailPane.setBorder(border2);
        detailPane.add(jSplitPane1, BorderLayout.CENTER);

        outlinePane.add(txtFileName, BorderLayout.CENTER);
        outlinePane.add(btnOpenFile, BorderLayout.EAST);

        titleScrollPane.getViewport().add(lstTitles);

        revPane.add(txtRevision, BorderLayout.CENTER);

        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setDividerLocation(150);
        jSplitPane1.add(titleScrollPane, JSplitPane.TOP);
        jSplitPane1.add(detailBottomPane, JSplitPane.BOTTOM);
        detailBottomPane.add(revPane, BorderLayout.NORTH);
        detailBottomPane.add(exPane, BorderLayout.CENTER);
        exPane.getViewport().add(txtException, null);
    }

    /**
     *
     * @throws HeadlessException
     */
    private void setFrameSize()
            throws HeadlessException
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.width = screenSize.width - 50;
        screenSize.height = screenSize.height - 50;
        if (screenSize.width > WIN_WIDTH)
        {
            screenSize.width = WIN_WIDTH;
        }
        if (screenSize.height > WIN_HEIGHT)
        {
            screenSize.height = WIN_HEIGHT;
        }
        this.setSize(screenSize);
    }

    /**
     * アプリケーションを終了します。
     * @param e
     */
    //File | Exit action performed
    @SuppressWarnings({
            "static-method",
            "javadoc"
    })
    public void jMenuFileExit_actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }

    //Overridden so we can exit when window is closed
    @Override
    protected void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            jMenuFileExit_actionPerformed(null);
        }
    }

    void btnOpenFile_actionPerformed(ActionEvent e)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select trace log file");
        chooser.setMultiSelectionEnabled(false);

        String curFileName = txtFileName.getText();
        if (curFileName.length() > 0)
        {
            File tf = (new File(curFileName)).getParentFile();
            if (tf != null && tf.exists())
            {
                chooser.setCurrentDirectory(tf);
            }
        }

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File selFile = chooser.getSelectedFile();
            txtFileName.setText(selFile.getAbsolutePath());
            txtFileName_actionPerformed(null);
        }
    }

    void txtFileName_actionPerformed(ActionEvent e)
    {
        try
        {
            File logFile = new File(txtFileName.getText());
            if (!logFile.exists())
            {
                statusBar.setText("File not found");
            }
            else if (!logFile.canRead())
            {
                statusBar.setText("File not readable");
            }
            else if (!logFile.isFile())
            {
                statusBar.setText(logFile.getName() + " is not a File");
            }
            else
            {
                statusBar.setText("Reading " + logFile.getName());
                repaint();

                int rec = setTraceList(logFile);

                statusBar.setText(rec + " items loaded");
                repaint();
            }
            System.gc();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            //
        }
    }

    /**
     *
     * @param logFile
     * @return 読み込んだ件数
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings({
            "unchecked",
            "rawtypes"
    })
    private int setTraceList(File logFile)
            throws FileNotFoundException,
                IOException
    {
        FileReader fr = null;
        try
        {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
            fr = new FileReader(logFile);
            _traces = TraceRecord.readTrace(new LineNumberReader(fr));

            modelSetup();
            lstTitles.setModel(titleModel);
            for (int i = 0; i < _traces.length; i++)
            {
                Vector cols = new Vector();

                TraceRecord trr = _traces[i];
                cols.add(fmt.format(trr.getLogDate()));
                cols.add(trr.getClassName());
                titleModel.addRow(cols);
            }
            // titleModel.fireTableStructureChanged() ;

            TableColumn dateCol = lstTitles.getColumnModel().getColumn(0);
            dateCol.setMinWidth(220);
            dateCol.setMaxWidth(220);
            // dateCol.setPreferredWidth(180) ;
            lstTitles.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            lstTitles.doLayout();

            txtException.setText("");
            txtRevision.setText("");
            return _traces.length;
        }
        finally
        {
            try
            {
                if (fr != null)
                {
                    fr.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }


    void lstTitles_mouseReleased(MouseEvent e)
    {
        showExceptionDetail();
    }

    /**
     *
     *
     */
    private void showExceptionDetail()
    {
        int sel = lstTitles.getSelectedRow();
        TraceRecord tr = _traces[sel];

        String[] traceLines = tr.getStackTrace();
        txtException.setText("");
        for (int i = 0; i < traceLines.length; i++)
        {
            txtException.append(traceLines[i] + "\n");
        }
        txtException.setCaretPosition(0);

        txtRevision.setText(tr.getRevision());
    }

    void lstTitles_keyReleased(KeyEvent e)
    {
        showExceptionDetail();
    }
}


class Frame1_jMenuFileExit_ActionAdapter
        implements ActionListener
{
    TraceViewFrame adaptee;

    Frame1_jMenuFileExit_ActionAdapter(TraceViewFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jMenuFileExit_actionPerformed(e);
    }
}


class Frame1_btnOpenFile_actionAdapter
        implements java.awt.event.ActionListener
{
    TraceViewFrame adaptee;

    Frame1_btnOpenFile_actionAdapter(TraceViewFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        adaptee.btnOpenFile_actionPerformed(e);
    }
}


class Frame1_txtFileName_actionAdapter
        implements java.awt.event.ActionListener
{
    TraceViewFrame adaptee;

    Frame1_txtFileName_actionAdapter(TraceViewFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        adaptee.txtFileName_actionPerformed(e);
    }
}


class Frame1_lstTitles_mouseAdapter
        extends java.awt.event.MouseAdapter
{
    TraceViewFrame adaptee;

    Frame1_lstTitles_mouseAdapter(TraceViewFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        adaptee.lstTitles_mouseReleased(e);
    }
}


class Frame1_lstTitles_keyAdapter
        extends java.awt.event.KeyAdapter
{
    TraceViewFrame adaptee;

    Frame1_lstTitles_keyAdapter(TraceViewFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        adaptee.lstTitles_keyReleased(e);
    }
}
