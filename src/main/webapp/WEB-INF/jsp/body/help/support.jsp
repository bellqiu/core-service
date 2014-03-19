<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container mainContainer">
	<div class="row">
		<div class="col-xs-4 col-xs-offset-4">
			<a href="${site.domain}/help/support"><img src="${site.resourceServer}/rs/img1/subtick.jpg" border="0"></a>
		</div>
	</div>
	<div class="row borderline">
		<span>
			<a href="${site.domain}/help/support">Support center</a> &gt;&gt; Submit a ticket
		</span>
	</div>
	<div class="row borderline">
		<div>
		<form name="questionForm" id="questionForm" enctype="multipart/form-data" method="post" action="/help/support" role="form">
			<table width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF" class="noborder" id="comment_tb" align="center" style="padding-top:20px;">
			<tbody>
				<tr>
			    	<td width="22%" height="32" class="support_label"><span class="red">*</span>Subject: </td>
			        <td><input type="text" size="100" maxlength="500" name="subject" required id="subject" style="width:700px" class="form-control"></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%"><span class="red">*</span>Message: </td>
			        <td><textarea name="message" id="message" rows="6" cols="60" style="width:700px" class="form-control"></textarea></td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%"><span class="red">*</span>Email: </td>
			      	<td><input type="email" name="email" id="email" size="40" style="width:350px" class="form-control">
			      		<br>Please make sure your email address is correct so we can locate your account information and solve your problem quickly. We will also reply to your message using this address.</td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top">Order Number (optional): </td>
			        <td><input type="text" name="order_number" id="order_number" size="40" style="width:350px">
			        	<br>Please leave your order number so that we can contact you as soon as possible.  
					</td>
			    </tr>
			    <tr>
			    	<td class="support_label" width="22%" valign="top">Phone Number (optional): </td>
			        <td><input type="text" name="phone_number" id="phone_number" size="40" style="width:350px"><br>Please leave your phone number so that we can contact you as soon as possible.        
					</td>
			    </tr>
			    
			    <tr>
			    	<td></td>
			        <td>
			        	<button type="submit" class="btn btn-primary"> Submit </button>
			    	</td>
			    </tr>
			</tbody></table>
		</form>
		</div>
	</div>
</div>