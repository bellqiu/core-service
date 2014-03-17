<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container mainContainer">
	<div class="row">
		<div class="col-xs-4 col-xs-offset-4">
			<a href="http://www.dentalsupplies360.com/help/support"><img src="http://www.dentalsupplies360.com/rs/img1/subtick.jpg" border="0"></a>
		</div>
	</div>
	<div class="row borderline">
		<span>
			<a href="http://www.dentalsupplies360.com/help/support">Support center</a> &gt;&gt; Submit a ticket
		</span>
		<!-- <table width="997" align="center" cellspacing="1" bgcolor="#FFFFFF" class="borderline">
		  <tbody><tr>
		    <th><table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tbody><tr>
		          <td width="56%" id="navleft"><a href="/">Support center</a> &gt;&gt; Submit a ticket</td>
		          <td width="44%" align="right"><a href="index.php?a=my_question&amp;m=contactus">Check my tickets &gt;&gt;</a></td>
		        </tr>
		    </tbody></table></th>
		  </tr>
		</tbody></table> -->
	</div>
	<div class="row borderline">
		<div class="row col-xs-11 col-xs-offset-1">
		<form name="questionForm" class="cmxform" id="commentForm" enctype="multipart/form-data" method="post" action="/help/support">
			<div class="form-group">
				<label for="subject" class="col-xs-2 control-label"><span class="red">*</span>Subject: </label>
				<div class="col-xs-10">
					<input class="form-control" type="text" id="subject" name="subject" required />
               </div>
           </div>
           <div class="form-group">
				<label for="regUsername" class="col-xs-2 control-label">Email</label>
				<div class="col-xs-10">
					<input class="form-control" type="text" id="regUsername" name="regUsername" required>
						<p class="help-block">
                   </p>
               </div>
           </div>
			<!-- <table width="100%" cellspacing="1" bgcolor="#FFFFFF" class="borderline" id="stripe_tb" align="center" style="padding-top:20px;">
			<tbody><tr>
			    	<td class="label" width="22%"> <span class="red">*</span>Question Type: </td><td colspan="2">
			        <select name="cat_id" id="cat_id" class="required">
			    	  <option value="">Please select...</option>    	      	      	  
			                	
			    	      	  <option value="2">Pre-sale Questions or Product/Stock Availability</option>
			    	                  	
			    	      	  <option value="10">Pre-sale Wholesales or Dropship Enquiry</option>
			    	                  	
			    	      	  <option value="1">After-sale Change Address /Cancel Order (unshipped orders only)</option>
			    	                  	
			    	      	  <option value="3">After-sale Order Enquiries /Order Tracking</option>
			    	                  	
			    	      	  <option value="4">After-sale Service (defective items /wrong items received/item missing)</option>
			    	                  	
			    	      	  <option value="6">Payment Issues</option>
			    	                  	
			    	      	  <option value="11">Other Issues(Forget password/Unsubscribe mail/Cooperation)</option>
			    	                  	
			    	      	  <option value="7">Website Feedback(Help us to improve our site and get free points )</option>
			    	                  	
			    	                  	
			    	      	  <option value="12">Making a Complaint</option>
			    	                 
			  	  </select>
			      <br>
			      Please submit a ticket using the correct Question Type. We will reply to you within 24 hours except on weekends and public holidays.
			      </td>
			    </tr>
				<tr id="sp_order_number" style="display:none;">
			    	<td class="label">
			        	<span class="red">*</span> Order Number:
			        </td>
			    	<td colspan="2">
			        	<input type="text" id="order_number" name="order_number" value="" class="">&nbsp;Please enter your order number if you have one.
			            <input type="hidden" name="order_money" id="order_money" value="0">
			        </td>
			    </tr>    
				<tr>
			    	<td width="22%" height="32" class="label"><span class="red">*</span>Subject: </td>
			        <td colspan="2"><input type="text" size="100" maxlength="300" name="subject" class="required" id="subject"></td>
			    </tr>
				<tr style="display:none">
			    	<td class="label" width="22%"><span class="red">*</span>Priority: </td>
			        <td colspan="2">
			        				<input type="radio" name="Priority" id="Priority" checked="" value="0">Normal
									<input type="radio" name="Priority" id="Priority" value="1">emergency
						            <br>Tips:for Normal question we will reply you in 8 hours <br>
			            for Emergency  question we will reply you in 2 hours        </td>
			        <td width="2%">
			        <input type="hidden" name="site" value="everbuying.com">
			    </td></tr>
			    
			    <tr>
			    	<td class="label" width="22%"><span class="red">*</span>Message: </td>
			        <td colspan="2"><textarea name="content" id="content" rows="6" cols="60" class="required"></textarea></td>
			    </tr>
			
				<tr>
			    	<td class="label" width="22%"><span class="red">*</span>Full Name: </td>
			        <td colspan="2"><input type="text" name="nickname" id="nickname" value="" class="required"></td>
			    </tr>
				<tr>
			    	<td class="label" width="22%"><span class="red">*</span>Email: </td>
			      <td colspan="2"><input type="text" name="email" id="email" size="40" value="qiulhong@gmail.com" class="required email"><br>Please make sure your email address is correct so we can locate your account information and solve your problem quickly. We will also reply to your message using this address.</td>
			    </tr>
				<tr>
			    	<td class="label" width="22%" valign="top">Phone Number (optional): </td>
			        <td colspan="2"><input type="text" name="phone_number" id="phone_number" size="40" value=""><br>Please leave your phone number so that we can contact you as soon as possible.        
					</td>
			    </tr>
			    <tr>
			    	<td class="label" width="22%" valign="middle">Attachment 1(optional): </td>
			    	<td width="28%">
			    	  <input type="file" name="attachment1" value="" title="browse">
			    	
			   	  </td>
			      <td rowspan="3" width="48%" valign="top">If you would like to show us some files, please upload them here.<br>
			Each file must be below 2MB. The following file formats are supported:<strong> gif, jpg, png, bmp, doc, xls, txt, rar, ppt, pdf</strong></td>
			    </tr>
				<tr>
			    	<td width="22%" height="44" class="label">Attachment 2(optional): </td>
			   	  <td><input type="file" name="attachment2" value=""></td>
			    </tr>
				<tr>
			    	<td class="label" width="22%">Attachment 3(optional): </td>
			    	<td><input type="file" name="attachment3" value=""></td>
			    </tr>
				<tr>
			    	<td class="label" width="22%" nowrap=""><span class="red">*</span>Enter the code shown:</td>
			    	<td colspan="2"><input name="verifyNo" class="required" type="text" id="verifyNo" size="5">&nbsp;<img src="/fun/?act=verify" id="verify"> <a href="javascript:;" id="flashverify">Try a different code</a></td>
			    </tr>	    
			    <tr>
			    	<td></td>
			        <td colspan="2">
			    	  <input class="sub_but" type="submit" value="  Submit  " id="sub_but" style="float:left;">
			          <div id="widget_submit_tip" style="margin-left:20px; border:solid 1px #999; height:30px; line-height:30px; width:350px; float:left; display:none;"><img src="temp/skin/images/loading.gif" style="vertical-align:central; padding-top:5px;">&nbsp;Data being submitted, please wait...</div>
			    	<br>
			    	</td>
			    </tr>
			    <tr>
			      <td><input type="hidden" name="data_source" id="data_source" value="1"></td>
			      <td colspan="2"><span class="red">*</span> Indicates required field</td>
			    </tr>
			</tbody></table> -->
		</form>
		</div>
	</div>
</div>