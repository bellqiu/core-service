 Ext.define('AM.model.Country', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'createDate', type:'date', defaultValue : 0 , convert : function(v,record){return new Date(v)}},
           {name:'updateDate', type:'date',   defaultValue : 0, convert : function(v,record){return new Date(v)}},
           {name:'status',type:'string'},
           {name:'code',type:'string'},
           {name:'abbrCode',type:'string'},
           {name:'normalDeliveryPrice',type:'float'},
           {name:'advanceDeliveryPrice',type:'float'},
           {name:'freeDeliveryPrice',type:'float'},
           {name:'freeAdvanceDeliveryPrice',type:'float'}
        ],
        idProperty: 'id'
    });