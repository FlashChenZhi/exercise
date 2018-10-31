<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" > 
<xsl:output method="xml" omit-xml-declaration="yes" />

<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>
  <xsl:variable name="context_path" select="@context-path"/>
  <xsl:variable name="layout_window" select="@layout-window"/>

  <!-- output "A" -->
  <xsl:text disable-output-escaping="yes"><![CDATA[<a]]></xsl:text>

  <xsl:if test="$layout_window=''">
    <xsl:text disable-output-escaping="yes"><![CDATA[ href="#" onclick="return false;"]]></xsl:text>
  </xsl:if>

  <!-- set attribute "css" -->
  <xsl:if test="property[@name='cssClass']/@value!=''">
    <xsl:text disable-output-escaping="yes"><![CDATA[ class="]]></xsl:text>
    <xsl:value-of select="property[@name='cssClass']/@value"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>
  </xsl:if>

  <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

  <!-- output "IMG" -->
  <xsl:choose>
    <xsl:when test="$layout_window=''">
      <xsl:text disable-output-escaping="yes"><![CDATA[<IMG src="]]></xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text disable-output-escaping="yes"><![CDATA[<input type='image' style='cursor:default' disabled src="]]></xsl:text>
    </xsl:otherwise>
  </xsl:choose>

  <!-- set attribute "src" -->
  <xsl:choose>
    <xsl:when test="property[@name='tempFileSrc']/@value!=''">
      <xsl:value-of select="$context_path"/>
      <xsl:apply-templates select="property[@name='tempFileSrc']"/>
    </xsl:when>
    <xsl:when test="property[@name='src']/@value!=''">
      <xsl:value-of select="$image_root"/>
      <xsl:apply-templates select="property[@name='src']"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$image_root"/>
      <xsl:text>imgbtn.gif</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>

  <!-- set attribute "border" -->
  <xsl:choose>
    <xsl:when test="$layout_window!=''">
      <xsl:text disable-output-escaping="yes"><![CDATA[border="2" ]]></xsl:text>
      <xsl:text disable-output-escaping="yes"><![CDATA[style="border-color:blue" ]]></xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="property[@name='border']/@value!=''">
        <xsl:text disable-output-escaping="yes"><![CDATA[border="]]></xsl:text>
        <xsl:value-of select="property[@name='border']/@value"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[" ]]></xsl:text>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

  <xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>
