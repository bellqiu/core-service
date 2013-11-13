<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld" %>

<div class="spotlight" id="featureProducts">
<h1 class="heading--large spotlightheader">Featured Products</h1>
	<ul class="product-gallary">
		<hb:tabProduct tabKey="FEATURE_PRODUCT" />
	</ul>
</div>

<script type="text/javascript">
$("#featureProducts").ready(function(){
	  
	  $('.spotlight .product-gallary').bxSlider({
		  captions: true,
		  slideWidth: 180,
		  maxSlides: 7,
		  slideMargin: 10
		});
	});
</script>
