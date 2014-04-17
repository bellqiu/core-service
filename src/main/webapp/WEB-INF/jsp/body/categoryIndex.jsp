<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<%--
<script src="/resources/ui/jquery-ui-1.10.3.min.js"></script>
<!--
<link rel="stylesheet" href="/resources/ui/themes/smoothness/jquery-ui-1.10.3.css"> -->
<!-- <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
--%>

<div class="container mainContainer">
	<div class="row">
		<div>
			<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<c:forEach items="${categoryBreadcrumbs}" var="category" varStatus="stat">
  				<c:choose>
				<c:when test="${stat.last}">
					<li class="active">${category.displayName}</li> 
				</c:when>
				<c:otherwise>
					<li><a href="${site.domain}/c/${category.name}">${category.displayName}</a>
				</c:otherwise>
				</c:choose>
				</c:forEach>
			</ol>
		</div>
		<div class="categoryTitle page-header">
			<h3>${currentCategoryDetail.description}</h3>
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
		<%-- <form role="form">
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
		</form> --%>
		</div>
		</div>
		<div class="col-xs-9" id="categoryProductListContainer">
			<c:choose>
				<c:when test="${empty products || (fn:length(products) < 1) }">
					<div class="alert alert-info">0 items found for <b>${currentCategoryDetail.displayName}</b> </div>
				</c:when>
				<c:otherwise>
					<jsp:include page="/WEB-INF/jsp/body/category/categoryProductList.jsp"></jsp:include>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<%--
<script>
  $(function() {
	var lowestPrice = parseFloat("${lowestPrice}");
	var highestPrice =  parseFloat("${highestPrice}");
	var currencySignal = "<hb:printCurrency />";
	var clp = parseFloat("${currentLowestPrice}");
	var hlp = parseFloat("${currentHighestPrice}");
	//var currencySignal = "<hb:printPrice price='${lowestPrice}'/>".replace(/[\d]+\.?[\d]*/,"")
    $( "#slider-range" ).slider({
      range: true,
      min: lowestPrice,
      max: highestPrice,
      //values: [ lowestPrice, highestPrice ],
      values: [ clp, hlp ],
      slide: function( event, ui ) {
        $( "#amount" ).val( currencySignal + ui.values[ 0 ] + " - " + currencySignal + ui.values[ 1 ] );
      },
      stop: function( event, ui ) {
    	  console.log("OK");
    	  //console.log($( "#slider-range" ).slider( "values", 0));
    	  //console.log($( "#slider-range" ).slider( "values", 1));
    	  // TODO add logic for search
    	  //$("#categoryProductListContainer").html("").load("/c/${currentCategoryDetail.name}");
    	  window.location.href=window.location.protocol+"//"+window.location.host + "/c/${currentCategoryDetail.name}?low=" + $( "#slider-range" ).slider( "values", 0) + "&high=" + $( "#slider-range" ).slider( "values", 1);
      }
    
    });
    $( "#amount" ).val( currencySignal + $( "#slider-range" ).slider( "values", 0 ) +
      " - " + currencySignal + $( "#slider-range" ).slider( "values", 1 ) );
    
  });
</script>
--%>
<style>
  .ui-autocomplete-loading {
    background: white url('/resources/bxslider/images/bx_loader.gif') right center no-repeat;
  }
 </style>
 