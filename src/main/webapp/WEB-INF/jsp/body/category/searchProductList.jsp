<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<c:choose>
	<c:when test="${empty productSummary || (fn:length(productSummary) < 1) }">
		<div class="alert alert-info">0 items found for ${keySpace} </div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info"><fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber> items found for ${keySpace} </div>
		<div class="row">
			<div class="col-xs-8">
				<ul class="pagination">
					<c:choose>
						<c:when test="${currentPageIndex == 0}">
							<li class="disabled"><a href="#">&lt;</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${site.domain}/search?keyword=${keyword}&page=${currentPageIndex-1}">&lt;</a></li>
						</c:otherwise>
					</c:choose>
					<c:forEach items="${pageIds}" var="item">
  						<c:choose>
  							<c:when test="${item < 0}">
  								<li><a>...</a></li>
  							</c:when>
  							<c:otherwise>
  								<c:choose>
  									<c:when test="${item == currentPageIndex}">
  										<li class="active"><a href="${site.domain}/search?keyword=${keyword}&page=${item}">${item+1} <span class="sr-only">(current)</span></a></li>
  									</c:when>
  									<c:otherwise>
  										<li><a href="${site.domain}/search?keyword=${keyword}&page=${item}">${item+1}</a></li>
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
							<li><a href="${site.domain}/search?keyword=${keyword}&page=${currentPageIndex+1}">&gt;</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
		</div>
					
		<div class="row">
			<c:forEach items="${productSummary }" var="item">
				<div class="col-xs-4">
					<div class="thumbnail productThumbnail productItem">
						<a href="${site.domain}/${item.name }" title="${item.title }">
      						<img src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${item.imageURL }" alt="${item.title }" class="category-product-img">
						</a>
						<div>
							<span class="activeprice">	<hb:printPrice price="${item.actualPrice}"/>
							</span>
							<span class="priceDeprecated"><hb:printPrice price="${item.price}"/></span>
						</div>
						<p><a href="${site.domain}/${item.name}">${item.title}
						</a></p>
						<c:if test="${item.price > 0}">
							<fmt:parseNumber value="${(item.price - item.actualPrice)/item.price * 100 }" var="percentage" integerOnly="true"></fmt:parseNumber>
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
  							<!-- <li><a href="#">&laquo;</a></li> -->
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
  							<!-- <li><a href="#">&raquo;</a></li> -->
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