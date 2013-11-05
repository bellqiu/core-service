<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container mainContainer">
	${currentCategoryDetail.name }
	<div class="row">
		<div class="col-xs-3">
			<h1>Left Panel Content</h1>
		</div>
		<div class="col-xs-9">
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
								<li><a href="/c/${currentCategoryDetail.name }/${currentCategoryPageIndex-1}">&lt;</a></li>
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
  										<li class="active"><a href="/c/${currentCategoryDetail.name }/${item}">${item+1} <span class="sr-only">(current)</span></a></li>
  										</c:when>
  										<c:otherwise>
  										<li><a href="/c/${currentCategoryDetail.name }/${item}">${item+1}</a></li>
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
								<li><a href="/c/${currentCategoryDetail.name }/${currentCategoryPageIndex+1}">&gt;</a></li>
							</c:otherwise>
							</c:choose>
						</ul>
						</div>
						<div class="pagination col-xs-4">&nbsp;</div>
						<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
					</div>
					
					<div class="row">
						<c:forEach items="${productSummary }" var="item">
							<div class="col-xs-4">
								<div class="thumbnail">
      							<img src="/rs/img/${item.imageURL }" alt="...">
      							<div class="caption">
        						<!-- <h3>Thumbnail label</h3> -->
        						<p><a href="/${item.name }">${item.title }</a></p>
      							</div>
   		 						</div>
							</div>
						</c:forEach>
						<div class="col-xs-4">
							<div class="thumbnail">
      						<img src="http://www.honeybuy.com/image/6750152374603175_690X500.jpg" alt="...">
      						<div class="caption">
        					<!-- <h3>Thumbnail label</h3> -->
        					<p><a href="#">One Shoulder Long Chiffon Prom Dress with Watteau Train</a></p>
      						</div>
   		 					</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<%-- <div class="row">
				<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
				<div class="pagination col-xs-4">&nbsp;</div>
				<div class="col-xs-4">
					<ul class="pagination">
  						<li><a href="#">&laquo;</a></li>
  						<li><a href="#">1</a></li>
  						<li><a>...</a></li>
  						<li><a href="#">2</a></li>
  						<li><a href="#">3</a></li>
  						<li><a href="#">4</a></li>
  						<li><a>...</a></li>
  						<li><a href="#">5</a></li>
  						<li><a href="#">&raquo;</a></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-4">
					<div class="thumbnail">
      					<img src="http://www.honeybuy.com/image/6750152374603175_690X500.jpg" alt="...">
      					<div class="caption">
        				<!-- <h3>Thumbnail label</h3> -->
        				<p><a href="#">One Shoulder Long Chiffon Prom Dress with Watteau Train</a></p>
      					</div>
   		 			</div>
				</div>
			</div> --%>
		</div>
	</div>
</div>
