<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" omit-xml-declaration="no" />
<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>
  <xsl:variable name="context_path" select="@context-path"/>
  <xsl:variable name="layout_window" select="@layout-window"/>

  <xsl:variable name="roop" select = "4" />
  <xsl:variable name="fix" select = "property[@name='fixedBank']/@value" />


  <!-- output "defaultvalue" -->
  <xsl:choose>
    <xsl:when test="count(list-items[@type='ListCellColumns']/list-item[@type='ListCellColumn'])>0">

      <!-- otput "TABLE" -->
      <table border="1" cellspacing="0" cellpadding="0"
        style= "border-top : 1px solid #413a8a; border-right : 1px solid #413a8a; border-bottom : 1px solid #413a8a; border-left : 1px solid #413a8a; ">

        <!-- set attribute "css" -->
        <xsl:if test="property[@name='tableCssClass']/@value!=''">
          <xsl:attribute name="class">
            <xsl:apply-templates select="property[@name='tableCssClass']"/>
          </xsl:attribute>
        </xsl:if>

        <!-- write header -->
        <thead>
        <tr>
          <xsl:for-each select="list-items[@type='ListCellColumns']/list-item[@type='ListCellColumn']">

            <xsl:if test="property[@name='newrow']/@value='true'">
              <xsl:text disable-output-escaping="yes"><![CDATA[</tr><tr>]]></xsl:text>
            </xsl:if>

            <!-- otput "TD" -->
            <td height="25">
              <!-- set attribute "css" -->
              <xsl:if test="property[@name='cssClass']/@value!=''">
                <xsl:attribute name="class">
                  <xsl:apply-templates select="property[@name='cssClass']"/>
                </xsl:attribute>
              </xsl:if>

              <xsl:if test="property[@name='colspan']/@value!=''">
                <xsl:attribute name="colspan">
                  <xsl:apply-templates select="property[@name='colspan']"/>
                </xsl:attribute>
              </xsl:if>

              <xsl:if test="property[@name='rowspan']/@value!=''">
                <xsl:attribute name="rowspan">
                  <xsl:apply-templates select="property[@name='rowspan']"/>
                </xsl:attribute>
              </xsl:if>

              <xsl:if test="property[@name='width']/@value!=''">
               <xsl:attribute name="width">
                 <xsl:apply-templates select="property[@name='width']"/>
               </xsl:attribute>
             </xsl:if>

           <span class="headerSpan">
             <!-- set attribute "style" -->
             <xsl:attribute name="style">
               height:25px;
               line-height:25px;

               <xsl:if test="property[@name='width']/@value!=''">
                 <xsl:text>width:</xsl:text>
                 <xsl:apply-templates select="property[@name='width']"/>
                 <xsl:text>px;</xsl:text>
               </xsl:if>
             </xsl:attribute>

             <!-- write header -->
             <xsl:call-template name="writehead">
               <xsl:with-param name="layout_window" select="$layout_window" />
             </xsl:call-template>
           </span>
           </td>

            <xsl:if test="position()=$fix">
              <td width="1" bgcolor="#413a8a" style="border-top : 1px solid #413a8a; border-right : 1px solid #413a8a; border-bottom : 1px solid #413a8a; border-left : 1px solid #413a8a;">　</td>
            </xsl:if>








         </xsl:for-each>

        </tr>
        </thead>

        <!-- write detail -->
        <tbody>
        <xsl:call-template name="loop" >
          <xsl:with-param name="end" select="$roop" />
          <xsl:with-param name="fix" select="$fix" />
        </xsl:call-template>
        </tbody>

       </table>
    </xsl:when>
  
    <xsl:otherwise>

      <!-- otput "TABLE" -->
      <table border="0" cellspacing="0" cellpadding="0" 
      style= "border-top : 1px solid #413a8a; border-right : 1px solid #413a8a; border-bottom : 1px solid #413a8a; border-left : 1px solid #413a8a; ">
        <thead>
          <tr>
            <td width = "150" height = "25" 
              style = "background-color : #413a8a; color : white; font-size : 100%; font-family : monospace; text-decoration : none; text-align : center; position : relative; padding-top : 5px; padding-bottom : 1px;"><span>col1</span></td>
            <td width = "150" height = "25" 
              style = "background-color : #413a8a; color : white; font-size : 100%; font-family : monospace; text-decoration : none; text-align : center; position : relative; padding-top : 5px; padding-bottom : 1px; border-left : 1px solid #ffffff;"><span>col2</span></td>
            <td width = "150" height = "25" 
              style = "background-color : #413a8a; color : white; font-size : 100%; font-family : monospace; text-decoration : none; text-align : center; position : relative; padding-top : 5px; padding-bottom : 1px; border-left : 1px solid #ffffff;"><span>col3</span></td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td width = "150" height = "30"  bgcolor = "white"
              style = "padding-right : 4px; padding-left : 4px; position : relative; ">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
            <td width = "150" height = "30"  bgcolor = "white"
              style = "padding-right : 4px; padding-left : 4px; position : relative; border-left : 1px solid #413a8a;">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
            <td width = "150" height = "30"  bgcolor = "white"
              style = "padding-right : 4px; padding-left : 4px; position : relative; border-left : 1px solid #413a8a;">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
          </tr>
          <tr>
            <td width = "150" height = "30"  bgcolor = "#dad9ee"
              style = "padding-right : 4px; padding-left : 4px; position : relative; ">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
            <td width = "150" height = "30"  bgcolor = "#dad9ee"
              style = "padding-right : 4px; padding-left : 4px; position : relative; border-left : 1px solid #413a8a;">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
            <td width = "150" height = "30"  bgcolor = "#dad9ee"
              style = "padding-right : 4px; padding-left : 4px; position : relative; border-left : 1px solid #413a8a;">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
          </tr>
          <tr>
            <td width = "150" height = "30"  bgcolor = "white"
              style = "padding-right : 4px; padding-left : 4px; position : relative; ">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
            <td width = "150" height = "30"  bgcolor = "white"
              style = "padding-right : 4px; padding-left : 4px; position : relative; border-left : 1px solid #413a8a;">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
            <td width = "150" height = "30"  bgcolor = "white"
              style = "padding-right : 4px; padding-left : 4px; position : relative; border-left : 1px solid #413a8a;">
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text></td>
          </tr>
        </tbody>
      </table>
    </xsl:otherwise>

  </xsl:choose>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

