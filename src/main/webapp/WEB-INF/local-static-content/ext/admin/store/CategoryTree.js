Ext.define('AM.store.CategoryTree', {
	extend : 'Ext.data.TreeStore',
	model : 'AM.model.CategoryTree',
	autoLoad : true,
	remoteFilter : true,
	autoSync : false,
	nodeParam : 'id',
	proxy : {
		type : "direct",
		api : {
			read : categoryDirectService.list,
			update : categoryDirectService.update,
			destroy : categoryDirectService.destroy
		},
		batchActions : false,
		listeners : {
			exception : function(direct, response, operation, eOpts) {
				Ext.example.msg('<font color="red">Error</font>',
						'<font color="red">' + response.message + "</font>");
			}
		}
	}
	
});