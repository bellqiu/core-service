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
        idProperty: 'id'
    });