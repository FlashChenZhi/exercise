// $Id: ReArrangeAllocator.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.operator.AsrsReArrangeOperator;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Station;

/**
 * <jp> 配置替え引当処理を行うクラスです。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2002/09/01</TD>
 * <TD>sawa</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author $Author: kishimoto $ </jp>
 */
public class ReArrangeAllocator extends RetrievalSender implements Runnable
{
	// Class fields --------------------------------------------------
	/**
	 * <jp> リモートオブジェクトにバインドするオブジェクト名 </jp>
	 */
	/**
	 * <en> Object to bind to the remote object </en>
	 */
	public static final String OBJECT_NAME = "ReArrangeAllocator";

	/**
	 * <jp> 起動モードです。デバッグモードで起動する場合コンストラクタに<CODE>true</CODE>をセットします。 </jp>
	 */
	/**
	 * <en> Start-up mode. If starting on debug mode, set constructor with <CODE>true</CODE>.
	 * </en>
	 */
	// <jp> デフォルト ノーマルモード</jp>
	// <en> Default normal mode</en>
	private static boolean $mode = false;

	// Class variables -----------------------------------------------
	/**
	 * <jp> スレッド待機時間 </jp>
	 */
	/**
	 * <en> Thread idle time </en>
	 */
	// <jp> デフォルト10秒</jp>
	// <en> Default 10 seconds</en>
	protected int _sleepTime = 10000;

	/**
	 * <jp> このフラグは、ReArrangeAllocatorクラスを終了するかどうか判断します。 ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
	 * このフラグを更新するためには、stop()メソッドを使用します。 </jp>
	 */
	/**
	 * <en> This flag determines whether/not to terminate the ReArrangeAllocator class. If ExitFlag
	 * changes to true, it pulls out of the infinite loop of run() method. This stop() method must
	 * be used to update this flag. </en>
	 */
	private boolean _exitFlag = false;

//    /**<jp>
//     * 多重起動フラグ
//     </jp>*/
//    /**<en>
//     * start-up overlaps
//     </en>*/
//    private boolean _overlapsFlag = false;

    /**
     * AGC 番号を保持する変数。
     */
    private String _agcNo;

	// Class method --------------------------------------------------
	/**
	 * <jp> このクラスのバージョンを返します。
	 * @return バージョンと日付 </jp>
	 */
	/**
	 * <en> Returns the version of this class.
	 * @return Version and the date </en>
	 */
	public static String getVersion()
	{
		return("$Revision: 8062 $,$Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $");
	}

	/**
	 * <jp> プロンプトに出力します。
	 * @param str
	 *        プロンプトに表示する文字列
	 * @param system
	 *        ノーマルモードで起動された場合でもログを落とす場合は<CODE>true</CODE>をセットします。 </jp>
	 */
	/**
	 * <en> Output to the prompt display.
	 * @param str
	 *        String to show on the prompt display
	 * @param system
	 *        Even if started up on normal mode, and if recording the log, set <CODE>true</CODE>.
	 *        </en>
	 */
	private static void println(String str, boolean system)
	{
		if($mode || system)
		{
			System.out.println(str);
		}
	}

