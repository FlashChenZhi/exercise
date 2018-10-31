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
  <input type="text">

    <!-- set attribute "maxlength" -->
    <xsl:attribute name="maxlength">
      <xsl:variable name="len" select="number(property[@name='length']/@value)"/>
      <xsl:variable name="dec" select="number(property[@name='decimalLength']/@value)"/>

      <xsl:choose>

        <!-- When decimalLength exists -->
        <xsl:when test="property[@name='decimalLength']/@value!=''">
          <xsl:value-of select="number($len + $dec + 1)"/>
        </xsl:when>

        <!-- When decimalLength does not exist -->
        <xsl:otherwise>
          <xsl:value-of select="number($len)"/>
        </xsl:otherwise>

      </xsl:choose>

    </xsl:attribute>

    <!-- set attribute "value" -->
    <xsl:attribute name="value">

      <xsl:call-template name="loop" >
        <xsl:with-param name="len" select="number(property[@name='length']/@value)" />
        <xsl:with-param name="dec" select="number(property[@name='decimalLength']/@value)" />
      </xsl:call-template>

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
      <xsl:text>text-align:right;</xsl:text>

      <!-- set attribute "readonly's color" -->
      <xsl:if test="property[@name='readOnly']/@value='true'">
        <xsl:text>color:#000000</xsl:text>
      </xsl:if>

    </xsl:attribute>

  </input>
</xsl:template>

<!-- loop of regulation -->
<xsl:template name="loop">

  <xsl:param name="len" />
  <xsl:param name="len_count" select="1" />
  
  <xsl:param name="dec" />
  <xsl:param name="dec_count" select="1" />

  <xsl:param name="value" select = "''" />
  <xsl:param name="dec_flag" select = "'false'"/> 

  <xsl:choose>

    <xsl:when test="$len_count &lt;= $len">
      <xsl:call-template name="loop">
        <xsl:with-param name="len" select="$len" /> 
        <xsl:with-param name="len_count" select="$len_count + 1" />
        <xsl:with-param name="dec" select="$dec" /> 
        <xsl:with-param name="dec_count" select="$dec_count" />
        <xsl:with-param name="value" select="concat($value, '9')" /> 
      </xsl:call-template>
    </xsl:when>

    <xsl:when test=" $dec_flag = 'false' ">

      <xsl:choose>
        <xsl:when test=" contains($dec, 'NaN') ">

          <xsl:call-template name="loop">
            <xsl:with-param name="len" select="$len" /> 
            <xsl:with-param name="len_count" select="$len_count + 1" />
            <xsl:with-param name="dec" select="$dec" /> 
            <xsl:with-param name="dec_count" select="$dec_count" />
            <xsl:with-param name="value" select="$value" /> 
            <xsl:with-param name="dec_flag" select="'true'" /> 
          </xsl:call-template>

        </xsl:when>

        <xsl:otherwise>
          <xsl:call-template name="loop">
            <xsl:with-param name="len" select="$len" /> 
            <xsl:with-param name="len_count" select="$len_count + 1" />
            <xsl:with-param name="dec" select="$dec" /> 
            <xsl:with-param name="dec_count" select="$dec_count" />
            <xsl:with-param name="value" select="concat($value, '.')" /> 
            <xsl:with-param name="dec_flag" select="'true'" /> 
          </xsl:call-template>
        </xsl:otherwise>

      </xsl:choose>

    </xsl:when>

     <xsl:when test="$dec_count &lt;= $dec">
       <xsl:call-template name="loop">
         <xsl:with-param name="len" select="$len" /> 
         <xsl:with-param name="len_count" select="$len_count + 1" />
         <xsl:with-param name="dec" select="$dec" /> 
         <xsl:with-param name="dec_count" select="$dec_count + 1" />
         <xsl:with-param name="value" select="concat($value, '9')" /> 
         <xsl:with-param name="dec_flag" select="'true'" />
       </xsl:call-template>
     </xsl:when>

     <xsl:otherwise>

       <xsl:choose>

         <xsl:when test="property[@name='comma']/@value = 'true' ">
           <xsl:value-of select="format-number($value,'#,###.###########')"/>
         </xsl:when>

         <xsl:otherwise>
           <xsl:value-of select="$value"/>
         </xsl:otherwise>
 
       </xsl:choose>

     </xsl:otherwise>
   </xsl:choose>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>
