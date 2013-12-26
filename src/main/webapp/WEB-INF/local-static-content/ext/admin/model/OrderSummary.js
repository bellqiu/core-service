 Ext.define('AM.model.OrderSummary', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'orderSN', type:'String'},
           {name:'status',type:'string'},
           {name:'useremail',type:'String'},
           {name:'trackingId', type:'String'},
           {name:'sourceId',type:'string'},
           {name:'amount',type:'float'},
           {name:'orderCurrencyCode',type:'String'}
        ], 
        idProperty: 'id'
    });