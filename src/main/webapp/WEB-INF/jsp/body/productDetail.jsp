<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container mainContainer">
	<ol class="breadcrumb margin0 whiteBackground">
		<li><a href="#">Home</a></li>
		<li><a href="#">Category1</a></li>
		<li class="active">Category2</li>
	</ol>
	<div class="row">
		<div class="col-md-7 col-xs-7 padding10 ProductImageRegion">
			<div class="row">
				<div class="col-md-1 col-xs-1" >
					Buttons
				</div>
				<div class="col-md-11 col-xs-11">
					<ul class="bxslider">
						<li class="ProductImageContainer">1</li>
						<li class="ProductImageContainer">2</li>
						<li class="ProductImageContainer">3</li>
						<li class="ProductImageContainer">4</li>
						<li class="ProductImageContainer">5</li>
						<li class="ProductImageContainer">6</li>
						<li class="ProductImageContainer">7</li>
						<li class="ProductImageContainer">8</li>
						<li class="ProductImageContainer">9</li>
						<li class="ProductImageContainer">10</li>
						<li class="ProductImageContainer">11</li>
						<li class="ProductImageContainer">12</li>
						<li class="ProductImageContainer">13</li>

					</ul>
				</div>
			</div>

			<div class="row">
				<ul id="bx-pager">
					<li><a class="ProductImageController" data-slide-index="0">1</a></li>
					<li><a class="ProductImageController" data-slide-index="1">2</a></li>
					<li><a class="ProductImageController" data-slide-index="2">3</a></li>
					<li><a class="ProductImageController" data-slide-index="3">4</a></li>
					<li><a class="ProductImageController" data-slide-index="4">5</a></li>
					<li><a class="ProductImageController" data-slide-index="5">6</a></li>
					<li><a class="ProductImageController" data-slide-index="6">7</a></li>
					<li><a class="ProductImageController" data-slide-index="7">8</a></li>
					<li><a class="ProductImageController" data-slide-index="8">9</a></li>
					<li><a class="ProductImageController" data-slide-index="9">10</a></li>
					<li><a class="ProductImageController" data-slide-index="10">11</a></li>
					<li><a class="ProductImageController" data-slide-index="11">12</a></li>
					<li><a class="ProductImageController" data-slide-index="12">13</a></li>
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
					maxSlides : 9,
					slideMargin : 5
				});
			</script>

		</div>
		<div class="col-md-5 col-xs-5 padding10">asdas</div>
	</div>
</div>
