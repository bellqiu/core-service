<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<div>
<c:choose>
	<c:when test="${empty orderDetail}">
		<div class="alert alert-info">You viewed order is not existing.</div>
	</c:when>
	<c:otherwise>
		<div class="padding10">
			<legend>View your order detail</legend>
		</div>
		<div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="row">
						<div class="orderItemHeader">
							<div class="col-xs-7">Item details</div>
							<div class="col-xs-5">
								<div class="col-xs-4">Price</div>
								<div class="col-xs-4">Quantity</div>
								<div class="col-xs-4">Total</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<c:forEach items="${orderDetail.items }" var="item">
						<div class="row order-item">
							<div class="col-xs-7">
								<div class="row">
									<div class="col-xs-4 order-item-img-col">
										<a title="${item.productSummary.title }"
											href="${site.domain}/${item.productSummary.name}"><img
											alt="${item.productSummary.title }"
											src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${item.productSummary.imageURL}"></a>
									</div>
									<div class="col-xs-8 order-item-selection-col">
										<div class="row">
											<a href="${site.domain}/${item.productSummary.name}">${item.productSummary.title }</a>
										</div>
										<c:forEach items="${item.selectedOpts }" var="opts">
											<div class="row order-item-selection">
												<div class="col-xs-12 order-item-selection-value">
													${opts.key }</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="col-xs-5">
								<div class="row">
									<div class="col-xs-4 order-item-price-col">
										<span class="price"> <hb:printPrice
												price="${item.finalPrice}" currencyCode="${orderDetail.currency}"/>
										</span>
									</div>
									<div class="col-xs-4 order-item-control-col">
										<span> ${item.quantity}</span>
									</div>
									<div class="col-xs-4">
										<hb:printPrice price="${item.quantity *  item.finalPrice}" currencyCode="${orderDetail.currency}" />
									</div>
								</div>
							</div>
						</div>
		
					</c:forEach>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="row">
							<div class="col-xs-7">
								<div class="row rowContent">&nbsp;</div>
								<div class="row rowContent">
									<div style="line-margin-left: 1em">Shipping Address:</div>
									<div class="row col-xs-11 col-xs-offset-1">
					 					<label>${orderDetail.shippingAddress}</label>
					 				</div> 
					 			</div>
					 			
					 			<div class="row rowContent">
									<div style="line-margin-left: 1em">Shipping method:</div>
									<div class="row col-xs-11 col-xs-offset-1">
					 					<label>${orderDetail.shippingMethod}</label>
					 				</div> 
					 			</div>
					 			
					 			<div class="row rowContent">
									<div style="line-margin-left: 1em">Billing Address:</div>
									<div class="row col-xs-11 col-xs-offset-1">
					 					<label>${orderDetail.billingAddress}</label>
					 				</div> 
					 			</div>
					 			
					 			<div class="row rowContent">
									<div style="line-margin-left: 1em">Order Status:</div>
									<div class="row col-xs-11 col-xs-offset-1">
					 					<label>${orderDetail.orderStatus}</label>
					 				</div> 
					 			</div>
							</div>
						
							<div class="col-xs-5">
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Item Sub total:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												<span id="orderItemTotalSpan"><hb:printPrice price="${orderDetail.itemTotal }" currencyCode="${orderDetail.currency}" /></span>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Coupon:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												- <span id="orderCouponCutOffSpan"><hb:printPrice price="${orderDetail.couponCutOff }" currencyCode="${orderDetail.currency}" /></span>
											</div>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Shipping Cost:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												 <span id="orderShippingCostSpan"><hb:printPrice price="${orderDetail.deliveryPrice }" currencyCode="${orderDetail.currency}" /></span>
											</div>
										</div>
									</div>
								</div>
								
								<div class="row rowContent">
									<div class="row ">
										<div class="row orderPrice-summary orderPrice-summary-all">
											<div class="row col-xs-6">Grand Total:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												 <span id="orderGrandTotalSpan"><hb:printPrice price="${orderDetail.grandTotal }" currencyCode="${orderDetail.currency}" /></span>
											</div>
										</div>
									</div>
								</div>
							</div>
								
						</div>
						<c:if test="${orderDetail.orderStatus == 'PENDING' }">
						<div class="row order_buttons">
							<div class="col-xs-3 col-xs-offset-9 padding10">
								<a href="${site.domain}/sp/payment/paymentInfo?orderId=${orderDetail.id}" class="btn btn-danger float_right" id="continuePayment">Continue Payment</a> 
							</div>
						</div>
						</c:if>
					</div>	
				</div>
			</div>
		</div>
	
	</c:otherwise>
</c:choose>
</div>