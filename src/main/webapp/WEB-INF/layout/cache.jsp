<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tag/HBTag.tld"  prefix="hb"%>
<html>
	<body>
		<ul>
		
		<c:forEach items="${cache }" var="el">
			<li>
				${el} <a href="/admin/cache/remove?el=${el}">clear</a>
			</li>
		</c:forEach>
		
		</ul>
		
		<hb:cleanCache/>
		
	</body>

</html>