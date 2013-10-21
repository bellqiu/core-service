<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container mainContainer">
	<ol class="breadcrumb margin0 whiteBackground">
		<c:forEach items="${currentProductDetail.categories}"
			varStatus="status" var="category">
			<li><a href="/c/${category.name }">${category.displayName }</a></li>
		</c:forEach>
		<li class="active">${currentProductDetail.title }</li>
	</ol>
	<div class="row">
		<div class="col-md-7 col-xs-7 padding10 ProductImageRegion">
			<div class="row">
				<div class="col-md-1 col-xs-1">Buttons</div>
				<div class="col-md-11 col-xs-11">
					<ul class="bxslider">
						<c:forEach items="${currentProductDetail.images}" var="img">
							<li class="ProductImageContainer"><img
								src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${img.largerUrl}"
								alt="${img.name }"></li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<div class="row">
				<ul id="bx-pager">
					<c:forEach items="${currentProductDetail.images}" var="img"
						varStatus="status">
						<li><a class="ProductImageController"
							data-slide-index="${status.index }"> <img
								src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${img.iconUrl}"
								alt="${img.name }">
						</a></li>
					</c:forEach>
				</ul>
			</div>
			<script type="text/javascript">
				$('.bxslider').bxSlider({
					pagerCustom : '#bx-pager',
					controls : false
				});
				$('#bx-pager').bxSlider({
					controls : true,
					slideWidth : 50,
					pager : false,
					maxSlides : 8,
					slideMargin : 5
				});
			</script>

		</div>
		<div class="col-md-5 col-xs-5 padding10">
			<div class="row ">
				<div class="col-md-12 col-xs-12">
					<h1>${currentProductDetail.title}</h1>
				</div>
				<div class="col-md-12 col-xs-12 padding10">
					<b class="priceDuplicate"> <fmt:formatNumber
							value="${currentProductDetail.price }"></fmt:formatNumber>
					</b> <b class="priceActive"><fmt:formatNumber
							value="${currentProductDetail.actualPrice }"></fmt:formatNumber>

					</b>
				</div>
				<div class="col-md-12 col-xs-12 padding10">
					<p>${currentProductDetail.abstractText}</p>
				</div>
				<div class="col-md-12 col-xs-12 ">
					<c:forEach items="${currentProductDetail.props }" var="prop">
						<div class="row padding10">
							<div class="col-md-3 col-xs-3">${prop.name }</div>
							<div class="col-md-9 col-xs-9">${prop.value}</div>
						</div>
					</c:forEach>
				</div>

				<div class="col-md-12 col-xs-12 ">

					<c:forEach items="${currentProductDetail.options }" var="opt">
						<c:if test="${opt.type=='SINGLE_ICON_LIST' }">
							<div class="row padding10">
								<div class="col-md-3 col-xs-3">${opt.name }:</div>
								<div class="col-md-9 col-xs-9">
									<div class="btn-group" data-toggle="buttons">
										<c:forEach items="${opt.items }" var="item">
											<c:if test="${item.value!=opt.defaultValue }">
												<label class="btn  btn-info"> <input type="radio"
													name="${opt.id }" value="${item.value}">
													${item.displayName}
												</label>
											</c:if>
											<c:if test="${item.value==opt.defaultValue }">
												<label class="btn  btn-info active"> <input
													type="radio" checked="checked" name="${opt.id }"
													value="${item.value}"> ${item.displayName}
												</label>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${opt.type=='MULTI_TEXT_LIST' }">
							<div class="col-md-3 col-xs-3">${opt.name }:</div>
							<div class="col-md-9 col-xs-9">
								<select class="selectpicker" id="sizePicker" name="${opt.id }"
									data-style="btn-info">
									<c:forEach items="${opt.items }" var="item">
										<c:if test="${item.value!=opt.defaultValue }">
											<option value="${item.value}">${item.displayName}</option>
										</c:if>
										<c:if test="${item.value==opt.defaultValue }">
											<option value="${item.value}" selected="selected">${item.displayName}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</c:if>
					</c:forEach>

					<div class="row padding10">
						<div class="col-md-3 col-xs-3">Qty:</div>
						<div class="col-md-9 col-xs-9">
							<input type="text" class="form-control width50" value="1">
						</div>
					</div>
				</div>




				<div class="col-md-12 col-xs-12 padding10">
					<button type="button" data-loading-text="Processing.."
						class="btn btn-danger">Add to Cart</button>
				</div>

			</div>
		</div>
	</div>
	<div class="row">
		<c:forEach items="${currentProductDetail.relatedProducts }" var="tabedProd">
			<h2>${tabedProd.name }:</h2>
			<ul class="Related_Product"  class="row">
				<c:forEach items="${tabedProd.products }" var="subProd" end="3">
					<li class="col-xs-3 padding10">
						<div class="thumbnail">
							<img src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${subProd.imageURL}" alt="...">
							<div class="caption">
								<h3>
									<a href="/${subProd.name }">${subProd.title }</a>
								</h3>
								<p>
									${subProd.abstractText }
								</p>
								<p>
									<a href="/xx" class="btn btn-primary">Add to Cart</a> 
								</p>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
	<div class="row">
		<h2>Details:</h2>
		<ul id="productHTMLs" class="nav nav-tabs">
			<li class="active"><a href="#ProductDetailDESC" data-toggle="tab">Detail</a></li>
			<c:forEach items="${ currentProductDetail.manuals}" var="html">
				<li><a href="#phtml${html.value.id }" data-toggle="tab">${html.key}</a></li>
			</c:forEach>
			
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="ProductDetailDESC">
				${currentProductDetail.detail }
			</div>
			<c:forEach items="${ currentProductDetail.manuals}" var="html">
				<div class="tab-pane fade" id="phtml${html.value.id }">
						${html.value.content}
				</div>
			</c:forEach>
		</div>
	</div>
</div>
