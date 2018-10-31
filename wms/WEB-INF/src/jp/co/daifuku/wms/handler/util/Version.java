// $Id: Version.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.util;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

/*
 * Update history of Handler package
 *  2007-03-14:add Version window.
 */
/**
 * Show handler version information.<br>
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class Version
        extends JFrame
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JTextField txtProduct = null;

    private JTextPane txtDetail = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * default constructor.
     */
    private Version()
    {
        super("Handler Version Information");

        initialize();
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    private void setVersion()
    {
        String[] msgs = {
            "<font size=+1>Version: 3, Revision: " + getRevision() + "</font>",
            "",
            getDate(),
            "",
            "<font size=-1>Copyright(c) 2001-2007 DAIFUKU Co.,Ltd.",
            "All rights reserved.</font>",
        };

        StringBuilder buff = new StringBuilder();
        for (String line : msgs)
        {
            buff.append(line);
            buff.append("<br>\n");
        }
        txtDetail.setText(new String(buff));
    }

    private String getRevision()
    {
        String rev = "$LastChangedRevision: 87 $";
        String[] rsp = rev.split(" ");

        return rsp[1];
    }

    private String getDate()
    {
        String dt = "$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $";
        return dt.substring(1, dt.length() - 2);
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: Version.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * This method initializes jContentPane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getTxtProduct(), BorderLayout.NORTH);
            jContentPane.add(getTxtDetail(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * This method initializes txtProduct	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getTxtProduct()
    {
        if (txtProduct == null)
        {
            txtProduct = new JTextField();
            txtProduct.setFont(new Font("Dialog", Font.BOLD, 16));
            txtProduct.setForeground(Color.darkGray);
            txtProduct.setBackground(SystemColor.control);
            txtProduct.setEditable(false);
            txtProduct.setText("Handler Package");
        }
        return txtProduct;
    }

    /**
     * This method initializes txtDetail	
     * 	
     * @return javax.swing.JTextPane	
     */
    private JTextPane getTxtDetail()
    {
        if (txtDetail == null)
        {
            txtDetail = new JTextPane();
            txtDetail.setEditable(false);
            txtDetail.setContentType("text/html");
        }
        return txtDetail;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                Version thisClass = new Version();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                thisClass.setLocationByPlatform(true);

                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This method initializes this
     */
    private void initialize()
    {
        this.setSize(300, 200);
        this.setContentPane(getJContentPane());
        setVersion();
    }
}
