#include <atlbase.h>
#include <comutil.h>
#include <jni.h>
#include <stdio.h>
#include <windows.h>
#include <WtsApi32.h>
#include "jp_co_daifuku_wms_base_util_sessionmanage_SessionManage.h"

DWORD GetUsersNames(BSTR ServerName,  char* UsersNamesOut);
DWORD SendTSMessage(BSTR ServerName, BSTR Message, BSTR SessionID, DWORD * Resp);
DWORD LogOffTSSession(BSTR ServerName, BSTR SessionID);
DWORD DisconnectTSSession(BSTR ServerName, BSTR SessionID);
DWORD ShutDownSystem(BSTR ServerName);
jstring WindowsTojstring( JNIEnv* env, char* str );

//getSessionList
extern "C" JNIEXPORT jstring JNICALL 
Java_jp_co_daifuku_wms_base_util_sessionmanage_SessionManage_getSessionList(JNIEnv* env, 
jobject, jstring jServerName) {
  const char* serverName=env->GetStringUTFChars(jServerName,0);
  printf("input:%s\n", serverName);
  char* names = "a";

  BSTR bstrServerName = _com_util::ConvertStringToBSTR(serverName);
  printf("terminal server name: %s\n", serverName);
  char UsersNamesOut[10200];
  DWORD retGetUserNames = GetUsersNames(bstrServerName,UsersNamesOut);
  jstring returnvalue = WindowsTojstring(env,"");
  if (retGetUserNames == S_OK)
  {
    printf("return terminal server session user names: %s\n", UsersNamesOut);
    returnvalue = WindowsTojstring(env,UsersNamesOut);
  }
  env->ReleaseStringUTFChars(jServerName,serverName);
  return returnvalue;
}

jstring WindowsTojstring( JNIEnv* env, char* str )
{
    jstring rtn = 0;
    int slen = strlen(str);
    unsigned short * buffer = 0;
    if( slen == 0 )
        rtn = (env)->NewStringUTF(str ); 
    else
    {
        int length = MultiByteToWideChar( CP_ACP, 0, (LPCSTR)str, slen, NULL, 0 );
        buffer = (unsigned short *)malloc( length*2 + 1 );
        if( MultiByteToWideChar( CP_ACP, 0, (LPCSTR)str, slen, (LPWSTR)buffer, length ) >0 )
            rtn = (env)->NewString(  (jchar*)buffer, length );
    }
    if( buffer )
        free( buffer );
    return rtn;
}

extern "C" JNIEXPORT jint JNICALL Java_jp_co_daifuku_wms_base_util_sessionmanage_SessionManage_sendMessage
  (JNIEnv* env, jobject, jstring jServerName, jstring jMsg, jstring jSessionId) {
  int result = 0;
  
  const char* serverName=env->GetStringUTFChars(jServerName,0);

  BSTR bstrServerName = _com_util::ConvertStringToBSTR(serverName);
  const char* msg=env->GetStringUTFChars(jMsg,0);

///////////////////////////////////////////////////////////////////
//Resolve Double Chars Format
//////////////////////////////////////////////////////////////////
  int length = (env)->GetStringLength(jMsg );
  const jchar *jcstr = (env)->GetStringChars(jMsg, 0 );
  char* chrBuffer = (char*)malloc( length*2+1 );
  int size = 0;
  size = WideCharToMultiByte( CP_ACP, 0, (LPCWSTR)jcstr, length, chrBuffer,(length*2+1), NULL, NULL );
  if( size <= 0 )  return -1; //Failed To Convert,Return Directly
  
  (env)->ReleaseStringChars(jMsg, jcstr );
  chrBuffer[size] = 0; //End Of C++ String Token;
	
  //Convert C++ String To BSTR Format 
  BSTR bstrMsg = _com_util::ConvertStringToBSTR(chrBuffer);
  
  /////////////////////////////////////////////////////////////
  
  
  const char* sessionId=env->GetStringUTFChars(jSessionId,0);
  BSTR bstrSessionId = _com_util::ConvertStringToBSTR(sessionId);
 
  DWORD  ret=0;
  DWORD ret1 = SendTSMessage(bstrServerName,bstrMsg,bstrSessionId,&ret);

  env->ReleaseStringUTFChars(jServerName,serverName);
  env->ReleaseStringUTFChars(jMsg,chrBuffer);
  env->ReleaseStringUTFChars(jSessionId,sessionId);
  
  return ret1;
}

