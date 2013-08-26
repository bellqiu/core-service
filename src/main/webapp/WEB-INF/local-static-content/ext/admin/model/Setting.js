 Ext.define('AM.model.Setting', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'createDate', type:'date', defaultValue : 0 , convert : function(v,record){return new Date(v)}},
           {name:'updateDate', type:'date',   defaultValue : 0, convert : function(v,record){return new Date(v)}},
           {name:'status',type:'string'},
           {name:'value',type:'string'},
           {name:'name',type:'string'},
           {name:'type',type:'string'},
        ],
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
		 },
        idProperty: 'id'
    });