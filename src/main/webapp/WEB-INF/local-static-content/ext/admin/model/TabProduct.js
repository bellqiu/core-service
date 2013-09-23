 Ext.define('AM.model.TabProduct', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'name',type:'string'}
        ],
        idProperty: 'id'
    });