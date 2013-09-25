 Ext.define('AM.model.Coupon', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'createDate', type:'date', defaultValue : 0 , convert : function(v,record){return new Date(v)}},
           {name:'updateDate', type:'date',   defaultValue : 0, convert : function(v,record){return new Date(v)}},
           {name:'status',type:'string'},
           {name:'code',type:'string'},
           {name:'value',type:'float'},
           {name:'usedCount',type:'int'},
           {name:'startDate', type:'date', defaultValue : 0 , convert : function(v,record){return new Date(v)}},
           {name:'endDate', type:'date',   defaultValue : 0, convert : function(v,record){return new Date(v)}},
           {name:'minCost',type:'float'},
           {name:'maxUsedCount',type:'int'},
           {name:'name',type:'string'},
           {name:'desc',type:'string'}
        ],
        idProperty: 'id'
    });