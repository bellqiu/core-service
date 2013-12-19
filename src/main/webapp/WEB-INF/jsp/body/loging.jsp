<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="container mainContainer">
	<form action="hb_login" id="userAutoLogin" method="post">
			<input type="hidden" name="username" value="${username }">
			<input type="hidden" name="password" value="${password }">
	</form>
	
	<script type="text/javascript">
		$("#userAutoLogin").submit();
	</script>
	
</div>