<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="hb" uri="/WEB-INF/tag/HBTag.tld"%>
<script type="text/javascript">
var productName = "${currentProductDetail.name}";
var productOpts = "${currentProductOptions}";
</script>
<div class="container mainContainer">
    <div class="row">
		<ol class="breadcrumb">
			<c:forEach items="${currentProductDetail.categories}"
				varStatus="status" var="category">
				<li><a href="/c/${category.name }">${category.displayName }</a></li>
			</c:forEach>
			<li class="active">${currentProductDetail.title }</li>
		</ol>
	</div>
	<div class="row">
		<div class="col-md-7 col-xs-7 padding10 ProductImageRegion">
			<div class="row">
				<div class="col-md-1 col-xs-1">
					<hb:htmltag htmlKey="PRODUCT_SHARE_BUTTONS"/>
				</div>
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
					<span class="gray">Product Id: 
						0000${currentProductDetail.id}
					</span>
					<h3>${currentProductDetail.title}</h3>
				</div>
				<div class="col-md-12 col-xs-12 padding10">
					<b class="priceDuplicate"> 
					
					<hb:printPrice price="${currentProductDetail.price }"/> 
					<span class="productChangedPrice">
						<c:if test="${currentProductProductChange.priceChange > 0.1 }">
							+<hb:printPrice price="${currentProductProductChange.priceChange}" withCurrency="false"/> 
						</c:if>
						<c:if test="${currentProductProductChange.priceChange < (-0.1) }">
							<hb:printPrice price="${currentProductProductChange.priceChange}" withCurrency="false"/> 
						</c:if>
					</span>
					</b> <b class="priceActive">
						<hb:printPrice price="${currentProductDetail.actualPrice}"/>
							<span class="productChangedPrice">
							<c:if test="${currentProductProductChange.priceChange > 0.1 }">
							+<hb:printPrice price="${currentProductProductChange.priceChange }" withCurrency="false"/> 
							</c:if>
							<c:if test="${currentProductProductChange.priceChange < (-0.1) }">
								<hb:printPrice price="${currentProductProductChange.priceChange }" withCurrency="false"/> 
							</c:if>
						</span>
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
				
				

				<div class="col-md-12 col-xs-12 productOptionRegion">
					<c:forEach items="${currentProductDetail.options }" var="opt">
						<c:set var="optIdString">${opt.id }</c:set>
						<c:if test="${opt.type=='SINGLE_ICON_LIST' }">
							<div class="row padding10">
								<div class="col-md-3 col-xs-3">${opt.name }:</div>
								<div class="col-md-9 col-xs-9">
									<div class="btn-group" data-toggle="buttons">
										<c:forEach items="${opt.items }" var="item">
											<c:if test="${(currentProductProductChange.selectedOptOriginal[optIdString] != null && currentProductProductChange.selectedOptOriginal[optIdString] != item.value) || (currentProductProductChange.selectedOptOriginal[optIdString] == null && item.value!=opt.defaultValue)}">
												<label class="btn  btn-info product_opts_input_wrap wantTooltip" data-toggle="tooltip" title='<c:if test="${item.priceChange > 0.1}">(+<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>)</c:if><c:if test="${item.priceChange < (-0.1)}">(<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>)</c:if>'> <input type="radio"
													name="${opt.id }" value="${item.value}" class="product_opts_input">
													${item.displayName}
												</label>
											</c:if>
											<c:if test="${currentProductProductChange.selectedOptOriginal[optIdString] == item.value || (currentProductProductChange.selectedOptOriginal[optIdString] == null && item.value==opt.defaultValue)}">
												<label class="btn  btn-info active product_opts_input_wrap wantTooltip" data-toggle="tooltip" title='<c:if test="${item.priceChange > 0.1}">(+<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>)</c:if><c:if test="${item.priceChange < (-0.1)}">(<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>)</c:if>'> <input
													type="radio" checked="checked" name="${opt.id }"
													value="${item.value}" class="product_opts_input"> ${item.displayName}
												</label>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${opt.type=='SINGLE_TEXT_LIST' }">
							<div class="col-md-3 col-xs-3">${opt.name }:</div>
							<div class="col-md-9 col-xs-9">
								<select class="selectpicker product_opts_select" id="sizePicker" name="${opt.id }"
									data-style="btn-info" >
									<c:forEach items="${opt.items }" var="item">
										<c:if test="${(currentProductProductChange.selectedOptOriginal[optIdString] != null && currentProductProductChange.selectedOptOriginal[optIdString] != item.value) || (currentProductProductChange.selectedOptOriginal[optIdString] == null && item.value!=opt.defaultValue)}">
											<option value="${item.value}">${item.displayName}
												
												<c:if test="${item.priceChange > 0.1}">
														(+
															<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>
														)
													</c:if>
													
													<c:if test="${item.priceChange < (-0.1)}">
														(	<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>
														)
													</c:if>
											
											</option>
										</c:if>
										<c:if test="${currentProductProductChange.selectedOptOriginal[optIdString] == item.value || (currentProductProductChange.selectedOptOriginal[optIdString] == null && item.value==opt.defaultValue)}">
											<option value="${item.value}" selected="selected">${item.displayName}
											
												<c:if test="${item.priceChange > 0.1}">(+<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>)</c:if><c:if test="${item.priceChange < (-0.1)}">(<hb:printPrice price="${item.priceChange}" withCurrency="false"/><hb:printCurrency/>)</c:if>
											</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</c:if>
					</c:forEach>

					<div class="row padding10">
						<div class="col-md-3 col-xs-3">Qty:</div>
						<div class="col-md-3 col-xs-3">
							<div class="input-group">
							<a href="javascript:void(0);" id="decreaseQty" class="input-group-addon">-</a>
							<input type="text" class="form-control width50" id="productAmountCustom" value="1">
							<a href="javascript:void(0);" id="increaseQty" class="input-group-addon">+</a>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12 col-xs-12 padding10">
					<hb:htmltag htmlKey="AD_PRODUCT_DETAIL"/>
				</div>


				<div class="col-md-12 col-xs-12 padding10">
					<form action="/sp/shoppingcart/add" id="add2cartForm" method="post">
						<input name="productName" value="" type="hidden" id="productNameSubmitInput">
						<input name="productOpts" value="" type="hidden" id="productOptsSubmitInput">
						<input name="productAmount" value="" type="hidden" id="productAmountSubmitInput">
						<button type="button" data-loading-text="Processing.."
							class="btn btn-danger" id="add2cartButton">Add to Cart</button>
					</form>
				</div>

			</div>
		</div>
	</div>
	<div class="row">
		<c:forEach items="${currentProductDetail.relatedProducts }" var="tabedProd">
			<h2>${tabedProd.name }:</h2>
			<div class="spotlight">
			<ul class="Related_Product"  class="row">
				<c:forEach items="${tabedProd.products }" var="subProd">
					<li class="col-xs-3 padding10">
						<div class="thumbnail">
							<img src="${site.resourceServer}${site.webResourcesFolder }/${site.productImageResourcesFolder}/${subProd.imageURL}" alt="...">
							<div class="caption">
								<h3>
									<a href="/${subProd.name }" class="relatedProductLink">${subProd.title }</a>
								</h3>
								<p>
									${subProd.abstractText }
								</p>
								<p>
									<a href="/cart/add?productId=${subProd.id }" class="btn btn-primary">Add to Cart</a> 
								</p>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
			</div>
		</c:forEach>
		<script type="text/javascript">
			$(".spotlight").ready(function(){
				  $('.spotlight .Related_Product').bxSlider({
					  captions: true,
					  slideWidth: 290,
					  maxSlides: 4,
					  slideMargin: 10,
					  pager : false,
					  //top : 30%,
					});
				});
		</script>
	</div>

<script src="/resources/js/ProductPageMain.js" type="text/javascript"></script>	
	
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
