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
  				<li><a href="${site.domain}/tags/index/${indexName}">Index ${indexName}</a></li>
  				<li><b>${tagName}</b></li>
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3">
			<c:if test="${not empty leftproducts}">
				<div style="border-bottom: 2px solid #ad3231;">
				<h3>Suggested Products</h3>
				</div>
				<c:forEach items="${leftproducts}" var="product">
					<div class="leftpad">
						<a rel="nofollow" href="${site.domain}/${product.name}" title="${product.title }"><img src="${site.resourceServer}/${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL }" alt="${product.title }" style="display: inline;"> </a>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<div class="col-xs-9">
		<c:choose>
			<c:when test="${empty products || (fn:length(products) < 1) }">
				<div class="alert alert-info">0 items found for <b>${tagName}</b> </div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<c:forEach items="${products }" var="product">
						<div class="col-xs-4">
							<div class="thumbnail productThumbnail productItem">
								<a href="${site.domain}/${product.name }" title="${product.title }">
		      						<img src="${site.resourceServer}/${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL }" alt="${product.title }" class="category-product-img">
								</a>
								<div>
									<span class="activeprice">	<hb:printPrice price="${product.actualPrice}"/>
									</span>
									<span class="priceDeprecated"><hb:printPrice price="${product.price}"/></span>
								</div>
								<p><a href="${site.domain}/${product.name}">${product.title}</a></p>
								<c:if test="${product.price > 0}">
									<fmt:parseNumber value="${(product.price - product.actualPrice)/product.price * 100 }" var="percentage" integerOnly="true"></fmt:parseNumber>
									<c:if test="${percentage > 10 }">
										<div class="sales-logo">
											<label class="text">Save</label>
											<label class="percentage">${percentage }%</label>
										</div>
									</c:if>
								</c:if>
		   					</div>
						</div>
					</c:forEach>	
				</div>
					
				<div class="row">
					<div class="col-xs-4">
						<ul class="pagination">
							<c:choose>
								<c:when test="${currentPageIndex == 0}">
									<li class="disabled"><a href="#">&lt;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentPageIndex-1}">&lt;</a></li>
								</c:otherwise>
							</c:choose>
							<c:forEach items="${pageIds }" var="item">
								<c:choose>
									<c:when test="${item < 0}">
										<li><a>...</a></li>
									</c:when>
									<c:otherwise>
										<c:choose>
										<c:when test="${item == currentPageIndex}">
										<li class="active"><a href="${site.domain}/c/${currentCategoryDetail.name }/${item}">${item+1} <span class="sr-only">(current)</span></a></li>
										</c:when>
										<c:otherwise>
										<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${item}">${item+1}</a></li>
										</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
		 					</c:forEach>
		 					<c:choose>
								<c:when test="${currentPageIndex+1 == totalPage}">
									<li class="disabled"><a href="#">&gt;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentPageIndex+1}">&gt;</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
					<div class="pagination col-xs-4">&nbsp;</div>
					<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
				</div>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>
