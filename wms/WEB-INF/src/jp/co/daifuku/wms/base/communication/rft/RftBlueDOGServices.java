// $Id: RftBlueDOGServices.java 87 2008-10-04 03:07:38Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.communication.rft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jp.co.daifuku.bluedog.exception.InitializationException;
import jp.co.daifuku.bluedog.logging.LogConfigurator;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.sql.DataSourceConfig;
import jp.co.daifuku.wms.base.common.WmsParam;
/**
 * <jp></jp><br>
 * <en>This class loads starts the BlueDOG services required for RFT server.
 *     This class starts the following services 1. Log4j service 2. DataBase polling service
 *  </en><br>
 * @author Muneendra
 * 
 */
public class RftBlueDOGServices
{
	/**
	 * <jp><br></jp>
	 * <en>This method starts the following services 1.Log4j service 2. DataBase polling service <br></en>
	 * @throws InitializationException
	 */
	public static void loadBleDOGServices() throws InitializationException
	{
		loadLog4jSerivce();
		loadDataBaseSerivce();
	}	
	/**
	 * <jp><br></jp>
	 * <en>This method loads Log4j service. The log4j service properties are configured in rft-log4j-config.xml file<br></en>
	 * @throws InitializationException
	 */
	private static void loadLog4jSerivce() throws InitializationException
	{			 
				System.out.println ("Loading LOG4J Services");
			    // 	Get rft-log4j-config.xml file path from WMSParam.properties
				String log4jConfigPath = WmsParam.RFT_LO4J_FILE_PATH;
				// throw exception if file path does not exists
				if (log4jConfigPath == null || "".equals(log4jConfigPath))
				{
					throw new InitializationException("rft-log4j-config.xml parameter does not exists in WMSParam.properties.");
				}				
				try
				{
					File file = new File(log4jConfigPath);
					// initialize log4j properties by calling blueDOG functions
					LogConfigurator.configure(file);
					LogConfigurator.freeze(); 
				}
				catch (Exception e)
				{
					throw new InitializationException("Check rft-log4j-config.xml",e);					
				}	
				//Logger.info("Log configuration completed.");				
	}
	/**
	 * <jp><br></jp>
	 * <en>This method loads DB pooling service. The DB pooling service properties are configured in rft-datasource.xml file<br></en>
	 * @throws InitializationException
	 */
	private static void loadDataBaseSerivce() throws InitializationException
	{	
				System.out.println ("Loading DATABASE Services");
				// 	Get rft-datasource.xml file path from WMSParam.properties		
				String dataSourceConfigPath = WmsParam.RFT_DATASOURCE_FILE_PATH;
				//throw exception if file path does not exists
				if (dataSourceConfigPath == null || "".equals(dataSourceConfigPath))
				{
					throw new InitializationException("rft-datasource.xml parameter does not exists in WMSParam.properties.");
				}				
				InputStream rftDataSourceXml = null;
				try
				{
					// create a file objec
					rftDataSourceXml = new File(dataSourceConfigPath).toURL().openStream();
				}
				catch (IOException e)
				{
					throw new InitializationException("datasouce file [" + dataSourceConfigPath + "] does not exists.",e);
				}				
				try
				{
					DataSourceConfig dsConfig = DataSourceConfig.getInstance();
					//	initialize database connection pool by calling blueDOG functions
					dsConfig.configure(rftDataSourceXml);
					dsConfig.freeze();
				}
				catch (Exception e)
				{
						throw new InitializationException("Check rft-datasource.xml configuration ",e);					
				}	
				//Logger.info("Data Source configuration completed.");
	}
	
	/**
	 * <jp><br></jp>
	 * <en>This method closes all the DataBase connections during RFT server shutdown. 
	 * This method is called if RFT server is shhutdown by using the command Ctrl-C<br></en>
	 * @throws InitializationException
	 */
	 public static void stopBlueDOGServices()
	 {
			try
			{	
				// Close all DB Connections in pool
				ConnectionManager.destroy();
				//Logger.info("shut down complete.");
			}
			catch (Exception e)
			{
				//Logger.error("shut down error.", e);
			}
	 }
}
