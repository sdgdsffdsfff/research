<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html lang="en">
    <head>
    <meta charset="utf-8" />
    </head>
  <body>
  <h2>My CD Collection</h2>
  <xsl:apply-templates/>
  </body>
  </html>
</xsl:template>

<xsl:template match="catalog/cd">
<p>
    <xsl:apply-templates select="title"/>
    <xsl:apply-templates select="artist"/>
</p>
</xsl:template>

<xsl:template match="title">
Title: <span style="color:#ff0000"><xsl:value-of select="."/></span>
<br/>
</xsl:template>

<xsl:template match="artist">
Artist: <span style="color:#00ff00"><xsl:value-of select="."/></span>
</xsl:template>


  <!-- 
    <table border="1">
      <tr bgcolor="#9acd32">
        <th align="left">Title</th>
        <th align="left">Artist</th>
      </tr>
      <xsl:for-each select="catalog/cd">
      <xsl:sort select="artist"/>
      <xsl:if test="price &gt; 10">
      <tr>
        <td><xsl:value-of select="title"/></td>
        <xsl:choose>
            <xsl:when test="price &gt; 10">
            <td bgcolor="#ff00ff"><xsl:value-of select="artist"/></td>
            </xsl:when>
            <xsl:when test="price &gt; 9">
            <td bgcolor="#cccccc"><xsl:value-of select="artist"/></td>
            </xsl:when>
            <xsl:otherwise>
            <td><xsl:value-of select="artist"/></td>
            </xsl:otherwise>
        </xsl:choose>
      </tr>
      </xsl:if>
      </xsl:for-each>
    </table> -->

</xsl:stylesheet>