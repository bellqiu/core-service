<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html lang="en">
<head>
		<title>${pageMeta.title } 
			<tiles:getAsString name="pageTitle"/>
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta content="${pageMeta.keywords } <tiles:getAsString name="pageKeywords"/>" name="keywords">
		<meta content="${pageMeta.description } <tiles:getAsString name="pageDescription"/>" name="description">
		 <meta name="viewport" content="width=1170">
		  <meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		
		<!-- Import css/js -->
		<style type="text/css">
				  @import url("/resources/css/reset.css");
				  @import url("/resources/bxslider/jquery.bxslider.css");
				  @import url("/resources/bootstrap/css/bootstrap.min.css");
				  @import url("/resources/bootstrap/bootstrap-select.min.css");
				   @import url("/resources/bootstrap/css/bootstrap-formhelpers.min.css");
				  
			<c:forEach items="${site.css }" var="css">
		   		 @import url("/resources/${css}");
			</c:forEach>
				 @import url("/resources/css/core.css");
		</style>
		<link href='http://fonts.googleapis.com/css?family=Londrina+Solid|Open+Sans:400,700|Shadows+Into+Light+Two' rel='stylesheet' type='text/css'>
		
		<script src="/resources/js/json2.js" type="text/javascript"></script>
		<script src="/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
		<script src="/resources/js/jquery.loadmask.min.js" type="text/javascript"></script>
		<script src="/resources/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="/resources/bxslider/jquery.bxslider.min.js" type="text/javascript"></script>
		<script src="/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="/resources/bootstrap/js/bootstrap-formhelpers.min.js" type="text/javascript"></script>
		<script src="/resources/bootstrap/bootstrap-select.min.js" type="text/javascript"></script>
		<script src="/resources/js/jqBootstrapValidation.js" type="text/javascript"></script>
		<script src="/resources/js/require.js" type="text/javascript"></script>
		
		<c:forEach items="${site.js }" var="js">
			<script src="${site.resourceServer}${site.webResourcesFolder}${js}" type="text/javascript"></script>
		</c:forEach>
		
		
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-48210569-1', 'dentalsupplies360.com');
		  ga('send', 'pageview');
		
		</script>
</head>
<body data-twttr-rendered="true" style="">
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