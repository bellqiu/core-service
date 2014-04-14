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
  				<li>${keyword}</li>
			</ol>
		</div>
		<div class="categoryTitle page-header">
			<h3><b>${currentCategoryDetail.displayName }</b></h3>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3">
		<div>
			<ul>
			<c:forEach items="${subCateogries}" var="item" varStatus="stat">
				<li class="subCategories">
					<a href="${site.domain}/c/${item.name}">${item.displayName}</a>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div>
			<c:if test="${not empty leftproducts && (fn:length(leftproducts) > 0)}">
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
		</div>
		<div class="col-xs-9" id="categoryProductListContainer">
			<jsp:include page="/WEB-INF/jsp/body/category/searchProductList.jsp"></jsp:include>
		</div>
	</div>
</div>

<style>
  .ui-autocomplete-loading {
    background: white url('/resources/bxslider/images/bx_loader.gif') right center no-repeat;
  }
  #keyword { width: 90% }
  #tag { width: 90% }
 </style>