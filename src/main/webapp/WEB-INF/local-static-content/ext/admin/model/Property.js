 Ext.define('AM.model.Property', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'value',type:'string'},
           {name:'name',type:'string'}
        ],
        idProperty: 'id'
    });