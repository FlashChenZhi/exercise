<%-- $Id: 01_login.jsp 87 2008-10-04 03:07:38Z admin $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * 
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/02/13 N.Sawa     created
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
</h:head>
<h:page>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr height="38">
    <td align="right" bgcolor="#413a8a" height="38"><img src="img/project/login/logo_top.gif" width="124" height="27" border="0"></td>
  </tr>
  <tr>
    <td align="center" valign="top" background="img/project/login/top_bg.gif">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="251">
          <td align="right" bgcolor="white" height="251"><img src="img/project/login/top_img1.gif" width="61" height="251" border="0"></td>
          <td width="208" height="251"><img src="img/project/login/top_img2.gif" width="208" height="251" border="0"></td>
          <td width="412" height="251"><img src="img/project/login/top_img3.gif" width="412" height="251" border="0"></td>
          <td width="233" height="251"><img src="img/project/login/top_img4.gif" width="233" height="251" border="0"></td>
          <td align="left" bgcolor="white" height="251"><img src="img/project/login/top_img5.gif" width="49" height="251" border="0"></td>
        </tr>
        <tr height="22">
          <td height="22"></td>
          <td rowspan="3" width="208" height="304"><img src="img/project/login/top_img6.gif" width="208" height="304" border="0"></td>
          <td width="412" height="22"><img src="img/project/login/top_img7.gif" width="412" height="22" border="0"></td>
          <td rowspan="3" width="233" height="304"><img src="img/project/login/top_img8.gif" width="233" height="304" border="0"></td>
          <td height="22"></td>
        </tr>
        <tr height="200">
          <td height="200"></td>
          <td align="center" valign="middle" width="412" height="200" background="img/project/login/top_bg2.gif">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr height="1">
                <td width="1" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td width="3" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td bgcolor="white" height="1"><img src="img/project/login/dot_w.gif" width="1" height="1" border="0"></td>
                <td width="3" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td width="1" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
              </tr>
              <tr height="3">
                <td width="1" height="3"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td width="3" height="3"><img src="img/project/login/top_sq1.gif" width="3" height="3" border="0"></td>
                <td height="3"><img src="img/project/login/spacer.gif" width="1" height="3" border="0"></td>
                <td width="3" height="3"><img src="img/project/login/top_sq2.gif" width="3" height="3" border="0"></td>
                <td width="1" height="3"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
              </tr>
              <tr>
                <td bgcolor="white" width="1"><img src="img/project/login/dot_w.gif" width="1" height="1" border="0"></td>
                <td width="3"><img src="img/project/login/spacer.gif" width="3" height="1" border="0"></td>
                <td align="center" valign="middle">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr height="10">
                      <td colspan="5" height="10"></td>
                    </tr>
                    <tr>
                      <td rowspan="3" width="20"></td>
                      <td colspan="3" align="center" valign="middle">
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>○○○</td>
                          </tr>
                        </table>
                      </td>
                      <td rowspan="3" width="20"></td>
                    </tr>
                    <tr height="10">
                      <td colspan="3" height="10"></td>
                    </tr>
                    <tr>
                      <td colspan="3" align="center">
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>○○</td>
                            <td>:</td>
                            <td>○○○</td>
                          </tr>
                          <tr height="10">
                            <td></td>
                            <td></td>
                            <td></td>
                          </tr>
                          <tr>
                            <td>○○</td>
                            <td>:</td>
                            <td>○○○</td>
                          </tr>
                          <tr height="15">
                            <td></td>
                            <td></td>
                            <td></td>
                          </tr>
                          <tr>
                            <td colspan="3" align="center">
                            <input type="button" name="login" value="○○">
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr height="10">
                      <td colspan="5" height="10"></td>
                    </tr>
                  </table>
                </td>
                <td width="3"><img src="img/project/login/spacer.gif" width="3" height="1" border="0"></td>
                <td bgcolor="white" width="1"><img src="img/project/login/dot_w.gif" width="1" height="1" border="0"></td>
              </tr>
              <tr height="3">
                <td width="1" height="3"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td width="3" height="3"><img src="img/project/login/top_sq4.gif" width="3" height="3" border="0"></td>
                <td height="3"><img src="img/project/login/spacer.gif" width="1" height="3" border="0"></td>
                <td width="3" height="3"><img src="img/project/login/top_sq3.gif" width="3" height="3" border="0"></td>
                <td width="1" height="3"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
              </tr>
              <tr height="1">
                <td width="1" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td width="3" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td bgcolor="white" height="1"><img src="img/project/login/dot_w.gif" width="1" height="1" border="0"></td>
                <td width="3" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
                <td width="1" height="1"><img src="img/project/login/spacer.gif" width="1" height="1" border="0"></td>
              </tr>
            </table>
          </td>
          <td height="200"></td>
        </tr>
        <tr height="82">
          <td height="82"></td>
          <td width="412" height="82"><img src="img/project/login/top_img9.gif" width="412" height="82" border="0"></td>
          <td height="82"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</h:page>
</h:html>
