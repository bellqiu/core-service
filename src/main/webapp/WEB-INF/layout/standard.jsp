<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE>
<html>
<head>
		<title>${pageMeta.title } </title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta content="" name="keywords">
		<meta content="" name="description">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta content="${pageMeta.keywords }" name="keywords">
		<meta content="${pageMeta.description }" name="description">
		
		<!-- Import css/js -->
		<style type="text/css">
				  @import url("/resources/css/reset.css");
				  @import url("/resources/css/smoothness/jquery-ui-1.10.3.custom.css"); 
				  @import url("/resources/bxslider/jquery.bxslider.css");
				  @import url("/resources/bootstrap/css/bootstrap.css");
				  @import url("/resources/bootstrap/bootstrap-select.css");
				  
			<c:forEach items="${site.css }" var="css">
		   		 @import url("/resources/${css}");
			</c:forEach>
				 @import url("/resources/css/core.css");
		</style>
		
		<script src="/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
		<script src="/resources/js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
		<script src="/resources/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="/resources/bxslider/jquery.bxslider.js" type="text/javascript"></script>
		<script src="/resources/bootstrap/js/bootstrap.js" type="text/javascript"></script>
		<script src="/resources/bootstrap/bootstrap-select.js" type="text/javascript"></script>
		<script src="/resources/js/jqBootstrapValidation.js" type="text/javascript"></script>
		<script src="/resources/js/require.js" type="text/javascript"></script>
		
		<c:forEach items="${site.js }" var="js">
			<script src="${site.resourceServer}${site.webResourcesFolder}${js}" type="text/javascript"></script>
		</c:forEach>
</head>
<body>
	<div class="row">
		<tiles:insertAttribute name="header"/>
	</div>

	<div class="row">
		<tiles:insertAttribute name="body"/>
	</div>

	<div class="row">
		<tiles:insertAttribute name="footer"/>
	</div>
	
	<script type="text/javascript">
		$(".selectpicker").selectpicker();
		$(".wantTooltip").tooltip();
	</script>
	
</body>
</html>