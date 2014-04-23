<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>

<div class="container mainContainer">
	<div class="row">
		<div>
			<ol class="breadcrumb">
  				<li><a href="${site.domain}">Home</a></li>
  				<li class="active">Blog</li>
			</ol>
		</div>
	</div>
	<div class="row tag-index">
		<c:choose>
			<c:when test="${empty bloggers || (fn:length(bloggers) < 1) }">
				<div class="alert alert-info">0 blogs found </div>
			</c:when>
			<c:otherwise>
			<div class="panel panel-default">
				<div id="tag-index-panel" class="quick-index">
					<table border="0" style="width:65%" align="center" class="table table-striped noborder" title="Blogs List">
					<thead>
						<tr><th width="7%">Index</th><th width="73%">Title</th><th width="20%">Content Type</th></tr>
					</thead>
					<tbody>
						<!-- <tr class="TableRowOddLB" style="border: none">
							<td align="right">1</td>
							<td align="left">
								<a class="TextLowContextLinkLB" href=" http://h71000.www7.hp.com/partners/oracle/archiveqs.html">HP OpenVMS systems - Partners - <b>Oracle</b></a>
							</td>
							<td align="left" valign="top">
										Manuals
							</td>
						</tr> -->
						<c:forEach var="blog" items="${bloggers}" varStatus="status">
							<tr>
								<td align="center">${status.count}</td>
								<td align="left">
									<a href="${site.domain}/rs/blog/${blog.name}">${blog.description}</a>
								</td>
								<td align="left" valign="top">
									${blog.type}
								</td>
							</tr>
						</c:forEach>
					</tbody></table>
		            <%-- <ul>
			            <c:forEach var="tag" items="${tags}">
			            	<c:set value="${fn:replace(tag, ' ', '-')}" var="tagIndexItemName"></c:set>
							<li><a href='${site.domain}/tags/key/<hb:urlUtil value="${tagIndexItemName}"/>'>${tag}</a></li>
						</c:forEach>
		         	</ul> --%>
				</div>
				<div class="text-center">
					<ul class="pagination">
						<c:choose>
							<c:when test="${currentPageIndex == 0}">
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${site.domain}/blog/list/${currentPageIndex-1}">&lt;</a></li>
							</c:otherwise>
						</c:choose>
						<c:forEach items="${pageIds }" var="item">
	  						<c:choose>
	  							<c:when test="${item < 0}">
	  								<li><a>...</a></li>
	  							</c:when>
	  							<c:otherwise>
	  								<c:choose>
	  									<c:when test="${item == currentPageIndex}">
	  										<li class="active"><a href="${site.domain}/blog/list/${item}">${item+1} <span class="sr-only">(current)</span></a></li>
	  									</c:when>
	  									<c:otherwise>
	  											<li><a href="${site.domain}/blog/list/${item}">${item+1}</a></li>
	  									</c:otherwise>
	  								</c:choose>
	  							</c:otherwise>
	  						</c:choose>
	  					</c:forEach>
						<c:choose>
							<c:when test="${currentPageIndex+1 == totalPage}">
								<li class="disabled"><a href="#">&gt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${site.domain}/blog/list/${currentPageIndex+1}">&gt;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>		
			</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>