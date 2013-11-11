<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
	<div class="logoAndBannerAndSearchBox">
		<div class="header-logo">
			<hb:htmltag htmlKey="HEADER_LOGO"/>
		</div>
		<%-- <div class="header-banner">
			<hb:htmltag htmlKey="HEAER_BANNER"/>
		</div> --%>
		<!-- <div class="header-logo">logo</div>
		<div class="header-banner">banner</div> -->
		<div class="header-searchBox">
			<div>
				<input title="Search by keyword" id="searchBox" >
				<input type="image" class="searchBoxCtr" title="Search by keyword" src="/resources/css/img/search.ltr.png">
			</div>
		</div>
		<div style="clear:both"></div>
	</div>