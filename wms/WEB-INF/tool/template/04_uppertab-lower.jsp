<%-- $Id: 04_uppertab-lower.jsp 344 2010-06-28 08:37:46Z dfk_sumiyama $ --%>
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

    <%-- title --%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#413a8a">
      <tr>
        <td width="7" height="24"></td>
        <td><h:label id="lbl_SettingName" templateKey="In_SettingName"/></td>
        <td height="24" align="right" style="padding-top : 1px;" background="img/control/tab/Tab_BackGround_Spacer.gif">
        <h:linkbutton id="btn_Help" templateKey="Help"/></td>
        <td width="7" height="24"></td>
      </tr>
    </table>

    <%-- message --%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#413a8a">
      <tr>
        <td colspan="4" height="4"></td>
      </tr>
      <tr>
        <td width="7"  height="23"></td>
        <td width="4"  height="23" bgcolor="white"></td>
        <td height="23" bgcolor="white"><h:message id="message" templateKey="OperationMsg"/></td>
        <td width="7" height="23"></td>
      </tr>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr height="2">
        <td bgcolor="#413a8a" height="2"></td>
      </tr>
    </table>

    <%-- tab --%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr height="2">
        <td bgcolor="#413A8A" height="2" ><img src="img/common/spacer.gif" width="1" height="2" border="0"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" background="img/control/tab/Tab_BackGround_Spacer.gif">
      <tr height="28">
        <td width="7"><img src="img/common/spacer.gif" width="7"></td>
        <td width="7"></td>
        <td nowrap><h:tab id="tab" templateKey=""/></td>
        <td width="100%" height="28"></td>
        <td><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></td>
      </tr>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr height="5">
        <td width="100%"></td>
      </tr>
    </table>

    <%-- upper display --%>
    <table bgcolor="#B8B7D7" width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-left:7">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr height="5" bgcolor="#DAD9EE">
          <td bgcolor="#DAD9EE"></td>
          <td bgcolor="#DAD9EE"></td>
          <td bgcolor="#DAD9EE"></td>
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
      </td>
    </tr>
  </table>

    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td bgcolor="#DAD9EE"><IMG src="<%=request.getContextPath()%>/img/common/spacer.gif" width="7px" border=0></td>
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
      <tr height="5">
        <td height="5"></td>
        <td height="5"></td>
        <td height="5"></td>
        <td height="5"></td>
      </tr>
    </table>

    <%-- lower display --%>
    <table bgcolor="#B8B7D7" width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-left:7">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr height="5" bgcolor="#B8B7D7">
          <td bgcolor="#B8B7D7"></td>
          <td bgcolor="#B8B7D7"></td>
          <td bgcolor="#B8B7D7"></td>
          </tr>
          <tr bgcolor="#B8B7D7">
            <td nowrap bgcolor="#B8B7D7">○○○</td>
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
      </td>
    </tr>
  </table>
</td>
  </tr>
  <tr>
    <td>
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
    </td>
  </tr>
</table>

</h:page>
</h:html>
