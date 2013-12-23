<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="list-group"> 
	 <a href="/ac/profile" id="profile" class="list-group-item active">
	 	Main
	 </a>
	 <a href="/ac/changePwd" id="changePwd" class="list-group-item">Change Password</a>
	 <a href="/ac/address" id="manageAddr" class="list-group-item">Manage Address</a>
	 <script type="text/javascript">
	var page = "${page}";
	$(document).ready(function(){
		$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); } );
		if(!page || page == "profile") {
		} else if(page == "password") {
			$(".active").removeClass('active');
			$("#changePwd").addClass("active");
		} else if(page == "address") {
			$(".active").removeClass('active');
			$("#manageAddr").addClass("active");
		}
	}); 
	</script>
</div>