<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<div class="container mainContainer" style="display: none;">
	  <div class="row">
		<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<li><a href="${site.domain}/sp/shoppingcart/list">Shopping Cart</a></li>
  				<li class="active"><a href="${site.domain}/sp/payment/paymentInfo/?orderId=${currentOrder.id}">Payment info</a></li> 
  				<li class="active">Complete payment</li> 
			</ol>
	</div>
			
	<div class="panel panel-default" id="paymentsumaryPanel">
		<div class="panel-body">
				<div class="row">
						<div  class="row">
							<div class="col-xs-8">
								<div  class="row orderSN">
									<div class="col-xs-2"></div>
									<div class="col-xs-4">
										<p class="rowContent">
										<strong>SN: ${currentOrder.orderSN }</strong>
										</p>
									</div>
									<div class="col-xs-2">
											<form action="https://www.paypal.com/cgi-bin/webscr" method="post"
												id="paypaysubmitForm">
												<input type="hidden" name="cmd" value="_xclick"> <input
													type="hidden" name="business" value="${paypalAccount }"> <input
													type="hidden" name="item_name" value="Order SN: ${currentOrder.orderSN }">
													
													<input
													type="hidden" name="item_desc" value="Test">
													
												<input type="hidden" name="amount"
													value='<hb:printPrice price="${currentOrder.grandTotal }" withCurrency="false"/>'>
													
												<input type="hidden" name="currency_code"
													value="<hb:printCurrency/>"> 
												
												<input type="hidden"
													name="lc" value="US"> 
													
												<input type="hidden" name="notify_url"
													value="${notifyUrl }">
												 <input
													type="hidden" name="return"
													value="${returnUrl }">
											<button type="button" data-orderId="${currentOrder.id }" class="btn btn-danger" id="payByPaypal" data-loading-text="Processing..">Pay by PayPal</button>
										</form>
									</div>
									<div class="col-xs-2"></div>
								</div>
							</div>
						
							<div class="col-xs-4">
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Item Sub total:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												<hb:printCurrency/> <span id="orderItemTotalSpan"><hb:printPrice price="${currentOrder.itemTotal }" withCurrency="false" /></span>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Coupon:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												- <hb:printCurrency/> <span id="orderCouponCutOffSpan"><hb:printPrice price="${currentOrder.couponCutOff }" withCurrency="false" /></span>
											</div>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Shipping Cost:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												 <hb:printCurrency/> <span id="orderShippingCostSpan"><hb:printPrice price="${currentOrder.deliveryPrice }" withCurrency="false" /></span>
											</div>
										</div>
									</div>
								</div>
								
								<div class="row rowContent">
									<div class="row ">
										<div class="row orderPrice-summary orderPrice-summary-all">
											<div class="row col-xs-6">Grand Total:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												 <hb:printCurrency/> <span id="orderGrandTotalSpan"><hb:printPrice price="${currentOrder.grandTotal }" withCurrency="false" /></span>
											</div>
										</div>
									</div>
								</div>
							</div>
								
						</div>
			</div>	
		</div>
	</div>
	
</div>
<script src="/resources/js/paypalPageMain.js" type="text/javascript"></script>