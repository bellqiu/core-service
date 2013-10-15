<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<body>
		<ul>
		
		<c:forEach items="${cache }" var="el">
			<li>
				${el} <a href="/admin/cache/remove?el=${el}">clear</a>
			</li>
		</c:forEach>
		
		</ul>
	</body>

</html>