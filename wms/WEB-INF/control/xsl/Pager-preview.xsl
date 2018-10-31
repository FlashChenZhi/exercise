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

  <!-- otput "TABLE" -->
  <table border="0" cellspacing="0" cellpadding="0"
  	style= "border-top : 0px solid #413a8a; border-right : 0px solid #413a8a; border-bottom : 0px solid #413a8a; border-left : 0px solid #413a8a; ">
  <tr>
  <td>
  <table cellspacing="0" border="0" cellpadding="0">
    <!-- otput "Spacer TR" -->
    <tr>
      <td colspan="13" bgcolor="#413a8a">
        <!-- otput "IMG" -->
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
    </tr>
    <!-- otput "TR" -->
    <tr>
      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">
        <!-- otput "IMG" -->
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
      <!-- otput "First TD" -->
      <td>
        <!-- otput "IMG" -->
        <img src="{$image_root}tbtn_ff_off.gif" />
      </td>
      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">
        <!-- otput "IMG" -->
        <img width="2" height="1" border="0" src="{$image_root}Spacer.gif" />
      </td>
      <!-- otput "Prev TD" -->
      <td>
        <!-- otput "IMG" -->
        <img src="{$image_root}tbtn_prev_off.gif" />
      </td>
      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">
        <!-- otput "IMG" -->
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
      <!-- otput "Result TD" -->
      <td bgcolor = "white">
        <span>
          <xsl:attribute name="style">
            <xsl:text>white-space : nowrap;</xsl:text>
          </xsl:attribute>
           <xsl:text>1-1/</xsl:text>
           <xsl:apply-templates select="property[@name='max']"/>
         </span>
      </td>
      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">
        <!-- otput "IMG" -->
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
      <!-- otput "Next TD" -->
      <td>
        <!-- otput "IMG" -->
        <img src="{$image_root}tbtn_nxt_off.gif" />
      </td>
      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
      <!-- otput "Last TD" -->
      <td>
        <!-- otput "IMG" -->
        <img src="{$image_root}tbtn_last_off.gif" />
      </td>
      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">
        <!-- otput "IMG" -->
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
    </tr>
    <!-- otput "Spacer TR" -->
    <tr>
      <td colspan="13" bgcolor="#413a8a">
        <img width="2" height="2" border="0" src="{$image_root}Spacer.gif" />
      </td>
    </tr>
  </table>
  </td>
  </tr>
  </table>


</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>