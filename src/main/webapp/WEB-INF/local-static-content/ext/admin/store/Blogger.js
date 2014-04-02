Ext.define('AM.store.Blogger', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Blogger',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : bloggerDirectService.list,
		  update : bloggerDirectService.update,
		  create : bloggerDirectService.update,
		  destroy : bloggerDirectService.destory
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