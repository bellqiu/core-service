<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="container-fluid body">
	<tiles:insertAttribute name="adSlider" />

	<div class="bodyContent">
		<div class="row">
			<div class="col-xs-3">
				<div class="row">
					<div class="col-xs-9 col-xs-offset-3">
						<div class="leftNav">
							<h1 class="heading--large">Our categories</h1>
							<ul class="our-categories-list">
								<li><a
									href="/store/msusa/en_US/cat/Surface/categoryID.66734700">Surface</a>
								</li>
								<li><a
									href="/store/msusa/en_US/cat/Xbox/categoryID.62684900">Xbox</a>
								</li>
								<li><a
									href="/store/msusa/en_US/cat/Computers/categoryID.62684600">Computers</a>
								</li>
								<li><a
									href="/store/msusa/en_US/cat/Windows-Phone/categoryID.62685000">Windows
										Phone</a></li>
								<li><a
									href="/store/msusa/en_US/cat/Windows/categoryID.62684800">Windows</a>
								</li>
								<li><a
									href="/store/msusa/en_US/cat/Office/categoryID.62684700">Office</a>
								</li>
								<li><a
									href="/store/msusa/en_US/cat/Accessories/categoryID.62685100">Accessories</a>
								</li>
								<li><a
									href="/store/msusa/en_US/cat/Additional-software/categoryID.62685200">Additional
										software</a></li>
								<li><a
									href="/store/msusa/en_US/list/Education/categoryID.67547700">Education</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-9">
				<tiles:insertAttribute name="spotlight" />

				<tiles:insertAttribute name="bestSelling" />
			</div>
		</div>
		<%-- <div class="row">
			<tiles:insertAttribute name="featuredProducts" />
		</div> --%>
	</div>
</div>

