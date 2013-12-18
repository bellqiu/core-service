(function($){
	
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
				 console.log(domain);
				 // TODO redirect to local login with facebook id and tooken
				 //window.location.href=domain + "/ac/login?type=facebook&userId=" + userID + "&token=" + token;  
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

})(jQuery);