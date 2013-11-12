<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dockbar">
	<div class="span4 offset8">
		<ul class="dockbar-menu">
			<li><a href="http://www.google.com" class="btn"><span
					class="glyphicon glyphicon-question-sign"></span><span>Help</span></a>
			</li>
			<li>
				<div class="btn-group">
					<button type="button" class="btn">
						<img alt="" src="/resources/css/img/${defaultCurrency.code }.png">
						${defaultCurrency.name } (Currency)
					</button>
					<button type="button" class="btn dropdown-toggle"
						data-toggle="dropdown">
						<span class="caret"></span> <span class="sr-only">Toggle
							Dropdown</span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<c:forEach items="${currencies }" var="currency">
							<li><a href="?currency=${currency.code }"><img alt=""
									src="/resources/css/img/${currency.code }.png"><span>${currency.name }</a></li>
						</c:forEach>
					</ul>
				</div>
			</li>
			<li>
				<div class="btn-group">
					<button type="button" class="btn">
						<span>User</span><span class="glyphicon glyphicon-info-sign"></span>
					</button>
					<button type="button" class="btn dropdown-toggle"
						data-toggle="dropdown">
						<span class="caret"></span> <span class="sr-only">Toggle
							Dropdown</span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#">User</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Separated link</a></li>
					</ul>
				</div>
			</li>
			<li class=""><a href="http://www.google.com"
				class="btn-group btn open"><span
					class="glyphicon glyphicon-shopping-cart"></span><span>Cart(0)</span></a></li>
		</ul>
	</div>
</div>

