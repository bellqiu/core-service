<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container mainContainer">
	<div class="row">
		<div class="col-xs-3">
			<div>&nbsp;</div>
			<div id="price-search">
				<p>
  					<label for="amount">Price range:</label>
  					<input type="text" id="amount" style="border: 0; color: #f6931f; font-weight: bold;" />
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
								<li><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex-1}">&lt;</a></li>
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
  										<li class="active"><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${item}">${item+1} <span class="sr-only">(current)</span></a></li>
  										</c:when>
  										<c:otherwise>
  										<li><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${item}">${item+1}</a></li>
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
								<li><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex+1}">&gt;</a></li>
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
								<a href="${site.resourceServer}/${item.name }" title="${item.title }">
      							<img src="${site.resourceServer}/rs/img/${item.imageURL }" alt="${item.title }">
								</a>
      							<div class="caption">
        						<!-- <h3>Thumbnail label</h3> -->
        						<p><a href="${site.resourceServer}/${item.name }">${item.title }</a></p>
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
								<li><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex-1}">&lt;</a></li>
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
  										<li class="active"><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${item}">${item+1} <span class="sr-only">(current)</span></a></li>
  										</c:when>
  										<c:otherwise>
  										<li><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${item}">${item+1}</a></li>
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
								<li><a href="${site.resourceServer}/c/${currentCategoryDetail.name }/${currentCategoryPageIndex+1}">&gt;</a></li>
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

<script>
  $(function() {
    $( "#slider-range" ).slider({
      range: true,
      min: ${lowestPrice},
      max: ${highestPrice},
      values: [ ${lowestPrice}, ${highestPrice} ], 
      slide: function( event, ui ) {
        $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
      }
    });
    $( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +
      " - $" + $( "#slider-range" ).slider( "values", 1 ) );
    
    $( "#keyword" ).autocomplete({
        source: function( request, response ) {
          $.ajax({
            //url: "http://ws.geonames.org/searchJSON",
            url: "/ajax/test?startWith=aa",
            dataType: "jsonp",
            data: {
              featureClass: "P",
              style: "full",
              maxRows: 12,
              name_startWith: request.term
            },
            /* success: function( data ) {
              response( $.map( data.geonames, function( item ) {
                return {
                  label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
                  value: item.name
                }
              }));
            } */
            success: function( data ) {
                response( $.map( data, function( item ) {
                  return {
                    label: item,
                    value: item
                  }
                }));
              }
          });
      },
      minLength: 2,
      select: function( event, ui ) {
    	  // TODO add logic for the select item
        console.log( ui.item ?
          "Selected: " + ui.item.label :
          "Nothing selected, input was " + this.value);
      },
      open: function() {
        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
      },
      close: function() {
        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
      }
      });
    
    $( "#tag" ).autocomplete({
        source: function( request, response ) {
          $.ajax({
            url: "http://ws.geonames.org/searchJSON",
            dataType: "jsonp",
            data: {
              featureClass: "P",
              style: "full",
              maxRows: 12,
              name_startWith: request.term
            },
            success: function( data ) {
              response( $.map( data.geonames, function( item ) {
                return {
                  label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
                  value: item.name
                }
              }));
            }
          });
      },
      minLength: 2,
      select: function( event, ui ) {
    	  // TODO add logic for the select item
        console.log( ui.item ?
          "Selected: " + ui.item.label :
          "Nothing selected, input was " + this.value);
      },
      open: function() {
        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
      },
      close: function() {
        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
      }
      });
  });
</script>
  
<style>
  .ui-autocomplete-loading {
    background: white url('/resources/bxslider/images/bx_loader.gif') right center no-repeat;
  }
  #keyword { width: 90% }
  #tag { width: 90% }
  </style>