// $Id: Id26Process.java 4694 2009-07-16 01:18:10Z ota $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id26;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * 到着報告サブクラスを管理するクラスです。
 * 到着報告の処理を行うId26SubThreadをステーショングループ毎に起動します。
 * Id26mckeyよりCarryInformationを生成し、搬送区分を元に到着処理を行います。
 * mckeyがダミー到着を表す値の場合（通常は'99999999'）
 * ダミー到着処理を行います。
 * 実際の到着処理は到着報告テキスト内のステーションNoを元に生成された、Stationを継承したクラスによって行われます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4694 $, $Date: 2009-07-16 10:18:10 +0900 (木, 16 7 2009) $
 * @author  $Author: ota $
 </jp>*/
public class Id26Process
        extends IdProcess
{
    // Class fields --------------------------------------------------
    /**<jp>デフォルトのステーショングループNo. ("1")</jp>*/
    public static final String DEFAULT_STATION_GROUP_NO = "1";

    /**<jp>
     * ステーショングループ一覧
     </jp>*/
    private static final ArrayList STATION_GROUP_LIST = 
        WmsParam.STATION_GROUP_LIST
    ;

    /**<jp>
     * ファイル内の区切り文字 (ステーショングループNo.とステーションNo.間)
     </jp>*/
    private static final String DELM_GROUP_ST = ":";

    /**<jp>
     * ファイル内の区切り文字 (ステーションNo.とステーションNo.間)
     </jp>*/
    private static final String DELM_ST_ST = ".";

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーショングループ一覧を管理する変数
     </jp>*/
    private Hashtable<String, String> _stationGroup = new Hashtable<String, String>();

    /**<jp>
     * Id26SubThreadを管理する変数
     </jp>*/
    private Hashtable<String, Id26SubThread> _id26ThreadList = new Hashtable<String, Id26SubThread>();

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4694 $,$Date: 2009-07-16 10:18:10 +0900 (木, 16 7 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     * AGCNoにGroupController.DEFAULT_AGC_NUMBERをセット
     </jp>*/
    /**<en>
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     </en>*/
    public Id26Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNo AGCNo
     </en>*/
    public Id26Process(String agcNo)
    {
        super(agcNo);
        loadStationGroup();
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 到着報告の処理を行うId26SubThreadをステーショングループ毎に起動します。
     * グループが指定されていないステーションはデフォルトグループNo.で起動します。
     * 
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        //<jp> 受信テキストを分解</jp>
        //<en> Decomposition of received texts</en>
        As21Id26 id26dt = new As21Id26(rdt);
        String stationNo = id26dt.getStationNo();

        String groupNo = null;
        // ステーショングループ一覧にステーションが存在するかチェックする
        if (_stationGroup.containsKey(stationNo))
        {
            // ステーションよりグループNo.を取得する
            groupNo = _stationGroup.get(stationNo);
        }
        else
        {
            // ステーションが存在しない場合はデフォルトグループNo.を使用する
            groupNo = DEFAULT_STATION_GROUP_NO;
        }

        //<jp> グループ毎の起動処理</jp>
        execId26Thread(groupNo, rdt);
    }

    /**<jp>
     * ステーショングループ一覧をHashtableにセットする。
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合にThrowされます。
     </jp>*/
    protected void loadStationGroup()
    {
        // ステーショングループ一覧をHashtableにセットする
        for (int i = 0; i < STATION_GROUP_LIST.size() ; i++)
        {
            String line =String.valueOf(STATION_GROUP_LIST.get(i));
            
            if (line == null)
            {
                break;
            }

            StringTokenizer deftoken;
            StringTokenizer mtoken;

            //<jp> グループNo.取得</jp>
            mtoken = new StringTokenizer(line, DELM_GROUP_ST, false);
            String groupNo = mtoken.nextToken();

            //<jp> ステーションNo.取得</jp>
            deftoken = new StringTokenizer(mtoken.nextToken(), DELM_ST_ST, false);

            while (deftoken.hasMoreTokens())
            {
                String station = deftoken.nextToken();
                _stationGroup.put(station, groupNo);
            }
        }

        return;
    }

    /**<jp>
     * グループ毎の処理を起動し、受信データを渡して処理を依頼する。
     * @param no ステーショングループNo.
     * @param byteArray 受信電文対応処理へ渡す電文
     * @throws InvalidDefineException Id26SubThread以外のインスタンスが生成された場合に通知されます。
     </jp>*/
    protected void execId26Thread(String no, byte[] byteArray)
            throws InvalidDefineException
    {
        //<jp> HashTable中に既にインスタンスを作ってRUNさせているものが居ないかをチェックする。</jp>
        //<en> Checks whether/not any instance has been alreade created and running in HashTable.</en>
        Id26SubThread id26Thread = null;
        if (_id26ThreadList.containsKey(no))
        {
            //<jp> HashTableからデータを渡すスレッドへの参照を取り出す。</jp>
            //<en> Remove the reference to the thread that data is given from HashTable.</en>
            Object oId26Thread = _id26ThreadList.get(no);
            if (oId26Thread instanceof Id26SubThread)
            {
                id26Thread = (Id26SubThread)oId26Thread;
            }
            //<jp> 動作しているスレッドに対してデータを渡す。</jp>
            //<en> Passes data to the active thread.</en>
            id26Thread.write(byteArray);
        }
        else
        {
            //<jp> インスタンスがRUNしていなければ、作成してRUNさせる。</jp>
            //<en> If there is no instance running, generate one and let run.</en>
            id26Thread = firstExecId26Thread(no, byteArray);
            //<jp> ID毎に生成されるインスタンスをHashTableへ登録</jp>
            //<en> Registers the instances, generated for each ID, to the HashTable</en>
            _id26ThreadList.put(no, id26Thread);
        }
    }

    /**<jp>
     * Id26SubThreadの処理を初回のみ起動し、受信データを渡して処理を依頼する。
     * @param no ステーショングループNo.
     * @param bytedata 受信電文対応処理へ渡す電文
     * @return Id26SubThreadインスタンス
     </jp>*/
    protected Id26SubThread firstExecId26Thread(String no, byte[] bytedata)
    {
        Id26SubThread id26Thread = new Id26SubThread(getAgcNo(), no);

        //<jp> Id26SubThreadを起動する。</jp>
        id26Thread.start();
        //<jp> Id26SubThreadのwriteメソッドを呼び出してデータを渡す。</jp>
        id26Thread.write(bytedata);

        return (id26Thread);
    }

    // Private methods -----------------------------------------------

}
//end of class

