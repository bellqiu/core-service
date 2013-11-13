
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:forEach items="${tabProduct.products }" var="product">
		<li>
		<div class="productItem">
			<a href="${site.domain}/${product.name}" title="${product.title}">
			<img alt="${product.title }" src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL}" />
			</a>
			<p><a href="${site.domain}/${product.name}">${product.title }</a></p>
			<p>asdasdasd</p>
			<p>asdasdasd</p>
		</div>
		
		</li>
	</c:forEach>
