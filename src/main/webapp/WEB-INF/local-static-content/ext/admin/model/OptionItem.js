 Ext.define('AM.model.OptionItem', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'value',type:'string'},
           {name:'iconUrl',type:'string'},
           {name:'displayName',type:'string'},
           {name:'priceChange',type:'float'}
        ],
        idProperty: 'id'
    });