<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="container bodyContent">
	    <ul id="signUpOrLoginTab" class="nav nav-tabs">
		    <li class="active">
		    	<a href="#login">Sign In</a>
		    </li>
		    <li>
		    	<a href="#signUp">Sign Up</a>
		    </li>
	    </ul>
	    <div id="signUpOrLoginTab" class="row signUpOrLoginBody tab-content">
	    	<div id="login" class="col-sm-12 col-md-12 tab-pane fade active in">
				<form action='/hb_login' method='POST' class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="loginUsername">Email</label>
						<div class="col-lg-10">
							<input class="form-control" name="username" type="email" id="loginUsername" placeholder="Email" required >
							<p class="help-block"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="loginPassword">Password</label>
						<div class="col-lg-10">
							<input class="form-control" type="password" name="password" id="loginPassword" placeholder="Password">
							<p class="help-block"></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-10">
						<div class="checkbox">
							<label>
								<input name='_spring_security_remember_me' type="checkbox"> Keep me signed in
							</label>
						</div>
						</div>
					</div>
					<div class="form-group">
                        <div class="col-lg-offset-2 col-lg-10">
                        	<div class="row">
                        		<div class="col-xs-2">
                          		  <button type="submit" class="btn btn-default">Sign in</button>
                          	  </div>
                          	  	<div class="col-xs-8 ">
                          	  		<!-- <button type="button" class="btn btn-default" id="facebookLoginButtonId">Facebook Sign in</button> --><a href="#" id="facebookLoginWithAnotherLinkId" > Login with Facebook</a>
                          	  		<div id="fb-root"></div>
												<script>
												  window.fbAsyncInit = function() {
													  FB.init({
													    appId      : '277233412302753',
													    status     : true, // check login status
													    cookie     : true, // enable cookies to allow the server to access the session
													    xfbml      : true  // parse XFBML
													  });
													
													  FB.Event.subscribe('auth.authResponseChange', function(response) {
													    if (response.status === 'connected') {
													    }
													  }); 
													 
													  
													  $("#facebookLoginWithAnotherLinkId").click(function(){
														  
														  if(!FB.getAuthResponse()){	  
															  FB.login(function(rs){
																  	var userId = rs.authResponse.userID;
																  	var token = rs.authResponse.accessToken;
																  	login(userId, token);
															  },{scope: 'email,user_about_me,user_status'});
														  }else{
															  FB.logout(function(response) {
																 FB.login(function(rs){
																	 var userId = rs.authResponse.userID;
																	 var token = rs.authResponse.accessToken;
																	 login(userId, token);
																  },{scope: 'email,user_about_me,user_status'}); 
															  });
														  }
													  });
												  };
												  
												  function login(userID, token){
													  console.log("userID:["+userID+"], "+"token:["+token+"]");
													  FB.api("/"+userID+"?fields=email,username,name", function(response) { 
															 console.log(response);
													  });
												  }
												
												  // Load the SDK asynchronously
												  (function(d){
												   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
												   if (d.getElementById(id)) {return;}
												   js = d.createElement('script'); js.id = id; js.async = true;
												   js.src = "//connect.facebook.net/en_US/all.js";
												   ref.parentNode.insertBefore(js, ref);
												  }(document));
												
												 
												</script>
												
												
												
												<!--
												  Below we include the Login Button social plugin. This button uses the JavaScript SDK to
												  present a graphical Login button that triggers the FB.login() function when clicked. -->
												
												<!-- <fb:login-button width="200" max-rows="1"></fb:login-button> -->
                          	  	</div>
                            </div>
                        </div>
                    </div>
				</form>
			</div>
	    	<div id="signUp" class="col-sm-12 col-md-12 tab-pane fade">
	    	<legend>To obtain a HoneyStore account</legend>
	    	<form action='/ac/newAccount' method='POST' class="form-horizontal">
                   <div class="form-group">
                       <label for="regUsername" class="col-lg-2 control-label">Email</label>
                       <div class="col-lg-10">
                           <input class="form-control" type="email" id="regUsername" name="regUsername" required>
                           <p class="help-block"></p>
                       </div>
                   </div>
                   <div class="form-group">
                       <label for="regPassword" class="col-lg-2 control-label">Password</label>
                       <div class="col-lg-10">
                           <input class="form-control" type="password" name="regPassword" id="regPassword" minlength="8" required>
                           <p class="help-block">Password should not less than 8 characters.</p>
                       </div>
                   </div>
                   <div class="form-group">
                       <label for="passwordAgain" class="col-lg-2 control-label">re-Password</label>
                       <div class="col-lg-10">
                           <input class="form-control" type="password" name="passwordAgain" id="passwordAgain" minlength="8" required>
                           <p class="help-block">Input your password again</p>
                       </div>
                   </div>
                   <div class="form-group">
                       <label for="terms-and-conditions" class="col-lg-2 control-label">Legal</label>
                       <div class="col-lg-10">
                           <label class="checkbox">
                               <input type="checkbox" data-validation-required-message="You must agree to the terms and conditions" required="" name="terms-and-conditions" id="terms-and-conditions">
                               I agree to the <a href="#">terms and conditions</a>
                           </label>
                           <p class="help-block"></p>
                       </div>
                   </div>
                   <div class="form-group">
                       <div class="col-lg-offset-2 col-lg-10">
                           <button type="submit" class="btn btn-default">Sign up</button>
                       </div>
                   </div>
               </form>
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