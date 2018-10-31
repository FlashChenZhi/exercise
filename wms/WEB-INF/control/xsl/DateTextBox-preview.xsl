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

  <!-- otput "INPUT" -->
<span>
    <input type="text">

    <!-- set attribute "value" -->
    <xsl:attribute name="value">
      <xsl:apply-templates select="property[@name='format']"/>
    </xsl:attribute>

    <!-- set attribute "readonly" -->
    <xsl:if test="property[@name='readOnly']/@value='true'">
      <xsl:attribute name="readonly">true</xsl:attribute>
    </xsl:if>

    <!-- set attribute "size" -->
    <xsl:attribute name="size">
      <xsl:apply-templates select="property[@name='size']"/>
    </xsl:attribute>

    <!-- set attribute "css" -->
    <xsl:if test="property[@name='cssClass']/@value!=''">
      <xsl:attribute name="class">
        <xsl:apply-templates select="property[@name='cssClass']"/>
      </xsl:attribute>
    </xsl:if>

    <!-- set attribute "style" -->
    <xsl:attribute name="style">

      <!-- set attribute "ime-mode" -->
      <xsl:text>ime-mode:disabled;</xsl:text>

      <!-- set attribute "text-align" -->
      <xsl:text>text-align:left;</xsl:text>

      <!-- set attribute "readonly's color" -->
      <xsl:if test="property[@name='readOnly']/@value='true'">
        <xsl:text>color:#000000</xsl:text>
      </xsl:if>

    </xsl:attribute>

    <!-- set attribute default "style" -->
    <xsl:if test="count(property)='0'">
        <xsl:attribute name="style">
            <xsl:text>ime-mode:disabled; text-align:left; font-weight:bold; font-size:100%; line-height:120%; font-family:monospace; text-decoration:none; border-style:solid; border-width:2px; border-color:#6a628b #d6d1ea #d6d1ea #6a628b; height:1.75em; margin-top:4px; margin-bottom:4px;</xsl:text>
        </xsl:attribute>
    </xsl:if>

</input>
<xsl:if test="property[@name='showCalendar']/@value='true'">
  <xsl:text disable-output-escaping="yes"><![CDATA[<input type="button" class="calendar" disabled="true"]]></xsl:text>
  <xsl:if test="property[@name='readOnly']/@value='true'">
    <xsl:text disable-output-escaping="yes"><![CDATA[ style="visibility:hidden;"]]></xsl:text>
  </xsl:if>
  <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
  <xsl:text disable-output-escaping="yes"><![CDATA[<input type="hidden" />]]></xsl:text>
</xsl:if>
</span>
</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>