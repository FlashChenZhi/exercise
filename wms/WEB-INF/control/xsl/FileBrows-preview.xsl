<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" omit-xml-declaration="no" />
<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>

  <!-- otput "INPUT" -->
  <input type="file">

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

    <!-- set attribute "disabled" -->
    <xsl:if test="property[@name='enabled']/@value!='true'">
      <xsl:attribute name="disabled">true</xsl:attribute>
    </xsl:if>

    <!-- set attribute "style" -->
    <xsl:attribute name="style">

      <!-- set attribute "ime-mode" -->
      <xsl:text>ime-mode:auto;</xsl:text>

      <!-- set attribute "text-align" -->
      <xsl:text>text-align:left;</xsl:text>

    </xsl:attribute>

  </input>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>