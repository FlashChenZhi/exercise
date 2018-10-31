<%-- $Id: 12_listbox-ScrollListCell.jsp 87 2008-10-04 03:07:38Z admin $ --%>
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
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
</h:head>
<h:page>
<%-- outer table --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
  <TBODY>
    <TR>
      <TD>
      
        <%-- header --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a border=0>
          <TBODY>
            <TR><TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" width=309 border=0></TD></TR>
            <TR height=1><TD bgColor=#dad9ee height=1></TD></TR>
          </TBODY>
        </TABLE>
        
        <%-- title --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
            <TR height=4 bgColor=#413a8a>
              <TD width="7px"></TD>
              <TD height=4><h:label id="lbl_ListName" templateKey="In_SettingName"/></TD>
            </TR>
            <TR height=4 bgColor=#413a8a>
              <TD width="7px" colspan=2></TD>
            </TR>
          </TBODY>
        </TABLE>
        
        <%-- message area --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a border=0>
          <TBODY>
            <TR>
              <TD colSpan=4 height=4></TD>
            </TR>
            <TR height=23>
              <TD width=7></TD>
              <TD width=4 bgColor=white ></TD>
              <TD bgColor=white><h:message id="message" templateKey="OperationMsg"/></TD>
              <TD width=7></TD>
            </TR>
            <TR>
              <TD colSpan=4 height=4></TD>
            </TR>
          </TBODY>
        </TABLE>

        <%-- search condition --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
          <TBODY>
            <TR  height="14px" ><TD width="7px"></TD><TD></TD></TR>
            <TR><TD width="7px"></TD><TD><h:listcell id="lst_SearchCondition" templateKey="SearchCondition"/></TD></TR>
            <TR  height="7px" ><TD width="7px"></TD><TD></TD></TR>
          </TBODY>
        </TABLE>

        <%-- list start --%>   
        <TABLE style="WIDTH:100%;" cellSpacing=0 cellPadding=0 bgColor=#dad9ee border=0>
          <TBODY>
            <TR bgColor=#b8b7d7  height="7px">
              <TD bgColor="#dad9ee" width="7px"></TD>
              <TD colspan=2></TD>
              <TD bgColor="#dad9ee" width="7px"></TD>
            </TR>
            <%-- pager --%>
            <TR bgColor=#b8b7d7>
              <TD bgColor="#dad9ee" width="7px"></TD>
              <TD width="7px"></TD>
              <TD><h:pager id="pager" templateKey="Pager"/></TD>
              <TD bgColor="#dad9ee" width="7px"></TD>
            </TR>
            <TR bgColor=#b8b7d7  height=7>
              <TD bgColor="#dad9ee" width="7px"></TD>
              <TD colspan=2></TD>
              <TD bgColor="#dad9ee" width="7px"></TD>
            </TR>
            <%-- listcell --%>
            <TR bgColor=#b8b7d7>
              <TD bgColor="#dad9ee" width="7px"></TD>
              <TD></TD>
              <TD><h:scrolllistcell id="lst_" templateKey=""/></TD>
              <TD bgColor="#dad9ee" width="7px"></TD>
            </TR>
            <TR bgColor=#b8b7d7  height=7>
              <TD bgColor="#dad9ee" width="7px"></TD>
              <TD colspan=2></TD>
              <TD bgColor="#dad9ee" width="7px"></TD>
            </TR>
          </TBODY>
        </TABLE>
        
        <%-- buttom --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
            <TR>
              <TD>
                <TABLE cellSpacing=0 cellPadding=0 border=0>
                  <TBODY>
                    <TR>
                      <TD bgColor="#dad9ee" width="7px"></TD>
                      <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
                      <TD bgColor=#b8b7d7>
                        <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
                          <TBODY>
                            <TR>
                              <TD></TD>
                              <TD><h:submitbutton id="btn_Close" templateKey="Close"/></TD>
                              <TD></TD>
                            </TR>
                          </TBODY>
                        </TABLE>
                      </TD>
                      <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD>
                    </TR>
                  </TBODY>
                </TABLE>
              </TD>
            </TR>
          </TBODY>
        </TABLE>
        
        <%-- footer --%>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
            <TR height=41>
              <TD width=7 bgColor=#dad9ee height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
              <TD bgColor=#dad9ee height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 border=0></TD></TR>
            <TR height=8>
              <TD width=7 background="<%=request.getContextPath()%>/img/common/bg_end.gif" height=8></TD>
              <TD background="<%=request.getContextPath()%>/img/common/bg_end.gif"  height=8></TD>
            </TR>
          </TBODY>
        </TABLE>
        
      </TD>
    </TR>
  </TBODY>
</TABLE>
</h:page>
</h:html>
