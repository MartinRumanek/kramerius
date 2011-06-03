<xsl:stylesheet  version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="no" encoding="UTF-8" omit-xml-declaration="yes" />
    <xsl:param name="bundle_url" select="bundle_url" />
    <xsl:param name="bundle" select="document($bundle_url)/bundle" />
    <xsl:template match="/">
        <div>KRAMERIUS 4 <xsl:value-of select="$bundle/value[@key='home.document.type']"/>&#160;
        <xsl:for-each select="response/lst[@name='facet_counts']/lst[@name='facet_fields']/lst[@name='document_type']/int">
            <span><b><a>
                <xsl:attribute name="href">r.jsp?fq=document_type:<xsl:value-of select="@name" /></xsl:attribute>
                <xsl:attribute name="title">javascript:addNavigation('document_type', '<xsl:value-of select="@name" />')</xsl:attribute>
            <xsl:variable name="t"><xsl:choose>
            <xsl:when test=". = 1">document.type.<xsl:value-of select="@name" /></xsl:when>
            <xsl:when test=". &lt; 5">document.type.<xsl:value-of select="@name" />.2</xsl:when>
            <xsl:otherwise>document.type.<xsl:value-of select="@name" />.5</xsl:otherwise>
            </xsl:choose></xsl:variable>
            <xsl:value-of select="." />&#160;<xsl:value-of select="$bundle/value[@key=$t]"/></a></b></span>
        </xsl:for-each></div>
        <script type="text/javascript">
        <xsl:comment><![CDATA[
        $(document).ready(function(){
            $('#dt_home>div>span').hide();
            rollTypes(0);
        });
        function rollTypes(index){
            $('#dt_home>div>span:nth('+index+')').fadeOut(function(){
                var i = index + 1;
                if(i>=$('#dt_home>div>span').length) i=0;
                $('#dt_home>div>span:nth('+i+')').fadeIn();
                setTimeout('rollTypes('+i+')', 3000);
            });
        }

        ]]> </xsl:comment>
        </script>
    </xsl:template>
</xsl:stylesheet>