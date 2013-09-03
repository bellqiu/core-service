Ext.define('AM.store.Country', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Country',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : countryDirectService.list,
		  update : countryDirectService.update,
		  create : countryDirectService.update,
		  destroy : countryDirectService.destory
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