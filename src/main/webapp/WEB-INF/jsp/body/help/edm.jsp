<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container mainContainer">
	<div class="row">
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-warning">${errorMessage}</div>
		</c:if>
	</div>
	<div class="row borderline">
		<div>
		<form name="edmForm" id="questionForm" enctype="multipart/form-data" method="post" action="/admin/edm" role="form">
			<table width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF" class="noborder" id="comment_tb" align="center" style="padding-top:20px;">
			<tbody>
				<tr>
			    	<td width="22%" height="32" class="support_label"><span class="red">*</span>Subject: </td>
			        <td><input type="text" size="100" maxlength="500" name="subject" required id="subject" style="width:900px" class="form-control"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%"><span class="red">*</span>Message: </td>
			        <td><textarea name="message" id="message" required rows="6" cols="60" style="width:900px" class="form-control"></textarea></td>
			    </tr>
			    <tr>
			      	<td class="support_label" width="22%"><span class="red">*</span>Emails: </td>
			        <td><textarea name="emails" id="emails" required rows="6" cols="60" style="width:900px" class="form-control"></textarea></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top">Email Separator(Default is blank space): </td>
			        <td><input type="text" name="emailSeparator" id="order_number" size="10" style="width:150px"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top"><span class="red">*</span>Email Host: </td>
			        <td><input type="text" name="emailHost" id="emailHost" required size="10" style="width:150px"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top">Email Port(Default: 25): </td>
			        <td><input type="number" name="emailPort" id="emailPort" size="10" style="width:150px"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top"><span class="red">*</span>Email Account: </td>
			        <td><input type="text" name="username" id="username" required size="10" style="width:150px"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top"><span class="red">*</span>Email Password: </td>
			        <td><input type="text" name="password" id="password" required size="10" style="width:150px"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top">From Email(same as login email if none): </td>
			        <td><input type="text" name="emailFrom" id="emailFrom" size="10" style="width:150px"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top"><span class="red">*</span>Interval(milliseconds): </td>
			        <td><input type="number" name="interval" id="interval" required size="10" style="width:150px"></td>
			    </tr>
			    
			    <tr>
			    	<td></td>
			        <td>
			        	<c:choose>
			        		<c:when test="${not empty processingMessage}">
			        			<button type="submit" disabled class="btn btn-warning"> Processing </button>
			        		</c:when>
			        		<c:otherwise>
			        			<button type="submit" class="btn btn-primary"> Submit </button>
			        		</c:otherwise>
			        	</c:choose>
			    	</td>
			    </tr>
			</tbody></table>
		</form>
		</div>
	</div>
	<c:if test="${not empty processingMessage}">
		<div class="row borderline">
			<div>${processingMessage}</div>
		</div>
	</c:if>
	
</div>