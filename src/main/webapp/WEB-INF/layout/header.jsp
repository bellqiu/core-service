<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<header>
<div class="row">
		<tiles:insertAttribute name="dockbar"/>
</div>
<div class="row header">
	<div class="row main-header">
	<tiles:insertAttribute name="mainHeader"/>
	</div>
	
	<tiles:insertAttribute name="navigator"/>

</div>
</header>