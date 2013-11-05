<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
		
	<!-- TODO add specialOffer tag -->
<div class="specialOffer">
	<c:forEach items="${specialcatlogs }" var="item" varStatus="stat">
		<div class="special-item">
			<a href="${site.resourceServer}/c/${item.name }">
				<img alt="" src="${item.iconUrl }">
				<p>${item.displayName }</p>
			</a>
		</div>
	</c:forEach>
	<div style="clear:both"></div>
</div>
