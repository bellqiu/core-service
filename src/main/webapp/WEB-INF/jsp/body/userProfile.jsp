<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container mainContainer">
	<div class="row">
		<div class="col-xs-4">
			<div class="list-group"> 
	 			<a href="#" id="profile" class="list-group-item active">
	    			Main
	  			</a>
	  			<a href="#" id="changePwd" class="list-group-item">Change Password</a>
	  			<a href="#" id="manageAddr" class="list-group-item">Manage Address</a>
	  			<a href="#" class="list-group-item">Action1</a>
	  			<a href="#" class="list-group-item">Action2</a>
			</div>
		
		</div>
		
		<div class="col-xs-8">
			<div class="profile" id="profileContent">
				<h1>Your email is xxx@xx.com</h1>
				<h1>User Profile Page1</h1>
			</div>
			<div class="profile" id="changePwdContent">
				<h1>Change Your password</h1>
				<div>
				<form id="changePwdForm" class="form-horizontal">
					<div class="form-group">
						<label class="col-lg-3 control-label" for="oldPassword">Old Password</label>
						<div class="col-lg-9">
							<input class="form-control" name="oldPassword" type="password" id="oldPassword" required >
							<p id="oldPasswordBlock" class="help-block">
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label" for="newPassword">New Password</label>
						<div class="col-lg-9">
							<input class="form-control" type="password" name="newPassword" id="newPassword" minlength="8" required>
							<p class="help-block"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label" for="newPasswordAgain">New Password Again</label>
						<div class="col-lg-9">
							<input class="form-control" type="password" name="newPasswordAgain" id="newPasswordAgain" data-validation-matches-match="newPassword" required>
							<p id="passwordBlock" class="help-block"></p>
						</div>
					</div>
					<div class="form-group">
                        <div class="col-lg-offset-3 col-lg-9">
                        	<div class="row">
                        		<div class="col-xs-2">
                          		  <button type="button" id="changePassword" data-loading-text="Processing.." class="btn btn-danger">Change</button>
                          	  </div>
                            </div>
                        </div>
                    </div>
				</form>
			</div>
			
			</div>
			<div class="profile" id="manageAddrContent">
				<h1>Change Your Addresses</h1>
				<h1>User Profile Page</h1>
				<h1>User Profile Page</h1>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var tab = "${param.page}";
	$(document).ready(function(){
		$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); } );
		$(".profile").hide();
		if(!tab || tab == "profile") {
			$("#profileContent").show(true);
		} else if(tab == "changPwd") {
			$(".active").removeClass('active');
			$("#changePwd").addClass("active");
			$("#changePwdContent").show(true);
		} else if(tab == "manageAddr") {
			(".active").removeClass('active');
			$("#manageAddr").addClass("active");
			$("#manageAddrContent").show(true);
		}
	}); 
	$("#profile").click(function() {
		$(".active").removeClass('active');
		$("#profile").addClass("active");
		$(".profile").hide();
		$("#profileContent").show(true);
	});
	$("#changePwd").click(function() {
		$(".active").removeClass('active');
		$("#changePwd").addClass("active");
		$(".profile").hide();
		$("#changePwdContent").show(true);
	});
	$("#manageAddr").click(function() {
		$(".active").removeClass('active');
		$("#manageAddr").addClass("active");
		$(".profile").hide();
		$("#manageAddrContent").show(true);
	});
	
</script>
<script src="/resources/js/ProfilePageMain.js" type="text/javascript"></script>