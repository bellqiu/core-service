<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<h1>404</h1>
<button type="button" class="btn btn-default" id="googleLoginButtonId">Google Sign in</button>
<script type="text/javascript">
 (function() {
   var po = document.createElement('script');
   po.type = 'text/javascript'; po.async = true;
   po.src = 'https://apis.google.com/js/client:plusone.js?onload=render';
   var s = document.getElementsByTagName('script')[0];
   s.parentNode.insertBefore(po, s);
 })();

 function render() {

   // Additional params including the callback, the rest of the params will
   // come from the page-level configuration.
   var additionalParams = {
		   'callback': signinCallback,
		     'clientid' : '573965665808.apps.googleusercontent.com',
		     'cookiepolicy' : 'single_host_origin',
		     'scope' : 'https://www.googleapis.com/auth/plus.login',
		     'requestvisibleactions' : 'http://schemas.google.com/AddActivity',
		     'accesstype' : "offline"
   };

   // Attach a click listener to a button to trigger the flow.
   var signinButton = document.getElementById('signinButton');
   signinButton.addEventListener('click', function() {
     gapi.auth.signIn(additionalParams); // Will use page level configuration
   });
   function signinCallback(authResult) {
	   console.log(authResult);
   }
 }
</script>
<button id="signinButton">Sign in with Google</button>
