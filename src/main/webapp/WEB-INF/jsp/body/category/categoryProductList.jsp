<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<%--<script>
	/* var subParameter = "${pfilter ? '?low='+currentLowestPrice+'&high='+currentHighestPrice : ''}"; */
	var subParameter = ""/*"${pStr}"*/;
</script>--%>
<script>
	var subParameter = "";
</script>
<c:choose>
	<c:when test="${empty products || (fn:length(products) < 1) }">
		<div class="alert alert-info">0 items found for <b>${currentCategoryDetail.displayName}</b> </div>
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-xs-8">
				<ul class="pagination">
					<c:choose>
						<c:when test="${currentPageIndex == 0}">
							<li class="disabled"><a href="#">&lt;</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentPageIndex-1}${pStr}">&lt;</a></li>
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
  										<li class="active"><a href="${site.domain}/c/${currentCategoryDetail.name }/${item}${pStr}">${item+1} <span class="sr-only">(current)</span></a></li>
  									</c:when>
  									<c:otherwise>
  										<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${item}${pStr}">${item+1}</a></li>
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
							<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentPageIndex+1}${pStr}">&gt;</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
		</div>
					
		<div class="row">
			<c:forEach items="${products}" var="product">
				<div class="col-xs-4">
					<div class="thumbnail productThumbnail productItem">
						<a href="${site.domain}/${product.name }" title="${product.title }">
      						<img src="${site.resourceServer}${site.webResourcesFolder }${site.productImageResourcesFolder}/${product.imageURL }" alt="${product.title }" class="category-product-img">
						</a>
						<div>
							<span class="activeprice">	<hb:printPrice price="${product.actualPrice}"/>
							</span>
							<span class="priceDeprecated"><hb:printPrice price="${product.price}"/></span>
						</div>
						<p><a href="${site.domain}/${product.name}">${product.title}
						<!-- <script type="text/javascript">
						var title = "${product.title}";
						if(title.length > 90) {
							title = title.substring(0,87) + "..."
						}
						document.write(title);
						</script> -->
						</a></p>
						<c:if test="${product.price > 0}">
							<fmt:parseNumber value="${(product.price - product.actualPrice)/product.price * 100 }" var="percentage" integerOnly="true"></fmt:parseNumber>
							<c:if test="${percentage > 10 }">
								<div class="sales-logo">
									<label class="text">Save</label>
									<label class="percentage">${percentage }%</label>
								</div>
							</c:if>
						</c:if>
						<div>
							<span class="likeSpan" data-id="${product.id}">Like (<span id="likeNum">${product.like}</span>)</span>
							<span class="gray">|</span>
							<span><span class="sold">${product.sold}</span><span class="gray"> Sold</span></span>
						</div>
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
							<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentPageIndex-1}${pStr}">&lt;</a></li>
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
								<li class="active"><a href="${site.domain}/c/${currentCategoryDetail.name }/${item}${pStr}">${item+1} <span class="sr-only">(current)</span></a></li>
								</c:when>
								<c:otherwise>
								<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${item}${pStr}">${item+1}</a></li>
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
							<li><a href="${site.domain}/c/${currentCategoryDetail.name }/${currentPageIndex+1}${pStr}">&gt;</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="pagination col-xs-4">&nbsp;</div>
			<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
		</div>
	</c:otherwise>
</c:choose>

<script src="/resources/js/categoryIndex.js" type="text/javascript"></script>