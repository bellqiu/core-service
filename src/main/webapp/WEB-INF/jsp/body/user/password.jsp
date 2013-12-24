<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<script src="/resources/js/ChangePwdPageMain.js" type="text/javascript"></script>
		</div>
</div>
