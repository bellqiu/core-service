<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<div class="container mainContainer">
	<div class="row">
		<div>
			<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<c:forEach items="${categoryBreadcrumbs}" var="item" varStatus="stat">
  				<c:choose>
				<c:when test="${stat.last}">
					<li class="active">${item}</li> 
				</c:when>
				<c:otherwise>
					<li><a href="${site.domain}/c/${item}">${item}</a>
				</c:otherwise>
				</c:choose>
				</c:forEach>
			</ol>
		</div>
		<div class="categoryTitle page-header">
			<h3><b>${currentCategoryDetail.displayName }</b></h3>
		</div>
	</div>
</div>