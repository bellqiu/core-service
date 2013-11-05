
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:forEach items="${tabProduct.products }" var="product">
		<li><div class="productItem">
			<img alt="${product.title }" src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL}" />
		</div></li>
	</c:forEach>
