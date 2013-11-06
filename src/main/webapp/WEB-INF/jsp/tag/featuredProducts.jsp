<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid featuredProducts">
<h1 class="heading--large">Featured Products</h1>
	<div class="row">
	<c:forEach items="${tabProduct.products }" var="product">
		<div class="productItem col-xs-3">
			<div>
			<a href="${site.resourceServer}/${product.name}" title="${product.title}">
				<img alt="${product.title }" src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL}" />
			</a>
			</div>
			<div>
				Product1
			</div>
		</div>
	</c:forEach>
	</div>
</div>
		<li><div class="productItem">
			
		</div></li>
