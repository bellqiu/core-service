<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="container mainContainer">
<div class="row">
	<div class="col-xs-3">
		<tiles:insertAttribute name="leftNav" />
	</div>
	<div class="col-xs-9">
		<tiles:insertAttribute name="userContent" />
	</div>
	
</div>
</div>

