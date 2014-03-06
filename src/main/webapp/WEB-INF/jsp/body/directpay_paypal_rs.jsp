<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="container mainContainer">
		<div class="panel panel-default" id="OrderAddressPanel">
			<div class="panel-heading">
				<div class="row">
					<span class="padding10">Payment status</span>
				</div>
			</div>
			<div class="panel-body">
				<div class="row rowContent">
					<div class="alert alert-success">
						Payment successful. <a href="/od/orderDetail?orderId=<%=request.getParameter("orderId") %>">View order detail</a>
					</div>
				</div>
			</div>
		</div>
</div>