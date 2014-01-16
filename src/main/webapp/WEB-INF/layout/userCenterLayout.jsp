<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="container mainContainer">
	<div class="row">
		<div>
			<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<li>
					${(empty page || page=='order') ? 'My Order' : (page=='password') ? 'Change Password' : (page=='address') ? 'Manage Address' : 'My Order'}
				</li>
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3">
			<tiles:insertAttribute name="leftNav" />
		</div>
		<div class="col-xs-9">
			<tiles:insertAttribute name="userContent" />
		</div>
		
	</div>
</div>


