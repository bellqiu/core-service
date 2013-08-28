Ext.define('AM.store.Setting', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Setting',
    autoLoad: false,
    remoteFilter: true,
	autoSync: false,
	proxy : {
		type : "direct",
		api : {
		  read : settingDirectService.list,
		  update : settingDirectService.update,
		  create : settingDirectService.update,
		  destroy : settingDirectService.destory
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