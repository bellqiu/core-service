<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<ul>
		<c:forEach items="${catlogs}" var="item" varStatus="stat">
			<c:choose>
				<c:when test="${item.type == 'NAVIGATION' }">
					<li class="l3Category"><a href="${site.domain}/c/${item.name }">${item.displayName }</a></li>
				</c:when>
				<c:otherwise>
					<li class="l3Category"><a href="${item.url }">${item.displayName }</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
