Ext.define('AM.store.OrderSummary', {
    extend: 'Ext.data.Store',
    model: 'AM.model.OrderSummary',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : orderDirectService.list
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