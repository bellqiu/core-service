(function($){
	define(
		function() {
			var address = {};
			
			function initAddressModal(title){
				if($("#userAddressUpdateModal").length < 1 ){
					$("body").append('<div id="userAddressUpdateModal" class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">'+title+'</h4></div><div class="modal-body"></div><div class="modal-footer"><button type="button" class="btn btn-danger">Save</button></div></div></div></div>');
				}
				
				$("#userAddressUpdateModal").modal();
			}
			
			address.getUserAddresses = function(id, callBack){
				
				if(callBack){
					$.ajax({
						dataType : "json",
						type: "GET",
						url : "/ac/address/list",
						success : callBack
					});
				}
			};
			
			address.getAddressById = function(id, callBack){
				
				if(callBack){
					$.ajax({
						dataType : "json",
						type: "GET",
						url : "/ac/address/one",
						data : {id: id},
						success : callBack
					});
				}
			};
			
			
			address.saveOrupdateAddress = function(addressId, callBack){
				var addressId = parseInt(addressId);
				var title = "New address";
				if(addressId){
					title = "Edit address";
				}
				initAddressModal(title);
			};
			
			
	        return address;
	    }
	);
})(jQuery);
