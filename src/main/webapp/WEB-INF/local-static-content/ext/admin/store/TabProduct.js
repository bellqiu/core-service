Ext.define('AM.store.TabProduct', {
    extend: 'Ext.data.Store',
    model: 'AM.model.TabProduct',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : tabProductDirectService.list,
		  update : tabProductDirectService.update,
		  create : tabProductDirectService.update,
		  destroy : tabProductDirectService.destory
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