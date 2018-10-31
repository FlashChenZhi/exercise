// $Id: AccessLog.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;

/**
 * <jp>ログ情報に関するエンティティです。<br>
 * </jp> <en> This entity provides Log related information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class AccessLog
        extends LogBaseEntity
        implements Serializable
{
    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- LogInfo Entity -------------------------------------\n");
        sb.append(" LogDate                    : " + this.getLogDate() + "\n");
        sb.append(" GMTDate                    : " + this.getGmtDate() + "\n");
        sb.append(" UserId                     : " + this.getUserId() + "\n");
        sb.append(" UserName                   : " + this.getUserName() + "\n");
        sb.append(" TerminalNumber             : " + this.getTerminalNumber() + "\n");
        sb.append(" TerminalName               : " + this.getTerminalName() + "\n");
        sb.append(" IpAddress                  : " + this.getIpAddress() + "\n");
        sb.append(" DSNumber                   : " + this.getDsNumber() + "\n");
        sb.append(" PageName                   : " + this.getPageName() + "\n");
        sb.append(" AccessType                 : " + this.getAccessType() + "\n");
        sb.append(" Message                    : " + this.getMessage() + "\n");
        sb.append(" Details                    : " + this.getDetails() + "\n");
        sb.append("--------------------------------------------------------\n");
        return sb.toString();
    }
}
