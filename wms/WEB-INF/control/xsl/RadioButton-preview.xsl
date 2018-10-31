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
  <input type="radio">

    <!-- set attribute "value" -->
    <xsl:attribute name="value">
      <xsl:apply-templates select="property[@name='value']"/>
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

    <!-- set attribute "name" -->
    <xsl:if test="property[@name='groupName']/@value!=''">
      <xsl:attribute name="name">
        <xsl:apply-templates select="property[@name='groupName']"/>
      </xsl:attribute>
    </xsl:if>

    <!-- set attribute "checked" -->
    <xsl:if test="property[@name='checked']/@value='true'">
      <xsl:attribute name="checked">true</xsl:attribute>
    </xsl:if>

    <!-- set attribute "disabled" -->
    <xsl:if test="property[@name='enabled']/@value='false'">
      <xsl:attribute name="disabled">true</xsl:attribute>
    </xsl:if>

  </input>

  <!-- otput "LABEL" -->
  <label>
    <!-- set attribute "css" -->
    <xsl:if test="property[@name='labelCssClass']/@value!=''">
      <xsl:attribute name="class">
        <xsl:apply-templates select="property[@name='labelCssClass']"/>
      </xsl:attribute>
    </xsl:if>

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

    </xsl:choose>

  </label>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>