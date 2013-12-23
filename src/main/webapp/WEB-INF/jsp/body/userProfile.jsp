<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container mainContainer">
	<div class="row">
		<div class="col-xs-3">
			<div class="list-group"> 
	 			<a href="#" id="profile" class="list-group-item active">
	    			Main
	  			</a>
	  			<a href="#" id="changePwd" class="list-group-item">Change Password</a>
	  			<a href="#" id="manageAddr" class="list-group-item">Manage Address</a>
			</div>
		
		</div>
		
		<div class="col-xs-9">
			<div class="profile" id="profileContent">
				<h1>Your email is xxx@xx.com</h1>
				<h1>User Profile Page1</h1>
			</div>
			<div class="profile" id="changePwdContent">
				<div>
				<legend>Change your password</legend>
				</div>
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
				<div>
				<legend>Edit your addresses</legend>
				</div>
				<div id="addressContainer" class="row panel-group">
				
				<!-- <div class="col-lg-12 panel panel-default">
					<div class="row form-group">
						<label class="col-lg-2">First Name: </label>
					 	<div class="col-lg-10">
						First Name......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Last Name: </label>
					 	<div class="col-lg-10">
						Last Name......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Address1: </label>
					 	<div class="col-lg-10">
						Address1......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Address2: </label>
					 	<div class="col-lg-10">
						Address2......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Postal Code: </label>
					 	<div class="col-lg-10">
						postal_code......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Phone: </label>
					 	<div class="col-lg-10">
						phone......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">City: </label>
					 	<div class="col-lg-10">
						City......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">State: </label>
					 	<div class="col-lg-10">
						state province......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Country Code: </label>
					 	<div class="col-lg-10">
						country_code......
						</div>
					</div>
					<div class="row">
						<div class="col-lg-offset-2 col-lg-10">
						<button type="button" class="btn btn-danger" onclick="getAddress(1)">Edit Address</button>
						</div>
					</div>
				</div>
				
				<div class="col-sm-6 panel panel-default">
					<div class="row form-group">
						<label class="col-sm-2">First Name: </label>
					 	<div class="col-sm-10">
						First Name......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-sm-2">Last Name: </label>
					 	<div class="col-sm-10">
						Last Name......
						</div>
					</div>
				</div> -->
				
				
				</div>
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
		} else if(tab == "changePwd") {
			$(".active").removeClass('active');
			$("#changePwd").addClass("active");
			$("#changePwdContent").show(true);
		} else if(tab == "manageAddr") {
			$(".active").removeClass('active');
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
		$("#addressContainer").html("").load("/ac/address/list");
	});
	
	function getAddress(id) {
		console.log(id);
	}
	
	
</script>
<script src="/resources/js/ProfilePageMain.js" type="text/javascript"></script>