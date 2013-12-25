<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<div>
	<legend>Manage your addresses</legend>
</div>
<div class="padding10">
<button type="button" id="addAddress" onclick="addAddress()" class="btn btn-danger">Add a New Address</button>
</div>
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
						<button type="button" data-toggle="modal" data-target="#editAddressModal${address.id}" onclick="editAddress(${address.id})" class="btn btn-danger">Edit</button>
						<button type="button" onclick="deleteAddress(${address.id})" class="btn btn-danger">Delete</button>
					</li>
				</ul>
			</div>
			<div class="modal fade" id="editAddressModal${address.id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  				<div class="modal-dialog">
    				<div class="modal-content">
      					<div class="modal-header">
        					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        					<h4 class="modal-title" id="myModalLabel">Edit Address</h4>
      					</div>
      					<div class="modal-body" id="fogot-modal-body">

<!-- Start -->      						
      						<form class="example form-inline">
<div class="addressbaody">
	<div class="row padding5">
		<div class="col-xs-3"><span>Your Name:</span></div>
		<div class="col-xs-7">
			<div class="row">
				<div class="col-xs-6 ">
					<div class="row padding5">
						<input class="form-control" value="${address.firstName}">
					</div>
					<div class="row">
						<span>* First Name</span>
					</div>
				</div>
				<div class="col-xs-6">
					<div class="row padding5">
						<input class="form-control">
					</div>
					<div class="row">
						<span>* Last Name</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*Address Line 1:</span></div>
		<div class="col-xs-7">
			<input class="form-control">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*Address Line 2:</span></div>
		<div class="col-xs-7">
			<input class="form-control">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*City:</span></div>
		<div class="col-xs-7">
			<input class="form-control">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*Destination Country/Region:</span></div>
		<div class="col-xs-7">
			<div id="countries_states" class="bfh-selectbox bfh-countries" data-country="US" data-flags="true" data-filter="true"></div>
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*State / Province / Region:</span></div>
		<div class="col-xs-7">
			<div class="bfh-selectbox bfh-states" data-country="countries_states"  data-filter="true"></div>
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*ZIP / Postal Code:</span></div>
		<div class="col-xs-7">
			<input class="form-control">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*Phone Number:</span></div>
		<div class="col-xs-7">
				<input class="form-control">
		</div>
	</div>

</div>
</form>
<!-- End -->
      						
      					</div>
     					<div class="modal-footer">
     						<div class="form-group">
        						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        						<button type="button" id="sendEmail" data-loading-text="Processing.." class="btn btn-danger">Send</button>
        					</div>
      					</div>
    				</div><!-- /.modal-content -->
  				</div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			</div>
	<c:if test="${status.index % 2 == 1 || status.last}">
		</div>
	</c:if>
</c:forEach>
</div>
</div>					
<script src="/resources/js/AddressPageMain.js" type="text/javascript">
</script>

<script type="text/javascript">
editAddress = function(addressId) {
	console.log("edit address");
}
deleteAddress = function(addressId) {
	console.log("delete address");
}
addAddress = function(addressId) {
	console.log("add address");
}
</script>			

					
