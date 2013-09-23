<%@page import="com.hb.core.shared.dto.SiteDTO"%>
<%@page import="java.io.StringWriter"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE>
<html>
<head>
		<title>Admin Console</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta content="" name="keywords">
		<meta content="" name="description">
		<script src="/resources/ext/plugins/flash/swfobject.js" type="text/javascript"></script>
		<!-- Import css/js -->
		<style type="text/css">
			@import url("/resources/ext/resources/ext-theme-neptune/ext-theme-neptune-all.css");
			@import url("/resources/ext/ux/shared/example.css");
		</style>
		<script type="text/javascript">
			var loginuser = {
					id : '1',
					email: 'wan-shan.zhu@hp.com'
			}
			<%
			
			ObjectMapper mapper = new ObjectMapper();
			
			StringWriter stringWriter = new StringWriter();
			
			SiteDTO dto = (SiteDTO)request.getAttribute("site");
			
			mapper.writeValue(stringWriter, dto);
			
			%>
			
			var site = <%= stringWriter.getBuffer().toString()%> ;
		</script>
		<script src="/resources/ext/ext-all-debug.js" type="text/javascript"></script>
		<script src="/api.js" type="text/javascript"></script>
		<script type="text/javascript" src="/resources/ext/ux/upload/plupload/js/plupload.js"></script>
		<script type="text/javascript" src="/resources/ext/ux/upload/plupload/js/plupload.html4.js"></script>
		<script type="text/javascript" src="/resources/ext/ux/upload/plupload/js/plupload.html5.js"></script>
		<script type="text/javascript" src="/resources/ext/ux/upload/plupload/js/plupload.flash.js"></script>
		<script type="text/javascript" src="/resources/ext/ux/upload/plupload/js/plupload.silverlight.js"></script>
		<script type="text/javascript" src="/resources/ext/ux/shared/examples.js"></script>	
		<script src="/resources/ext/Admin.js" type="text/javascript"></script>
		
</head>
<body>
</body>
</html>