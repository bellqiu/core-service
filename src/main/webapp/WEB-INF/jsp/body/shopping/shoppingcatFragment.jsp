<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="panel panel-default">

	<div class="panel-heading">Shopping cart details</div>
	<div class="panel-body">
	
	<c:forEach items="${currentOrder.items }" var="item">
		<div class="row order-item">
			<div class="col-xs-8">
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
			<div class="col-xs-4">
				<div class="row">
					<div class="col-xs-4 order-item-price-col">
						<span class="price">
							<hb:printPrice price="${item.quantity *  item.finalPrice}"/>
						</span>
					</div>
					<div class="col-xs-4 order-item-control-col">
                            <a href="javascript:void(0);" class="decrement OrderItemDecrement" data-orderItemId="${item.id}">-</a>
                            <input type="text" class="quantity-text OrderItemEnter" value="${item.quantity}" size="3" data-orderItemId="${item.id}">
                            <a href="javascript:void(0);" class="increment OrderItemIncrement" data-orderItemId="${item.id}">+</a>
					</div>
					<div class="col-xs-4">
						<a href="javascript:void(0);" class="removeItemFromOrder" data-orderItemId="${item.id}">Remove</a>
					</div>
				</div>
			</div>
		</div>	
		
		</c:forEach>
			<div class="row">
				<div class="row">
					<div class="row col-xs-4  col-xs-offset-8 ">
						<div class="row under_score orderPrice-summary">
							<div class="row col-xs-6">
								Item Sub total:
							</div>
							<div class="row col-xs-6 orderPrice-summary_value">
								<hb:printPrice price="${currentOrder.itemTotal }"/>
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
							<div class="row col-xs-6 orderPrice-summary_value">
									<hb:printPrice price="${currentOrder.grandTotal }"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row order_buttons">
					<div class="col-xs-4 ">
						<a href="/home" class="btn btn-default">Continue Shopping</a> 
					</div>
					<div class="col-xs-8  padding10">
						<a href="#" class="btn btn-danger float_right">Process to Checkout</a> 
					</div>
				</div>
			</div>
	</div>

</div>