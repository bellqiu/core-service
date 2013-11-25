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
	<div class="row">
		<div class="col-xs-3">
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
		<div class="col-xs-9" id="categoryProductListContainer">
			<jsp:include page="/WEB-INF/jsp/body/category/categoryProductList.jsp"></jsp:include>
		</div>
	</div>
</div>

<script>
  $(function() {
	var lowestPrice = parseFloat("<hb:printPrice price='${lowestPrice}'/>".replace(/[^\d]+/,""));
	var highestPrice =  parseFloat("<hb:printPrice price='${highestPrice}'/>".replace(/[^\d]+/,""));
	var currencySignal = "<hb:printPrice price='${lowestPrice}'/>".replace(/[\d]+\.?[\d]*/,"");
    $( "#slider-range" ).slider({
      range: true,
      min: lowestPrice,
      max: highestPrice,
      values: [ lowestPrice, highestPrice ], 
      slide: function( event, ui ) {
        $( "#amount" ).val( currencySignal + ui.values[ 0 ] + " - " + currencySignal + ui.values[ 1 ] );
      },
      stop: function( event, ui ) {
    	  console.log("OK");
    	  $("#categoryProductListContainer").html("").load("/seach/c/test");
      }
    
    });
    $( "#amount" ).val( currencySignal + $( "#slider-range" ).slider( "values", 0 ) +
      " - " + currencySignal + $( "#slider-range" ).slider( "values", 1 ) );
    
    $( "#keyword" ).autocomplete({
        source: function( request, response ) {
          $.ajax({
            url: "/ajax/c/${currentCategoryDetail.name}",
            dataType: "jsonp",
            data: {
              key: "keywords",
              maxRows: 12,
              startWith: request.term
            },
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
            url: "/ajax/c/${currentCategoryDetail.name}",
            dataType: "jsonp",
            data: {
            	key: "tags",
                maxRows: 12,
                startWith: request.term
            },
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
  });
</script>
  
<style>
  .ui-autocomplete-loading {
    background: white url('/resources/bxslider/images/bx_loader.gif') right center no-repeat;
  }
  #keyword { width: 90% }
  #tag { width: 90% }
  </style>