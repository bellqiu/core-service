<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld" %>
<div class="spotlight">
<h1 class="heading--large">Spotlight</h1>

	<hb:tabProduct tabKey="SPOTLIGHT_PRODUCT"/>

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