	// Constructors --------------------------------------------------
    /**<jp>
     * デフォルトの監視時間を使用して新しい<code>ReArrangeAllocator</code>のインスタンスを構築します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Constructs a new instance of <code>ReArrangeAllocator</code> using the default time of monitoring.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public ReArrangeAllocator() throws ReadWriteException,
            RemoteException
    {
        restRequest();
//        Runtime.getRuntime().addShutdownHook(new Thread()
//        {
//            public void run()
//            {
//                // 終了処理
//                try
//                {
//                    if (_overlapsFlag)
//                    {
//                        // 多重起動の場合は何もしない
//                        return;
//                    }
//
//                    _exitFlag = true;
//	                closeConnection();
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME))
//                    {
//                        unbinder.unbind(OBJECT_NAME);
//                    }
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });
    }

	/**
	 * <jp> デフォルトの監視時間を使用して新しい<code>ReArrangeAllocator</code>のインスタンスを構築します。
	 * @param mode
	 *        起動モードを指定します。デバッグモードで起動する場合<CODE>true</CODE>をセットします。
	 * @throws RemoteException
	 *         リモートメソッド呼び出しの実行中に発生する通信関連の例外
	 * @throws ReadWriteException
	 *         データベース接続で例外が発生した場合に通知します。
	 * @throws RemoteException
	 *         リモートメソッド呼び出しの実行中に発生する通信関連の例外 </jp>
	 */
	/**
	 * <en> Constructs a new instance of <code>ReArrangeAllocator</code> using the default time of
	 * monitoring.
	 * @param mode
	 *        :designate the start-up mode. Set <CODE>true</CODE> if starting by debug mode.
	 * @throws ReadWriteException :
	 *         Notifies if exception occured during the database connection.
	 * @throws RemoteException
	 *         Exception related to communication generated while executing remote method call </en>
	 */
	public ReArrangeAllocator(boolean mode) throws ReadWriteException, RemoteException
	{
		$mode = mode;
		restRequest();
//		Runtime.getRuntime().addShutdownHook(new Thread()
//		{
//			public void run()
//			{
//				// 終了処理
//                try
//                {
//                    if (_overlapsFlag)
//                    {
//                        // 多重起動の場合は何もしない
//                        return;
//                    }
//
//                    _exitFlag = true;
//					closeConnection();
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME))
//                    {
//                        unbinder.unbind(OBJECT_NAME);
//					}
//                }
//                catch (Exception e)
//                {
//
//                }
//			}
//		});
	}

	/**
	 * @throws ReadWriteException
	 *         データベース接続で例外が発生した場合に通知します。
	 * @throws RemoteException
	 *         リモートメソッド呼び出しの実行中に発生する通信関連の例外 </jp>
	 */
	/**
	 * <en> Constructs a new instance of <code>ReArrangeAllocator</code>.
	 * @param mode


	/**
	 * <jp> 新しい<code>ReArrangeAllocator</code>のインスタンスを構築します。
	 * @param mode
	 *        起動モードを指定します。デバッグモードで起動する場合<CODE>true</CODE>をセットします。


	 *        :designate the start-up mode. Set <CODE>true</CODE> if starting by debug mode.
	 * @param agcNumber AGCNo.
	 * @throws ReadWriteException :
	 *         Notifies if exception occured during the database connection.
	 * @throws RemoteException
	 *         Exception related to communication generated while executing remote method call </en>
	 */
	public ReArrangeAllocator(boolean mode,String agcNumber ) throws ReadWriteException, RemoteException
	{
        _agcNo = agcNumber;
		$mode = mode;
		restRequest();
//		Runtime.getRuntime().addShutdownHook(new Thread()
//		{
//			public void run()
//			{
//				// 終了処理
//                try
//                {
//                    if (_overlapsFlag)
//                    {
//                        // 多重起動の場合は何もしない
//                        return;
//                    }
//
//                    _exitFlag = true;
//					closeConnection();
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME + _agcNo))
//                    {
//                        unbinder.unbind(OBJECT_NAME + _agcNo);
//                    }
//                }
//                catch (Exception e)
//                {
//
//                }
//			}
//		});
	}

	// Public methods ------------------------------------------------
	/**
	 * <jp> スレッドで<CODE>ReArrangeAllocator</CODE>を起動します。
	 * @param args 引数 </jp>
	 */
	/**
	 * <en> Starts <CODE>ReArrangeAllocator</CODE> using thread.
	 * @param args :
	 *        Arguments </en>
	 */
	public static void main(String[] args)
	{
		ReArrangeAllocator rearrange = null;

		// <jp> 配置替え引当時間監視処理</jp>
		ReArrangeTimeKeeper timekeeper = null;

		try
		{
			// <jp> 使用法</jp>
			// <en> Usage</en>
            if(args != null && args.length == 1
					&& (args[0].equals("-?") || args[0].equals("-help")))
			{
				println(
						"example of the usage  j jp.co.daifuku.asrs.communication.as21.ReArrangeAllocator 180000 true",
						true);
				println(
						"Sets 1st argument to be monitor time, 2nd argument to be start mode, (true:debug mode)",
						true);
				System.exit(0);
			}
			else if(WmsParam.MULTI_ASRSSERVER && args != null && args.length >= 2)
			{
				boolean mode = new Boolean(args[0]).booleanValue();
				// AGCNoの引数の分だけ起動
				for(int i = 1; i < args.length; i++)
				{
					System.out.println(i);
					rearrange = new ReArrangeAllocator(mode, args[i]);

					new Thread(rearrange).start();

					// <jp> 時間監視処理 起動</jp>
					// <en> Starts the time keeping.</en>
					timekeeper = new ReArrangeTimeKeeper(rearrange, args[i]);
					new Thread(timekeeper).start();
				}
			}
			else if(!WmsParam.MULTI_ASRSSERVER && args != null && args.length == 1)
			{
				rearrange = new ReArrangeAllocator(new Boolean(args[0]).booleanValue());

				new Thread(rearrange).start();

				// <jp> 時間監視処理 起動</jp>
				// <en> Starts the time keeping.</en>
				timekeeper = new ReArrangeTimeKeeper(rearrange);
				new Thread(timekeeper).start();
			}
			else if(!WmsParam.MULTI_ASRSSERVER)
			{
				rearrange = new ReArrangeAllocator();

				new Thread(rearrange).start();

				// <jp> 時間監視処理 起動</jp>
				// <en> Starts the time keeping.</en>
				timekeeper = new ReArrangeTimeKeeper(rearrange);
				new Thread(timekeeper).start();
			}
		}
		catch(Exception e)
		{
			println("\n Cannot start up." + String.valueOf(e) + "\n", true);
			if(rearrange != null)
			{
				// <jp> 6026064=例外が通知された為、異常終了します。タスク名={0}</jp>
				// <en> 6026064=Exception is notified. Abnormal ending. Task={0}</en>
				Object[] tobj = {"ReArrangeAllocator"};
				rearrange.logging(6026064, tobj);
				println("Due to the exceptions, it abnormally ends." + new java.util.Date(), true);
				e.printStackTrace();
			}
		}
	}

	/**
	 * <jp> 配置替え設定データが存在する場合は、配置替え引当処理を行います<BR>
	 * </jp>
	 */
	public void run()
	{
		int ret = 0;
		AsrsReArrangeOperator operator = null;

		try
		{
        	if(WmsParam.MULTI_ASRSSERVER)
        	{
				// <jp> レジストリに登録</jp>
				// <en> Register in the registo</en>
				this.bind("//" + RmiSendClient.RMI_REG_SERVER + _agcNo + "/" + OBJECT_NAME + _agcNo);
        	}
        	else
        	{
    			// <jp> レジストリに登録</jp>
    			// <en> Register in the registo</en>
    			this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);
        	}

			// <jp> 6020020=リクエスト監視処理を起動します。</jp>
			// <en> Start up the request monitoring process.</en>
			RmiMsgLogClient.write(6020020, "ReArrangeAllocator");
			println(
					this.getClass().getName()
							+ " Started !!  Start up the process of monitoring response to work mode switching.",
					true);

			// <jp> ずっと動き続けること。</jp>
			// <en> Repeat the following</en>
			while(_exitFlag == false)
			{
				try
				{
					if(getConnection() == null || getConnection().isClosed())
					{
						setConnection(WmsParam.getConnection());
						initHandler();
					}

					while(_exitFlag == false)
					{
						synchronized(this)
						{
							try
							{
								System.out.println("ReArrangeAllocator ::: WAIT!!!");
								// <jp> 要求が行なわれていない場合、wait状態にする。</jp>
								// <en> If no request is made, puts on wait state.</en>
								if(isRequest() == false)
								{
									this.wait();
								}
								// <jp> 要求フラグをリセットする。</jp>
								// <en> Resets the request flag.</en>
								restRequest();
								System.out.println("ReArrangeAllocator ::: Wake UP UP UP!!!");
                                if (_exitFlag)
                                {
                                    break;
                                }
							}
							catch(Exception e)
							{
								// <jp> エラーをログファイルに落とす</jp>
								// <en>Records errors in log file.</en>
								// <jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
								// <en> 6026043=Error occurred in transfer instruction task.
								// Exception:{0}</en>
								RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass()
										.getName());
							}
						}

						operator = new AsrsReArrangeOperator(getConnection(), this.getClass());

						// 負荷分散する場合
			        	if(WmsParam.MULTI_ASRSSERVER)
			        	{
							if(operator.reArrangeAllocate(_agcNo) > 0)
							{
								// commit.
								getConnection().commit();
							}
							else
							{
								getConnection().rollback();
								// 該当データが存在しない場合は少しSleep
								Thread.sleep(_sleepTime);
							}

							// 棚間移動データが作成されていた場合は搬送指示を送信する。
							// DoubleDeepRetrievalSender、AutomaticChangeSender両方から呼び出される可能性があるため
							// 最初に呼んだ側がそのデータをロックし同一の棚間指示を送信しないようにする
							CarryInfo[] carryMove = getRackMoveInfoForUpdate();
							if(carryMove.length > 0)
							{
								// 出庫指示の送信を行う。
								sendCarry(carryMove, null);
							}
							else
							{
								// 搬送データが無い時でもパレットと搬送情報をロックしてるので開放する。
								getConnection().rollback();
							}
			        	}
			        	// 負荷分散しない場合
			        	else
			        	{
							if(operator.reArrangeAllocate() > 0)
							{
								// commit.
								getConnection().commit();
							}
							else
							{
								getConnection().rollback();
								// 該当データが存在しない場合は少しSleep
								Thread.sleep(_sleepTime);
							}

							// 棚間移動データが作成されていた場合は搬送指示を送信する。
							// DoubleDeepRetrievalSender、AutomaticChangeSender両方から呼び出される可能性があるため
							// 最初に呼んだ側がそのデータをロックし同一の棚間指示を送信しないようにする
							CarryInfo[] carryMove = getRackMoveInfoForUpdate();
							if(carryMove.length > 0)
							{
								// 出庫指示の送信を行う。
								sendCarry(carryMove, null);
							}
							else
							{
								// 搬送データが無い時でもパレットと搬送情報をロックしてるので開放する。
								getConnection().rollback();
							}
			        	}
					}
				}
				catch(Exception e)
				{
					// <jp> 6016102=致命的なエラーが発生しました。{0}</jp>
					// <en> 6016102=A fatal error occurred. {0}</en>
					logging(6016102, e);
					Thread.sleep(3000);
				}
				finally
				{
					try
					{
						if(getConnection() != null)
						{
							getConnection().rollback();
							getConnection().close();
						}
					}
					catch(SQLException e)
					{
						// <jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
						// <en> 6007030=Database error occured. error code={0}</en>
						Object[] tobj = {String.valueOf(e.getErrorCode())};
						logging(6007030, tobj);
						setConnection(null);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ret = 1;
		}
		finally
		{
			System.out.println("ReArrangeAllocator::::finally!!");
			// <jp> 6020021=リクエスト監視処理を停止します。</jp>
			// <en> 6020021=Terminating request monitoring.</en>
			RmiMsgLogClient.write(6020021, "ReArrangeAllocator");
//			try
//			{
				this.unbind();
                DBUtil.close(getConnection());
				if(ret > 0)
				{
					// <jp> 6026064=例外が通知された為、異常終了します。タスク名={0}</jp>
					// <en> 6026064=Exception is notified. Abnormal ending. Task={0}</en>
					Object[] tobj = {"ReArrangeAllocator"};
					logging(6026064, tobj);
					println("As the exception has been notified, it is abnormally ending."
							+ new java.util.Date(), true);
				}
//			}
//			catch(SQLException e)
//			{
//				// <jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
//				// <en> 6007030=Database error occured. error code={0}</en>
//				Object[] tobj = {String.valueOf(e.getErrorCode())};
//				logging(6007030, tobj);
//				ret = 1;
//			}
//			finally
//			{
//				System.exit(ret);
//			}
		}
	}

	/**
	 * <jp> Wait中の本配置替え引当処理を起します。 </jp>
	 */
	/**
	 * <en> Activates hte carrying instruction on Wait state. </en>
	 */
	public synchronized void wakeup()
	{
		this.notify();
	}

	/**
	 * <jp> 使用されないメソッドです。上位で宣言されたメソッドを仮実装するために用意されています。
	 * @param params
	 *        使用されません。 </jp>
	 */
	/**
	 * <en> This method is unused. This is prepared only to virtually implement the method declared
	 * upperclass.
	 * @param params
	 *        Unused </en>
	 */
	public synchronized void write(Object[] params)
	{
		// <jp> 要求フラグをセットしておく</jp>
		// <en> Sets the request flag.</en>
		setRequest();
		// <jp> wait解除</jp>
		// <en> Cancels the wait state.</en>
		this.notify();
	}

	/**
	 * <jp> 処理を終了します。 外部よりこのメソッドを呼び出された場合、処理を終了します。 </jp>
	 */
	/**
	 * <en> Terminating the process. It terminates the process when it is called exrternally. </en>
	 */
	public synchronized void stop()
	{
        // <jp> スレッドのループが終了するように、フラグを更新する。</jp>
        // <en> Updates the flag so that the looping of this thread should discontine.</en>
        _exitFlag = true;
		// <jp> このスレッドの待ち状態を解除する。</jp>
		// <en> Cancels the wait state of this thread.</en>
		this.notify();
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * <jp> 出庫指示の条件チェックを行います。現在指示済みの搬送データ件数と搬送可能件数との比較を行い、搬送可能件数以下ならばtrue 搬送可能件数を超えていればfalseを返します。
	 * @param cInfo
	 *        搬送対象CarryInformation
	 * @param destSt
	 *        搬送先ステーション
	 * @return 搬送可能件数以下ならばtrue、搬送可能件数を超えていればfalseを返します。
	 * @throws Exception
	 *         データベースの読書きで障害が発生した場合に通知されます。 </jp>
	 */
	/**
	 * <en> Check the conditions of retrieval instructions. Compare the number of carrying data that
	 * instuctions have already been released and the MAX. number of carrying operations acceptable ;
	 * if the number of data is less than operationally available work volume, returns 'true'.
	 * Returns 'false' if the data exceeded the available operation number.
	 * @param cInfo
	 *        CarryInformation
	 * @param destSt
	 *        Receiving station
	 * @return 'true' if the data is less than MAX. operation work number to handle, or 'false' if
	 *         exceeded.
	 * @throws Exception
	 *         Notifies if trouble occured while reading database. </en>
	 */
	protected boolean destDetermin(CarryInfo cInfo, Station destSt) throws Exception
	{
		try
		{
			System.out.println("ReArrangeAllocator destDetermin()");

			// 出庫指示条件チェック
			// <jp> 今回出庫指示対象となるCarryInfornationの搬送区分によって</jp>
			// <jp> 出庫指示条件チェックを変える。</jp>
			// <en> The conditions of retrieval instrucitons to check should be changed depending on
			// the transport section of its</en>
			// <en> carrying information applicable.</en>
			if(CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(cInfo.getCarryFlag()))
			{
				// <jp> 棚間移動の場合、グループコントローラーの状態チェックのみ行う。</jp>
				// <jp> 搬送先となる棚の状態をチェックし搬送可能か判断する。</jp>
				// <en> In case of location to location move, check should be done only for hte
				// status of group controller.</en>
				// <en> By checking the location state of the receiving, determine whether/not the
				// carrying can be carried out.</en>
				Station station = StationFactory.makeStation(getConnection(), cInfo
						.getAisleStationNo());

				// <jp> グループコントローラーがオンライン以外の場合は搬送不可</jp>
				// <en> Carrying is not available if the group controller has no on-line
				// environment.</en>
				GroupController groupController = GroupController.getInstance(getConnection(),
						station.getControllerNo());
				if(groupController.getStatus() != GroupController.STATUS_ONLINE)
				{
					return false;
				}
			}
		}
		// Station定義が不正の場合に発生
		catch(InvalidDefineException e)
		{
			carryFailure(cInfo);
			return false;
		}
		// Station未定義の場合に発生
		catch(NotFoundException e)
		{
			carryFailure(cInfo);
			return false;
		}

		return true;
	}

	// Private methods -----------------------------------------------
	/**
	 * <jp> ログファイルに出力します。
	 * @param msgnum
	 *        メッセージ番号
	 * @param tobj
	 *        メッセージ・ログへ書き込むパラメータ </jp>
	 */
	/**
	 * <en> Outputting to the log file.
	 * @param msgnum
	 *        Message no.
	 * @param tobj
	 *        Parameter to write in log file </en>
	 */
	private void logging(int msgnum, Object[] tobj)
	{
		RmiMsgLogClient.write(msgnum, "ReArrangeAllocator", tobj);
	}

	/**
	 * <jp> ログファイルに出力します。
	 * @param msgnum
	 *        メッセージ番号
	 * @param e
	 *        通知された例外 </jp>
	 */
	/**
	 * <en> Outputting to the log file.
	 * @param msgnum
	 *        Message no.
	 * @param e
	 *        Exception notified </en>
	 */
	private void logging(int msgnum, Exception e)
	{
		Object[] tobj = {TraceHandler.getStackTrace(e)};
		RmiMsgLogClient.write(msgnum, "ReArrangeAllocator", tobj);
	}

	/**
	 * このクラスで保持しているコネクションをクローズします。 2006.10.03
	 * @author E.Takeda
	 */
	private void closeConnection()
	{
		try
		{
			if(getConnection() != null)
			{
				getConnection().rollback();
				getConnection().close();
			}
		}
		catch(SQLException e)
		{
			// 何もしない
		}
		// <jp> 6020021=リクエスト監視処理を停止します。</jp>
		// <en> 6020021=Terminating request monitoring.</en>
		RmiMsgLogClient.write(6020021, "ReArrangeAllocator");
	}
}
// end of class
