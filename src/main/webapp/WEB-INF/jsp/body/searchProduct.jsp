<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<h1>Search Product result page</h1>
<%-- <div class="container mainContainer">
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
		<form role="form">
			<div>&nbsp;</div>
			<input id="minPrice" type="hidden" name="minPrice" />
			<input id="maxPrice" type="hidden" name="maxPrice" />
			<div id="price-search">
				<p>
  					<label for="amount">Price range:</label>
  					<input type="text" id="amount" disabled="disabled" style="border: 0; color: #f6931f; font-weight: bold;" />
				</p>
				<div id="slider-range"></div>
			</div>
			<div>&nbsp;</div>
			<div id="keyword-search">
				<div class="ui-widget">
  					<label for="keyword">Keywords: </label>
  					<input id="keyword" />
				</div>
			</div>
			<div>&nbsp;</div>
			<div id="tag-search">
				<div class="ui-widget">
  					<label for="tag">Tags: </label>
  					<input id="tag" />
				</div>
			</div>
			<div>&nbsp;</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
		</div>
		</div>
		<div class="col-xs-9" id="categoryProductListContainer">
			<jsp:include page="/WEB-INF/jsp/body/category/categoryProductList.jsp"></jsp:include>
		</div>
	</div>
</div>
 --%>  
