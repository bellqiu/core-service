<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${not empty freedompage }">
	<c:catch var="freedompageException">
		<c:import url="/WEB-INF/jsp/freedom_pages/${freedompage}.jsp"></c:import>
	</c:catch>
</c:if>
<c:if test="${not empty freedompageException }">
	No Such Page
</c:if>
