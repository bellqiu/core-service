<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.spotlight .bx-wrapper .bx-pager{
	display: none;
}
</style>
<div class="spotlight">
<h1 class="heading--large">Spotlight</h1>
	<ul class="product-gallary">
		<li><div class="productItem">1</div></li>
		<li><div class="productItem">2</div></li>
		<li><div class="productItem">3</div></li>
		<li><div class="productItem">4</div></li>
		<li><div class="productItem">5</div></li>
		<li><div class="productItem">6</div></li>
		<li><div class="productItem">7</div></li>
		<li><div class="productItem">8</div></li>
		<li><div class="productItem">9</div></li>
		<li><div class="productItem">10</div></li>
		<li><div class="productItem">11</div></li>
		<li><div class="productItem">12</div></li>
	</ul>
</div>

<script type="text/javascript">
$(".spotlight").ready(function(){
	  
	  $('.spotlight .product-gallary').bxSlider({
		  captions: true,
		  slideWidth: 230,
		  maxSlides: 5,
		  slideMargin: 10
		});
	});
</script>