<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<div>
<div class="padding10">
	<legend >Manage your addresses</legend>
</div>
<c:if test="${(empty addresses) || (fn:length(addresses) < 6)}">
<div class="padding10">
<button type="button" id="addAddress" class="btn btn-danger">Add a New Address</button>
</div>
</c:if>
<div id="addressContainer" >
<c:forEach items="${addresses }" var="address" varStatus="status">
	<c:if test="${status.index % 2 == 0}">
		<div class="row">
	</c:if>
			<div class="col-xs-6">
			<div class="row padding10">
				<ul>
					<li><strong>${address.firstName} ${address.lastName}</strong></li>
					<li>${address.address1},${address.address2}</li>
					<li>${address.city},${address.stateProvince},${address.postalCode}</li>
					<li>${address.countryCode}</li>
					<li>${address.phone}</li>
					<li>
						<button type="button" data-addressId="${address.id}" class="btn btn-danger editAddress">Edit</button>
						<button type="button" data-addressId="${address.id}" class="btn btn-danger deleteAddress">Delete</button>
					</li>
				</ul>
			</div>
			</div>
	<c:if test="${status.index % 2 == 1 || status.last}">
		</div>
	</c:if>
</c:forEach>
</div>
</div>					
<script src="/resources/js/AddressPageMain.js" type="text/javascript">
</script>

