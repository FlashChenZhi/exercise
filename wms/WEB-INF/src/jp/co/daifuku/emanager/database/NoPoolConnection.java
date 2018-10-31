package jp.co.daifuku.emanager.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class makes DB connection for a given datasource name by reading DB configurations from datasource.xml file 
 * This prograame can run on different JVM or same as e-series JVM 
 * 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="DataSourceDBConnection"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/04/20</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class NoPoolConnection
        extends Object
{

    /**
     * ユーザ名
     */
    private static final String USERNAME = "username";

    /**
     * パスワード
     */
    private static final String PASSWORD = "password";

    /**
     * URL
     */
    private static final String URL = "url";

    /**
     * 
     * Attempts to establish a connection to Database for a given datasource name in datasource.xml file.
     * 
     * @param dataSourceXMLPath  :   Absolute path of datasource.xml file
     * @param dataSourceName :    dataSourceName
     * @throws SQLException : When database access goes wrong, notify.
     * @return   <code>Connection</code>
     */
    public static Connection getConnection(String dataSourceXMLPath, String dataSourceName)
            throws SQLException
    {
        String url = null;
        String user = null;
        String passwd = null;
        Connection conn = null;

        if (dataSourceName == null || "".equals(dataSourceName))
        {
            throw new SQLException("Data source name can not be null or empty string. ");
        }

        try
        {
            File xmlFile = new File(dataSourceXMLPath);
            //Get Doucment Factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Document Builder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Load DATA source XML file file
            Document document = builder.parse(xmlFile);

            // Read datasource Element from  datasource.xml file
            NodeList nl = document.getElementsByTagName("datasource");

            for (int n = 0; n < nl.getLength(); n++)
            {
                Element element = (Element)nl.item(n);

                // get Datasource name
                String dataSourceNameTemp = element.getAttribute("name");

                // if DataSource Name is given datasource, get db connection properties from datasource.xml file 
                if (dataSourceName.equals(dataSourceNameTemp))
                {
                    // get all properties Elements
                    NodeList properties = element.getElementsByTagName("property");

                    for (int pro = 0; pro < properties.getLength(); pro++)
                    {
                        Element propElement = (Element)properties.item(pro);

                        // Get property element Name Attribute 
                        String propertyName = propElement.getAttribute("name");

                        // If Attribute name is USERNAME, get value
                        if (propertyName != null && USERNAME.equals(propertyName))
                        {
                            user = propElement.getAttribute("value");
                        }
                        // If Attribute name is PASSWORD, get value
                        else if (propertyName != null && PASSWORD.equals(propertyName))
                        {
                            passwd = propElement.getAttribute("value");
                        }
                        //If Attribute name is URL, get value
                        else if (propertyName != null && URL.equals(propertyName))
                        {
                            url = propElement.getAttribute("value");
                        }
                    }
                }
            }

            conn = getConnection(url, user, passwd);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw new SQLException("Check the path or cofiguration of dataSource.xml file");
        }
        // return DB Connection
        return conn;
    }


    /**
     * Oracle DB接続のための<code>Connection</code>を取得します。
     * @param url       データベース・URL
     * @param user      データベース・ユーザ名
     * @param passwd    ユーザ・パスワード
     * @throws SQLException データベースアクセスに失敗した場合に通知します。
     * @return   <code>Connection</code>
     */
    private static Connection getConnection(String url, String user, String passwd)
            throws SQLException,
                ClassNotFoundException
    {
        // JDBC driver 読み込み
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // コネクション作成
        Connection conn = DriverManager.getConnection(url, user, passwd);
        // 自動commit off
        conn.setAutoCommit(false);

        return (conn);
    }

    /**
     * @param args 引数
     */
    public static void main(String[] args)
    {
        try
        {
            String dataSourceXML = "C:\\daifuku\\wms\\tomcat\\webapps\\emTools\\WEB-INF\\datasource.xml";
            Connection con = getConnection(dataSourceXML, "wms");
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT COUNT(*) as COUNT FROM COM_LOGINUSER");
            rset.next();
            System.out.println(rset.getInt(1));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
