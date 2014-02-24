<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<div class="container mainContainer">
	<div class="row">
		<div>
			<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<li>Index <span style="color:#ff6600">${indexName}</span></li>
			</ol>
		</div>
	</div>
	<div class="row tag-index">
		<div class="panel panel-default">
			<div class="quick-index">
			<strong>Quick Index:</strong>
			<a href="${site.domain}/tags/index/A">A</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/B">B</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/C">C</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/D">D</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/E">E</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/F">F</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/G">G</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/H">H</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/I">I</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/J">J</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/K">K</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/L">L</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/M">M</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/N">N</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/O">O</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/P">P</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/Q">Q</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/R">R</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/S">S</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/T">T</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/U">U</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/V">V</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/W">W</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/X">X</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/Y">Y</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/Z">Z</a> &nbsp;&nbsp;&nbsp;
			<a href="${site.domain}/tags/index/0-9">0-9</a> &nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
	<div class="row tag-index">
		<c:choose>
			<c:when test="${empty tags || (fn:length(tags) < 1) }">
				<div class="alert alert-info">0 items found for index ${indexName} </div>
			</c:when>
			<c:otherwise>
			<div class="panel panel-default">
			<div id="tag-index-panel" class="quick-index">
            <ul>
            <c:forEach var="tag" items="${tags}">
				<li><a href="${site.domain}/tags/key/${tag}">${tag}</a></li>
			</c:forEach>
        	<!-- <li><a href="/Wholesale-Airplane-Shaped-MP3-Player.html">Airplane Shaped MP3 Player</a></li>
         	<li><a href="/Wholesale-Android-4.2-Phone-Tablet-PC-With-3G.html">Android 4.2 Phone Tablet PC With 3G</a></li>
         	<li><a href="/Wholesale-A-Line-Dresses-Sale.html">A Line Dresses Sale</a></li>
         	<li><a href="/Wholesale-Anodized-Hand-Writing-Touch-Pen.html">Anodized Hand Writing Touch Pen</a></li>
	         <li><a href="/Wholesale-Anime-Figure.html">Anime Figure</a></li>
	         <li><a href="/Wholesale-Apollo-One.html">Apollo One</a></li>
	         <li><a href="/Wholesale-Ac-Adapter-Usb.html">Ac Adapter Usb</a></li>
	         <li><a href="/Wholesale-Acetone-Nail-Polish-Remover.html">Acetone Nail Polish Remover</a></li> -->
	         
         	</ul>
			</div>
			<div class="tag-pagination">
				<ul class="pagination">
				<li class="disabled"><a href="#">&lt;</a></li>
				<li class="active"><a href="${site.domain}/tags/index/${indexName}/0">1 <span class="sr-only">(current)</span></a></li>
				<li><a href="${site.domain}/tags/index/${indexName}/1">2</a></li>
				<li><a href="${site.domain}/tags/index/${indexName}/2">3</a></li>
				<li><a href="${site.domain}/tags/index/${indexName}/3">4</a></li>
				<li><a href="${site.domain}/tags/index/${indexName}/4">5</a></li>
				<li><a>...</a></li>
				<li><a href="${site.domain}/tags/index/${indexName}/16">17</a></li>
				<li><a href="${site.domain}/tags/index/${indexName}/1">&gt;</a></li>
				</ul>
			</div>
			</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
