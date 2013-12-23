<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<c:if
	test="${(not empty currentOrder)&& (fn:length(currentOrder.items) > 0)}">
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
			<c:forEach items="${currentOrder.items }" var="item">
				<div class="row order-item">
					<div class="col-xs-7">
						<div class="row">
							<div class="col-xs-3 order-item-img-col">
								<a title="${item.productSummary.title }"
									href="${site.domain}/${item.productSummary.name}"><img
									alt="${item.productSummary.title }"
									src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${item.productSummary.imageURL}"></a>
							</div>
							<div class="col-xs-9 order-item-selection-col">
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
										price="${item.finalPrice}" />
								</span>
							</div>
							<div class="col-xs-4 order-item-control-col">
								<span> ${item.quantity}</span>
							</div>
							<div class="col-xs-4">
								<hb:printPrice price="${item.quantity *  item.finalPrice}" />
							</div>
						</div>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>
	
	<div class="panel panel-default" id="OrderAddressPanel">
		<div class="panel-heading">
			<div class="row">
				<span class="padding10">Shipping & Delivery</span>
			</div>
		</div>
		
		<div class="panel-body">
			 <div class="row rowContent">
			 			<div class="row ">Shipping Address: <a href="#" id="new_shipping_address">New Shipping Address</a></div>
			 			<div class="row col-xs-11 col-xs-offset-1">
			 				<div>
				 				<ul>
				 					<li class="removeBorder selected">
												<input class="address" type="radio" name="shipping_address_id" id="addr_10800797" value="10800797">
												<label for="addr_10800797"><strong>FirstName LastName</strong>&nbsp;(Address line 1 Address line 2 City, 1234567 AB, Canada) </label>
												&nbsp;<a class="toEditAddr u" href="javascript:void(0)" style="">Edit</a>
										</li>
										
									<li class="removeBorder selected">
												<input class="address" type="radio" name="shipping_address_id" id="addr_10800797" value="10800797">
												<label for="addr_10800797"><strong>FirstName LastName</strong>&nbsp;(Address line 1 Address line 2 City, 1234567 AB, Canada) </label>
												&nbsp;<a class="toEditAddr u" href="javascript:void(0)" style="">Edit</a>
										</li>
										
									<li class="removeBorder selected">
												<input class="address" type="radio" name="shipping_address_id" id="addr_10800797" value="10800797">
												<label for="addr_10800797"><strong>FirstName LastName</strong>&nbsp;(Address line 1 Address line 2 City, 1234567 AB, Canada) </label>
												&nbsp;<a class="toEditAddr u" href="javascript:void(0)" style="">Edit</a>
										</li>
				 				</ul>
			 				</div>
			 			</div>
			 			
			 </div>
			 <div class="row rowContent">
			 			<div class="row ">Billing Address:  <a>New Billing Address</a> <input type="checkbox"/>Same as Shipping Address </div>
			 			<div class="row col-xs-11 col-xs-offset-1">
			 				<div>
				 				<ul>
				 					<li class="removeBorder selected">
												<input class="address" type="radio" name="shipping_address_id" id="addr_10800797" value="10800797">
												<label for="addr_10800797"><strong>FirstName LastName</strong>&nbsp;(Address line 1 Address line 2 City, 1234567 AB, Canada) </label>
												&nbsp;<a class="toEditAddr u" href="javascript:void(0)" style="">Edit</a>
										</li>
										
									<li class="removeBorder selected">
												<input class="address" type="radio" name="shipping_address_id" id="addr_10800797" value="10800797">
												<label for="addr_10800797"><strong>FirstName LastName</strong>&nbsp;(Address line 1 Address line 2 City, 1234567 AB, Canada) </label>
												&nbsp;<a class="toEditAddr u" href="javascript:void(0)" style="">Edit</a>
										</li>
										
									<li class="removeBorder selected">
												<input class="address" type="radio" name="shipping_address_id" id="addr_10800797" value="10800797">
												<label for="addr_10800797"><strong>FirstName LastName</strong>&nbsp;(Address line 1 Address line 2 City, 1234567 AB, Canada) </label>
												&nbsp;<a class="toEditAddr u" href="javascript:void(0)" style="">Edit</a>
										</li>
				 				</ul>
			 				</div>
			 			</div>
			 			
			 </div>
			<!-- 	<div id="countries_states2" class="bfh-selectbox bfh-countries" data-country="US" data-filter="true" data-flags="true">
				</div>
				<br><br>
				<div class="bfh-selectbox bfh-states" data-country="countries_states2" data-filter="true">
				</div> -->
		</div>
	</div>
	
		
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row">
				<span class="padding10">Payment Method</span>
			</div>
		</div>
		
		<div class="panel-body">
			 <div class="row rowContent">
			 	<div class="row col-xs-3">
			 		<input type="radio"> Standard Shipping
			 	</div>
			 	<div class="row col-xs-3">
			 		Normally 4 - 6 days
			 	</div>
			 	<div class="row col-xs-6">
			 		USD 25
			 	</div>
			 </div>		
			  <div class="row rowContent">
			 	<div class="row col-xs-3">
			 		<input type="radio"> Standard Shipping
			 	</div>
			 	<div class="row col-xs-3">
			 		Normally 4 - 6 days
			 	</div>
			 	<div class="row col-xs-6">
			 		USD 25
			 	</div>
			 </div>	
			  <div class="row rowContent">
			  		<span>When will my order arrive?</span>	<br/>
			  		<p>
			  			<img src="/resources/css/img/processing-time.jpg">
			  		</p>
			  		Processing time is the time includes preparing your items, performing quality checks, and packing for shipment. 
					Processing Time: 7-12 days for standard, 5-8 days for urgent(Custom-Made Items) 1-3 Days for others(Ready to Ship Items). Shipping Time: 3-5 business days.
			  </div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-body">
				<div class="row">
						<div  class="row">
							<div class="col-xs-8">
								<div class="row rowContent">&nbsp;</div>
								<div class="row rowContent">
									<div class="row">
										<div class="col-xs-3" style="line-height: 2.5em">
											Enter Coupon:
										</div>
										<div class="col-xs-5">
											<input style="width: 100%" class="form-control" />
										</div>
										<div class="col-xs-4">
											<button class="btn btn-default" type="button">Apply</button>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3"> </div>
										<div class="col-xs-7">
											<div class="alert alert-danger" style="margin-top: 5px">
												Invalid Coupon code
											</div>
										</div>
											<div class="col-xs-2"></div>
									</div>
								</div>
								
								<div class="row rowContent">
									<div class="row">
										<div class="col-xs-3" style="line-height: 2.5em">
											Leave a Message:
										</div>
										<div class="col-xs-7">
											<textarea rows="" cols="" class="form-control" style="width: 100%;height: 80px"></textarea>
										</div>
										<div class="col-xs-2"></div>
									</div>
								</div>
							</div>
						
							<div class="col-xs-4">
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Item Sub total:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												<hb:printPrice price="${currentOrder.itemTotal }" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Coupon:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												<hb:printPrice price="0" />
											</div>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="row rowContent">
										<div class="row under_score orderPrice-summary">
											<div class="row col-xs-6">Shipping Cost:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												<hb:printPrice price="${currentOrder.itemTotal }" />
											</div>
										</div>
									</div>
								</div>
								
								<div class="row rowContent">
									<div class="row ">
										<div class="row orderPrice-summary orderPrice-summary-all">
											<div class="row col-xs-6">Grand Total:</div>
											<div class="row col-xs-6 orderPrice-summary_value">
												<hb:printPrice price="0" />
											</div>
										</div>
									</div>
								</div>
							</div>
								
						</div>
						<div class="row order_buttons">
							<div class="col-xs-3 col-xs-offset-9  padding10">
								<a href="#" class="btn btn-danger float_right">Process to Checkout</a> 
							</div>
						</div>
			</div>	
		</div>
	</div>

</c:if>
<c:if
	test="${(empty currentOrder) || (fn:length(currentOrder.items) < 1)}">
	<div class="alert alert-warning">
		No item. <a href="/home">Go to Shopping</a>
	</div>
</c:if>
