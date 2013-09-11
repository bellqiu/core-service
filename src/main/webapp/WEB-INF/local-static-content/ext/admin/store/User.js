Ext.define('AM.store.User', {
    extend: 'Ext.data.Store',
    model: 'AM.model.User',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : userDirectService.list,
		  update : userDirectService.update,
		  create : userDirectService.update,
		  destroy : userDirectService.destory
		},
		reader : {
		  root : "records"
		},
		listeners : {
			exception : function( direct, response, operation, eOpts ){
				 Ext.example.msg('<font color="red">Error</font>', '<font color="red">' + response.message + "</font>");
			}
		},
		batchActions : false
	 }
});