extern "C" JNIEXPORT jint JNICALL Java_jp_co_daifuku_wms_base_util_sessionmanage_SessionManage_logoff
  (JNIEnv* env, jobject, jstring jServerName, jstring jSessionId) {
  int result = 0;

  const char* serverName=env->GetStringUTFChars(jServerName,0);
  BSTR bstrServerName = _com_util::ConvertStringToBSTR(serverName);
  const char* sessionId=env->GetStringUTFChars(jSessionId,0);
  BSTR bstrSessionId = _com_util::ConvertStringToBSTR(sessionId);
  printf("terminal server name: %s\n", serverName);
  printf("session id: %s\n", sessionId);
  DWORD ret1 = LogOffTSSession(bstrServerName,bstrSessionId);
  env->ReleaseStringUTFChars(jServerName,serverName);
  env->ReleaseStringUTFChars(jSessionId,sessionId);
  return ret1;
}

extern "C" JNIEXPORT jint JNICALL Java_jp_co_daifuku_wms_base_util_sessionmanage_SessionManage_disconnect
  (JNIEnv* env, jobject, jstring jServerName, jstring jSessionId) {
  int result = 0;
  const char* serverName=env->GetStringUTFChars(jServerName,0);
  BSTR bstrServerName = _com_util::ConvertStringToBSTR(serverName);
  const char* sessionId=env->GetStringUTFChars(jSessionId,0);
  BSTR bstrSessionId = _com_util::ConvertStringToBSTR(sessionId);
  printf("terminal server name: %s\n", serverName);
  printf("session id: %s\n", sessionId);
  DWORD ret1 = DisconnectTSSession(bstrServerName,bstrSessionId);
  env->ReleaseStringUTFChars(jServerName,serverName);
  env->ReleaseStringUTFChars(jSessionId,sessionId);
  return ret1;
}

extern "C" JNIEXPORT jint JNICALL Java_jp_co_daifuku_wms_base_util_sessionmanage_SessionManage_shutdownSystem
  (JNIEnv* env, jobject, jstring jServerName) {
  int result = 0;
  const char* serverName=env->GetStringUTFChars(jServerName,0);
  BSTR bstrServerName = _com_util::ConvertStringToBSTR(serverName);
  printf("terminal server name: %s\n", serverName);
  DWORD ret1 = ShutDownSystem(bstrServerName);
  env->ReleaseStringUTFChars(jServerName,serverName);
  return ret1;
}

DWORD ShutDownSystem(BSTR ServerName)
{
	// TODO: Add your implementation code here


	HANDLE hOpenTS;
	
	char SrvName[256] = "";
	int convertresult;
	convertresult = WideCharToMultiByte(NULL, NULL, ServerName,-1,SrvName,sizeof(SrvName), NULL, NULL); 

	if (convertresult != 0)
	{
	
		BOOL bWTSShutdownSystemSession;
	
		//log off the client based on the server name and session id passed
		// passing FALSE so we return to the script immediately.. if you pass true the the script will appear to hang
	
	    hOpenTS = WTSOpenServer(SrvName);
	    if(hOpenTS != NULL)
	    {
			bWTSShutdownSystemSession = WTSShutdownSystem(hOpenTS, WTS_WSD_REBOOT);
		
			if (bWTSShutdownSystemSession != 0)
			{
				//printf("Reponse received: %d\n", dwResp );
				
		        WTSCloseServer(hOpenTS);
				return S_OK;
			}
			else
			{
	        	WTSCloseServer(hOpenTS);
				DWORD dwErr;
				dwErr = GetLastError();
				return dwErr;
			}
	    }//if(hOpenTS != NULL)
	    else
	    {		
	      //WTSCloseServer( hOpenTS);
	      return (GetLastError());
	    }

	}//if (convertresult != 0)
	else
	{
		DWORD dwErr;
		dwErr = GetLastError();
		return dwErr;
	}

}

