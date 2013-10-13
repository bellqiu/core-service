<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.bestSelling .bx-wrapper .bx-pager{
	display: none;
}
</style>
<div class="container-fluid bestSelling">
<h1 class="heading--large">Best-selling</h1>
	<div class="row-fluid">
	<div class="span4">
	<ul id="bestSelling-column1" class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	<div id="column1-prev" class="controller-prev"></div>
	<div id="column1-next" class="controller-next"></div>
	</div>
	<div class="span4">
	<ul id="bestSelling-column2" class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	<div id="column2-prev" class="controller-prev"></div>
	<div id="column2-next" class="controller-next"></div>
	</div>
	<div class="span4">
	<ul id="bestSelling-column3" class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
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