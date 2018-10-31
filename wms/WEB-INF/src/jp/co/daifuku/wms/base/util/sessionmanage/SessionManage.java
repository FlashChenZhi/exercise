package jp.co.daifuku.wms.base.util.sessionmanage;

/**
 * 
 * TODO This is default class comment.<br>
 * 
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author 060527
 * @author Last commit: $Author: admin $
 */
public class SessionManage {
	static {
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("SessionManageImpl");
	}

	private native String getSessionList(String serverName);

	private native int sendMessage(String serverName, String msg,
			String sessionId);

	private native int logoff(String serverName, String sessionId);

	private native int disconnect(String serverName, String sessionId);

	private native int shutdownSystem(String serverName);

	public String[][] wtsGetSessionList(String serverName) {

		String[][] data = getSessionData(serverName);
		return data;
	}

	public int wtsSendMessage(String serverName, String msg, String sessionId) {

		int nResult = sendMessage(serverName, msg, sessionId);
		return nResult;
	}

	public int wtsLogoff(String serverName, String sessionId) {
		return logoff(serverName, sessionId);
	}

	public int wtsDisconnect(String serverName, String sessionId) {
		return disconnect(serverName, sessionId);
	}

	public int wtsShutdownSystem(String serverName) {
		return shutdownSystem(serverName);
	}

	private String[][] getSessionData(String serverName) 
	{
		String[][] data = null;
		String output = getSessionList(serverName);
		System.out.println("the output value:" + output);
		if (!"".equals(output)) {
			String[] all = output.split(",");
			for (int i = 0; i < all.length; i++) 
			{
				String[] one = all[i].split("::");
				if (i == 0) {
					// data： セッションID、ユーザ名、クライアント名、状態、IPアドレス、号機No
					data = new String[all.length][6];
				}
				if (one.length == 6) 
				{
					String[] info = one[5].split(" ");
					if (info.length > 1) 
					{
						one[5] = info[1];
					} else 
					{
						one[5] = "";
					}
				}
				data[i] = one;
			}
		}

		return data;
	}

}
