<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid featuredProducts">
<h1 class="heading--large">Featured Products</h1>
	<div class="row">
	<c:forEach items="${tabProduct.products }" var="product">
		<div class="productItem col-xs-3">
			<div>
			<a href="${site.domain}/${product.name}" title="${product.title}">
				<img alt="${product.title }" src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL}" />
			</a>
			</div>
			<div>
				<a href="${site.domain}/${product.name}">${product.title}</a>
			</div>
		</div>
	</c:forEach>
	</div>
</div>
		
