<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld" %>
<style type="text/css">
.bestSelling .bx-wrapper .bx-pager{
	display: none;
}
</style>
<div class="container-fluid bestSelling">
<h1 class="heading--large">Best-selling</h1>
	<div class="row-fluid">
	<div class="col-md-4 col-xs-4">
	<ul id="bestSelling-column1" class="product-gallary">
		<hb:tabProduct tabKey="BEST_PRODUCT1"/>
	</ul>
	</div>
	<div class="col-md-4 col-xs-4">
	<ul id="bestSelling-column2" class="product-gallary">
		<hb:tabProduct tabKey="BEST_PRODUCT2"/>
	</ul>
	</div>
	<div class="col-md-4 col-xs-4">
	<ul id="bestSelling-column3" class="product-gallary">
		<hb:tabProduct tabKey="BEST_PRODUCT3"/>
	</ul>
	</div>
	</div>
</div>

<script type="text/javascript">
$(".bestSelling").ready(function(){
  $('#bestSelling-column1').bxSlider({
	  mode: 'vertical',
	  maxSlides: 2,
	  slideWidth: 230,
	  slideMargin: 20,
	  auto : true,
	  randomStart:true,
	  controls: false,
	  pager : false
	});
  
  $('#bestSelling-column2').bxSlider({
	  mode: 'vertical',
	  maxSlides: 2,
	  slideWidth: 230,
	  slideMargin: 20,
	  auto : true,
	  randomStart:true,
	  controls: false,
	  pager : false
	});
  
  $('#bestSelling-column3').bxSlider({
	  mode: 'vertical',
	  maxSlides: 2,
	  slideWidth: 230,
	  slideMargin: 20,
	  auto : true,
	  randomStart:true,
	  controls: false,
	  pager : false
	});
});
</script>