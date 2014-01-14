<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<h1>404</h1>

<script type="text/javascript" src="http://platform.linkedin.com/in.js">
  api_key: 7530oj8n5n1fan
  onLoad: onLinkedInLoad
  authorize: false
</script>
<script type="text/javascript">
function onLinkedInLoad() {
	console.log("Load");
    IN.Event.on(IN, "auth", onLinkedInAuth);
	$("#linkedInButton").click(function(){
		console.log("Click");
		IN.UI.Authorize().place();
	});
}
function onLinkedInAuth() {
	console.log("Auth");
    IN.API.Profile("me").fields(["id", "firstName", "lastName", "email-address"]).result(displayProfiles)
}
function displayProfiles(profiles) {
	console.log(profiles);
	console.log(profiles.values[0].emailAddress);
	console.log(IN.ENV.auth.oauth_token);
}
</script>
<body>
<script type="IN/Login"></script>
<div>
	<button id="linkedInButton">Sign In LinkedIn</button>
</div>
</body>