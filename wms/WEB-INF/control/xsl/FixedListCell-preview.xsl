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

  <!-- output "defaultvalue" -->
  <xsl:choose>
    <xsl:when test="count(list-items[@type='ListCellColumns']/list-item[@type='ListCellColumn'])>0">
      <!-- otput "TABLE" -->
      <table border="0" cellspacing="0" cellpadding="0"
       style= "border-top : 1px solid #413a8a; border-right : 1px solid #413a8a; border-bottom : 1px solid #413a8a; border-left : 1px solid #413a8a; ">

      <!-- set attribute "css" -->
      <xsl:if test="property[@name='tableCssClass']/@value!=''">
        <xsl:attribute name="class">
          <xsl:apply-templates select="property[@name='tableCssClass']"/>
        </xsl:attribute>
      </xsl:if>

      <!-- write header -->
      <xsl:if test="property[@name='colHeader']/@value!='false'">
      <thead>
        <tr>

        <xsl:for-each select="list-items[@type='ListCellRowHeaders']/list-items[@type='RowHeaders']">
          <xsl:if test="position()=1">
            <xsl:for-each select="list-item[@type='RowHeader']">

              <!-- otput "TD" -->
              <td>
              <!-- set attribute "css" -->
              <xsl:if test="property[@name='cssClass']/@value!=''">
                <xsl:attribute name="class">
                  <xsl:apply-templates select="property[@name='cssClass']"/>
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

              </td>
            </xsl:for-each>
          </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="list-items[@type='ListCellColumns']/list-item[@type='ListCellColumn']">
          <!-- otput "TD" -->
          <td>
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

          <!-- write header -->
          <xsl:call-template name="writehead">
            <xsl:with-param name="layout_window" select="$layout_window" />
          </xsl:call-template>
          </td>
        </xsl:for-each>

        </tr>
      </thead>
      </xsl:if>

      <!-- write detail -->
      <tbody>
        <xsl:call-template name="loop" >
          <xsl:with-param name="end" select="property[@name='maxRows']/@value - 1" />
          <xsl:with-param name="alterationBgColor" select="property[@name='alterationBgColor']/@value" />
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
            <td width = "150" height = "30" 
              style = "background-color : #413a8a; color : white; font-size : 100%; font-family : monospace; text-decoration : none; text-align : center; position : relative; padding-top : 5px; padding-bottom : 1px;"><span>col1</span></td>
            <td width = "150" height = "30" 
              style = "background-color : #413a8a; color : white; font-size : 100%; font-family : monospace; text-decoration : none; text-align : center; position : relative; padding-top : 5px; padding-bottom : 1px; border-left : 1px solid #ffffff;"><span>col2</span></td>
            <td width = "150" height = "30" 
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

  <xsl:param name="alterationBgColor" />
  <xsl:param name="end" />
  <xsl:param name="count" select="1" /> 

  <xsl:if test="number($count) &lt;= number($end)">

    <tr>
    <xsl:for-each select="list-items[@type='ListCellRowHeaders']/list-items[@type='RowHeaders']">
      <xsl:if test="position()-1=$count">
        <xsl:for-each select="list-item[@type='RowHeader']">

          <!-- otput "TD" -->
          <td bgcolor = "white">
          <!-- set attribute "css" -->
          <xsl:if test="property[@name='cssClass']/@value!=''">
            <xsl:attribute name="class">
              <xsl:apply-templates select="property[@name='cssClass']"/>
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
          </td>

        </xsl:for-each>
      </xsl:if>
    </xsl:for-each>

    <xsl:for-each select="list-items[@type='ListCellColumns']/list-item[@type='ListCellColumn']">

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
          <xsl:when test="$alterationBgColor='false'">
            <xsl:text>white</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:choose>
              <xsl:when test="($count mod 2) = 1">
                <xsl:text>white</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>#dad9ee</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>

      <!-- set text -->
      <xsl:text disable-output-escaping="yes"><![CDATA[<br>]]></xsl:text>
      </td>

    </xsl:for-each>
    </tr>

    <xsl:call-template name="loop">
      <xsl:with-param name="alterationBgColor" select="$alterationBgColor" />
      <xsl:with-param name="end" select="$end" />
      <xsl:with-param name="count" select="$count + 1" /> 
    </xsl:call-template>

  </xsl:if>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>
