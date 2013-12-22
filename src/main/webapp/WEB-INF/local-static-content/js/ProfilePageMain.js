(function($){
	
	requirejs(
		["/resources/js/user/ChangePassword.js"], function(changepassword){
			
			var changepasswordCallBack =  function(change){
				if(change.status == "true") {
					$("#passwordBlock")[0].innerHTML="<ul role='alert' class='alert alert-success fade in'><li><strong>" + change.message + "</strong></li></ui>";
				} else {
					$("#oldPasswordBlock")[0].innerHTML="<ul role='alert' class='alert alert-danger fade in'><li><strong>" + change.message + "</strong></li></ui>";
				}
				$("#changePassword").button('reset');
				$("#changePwdForm")[0].reset();
			};
			
			$("#changePassword").click(function() {
				var valid = this.form.checkValidity();
				if(valid) {
					var oldPassword = $("#oldPassword").val();
					var newPassword = $("#newPassword").val();
					$(this).button('loading');
					changepassword.change(oldPassword, newPassword, changepasswordCallBack);
				}
				return false;
			});
		}
	
	);
	
	
	requirejs(
			["/resources/js/user/Address.js"], function(address){
				
				var addressListCallBack =  function(change){
				};
				
				$("#manageAddr").click(function() {
				});
			}
		
		);
	
	
})(jQuery);