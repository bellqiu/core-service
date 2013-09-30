<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="container-fluid body">
	<tiles:insertAttribute name="adSlider"/>
	
	<div class="bodyContent">
		<tiles:insertAttribute name="spotlight"/>
	
		<tiles:insertAttribute name="bestSelling"/>
	
		<tiles:insertAttribute name="featuredProducts"/>
	</div>
</div>

