Ext.define('AM.store.HTML', {
    extend: 'Ext.data.Store',
    model: 'AM.model.HTML',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : htmlDirectService.list,
		  update : htmlDirectService.update,
		  create : htmlDirectService.update,
		  destroy : htmlDirectService.destory
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