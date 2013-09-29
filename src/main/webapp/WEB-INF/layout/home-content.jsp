<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="container-fluid body">
	<div class="row-fluid">
		<tiles:insertAttribute name="adSlider"/>
	</div>
	
	<div class="row-fluid">
	<div class="span10 offset1">
		<tiles:insertAttribute name="spotlight"/>
	</div>
	</div>
	
	<div class="row-fluid">
	<div class="span10 offset1">
		<tiles:insertAttribute name="bestSelling"/>
	</div>
	</div>
	
	<div class="row-fluid">
	<div class="span10 offset1">
		<tiles:insertAttribute name="featuredProducts"/>
	</div>
	</div>
	
</div>

