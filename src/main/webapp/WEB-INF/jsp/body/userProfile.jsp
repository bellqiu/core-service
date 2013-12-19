<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container mainContainer">
	<div class="row">
		<div class="col-xs-4">
			<div class="list-group"> 
	 			<a href="#" id="main" class="list-group-item active">
	    			Main
	  			</a>
	  			<a href="#" id="changePwd" class="list-group-item">Change Password</a>
	  			<a href="#" class="list-group-item">Action1</a>
	  			<a href="#" class="list-group-item">Action2</a>
	  			<a href="#" class="list-group-item">Action3</a>
			</div>
		
		</div>
		
		<div class="col-xs-8">
			<div class="profile" id="mainContent">
				<h1>User Profile Page</h1>
				<h1>User Profile Page1</h1>
			</div>
			<div class="profile" id="changePwdContent" hide="true">
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
			
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
				<h1>User Profile Page2</h1>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".profile").hide();
		$("#mainContent").show(true);
	}); 
	$("#changePwd").click(function() {
		console.log("OK");
		$(document).off('.active');
		$("#changePwd").addClass(".active");
		$(".profile").hide();
		$("#changePwdContent").show(true);
	});
</script>
