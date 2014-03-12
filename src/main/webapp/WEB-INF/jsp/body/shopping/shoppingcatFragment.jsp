<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<c:if test="${(not empty currentOrder)&& (fn:length(currentOrder.items) > 0)}">
<div class="panel panel-default">

	<div class="panel-heading">
		<div class="row">
			<div class="col-xs-7">
				Cart Items
			</div>
			<div class="col-xs-5">
				<div class="col-xs-6"></div>
				<div class="col-xs-3"></div>
				<div class="col-xs-3"></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
	
	<c:forEach items="${currentOrder.items }" var="item">
		<div class="row order-item">
			<div class="col-xs-7">
				<div class="row">
					<div class="col-xs-3 order-item-img-col" >
						<a title="${item.productSummary.title }" href="${site.domain}/${item.productSummary.name}"><img alt="${item.productSummary.title }" src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${item.productSummary.imageURL}"></a>
					</div>
					<div class="col-xs-9 order-item-selection-col" >
						<div class="row">
							<a href="${site.domain}/${item.productSummary.name}" >${item.productSummary.title }</a>
						</div>
						<c:forEach items="${item.selectedOpts }" var="opts">
							<div class="row order-item-selection">
								<div class="col-xs-12 order-item-selection-value">
									${opts.key }
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-xs-5">
				<div class="row">
					<div class="col-xs-5 order-item-price-col">
						<span class="price">
							<hb:printPrice price="${item.finalPrice}"/> x ${item.quantity }
						</span>
					</div>
					<div class="col-xs-4 order-item-control-col">
							<div class="input-group">
                            <a href="javascript:void(0);" class="decrement OrderItemDecrement input-group-addon" data-orderItemId="${item.id}">-</a>
                            <input type="text" class="quantity-text OrderItemEnter form-control" value="${item.quantity}" size="5" data-orderItemId="${item.id}">
                            <a href="javascript:void(0);" class="increment OrderItemIncrement input-group-addon" data-orderItemId="${item.id}">+</a>
							</div>
					</div>
					<div class="col-xs-3">
						<a href="javascript:void(0);" class="removeItemFromOrder" data-orderItemId="${item.id}">Remove</a>
					</div>
				</div>
			</div>
		</div>	
		
		</c:forEach>
			<div class="row">
						<div class="row rowContent">
							<div class="row">
								<div class="col-xs-2 col-xs-offset-2" style="line-height: 2.5em">Enter
									Coupon:</div>
								<div class="col-xs-3">
									<input style="width: 100%" class="form-control"
										name="couponCode" />
								</div>
								<div class="col-xs-5">
									<button class="btn btn-default" type="button"
										data-order-id="${currentOrder.id }" id="applyCouponButton">Apply</button>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-4"></div>
								<div class="col-xs-3">
									<div class="alert alert-danger couponErrorArea">Invalid
										Coupon code</div>
									<div class="alert alert-info couponInfoArea">Invalid
										Coupon code</div>
								</div>
								<div class="col-xs-5"></div>
							</div>
						</div>
				<div class="row">
					<div class="row col-xs-4  col-xs-offset-8 ">
						<div class="row under_score orderPrice-summary">
							<div class="row col-xs-6">
								Item Sub total:
							</div>
							<div class="row col-xs-6 orderPrice-summary_value">
								<hb:printCurrency /><span id="orderItemTotalSpan"><hb:printPrice price="${currentOrder.itemTotal }" withCurrency="false"/></span>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
							<div class="row col-xs-4  col-xs-offset-8 "">
								<div class="row under_score orderPrice-summary">
									<div class="row col-xs-6">Coupon:</div>
									<div class="row col-xs-6 orderPrice-summary_value">
										-
										<hb:printCurrency />
										<span id="orderCouponCutOffSpan"><hb:printPrice
												price="${currentOrder.couponCutOff }" withCurrency="false" /></span>
									</div>
								</div>
							</div>
				</div>
				<div class="row">
					<div class="row col-xs-4  col-xs-offset-8 ">
						<div class="row orderPrice-summary orderPrice-summary-all">
							<div class="row col-xs-6">
								Grand Total:
							</div>
							<div class="row col-xs-6 orderPrice-summary_value" >
									<hb:printCurrency /><span id="orderGrandTotalSpan"><hb:printPrice price="${currentOrder.grandTotal }" withCurrency="false"/></span>
							</div>
						</div>
					</div>
				</div>
				<div class="row order_buttons">
					<div class="col-xs-4 ">
						<a href="/home" class="btn btn-default">Continue Shopping</a> 
					</div>
					<div class="col-xs-8  padding10">
						 <a href="/sp/payment/paymentInfo" class="btn btn-warning float_right">Proceed to Checkout</a>  
						 <c:if test="${empty currentUser }">
							 <div class="float_right padding5">
								 <a href="/sp/directpay/paypal"  >
								 	<img alt="Paypal PAY" src="/resources/css/img/btn_xpressCheckout.gif">
								 </a>
							 </div>
						 </c:if>
						<!--  <input type="image" name="submit" -->
					</div>
				</div>
			</div>
	</div>
</div>
</c:if>
<c:if test="${(empty currentOrder) || (fn:length(currentOrder.items) < 1)}">
	<div class="alert alert-warning">Shoppingcart is empty. <a href="/home">Go to Shopping</a></div>
</c:if>