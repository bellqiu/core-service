<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<div class="container mainContainer">
	<div class="row">
		<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<li><a href="${site.domain}/sp/shoppingcart/list">Shopping Cart</a></li>
  				<li class="active">Filling Address</li> 
			</ol>
	</div>
	<div class="row shopping_cart_container">
		<jsp:include page="shoppingcatAddressFragment.jsp"></jsp:include>
	</div>
	
</div>


<script src="/resources/js/ShoppingcartAdressPageMain.js" type="text/javascript"></script>