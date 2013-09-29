<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container-fluid bestSelling">
<h1 class="heading--large">Best-selling</h1>
	<div class="row-fluid">
	<div class="span2">
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	</div>
	<div class="span2">
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	</div>
	<div class="span2">
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	</div>
	<div class="span2">
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	</div>
	<div class="span2">
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	</div>
	<div class="span2">
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
	</ul>
	</div>
	</div>
</div>

<script type="text/javascript">
$(".bestSelling").ready(function(){
	  
	  $('.bestSelling .product-gallary').bxSlider({
		  mode: 'vertical',
		  minSlides: 1
		});
	});
</script>