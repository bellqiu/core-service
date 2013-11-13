<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld" %>
<div class="spotlight">
<h1 class="heading--large spotlightheader">Spotlight</h1>
	<ul class="product-gallary">
	<hb:tabProduct tabKey="SPOTLIGHT_PRODUCT"/>
	</ul>
</div>

<script type="text/javascript">
$(".spotlight").ready(function(){
	  
	  $('.spotlight .product-gallary').bxSlider({
		  captions: true,
		  slideWidth: 180,
		  maxSlides: 5,
		  slideMargin: 10
		});
	});
</script>