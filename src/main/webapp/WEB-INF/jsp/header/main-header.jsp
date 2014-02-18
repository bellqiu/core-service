<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
	<div class="logoAndBannerAndSearchBox">
		<div class="header-logo">
			<hb:htmltag htmlKey="HEADER_LOGO"/>
		</div>
		<div class="header-banner">
			<hb:htmltag htmlKey="HEAER_BANNER"/>
		</div> 
		<!-- <div class="header-logo">logo</div>
		<div class="header-banner">banner</div> -->
		<div class="header-searchBox">
		<form id="search-form" role="form" action="${site.domain}/search">
			<div>
				<input title="Search by keyword" id="searchBox" name="keyword" >
				<span id="search-glyphicon" class="glyphicon glyphicon-search"></span>
			</div>
		</form>
		</div>
		<div style="clear:both"></div>
	</div>

<script>
 $("#search-glyphicon").click(
		 function(bt) {
			 $("#search-form").submit();
		 })
</script>