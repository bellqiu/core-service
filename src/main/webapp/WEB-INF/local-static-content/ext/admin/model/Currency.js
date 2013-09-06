 Ext.define('AM.model.Currency', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'createDate', type:'date', defaultValue : 0 , convert : function(v,record){return new Date(v)}},
           {name:'updateDate', type:'date',   defaultValue : 0, convert : function(v,record){return new Date(v)}},
           {name:'status',type:'string'},
           {name:'code',type:'string'},
           {name:'name',type:'string'},
           {name:'defaultCurrency',type:'boolean'},
           {name:'exchangeRateBaseOnDefault',type:'float'},
        ],
        idProperty: 'id'
    });