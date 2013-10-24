<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
	<div class="row navigator">
		<div class="top-level-navigator">
			<ul class="top-level-navigator-list">
				<c:forEach items="${catlogs}" var="item" varStatus="stat">
					<c:choose>
					<c:when test="${item.type == 'NAVIGATION' || item.type == 'SPECIAL_OFFER' }">
						<li class="top-level-navigator-item">
							<a aimTo="category-content${stat.index}" href="#" class="top-level-navigator-link">${item.displayName }</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="top-level-navigator-item">
							<a aimTo="category-content${stat.index}" href="${item.url }" class="top-level-navigator-link">${item.displayName }</a>
						</li>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="menu-line"></div>
	<div class="menu-content">
		<c:forEach items="${catlogs}" var="item" varStatus="stat">
			<c:choose>
			<c:when test="${item.type == 'NAVIGATION' || item.type == 'SPECIAL_OFFER' }">
				<div id="category-content${stat.index}" class="category-content">
					<!-- TODO add specialOffer tag -->
					<hb:catlog parentId="${item.id }" level="2"/>
				</div>
			</c:when>
			</c:choose>
		</c:forEach>
		<div style="clear:both"></div>
	</div>
	
	<script type="text/javascript">
		$(".top-level-navigator-item a").click(function(){
			$(".top-level-navigator-item a").removeClass("light-gray-background");
			//$(".category-content").slideToggle("slow");
			var link = $(this);
			link.addClass("light-gray-background");
			var contentId = $(this).attr("aimTo");
			var content = $("#"+contentId);
			content.slideToggle("slow");
			link.mouseout(function(){
				$(window).click(function(e){
					content.slideUp("slow");
					link.removeClass("light-gray-background");
					$(this).unbind(e);
				});
			});
			content.mouseout(function(){
				$(window).click(function(e){
					content.slideUp("slow");
					link.removeClass("light-gray-background");
					$(this).unbind(e);
				});
			});
		});
	</script>