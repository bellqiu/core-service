<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="example form-inline">
<div class="addressbaody">
	<div class="row padding5">
		<div class="col-xs-3"><span>Your Name:</span></div>
		<div class="col-xs-7">
			<div class="row">
				<div class="col-xs-6 ">
					<div class="row padding5">
						<input class="form-control">
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
<script src="/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/resources/bootstrap/js/bootstrap-formhelpers.min.js" type="text/javascript"></script>