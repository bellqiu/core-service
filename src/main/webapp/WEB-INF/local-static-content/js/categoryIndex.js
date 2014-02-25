(function($){

	$(document).ready(function(){
		$(".category-product-img").each(function(index,el){
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
		});
	});
})(jQuery);