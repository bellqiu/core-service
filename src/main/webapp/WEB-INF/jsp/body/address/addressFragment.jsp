<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="addressbaody">
	<div class="row padding5">
		<div class="col-xs-3"><span>Your Name:</span></div>
		<div class="col-xs-7">
			<div class="row">
				<div class="col-xs-6 ">
					<div class="row padding5">
						<input class="form-control"  name="firstName" required="required"  data-validation-required-message="Please fill your first name">
					</div>
					<div class="row">
						<span>* First Name</span>
					</div>
				</div>
				<div class="col-xs-6">
					<div class="row padding5">
						<input class="form-control" name="lastName" required="required"  data-validation-required-message="Please fill your last name">
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
			<input class="form-control" name="address1" required="required"  data-validation-required-message="Please fill your address">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>Address Line 2:</span></div>
		<div class="col-xs-7">
			<input class="form-control" name="address2">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*City:</span></div>
		<div class="col-xs-7">
			<input class="form-control" name="city">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*Destination Country/Region:</span></div>
		<div class="col-xs-7">
			<div id="countries_states" class="bfh-selectbox bfh-countries" data-name="country" data-country="US" data-flags="true" data-filter="true"></div>
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>State / Province / Region:</span></div>
		<div class="col-xs-7">
			<div id="state_province" class="bfh-selectbox bfh-states" data-country="countries_states" data-name="stateProvince"  data-filter="true"></div>
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>ZIP / Postal Code:</span></div>
		<div class="col-xs-7">
			<input class="form-control" name="postalCode">
		</div>
	</div>
	
	<div class="row padding5">
		<div class="col-xs-3"><span>*Phone Number:</span></div>
		<div class="col-xs-7">
			<input class="form-control" name="phone" required="required"  data-validation-required-message="Please fill your phone number">
		</div>
	</div>

</div>
<script type="text/javascript">
	$('form select.bfh-countries, span.bfh-countries, div.bfh-countries').each(function () {
    var $countries;

    $countries = $(this);

    if ($countries.hasClass('bfh-selectbox')) {
      $countries.bfhselectbox($countries.data());
    }
    $countries.bfhcountries($countries.data());
  });

$('form select.bfh-states, span.bfh-states, div.bfh-states').each(function () {
    var $states;

    $states = $(this);

    if ($states.hasClass('bfh-selectbox')) {
      $states.bfhselectbox($states.data());
    }
    $states.bfhstates($states.data());
  });
  

</script>