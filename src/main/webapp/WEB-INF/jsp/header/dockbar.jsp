<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="dockbar">
	<div class="span4 offset8">
	<ul class="dockbar-menu">
		<li>
			<a href="#">Help</span></a>
			
			<ul id="helpMenu">
				<li><a href="#">item1</a></li>
				<li><a href="#">item2</a></li>
				<li><a href="#">item3</a></li>
			</ul>
		</li>
		<li>
			<a href="#">Currency</a>
			
			<ul id="currencyMenu">
				<li><a href="#">item1</a></li>
				<li><a href="#">item2</a></li>
				<li><a href="#">item3</a></li>
			</ul>
		</li>
		<li>
			<a href="#">User</a>
			
			<ul id="userMenu">
				<li><a href="#">item1</a></li>
				<li><a href="#">item2</a></li>
				<li><a href="#">item3</a></li>
			</ul>
		</li>
		<li>
			<a href="#">Cart (0)</a>
		</li>
	</ul>
	</div>
</div>

<script>
	$(function() {
		$( "#helpMenu" ).menu();
		$( "#currencyMenu" ).menu();
		$( "#userMenu" ).menu();
		
		$(".dockbar-menu li a").click(function(){
			var menu = $(this.parentNode).children("ul");
			menu.show();
			$(this.parentNode).mouseleave(function(){
				menu.hide();
			});
		});
	});
</script>