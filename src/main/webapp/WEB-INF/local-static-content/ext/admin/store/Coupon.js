Ext.define('AM.store.Coupon', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Coupon',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : couponDirectService.list,
		  update : couponDirectService.update,
		  create : couponDirectService.update,
		  destroy : couponDirectService.destory
		},
		reader : {
		  root : "records"
		},
		listeners : {
			exception : function( direct, response, operation, eOpts ){
				 Ext.example.msg('<font color="red">Error</font>', '<font color="red">' + response.message + "</font>");
			}
		}
		,
		batchActions : false
	 }
});