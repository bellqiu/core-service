<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb" %>
		
<div class="subCategory row">
	<c:forEach items="${catlogs}" var="item" varStatus="stat">
		<div class="subCategory-list col-xs-3">
			<c:choose>
				<c:when test="${item.type == 'NAVIGATION' }">
					<h2 class="l2Category"><a href="/c/${item.name }">${item.displayName }</a></h2>
					<hb:catlog parentId="${item.id }" level="3"/>
				</c:when>
				<c:otherwise>
					<h2 class="l2Category"><a href="${item.url }">${item.displayName }</a></h2>
					<hb:catlog parentId="${item.id }" level="3"/>
				</c:otherwise>
			</c:choose>
		</div>
	</c:forEach>
</div>
