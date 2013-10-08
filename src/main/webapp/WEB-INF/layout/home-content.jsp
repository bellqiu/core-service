<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
<div class="container-fluid body">
	<tiles:insertAttribute name="adSlider"/>
	
	<div class="bodyContent">
		<tiles:insertAttribute name="spotlight"/>
	
		<tiles:insertAttribute name="bestSelling"/>
	
		<tiles:insertAttribute name="featuredProducts"/>
	</div>
	<hb:bigAdd settingKey="test"/>
</div>

