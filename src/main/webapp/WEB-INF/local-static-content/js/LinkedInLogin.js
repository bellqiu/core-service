(function($){
	window.fbAsyncInit = function() {
		/*IN.init({
			onLoad:"onLinkedInLoad",
			api_key:"7530oj8n5n1fan",
			authorize: false
		});*/
		
		
		IN.Event.on(IN, "auth", onLinkedInAuth);
		onLinkedInLoad = function onLinkedInLoad() {
			
		};
		$("#linkedInLogin").click(function(){
			if(!IN || !IN.User) {
				return false;
			}
			if(IN.User.isAuthorized()) {
				IN.User.logout(function(){
					IN.UI.Authorize().place();
				});
			} else {
				IN.UI.Authorize().params({immediate: false,authorize:false}).place();
			}
		});
		
		function onLinkedInAuth() {
			window.location.href=window.location.protocol+"//"+window.location.host + "/ac/login?type=linkedIn&token=" + IN.ENV.auth.oauth_token;
		};
		
	};
	
	(function(d){
		   var js, id = 'linkedin-jssdk', ref = d.getElementsByTagName('script')[0];
		   if (d.getElementById(id)) {return;}
		   js = d.createElement('script'); js.id = id; js.async = true;
		   js.src = "//platform.linkedin.com/in.js";
		   ref.parentNode.insertBefore(js, ref);
	}(document));
	
})(jQuery);