// $Id: Id71Process.java 8004 2011-08-30 00:51:30Z kishimoto $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id71;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.AccessNgShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.AccessNgShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfFinder;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.AccessNgShelf;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * Access不可棚報告を処理するクラスです。指定された棚の範囲に該当するShelfのアクセス状態フラグを
 * アクセス可又はアクセス不可に変更します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8004 $, $Date: 2011-08-30 09:51:30 +0900 (火, 30 8 2011) $
 * @author  $Author: kishimoto $
 </jp>*/
public class Id71Process
        extends IdProcess
{
    /**<jp>
     * Access不可解除を表す。
     </jp>*/
    /**<en>
     * Canceling the inaccessible state.
     </en>*/
    private static final String A_IMPOSSIBLE_CANCEL = "0";

    /**<jp>
     * Access不可を表す。
     </jp>*/
    /**<en>
     * State of inaccessibility
     </en>*/
    private static final String ACCESS_IMPOSSIBLE = "1";

    /**
     * オンラインチェックのリトライ回数
     */
    private static final int RETRY_COUNT = 10;

    /**
     * オンラインチェック間のスリープ時間
     */
    private static final int SLEEP_TIME = 1000;

    // Class variables -----------------------------------------------

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
        return ("$Revision: 8004 $,$Date: 2011-08-30 09:51:30 +0900 (火, 30 8 2011) $");
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
    public Id71Process()
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
    public Id71Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * Access不可棚報告電文を処理します。
     * 受信した電文の内容から、<code>Shelf</code>を検索しアクセス不可棚フラグを更新します。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、
     * 呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the communication message of inaccessible location report.
     * Based on the received communication message, search <code>Shelf</code> 
     * then updates the inaccessible location flag.
     * However the call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id71 id71dt = new As21Id71(rdt);

        // update start 2011.08.29 オンラインになるまで待機する
        boolean isOnline = false;
        for (int i = 0; i < RETRY_COUNT; i++)
        {
            if (GroupController.isOnLine(getConnection(), getAgcNo()))
            {
                isOnline = true;
                break;
            }

            // オンライン以外なら、少しスリープして再チェックする
            Thread.sleep(SLEEP_TIME);
        }

        if (!isOnline)
        {
            // オンラインにならなかった場合は、中断する
            // 6024072=システムがオフラインのため、アクセス不可棚報告の処理を中断しました。
            RmiMsgLogClient.write(6024072, LogMessage.F_WARN, getClass().getName());
            return;
        }
        // update end 2011.08.29 オンラインになるまで待機する

        //<jp> WareHouseHandler のインスタンス生成</jp>
        //<en> Generate the instance of WareHouseHandler.</en>
        WareHouseHandler wHouseHand = new WareHouseHandler(getConnection());

        //<jp> WareHouseSearchKey のインスタンス生成</jp>
        //<en> Generate the instance of WareHouseSearchKey.</en>
        WareHouseSearchKey whkey = new WareHouseSearchKey();

        //<jp> ShelfHandler のインスタンス生成</jp>
        //<en> Generate the instance of ShelfHandler.</en>
        ShelfHandler shelfHand = new ShelfHandler(getConnection());
        ShelfFinder shelfFind = new ShelfFinder(getConnection());
        ShelfAlterKey shlfAKey = new ShelfAlterKey();
        ShelfSearchKey shlfSKey = new ShelfSearchKey();

        //<jp> AccessNgShelfHandler のインスタンス生成</jp>
        //<en> Generate the instance of AccessNgShelfHandler.</en>
        AccessNgShelfHandler ansHand = new AccessNgShelfHandler(getConnection());

        //<jp> AccessNgShelfSearchKey のインスタンス生成</jp>
        //<en> Generate the instance of AccessNgShelfSearchKey.</en>
        AccessNgShelfSearchKey anskey = new AccessNgShelfSearchKey();

        //<jp> 完了データ数分処理</jp>
        //<en> Processes as much as completion data.</en>
        int numberOfReports = id71dt.getNumberOfReports();
        
        // 報告件数が0件の場合は全棚アクセス不可解除を行う
        if (numberOfReports == 0)
        {
            // AGCNo.から更新する範囲を取得します。
            AisleSearchKey askey = new AisleSearchKey();
            AisleHandler ahandler = new AisleHandler(getConnection());
            askey.setControllerNo(getAgcNo());
            Aisle[] aisles = (Aisle[])ahandler.find(askey);
            
            for (Aisle aisle : aisles)
            {   
                shlfAKey.clear();
                shlfAKey.setParentStationNo(aisle.getStationNo());

                //<jp> Access不可解除</jp>
                //<en> Canceling the inaccessible status</en>
                shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);

                shelfHand.modify(shlfAKey);
            }
            
            return;
        }
            
        for (int i = 0; i < numberOfReports; i++)
        {
            // 状態チェック
            if (!id71dt.getCondition(i).equals(A_IMPOSSIBLE_CANCEL)
                    && !id71dt.getCondition(i).equals(ACCESS_IMPOSSIBLE))
            {
                //<jp> 6024017=作業完了報告テキスト内の完了区分が不正です。完了区分={0} mckey={1}</jp>
                //<en> 6024017=Invalid completion category. (Work Completion Report text) Category={0} mckey={1}</en>
                Object[] tObj = {
                    id71dt.getCondition(i),
                };
                RmiMsgLogClient.write(6024017, LogMessage.F_WARN, getClass().getName(), tObj);
                throw new InvalidProtocolException(WmsMessageFormatter.format(6024017, tObj));
            }


            //<jp> 格納区分（倉庫番号）をintに変換</jp>
            //<en> Translate the storage division (warehouse no.) to int.</en>
            String stClass = id71dt.getStatusClass(i);

            //<jp> 格納区分（倉庫番号）をKeyに検索</jp>
            //<en> Search by using the storage division(warehous no.) as a Key</en>
            whkey.clear();
            whkey.setWarehouseNo(stClass);
            WareHouse[] wHouseInfo = (WareHouse[])wHouseHand.find(whkey);

            //<jp> Keyにより検索されるStationNumberを取得</jp>
            //<en> Retrieve the StationNumber serched by the Key.</en>
            String wareHouse = wHouseInfo[0].getStationNo();

            // フリーアロケーション倉庫の場合
            if (WareHouse.FREE_ALLOCATION_ON.equals(wHouseInfo[0].getFreeAllocationType()))
            {
                // 棚情報をロック
                shlfSKey.clear();
                shlfSKey.setWhStationNo(wareHouse);
                shlfSKey.setBankNo(Integer.parseInt(id71dt.getBankNo(i)));
                shlfSKey.setBayNo(Integer.parseInt(id71dt.getStartBay(i)), ">=");
                shlfSKey.setBayNo(Integer.parseInt(id71dt.getEndBay(i)), "<=");
                shlfSKey.setLevelNo(Integer.parseInt(id71dt.getStLevelNo(i)), ">=");
                shlfSKey.setLevelNo(Integer.parseInt(id71dt.getEnLevelNo(i)), "<=");
                shelfHand.lock(shlfSKey);

                // 荷幅フリーの棚情報を更新
                shlfAKey.clear();
                shlfAKey.setBankNo(Integer.parseInt(id71dt.getBankNo(i)));
                shlfAKey.setBayNo(Integer.parseInt(id71dt.getStartBay(i)), ">=");
                shlfAKey.setBayNo(Integer.parseInt(id71dt.getEndBay(i)), "<=");
                shlfAKey.setLevelNo(Integer.parseInt(id71dt.getStLevelNo(i)), ">=");
                shlfAKey.setLevelNo(Integer.parseInt(id71dt.getEnLevelNo(i)), "<=");
                shlfAKey.setWhStationNo(wareHouse);
                shlfAKey.setWidth(Shelf.WIDTH_FREE);

                if (id71dt.getCondition(i).equals(A_IMPOSSIBLE_CANCEL))
                {
                    //<jp> Access不可解除</jp>
                    //<en> Canceling the inaccessible status</en>
                    shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
                }
                else if (id71dt.getCondition(i).equals(ACCESS_IMPOSSIBLE))
                {
                    //<jp> Access不可</jp>
                    //<en> Inaccessible</en>
                    shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_NG);
                }
                try
                {
                    shelfHand.modify(shlfAKey);
                }
                catch (NotFoundException e)
                {
                    // データなしでもエラーとしない
                }

                // 荷幅フリー以外の棚を検索（バンク、ベイ、レベル単位）
                anskey.clear();
                shlfSKey.clear();
                shlfSKey.setBankNoCollect();
                shlfSKey.setBayNoCollect();
                shlfSKey.setLevelNoCollect();
                shlfSKey.setWidthCollect("MAX");
                shlfSKey.setWhStationNo(wareHouse);
                shlfSKey.setBankNo(Integer.parseInt(id71dt.getBankNo(i)));
                shlfSKey.setBayNo(Integer.parseInt(id71dt.getStartBay(i)), ">=");
                shlfSKey.setBayNo(Integer.parseInt(id71dt.getEndBay(i)), "<=");
                shlfSKey.setLevelNo(Integer.parseInt(id71dt.getStLevelNo(i)), ">=");
                shlfSKey.setLevelNo(Integer.parseInt(id71dt.getEnLevelNo(i)), "<=");
                shlfSKey.setWidth(Shelf.WIDTH_FREE, "!=");
                shlfSKey.setBankNoGroup();
                shlfSKey.setBayNoGroup();
                shlfSKey.setLevelNoGroup();

                shelfFind.open(true);
                int num = shelfFind.search(shlfSKey);
                if (0 == num)
                {
                    continue;
                }

                // 荷幅が設定されている場合はアクセス不可棚情報を検索し、
                // アクセス不可とするアドレスのみ更新する
                while (shelfFind.hasNext())
                {
                    Shelf[] shelves = (Shelf[])shelfFind.getEntities(100);

                    for (Shelf shelf : shelves)
                    {
                        // 荷幅フリー以外の棚情報を更新するためのキーをセットする
                        shlfAKey.clear();
                        shlfAKey.setBankNo(shelf.getBankNo());
                        shlfAKey.setBayNo(shelf.getBayNo());
                        shlfAKey.setLevelNo(shelf.getLevelNo());
                        shlfAKey.setWhStationNo(wareHouse);
                        if (id71dt.getCondition(i).equals(A_IMPOSSIBLE_CANCEL))
                        {
                            //<jp> Access不可解除</jp>
                            //<en> Canceling the inaccessible status</en>
                            shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
                        }
                        else if (id71dt.getCondition(i).equals(ACCESS_IMPOSSIBLE))
                        {
                            //<jp> Access不可</jp>
                            //<en> Inaccessible</en>
                            shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_NG);
                        }

                        // アクセス不可棚情報の検索
                        // バンク、ベイ、レベルに0が指定されている場合は全値有効
                        anskey.clear();
                        anskey.setWhStationNo(wareHouse);
                        anskey.setBankNo(0, "=", "(", "", false);
                        anskey.setBankNo(shelf.getBankNo(), "=", "", ")", true);
                        anskey.setBayNo(0, "=", "(", "", false);
                        anskey.setBayNo(shelf.getBayNo(), "=", "", ")", true);
                        anskey.setLevelNo(0, "=", "(", "", false);
                        anskey.setLevelNo(shelf.getLevelNo(), "=", "", ")", true);
                        anskey.setWidth(shelf.getWidth());
                        AccessNgShelf[] ansInfo = (AccessNgShelf[])ansHand.find(anskey);

                        for (int j = 0; j < ansInfo.length; j++)
                        {
                            // アドレス範囲指定
                            if (j == 0)
                            {
                                // 最初は((でくくる
                                shlfAKey.setAddressNo(ansInfo[j].getStartAddressNo(), ">=", "((", "", true);
                            }
                            else
                            {
                                shlfAKey.setAddressNo(ansInfo[j].getStartAddressNo(), ">=", "(", "", true);
                            }

                            if (j == ansInfo.length - 1)
                            {
                                // 最後は))でくくる
                                shlfAKey.setAddressNo(ansInfo[j].getEndAddressNo(), "<=", "", "))", false);
                            }
                            else
                            {
                                shlfAKey.setAddressNo(ansInfo[j].getEndAddressNo(), "<=", "", ")", false);
                            }
                        }

                        // 更新処理
                        shelfHand.modify(shlfAKey);
                    }
                }
            }
            // フリーアロケーション倉庫以外の場合
            else
            {
                shlfAKey.clear();
                shlfAKey.setBankNo(Integer.parseInt(id71dt.getBankNo(i)));
                shlfAKey.setBayNo(Integer.parseInt(id71dt.getStartBay(i)), ">=");
                shlfAKey.setBayNo(Integer.parseInt(id71dt.getEndBay(i)), "<=");
                shlfAKey.setLevelNo(Integer.parseInt(id71dt.getStLevelNo(i)), ">=");
                shlfAKey.setLevelNo(Integer.parseInt(id71dt.getEnLevelNo(i)), "<=");
                shlfAKey.setWhStationNo(wareHouse);

                if (id71dt.getCondition(i).equals(A_IMPOSSIBLE_CANCEL))
                {
                    //<jp> Access不可解除</jp>
                    //<en> Canceling the inaccessible status</en>
                    shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
                }
                else if (id71dt.getCondition(i).equals(ACCESS_IMPOSSIBLE))
                {
                    //<jp> Access不可</jp>
                    //<en> Inaccessible</en>
                    shlfAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_NG);
                }
                shelfHand.modify(shlfAKey);
            }
        }
    }
}
//end of class
