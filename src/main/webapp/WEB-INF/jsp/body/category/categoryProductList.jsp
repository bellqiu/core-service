<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<c:choose>
				<c:when test="${noproduct }">
					<div class="alert alert-info">No products</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<div class="col-xs-4">
						<ul class="pagination">
							<c:choose>
							<c:when test="${currentCategoryPageIndex == 0}">
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex-1}">&lt;</a></li>
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
  										<c:when test="${item == currentCategoryPageIndex}">
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
							<c:when test="${currentCategoryPageIndex+1 == totalPage}">
								<li class="disabled"><a href="#">&gt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex+1}">&gt;</a></li>
							</c:otherwise>
							</c:choose>
						</ul>
						</div>
						<div class="pagination col-xs-4">&nbsp;</div>
						<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
					</div>
					
					<div class="row">
						<c:forEach items="${productSummary }" var="item">
							<div class="col-xs-4 productThumbnail">
								<div class="thumbnail">
								<a href="${site.domain}/${item.name }" title="${item.title }">
      							<img src="${site.resourceServer}/rs/img/${item.imageURL }" alt="${item.title }">
								</a>
      							<div class="caption">
        						<!-- <h3>Thumbnail label</h3> -->
        						<p><a href="${site.domain}/${item.name }">${item.title }</a></p>
      							</div>
   		 						</div>
							</div>
						</c:forEach>
					</div>
					
					<div class="row">
						<div class="col-xs-4">
						<ul class="pagination">
							<c:choose>
							<c:when test="${currentCategoryPageIndex == 0}">
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex-1}">&lt;</a></li>
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
  										<c:when test="${item == currentCategoryPageIndex}">
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
							<c:when test="${currentCategoryPageIndex+1 == totalPage}">
								<li class="disabled"><a href="#">&gt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex+1}">&gt;</a></li>
							</c:otherwise>
							</c:choose>
						</ul>
						</div>
						<div class="pagination col-xs-4">&nbsp;</div>
						<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
					</div>
					
				</c:otherwise>
			</c:choose>