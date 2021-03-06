<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<script type="text/javascript">
var isSignUpPage = ${not empty isSignUpPage? true : false};
var domain = '${site.domain}';
</script>
<div class="container mainContainer">
	    <ul id="signUpOrLoginTab" class="nav nav-tabs">
		    <li class="active">
		    	<a href="#loginTab">Sign In</a>
		    </li>
		    <li>
		    	<a href="#signUpTab">Sign Up</a>
		    </li>
	    </ul>
	    <div id="signUpOrLoginTabCotent" class="row signUpOrLoginBody tab-content">
	    	<div id="loginTab" class="col-sm-12 col-md-12 tab-pane fade active in">
				<form action='/hb_login' method='POST' class="form-horizontal" role="form">
					<div class="row">
						<div class="col-sm-2"></div>
						<!-- <label class="col-sm-2"></label> -->
						<div class="col-sm-5">
							
							<c:if test="${not empty param.failed and param.failed}">
                           		<p  class='alert alert-danger fade in'>
                           			<strong>The email address or password you entered is incorrect.</strong>
                           		</p>
							</c:if>
						</div>
						<div class="col-sm-5">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="loginUsername">Email</label>
						<div class="col-sm-5">
							<input class="form-control" name="username"  id="loginUsername" placeholder="Email" required >
							<p class="help-block"></p>
						</div>
						<div class="col-sm-5">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="loginPassword">Password</label>
						<div class="col-sm-5">
							<input class="form-control" type="password" name="password" id="loginPassword" size="40" placeholder="Password">
							<p class="help-block"></p>
						</div>
						<div class="col-sm-5">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							<label>
								<input name='_spring_security_remember_me' type="checkbox"> Keep me signed in
							</label>
						</div>
						<div>
							<a href="" id="forgot_password" data-toggle="modal" data-target="#forgotPaswordModal">Forgot Password</a>
						</div>
						</div>
					</div>
					<div class="form-group">
                        <div class="col-lg-offset-2 col-lg-10">
                        	<div class="row">
                        		<div class="col-xs-2">
                          		  <button type="submit" id="sign-in" class="btn btn-primary">Sign in</button>
                          		</div>
                            </div>
                        </div>
                        <div class="col-lg-offset-2 col-lg-10">
						    
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <div class="col-lg-offset-2 col-lg-10">
                        	<div class="row">
                        		<script src="/resources/js/ThreePartyLogin.js" type="text/javascript"></script>
                          	  	<div class="col-xs-2">
                          	  		<!-- <button type="button" class="btn btn-default" id="facebookLoginButtonId">Facebook Sign in</button> -->
                          	  		<a href="javascript:void(0)" id="facebookLoginWithAnotherLinkId" class="login-lnk btn-facebook" title="Log in using your Facebook account">Facebook</a>
                          	  		<div id="fb-root"></div>
									<!-- <script src="/resources/js/FacebookLogin.js" type="text/javascript"></script> -->
												<!--
												  Below we include the Login Button social plugin. This button uses the JavaScript SDK to
												  present a graphical Login button that triggers the FB.login() function when clicked. -->
												
												<!-- <fb:login-button width="200" max-rows="1"></fb:login-button> -->
                          	  	</div>
                          	  	<div class="col-xs-2">
						    		<!-- <script src="/resources/js/GoogleLogin.js" type="text/javascript"></script> -->
						    		<a href="javascript:void(0)" id="googleLoginId" class="login-lnk btn-google" title="Log in using your Google account">Google</a>
                          	  	</div>
                          	  	<%-- <div class="col-xs-2">
                          	  		<a href="${site.domain}/ac/login?type=twitter" id="twitterLogin" >Login with Twitter</a>
                          	  	</div> --%>
                          	  	<div class="col-xs-2">
                          	  		<script type="text/javascript" src="http://platform.linkedin.com/in.js">
									  api_key:7530oj8n5n1fan	
									  onLoad:onLinkedInLoad
									  authorize:false
									  lang:en_US
									</script>
                          	  		<!-- <script src="/resources/js/LinkedInLogin.js" type="text/javascript"></script> -->
                          	  		<a href="javascript:void(0)" id="linkedInLogin" class="login-lnk btn-linkedin" title="Log in using your LinkedIn account">LinkedIn</a>
                          	  	</div>
                            </div>
                        </div>
                    </div>
				</form>
			</div>
			
			<div class="modal fade" id="forgotPaswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  				<div class="modal-dialog">
    				<div class="modal-content">
      					<div class="modal-header">
        					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        					<h4 class="modal-title" id="myModalLabel">Forgot Password</h4>
      					</div>
      					<div class="modal-body" id="fogot-modal-body">
      						<form id="forgotpasswordform" role="form">
  								<div class="form-group">
    								<label for="forgotEmail">Enter your email or login account:</label>
    								<input type="email" class="form-control" id="forgotEmail" placeholder="Enter email">
    								<p class="help-block" id="forgotblock"></p>
    								<div id="callbackMessage"></div>
  								</div>
  							</form>
      					</div>
     					<div class="modal-footer">
     						<div class="form-group">
        						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        						<button type="button" id="sendEmail" data-loading-text="Processing.." class="btn btn-danger">Send</button>
        					</div>
      					</div>
    				</div><!-- /.modal-content -->
  				</div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
						
	    	<div id="signUpTab" class="col-sm-12 col-md-12 tab-pane fade">
	    	<legend>To obtain an account</legend>
	    	<form action='/ac/newAccount' method='POST' class="form-horizontal">
                   <div class="form-group">
                       <label for="regUsername" class="col-sm-2 control-label">Email</label>
                       <div class="col-sm-5">
                           <input class="form-control" type="email" id="regUsername" name="regUsername" required />
                           <p class="help-block">
                           <c:choose>
                           	<c:when test="${isSignUpFail}">
                           		<p class='alert alert-danger fade in'>
                           			<strong>Email has already been registered</strong>
                           		</p>
                           	</c:when>
                           </c:choose>
                           </p>
                       </div>
                       <div class="col-sm-5"></div>
                   </div>
                   <div class="form-group">
                       <label for="regPassword" class="col-sm-2 control-label">Password</label>
                       <div class="col-sm-5">
                           <input class="form-control" type="password" name="regPassword" id="regPassword" minlength="8" required />
                           <p class="help-block"></p>
                       </div>
                       <div class="col-sm-5"></div>
                   </div>
                   <div class="form-group">
                       <label for="passwordAgain" class="col-sm-2 control-label">re-Password</label>
                       <div class="col-sm-5">
                           <input class="form-control" type="password" name="passwordAgain" id="passwordAgain" data-validation-matches-match="regPassword" required />
                           <p class="help-block" id="pwdAgainBlock"></p>
                       </div>
                       <div class="col-sm-5"></div>
                   </div>
                   <div class="form-group">
                       <label for="terms-and-conditions" class="col-lg-2 control-label">Legal</label>
                       <div class="col-lg-10">
                           <label class="checkbox">
                               <input type="checkbox" data-validation-required-message="You must agree to the terms and conditions" required="" name="terms-and-conditions" id="terms-and-conditions" />
                               I agree to the <a href="#">terms and conditions</a>
                           </label>
                           <p class="help-block"></p>
                       </div>
                   </div>
                   <div class="form-group">
                       <div class="col-lg-offset-2 col-lg-10">
                           <button type="submit" class="btn btn-default" id="sign-up">Sign up</button>
                       </div>
                   </div>
               </form>
	    	</div>
	    </div>
</div>
<script src="/resources/js/ForgotPasswordPageMain.js" type="text/javascript"></script>
<script src="/resources/js/LoginMain.js" type="text/javascript"></script>