<!-- otput head -->
<xsl:template name="writehead">
  <xsl:param name="layout_window" /> 

  <xsl:choose>

    <xsl:when test="property[@name='columnClickEvent']/@value='true'">

      <xsl:choose>
        <xsl:when test="$layout_window=''">
          <!-- otput "A" -->
          <a href = "#" onclick = "return false">
            <!-- set attribute "text" -->
            <xsl:choose>
              <xsl:when test="property[@name='text']/@value!=''">
                <xsl:apply-templates select="property[@name='text']"/>
              </xsl:when>
              <xsl:when test="property[@name='resourceKey']/@value!=''">
                <xsl:text disable-output-escaping="yes"><![CDATA[&DISP_RESOURCE]]>{</xsl:text>
                <xsl:apply-templates select="property[@name='resourceKey']"/>
                <xsl:text>}</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </a>
        </xsl:when>
        <xsl:otherwise>
          <span>
            <u>
            <xsl:choose>
              <xsl:when test="property[@name='text']/@value!=''">
                <xsl:apply-templates select="property[@name='text']"/>
              </xsl:when>
              <xsl:when test="property[@name='resourceKey']/@value!=''">
                <xsl:text disable-output-escaping="yes"><![CDATA[&DISP_RESOURCE]]>{</xsl:text>
                <xsl:apply-templates select="property[@name='resourceKey']"/>
                <xsl:text>}</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text>
              </xsl:otherwise>
            </xsl:choose>
            </u>
          </span>
        </xsl:otherwise>

      </xsl:choose>

    </xsl:when>

    <xsl:otherwise>

      <!-- otput "SPAN" -->
      <span>

        <!-- set "text" -->
        <xsl:choose>
          <xsl:when test="property[@name='text']/@value!=''">
            <xsl:apply-templates select="property[@name='text']"/>
          </xsl:when>
          <xsl:when test="property[@name='resourceKey']/@value!=''">
            <xsl:text disable-output-escaping="yes"><![CDATA[&DISP_RESOURCE]]>{</xsl:text>
            <xsl:apply-templates select="property[@name='resourceKey']"/>
            <xsl:text>}</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text>
          </xsl:otherwise>
        </xsl:choose>

       </span>

    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!-- loop of regulation -->
<xsl:template name="loop">

  <xsl:param name="end" /> 
  <xsl:param name="fix" /> 
  <xsl:param name="count" select="1" /> 

  <xsl:if test="number($count) &lt;= number($end)">

     <tr>

       <xsl:for-each select="list-items[@type='ListCellColumns']/list-item[@type='ListCellColumn']">

          <xsl:if test="property[@name='newrow']/@value='true'">
            <xsl:text disable-output-escaping="yes"><![CDATA[</tr><tr>]]></xsl:text>
          </xsl:if>

          <!-- otput "TD" -->
          <td>

            <!-- set attribute "css" -->
            <xsl:if test="property[@name='detailCssClass']/@value!=''">
              <xsl:attribute name="class">
                <xsl:apply-templates select="property[@name='detailCssClass']"/>
              </xsl:attribute>
            </xsl:if>

            <xsl:if test="property[@name='colspan']/@value!=''">
              <xsl:attribute name="colspan">
                <xsl:apply-templates select="property[@name='colspan']"/>
              </xsl:attribute>
            </xsl:if>

            <xsl:if test="property[@name='rowspan']/@value!=''">
              <xsl:attribute name="rowspan">
                <xsl:apply-templates select="property[@name='rowspan']"/>
              </xsl:attribute>
            </xsl:if>

            <xsl:if test="property[@name='height']/@value!=''">
              <xsl:attribute name="height">
                <xsl:apply-templates select="property[@name='height']"/>
              </xsl:attribute>
            </xsl:if>

            <xsl:if test="property[@name='width']/@value!=''">
              <xsl:attribute name="width">
                <xsl:apply-templates select="property[@name='width']"/>
              </xsl:attribute>
            </xsl:if>

            <!-- set attribute "bgcolor" -->
            <xsl:attribute name="bgcolor">

              <xsl:choose>

                <xsl:when test="($count mod 2) != 1">
                  <xsl:text>#dad9ee</xsl:text>
                </xsl:when>

                <xsl:otherwise>
                  <xsl:text>white</xsl:text>
                </xsl:otherwise>

              </xsl:choose>

            </xsl:attribute>

            <!-- set text -->
            <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text>
          </td>




            <xsl:if test="position()=$fix">
              <td width="1" bgcolor="#413a8a" style="border-top : 1px solid #413a8a; border-right : 1px solid #413a8a; border-bottom : 1px solid #413a8a; border-left : 1px solid #413a8a;">　</td>
            </xsl:if>


        </xsl:for-each>
      </tr>

    <xsl:call-template name="loop">
      <xsl:with-param name="end" select="$end" /> 
      <xsl:with-param name="fix" select="$fix" /> 
      <xsl:with-param name="count" select="$count + 1" /> 
    </xsl:call-template>
  </xsl:if>

</xsl:template>

</xsl:stylesheet>