DWORD DisconnectTSSession(BSTR ServerName, BSTR SessionID)
{
	// TODO: Add your implementation code here


	HANDLE hOpenTS;
	
	char SrvName[256] = "";
	int convertresult;
	convertresult = WideCharToMultiByte(NULL, NULL, ServerName,-1,SrvName,sizeof(SrvName), NULL, NULL); 

	char SessID[256] = "";
	int convertresult1;
	convertresult1 = WideCharToMultiByte(NULL, NULL, SessionID,-1,SessID,sizeof(SessID), NULL, NULL); 

					
	int numsessid;
	numsessid = atoi( SessID );


	if ((convertresult != 0) && (convertresult1 != 0))
	{
	
		BOOL bWTSLogoffSession;
	
		//log off the client based on the server name and session id passed
		// passing FALSE so we return to the script immediately.. if you pass true the the script will appear to hang
	
	    hOpenTS = WTSOpenServer(SrvName);
	    if(hOpenTS != NULL)
	    {
			bWTSLogoffSession = WTSDisconnectSession(hOpenTS, numsessid, FALSE);
		
			if (bWTSLogoffSession != 0)
			{
				//printf("Reponse received: %d\n", dwResp );
				
		        WTSCloseServer(hOpenTS);
				return S_OK;
			}
			else
			{
	       		WTSCloseServer(hOpenTS);
				DWORD dwErr;
				dwErr = GetLastError();
				return dwErr;
			}
	    }//if(hOpenTS != NULL)
	    else
	    {		
	      //WTSCloseServer( hOpenTS);
	      return (GetLastError());
	    }

	}//if (convertresult != 0)
	else
	{
		DWORD dwErr;
		dwErr = GetLastError();
		return dwErr;
	}

}

DWORD LogOffTSSession(BSTR ServerName, BSTR SessionID)
{
	// TODO: Add your implementation code here


	HANDLE hOpenTS;
	
	char SrvName[256] = "";
	int convertresult;
	convertresult = WideCharToMultiByte(NULL, NULL, ServerName,-1,SrvName,sizeof(SrvName), NULL, NULL); 

	char SessID[256] = "";
	int convertresult1;
	convertresult1 = WideCharToMultiByte(NULL, NULL, SessionID,-1,SessID,sizeof(SessID), NULL, NULL); 

					
	int numsessid;
	numsessid = atoi( SessID );


	if ((convertresult != 0) && (convertresult1 != 0))
	{
	
		BOOL bWTSLogoffSession;
	
		//log off the client based on the server name and session id passed
		// passing FALSE so we return to the script immediately.. if you pass true the the script will appear to hang
	
	    hOpenTS = WTSOpenServer(SrvName);
	    if(hOpenTS != NULL)
	    {
			bWTSLogoffSession = WTSLogoffSession(hOpenTS, numsessid, TRUE);
		
			if (bWTSLogoffSession != 0)
			{
				//printf("Reponse received: %d\n", dwResp );
				
		       	WTSCloseServer(hOpenTS);
				return S_OK;
			}
			else
			{
	       		WTSCloseServer(hOpenTS);
				DWORD dwErr;
				dwErr = GetLastError();
				return dwErr;
			}
	    }//if(hOpenTS != NULL)
	    else
	    {		
	      //WTSCloseServer( hOpenTS);
	      return (GetLastError());
	    }

	}//if (convertresult != 0)
	else
	{
		DWORD dwErr;
		dwErr = GetLastError();
		return dwErr;
	}

}

