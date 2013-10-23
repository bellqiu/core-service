<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="container-fluid body">
	<div class="bodyContent">
	    <ul id="signUpOrLoginTab" class="nav nav-tabs">
		    <li class="active">
		    	<a href="#login">Sign In</a>
		    </li>
		    <li>
		    	<a href="#signUp">Sign Up</a>
		    </li>
	    </ul>
	    <div id="signUpOrLoginTab" class="signUpOrLoginBody tab-content">
	    	<div id="login" class="tab-pane fade active in">
				<form class="form-horizontal">
					<legend>HoneyStore account</legend>
					<div class="control-group">
						<label class="control-label" for="loginUsername">Email</label>
						<div class="controls">
							<input type="email" id="loginUsername" placeholder="Email" required >
							<p class="help-block"></p>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="loginPassword">Password</label>
						<div class="controls">
							<input type="password" id="loginPassword" placeholder="Password" required >
							<p class="help-block"></p>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<label class="checkbox">
								<input type="checkbox"> Keep me signed in
							</label>
						</div>
					</div>
					<div class="control-group">
                        <div class="controls">
                            <button type="submit" class="btn">Sign in</button>
                        </div>
                    </div>
				</form>
			</div>
	    	<div id="signUp" class="tab-pane fade">
	    	<legend>To obtain a HoneyStore account</legend>
	    	<form novalidate="" class="form-horizontal">
                        <div class="control-group">
                            <label for="regUsername" class="control-label">Email</label>
                            <div class="controls">
                                <input type="email" id="regUsername" name="regUsername" required>
                                <p class="help-block">Email address we can contact you on</p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="regPassword" class="control-label">Password</label>
                            <div class="controls">
                                <input type="password" name="regPassword" id="regPassword" minlength="8" required>
                                <p class="help-block">Password should not less than 8 characters.</p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="passwordAgain" class="control-label">re-Password</label>
                            <div class="controls">
                                <input type="password" name="passwordAgain" id="passwordAgain" minlength="8" required>
                                <p class="help-block">Input your password again</p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="terms-and-conditions" class="control-label">Legal</label>
                            <div class="controls">
                                <label class="checkbox">
                                    <input type="checkbox" data-validation-required-message="You must agree to the terms and conditions" required="" name="terms-and-conditions" id="terms-and-conditions">
                                    I agree to the <a href="#">terms and conditions</a>
                                </label>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <button type="submit" class="btn">Sign up</button>
                            </div>
                        </div>
                    </form>
	    	</div>
	    </div>
	</div>
</div>
<script type="text/javascript">
$(".bodyContent").ready(function(){
	$('#signUpOrLoginTab a').click(function (e) {
	    e.preventDefault();
	    $(this).tab('show');
	});
	$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
});


</script>