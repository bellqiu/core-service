(function($){
	define(
		function() {
			var address = {};
			
			function initAddressModal(){
				if($("#userAddressUpdateModal")){
					
				}
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
			
			
			address.saveAddress = function(id, value, callBack){
				
				if(callBack){
					$.ajax({
						dataType : "json",
						type: "POST",
						url : "/ac/address",
						data : {id: id, newPassword: newPassword},
						success : callBack
					});
				}
			};
			
			address.saveAddress = function(addressJson, callBack){
				initAddressModal();
			};
			
			address.editAddress = function(addressId, callBack){
				initAddressModal();
			};
			
	        return address;
	    }
	);
})(jQuery);