DWORD SendTSMessage(BSTR ServerName, BSTR Message, BSTR SessionID, DWORD * Resp)
{
	// TODO: Add your implementation code here

	HANDLE hOpenTS;

	//convert server name
	char SrvName[256] = "";
	int convertresult;
	convertresult = WideCharToMultiByte(NULL, NULL, ServerName,-1,SrvName,sizeof(SrvName), NULL, NULL); 

	//convert Message name
	char Msg[256] = "";
	int convertresult1;
	convertresult1 = WideCharToMultiByte(NULL, NULL, Message,-1,Msg,sizeof(Msg), NULL, NULL); 

	//convert Session ID name
	char SessID[256] = "";
	int convertresult2;
	convertresult2 = WideCharToMultiByte(NULL, NULL, SessionID,-1,SessID,sizeof(SessID), NULL, NULL); 
	int numsessid;
	numsessid = atoi( SessID );
	
	if (convertresult != 0)
	{

		BOOL bWTSSendMsg;
		char WindowTitle[] = "WTSMsg";

	    hOpenTS = WTSOpenServer(SrvName);
	    if(hOpenTS != NULL)
	    {
			bWTSSendMsg = WTSSendMessage(hOpenTS,numsessid, WindowTitle, sizeof(WindowTitle), Msg, sizeof(Msg), MB_OK, 0x20, (unsigned long *)Resp, FALSE);
	
	
			//printf("message Sent Waiting on reponse\n");
			if (bWTSSendMsg != 0)
			{
				//printf("Reponse received: %d\n", dwResp );
				
		       	WTSCloseServer(hOpenTS);
				return S_OK;
			}
			else
			{
		       	WTSCloseServer(hOpenTS);
				DWORD dwErr;
				dwErr = GetLastError();
				return dwErr;
			}
	    }//if(hOpenTS != NULL)
	    else
	    {		
	      //WTSCloseServer( hOpenTS);
	      return (GetLastError());
	    }

	}//if (convertresult != 0)
	else
	{
		DWORD dwErr;
		dwErr = GetLastError();
		return dwErr;
	}
}

