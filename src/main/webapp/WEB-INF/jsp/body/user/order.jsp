<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<div>
<div class="padding10">
	<legend>View your orders</legend>
</div>
<div>
<c:choose>
	<c:when test="${empty orders || (fn:length(orders) < 1) }">
		<div class="alert alert-info">No orders</div>
	</c:when>
	<c:otherwise>
		
		<div class="row">
			<table class="table table-striped">
        	<thead>
          		<tr>
            		<th>#</th>
            		<th>Order SN</th>
            		<th>Price</th>
            		<th>Status</th>
          		</tr>
        	</thead>
        	<tbody>
			<c:forEach items="${orders }" var="order" varStatus="status">
				<tr>
					<td>${status.index + resultStart}</td>
					<td><a href="#">${order.orderSN}</a></td>
					<td>${order.amount}</td>
					<td>${order.status}</td>
				</tr>
			</c:forEach>
        	</tbody>
			</table>
		</div>
		
		<div class="row">
			<div class="col-xs-8">
				<ul class="pagination">
					<c:choose>
						<c:when test="${currentPageIndex == 0}">
							<li class="disabled"><a href="#">&lt;</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${site.domain}/od/myOrder?page=${currentPageIndex-1}">&lt;</a></li>
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
  										<li class="active"><a href="${site.domain}/od/myOrder?page=${item}">${item+1} <span class="sr-only">(current)</span></a></li>
  									</c:when>
  									<c:otherwise>
  										<li><a href="${site.domain}/od/myOrder?page=${item}">${item+1}</a></li>
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
							<li><a href="${site.domain}/od/myOrder?page=${currentPageIndex+1}">&gt;</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="pagination col-xs-4">Showing Results ${resultStart } - ${resultEnd } of <fmt:formatNumber pattern=",###">${resultTotal }</fmt:formatNumber></div>
		</div>
	</c:otherwise>
</c:choose>
<table class="table table-striped">
        <thead>
          <tr>
            <th>#</th>
            <th>Order SN</th>
            <th>Price</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td><a href="">Order #1</a></td>
            <td>$10</td>
            <td>PENDING</td>
          </tr>
          <tr>
            <td>2</td>
            <td><a href="">Order #2</a></td>
            <td>$20</td>
            <td>PAID</td>
          </tr>
          <tr>
            <td>3</td>
            <td><a href="">Order #3</a></td>
            <td>$120</td>
            <td>COMPLETED</td>
          </tr>
        </tbody>
      </table>
</div>
</div>