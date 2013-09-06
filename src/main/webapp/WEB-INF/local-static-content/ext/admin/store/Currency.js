Ext.define('AM.store.Currency', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Currency',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : currencyDirectService.list,
		  update : currencyDirectService.update,
		  create : currencyDirectService.update,
		  destroy : currencyDirectService.destory
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