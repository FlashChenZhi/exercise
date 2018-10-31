<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" omit-xml-declaration="yes" />
<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>
  <xsl:variable name="context_path" select="@context-path"/>

  <xsl:variable name = "selectedIndexs" >
    <xsl:for-each select="list-items[@type='TabColumns']/list-item[@type='TabColumn']">
      <xsl:if test="property[@name='selected']/@value='true'">
        <xsl:value-of select="position()" />
      </xsl:if>
    </xsl:for-each>
  </xsl:variable>

  <xsl:variable name = "selectedIndex" >
    <xsl:choose>
      <xsl:when test="string-length($selectedIndexs)=0">
        <xsl:text>-1</xsl:text>
      </xsl:when>
      <xsl:when test="string-length($selectedIndexs)=1">
        <xsl:value-of select="$selectedIndexs" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="substring-after($selectedIndexs,string-length($selectedIndexs)-1)" />
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <!-- otput "TABLE" -->
  <table border="0" cellspacing="0" cellpadding="0">

    <!-- otput "TR" -->
    <tr>
      <xsl:choose>
        <xsl:when test="count(list-items[@type='TabColumns']/list-item[@type='TabColumn'])>0">

          <!-- output "TD" -->
          <xsl:for-each select="list-items[@type='TabColumns']/list-item[@type='TabColumn']">
            <!-- otput "Left TD" -->
            <td height="28">
              <!-- output "IMG" -->
              <xsl:variable name = "tabLeft" >
                <xsl:choose>
                  <xsl:when test="$selectedIndex = position()">
                    <xsl:text>Tab_Left_Selected.gif</xsl:text>
                  </xsl:when>
                    <xsl:otherwise>
                     <xsl:text>Tab_Left.gif</xsl:text>
                   </xsl:otherwise>
                </xsl:choose>
              </xsl:variable>
              <img width="7" height="28" border="0"  src="{$image_root}{$tabLeft}" />
            </td>

            <!-- otput "BackGround TD" -->
            <td height="28">
              <!-- set "background" -->
              <xsl:attribute name="background">
                <xsl:value-of select="$image_root"/>

                <xsl:choose>
                  <xsl:when test="$selectedIndex = position()">
                    <xsl:text>Tab_BackGround_Selected.gif</xsl:text>
                  </xsl:when>

                  <xsl:otherwise>
                    <xsl:text>Tab_BackGround.gif</xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:attribute>

              <!-- otput "SPAN" -->
              <span>

                <!-- set "style" -->
                <xsl:attribute name="style">
                  <xsl:text>white-space : nowrap; font-size: 16px; font-family: monospace; text-decoration: none; position: relative; top: 2p;</xsl:text>

                  <xsl:if test="$selectedIndex &lt;= position()">
                    <xsl:text>color:#50408</xsl:text>
                  </xsl:if>

                </xsl:attribute>

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
                </xsl:choose>
              </span>
            </td>

            <!-- otput "IMG" -->
            <xsl:if test="position() != last() ">

              <td height="28">

                <!-- set "background" -->
                <xsl:attribute name="background">
                  <xsl:value-of select="$image_root"/>

                  <xsl:choose>
                    <xsl:when test="$selectedIndex = position()">
                      <xsl:text>Tab_BackGround_Selected.gif</xsl:text>
                    </xsl:when>

                    <xsl:otherwise>
                      <xsl:text>Tab_BackGround.gif</xsl:text>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:attribute>
                <img height="14" border="0"  src="{$image_root}Tab_Arrow.gif" />
              </td>
            </xsl:if>

            <!-- otput "Right TD" -->
            <td width="7" height="28">

              <!-- output "IMG" -->
              <xsl:variable name = "tabRight" >
                <xsl:choose>
                  <xsl:when test="$selectedIndex = position()">
                    <xsl:text>Tab_Right_Selected.gif</xsl:text>
                  </xsl:when>
                    <xsl:otherwise>
                     <xsl:text>Tab_Right.gif</xsl:text>
                   </xsl:otherwise>
                </xsl:choose>
              </xsl:variable>
              <img width="7" height="28" border="0"  src="{$image_root}{$tabRight}" />
            </td>
          </xsl:for-each>
        </xsl:when>

        <!-- otput defaultvalue -->
        <xsl:otherwise>

          <td height="28">
            <xsl:attribute name="background">
              <xsl:value-of select="$image_root"/>
              <xsl:text>Tab_Left_Selected.gif</xsl:text>
            </xsl:attribute>
            <img width="7" height="28" border="0"  src="{$image_root}Tab_Left_Selected.gif" />
          </td>

          <!-- otput "BackGround TD" -->
          <td height="28">

            <!-- set "background" -->
            <xsl:attribute name="background">
              <xsl:value-of select="$image_root"/>
              <xsl:text>Tab_BackGround_Selected.gif</xsl:text>
            </xsl:attribute>

            <!-- otput "SPAN" -->
            <span>
              <!-- set "style" -->
              <xsl:attribute name="style">
                <xsl:text>white-space : nowrap; font-size: 16px; font-family: monospace; text-decoration: none; position: relative; top: 2p; color:#50408;</xsl:text>
              </xsl:attribute>
              <!-- set "text" -->
              <xsl:text>Tab</xsl:text>
            </span>
          </td>

          <!-- otput "Right TD" -->
          <td height="28">
            <!-- output "IMG" -->
            <img width="7" height="28" border="0"  src="{$image_root}Tab_Right_Selected.gif"/>
          </td>
        </xsl:otherwise>
      </xsl:choose>
    </tr>

  </table>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>