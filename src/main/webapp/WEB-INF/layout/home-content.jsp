<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<div class="container-fluid body">
	<tiles:insertAttribute name="adSlider" />

	<div class="bodyContent">
		<div class="row">
			<div class="col-xs-3">
				<div class="row">
					<div class="col-xs-9 col-xs-offset-3">
						<div class="leftNav">
							<h1 class="heading--large">Our categories</h1>
							<hb:subCategory settingKey="CATEGORY_IN_HOME"/>
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

