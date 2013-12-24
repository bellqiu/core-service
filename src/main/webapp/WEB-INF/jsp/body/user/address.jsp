<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<div>
	<legend>Manage your addresses</legend>
</div>
<div class="padding10">
<button type="button" id="addAddress" class="btn btn-danger">Add a New Address</button>
</div>
<div id="addressContainer" >
<c:forEach items="${addresses }" var="address" varStatus="status">
	<c:if test="${status.index % 2 == 0}">
		<div class="row">
	</c:if>
			<div class="col-xs-6">
			<div class="row padding10 panel panel-default">
				<ul>
					<li><strong>${address.firstName} ${address.lastName}</strong></li>
					<li>${address.address1},${address.address2}</li>
					<li>${address.city},${address.stateProvince},${address.postalCode}</li>
					<li>${address.countryCode}</li>
					<li>${address.phone}</li>
					<li>
						<button type="button" onclick="editAddress(${address.id})" class="btn btn-danger">Edit</button>
						<button type="button" onclick="deleteAddress(${address.id})" class="btn btn-danger">Delete</button>
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

					
