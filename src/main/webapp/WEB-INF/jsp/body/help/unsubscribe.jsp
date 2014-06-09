<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container mainContainer">
<style>
#mailMain{margin-left:40px;}
#mailMain td{padding-top:20px;font:12px Arial;}
.button{cursor:pointer;border:none;width:71px;height:22px;background:url(/rs/img1/continue.jpg) 0 0 no-repeat;}
.input{border:1px solid #b0b0b0;}
#mailNew td{padding:0;}
</style>
<div style="border:1px solid #b0b0b0;width:670px;overflow:hidden;margin:12px auto 0 auto;">

<table width="670" cellpadding="0" cellspacing="0" border="0" id="mailMain" class="noborder">
<tbody>
<c:if test="${invalidEmail}">
	<tr><td style="padding-top:40px">The email address you entered is not valid. Please enter a valid email address and try again. </td></tr>
</c:if>
<c:if test="${noEmail}">
	<tr>
		<td height="30">Unsubscribe from the Dentalsupplies360</td>
	</tr>	
	<form ACTION="/help/unsubscribe" METHOD="POST">
	<tr>
		<td>Please provide you email and click continue: </td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" border="0" id="mailNew">
			<tr>
			<td>E-mail to be unsubscribed:&nbsp; </td><td><input type="text" name="email" maxlength="100" class="input"></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td><input name="submit" value="" id="" class="button" type="submit"></td>
	</tr>
	</form>
</c:if>
<c:if test="${requestSuccess}">
	<tr>
		<td>Your request has been processed.<br><br> Please <a href="${site.domain}">click here</a> to continue shopping.</td>
	</tr>
</c:if>
<c:if test="${!invalidEmail && !requestSuccess && !noEmail}">
	<tr>
		<td height="30">Unsubscribe from the Dentalsupplies360 Newsletter or Change Your E-mail Address</td>
	</tr>

	<tr>
		<td>Your current E-mail address: <strong>${email}</strong></td>
	</tr>
	<form ACTION="/help/changeSubscribe" METHOD="post">
	<input type='hidden' name='email' value='${email}'>
	<tr>
		<td>Change your E-mail address:</td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" border="0" id="mailNew">
			<tr>
			<td>My new E-mail address is:&nbsp; </td><td><input type="text" name="newEmail" maxlength="100" class="input"></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td><input name="submit" value="" class="button" style="" type="submit"></td>
	</tr>
	</form>
	<form ACTION="/help/unsubscribe" METHOD="POST">
	<input type='hidden' name='email' value='${email}'> 
	<tr>
		<td>If you would like to unsubscribe from the Dentalsupplies360 newsletter, please click below:	</td>
	</tr>
	<tr>
		<td><input type="radio" required name="a" value=11>I would like to unsubscribe.</td>
	</tr>

	<tr>
		<td><input name="submit" value="" id="" class="button" type="submit"></td>
	</tr>
	</form>
</c:if>
</tbody>
</table>
</div>
</div>