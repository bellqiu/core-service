<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Test
<c:forEach items="${addresses }" var="item" varStatus="status">
	<div class="col-lg-12 panel panel-default">
					<div class="row form-group">
						<label class="col-lg-2">First Name: </label>
					 	<div class="col-lg-10">
						First Name......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Last Name: </label>
					 	<div class="col-lg-10">
						Last Name......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Address1: </label>
					 	<div class="col-lg-10">
						Address1......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Address2: </label>
					 	<div class="col-lg-10">
						Address2......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Postal Code: </label>
					 	<div class="col-lg-10">
						postal_code......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Phone: </label>
					 	<div class="col-lg-10">
						phone......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">City: </label>
					 	<div class="col-lg-10">
						City......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">State: </label>
					 	<div class="col-lg-10">
						state province......
						</div>
					</div>
					<div class="row form-group">
						<label class="col-lg-2">Country Code: </label>
					 	<div class="col-lg-10">
						country_code......
						</div>
					</div>
					<div class="row">
						<div class="col-lg-offset-2 col-lg-10">
						<button type="button" class="btn btn-danger" onclick="getAddress(1)">Edit Address</button>
						</div>
					</div>
	</div>
</c:forEach>				
						
<%-- 	<div class="row">
			<c:forEach items="${productSummary }" var="item">
							<div class="col-xs-4">
								<div class="thumbnail productThumbnail">
								<a href="${site.domain}/${item.name }" title="${item.title }">
      							<img src="${site.resourceServer}/rs/img/${item.imageURL }" alt="${item.title }" class="category-product-img">
								</a>
      							<div class="caption">
        						<!-- <h3>Thumbnail label</h3> -->
        						<p><a href="${site.domain}/${item.name }">${item.title }</a></p>
      							</div>
   		 						</div>
							</div>
						</c:forEach>
					</div> --%>
					
