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

  <!-- output "IMG" -->
  <xsl:text disable-output-escaping="yes"><![CDATA[<input type='image' style='cursor:default' disabled src="]]></xsl:text>

  <!-- set attribute "src" -->
  <xsl:value-of select="$image_root"/>
  <xsl:text disable-output-escaping="yes"><![CDATA[GanttChart.png]]></xsl:text>
  <xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text>

  <!-- set attribute "border" -->
  <xsl:text disable-output-escaping="yes"><![CDATA[ border="0"]]></xsl:text>

  <!-- set attribute "width" -->
   <xsl:if test="property[@name='width']/@value!=''">
    <xsl:text disable-output-escaping="yes"><![CDATA[ width="]]></xsl:text>
    <xsl:apply-templates select="property[@name='width']"/>
    <xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text>
  </xsl:if>

  <!-- set attribute "height" -->
   <xsl:if test="property[@name='height']/@value!=''">
    <xsl:text disable-output-escaping="yes"><![CDATA[ height="]]></xsl:text>
    <xsl:apply-templates select="property[@name='height']"/>
    <xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text>
  </xsl:if>

  <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>
