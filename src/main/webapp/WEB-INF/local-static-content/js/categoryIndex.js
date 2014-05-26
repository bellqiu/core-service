(function($){

	$(document).ready(function(){
		/*$(".category-product-img").each(function(index,el){
			$(el).load(function() {
				
				var image = el;
				if(image.naturalWidth < image.naturalHeight) {
					var width = image.naturalWidth * 235 / image.naturalHeight;
					if(width < 235) {
						var imageLink = image.parentElement;
						if(imageLink) {
							$(imageLink).css("margin","0px " + ((235 - width) / 2) + "px");
						}
					}
				} else if(image.naturalWidth > image.naturalHeight) {
					$(image).css("width", "235px");
				}
			});
		});*/
		
		$(".likeSpan").each(function(index,el){
			$(el).click(function() {
				var id = $(this).attr("data-id");
				var likeSpan = this;
				$.ajax({
					url : "/json/changeLike?_tp="+new Date().getTime(),
					type : 'POST',
					data : {"id" : id},
					complete : function(response){
						var num = parseInt(response.responseText);
					 	if(isFinite(num) && num > 1) {
					 		if(likeSpan.firstElementChild) {
					 			$(likeSpan.firstElementChild).text(num);
							}
					 	} else {
					 		alert(":) You have liked it!");
					 	}
					}
				});
			});
		});
		
	});
})(jQuery);