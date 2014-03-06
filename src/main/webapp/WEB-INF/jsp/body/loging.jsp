<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container mainContainer">
	<form action="hb_login" id="userAutoLogin" method="post">
			<input type="hidden" name="username" value="${username }">
			<input type="hidden" name="password" value="${password }">
			<c:if test="${not empty targetUrl }">
				<input type="hidden" name="target" value="${targetUrl }">
			</c:if>
	</form>
	
	<script type="text/javascript">
		$("#userAutoLogin").submit();
	</script>
	
</div>