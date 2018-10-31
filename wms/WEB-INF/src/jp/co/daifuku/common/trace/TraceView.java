package jp.co.daifuku.common.trace ;

import java.awt.* ;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TraceView
{
	boolean packFrame = false ;

	/**
	 * フレームを生成します。
	 */
	//Construct the application
	public TraceView()
	{
		TraceViewFrame frame = new TraceViewFrame() ;
		//Validate frames that have preset sizes
		//Pack frames that have useful preferred size info, e.g. from their layout
		if (packFrame)
		{
			frame.pack() ;
		}
		else
		{
			frame.validate() ;
		}
		//Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
		Dimension frameSize = frame.getSize() ;
		if (frameSize.height > screenSize.height)
		{
			frameSize.height = screenSize.height ;
		}
		if (frameSize.width > screenSize.width)
		{
			frameSize.width = screenSize.width ;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 4) ;
		frame.setVisible(true) ;
	}

	/**
	 * メイン
	 * @param args
	 */
	public static void main(String[] args)
	{
		/*
		 try {
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 }
		 catch(Exception e) {
		 e.printStackTrace();
		 }*/
		new TraceView() ;
	}
}
