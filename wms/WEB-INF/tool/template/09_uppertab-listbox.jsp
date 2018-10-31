<%-- $Id: 09_uppertab-listbox.jsp 87 2008-10-04 03:07:38Z admin $ --%>
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
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DAD9EE">
<tr>
<td>
  <%-- header --%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" BGCOLOR="#413a8a">
    <tr>
      <td><img src="img/project/etc/logo_product_header.gif" border="0"></td>
    </tr>
  </table>
  <%-- title --%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#413a8a">
    <tr>
      <td width="7" height="24"></td>
      <td>&nbsp;<h:label id="lbl_ListName" templateKey="In_SettingName"/></td>
      <td></td>
      <td width="7" height="24"></td>
    </tr>
  </table>
  <%-- message --%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#413a8a">
    <tr><td colspan="4" height="4"></td></tr>
    <tr>
      <td width="7"  height="23"></td>
      <td width="4"  height="23" bgcolor="white"></td>
      <td height="23" bgcolor="white"><h:message id="message" templateKey="OperationMsg"/></td>
      <td width="7" height="23"></td>
    </tr>
  </table>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE>

      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"><IMG height=10 src="img/common/spacer.gif" width="100%" border=0></TD></TR>
       </TBODY></TABLE>
  <%-- close botton --%>
  <table border="0" cellspacing="0" cellpadding="0" align="right" bgcolor="#DAD9EE">
    <tr>
      <td width="7" height="10"></td>
      <td width="7" height="10"></td>
      <td height="10"align="right">
        <table border="0" cellspacing="0" cellpadding="0" bgcolor="#B8B7D7">
          <tr><td width="7" height="7"></td><td></td><td width="7" height="7"></td></tr>
          <tr>
            <td></td>
            <td><h:submitbutton id="btn_Close_U" templateKey="Close"/></td>
            <td></td>
          </tr>
          <tr><td width="7" height="7"></td><td></td><td width="7" height="7"></td></tr>
        </table>
      </td>
      <td width="7" height="10"></td>
    </tr>
  </table>


  <%-- display --%>
  <table border="0" cellspacing="0" cellpadding="0" bgcolor="#DAD9EE" style="margin-left:7" >
    <tr height="5" bgcolor="#DAD9EE" >
    <td bgcolor="#DAD9EE"></td>
    <td bgcolor="#DAD9EE"></td>
    <td bgcolor="#DAD9EE"></td>
    <td colspan="1" bgcolor="#DAD9EE"></td>
    </tr>
    <tr bgcolor="#DAD9EE">
      <td nowrap bgcolor="#DAD9EE">○○○</td>
      <td bgcolor="#DAD9EE"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#DAD9EE"></td>
    </tr>
    <tr bgcolor="#DAD9EE">
      <td nowrap bgcolor="#DAD9EE"></td>
      <td bgcolor="#DAD9EE"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#DAD9EE"></td>
    </tr>
    <tr bgcolor="#DAD9EE">
      <td nowrap bgcolor="#DAD9EE"></td>
      <td bgcolor="#DAD9EE"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#DAD9EE"></td>
    </tr>
    <tr bgcolor="#B8B7D7">
      <td nowrap bgcolor="#B8B7D7"></td>
      <td bgcolor="#B8B7D7"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#B8B7D7"></td>
    </tr>
    <tr bgcolor="#B8B7D7">
      <td nowrap bgcolor="#B8B7D7"></td>
      <td bgcolor="#B8B7D7"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#B8B7D7"></td>
    </tr>
    <tr bgcolor="#B8B7D7">
      <td nowrap bgcolor="#B8B7D7"></td>
      <td bgcolor="#B8B7D7"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#B8B7D7"></td>
    </tr>
    <tr bgcolor="#B8B7D7">
      <td nowrap bgcolor="#B8B7D7"></td>
      <td bgcolor="#B8B7D7"><img src="img/common/icon_colon3.gif"></td>
      <td width="100%" bgcolor="#B8B7D7"></td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="0" style="margin-left:7">
    <tr>
      <td><img src="img/common/tab_lt3.gif" width="42" height="40" border="0"></td>
      <td bgcolor="#B8B7D7">
        <table width="80" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </table>
      </td>
      <td><img src="img/common/tab_rt3.gif" width="42" height="40" border="0"></td>
    </tr>
  </table>
</td>
</tr>
<tr><td>

<%-- footer --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr height="41">
    <td bgcolor="#DAD9EE" width="7" height="41"><img src="img/common/spacer.gif" width="7" height="1" border="0"></td>
    <td bgcolor="#DAD9EE" height="41"><img src="img/common/logo_dp.gif" ALT="" width="374" height="23" border="0"></td>
  </tr>
  <tr height="8">
    <td width="7" height="8" background="img/common/bg_end.gif"></td>
    <td height="8" background="img/common/bg_end.gif"></td>
  </tr>
</table>
</td></tr>
</table>

</h:page>
</h:html>
