<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<header>
<div class="container-fluid header">
	<div class="row-fluid">
		<tiles:insertAttribute name="dockbar"/>
	</div>
	<div class="row-fluid main-header">
	<tiles:insertAttribute name="mainHeader"/>
	</div>
	
	<div class="row-fluid navigator">
	<tiles:insertAttribute name="navigator"/>
	</div>
</div>
</header>