DWORD GetUsersNames(BSTR ServerName,  char* UsersNamesOut)
{
  HANDLE hOpenTS;
  CComBSTR UsersNames;
  char SrvName[256] = "";
  
  int convertresult;
  convertresult = WideCharToMultiByte(NULL, NULL, ServerName,-1,SrvName,sizeof(SrvName), NULL, NULL); 

  if (convertresult != 0)
  {
    // open a handle to the server
    hOpenTS = WTSOpenServer(SrvName);
    if(hOpenTS != NULL)
    {
      PWTS_SESSION_INFO ppSessionInfo;
      DWORD dwPCount;
      BOOL bWTSEnumSess;
      BOOL bWTSQuerySess;
      LPTSTR ppBuffer = NULL;
	  LPTSTR ppBuffer1 = NULL;
	  LPTSTR ppBuffer2 = NULL;
	  LPTSTR ppBuffer3 = NULL;
	  LPTSTR ppBuffer4 = NULL;
      DWORD dwppBuffer = 0 ;
	  PWTS_CLIENT_ADDRESS pClientAddr;

      bWTSEnumSess = WTSEnumerateSessions(hOpenTS, 0,1, &ppSessionInfo, &dwPCount);
      if(bWTSEnumSess != 0)
      {
		memset(UsersNamesOut, 0, 10200);
        // for each session
        for(int i = 0; i < dwPCount; i++)
        {  
          DWORD sID = ppSessionInfo->SessionId;
		  // session is not console
          if (sID<65536 && sID > 0)
          {
            bWTSQuerySess = WTSQuerySessionInformation(hOpenTS, sID, WTSUserName, &ppBuffer, &dwppBuffer);
            DWORD dwErr = GetLastError();
            // if query session succeeded and the session is found
            // Q186548 Terminal Server Error Messages: 3900 to 7999
            if((bWTSQuerySess != 0) && (dwErr != 7022))
            {
			  char* buffer = new char[256];
              _itoa( sID, buffer, 10 );
			  strcat(UsersNamesOut,buffer);
			  strcat(UsersNamesOut,"::");
			  strcat(UsersNamesOut,ppBuffer);
			  strcat(UsersNamesOut,"::");
			}else{
			  char* buffer = new char[256];
              _itoa( sID, buffer, 10 );
			  strcat(UsersNamesOut,buffer);
			  strcat(UsersNamesOut,"::");
			  strcat(UsersNamesOut,"::");
			}
			bWTSQuerySess = WTSQuerySessionInformation(hOpenTS, sID, WTSClientName, &ppBuffer1, &dwppBuffer);
			dwErr = GetLastError();
			// if query session succeeded and the session is found
			// Q186548 Terminal Server Error Messages: 3900 to 7999
			if((bWTSQuerySess != 0) && (dwErr != 7022))
			{
			  strcat(UsersNamesOut,ppBuffer1);
			  strcat(UsersNamesOut,"::");
			}else{
			  strcat(UsersNamesOut,"::");
			}
			bWTSQuerySess = WTSQuerySessionInformation(hOpenTS, sID, WTSConnectState, &ppBuffer2, &dwppBuffer);
			dwErr = GetLastError();
			// if query session succeeded and the session is found
			// Q186548 Terminal Server Error Messages: 3900 to 7999
			if((bWTSQuerySess != 0) && (dwErr != 7022))
			{
				char* status = new char[256];
				switch((*ppBuffer2))
				{
				case 0:
					status="WTSActive";
					break;
				case 1:
					status="WTSConnected";
					break;
				case 2:
					status="WTSConnectQuery";
					break;
				case 3:
					status="WTSShadow";
					break;
				case 4:
					status="WTSDisconnected";
					break;
				case 5:
					status="WTSIdle";
					break;
				case 6:
					status="WTSListen";
					break;
				case 7:
					status="WTSReset";
					break;
				case 8:
					status="WTSDown";
					break;
				case 9:
					status="WTSInit";
					break;
				default:
					status="State unknown";
				}
			    strcat(UsersNamesOut,status);
			    strcat(UsersNamesOut,"::");
			}else{
			    strcat(UsersNamesOut,"::");
			}
			bWTSQuerySess = WTSQuerySessionInformation(hOpenTS, sID, WTSClientAddress, &ppBuffer3, &dwppBuffer);
			dwErr = GetLastError();

			// if query session succeeded and the session is found
			// Q186548 Terminal Server Error Messages: 3900 to 7999
			if((bWTSQuerySess != 0) && (dwErr != 7022))
			{
		
				pClientAddr = (_WTS_CLIENT_ADDRESS *)ppBuffer3;

				//char buffer[20];
				char clientip[20] = "";
				char clientip0[20]= "";
				char clientip1[20]= "";
				char clientip2[20]= "";
				char clientip3[20]= "";

				wsprintf(clientip0,"%u.",pClientAddr->Address[2]);
				strcat(clientip, clientip0);
				wsprintf(clientip1,"%u.",pClientAddr->Address[3] );
				strcat(clientip,clientip1);
				wsprintf(clientip2,"%u.",pClientAddr->Address[4] );
				strcat(clientip,clientip2);
				wsprintf(clientip3,"%u",pClientAddr->Address[5]);
				strcat(clientip,clientip3);
			    strcat(UsersNamesOut,clientip);
			    strcat(UsersNamesOut,"::");
			}else{
			    strcat(UsersNamesOut,"::");
			}

			bWTSQuerySess = WTSQuerySessionInformation(hOpenTS, sID, WTSInitialProgram, &ppBuffer4, &dwppBuffer);
			dwErr = GetLastError();
			// if query session succeeded and the session is found
			// Q186548 Terminal Server Error Messages: 3900 to 7999
			if((bWTSQuerySess != 0) && (dwErr != 7022))
			{
			  strcat(UsersNamesOut,ppBuffer4);
			  strcat(UsersNamesOut,",");
			}else{
			  strcat(UsersNamesOut,",");
			}
          }
          ppSessionInfo++;
        }//for
      }//if(bWTSEnumSess != 0)
      else
      {
        //assign for return
        //*UsersNamesOut = UsersNames.Detach();
        WTSFreeMemory(ppBuffer);
		WTSFreeMemory(ppBuffer1);
		WTSFreeMemory(ppBuffer2);
		WTSFreeMemory(ppBuffer3);
		WTSFreeMemory(ppBuffer4);
        WTSCloseServer(hOpenTS);
        return (GetLastError());
      }// everything worked just fine.. clean up and say Okay..
      //assign for return
      //*UsersNamesOut = UsersNames.Detach();
      WTSFreeMemory(ppBuffer);
	  WTSFreeMemory(ppBuffer1);
	  WTSFreeMemory(ppBuffer2);
	  WTSFreeMemory(ppBuffer3);
	  WTSFreeMemory(ppBuffer4);
      WTSFreeMemory(ppSessionInfo);
      WTSCloseServer(hOpenTS);
      return S_OK;
    }//if(hOpenTS != NULL)
    else
    {		
      //WTSCloseServer( hOpenTS);
      return (GetLastError());
    }
  }//if (convertresult != 0)
  else
  {
    DWORD dwErr;
    dwErr = GetLastError();
    return dwErr;
  }
}