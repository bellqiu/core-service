<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Knowledgebase</h3>
	</div>
	<div class="panel-body">
		<div class="panel-group" id="helpLeftNav">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="helpTopLink" data-toggle="collapse" data-parent="#helpLeftNav" href="#companyInfomation">
							<span class="glyphicon glyphicon-chevron-down"></span>
							Company Information
						</a>
					</h4>
				</div>
				<div id="companyInfomation" class="helpSub panel-collapse collapse in">
					<div class="panel-body">
						<ul class="helpItem">	
							<li><a href="/help/about-us">About Us</a></li>
							<li><a href="/help/contact-us">Contact Us</a></li>
							<li><a href="/help/customer-service">Customer Service</a></li>
							<li><a href="/help/privacy-policies">Privacy Policies</a></li>
							<li><a href="/help/legal-window">Legal Window</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="helpTopLink" data-toggle="collapse" data-parent="#helpLeftNav" href="#customerService">
							<span class="glyphicon glyphicon-chevron-right"></span>
							Customer Service
						</a>
					</h4>
				</div>
				<div id="customerService" class="helpSub panel-collapse collapse">
					<div class="panel-body">
						<ul class="helpItem">
							<li><a href="/help/warranty-return">Warranty and Return</a></li>
							<li><a href="/help/payment-methods">Payment Methods</a></li>
							<li><a href="/help/terms-conditions">Terms and Conditions</a></li>
							<li><a href="/help/shipping-handling">Shipping&amp;Handling</a></li>
							<li><a href="/help/testimonials">Testimonials</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="helpTopLink" data-toggle="collapse" data-parent="#helpLeftNav" href="#myAccount">
							<span class="glyphicon glyphicon-chevron-right"></span>
							My Account
						</a>
					</h4>
				</div>
				<div id="myAccount" class="helpSub panel-collapse collapse">
					<div class="panel-body">
						<ul class="helpItem">	
							<li><a href="/ac/login">Login/Register</a></li>
							<li><a href="/od/myOrder">Order History</a></li>
							<li><a href="/ac/address">Manage Address</a></li>
							<li><a href="/help/faq">FAQ</a></li>
							<li><a href="/m-article-id-91-pid-.html">Affiliate Program</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a class="helpTopLink" data-toggle="collapse" data-parent="#helpLeftNav" href="#otherInfomation">
							<span class="glyphicon glyphicon-chevron-right"></span>
							Other Information
						</a>
					</h4>
				</div>
				<div id="otherInfomation" class="helpSub panel-collapse collapse">
					<div class="panel-body">
						<ul class="helpItem">	
							<li><a href="/help/shipping-and-handling">Shipping &amp; Handling</a></li>
							<li><a href="/help/large-order-quotations">Large Order Quotations</a></li>
							<li><a href="/help/lowest-price-guarantee">Lowest Price Guarantee</a></li>
							<li><a href="/help/look-for-vendors">Look for Vendors</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$("div#helpLeftNav>div").each(function(index,el){
		var helpDiv = $(el).find(".helpSub");
		helpDiv.on('hidden.bs.collapse', function () {
			$(el).find("span").removeClass("glyphicon-chevron-down");
			$(el).find("span").addClass("glyphicon-chevron-right");
		})
		helpDiv.on('shown.bs.collapse', function () {
			$(el).find("span").removeClass("glyphicon-chevron-right");
			$(el).find("span").addClass("glyphicon-chevron-down");
		})
	})
	var page = '${page}';
	if(page == 'customerService') {
		$("#companyInfomation").removeClass("in");
		$("#customerService").addClass("in");
	} else if(page == 'myAccount') {
		$("#companyInfomation").removeClass("in");
		$("#myAccount").addClass("in");
	} else if(page == 'otherInfomation') {
		$("#companyInfomation").removeClass("in");
		$("#otherInfomation").addClass("in");
	} 
</script>