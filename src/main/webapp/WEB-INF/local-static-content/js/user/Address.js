(function($){
	define(
		function() {
			var address = {};
			
			function initAddressModal(title, addressId, callBack){
				
				addressId = parseInt(addressId);
				
				if(!addressId){
					addressId = 0;
				}
				
				$("#OrderAddressPanel").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
				
				if($("#userAddressUpdateModal").length < 1 ){
					$("body").append('<div id="userAddressUpdateModal" class="modal fade"><form class="form-inline"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">'+title+'</h4></div><div class="modal-body"></div><div class="modal-footer"><button type="submit" class="btn btn-danger saveBtn" data-loading-text="Processing..">Save</button></div></div></div></form></div>');
				}
				
				$("#userAddressUpdateModal .modal-body").load("/ac/address/fragment?addressId="+addressId, function(){
					$("#OrderAddressPanel").unmask();
					$("#userAddressUpdateModal").modal();
					
					$("#userAddressUpdateModal button.saveBtn").unbind();
					
					$("#userAddressUpdateModal .addressbaody .addresserror").hide();
					
					$("input,select,textarea").not("[type=submit]").jqBootstrapValidation("destroy");
					
					$("input,select,textarea").not("[type=submit]").jqBootstrapValidation({
						preventSubmit: true, 
     	   
                	   submitSuccess: function($form, event) {
                		    $("#userAddressUpdateModal button.saveBtn").button("loading");
                		    var id = $(".addressbaody input[name='id']").val();
                			var firstName = $(".addressbaody input[name='firstName']").val();
    						var lastName = $(".addressbaody input[name='lastName']").val();
    						var address1 = $(".addressbaody input[name='address1']").val();
    						var address2 = $(".addressbaody input[name='address2']").val();
    						var city = $(".addressbaody input[name='city']").val();
    						var country = $(".addressbaody input[name='country']").val();
    						var stateProvince = $(".addressbaody input[name='stateProvince']").val();
    						var postalCode = $(".addressbaody input[name='postalCode']").val();
    						var phone = $(".addressbaody input[name='phone']").val();
    						
    						$.ajax({
    							type: "POST",
    							contentType : "application/json; charset=UTF-8",
    							url : "/ac/address/save",
    							data : JSON.stringify({
    								"id" : id,
    								"firstName" : firstName,
    								"lastName" : lastName,
    								"address1" : address1,
    								"address2" : address2,
    								"city" : city,
    								"countryCode" : country,
    								"stateProvince" : stateProvince,
    								"postalCode" : postalCode,
    								"phone" : phone
    							}),
    							complete : function(response){
    								var address = null;
    								
    								try{
    									address = JSON.parse(response.responseText);
    								}catch(e){
    									
    								}
    								
    								if(callBack && address){
    									callBack(address);
    									$("#userAddressUpdateModal").modal('hide');
    								}else{
    									$("#userAddressUpdateModal .addressbaody .addresserror").show();
    								}
    								
    								$("#userAddressUpdateModal button.saveBtn").button("reset");
    							},
    						});
                		   
		                    event.preventDefault();
                	   }
        
					});
				});
				
				
				
				
				
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
				initAddressModal(title, addressId, callBack);
			};
			
			address.deleteAddress = function(addressId, callBack){
				var addressId = parseInt(addressId);
				
				if(addressId && callBack){
					$.ajax({
						dataType : "json",
						type: "POST",
						url : "/ac/address/delete/" + addressId + "?time=" + new Date().getTime(),
						success : callBack
					});
				}
			};
			
			
	        return address;
	    }
	);
})(jQuery);
