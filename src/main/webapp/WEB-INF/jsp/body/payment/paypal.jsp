<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<div class="container mainContainer">
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
								confirm Order SN: ${currentOrder.orderSN }
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
