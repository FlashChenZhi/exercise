<%-- $Id: 20_listbox.jsp 344 2010-06-28 08:37:46Z dfk_sumiyama $ --%>
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
 * @version $Revision: 344 $, $Date: 2010-06-28 17:37:46 +0900 (月, 28 6 2010) $
 * @author  $Author: dfk_sumiyama $
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
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1"><td bgcolor="#DAD9EE" colspan="3" height="1"></td></tr>
    <tr height="2"><td bgcolor="#413A8A" colspan="3" height="4"></td></tr>
    <tr>
      <td bgcolor="#413A8A" width="7" height="16"><img src="img/common/spacer.gif" width="7" height="1" border="0"></td>
      <td bgcolor="#413A8A">&nbsp;<h:label id="lbl_ListName" templateKey="In_SettingName"/></td>
      <td bgcolor="#413A8A" width="7" height="16"><img src="img/common/spacer.gif" width="7" height="1" border="0"></td>
    </tr>
    <tr height="2"><td bgcolor="#413a8a" colspan="3" height="4"></td></tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr><td width="100%"><img src="img/common/spacer.gif" width="100%" height="10" border="0"></td></tr>
  </table>

  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR><TD width="100%">

  <%-- display --%>
  <table border="0" cellspacing="0" cellpadding="0" bgcolor="#DAD9EE" style="margin-left:7" >
    <tr height="5" bgcolor="#DAD9EE" >
    <td></td>
    <td></td>
    <td></td>
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
  </table>

  </TD>
  <TD ALIGN=RIGHT VALIGN = BOTTOM>

  <%-- close botton --%>
  <table border="0" cellspacing="0" cellpadding="0" align="right" bgcolor="#DAD9EE">
    <tr>
      <td width="7" height="10"></td>
      <td width="7" height="10"></td>
      <td height="10"align="right">
        <table border="0" cellspacing="0" cellpadding="0" bgcolor="#B8B7D7">
          <tr><td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
              <td></td>
              <td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
          </tr>
          <tr>
            <td></td>
            <td><h:submitbutton id="btn_Close_U" templateKey="Close"/></td>
            <td></td>
          </tr>
          <tr><td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
              <td></td>
              <td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
          </tr>
        </table>
      </td>
      <td width="7" height="10"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
    </tr>
  </table>
  </TD></TR></TBODY></TABLE>
  <BR>

  <%-- List start --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0
      bgColor=#dad9ee border=0>
      <TR width="100%" bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD  height="7" bgColor=#b8b7d7></TD>
          </TR>
      <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD  align="right" bgColor=#b8b7d7><h:pager id="pgr_U" templateKey="Pager"/></TD>
          <TD bgColor=#b8b7d7></TD>
      </TR>
      <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:label id="lbl_InMsg" templateKey="In_ErrorJsp"/></TD>
          <TD height="7" bgColor=#b8b7d7></TD>
      </TR>
	  <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_" templateKey=""/></TD>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
	  </TR>
      <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD height="7" bgColor=#b8b7d7></TD>
          </TR>
	  <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD align="right" bgColor=#b8b7d7><h:pager id="pgr_D" templateKey="Pager"/></TD>
          <TD bgColor=#b8b7d7></TD>
          </TR>
      <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD  height="7" bgColor=#b8b7d7>
          </TD></TR>
      </TBODY>
    </TABLE>
  <br>

  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR><TD width="100%">
  </TD>
  <TD align=right >


  <%-- close botton --%>
  <table border="0" cellspacing="0" cellpadding="0" align="right" bgcolor="#DAD9EE">
    <tr>
      <td width="7" height="10"></td>
      <td width="7" height="10"></td>
      <td height="10"align="right">
        <table border="0" cellspacing="0" cellpadding="0" bgcolor="#B8B7D7">
          <tr><td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
              <td></td>
              <td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
          </tr>
          <tr>
            <td></td>
            <td><h:submitbutton id="btn_Close_D" templateKey="Close"/></td>
            <td></td>
          </tr>
          <tr><td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
              <td></td>
              <td width="7" height="7"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
          </tr>
        </table>
      </td>
      <td width="7" height="10"><IMG height=1 src="img/common/spacer.gif" width=7 border=0></td>
    </tr>

  </TD></TR></TBODY></TABLE>

</td>
</tr>
</table>

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



</h:page>
</h:html>
