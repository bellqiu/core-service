
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="ad-slider">
	<ul class="main-content-slider">
		<c:forEach items="${bigAddHtmls}" var="html" varStatus="stat">
			<h1>
				<li>
				<c:choose>
				<c:when test="${(stat.index + 1) % 3 == 1 }">
					<div class="sliderContent1">
							<c:out value="${html.content }" escapeXml="false"></c:out>
					</div>
				</c:when>
				<c:when test="${(stat.index + 1) % 3 == 2 }">
					<div class="sliderContent2">
							<c:out value="${html.content }" escapeXml="false"></c:out>
					</div>
				</c:when>
				<c:otherwise>
					<div class="sliderContent3">
							<c:out value="${html.content }" escapeXml="false"></c:out>
					</div>
				</c:otherwise>
				</c:choose>
				</li>
			</h1>
		</c:forEach>
		<!-- <li><div class="sliderContent1"></div></li>
		<li><div class="sliderContent2"></div></li>
		<li><div class="sliderContent3"></div></li>
		<li><div class="sliderContent1"></div></li>
		<li><div class="sliderContent2"></div></li>
		<li><div class="sliderContent3"></div></li> -->
	</ul>
</div>
<script type="text/javascript">
	$(".ad-slider").ready(function() {
		$('.main-content-slider').bxSlider({ 	
			auto : true,
			autoControls : true,
			// 		  slideWidth: 333,
			// 		  minSlides: 2,
			// 		  maxSlides: 3,
			// 		  slideMargin: 5
			mode : 'horizontal',
			useCSS : false,
			infiniteLoop : true,
			easing : 'easeOutElastic',
			speed : 3000
		});
	});
</script>