
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>

	<c:forEach items="${tabProduct.products }" var="product">
		<li>
		<div class="productItem">
			<div class="tabproductimgdiv">
			<a href="${site.domain}/${product.name}" title="${product.title}">
				<img alt="${product.title }" align="middle" src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${product.imageURL}" />
			</a>
			</div>
			<div>
				<span class="activeprice">	<hb:printPrice price="${product.actualPrice}"/>
				</span>
				<span class="priceDeprecated"><hb:printPrice price="${product.price}"/></span>
			</div>
			<p><a href="${site.domain}/${product.name}">${product.title }</a></p>
			<c:if test="${product.price > 0}">
			<fmt:parseNumber value="${(product.price - product.actualPrice)/product.price * 100 }" var="percentage" integerOnly="true"></fmt:parseNumber>
				<c:if test="${percentage > 10 }">
					<div class="sales-logo">
						<label class="text">
							Save
						</label>
						<label class="percentage">
							
							${percentage }%
						</label>
					</div>
				</c:if>
			</c:if>
		</div>
		
		</li>
	</c:forEach>
