<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
		
	<!-- TODO add specialOffer tag -->
			<div class="specialOffer">
				<div class="special-item">
				<a href="#">
					<img alt="" src="http://dri2.img.digitalrivercontent.net/Storefront/Site/msusa/images/promo/en-US-Global_NAV_Surface.png">
					<p>category name</p>
				</a>
				</div>
				<div class="special-item">
				<a href="#">
					<img alt="" src="http://dri2.img.digitalrivercontent.net/Storefront/Site/msusa/images/promo/en-US-Global_NAV_Surface.png">
					<p>category name</p>
				</a>
				</div>
				<div class="special-item">
				<a href="#">
					<img alt="" src="http://dri2.img.digitalrivercontent.net/Storefront/Site/msusa/images/promo/en-US-Global_NAV_Surface.png">
					<p>category name</p>
				</a>
				</div>
				<div class="special-item">
				<a href="#">
					<img alt="" src="http://dri2.img.digitalrivercontent.net/Storefront/Site/msusa/images/promo/en-US-Global_NAV_Surface.png">
					<p>category name</p>
				</a>
				</div>
				<div class="special-item">
				<a href="#">
					<img alt="" src="http://dri2.img.digitalrivercontent.net/Storefront/Site/msusa/images/promo/en-US-Global_NAV_Surface.png">
					<p>category name</p>
				</a>
				</div>
				<div style="clear:both"></div>
			</div>
		<div class="subCategory row">
			<c:forEach items="${catlogs}" var="item" varStatus="stat">
				<div class="subCategory-list col-xs-3">
					<h2 class="l2Category"><a href="/c/${item.name }">${item.displayName }</a></h2>
					<hb:catlog parentId="${item.id }" level="3"/>
				</div>
			</c:forEach>
		</div>
