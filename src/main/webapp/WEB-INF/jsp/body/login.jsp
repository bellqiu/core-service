<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>Login with Username and Password</h3>
<form name='f' action='/hb_login' method='POST'>
	<table>
		<tr>
			<td>User:</td>
			<td><input type='text' name='username' value='${createdUser.email}'></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='password' value="${createdUser.password}" /></td>
		</tr>
		<tr>
			<td><input type='checkbox' name='_spring_security_remember_me' /></td>
			<td>Remember me on this computer.</td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" id="submitLogin" type="submit" value="Login" /></td>
		</tr>
	</table>
</form>
<c:if test="${not empty createdUser}">

	<script>
		$("#submitLogin").click();
	</script>

</c:if>

<h3>Create user</h3>
<form name='c' action='/ac/newAccount' method='POST'>
	<table>
		<tr>
			<td>User:</td>
			<td><input type='text' name='username' value=''></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='password' /></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit" value="Create" /></td>
		</tr>
	</table>
</form>


