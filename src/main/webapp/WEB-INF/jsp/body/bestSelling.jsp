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
	<div id="column1-prev" class="controller-prev"></div>
	<div id="column1-next" class="controller-next"></div>
	</div>
	<div class="col-md-4 col-xs-4">
	<ul id="bestSelling-column2" class="product-gallary">
		<hb:tabProduct tabKey="BEST_PRODUCT2"/>
	</ul>
	<div id="column2-prev" class="controller-prev"></div>
	<div id="column2-next" class="controller-next"></div>
	</div>
	<div class="col-md-4 col-xs-4">
	<ul id="bestSelling-column3" class="product-gallary">
		<hb:tabProduct tabKey="BEST_PRODUCT3"/>
	</ul>
	<div id="column3-prev" class="controller-prev"></div>
	<div id="column3-next" class="controller-next"></div>
	</div>
	</div>
</div>

<script type="text/javascript">
$(".bestSelling").ready(function(){
  $('#bestSelling-column1').bxSlider({
	  nextSelector: '#column1-next',
	  prevSelector: '#column1-prev',
	  nextText: 'Down',
	  prevText: 'Up',
	  mode: 'vertical',
	  maxSlides: 2,
	  slideWidth: 390,
	  slideMargin: 20
	});
  
  $('#bestSelling-column2').bxSlider({
	  nextSelector: '#column2-next',
	  prevSelector: '#column2-prev',
	  nextText: 'Down',
	  prevText: 'Up',
	  mode: 'vertical',
	  maxSlides: 2,
	  slideWidth: 390,
	  slideMargin: 20
	});
  
  $('#bestSelling-column3').bxSlider({
	  nextSelector: '#column3-next',
	  prevSelector: '#column3-prev',
	  nextText: 'Down',
	  prevText: 'Up',
	  mode: 'vertical',
	  maxSlides: 2,
	  slideWidth: 390,
	  slideMargin: 20
	});
});
</script>