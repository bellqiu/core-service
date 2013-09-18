Ext.define('AM.store.ProductSummary', {
    extend: 'Ext.data.Store',
    model: 'AM.model.ProductSummary',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : productDirectService.list,
		  update : productDirectService.update
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